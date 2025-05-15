#region Funciones base

function Show-MessageBox {
    param(
        [string]$message,
        [ValidateSet("Info", "Warning", "Error")] 
        [string]$type = "Info"
    )
    Add-Type -AssemblyName System.Windows.Forms
    
    $icon = switch ($type) {
        "Info" { [System.Windows.Forms.MessageBoxIcon]::Information }
        "Warning" { [System.Windows.Forms.MessageBoxIcon]::Warning }
        "Error" { [System.Windows.Forms.MessageBoxIcon]::Error }
    }

    $title = switch ($type) {
        "Info" { "Info del sistema" }
        "Warning" { "Advertencia" }
        "Error" { "Se ha producido un error" }
    }

    [System.Windows.Forms.MessageBox]::Show($message, $title, [System.Windows.Forms.MessageBoxButtons]::OK, $icon) | Out-Null
}

function Print-Info {
param([string]$msg)

Write-Host "$msg `n" -ForegroundColor Blue
Show-MessageBox $msg -type "Info"
}

function Print-Error {
param([string]$msg)

Write-Host "$msg `n" -ForegroundColor Red
Show-MessageBox $msg -type "Error"
}

function Print-Warn {
param([string]$msg)

Write-Host "$msg `n" -ForegroundColor Yellow
Show-MessageBox $msg -type "Warning"
}

function Limpiar-Ruta-Segura {
    param([string]$texto)
    $texto = $texto -replace '[<>:"\\|?*]', '_'
    $texto = $texto.TrimEnd('. ')
    if ($texto.Length -eq 0) { $texto = "_" }
    return $texto
}

function Get-FileSavePath {
    Add-Type -AssemblyName System.Windows.Forms
    $dialog = New-Object System.Windows.Forms.SaveFileDialog
    $dialog.Filter = "Archivos de texto (*.txt)|*.txt|Todos los archivos (*.*)|*.*"
    $dialog.Title = "Guardar lista de recursos"
    if ($dialog.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) { 
        return $dialog.FileName 
    }
    return $null
}

function Get-FolderPath {
    Add-Type -AssemblyName System.Windows.Forms
    $dialog = New-Object System.Windows.Forms.FolderBrowserDialog
    if ($dialog.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) { 
        return $dialog.SelectedPath 
    }
    return $null
}

function Get-Response {
    param(
        [Parameter(Mandatory = $true)][string]$Url,
        [string]$Cookies = ""
    )

    $headers = @{
        'User-Agent'      = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/123.0.0.0 Safari/537.36'
        'Accept'          = 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8'
        'Accept-Language' = 'es-ES,es;q=0.9'
    }
    if ($Cookies) { $headers['Cookie'] = $Cookies }

    try {
        return Invoke-WebRequest -Uri $Url -UseBasicParsing -Headers $headers -ErrorAction Stop
    } catch {
        Print-Error -msg "Error al hacer la solicitud: $($_.Exception.Message)"
        return $null
    }
}

function Get-WebResources {
    param(
        [Parameter(Mandatory = $true)][object]$Response,
        [Parameter(Mandatory = $false)][string]$Dominio = ""
    )

    $recursos = [System.Collections.Generic.List[string]]::new()

    $html = $Response.Content

    $patrones = @(
        '(?i)(?:src|href|data-src|data-href)\s*=\s*["'']([^"'']+)["'']',
        '(?i)url\(["'']?([^)"'']+)["'']?\)'
    )

    foreach ($patron in $patrones) {
        foreach ($match in [regex]::Matches($html, $patron)) {
            $ruta = $match.Groups[1].Value
            if (![string]::IsNullOrWhiteSpace($ruta) -and -not $recursos.Contains($ruta)) {
                $recursos.Add($ruta)
            }
        }
    }

    if ($Dominio) {
        for ($i = 0; $i -lt $recursos.Count; $i++) {
            if ($recursos[$i] -notmatch '^https?://') {
                if ($recursos[$i].StartsWith('/')) {
                    $recursos[$i] = "$Dominio$($recursos[$i])"
                } else {
                    $recursos[$i] = "$Dominio/$($recursos[$i])"
                }
            }
        }
    }

    return $recursos
}

function Test-WebLogin {
    param(
        [Parameter(Mandatory = $true)][object]$Response
    )

    if ($Response) {
        return ($Response.Content -match "login|signin|logon|password|username|userid|passwd")
    }
    return $false
}

function Save-ResourceList {
    param($recursos, $filePath)
    try {
        $recursos | Out-File -FilePath $filePath -Encoding UTF8
        return $true
    } catch {
        Print-Error -msg "Error al guardar el archivo: $_"
        return $false
    }
}

function Download-Resources {
    param($recursos, $destino, $cookies, $dominio)
    $results = @{
        Total = $recursos.Count
        Success = 0
        Failed = 0
    }

    foreach ($recurso in $recursos) {
        Write-Host "Descargando recurso: $recurso"

        $rutaRelativa = $recurso.Replace("http://$dominio/", "").Replace("https://$dominio/", "").TrimStart('/')
        $archivoDestino = Join-Path $destino (Limpiar-Ruta-Segura $rutaRelativa)
        $directorioDestino = Split-Path $archivoDestino -Parent
        
        if (-not (Test-Path $directorioDestino)) { 
            New-Item -ItemType Directory -Path $directorioDestino -Force | Out-Null 
        }
        
        $respuesta = Get-Response -Url $recurso -Cookies $cookies
        if ($respuesta) {
            [System.IO.File]::WriteAllBytes($archivoDestino, [System.Text.Encoding]::UTF8.GetBytes($respuesta.Content) )
            Write-Host "[OK] Archivo descargado exitosamente" -ForegroundColor Cyan
            $results.Success++
        } else {
            $results.Failed++
        }
        Write-Host
    }

    return $results
}

#endregion

#region Funciones GUI

function Show-ResourceSelectorGUI {
    param($recursos)

    Add-Type -AssemblyName System.Windows.Forms
    Add-Type -AssemblyName System.Drawing

    $form = New-Object System.Windows.Forms.Form
    $form.Text = ""
    $form.FormBorderStyle = 'None'
    $form.Size = New-Object System.Drawing.Size(600, 500)
    $form.StartPosition = "CenterScreen"
    $form.BackColor = 'White'

    #### Barra para mover
    $titleBar = New-Object System.Windows.Forms.Panel
	$titleBar.Width = 64
    $titleBar.Height = 30
    $titleBar.Dock = 'Top'
    $titleBar.BackColor = 'SteelBlue'
    $form.Controls.Add($titleBar)

    $titleLabel = New-Object System.Windows.Forms.Label
    $titleLabel.Text = "Selector de recursos web"
    $titleLabel.ForeColor = 'White'
    $titleLabel.Dock = 'Left'
    $titleLabel.Padding = '10,7,0,0'
    $titleBar.Controls.Add($titleLabel)

    $closeButton = New-Object System.Windows.Forms.Button
    $closeButton.Text = "X"
    $closeButton.ForeColor = 'White'
    $closeButton.BackColor = 'IndianRed'
    $closeButton.FlatStyle = 'Flat'
    $closeButton.Width = 30
    $closeButton.Height = 30
    $closeButton.Dock = 'Right'
    $closeButton.FlatAppearance.BorderSize = 0
    $titleBar.Controls.Add($closeButton)
    $closeButton.Add_Click({ $form.Close() })

    #### Panel para contenido
    $contentPanel = New-Object System.Windows.Forms.Panel
    $contentPanel.Dock = 'Fill'
    $form.Controls.Add($contentPanel)

    #### Barra de estado
    $statusBar = New-Object System.Windows.Forms.StatusStrip
    $statusLabel = New-Object System.Windows.Forms.ToolStripStatusLabel
    $statusLabel.Text = "Listo."
    $statusBar.Items.Add($statusLabel)
    $form.Controls.Add($statusBar)

    #### Controles de contenido
    $filterBox = New-Object System.Windows.Forms.TextBox
    $filterBox.Dock = 'Top'
    $filterBox.Text = "Filtrar..."
    $filterBox.ForeColor = 'Gray'
    $filtering = $false

    $checkedListBox = New-Object System.Windows.Forms.CheckedListBox
    $checkedListBox.Dock = 'Fill'
    $checkedListBox.CheckOnClick = $true

    $selectAllCheckbox = New-Object System.Windows.Forms.CheckBox
    $selectAllCheckbox.Text = "Seleccionar/Deseleccionar todo"
    $selectAllCheckbox.Dock = 'Bottom'

    $okButton = New-Object System.Windows.Forms.Button
    $okButton.Text = "Aceptar"
    $okButton.Dock = 'Bottom'

    #### Agregar en el orden correcto
    $contentPanel.Controls.Add($checkedListBox)      # Lista principal
    $contentPanel.Controls.Add($selectAllCheckbox)   # Checkbox Seleccionar Todo debajo
    $contentPanel.Controls.Add($okButton)             # Botón aceptar abajo
    $contentPanel.Controls.Add($filterBox)            # Caja filtro arriba

# Variables para mover
$dragging = $false
$offset = [System.Drawing.Point]::Empty

$titleBar.Add_MouseDown({
    $dragging = $false
    $mousePos = [System.Windows.Forms.Control]::MousePosition
    $offset = New-Object System.Drawing.Point($mousePos.X - $form.Left, $mousePos.Y - $form.Top)
})

$titleBar.Add_MouseMove({
    if ($dragging) {
        $mousePos = [System.Windows.Forms.Control]::MousePosition
        $form.Location = New-Object System.Drawing.Point($mousePos.X - $offset.X, $mousePos.Y - $offset.Y)
    }
})

$titleBar.Add_MouseUp({
    $dragging = $false
})


    #### Almacenar recursos
    $allItems = @($recursos)

    #### Función refrescar lista
    function Refresh-List {
        param($filter = "")
        $checkedItemsText = @($checkedListBox.CheckedItems | ForEach-Object { $_.ToString() })

        $checkedListBox.Items.Clear()

        $filteredItems = $allItems | Where-Object { $_ -like "*$filter*" }
        foreach ($item in $filteredItems) {
            $index = $checkedListBox.Items.Add($item)
            if ($checkedItemsText -contains $item) {
                $checkedListBox.SetItemChecked($index, $true)
            }
        }

        $statusLabel.Text = "$($checkedListBox.Items.Count) recursos mostrados."
    }

    #### Inicializar lista
    Refresh-List

    #### Eventos
    $filterBox.Add_Enter({
        if (-not $filtering) {
            $filterBox.Text = ""
            $filterBox.ForeColor = 'Black'
            $filtering = $true
        }
    })

    $filterBox.Add_Leave({
        if ($filterBox.Text -eq "") {
            $filterBox.Text = "Filtrar..."
            $filterBox.ForeColor = 'Gray'
            $filtering = $false
        }
    })

    $filterBox.Add_TextChanged({
        if ($filtering) {
            Refresh-List -filter $filterBox.Text
        }
    })

    $selectAllCheckbox.Add_CheckedChanged({
        for ($i = 0; $i -lt $checkedListBox.Items.Count; $i++) {
            $checkedListBox.SetItemChecked($i, $selectAllCheckbox.Checked)
        }
        if ($selectAllCheckbox.Checked) {
            $statusLabel.Text = "Todos seleccionados."
        } else {
            $statusLabel.Text = "Todos desmarcados."
        }
    })

    $okButton.Add_Click({
        $form.DialogResult = [System.Windows.Forms.DialogResult]::OK
        $form.Close()
    })

    if ($form.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) {
        return $checkedListBox.CheckedItems
    }
    return @()
}

#endregion

#region Funciones Lógicas

function Listar-Recursos {
    $global:SiteUrl = Read-Host "Ingresa la URL a analizar"

    if (-not $global:SiteUrl.StartsWith("http")) {
        $global:SiteUrl = "http://" + $global:SiteUrl
    }

    $global:SiteDomain = ([uri]$global:SiteUrl).Host

    $respuesta = Get-Response -Url $global:SiteUrl
    if (-not $respuesta) { return }

    if (Test-WebLogin -Response $respuesta) {
        Write-Host "`nEl sitio parece requerir autenticacion." -ForegroundColor Yellow
        $global:SiteCookies = Read-Host "Ingresa las cookies"
		
		Write-Host "`n"
    }

    $global:WebResources = Get-WebResources -Response $respuesta -Dominio $global:SiteDomain

    if ($global:WebResources.Count -gt 0) {
        Write-Host "======== Recursos encontrados ========`n"
        $index = 1
        foreach ($recurso in $global:WebResources) {
            Write-Host ("[{0}] {1}" -f $index, $recurso) -ForegroundColor Cyan
            $index++
        }

        $exportar = Read-Host "`nExportar lista (S/N)"
        if ($exportar -match "^[sS]$") {
            Write-Host "`nSelecciona el archivo de destino"
            $exportPath = Get-FileSavePath
            if ($exportPath) {
				Write-Host "Ruta seleccionada: $exportPath `n"
                if (Save-ResourceList -recursos $global:WebResources -filePath $exportPath) {
                    Print-Info -msg "Lista exportada correctamente."
                }
            } else {
                Print-Warn -msg "No se seleccionó un archivo de destino."
            }
        }
    } else {
        Print-Warn -msg "No se encontraron recursos en el sitio."
    }
}

function Descargar-Recursos {
    $url = Read-Host "Ingresa la URL a clonar"

    if (-not $url.StartsWith("http")) {
        $url = "http://" + $url
    }

    $dominio = ([uri]$url).Host

    $respuesta = Get-Response -Url $url
    if (-not $respuesta) { return }

    $cookies = ""
    if (Test-WebLogin -Response $respuesta) {
        Write-Host "`nEl sitio requiere autenticacion." -ForegroundColor Yellow
        $cookies = Read-Host "Ingresa las cookies de sesion"
		
		Write-Host "`n"
    }

    $webResources = Get-WebResources -Response $respuesta -Dominio $dominio
    if (-not $webResources -or $webResources.Count -eq 0) {
        Print-Warn -msg "No se encontraron recursos en el sitio."
        return
    }

    $recursosSeleccionados = Show-ResourceSelectorGUI -recursos $webResources
    if (-not $recursosSeleccionados) {
        Print-Warn -msg "No se seleccionaron recursos. Operación cancelada."
        return
    }

    Write-Host "`nSelecciona la carpeta de destino"
    $destino = Get-FolderPath
    if (-not $destino) {
        Print-Warn -msg "No se seleccionó una carpeta. Abortando..."
        return
    }

    Write-Host "Carpeta seleccionada: $destino `n"
	
    Write-Host "Iniciando descarga de $($recursosSeleccionados.Count) recursos..."
    $resultados = Download-Resources -recursos $recursosSeleccionados -destino $destino -cookies $cookies -dominio $dominio

    Write-Host "`n# Archivos descargados: $($resultados.Success)/$($resultados.Total) ($($resultados.Failed) errores)`n" -ForegroundColor Cyan
}

function Verificar-Login {
    $url = Read-Host "Ingresa la URL a verificar"

    if (-not $url.StartsWith("http")) {
        $url = "http://" + $url
    }

    $respuesta = Get-Response -Url $url
    if (-not $respuesta) { return }

    if (Test-WebLogin -Response $respuesta) {
        Print-Warn -msg "El sitio requiere autenticación."
    } else {
        Print-Info -msg "El sitio no requiere autenticación."
    }
}

#endregion

# Main

function Esperar-Tecla {
    Write-Host "`nPresiona una tecla para continuar: " -NoNewline
    [void][System.Console]::ReadKey($false)
    Write-Host "`n"
}

while ($true) {
    Clear-Host

    Write-Host "URL Ripper por FranZ`n"
	
    Write-Host "======================================`n"
	
    Write-Host "1. Listar recursos"
    Write-Host "2. Descargar recursos"
    Write-Host "3. Verificar si el sitio requiere login"
    Write-Host "4. Salir`n"

    $opcion = Read-Host "Selecciona una opcion"
    Write-Host "`n"

    switch ($opcion) {
        "1" { Listar-Recursos; Esperar-Tecla }
        "2" { Descargar-Recursos; Esperar-Tecla }
        "3" { Verificar-Login; Esperar-Tecla }
        "4" { exit }
        default {
            Print-Warn -msg "Opcion no valida."
            Start-Sleep -Seconds 1
        }
    }
}

#endregion
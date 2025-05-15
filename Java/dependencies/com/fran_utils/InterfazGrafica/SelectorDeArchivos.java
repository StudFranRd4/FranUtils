package com.fran_utils.InterfazGrafica;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import com.fran_utils.InterfazGrafica.Ventanas.*;

/** Representa un objeto que permite la selección dinámica de archivos */

public class SelectorDeArchivos extends JFileChooser
{
private static final long serialVersionUID = 1111L;

private VentanaEmergente avisos = new VentanaEmergente();

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos()
{
}

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos(String rutaDir)
{
super(rutaDir);
}

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos(File dirActual)
{
super(dirActual);
}

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos(FileSystemView fsv)
{
super(fsv);
}

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos(File dirActual, FileSystemView fsv)
{
super(dirActual, fsv);
}

/** Crea un nuevo selector de archivos */

public SelectorDeArchivos(String rutaDir, FileSystemView fsv)
{
super(rutaDir, fsv);
}

/** Abre un archivo usando una interfaz gráfica. */

public File abrirArchivo()
{
File archivo = null;
int estadoSeleccion = showOpenDialog(this);

if(estadoSeleccion == JFileChooser.APPROVE_OPTION)
archivo = getSelectedFile();

else
avisos.mostrarAdvertencia(this, "No se seleccionó un archivo.\nAbre uno para procesar sus datos.");

return archivo;
}

/** Guardar un archivo usando una interfaz gráfica. */

public File guardarArchivo()
{
File archivo = null;
int estadoSeleccion = showSaveDialog(this);

if(estadoSeleccion == JFileChooser.APPROVE_OPTION)
archivo = getSelectedFile();

else
avisos.mostrarAdvertencia(this, "No se seleccionó un archivo.\nElije uno para guardar los datos procesados.");

return archivo;
}

}

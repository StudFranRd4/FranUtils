package com.fran_utils.InterfazGrafica.Ventanas;

import java.awt.*;
import javax.swing.*;

/** Representa una ventana que aparece para dar un aviso al usuario */

public class VentanaEmergente extends JOptionPane
{
private static final long serialVersionUID = 5L;

/** Crea una nueva ventana emergente. */

public VentanaEmergente()
{
}

/** Muestra el progreso de una tarea en la ventana emergente  */

public void mostrarProgreso(Component base, JProgressBar barraProgreso)
{
showMessageDialog(base, barraProgreso, "Progreso", JOptionPane.INFORMATION_MESSAGE);
}

/** Muestra un mensaje de advertencia en la ventana emergente  */

public void mostrarAdvertencia(Component base, String msg)
{
showMessageDialog(base, msg, "Advertencia", JOptionPane.WARNING_MESSAGE);
}

/** Muestra un mensaje de error en la ventana emergente  */

public void mostrarError(Component base, Exception error)
{
String tipoDeError = error.getClass().getName();
String nombre = String.format("Error: %s", tipoDeError);

showMessageDialog(base, error.getLocalizedMessage(), nombre, JOptionPane.ERROR_MESSAGE);
}

}
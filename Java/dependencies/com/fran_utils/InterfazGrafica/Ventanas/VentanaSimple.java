package com.fran_utils.InterfazGrafica.Ventanas;

import java.awt.*;
import javax.swing.*;

/** Representa una ventana con elementos simples en la misma */

public class VentanaSimple extends JFrame
{
private static final long serialVersionUID = 1L;

/** Crea una nueva ventana. */

public VentanaSimple()
{
asignarPropiedades();
}

/** Crea una nueva ventana.

@param n El nombre de la ventana */

public VentanaSimple(String n)
{
super(n);
}

/** Crea una nueva ventana.

@param n El nombre de la ventana
@param w El valor de la anchura
@param h El valor de la altura
@param x La coordenada en el Eje X
@param y La coordenada en el Eje Y
@param accion La acción a realizar cuando se cierre la ventana
@param esVisible Determina si la ventana es visible o no */

public VentanaSimple(String n, Color bg, int w, int h, int x, int y, int accion, boolean esVisible)
{
asignarPropiedades(n, bg, w, h, x, y, accion, esVisible);
}

/** Crea una nueva ventana usando una configuración gráfica.

@param gc La configuración gráfica de la ventana */

public VentanaSimple(GraphicsConfiguration gc)
{
super(gc);
}

/** Crea una nueva ventana usando una configuración gráfica.

@param n El nombre de la ventana
@param gc La configuración gráfica de la ventana */

public VentanaSimple(String n, GraphicsConfiguration gc)
{
super(n, gc);
}

/** Crea una nueva ventana.

@param gc La configuración gráfica de la ventana
@param w El valor de la anchura
@param h El valor de la altura
@param x La coordenada en el Eje X
@param y La coordenada en el Eje Y
@param accion La acción a realizar cuando se cierre la ventana
@param esVisible Determina si la ventana es visible o no */

public VentanaSimple(String n, Color bg, GraphicsConfiguration gc, int w, int h, int x, int y, int accion, boolean esVisible)
{
super(gc);

asignarPropiedades(n, bg, w, h, x, y, accion, esVisible);
}

/** Asigna todas las propiedades a la ventana

@param n El nombre de la ventana
@param bg El color de fondo del panel
@param w La anchura de la ventana
@param h La altura de la ventana
@param x La coordenada en el Eje X
@param y La coordenada en el Eje Y
@param accion La accion al cerrar la ventana
@param esVisible Determina si la ventana es visible o no */

public void asignarPropiedades(String n, Color bg, int w, int h, int x, int y, int accion, boolean esVisible)
{
setTitle(n);
setBackground(bg);

setSize(w, h);
setLocation(x, y);

setDefaultCloseOperation(accion);
setVisible(esVisible);
}

/** Asigna las propiedades por default de la ventana */

public void asignarPropiedades()
{
final int w = 200;
final int h = 150;

asignarPropiedades("Mi ventana", Color.LIGHT_GRAY, w, h, w/2, h/2, JFrame.DISPOSE_ON_CLOSE, true);
}

}
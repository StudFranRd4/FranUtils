package com.fran_utils.InterfazGrafica.Ventanas;

import java.awt.*;

import com.fran_utils.InterfazGrafica.AdminDeComponentesGUI;

/** Representa una ventana que admite diferentes componentes gráficos */

public class VentanaGrafica extends VentanaSimple
{
private static final long serialVersionUID = 2L;

/** Crea una nueva ventana gráfica.

@param comp Los componentes a añadir */

public VentanaGrafica(Component... comps)
{
super();

AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea una nueva ventana gráfica usando un delimitador.

@param comp Los componentes a añadir */

public VentanaGrafica(String delim, Component... comps)
{
super();

AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea una nueva ventana gráfica usando un espacio entre elementos.

@param comp Los componentes a añadir */

public VentanaGrafica(int espacio, Component... comps)
{
super();

AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

/** Crea una nueva ventana gráfica.

@param gc La configuración gráfica de la ventana
@param comp Los componentes a añadir */

public VentanaGrafica(GraphicsConfiguration gc, Component... comps)
{
super(gc);

AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea una nueva ventana gráfica.

@param gc La configuración gráfica de la ventana
@param comp Los componentes a añadir */

public VentanaGrafica(GraphicsConfiguration gc, String delim, Component... comps)
{
super(gc);

AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea una nueva ventana gráfica.

@param gc La configuración gráfica de la ventana
@param comp Los componentes a añadir */

public VentanaGrafica(GraphicsConfiguration gc, int espacio, Component... comps)
{
super(gc);

AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

/** Crea una nueva ventana gráfica.

@param n El nombre de la ventana
@param gc La configuración gráfica de la ventana */

public VentanaGrafica(String n, GraphicsConfiguration gc, Component... comps)
{
super(n, gc);

AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea una nueva ventana gráfica.

@param n El nombre de la ventana
@param gc La configuración gráfica de la ventana */

public VentanaGrafica(String n, GraphicsConfiguration gc, String delim, Component... comps)
{
super(n, gc);

AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea una nueva ventana gráfica.

@param n El nombre de la ventana
@param gc La configuración gráfica de la ventana */

public VentanaGrafica(String n, GraphicsConfiguration gc, int espacio, Component... comps)
{
super(n, gc);

AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

}
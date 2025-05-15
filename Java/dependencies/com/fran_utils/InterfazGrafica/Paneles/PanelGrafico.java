package com.fran_utils.InterfazGrafica.Paneles;

import java.awt.*;
import com.fran_utils.InterfazGrafica.*;

/** Representa un panel donde se pueden agrupar varios componentes. */

public class PanelGrafico extends PanelSimple
{
private static final long serialVersionUID = 12L;

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(Component... comps)
{
AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(String delim, Component... comps)
{
AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(int espacio, Component... comps)
{
AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(boolean usarDosBufers, Component... comps)
{
super(usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(boolean usarDosBufers, String delim, Component... comps)
{
super(usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(boolean usarDosBufers, int espacio, Component... comps)
{
super(usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(LayoutManager mgr, boolean usarDosBufers, Component... comps)
{
super(mgr, usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(LayoutManager mgr, boolean usarDosBufers, String delim, Component... comps)
{
super(mgr, usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, delim, comps);
}

/** Crea un nuevo panel gráfico.

@param comp Los componentes a añadir */

public PanelGrafico(LayoutManager mgr, boolean usarDosBufers, int espacio, Component... comps)
{
super(mgr, usarDosBufers);

AdminDeComponentesGUI.agregarComponentes(this, espacio, comps);
}

}

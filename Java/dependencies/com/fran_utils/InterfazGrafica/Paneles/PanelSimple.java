package com.fran_utils.InterfazGrafica.Paneles;

import java.awt.*;
import javax.swing.*;

/** Representa un contenedor gráfico donde se almacenan diferentes tipos de componentes. */

public class PanelSimple extends JPanel
{
private static final long serialVersionUID = 11L;

/** Crea un nuevo panel. */

public PanelSimple()
{
asignarPropiedades();
}

/** Crea un nuevo panel.

@param mgr Una instancia que maneja los componentes en el panel. */

public PanelSimple(LayoutManager mgr)
{
super(mgr);
}

/** Crea un nuevo panel.

@param usarDosBufers Determina si usar dos Buffers en el panel. */

public PanelSimple(boolean usarDosBufers)
{
super(usarDosBufers);
}

/** Crea un nuevo panel.

@param mgr Una instancia que maneja los componentes en el panel.
@param usarDosBufers Determina si usar dos Buffers en el panel. */

public PanelSimple(LayoutManager mgr, boolean usarDosBufers)
{
super(mgr, usarDosBufers);
}

/** Crea un nuevo panel.

@param bg El color de fondo del panel
@param w La anchura del panel
@param h La altura del panel
@param mgr Una instancia que maneja los componentes en el panel. */

public PanelSimple(Color bg, int w, int h, LayoutManager mgr)
{
asignarPropiedades(bg, w, h, mgr);
}

/** Crea un nuevo panel.

@param bg El color de fondo del panel
@param w La anchura del panel
@param h La altura del panel
@param mgr Una instancia que maneja los componentes en el panel. */

public PanelSimple(Color bg, int w, int h, LayoutManager mgr, boolean usarDosBufers)
{
super(usarDosBufers);

asignarPropiedades(bg, w, h, mgr);
}

/** Asigna todas las propiedades al panel

@param bg El color de fondo del panel
@param w La anchura del panel
@param h La altura del panel
@param mgr Una instancia que maneja los componentes en el panel. */

public void asignarPropiedades(Color bg, int w, int h, LayoutManager mgr)
{
setBackground(bg);

setPreferredSize(new Dimension(w, h) );

setLayout(mgr);
}

/** Asigna las propiedades por default al panel */

public void asignarPropiedades()
{
asignarPropiedades(Color.CYAN, 400, 300, new FlowLayout() );
}

}

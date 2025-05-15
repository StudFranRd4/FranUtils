package com.fran_utils.InterfazGrafica.Ventanas;

import java.awt.*;

/** Ventana que permite dibujar texto subrayado */

public class VentanaConTexto2 extends VentanaConTexto
{
private static final long serialVersionUID = 4L;

/** La primera posicion de la línea en el eje horizontal. */

protected int posLineaX1;

/** La primera posicion de la línea en el eje vertical. */

protected int posLineaY1;

/** La segunda posicion de la línea en el eje horizontal. */

protected int posLineaX2;

/** La segunda posicion de la línea en eje vertical. */

protected int posLineaY2;

/** Crea una nueva ventana con texto subrayado. */

public VentanaConTexto2()
{
super();

asignarLinea();
}

/** Crea una nueva ventana con texto subrayado. */

public VentanaConTexto2(int x1, int y1, int x2, int y2)
{
super();

asignarLinea(x1, y1, x2, y2);
}

/** Crea una nueva ventana con texto subrayado. */

public VentanaConTexto2(GraphicsConfiguration gc)
{
super(gc);

asignarLinea();
}

/** Crea una nueva ventana con texto subrayado. */

public VentanaConTexto2(GraphicsConfiguration gc, int x1, int y1, int x2, int y2)
{
super(gc);

asignarLinea(x1, y1, x2, y2);
}

/** Asigna todas las propiedades a la línea a mostrar */

public void asignarLinea(int x1, int y1, int x2, int y2)
{
posLineaX1 = x1;
posLineaY1 = y1;

posLineaX2 = x2;
posLineaY2 = y2;
}

/** Asigna las propiedades por default a la línea a mostrar */

public void asignarLinea()
{
asignarLinea(35, 70, 225, 75);
}

@Override

public void paint(Graphics g)
{
g.setFont(tipoDeLetra);

g.drawString(texto, posTextoX, posTextoY);

g.drawLine(posLineaX1, posLineaY1, posLineaX2, posLineaY2);
}

}

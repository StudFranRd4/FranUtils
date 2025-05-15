package com.fran_utils.InterfazGrafica.Ventanas;

import java.awt.*;

/** Ventana que permite dibujar texto en ella */

public class VentanaConTexto extends VentanaSimple
{
private static final long serialVersionUID = 3L;

/** Una fuente de texto que determina el estilo y el tamaño de los caracteres mostrados. */

protected Font tipoDeLetra;

/** El texto a mostrar. */

protected String texto;

/** La posicion horizontal del texto. */

protected int posTextoX;

/** La posicion vertical del texto. */

protected int posTextoY;

/** Crea una nueva ventana con texto. */

public VentanaConTexto()
{
super();

asignarTexto();
}

/** Crea una nueva ventana con texto.

@param ft La fuente de texto a usar
@param e El estilo del texto
@param s El tamaño del texto
@param msg El mensaje del texto
@param x La coordenada del texto en el Eje X
@param y La coordenada del texto en el Eje Y */

public VentanaConTexto(String ft, int e, int s, String msg, int x, int y)
{
super();

asignarTexto(ft, e, s, msg, x, y);
}

/** Crea una nueva ventana con texto.

@param gc La configuración gráfica de la ventana */

public VentanaConTexto(GraphicsConfiguration gc)
{
super(gc);

asignarTexto();
}

/** Crea una nueva ventana con texto.

@param gc La configuración gráfica de la ventana
@param ft La fuente de texto a usar
@param e El estilo del texto
@param s El tamaño del texto
@param msg El mensaje del texto
@param x La coordenada del texto en el Eje X
@param y La coordenada del texto en el Eje Y */

public VentanaConTexto(GraphicsConfiguration gc, String ft, int e, int s, String msg, int x, int y)
{
super(gc);

asignarTexto(ft, e, s, msg, x, y);
}

/** Asigna todas las propiedades al texto a mostrar

@param ft La fuente de texto a usar
@param e El estilo del texto
@param s El tamaño del texto
@param msg El mensaje del texto
@param x La coordenada del texto en el Eje X
@param y La coordenada del texto en el Eje Y */

public void asignarTexto(String ft, int e, int s, String msg, int x, int y)
{
tipoDeLetra = new Font(ft, e, s);
texto = msg;

posTextoX = x;
posTextoY = y;
}

/** Asigna las propiedades por default al texto a mostrar */

public void asignarTexto()
{
asignarTexto("Default", Font.TRUETYPE_FONT, 12, "Hola, mundo :)", 35, 70);
}

@Override

public void paint(Graphics g)
{
g.setFont(tipoDeLetra);
g.drawString(texto, posTextoX, posTextoY);
}

}

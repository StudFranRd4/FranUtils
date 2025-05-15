package com.fran_utils.InterfazGrafica;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;

/** Representa una barra que reporta el progreso de una acción al usuario */

public class BarraDeProgreso extends JProgressBar
{
private static final long serialVersionUID = 111L;

public BarraDeProgreso()
{
}

public BarraDeProgreso(int orient)
{
super(orient);
}

public BarraDeProgreso(BoundedRangeModel newModel)
{
super(newModel);

}

public BarraDeProgreso(int min, int max)
{
super(min, max);
}

public BarraDeProgreso(int orient, int min, int max)
{
super(orient, min, max);
}

public void estimarProgreso(int actual, int esperado)
{
double progreso = (actual / esperado) * 100;
setValue( (int) progreso);
}

}

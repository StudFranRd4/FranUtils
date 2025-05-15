package com.fran_utils.InterfazGrafica;

import java.awt.*;

import javax.swing.*;

import com.fran_utils.InterfazGrafica.Paneles.PanelSimple;

/** Clase usada para agregar múltiples elementos a un objeto del tipo <b>Component</b> */

public final class AdminDeComponentesGUI
{
/** Agrega una secuencia de componentes a un contenedor

@param base El contenedor donde añadir los componentes
@param comps Los componentes a añadir */

public static void agregarComponentes(Container base, Component... comps)
{

// Procesar cada componente

for(Component comp : comps) 
base.add(comp);
  
}

/** Agrega una secuencia de componentes a un contenedor en un mismo panel

@param base El contenedor donde añadir los componentes
@param comps Los componentes a añadir */

public static void agregarComponentes(Container base, String delim, Component... comps)
{
PanelSimple panel = new PanelSimple();

// Procesar cada componente en un mismo panel

for(Component comp : comps) 
panel.add(comp);

base.add(panel, delim);
}

/** Agrega una secuencia de componentes a un contenedor seguido de un espacio en blanco

@param base El contenedor donde añadir los componentes
@param comps Los componentes a añadir */

public static void agregarComponentes(Container base, int espacio, Component... comps)
{
// Procesar cada componente entre espacios

for(int i = 0; i < comps.length; i++)
{
base.add(comps[i] );

if(i != comps.length - 1)
Box.createHorizontalStrut(espacio);

}

}

}

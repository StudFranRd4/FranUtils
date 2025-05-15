package com.fran_utils.ManejoDeArchivos;

/** Determina si un sistema de archivo es realmente un archivo o un directorio */

public enum FiltroDeArchivos
{
/** No se filtrarán la lista de rutas del sistema. */
NoFiltrar,

/** Filtrar rutas del sistema que coincidan con nombres de archivo */
ObtenerArchivos,

/** Filtrar rutas del sistema que coincidan con nombres de carpeta */
ObtenerCarpetas
}
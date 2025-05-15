package com.fran_utils;

/** Sirve para ordenar las clases heredadas por ID y por nombre. */

public abstract class ClaseOrdenable implements Comparable<ClaseOrdenable>
{
/** Obtiene o asigna un ID para una clase que hereda de este tipo.
@return El ID de la clase. */

public int id;

/** Obtiene o asigna un nombre para una clase que hereda de este tipo.
@return El nombre de la clase. */

public String nombre;

@Override

public int compareTo(ClaseOrdenable otraClase)
{
return Integer.compare(this.id, otraClase.id);
}

}
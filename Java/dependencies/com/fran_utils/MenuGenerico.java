package com.fran_utils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.fran_utils.ConsolaGenerica.EntradaDatos;

/** Sirve como base para la selección de múltiples opciones dentro de una lista.

@param T Determina el tipo de elementos esperados en la lista.
Debe ser del tipo 'ClaseOrdenable' o un tipo heredado de este. */

public abstract class MenuGenerico<T extends ClaseOrdenable>
{
/** Obtiene el menor ID dentro de una lista de elementos tipo 'ClaseOrdenable'

@param lista La lista a analizar
 
@return El ID con el menor valor en la lista. */

private int obtenerMenorID(List<T> lista)
{
Optional<T> elementoMenorID = lista.stream().min( (a, b) -> Integer.compare(a.id, b.id) );
return elementoMenorID.map(c -> c.id).orElseThrow();
}

/** Obtiene el mayor ID dentro de una lista de elementos tipo 'ClaseOrdenable'

@param lista La lista a analizar
 
@return El ID con el mayor valor en la lista. */

private int obtenerMayorID(List<T> lista)
{
Optional<T> elementoMayorID = lista.stream().max( (a, b) -> Integer.compare(a.id, b.id) );
return elementoMayorID.map(c -> c.id).orElseThrow();
}

/** Busca un elemento del tipo 'ClaseOrdenada' que coincida con un número ingresado por el usuario.

@param lista La lista donde se hará la búsqueda.
@param idBuscado El número ingresado por el usuario.

@return El elemento encontrado; null si no se encuentra. */

private T buscarElemento(List<T> lista, int idBuscado)
{
return lista.stream().filter(e -> e.id == idBuscado).findFirst().orElse(null);
}

/** Permite al usuario seleccionar una opción de entre una lista de elementos.

@param listaOpciones Las opciones a elegir. 
@param encabezado El encabezado a mostrar.
@param msgSeleccion El mensaje a mostrar indicando al usuario que haga una selección.
@param lector Instania del Scanner usada para capturar la entrada del usuario por teclado.

@return La opción seleccionada por el usuario. */

public T guardarOpcion(List<T> listaOpciones, String encabezado, String msgSeleccion, Scanner lector)
{
System.out.printf("<--------- %s -------->\n\n", encabezado);

// Verificar si la lista es null o está vacía

if(listaOpciones == null || listaOpciones.isEmpty() ) 
{
System.out.println("\u26A0 No se encontraron opciones.\n");
return null;
}

int minOpcion = obtenerMenorID(listaOpciones);
int maxOpcion = obtenerMayorID(listaOpciones);

int cantOpciones = listaOpciones.size();

// Mostrar las opciones disponibles

for(int i = 0; i < cantOpciones; i++) 
{
mostrarElemento(listaOpciones.get(i) );

if(i == cantOpciones - 1)
System.out.println();

}

int opcionSelec = EntradaDatos.leerNumEnRango(minOpcion, maxOpcion, msgSeleccion, lector);

return buscarElemento(listaOpciones, opcionSelec);
}

/** Método usado para mostrar el elemento actual (debe sobreescribirse en las clases hijas)
@param elemento El elemento a mostrar. */

public abstract void mostrarElemento(T elemento);
}
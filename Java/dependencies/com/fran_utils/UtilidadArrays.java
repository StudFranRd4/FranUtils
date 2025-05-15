package com.fran_utils;

public final class UtilidadArrays
{
// Obtiene la fila con menos elementos dentro de un Array de 2D

public static <T> T[] obtenerFilaMasChica(T[][] arr)
{
T[] filaMenor = null;

int minElementos = Integer.MAX_VALUE;

for(T[] fila : arr)
{
int numElementos = fila.length;

if(numElementos < minElementos)
{
minElementos = numElementos;
filaMenor = fila;
}

}

return filaMenor;
}

// Obtiene la fila con más elementos dentro de un Array de 2D

public static <T> T[] obtenerFilaMasGrande(T[][] arr)
{
T[] filaMayor = null;

int maxElementos = -1;

for(T[] fila : arr)
{
int numElementos = fila.length;

if(numElementos > maxElementos)
{
maxElementos = numElementos;
filaMayor = fila;
}

}

return filaMayor;
}


}
package com.fran_utils.ConsolaGenerica;

import com.fran_utils.UtilidadArrays;

public final class SalidaDatos
{
// Ajustar la longitud de un elemento para mostrarlo en pantalla

private static <T> String ajustarElemento(T elemento)
{
String elementoStr = (elemento != null) ? elemento.toString() : "<Vacío>";

int longitudMaxima = 12;

if(elementoStr.length() > longitudMaxima) 
elementoStr = elementoStr.substring(0, longitudMaxima - 3) + "...";

return elementoStr;
}

// Imprimir Array de una sola dimensión

public static <T> void imprimirArr(T[] arr, boolean mostrarIndices)
{
// Mostrar el Array como tabla si se desean incluir los índices de cada elemento	 
	
if(mostrarIndices)
{
System.out.println("┌──────────────┬──────────┐");
System.out.println("│   Elemento   │  Índice  │");
System.out.println("├──────────────┼──────────┤");
}

// De lo contrario, mostrar el Array de forma simplficada

else 
System.out.print("[ ");

int longitudArr = arr.length;

for (int i = 0; i < longitudArr; i++)
{
T elemento = arr[i];
String elementoStr = ajustarElemento(elemento);

if(mostrarIndices) 
System.out.printf(" │ %12s │ %10d │ %n", elementoStr, i);

else
{
System.out.print(elementoStr);

if(i < longitudArr - 1) 
System.out.print(", ");
		            
}

}

if(mostrarIndices) 
System.out.println("└──────────────┴──────────┘");

else 
System.out.println(" ]");

System.out.println();
}

// Imprimir Array multidimensional

public static <T> void imprimirArrComoTabla(T[][] arr, boolean mostrarIndices)
{
int filas = arr.length;

// Mostrar índices de las columnas si se especifica

if(mostrarIndices)
{
T[] filaMayor = UtilidadArrays.obtenerFilaMasGrande(arr);
int maxColumnas = filaMayor.length;

// El bucle solo muestra los índices de la fila con mayor columnas

for(int i = 0; i < maxColumnas; i++) 
{

if(i == 0)
System.out.printf("                 %s  ", i);
		
else if(i == maxColumnas - 1)
System.out.printf("   %s    Columnas", i);

else
System.out.printf("  %s  ", i);

}

System.out.println();
}

// Bucle anidado para mostrar los elementos de la tabla

for(int x = 0; x < filas; x++)
{
int columnas = arr[x].length;

// Mostrar índice de las filas si se especifica
		
if(mostrarIndices)
{

if(x == 0)
System.out.printf("Filas     %d  ", x);

else
System.out.printf("          %d  ", x);

}

// Ahora, imprimir cada elemento de las columnas

for(int y = 0; y < columnas; y++)
{
T elemento = arr[x][y];
String elementoStr = ajustarElemento(elemento);

if(y == 0)
System.out.printf("[  %s  ", elementoStr);

else if(y == columnas - 1)
	System.out.printf("  %s  ]", elementoStr);

else
System.out.printf("  %s  ", elementoStr);

}

System.out.println();
}

System.out.println();
}

}
package com.fran_utils.ConsolaGenerica;

import java.util.*;

/** Maneja de forma manual la entrada de datos hecha por el usuario. */

public final class EntradaDatos
{
/** Lee un valor según el tipo esperado por el programador

@param <T> El tipo de dato a obtener (puede ser un bool, int, double, float, char o string)
@param lector Instancia del Scanner usada para capturar la entrada del usuario por teclado.
@param mensaje
 
@return El valor ingresado por el usuario */

@SuppressWarnings("unchecked")

public static <T> T leerValor(Scanner lector, String mensaje, Class<T> tipo)
{
// Asignar valor por default al mensaje si es null o está vacío

String msgDef = String.format("Ingresa un valor del tipo %s", tipo.toString() );
mensaje = Objects.requireNonNullElse(mensaje, msgDef);

System.out.printf("* %s: ", mensaje);

T valor = null;

while(true)
{

try
{
// Verificar si T es int y leer un entero en caso de que lo sea

if(tipo == Integer.class || tipo == int.class) 
{
valor = (T)Integer.valueOf(lector.nextInt() );
lector.nextLine();
}

// Verificar si T es double y leer un decimal doble en caso de que lo sea
                
else if(tipo == Double.class || tipo == double.class)
{
valor = (T)Double.valueOf(lector.nextDouble() );
lector.nextLine();
}

// Verificar si T es float y leer un decimal en caso de que lo sea

else if(tipo == Float.class || tipo == float.class)
{
valor = (T)Float.valueOf(lector.nextFloat() );
lector.nextLine();
}

// Verificar si T es bool y leer un booleano en caso de que lo sea
                
else if(tipo == Boolean.class)
valor = (T)Boolean.valueOf(lector.nextBoolean() );

// Verificar si T es char y leer un caracter en caso de que lo sea
      
else if(tipo == Character.class || tipo == char.class) 
{
valor = (T)Character.valueOf(lector.next().charAt(0) );
lector.nextLine();
}

// Leer una cadena de texto como valor por default

else
return (T)lector.nextLine();

return valor;
}

catch(InputMismatchException error)
{
System.out.println("El valor está en un formato incorrecto.\n");
}

System.out.printf("%s: ", mensaje);
lector.next();
}

}

/** Lee un número ingresado bajo un límite específico.

@param valorMin El mínimo valor aceptado. 
@param valorMax El máximo valor aceptado.
@param msgSelec El mensaje a mostrar indicando al usuario que haga una selección.
@param lector Instancia del Scanner usada para capturar la entrada del usuario por teclado.

@return El número ingresado por el usuario. */

public static int leerNumEnRango(int valorMin, int valorMax, String msgSelec, Scanner lector)
{
// Asignar valor por default al mensaje si es null o está vacío

msgSelec = Objects.requireNonNullElse(msgSelec, "Ingresa un valor");
	
int entrada = -1;

while(entrada < valorMin || entrada > valorMax)
{
String msgLim = String.format("%s (solo se aceptan valores entre %d y %d)", msgSelec, valorMin, valorMax);
entrada = leerValor(lector, msgLim, int.class);

System.out.println();
}

return entrada;
}

/** Lee una secuencia de elementos en un arreglo

@param <T> El tipo de arreglo a leer
@param lector Instancia del Scanner usada para capturar la entrada del usuario por teclado.
 
@return El arreglo ingresado */
	
@SuppressWarnings("unchecked")

public static <T> T[] leerArreglo(String cuerpo, String msgSelec, Scanner lector, Class<T> tipo)
{
// Asignar valor por default al cuerpo y al mensaje si son null o están vacíos

cuerpo = Objects.requireNonNullElse(cuerpo, "Ingresa una longitud para el arreglo");
msgSelec = Objects.requireNonNullElse(msgSelec, "Ingresa un valor para el elemento");

// Obtener la longitud del Array definida por el usuario

int longitud = EntradaDatos.leerValor(lector, cuerpo, int.class);

Object[] arreglo = new Object[longitud];

// Obtener los datos uno por uno

for(int i = 0; i < arreglo.length; i++)
{
String msg = String.format("%s #%d", msgSelec, i+1);
arreglo[i] = EntradaDatos.leerValor(lector, msg, tipo);
}

return (T[]) arreglo; // Podría dar error si T no es del tipo Object
}

/** Lee una secuencia de elementos en un arreglo bidimensional

@param <T> El tipo de arreglo a leer
@param lector Instancia del Scanner usada para capturar la entrada del usuario por teclado.
 
@return El arreglo ingresado */
	
@SuppressWarnings("unchecked")

public static <T> T[][] leerMatriz(Scanner lector, Class<T> tipo)
{
// Obtener la cantidad de filas para el Array

int filas = leerValor(lector, "Ingresa el número de filas para el arreglo", int.class);

// Obtener la cantidad de columnas para el Array

int columnas = leerValor(lector, "Ingresa el número de columnas para el arreglo", int.class);

Object[][] matriz = new Object[filas][columnas];

// Obtener los datos uno por uno

for (int i = 0; i < filas; i++)
{

for (int j = 0; j < columnas; j++)
{
String msg = String.format("Ingresa un valor para la posición (%d, %d)", i+1, j+1);
matriz[i][j] = leerValor(lector, msg, tipo);
}

}

return (T[][]) matriz; // Podría dar error si T no es del tipo Object
}

/** Permite al usuario elegir un booleano por teclado (0 para false; 1 para true)
 * 
 * @param encabezado
 * @param cuerpo
 * @param msgSelec
 * @param lector
 * @return
 */

public static boolean selecBooleana(String encabezado, String cuerpo, String msgSelec, Scanner lector)
{
// Asignar valor por default al encabezado, cuerpo y al mensaje si son null o están vacíos

encabezado = Objects.requireNonNullElse(encabezado, "Selección booleana");
cuerpo = Objects.requireNonNullElse(cuerpo, "<%d> para false o <%d> para true");
msgSelec = Objects.requireNonNullElse(msgSelec, "Confirmar selección");

System.out.printf("i [ ¿ %s ? ]\n\n", encabezado);

final int FALSO = 0;
final int VERDADERO = 1;

System.out.println(String.format(cuerpo, FALSO, VERDADERO) + ".\n");

int boolNumerico = leerNumEnRango(FALSO, VERDADERO, msgSelec, lector);
return boolNumerico == VERDADERO;
}

// Sele Enum

public static <T extends Enum<T>> T selecEnum(String msgSelec, Class<T> enumType, Scanner lector)
{
msgSelec = Objects.requireNonNullElse(msgSelec, "Elige el valor numérico de la opción");

T[] valores = enumType.getEnumConstants();

for(int i = 0; i < valores.length; i++) 
System.out.printf("%s => %d\n", valores[i].name(), valores[i].ordinal() );

int valorMenor = Arrays.stream(valores).mapToInt(Enum::ordinal).min().orElse(-1);
int valorMayor = Arrays.stream(valores).mapToInt(Enum::ordinal).max().orElse(-1);

int enumOrdinal = leerNumEnRango(valorMenor, valorMayor, msgSelec, lector);

T seleccion = Arrays.stream(valores).filter(v -> v.ordinal() == enumOrdinal).findFirst().orElse(null);

if (seleccion != null)
System.out.printf("Seleccionaste: %s (%d)", seleccion, seleccion.ordinal() );

else
System.out.println("Opción no válida.");

return seleccion;
}

}
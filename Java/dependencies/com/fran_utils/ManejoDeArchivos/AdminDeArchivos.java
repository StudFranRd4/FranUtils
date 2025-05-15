package com.fran_utils.ManejoDeArchivos;

import java.io.*;
import java.nio.file.*;

public final class AdminDeArchivos
{
// Estimar tamaño en otras unidades dependiendo de la cantidad de bytes

public static String obtenerTamañoReal(long cantidad)
{
final long KB = 1024;
final long MB = KB * 1024;
final long GB = MB * 1024;

double factor;
String simbolo;

// Calcular tamaño en Gigabytes

if(cantidad >= GB)
{
factor = (double) cantidad / GB;
simbolo = "GB";
}

// Calcular tamaño en Megabytes

else if(cantidad >= MB)
{
factor = (double) cantidad / MB;
simbolo = "MB";
}

// Calcular tamaño en Kilobytes

else if(cantidad >= KB)
{
factor = (double) cantidad / KB;
simbolo = "KB";
}

// Calcular tamaño en Bytes (por default)

else
{
factor = cantidad;
simbolo = "B";
}

long tamaño = (long) Math.ceil(factor);
return String.format("%,d %s", tamaño, simbolo);
}

// Cuenta la cantidad de archivos o subcarpetas dentro de una carpeta

public static int contarEntradas(File carpeta, boolean contarSubCarpetas)
{
File[] listaEntradas = carpeta.listFiles();

int entradasContadas = 0;

for(File entrada : listaEntradas)
{
// Verificar si la entrada es una sub-carpeta

if(entrada.isDirectory() )
{
// Contar las carpetas dentro de esa sub-carpeta

if(contarSubCarpetas)
entradasContadas++;

entradasContadas += contarEntradas(entrada, contarSubCarpetas); // Recursividad para sub-carpetas y archivos
}

// Si solo se desean contar archivos, verificar si la entrada es uno

else
{
// Contar solo las entradas si son archivos

if(!contarSubCarpetas)
entradasContadas++;
 
}

}

return entradasContadas;
}

// Obtener tamaño total de una carpeta

public static long obtenerTamañoCarpeta(File carpeta)
{
File[] listaEntradas = carpeta.listFiles();

long tamaño = 0;

for(File entrada : listaEntradas)
{
// Si la entrada es un archivo, obtener su tamaño

if(entrada.isFile() ) 
tamaño += entrada.length();

// De lo contrario, asumir que es una sub-carpeta y aplicar la recursividad en ella
        
else 
tamaño += obtenerTamañoCarpeta(entrada);
        
}

return tamaño;
}

// Crear un nuevo archivo

public static void nuevoArchivo(File archivo)
{

try
{
System.out.printf("Creando el archivo \"%s\"...\n", archivo.getName() );
	
// Verificar si el archivo ya existe y crearlo solo en caso de que no exista
		
if(archivo.createNewFile() ) 
System.out.printf("Archivo creado en \"%s\".\n", archivo.getParent() );
	        
else 
System.out.println("El archivo ya existe.");
	  
}

catch(IOException error)
{
System.out.println("Error al crear el archivo:\n");
error.printStackTrace();
}

}

// Crear nueva carpeta

public static void nuevaCarpeta(File carpeta)
{
System.out.printf("Creando la carpeta \"%s\"...\n", carpeta.getName() );

// Verificar si la carpeta ya existe

if(carpeta.exists() )
System.out.println("La carpeta ya existe.");

else
{
// Crear la carpeta

if(carpeta.mkdirs() ) 
System.out.printf("Carpeta creada en \"%s\".\n", carpeta.getParent() );

else 
System.out.println("Error al crear la carpeta.");

}

}

// Crear nuevo archivo o carpeta (según lo especificado)

public static void crearNuevo(String ruta, boolean tratarRutaComoDir)
{
File sistemaDeArchivos = new File(ruta);

if(tratarRutaComoDir)
nuevaCarpeta(sistemaDeArchivos);

else
nuevoArchivo(sistemaDeArchivos);

}

// Renombrar archivo o carpeta

public static void renombrar(File sistemaDeArchivos, String nuevoNombre)
{
File nuevoSis = new File(nuevoNombre);

// Renombrar

if(sistemaDeArchivos.renameTo(nuevoSis) )
System.out.printf("Se cambió el nombre de \"%s\" a \"%s\".\n", sistemaDeArchivos.getName(), nuevoSis.getName() );

else 
System.out.println("Error al cambiar el nombre.");

}

// Copiar un archivo

public static void copiarArchivo(Path origen, Path destino, StandardCopyOption opcionCopiado)
{

try
{
System.out.printf("Copiando el archivo \"%s\" de \"%s\" a \"%s\"...\n", origen.getFileName(), origen, destino);

Files.copy(origen, destino, opcionCopiado);

System.out.println("Archivo copiado con éxito.");
}

catch(IOException error)
{
System.out.println("Error al copiar el archivo:\n");

error.printStackTrace();

System.out.println();
}

}

// Copiar una carpeta

public static void copiarCarpeta(Path origen, Path destino, StandardCopyOption opcionCopiado)
{

try
{
System.out.printf("Copiando la carpeta \"%s\" de \"%s\" a \"%s\"...\n", origen.getFileName(), origen, destino);

Files.walk(origen)
.forEach(source -> copiarArchivo(source, destino.resolve(origen.relativize(source) ), opcionCopiado) );


System.out.println("Carpeta copiada con éxito.");
} 

catch(IOException error)
{
System.out.println("Error al copiar la carpeta:\n");
error.printStackTrace();
}

}

// Copiar carpeta o archivo

public static void copiar(Path origen, Path destino, StandardCopyOption opcionCopiado)
{
// Verificar si la ruta se refiere a un directorio y copiarlo como carpeta

if(Files.isDirectory(origen) )
copiarCarpeta(origen, destino, opcionCopiado);

// De lo contrario, copiar un único archivo

else
copiarArchivo(origen, destino, opcionCopiado);

}

// Mover un archivo o carpeta

public static void mover(Path origen, Path destino, StandardCopyOption opcionCopiado)
{

try
{
System.out.printf("Moviendo \"%s\" de \"%s\" a \"%s\"...\n", origen.getFileName(), origen, destino);

Files.move(origen, destino, opcionCopiado);

System.out.println("Elemento movido con éxito.");
} 

catch(IOException error)
{
System.out.println("Error al mover el elemento:\n");
error.printStackTrace();
}

}

// Eliminar un archivo o carpeta

public static void eliminar(Path ruta)
{

try
{
System.out.printf("Eliminando \"%s\"...\n", ruta.getFileName() );

Files.deleteIfExists(ruta);

System.out.println("Borrado con éxito.");
} 

catch(IOException error)
{
System.out.println("Error al eliminar el elemento:\n");
error.printStackTrace();
}

}

// Leer los bytes de un archivo

public static byte[] leerArchivo(File archivo)
{
byte[] datos = new byte[2];

try(FileInputStream flujoEntrada = new FileInputStream(archivo) )
{
datos = new byte[ (int) archivo.length() ];
flujoEntrada.read(datos);
}

catch(IOException error)
{
error.printStackTrace();
}

return datos;
}

// Escribir bytes a un archivo

public static void escribirDatos(File archivo, byte[] datos)
{

try(FileOutputStream flujoSalida = new FileOutputStream(archivo) )
{        
flujoSalida.write(datos);
} 

catch(IOException error)
{
error.printStackTrace();
}

}

}
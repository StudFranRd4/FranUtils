package com.fran_utils.ManejoDeArchivos;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.fran_utils.ClaseOrdenable;

/** Representa un archivo o un directorio que puede ordenarse por id */

public class SistemaDeArchivos extends ClaseOrdenable
{
// Directorio del usuario

private String directorioPorDef = System.getProperty("user.home");

/** Obtiene o asigna la ruta donde se encuentra sistema de archivos
@return La ruta al sistema de archivos. */

public Path ruta;

/** Obtiene o asigna un valor que determina si el sistema de archivos es un archivo o una carpeta. 
@returns Un valor usado para clasificar el sistema de archivos . */

protected AtributoDeArchivo atributo;

/** Obtiene o crea una lista de subSistemas dentro de este sistema de archivos si es una carpeta 
@returns La lista de subSistemas . */

public List<SistemaDeArchivos> subSistemas;

// ctor por default

public SistemaDeArchivos()
{
id = 0;
ruta = Paths.get(directorioPorDef);

nombre = ruta.getFileName().toString();
asignarAtributo();
}

// ctor sobrecargado

public SistemaDeArchivos(int i, Path r)
{
id = i;
ruta = r;

nombre = ruta.getFileName().toString();
asignarAtributo();
}

// Obtener listas de sistemas de archivos si este sistema es un Directorio

public void asignarSubSistemas(FiltroDeArchivos filtro)
{
subSistemas = new ArrayList<>();

if(atributo != AtributoDeArchivo.Directorio)
return;

else
{
int i = 0;

try
{
DirectoryStream<Path> stream = Files.newDirectoryStream(ruta);

for(Path ruta : stream)
{
boolean esCarpeta = Files.isDirectory(ruta);

// Filtrar entradas

switch(filtro)
{
case ObtenerCarpetas:

if(esCarpeta)
subSistemas.add(new SistemaDeArchivos(i++, ruta) );

break;

case ObtenerArchivos:
	
if(!esCarpeta)
subSistemas.add(new SistemaDeArchivos(i++, ruta) );

break;

default:
subSistemas.add(new SistemaDeArchivos(i++, ruta) );
break;
}

}

}

// Error de acceso denegado

catch (AccessDeniedException error)
{
System.out.println("Acceso denegado: " + ruta);
}

// Manejar otros tipos de excepciones que puedan lanzarse

catch (IOException otroError)
{
otroError.printStackTrace();
}

}

}

public List<SistemaDeArchivos> obtenerSubSistemas()
{
return subSistemas;
}

// Verifica si el sistema es un directorio o un archivo y lo asigna como atributo

private void asignarAtributo()
{
atributo = Files.isDirectory(ruta) ? AtributoDeArchivo.Directorio : AtributoDeArchivo.Archivo;
}

// Obtener atributo del sistema de archivos

public AtributoDeArchivo obtenerAtributo()
{
return atributo;
}

}
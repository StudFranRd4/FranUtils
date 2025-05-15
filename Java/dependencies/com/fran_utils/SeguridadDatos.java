package com.fran_utils;

import java.security.*;
import java.util.*;

/** Clase usada para manejar datos de manera segura */

public final class SeguridadDatos
{
// Obtener la lista de proveedores de seguridad
	
private static Provider[] proveedores = Security.getProviders();

// Convertir bytes en cadena hex

public static String obtenerStrHex(byte[] bytes)
{
StringBuilder strHex = new StringBuilder();

for(byte b : bytes)
{
String hex = String.format("%02X", b);
strHex.append(hex);
}
    
return strHex.toString() ;
}

// Obtener hash de unos datos y pasarlos a hex

public static String obtenerHash(String tipoHash, byte[] datos)
{
String hash = "";

try
{
MessageDigest digest = MessageDigest.getInstance(tipoHash);

byte[] hashBytes = digest.digest(datos);

hash = obtenerStrHex(hashBytes).toLowerCase();
} 

catch (NoSuchAlgorithmException error)
{
error.printStackTrace();
}

return hash;
}

// Listar algoritmos disponibles (Cipher, KeyGenerator o MessageDigest)

public static List<String> obtenerAlgoritmos(String criterio)
{
List<String> listaAlgs = new ArrayList<>();

// Iterar sobre cada proveedor de seguridad

for(Provider proveedor : proveedores)
{
// A su vez, iterar entre cada servicio del proveedor

for(Provider.Service servicio : proveedor.getServices() ) 
{
// Verificar si el servicio coincide con lo deseado y agregarlo a la lista

if(servicio.getType().equals(criterio) ) 
listaAlgs.add(servicio.getAlgorithm() );

}

}

return listaAlgs;
}

}
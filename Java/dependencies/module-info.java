/** <b>[Modulo del proyecto]</b>

<br>Permite el acceso a varias implementaciones de clases en Java a modo de extensiones  */

module FranUtilsMod
{
// Definir las dependencias aquí

requires transitive java.desktop;

// Exportar los paquetes del proyecto aquí

exports com.fran_utils;
exports com.fran_utils.ConsolaGenerica;
exports com.fran_utils.ManejoDeArchivos;
exports com.fran_utils.InterfazGrafica;
exports com.fran_utils.InterfazGrafica.Ventanas;
exports com.fran_utils.InterfazGrafica.Paneles;
}
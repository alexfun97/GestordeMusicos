<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMensaje = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error



$queryCantante = "SELECT * FROM cantante";

$queryGenero = "SELECT * FROM genero";

$resultCantante = $conn->query ( $queryCantante );

$resultGenero = $conn->query ( $queryGenero );


if ((isset ( $resultCantante ) && $resultCantante ) && (isset ( $resultGenero ) && $resultGenero )) { // Si pasa por este if, la query está está bien y se obtiene resultado
	
	if (($resultCantante->num_rows > 0) && ($resultGenero->num_rows > 0)) { // Aunque la query esté bien puede no obtenerse resultado (tabla vacía). Comprobamos antes de recorrer
		
		$arrCantantes = array();
		
		while ( $row = $resultCantante->fetch_assoc () ) {
			
			// Por cada vuelta del bucle creamos un cantante. Como es un objeto hacemos un array asociativo
			$arrCantante = array();
			// Por cada columna de la tabla creamos una propiedad para el objeto
			$arrCantante["id"] = $row["ID"];
			$arrCantante["nombre"] = $row["nombre"];
			$arrCantante["fecha"] = $row["fechaNac"];
			$arrCantante["nacionalidad"] = $row["nacionalidad"];
			$arrCantante["genero"] = $row["genero"];
			// Por último, añadimos el nuevo jugador al array de cantantes
			$arrCantantes[] = $arrCantante;
			
		}

		$arrGeneros = array();

		while ( $row = $resultGenero->fetch_assoc () ) {
			
			// Por cada vuelta del bucle creamos un genero. Como es un objeto hacemos un array asociativo
			$arrGenero = array();
			// Por cada columna de la tabla creamos una propiedad para el objeto
			$arrGenero["id"] = $row["ID"];
			$arrGenero["nombre"] = $row["nombre"];
			$arrGenero["creacion"] = $row["añoCreacion"];
			// Por último, añadimos el nuevo jugador al array de generos
			$arrGeneros[] = $arrGenero;
			
		}

		// Añadimos al $arrMensaje el array de cantantes y generos y añadimos un campo para indicar que todo ha ido OK
		$arrMensaje["estado"] = "ok";
		$arrMensaje["cantantes"] = $arrCantantes;
		$arrMensaje["generos"] = $arrGeneros;
		
		
	} else {
		
		$arrMensaje["estado"] = "ok";
		$arrMensaje["cantantes"] = [];
		$arrMensaje["generos"] = []; // Array vacío si no hay resultados
	}
	
} else {
	
	$arrMensaje["estado"] = "error";
	$arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
	$arrMensaje["error"] = $conn->error;
	$arrMensaje["queryCantante"] = $queryCantante;
	$arrMensaje["queryGenero"] = $queryGenero;
	
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas 
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

?>
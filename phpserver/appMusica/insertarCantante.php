<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD
require 'jsonEsperado.php';

$arrMensaje = array(); // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

/*
 * Lo primero es comprobar que nos han enviado la información via JSON
 */

$parameters = file_get_contents("php://input");

if(isset($parameters)){

	// Parseamos el string json y lo convertimos a objeto JSON
	$mensajeRecibido = json_decode($parameters, true);
	// Comprobamos que están todos los datos en el json que hemos recibido
	// Funcion declarada en jsonEsperado.php
	if(JSONCorrecto($mensajeRecibido)){

		$cantante = $mensajeRecibido["insertarCantante"]; 
		
		$nombre = $cantante["nombre"];
		$fecha = $cantante["fecha"];
		$nacionalidad = $cantante["nacionalidad"];
		$genero = $cantante["genero"];
		
		$query  = "INSERT INTO  cantante (ID, nombre, fechaNac, nacionalidad, genero)";
		$query .= "VALUES (NULL,'$nombre','$fecha','$nacionalidad',$genero)";
		
		$result = $conn->query ( $query );
		
		if (isset ( $result ) && $result) {
			
			$arrMensaje["estado"] = "ok";
			$arrMensaje["mensaje"] = "Cantante insertado correctamente";
			
		}else{ 
			
			$arrMensaje["estado"] = "error";
			$arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
			$arrMensaje["error"] = $conn->error;
			$arrMensaje["query"] = $query;
			
		}

		
	}else{
		
		$arrMensaje["estado"] = "error";
		$arrMensaje["mensaje"] = "EL JSON NO CONTIENE LOS CAMPOS ESPERADOS";
		$arrMensaje["recibido"] = $mensajeRecibido;
		$arrMensaje["esperado"] = $arrEsperado;
	}

}else{
	
	$arrMensaje["estado"] = "error";
	$arrMensaje["mensaje"] = "EL JSON NO SE HA ENVIADO CORRECTAMENTE";
	
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

die();

?>
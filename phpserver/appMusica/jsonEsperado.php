<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrCantanteEsperado = array();


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrecto($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["insertarCantante"])){
		
		$auxCantante = $recibido["insertarCantante"];
		if(isset($auxCantante["nombre"]) && isset($auxCantante["fecha"]) && isset($auxCantante["nacionalidad"]) && isset($auxCantante["genero"])){
			$auxCorrecto = true;
		}
		
	} else if(isset($recibido["peticion"]) && $recibido["peticion"] ="delete" && isset($recibido["borrarCantante"])){

		$auxCantante = $recibido["borrarCantante"];
		if(isset($auxCantante["id"]) ){
			$auxCorrecto = true;
		}

	} else if(isset($recibido["peticion"]) && $recibido["peticion"] ="update" && isset($recibido["actualizarCantante"])){

		$auxCantante = $recibido["actualizarCantante"];
		if(isset($auxCantante["nombre"]) && isset($auxCantante["fecha"]) && isset($auxCantante["nacionalidad"]) && isset($auxCantante["genero"])){
			$auxCorrecto = true;
		}

	}
	
	
	return $auxCorrecto;
	
}

package modelo;

import java.util.ArrayList;

public class Genero {
	
	private int ID;
	private String Nombre;
	private int Año;
//	private ArrayList<Cantante> arrayCantantes;
	
	public Genero(){
		
	}
	
	public Genero(int iD, String nombre, int año) {
		ID = iD;
		Nombre = nombre;
		Año = año;
		//setArrayCantantes(arC);
	}

	public Genero(String nombre, int año) {
		Nombre = nombre;
		Año = año;
//		setArrayCantantes(arC);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public int getAño() {
		return Año;
	}

	public void setAño(int año) {
		Año = año;
	}

//	public ArrayList<Cantante> getArrayCantantes() {
//		return arrayCantantes;
//	}
//
//	public void setArrayCantantes(ArrayList<Cantante> arrayCantantes) {
//		this.arrayCantantes = arrayCantantes;
//	}
	
}

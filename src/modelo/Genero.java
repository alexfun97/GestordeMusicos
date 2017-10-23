package modelo;

public class Genero {
	
	private int ID;
	private String Nombre;
	private int Año;
	
	public Genero(int iD, String nombre, int año) {
		ID = iD;
		Nombre = nombre;
		Año = año;
	}

	public Genero(String nombre, int año) {
		Nombre = nombre;
		Año = año;
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
	
}

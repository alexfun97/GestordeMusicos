package modelo;

public class Cantante {
	
	private int ID;
	private String Nombre;
	private String Nacimiento;
	private String Nacionalidad;
	private int Genero;
	
	public Cantante(int iD, String nombre, String nacimiento, String nacionalidad, int genero) {
		ID = iD;
		Nombre = nombre;
		Nacimiento = nacimiento;
		Nacionalidad = nacionalidad;
		Genero = genero;
	}
	
	public Cantante(String nombre, String nacimiento, String nacionalidad, int genero) {
		Nombre = nombre;
		Nacimiento = nacimiento;
		Nacionalidad = nacionalidad;
		Genero = genero;
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

	public String getNacimiento() {
		return Nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		Nacimiento = nacimiento;
	}

	public String getNacionalidad() {
		return Nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}

	public int getGenero() {
		return Genero;
	}

	public void setGenero(int genero) {
		Genero = genero;
	}
	

	
	
	
}

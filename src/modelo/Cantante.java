package modelo;

public class Cantante {
	
	private String ID;
	private String Nombre;
	private String Nacimiento;
	private String Nacionalidad;
	private String Genero;
	
	public Cantante(String iD, String nombre, String nacimiento, String nacionalidad, String genero) {
		ID = iD;
		Nombre = nombre;
		Nacimiento = nacimiento;
		Nacionalidad = nacionalidad;
		Genero = genero;
	}
	
	public Cantante(String nombre, String nacimiento, String nacionalidad, String genero) {
		Nombre = nombre;
		Nacimiento = nacimiento;
		Nacionalidad = nacionalidad;
		Genero = genero;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
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

	public String getGenero() {
		return Genero;
	}

	public void setGenero(String genero) {
		Genero = genero;
	}
	

	
	
	
}

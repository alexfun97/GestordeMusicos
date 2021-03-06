package modelo;

public class Cantante {
	
	private int ID;
	private String Nombre;
	private String Nacimiento;
	private String Nacionalidad;
	private Genero Genero;
	
	public Cantante(){
	}
	
	public Cantante(int iD, String nombre, String nacimiento, String nacionalidad, Genero genero) {
		ID = iD;
		Nombre = nombre;
		Nacimiento = nacimiento;
		Nacionalidad = nacionalidad;
		Genero = genero;
	}
	
	public Cantante(String nombre, String nacimiento, String nacionalidad, Genero genero) {
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

	public Genero getGenero() {
		return Genero;
	}

	public void setGenero(Genero genero) {
		Genero = genero;
	}
	
	public String getNombreGenero() {
		return Genero.getNombre();
	}
	
	public void setNombreGenero(String nombreGenero) {
		Genero.setNombre(nombreGenero);
	}
	
}

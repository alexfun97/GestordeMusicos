package modelo;

import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Modelo {
	private String url = "jdbc:mysql://localhost/proyecto2DAMP";
	private String usr = "root";
	private String pwd = "";
	private ObservableList<Cantante> cantantes;
	private ArrayList<PreparedStatement> arrayCantantes = new ArrayList<PreparedStatement>();
	public Connection conexion() {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conexion = DriverManager.getConnection(url, usr, pwd);
		return conexion;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		return null;
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
	}
	
	public ObservableList<Cantante> transicionDatos(){
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet resultado = conex.createStatement().executeQuery("SELECT * FROM cantante");
			while (resultado.next()){
				cantantes.add(new Cantante(resultado.getInt("ID"), resultado.getString("nombre"), resultado.getDate("fechaNac"), resultado.getString("nacionalidad"), resultado.getInt("genero")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}
	
	public ObservableList<Cantante> insercionDatos(Cantante cantante){
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			PreparedStatement resultado = conex.prepareStatement("INSERT INTO Cantante (Nombre, fechaNac, Nacionalidad, Genero) VALUES (?,?,?,?);");
			
			String nombre = cantante.getNombre();
			Date fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = cantante.getGenero();
			
			resultado.setString(1,nombre);
			resultado.setDate(2,fechaNac);
			resultado.setString(3,nacionalidad);
			resultado.setInt(4,genero);
			resultado.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}
	
	public ObservableList<Cantante> borradoDatos(Cantante cantante) {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			System.out.println("Borrando cantante...");
			PreparedStatement resultado = conex.prepareStatement("DELETE FROM `cantante` WHERE cantante.ID = ?;");
			
			int ID = cantante.getID();
			
			resultado.setInt(1,ID);
			resultado.executeUpdate();
			System.out.println("Borrado con éxito");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}
	
	public PreparedStatement insercionVariosDatos(Cantante cantante){
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			PreparedStatement resultado = conex.prepareStatement("INSERT INTO Cantante (Nombre, fechaNac, Nacionalidad, Genero) VALUES (?,?,?,?);");
			
			String nombre = cantante.getNombre();
			Date fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = cantante.getGenero();
				
			resultado.setString(1,nombre);
			resultado.setDate(2,fechaNac);
			resultado.setString(3,nacionalidad);
			resultado.setInt(4,genero);
			System.out.println("Pendiente de inserción");
			return resultado;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PreparedStatement borradoVariosDatos(Cantante cantante){
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try{
			PreparedStatement resultado = conex.prepareStatement("DELETE FROM `cantante` WHERE cantante.ID = ?;");
			
			int ID = cantante.getID();
			
			resultado.setInt(1,ID);
			System.out.println("Pendiente de borrado");
			return resultado;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void aceptarInsercionVariosDatos(ArrayList<PreparedStatement> aux){
		System.out.println("Aceptar inserción...");
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			for(int x = 0; x<aux.size(); x++){
				System.out.println("Registro completado." );
				aux.get(x).executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insertado con éxito");
		while(aux.size() != 0){
			System.out.println("Vaciando array...");
			aux.remove(0);
		}
		System.out.println("Array vacia.");
	}
	
	public ArrayList<PreparedStatement> cancelarInsercionVariosDatos(ArrayList<PreparedStatement> aux){
		System.out.println("Cancelar inserción...");
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		while(aux.size() != 0){
			System.out.println("Vaciando array...");
			aux.remove(0);
		}
		System.out.println("Cancelado con éxito");
		return aux;
	}
}

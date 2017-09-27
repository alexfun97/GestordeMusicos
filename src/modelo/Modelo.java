package modelo;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vista.VentanaPrincipalController;

public class Modelo {
	private String url = "jdbc:mysql://localhost/proyecto2DAMP";
	private String usr = "root";
	private String pwd = "";
	private ObservableList<Cantante> cantantes;
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
				cantantes.add(new Cantante(resultado.getInt("ID"), resultado.getString("nombre"), resultado.getString("fechaNac"), resultado.getString("nacionalidad"), resultado.getInt("genero")));
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
			String fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = cantante.getGenero();
			
			resultado.setString(1,nombre);
			resultado.setString(2,fechaNac);
			resultado.setString(3,nacionalidad);
			resultado.setInt(4,genero);
			resultado.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}
}

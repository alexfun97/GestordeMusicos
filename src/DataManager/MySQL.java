package DataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;
import modelo.Genero;

public class MySQL implements DataManager {
	private DBData datos = new DBData();
	private String url;
	private String usr;
	private String pwd;
	private ObservableList<Cantante> cantantes;
	private ObservableList<String> nombre;

	public Connection conexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(url, usr, pwd);
			return conexion;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void datosBBDD() {
		datos.init();
		url = datos.getUrl();
		System.out.println(datos.getUrl());
		usr = datos.getUsr();
		if (datos.getPwd().equals("null")) {
			pwd = "";
		} else {
			pwd = datos.getPwd();
		}
	}
	
	public HashMap<Integer, Genero> obtenerGeneros(){
		Connection conex = conexion();
		ResultSet qGenero;
		
		try {
			qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
			Genero genero = null;
			HashMap<Integer, Genero> generos = new HashMap<Integer, Genero>();

			while (qGenero.next()) {
				genero = new Genero(qGenero.getInt("ID"), qGenero.getString("nombre"),
						qGenero.getInt("añoCreacion"));
				generos.put(qGenero.getInt("ID"), genero);
			}
			
			return generos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ObservableList<Cantante> transicionDatos() {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet qCantante = conex.createStatement().executeQuery("SELECT * FROM cantante");
			while (qCantante.next()){ 
				cantantes.add(new Cantante(qCantante.getInt("ID"), qCantante.getString("nombre"),
						qCantante.getString("fechaNac"), qCantante.getString("nacionalidad"),
						obtenerGeneros().get(qCantante.getInt("genero"))));
			}
			qCantante.close();
			conex.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}

	public void insercionDatos(Cantante cantante) {
		Connection conex = conexion();
		try {
			PreparedStatement qCantante = conex.prepareStatement(
					"INSERT INTO Cantante (Nombre, fechaNac, Nacionalidad, Genero) VALUES (?,?,?,?);");
			ResultSet qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
			String nombre = cantante.getNombre();
			String fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = 0;
			while (qGenero.next()) {
				if (qGenero.getString("nombre").equals(cantante.getGenero().getNombre())) {
					genero = qGenero.getInt("ID");
				}
			}

			qCantante.setString(1, nombre);
			qCantante.setString(2, fechaNac);
			qCantante.setString(3, nacionalidad);
			qCantante.setInt(4, genero);
			qCantante.executeUpdate();
			qCantante.close();
			conex.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void borradoDatos(Cantante cantante) {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			PreparedStatement resultado = conex.prepareStatement("DELETE FROM `cantante` WHERE cantante.ID = ?;");

			int ID = cantante.getID();

			resultado.setInt(1, ID);
			resultado.executeUpdate();
			resultado.close();
			conex.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void modificado(Cantante cantante) {
		Connection conex = conexion();
		try {
			PreparedStatement qCantante = conex.prepareStatement(
					"UPDATE cantante SET nombre = ?, fechaNac = ?, nacionalidad = ?,"
					+ " genero = ? WHERE ID = " + cantante.getID() + ";");
			ResultSet qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
			String nombre = cantante.getNombre();
			String fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = 0;
			while (qGenero.next()) {
				if (qGenero.getString("nombre").equals(cantante.getGenero().getNombre())) {
					genero = qGenero.getInt("ID");
				}
			}

			qCantante.setString(1, nombre);
			qCantante.setString(2, fechaNac);
			qCantante.setString(3, nacionalidad);
			qCantante.setInt(4, genero);
			qCantante.executeUpdate();
			qCantante.close();
			conex.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void borradoTabla() {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			PreparedStatement st = conex.prepareStatement("DELETE FROM `cantante`");
			st.executeUpdate();
			st.close();
			conex.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Cantante> exportarDatos() {
		return this.transicionDatos();
	}

	public void importarDatos(ObservableList<Cantante> arrayCantantes) {

		this.borradoTabla();

		for (int x = 0; x < arrayCantantes.size(); x++) {
			this.insercionDatos(arrayCantantes.get(x));
		}
	}

	public ObservableList<String> nombreGeneros() {
		nombre = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet qGenero = conex.createStatement().executeQuery("SELECT nombre FROM genero");
			while (qGenero.next()) {
				nombre.add(qGenero.getString("nombre"));
			}

			qGenero.close();
			conex.close();
			return nombre;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Genero pedirGenero(String nomGenero) {
		Genero genero = null;
		Connection conex = conexion();
		try {
			ResultSet qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
			while (qGenero.next()) {
				if (qGenero.getString("nombre").equals(nomGenero)){
					genero = new Genero(qGenero.getInt("ID"), qGenero.getString("nombre"), qGenero.getInt("añoCreacion"));
				}
			}
			qGenero.close();
			conex.close();
			return genero;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

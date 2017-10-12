package DataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;

public class MySQL implements DataManager {
	private String url = "jdbc:mysql://localhost/proyecto2DAMP";
	private String usr = "root";
	private String pwd = "";
	private ObservableList<Cantante> cantantes;

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

	public ObservableList<Cantante> transicionDatos() {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet resultado = conex.createStatement().executeQuery("SELECT * FROM cantante");
			while (resultado.next()) {
				cantantes.add(new Cantante(resultado.getInt("ID"), resultado.getString("nombre"),
						resultado.getString("fechaNac"), resultado.getString("nacionalidad"),
						resultado.getInt("genero")));
			}
			resultado.close();
			conex.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cantantes;
	}

	public void insercionDatos(Cantante cantante) {
		Connection conex = conexion();
		try {
			PreparedStatement resultado = conex.prepareStatement(
					"INSERT INTO Cantante (Nombre, fechaNac, Nacionalidad, Genero) VALUES (?,?,?,?);");

			String nombre = cantante.getNombre();
			String fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = cantante.getGenero();

			resultado.setString(1, nombre);
			resultado.setString(2, fechaNac);
			resultado.setString(3, nacionalidad);
			resultado.setInt(4, genero);
			resultado.executeUpdate();
			resultado.close();
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

	public ObservableList<String> muestraUno(Cantante cantante) {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet resultado = conex.createStatement()
					.executeQuery("SELECT * FROM `cantante` WHERE cantante.ID = " + cantante.getID() + ";");
			resultado.next();
			ObservableList<String> datosCantante = FXCollections.observableArrayList();
			for (int x = 1; x < (resultado.getMetaData().getColumnCount() + 1); x++) {
				datosCantante.add(resultado.getString(x));
			}
			resultado.close();
			conex.close();
			return datosCantante;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
		
		for (int x = 0; x < arrayCantantes.size(); x++){
			this.insercionDatos(arrayCantantes.get(x));
		}
	}
}

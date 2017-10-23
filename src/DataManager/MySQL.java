package DataManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public ObservableList<Cantante> transicionDatos() {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet qCantante = conex.createStatement().executeQuery("SELECT * FROM cantante");
			while (qCantante.next()) {
				ResultSet qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
				Genero genero = null;
				ObservableList<Genero> generos = FXCollections.observableArrayList();

				while (qGenero.next()) {
					genero = new Genero(qGenero.getInt("ID"), qGenero.getString("nombre"),
							qGenero.getInt("añoCreacion"));
					generos.add(genero);
				}

				int idCantGen = qCantante.getInt("genero");
				
				int x;

				for (x = 0; idCantGen != generos.get(x).getID(); x++) {
				}
				cantantes.add(new Cantante(qCantante.getInt("ID"), qCantante.getString("nombre"),
						qCantante.getString("fechaNac"), qCantante.getString("nacionalidad"),
						generos.get(x).getNombre()));
				qGenero.close();
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
				if (qGenero.getString("nombre").equals(cantante.getGenero())) {
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

	public ObservableList<String> muestraUno(Cantante cantante) {
		cantantes = FXCollections.observableArrayList();
		Connection conex = conexion();
		try {
			ResultSet qCantante = conex.createStatement()
					.executeQuery("SELECT * FROM `cantante` WHERE cantante.ID = " + cantante.getID() + ";");
			qCantante.next();
			ResultSet qGenero = conex.createStatement().executeQuery("SELECT * FROM genero");
			ObservableList<String> datosCantante = FXCollections.observableArrayList();
			for (int x = 1; x < (qCantante.getMetaData().getColumnCount() + 1); x++) {
				if (x == qCantante.getMetaData().getColumnCount()) {

					String nombre = null;
					while (qGenero.next()) {
						if (qGenero.getInt("ID") == Integer.parseInt(qCantante.getString(x))) {
							nombre = qGenero.getString("nombre");
						}
					}

					datosCantante.add(nombre);
				} else {
					datosCantante.add(qCantante.getString(x));
				}
			}
			qCantante.close();
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

}

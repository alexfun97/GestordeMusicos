package DataManager;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import modelo.Cantante;

public class File {

	public ObservableList<Cantante> transicionDatos() {
		// TODO Auto-generated method stub
		return null;
	}
//	cantantes = FXCollections.observableArrayList();
//	Connection conex = conexion();
//	try {
//		File archivo = new File("cantantes.txt");
//		FileWriter leerArchivo = new FileWriter(archivo);
//		PrintWriter p = new PrintWriter(leerArchivo);
//		ResultSet resultado = conex.createStatement().executeQuery("SELECT * FROM `cantante`");
//		ArrayList<String> datosCantante = new ArrayList<String>();
//		System.out.println("Pasar uno");
//		while (resultado.next()) {
//			for (int x = 1; x < (resultado.getMetaData().getColumnCount() + 1); x++) {
//				datosCantante.add(resultado.getString(x));
//			}
//		}
//
//		p.print(datosCantante.get(0) + ", ");
//		p.print(datosCantante.get(1) + ", ");
//		p.print(datosCantante.get(2) + ", ");
//		p.print(datosCantante.get(3) + ", ");
//		p.println(datosCantante.get(5));
//
//		p.close();
//		leerArchivo.close();
//
//		resultado.close();
//
//	} catch (SQLException | IOException e) {
//		e.printStackTrace();
//	}

	public ObservableList<Cantante> insercionDatos(Cantante cantante) {
		System.out.println("DE PUTA MADRE");
		return null;
	}

	public ObservableList<Cantante> borradoDatos(Cantante selectedItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement insercionVariosDatos(Cantante cantante) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> muestraUno(Cantante selectedItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public void borradoTabla() {
		// TODO Auto-generated method stub
		
	}

	public void aceptarInsercionVariosDatos(ArrayList<PreparedStatement> arrayCantantes) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<PreparedStatement> cancelarInsercionVariosDatos(ArrayList<PreparedStatement> arrayCantantes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Cantante> exportarDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	public void importarDatos(ArrayList<Cantante> cantantes) {
		// TODO Auto-generated method stub
		
	}
}

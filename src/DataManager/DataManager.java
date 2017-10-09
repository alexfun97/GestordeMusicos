package DataManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import modelo.Cantante;

public interface DataManager {

	public Connection conexion();

	public ObservableList<Cantante> transicionDatos();

	public ObservableList<Cantante> insercionDatos(Cantante cantante);

	public ObservableList<Cantante> borradoDatos(Cantante cantante);

	public PreparedStatement insercionVariosDatos(Cantante cantante);

	public ArrayList<String> muestraUno(Cantante cantante);

	public void borradoTabla();

	public void aceptarInsercionVariosDatos(ArrayList<PreparedStatement> aux);

	public ArrayList<PreparedStatement> cancelarInsercionVariosDatos(ArrayList<PreparedStatement> aux);
	
	public ArrayList<Cantante> exportarDatos();
	
	public void importarDatos(ArrayList<Cantante> cantantes);
}

package DataManager;

import javafx.collections.ObservableList;
import modelo.Cantante;

public interface DataManager {

	public ObservableList<Cantante> transicionDatos();

	public void insercionDatos(Cantante cantante);

	public ObservableList<Cantante> borradoDatos(Cantante cantante);

	public ObservableList<String> muestraUno(Cantante cantante);

	public void borradoTabla();
	
	public ObservableList<Cantante> exportarDatos();
	
	public void importarDatos(ObservableList<Cantante> cantantes);
}

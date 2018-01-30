package DataManager;

import javafx.collections.ObservableList;
import modelo.Cantante;
import modelo.Genero;

public interface DataManager {

	public ObservableList<Cantante> transicionDatos();

	public void insercionDatos(Cantante cantante);

	public void borradoDatos(Cantante cantante);

	public void borradoTabla();
	
	public ObservableList<Cantante> exportarDatos();
	
	public void importarDatos(ObservableList<Cantante> arrayCantantes);
	
	public Genero pedirGenero(String nomGenero);

	public ObservableList<String> nombreGeneros();
	
	public void modificado(Cantante cantante);
}

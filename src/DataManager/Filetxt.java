package DataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;

public class Filetxt implements DataManager {

	File archivo = new File("cantantes.txt");

	private ObservableList<Cantante> cantantes;

	public ObservableList<Cantante> transicionDatos() {

		cantantes = FXCollections.observableArrayList();
		FileReader leerArchivo;

		try {

			leerArchivo = new FileReader(archivo);
			BufferedReader br = new BufferedReader(leerArchivo);
			String linea = br.readLine();
			while (linea != null) {
				String[] aux = linea.split(", ");
				Cantante sell = new Cantante(Integer.parseInt(aux[0]), aux[1], aux[2], aux[3],
						Integer.parseInt(aux[4]));
				cantantes.add(sell);
				linea = br.readLine();
			}
			br.close();
			leerArchivo.close();
			return cantantes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void insercionDatos(Cantante cantante) {
		String nombre = cantante.getNombre();
		String fechaNac = cantante.getNacimiento();
		String nacionalidad = cantante.getNacionalidad();
		int genero = cantante.getGenero();

		try {
			FileWriter p = new FileWriter(archivo, true);
			PrintWriter pr = new PrintWriter(p, true);
			pr.print(this.darID() + ", ");
			pr.print(nombre + ", ");
			pr.print(fechaNac + ", ");
			pr.print(nacionalidad + ", ");
			pr.println(genero);
			pr.close();
			p.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void borradoDatos(Cantante cantante) {
		try {
			for (int i = 0; i < cantantes.size(); i++) {
				if (cantantes.get(i).getNombre().equals(cantante.getNombre()))
					this.cantantes.remove(i);
			}

			FileWriter p = new FileWriter(archivo);
			PrintWriter pr = new PrintWriter(p, true);
			for (int x = 0; x < cantantes.size(); x++) {
				String nombre = cantantes.get(x).getNombre();
				String fechaNac = cantantes.get(x).getNacimiento();
				String nacionalidad = cantantes.get(x).getNacionalidad();
				int genero = cantantes.get(x).getGenero();
				pr.print("1" + ", ");
				pr.print(nombre + ", ");
				pr.print(fechaNac + ", ");
				pr.print(nacionalidad + ", ");
				pr.println(genero);
			}
			pr.close();
			p.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public ObservableList<String> muestraUno(Cantante selectedItem) {
		cantantes = FXCollections.observableArrayList();
		ObservableList<String> datosCantante = FXCollections.observableArrayList();
		
		return datosCantante;
	}

	public void borradoTabla() {
			try {
				FileWriter p = new FileWriter(archivo);
				PrintWriter pr = new PrintWriter(p, true);
				pr.print("");
				pr.close();
				p.close();
			} catch (IOException e) {
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
	
	public int darID() throws IOException{
		cantantes = FXCollections.observableArrayList();
		FileReader leerArchivo = new FileReader(archivo);
		BufferedReader br = new BufferedReader(leerArchivo);
		String linea = br.readLine();
		int x = 0;
		while (linea != null) {
			String[] aux = linea.split(", ");
			if(x < Integer.parseInt(aux[0])){
				x = Integer.parseInt(aux[0]);
			}
			linea = br.readLine();
		}
		br.close();
		leerArchivo.close();
		return x + 1;
	}

}

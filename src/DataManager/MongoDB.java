package DataManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;
import modelo.Genero;

public class MongoDB implements DataManager {

	MongoClient mongoClient;
	MongoCollection<Document> collectionC;
	MongoCollection<Document> collectionG;
	MongoDatabase db;
	private ObservableList<Cantante> cantantes;
	private ObservableList<String> nombre;

	public MongoDB() {
		try {
			// PASO 1: Conexión al Server de MongoDB Pasandole el host y el
			// puerto
			mongoClient = new MongoClient("localhost", 27017);

			// PASO 2: Conexión a la base de datos
			db = mongoClient.getDatabase("appMusica");
			System.out.println("Conectado a BD MONGO");

		} catch (Exception e) {
			System.out.println("Error leyendo la BD MONGO: " + e.getMessage());
			System.out.println("Fin de la ejecucion del programa");
			System.exit(1);
		}

	}

	public HashMap<Integer, Genero> obtenerGeneros() {

		collectionG = db.getCollection("genero");
		MongoCursor<Document> cursor = collectionG.find().iterator();

		Genero genero = null;
		HashMap<Integer, Genero> generos = new HashMap<Integer, Genero>();
		int ID, creacion;
		String nombre;

		while (cursor.hasNext()) {
			Document rs = cursor.next();
			ID = rs.getInteger("id");
			nombre = rs.getString("nombre");
			creacion = rs.getInteger("creacion");
			genero = new Genero(ID, nombre, creacion);
			generos.put(ID, genero);
		}

		return generos;
	}

	@Override
	public ObservableList<Cantante> transicionDatos() {

		cantantes = FXCollections.observableArrayList();
		collectionC = db.getCollection("cantante");
		int ID;
		String nombre, fecha, nacionalidad;
		Genero genero;
		MongoCursor<Document> cursor = collectionC.find().iterator();

		while (cursor.hasNext()) {
			Document rs = cursor.next();
			ID = rs.getInteger("id");
			nombre = rs.getString("nombre");
			fecha = rs.getString("fechaNac");
			nacionalidad = rs.getString("nacionalidad");
			genero = this.obtenerGeneros().get(rs.getInteger("genero"));
			
			cantantes.add(new Cantante(ID, nombre, fecha, nacionalidad, genero));
		}

		return cantantes;
	}

	@Override
	public void insercionDatos(Cantante cantante) {

		collectionC = db.getCollection("cantante");
		Document obj = new Document();
		try {
			int id = this.darID();
			String nombre = cantante.getNombre();
			String fechaNac = cantante.getNacimiento();
			String nacionalidad = cantante.getNacionalidad();
			int genero = 0;
			HashMap<Integer, Genero> generos = this.obtenerGeneros();
			for (int i = 1; i <= generos.size(); i++) {
				if (generos.get(i).getNombre().equals(cantante.getGenero().getNombre())) {
					genero = generos.get(i).getID();
				}
			}

			obj.append("id", id);
			obj.append("nombre", nombre);
			obj.append("fechaNac", fechaNac);
			obj.append("nacionalidad", nacionalidad);
			obj.append("genero", genero);
			
			collectionC.insertOne(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void borradoDatos(Cantante cantante) {
		collectionC = db.getCollection("cantante");
		Document obj = new Document();
		int id = cantante.getID();
		obj.append("id", id);
		collectionC.deleteOne(obj);
	}

	@Override
	public void borradoTabla() {
		collectionC = db.getCollection("cantante");
		collectionC.drop();
	}

	@Override
	public ObservableList<Cantante> exportarDatos() {
		return this.transicionDatos();
	}

	@Override
	public void importarDatos(ObservableList<Cantante> arrayCantantes) {
		this.borradoTabla();

		for (int x = 0; x < arrayCantantes.size(); x++) {
			this.insercionDatos(arrayCantantes.get(x));
		}
	}

	@Override
	public Genero pedirGenero(String nomGenero) {
		HashMap<Integer, Genero> generos = this.obtenerGeneros();
		Genero genero = null;
		for (int i = 1; i <= generos.size(); i++) {
			if(nomGenero.equals(generos.get(i).getNombre())){
				genero = generos.get(i);
			}
		}
		return genero;
	}

	@Override
	public ObservableList<String> nombreGeneros() {
		HashMap<Integer, Genero> generos = this.obtenerGeneros();
		nombre = FXCollections.observableArrayList();
		for (int i = 1; i <= generos.size(); i++) {
			nombre.add(generos.get(i).getNombre());
		}
		return nombre;
	}

	public int darID() throws IOException {
		ObservableList<Cantante> cantantes = this.transicionDatos();
		int x = 0;
		for (int i = 0; i < cantantes.size(); i++) {
			if (x < cantantes.get(i).getID()) {
				x = cantantes.get(i).getID();
			}
		}
		return x + 1;
	}

	@Override
	public void modificado(Cantante cantante) {
		collectionC = db.getCollection("cantante");
		Document objNew = new Document();
		String nombre = cantante.getNombre();
		String fechaNac = cantante.getNacimiento();
		String nacionalidad = cantante.getNacionalidad();
		int genero = 0;
		HashMap<Integer, Genero> generos = this.obtenerGeneros();
		for (int i = 1; i <= generos.size(); i++) {
			if (generos.get(i).getNombre().equals(cantante.getGenero().getNombre())) {
				genero = generos.get(i).getID();
			}
		}

		objNew.append("nombre", nombre);
		objNew.append("fechaNac", fechaNac);
		objNew.append("nacionalidad", nacionalidad);
		objNew.append("genero", genero);
		
		Document obj = new Document();
		int id = cantante.getID();
		obj.append("id", id);
		collectionC.updateOne(obj, new Document("$set", objNew));

	}

}

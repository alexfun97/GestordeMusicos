package DataManager;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cantante;
import modelo.Genero;

public class JSON implements DataManager {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, LEER_CANTANTE, INSERT_CANTANTE, BORRAR_CANTANTE, UPDATE_CANTANTE;
	private ObservableList<String> nombre;
	private Genero genero;

	public JSON() {

		encargadoPeticiones = new ApiRequests();

		SERVER_PATH = "http://localhost/appMusica/";
		LEER_CANTANTE = "leeTodo.php";
		INSERT_CANTANTE = "insertarCantante.php";
		BORRAR_CANTANTE = "borrarCantante.php";
		UPDATE_CANTANTE = "updateCantante.php";

	}

	public HashMap<Integer, Genero> obtenerGeneros() {

		HashMap<Integer, Genero> generos = new HashMap<Integer, Genero>();

		try {

			String url = SERVER_PATH + LEER_CANTANTE;

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			String estado = (String) respuesta.get("estado");

			JSONArray arrayGeneros = (JSONArray) respuesta.get("generos");

			for (int i = 0; i < arrayGeneros.size(); i++) {
				JSONObject row = (JSONObject) arrayGeneros.get(i);
				genero = new Genero(Integer.parseInt(row.get("id").toString()), row.get("nombre").toString(),
						Integer.parseInt(row.get("creacion").toString()));
				generos.put(Integer.parseInt(row.get("id").toString()), genero);

			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

			System.exit(-1);
		}
		return generos;
	}

	@Override
	public ObservableList<Cantante> transicionDatos() {
		ObservableList<Cantante> cantantes = FXCollections.observableArrayList();

		try {

			String url = SERVER_PATH + LEER_CANTANTE;

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			String estado = (String) respuesta.get("estado");

			JSONArray arrayCantantes = (JSONArray) respuesta.get("cantantes");

			Cantante nuevoCant;
			String nombre, fecha, nacionalidad;
			int id;

			for (int i = 0; i < arrayCantantes.size(); i++) {
				JSONObject row = (JSONObject) arrayCantantes.get(i);

				id = Integer.parseInt(row.get("id").toString());
				nombre = row.get("nombre").toString();
				fecha = row.get("fecha").toString();
				nacionalidad = row.get("nacionalidad").toString();
				genero = obtenerGeneros().get(Integer.parseInt(row.get("genero").toString()));

				nuevoCant = new Cantante(id, nombre, fecha, nacionalidad, genero);

				cantantes.add(nuevoCant);
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

			System.exit(-1);
		}

		return cantantes;
	}

	@Override
	public void insercionDatos(Cantante cantante) {
		try {
			JSONObject objCantante = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objCantante.put("nombre", cantante.getNombre());
			objCantante.put("fecha", cantante.getNacimiento());
			objCantante.put("nacionalidad", cantante.getNacionalidad());
			objCantante.put("genero", cantante.getGenero().getID());

			objPeticion.put("peticion", "add");
			objPeticion.put("insertarCantante", objCantante);

			String json = objPeticion.toJSONString();

			String url = SERVER_PATH + INSERT_CANTANTE;

			String response = encargadoPeticiones.postRequest(url, json);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida.");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}

	}

	@Override
	public void borradoDatos(Cantante cantante) {
		try {

			JSONObject objCantante = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objCantante.put("id", " WHERE cantante.ID = " + cantante.getID());

			objPeticion.put("peticion", "delete");
			objPeticion.put("borrarCantante", objCantante);

			String json = objPeticion.toJSONString();

			String url = SERVER_PATH + BORRAR_CANTANTE;

			String response = encargadoPeticiones.postRequest(url, json);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida.");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}

	}

	@Override
	public void borradoTabla() {
		try {

			JSONObject objCantante = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objCantante.put("id", "");

			objPeticion.put("peticion", "delete");
			objPeticion.put("borrarCantante", objCantante);

			String json = objPeticion.toJSONString();

			String url = SERVER_PATH + BORRAR_CANTANTE;

			String response = encargadoPeticiones.postRequest(url, json);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida.");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}

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
		for (int i = 1; i <= generos.size(); i++) {
			if (generos.get(i).getNombre().equals(nomGenero)) {
				genero = generos.get(i);
			}
		}
		return genero;
	}

	@Override
	public ObservableList<String> nombreGeneros() {
		nombre = FXCollections.observableArrayList();
		HashMap<Integer, Genero> generos = this.obtenerGeneros();

		for (int i = 1; i <= generos.size(); i++) {
			nombre.add(generos.get(i).getNombre());
		}

		return nombre;
	}

	@Override
	public void modificado(Cantante cantante) {
		try {
			JSONObject objCantante = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objCantante.put("id", cantante.getID());
			objCantante.put("nombre", cantante.getNombre());
			objCantante.put("fecha", cantante.getNacimiento());
			objCantante.put("nacionalidad", cantante.getNacionalidad());
			objCantante.put("genero", cantante.getGenero().getID());

			objPeticion.put("peticion", "update");
			objPeticion.put("actualizarCantante", objCantante);

			String json = objPeticion.toJSONString();

			String url = SERVER_PATH + UPDATE_CANTANTE;

			String response = encargadoPeticiones.postRequest(url, json);
			
			System.out.println("El json que recibimos es: ");
			
			System.out.println(response);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		} catch (Exception e) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el m�todo 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecuci�n");
			System.exit(-1);
		}

	
	}

}

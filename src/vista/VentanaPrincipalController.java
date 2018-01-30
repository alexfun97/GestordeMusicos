package vista;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import DataManager.Filetxt;
import DataManager.Hibernate;
import DataManager.JSON;
import DataManager.MongoDB;
import DataManager.MySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import launcher.Main;
import modelo.Cantante;
import modelo.Genero;

public class VentanaPrincipalController {

	private ObservableList<String> listMyFile = FXCollections.observableArrayList("MySQL", "Filetxt", "Hibernate",
			"JSON", "MongoDB");

	@FXML
	private ComboBox<String> despMyFile;

	@FXML
	private TextField txtNombre;

	@FXML
	private DatePicker dateFechaNac;

	@FXML
	private TextField txtNacionalidad;

	@FXML
	private ObservableList<String> generoList = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> cbGenero;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnAñadir;

	@FXML
	private Button btnBorrar;

	@FXML
	private Button btnAñadirVarios;

	@FXML
	private Button btnMostrarUno;

	@FXML
	private Button btnModificar;

	@FXML
	private Button btnAceptarAV;

	@FXML
	private Button btnCancelarAV;

	@FXML
	private Button btnBorrarAV;

	@FXML
	private Button btnMostrarUnoAV;

	@FXML
	private Button btnBorrarTabla;

	@FXML
	private Button btnImportarArriba;

	@FXML
	private Button btnImportarMedioArriba;
	
	@FXML
	private Button btnImportarMedioAbajo;

	@FXML
	private Button btnImportarAbajo;

	@FXML
	private Button btnExportarArriba;

	@FXML
	private Button btnExportarMedioArriba;
	
	@FXML
	private Button btnExportarMedioAbajo;

	@FXML
	private Button btnExportarAbajo;

	@FXML
	private TableView<Cantante> tablaCantante;

	@FXML
	private TableColumn<Cantante, String> tcNombre;

	@FXML
	private TableColumn<Cantante, Date> tcFechaNac;

	@FXML
	private TableColumn<Cantante, String> tcNacionalidad;

	@FXML
	private TableColumn<Cantante, String> tcGenero;

	@FXML
	private TableView<Cantante> tablaAddCantante;

	@FXML
	private TableColumn<Cantante, String> tcAddNombre;

	@FXML
	private TableColumn<Cantante, Date> tcAddFechaNac;

	@FXML
	private TableColumn<Cantante, String> tcAddNacionalidad;

	@FXML
	private TableColumn<Cantante, String> tcAddGenero;

	private ObservableList<Cantante> arrayCantantes = FXCollections.observableArrayList();

	private MySQL mysql = new MySQL();

	private Filetxt file = new Filetxt();

	private Hibernate hibernate = new Hibernate();

	private JSON json = new JSON();
	
	private MongoDB mongodb = new MongoDB();

	// Cargan los datos en la tabla

	public void initialize() {
		mysql.datosBBDD();
		despMyFile.setValue("MySQL");
		despMyFile.setItems(listMyFile);
		this.refreshTabla();
		btnAceptarAV.setDisable(true);
		btnCancelarAV.setDisable(true);
		btnBorrarAV.setDisable(true);
		btnMostrarUnoAV.setDisable(true);
	}

	public void refreshTabla() {
		if (despMyFile.getValue() == "MySQL") {
			tablaCantante.setItems(mysql.transicionDatos());

			btnImportarArriba.setText("Importar de Filetxt");
			btnImportarMedioArriba.setText("Importar de Hibernate");
			btnImportarMedioAbajo.setText("Importar de JSON");
			btnImportarAbajo.setText("Importar de MongoDB");

			btnExportarArriba.setText("Exportar a Filetxt");
			btnExportarMedioArriba.setText("Exportar de Hibernate");
			btnExportarMedioAbajo.setText("Exportar de JSON");
			btnExportarAbajo.setText("Exportar a MongoDB");

			generoList.clear();
			generoList.addAll(mysql.nombreGeneros());
			cbGenero.setValue(mysql.nombreGeneros().get(0));
			cbGenero.setItems(generoList);
		} else if (despMyFile.getValue() == "Filetxt") {
			tablaCantante.setItems(file.transicionDatos());

			btnImportarArriba.setText("Importar de Hibernate");
			btnImportarMedioArriba.setText("Importar de JSON");
			btnImportarMedioAbajo.setText("Importar de MongoDB");
			btnImportarAbajo.setText("Importar de MySQL");

			btnExportarArriba.setText("Exportar a Hibernate");
			btnExportarMedioArriba.setText("Exportar de JSON");
			btnExportarMedioAbajo.setText("Exportar de MongoDB");
			btnExportarAbajo.setText("Exportar a MySQL");

			generoList.clear();
			generoList.addAll(file.nombreGeneros());
			cbGenero.setValue(file.nombreGeneros().get(0));
			cbGenero.setItems(generoList);
		} else if (despMyFile.getValue() == "Hibernate") {
			tablaCantante.setItems(hibernate.transicionDatos());

			btnImportarArriba.setText("Importar de JSON");
			btnImportarMedioArriba.setText("Importar de MongoDB");
			btnImportarMedioAbajo.setText("Importar de MySQL");
			btnImportarAbajo.setText("Importar de Filetxt");

			btnExportarArriba.setText("Exportar a JSON");
			btnExportarMedioArriba.setText("Exportar de MongoDB");
			btnExportarMedioAbajo.setText("Exportar de MySQL");
			btnExportarAbajo.setText("Exportar a Filetxt");

			generoList.clear();
			generoList.addAll(hibernate.nombreGeneros());
			cbGenero.setValue(hibernate.nombreGeneros().get(0));
			cbGenero.setItems(generoList);
		} else if (despMyFile.getValue() == "JSON") {
			tablaCantante.setItems(json.transicionDatos());

			btnImportarArriba.setText("Importar de MongoDB");
			btnImportarMedioArriba.setText("Importar de MySQL");
			btnImportarMedioAbajo.setText("Importar de Filetxt");
			btnImportarAbajo.setText("Importar de Hibernate");

			btnExportarArriba.setText("Exportar a MongoDB");
			btnExportarMedioArriba.setText("Exportar de MySQL");
			btnExportarMedioAbajo.setText("Exportar de Filetxt");
			btnExportarAbajo.setText("Exportar a Hibernate");

			generoList.clear();
			generoList.addAll(json.nombreGeneros());
			cbGenero.setValue(json.nombreGeneros().get(0));
			cbGenero.setItems(generoList);
		} else if (despMyFile.getValue() == "MongoDB") {
			tablaCantante.setItems(mongodb.transicionDatos());

			btnImportarArriba.setText("Importar de MySQL");
			btnImportarMedioArriba.setText("Importar de Filetxt");
			btnImportarMedioAbajo.setText("Importar de Hibernate");
			btnImportarAbajo.setText("Importar de JSON");

			btnExportarArriba.setText("Exportar a MySQL");
			btnExportarMedioArriba.setText("Exportar de Filetxt");
			btnExportarMedioAbajo.setText("Exportar de Hibernate");
			btnExportarAbajo.setText("Exportar a JSON");

			generoList.clear();
			generoList.addAll(mongodb.nombreGeneros());
			cbGenero.setValue(mongodb.nombreGeneros().get(0));
			cbGenero.setItems(generoList);
		}
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante, Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante, String>("NombreGenero"));
	}

	public void insertarDatos() {
		Genero genero = null;
		if (despMyFile.getValue() == "MySQL") {
			genero = mysql.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Filetxt") {
			genero = file.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Hibernate") {
			genero = hibernate.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "JSON") {
			genero = json.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "MongoDB") {
			genero = mongodb.pedirGenero(cbGenero.getValue());
		}

		Cantante cantante = new Cantante(txtNombre.getText(), dateFechaNac.getValue().toString(),
				txtNacionalidad.getText(), genero);
		if (despMyFile.getValue() == "MySQL") {
			mysql.insercionDatos(cantante);
		} else if (despMyFile.getValue() == "Filetxt") {
			file.insercionDatos(cantante);
		} else if (despMyFile.getValue() == "Hibernate") {
			hibernate.insercionDatos(cantante);
		} else if (despMyFile.getValue() == "JSON") {
			json.insercionDatos(cantante);
		} else if (despMyFile.getValue() == "MongoDB") {
			mongodb.insercionDatos(cantante);
		}
		this.limpiar();
		this.refreshTabla();
	}

	public void borrarDatos() {
		if (despMyFile.getValue() == "MySQL") {
			mysql.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "Filetxt") {
			file.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "Hibernate") {
			hibernate.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "JSON") {
			json.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "MongoDB") {
			mongodb.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		}
		this.refreshTabla();
	}

	public void insertarVariosDatos() {
		Genero genero = null;
		if (despMyFile.getValue() == "MySQL") {
			genero = mysql.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Filetxt") {
			genero = file.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Hibernate") {
			genero = hibernate.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "JSON") {
			genero = json.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "MongoDB") {
			genero = mongodb.pedirGenero(cbGenero.getValue());
		}

		Cantante cantante = new Cantante(txtNombre.getText(), dateFechaNac.getValue().toString(),
				txtNacionalidad.getText(), genero);

		arrayCantantes.add(cantante);

		for (int x = 0; x < arrayCantantes.size(); x++) {
			tablaAddCantante.setItems(arrayCantantes);
			tcAddNombre.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nombre"));
			tcAddFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante, Date>("Nacimiento"));
			tcAddNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nacionalidad"));
			tcAddGenero.setCellValueFactory(new PropertyValueFactory<Cantante, String>("NombreGenero"));
		}
		btnAñadir.setDisable(true);
		btnBorrar.setDisable(true);
		btnMostrarUno.setDisable(false);
		btnBorrarTabla.setDisable(true);
		btnBorrarAV.setDisable(false);
		btnMostrarUnoAV.setDisable(false);
		btnAceptarAV.setDisable(false);
		btnCancelarAV.setDisable(false);
		this.limpiar();
		this.refreshTabla();
	}

	public void mostrarUno() {
		Cantante datosCantante = null;
		LocalDate fech = null;
		datosCantante = tablaCantante.getSelectionModel().getSelectedItem();

		txtNombre.setText(datosCantante.getNombre());
		fech = LocalDate.parse(datosCantante.getNacimiento(), DateTimeFormatter.ofPattern("yyyy-MM-d"));
		dateFechaNac.setValue(fech);
		txtNacionalidad.setText(datosCantante.getNacionalidad());
		cbGenero.setValue(datosCantante.getGenero().getNombre());
	}

	public void limpiar() {

		txtNombre.setText(null);

		dateFechaNac.setValue(null);

		txtNacionalidad.setText(null);

		if (despMyFile.getValue() == "MySQL") {
			cbGenero.setValue(mysql.nombreGeneros().get(0));
		} else if (despMyFile.getValue() == "Filetxt") {
			cbGenero.setValue(file.nombreGeneros().get(0));
		} else if (despMyFile.getValue() == "Hibernate") {
			cbGenero.setValue(hibernate.nombreGeneros().get(0));
		} else if (despMyFile.getValue() == "JSON") {
			cbGenero.setValue(json.nombreGeneros().get(0));
		} else if (despMyFile.getValue() == "MongoDB") {
			cbGenero.setValue(json.nombreGeneros().get(0));
		}

	}

	public void modificar() {
		Genero genero = null;
		if (despMyFile.getValue() == "MySQL") {
			genero = mysql.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Filetxt") {
			genero = file.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "Hibernate") {
			genero = hibernate.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "JSON") {
			genero = json.pedirGenero(cbGenero.getValue());
		} else if (despMyFile.getValue() == "MongoDB") {
			genero = mongodb.pedirGenero(cbGenero.getValue());
		}

		Cantante cantante = new Cantante(tablaCantante.getSelectionModel().getSelectedItem().getID(),
				txtNombre.getText(), dateFechaNac.getValue().toString(), txtNacionalidad.getText(), genero);

		if (despMyFile.getValue() == "MySQL") {
			mysql.modificado(cantante);
		} else if (despMyFile.getValue() == "Filetxt") {
			file.modificado(cantante);
		} else if (despMyFile.getValue() == "Hibernate") {
			hibernate.modificado(cantante);
		} else if (despMyFile.getValue() == "JSON") {
			json.modificado(cantante);
		} else if (despMyFile.getValue() == "MongoDB") {
			mongodb.modificado(cantante);
		}
		this.limpiar();
		this.refreshTabla();
	}

	public void borrarTabla() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");
		alert.setHeaderText("¿Seguro que quieres borrar toda la tabla?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (despMyFile.getValue() == "MySQL") {
				mysql.borradoTabla();
			} else if (despMyFile.getValue() == "Filetxt") {
				file.borradoTabla();
			} else if (despMyFile.getValue() == "Hibernate") {
				hibernate.borradoTabla();
			} else if (despMyFile.getValue() == "JSON") {
				json.borradoTabla();
			} else if (despMyFile.getValue() == "MongoDB") {
				mongodb.borradoTabla();
			}
			this.refreshTabla();
		}
	}

	public void aceptarInsertarVD() {
		for (int x = 0; x < arrayCantantes.size(); x++) {
			if (despMyFile.getValue() == "MySQL") {
				mysql.insercionDatos(arrayCantantes.get(x));
			} else if (despMyFile.getValue() == "Filetxt") {
				file.insercionDatos(arrayCantantes.get(x));
			} else if (despMyFile.getValue() == "Hibernate") {
				hibernate.insercionDatos(arrayCantantes.get(x));
			} else if (despMyFile.getValue() == "JSON") {
				json.insercionDatos(arrayCantantes.get(x));
			} else if (despMyFile.getValue() == "MongoDB") {
				mongodb.insercionDatos(arrayCantantes.get(x));
			}
		}
		arrayCantantes.clear();
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnBorrarTabla.setDisable(false);
		btnBorrarAV.setDisable(true);
		btnMostrarUnoAV.setDisable(true);
		btnAceptarAV.setDisable(true);
		btnCancelarAV.setDisable(true);
		this.limpiar();
		this.refreshTabla();
	}

	public void cancelarInsertarVD() {
		arrayCantantes.clear();
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnBorrarTabla.setDisable(false);
		btnBorrarAV.setDisable(true);
		btnMostrarUnoAV.setDisable(true);
		btnAceptarAV.setDisable(true);
		btnCancelarAV.setDisable(true);
		this.limpiar();
		this.refreshTabla();
	}

	public void mostrarUnoVD() {

		txtNombre.setText(tablaAddCantante.getSelectionModel().getSelectedItem().getNombre());

		LocalDate fech = LocalDate.parse(tablaAddCantante.getSelectionModel().getSelectedItem().getNacimiento(),
				DateTimeFormatter.ofPattern("yyyy-MM-d"));

		dateFechaNac.setValue(fech);

		txtNacionalidad.setText(tablaAddCantante.getSelectionModel().getSelectedItem().getNacionalidad());

		cbGenero.setValue(tablaAddCantante.getSelectionModel().getSelectedItem().getGenero().getNombre());

	}

	public void borrarDatosVD() {
		arrayCantantes.remove(tablaAddCantante.getSelectionModel().getSelectedItem());
		this.refreshTabla();
	}

	public void importarTablaArriba() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Filetxt a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Hibernate a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla JSON a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MongoDB a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(mongodb.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MySQL a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(mysql.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public void importarTablaMedioArriba() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Hibernate a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla JSON a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MongoDB a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(mongodb.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MySQL a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Filetxt a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(file.transicionDatos());
			}

		}
		this.refreshTabla();
	}
	
	public void importarTablaMedioAbajo() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla JSON a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MongoDB a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(mongodb.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MySQL a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Filetxt a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Hibernate a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(hibernate.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public void importarTablaAbajo() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MongoDB a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(mongodb.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla MySQL a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Filetxt a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla Hibernate a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres importar la tabla JSON a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(json.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public void exportarTablaArriba() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MySQL a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Filetxt a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Hibernate a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla JSON a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MongoDB a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(mongodb.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public void exportarTablaMedioArriba() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MySQL a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Filetxt a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Hibernate a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla JSON a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MongoDB a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(mongodb.transicionDatos());
			}

		}
		this.refreshTabla();
	}
	
	public void exportarTablaMedioAbajo() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MySQL a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Filetxt a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Hibernate a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla JSON a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MongoDB a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(mongodb.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public void exportarTablaAbajo() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerta");

		if (despMyFile.getValue() == "MySQL") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MySQL a MongoDB?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mongodb.importarDatos(mysql.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Filetxt a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(file.transicionDatos());
			}

		} else if (despMyFile.getValue() == "Hibernate") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla Hibernate a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(hibernate.transicionDatos());
			}

		} else if (despMyFile.getValue() == "JSON") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla JSON a Hibernate?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				hibernate.importarDatos(json.transicionDatos());
			}

		} else if (despMyFile.getValue() == "MongoDB") {

			alert.setHeaderText("¿Seguro que quieres exportar la tabla MongoDB a JSON?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				json.importarDatos(mongodb.transicionDatos());
			}

		}
		this.refreshTabla();
	}

	public TextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(TextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public DatePicker getDateFechaNac() {
		return dateFechaNac;
	}

	public void setDateFechaNac(DatePicker txtFechaNac) {
		this.dateFechaNac = txtFechaNac;
	}

	public TextField getTxtNacionalidad() {
		return txtNacionalidad;
	}

	public void setTxtNacionalidad(TextField txtNacionalidad) {
		this.txtNacionalidad = txtNacionalidad;
	}

	public ComboBox<String> getTxtGenero() {
		return cbGenero;
	}

	public void setTxtGenero(ComboBox<String> txtGenero) {
		this.cbGenero = txtGenero;
	}

	public void setProgramaPrincipal(Main main) {

	}
}

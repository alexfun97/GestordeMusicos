package vista;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import DataManager.Filetxt;
import DataManager.MySQL;
import controlador.Main;
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
import modelo.Cantante;

public class VentanaPrincipalController {

	ObservableList<String> listMyFile = FXCollections.observableArrayList("MySQL", "Filetxt");

	@FXML
	private ComboBox<String> despMyFile;

	@FXML
	private TextField txtNombre;

	@FXML
	private DatePicker dateFechaNac;

	@FXML
	private TextField txtNacionalidad;

	@FXML
	private TextField txtGenero;

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
	private Button btnAceptarAñadirVarios;

	@FXML
	private Button btnCancelarAñadirVarios;

	@FXML
	private Button btnBorrarTabla;

	@FXML
	private Button btnImportar;

	@FXML
	private TableView<Cantante> tablaCantante;

	@FXML
	private TableColumn<Cantante, String> tcNombre;

	@FXML
	private TableColumn<Cantante, Date> tcFechaNac;

	@FXML
	private TableColumn<Cantante, String> tcNacionalidad;

	@FXML
	private TableColumn<Cantante, Integer> tcGenero;

	private ArrayList<Cantante> arrayCantantes = new ArrayList<Cantante>();

	private MySQL mysql = new MySQL();

	private Filetxt file = new Filetxt();

	// Cargan los datos en la tabla

	public void initialize() {
		despMyFile.setValue("MySQL");
		despMyFile.setItems(listMyFile);
		tablaCantante.setItems(mysql.transicionDatos());
		btnImportar.setText("Importar Filetxt");
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante, Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante, Integer>("Genero"));
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
	}

	public void refreshTabla() {
		if (despMyFile.getValue() == "MySQL") {
			tablaCantante.setItems(mysql.transicionDatos());
			btnImportar.setText("Importar Filetxt");
		} else if (despMyFile.getValue() == "Filetxt") {
			tablaCantante.setItems(file.transicionDatos());
			btnImportar.setText("Importar MySQL");
		}
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante, Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante, String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante, Integer>("Genero"));
	}

	public void insertarDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), dateFechaNac.getValue().toString(),
				txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		if (despMyFile.getValue() == "MySQL") {
			mysql.insercionDatos(cantante);
		} else if (despMyFile.getValue() == "Filetxt") {
			file.insercionDatos(cantante);
		}
		this.limpiar();
		this.refreshTabla();
	}

	public void borrarDatos() {
		if (despMyFile.getValue() == "MySQL") {
			mysql.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "Filetxt") {
			file.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		}
		this.refreshTabla();
	}

	public void insertarVariosDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), dateFechaNac.getValue().toString(),
				txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		if (despMyFile.getValue() == "MySQL") {
			arrayCantantes.add(cantante);
		} else if (despMyFile.getValue() == "Filetxt") {
			arrayCantantes.add(cantante);
		}
		btnAñadir.setDisable(true);
		btnBorrar.setDisable(true);
		btnMostrarUno.setDisable(true);
		btnBorrarTabla.setDisable(true);
		btnAceptarAñadirVarios.setDisable(false);
		btnCancelarAñadirVarios.setDisable(false);
		this.limpiar();
		this.refreshTabla();
	}

	public void mostrarUno() {
		ObservableList<String> datosCantante = null;
		if (despMyFile.getValue() == "MySQL") {
			datosCantante = mysql.muestraUno(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue() == "Filetxt") {
			datosCantante = file.muestraUno(tablaCantante.getSelectionModel().getSelectedItem());
		}

		txtNombre.setText(datosCantante.get(1));

		LocalDate fech = LocalDate.parse(datosCantante.get(2), DateTimeFormatter.ofPattern("yyyy-MM-d"));

		dateFechaNac.setValue(fech);

		txtNacionalidad.setText(datosCantante.get(3));

		txtGenero.setText(String.valueOf(datosCantante.get(4)));
	}

	public void limpiar() {

		txtNombre.setText(null);

		dateFechaNac.setValue(null);

		txtNacionalidad.setText(null);

		txtGenero.setText(null);
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
			}
			this.refreshTabla();
		}
	}

	public void aceptarInsertarVariosDatos() {
		for (int x = 0; x < arrayCantantes.size(); x++) {
			if (despMyFile.getValue() == "MySQL") {
				mysql.insercionDatos(arrayCantantes.get(x));
			} else if (despMyFile.getValue() == "Filetxt") {
				file.insercionDatos(arrayCantantes.get(x));
			}
		}
		arrayCantantes.clear();
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnBorrarTabla.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.limpiar();
		this.refreshTabla();
	}

	public void cancelarInsertarVariosDatos() {
		arrayCantantes.clear();
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnBorrarTabla.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.limpiar();
		this.refreshTabla();
	}

	public void importarTabla() {

		if (despMyFile.getValue() == "MySQL") {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Alerta");
			alert.setHeaderText("¿Seguro que quieres importar la tabla Filetxt a MySQL?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				mysql.importarDatos(file.exportarDatos());
			}

		} else if (despMyFile.getValue() == "Filetxt") {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Alerta");
			alert.setHeaderText("¿Seguro que quieres importar la tabla MySQL a Filetxt?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				file.importarDatos(mysql.exportarDatos());
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

	public TextField getTxtGenero() {
		return txtGenero;
	}

	public void setTxtGenero(TextField txtGenero) {
		this.txtGenero = txtGenero;
	}

	public void setProgramaPrincipal(Main main) {

	}
}

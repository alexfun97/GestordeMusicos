package vista;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import DataManager.File;
import DataManager.MySQL;
import controlador.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cantante;

public class VentanaPrincipalController {
	
	ObservableList<String> listMyFile = FXCollections.observableArrayList("MySQL","File");
	
	@FXML
	private ComboBox despMyFile;
	
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
    private Button btnExportarAFichero;
	
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
	
	private ArrayList<PreparedStatement> arrayCantantes= new ArrayList<PreparedStatement>();
	
	
	//Cargan los datos en la tabla
	
	public void initialize() {
		despMyFile.setValue("MySQL");
		despMyFile.setItems(listMyFile);
		MySQL mysql = new MySQL();
		tablaCantante.setItems(mysql.transicionDatos());
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("Genero"));
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
	}
	
	public void refreshTabla() {
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			tablaCantante.setItems(mysql.transicionDatos());
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			tablaCantante.setItems(file.transicionDatos());
		}
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("Genero"));
	}
	
	public void insertarDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			mysql.insercionDatos(cantante);
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			file.insercionDatos(cantante);
		}
		this.refreshTabla();
	}
	
	public void borrarDatos() {
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			mysql.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			file.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		}
		this.refreshTabla();
	}
	
	public void insertarVariosDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			arrayCantantes.add(mysql.insercionVariosDatos(cantante));
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			arrayCantantes.add(file.insercionVariosDatos(cantante));
		}
		btnAñadir.setDisable(true);
		btnBorrar.setDisable(true);
		btnMostrarUno.setDisable(true);
		btnAceptarAñadirVarios.setDisable(false);
		btnCancelarAñadirVarios.setDisable(false);
		this.refreshTabla();
	}
	
	
	public void mostrarUno() {
		ArrayList<String> datosCantante = null;
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			datosCantante = mysql.muestraUno(tablaCantante.getSelectionModel().getSelectedItem());
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			datosCantante = file.muestraUno(tablaCantante.getSelectionModel().getSelectedItem());
		}
		
		txtNombre.setText(datosCantante.get(1));
		
		String fecha = datosCantante.get(2);
		String[] parts = fecha.split("-");
		int año = Integer.parseInt(parts[0]);
		int mes = Integer.parseInt(parts[1]);
		int dia = Integer.parseInt(parts[2]);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		
		LocalDate fech = LocalDate.parse(fecha, formatter);
		
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
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			mysql.borradoTabla();
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			file.borradoTabla();
		}
		this.refreshTabla();
	}
	
	public void aceptarInsertarVariosDatos() {
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			mysql.aceptarInsercionVariosDatos(arrayCantantes);
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			file.aceptarInsercionVariosDatos(arrayCantantes);
		}
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnAñadirVarios.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.refreshTabla();
	}
	
	public void cancelarInsertarVariosDatos() {
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			arrayCantantes = mysql.cancelarInsercionVariosDatos(arrayCantantes);
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			arrayCantantes = file.cancelarInsercionVariosDatos(arrayCantantes);
		}
		
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnAñadirVarios.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.refreshTabla();
	}
	
	public void importarTabla() {
		if(despMyFile.getValue()=="MySQL"){
			MySQL mysql = new MySQL();
			File file = new File();
			file.importarDatos(mysql.exportarDatos());
		} else if (despMyFile.getValue()=="File"){
			File file = new File();
			MySQL mysql = new MySQL();
			mysql.importarDatos(file.exportarDatos());
		}
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
		// TODO Auto-generated method stub
		
	}
}

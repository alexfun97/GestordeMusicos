package vista;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controlador.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cantante;
import modelo.Modelo;

public class VentanaPrincipalController {

	private Modelo modelo;
	
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
		modelo = new Modelo();
		tablaCantante.setItems(modelo.transicionDatos());
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("Genero"));
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
	}
	
	public void refreshTabla() {
		modelo = new Modelo();
		tablaCantante.setItems(modelo.transicionDatos());
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("Genero"));
	}
	
	public void insertarDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		modelo.insercionDatos(cantante);
		this.refreshTabla();
	}
	
	public void borrarDatos() {
		modelo.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		this.refreshTabla();
	}
	
	public void insertarVariosDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		arrayCantantes.add(modelo.insercionVariosDatos(cantante));
		btnAñadir.setDisable(true);
		btnBorrar.setDisable(true);
		btnMostrarUno.setDisable(true);
		btnAceptarAñadirVarios.setDisable(false);
		btnCancelarAñadirVarios.setDisable(false);
		this.refreshTabla();
	}
	
	
	public void mostrarUno() {
		ArrayList<String> datosCantante = modelo.muestraUno(tablaCantante.getSelectionModel().getSelectedItem());
		
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
		modelo.borradoTabla();
		this.refreshTabla();
	}
	
	public void aceptarInsertarVariosDatos() {
		modelo.aceptarInsercionVariosDatos(arrayCantantes);
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnAñadirVarios.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.refreshTabla();
	}
	
	public void cancelarInsertarVariosDatos() {
		arrayCantantes = modelo.cancelarInsercionVariosDatos(arrayCantantes);
		btnAñadir.setDisable(false);
		btnBorrar.setDisable(false);
		btnAñadirVarios.setDisable(false);
		btnMostrarUno.setDisable(false);
		btnAceptarAñadirVarios.setDisable(true);
		btnCancelarAñadirVarios.setDisable(true);
		this.refreshTabla();
	}
	
	public void exportarAFichero() {
		modelo.exporteAFichero();
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

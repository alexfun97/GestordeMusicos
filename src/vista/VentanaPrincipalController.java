package vista;

import java.sql.Date;
import java.sql.PreparedStatement;
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
    private Button btnAñadir;
	
	@FXML
    private Button btnAñadirVarios;
	
	@FXML
    private Button btnAceptarAñadirVarios;
	
	@FXML
    private Button btnCancelarAñadirVarios;
	
	@FXML
    private Button btnBorrar;
	
	@FXML
    private Button btnBorrarVarios;
	
	@FXML
	private TableView<Cantante> tablaCantante;
	
	@FXML
    private TableColumn<Cantante, Integer> tcID;
	
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
		tcID.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("ID"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,Date>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,Integer>("Genero"));
		
	}
	
	public void insertarDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		modelo.insercionDatos(cantante);
		this.initialize();
	}
	
	public void borrarDatos() {
		modelo.borradoDatos(tablaCantante.getSelectionModel().getSelectedItem());
		this.initialize();
	}
	
	public void insertarVariosDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), Date.valueOf(dateFechaNac.getValue()), txtNacionalidad.getText(), Integer.parseInt(txtGenero.getText()));
		arrayCantantes.add(modelo.insercionVariosDatos(cantante));
		this.initialize();
	}
	
	public void borrarVariosDatos() {
		arrayCantantes.add(modelo.borradoVariosDatos(tablaCantante.getSelectionModel().getSelectedItem()));
		this.initialize();
	}
	
	public void aceptarInsertarVariosDatos() {
		modelo.aceptarInsercionVariosDatos(arrayCantantes);
		this.initialize();
	}
	
	public void cancelarInsertarVariosDatos() {
		arrayCantantes = modelo.cancelarInsercionVariosDatos(arrayCantantes);
		this.initialize();
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

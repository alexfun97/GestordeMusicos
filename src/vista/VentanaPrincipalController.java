package vista;

import controlador.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cantante;
import modelo.Modelo;

public class VentanaPrincipalController {

	private Main ProgramaPrincipal;
	private Modelo modelo;
	
	@FXML
    private TextField txtNombre;
	
	@FXML
    private TextField txtFechaNac;
	
	@FXML
    private TextField txtNacionalidad;
	
	@FXML
    private TextField txtGenero;
	
	@FXML
    private Button btnAñadir;
	
	@FXML
	private TableView<Cantante> tablaCantante;
	
	@FXML
    private TableColumn<Cantante, String> tcID;
	
	@FXML
    private TableColumn<Cantante, String> tcNombre;
	
	@FXML
    private TableColumn<Cantante, String> tcFechaNac;
	
	@FXML
    private TableColumn<Cantante, String> tcNacionalidad;
	
	@FXML
    private TableColumn<Cantante, String> tcGenero;
	
	
	
	

	public void initialize() {
		modelo = new Modelo();
		tablaCantante.setItems(modelo.transicionDatos());
		tcID.setCellValueFactory(new PropertyValueFactory<Cantante,String>("ID"));
		tcNombre.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nombre"));
		tcFechaNac.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacimiento"));
		tcNacionalidad.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Nacionalidad"));
		tcGenero.setCellValueFactory(new PropertyValueFactory<Cantante,String>("Genero"));
		modelo.transicionDatos();
		
	}
	
	public void insertarDatos() {
		Cantante cantante = new Cantante(txtNombre.getText(), txtFechaNac.getText(), txtNacionalidad.getText(), txtGenero.getText());
		modelo.insercionDatos(cantante);
		this.initialize();
	}
	
	
	
	
	public TextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(TextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public TextField getTxtFechaNac() {
		return txtFechaNac;
	}

	public void setTxtFechaNac(TextField txtFechaNac) {
		this.txtFechaNac = txtFechaNac;
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

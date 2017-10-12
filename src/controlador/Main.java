 package controlador;
	
import java.io.IOException;

import vista.VentanaPrincipalController;
import controlador.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private static Stage stagePrincipal;
	private AnchorPane rootPane;

	@Override
    public void start(Stage stagePrincipal) throws Exception {
        Main.stagePrincipal = stagePrincipal;
        mostrarVentanaPrincipal();

    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../vista/ventanaPrincipal.fxml"));
            rootPane=(AnchorPane) loader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setTitle("iTunes Library");
            stagePrincipal.setScene(scene);
            /*
             * A�adidos las llamadas del main al Controlador y del controlador al main ***/
            VentanaPrincipalController controller = loader.getController();
            controller.setProgramaPrincipal(this);

            stagePrincipal.show();
        } catch (IOException e) {
           e.printStackTrace();
        }
   }
}

package co.edu.uniquindio.gestorcontacto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/gestorcontacto/Contacto.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestor de Contactos");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
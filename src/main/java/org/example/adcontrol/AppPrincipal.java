package org.example.adcontrol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase AppPrincipal que Inicia la aplicación de ADControl
 */
public class AppPrincipal extends Application {
    /**
     * Método que incia la aplicación del programa
     * @param stage ----------------------------------
     * @throws IOException Excepcion necesaria para el método load() de fxmlLoader
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Vistas/pantallaCarga.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ADControl");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Método main que lanza la aplicación de ADControl
     * @param args Parámetros que recibe el método main
     */
    public static void main(String[] args) {
        launch(args);
    }
}

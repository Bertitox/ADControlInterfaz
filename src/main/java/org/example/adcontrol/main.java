package org.example.adcontrol;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
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
        stage.setResizable(false);
        stage.show();

        // Asegurar que la interfaz ya está cargada antes de iniciar el hilo
        Platform.runLater(() -> {
            ControladorPantallaCarga controlador = fxmlLoader.getController();
            Thread hilo = new Thread(controlador);
            hilo.start();
        });
    }



    public static void main(String[] args) {
        launch();
    }
}
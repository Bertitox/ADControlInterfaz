package org.example.adcontrol;

import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;

public class ControladorPantallaCarga implements Runnable{
    @FXML
    ProgressBar progressBar;

    /**
     * Método encargado de poner el color a la barra de progreso y de hacer que aumente cada 50 milisegundos
     */
    @Override
    public void run() {
        //AQUÍ TENDRÍA QUE ESTAR EL MÉTODO QUE RELLENA LOS DATOS DEL GRÁFICO (actualizarGrafico())
        //****************************************************************************************//
        progressBar.setStyle("-fx-accent: #01a5e7;");
        for (int i = 0; i <= 100; i++) {
            final int value = i;
            Platform.runLater(() -> progressBar.setProgress(value / 100.0));
            try {
                Thread.sleep(1); // Espera 0.05s para que la barra no se complete de golpe
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            try {
                primeraVentana(); // Se llama al método que iniciará la nueva ventana
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método encargado de cargar la nueva ventana utilizando la anterior, es decir,
     * sin generar una nueva por encima de la actual
     * @throws IOException Excepcion necesaria para el método load() de fxmlLoader
     */
    @FXML
    public void primeraVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) progressBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }
}
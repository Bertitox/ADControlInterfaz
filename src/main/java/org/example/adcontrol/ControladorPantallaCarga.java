package org.example.adcontrol;

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


    @Override
    public void run() {
        progressBar.setStyle("-fx-accent: #01a5e7;");
        for (int i = 0; i <= 100; i++) {
            final int value = i;
            Platform.runLater(() -> progressBar.setProgress(value / 100.0)); // Se establece un valor máximo de 100
            try {
                Thread.sleep(1); // El programa espera 0.05s para que la barra no se complete de golpe
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> {
            try {
                primeraVentana();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


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
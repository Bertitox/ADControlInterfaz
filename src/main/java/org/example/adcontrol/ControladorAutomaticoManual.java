package org.example.adcontrol;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

    public class ControladorAutomaticoManual extends Controlador {
    @FXML
    AnchorPane anchorPane;

    @FXML
    void vistaAutomatico(MouseEvent event) {

    }

    @FXML
    void vistaManual(MouseEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setResizable(false);
            stage.setTitle("Modo de creacion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

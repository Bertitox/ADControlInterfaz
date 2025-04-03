package org.example.adcontrol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class ControladorMapa {

    @FXML
    private Button DAM1;

    @FXML
    void cambiarAula(ActionEvent event) {
        Button b = (Button) event.getSource();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + b.getText());
        alert.setTitle("Cambiando de aula");
        alert.setHeaderText(null); //Elimina el encabezado
        alert.showAndWait();
    }
}

package org.example.adcontrol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

public class ControladorMapa {

    @FXML
    private Button DAM1;

    @FXML
    private Rectangle rectSecret;

    @FXML
    private Rectangle rectaulainf;

    @FXML
    private Rectangle rectprof;

    @FXML
    void cambiarAula(ActionEvent event) {
        Button b = (Button) event.getSource();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + b.getText());
        alert.setTitle("Cambiando de aula");
        alert.setHeaderText(null); //Elimina el encabezado
        alert.showAndWait();
    }

    @FXML
    void botonMapaHover(MouseEvent event) {
        Button b = (Button) event.getSource();
        if(b.getText().equals("Aula de informatica 1")) {
            rectaulainf.setOpacity(0.5);
        }else if(b.getText().equals("Secretaria")) {
            rectSecret.setOpacity(0.5);
        }else{
            rectprof.setOpacity(0.5);
        }
        b.setOpacity(0.5);
    }

    @FXML
    void botonMapaNormal(MouseEvent event) {
        Button b = (Button) event.getSource();
        b.setOpacity(1);
        rectaulainf.setOpacity(1);
        rectSecret.setOpacity(1);
        rectprof.setOpacity(1);

    }
}

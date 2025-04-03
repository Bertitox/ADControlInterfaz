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
    private Rectangle rectSecret;

    @FXML
    private Rectangle rectaulainf;

    @FXML
    private Rectangle rectprof;

    @FXML
    private Button DespachoProfesores2;

    @FXML
    private Button aulaInfo1;

    @FXML
    private Button secretaria;

    @FXML
    void cambiarAula(ActionEvent event) {
        Alert alert = null;
        if (event.getSource() == Rectangle.class) {
            Rectangle rectangle = (Rectangle) event.getSource();
            if (rectangle.getId().equalsIgnoreCase("rectSecret")) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + secretaria.getText());
            } else if (rectangle.getId().equalsIgnoreCase("rectprof")) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + DespachoProfesores2.getText());
            } else if (rectangle.getId().equalsIgnoreCase("rectaulainf")) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + aulaInfo1.getText());
            }
        } else {
            Button b = (Button) event.getSource();
            alert = new Alert(Alert.AlertType.INFORMATION, "Cambiando a " + b.getText());
        }
        alert.setTitle("Cambiando de aula");
        alert.setHeaderText(null); //Elimina el encabezado
        alert.showAndWait();
    }

    @FXML
    void botonMapaHover(MouseEvent event) {
        if (event.getSource().getClass() == Button.class) {
            Button b = (Button) event.getSource();
            if (b.getText().equalsIgnoreCase("Aula de informatica 1")) {
                rectaulainf.setOpacity(0.5);
            } else if (b.getText().equalsIgnoreCase("Secretaria")) {
                rectSecret.setOpacity(0.5);
            } else if (b.getText().equalsIgnoreCase("Desp. Prof 2")) {
                rectprof.setOpacity(0.5);
            }
            b.setOpacity(0.5);
        }

        if (event.getSource().getClass() == Rectangle.class) {
            Rectangle r = (Rectangle) event.getSource();
            if (r.getId().equalsIgnoreCase("rectaulainf")) {
                aulaInfo1.setOpacity(0.5);
            } else if (r.getId().equalsIgnoreCase("rectSecret")) {
                secretaria.setOpacity(0.5);
            } else if (r.getId().equalsIgnoreCase("rectprof")) {
                DespachoProfesores2.setOpacity(0.5);
            }
            r.setOpacity(0.5);
        }
    }

    @FXML
    void botonMapaNormal(MouseEvent event) {
        if (event.getSource().getClass() == Button.class) {
            Button b = (Button) event.getSource();
            if (b.getText().equals("Aula de informatica 1")) {
                rectaulainf.setOpacity(1);
            } else if (b.getText().equals("Secretaria")) {
                rectSecret.setOpacity(1);
            } else if (b.getText().equals("Desp. Prof 2")) {
                rectprof.setOpacity(1);
            } else {

            }
            b.setOpacity(1);
        }

        if (event.getSource().getClass() == Rectangle.class) {
            Rectangle r = (Rectangle) event.getSource();
            if (r.getId().equals("rectaulainf")) {
                aulaInfo1.setOpacity(1);
            } else if (r.getId().equals("rectSecret")) {
                secretaria.setOpacity(1);
            } else if (r.getId().equals("rectprof")) {
                DespachoProfesores2.setOpacity(1);
            } else {

            }
            r.setOpacity(1);
        }

    }
}

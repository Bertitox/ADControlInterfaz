package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDAulas;
import BBDD.DTO.Aulas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
public class ControladorMapa {
    @FXML
    private Button DespachoProfesores2;

    @FXML
    private Button aulaInfo1;

    @FXML
    private Button secretaria;

    @FXML
    private Button DespachoProfesores2rect;

    @FXML
    private Button secretariarect;

    @FXML
    private Button aulaInfo1rect;


    @FXML
    void cambiarAula(ActionEvent event) {
        CRUDAulas cruda = new CRUDAulas();
        Button b = (Button) event.getSource();
        if (cruda.comprobarAula(b.getText().trim())) {
            //entrar a vista aula (la vista con pcs)
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El aula no está en la BBDD, ¿desea crearla?", ButtonType.YES, ButtonType.NO);
            alert.setTitle(b.getText() + " no se encontró");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                cruda.insertAula(new Aulas(b.getText().trim()));
            } else {
                alert.hide();
            }
        }
    }

    @FXML
    void botonMapaHover(MouseEvent event) {
        Button b = (Button) event.getSource();
        if (b.getId().equalsIgnoreCase("aulaInfo1rect") || b.getId().equalsIgnoreCase("aulaInfo1")) {
            aulaInfo1.setOpacity(0.5);
            aulaInfo1rect.setOpacity(0.5);
        } else if (b.getId().equalsIgnoreCase("secretariarect") || b.getId().equalsIgnoreCase("secretaria")) {
            secretariarect.setOpacity(0.5);
            secretaria.setOpacity(0.5);
        } else if (b.getId().equalsIgnoreCase("DespachoProfesores2rect") || b.getId().equalsIgnoreCase("DespachoProfesores2")) {
            DespachoProfesores2rect.setOpacity(0.5);
            DespachoProfesores2.setOpacity(0.5);
        } else {
            b.setOpacity(0.5);
        }
    }

    @FXML
    void botonMapaNormal(MouseEvent event) {
        Button b = (Button) event.getSource();
        if (b.getId().equalsIgnoreCase("aulaInfo1rect") || b.getId().equalsIgnoreCase("aulaInfo1")) {
            aulaInfo1.setOpacity(1);
            aulaInfo1rect.setOpacity(1);
        } else if (b.getId().equalsIgnoreCase("secretariarect") || b.getId().equalsIgnoreCase("secretaria")) {
            secretariarect.setOpacity(1);
            secretaria.setOpacity(1);
        } else if (b.getId().equalsIgnoreCase("DespachoProfesores2rect") || b.getId().equalsIgnoreCase("DespachoProfesores2")) {
            DespachoProfesores2rect.setOpacity(1);
            DespachoProfesores2.setOpacity(1);
        } else {
            b.setOpacity(1);
        }
    }
}

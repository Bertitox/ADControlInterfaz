package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDAulas;
import BBDD.DTO.Aulas;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
    private Pane panelMapa;


    @FXML
    void cambiarAula(ActionEvent event) {
        CRUDAulas cruda = new CRUDAulas();
        Button b = (Button) event.getSource();
        String nombreAula = b.getText();

        if (cruda.comprobarAula(b.getText().trim())) {
            //Entrar a vista aula (la vista con pcs)
            vistaAula(nombreAula);
        } else {
            //CREAR EL AULA EN AULA Y AULA_EQUIPO
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

//    public void vistaAula(String nombreAula)  {
//        Platform.runLater(() -> {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelAula.fxml"));
//                Parent root = fxmlLoader.load();
//                panelMapa.getChildren().clear();
//                panelMapa.getChildren().add(root);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public void vistaAula(String nombreAula) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelAula.fxml"));
                Parent root = fxmlLoader.load();

                ControladorVistaPanelAula controladorVista = fxmlLoader.getController();

                // Esperar un poco a que cargue panelAula.fxml (por el Platform.runLater interno)
                // Alternativa simple: usar otro runLater anidado
                Platform.runLater(() -> {
                    ControladorPanelAula controladorPanel = controladorVista.getControladorPanelAula();
                    if (controladorPanel != null) {
                        try {
                            controladorPanel.ponerClase(nombreAula);
                        } catch (AulaNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.err.println("El controlador del panel aula no está disponible todavía.");
                    }
                });

                panelMapa.getChildren().clear();
                panelMapa.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}

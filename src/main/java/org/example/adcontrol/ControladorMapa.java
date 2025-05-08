package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDAulas;
import BBDD.DTO.Aulas;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorMapa {//implements Initializable {

    @FXML
    private Pane panelMapa;

    @FXML
    private Button ASIR1;

    @FXML
    private Button ASIR2;

    @FXML
    private Button AulaEstudio;

    @FXML
    private Button DAM1;

    @FXML
    private Button DAM2;

    @FXML
    private Button DAW1;

    @FXML
    private Button DAW2;

    @FXML
    private Button DespachoProfesores1;

    @FXML
    private Button DespachoProfesores2;

    @FXML
    private Button DespachoProfesores2rect;

    @FXML
    private Button SMR1A;

    @FXML
    private Button SMR1B;

    @FXML
    private Button SMR2A;

    @FXML
    private Button SMR2B;

    @FXML
    private Button aulaConvencional;

    @FXML
    private Button aulaCristal1;

    @FXML
    private Button aulaCristal2;

    @FXML
    private Button aulaCristal3;

    @FXML
    private Button aulaInfo1;

    @FXML
    private Button aulaInfo1rect;

    @FXML
    private Button aulaInfo2;

    @FXML
    private Button secretaria;

    @FXML
    private Button secretariarect;

    @FXML
    private Button taller;

    @FXML
    private Button pared1;

    @FXML
    private Button pared2;


    @FXML
    private Button pared3;

    @FXML
    private Button pared4;

    @FXML
    private Button pared5;

    @FXML
    private Button pared6;

    @FXML
    private Button pared7;

    @FXML
    private Button pared8;

    @FXML
    private Button pared9;

    @FXML
    private Button pared10;

    @FXML
    private Button pared11;

    @FXML
    private Button pared12;

    @FXML
    private Button pared13;


    private final List<Rectangle2D> zonasPasillo = new ArrayList<>();
    private final List<Button> aulas = new ArrayList<>();

    //Métrodo que genera las personas
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // Define zonas negras del pasillo (ajústalas según tu imagen)
//        zonasPasillo.add(new Rectangle2D(165, 40, 655, 50));   // Pasillo horizontal superior
//        zonasPasillo.add(new Rectangle2D(165, 90, 50, 460));   // Pasillo vertical izquierdo
//        zonasPasillo.add(new Rectangle2D(215, 470, 610, 50));  // Pasillo horizontal inferior
//        zonasPasillo.add(new Rectangle2D(775, 90, 50, 380));   // Pasillo vertical derecho
//        zonasPasillo.add(new Rectangle2D(420, 220, 190, 40));  // Pasillo entre Aula Estudio y Sala Prof. 1
//
//
//        // Agrega las aulas (botones)
//        aulas.add(DespachoProfesores2);
//        aulas.add(aulaInfo1);
//        aulas.add(secretaria);
//        aulas.add(DespachoProfesores2rect);
//        aulas.add(secretariarect);
//        aulas.add(aulaInfo1rect);
//        aulas.add(aulaConvencional);
//        aulas.add(aulaCristal1);
//        aulas.add(aulaCristal2);
//        aulas.add(aulaCristal3);
//        aulas.add(aulaInfo2);
//        aulas.add(taller);
//        aulas.add(SMR2B);
//        aulas.add(DespachoProfesores1);
//        aulas.add(SMR1A);
//        aulas.add(SMR1B);
//        aulas.add(SMR2A);
//        aulas.add(ASIR1);
//        aulas.add(ASIR2);
//        aulas.add(DAM1);
//        aulas.add(DAM2);
//        aulas.add(DAW1);
//        aulas.add(DAW2);
//        aulas.add(AulaEstudio);
//        aulas.add(AulaEstudio);
//        aulas.add(pared1);
//        aulas.add(pared2);
//        aulas.add(pared3);
//        aulas.add(pared4);
//        aulas.add(pared5);
//        aulas.add(pared6);
//        aulas.add(pared7);
//        aulas.add(pared8);
//        aulas.add(pared9);
//        aulas.add(pared10);
//        aulas.add(pared11);
//        aulas.add(pared12);
//        aulas.add(pared13);
//
//        //Creamos las personas
//        for (int i = 0; i < 25; i++) {
//            new Persona(panelMapa, zonasPasillo, aulas);
//        }
//    }

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

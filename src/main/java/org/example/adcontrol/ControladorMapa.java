package org.example.adcontrol;

import BBDD.DAO.CRUDAulas;
import BBDD.DTO.Aulas;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase controladora que se encarga de todas las acciónes relacionadas con la vista del mapa
 *
 * @author Daniel García y Alberto
 * @version 1.0
 */
public class ControladorMapa {//implements Initializable {

    //Elementos FXML que usará la clase.
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

    //Listas que guardan los datos, relacionado con el movimiento de las personas en el mapa.
    private final List<Rectangle2D> zonasPasillo = new ArrayList<>();
    private final List<Button> aulas = new ArrayList<>();

    //Método que genera las personas, posible implementación.
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //Define zonas negras del pasillo (ajústalas según tu imagen)
//        zonasPasillo.add(new Rectangle2D(165, 40, 655, 50));   //Pasillo horizontal superior
//        zonasPasillo.add(new Rectangle2D(165, 90, 50, 460));   //Pasillo vertical izquierdo
//        zonasPasillo.add(new Rectangle2D(215, 470, 610, 50));  //Pasillo horizontal inferior
//        zonasPasillo.add(new Rectangle2D(775, 90, 50, 380));   //Pasillo vertical derecho
//        zonasPasillo.add(new Rectangle2D(420, 220, 190, 40));  //Pasillo entre Aula Estudio y Sala Prof. 1
//
//
//        //Agrega las aulas (botones)
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

    /**
     * Método que se encarga de cambiar entre las aulas del mapa.
     *
     * @param event Evento ActionEvent que espera el método.
     */
    @FXML
    void cambiarAula(ActionEvent event) {
        CRUDAulas cruda = new CRUDAulas(); //Crud con conexión a BBDD
        Button b = (Button) event.getSource();

        String nombreAula = b.getText(); //String que recoje el nombre del aula.

        if (cruda.comprobarAula(b.getText().trim())) {
            //Entrar a vista aula (la vista con pcs)
            vistaAula(nombreAula);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El aula no está en la BBDD, ¿desea crearla?", ButtonType.YES, ButtonType.NO); //Mensaje de advertencia de que el aula no se encontró, para poder crearla.
            alert.setTitle(b.getText() + " no se encontró");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) { //Si el usuario decide crear un aula pulsando "YES" en el alert, entonces creamos el aula.
                //Creación del el aula nueva
                cruda.insertAula(new Aulas(b.getText().trim()));
            } else {
                alert.hide();
            }
        }
    }


    /**
     * Método que se encarga de poner un hover a los diferentes botones del mapa.
     *
     * @param event Evento del tipo MouseEvent que espera el método.
     */
    @FXML
    void botonMapaHover(MouseEvent event) {
        Button b = (Button) event.getSource();
        //Al haber clases que no son cuadradas, se componen de dos botones en vez de uno, esa es la razón para en estos casos usar condicionales.
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

    /**
     * Método que representa los botones de forma normal.
     *
     * @param event Evento del tipo MouseEvent que espera el método.
     */
    @FXML
    void botonMapaNormal(MouseEvent event) {
        Button b = (Button) event.getSource();
        //Al haber clases que no son cuadradas, se componen de dos botones en vez de uno, esa es la razón para en estos casos usar condicionales.
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

    /**
     * Método que se encarga de conectar el aula seleccionado al pulsar el botón, con el panel de ese aula en específico.
     *
     * @param nombreAula String que indica la referencia (el nombre) del aula.
     */
    public void vistaAula(String nombreAula) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelAula.fxml"));//Cargamos la vista del panel del aula
                Parent root = fxmlLoader.load();
                ControladorVistaPanelAula controladorVista = fxmlLoader.getController();

                //Esperar un poco a que cargue panelAula.fxml (por el Platform.runLater interno)
                Platform.runLater(() -> {
                    ControladorPanelAula controladorPanel = controladorVista.getControladorPanelAula();
                    if (controladorPanel != null) {
                        try {
                            controladorPanel.ponerClase(nombreAula);//Actualizamos el nombre de la clase con el actual.
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

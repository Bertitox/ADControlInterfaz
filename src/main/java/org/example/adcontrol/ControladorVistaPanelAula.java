package org.example.adcontrol;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * Clase intermedia que añade el scrollPane, y dentro de este, el pane que se va a cargar
 *
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorVistaPanelAula extends Controlador {
    //Elemento padre del fxml donde se crean todos los elementos
    @FXML
    private Pane panelGrande;
    //Controlador necesario para iniciarlizar la nueva vista con este controlador
    private ControladorPanelAula controladorPanelAula;

    /**
     * Getter del controladorPanelAula necesario para acceder a esta vista
     *
     * @return devuelve el controladorPanelAula
     */
    public ControladorPanelAula getControladorPanelAula() {
        return controladorPanelAula;
    }

    /**
     * Nada más inciar la vista con este controlador, se carga dentro del scrollbar la vista panelAula.fxml
     */
    @FXML
    void initialize() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/panelAula.fxml"));
                Parent root = fxmlLoader.load();

                controladorPanelAula = fxmlLoader.getController();

                panelGrande.getChildren().clear();
                panelGrande.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();

            }
        });
    }
}

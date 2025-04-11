package org.example.adcontrol;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ControladorVistaPanelAula extends Controlador{
    @FXML
    private Pane panelGrande;

    private ControladorPanelAula controladorPanelAula;

    public ControladorPanelAula getControladorPanelAula() {
        return controladorPanelAula;
    }

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

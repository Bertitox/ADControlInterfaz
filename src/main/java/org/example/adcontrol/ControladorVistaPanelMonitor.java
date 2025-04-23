package org.example.adcontrol;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ControladorVistaPanelMonitor {
    @FXML
    private Pane panelGrande;

    private ControladorMonitor controladorMonitor;

    public ControladorMonitor getControladorMonitor() {
        return controladorMonitor;
    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaMonitor.fxml"));
                Parent root = fxmlLoader.load();

                controladorMonitor = fxmlLoader.getController();

                panelGrande.getChildren().clear();
                panelGrande.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
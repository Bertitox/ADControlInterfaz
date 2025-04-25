package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class ControladorPanelAula extends Controlador{

    @FXML
    Label labelNumEquiposAula;

    @FXML
    Label labelNumIncidenciasAula;

    @FXML
    ProgressBar barraEstadoAula;

    @FXML
    Label labelAula;

    @FXML
    Label fechaActual;

    @FXML
    Label fechaUltMod;

    @FXML
    Label horaUltMod;

    @FXML
    private Pane panelLargo;

    ControladorMapa cm = new ControladorMapa();
    //Clases gestoras de la BBDD
    CRUDAula_Equipo AE = new CRUDAula_Equipo();
    CRUDIncidencia I = new CRUDIncidencia();

    @FXML
    public void initialize() throws AulaNotFoundException {
        //labelAula.setText(cm.getAulaSeleccionada());
        //labelNumEquiposAula.setText(AE.numEquiposXAula(labelAula.getText()));
        //labelNumIncidenciasAula.setText(" "+I.numIncidenciasAula(labelAula.getText()));
        fechaActual.setText(LocalDate.now().toString());

    }

    public void ponerClase(String nombreAula) throws AulaNotFoundException {
        labelAula.setText(nombreAula);

        // Ahora sí puedes hacer tus consultas
        labelNumEquiposAula.setText(AE.numEquiposXAula(nombreAula));
        labelNumIncidenciasAula.setText(" " + I.numIncidenciasAula(nombreAula));
        if (I.getUltFechaMod(labelAula.getText()) == null) {
            fechaUltMod.setText("00000000");
            horaUltMod.setText("000000");

        }else {
            fechaUltMod.setText(I.getUltFechaMod(labelAula.getText()));
            horaUltMod.setText(I.getUltHoraMod(labelAula.getText()));
        }
    }

    @FXML
    void vistaEquipos(MouseEvent event) throws IOException {
        if(AE.numEquiposXAula(labelAula.getText()).equals("0")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No hay aulas en esta clase, debes de agregar un equipo al menos.", ButtonType.OK);
            alert.setTitle("No hay equipos");
            alert.setHeaderText(null); //Elimina el encabezado
            alert.showAndWait();
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAula.fxml"));
            Parent root = fxmlLoader.load();
            panelLargo.getChildren().clear();
            panelLargo.getChildren().add(root);
        }
    }

    @FXML
    void agregarEquipo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/OpcionAutomaticoManual.fxml"));
            Parent root = loader.load();

            // Obtener el controlador cargado desde el FXML
            ControladorAutomaticoManual controladorAutomaticoManual = loader.getController();

            // Pasarle el dato al controlador
            controladorAutomaticoManual.setAulaActual(labelAula.getText());

            Stage stage = new Stage();
            stage.setTitle("Modo de creación");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

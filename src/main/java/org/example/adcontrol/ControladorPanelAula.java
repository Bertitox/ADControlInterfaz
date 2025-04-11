package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ControladorPanelAula extends Controlador{

    @FXML
    Label labelNumEquiposAula;

    @FXML
    Label labelNumIncidenciasAula;

    @FXML
    ProgressBar barraEstadoAula;

    @FXML
    Label labelAula;

    ControladorMapa cm = new ControladorMapa();
    //Clases gestoras de la BBDD
    CRUDAula_Equipo AE = new CRUDAula_Equipo();
    CRUDIncidencia I = new CRUDIncidencia();

    @FXML
    public void initialize() throws AulaNotFoundException {
        //labelAula.setText(cm.getAulaSeleccionada());
        //labelNumEquiposAula.setText(AE.numEquiposXAula(labelAula.getText()));
        //labelNumIncidenciasAula.setText(" "+I.numIncidenciasAula(labelAula.getText()));
    }

    public void ponerClase(String nombreAula) throws AulaNotFoundException {
        labelAula.setText(nombreAula);

        // Ahora sí puedes hacer tus consultas
        labelNumEquiposAula.setText(AE.numEquiposXAula(nombreAula));
        labelNumIncidenciasAula.setText(" " + I.numIncidenciasAula(nombreAula));
    }


}

package org.example.adcontrol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class ControladorResultadoEscaneo {

    //Variables que se usarán en la clase.
    private static ControladorResultadoEscaneo instancia; //Instancia única de control idioma, para saber el idioma actual.
    static List<String> ipsDetectadas;

    @FXML
    private TableView<String> tablaIps;

    @FXML
    private Label textoEscaneado;

    @FXML
    void initialize() {
        rellenarTabla();
        actualizarTexto();
    }

    private void actualizarTexto() {
        textoEscaneado.setText("Se han escaneado " + ipsDetectadas.size() + " IPs diferentes");
    }

    private void rellenarTabla() {
    }

    /**
     * Método que se encarga de controlar la instancia del idioma, usando la instancia ya creada.
     * @return instancia ControlIdioma.
     */
    public static ControladorResultadoEscaneo getInstance() {
        if (instancia == null) {
            ControladorResultadoEscaneo controladorResultadoEscaneo = ControladorResultadoEscaneo.getInstance();
            instancia = new ControladorResultadoEscaneo();
            ipsDetectadas = new ArrayList<>();
        }
        return instancia;
    }

    public List<String> getIpsDetectadas() {
        return ipsDetectadas;
    }

    public void setIpsDetectadas(List<String> ipsDetectadas) {
        ControladorResultadoEscaneo.ipsDetectadas = ipsDetectadas;
    }
}

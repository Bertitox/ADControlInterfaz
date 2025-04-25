package org.example.adcontrol;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorResultadoEscaneo {

    //Variables que se usarán en la clase.
    private static ControladorResultadoEscaneo instancia; //Instancia única de control idioma, para saber el idioma actual.
    static List<String> ipsDetectadas;

    @FXML
    private TableView<Map.Entry<String, String>> tablaIps;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> columnaIp;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> columnaEquipo;


    @FXML
    private Label textoEscaneado;

    @FXML
    void initialize() {
        columnaIp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        columnaEquipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));

        rellenarTabla();
        actualizarTexto();
    }

    private void actualizarTexto() {
        textoEscaneado.setText("Se han escaneado " + ipsDetectadas.size() + " IPs diferentes");

    }

    private void rellenarTabla() {
        ObservableList<Map.Entry<String, String>> datos = FXCollections.observableArrayList(mapaIPEquipos().entrySet());
        tablaIps.setItems(datos);
    }

    /**
     * Método que se encarga de controlar la instancia del idioma, usando la instancia ya creada.
     * @return instancia ControlIdioma.
     */
    public static ControladorResultadoEscaneo getInstance() {
        if (instancia == null) {
            ControladorResultadoEscaneo controladorResultadoEscaneo = new ControladorResultadoEscaneo();
            instancia = new ControladorResultadoEscaneo();
            ipsDetectadas = new ArrayList<>();
        }
        return instancia;
    }

    public List<String> getIpsDetectadas() {
        return ipsDetectadas;
    }

    public Map<String, String> mapaIPEquipos(){
        Map<String, String> mapaIPEquipos = new HashMap<>();

        for (String ip : ipsDetectadas) {
            mapaIPEquipos.put(ip, "Equipo "+1);
        }
        return mapaIPEquipos;
    }

    public void setIpsDetectadas(List<String> ipsDetectadas) {
        ControladorResultadoEscaneo.ipsDetectadas = ipsDetectadas;
    }

//    public static void main(String[] args) {
//        ControladorResultadoEscaneo controladorResultadoEscaneo = new ControladorResultadoEscaneo();
//
//        for (Map.Entry<String, String> map: controladorResultadoEscaneo.mapaIPEquipos().entrySet()) {
//            System.out.println(controladorResultadoEscaneo.mapaIPEquipos().get(map.getKey()));
//        }
//    }
}

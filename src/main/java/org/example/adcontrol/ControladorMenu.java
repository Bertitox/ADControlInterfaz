package org.example.adcontrol;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ControladorMenu extends Controlador {
    @FXML
    public Pane panelPrincipal;
    @FXML
    private Label textoTitulo;
    @FXML
    private AnchorPane barraArriba;
    @FXML
    private AnchorPane barraIzquierda;

    Boolean daltónico = false;

    @FXML
    void alternarPaleta(ActionEvent event) {
        if(!daltónico){
            barraArriba.setStyle("-fx-background-color: red");
            barraIzquierda.setStyle("-fx-background-color: red");
            daltónico = true;
        }else{
            barraArriba.setStyle("-fx-background-color: #01a5e7");
            barraIzquierda.setStyle("-fx-background-color: #01a5e7");
            daltónico = false;
        }
    }
    @FXML
    void initialize() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
            Parent root = fxmlLoader.load();
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(root);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    /**
     * Método que sirve para cambiar la pantalla de Configuracion
     *
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarpantallaInforme(ActionEvent event) throws IOException {
        textoTitulo.setText("Generar informes");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaInforme.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Método que se encarga de cambiar la pantalla actual por la correspondiente al botón pulsado (pantalla de administradorS de equipos)
     *
     * @param event evento que espera el método
     * @throws IOException excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarPantallaMonitor(ActionEvent event) throws IOException {
        textoTitulo.setText("Incidencias");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaMonitor.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Método que sirve para cambiar la pantalla Home
     *
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarPantallaHome(ActionEvent event) throws IOException {
        textoTitulo.setText("IES Laguna de Joatzel");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }


    /**
     * Método que sirve para cambiar la pantalla de Ayuda
     *
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarpantallaAyuda(ActionEvent event) throws IOException {
        textoTitulo.setText("Ayuda");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAyuda.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Evento que muestra al usuario una ventana de confirmación par salir o no de la app
     *
     * @param event Evento de pulsacion del botón
     */
    public void salir(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que desea salir?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Salir de ADControl");
        alert.setHeaderText(null); //Elimina el encabezado

        //Mostrar diálogo y esperar a la respuesta del usuario
        if (alert.showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }
}

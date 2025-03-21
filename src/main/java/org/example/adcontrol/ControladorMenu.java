package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;


public class ControladorMenu extends Controlador {
    @FXML
    public Pane panelPrincipal;

    @FXML
    private MenuItem español;

    @FXML
    private MenuItem frances;

    @FXML
    private MenuItem ingles;

    @FXML
    private MenuButton menuButtonIdiomas;

    @FXML
    private Label textoTitulo;


    public ControladorMenu() {
    }

    @FXML
    void initialize() {
        super.setIdioma("Español");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
            Parent root = fxmlLoader.load();
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

        español.setOnAction(e -> {
            super.setIdioma("Español");  // Esto debería actualizar la propiedad 'idioma'
            cambiarIdioma("Español");
        });

        ingles.setOnAction(e -> {
            super.setIdioma("Ingles");
            cambiarIdioma("English");
        });

        frances.setOnAction(e -> {
            super.setIdioma("Frances");
            cambiarIdioma("Français");
        });

    }

    /**
     * Método que añade un efecto de zoom cuando se superpone el cursor por encima de un boton
     *
     * @param event Cuando se pasa el cursor por encima
     */
    @FXML
    void hoverBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), boton);
        zoomIn.setToX(1.1);  // 10% más grande en X
        zoomIn.setToY(1.1);  // 10% más grande en Y
        zoomIn.play();
    }

    /**
     * Método que define el estilo normal de los botones tras pasar el cursor por encima
     *
     * @param event Cuando el ratón no está por encima de algun boton
     */
    @FXML
    void normalBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(150), boton);
        zoomIn.setToX(1.0);  // 10% más grande en X
        zoomIn.setToY(1.0);  // 10% más grande en Y
        zoomIn.play();
    }


    /**
     * Método que sirve para cambiar la pantalla de Configuracion
     *
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarpantallaInforme(ActionEvent event) throws IOException {
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

    /**
     * Método que cambia el texto para indicar el idioma actual al que se está traduciendo.
     *
     * @param idioma Recibe un String idioma
     */
    public void cambiarIdioma(String idioma) {
        menuButtonIdiomas.setText(idioma);
    }

    public StringProperty getIdioma() {
        return idioma;
    }

    @FXML
    public void Hola(){
        System.out.println("hola");
    }

}

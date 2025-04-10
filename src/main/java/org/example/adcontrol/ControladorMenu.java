package org.example.adcontrol;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Clase ControladorMenu que sirve como controlador específico del menú.
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorMenu extends Controlador {
    @FXML
    public Pane panelPrincipal;
    @FXML
    private Label textoTitulo;
    @FXML
    private AnchorPane barraArriba;
    @FXML
    private AnchorPane barraIzquierda;
    @FXML
    private MenuButton menuButtonIdiomas;
    @FXML
    private MenuItem español;
    @FXML
    private MenuItem frances;
    @FXML
    private MenuItem ingles;
    @FXML
    public VBox contenedorBotones;

    Boolean daltónico = false;

    ControlIdioma controlIdioma = ControlIdioma.getInstance();
    InfoInit infoInit= InfoInit.getInstance();

    /**
     * Constructor por defecto de la clase ControladorMenu
     */
    public ControladorMenu() {
    }

    /**
     * Método que inicializa los componentes de la clase ControladorMenu
     */
    @FXML
    void initialize() {
        barraArriba.setStyle("-fx-background-color: " + infoInit.getTemaLeido());
        barraIzquierda.setStyle("-fx-background-color:" + infoInit.getTemaLeido());
        try {
            //SE CARGA POR DEFECTO LA VISTA HOME
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
            Parent root = fxmlLoader.load();
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //refrescarIdioma();

        español.setOnAction(e -> {
            controlIdioma.setIdioma("Español");
            cambiarIdioma("Español");
        });

        ingles.setOnAction(e -> {
            controlIdioma.setIdioma("Ingles");
            cambiarIdioma("English");
        });

        frances.setOnAction(e -> {
            controlIdioma.setIdioma("Frances");
            cambiarIdioma("Français");
        });


    }


    /**
     * Método que cambia la paleta de colores de la barra lateral de la aplicación.
     * @param event Evento que espera el método para actuar.
     */
    @FXML
    void alternarPaleta(ActionEvent event) {
        if(!daltónico){
            barraArriba.setStyle("-fx-background-color: red");
            barraIzquierda.setStyle("-fx-background-color: red");
            daltónico = true;
        }else{
            barraArriba.setStyle("-fx-background-color: " + infoInit.getTemaLeido());
            barraIzquierda.setStyle("-fx-background-color:" + infoInit.getTemaLeido());
            daltónico = false;
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


    @FXML
    void cambiarpantallaSSH(ActionEvent event) throws IOException {
        textoTitulo.setText("Terminal SSH");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaSSH.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }


    @FXML
    void cambiarpantallaAula(ActionEvent event) throws IOException {
        textoTitulo.setText("Aulas");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAjustes.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    void cambiarpantallaMapa(ActionEvent event) throws IOException {
        textoTitulo.setText("Mapa del Instituto");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaMapa.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    void cambiarpantallaConfiguracion(ActionEvent event) throws IOException {
        textoTitulo.setText("Ajustes");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAjustes.fxml"));
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
     * Método que cambia el texto del combobox para indicar el idioma actual al que se está traduciendo.
     *
     * @param idioma Recibe un String idioma
     */
    public void cambiarIdioma(String idioma) {
        menuButtonIdiomas.setText(idioma);
    }


    public VBox getContenedorBotones() {
        return contenedorBotones;
    }

    public void setContenedorBotones(VBox contenedorBotones) {
        this.contenedorBotones = contenedorBotones;
    }



}

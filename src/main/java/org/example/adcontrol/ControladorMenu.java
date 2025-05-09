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
 *
 * @author Daniel García y Alberto
 * @version 1.5
 */
public class ControladorMenu extends Controlador {

    //Elementos FXML que se usarán en la clase.
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

    InfoInit infoInit = InfoInit.getInstance();

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
        menuButtonIdiomas.setText(infoInit.getIdiomaLeido());//Cambiamos el texto del idioma actual.
        //Cambiamnos el texto y el estilo de la barra superiór y lateral.
        barraArriba.setStyle("-fx-background-color: " + infoInit.getTemaLeido());
        barraIzquierda.setStyle("-fx-background-color:" + infoInit.getTemaLeido());
        try {
            //Se carga por defecto la vista home
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
            Parent root = fxmlLoader.load();
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //refrescarIdioma();

        //Cambiamos los idiomas.
        español.setOnAction(e -> {
            controlIdioma.setIdioma("Español");
            cambiarIdioma("Español");
        });

        ingles.setOnAction(e -> {
            controlIdioma.setIdioma("English");
            cambiarIdioma("English");
        });

        frances.setOnAction(e -> {
            controlIdioma.setIdioma("Français");
            cambiarIdioma("Français");
        });
    }

    /**
     * Método que cambia la paleta de colores de la barra lateral de la aplicación.
     *
     * @param event Evento que espera el método para actuar.
     */
    @FXML
    void alternarPaleta(ActionEvent event) {
        if (!daltónico) { //Si se pulsa el botón cambiamos el color.
            //Color rojo de las barras de la app, tanto la lateral como la superiór.
            barraArriba.setStyle("-fx-background-color: red");
            barraIzquierda.setStyle("-fx-background-color: red");
            daltónico = true;
        } else {
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelMonitor.fxml"));
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
     * Método que sirve para cambiar la pantalla de Ayuda al pulsar el botón correspondiente
     *
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada/salida lanzada por el método
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
     * Método que se encarga de cambiar a la pantalla de SSH al pulsar el botón correspondiente.
     *
     * @param event Evento ActionEvent que recibe el método.
     * @throws IOException Excepción de entrada/salida.
     */
    @FXML
    void cambiarpantallaSSH(ActionEvent event) throws IOException {
        textoTitulo.setText("Terminal SSH");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaSSH.fxml")); //Cargamos la pantalla vistaSSH.
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Método que se encarga de cambiar a la pantalla del aula al pulsar el botón correspondiente.
     *
     * @param event Evento ActionEvent que recibe el método.
     * @throws IOException Excepción de entrada/salida.
     */
    @FXML
    void cambiarpantallaAula(ActionEvent event) throws IOException {
        textoTitulo.setText("Aulas");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAjustes.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Método que se encarga de cambiar a la pantalla del mapa al pulsar el botón correspondiente.
     *
     * @param event Evento ActionEvent que recibe el método.
     * @throws IOException Excepción de entrada/salida.
     */
    @FXML
    void cambiarpantallaMapa(ActionEvent event) throws IOException {
        textoTitulo.setText("Mapa del Instituto");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaMapa.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Método que se encarga de cambiar a la pantalla de los ajustes al pulsar el botón correspondiente.
     *
     * @param event Evento ActionEvent que recibe el método.
     * @throws IOException Excepción de entrada/salida.
     */
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

    /**
     * Método getter que retorna el contenedor de los botones.
     *
     * @return VBox con los botones.
     */
    public VBox getContenedorBotones() {
        return contenedorBotones;
    }

    /**
     * Método setter que modifica el contenedor de los botones.
     *
     * @param contenedorBotones Recibe un VBox que contiene un conjunto de botones.
     */
    public void setContenedorBotones(VBox contenedorBotones) {
        this.contenedorBotones = contenedorBotones;
    }
}
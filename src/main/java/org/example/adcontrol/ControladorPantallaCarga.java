package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;

/**
 * Clase controladora de la pantalla de carga.
 *
 * @author Daniel y Alberto
 * @version 1.5
 */
public class ControladorPantallaCarga {
    @FXML
    ProgressBar progressBar;
    @FXML
    Label textoCarga;
    @FXML
    MediaView panelMediaView;
    @FXML
    Pane panel;

    MediaPlayer mediaPlayer;
    Media media;


    /**
     * Costructor por defecto del controlador
     */
    public ControladorPantallaCarga() {
    }


    @FXML
    void initialize(){
        // Cargar y reproducir vídeo
        File f = new File("src/main/resources/org/example/adcontrol/VideoCarga.mp4");
        media = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        panelMediaView.setMediaPlayer(mediaPlayer);

        // Ajustar al tamaño del contenedor
        panelMediaView.fitWidthProperty().bind(panel.widthProperty());
        panelMediaView.fitHeightProperty().bind(panel.heightProperty());
        panelMediaView.setPreserveRatio(true);
        // Cargar datos en segundo plano
        Thread thread = new Thread(this::cargarDatosBBDD);
        thread.setDaemon(true);
        thread.start();

        // CUANDO TERMINE EL VÍDEO -> CAMBIAR DE PANTALLA
        mediaPlayer.setOnEndOfMedia(() -> {
            Platform.runLater(() -> {
                try {
                    primeraVentana();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    /**
     * Método que realiza la carga de la base de datos
     */
    private void cargarDatosBBDD() {
        // Simula la carga de la base de datos
        CRUDIncidencia incidencia = new CRUDIncidencia();
        CRUDAula_Equipo aula = new CRUDAula_Equipo();

        incidencia.numIncidencias();
        aula.readAllAulas();
    }

    /**
     * Método encargado de cargar la nueva ventana utilizando la anterior, es decir,
     * sin generar una nueva por encima de la actual
     *
     * @throws IOException Excepcion necesaria para el método load() de fxmlLoader
     */
    @FXML
    public void primeraVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/controlesMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) panel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(true);
        stage.setTitle("Pagina principal");
        stage.show();
    }
}
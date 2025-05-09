package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

import java.io.File;
import java.io.IOException;

/**
 * Clase controladora de la pantalla de carga.
 *
 * @author Daniel García y Alberto
 * @version 2.0
 */
public class ControladorPantallaCarga {

    //Elementos FXML usados en la clase.
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


    /**
     * Método que inicializa todos los componentes.
     */
    @FXML
    void initialize() {
        InfoInit infoInit = InfoInit.getInstance();

        //Cargar y reproducir vídeo
        File f = new File("src/main/resources/org/example/adcontrol/VideoCarga.mp4"); //Iniciamos el video de la pantalla de carga.
        media = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(media);//El media player será el reproductor que usaremos para el video.

        //Se lee dentro del fichero si está muteado o si no lo está.
        mediaPlayer.setMute(infoInit.getMuteLeido());

        mediaPlayer.setVolume(infoInit.getVolumenLeido());

        mediaPlayer.setAutoPlay(true);
        panelMediaView.setMediaPlayer(mediaPlayer);
        panelMediaView.fitWidthProperty().bind(panel.widthProperty());
        panelMediaView.fitHeightProperty().bind(panel.heightProperty());
        panelMediaView.setPreserveRatio(true);
        //mediaPlayer.setMute(true); Esto mutea el audio.

        //Cargar datos en segundo plano.
        Thread thread = new Thread(this::cargarDatosBBDD);
        thread.setDaemon(true);
        thread.start();

        //Cuando termine el video, se cambia de pantalla.
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
        //Simula la carga de la base de datos
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/controlesMenu.fxml"));//Cargamos la vista contrelesMenu.
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) panel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());//Aplicamos la clase de style.css.
        stage.setResizable(true);
        stage.setTitle("Pagina principal"); //Cambiamos el título de la ventana.
        stage.show();
    }

}
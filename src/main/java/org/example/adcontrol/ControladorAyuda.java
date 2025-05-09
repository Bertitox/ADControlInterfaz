package org.example.adcontrol;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de la ayuda al usuario.
 *
 * @author Daniel García y Alberto
 * @version 2.0
 */
public class ControladorAyuda extends Controlador {

    private ResourceBundle bundle;

    //Elementos FXML a traducir
    @FXML
    private Label PreguntaIncidencias;
    @FXML
    private Label PreguntaInforme;
    @FXML
    private Label DescIncidencias;
    @FXML
    private Label DescInforme;
    @FXML
    private Label TituloMysql;
    @FXML
    private Label DescMysql;
    @FXML
    private Label TituloJasper;
    @FXML
    private Label DescJasper;

    /**
     * Inicializa los elementos de la interfaz y configura los botones de idioma.
     */
    @FXML
    public void initialize() {
        refrescarIdioma();
    }

    /**
     * Método cargarIdioma que se encarga de realizar la carga del idioma seleccionado.
     *
     * @param locale El método recibe como parámetro el idioma al que se quiere traducir.
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Debug

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            PreguntaIncidencias.setText(bundle.getString("PreguntaIncidencias"));
            PreguntaInforme.setText(bundle.getString("PreguntaInforme"));
            DescIncidencias.setText(bundle.getString("DescIncidencias"));
            DescInforme.setText(bundle.getString("DescInforme"));
            TituloMysql.setText(bundle.getString("TituloMysql"));
            DescMysql.setText(bundle.getString("DescMysql"));
            TituloJasper.setText(bundle.getString("TituloJasper"));
            DescJasper.setText(bundle.getString("DescJasper"));

            System.out.println("Idioma cargado exitosamente.");//Debug

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al cargar el idioma ", ButtonType.CLOSE);
        }
    }

    /**
     * Método encargado de abrir un enlace web en el navegador
     *
     * @param event El evento que espera el método
     * @throws URISyntaxException Error en el recurso
     * @throws IOException        Error si no se encuentra el recurso
     */
    @FXML
    public void lanza1(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://dev.mysql.com/doc/mysql-installer/en/"));
    }

    /**
     * Método encargado de abrir un enlace web en el navegador
     *
     * @param event El evento que espera el método
     * @throws URISyntaxException Error en el recurso
     * @throws IOException        Error si no se encuentra el recurso
     */
    @FXML
    public void lanza2(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/JasperReports"));
    }
}
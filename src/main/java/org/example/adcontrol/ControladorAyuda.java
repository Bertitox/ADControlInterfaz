package org.example.adcontrol;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de la ayuda al usuario.
 *
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorAyuda extends Controlador {
    private ResourceBundle bundle;
    //Elementos a traducir
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
     * Método encargado de abrir un enlace web en el navegador
     *
     * @param event El evento que espera el método
     * @throws URISyntaxException Error del recurso
     * @throws IOException        Error si no encuentra el recurso
     */
    @FXML
    public void lanza1(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://dev.mysql.com/doc/mysql-installer/en/"));
    }

    /**
     * Método encargado de abrir un enlace web en el navegador
     *
     * @param event El evento que espera el método
     * @throws URISyntaxException Error del recurso
     * @throws IOException        Error si no encuentra el recurso
     */
    @FXML
    public void lanza2(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/JasperReports"));
    }

}
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
 * @version 1.5
 */
public class ControladorAyuda extends Controlador{

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
     * Inicializa los elementos de la interfaz y configura los botones de idioma.
     */
    @FXML
    public void initialize() {
        refrescarIdioma();
    }

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
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Método encargado de abrir un enlace web en el navegador
     *
     * @param event El evento que espera el método
     * @throws URISyntaxException Error del recurso
     * @throws IOException Error si no encuentra el recurso
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
     * @throws IOException Error si no encuentra el recurso
     */
    @FXML
    public void lanza2(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/JasperReports"));
    }

}
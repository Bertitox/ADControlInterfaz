package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de la ayuda al usuario.
 *
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorAyuda extends Controlador{

    @FXML
    MenuButton idiomas;

    @FXML
    MenuItem español;

    @FXML
    MenuItem ingles;

    @FXML
    MenuItem frances;

    @FXML
    private Button ajustesBoton;
    @FXML
    private Button ayudaBoton;
    @FXML
    private Button homeBoton;
    @FXML
    private Button monitorBoton;
    @FXML
    private Button salirBoton;

    private ResourceBundle bundle;

    List<Button> botones;

    Boolean isInHome = true;

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
        botones = new ArrayList<>();
        botones.add(ajustesBoton);
        botones.add(ayudaBoton);
        botones.add(homeBoton);
        botones.add(monitorBoton);
        botones.add(salirBoton);

        //Idiomas
        español.setOnAction(e -> {
            cargarIdioma(new Locale("es"));
            actualizarTextoIdioma("Español"); //Actualizar texto Idioma
        });

        ingles.setOnAction(e -> {
            cargarIdioma(new Locale("en"));
            actualizarTextoIdioma("English"); //Actualizar texto Idioma
        });

        frances.setOnAction(e -> {
            cargarIdioma(new Locale("fr"));
            actualizarTextoIdioma("Français"); //Actualizar texto Idioma
        });

        // Cargar el idioma inicial (Español)
        cargarIdioma(new Locale("es"));
        actualizarTextoIdioma("Español"); //Actualizar texto Idioma
    }

    /**
     * Método que cambia el texto para indicar el idioma actual al que se está traduciendo.
     * @param idioma Recibe un String idioma
     */
    private void actualizarTextoIdioma(String idioma) {
        idiomas.setText("" + idioma);
    }

    //Cargar idiomas
    // Método para cambiar el idioma
    /**
     * Carga los textos en el idioma seleccionado.
     *
     * @param locale El idioma a cargar.
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

        }catch (Exception e) {
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
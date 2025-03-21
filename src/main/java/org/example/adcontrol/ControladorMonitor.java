package org.example.adcontrol;

import BBDD.DAO.CRUDIncidencia;
import BBDD.DTO.Incidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de usuario y la lógica de la aplicación.
 *
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorMonitor extends Controlador{

    @FXML
    ListView<String> listIncidencias;

    @FXML
    TextField textIncidencias;

    @FXML
    PieChart graficoIncidencias;

    @FXML
    Button actualizar;

    @FXML
    Button actualizar1;

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
    private Label textIncidencia;
    @FXML
    private Label tituloIncidencias;

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
    //Método para cambiar el idioma
    /**
     * Carga los textos en el idioma seleccionado.
     *
     * @param locale El idioma a cargar.
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Debug

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            textIncidencia.setText(bundle.getString("textIncidencia"));
            tituloIncidencias.setText(bundle.getString("tituloIncidencias"));
            actualizar.setText(bundle.getString("boton.text"));
            actualizar1.setText(bundle.getString("boton2.text"));

            System.out.println("Idioma cargado exitosamente.");//Debug

        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Método que rellena el ListView
    /**
     * Muestra las incidencias en el ListView.
     *
     * @throws AulaNotFoundException Si el aula no se encuentra.
     */
    public void mostrarIncidenciasListView() throws AulaNotFoundException {
        CRUDIncidencia crud = new CRUDIncidencia();
        List<Incidencia> incidencias = crud.incidenciasXAulas(textIncidencias.getText().toString());

        //Pasamos la información a una Lista observable para el ListView
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Incidencia incidencia : incidencias) {
            items.add("Código Error: " + incidencia.getCodigoError().getCodigoError());
            items.add("Descripción: " + incidencia.getDescripcion());
            items.add("");
        }

        listIncidencias.setItems(items);
    }

    //Método para rellenar el gráfico
    /**
     * Método que actualiza y rellena el gráfico de incidencias.
     */
    @FXML
    public void actualizarGrafico() {
        String referencia = textIncidencias.getText().trim(); //Obtener referencia del aula ingresada
        CRUDIncidencia crudIncidencia = new CRUDIncidencia();

        if (referencia.isEmpty()) {
            mostrarAlerta("Incidencias Aula","Ingrese la referencia a un Aula");
            return;
        }

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        try {
            int numIncidencias = crudIncidencia.numIncidenciasAula(referencia);

            if (numIncidencias > 0) {
                datosGrafico.add(new PieChart.Data(referencia, numIncidencias));
            } else {
                mostrarAlerta("Incidencias Aula","No hay Incidencias para el Aula "+ textIncidencias.getText().toString());
            }
        } catch (AulaNotFoundException e) {
            System.err.println("Error: " + e.getMessage()); //Manejo del error en el caso de que el aula no exista
        }

        graficoIncidencias.setData(datosGrafico); //Asignación de los datos al Gráfico PieChart
    }

    /**
     * Muestra errores al usuario.
     *
     * @param titulo  El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
package org.example.adcontrol;


import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ControladorApp implements Runnable{
    @FXML
    private ImageView exitIcon;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem action1;
    @FXML
    private MenuItem action2;
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
    @FXML
    private Pane panel1;

    //Campos del Gráfico de datos
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    List<Button> botones;

    Boolean isInHome = true;

    /**
     * Método que incializa la lista y se añaden los botones a esta. También añade los datos al gráfico
     */
    @FXML
    public void initialize() {
        botones = new ArrayList<>();
        botones.add(ajustesBoton);
        botones.add(ayudaBoton);
        botones.add(homeBoton);
        botones.add(monitorBoton);
        botones.add(salirBoton);
        //actualizarGrafico();
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

    @FXML
    void zoomPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.01);
        zoomIn.setToY(1.01);
        zoomIn.play();
    }

    @FXML
    void quitarzoomPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }

    @FXML
    void zoomBarChart(MouseEvent event) {
        BarChart barChart = (BarChart) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), barChart);

        zoomIn.setToX(1.01);
        zoomIn.setToY(1.01);
        zoomIn.play();
    }

    @FXML
    void quitarzoomBarChart(MouseEvent event) {
        BarChart barChart = (BarChart) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), barChart);

        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }


    /**
     * Método que se encarga de cambiar la pantalla actual por la correspondiente al botón pulsado (pantalla de administradorS de equipos)
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cambiarPantallaMonitor(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistaIncidencias.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ajustesBoton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }

    @FXML
    void cambiarPantallaHome(ActionEvent event) throws IOException {
        this.isInHome = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ajustesBoton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }


    @FXML
    void cambiarpantallaAyuda(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistaAyuda.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ajustesBoton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }

    @FXML
    void cambiarpantallaConfig(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistaConfiguracion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ajustesBoton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }

    /**
     * Método que rellenará los datos del gráfico de barras obteniendo la información de la BBDD
     *
     * @throws AulaNotFoundException Excepción personalizada que se mostrará si no se encuentra ningún aula
     */
    public void actualizarGrafico() throws AulaNotFoundException {
        // Obtener las referencias de todas las aulas con incidencias
        CRUDIncidencia incidencia = new CRUDIncidencia();
        Set<String> aulasConIncidencias = incidencia.getAulasIncidencias();

        // Crear una lista observable para almacenar los datos del gráfico
        ObservableList<XYChart.Data<String, Number>> datosGrafico = FXCollections.observableArrayList();

        // Iterar sobre todas las aulas y obtener el número de incidencias para cada una
        for (String referencia : aulasConIncidencias) {
            int numIncidencias = incidencia.getNumIncidenciasAulas(referencia);  // Obtener el número de incidencias por aula
            // Crear un objeto Data con la referencia del aula y el número de incidencias
            XYChart.Data<String, Number> data = new XYChart.Data<>(referencia, numIncidencias);
            datosGrafico.add(data);
        }

        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        barChart.setLegendVisible(false);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickMarkVisible(true);

        // Crear una serie de datos y añadir los datos al gráfico
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setData(datosGrafico);
        barChart.setBarGap(500);
        barChart.setCategoryGap(200);

        // Limpiar el gráfico y añadir la nueva serie de datos
        barChart.getData().clear();
        barChart.getData().add(serie);
    }

    /**
     * Evento que muestra al usuario una ventana de confirmación par salir o no de la app
     *
     * @param event Evento de pulsacion del botón
     */
    public void salir(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que desea salir?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Salir de ADControl");
        alert.setHeaderText(null); // Elimina encabezado

        // Mostrar el diálogo y esperar respuesta
        if (alert.showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }

    @Override
    public void run() {
        try {
            actualizarGrafico();
        } catch (AulaNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

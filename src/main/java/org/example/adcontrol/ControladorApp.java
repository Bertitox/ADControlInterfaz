package org.example.adcontrol;


import BBDD.DAO.CRUDAula;
import BBDD.DAO.CRUDIncidencia;
import BBDD.DTO.Aula;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorApp {//implements Initializable {
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

    @FXML
    private Label lblTituloGrave;

    @FXML
    private TextArea nombreInforme;

    @FXML
    private Label lblTituloUltimasIncidecias;

    //Campos del Gráfico de datos
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label campoFecha;

    @FXML
    private Label campoFecha1;

    @FXML
    private TextArea textAreaRuta;

    @FXML
    private ComboBox comboboxInforme = new ComboBox();

    List<Button> botones;

    Boolean isInHome = true;

    @FXML
    MenuButton idiomas;

    @FXML
    MenuItem español;

    @FXML
    MenuItem ingles;

    @FXML
    private Label ultimoNombre;

    @FXML
    private Label ultimoInforme;

    @FXML
    MenuItem frances;

    @FXML
    Button botonGenerar;

    @FXML
    Button botonExplorar;

    //Elementos a traducir
    @FXML
    private Label labelIncidencias;
    @FXML
    private Label labelAulasDisponibles;
    @FXML
    private Label textIncidenciasSistema;
    @FXML
    private Label textAulasDisponibles;
    @FXML
    private Label textFecha1;
    @FXML
    private Label textFecha2;

    @FXML
    private Label numeroTotalInformes;

    @FXML
    private Label informeMasUtilizado;

    Map<String, Integer> mapaInformeUtilizado;

    Integer nTotal = 0;

    private ResourceBundle bundle;

    /**
     * Costructor por defecto del controlador
     */
    public ControladorApp() {
    }

    /**
     * Método que incializa la lista y se añaden los botones a esta. También añade los datos al gráfico y los idiomas.
     * Inicializa la app entera.
     */
    @FXML
    public void initialize() {
        botonGenerar = new Button();
        botonExplorar = new Button();
        botonExplorar.getStyleClass().add("botonPrueba");
        botonGenerar.getStyleClass().add("botonPrueba");

        mapaInformeUtilizado = new HashMap<>();
        ObservableList<String> items = FXCollections.observableArrayList("Aulas", "Incidencias", "Equipos");
        comboboxInforme.setItems(items);

        botones = new ArrayList<>();
        botones.add(ajustesBoton);
        botones.add(ayudaBoton);
        botones.add(homeBoton);
        botones.add(monitorBoton);
        botones.add(salirBoton);

        Platform.runLater(() -> {

            //Idiomas
            español.setOnAction(e -> cargarIdioma(new Locale("es")));
            ingles.setOnAction(e -> cargarIdioma(new Locale("en")));
            frances.setOnAction(e -> cargarIdioma(new Locale("fr")));

            // Cargar el idioma inicial (Español)
            cargarIdioma(new Locale("es"));

            //Gestión incidencias BBDD
            CRUDIncidencia incidencia = new CRUDIncidencia();
            CRUDAula aula = new CRUDAula();

            //Actualizar Incidencias
            actualizarIncidencias(incidencia.numIncidencias(), aula.readAllAulas().size());
            campoFecha.setText(LocalDate.now().toString());
            campoFecha1.setText(LocalDate.now().toString());
            try {
                actualizarGrafico();
            } catch (Exception e) {
            }
        });

    }

    /**
     * Método que sirve para cambiar el idioma
     * @param locale Recive el idioma Local ("es")
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Debug

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            labelIncidencias.setText(bundle.getString("labelIncidencias"));
            labelAulasDisponibles.setText(bundle.getString("labelAulasDisponibles"));
            textIncidenciasSistema.setText(bundle.getString("textIncidenciasSistema"));
            textAulasDisponibles.setText(bundle.getString("textAulasDisponibles"));
            textFecha1.setText(bundle.getString("textFecha1"));
            textFecha2.setText(bundle.getString("textFecha2"));

            System.out.println("Idioma cargado exitosamente.");//Debug

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Método para actualizar el número de incidencias que hay en el sistema
     * @param incidenciasGraves Recive un int de las incidencias graves que hay en el sistema
     * @param incidenciasLeves Recive un int de las incidencias leves que hay en el sistema
     */
    @FXML
    public void actualizarIncidencias(int incidenciasGraves, int incidenciasLeves) {
        lblTituloGrave.setText(incidenciasGraves + "");
        lblTituloUltimasIncidecias.setText(incidenciasLeves + "");
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

    /**
     * Pone el zoom al Pane
     * @param event Evento que espera el método
     */
    @FXML
    void zoomPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.01);
        zoomIn.setToY(1.01);
        zoomIn.play();
    }

    /**
     * Pone el zoom al Pane
     * @param event Evento que espera el método
     */
    @FXML
    void quitarzoomPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }

    /**
     * Pone el zoom al Gráfico
     * @param event Evento que espera el método
     */
    @FXML
    void zoomBarChart(MouseEvent event) {
        BarChart barChart = (BarChart) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), barChart);

        zoomIn.setToX(1.01);
        zoomIn.setToY(1.01);
        zoomIn.play();
    }

    /**
     * Quita el zoom al Gráfico
     * @param event Evento que espera el método
     */
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
     * @param event evento que espera el método
     * @throws IOException excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarPantallaMonitor(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaIncidencias.fxml"));
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
     * Método que sirve para cambiar la pantalla Home
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarPantallaHome(ActionEvent event) throws IOException {
        this.isInHome = true;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/hello-view.fxml"));
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
     * Método que sirve para cambiar la pantalla de Ayuda
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarpantallaAyuda(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAyuda.fxml"));
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
     * Método que sirve para cambiar la pantalla de Configuracion
     * @param event Evento que inicia el método
     * @throws IOException Excepción de entrada salida lanzada por el método
     */
    @FXML
    void cambiarpantallaConfig(ActionEvent event) throws IOException {
        this.isInHome = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaConfiguracion.fxml"));
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
        //Obtengo la referencia de las aulas con incidencias
        CRUDIncidencia incidencia = new CRUDIncidencia();
        Set<String> aulasConIncidencias = incidencia.getAulasIncidencias();

        //Crea una lista observable para almacenar datos del gráfico
        ObservableList<XYChart.Data<String, Number>> datosGrafico = FXCollections.observableArrayList();

        //Foreach sobre todas las aulas para obtener el número de incidencias de cada una
        for (String referencia : aulasConIncidencias) {
            int numIncidencias = incidencia.getNumIncidenciasAulas(referencia);  //Obtiene número de incidencias por aula
            //Objeto Data con la referencia del aula y el número de incidencias
            XYChart.Data<String, Number> data = new XYChart.Data<>(referencia, numIncidencias);
            datosGrafico.add(data);
        }

        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        barChart.setLegendVisible(false);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickMarkVisible(true);

        //Añadir los datos al gráfico
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setData(datosGrafico);
        barChart.setBarGap(500);
        barChart.setCategoryGap(100);

        //Limpiar el gráfico
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
        alert.setHeaderText(null); //Elimina el encabezado

        //Mostrar diálogo y esperar a la respuesta del usuario
        if (alert.showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }
}

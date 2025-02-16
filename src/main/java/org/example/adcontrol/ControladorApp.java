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
     * Método que incializa la lista y se añaden los botones a esta. También añade los datos al gráfico
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

    //Cargar idiomas
    // Método para cambiar el idioma
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


    //Método para actualizar el número de incidencias
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
        barChart.setCategoryGap(100);

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

    @FXML
    public void lanza1(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://dev.mysql.com/doc/mysql-installer/en/"));
    }

    @FXML
    public void lanza2(Event event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/JasperReports"));
    }

    @FXML
    void generarPDF(ActionEvent event) throws ClassNotFoundException, SQLException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        Map parametros = new HashMap();
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/infoSistema", "root", "210205");
        JasperPrint print = null;
        if (comboboxInforme.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el informe", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null); // Elimina encabezado
            alert.showAndWait();

        } else if(textAreaRuta.getText().isBlank() || textAreaRuta.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selecciona una ruta", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null); // Elimina encabezado
            alert.showAndWait();
        }else{
            switch (comboboxInforme.getValue().toString()) {
                case "Aulas":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/InformeAula.jasper", null, conexion);
                    break;
                case "Incidencias":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/InformeIncidencias.jasper", null, conexion);
                    break;
                case "Equipos":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/Informe.jasper", null, conexion);
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el informe", ButtonType.OK);
                    alert.setTitle("Error");
                    alert.setHeaderText(null); // Elimina encabezado
                    alert.showAndWait();
                    break;
            }

            String ruta;
            if (nombreInforme.getText().isBlank() || nombreInforme.getText().isEmpty()) {
                ruta = textAreaRuta.getText() + "/informe.pdf";
            } else {
                ruta = textAreaRuta.getText() +"/" +  nombreInforme.getText() + ".pdf";
            }
            JasperExportManager.exportReportToPdfFile(print, ruta);
            ultimoInforme.setText(comboboxInforme.getValue().toString());
            mapaInformeUtilizado.put(comboboxInforme.getValue().toString(), mapaInformeUtilizado.getOrDefault(comboboxInforme.getValue().toString(), 0) + 1);
            this.nTotal++;
            numeroTotalInformes.setText(this.nTotal.toString());
            informeMasUtilizado.setText(getInformeMas().toString());
            ultimoNombre.setText(nombreInforme.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Informe creado correctamente", ButtonType.OK);
            alert.setTitle("Informe creado");
            alert.setHeaderText(null); // Elimina encabezado
            alert.showAndWait();
            nombreInforme.setText("");
        }
    }

    @FXML
    void cambiarRuta(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File f = directoryChooser.showDialog(null);
        textAreaRuta.setText(f.getAbsolutePath());
    }

    public String getInformeMas() {
        Integer MAX = 0;
        String informe = null;
        for (String i : mapaInformeUtilizado.keySet()) {
            if (mapaInformeUtilizado.get(i) > MAX) {
                MAX = mapaInformeUtilizado.get(i);
                informe = i;
            }
        }
        return informe;
    }


    @FXML
    void hoverInforme(MouseEvent event) {
        Button boton = (Button) event.getSource();

        boton.setStyle("-fx-background-color: grey");
        boton.setStyle("-fx-border-color: grey");
        boton.setStyle("-fx-border-radius: 5px");
    }

    @FXML
    void hoverNormalInforme(MouseEvent event) {
        Button boton = (Button) event.getSource();

        boton.getStyleClass().add("botonPrueba"); // Aplica la clase CSS al botón
        boton.setStyle("-fx-background-color: transparent");
        boton.setStyle("-fx-border-color: grey");
        boton.setStyle("-fx-border-radius: 5px");

    }
}

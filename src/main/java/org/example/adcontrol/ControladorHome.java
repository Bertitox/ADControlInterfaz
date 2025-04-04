package org.example.adcontrol;
import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Daniel y Alberto
 * @version 1.5
 */
public class ControladorHome extends Controlador{//implements Initializable {

    @FXML
    private Label lblTituloGrave;

    @FXML
    private Label lblTituloUltimasIncidecias;

    //Campos del Gráfico de datos
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label campoFecha;

    @FXML
    private Label campoFecha1;

    @FXML
    private ComboBox comboboxInforme = new ComboBox();


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

    Map<String, Integer> mapaInformeUtilizado;

    private ResourceBundle bundle;

    /**
     * Costructor por defecto del controlador
     */
    public ControladorHome() {
    }

    /**
     * Método que incializa la lista y se añaden los botones a esta. También añade los datos al gráfico y los idiomas.
     * Inicializa la app entera.
     */
    public void initialize() {
        refrescarIdioma();

        botonGenerar = new Button();
        botonExplorar = new Button();
        botonExplorar.getStyleClass().add("botonPrueba");
        botonGenerar.getStyleClass().add("botonPrueba");


        mapaInformeUtilizado = new HashMap<>();
        ObservableList<String> items = FXCollections.observableArrayList("Aulas", "Incidencias", "Equipos");
        comboboxInforme.setItems(items);

        //Gestión incidencias BBDD
        CRUDIncidencia incidencia = new CRUDIncidencia();
        CRUDAula_Equipo aula = new CRUDAula_Equipo();

        //Actualizar Incidencias
        actualizarIncidencias(incidencia.numIncidencias(), aula.readAllAulas().size());
        campoFecha.setText(LocalDate.now().toString());
        campoFecha1.setText(LocalDate.now().toString());
        try {
            actualizarGrafico();
        } catch (Exception e) {
        }
    }

    /**
     * Método que cambia el idioma
     *
     * @param locale Recibe el idioma Local ("es")
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage());

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            labelIncidencias.setText(bundle.getString("labelIncidencias"));
            labelAulasDisponibles.setText(bundle.getString("labelAulasDisponibles"));
            textIncidenciasSistema.setText(bundle.getString("textIncidenciasSistema"));
            textAulasDisponibles.setText(bundle.getString("textAulasDisponibles"));
            textFecha1.setText(bundle.getString("textFecha1"));
            textFecha2.setText(bundle.getString("textFecha2"));

            System.out.println("Idioma cargado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para actualizar el número de incidencias que hay en el sistema
     *
     * @param incidenciasGraves Recive un int de las incidencias graves que hay en el sistema
     * @param incidenciasLeves  Recive un int de las incidencias leves que hay en el sistema
     */
    @FXML
    public void actualizarIncidencias(int incidenciasGraves, int incidenciasLeves) {
        lblTituloGrave.setText(incidenciasGraves + "");
        lblTituloUltimasIncidecias.setText(incidenciasLeves + "");
    }

    /**
     * Pone el zoom al Gráfico
     *
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
     *
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

        //Desactivamos el ajuste automático del rango
        yAxis.setAutoRanging(false);
        //Definimos valores mínimos y máximos del eje Y
        yAxis.setLowerBound(0); // Empieza en 0
        yAxis.setUpperBound(incidencia.numMaximoIcidenciasAula()); //Ajustamos al máximo necesario
        //Configura la separación entre marcas del eje
        yAxis.setTickUnit(1);
        //Elimina las marcas menores y asegura que las principales estén visibles
        yAxis.setMinorTickCount(0);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickMarkVisible(true);

        //Formatea las etiquetas como números enteros
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });


        //Añadir datos al gráfico
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setData(datosGrafico);
        barChart.setBarGap(500);
        barChart.setCategoryGap(100);

        //Limpiar el gráfico
        barChart.getData().clear();
        barChart.getData().add(serie);
    }

}
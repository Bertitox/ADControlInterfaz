package org.example.adcontrol;

import BBDD.DAO.CRUDAula;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ControladorApp implements Initializable {

    @FXML
    private ImageView exitIcon;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem action1;

    @FXML
    private MenuItem action2;

    List<Button> botones;

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

    //Campos del Gráfico de datos
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    // Método para inicializar componentes
    @FXML
    public void initialize() {
        botones = new ArrayList<>();
        botones.add(ajustesBoton);
        botones.add(ayudaBoton);
        botones.add(homeBoton);
        botones.add(monitorBoton);
        botones.add(salirBoton);
    }

    @FXML
    void hoverBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), boton);
        zoomIn.setToX(1.1);  // 10% más grande en X
        zoomIn.setToY(1.1);  // 10% más grande en Y
        zoomIn.setDelay(Duration.millis(100)); // Pequeño delay
        zoomIn.play();
    }


    @FXML
    void normalBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(150), boton);
        zoomIn.setToX(1.0);  // 10% más grande en X
        zoomIn.setToY(1.0);  // 10% más grande en Y
        zoomIn.setDelay(Duration.millis(100)); // Pequeño delay
        zoomIn.play();


    }

    @FXML
    void cambiarPantallaMonitor(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantallaCarga.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ajustesBoton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }

    // Método que actualizará el gráfico
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

        // Crear una serie de datos y añadir los datos al gráfico
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setData(datosGrafico);

        // Limpiar el gráfico y añadir la nueva serie de datos
        barChart.getData().clear();
        barChart.getData().add(serie);
    }

    // Método para ser llamado cuando se desee actualizar el gráfico, como por ejemplo en un botón
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            actualizarGrafico();  // Llamar al método para actualizar el gráfico al iniciar la app
        } catch (AulaNotFoundException e) {
            e.printStackTrace();
            // Aquí podrías agregar algún manejo de errores, como mostrar un mensaje en la UI
        }
    }

}

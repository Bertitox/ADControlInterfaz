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
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
public class ControladorPC {

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

    /**
     * Método que define el estilo de los botones cuando el ratón pasa por encima.
     *
     * @param event El evento de pasar el ratón por encima del botón.
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
     * @param event Cuando el ratón no está por encima de algún boton
     */
    @FXML
    void normalBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();

        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(150), boton);
        zoomIn.setToX(1.0);  //10% más grande en X
        zoomIn.setToY(1.0);  //10% más grande en Y
        zoomIn.play();
    }

    /**
     * Aplica zoom al pasar el ratón por encima de un Pane.
     *
     * @param event El evento de pasar el ratón por encima del Pane.
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
     * Restaura el tamaño normal del Pane.
     *
     * @param event El evento de quitar el ratón de encima del Pane.
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

    /**
     * Cambia la pantalla a la vista principal.
     *
     * @param event El evento de pulsación del botón.
     * @throws IOException Si ocurre un error al cargar la vista.
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
     * Método que se usa para cambiar la pantalla de ayuda
     * @param event El evento de pulsación del botón.
     * @throws IOException Si ocurre un error al cargar la vista.
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
     * Método que se usa para cambiar la pantalla de configuración
     * @param event El evento de pulsación del botón.
     * @throws IOException Si ocurre un error al cargar la vista.
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
     * Evento que muestra al usuario una ventana de confirmación par salir o no de la app
     *
     * @param event Evento de pulsacion del botón
     */
    public void salir(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que desea salir?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Salir de ADControl");
        alert.setHeaderText(null); //Elimina el encabezado

        //Mostrar el diálogo y esperar respuesta
        if (alert.showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }
}
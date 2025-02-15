package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class ControladorVistaAyuda {

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

    @FXML
    public void initialize() {
        botones = new ArrayList<>();
        botones.add(ajustesBoton);
        botones.add(ayudaBoton);
        botones.add(homeBoton);
        botones.add(monitorBoton);
        botones.add(salirBoton);

        español.setOnAction(e -> cargarIdioma(new Locale("es")));
        ingles.setOnAction(e -> cargarIdioma(new Locale("en")));
        frances.setOnAction(e -> cargarIdioma(new Locale("fr")));
    }

    //Cargar idiomas
    // Método para cambiar el idioma
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
        zoomIn.setToX(1.0);  //10% más grande en X
        zoomIn.setToY(1.0);  //10% más grande en Y
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
}

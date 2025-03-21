package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControladorMenu {

    @FXML
    public Pane panelPrincipal;

    @FXML
    MenuButton idiomas;

    @FXML
    MenuItem español;

    @FXML
    MenuItem ingles;

    @FXML
    MenuItem frances;

    @FXML
    public void initialize() throws IOException {
        // Comprobar que los elementos FXML no son nulos
        System.out.println("Español: " + español);
        System.out.println("Inglés: " + ingles);
        System.out.println("Francés: " + frances);

        if (español == null || ingles == null || frances == null) {
            System.err.println("Error: Los elementos FXML no fueron inyectados correctamente.");
        } else {
            Platform.runLater(() -> {
                // Configurar los eventos de acción para cada idioma
                español.setOnAction(e -> {
                    cambiarIdioma(new Locale("es"));
                    actualizarTextoIdioma("Español");
                });

                ingles.setOnAction(e -> {
                    cambiarIdioma(new Locale("en"));
                    actualizarTextoIdioma("English");
                });

                frances.setOnAction(e -> {
                    cambiarIdioma(new Locale("fr"));
                    actualizarTextoIdioma("Français");
                });

                // Cargar el idioma inicial (Español)
                cambiarIdioma(new Locale("es"));
                actualizarTextoIdioma("Español");
            });
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);


    }

    // Método que se encarga de cambiar el idioma
    private void cambiarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage());

            // Cargar el idioma (esto se puede adaptar a tu lógica para cargar el idioma)
            // Cambiar las cadenas de texto en los elementos de la interfaz según el idioma
            ResourceBundle bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);

            // Ejemplo de actualización de elementos con los textos traducidos
            // Asumiendo que tienes estos elementos en tu interfaz:
            // labelIncidencias.setText(bundle.getString("labelIncidencias"));
            // labelAulasDisponibles.setText(bundle.getString("labelAulasDisponibles"));

            System.out.println("Idioma cargado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de error de carga del idioma
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar el idioma: " + e.getMessage(), ButtonType.OK);
            alert.setTitle("Error de idioma");
            alert.show();
        }
    }

    // Método que actualiza el texto del idioma en la interfaz
    private void actualizarTextoIdioma(String idioma) {
        if (idiomas != null) {
            idiomas.setText(idioma);
        }
    }

    @FXML
    void cambiarpantallaInforme(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaInforme.fxml"));
        fxmlLoader.setController(this); // Establecer el controlador manualmente
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    void cambiarPantallaMonitor(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaMonitor.fxml"));
        fxmlLoader.setController(this); // Establecer el controlador manualmente
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    void cambiarPantallaHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaHome.fxml"));
        fxmlLoader.setController(this); // Establecer el controlador manualmente
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    void cambiarpantallaAyuda(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAyuda.fxml"));
        fxmlLoader.setController(this); // Establecer el controlador manualmente
        Parent root = fxmlLoader.load();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    public void salir(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que desea salir?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Salir de ADControl");
        alert.setHeaderText(null);

        if (alert.showAndWait().get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    void hoverBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), boton);
        zoomIn.setToX(1.1);
        zoomIn.setToY(1.1);
        zoomIn.play();
    }

    @FXML
    void normalBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(150), boton);
        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }

    // Getters para los elementos FXML
    public MenuButton getIdiomas() {
        return idiomas;
    }

    public MenuItem getEspañol() {
        return español;
    }

    public MenuItem getIngles() {
        return ingles;
    }

    public MenuItem getFrances() {
        return frances;
    }
}

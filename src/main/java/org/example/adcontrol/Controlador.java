package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Locale;

/**
 * @author Daniel García y Alberto
 * @version 2.0
 * Clase controladora de toda la app. Incluye métodos comunes a todos los demás controladores.
 */
public class Controlador {

    ControlIdioma controlIdioma = ControlIdioma.getInstance();

    /**
     * Constructor principal de la clase Controlador
     */
    public Controlador() {
        controlIdioma.getIdioma().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "Español":
                        cargarIdioma(new Locale("es"));
                        break;
                    case "English":
                        cargarIdioma(new Locale("en"));
                        break;
                    case "Français":
                        cargarIdioma(new Locale("fr"));
                        break;
                }
            }
        });
    }

    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     *
     * @param menuButton Menubutton al que se le aplica el efecto.
     */
    public void aplicarEfectoHoverMenuButton(MenuButton menuButton) {
        ScaleTransition stHover = new ScaleTransition(Duration.millis(200), menuButton);
        stHover.setToX(1.05);
        stHover.setToY(1.05);

        ScaleTransition stNormal = new ScaleTransition(Duration.millis(200), menuButton);
        stNormal.setToX(1.0);
        stNormal.setToY(1.0);

        menuButton.setOnMouseEntered(e -> stHover.playFromStart());
        menuButton.setOnMouseExited(e -> stNormal.playFromStart());
    }

    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     *
     * @param Button Botom al que se le aplica el efecto.
     */
    public void aplicarEfectoHoverButton(Button Button) {
        ScaleTransition stHover = new ScaleTransition(Duration.millis(200), Button);
        stHover.setToX(1.05);
        stHover.setToY(1.05);

        ScaleTransition stNormal = new ScaleTransition(Duration.millis(200), Button);
        stNormal.setToX(1.0);
        stNormal.setToY(1.0);

        Button.setOnMouseEntered(e -> stHover.playFromStart());
        Button.setOnMouseExited(e -> stNormal.playFromStart());
    }

    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     *
     * @param Button Botom al que se le aplica el efecto.
     * @valor Valor numerico que especifica el tamaño de la escala del boton
     */
    public void aplicarEfectoHoverButton(Button Button, Double valor) {
        ScaleTransition stHover = new ScaleTransition(Duration.millis(200), Button);
        stHover.setToX(valor);
        stHover.setToY(valor);

        ScaleTransition stNormal = new ScaleTransition(Duration.millis(200), Button);
        stNormal.setToX(1.0);
        stNormal.setToY(1.0);

        Button.setOnMouseEntered(e -> stHover.playFromStart());
        Button.setOnMouseExited(e -> stNormal.playFromStart());
    }

    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     *
     * @param comboBox comboBox al que se le aplica el efecto.
     */
    public void aplicarEfectoHoverComboBox(ComboBox comboBox) {
        ScaleTransition stHover = new ScaleTransition(Duration.millis(200), comboBox);
        stHover.setToX(1.05);
        stHover.setToY(1.05);

        ScaleTransition stNormal = new ScaleTransition(Duration.millis(200), comboBox);
        stNormal.setToX(1.0);
        stNormal.setToY(1.0);

        comboBox.setOnMouseEntered(e -> stHover.playFromStart());
        comboBox.setOnMouseExited(e -> stNormal.playFromStart());
    }

    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     *
     * @param comboBox comboBox al que se le aplica el efecto.
     * @param valor Valor numerico que especifica el tamaño de la escala del boton
     */
    public void aplicarEfectoHoverComboBox(ComboBox comboBox, Double valor) {
        ScaleTransition stHover = new ScaleTransition(Duration.millis(200), comboBox);
        stHover.setToX(valor);
        stHover.setToY(valor);

        ScaleTransition stNormal = new ScaleTransition(Duration.millis(200), comboBox);
        stNormal.setToX(1.0);
        stNormal.setToY(1.0);

        comboBox.setOnMouseEntered(e -> stHover.playFromStart());
        comboBox.setOnMouseExited(e -> stNormal.playFromStart());
    }

    /**
     * Clase que se encarga de cambiar el idioma según la opción seleccionada en el ComboBox
     */
    public void refrescarIdioma() {
        String idioma = controlIdioma.getIdioma().getValue();
        switch (idioma) {
            case "Español":
                cargarIdioma(new Locale("es"));
                break;
            case "English":
                cargarIdioma(new Locale("en"));
                break;
            case "Français":
                cargarIdioma(new Locale("fr"));
                break;
        }
    }

    /**
     * Método que carga el idioma, y que implementará cada controlador.
     *
     * @param locale
     */
    public void cargarIdioma(Locale locale) {
        //Método vacío que sobreescribe las clases de provienen de esta.
    }

    /**
     * Método que añade un efecto de zoom cuando se superpone el cursor por encima de un botón.
     *
     * @param event Cuando se pasa el cursor por encima
     */
    @FXML
    void hoverBoton(MouseEvent event) {
        Button boton = (Button) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), boton);
        zoomIn.setToX(1.1);
        zoomIn.setToY(1.1);
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
        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }

    /**
     * Pone el zoom al Pane
     *
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
     *
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
     * Pone el zoom al vBox
     *
     * @param event Evento que espera el método
     */
    @FXML
    void zoomVBox(MouseEvent event) {
        VBox pane = (VBox) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.01);
        zoomIn.setToY(1.01);
        zoomIn.play();
    }

    /**
     * Quita el zoom al vBox
     *
     * @param event Evento que espera el método
     */
    @FXML
    void quitarzoomVBox(MouseEvent event) {
        VBox pane = (VBox) event.getSource();
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(100), pane);

        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }

}

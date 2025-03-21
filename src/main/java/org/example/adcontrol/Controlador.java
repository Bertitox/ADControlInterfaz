package org.example.adcontrol;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Locale;


public class Controlador {

    ControlIdioma controlIdioma = ControlIdioma.getInstance();

    public Controlador() {
        controlIdioma.getIdioma().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "Español":
                        cargarIdioma(new Locale("es"));
                        break;
                    case "Ingles":
                        cargarIdioma(new Locale("en"));
                        break;
                    case "Frances":
                        cargarIdioma(new Locale("fr"));
                        break;
                }
            }
        });
    }

    public void refrescarIdioma() {
        String idioma = controlIdioma.getIdioma().getValue();
        switch (idioma) {
            case "Español":
                cargarIdioma(new Locale("es"));
                break;
            case "Ingles":
                cargarIdioma(new Locale("en"));
                break;
            case "Frances":
                cargarIdioma(new Locale("fr"));
                break;
        }
    }

    public void cargarIdioma(Locale locale) {
        //MÉTDODO VACÍO QUE SOBREESCRIBEN LAS CLASES QUE EXTIENDEN DE ESTA
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

}

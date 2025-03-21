    package org.example.adcontrol;

    import javafx.animation.ScaleTransition;
    import javafx.application.Platform;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.MenuButton;
    import javafx.scene.control.MenuItem;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.Pane;
    import javafx.util.Duration;

    import java.io.IOException;
    import java.util.Locale;


        public class Controlador extends ControladorMenu {


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

package org.example.adcontrol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class Persona extends ImageView {

    private final Pane contenedor;
    private final List<Rectangle2D> zonasPasillo;
    private final List<Button> aulas;
    private final Random random = new Random();

    private double velocidad = 1.5;

    private double destinoX = -1;
    private double destinoY = -1;

    public Persona(Pane contenedor, List<Rectangle2D> zonasPasillo, List<Button> aulas) {
        this.contenedor = contenedor;
        this.zonasPasillo = zonasPasillo;
        this.aulas = aulas;

        // Carga la imagen (ajusta la ruta si está en otro paquete/carpeta)
        //Image imagen = new Image(getClass().getResource("/org/example/adcontrol/Imagenes/person-standing.png").toExternalForm());
        //this.setImage(imagen);
        this.setFitWidth(30);
        this.setFitHeight(30);

        // Posición inicial
        Rectangle2D inicio = zonasPasillo.get(random.nextInt(zonasPasillo.size()));
        setLayoutX(inicio.getMinX() + random.nextDouble() * inicio.getWidth());
        setLayoutY(inicio.getMinY() + random.nextDouble() * inicio.getHeight());

        contenedor.getChildren().add(this);
        iniciarMovimiento();
    }

    private void iniciarMovimiento() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), e -> moverHaciaDestino()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moverHaciaDestino() {
        if (destinoX == -1 || destinoY == -1 || haLlegado()) {
            Rectangle2D nuevaZona = zonasPasillo.get(random.nextInt(zonasPasillo.size()));
            destinoX = nuevaZona.getMinX() + random.nextDouble() * nuevaZona.getWidth();
            destinoY = nuevaZona.getMinY() + random.nextDouble() * nuevaZona.getHeight();
        }

        double dx = destinoX - getLayoutX();
        double dy = destinoY - getLayoutY();
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia > 1) {
            setLayoutX(getLayoutX() + velocidad * dx / distancia);
            setLayoutY(getLayoutY() + velocidad * dy / distancia);
        }

        verificarColision();
    }

    private boolean haLlegado() {
        return Math.abs(destinoX - getLayoutX()) < 2 && Math.abs(destinoY - getLayoutY()) < 2;
    }

    private void verificarColision() {
        Bounds personaBounds = this.localToScene(this.getBoundsInLocal());

        for (Button aula : aulas) {
            if (aula.localToScene(aula.getBoundsInLocal()).intersects(personaBounds)) {
                contenedor.getChildren().remove(this);
                break;
            }
        }
    }
}

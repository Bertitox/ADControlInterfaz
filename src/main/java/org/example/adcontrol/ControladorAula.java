package org.example.adcontrol;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ControladorAula {

    @FXML
    private MenuButton menuButtonAulas;

    @FXML
    private FlowPane contenedorMonitores; // Mejor usar FlowPane para distribución automática

    private Map<String, Integer> aulasMonitores;

    public void initialize() {
        // Mapeo de aulas con la cantidad de monitores
        menuButtonAulas = new MenuButton("Aulas");
        aulasMonitores = new HashMap<>();
        aulasMonitores.put("Aula 1", 10);
        aulasMonitores.put("Aula 2", 15);
        aulasMonitores.put("Aula 3", 8);

        // Agregar dinámicamente opciones al menú
        for (String aula : aulasMonitores.keySet()) {
            MenuItem item = new MenuItem(aula);
            item.setOnAction(event -> actualizarMonitores(aula));
            menuButtonAulas.getItems().add(item);
        }
    }

    private void actualizarMonitores(String aulaSeleccionada) {
        // Limpiar el contenedor antes de agregar nuevos monitores
        contenedorMonitores.getChildren().clear();

        // Obtener la cantidad de monitores según el aula seleccionada
        int cantidadMonitores = aulasMonitores.getOrDefault(aulaSeleccionada, 0);

        // Cargar la imagen del monitor
        InputStream imagenStream = getClass().getResourceAsStream("/Imagenes/imagenmonitor.png");

        if (imagenStream == null) {
            System.err.println("No se pudo cargar la imagen: /Imagenes/imagenmonitor.png");
            return;
        }

        Image imagenMonitor = new Image(imagenStream);

        // Crear y agregar los monitores
        for (int i = 0; i < cantidadMonitores; i++) {
            ImageView monitor = new ImageView(imagenMonitor);
            monitor.setFitWidth(80);  // Ajustar tamaño
            monitor.setFitHeight(50);
            contenedorMonitores.getChildren().add(monitor);
        }

        // Cambiar el texto del MenuButton a la opción seleccionada
        menuButtonAulas.setText(aulaSeleccionada);
    }
}

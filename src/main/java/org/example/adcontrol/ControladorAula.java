package org.example.adcontrol;

import BBDD.DAO.CRUDAula;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ControladorAula {

    @FXML
    private MenuButton menuButtonAulas = new MenuButton();

    @FXML
    private GridPane gridPaneMonitores;

    CRUDAula cruda = new CRUDAula();
    private Map<String, Integer> aulasMonitores;

    public void initialize() {
        // Mapeo de aulas con la cantidad de monitores
        CRUDAula cruda = new CRUDAula();
        aulasMonitores = cruda.mapEquiposPorAula();

        // Agregar dinámicamente opciones al menú
        for (String aula : aulasMonitores.keySet()) {
            MenuItem item = new MenuItem(aula);
            item.setOnAction(event -> actualizarMonitores(aula));
            menuButtonAulas.getItems().add(item);
        }
    }

    private void actualizarMonitores(String aulaSeleccionada) {
        // Limpiar el grid antes de agregar nuevos monitores
        gridPaneMonitores.getChildren().clear();

        // Validar aula seleccionada y cantidad de monitores
        if (aulaSeleccionada == null || aulaSeleccionada.isEmpty()) {
            System.err.println("El aula seleccionada no es válida.");
            return;
        }

        int cantidadMonitores = aulasMonitores.getOrDefault(aulaSeleccionada, 0);
        if (cantidadMonitores < 0) {
            System.err.println("La cantidad de monitores no puede ser negativa.");
            return;
        }

        // Cargar la imagen del monitor con manejo de excepciones
        Image imagenMonitor;
        try (InputStream imagenStream = getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/imagenmonitor.png")) {
            if (imagenStream == null) {
                throw new IOException("No se encontró la imagen: /Imagenes/imagenmonitor.png");
            }
            imagenMonitor = new Image(imagenStream);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen del monitor: " + e.getMessage());
            return;
        }

        // Dimensiones dinámicas de la cuadrícula
        int columnas = 5; // Configuración inicial
        int filas = 3;    // Configuración inicial

        // Posicionar los monitores en la cuadrícula
        for (int i = 0; i < cantidadMonitores; i++) {
            int fila = i / columnas;
            int columna = i % columnas;

            if (fila >= filas) break; // Evitar desbordamiento de filas

            ImageView monitor = new ImageView(imagenMonitor);
            monitor.setFitWidth(80);
            monitor.setFitHeight(50);
            monitor.getStyleClass().add("estilo-monitor"); // Clase CSS opcional para personalización

            gridPaneMonitores.add(monitor, columna, fila);
        }

        // Cambiar el texto del MenuButton a la opción seleccionada
        menuButtonAulas.setText(aulaSeleccionada);
    }
}

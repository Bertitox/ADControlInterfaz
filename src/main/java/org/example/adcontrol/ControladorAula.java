package org.example.adcontrol;

import BBDD.DAO.CRUDAula;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * @author Daniel y Alberto
 * @version 1.0
 * Clase controladora de la interfaz para las aulas.
 */
public class ControladorAula {

    @FXML
    private MenuButton menuButtonAulas = new MenuButton();

    @FXML
    private GridPane gridPaneMonitores;

    CRUDAula cruda = new CRUDAula(); //Llamada al CRUD que conecta con la tabla Aula de la BBDD
    private Map<String, Integer> aulasMonitores;

    /**
     * Método que inicializa los elementos del controlador que se usarán.
     */
    public void initialize() {
        //Mapeo de aulas junto con la cantidad de monitores
        aulasMonitores = cruda.mapEquiposPorAula();

        //Agregamos dinámicamente opciones (menuItem) al menú (MenuButton)
        for (String aula : aulasMonitores.keySet()) {
            MenuItem item = new MenuItem(aula);
            item.setOnAction(event -> actualizarMonitores(aula));
            menuButtonAulas.getItems().add(item);
        }
    }

    /**
     * Método que se encarga de actualizar, cargar y mostrar las imágenes de los monitores por aula y el texto del comboBox correspondiente a cada aula
     * @param aulaSeleccionada Recibe como parámetro el aula seleccionada, y la cambia en el combobox
     */
    private void actualizarMonitores(String aulaSeleccionada) {
        //Limpiar el grid antes de agregar nuevos monitores
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

        //Cargamos la imagen del monitor y la manejamos
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

        //Dimensiones dinámicas del contenedor
        int columnas = 5; //Configuración total de las columnas
        int filas = 3;    //Configuración total de las filas

        gridPaneMonitores.setHgap(10); //Espaciado horizontal entre columnas
        gridPaneMonitores.setVgap(100); //Espaciado vertical entre filas

        for (int i = 0; i < cantidadMonitores; i++) {
            int fila = i / columnas;
            int columna = i % columnas;

            if (fila >= filas) break;

            ImageView monitor = new ImageView(imagenMonitor);
            monitor.setFitWidth(150);
            monitor.setFitHeight(150);

            GridPane.setHalignment(monitor, HPos.CENTER);
            GridPane.setValignment(monitor, VPos.CENTER);

            GridPane.setMargin(monitor, new Insets(50, 0, 0, 0)); //Espaciado extra entre los elementos
            gridPaneMonitores.add(monitor, columna, fila);
        }

        //Cambiamos el texto del MenuButton a la opción seleccionada (Aula seleccionada)
        menuButtonAulas.setText(aulaSeleccionada);
    }
}

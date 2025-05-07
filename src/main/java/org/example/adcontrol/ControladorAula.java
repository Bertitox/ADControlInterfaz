package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    CRUDAula_Equipo cruda = new CRUDAula_Equipo(); //Llamada al CRUD que conecta con la tabla Aula de la BBDD
    CRUDIncidencia crudIncidencia = new CRUDIncidencia();
    private Map<String, Integer> aulasMonitores;

    String aulaActual;

    @FXML
    AnchorPane panelLargo;

    /**
     * Método que inicializa los elementos del controlador que se usarán.
     */
    public void initialize() {
        //Mapeo de aulas junto con la cantidad de monitores
        aulasMonitores = cruda.mapEquiposPorAula();

        //Agregamos dinámicamente opciones (menuItem) al menú (MenuButton)
        for (String aula : aulasMonitores.keySet()) {
            MenuItem item = new MenuItem(aula);
            item.setOnAction(event -> {
                actualizarMonitores(aula);
                setAulaActual(aula);
            });
            menuButtonAulas.getItems().add(item);
        }
    }

    /**
     * Método que se encarga de actualizar, cargar y mostrar las imágenes de los monitores por aula y el texto del comboBox correspondiente a cada aula
     * @param aulaSeleccionada Recibe como parámetro el aula seleccionada, y la cambia en el combobox
     */
    private void actualizarMonitores(String aulaSeleccionada) {
        gridPaneMonitores.getChildren().clear();

        if (aulaSeleccionada == null || aulaSeleccionada.isEmpty()) {
            System.err.println("El aula seleccionada no es válida.");
            return;
        }

        int cantidadMonitores = aulasMonitores.getOrDefault(aulaSeleccionada, 0);

        if (cantidadMonitores < 0) {
            System.err.println("La cantidad de monitores no puede ser negativa.");
            return;
        }

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

        int columnas = 5;
        int filas = 3;

        gridPaneMonitores.setHgap(10);
        gridPaneMonitores.setVgap(100);

        for (int i = 0; i < cantidadMonitores; i++) {
            int fila = i / columnas;
            int columna = i % columnas;

            if (fila >= filas) break;

            // 📌 Imagen del monitor
            ImageView monitor = new ImageView(imagenMonitor);
            monitor.setFitWidth(150);
            monitor.setFitHeight(150);

            // 📌 IP ficticia de ejemplo (sustituye esto por un getIP o similar si tienes la info real)
            //String ip = "192.168.1." + (i + 1);
            String ip = new CRUDAula_Equipo().getEquipoPorIndiceYAula(aulaSeleccionada, i).getIp();

            // 📌 Label de la IP
            Label ipLabel = new Label(ip);
            ipLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; "
                    + "-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 3px 5px; "
                    + "-fx-background-radius: 5px;");
            ipLabel.setMouseTransparent(true);  // Para que no bloquee clics ni tooltips

            // 📌 StackPane para imagen + IP superpuesta
            StackPane stack = new StackPane();
            stack.getChildren().addAll(monitor, ipLabel);
            StackPane.setAlignment(ipLabel, Pos.CENTER);

            // 📌 Tooltip personalizado
            String nombreEquipo = cruda.getEquipoPorIndiceYAula(aulaSeleccionada, i).getNombre();

            //int numIncidencias = 0; // Sustituye con llamada a tu método real de incidencias
            int numIncidencias = crudIncidencia.getNumIncidenciasEquipo(cruda.getEquipoPorIndiceYAula(aulaSeleccionada, i).getId());
            Tooltip tooltip = new Tooltip("Nombre: " + nombreEquipo + "\nIncidencias: " + numIncidencias);
            Tooltip.install(stack, tooltip);

            // 📌 Añadir al GridPane
            GridPane.setHalignment(stack, HPos.CENTER);
            GridPane.setValignment(stack, VPos.CENTER);
            GridPane.setMargin(stack, new Insets(50, 0, 0, 0));

            gridPaneMonitores.add(stack, columna, fila);
        }

        menuButtonAulas.setText(aulaSeleccionada);
    }

    public String getAulaActual() {
        return aulaActual;
    }

    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
        actualizarMonitores(aulaActual);
    }

    @FXML
    void volver(ActionEvent event) throws IOException, AulaNotFoundException {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelAula.fxml"));
                Parent root = fxmlLoader.load();

                ControladorVistaPanelAula controladorVista = fxmlLoader.getController();

                // Esperar un poco a que cargue panelAula.fxml (por el Platform.runLater interno)
                // Alternativa simple: usar otro runLater anidado
                Platform.runLater(() -> {
                    ControladorPanelAula controladorPanel = controladorVista.getControladorPanelAula();
                    if (controladorPanel != null) {
                        try {
                            controladorPanel.ponerClase(aulaActual);
                        } catch (AulaNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.err.println("El controlador del panel aula no está disponible todavía.");
                    }
                });

                panelLargo.getChildren().clear();
                panelLargo.getChildren().add(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

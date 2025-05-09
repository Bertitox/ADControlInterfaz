package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.DAO.CRUDInfoSistema;
import BBDD.DTO.InformacionSistema;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel y Alberto
 * @version 1.5
 * Clase controladora de la interfaz para las aulas.
 */
public class ControladorAula {

    //Elementos FXML que se usarán en el controlador.
    @FXML
    private MenuButton menuButtonAulas = new MenuButton();

    @FXML
    private GridPane gridPaneMonitores;

    //Clases de acceso a BBDD
    CRUDAula_Equipo cruda = new CRUDAula_Equipo(); //Llamada al CRUD que conecta con la tabla Aula de la BBDD
    CRUDIncidencia crudIncidencia = new CRUDIncidencia();

    //Mapa clave-valor con las aulas y el número de equipos
    private Map<String, Integer> aulasMonitores;

    //Aula actual
    String aulaActual;

    //Panel padre que contiene todos los elementos de la clase
    @FXML
    AnchorPane panelLargo;

    //Clase
    private ScheduledExecutorService temporizador;

    CRUDInfoSistema crudInfoSistema = new CRUDInfoSistema();


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
        InfoInit infoInit = InfoInit.getInstance();
        Integer intervalo = infoInit.getIntervalo();
        System.out.println("Intervalo de: " + intervalo);
        //Programar actualización automática cada 2 minutos solo de los indicadores de estado
        temporizador = Executors.newSingleThreadScheduledExecutor();
        temporizador.scheduleAtFixedRate(() -> {
            if (aulaActual != null) {
                actualizarEstadoMonitores();
            }
        }, intervalo, intervalo, TimeUnit.SECONDS);
    }

    /**
     * Método que actualiza únicamente el color de los indicadores de estado de los monitores, sin refrescar imágenes ni grid.
     */
    private void actualizarEstadoMonitores() {
        if (aulaActual == null) return;

        for (javafx.scene.Node node : gridPaneMonitores.getChildren()) {
            if (node instanceof StackPane stack) {
                String ip = null;
                javafx.scene.shape.Circle estadoCircle = null;

                for (javafx.scene.Node child : stack.getChildren()) {
                    if (child instanceof Label label) {
                        ip = label.getText();
                    } else if (child instanceof javafx.scene.shape.Circle circle) {
                        estadoCircle = circle;
                    }
                }

                if (ip != null && estadoCircle != null) {
                    String finalIp = ip;
                    javafx.scene.shape.Circle finalEstadoCircle = estadoCircle;

                    new Thread(() -> {
                        Color estado = comprobarEstadoMonitor(finalIp);
                        Platform.runLater(() -> finalEstadoCircle.setFill(estado));
                    }).start();
                }
            }
        }
    }

    /**
     * Método que se encarga de actualizar, cargar y mostrar las imágenes de los monitores por aula y el texto del comboBox correspondiente a cada aula
     *
     * @param aulaSeleccionada Recibe como parámetro el aula seleccionada, y la cambia en el combobox
     */
    public void actualizarMonitores(String aulaSeleccionada) {
        gridPaneMonitores.getChildren().clear();

        //Validaciónes previas
        if (aulaSeleccionada == null || aulaSeleccionada.isEmpty()) {
            System.err.println("El aula seleccionada no es válida.");
            return;
        }

        int cantidadMonitores = aulasMonitores.getOrDefault(aulaSeleccionada, 0);
        System.out.println(cantidadMonitores);

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

        for (int i = 0; i < cantidadMonitores; i++) { //Calculamos cuantos monitores hay y les aplicamos estilos y funciones
            int fila = i / columnas;
            int columna = i % columnas;
            if (fila >= filas) break;

            ImageView monitor = new ImageView(imagenMonitor);
            monitor.setFitWidth(150);
            monitor.setFitHeight(150);

            String ip = cruda.getEquipoPorIndiceYAula(aulaSeleccionada, i).getIp();

            //Damos color al círculo de estado del minitor y configuramos el label de la IP
            Label ipLabel = new Label(ip);
            ipLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; "
                    + "-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 3px 5px; "
                    + "-fx-background-radius: 5px;");
            ipLabel.setMouseTransparent(true);

            javafx.scene.shape.Circle estadoCircle = new javafx.scene.shape.Circle(8);
            estadoCircle.setStroke(Color.BLACK);
            estadoCircle.setStrokeWidth(1.5);
            estadoCircle.setFill(Color.GREY);

            //Hacemos ping, para ello usaremos un hilo separado del principal
            new Thread(() -> {
                Color estado = comprobarEstadoMonitor(ip);
                Platform.runLater(() -> estadoCircle.setFill(estado));
            }).start();

            StackPane stack = new StackPane();
            stack.getChildren().addAll(monitor, ipLabel, estadoCircle);
            StackPane.setAlignment(ipLabel, Pos.CENTER);
            StackPane.setAlignment(estadoCircle, Pos.TOP_RIGHT);
            StackPane.setMargin(estadoCircle, new Insets(5, 5, 0, 0));

            String nombreEquipo = cruda.getEquipoPorIndiceYAula(aulaSeleccionada, i).getNombre();

            ContextMenu menu = new ContextMenu();//Creamos el menu que aparecerá al dar clic derecho en el dispositivo.

            //Configuramos los MenuItem del contextMenu
            MenuItem modificarEquipo = new MenuItem("Modificar equipo");
            MenuItem eliminarEquipo = new MenuItem("Eliminar equipo");
            MenuItem apagarEquipo = new MenuItem("Apagar equipo");

            //Acción del menuItem modificarEquipo que sirve para abrir una nueva pestaña para modificar el equipo.
            modificarEquipo.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));
                    Parent root = loader.load();
                    ControladorFormularioEquipo controladorB = loader.getController();
                    InformacionSistema equipoSeleccionado = crudInfoSistema.getByNombre(nombreEquipo);
                    controladorB.setAulaActual(aulaActual);
                    controladorB.setCampoCPU(equipoSeleccionado.getProcesador());
                    controladorB.setCampoArquitectura(equipoSeleccionado.getArquitectura());
                    controladorB.setCampoIP(equipoSeleccionado.getIp());
                    controladorB.setCampoNombre(equipoSeleccionado.getNombre());
                    controladorB.setCampoKernel(equipoSeleccionado.getReleasee());
                    controladorB.setCampoMAC(equipoSeleccionado.getMac());
                    controladorB.setCampoSO(equipoSeleccionado.getSo());
                    controladorB.setCampoMemDisp(equipoSeleccionado.getMemDisp().toString());
                    controladorB.setCampoMemTotal(equipoSeleccionado.getMemTotal().toString());
                    controladorB.setCampoVersion(equipoSeleccionado.getVersion());
                    controladorB.setCampoUsoCPU(equipoSeleccionado.getUsoCpu());
                    controladorB.setCampoNodo(equipoSeleccionado.getNombreNodo());
                    controladorB.setModificar(true);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                    actualizarMonitores(aulaActual);  //Actualizamos el GridPane al cerrarse

                } catch (IOException e) {
                    System.out.println("Error al cambiar la pantalla al formulario para crear el equipo");
                }
            });

            //MenuItem que elimina el equipo
            eliminarEquipo.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que desea eliminar el equipo?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    CRUDInfoSistema crudInfoSistema = new CRUDInfoSistema();
                    InformacionSistema info = crudInfoSistema.getByNombre(nombreEquipo);
                    crudInfoSistema.delete(info);
                    actualizarMonitores(aulaActual);
                }
            });

            //MenuItem que apaga el equipo
            apagarEquipo.setOnAction(event -> {
                ControlSSH controlSSH = new ControlSSH();
                InformacionSistema equipoSeleccionado = crudInfoSistema.getByNombre(nombreEquipo);
                try {
                    controlSSH.conectarYApagar(equipoSeleccionado);
                } catch (IOException e) {
                    System.out.println("Error al intentar apagar");
                    throw new RuntimeException(e);
                }
            });

            menu.getItems().addAll(modificarEquipo, eliminarEquipo, apagarEquipo);
            stack.setOnContextMenuRequested(e -> menu.show(stack, e.getScreenX(), e.getScreenY()));

            int numIncidencias = crudIncidencia.getNumIncidenciasEquipo(cruda.getEquipoPorIndiceYAula(aulaSeleccionada, i).getId());
            Tooltip tooltip = new Tooltip("Nombre: " + nombreEquipo + "\nIncidencias: " + numIncidencias);
            Tooltip.install(stack, tooltip);

            GridPane.setHalignment(stack, HPos.CENTER);
            GridPane.setValignment(stack, VPos.CENTER);
            GridPane.setMargin(stack, new Insets(50, 0, 0, 0));

            gridPaneMonitores.add(stack, columna, fila);
        }

        menuButtonAulas.setText(aulaSeleccionada);
    }

    /**
     * Método que se encarga de cambiar el aula actual.
     *
     * @param aulaActual String que representa la referencia al aula actual.
     */
    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
        actualizarMonitores(aulaActual);
    }

    /**
     * Método que se encarga de realizar la acción de volver atrás.
     *
     * @param event Evento del tipo ActionEvent que espera el método.
     * @throws IOException           Error de entrada/salida que pueda experimentar el método.
     * @throws AulaNotFoundException Excepción si no encuentra el aula.
     */
    @FXML
    void volver(ActionEvent event) throws IOException, AulaNotFoundException {
        //Detenemos la tarea programada
        if (temporizador != null && !temporizador.isShutdown()) {
            temporizador.shutdown();
        }

        Platform.runLater(() -> {
            try {
                //Cargamos la vista a la que queremos volver, en este caso PanelAula.
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelAula.fxml"));
                Parent root = fxmlLoader.load();

                ControladorVistaPanelAula controladorVista = fxmlLoader.getController();//Cargamos la vista.

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

    /**
     * Método que comprueba el estado de un monitor a partir de su IP.
     *
     * @param ip Dirección IP del monitor
     * @return Color que representa el estado (VERDE = activo, ROJO = inactivo, GRIS = error)
     */
    private Color comprobarEstadoMonitor(String ip) {
        try {
            System.out.println("Ping a " + ip);
            InetAddress inet = InetAddress.getByName(ip);
            boolean reachable = inet.isReachable(3000);//Tiempo que tarda esperando a que responda el ping, máximo de 3 segundos.
            if (reachable) { //Dependiendo de si lo acepta o no se pone el rojo o en verde el círculo.
                return javafx.scene.paint.Color.LIMEGREEN;//Circulo en verde.
            } else {
                return javafx.scene.paint.Color.RED;//Círculo en rojo.
            }
        } catch (Exception e) {
            return javafx.scene.paint.Color.GREY;//Círculo en gris.
        }
    }
}
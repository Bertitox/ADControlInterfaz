package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.DTO.Incidencia;
import BBDD.DTO.InformacionSistema;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.chart.NumberAxis;
import javafx.util.Duration;
import javafx.animation.KeyFrame;


public class ControladorPanelAula extends Controlador {

    @FXML
    private Label incidenciaRecEq1;

    @FXML
    private Label incidenciaRecEq2;

    @FXML
    private Label incidenciaRecEq3;

    @FXML
    private ImageView imgIncidencia1;

    @FXML
    private ImageView imgIncidencia2;

    @FXML
    private ImageView imgIncidencia3;

    @FXML
    private Label incidencia1;

    @FXML
    private Label incidencia2;

    @FXML
    private Label incidencia3;

    @FXML
    Label labelNumEquiposAula;

    @FXML
    Label labelNumIncidenciasAula;

    @FXML
    ProgressBar barraEstadoAula;

    @FXML
    Label labelAula;

    @FXML
    Label fechaActual;

    @FXML
    Label fechaUltMod;

    @FXML
    Label horaUltMod;

    @FXML
    private Button botonEQ1;

    @FXML
    private Button botonEQ2;

    @FXML
    private Button botonEQ3;

    @FXML
    private Label nombreEQ1;

    @FXML
    private Label nombreEQ2;

    @FXML
    private Label nombreEQ3;

    @FXML
    private ImageView imgMonitor1;

    @FXML
    private ImageView imgMonitor2;

    @FXML
    private ImageView imgMonitor3;

    @FXML
    private Label ip1;

    @FXML
    private Label ip2;

    @FXML
    private Label ip3;

    @FXML
    private ImageView caraImagen;

    @FXML
    Label labelPorcentajeProgreso;

    @FXML
    private Pane panelLargo;

    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private int tiempo = 0;
    private int incidenciasActuales = -1;
    private Timeline timeline;

    @FXML
    private LineChart<Number, Number> graficoIncidenciasAula;

    ControladorMapa cm = new ControladorMapa();

    //Clases gestoras de la BBDD
    CRUDAula_Equipo AE = new CRUDAula_Equipo();
    CRUDIncidencia I = new CRUDIncidencia();

    @FXML
    public void initialize() throws AulaNotFoundException {
        //labelAula.setText(cm.getAulaSeleccionada());
        //labelNumEquiposAula.setText(AE.numEquiposXAula(labelAula.getText()));
        //labelNumIncidenciasAula.setText(" "+I.numIncidenciasAula(labelAula.getText()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = LocalDate.now().format(formatter);
        fechaActual.setText(fechaFormateada);
        startChartUpdater();
        //cargarBarra(); //Método que se encarga de actualizar la barra de progreso del estado general del aula
    }

    public void rellenarAdministrarEquipos() {
        List<InformacionSistema> equipos = AE.get3EquiposXAula(labelAula.getText());
        // Asignamos valores según posición

        // Opcional: si quieres limpiar los huecos restantes cuando hay menos de 3
        if (equipos.size() < 3) {
            if (equipos.size() < 1) {
                ip1.setText("");
                imgMonitor1.setOpacity(0.0);
                nombreEQ1.setText("");
                botonEQ1.setOpacity(0.0);
            }
            if (equipos.size() < 2) {
                ip2.setText("");
                imgMonitor2.setOpacity(0.0);
                nombreEQ2.setText("");
                botonEQ2.setOpacity(0.0);
            }
            if (equipos.size() < 3) {
                ip3.setText("");
                imgMonitor3.setOpacity(0.0);
                nombreEQ3.setText("");
                botonEQ3.setOpacity(0.0);
            }
        }

        for (int i = 0; i < equipos.size(); i++) {
            InformacionSistema equipo = equipos.get(i);
            switch (i) {
                case 0:
                    ip1.setText(equipo.getIp()); // Asumiendo que tienes getIp()
                    imgMonitor1.setOpacity(1.0);
                    nombreEQ1.setText(equipo.getNombre());
                    botonEQ1.setOpacity(1.0);
                    break;
                case 1:
                    ip2.setText(equipo.getIp());
                    imgMonitor2.setOpacity(1.0);
                    nombreEQ2.setText(equipo.getNombre());
                    botonEQ2.setOpacity(1.0);
                    break;
                case 2:
                    ip3.setText(equipo.getIp());
                    imgMonitor3.setOpacity(1.0);
                    nombreEQ3.setText(equipo.getNombre());
                    botonEQ3.setOpacity(1.0);
                    break;
            }
        }
    }


    public void rellenarIncidenciasRecientes() throws AulaNotFoundException {
        List<Incidencia> incidencias = I.getUltimasTresIncidenciasAula(labelAula.getText());  // Simulado
        System.out.println(incidencias.size());
        // Primero limpiar huecos restantes
        if (incidencias.size() < 3) {
            if (incidencias.size() < 1) {
                imgIncidencia1.setOpacity(0.0);
                incidencia1.setText("");
                incidenciaRecEq1.setText("");
            }
            if (incidencias.size() < 2) {
                imgIncidencia2.setOpacity(0.0);
                incidencia2.setText("");
                incidenciaRecEq2.setText("");
            }
            if (incidencias.size() < 3) {
                imgIncidencia3.setOpacity(0.0);
                incidencia3.setText("");
                incidenciaRecEq3.setText("");
            }
        }

        for (int i = 0; i < incidencias.size(); i++) {
            Incidencia inc = incidencias.get(i);
            ImageView imgView;
            Label lblIncidencia;
            Label nombreEqIncidencia;
            switch (i) {
                case 0:
                    imgView = imgIncidencia1;
                    lblIncidencia = incidencia1;
                    nombreEqIncidencia = incidenciaRecEq1;
                    break;
                case 1:
                    imgView = imgIncidencia2;
                    lblIncidencia = incidencia2;
                    nombreEqIncidencia = incidenciaRecEq2;
                    break;
                case 2:
                    imgView = imgIncidencia3;
                    lblIncidencia = incidencia3;
                    nombreEqIncidencia = incidenciaRecEq3;
                    break;
                default:
                    continue;
            }

            imgView.setOpacity(1.0);
            lblIncidencia.setText(inc.getDescripcion());
            nombreEqIncidencia.setText(inc.getIdInformacionSistema().getNombre());

            if (inc.getCodigoError().getCodigoError().equals("E001")) {
                imgView.setImage(new Image(getClass().getResource("/org/example/adcontrol/Imagenes/errorRed.png").toExternalForm()));
            } else {
                imgView.setImage(new Image(getClass().getResource("/org/example/adcontrol/Imagenes/cpuError.png").toExternalForm()));
            }
        }
    }


    public void initializeLineChart() {
        if (graficoIncidenciasAula.getXAxis() instanceof NumberAxis &&
                graficoIncidenciasAula.getYAxis() instanceof NumberAxis) {

            NumberAxis xAxis = (NumberAxis) graficoIncidenciasAula.getXAxis();
            NumberAxis yAxis = (NumberAxis) graficoIncidenciasAula.getYAxis();

            //Etiquetas de los ejes
            xAxis.setLabel("Día del Mes");
            yAxis.setLabel("Incidencias");

            //Obtener datos
            int diaActual = LocalDate.now().getDayOfMonth();
            int numIncidencias;

            try {
                numIncidencias = Integer.parseInt(labelNumIncidenciasAula.getText().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: El valor del label no es un número válido.");
                return;
            }

            //Eje X
            xAxis.setAutoRanging(false);
            xAxis.setLowerBound(0);
            xAxis.setUpperBound(Math.max(1, diaActual) +1);
            xAxis.setTickUnit(1);

            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(0);
            yAxis.setUpperBound(numIncidencias+2);
            yAxis.setTickUnit(1);

            //Serie del punto 0,0 y punto incidencias, días
            XYChart.Series<Number, Number> serie = new XYChart.Series<>();
            serie.setName("Incidencias");

            serie.getData().add(new XYChart.Data<>(0, 0)); // Creo el primer punto
            serie.getData().add(new XYChart.Data<>(diaActual, numIncidencias));

            //Limpiar y agregar datos
            graficoIncidenciasAula.getData().clear();
            graficoIncidenciasAula.getData().add(serie);
            graficoIncidenciasAula.setTitle("Histórico de Incidencias");
        } else {
            System.out.println("Error: El gráfico no tiene ejes numéricos.");
        }
    }

    public void startChartUpdater() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    try {
                        actualizarGraficoIncidencias();
                    } catch (AulaNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void actualizarGraficoIncidencias() throws AulaNotFoundException {
        int nuevasIncidencias = I.getNumIncidenciasAulas(labelAula.getText());
        if (nuevasIncidencias != incidenciasActuales) {
            tiempo++;
            incidenciasActuales = nuevasIncidencias;
            series.getData().add(new XYChart.Data<>(tiempo, incidenciasActuales));
        }
    }

    public void ponerClase(String nombreAula) throws AulaNotFoundException {
        labelAula.setText(nombreAula);

        labelNumEquiposAula.setText(AE.numEquiposXAula(nombreAula));
        labelNumIncidenciasAula.setText(String.valueOf(I.numIncidenciasAula(nombreAula)));
        //CUANDO SE PONGA EL VALOR DE LA INCIDENCIA SE ACTUALIZA EL GRÁFICO
        initializeLineChart();
        if (I.getUltFechaMod(labelAula.getText()) == null) {
            fechaUltMod.setText("Sin datos");
            horaUltMod.setText("Sin datos");

        } else {
            DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatoES = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            //Obtenemos la fecha en formato yyyy-MM-dd desde la base de datos
            String fechaBD = I.getUltFechaMod(labelAula.getText());

            if (fechaBD != null) {
                LocalDate fecha = LocalDate.parse(fechaBD, formatoBD);
                fechaUltMod.setText(fecha.format(formatoES));
            } else {
                fechaUltMod.setText("Sin registro");
            }
            horaUltMod.setText(I.getUltHoraMod(labelAula.getText()));
        }

        cargarBarra();
        rellenarAdministrarEquipos();
        rellenarIncidenciasRecientes();
    }

    @FXML
    void vistaEquipos(ActionEvent event) throws IOException {
        if (AE.numEquiposXAula(labelAula.getText()).equals("0")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No hay aulas en esta clase, debes de agregar un equipo al menos.", ButtonType.OK);
            alert.setTitle("No hay equipos");
            alert.setHeaderText(null); //Elimina el encabezado
            alert.showAndWait();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaAula.fxml"));
            Parent root = fxmlLoader.load();
            ControladorAula controladorAula = fxmlLoader.getController();
            controladorAula.setAulaActual(labelAula.getText());

            panelLargo.getChildren().clear();
            panelLargo.getChildren().add(root);
        }
    }

    @FXML
    void refrescar(MouseEvent event) throws AulaNotFoundException {
        labelNumEquiposAula.setText(AE.numEquiposXAula(labelAula.getText()));
        labelNumIncidenciasAula.setText(" " + I.numIncidenciasAula(labelAula.getText()));
        cargarBarra();
        rellenarAdministrarEquipos();
        initializeLineChart();
        rellenarIncidenciasRecientes();
    }


    void refrescar() throws AulaNotFoundException {
        labelNumEquiposAula.setText(AE.numEquiposXAula(labelAula.getText()));
        labelNumIncidenciasAula.setText(" " + I.numIncidenciasAula(labelAula.getText()));
        cargarBarra();
        rellenarAdministrarEquipos();
        rellenarIncidenciasRecientes();
    }

    void cargarBarra() throws AulaNotFoundException {
        //Fórmula para calcular el estado general del aula --> % Estado General = (Num Equipos Incidencias / Num Total Equipos) * 100
        //Si no hay equipos ponemos el progreso del progress bar a 0
        int totalEquipos = Integer.parseInt(labelNumEquiposAula.getText());
        Double calculo = 0.0;

        if (totalEquipos == 0) {
            barraEstadoAula.setProgress(0);
            labelPorcentajeProgreso.setText("0 %");
        } else {
            calculo = (double) I.getEquipoIncidenciasXAula(labelAula.getText()) / Integer.parseInt(labelNumEquiposAula.getText()) * 100;
            double estadoRedondeado = Math.round(calculo * 10.0) / 10.0;
            labelPorcentajeProgreso.setText(estadoRedondeado + " %");
            barraEstadoAula.setProgress((double) I.getEquipoIncidenciasXAula(labelAula.getText()) / Integer.parseInt(labelNumEquiposAula.getText()));
        }
        if (calculo <= 25.0) {
            caraImagen.setImage(new Image(getClass().getResource("/org/example/adcontrol/Imagenes/contento.png").toExternalForm()));
        } else if (calculo > 25.0 && calculo < 75.0) {
            caraImagen.setImage(new Image(getClass().getResource("/org/example/adcontrol/Imagenes/medio.png").toExternalForm()));
        } else {
            caraImagen.setImage(new Image(getClass().getResource("/org/example/adcontrol/Imagenes/triste.png").toExternalForm()));
        }
    }


    @FXML
    void agregarEquipo(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/OpcionAutomaticoManual.fxml"));
            Parent root = loader.load();

            //Obtener el controlador cargado desde el FXML
            ControladorAutomaticoManual controladorAutomaticoManual = loader.getController();

            //Pasarle el dato al controlador
            controladorAutomaticoManual.setAulaActual(labelAula.getText());

            Stage stage = new Stage();
            stage.setTitle("Modo de creación");

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.setOnCloseRequest(e -> {
                try {
                    refrescar(null); // Llamamos a refrescar al cerrar la ventana
                } catch (AulaNotFoundException ex) {
                    ex.printStackTrace(); // Manejo de errores
                }
            });

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void conectarSSH(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/vistaSSH.fxml"));
            Parent root = loader.load();

            ControlSSH controladorB = loader.getController();
            controladorB.setClaseActual(labelAula.getText());

            Stage stage = new Stage();
            Scene scene = new Scene(root);  //Aquí la guardas en una variable

            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void conectarIncidencias(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaPanelMonitor.fxml"));
            Parent root = fxmlLoader.load();

            ControladorVistaPanelMonitor controladorVistaPanelMonitor = fxmlLoader.getController();
            //controladorVistaPanelMonitor.setAulaActual(labelAula.getText());

            panelLargo.getChildren().clear();
            panelLargo.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

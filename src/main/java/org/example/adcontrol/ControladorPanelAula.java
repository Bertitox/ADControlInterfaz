package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.chart.NumberAxis;
import javafx.util.Duration;
import javafx.animation.KeyFrame;


public class ControladorPanelAula extends Controlador {

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
        initializeLineChart();
        startChartUpdater();
        cargarBarra(); //Método que se encarga de actualizar la barra de progreso del estado general del aula

    }


    public void initializeLineChart() {
        if (graficoIncidenciasAula.getXAxis() instanceof NumberAxis
                && graficoIncidenciasAula.getYAxis() instanceof NumberAxis) {

            NumberAxis xAxis = (NumberAxis) graficoIncidenciasAula.getXAxis();
            NumberAxis yAxis = (NumberAxis) graficoIncidenciasAula.getYAxis();

            xAxis.setLabel("Tiempo");
            yAxis.setLabel("Incidencias");

            yAxis.setTickUnit(1);
            yAxis.setMinorTickCount(0);
            yAxis.setTickLabelFormatter(new StringConverter<Number>() {
                @Override
                public String toString(Number value) {
                    return String.valueOf(value.intValue()); //Solo enteros
                }

                @Override
                public Number fromString(String string) {
                    return Integer.parseInt(string);
                }
            });

            graficoIncidenciasAula.setTitle("Histórico de Incidencias");
            series.setName("Histórico de Incidencias");

            graficoIncidenciasAula.getData().clear();
            series.getData().add(new XYChart.Data<>(0, 0)); //Punto inicial a 0
            graficoIncidenciasAula.getData().add(series);
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
        labelNumIncidenciasAula.setText(" " + I.numIncidenciasAula(nombreAula));
        if (I.getUltFechaMod(labelAula.getText()) == null) {
            fechaUltMod.setText("00000000");
            horaUltMod.setText("000000");

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
    }

    @FXML
    void vistaEquipos(MouseEvent event) throws IOException {
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
    }

    void cargarBarra() throws AulaNotFoundException {
        //Fórmula para calcular el estado general del aula --> % Estado General = (Num Equipos Incidencias / Num Total Equipos) * 100
        labelPorcentajeProgreso.setText(String.valueOf((double) I.getEquipoIncidenciasXAula(labelAula.getText()) / Integer.parseInt(labelNumEquiposAula.getText()) * 100) + " %");
        barraEstadoAula.setProgress((((double) I.getEquipoIncidenciasXAula(labelAula.getText()) / Integer.parseInt(labelNumEquiposAula.getText())) * 100) / 100);
    }

    @FXML
    void agregarEquipo(ActionEvent event) {
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
            Scene scene = new Scene(root);  // Aquí la guardas en una variable

            // Ahora sí puedes añadirle el CSS externo
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

            panelLargo.getChildren().clear();
            panelLargo.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

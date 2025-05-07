package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDIncidencia;
import BBDD.DTO.InformacionSistema;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        initializeLineChart();
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
            series.setName("Incidencias");

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

        cargarBarra();
        rellenarAdministrarEquipos();
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
        rellenarAdministrarEquipos();
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
            barraEstadoAula.setProgress((((double) I.getEquipoIncidenciasXAula(labelAula.getText()) / Integer.parseInt(labelNumEquiposAula.getText())) * 100) / 100);
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

            ControladorVistaPanelMonitor controladorVistaPanelMonitor = fxmlLoader.getController();
            //controladorVistaPanelMonitor.setAulaActual(labelAula.getText());

            panelLargo.getChildren().clear();
            panelLargo.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

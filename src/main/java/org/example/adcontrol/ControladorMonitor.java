package org.example.adcontrol;
import BBDD.DAO.*;
import BBDD.DTO.*;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de usuario y la lógica de la aplicación.
 * @author Daniel y Alberto
 * @version 1.5
 */
public class ControladorMonitor extends Controlador{

    @FXML
    TableView<Incidencia> tableIncidencias;

    @FXML
    TextField textIncidencias;

    @FXML
    PieChart graficoIncidencias;

    @FXML
    Button actualizar;

    @FXML
    Button actualizar1;

    @FXML
    TableView<Map.Entry<String, String>> tableViewErrores;

    //Elementos para la creación de incidencias
    @FXML
    Button crearIncidencia;

    @FXML
    MenuButton MBaula;

    @FXML
    MenuButton MBerror;

    @FXML
    MenuButton MBequipo;

    @FXML
    TextArea labelDescripcion;

    //Elementos a traducir
    private ResourceBundle bundle;

    @FXML
    private Label textIncidencia;

    @FXML
    private Label tituloIncidencias;

    //Acceso a BBDD
    CRUDAulas aulas = new CRUDAulas();
    CRUDInfoSistema infoSistema = new CRUDInfoSistema();
    GestionErrores errores = new GestionErrores();
    CRUDIncidencia incidencia = new CRUDIncidencia();
    CRUDAula_Equipo aulaEquipo = new CRUDAula_Equipo();


    /**
     * Inicializa los elementos de la interfaz y configura los botones de idioma.
     */
    @FXML
    public void initialize(){
        refrescarIdioma();
        mostrarErroresTableView();
        rellenarMenuButtonAula();
        //rellenarMenuButtonEquipo();
        rellenarMenuButtonErrores();
    }

    /**
     * Método que carga el idioma y se encarga de la traducción de los elementos.
     * @param locale Idioma que recibe el método y con el que actúa.
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Debug

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            textIncidencia.setText(bundle.getString("textIncidencia"));
            tituloIncidencias.setText(bundle.getString("tituloIncidencias"));
            actualizar.setText(bundle.getString("boton.text"));
            actualizar1.setText(bundle.getString("boton2.text"));

            System.out.println("Idioma cargado exitosamente.");//Debug

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Muestra las incidencias en el TableView.
     *
     * @throws AulaNotFoundException Si el aula no se encuentra.
     */
    public void mostrarIncidenciasTableView() throws AulaNotFoundException {
        CRUDIncidencia crud = new CRUDIncidencia();
        List<Incidencia> incidencias = crud.incidenciasXAulas(textIncidencias.getText().toString());

        // Pasamos la información a una Lista observable para el TableView
        ObservableList<Incidencia> items = FXCollections.observableArrayList(incidencias);

        // Configuramos las columnas del TableView si aún no están configuradas
        TableColumn<Incidencia, String> colCodigo = new TableColumn<>("CÓDIGO ERROR");
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigoError().getCodigoError()));

        TableColumn<Incidencia, String> colDescripcion = new TableColumn<>("DESCRIPCIÓN");
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));

        tableIncidencias.getColumns().setAll(colCodigo, colDescripcion);

        tableIncidencias.setItems(items);
    }


    /**
     * Método para mostrar todos los tipos del errores creados y su descripción.
     */
    public void mostrarErroresTableView(){
        GestionErrores g = new GestionErrores();
        Map<String, String> errores = g.erroresMap();

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(errores.entrySet());

        TableColumn<Map.Entry<String, String>, String> colCodigo = new TableColumn<>("Código Error");
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String, String>, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        tableViewErrores.getColumns().setAll(colCodigo, colDescripcion);
        tableViewErrores.setItems(items);
    }

    //Método para rellenar el gráfico
    /**
     * Método que actualiza y rellena el gráfico de incidencias.
     */
    @FXML
    public void actualizarGrafico() {
        String referencia = textIncidencias.getText().trim();
        CRUDIncidencia crudIncidencia = new CRUDIncidencia();

        if (referencia.isEmpty()) {
            mostrarAlerta("Incidencias Aula", "Ingrese la referencia a un Aula");
            return;
        }

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        try {
            int numIncidencias = crudIncidencia.numIncidenciasAula(referencia);

            if (numIncidencias > 0) {
                for (int i = 1; i <= numIncidencias; i++) {
                    datosGrafico.add(new PieChart.Data("Incidencia " + i, 1));
                }
            } else {
                mostrarAlerta("Incidencias Aula", "No hay Incidencias para el Aula " + referencia);
            }
        } catch (AulaNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

        graficoIncidencias.setData(datosGrafico);
    }

    /**
     * Muestra errores al usuario.
     *
     * @param titulo  El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Método que rellena los MenuItem del MenuButton de Aulas
     */
    public void rellenarMenuButtonAula() {
        MBaula.getItems().clear(); //Limpiamos elementos anteriores

        for (Aula_Equipo a : aulaEquipo.readAllAulas()) {
            MenuItem item = new MenuItem(a.getReferencia().getReferencia()); //Rellenamos los MenúItems a partir de las Aulas que haya en la tabla Aula_Equipos
            item.setOnAction(e -> {
                MBaula.setText(a.getReferencia().getReferencia());
                rellenarMenuButtonEquipo(); //Llamamos al método que actualiza el MenuButton de equipos según su Aula
            });
            MBaula.getItems().add(item);
        }
    }

    /**
     * Método que rellena los MenuItem del MenuButton de Equipos en función del Aula seleccionada
     */
    public void rellenarMenuButtonEquipo() {
        MBequipo.getItems().clear(); //Limpiamos elementos anteriores

        String aulaSeleccionada = MBaula.getText();
        if (aulaSeleccionada == null || aulaSeleccionada.isEmpty()) {
            //Si el aula no se ha seleccionado mandamos un Alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Aula no seleccionada");
            alert.showAndWait();
            return;
        }

        //Filtramos los equipos según el Aula seleccionada
        for (Aula_Equipo i : aulaEquipo.readAllAulas()) {
            if (i.getReferencia().getReferencia().equals(aulaSeleccionada)) { //Comprobamos que el Aula coincide
                MenuItem item = new MenuItem(i.getIdInformacionSistema().getNombre());
                item.setOnAction(e -> MBequipo.setText(i.getIdInformacionSistema().getNombre()));
                MBequipo.getItems().add(item);
            }
        }
    }

    /**
     * Método que rellena los MenuItem del MenuButton de Errores
     */
    public void rellenarMenuButtonErrores(){
        for (Errores i : errores.readAllErrores()) {
            MenuItem item = new MenuItem(i.getCodigoError());
            item.setOnAction(e -> MBerror.setText(i.getCodigoError()));
            MBerror.getItems().add(item);
        }
    }

    /**
     * Método que se encarga de crear Incidencias y añadirlas a la BBDD
     */
    public void crearIncidencia() {
        String equipoSeleccionado = MBequipo.getText();
        String aulaSeleccionada = MBaula.getText();
        String errorSeleccionado = MBerror.getText();
        String descripcion = labelDescripcion.getText();

        if (equipoSeleccionado.equals("Equipo") || equipoSeleccionado.isEmpty() ||
                aulaSeleccionada.equals("Aula") || aulaSeleccionada.isEmpty() ||
                errorSeleccionado.equals("Error") || errorSeleccionado.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Uno o más campos no han sido seleccionados correctamente.");
            alert.showAndWait(); //Muestra el mensaje y espera a que el usuario cierre la alerta

            return; //Salir del método si falta información
        }

        InformacionSistema infoSistemas = incidencia.getEntityManager().find(InformacionSistema.class, infoSistema.getIdEquipo(equipoSeleccionado));
        if (infoSistema == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se encontró el equipo en la base de datos.");
            alert.showAndWait();
            return;
        }

        //Crear la incidencia con datos validados
        Incidencia nuevaIncidencia = new Incidencia(infoSistemas, new Aulas(aulaSeleccionada), new Errores(errorSeleccionado, null), descripcion);
        incidencia.insertIncidencia(nuevaIncidencia);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incidencia Creada");
        alert.setHeaderText(null);
        alert.setContentText("Incidencia creada correctamente.");
        alert.showAndWait();
    }
}
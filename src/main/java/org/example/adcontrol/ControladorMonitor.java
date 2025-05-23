package org.example.adcontrol;

import BBDD.DAO.*;
import BBDD.DTO.*;
import BBDD.Excepciones.AulaNotFoundException;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.*;

/**
 * Clase ControladorMonitor que controla la interfaz de incidencias y la lógica de la aplicación.
 *
 * @author Daniel García y Alberto.
 * @version 1.5
 */
public class ControladorMonitor extends Controlador {

    //Elementos FXML que se usarán en la clase.
    @FXML
    TableView<Incidencia> tableIncidencias;//Tabla para representar las distintas incidencias del sistema.

    @FXML
    MenuButton textIncidencias;

    @FXML
    PieChart graficoIncidencias; //Gráfico para las incidencias.

    @FXML
    Button cargarDatosButton;

    @FXML
    Button borrarInidencias;

    @FXML
    TableView<Map.Entry<String, String>> tableViewErrores; //Tabla para representar los distintos errores del sistema.

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
    public void initialize() {
        refrescarIdioma();
        mostrarErroresTableView();
        rellenarMenuButtonAula();
        rellenarMenuButtonSelecciónAula();
        //rellenarMenuButtonEquipo();
        rellenarMenuButtonErrores();

        //Efecto hover de los botones de la pagina
        aplicarEfectoHoverButton(crearIncidencia);
        aplicarEfectoHoverButton(cargarDatosButton);
        aplicarEfectoHoverButton(borrarInidencias);
        aplicarEfectoHoverMenuButton(MBaula);
        aplicarEfectoHoverMenuButton(MBequipo);
        aplicarEfectoHoverMenuButton(MBerror);
        aplicarEfectoHoverMenuButton(textIncidencias);
    }




    /**
     * Método que carga el idioma y se encarga de la traducción de los elementos.
     *
     * @param locale Idioma que recibe el método y con el que actúa.
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Debug

            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            textIncidencia.setText(bundle.getString("textIncidencia"));
            tituloIncidencias.setText(bundle.getString("tituloIncidencias"));
            cargarDatosButton.setText(bundle.getString("boton2.text"));

            System.out.println("Idioma cargado exitosamente.");//Debug

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al cargar el idioma ", ButtonType.CLOSE);
        }

    }

    /**
     * Muestra las incidencias en el TableView.
     *
     * @throws AulaNotFoundException Si el aula no se encuentra.
     */
    public void mostrarIncidenciasTableView() throws AulaNotFoundException {
        CRUDIncidencia crud = new CRUDIncidencia();
        List<Incidencia> incidencias = crud.incidenciasXAulas(textIncidencias.getText()); //Lista de todas las incidencias por aula seleccionada, usando la referencia del aula recogida en el label textIncidencias.

        //Pasamos la información a una Lista observable para el TableView
        ObservableList<Incidencia> items = FXCollections.observableArrayList(incidencias);

        //Configuramos las columnas del TableView si aún no están configuradas
        TableColumn<Incidencia, String> colEquipo = new TableColumn<>("EQUIPO");//Columna que contiene el nombre del equipo
        colEquipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdInformacionSistema().getNombre()));


        TableColumn<Incidencia, String> colCodigo = new TableColumn<>("CÓDIGO ERROR");//Columna que contiene el código de error de la incidencia sobre un equipo
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigoError().getCodigoError()));

        TableColumn<Incidencia, String> colDescripcion = new TableColumn<>("DESCRIPCIÓN");//Columna que contiene la descripción de una incidencia sobre un equipo
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));

        tableIncidencias.getColumns().setAll(colEquipo, colCodigo, colDescripcion);//Editamos las columnas de la tabla.

        tableIncidencias.setItems(items);//Editamos los items de la tabla.
    }


    /**
     * Método para mostrar todos los tipos del errores creados y su descripción.
     */
    public void mostrarErroresTableView() {
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

    /**
     * Método que actualiza y rellena el gráfico de incidencias.
     */
    @FXML
    public void actualizarGrafico() throws AulaNotFoundException {
        String referencia = textIncidencias.getText().trim();
        CRUDIncidencia crudIncidencia = new CRUDIncidencia();

        if (referencia.isEmpty()) {
            mostrarAlerta("Incidencias Aula", "Ingrese la referencia a un Aula");//Alerta si la referencia esta vacía.
            return;
        }

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        try {
            int numIncidencias = crudIncidencia.numIncidenciasAula(referencia);//Sacamos el número de incidencias.

            if (numIncidencias > 0) {
                for (int i = 1; i <= numIncidencias; i++) {
                    datosGrafico.add(new PieChart.Data("Incidencia " + i, 1));//Añadimos los datos al gráficos si las incidencias son mayores de 0.
                }
            } else {
                mostrarAlerta("Incidencias Aula", "No hay Incidencias para el Aula " + referencia);//Alerta si no se encuentran incidencias.
            }
        } catch (AulaNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

        graficoIncidencias.setData(datosGrafico);//cambiamos los datos del gráfico de incidencias.
        mostrarIncidenciasTableView(); //Llamamos al método que rellena la tabla de incidencias.
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
        Set<String> referenciasUnicas = new HashSet<>();

        for (Aula_Equipo a : aulaEquipo.readAllAulas()) {
            String referencia = a.getReferencia().getReferencia();

            //Agregamos referencias si no han sido añadida antes
            if (!referenciasUnicas.contains(referencia)) {
                referenciasUnicas.add(referencia);
                MenuItem item = new MenuItem(referencia);
                item.setOnAction(e -> {
                    MBaula.setText(referencia);
                    rellenarMenuButtonEquipo();
                });
                MBaula.getItems().add(item);
            }
        }
    }

    /**
     * Método que rellena los MenuItem del MenuButton de Aulas
     */
    public void rellenarMenuButtonSelecciónAula() {
        textIncidencias.getItems().clear(); //Limpiamos elementos anteriores
        Set<String> referenciasUnicas = new HashSet<>();

        for (Aula_Equipo a : aulaEquipo.readAllAulas()) {
            String referencia = a.getReferencia().getReferencia();

            //Agregamos referencias si no han sido añadida antes
            if (!referenciasUnicas.contains(referencia)) {
                referenciasUnicas.add(referencia);
                MenuItem item = new MenuItem(referencia);
                item.setOnAction(e -> {
                    textIncidencias.setText(referencia);
                    rellenarMenuButtonEquipo();
                });
                textIncidencias.getItems().add(item);
            }
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
    public void rellenarMenuButtonErrores() {
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
        try {
            actualizarGrafico();
        } catch (AulaNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que se encarga de borrar Incidencias de la BBDD
     */
    public void borrarIncidencia() {
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

        //Borrar incidencias
        Incidencia incBorrar = new Incidencia();
        boolean incidenciaEncontrada = false;
        for (Incidencia i : incidencia.getAllIncidencias()) {
            if ((i.getIdAula().getReferencia().equalsIgnoreCase(aulaSeleccionada)) && (i.getCodigoError().getCodigoError().equalsIgnoreCase(errorSeleccionado)) && i.getIdInformacionSistema().getNombre().equalsIgnoreCase(equipoSeleccionado)) {
                incBorrar = i;
                //Eliminamos la incidencia
                incidencia.deleteIncidencia(incBorrar);
                incidenciaEncontrada = true;
            }
        }
        Alert alert = new Alert(incidenciaEncontrada ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
        alert.setTitle(incidenciaEncontrada ? "Incidencia Eliminada" : "Incidencia No Encontrada");
        alert.setHeaderText(null);
        alert.setContentText(incidenciaEncontrada ? "Incidencia eliminada correctamente." : "No se encontró ninguna incidencia con los criterios seleccionados.");
        alert.showAndWait();
        try {
            actualizarGrafico();
        } catch (AulaNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
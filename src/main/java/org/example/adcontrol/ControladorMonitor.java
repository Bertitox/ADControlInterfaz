package org.example.adcontrol;
import BBDD.DAO.CRUDIncidencia;
import BBDD.DTO.Incidencia;
import BBDD.Excepciones.AulaNotFoundException;
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
import java.util.ResourceBundle;

/**
 * Clase que controla la interfaz de usuario y la lógica de la aplicación.
 * @author Daniel y Alberto
 * @version 1.5
 */
public class ControladorMonitor extends Controlador{

    @FXML
    ListView<String> listIncidencias;

    @FXML
    TextField textIncidencias;

    @FXML
    PieChart graficoIncidencias;

    @FXML
    Button actualizar;

    @FXML
    Button actualizar1;

    private ResourceBundle bundle;

    //Elementos a traducir
    @FXML
    private Label textIncidencia;
    @FXML
    private Label tituloIncidencias;

    /**
     * Inicializa los elementos de la interfaz y configura los botones de idioma.
     */
    @FXML
    public void initialize() {
        refrescarIdioma();
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

    //Método que rellena el ListView
    /**
     * Muestra las incidencias en el ListView.
     *
     * @throws AulaNotFoundException Si el aula no se encuentra.
     */
    public void mostrarIncidenciasListView() throws AulaNotFoundException {
        CRUDIncidencia crud = new CRUDIncidencia();
        List<Incidencia> incidencias = crud.incidenciasXAulas(textIncidencias.getText().toString());

        //Pasamos la información a una Lista observable para el ListView
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Incidencia incidencia : incidencias) {
            items.add("Código Error: " + incidencia.getCodigoError().getCodigoError());
            items.add("Descripción: " + incidencia.getDescripcion());
            items.add("");
        }

        listIncidencias.setItems(items);
    }

    //Método para rellenar el gráfico
    /**
     * Método que actualiza y rellena el gráfico de incidencias.
     */
    @FXML
    public void actualizarGrafico() {
        String referencia = textIncidencias.getText().trim(); //Obtener referencia del aula ingresada
        CRUDIncidencia crudIncidencia = new CRUDIncidencia();

        if (referencia.isEmpty()) {
            mostrarAlerta("Incidencias Aula","Ingrese la referencia a un Aula");
            return;
        }

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        try {
            int numIncidencias = crudIncidencia.numIncidenciasAula(referencia);

            if (numIncidencias > 0) {
                datosGrafico.add(new PieChart.Data(referencia, numIncidencias));
            } else {
                mostrarAlerta("Incidencias Aula","No hay Incidencias para el Aula "+ textIncidencias.getText().toString());
            }
        } catch (AulaNotFoundException e) {
            System.err.println("Error: " + e.getMessage()); //Manejo del error en el caso de que el aula no exista
        }

        graficoIncidencias.setData(datosGrafico); //Asignación de los datos al Gráfico PieChart
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

}
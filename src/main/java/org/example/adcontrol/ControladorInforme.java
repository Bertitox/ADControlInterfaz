package org.example.adcontrol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Clase Controladora de la pestaña informes
 *
 * @author Daniel García y Alberto
 * @version 2.0
 */
public class ControladorInforme extends Controlador {

    //Elementos FXML que se usarán.
    @FXML
    private Label tituloInforme;
    @FXML
    private Label tipo1;
    @FXML
    private Label tipo2;
    @FXML
    private Label textdirectorio;
    @FXML
    private Label textTotales;
    @FXML
    private Label textInforme;
    @FXML
    private Label textUltimo;
    @FXML
    private Label textNombreUltimo;

    //Elementos para generar Informes
    @FXML
    private TextArea nombreInforme;
    @FXML
    private TextArea textAreaRuta;
    @FXML
    private Label ultimoNombre;

    @FXML
    private Label numeroTotalInformes;

    @FXML
    private Label informeMasUtilizado;

    @FXML
    private Label ultimoInforme;

    Map<String, Integer> mapaInformeUtilizado;

    Integer nTotal = 0;

    @FXML
    private ComboBox comboboxInforme = new ComboBox();

    List<Button> botones;

    @FXML
    Button botonGenerar;

    @FXML
    Button botonExplorar;

    private ResourceBundle bundle;

    /**
     * Constructor por defecto del controlador
     */
    public ControladorInforme() {
    }

    /**
     * Método que incializa la lista y se añaden los botones a esta. También añade los datos al gráfico
     */
    @FXML
    public void initialize() {
        refrescarIdioma();

        mapaInformeUtilizado = new HashMap<>();
        //Para el Combobox
        ObservableList<String> items = FXCollections.observableArrayList("Aulas", "Incidencias", "Equipos");
        comboboxInforme.setItems(items);
        aplicarEfectoHoverButton(botonExplorar);
        aplicarEfectoHoverButton(botonGenerar, 1.03);
        aplicarEfectoHoverComboBox(comboboxInforme, 1.03);

    }

    /**
     * Método que se encarga de cargar el idioma seleccionado.
     *
     * @param locale Parámetro que recibe el método que representa el idioma.
     */
    public void cargarIdioma(Locale locale) {
        try {
            System.out.println("Cargando idioma: " + locale.getLanguage()); //Linea de Debug para asegurarse que funciona

            //Traducción de los elementos
            bundle = ResourceBundle.getBundle("org/example/adcontrol/messages", locale);
            tituloInforme.setText(bundle.getString("tituloInforme"));
            tipo1.setText(bundle.getString("tipo1"));
            tipo2.setText(bundle.getString("tipo2"));
            textdirectorio.setText(bundle.getString("textdirectorio"));
            textTotales.setText(bundle.getString("textTotales"));
            textUltimo.setText(bundle.getString("textUltimo"));
            textInforme.setText(bundle.getString("textInforme"));
            textNombreUltimo.setText(bundle.getString("textNombreUltimo"));
            botonGenerar.setText(bundle.getString("botonGenerar.text"));
            botonExplorar.setText(bundle.getString("botonExplorar.text"));
            System.out.println("Idioma cargado exitosamente.");//Debug

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el idioma: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Genera un archivo PDF con base en los parámetros seleccionados en la interfaz.
     *
     * @param event El evento generado al hacer clic en el botón de generar PDF.
     */
    @FXML
    void generarPDF(ActionEvent event) throws ClassNotFoundException, SQLException, JRException {
        Class.forName("com.mysql.jdbc.Driver");
        Map parametros = new HashMap();
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/infoSistema", "root", "210205");
        JasperPrint print = null;
        if (comboboxInforme.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el informe", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null); //Eliminamos el encabezado
            alert.showAndWait();

        } else if (textAreaRuta.getText().isBlank() || textAreaRuta.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selecciona una ruta", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null); //Elimina encabezado el encabezado
            alert.showAndWait();
        } else {
            //Según el tipo de informe que queramos, nos lo genera.
            switch (comboboxInforme.getValue().toString()) {
                case "Aulas":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/InformeAula.jasper", null, conexion);
                    break;
                case "Incidencias":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/InformeIncidencias.jasper", null, conexion);
                    break;
                case "Equipos":
                    print = JasperFillManager.fillReport("src/main/resources/org/example/adcontrol/Jaspers/Informe.jasper", null, conexion);
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el informe", ButtonType.OK);
                    alert.setTitle("Error");
                    alert.setHeaderText(null); //Elimina el encabezado
                    alert.showAndWait();
                    break;
            }

            String ruta;
            if (nombreInforme.getText().isBlank() || nombreInforme.getText().isEmpty()) {
                ruta = textAreaRuta.getText() + "/informe.pdf";
            } else {
                ruta = textAreaRuta.getText() + "/" + nombreInforme.getText() + ".pdf";
            }
            JasperExportManager.exportReportToPdfFile(print, ruta); //Pasamos el reporte a pdf.
            //Actualización de los labels correspondientes a los informes.
            ultimoInforme.setText(comboboxInforme.getValue().toString());
            mapaInformeUtilizado.put(comboboxInforme.getValue().toString(), mapaInformeUtilizado.getOrDefault(comboboxInforme.getValue().toString(), 0) + 1);
            this.nTotal++;
            numeroTotalInformes.setText(this.nTotal.toString());
            informeMasUtilizado.setText(getInformeMas());
            ultimoNombre.setText(nombreInforme.getText());

            //Alerta que nos avisa de que se ha creado un nuevo informe.
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Informe creado correctamente", ButtonType.OK);
            alert.setTitle("Informe creado");
            alert.setHeaderText(null); //Elimina el encabezado
            alert.showAndWait();
            nombreInforme.setText("");
        }
    }

    /**
     * Abre un selector de directorios para cambiar la ruta donde se guardará el informe generado.
     *
     * @param event El evento generado al hacer clic en el botón de explorar.
     */
    @FXML
    void cambiarRuta(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File f = directoryChooser.showDialog(null);
        textAreaRuta.setText(f.getAbsolutePath());
    }

    /**
     * Método que obtiene el informe más utilizado basado en el contador de informes generados.
     *
     * @return Retorna el nombre del informe más utilizado.
     */
    public String getInformeMas() {
        Integer MAX = 0; //Variable acumuladora.
        String informe = null; //Variable String que almacena el Nombre del informe más usado.
        for (String i : mapaInformeUtilizado.keySet()) { //Buscamos dentro del mapa
            if (mapaInformeUtilizado.get(i) > MAX) {//Si es mayor al máximo guardamos el String.
                MAX = mapaInformeUtilizado.get(i);
                informe = i;
            }
        }
        return informe;
    }

    /**
     * Método que aplica un estilo de fondo y borde al pasar el cursor sobre un botón de informe.
     *
     * @param event El evento generado cuando el ratón pasa sobre el botón de informe.
     */
    @FXML
    void hoverInforme(MouseEvent event) {
        Button boton = (Button) event.getSource();

        boton.setStyle("-fx-background-color: grey");
        boton.setStyle("-fx-border-color: grey");
        boton.setStyle("-fx-border-radius: 5px");
    }

    /**
     * Método que restablece el estilo de fondo y borde de un botón de informe cuando el ratón deja de estar sobre él.
     *
     * @param event El evento generado cuando el ratón deja de estar sobre el botón de informe.
     */
    @FXML
    void hoverNormalInforme(MouseEvent event) {
        Button boton = (Button) event.getSource();

        boton.getStyleClass().add("botonPrueba"); //Aplicamos la clase CSS al botón
        boton.setStyle("-fx-background-color: transparent");
        boton.setStyle("-fx-border-color: grey");
        boton.setStyle("-fx-border-radius: 5px");
    }
}
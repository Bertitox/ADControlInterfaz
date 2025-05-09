package org.example.adcontrol;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Clase
 *
 * @author Daniel y Alberto
 * @version 1.0
 */
public class ControladorResultadoEscaneo {

    //Instancia única de controladorResultadoEscaneo, para usar siempre la misma instancia
    private static ControladorResultadoEscaneo instancia;

    //Lista de las ips que se han detectado
    static List<String> ipsDetectadas;

    String aulaActual;

    //Vista en formato de tabla en la que se mostrarán todas las ips que se han detectado
    @FXML
    private TableView<Map.Entry<String, String>> tablaIps;

    //Texto en el que se indica en la vista la cantidad de equipos que se han escaneado
    @FXML
    private Label textoEscaneado;

    /**
     * Método que se encarga de controlar la instancia del idioma, usando la instancia ya creada.
     *
     * @return instancia ControlIdioma.
     */
    public static ControladorResultadoEscaneo getInstance() {
        if (instancia == null) {
            ControladorResultadoEscaneo controladorResultadoEscaneo = new ControladorResultadoEscaneo();
            instancia = new ControladorResultadoEscaneo();
            ipsDetectadas = new ArrayList<>();
        }
        return instancia;
    }

    /**
     * Método que inicializa todos los elementos necesarios de la vista
     */
    @FXML
    void initialize() {
        rellenarTabla();
        actualizarTexto();

        //Se establece la accion para cada fila (IP) de la tabla.
        tablaIps.setRowFactory(tv -> {
            TableRow<Map.Entry<String, String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //Se valida que se hagan 2 clicks encima de la ip y que la fila no esté vacía
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    //Se escoge la fila seleeccionada
                    Map.Entry<String, String> filaSeleccionada = row.getItem();
                    mandarScript(filaSeleccionada.getKey()); //Se escoge el valor (IP) de la fila
                }
            });
            return row;
        });
    }

    /**
     * Método que actualiza el texto, mostrando la cantidad de equipos escaneados
     */
    private void actualizarTexto() {
        textoEscaneado.setText("Se han escaneado " + ipsDetectadas.size() + " IPs diferentes");
    }

    /**
     * Método que rellena la tabla "tablaIps" por las direcciones y equipos escaneados
     */
    private void rellenarTabla() {
        Map<String, String> datosMapa = mapaIPEquipos(); //Se obtiene el valor de datosMapa a través del método mapaIPEquipos
        ObservableList<Map.Entry<String, String>> datos = FXCollections.observableArrayList(datosMapa.entrySet());

        TableColumn<Map.Entry<String, String>, String> colIp = new TableColumn<>("IP"); //Columna IP
        colIp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String, String>, String> colEquipo = new TableColumn<>("Equipo"); //Columna Equipo
        colEquipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        tablaIps.getColumns().setAll(colIp, colEquipo); //Se añaden la columnas
        tablaIps.setItems(datos); //Se ponen los datos
    }

    /**
     * Método que añade a cada dirección IP un nombre de equipo
     *
     * @return devuelve un mapa <IP(String), nombreDeEquipo(String)>
     */
    public Map<String, String> mapaIPEquipos() {
        Map<String, String> mapaIPEquipos = new HashMap<>();
        int i = 0;
        for (String ip : ipsDetectadas) {
            mapaIPEquipos.put(ip, "Equipo " + i++);
        }
        return mapaIPEquipos;
    }

    /**
     * Setter que guarda en la variable ipsDetectadas las ips
     *
     * @param ipsDetectadas Lista de las ips que se han escaneado
     */
    public void setIpsDetectadas(List<String> ipsDetectadas) {
        ControladorResultadoEscaneo.ipsDetectadas = ipsDetectadas;
    }

    /**
     * Método que pide las credenciales, y lanza el script para obtener los datos a través del json
     *
     * @param filaSeleccionada El valor de la IP al que se va a mandar el script
     */
    private void mandarScript(String filaSeleccionada) {
        Pair<String, String> credenciales = mostrarFormularioSSH(); //Se manda un formulario que requiere el usuario y la contraseña del equipo al que se va a conectar
        if (credenciales != null) {
            String passwordLocal = mostrarPasswordLocal(); //Se manda un formulario que requiere de la contraseña del equipo actual, para poder ejecutar sudo con este
            if (passwordLocal != null) {
                try {
                    //Se actualizan las credenciales
                    actualizarCredenciales(credenciales.getKey(), credenciales.getValue(), filaSeleccionada);
                } catch (Exception e) {
                    System.out.println("Excepción al actualizar credenciales en el script");
                    e.printStackTrace();
                }

                //Se ejecuta el script
                ejecutarScriptConSudo(passwordLocal);

                //Se espera a que el archivo .json esté disponible en el directorio
                while (!nuevoArchivoJsonDisponible()) {
                    try {
                        Thread.sleep(1000); // Revisar cada segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //Cuando el archivo .json esté presente, se ejecuta vistaManual()
                vistaManual();
            }
        }
    }

    /**
     * Muestra un formulario para que el usuario pueda introducir el usuario y contraseña del equipo al que se va a conectar, y su contraseña para ejecutarlo con sudo
     *
     * @return Devuelve el usuario y contraseña que se han introducido en el formulario
     */
    public static Pair<String, String> mostrarFormularioSSH() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Conexión SSH");
        dialog.setHeaderText("Introduce tus credenciales SSH");

        //Botones Aceptar y Cancelar
        ButtonType loginButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //Se establecen los espacios del gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        //Campo de texto de usuario
        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Usuario");

        //Campo de contraseña
        PasswordField contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");

        //Posiciones de los campos de texto
        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(usuarioField, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(contrasenaField, 1, 1);

        //Cuando el textfield crezca se redimensiona
        GridPane.setHgrow(usuarioField, Priority.ALWAYS);
        GridPane.setHgrow(contrasenaField, Priority.ALWAYS);

        //Se añade el contenido (gridpane) al dialog
        dialog.getDialogPane().setContent(grid);

        //Habilitar o deshabilitar el botón de Conectar si el usuario se ha escrito o no
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
        usuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(newValue.trim().isEmpty());
        });

        // Devolver los datos al pulsar Conectar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usuarioField.getText(), contrasenaField.getText());
            }
            return null;
        });
        //Se guarda los valores en resultado
        Optional<Pair<String, String>> resultado = dialog.showAndWait();

        //Se retorna resultado o null si está vacío
        return resultado.orElse(null);
    }

    /**
     * Método que actualiza dentro del fichero del script las variables globales como el usuario, contraseña o equipo al que se va a mandar el script
     *
     * @param usuario  Nuevo valor del usuario
     * @param password Nuevo valor del contraseña
     * @param ip       Nuevo valor del ip
     * @throws IOException Excepción de entrada y salida
     */
    public static void actualizarCredenciales(String usuario, String password, String ip) throws IOException {
        Path path = Paths.get("src/main/resources/org/example/adcontrol/Scripts/Script_funcional_en_VM.sh"); //Localización del script
        List<String> lineas = Files.readAllLines(path);

        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).startsWith("USUARIO=")) {
                lineas.set(i, "USUARIO=\"" + usuario + "\"                # Usuario del equipo remoto"); //Dejamos los comentarios para que no se borren
            }
            if (lineas.get(i).startsWith("HOST_REMOTO=")) {
                lineas.set(i, "HOST_REMOTO=\"" + ip + "\"      # Dirección IP o nombre del equipo remoto");//Dejamos los comentarios para que no se borren
            }
            if (lineas.get(i).startsWith("PASSWORD=")) {
                lineas.set(i, "PASSWORD=\"" + password + "\"        # Contraseña de sudo del equipo remoto");//Dejamos los comentarios para que no se borren
            }
        }

        Files.write(path, lineas); //Se escribe el fichero de nuevo y se actualiza
        System.out.println("Credenciales actualizadas en el script correctamente.");
    }

    /**
     * Método que valida si en el directorio "ResultadoScript" hay un archivo .json
     *
     * @return devuelve true si si existe, false si no existe
     */
    private boolean nuevoArchivoJsonDisponible() {
        // Ruta donde se busca el archivo .json
        File dir = new File("src/main/resources/org/example/adcontrol/ResultadoScript");
        File[] archivos = dir.listFiles((d, name) -> name.endsWith(".json"));
        return archivos != null && archivos.length > 0;
    }

    /**
     * Método que cambia la vista al formulario, tras confirmar que hay un archivo .json en el directorio "ResultadoScript"
     */
    void vistaManual() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));
            Parent root = loader.load();
            ControladorFormularioEquipo controladorB = loader.getController();
            controladorB.setAulaActual(aulaActual);
            controladorB.setLeerJson(true); //Seteo a true para que nada mas abrir la pantalla se carguen en los textfields los valores del json
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cambiar la pantalla al formulario para crear el equipo");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que muestra un formulario para que el usuario lo rellene con su contraseña del equipo local y poder mandar el script
     *
     * @return Devuelve un String con la contraseña
     */
    public static String mostrarPasswordLocal() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Contraseña local de administrador");
        dialog.setHeaderText("Introduce la contraseña de tu equipo actual (anfitrión) para ejecutar sudo:");

        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE); //Se modifica el texto del boton de OK_DONE y se pone el texto "Aceptar"
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //Se ponen los espacios de los textfields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        //Se añade un campo de contraseña
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña local");

        //Se añade el label
        grid.add(new Label("Contraseña local:"), 0, 0);
        grid.add(passwordField, 1, 0);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);

        //Se añade el grid al dialog
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            //Si se pulsa el boton de loginButtonType se retorna la contraseña
            if (dialogButton == loginButtonType) {
                return passwordField.getText();
            }
            return null;
        });

        //Se guarda el valor de la contraseña, puede ser nulo
        Optional<String> resultado = dialog.showAndWait();

        //Se retorna la contraseña o null si no hay
        return resultado.orElse(null);
    }

    /**
     * Método que ejecuta el script con sudo, para que no haya problemas durante el proceso
     *
     * @param password Se pasa como parámetro directamente la contraseña, ya que no es seguro guardar la contaseña en una variable
     */
    public static void ejecutarScriptConSudo(String password) {
        try {
            String rutaScript = "src/main/resources/org/example/adcontrol/Scripts/Script_funcional_en_VMV2.sh"; //Ruta del script que se va a ejecutar

            //SE hace ejecutable el script
            Process chmod = Runtime.getRuntime().exec("chmod +x " + rutaScript);
            chmod.waitFor();

            //Comando con sudo -S para que la contraseña la introduzcamos desde el stdin
            String[] comando = {"/bin/bash", "-c", "echo \"" + password + "\" | sudo -S " + rutaScript};

            //Se crea el proceso
            Process proceso = Runtime.getRuntime().exec(comando);

            //Se lee la salida normal
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            //Se lee la salida de error (si es que hay)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            while ((linea = errorReader.readLine()) != null) {
                System.err.println(linea);
            }

            //Se espera a que el proceso termine
            int exitCode = proceso.waitFor();
            if (exitCode == 0) {
                System.out.println("Script ejecutado correctamente con sudo.");
            } else {
                System.out.println("Error al ejecutar el script con sudo. Código de salida: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter del aula actual en la que se encuentra el usuario
     *
     * @param aulaActual Aula actual en la que está el usuario
     */
    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }
}
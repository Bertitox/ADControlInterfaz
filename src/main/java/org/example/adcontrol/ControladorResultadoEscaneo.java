package org.example.adcontrol;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

public class ControladorResultadoEscaneo {

    //Variables que se usarán en la clase.
    private static ControladorResultadoEscaneo instancia; //Instancia única de control idioma, para saber el idioma actual.
    static List<String> ipsDetectadas;

    String aulaActual;

    @FXML
    private TableView<Map.Entry<String, String>> tablaIps;

    @FXML
    private Label textoEscaneado;

    @FXML
    void initialize() {
        rellenarTabla();
        actualizarTexto();

        tablaIps.setRowFactory(tv -> {
            TableRow<Map.Entry<String, String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Map.Entry<String, String> filaSeleccionada = row.getItem();
                    mandarScript(filaSeleccionada.getKey());
                }
            });
            return row;
        });
    }

    private void actualizarTexto() {
        textoEscaneado.setText("Se han escaneado " + ipsDetectadas.size() + " IPs diferentes");

    }

    private void rellenarTabla() {
        Map<String, String> datosMapa = mapaIPEquipos(); //Mapa para rellenar la tabla
        ObservableList<Map.Entry<String, String>> datos = FXCollections.observableArrayList(datosMapa.entrySet());

        TableColumn<Map.Entry<String, String>, String> colIp = new TableColumn<>("IP");
        colIp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String, String>, String> colEquipo = new TableColumn<>("Equipo");
        colEquipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValue()));

        tablaIps.getColumns().setAll(colIp, colEquipo);
        tablaIps.setItems(datos);
    }


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

    public List<String> getIpsDetectadas() {
        return ipsDetectadas;
    }

    public Map<String, String> mapaIPEquipos() {
        Map<String, String> mapaIPEquipos = new HashMap<>();
        int i = 0;
        for (String ip : ipsDetectadas) {
            mapaIPEquipos.put(ip, "Equipo " + i++);
        }
        return mapaIPEquipos;
    }

    public void setIpsDetectadas(List<String> ipsDetectadas) {
        ControladorResultadoEscaneo.ipsDetectadas = ipsDetectadas;
    }

    public static Pair<String, String> mostrarFormularioSSH() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Conexión SSH");
        dialog.setHeaderText("Introduce tus credenciales SSH");

        // Botones Aceptar / Cancelar
        ButtonType loginButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Campos de texto
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Usuario");

        PasswordField contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(usuarioField, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(contrasenaField, 1, 1);

        // Hacer que el textfield crezca si se redimensiona
        GridPane.setHgrow(usuarioField, Priority.ALWAYS);
        GridPane.setHgrow(contrasenaField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Habilitar o deshabilitar el botón de Conectar según haya usuario escrito
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

        Optional<Pair<String, String>> resultado = dialog.showAndWait();

        return resultado.orElse(null);
    }


    public static void actualizarCredenciales(String usuario, String password, String ip) throws IOException {
        Path path = Paths.get("src/main/resources/org/example/adcontrol/Scripts/Script_funcional_en_VM.sh");
        List<String> lineas = Files.readAllLines(path);

        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).startsWith("USUARIO=")) {
                lineas.set(i, "USUARIO=\"" + usuario + "\"                # Usuario del equipo remoto");
            }
            if (lineas.get(i).startsWith("HOST_REMOTO=")) {
                lineas.set(i, "HOST_REMOTO=\"" + ip + "\"      # Dirección IP o nombre del equipo remoto");
            }
            if (lineas.get(i).startsWith("PASSWORD=")) {
                lineas.set(i, "PASSWORD=\"" + password + "\"        # Contraseña de sudo del equipo remoto");
            }
        }

        Files.write(path, lineas);
        System.out.println("Credenciales actualizadas en el script correctamente.");
    }

    private void mandarScript(String filaSeleccionada) {
        Pair<String, String> credenciales = mostrarFormularioSSH();
        if (credenciales != null) {
            String passwordLocal = mostrarPasswordLocal();
            if (passwordLocal != null) {

                try {
                    // Actualizar credenciales
                    actualizarCredenciales(credenciales.getKey(), credenciales.getValue(), filaSeleccionada);
                } catch (Exception e) {
                    System.out.println("Excepción al actualizar credenciales en el script");
                    e.printStackTrace();
                }

                // Ejecutar el script
                ejecutarScriptConSudo(passwordLocal);

                // Esperar a que el archivo .json esté disponible en el directorio
                while (!nuevoArchivoJsonDisponible()) {
                    try {
                        Thread.sleep(1000); // Revisar cada segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Cuando el archivo .json esté presente, ejecutar vistaManual()
                vistaManual();
            }
        }
    }

    private boolean nuevoArchivoJsonDisponible() {
        // Ruta donde se busca el archivo .json
        File dir = new File("src/main/resources/org/example/adcontrol/ResultadoScript");
        File[] archivos = dir.listFiles((d, name) -> name.endsWith(".json"));
        return archivos != null && archivos.length > 0;
    }

    void vistaManual() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));
            Parent root = loader.load();
            ControladorFormularioEquipo controladorB = loader.getController();
            controladorB.setAulaActual(aulaActual);
            controladorB.setLeerJson(true);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e){
            System.out.println("Error al cambiar la pantalla al formulario para crear el equipo");
            e.printStackTrace();
        }
    }

    public static String mostrarPasswordLocal() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Contraseña local de administrador");
        dialog.setHeaderText("Introduce la contraseña de tu equipo actual (anfitrión) para ejecutar sudo:");

        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña local");

        grid.add(new Label("Contraseña local:"), 0, 0);
        grid.add(passwordField, 1, 0);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> resultado = dialog.showAndWait();
        return resultado.orElse(null);
    }

    public static void ejecutarScriptConSudo(String password) {
        try {
            String rutaScript = "src/main/resources/org/example/adcontrol/Scripts/Script_funcional_en_VM.sh";

            // Hacer ejecutable el script
            Process chmod = Runtime.getRuntime().exec("chmod +x " + rutaScript);
            chmod.waitFor();

            // Comando con sudo -S
            String[] comando = {"/bin/bash", "-c", "echo \"" + password + "\" | sudo -S " + rutaScript};

            Process proceso = Runtime.getRuntime().exec(comando);

            // Leer la salida normal
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Leer la salida de error
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            while ((linea = errorReader.readLine()) != null) {
                System.err.println(linea);
            }

            // Esperar a que el proceso termine
            int exitCode = proceso.waitFor();
            if (exitCode == 0) {
                System.out.println("✅ Script ejecutado correctamente con sudo.");
            } else {
                System.out.println("❌ Error al ejecutar el script con sudo. Código de salida: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }
}
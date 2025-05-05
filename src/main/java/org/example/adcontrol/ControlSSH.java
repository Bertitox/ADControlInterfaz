package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DTO.Aula_Equipo;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import static org.example.adcontrol.ControladorResultadoEscaneo.mostrarFormularioSSH;
import static org.example.adcontrol.ControladorResultadoEscaneo.mostrarPasswordLocal;

public class ControlSSH {
    @FXML
    private TextArea salidaTerminal;
    @FXML
    private TextArea entradaComando;

    String host = "";
    String user = "";
    String password = "";

    String claseActual;

    Boolean superusuario = true;
    private OutputStream out;
    private ChannelShell channel;

    @FXML
    private MenuButton menuButtonEquipos;

    public ControlSSH() {
        salidaTerminal = new TextArea();
        salidaTerminal.setEditable(false);
        salidaTerminal.setWrapText(true);
        entradaComando = new TextArea();

        //Agregamos un listener para capturar el "Enter"
        entradaComando.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> enviarComando();
            }
        });
    }

    public void inciarConexionSSH() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");

            salidaTerminal.appendText("Conectando al servidor...\n");
            session.connect(30000);
            salidaTerminal.appendText("Conexión establecida con éxito.\n");

            channel = (ChannelShell) session.openChannel("shell");
            out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();
            //Hilo para leer la salida en tiempo real
            new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        String output = new String(buffer, 0, bytesRead);
                        Platform.runLater(() -> salidaTerminal.appendText(output));
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> salidaTerminal.appendText("Error al leer la salida: " + e.getMessage() + "\n"));
                }
            }).start();
            if (superusuario) {
                convSuper();
            }
        } catch (Exception e) {
            salidaTerminal.appendText("Error en la conexión SSH: " + e.getMessage() + "\n");
        }
    }

    public void convSuper() throws IOException, InterruptedException {
        //Damos privilegios al usuario root con "sudo su"
        out.write("sudo su\n".getBytes());
        out.flush();
        Thread.sleep(500); //Esperamos un poco para que pida la contraseña

        //Enviar la contraseña cuando el sistema la solicite
        out.write((password + "\n").getBytes());
        out.flush();
        superusuario = false;
    }

    @FXML
    private void enviarComando() {
        try {
            String comando = entradaComando.getText();

            if (comando.equals("clear\n")) {
                salidaTerminal.clear();
            } else if (comando.equals("exit\n")) {
                channel.disconnect();
            } else if (!(comando.equals("") || comando.isBlank() || comando.isEmpty() || comando == null)) {
                out.write((comando + "\n").getBytes());
                out.flush();
            }
            Platform.runLater(() -> entradaComando.clear()); //Limpiamos el TextArea de entrada

        } catch (Exception e) {
            salidaTerminal.appendText("Error al enviar comando: " + e.getMessage() + "\n");
        }
    }

    @FXML
    void validarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            enviarComando();
        }
    }

    @FXML
    void apagar(ActionEvent event) throws IOException {
        out.write(("sudo poweroff" + "\n").getBytes());
        out.flush();
        salidaTerminal.clear();
        salidaTerminal.setStyle("-fx-control-inner-background: red");
    }

    @FXML
    void reiniciar(ActionEvent event) throws IOException {
        out.write(("sudo reboot" + "\n").getBytes());
        out.flush();
        salidaTerminal.clear();
        salidaTerminal.setStyle("-fx-control-inner-background: orange");
    }

    @FXML
    void botonConectarse(ActionEvent event) throws IOException {
        Pair<String, String> credenciales = mostrarFormularioSSH();
        user = credenciales.getKey();
        password = credenciales.getValue();
        this.inciarConexionSSH();
    }
    public static Pair<String, String> mostrarFormularioSSH() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Conexión SSH");
        dialog.setHeaderText("Introduce tus credenciales SSH");

        //Botones Aceptar / Cancelar
        ButtonType loginButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //Campos de texto
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

        //Hacemos que el textfield crezca si se redimensiona
        GridPane.setHgrow(usuarioField, Priority.ALWAYS);
        GridPane.setHgrow(contrasenaField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        //Habilitamos o deshabilitamos el botón de Conectar según haya usuarios escritos
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);

        usuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(newValue.trim().isEmpty());
        });

        //Devolvemos los datos al pulsar Conectar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usuarioField.getText(), contrasenaField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> resultado = dialog.showAndWait();

        return resultado.orElse(null);
    }


    public void setClaseActual(String claseActual) {
        this.claseActual = claseActual;
        cargarEquipos();
    }

    private void cargarEquipos() {
        CRUDAula_Equipo aulaEquipo = new CRUDAula_Equipo();
        menuButtonEquipos.getItems().clear();

        for (Aula_Equipo i : aulaEquipo.readAllAulas()) {
            if (i.getReferencia().getReferencia().equals(claseActual)) {
                MenuItem item = new MenuItem(i.getIdInformacionSistema().getNombre());
                menuButtonEquipos.getItems().add(item);
                item.setOnAction(event -> {
                    menuButtonEquipos.setText(item.getText());
                    host = i.getIdInformacionSistema().getIp();
                });
            }
        }


    }
}

package org.example.adcontrol;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ControlSSH {
    @FXML
    private TextArea salidaTerminal;
    @FXML
    private TextArea entradaComando;

    String host = "10.211.55.5";
    String user = "parallels";
    String password = "usuarioxd";

    Boolean superusuario = true;
    private OutputStream out;
    private ChannelShell channel;

    public ControlSSH() {
        salidaTerminal = new TextArea();
        salidaTerminal.setEditable(false);
        salidaTerminal.setWrapText(true);
        entradaComando = new TextArea();

        // Agregar listener para capturar "Enter"
        entradaComando.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> enviarComando();
            }
        });
    }

    @FXML
    void initialize() {
        Platform.runLater(this::inciarConexionSSH);
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
            // Hilo para leer la salida en tiempo real
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
        // Elevar privilegios a root con "sudo su"
        out.write("sudo su\n".getBytes());
        out.flush();
        Thread.sleep(500); // Esperar un poco para que pida la contraseña

        // Enviar la contraseña cuando el sistema la solicite
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
            Platform.runLater(() -> entradaComando.clear()); // Limpiar el TextArea de entrada

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
}

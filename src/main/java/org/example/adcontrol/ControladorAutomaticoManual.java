package org.example.adcontrol;

import BBDD.Excepciones.AulaNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorAutomaticoManual extends Controlador {
    @FXML
    AnchorPane anchorPane;

    String aulaActual;

    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }

    @FXML
    void vistaAutomatico(MouseEvent event) {
        List<String> ipsDetectadas = new ArrayList<>();
        ipsDetectadas.clear();
        try {
            // Detecta la IP local usando `ip addr`
            Process obtenerIP = Runtime.getRuntime().exec("ip addr");
            BufferedReader lectorIP = new BufferedReader(new InputStreamReader(obtenerIP.getInputStream()));

            String linea;
            String ipLocal = null;

            while ((linea = lectorIP.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("inet ") && !linea.contains("127.0.0.1")) {
                    String[] partes = linea.split("\\s+");
                    ipLocal = partes[1]; // Ej: 192.168.1.45/24
                    break;
                }
            }

            obtenerIP.waitFor();

            if (ipLocal == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo obtener la IP local.", ButtonType.OK);
                alert.setTitle("Error de red");
                alert.setHeaderText(null);
                alert.show();
                return;
            }

            // Extrae el prefijo de red (ej: 192.168.1.0/24)
            String red = ipLocal.substring(0, ipLocal.indexOf('/'));
            String[] octetos = red.split("\\.");
            String rangoRed = octetos[0] + "." + octetos[1] + "." + octetos[2] + ".0/24";

            System.out.println("Escaneando red: " + rangoRed);

            // Ejecuta nmap con la red detectada
            String comando = "nmap -sn " + rangoRed;
            Process proceso = Runtime.getRuntime().exec(comando);
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            while ((linea = lector.readLine()) != null) {
                if (linea.startsWith("Nmap scan report for")) {
                    String[] partes = linea.split(" ");
                    if (partes.length >= 5) {
                        String ip = partes[partes.length - 1];
                        ipsDetectadas.add(ip); // Guarda solo la IP
                    }
                }
            }

            proceso.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Se han detectado " + ipsDetectadas.size() + " equipos");

        for (String ip : ipsDetectadas) {
            System.out.println("IP detectada: " + ip);
        }

        ControladorResultadoEscaneo controladorResultadoEscaneo = ControladorResultadoEscaneo.getInstance();
        controladorResultadoEscaneo.setIpsDetectadas(ipsDetectadas);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaResultadoEscaneo.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setResizable(false);
            stage.setTitle("IPs detectadas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void vistaManual(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));
            Parent root = loader.load();
            ControladorFormularioEquipo controladorB = loader.getController();
            controladorB.setAulaActual(aulaActual);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e){
            System.out.println("Error al cambiar la pantalla al formulario para crear el equipo");
        }
    }
}

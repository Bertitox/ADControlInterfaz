package org.example.adcontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ControladorAutomaticoManual que se encarga de gestionar el pop up para añadir equipos, tanto de forma automática como de forma manual.
 *
 * @author Daniel García y Alberto
 * @version 1.5
 */
public class ControladorAutomaticoManual extends Controlador {

    //Componentes FXML necesarios.
    @FXML
    AnchorPane anchorPane;

    String aulaActual;

    /**
     * Método que se encarga de cambiar el aula actual.
     *
     * @param aulaActual String con la referencia al aula actual.
     */
    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }

    /**
     * Método que se encarga de la cofiguración de la vista automática.
     *
     * @param event MouseEvent que espera el método.
     */
    @FXML
    void vistaAutomatico(MouseEvent event) {
        List<String> ipsDetectadas = new ArrayList<>();
        ipsDetectadas.clear();
        try {
            //Detecta la IP local usando `ip addr`
            Process obtenerIP = Runtime.getRuntime().exec("ip addr");
            BufferedReader lectorIP = new BufferedReader(new InputStreamReader(obtenerIP.getInputStream()));

            String linea;
            String ipLocal = null;

            while ((linea = lectorIP.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("inet ") && !linea.contains("127.0.0.1")) {
                    String[] partes = linea.split("\\s+");
                    ipLocal = partes[1]; //Ej: 192.168.1.45/24
                    break;
                }
            }

            obtenerIP.waitFor();

            //Si la ip local es nula, mandamos la alerta de que no se puedo obtener la IP.
            if (ipLocal == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo obtener la IP local.", ButtonType.OK);
                alert.setTitle("Error de red");
                alert.setHeaderText(null);
                alert.show();
                return;
            }

            //Extrae el prefijo de red (ej: 192.168.1.0/24)
            String red = ipLocal.substring(0, ipLocal.indexOf('/'));
            String[] octetos = red.split("\\.");
            String rangoRed = octetos[0] + "." + octetos[1] + "." + octetos[2] + ".0/24";

            System.out.println("Escaneando red: " + rangoRed);

            //Ejecuta "nmap" con la red que ha detectado
            String comando = "nmap -sn " + rangoRed;
            Process proceso = Runtime.getRuntime().exec(comando);
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            //Usamos el bucle para leer todas las IP´s y almacernarlas en un Array de Strings
            while ((linea = lector.readLine()) != null) {
                if (linea.startsWith("Nmap scan report for")) {
                    String[] partes = linea.split(" ");
                    if (partes.length >= 5) {
                        String ip = partes[partes.length - 1];
                        ipsDetectadas.add(ip); //Guarda solo la IP
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
            //Iniciamos la vista del resultado del escaneo.
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/vistaResultadoEscaneo.fxml"));//Cargamos la vista.
            Parent root = fxmlLoader.load();
            ControladorResultadoEscaneo controladorA = fxmlLoader.getController();
            controladorA.setAulaActual(aulaActual);//Cambiamos el aula por el aula actual.
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

    /**
     * Método que se de cargar la vista manual para agregar equipos.
     *
     * @param event Evento MouseEvent que espera el método vistaManual.
     */
    @FXML
    void vistaManual(MouseEvent event) {
        try {
            //Iniciamos la vista manual.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vistas/formularioEquipo.fxml"));//Cargamos la vista manual.
            Parent root = loader.load();
            ControladorFormularioEquipo controladorB = loader.getController();
            controladorB.setAulaActual(aulaActual); //Seleccionamos el aula actual
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cambiar la pantalla al formulario para crear el equipo");//Error que muestra si la vista no se ha cargado correctamente.
        }
    }
}
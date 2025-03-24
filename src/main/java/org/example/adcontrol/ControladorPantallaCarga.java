package org.example.adcontrol;

import BBDD.DAO.CRUDAula;
import BBDD.DAO.CRUDIncidencia;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.IOException;

public class ControladorPantallaCarga implements Runnable {
    @FXML
    ProgressBar progressBar;
    @FXML
    Label textoCarga;

/*    *//**
     * Método encargado de poner el color a la barra de progreso y de hacer que aumente cada 50 milisegundos
     *//*
    @Override
    public void run() {
        //progressBar.setStyle("-fx-accent: #01a5e7;");
        for (int i = 0; i <= 100; i++) {
            final int value = i;
            Platform.runLater(() -> progressBar.setProgress(value / 100.0));
            try {
                Thread.sleep(50); // Espera 0.05s para que la barra no se complete de golpe
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            try {
                primeraVentana(); // Se llama al método que iniciará la nueva ventana
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }*/

    /**
     * Costructor por defecto del controlador
     */
    public ControladorPantallaCarga() {
    }

    /**
     * Método encargado de cargar la bbdd y rellenar la barra de tareas.
     */
    @Override
    public void run() {
        try {
            actualizarTexto("Iniciando carga de la aplicación...");

            for (int i = 0; i <= 100; i++) {
                final int value = i;
                Platform.runLater(() -> progressBar.setProgress(value / 100.0));

                //Frases aleatoria para el usuario durante la carga
                if (i == 10) actualizarTexto("Conectando a la base de datos...");
                if (i == 30) actualizarTexto("Cargando datos de las incidencias...");
                if (i == 60) {
                    cargarDatosBBDD();  // Aquí cargamos la información de la base de datos
                    actualizarTexto("Datos cargados correctamente.");
                }
                if (i == 80) actualizarTexto("Aplicando configuraciones...");
                if (i == 100) actualizarTexto("Carga completa. Abriendo la aplicación...");

                Thread.sleep(50); //Simular tiempo de carga
            }

            Platform.runLater(() -> {
                try {
                    primeraVentana();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para actualizar el texto de la pantalla de carga
     * @param mensaje Recibe un String mensaje que muestra al usuario y va cambiando
     */
    private void actualizarTexto(String mensaje) {
        Platform.runLater(() -> textoCarga.setText(mensaje));
    }

    /**
     * Método que realiza la carga de la base de datos
     */
    private void cargarDatosBBDD() {
        // Simula la carga de la base de datos
        CRUDIncidencia incidencia = new CRUDIncidencia();
        CRUDAula aula = new CRUDAula();

        incidencia.numIncidencias();
        aula.readAllAulas();
    }

    /**
     * Método encargado de cargar la nueva ventana utilizando la anterior, es decir,
     * sin generar una nueva por encima de la actual
     * @throws IOException Excepcion necesaria para el método load() de fxmlLoader
     */
    @FXML
    public void primeraVentana() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Vistas/controlesMenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) progressBar.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Pagina principal");
        stage.show();
    }
}
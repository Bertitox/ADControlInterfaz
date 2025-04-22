package org.example.adcontrol;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class FormularioPopUpEquipos {

    public static Optional<Equipo> mostrar() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Nuevo Equipo");

        // Campos
        TextField nombre = new TextField();
        TextField so = new TextField();
        DatePicker ultFecha = new DatePicker();
        TextField ultHora = new TextField();
        ultHora.setPromptText("HH:mm:ss");

        TextField nombreNodo = new TextField();
        TextField mac = new TextField();

        TextField releasee = new TextField();
        TextField version = new TextField();
        TextField arquitectura = new TextField();

        TextField procesador = new TextField();
        TextField usoCpu = new TextField();

        TextField memTotal = new TextField();
        TextField memDisp = new TextField();

        // Botones
        Button cancelarBtn = new Button("Cancelar");
        Button añadirBtn = new Button("Añadir");

        final Equipo[] resultado = new Equipo[1];

        cancelarBtn.setOnAction(e -> {
            resultado[0] = null;
            popup.close();
        });

        añadirBtn.setOnAction(e -> {
            resultado[0] = new Equipo(
                    nombre.getText(),
                    so.getText(),
                    ultFecha.getValue(),
                    nombreNodo.getText(),
                    releasee.getText(),
                    version.getText(),
                    arquitectura.getText(),
                    procesador.getText(),
                    Integer.parseInt(memTotal.getText()),
                    Integer.parseInt(memDisp.getText()),
                    usoCpu.getText(),
                    mac.getText(),
                    LocalTime.parse(ultHora.getText())
            );
            popup.close();
        });

        // Grid Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(12);

        int row = 0;

        // Fila 1
        grid.add(new Label("Nombre:"), 0, row);
        grid.add(nombre, 1, row);
        grid.add(new Label("SO:"), 2, row);
        grid.add(so, 3, row);
        row++;

        // Fila 2
        grid.add(new Label("Fecha Última:"), 0, row);
        grid.add(ultFecha, 1, row);
        grid.add(new Label("Hora Última:"), 2, row);
        grid.add(ultHora, 3, row);
        row++;

        // Fila 3
        grid.add(new Label("Nombre Nodo:"), 0, row);
        grid.add(nombreNodo, 1, row);
        grid.add(new Label("MAC:"), 2, row);
        grid.add(mac, 3, row);
        row++;

        // Fila 4
        grid.add(new Label("Release:"), 0, row);
        grid.add(releasee, 1, row);
        grid.add(new Label("Versión:"), 2, row);
        grid.add(version, 3, row);
        row++;

        // Fila 5
        grid.add(new Label("Arquitectura:"), 0, row);
        grid.add(arquitectura, 1, row);
        grid.add(new Label("Procesador:"), 2, row);
        grid.add(procesador, 3, row);
        row++;

        // Fila 6
        grid.add(new Label("Uso CPU:"), 0, row);
        grid.add(usoCpu, 1, row);
        grid.add(new Label("Mem Total (MB):"), 2, row);
        grid.add(memTotal, 3, row);
        row++;

        // Fila 7
        grid.add(new Label("Mem Disponible (MB):"), 0, row);
        grid.add(memDisp, 1, row);

        // Botones
        HBox botones = new HBox(10, cancelarBtn, añadirBtn);
        botones.setPadding(new Insets(20, 0, 0, 0));
        grid.add(botones, 0, ++row, 4, 1); // Span de 4 columnas

        Scene scene = new Scene(grid, 720, 400);
        popup.setScene(scene);
        popup.showAndWait();

        return Optional.ofNullable(resultado[0]);
    }

    public record Equipo(
            String nombre,
            String so,
            LocalDate ultFecha,
            String nombreNodo,
            String releasee,
            String version,
            String arquitectura,
            String procesador,
            int memTotal,
            int memDisp,
            String usoCpu,
            String mac,
            LocalTime ultHora
    ) {}
}
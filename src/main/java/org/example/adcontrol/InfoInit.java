package org.example.adcontrol;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.*;

public class InfoInit {
    private static String idiomaLeido;
    private static Boolean muteLeido;
    private static Double volumenLeido;
    private static String temaLeido;
    private static InfoInit instancia;

    public static InfoInit getInstance() {
        if (instancia == null) {
            instancia = new InfoInit();
            cargarDatos();
        }
        return instancia;
    }

    public static void cargarDatos() {
        String ruta = "src/main/resources/org/example/adcontrol/Fichero Inicio/init";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("\\s+");

                switch (palabras[0]) {
                    case "I":
                        idiomaLeido = palabras[1];
                        System.out.println("IDIOMA PREDEFINIDO: " + idiomaLeido);
                        break;
                    case "R":
                        muteLeido = Boolean.parseBoolean(palabras[1]);
                        System.out.println("MUTE PREDEFINIDO: " + muteLeido);
                        break;
                    case "V":
                        volumenLeido = Double.valueOf(palabras[1]);
                        System.out.println("VOLUMEN PREDEFINIDO: " + volumenLeido);
                        break;
                    case "T":
                        temaLeido = palabras[1];
                        System.out.println("TEMA PREDEFINIDO: " + temaLeido);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void restablecerAjustes() {
        Path ini = Path.of("src/main/resources/org/example/adcontrol/Fichero Inicio/init");
        Path predefinido = Path.of("src/main/resources/org/example/adcontrol/Fichero Inicio/predefinido");
        try {
            // Leer todo el contenido del archivo origen
            byte[] contenido = Files.readAllBytes(predefinido);

            // Escribir (reemplazar) el contenido en el archivo destino
            Files.write(ini, contenido, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

            System.out.println("Contenido copiado y reemplazado correctamente.");

            cargarDatos();
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
    }

    public void escribirDatos() {
        String ruta = "src/main/resources/org/example/adcontrol/Fichero Inicio/init";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {

            escritor.write("I " + idiomaLeido);
            escritor.newLine();

            escritor.write("R " + muteLeido);
            escritor.newLine();

            escritor.write("V " + volumenLeido);
            escritor.newLine();

            escritor.write("T " + temaLeido);
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    public String getIdiomaLeido() {
        return idiomaLeido;
    }

    public Boolean getMuteLeido() {
        return muteLeido;
    }

    public Double getVolumenLeido() {
        return volumenLeido;
    }

    public String getTemaLeido() {
        return temaLeido;
    }

    public InfoInit getInstancia() {
        return instancia;
    }

    public void setIdiomaLeido(String idiomaLeido) {
        InfoInit.idiomaLeido = idiomaLeido;
        escribirDatos();
    }

    public void setMuteLeido(Boolean muteLeido) {
        InfoInit.muteLeido = muteLeido;
        escribirDatos();
    }

    public void setVolumenLeido(Double volumenLeido) {
        InfoInit.volumenLeido = volumenLeido;
        escribirDatos();
    }

    public void setTemaLeido(String temaLeido) {
        InfoInit.temaLeido = temaLeido;
        escribirDatos();
    }

    public void setInstancia(InfoInit instancia) {
        InfoInit.instancia = instancia;
        escribirDatos();
    }
}

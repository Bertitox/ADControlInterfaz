package org.example.adcontrol;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author Daniel y Alberto
 * @version 1.0
 * Clase singletone que guarda, actualiza y restablece los valores del fichero init
 */
public class InfoInit {
    private static String idiomaLeido;
    private static Boolean muteLeido;
    private static Double volumenLeido;
    private static String temaLeido;
    private static Integer intervalo;
    private static InfoInit instancia;

    /**
     * Método para obtener la instancia de la misma clase si ya está creada. De lo contrario creará y guardará una nueva.
     *
     * @return Devuelve la instancia para usar siempre la misma instancia
     */
    public static InfoInit getInstance() {
        if (instancia == null) {
            instancia = new InfoInit();
            cargarDatos();
        }
        return instancia;
    }

    /**
     * Método que accede al fichero "init", lee cada línea, y en función de la primera letra antes del espacio, guarda
     * el valor en una variable u otra.
     */
    public static void cargarDatos() {
        String ruta = "src/main/resources/org/example/adcontrol/Fichero Inicio/init";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("\\s+"); //Separa el string en 2 partes, la letra antes del espacio (palabras[0]) y el valor (palabras[1])

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
                    case "IN":
                        intervalo = Integer.valueOf(palabras[1]);
                        System.out.println("INTERVALO PREDEFINIDO: " + intervalo);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Copia todo el contenido de "predefinido" y lo pega reemplazando todo en "init"
     */
    public static void restablecerAjustes() {
        Path ini = Path.of("src/main/resources/org/example/adcontrol/Fichero Inicio/init");
        Path predefinido = Path.of("src/main/resources/org/example/adcontrol/Fichero Inicio/predefinido");
        try {
            //Obtiene todo el contenido del archivo "predefinido"
            byte[] contenido = Files.readAllBytes(predefinido);

            //Pega el contenido archivo "predefinido" en el archivo "init"
            Files.write(ini, contenido, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);

            System.out.println("Contenido copiado y reemplazado correctamente.");

            cargarDatos();
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
    }

    /**
     * Método que actualiza los datos el fichero "init" por completo
     */
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

            escritor.write("IN " + intervalo);
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    //Getters de las variables
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

    public Integer getIntervalo() {
        return intervalo;
    }

    //Setters de las variables, actualizandose también en el documento
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

    public void setIntervalo(Integer intervalo) {
        InfoInit.intervalo = intervalo;
        escribirDatos();
    }
}

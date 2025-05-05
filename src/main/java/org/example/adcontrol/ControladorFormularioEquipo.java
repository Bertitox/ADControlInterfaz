package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDAulas;
import BBDD.DAO.CRUDInfoSistema;
import BBDD.DTO.Aula_Equipo;
import BBDD.DTO.Aulas;
import BBDD.DTO.InformacionSistema;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControladorFormularioEquipo {
    @FXML
    private TextField campoArquitectura;

    @FXML
    private TextField campoCPU;

    @FXML
    private DatePicker campoFecha;

    @FXML
    private TextField campoHora;

    @FXML
    private TextField campoIP;

    @FXML
    private TextField campoKernel;

    @FXML
    private TextField campoMAC;

    @FXML
    private TextField campoMemDisp;

    @FXML
    private TextField campoMemTotal;

    @FXML
    private TextField campoNodo;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoSO;

    @FXML
    private TextField campoUsoCPU;

    @FXML
    private TextField campoVersion;

    String aulaActual;

    LocalTime horaActual;
    LocalDate fechaActual;

    Boolean leerJson = false;

    public void setLeerJson(Boolean leerJson) {
        this.leerJson = leerJson;
        if(leerJson){
            System.out.println("Leyendo json");
            cargarDatosDesdeJSON();
            leerJson = false;
        }
    }

    @FXML
    void initialize() {
        campoFecha.setValue(LocalDate.now());
        // Obtener hora actual
        horaActual = LocalTime.now();
        fechaActual = LocalDate.now();

        // Formatear la hora como "HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);

        // Asignarla al TextField
        campoHora.setText(horaFormateada);
    }

    @FXML
    void crearEquipo(ActionEvent event) {
        CRUDInfoSistema crudinfoSistema = new CRUDInfoSistema();
        CRUDAula_Equipo crudaula_equipo = new CRUDAula_Equipo();

        InformacionSistema informacionSistema = new InformacionSistema();
        if(!campoNombre.getText().isEmpty() || !campoMAC.getText().isEmpty()) {
            informacionSistema.setNombre(campoNombre.getText());
            informacionSistema.setArquitectura(campoArquitectura.getText());
            informacionSistema.setUsoCpu(campoUsoCPU.getText());
            informacionSistema.setProcesador(campoCPU.getText());

            if(campoMemTotal.getText().isEmpty()){
                informacionSistema.setMemTotal(0);
            }else{
                informacionSistema.setMemTotal(Integer.valueOf(campoMemTotal.getText()));
            }

            if(campoMemDisp.getText().isEmpty()){
                informacionSistema.setMemDisp(0);
            }else{
                informacionSistema.setMemDisp(Integer.valueOf(campoMemTotal.getText()));
            }
            informacionSistema.setUltHora(horaActual);
            informacionSistema.setIp(campoIP.getText());
            informacionSistema.setUltFecha(fechaActual);
            informacionSistema.setVersion(campoVersion.getText());
            informacionSistema.setSo(campoSO.getText());
            informacionSistema.setNombreNodo(campoNodo.getText());
            informacionSistema.setMac(campoMAC.getText());
            informacionSistema.setReleasee(campoKernel.getText());
            crudinfoSistema.insert(informacionSistema);

            CRUDAulas crudaulas = new CRUDAulas();
            Aulas aula = new Aulas();
            try{
                aula = crudaulas.getbyReferencia(aulaActual);

            }catch (Exception e){
                System.out.println("error al buscar el aula por la referencia");
            }

            Aula_Equipo aula_equipo = new Aula_Equipo(aula, informacionSistema);
            crudaula_equipo.insertAula(aula_equipo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Equipo creado con éxito", ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "El equipo debe tener al menos un nombre", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }

    private void cargarDatosDesdeJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/org/example/adcontrol/ResultadoScript/system_info.json");
        try {
            JsonNode root = mapper.readTree(jsonFile);

            campoNombre.setText(root.path("nombre_equipo").asText());
            campoNodo.setText(root.path("Nombre_Nodo").asText());
            campoSO.setText(root.path("Sistema_Operativo").asText());
            campoMAC.setText(root.path("MAC").asText());

            campoCPU.setText(root.path("Procesador").asText());
            campoUsoCPU.setText(root.path("Uso_CPU").asText());
            campoMemTotal.setText(root.path("Memoria_Total_MB").asText());
            campoMemDisp.setText(root.path("Memoria_Disponible_MB").asText());

            campoKernel.setText(root.path("Release").asText());
            campoVersion.setText(root.path("Version").asText());
            campoArquitectura.setText(root.path("Arquitectura").asText());
            campoIP.setText(root.path("IP").asText());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        // Intentar borrar el archivo tras leerlo
        if (jsonFile.exists()) {
            if (jsonFile.delete()) {
                System.out.println("Archivo JSON eliminado correctamente.");
            } else {
                System.err.println("No se pudo eliminar el archivo JSON.");
            }
        }
    }
    }
}

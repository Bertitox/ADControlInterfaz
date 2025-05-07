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
import javafx.scene.control.*;

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

    @FXML
    private Label labelPrincipal;

    @FXML
    private Label labelSecundario;

    String aulaActual;

    LocalTime horaActual;
    LocalDate fechaActual;

    Boolean leerJson = false;
    Boolean Modificar = false;

    public void setLeerJson(Boolean leerJson) {
        this.leerJson = leerJson;
        if (leerJson) {
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
        CRUDAulas crudaulas = new CRUDAulas();

        if (!campoNombre.getText().isEmpty() || !campoMAC.getText().isEmpty() || campoIP.getText().isEmpty()) {
            InformacionSistema informacionSistema = null;
            if (!Modificar) {
                informacionSistema = new InformacionSistema();
            } else {
                informacionSistema = crudinfoSistema.getByMac(campoMAC.getText());
            }

            informacionSistema.setNombre(campoNombre.getText());
            informacionSistema.setArquitectura(campoArquitectura.getText());
            informacionSistema.setUsoCpu(campoUsoCPU.getText());
            informacionSistema.setProcesador(campoCPU.getText());
            informacionSistema.setUltHora(horaActual);
            informacionSistema.setIp(campoIP.getText());
            informacionSistema.setUltFecha(fechaActual);
            informacionSistema.setVersion(campoVersion.getText());
            informacionSistema.setSo(campoSO.getText());
            informacionSistema.setNombreNodo(campoNodo.getText());
            informacionSistema.setMac(campoMAC.getText());
            informacionSistema.setReleasee(campoKernel.getText());

            if (campoMemTotal.getText().isEmpty()) {
                informacionSistema.setMemTotal(0);
            } else {
                informacionSistema.setMemTotal(Integer.valueOf(campoMemTotal.getText()));
            }

            if (campoMemDisp.getText().isEmpty()) {
                informacionSistema.setMemDisp(0);
            } else {
                informacionSistema.setMemDisp(Integer.valueOf(campoMemTotal.getText()));
            }

            Aulas aula = new Aulas();
            try {
                aula = crudaulas.getbyReferencia(aulaActual);

            } catch (Exception e) {
                System.out.println("error al buscar el aula por la referencia");
            }


            if (!Modificar) {
                System.out.println("Creando");
                crudinfoSistema.insert(informacionSistema);
                Aula_Equipo aula_equipo = new Aula_Equipo(aula, informacionSistema);
                crudaula_equipo.insertAula(aula_equipo);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Equipo creado con éxito", ButtonType.OK);
                alert.showAndWait();
            } else {
                System.out.println("Modificando");
                crudinfoSistema.update(informacionSistema);
                Modificar = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Equipo modificado con éxito", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
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
        } finally {
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

    public void setCampoArquitectura(String campoArquitectura) {
        this.campoArquitectura.setText(campoArquitectura);
    }

    public void setCampoCPU(String campoCPU) {
        this.campoCPU.setText(campoCPU);
    }

    public void setCampoFecha(LocalDate campoFecha) {
        this.campoFecha.setValue(campoFecha);
    }

    public void setCampoHora(String campoHora) {
        this.campoHora.setText(campoHora);
    }

    public void setCampoIP(String campoIP) {
        this.campoIP.setText(campoIP);
    }

    public void setCampoKernel(String campoKernel) {
        this.campoKernel.setText(campoKernel);
    }

    public void setCampoMAC(String campoMAC) {
        this.campoMAC.setText(campoMAC);
    }

    public void setCampoMemDisp(String campoMemDisp) {
        this.campoMemDisp.setText(campoMemDisp);
    }

    public void setCampoMemTotal(String campoMemTotal) {
        this.campoMemTotal.setText(campoMemTotal);
    }

    public void setCampoNodo(String campoNodo) {
        this.campoNodo.setText(campoNodo);
    }

    public void setCampoNombre(String campoNombre) {
        this.campoNombre.setText(campoNombre);
    }

    public void setCampoSO(String campoSO) {
        this.campoSO.setText(campoSO);
    }

    public void setCampoUsoCPU(String campoUsoCPU) {
        this.campoUsoCPU.setText(campoUsoCPU);
    }

    public void setCampoVersion(String campoVersion) {
        this.campoVersion.setText(campoVersion);
    }

    public void setModificar(Boolean modificar) {
        Modificar = modificar;
        if(Modificar) {
            labelPrincipal.setText("Formulario para modificar un equipo");
            labelSecundario.setText("Modifica los datos del equipo para actualizarlo en la base de datos.");
        }
    }
}

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase controladora del formulario para crear un nuevo equipo.
 *
 * @author Daniel García y Alberto
 * @version 1.0
 */
public class ControladorFormularioEquipo {

    //Elementos FXML que se usarán.
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

    @FXML
    private Pane ventanaFormulario;

    @FXML
    private Button boton;

    String aulaActual;

    LocalTime horaActual;
    LocalDate fechaActual;

    Boolean leerJson = false;
    Boolean Modificar = false;

    /**
     * Método initialize que inicia los componentes y variables necesarias de la clase.
     */
    @FXML
    void initialize() {
        campoFecha.setValue(LocalDate.now());
        //Obtener hora actual
        horaActual = LocalTime.now();
        fechaActual = LocalDate.now();

        //Formatear la hora como "HH:mm:ss"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = horaActual.format(formatter);

        //Asignarla al TextField
        campoHora.setText(horaFormateada);
    }

    /**
     * Método que se encarga de cargar los datos en el formulario desde el json.
     *
     * @param leerJson Valor Booleano que recibe el método
     */
    public void setLeerJson(Boolean leerJson) {
        this.leerJson = leerJson;
        if (leerJson) {
            System.out.println("Leyendo json");
            cargarDatosDesdeJSON(); //Llamada al método que carga los datos desde el json.
            leerJson = false;
        }
    }

    /**
     * Método que se encarga de crear equipo.
     *
     * @param event Evento del tipo ActionEvent que espera el método.
     */
    @FXML
    void crearEquipo(ActionEvent event) {
        CRUDInfoSistema crudinfoSistema = new CRUDInfoSistema();
        CRUDAula_Equipo crudaula_equipo = new CRUDAula_Equipo();
        CRUDAulas crudaulas = new CRUDAulas();

        //Si los campos obligatorios están rellenos, entonces creamos el equipo.
        if (!campoNombre.getText().isEmpty() || !campoMAC.getText().isEmpty() || campoIP.getText().isEmpty()) { //Validamos que los campos obligatorios estén rellenos.
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
                Stage stage = (Stage) ventanaFormulario.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Modificando");
                crudinfoSistema.update(informacionSistema);
                Modificar = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Equipo modificado con éxito", ButtonType.OK);
                alert.showAndWait();
                Stage stage = (Stage) ventanaFormulario.getScene().getWindow();
                stage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El equipo debe tener al menos un nombre", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Método que se encarga del cambiar el aula al actual.
     *
     * @param aulaActual String con el nombre del aula actual.
     */
    public void setAulaActual(String aulaActual) {
        this.aulaActual = aulaActual;
    }

    /**
     * Método que carga los datos desde el json.
     */
    private void cargarDatosDesdeJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/org/example/adcontrol/ResultadoScript/system_info.json");//Localizamos el fichero json.
        try {
            JsonNode root = mapper.readTree(jsonFile);

            //Rellenamos los campos del formulario a partír de los datos del json.
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
            //Se borra el archivo tras leerlo.
            if (jsonFile.exists()) {
                if (jsonFile.delete()) {
                    System.out.println("Archivo JSON eliminado correctamente.");
                } else {
                    System.err.println("No se pudo eliminar el archivo JSON.");
                }
            }
        }
    }

    /**
     * Establece el valor del campo de arquitectura en la interfaz.
     *
     * @param campoArquitectura Texto que representa la arquitectura del sistema.
     */
    public void setCampoArquitectura(String campoArquitectura) {
        this.campoArquitectura.setText(campoArquitectura);
    }

    /**
     * Establece el valor del campo de CPU en la interfaz.
     *
     * @param campoCPU Texto que representa el modelo o tipo de CPU.
     */
    public void setCampoCPU(String campoCPU) {
        this.campoCPU.setText(campoCPU);
    }

    /**
     * Establece la fecha en el campo correspondiente de la interfaz.
     *
     * @param campoFecha Fecha a mostrar.
     */
    public void setCampoFecha(LocalDate campoFecha) {
        this.campoFecha.setValue(campoFecha);
    }

    /**
     * Establece el valor del campo de hora en la interfaz.
     *
     * @param campoHora Texto que representa la hora del sistema.
     */
    public void setCampoHora(String campoHora) {
        this.campoHora.setText(campoHora);
    }

    /**
     * Establece la dirección IP en el campo correspondiente de la interfaz.
     *
     * @param campoIP Dirección IP del equipo.
     */
    public void setCampoIP(String campoIP) {
        this.campoIP.setText(campoIP);
    }

    /**
     * Establece la versión del kernel en el campo correspondiente de la interfaz.
     *
     * @param campoKernel Texto que representa el kernel del sistema operativo.
     */
    public void setCampoKernel(String campoKernel) {
        this.campoKernel.setText(campoKernel);
    }

    /**
     * Establece la dirección MAC en el campo correspondiente de la interfaz.
     *
     * @param campoMAC Dirección MAC del dispositivo de red.
     */
    public void setCampoMAC(String campoMAC) {
        this.campoMAC.setText(campoMAC);
    }

    /**
     * Establece la memoria disponible en el campo correspondiente de la interfaz.
     *
     * @param campoMemDisp Texto que representa la cantidad de memoria disponible.
     */
    public void setCampoMemDisp(String campoMemDisp) {
        this.campoMemDisp.setText(campoMemDisp);
    }

    /**
     * Establece la memoria total en el campo correspondiente de la interfaz.
     *
     * @param campoMemTotal Texto que representa la memoria total del sistema.
     */
    public void setCampoMemTotal(String campoMemTotal) {
        this.campoMemTotal.setText(campoMemTotal);
    }

    /**
     * Establece el nombre del nodo en el campo correspondiente de la interfaz.
     *
     * @param campoNodo Nombre del nodo o nombre del host.
     */
    public void setCampoNodo(String campoNodo) {
        this.campoNodo.setText(campoNodo);
    }

    /**
     * Establece el nombre del dispositivo en el campo correspondiente de la interfaz.
     *
     * @param campoNombre Nombre del equipo o dispositivo.
     */
    public void setCampoNombre(String campoNombre) {
        this.campoNombre.setText(campoNombre);
    }

    /**
     * Establece el nombre del sistema operativo en el campo correspondiente de la interfaz.
     *
     * @param campoSO Nombre del sistema operativo.
     */
    public void setCampoSO(String campoSO) {
        this.campoSO.setText(campoSO);
    }

    /**
     * Establece el uso actual de CPU en el campo correspondiente de la interfaz.
     *
     * @param campoUsoCPU Texto que representa el porcentaje de uso de la CPU.
     */
    public void setCampoUsoCPU(String campoUsoCPU) {
        this.campoUsoCPU.setText(campoUsoCPU);
    }

    /**
     * Establece la versión del sistema operativo en el campo correspondiente de la interfaz.
     *
     * @param campoVersion Texto que representa la versión del sistema operativo.
     */
    public void setCampoVersion(String campoVersion) {
        this.campoVersion.setText(campoVersion);
    }

    /**
     * Método que se encarga de cambiar el texto de los componentes para modificar equipos.
     *
     * @param modificar Parámetro booleano de modificación.
     */
    public void setModificar(Boolean modificar) {
        Modificar = modificar;
        if (Modificar) {
            labelPrincipal.setText("Formulario para modificar un equipo");
            labelSecundario.setText("Modifica los datos del equipo para actualizarlo en la base de datos.");
            //Cambiar el texto del botón
            boton.setText("Modificar equipo");
        }
    }
}

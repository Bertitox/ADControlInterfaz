package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DAO.CRUDAulas;
import BBDD.DAO.CRUDInfoSistema;
import BBDD.DTO.Aula_Equipo;
import BBDD.DTO.Aulas;
import BBDD.DTO.InformacionSistema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
}

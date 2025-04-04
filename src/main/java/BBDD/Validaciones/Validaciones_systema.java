package BBDD.Validaciones;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DTO.Aula_Equipo;

/**
 * Clase que realiza las validaciones de la app.
 * @author Daniel y Alberto
 * @version 1.0
 */
public class Validaciones_systema {

    /**
     * Constructor por defecto de la clase
     */
    public Validaciones_systema() {
    }

    /**
     * Método que verifica que solo existe un ordenador
     * @param id_infoSistema referencia al campo int id_infoSistema
     * @return devuelve un valor boolean con el resultado de la operación
     */
    public boolean onlyOneDevice(int id_infoSistema) {
        CRUDAula_Equipo crudAulaEquipo = new CRUDAula_Equipo();
        boolean resultado = false;
        for (Aula_Equipo a: crudAulaEquipo.readAllAulas()){
            if (a.getIdInformacionSistema().getId().equals(id_infoSistema)) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Método que comprueva si existe un Aula en la BBDD
     * @param referencia referencia al Aula que se quiere comprobar
     * @return retorna True o False dependiendo de si el aula existe o no
     */
    public boolean existeAula(String referencia) {
        CRUDAula_Equipo crudAulaEquipo = new CRUDAula_Equipo();
        boolean exist = false;

        for (Aula_Equipo a: crudAulaEquipo.readAllAulas()){
            if (a.getReferencia().equals(referencia)) {
                exist = true;
            }
        }
        return exist;
    }

    /**
    public static void main(String[] args) {
        Validaciones_systema validaciones = new Validaciones_systema();
        System.out.println(validaciones.existeAula("PRUEBA0"));
    }*/

}

package BBDD.Validaciones;

import BBDD.DAO.CRUDAula;
import BBDD.DTO.Aula;

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
        CRUDAula crudAula = new CRUDAula();
        boolean resultado = false;
        for (Aula a: crudAula.readAllAulas()){
            if (a.getIdInformacionSistema().getId().equals(id_infoSistema)) {
                resultado = true;
            }
        }
        return resultado;
    }
}

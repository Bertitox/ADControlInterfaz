package BBDD.Validaciones;

import BBDD.DAO.CRUDAula;
import BBDD.DTO.Aula;

public class Validaciones_systema {
    public Validaciones_systema() {
    }

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

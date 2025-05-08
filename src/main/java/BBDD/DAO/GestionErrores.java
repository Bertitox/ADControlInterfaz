package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Errores;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GestionErrores {
    Conexion conexion = Conexion.getInstancia();

    // Aquí obtienes el EntityManager desde la clase CONEXION
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    public GestionErrores() {
    }

    public List<Errores> readAllErrores() {
        List<Errores> errores = em.createQuery("Select e from Errores e").getResultList();
        return errores;
    }

    public Map<String, String> erroresMap(){
        Map<String, String> errores = new HashMap<String, String>();

        List<Errores> erroresList = em.createQuery("select e from Errores e").getResultList();

        for (Errores error: erroresList){
            errores.put(error.getCodigoError(), error.getDescripcionError());
        }
        return errores;
    }

//    public static void main(String[] args) {
//        GestionErrores g = new GestionErrores();
//      for (Map.Entry<String, String> entry: g.erroresMap().entrySet()){
//          System.out.println(entry.getKey() + ": " + entry.getValue());
//      }
//        for(Errores error: g.readAllErrores()){
//            System.out.println(error.getCodigoError());
//        }
//    }
}

package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Errores;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase gestora de la entidad {@link Errores}
 * @author Daniel García y Alberto
 * @version 1.0
 */
public class GestionErrores {

    //Objetos necesarios para realizar la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    /**
     * Constructor por defecto de la clase {@link GestionErrores}
     */
    public GestionErrores() {
    }

    /**
     * Método el cual retorna una lista con todos los errores almacenados en BBDD.
     * @return Retorna una lista con todos los errores de la BBDD.
     */
    public List<Errores> readAllErrores() {
        List<Errores> errores = em.createQuery("Select e from Errores e").getResultList();
        return errores;
    }

    /**
     * Método que se encarga de devolver un mapa con los errores y sus descripciones.
     * @return Retorna un Mapa con los códigos de error y sus descripciónes.
     */
    public Map<String, String> erroresMap(){
        Map<String, String> errores = new HashMap<String, String>();

        List<Errores> erroresList = em.createQuery("select e from Errores e").getResultList(); //Consulta que devuelve una lista con los erroes.

        for (Errores error: erroresList){ //Recorre la lista e inseta en el mapa cada error con su descripción.
            errores.put(error.getCodigoError(), error.getDescripcionError()); //Insertamos en el mapa cada código de error con su descripción.
        }
        return errores;//Retorna el mapa.
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

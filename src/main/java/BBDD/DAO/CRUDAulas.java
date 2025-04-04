package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula_Equipo;
import BBDD.DTO.Aulas;
import BBDD.Excepciones.AulaNotFoundException;
import BBDD.Validaciones.Validaciones_systema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class CRUDAulas {

    //Inicialización de los objetos que participan en la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
    EntityManager gestorEntidad = conexion.getGestor();
    EntityTransaction transaction = gestorEntidad.getTransaction();


    /**
     * Constructor por defecto de la clase {@link CRUDAulas}
     */
    public CRUDAulas() {
    }

    /**
     * Método que lleva a cabo la insercción de un {@link Aulas}
     *
     * @param aula Objeto {@link Aula_Equipo}
     */
    public void insertAula(Aulas aula) {
            transaction.begin();
            gestorEntidad.persist(aula);
            transaction.commit();

    }

    /**
     * Método que lleva a cabo la actualización de un {@link Aulas}
     * @param aula Objeto {@link Aula_Equipo}
     */
    public void updateAula(Aulas aula) {
        transaction.begin();
        gestorEntidad.merge(aula);
        transaction.commit();
    }

    /**
     * Método que lleva a cabo la eliminación de un {@link Aulas}
     * @param aula Objeto {@link Aulas}
     */
    public void deleteAula(Aulas aula) {
        transaction.begin();
        gestorEntidad.remove(aula);
        transaction.commit();
    }

    /**
     * Método que retorna una lista de todas las Aulas
     * @return retorna una {@link List <Aulas>}
     */
    public List<Aulas> readAllAulas() {
        String consulta = "Select aula from Aulas aula";
        List<Aulas> aulaEquipos = gestorEntidad.createQuery(consulta, Aulas.class).getResultList();
        return aulaEquipos;
    }


    /**
     * Método que busca si un aula existe por su referencia
     * @param referencia Referencia del aula que está comprobando
     * @return retorna un boolean con el resultado de la operación
     */
    public boolean comprobarAula(String referencia) {
        for (Aulas aula : readAllAulas()) {
            if (aula.getReferencia().equals(referencia)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que retorna un {@link Aula_Equipo} por su referencia
     *
     * @param referencia String que referencia al {@link Aula_Equipo}
     * @return Retorna un {@link Aula_Equipo}
     * @throws AulaNotFoundException Excepción cuando el {@link Aula_Equipo} que busca no existe
     */
    public Aulas getbyReferencia(String referencia) throws AulaNotFoundException {
        for (Aulas aulaEquipo : readAllAulas()) {
            if (aulaEquipo.getReferencia().equals(referencia)) {
                return aulaEquipo;
            }
        }
        throw new AulaNotFoundException("Aula no encontrada con la referencia: " + referencia);
    }

}

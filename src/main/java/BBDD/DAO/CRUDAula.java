package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula;
import BBDD.DTO.InformacionSistema;
import BBDD.Excepciones.AulaNotFoundException;
import BBDD.Validaciones.Validaciones_systema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Case que realiza las operaciónes básicas para la Entidad Aula
 * @author Alberto y Daniel
 * @version 1.0
 */
public class CRUDAula {

    //Inicialización de los objetos que participan en la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
    EntityManager gestorEntidad = conexion.getGestor();
    EntityTransaction transaction = gestorEntidad.getTransaction();

    //Clase de validaciones
    Validaciones_systema validaciones = new Validaciones_systema();

    /**
     * Constructor por defecto de la clase {@link CRUDAula}
     */
    public CRUDAula() {
    }

    /**
     * Método que lleva a cabo la insercción de un {@link Aula}
     *
     * @param aula Objeto {@link Aula}
     */
    public void insertAula(Aula aula) {
        if (!(validaciones.onlyOneDevice(aula.getIdInformacionSistema().getId())) && (!(validaciones.existeAula(aula.getReferencia())))) {
            transaction.begin();
            gestorEntidad.persist(aula);
            transaction.commit();
        }else {
            throw new RuntimeException("No se puede agregar el aula: Equipo ya existente en el aula");
        }
    }

    /**
     * Método que lleva a cabo la actualización de un {@link Aula}
     * @param aula Objeto {@link Aula}
     */
    public void updateAula(Aula aula) {
        transaction.begin();
        gestorEntidad.merge(aula);
        transaction.commit();
    }

    /**
     * Método que lleva a cabo la eliminación de un {@link Aula}
     * @param aula Objeto {@link Aula}
     */
    public void deleteAula(Aula aula) {
        transaction.begin();
        gestorEntidad.remove(aula);
        transaction.commit();
    }

    /**
     * Método que retorna una lista de todas las Aulas
     * @return retorna una {@link List<Aula>}
     */
    public List<Aula> readAllAulas() {
        String consulta = "Select aula from Aula aula";
        List<Aula> aulas = gestorEntidad.createQuery(consulta, Aula.class).getResultList();
        return aulas;
    }

    /**
     * Método que retorna un {@link Aula} por su referencia
     *
     * @param referencia String que referencia al {@link Aula}
     * @return Retorna un {@link Aula}
     * @throws AulaNotFoundException Excepción cuando el {@link Aula} que busca no existe
     */
    public Aula getbyReferencia(String referencia) throws AulaNotFoundException {
        for (Aula aula : readAllAulas()) {
            if (aula.getReferencia().equals(referencia)) {
                return aula;
            }
        }
        throw new AulaNotFoundException("Aula no encontrada con la referencia: " + referencia);
    }

    /**
     * Método que muestra todas las Aulas
     */
    public void mostrarAulas(){
        for (Aula a : readAllAulas()) {
            System.out.println(a);
        }
    }
}

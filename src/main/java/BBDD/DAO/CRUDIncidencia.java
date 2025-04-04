package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula_Equipo;
import BBDD.DTO.Aulas;
import BBDD.DTO.Incidencia;
import BBDD.Excepciones.AulaNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.*;

/**
 * Clase {@link CRUDIncidencia} que se encarga de realizar las operaciones básicas para gestionar incidencias
 * @author Alberto y Daniel
 * @version 1.5
 */

public class CRUDIncidencia {

    //Objetos necesarios para realizar la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    /**
     * Constructor por defecto de la clase {@link CRUDIncidencia}
     */
    public CRUDIncidencia() {
    }

    /**
     * Método que se encarga de insertar una nueva {@link Incidencia}
     * @param incidencia Representa una {@link Incidencia}
     */
    public void insertIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.persist(incidencia);
        em.getTransaction().commit();
    }

    /**
     * Método que se encarga de actualizar cada {@link Incidencia}
     * @param incidencia Representa una {@link Incidencia}
     */
    public void updateIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.merge(incidencia);
        em.getTransaction().commit();
    }

    /**
     * Método que se encarga de eleminar cada {@link Incidencia}
     * @param incidencia Representa una {@link Incidencia}
     */
    public void deleteIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.remove(incidencia);
        em.getTransaction().commit();
    }

    /**
     * Método que retorna todas las incidencias
     * @return devuelve una List<Incidencia>
     */
    public List<Incidencia> getAllIncidencias() {
        List<Incidencia> incidencias;
        String consulta = "Select incidencia from Incidencia incidencia";
        incidencias = em.createQuery(consulta, Incidencia.class).getResultList();
        return incidencias;
    }

    /**
     * Método que muestra todas las incidencias de la BBDD
     */
    public void mostrarIncidencias(){
        for (Incidencia incidencia : getAllIncidencias()) {
            System.out.println(incidencia);
        }
    }

    /**
     * Método que se encarga de contar el número de incidencias que existen por {@link Aulas}
     * @param referencia Referencia a un {@link Aulas}
     * @return retorna un entero que contiene el número de incidencias por aula
     * @throws AulaNotFoundException excepción personalizada que se lanza cuando no existe el aula
     */
    public int getNumIncidenciasAulas(String referencia) throws AulaNotFoundException {
        int incidencias = 0;
        CRUDAulas crudAulas = new CRUDAulas();
        Aulas a = crudAulas.getbyReferencia(referencia);
        //System.out.println(a.getReferencia());

        for (Incidencia incidencia : getAllIncidencias()) {
             if (incidencia.getIdAula().getReferencia().equals(a.getReferencia())){
                 incidencias++;
             }
        }
        return incidencias;
    }

    /**
     * Método que retorna una lList<Incidencia> por {@link Aula_Equipo}
     *
     * @param referencia referencia a un {@link Aula_Equipo}
     * @return retorna una List<Incidencia>
     * @throws AulaNotFoundException excepción personalizada que se lanza cuando no existe el aula
     */
    public List<Incidencia> getIncidenciasAulas(String referencia) throws AulaNotFoundException {
        List<Incidencia> incidencias = new ArrayList<>();
        CRUDAula_Equipo crudAulaEquipo = new CRUDAula_Equipo();
        Aula_Equipo a = crudAulaEquipo.getbyReferencia(referencia);

        for (Incidencia i : getAllIncidencias()) {
            if (i.getIdAula().getReferencia().equals(a.getReferencia())){
                incidencias.add(i);
            }
        }
        return incidencias;
    }

    /**
     * Método que retorna un conjunto formado por String's que representan el nombre de las aulas con incidencias
     *
     * @return Set<String> con el nombre de las aulas que tiene incidencias
     * @throws AulaNotFoundException excepción personalizada que se lanza cuando no existe el aula
     */
    public Set<String> getAulasIncidencias() throws AulaNotFoundException {
        Set<String> aulas = new LinkedHashSet<>();
        for (Incidencia incidencia : getAllIncidencias()) {
            aulas.add(incidencia.getIdAula().getReferencia());
        }
        return aulas;
    }

    /**
     * Método que se ecarga de recontar las incidencias
     * @return Retorna un int número de incidencias
     */
    public int numIncidencias() {
        return getAllIncidencias().size();
    }

    /**
     * Método que muestra las Aulas con incidencias
     * @throws AulaNotFoundException excepción personalizada que se lanza cuando no existe el aula
     */
    public void mostrarAulasIncidencias() throws AulaNotFoundException {
        for (String s : getAulasIncidencias()){
            System.out.println(s);
        }
    }

    /**
     * Método que muestra las incidencias filtrando por ID de {@link Aula_Equipo}
     * @param id Int que representa el id de un {@link Aula_Equipo}
     * @throws AulaNotFoundException Excepción de usuario {@link AulaNotFoundException} que lanza el método
     */
    public void mostrarIncidenciasIdAulas(String id) throws AulaNotFoundException {
        Aula_Equipo a = em.find(Aula_Equipo.class, 1);
        for (Incidencia incidencia : getIncidenciasAulas(a.getReferencia().getReferencia())) {
            System.out.println(incidencia);
        }
    }

    /**
     * Método que devuelve una lista de incidencias según la referencia del aula
     * @param referencia String que hace referencia al Aula
     * @return List<Incidencia>
     * @throws AulaNotFoundException Excepción de usuario {@link AulaNotFoundException} que lanza el método
     */
    public List<Incidencia> incidenciasXAulas(String referencia) throws AulaNotFoundException {
        List<Incidencia> incidencias = new ArrayList<>();
        for (Incidencia i : getAllIncidencias()) {
            if (i.getIdAula().getReferencia().equals(referencia)){
                 incidencias.add(i);
            }
        }
        return incidencias;
    }

    /**
     * Método para Mostrar Incidencias Por Aula usando la referencia al Aula
     * @param referencia String que hace referencia al Aula
     * @throws AulaNotFoundException Excepción de usuario {@link AulaNotFoundException} que lanza el método
     */
    public void mostrarIncidenciasxAula(String referencia) throws AulaNotFoundException {
        for (Incidencia incidencia : incidenciasXAulas(referencia)) {
            System.out.println(incidencia);
        }
    }

    /**
     * Método que se ecarga de recontar las incidencias por Aula
     * @return Retorna un int número de incidencias por Aula
     */
    public int numIncidenciasAula(String referencia) throws AulaNotFoundException {
        return incidenciasXAulas(referencia).size();
    }

    public Integer numMaximoIcidenciasAula() throws AulaNotFoundException {
        Integer maxIncidencias  = Integer.parseInt(em.createNativeQuery("""
            SELECT MAX(incidencias_por_aula)
            FROM (
                SELECT COUNT(*) AS incidencias_por_aula
                FROM incidencias
                GROUP BY id_aula
            ) AS subconsulta;
        """).getSingleResult().toString());

        return maxIncidencias;
    }
    /*
    /**
     * Clase main para realizar pruebas
     * @param args arg del main
     * @throws AulaNotFoundException excepción personalizada para el aula
    */
    //public static void main(String[] args) throws AulaNotFoundException {
      //  CRUDIncidencia crudIncidencia = new CRUDIncidencia();
        //crudIncidencia.mostrarIncidenciasxAula("PRUEBA2");
        //System.out.println(crudIncidencia.numMaximoIcidenciasAula());
    //}
}

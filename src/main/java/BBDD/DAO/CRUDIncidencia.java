package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula;
import BBDD.DTO.Incidencia;
import BBDD.Excepciones.AulaNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CRUDIncidencia {

    Conexion conexion = new Conexion();

    // Aquí obtienes el EntityManager desde la clase CONEXION
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    public CRUDIncidencia() {
    }

    public void insertIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.persist(incidencia);
        em.getTransaction().commit();
    }

    public void updateIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.merge(incidencia);
        em.getTransaction().commit();
    }

    public void deleteIncidencia(Incidencia incidencia) {
        em.getTransaction().begin();
        em.remove(incidencia);
        em.getTransaction().commit();
    }

    public List<Incidencia> getAllIncidencias() {
        List<Incidencia> incidencias;
        String consulta = "Select incidencia from Incidencia incidencia";
        incidencias = em.createQuery(consulta, Incidencia.class).getResultList();
        return incidencias;
    }

    public void mostrarIncidencias(){
        for (Incidencia incidencia : getAllIncidencias()) {
            System.out.println(incidencia);
        }
    }

    public int getNumIncidenciasAulas(String referencia) throws AulaNotFoundException {
        int incidencias = 0;
        CRUDAula crudAula = new CRUDAula();
        Aula a = crudAula.getbyReferencia(referencia);
        //System.out.println(a.getReferencia());

        for (Incidencia incidencia : getAllIncidencias()) {
             if (incidencia.getIdAula().getReferencia().equals(a.getReferencia())){
                 incidencias++;
             }
        }
        return incidencias;
    }

    public Incidencia getIncidenciasAulas(String referencia) throws AulaNotFoundException {
        Incidencia incidencia = new Incidencia();
        CRUDAula crudAula = new CRUDAula();
        Aula a = crudAula.getbyReferencia(referencia);

        for (Incidencia i : getAllIncidencias()) {
            if (incidencia.getIdAula().equals(a.getId())){
                incidencia = i;
            }
        }
        return incidencia;
    }

    public Set<String> getAulasIncidencias() throws AulaNotFoundException {
        Set<String> aulas = new HashSet<>();
        for (Incidencia incidencia : getAllIncidencias()) {
            aulas.add(incidencia.getIdAula().getReferencia());
        }
        return aulas;
    }

    public void mostrarAulasIncidencias() throws AulaNotFoundException {
        for (String s : getAulasIncidencias()){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws AulaNotFoundException {
        CRUDIncidencia crudIncidencia = new CRUDIncidencia();
        //System.out.println(crudIncidencia.getNumIncidenciasAulas("PRUEBA3"));
        //crudIncidencia.mostrarAulasIncidencias();
    }
}

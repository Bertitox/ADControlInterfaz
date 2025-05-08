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
 * @version 2.0
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
     * Método que devuelve un {@link EntityManager}
     * @return Retorna un objeto del tipo {@link EntityManager}
     */
    public EntityManager getEntityManager(){
        return em;
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
        String consulta = "Select incidencia from Incidencia incidencia";//Consulta que muestra todas las incidencias.
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
     * Método que devuelve las 3 últimas incidencias registradas para un aula concreta (por id descendente)
     * @param referencia Referencia del aula
     * @return List<Incidencia> con las últimas 3 incidencias del aula o lista vacía si no hay
     * @throws AulaNotFoundException excepción personalizada si no existe el aula
     */
    public List<Incidencia> getUltimasTresIncidenciasAula(String referencia) throws AulaNotFoundException {
        CRUDAulas crudAulas = new CRUDAulas();
        Aulas aula = crudAulas.getbyReferencia(referencia);

        List<Incidencia> incidencias = em.createQuery(
                        "SELECT i FROM Incidencia i WHERE i.Aula.referencia = :referencia ORDER BY i.id DESC", Incidencia.class)
                .setParameter("referencia", referencia)
                .setMaxResults(3)
                .getResultList();

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
     * Método que se ecarga de recontar las incidencias por Aula.
     * @return Retorna un int número de incidencias por Aula.
     */
    public int numIncidenciasAula(String referencia) throws AulaNotFoundException {
        return incidenciasXAulas(referencia).size();
    }

    /**
     * Método que se encarga de obtener el número máximo de incidencias por Aula.
     * @return Retorna un {@link Integer} con el número máximo de incidencias por Aula.
     * @throws AulaNotFoundException Excepción personalizada de Aula no encontrada.
     */
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

    /**
     * Método que retorna la ultima fecha y hora de modificación por Aula.
     * @return Retorna un Mapa con la última fecha y hora por aula.
     */
    public Map<String, String[]> getUltimaFechaHoraPorAula() {
        //Consulta que se encarga de sacar la referencia del aula y su fecha y hora de última modificación.
        String sql = """
            SELECT a.referencia, i.ultFecha, i.ultHora
            FROM aula a
            JOIN informacion_sistema i ON a.id_informacion_sistema = i.id
            WHERE (a.referencia, i.ultFecha, i.ultHora) IN (
                SELECT a2.referencia, MAX(i2.ultFecha), MAX(i2.ultHora)
                FROM aula a2
                JOIN informacion_sistema i2 ON a2.id_informacion_sistema = i2.id
                GROUP BY a2.referencia
            )
        """;

        //El resultado de la lista se guarda en una Lista de objetos.
        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        //Mapa quie contiene la referencia al aula y un array de String que contiene la fecha y hora de última modificación.
        Map<String, String[]> resultMap = new HashMap<>();
        for (Object[] row : results) {
            //Rellenamos el mapa
            String referencia = (String) row[0];
            //Insertamos en las variables el contenido del array
            String ultFecha = row[1].toString();
            String ultHora = row[2].toString();
            //Insertamos los nuevos valores en el mapa
            resultMap.put(referencia, new String[]{ultFecha, ultHora});
        }

        return resultMap; //Devolvemos el mapa
    }

    /**
     * Método getter que devuelve la última fecha de modificación para un aula en específico.
     * @param referencia String con la referencia del aula.
     * @return Retorna un String con la última fecha dse modificación.
     * @throws AulaNotFoundException Excepción personalizada si no encuentra el aula.
     */
    public String getUltFechaMod(String referencia) throws AulaNotFoundException {
        String[] datos = getUltimaFechaHoraPorAula().get(referencia);
        if (datos != null && datos[0] != null) {
            return datos[0];
        }
        return null;
    }

    /**
     * Método getter que devuelve la última hora de modificación para un aula en específico.
     * @param referencia String con la referencia del aula.
     * @return Retorna un String con la última hora dse modificación.
     * @throws AulaNotFoundException Excepción personalizada si no encuentra el aula.
     */
    public String getUltHoraMod(String referencia) throws AulaNotFoundException {
        String[] datos = getUltimaFechaHoraPorAula().get(referencia);
        if (datos != null && datos[1] != null) {
            return datos[1];
        }
        return null;
    }

    /**
     * Método getter que se encarga de devolver el número de equipos con incidencias por aula.
     * @param referencia String que representa la referencia a un aula.
     * @return Retorna un entero con el número de equipos con incidencias.
     * @throws AulaNotFoundException
     */
    public int getEquipoIncidenciasXAula(String referencia) throws AulaNotFoundException {
        Set<String> aulasConIncidencias = getAulasIncidencias();
        for (String aula : aulasConIncidencias) {
            if (aula.equals(referencia)) {
                //Si existe el aula devuelve el número de equipos con incidencias.
                return Integer.parseInt(em.createNativeQuery("select COUNT(Distinct id_informacion_sistema) from incidencias where id_aula ='"+referencia+"' group by id_aula;").getSingleResult().toString());
            }
        }
        //Si no existe el aula devuelve 0
        return 0;
    }

    /**
     * Método que devuelve el número de incidencias asociadas a un equipo concreto
     * @param idEquipo el ID de {@link BBDD.DTO.InformacionSistema}
     * @return número de incidencias para ese equipo
     */
    public int getNumIncidenciasEquipo(int idEquipo) {
        String sql = "SELECT COUNT(*) FROM incidencias WHERE id_informacion_sistema = :idEquipo";
        Object resultado = em.createNativeQuery(sql)
                .setParameter("idEquipo", idEquipo)
                .getSingleResult();
        return Integer.parseInt(resultado.toString());
    }

//    /**
//     * Clase main para realizar pruebas
//     * @param args arg del main
//     * @throws AulaNotFoundException excepción personalizada para el aula
//    */
//
// public static void main(String[] args) throws AulaNotFoundException {
//     CRUDIncidencia crudIncidencia = new CRUDIncidencia();
//     //crudIncidencia.mostrarIncidenciasxAula("PRUEBA2");
//     //System.out.println(crudIncidencia.numMaximoIcidenciasAula());
//
//       Map<String, String[]> datos = crudIncidencia.getUltimaFechaHoraPorAula();
//       for (Map.Entry<String, String[]> entry : datos.entrySet()) {
//           System.out.println("Referencia: " + entry.getKey());
//           System.out.println("Fecha: " + entry.getValue()[0]);
//           System.out.println("Hora: " + entry.getValue()[1]);
//           System.out.println("------------");
//       }
//       System.out.println(crudIncidencia.getUltHoraMod("PRUEBA6"));
//       System.out.println(crudIncidencia.getUltFechaMod("PRUEBA6"));
//     System.out.println(crudIncidencia.getEquipoIncidenciasXAula("DAM 2"));
// }
}

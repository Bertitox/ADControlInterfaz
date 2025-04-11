package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula_Equipo;
import BBDD.Excepciones.AulaNotFoundException;
import BBDD.Validaciones.Validaciones_systema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Case que realiza las operaciónes básicas para la Entidad Aula
 * @author Alberto y Daniel
 * @version 1.0
 */
public class CRUDAula_Equipo {

    //Inicialización de los objetos que participan en la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
    EntityManager gestorEntidad = conexion.getGestor();
    EntityTransaction transaction = gestorEntidad.getTransaction();

    //Clase de validaciones
    Validaciones_systema validaciones = new Validaciones_systema();

    /**
     * Constructor por defecto de la clase {@link CRUDAula_Equipo}
     */
    public CRUDAula_Equipo() {
    }

    /**
     * Método que lleva a cabo la insercción de un {@link Aula_Equipo}
     *
     * @param aulaEquipo Objeto {@link Aula_Equipo}
     */
    public void insertAula(Aula_Equipo aulaEquipo) {
        if (!(validaciones.onlyOneDevice(aulaEquipo.getIdInformacionSistema().getId()))) {
            transaction.begin();
            gestorEntidad.persist(aulaEquipo);
            transaction.commit();
        }else {
            throw new RuntimeException("No se puede agregar el aula: Equipo ya existente en el aula");
        }
    }

    /**
     * Método que lleva a cabo la actualización de un {@link Aula_Equipo}
     * @param aulaEquipo Objeto {@link Aula_Equipo}
     */
    public void updateAula(Aula_Equipo aulaEquipo) {
        transaction.begin();
        gestorEntidad.merge(aulaEquipo);
        transaction.commit();
    }

    /**
     * Método que lleva a cabo la eliminación de un {@link Aula_Equipo}
     * @param aulaEquipo Objeto {@link Aula_Equipo}
     */
    public void deleteAula(Aula_Equipo aulaEquipo) {
        transaction.begin();
        gestorEntidad.remove(aulaEquipo);
        transaction.commit();
    }

    /**
     * Método que retorna una lista de todas las Aulas
     * @return retorna una {@link List< Aula_Equipo >}
     */
    public List<Aula_Equipo> readAllAulas() {
        String consulta = "Select aula from Aula_Equipo aula";
        List<Aula_Equipo> aulaEquipos = gestorEntidad.createQuery(consulta, Aula_Equipo.class).getResultList();
        return aulaEquipos;
    }

    /**
     * Método que retorna un {@link Aula_Equipo} por su referencia
     *
     * @param referencia String que referencia al {@link Aula_Equipo}
     * @return Retorna un {@link Aula_Equipo}
     * @throws AulaNotFoundException Excepción cuando el {@link Aula_Equipo} que busca no existe
     */
    public Aula_Equipo getbyReferencia(String referencia) throws AulaNotFoundException {
        for (Aula_Equipo aulaEquipo : readAllAulas()) {
            if (aulaEquipo.getReferencia().equals(referencia)) {
                return aulaEquipo;
            }
        }
        throw new AulaNotFoundException("Aula no encontrada con la referencia: " + referencia);
    }

    /**
     * Método que muestra todas las Aulas
     */
    public void mostrarAulas(){
        for (Aula_Equipo a : readAllAulas()) {
            System.out.println(a);
        }
    }

    /**
     * Método que devuelve el número de equipos por aula.
     * @param referencia referencia del aula a consultar.
     * @return devuelve un entero con el número de equipos por aula.
     */
    public String numEquiposXAula(String referencia) {
        try {
            //Consulta que devuelve el num de equipos por aula
            Object result = gestorEntidad.createNativeQuery("""
            SELECT COUNT(id_informacion_sistema) 
            FROM aula 
            WHERE referencia = :referencia
            GROUP BY referencia
            """)
                    .setParameter("referencia", referencia)  //Usamos setParameter para inyectar la variable
                    .getSingleResult();  //Devolvemos el resultado de la consulta

            //Convertimos el resultado en un entero
            int resultado = (result != null) ? ((Number) result).intValue() : 0;
            return String.valueOf(resultado);

        //Gestionamos el resultado en el caso de que sea 0 o un num negativo
        } catch (jakarta.persistence.NoResultException e) {
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * Método que devuelve un mapa con cada aula como clave y el número de equipos por aula de valor.
     * @return devuelve un mapa con cada aula como clave y el número de equipos por aula de valor.
     */
    public Map<String, Integer> mapEquiposPorAula() {
        Map<String, Integer> equiposPorAula = new LinkedHashMap<>(); //Hacemos el mapa LinkedHashMap para que se ordene de menor a mayor en orden natural

        try {
            //Ejecutamos la consulta
            List<Object[]> resultados = gestorEntidad.createNativeQuery("""
            SELECT referencia AS aula, COUNT(id_informacion_sistema) AS total_equipos
            FROM aula
            GROUP BY referencia
            """).getResultList();

            //Agregamos los resultados de la consulta al mapa
            for (Object[] row : resultados) {
                String referencia = (String) row[0]; //Agregamos la referencia (clave)
                Integer totalEquipos = ((Number) row[1]).intValue(); //Agregamos el conteo de los equipos (valor)
                equiposPorAula.put(referencia, totalEquipos);  //Agregamos los valores al mapa
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return equiposPorAula;  //Devolvemos el mapa
    }

    //Método main para realizar pruebas.
    public static void main(String[] args) {
        CRUDAula_Equipo c = new CRUDAula_Equipo();
        //System.out.println(c.numEquiposXAula("PRUEBA1"));
        //c.insertAula(new Aula("PRUEBA6", c.gestorEntidad.find(InformacionSistema.class, 7)));
        //System.out.println(c.numEquiposXAula("PRUEBA5"));
        /*
        for (Map.Entry<String, Integer> entry : c.mapEquiposPorAula().entrySet()) {
            String referencia = entry.getKey();
            Integer totalEquipos = entry.getValue();
            System.out.println("Aula: " + referencia + " - Total de equipos: " + totalEquipos);
        }
         */
        //System.out.println(c.comprobarAula("PRUEBA1"));
    }

}

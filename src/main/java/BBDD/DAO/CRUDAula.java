package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula;
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
        if (!(validaciones.onlyOneDevice(aula.getIdInformacionSistema().getId()))) {
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

    /*
    //Método main para realizar pruebas.
    public static void main(String[] args) {
        CRUDAula c = new CRUDAula();
        //c.insertAula(new Aula("PRUEBA6", c.gestorEntidad.find(InformacionSistema.class, 7)));
        //System.out.println(c.numEquiposXAula("PRUEBA5"));

        for (Map.Entry<String, Integer> entry : c.mapEquiposPorAula().entrySet()) {
            String referencia = entry.getKey();
            Integer totalEquipos = entry.getValue();
            System.out.println("Aula: " + referencia + " - Total de equipos: " + totalEquipos);
        }
    }
     */
}

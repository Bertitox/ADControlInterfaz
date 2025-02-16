package BBDD.Conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * Clase Singleton para la conexión a la BBDD con JPA
 * @author Alberto y Daniel
 * @version 1.1
 */
public class Conexion {

    private static Conexion instancia;
    private static EntityManagerFactory factoria;
    private static EntityManager gestor;

    /**
     * Constructor privado para evitar múltiples instancias.
     */
    private Conexion() {
        factoria = Persistence.createEntityManagerFactory("miPersistencia");
        gestor = factoria.createEntityManager();
    }

    /**
     * Método estático para obtener la instancia única de la conexión.
     * @return instancia única de la clase Conexion
     */
    public static Conexion getInstancia() {
        if (instancia == null) {
            synchronized (Conexion.class) { // Bloqueo para garantizar seguridad en entornos multi-hilo
                if (instancia == null) {
                    instancia = new Conexion();
                }
            }
        }
        return instancia;
    }

    /**
     * Método para obtener el EntityManager.
     * @return EntityManager gestor
     */
    public EntityManager getGestor() {
        return gestor;
    }

    /**
     * Método para iniciar una transacción.
     * @return EntityTransaction transacción activa
     */
    public EntityTransaction getTransaccion() {
        return gestor.getTransaction();
    }

    /**
     * Método para cerrar la conexión con la base de datos.
     */
    public static void cerrarConexion() {
        if (gestor != null && gestor.isOpen()) {
            gestor.close();
        }
        if (factoria != null && factoria.isOpen()) {
            factoria.close();
        }
        instancia = null; // Permitir reinicialización si es necesario
    }
}

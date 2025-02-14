package BBDD.Conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * Clase conexión a la BBDD
 * @author Alberto y Daniel
 * @version 1.0
 */
public class Conexion {

        //Objetos necesarios para la conexión a la bbdd
        EntityManagerFactory factoria;
        EntityManager gestor;
        EntityTransaction transaccion;

        /**
         * Método constructor de la clase que inicializa los objetos necesarios para la conexión
         */
        public Conexion() {
            factoria = Persistence.createEntityManagerFactory("miPersistencia"); // EntityManagerFactory asociado al Persistence.xml
            gestor = factoria.createEntityManager();
            transaccion = gestor.getTransaction();
        }

        /**
         * Método getter que retorna el EntityManagerFactory
         *
         * @return retorna EntityManagerFactory factoria
         */
        public EntityManagerFactory getFactoria() {
            return factoria;
        }

        /**
         * Método getter que retorna el EntityManager
         *
         * @return retorna EntityManager gestor
         */
        public EntityManager getGestor() {
            return gestor;
        }

        /**
         * Método getter que retorna el EntityTransaction
         *
         * @return retorna EntityTransaction transaccion
         */
        public EntityTransaction getTransaccion() {
            return transaccion;
        }
}

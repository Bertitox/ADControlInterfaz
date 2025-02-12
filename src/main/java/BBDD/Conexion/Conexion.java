package BBDD.Conexion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Conexion {
        EntityManagerFactory factoria;
        EntityManager gestor;
        EntityTransaction transaccion;

        public Conexion() {
            factoria = Persistence.createEntityManagerFactory("miPersistencia");
            gestor = factoria.createEntityManager();
            transaccion = gestor.getTransaction();
        }

        public EntityManagerFactory getFactoria() {
            return factoria;
        }

        public EntityManager getGestor() {
            return gestor;
        }

        public EntityTransaction getTransaccion() {
            return transaccion;
        }
}

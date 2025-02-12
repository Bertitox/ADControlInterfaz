package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula;
import BBDD.DTO.InformacionSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class CRUDAula {

    Conexion conexion = new Conexion();
    EntityManager gestorEntidad = conexion.getGestor();
    EntityTransaction transaction = gestorEntidad.getTransaction();

    public CRUDAula() {
    }

    public void insertAula(Aula aula) {
        transaction.begin();
        gestorEntidad.persist(aula);
        transaction.commit();
    }

    public void updateAula(Aula aula) {
        transaction.begin();
        gestorEntidad.merge(aula);
        transaction.commit();
    }

    public void deleteAula(Aula aula) {
        transaction.begin();
        gestorEntidad.remove(aula);
        transaction.commit();
    }

    public List<Aula> readAllAulas() {
        String consulta = "Select aula from Aula aula";
        List<Aula> aulas = gestorEntidad.createQuery(consulta, Aula.class).getResultList();
        return aulas;
    }

    public Aula getbyId(int id) {
        Aula a = gestorEntidad.find(Aula.class, id);
        return a;
    }

    public Aula getbyReferencia(String referencia) {
        Aula a = gestorEntidad.find(Aula.class, referencia);
        return a;
    }

    public void mostrarAulas(){
        for (Aula a : readAllAulas()) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        CRUDAula aula = new CRUDAula();
            //aula.insertAula(new Aula("PRUEBA1", aula.gestorEntidad.find(InformacionSistema.class, 1)));
    }
}

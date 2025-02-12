package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.Aula;
import BBDD.Excepciones.AulaNotFoundException;
import BBDD.Validaciones.Validaciones_systema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class CRUDAula {

    Conexion conexion = new Conexion();
    EntityManager gestorEntidad = conexion.getGestor();
    EntityTransaction transaction = gestorEntidad.getTransaction();

    //Clase de validaciones
    Validaciones_systema validaciones = new Validaciones_systema();

    public CRUDAula() {
    }

    public void insertAula(Aula aula) {
        if (!(validaciones.onlyOneDevice(aula.getIdInformacionSistema().getId()))) {
            transaction.begin();
            gestorEntidad.persist(aula);
            transaction.commit();
        }else {
            throw new RuntimeException("No se puede agregar el aula: Equipo ya existente en el aula");
        }
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

    public Aula getbyReferencia(String referencia) throws AulaNotFoundException {
        for (Aula aula : readAllAulas()) {
            if (aula.getReferencia().equals(referencia)) {
                return aula;
            }
        }
        throw new AulaNotFoundException("Aula no encontrada con la referencia: " + referencia);
    }


    public void mostrarAulas(){
        for (Aula a : readAllAulas()) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) {
        CRUDAula aula = new CRUDAula();
        //aula.insertAula(new Aula("PRUEBA1", aula.gestorEntidad.find(InformacionSistema.class, 6)));
    }
}

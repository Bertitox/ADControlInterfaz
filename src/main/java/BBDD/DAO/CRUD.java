package BBDD.DAO;


import BBDD.Conexion.Conexion;
import BBDD.DAO.MapearJson;
import BBDD.DTO.InformacionSistema;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class CRUD {

    Conexion conexion = new Conexion();

    // Aquí obtienes el EntityManager desde la clase CONEXION
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    public void insert(InformacionSistema informacionSistema) {
        transaction.begin();
        em.persist(informacionSistema); // Usa el 'em' que es correcto
        transaction.commit();
    }
    
    public void update(InformacionSistema informacionSistema) {
        transaction.begin();
        em.merge(informacionSistema);
        transaction.commit();
    }
    
    public void delete(InformacionSistema informacionSistema) {
        transaction.begin();
        em.remove(informacionSistema);
        transaction.commit();
    }
    
    public List<InformacionSistema> readAll() {
        String consulta = "Select e from InformacionSistema e ";
        List<InformacionSistema> info = em.createQuery(consulta, InformacionSistema.class).getResultList();
        return info;
    }
    
    public void mostrarInformacionSistema() {
        for (InformacionSistema informacionSistema : readAll()) {
            System.out.println(informacionSistema);
        }
    }
    
    public InformacionSistema getbyId(Integer id) {
        InformacionSistema info = new InformacionSistema(); 
        for (InformacionSistema informacionSistema : readAll()) {
            if (informacionSistema.getId().equals(id)) {
                info = informacionSistema;
            }
        }
        return info; 
    }

    public static void main(String[] args) {
        CRUD crud = new CRUD();
        InformacionSistema info = MapearJson.mapJsonToEntity("src/main/java/BBDD/DAO/System_info.json");
        if (info != null) {
            crud.mostrarInformacionSistema(); // Insertamos la entidad correctamente
        }
    }
}

package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.InformacionSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * La clase CRUDInfoSistema proporciona métodos para realizar operaciones CRUD en la entidad InformacionSistema.
 * @author Daniel y Alberto
 * @version 1.0
 */
public class CRUDInfoSistema {

    Conexion conexion = Conexion.getInstancia();

    // Aquí obtienes el EntityManager desde la clase CONEXION
    EntityManager em = conexion.getGestor();
    EntityTransaction transaction = em.getTransaction();

    /**
     * Constructor por defecto.
     */
    public CRUDInfoSistema() {
    }

    /**
     * Inserta una nueva entidad InformacionSistema en la base de datos.
     *
     * @param informacionSistema la entidad InformacionSistema a insertar
     */
    public void insert(InformacionSistema informacionSistema) {
        transaction.begin();
        em.persist(informacionSistema); // Usa el 'em' que es correcto
        transaction.commit();
    }

    /**
     * Actualiza una entidad InformacionSistema existente en la base de datos.
     *
     * @param informacionSistema la entidad InformacionSistema a actualizar
     */
    public void update(InformacionSistema informacionSistema) {
        transaction.begin();
        em.merge(informacionSistema);
        transaction.commit();
    }

    /**
     * Elimina una entidad InformacionSistema de la base de datos.
     *
     * @param informacionSistema la entidad InformacionSistema a eliminar
     */
    public void delete(InformacionSistema informacionSistema) {
        transaction.begin();
        em.remove(informacionSistema);
        transaction.commit();
    }

    /**
     * Recupera todas las entidades InformacionSistema de la base de datos.
     *
     * @return una lista de todas las entidades InformacionSistema
     */
    public List<InformacionSistema> readAll() {
        String consulta = "Select e from InformacionSistema e ";
        List<InformacionSistema> info = em.createQuery(consulta, InformacionSistema.class).getResultList();
        return info;
    }

    public int getIdEquipo(String equipo) {
        Integer idEquipo = 0;
        for (InformacionSistema info : readAll()) {
            if (info.getNombre().equals(equipo)) {
                idEquipo = info.getId();
            }
        }
        return idEquipo;
    }

    /**
     * Muestra todas las entidades InformacionSistema en la consola.
     */
    public void mostrarInformacionSistema() {
        for (InformacionSistema informacionSistema : readAll()) {
            System.out.println(informacionSistema);
        }
    }

    /**
     * Recupera una entidad InformacionSistema por su id.
     *
     * @param id el id de la entidad InformacionSistema
     * @return la entidad InformacionSistema con el id proporcionado
     */
    public InformacionSistema getbyId(Integer id) {
        InformacionSistema info = new InformacionSistema();
        for (InformacionSistema informacionSistema : readAll()) {
            if (informacionSistema.getId().equals(id)) {
                info = informacionSistema;
            }
        }
        return info;
    }

    /**
     * Recupera una entidad InformacionSistema por su id.
     *
     * @param id el id de la entidad InformacionSistema
     * @return la entidad InformacionSistema con el id proporcionado
     */
    public InformacionSistema getByNombre(String nombre) {
        InformacionSistema info = new InformacionSistema();
        for (InformacionSistema informacionSistema : readAll()) {
            if (informacionSistema.getNombre().equals(nombre)) {
                info = informacionSistema;
            }
        }
        return info;
    }

    /**
     * Recupera una entidad InformacionSistema por su id.
     *
     * @param id el id de la entidad InformacionSistema
     * @return la entidad InformacionSistema con el id proporcionado
     */
    public InformacionSistema getByMac(String mac) {
        InformacionSistema info = new InformacionSistema();
        for (InformacionSistema informacionSistema : readAll()) {
            if (informacionSistema.getMac().equals(mac)) {
                info = informacionSistema;
            }
        }
        return info;
    }


}

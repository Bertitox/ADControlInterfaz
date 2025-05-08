package BBDD.DAO;

import BBDD.Conexion.Conexion;
import BBDD.DTO.InformacionSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * La clase CRUDInfoSistema proporciona métodos para realizar operaciones CRUD en la entidad InformacionSistema.
 * @author Daniel y Alberto
 * @version 1.5
 */
public class CRUDInfoSistema {

    //Objetos necesarios para realizar la conexión a la BBDD
    Conexion conexion = Conexion.getInstancia();
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
        em.persist(informacionSistema);
        em.flush();
        em.clear();
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
        em.flush();
        em.clear();
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

    /**
     * Método getter que obtiene el id del equipo referente al ID de {@link InformacionSistema}
     * @param equipo String que recive el método que representa el nombre del equipo.
     * @return Retorna un int que representa el ID del equipo
     */
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
     * @param id el ID de la entidad InformacionSistema
     * @return la entidad InformacionSistema con el ID proporcionado
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
     * Método getter que retorna el equipo (Objeto {@link InformacionSistema}) completo, según su nombre.
     * @param nombre Nombre del equipo.
     * @return Retorna un objeto {@link InformacionSistema}
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
     * Mettodo getter que retorna una entidad InformacionSistema recuperada por su MAC.
     * @param mac Parámetro que representa la dirección MAC que se busca recuperar, de la entidad InformacionSistema
     * @return Retorna la entidad InformacionSistema.
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

package BBDD.DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Clase que representa en la bbdd incidencias, se encarga de relacionar el equipo con el aula, el error y una descripción más detallada en la que se
 * pueda basar el técnico para su solución
 * @author Daniel y Alberto
 * @version 1.5
 */
@Entity
@Table(name = "incidencias")
public class Incidencia {

    /**
     * Identificador único de la incidencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Información del sistema asociada a la incidencia.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_informacion_sistema", nullable = false)
    private InformacionSistema idInformacionSistema;

    /**
     * Aula asociada a la incidencia.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_aula", nullable = false)
    private Aulas Aula;

    /**
     * Código de error asociado a la incidencia.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "codigo_error")
    private Errores codigoError;

    /**
     * Descripción detallada de la incidencia.
     */
    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Constructor por defecto de Incidencia.
     */
    public Incidencia() {
    }

    /**
     * Constructor de Incidencia con parámetros.
     *
     * @param idInformacionSistema Información del sistema asociada a la incidencia.
     * @param aula Aula asociada a la incidencia.
     * @param descripcion Descripción detallada de la incidencia.
     * @param codigoError Código de error asociado a la incidencia.
     */
    public Incidencia(InformacionSistema idInformacionSistema, Aulas aula, Errores codigoError, String descripcion) {
        this.idInformacionSistema = idInformacionSistema;
        Aula = aula;
        this.codigoError = codigoError;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el identificador de la incidencia.
     *
     * @return El identificador de la incidencia.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador de la incidencia.
     *
     * @param id El identificador de la incidencia.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la información del sistema asociada a la incidencia.
     *
     * @return La información del sistema asociada a la incidencia.
     */
    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    /**
     * Establece la información del sistema asociada a la incidencia.
     *
     * @param idInformacionSistema La información del sistema asociada a la incidencia.
     */
    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }
    /**
     * Obtiene el aula asociada a la incidencia.
     *
     * @return El aula asociada a la incidencia.
     */
    public Aulas getIdAula() {
        return Aula;
    }

    /**
     * Establece el aula asociada a la incidencia.
     *
     * @param Aula El aula asociada a la incidencia.
     */
    public void setIdAula(Aulas Aula) {
        this.Aula = Aula;
    }

    /**
     * Obtiene el código de error asociado a la incidencia.
     *
     * @return El código de error asociado a la incidencia.
     */
    public Errores getCodigoError() {
        return codigoError;
    }

    /**
     * Establece el código de error asociado a la incidencia.
     *
     * @param codigoError El código de error asociado a la incidencia.
     */
    public void setCodigoError(Errores codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * Obtiene la descripción de la incidencia.
     *
     * @return La descripción de la incidencia.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la incidencia.
     *
     * @param descripcion La descripción de la incidencia.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna una representación en forma de cadena del objeto Incidencia.
     *
     * @return Una cadena que representa la incidencia.
     */
    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", idInformacionSistema=" + idInformacionSistema +
                ", Aula=" + Aula +
                ", codigoError=" + codigoError +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
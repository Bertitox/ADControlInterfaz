package BBDD.DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Clase que representa una entidad de Incidencia en la base de datos.
 * @author Daniel y Alberto
 * @version 1.0
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
    private Aula idAula;

    /**
     * Código de error asociado a la incidencia.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "codigo_error", nullable = false)
    private Errores codigoError;

    /**
     * Descripción detallada de la incidencia.
     */
    @Lob
    @Column(name = "descripcion", nullable = false)
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
     * @param idAula Aula asociada a la incidencia.
     * @param descripcion Descripción detallada de la incidencia.
     * @param codigoError Código de error asociado a la incidencia.
     */
    public Incidencia(InformacionSistema idInformacionSistema, Aula idAula, String descripcion, Errores codigoError) {
        this.idInformacionSistema = idInformacionSistema;
        this.idAula = idAula;
        this.descripcion = descripcion;
        this.codigoError = codigoError;
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
    public Aula getIdAula() {
        return idAula;
    }

    /**
     * Establece el aula asociada a la incidencia.
     *
     * @param idAula El aula asociada a la incidencia.
     */
    public void setIdAula(Aula idAula) {
        this.idAula = idAula;
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
                ", idInformacionSistema=" + idInformacionSistema.getId() +
                ", idAula=" + idAula.getId() +
                ", codigoError=" + codigoError.getCodigoError() +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

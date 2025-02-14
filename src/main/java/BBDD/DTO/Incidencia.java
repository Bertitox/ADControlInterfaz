package BBDD.DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * La clase Incidencia representa una incidencia en el sistema.
 * @author Daniel y Alberto
 * @version 1.0
 */
@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_informacion_sistema", nullable = false)
    private InformacionSistema idInformacionSistema;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "referencia", nullable = false)
    private Aula referencia;

    @Column(name = "codigo_error", nullable = false, length = 50)
    private String codigoError;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    /**
     * Constructor por defecto.
     */
    public Incidencia() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param idInformacionSistema la información del sistema relacionada
     * @param referencia la referencia del aula
     * @param codigoError el código de error
     * @param descripcion la descripción de la incidencia
     */
    public Incidencia(InformacionSistema idInformacionSistema, Aula referencia, String codigoError, String descripcion) {
        this.idInformacionSistema = idInformacionSistema;
        this.referencia = referencia;
        this.codigoError = codigoError;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el id de la incidencia.
     *
     * @return el id de la incidencia
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el id de la incidencia.
     *
     * @param id el nuevo id de la incidencia
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la información del sistema relacionada.
     *
     * @return la información del sistema relacionada
     */
    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    /**
     * Establece la información del sistema relacionada.
     *
     * @param idInformacionSistema la nueva información del sistema relacionada
     */
    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }

    /**
     * Obtiene la referencia del aula.
     *
     * @return la referencia del aula
     */
    public Aula getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia del aula.
     *
     * @param referencia la nueva referencia del aula
     */
    public void setReferencia(Aula referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtiene el código de error.
     *
     * @return el código de error
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Establece el código de error.
     *
     * @param codigoError el nuevo código de error
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * Obtiene la descripción de la incidencia.
     *
     * @return la descripción de la incidencia
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la incidencia.
     *
     * @param descripcion la nueva descripción de la incidencia
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve una representación en cadena de la incidencia.
     *
     * @return una representación en cadena de la incidencia
     */
    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", idInformacionSistema=" + idInformacionSistema.getId() +
                ", referencia=" + referencia.getReferencia() +
                ", codigoError='" + codigoError + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

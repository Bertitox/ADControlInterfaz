package BBDD.DTO;

import jakarta.persistence.*;

/**
 * Clase que representa una entidad de Aula en la base de datos.
 * @version 1.0
 * @author Daniel y Alberto
 */
@Entity
@Table(name = "aula")
public class Aula {

    /**
     * El identificador único del aula.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * La referencia del aula.
     */
    @Column(name = "referencia", nullable = false)
    private String referencia;

    /**
     * La información del sistema asociada al aula.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_informacion_sistema", nullable = false)
    private InformacionSistema idInformacionSistema;

    /**
     * Constructor por defecto de Aula.
     */
    public Aula() {
    }

    /**
     * Constructor de Aula con referencia e información del sistema.
     *
     * @param referencia             La referencia del aula.
     * @param idInformacionSistema   La información del sistema asociada al aula.
     */
    public Aula(String referencia, InformacionSistema idInformacionSistema) {
        this.referencia = referencia;
        this.idInformacionSistema = idInformacionSistema;
    }

    /**
     * Obtiene el identificador del aula.
     *
     * @return El identificador del aula.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador del aula.
     *
     * @param id El identificador del aula.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la referencia del aula.
     *
     * @return La referencia del aula.
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Establece la referencia del aula.
     *
     * @param referencia La referencia del aula.
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtiene la información del sistema asociada al aula.
     *
     * @return La información del sistema asociada al aula.
     */
    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    /**
     * Establece la información del sistema asociada al aula.
     *
     * @param idInformacionSistema La información del sistema asociada al aula.
     */
    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }

    /**
     * Retorna una representación en forma de cadena del objeto Aula.
     *
     * @return Una cadena que representa el aula.
     */
    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", referencia='" + referencia + '\'' +
                ", idInformacionSistema=" + idInformacionSistema.getId() +
                '}';
    }
}

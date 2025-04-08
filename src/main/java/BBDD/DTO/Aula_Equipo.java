package BBDD.DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Clase que representa una entidad de Aula en la base de datos.
 * @version 1.5
 * @author Daniel y Alberto
 */
@Entity
@Table(name = "aula")
public class Aula_Equipo {
    //EN BBDD SE LLAMA AULA
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "referencia", nullable = false)
    private Aulas referencia;

    /**
     * El quipo asociado al aula.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_informacion_sistema", nullable = false)
    private InformacionSistema idInformacionSistema;

    /**
     * Constructor por defecto de la clase {@link Aula_Equipo}
     */
    public Aula_Equipo() {
    }

    /**
     * Constructor de {@link Aula_Equipo} con referencia e información del sistema.
     * @param referencia             La referencia del aula.
     * @param idInformacionSistema   El equipo asociado a cada aula.
     */
    public Aula_Equipo(Aulas referencia, InformacionSistema idInformacionSistema) {
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
     * Obtiene el {@link Aulas}.
     *
     * @return Retorna una instancia de la clase {@link Aulas}.
     */
    public Aulas getReferencia() {
        return referencia;
    }

    /**
     * Establece el aula.
     *
     * @param referencia El aula.
     */
    public void setReferencia(Aulas referencia) {
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
     * Retorna una representación en forma de cadena del objeto {@link Aulas}.
     *
     * @return Una cadena que representa el {@link Aulas}.
     */
    @Override
    public String toString() {
        return "Aula_Equipo{" +
                "id=" + id +
                ", referencia=" + referencia +
                ", idInformacionSistema=" + idInformacionSistema +
                '}';
    }
}
package BBDD.DTO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entidad que representa a la tabla Aula de la BBDD
 * @author Daniel y Alberto
 * @version 1.0
 */
@Entity
@Table(name = "aula")
public class Aula {
    @Id
    @Column(name = "referencia", nullable = false)
    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_informacion_sistema")
    private InformacionSistema idInformacionSistema;

    /**
     * Constructor por defecto de la clase
     */
    public Aula() {
    }

    /**
     * Constructor principal de la clase
     * @param referencia campo de la BBDD
     * @param idInformacionSistema campo de la BBDD
     */
    public Aula(String referencia, InformacionSistema idInformacionSistema) {
        this.referencia = referencia;
        this.idInformacionSistema = idInformacionSistema;
    }

    /**
     * Método getter que devuelve un String
     * @return devuelve un String
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Método setter que actualiza la referencia de un {@link Aula}
     * @param referencia recive un String
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Método getter que devuelve un objeto del tipo {@link InformacionSistema}
     * @return devuelve una instancia de {@link InformacionSistema}
     */
    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    /**
     * Método setter que actualiza la referencia de un {@link Aula} con respecto a los equipos de {@link InformacionSistema}
     * @param idInformacionSistema recive un objeto {@link InformacionSistema}
     */
    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }

    /**
     * Método to String
     * @return retorna un String
     */
    @Override
    public String toString() {
        return "Aula{" +
                "referencia='" + referencia + '\'' +
                ", idInformacionSistema=" + idInformacionSistema.getId() +
                '}';
    }
}
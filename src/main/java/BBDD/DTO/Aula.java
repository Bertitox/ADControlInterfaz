package BBDD.DTO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "aula")
public class Aula {
    @Id
    @Column(name = "referencia", nullable = false)
    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_informacion_sistema")
    private InformacionSistema idInformacionSistema;

    public Aula() {
    }

    public Aula(String referencia, InformacionSistema idInformacionSistema) {
        this.referencia = referencia;
        this.idInformacionSistema = idInformacionSistema;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }

    @Override
    public String toString() {
        return "Aula{" +
                "referencia='" + referencia + '\'' +
                ", idInformacionSistema=" + idInformacionSistema.getId() +
                '}';
    }
}
package BBDD.DTO;

import jakarta.persistence.*;

@Entity
@Table(name = "aula")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "referencia")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return String.format(
                "%s, %s, %s", id, referencia, idInformacionSistema.getId());
    }
}
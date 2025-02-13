package BBDD.DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    public Incidencia() {
    }

    public Incidencia(InformacionSistema idInformacionSistema, Aula referencia, String codigoError, String descripcion) {
        this.idInformacionSistema = idInformacionSistema;
        this.referencia = referencia;
        this.codigoError = codigoError;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InformacionSistema getIdInformacionSistema() {
        return idInformacionSistema;
    }

    public void setIdInformacionSistema(InformacionSistema idInformacionSistema) {
        this.idInformacionSistema = idInformacionSistema;
    }

    public Aula getReferencia() {
        return referencia;
    }

    public void setReferencia(Aula referencia) {
        this.referencia = referencia;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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
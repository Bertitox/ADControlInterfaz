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
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula idAula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "codigo_error", nullable = false)
    private Errores codigoError;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public Incidencia() {
    }

    public Incidencia(InformacionSistema idInformacionSistema, Aula idAula, String descripcion, Errores codigoError) {
        this.idInformacionSistema = idInformacionSistema;
        this.idAula = idAula;
        this.descripcion = descripcion;
        this.codigoError = codigoError;
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

    public Aula getIdAula() {
        return idAula;
    }

    public void setIdAula(Aula idAula) {
        this.idAula = idAula;
    }

    public Errores getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(Errores codigoError) {
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
                ", idAula=" + idAula.getId() +
                ", codigoError=" + codigoError.getCodigoError() +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
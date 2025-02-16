package BBDD.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "errores")
public class Errores {
    @Id
    @Column(name = "codigo_error", nullable = false, length = 50)
    private String codigoError;

    @Column(name = "descripcion_error", nullable = false, length = 200)
    private String descripcionError;

    public Errores() {
    }

    public Errores(String codigoError, String descripcionError) {
        this.codigoError = codigoError;
        this.descripcionError = descripcionError;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    @Override
    public String toString() {
        return "Errores{" +
                "codigoError='" + codigoError + '\'' +
                ", descripcionError='" + descripcionError + '\'' +
                '}';
    }
}
package BBDD.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa en la bbdd la tabla errores, que contiene un identificador de String (p.e. E001). Tiene una pequeña descripcion
 * para determinar el tipo de error general. Esta clase no podrá ser modificada por nadie, se usará como base para las incidencias.
 *
 * @version 1.0
 * @author Daniel y Alberto
 */
@Entity
@Table(name = "errores")
public class Errores {

    /**
     * Código único de identificación del error.
     */
    @Id
    @Column(name = "codigo_error", nullable = false, length = 50)
    private String codigoError;

    /**
     * Descripción detallada del error.
     */
    @Column(name = "descripcion_error", nullable = false, length = 200)
    private String descripcionError;

    /**
     * Constructor por defecto.
     */
    public Errores() {
    }

    /**
     * Constructor con parámetros para inicializar los atributos del error.
     *
     * @param codigoError       Código único de identificación del error.
     * @param descripcionError  Descripción detallada del error.
     */
    public Errores(String codigoError, String descripcionError) {
        this.codigoError = codigoError;
        this.descripcionError = descripcionError;
    }

    /**
     * Obtiene el código del error.
     *
     * @return El código del error.
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Establece el código del error.
     *
     * @param codigoError El código del error.
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * Obtiene la descripción del error.
     *
     * @return La descripción del error.
     */
    public String getDescripcionError() {
        return descripcionError;
    }

    /**
     * Establece la descripción del error.
     *
     * @param descripcionError La descripción del error.
     */
    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    /**
     * Retorna una representación en forma de cadena del objeto Errores.
     *
     * @return Una cadena que representa el error.
     */
    @Override
    public String toString() {
        return "Errores{" +
                "codigoError='" + codigoError + '\'' +
                ", descripcionError='" + descripcionError + '\'' +
                '}';
    }
}

package BBDD.DTO;

import jakarta.persistence.*;


/**
 * Clase que representa una entidad de {@link Aulas} en la base de datos.
 * @author Daniel y Alberto
 * @version 1.0
 */
@Entity
@Table(name = "aulas")
public class Aulas {

    /**
     * La referencia que es la clave primaria del {@link Aulas}
     */
    @Id
    @Column(name = "referencia", nullable = false, length = 50)
    private String referencia;


    /**
     * Constructor principal de la clase {@link Aulas}
     * @param referencia referencia de la clase {@link Aulas}
     */
    public Aulas(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Constructor por defecto de la clase {@link Aulas}
     */
    public Aulas() {
    }

    /**
     * Método que retorna la referencia del {@link Aulas}
     * @return Referencia del {@link Aulas}
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Método que cambia la referencia del {@link Aulas}
     * @param referencia recibe la referencia del {@link Aulas}
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
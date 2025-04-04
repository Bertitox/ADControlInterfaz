package BBDD.DTO;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "aulas")
public class Aulas {
    @Id
    @Column(name = "referencia", nullable = false, length = 50)
    private String referencia;


    public Aulas(String referencia) {
        this.referencia = referencia;
    }

    public Aulas() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }


}
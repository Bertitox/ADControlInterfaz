package org.example.adcontrol;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlIdioma {
    private static ControlIdioma instancia;

    private StringProperty idioma = new SimpleStringProperty();

    public static ControlIdioma getInstance() {
        if (instancia == null) {
            instancia = new ControlIdioma();
            instancia.idioma.setValue("Español"); //IDIOMA POR DEFECTO AL INICIAR LA APP
        }
        return instancia;
    }

    public StringProperty getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma.set(idioma);
    }
}

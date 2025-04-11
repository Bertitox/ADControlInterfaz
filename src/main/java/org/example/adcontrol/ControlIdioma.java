package org.example.adcontrol;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase singletone que controla la traducción de los idiomas.
 * @version 1.0
 * @author Daniel y Alberto
 */
public class ControlIdioma {

    //Variables que se usarán en la clase.
    private static ControlIdioma instancia; //Instancia única de control idioma, para saber el idioma actual.
    private StringProperty idioma = new SimpleStringProperty(); //Variable que contiene el idioma actual.

    /**
     * Método que se encarga de controlar la instancia del idioma, usando la instancia ya creada.
     * @return instancia ControlIdioma.
     */
    public static ControlIdioma getInstance() {
        if (instancia == null) {
            InfoInit infoInit = InfoInit.getInstance();
            instancia = new ControlIdioma();
            instancia.idioma.setValue(infoInit.getIdiomaLeido()); //IDIOMA POR DEFECTO AL INICIAR LA APP
        }
        return instancia;
    }

    /**
     * Getter que devuelve el idioma actual.
     * @return Devuelve un StringProperty del idioma actual.
     */
    public StringProperty getIdioma() {
        return idioma;
    }

    /**
     * Setter que modifica el idioma.
     * @param idioma Recibe el idioma a cambiar.
     */
    public void setIdioma(String idioma) {
        this.idioma.set(idioma);
    }

}

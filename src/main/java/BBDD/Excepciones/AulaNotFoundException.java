package BBDD.Excepciones;

import BBDD.DTO.Aula_Equipo;

/**
 * Clase {@link AulaNotFoundException} que extiende de {@link Exception} y lanza una excepción personalizada o de usuario para gestionar un {@link Aula_Equipo}
 * @author Alberto y Daniel
 */
public class AulaNotFoundException extends Exception {

    /**
     * Método que recoge el mensaje personalizado para la excepción
     * @param message Recive un mensaje que luego mostrará en la excepción
     */
    public AulaNotFoundException(String message) {
        super(message);
    }
}

package BBDD.Excepciones;

/**
 * Clase {@link AulaNotFoundException} que extiende de {@link Exception} y lanza una excepción personalizada o de usuario para gestionar un {@link BBDD.DTO.Aula}
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

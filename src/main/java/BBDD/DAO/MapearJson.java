package BBDD.DAO;

import BBDD.DTO.InformacionSistema;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * La clase MapearJson que sirve para gestionar el JSON.
 * @author Daniel y Alberto.
 * @version 1.5
 */
public class MapearJson {
    /**
     * Método que se encarga de mapear el Json y convertirlo en una entidad de {@link InformacionSistema}.
     * @param jsonFilePath recibe el String del JSon.
     * @return Retorna una instancia de la clase {@link InformacionSistema}.
     */
    public static InformacionSistema mapJsonToEntity(String jsonFilePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/java/BBDD/DAO/System_info.json")) {//Lee el fichero Json.
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            InformacionSistema infoSistema = new InformacionSistema();

            //Creamos una nueva instancia de InformacionSistema.
            infoSistema.setSo(jsonObject.get("Sistema_Operativo").getAsString());
            infoSistema.setNombreNodo(jsonObject.get("Nombre_Nodo").getAsString());
            infoSistema.setReleasee(jsonObject.get("Release").getAsString());
            infoSistema.setVersion(jsonObject.get("Version").getAsString());
            infoSistema.setArquitectura(jsonObject.get("Arquitectura").getAsString());
            infoSistema.setProcesador(jsonObject.get("Procesador").getAsString());
            infoSistema.setMemTotal(jsonObject.get("Memoria_Total_MB").getAsInt());
            infoSistema.setMemDisp(jsonObject.get("Memoria_Disponible_MB").getAsInt());
            infoSistema.setUsoCpu(jsonObject.get("Uso_CPU").getAsString());

            infoSistema.setUltFecha(LocalDate.now());
            infoSistema.setUltHora(LocalTime.now());

            return infoSistema; //Retornamos la nueva instancia de InformacionSistema.
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método main que prueba el mapeo del Json.
     * @param args argumentos que se le pasa al método main.
     */
    public static void main(String[] args) {
        InformacionSistema info = mapJsonToEntity("src/main/java/JPA/GESTION_BBDD/DAO/System_info.json"); //Pasa el resultado del json a la entidad InformacionSistema.
        if (info != null) {
            System.out.println("Datos del JSON mapeados correctamente.");
        }
    }
}

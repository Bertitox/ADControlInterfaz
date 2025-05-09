package org.example.adcontrol;

import BBDD.DAO.CRUDAula_Equipo;
import BBDD.DTO.Aula_Equipo;
import BBDD.DTO.InformacionSistema;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;


/**
 * @author Daniel y Alberto
 * @version 1.0
 * Clase encargada de realizar la conexión de ssh mediante el usuario, la contraseña y el host (la IP)
 */
public class ControlSSH {
    //Variables declaradas en el FXML
    @FXML
    private TextArea salidaTerminal;

    @FXML
    private TextField entradaComando;

    @FXML
    private MenuButton menuButtonEquipos;

    @FXML
    private MenuButton menuButtonTema;

    //Variables necesarias para realizar la conexión SSH
    String host = "";
    String user = "";
    String password = "";

    //Clase actual en la que se encuentra el usuario
    String claseActual;

    //Booleano para acceder como superUsuario
    Boolean superusuario = true;

    //Canales de entrada y salida por los que se comunicará el usuario
    private OutputStream out;
    private ChannelShell channel;

    //Items del menuButtonTema
    MenuItem itemClaro = new MenuItem("Tema claro (monitor)");
    MenuItem itemOscuro = new MenuItem("Tema oscuro (terminal)");

    /**
     * Método que inicializa variables y elementos necesarios de la clase.
     */
    @FXML
    void initialize() {

        //Listener para capturar "Enter" cuando se usa el textfield de entradaComando
        entradaComando.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> validarTecla(event);
            }
        });


        //Agregamos los items creados anteriormente en el menuButtonTema
        menuButtonTema.getItems().addAll(itemClaro, itemOscuro);

        //Manejador que determinará la acción que se va a hacer cuando se seleccione un tema.
        EventHandler<ActionEvent> handler = event -> {
            Object source = event.getSource();
            //Source tiene valor: MenuItem@6594b40d[styleClass=[menu-item]]
            cambiarTema(source); //Llama al método cambiarTema para cambiar la vista de la ventana de SSH
        };

        //Asignamos la acción de los 2 items al manejador ya creado
        itemClaro.setOnAction(handler);
        itemOscuro.setOnAction(handler);
    }

    /**
     * Método que dependiendo del menuItem que se haya pulsado, cambiará el tema en base a este.
     *
     * @param source Objeto source que será el menuItem de los 2 que se haya seleccionado (itemClaro o itemOscuro)
     */
    void cambiarTema(Object source) {
        try {
            FXMLLoader loader = new FXMLLoader();
            if (source.equals(itemClaro)) { //Si pulsa el item itemClaro se cambia la vista a vistaSSH.fxml
                loader = new FXMLLoader(getClass().getResource("Vistas/vistaSSH.fxml"));
            } else if (source.equals(itemOscuro)) {//Si pulsa el item itemOscuro se cambia la vista a vistaSSHV2.fxml
                loader = new FXMLLoader(getClass().getResource("Vistas/vistaSSHV2.fxml"));
            }

            Parent root = loader.load();

            //Obtener el stage (la ventana) actual a partir de del menuButtonTema.
            Stage stage = (Stage) menuButtonTema.getScene().getWindow();

            ControlSSH controladorB = loader.getController();
            controladorB.setClaseActual(claseActual);

            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Error al cambiar tema");
            e.printStackTrace();
        }
    }

    /**
     * Inicia la conexión SSH a través de JSch (Java Secure Channel).
     */
    public void inciarConexionSSH() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22); //Se declara el usuario, ip y puerto para conectarse con ssh
            session.setPassword(password); //Se añade la contraseña
            session.setConfig("StrictHostKeyChecking", "no"); //Emitimos la validación de la clave del servidor para evitar errores de conexión (suponemos que no hay un man-in-the-middle)

            salidaTerminal.appendText("Conectando al servidor...\n");
            session.connect(30000);
            salidaTerminal.appendText("Conexión establecida con éxito.\n");

            channel = (ChannelShell) session.openChannel("shell"); //Iniciamos un canal de tipo channel para "interpretar" la terminal del equipo al que nos conectamos.
            out = channel.getOutputStream(); //Comunicamos la entrada del canal con nuestro OutPutStream para poder mandar mensajes y comandos.
            InputStream in = channel.getInputStream(); //Comunicamos la salida del canal con nuestro InputStream (para que se vea en la terminal de IntelIJ)

            channel.connect();

            //Hilo para leer la salida en tiempo real
            new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) { //Mientras que haya datos por leer (bytesRead = 0) va a serguir el bucle, cuando no haya (bytesRead = -1) acabará
                        String outputf = new String(buffer, 0, bytesRead);
                        String output = outputf.replaceAll("\\[\\?2004[hl]", ""); //Quitamos los caracteres ANSI, pues la terminal en la que se muestra no puede representar estos caracteres, entonces los quitamos directamente

                        Platform.runLater(() -> salidaTerminal.appendText(output)); //Mostramos por el textField de salidaTerminal el resultado del comando
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> salidaTerminal.appendText("Error al leer la salida: " + e.getMessage() + "\n"));
                }
            }).start();//Empezamos el proceso

            //Nada más empezar la conexión, se establece el usuario como superUsuario, para mejor manejo de la terminal y comandos
            if (superusuario) {
                convSuper();
            }
        } catch (Exception e) {
            salidaTerminal.appendText("Error en la conexión SSH: " + e.getMessage() + "\n");
        }
    }

    /**
     * Método que convierte el usuario de la sesión actual en superUsuario para tener acceso a todos los comandos que necesite
     *
     * @throws IOException          Excepción de entrada y salida
     * @throws InterruptedException Excepción de interrupción del Thread.sleep(500)
     */
    public void convSuper() throws IOException, InterruptedException {
        //Damos privilegios al usuario root con "sudo su"
        out.write("sudo su\n".getBytes());
        out.flush();
        Thread.sleep(500); //Esperamos un poco para que pida la contraseña

        //Enviamos la contraseña cuando el sistema la solicite
        out.write((password + "\n").getBytes());
        out.flush();
        superusuario = false; //Ponemos superusuario en false para que no se repita más de una vez este método por sesión
    }

    /**
     * Método que envía por el canal asignado el comando que ingrese el usuario
     */
    @FXML
    private void enviarComando() {
        try {
            String comando = entradaComando.getText();
            if (comando.equals("clear")) { //Si el comando es clear, se borra el contenido del textArea por el que se muestra el resultado de todos los comandos
                salidaTerminal.clear();
            } else if (!(comando.equals("") || comando.isBlank() || comando.isEmpty() || comando == null)) { //Si el comando no es nulo, vacío o " " se envía el comando
                out.write((comando + "\n").getBytes());
                out.flush();
            }
            Platform.runLater(() -> entradaComando.clear()); //Limpiamos el TextArea de entrada

        } catch (Exception e) {
            salidaTerminal.appendText("Error al enviar comando: " + e.getMessage() + "\n");
        }
    }

    /**
     * Método asignado al usar el textField de entradaComando, cuando se pulsa la tecla "Enter" se manda directamente el comando sin tener que pulsar el botón de enviar
     *
     * @param event Variable necesaria para identificar el tipo de tecla que se ha pulsado
     */
    @FXML
    void validarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            enviarComando();
        }
    }

    /**
     * Método asignado a un botón "Apagar", que cuando se pulsa manda por el canal un comando "sudo poweroff" para apagar el equipo
     *
     * @param event Evento que determina si se ha pulsado o no el botón
     * @throws IOException Excepción de entrada y salida
     */
    @FXML
    void apagar(ActionEvent event) throws IOException {
        out.write(("sudo poweroff" + "\n").getBytes());
        out.flush();
        out.close(); //Se cierra el canal de comunicación
    }

    /**
     * Método se encarga de conectarse al equipo mediante ssh y apagarlo
     *
     * @throws IOException Excepción de entrada y salida
     */
    void conectarYApagar(InformacionSistema informacionSistema) throws IOException {
        //Formulario para que rellene el usuario y contraseña del equipo al que va a conectarse
        Pair<String, String> credenciales = mostrarFormularioSSH();
        user = credenciales.getKey();
        password = credenciales.getValue();
        //Se establece la ip del equipo al que se va a conectar
        host = informacionSistema.getIp();

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22); //Se declara el usuario, ip y puerto para conectarse con ssh
            session.setPassword(password); //Se añade la contraseña
            session.setConfig("StrictHostKeyChecking", "no"); //Emitimos la validación de la clave del servidor para evitar errores de conexión (suponemos que no hay un man-in-the-middle)

            session.connect(30000);

            channel = (ChannelShell) session.openChannel("shell"); //Iniciamos un canal de tipo channel para "interpretar" la terminal del equipo al que nos conectamos.
            out = channel.getOutputStream(); //Comunicamos la entrada del canal con nuestro OutPutStream para poder mandar mensajes y comandos.

            channel.connect();
            convSuper();
            out.write(("sudo poweroff" + "\n").getBytes());
            out.flush();
            out.close(); //Se cierra el canal de comunicación
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método asignado a un botón "Reiniciar", que cuando se pulsa manda por el canal un comando "sudo reboot" para apagar el equipo
     *
     * @param event Evento que determina si se ha pulsado o no el botón
     * @throws IOException Excepción de entrada y salida
     */
    @FXML
    void reiniciar(ActionEvent event) throws IOException {
        out.write(("sudo reboot" + "\n").getBytes());
        out.flush();
        out.close();
    }

    /**
     * Acción que tiene asignado el boton "Conectarse" que guarda los valores introducidos en el formulario "mostrarFormularioSSH()" en sus variables correspondientes, después inicia la conexión
     *
     * @param event Evento que determina si se ha pulsado o no el botón
     */
    @FXML
    void botonConectarse(ActionEvent event) {
        Pair<String, String> credenciales = mostrarFormularioSSH();
        user = credenciales.getKey();
        password = credenciales.getValue();
        this.inciarConexionSSH();
    }

    /**
     * Método que muestra en pantalla un pequeño formulario antes de iniciar conexion con el equipo, en el que se pide el usuario y contraseña del equipo al que se va a conectar
     *
     * @return Devuelve un valor de tipo Pair, que se trata como un mapa, pero de una sola unidad clave-valor.
     */
    public static Pair<String, String> mostrarFormularioSSH() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Conexión SSH");
        dialog.setHeaderText("Introduce tus credenciales SSH");

        //Botones Aceptar y Cancelar
        ButtonType loginButtonType = new ButtonType("Conectar", ButtonBar.ButtonData.OK_DONE); //Modificamos el texto del boton OK_DONE y añadimos de texto "Conectar"
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL); //Añadimos los botones

        //Espacios del gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Usuario"); //Campo de texto "usuario"

        PasswordField contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");//Campo de contraseña "contraseña"

        //Determinamos y posiciones de cada uno
        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(usuarioField, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(contrasenaField, 1, 1);

        //Hacemos que el textfield crezca si se redimensiona
        GridPane.setHgrow(usuarioField, Priority.ALWAYS);
        GridPane.setHgrow(contrasenaField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid); //Añadimos al dialog el gridpane

        //Habilitamos o deshabilitamos el botón de Conectar según si hay usuarios escritos o no
        dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
        usuarioField.textProperty().addListener((observable, oldValue, newValue) -> {
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(newValue.trim().isEmpty());
        });

        //Devolvemos los datos al pulsar Conectar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usuarioField.getText(), contrasenaField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> resultado = dialog.showAndWait(); //Guardamos el resultado del formulario

        return resultado.orElse(null); //Retornamos la variable creada anteriormente o null si no se ha podido crear
    }

    /**
     * Setter de la claseActual, que carga directamente los equipos del menuButtonEquipos
     *
     * @param claseActual Clase que recibe el setter para actualizar la variable
     */
    public void setClaseActual(String claseActual) {
        this.claseActual = claseActual;
        cargarEquipos(); //Carga el menuButtonEquipos
    }

    /**
     * Carga el menuButtonEquipos dependiendo del valor de claseActual
     */
    private void cargarEquipos() {
        CRUDAula_Equipo aulaEquipo = new CRUDAula_Equipo();
        menuButtonEquipos.getItems().clear();

        for (Aula_Equipo i : aulaEquipo.readAllAulas()) {
            if (i.getReferencia().getReferencia().equals(claseActual)) {
                MenuItem item = new MenuItem(i.getIdInformacionSistema().getNombre());
                menuButtonEquipos.getItems().add(item);
                item.setOnAction(event -> {
                    menuButtonEquipos.setText(item.getText());
                    host = i.getIdInformacionSistema().getIp();
                });
            }
        }
    }
}

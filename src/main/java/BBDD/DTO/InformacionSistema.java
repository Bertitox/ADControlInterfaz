package BBDD.DTO;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Clase que representa en la bbdd la tabla informacion_sistema, la cual contiene toda la información que tienen los equipos.
 * @author Daniel y Alberto
 * @version 1.0
 */
@Entity
@Table(name = "informacion_sistema", schema = "infoSistema")
public class InformacionSistema {
    /**
     * Identificador de cada ordenador, es autoincremental
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Nombre de cada ordenador (Equipo 1)
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Sistema operativo del equipo
     */
    @Column(name = "so")
    private String so;

    /**
     * Ultima fecha de modificación del equipo
     */
    @Column(name = "ultFecha")
    private LocalDate ultFecha;

    /**
     * Nombre que tiene el equipo por defecto
     */
    @Column(name = "nombre_nodo")
    private String nombreNodo;

    /**
     * Nombre y version del kernel del equipo
     */
    @Column(name = "releasee")
    private String releasee;

    /**
     * Version del sistema operativo
     */
    @Column(name = "version")
    private String version;

    /**
     * Arquitectura del procesodor (x64, ARM...)
     */
    @Column(name = "arquitectura")
    private String arquitectura;

    /**
     * Tipo de procesador que lleva incorporado
     */
    @Column(name = "procesador")
    private String procesador;

    /**
     * Memoria total del equipo
     */
    @Column(name = "mem_total")
    private Integer memTotal;

    /**
     * Memoria disponible del equipo
     */
    @Column(name = "mem_disp")
    private Integer memDisp;

    /**
     * Uso total del procesador
     */
    @Column(name = "uso_cpu")
    private String usoCpu;

    /**
     * MAC del equipo
     */
    @Column(name = "mac")
    private String mac;

    /**
     * IP del equipo
     */
    @Column(name = "ip")
    private String ip;

    /**
     * Ultima hora de modificación del equipo
     */
    @Column(name = "ultHora")
    private LocalTime ultHora;

    /**
     * Contructor por defecto de la entidad {@link InformacionSistema}
     */
    public InformacionSistema() {
    }

    /**
     * Constructor sobrecargado principal de la clase
     *
     * @param nombre Campo de la BBDD
     * @param so Campo de la BBDD
     * @param ultFecha Campo de la BBDD
     * @param nombreNodo Campo de la BBDD
     * @param releasee Campo de la BBDD
     * @param version Campo de la BBDD
     * @param arquitectura Campo de la BBDD
     * @param procesador Campo de la BBDD
     * @param memTotal Campo de la BBDD
     * @param memDisp Campo de la BBDD
     * @param usoCpu Campo de la BBDD
     * @param mac Campo de la BBDD
     * @param ultHora Campo de la BBDD
     * @param ip Campo de la BBDD
     */
    public InformacionSistema(String nombre, String so, LocalDate ultFecha, String nombreNodo, String releasee, String version, String arquitectura, String procesador, Integer memTotal, Integer memDisp, String usoCpu, String mac, LocalTime ultHora, String ip) {
        this.nombre = nombre;
        this.so = so;
        this.ultFecha = ultFecha;
        this.nombreNodo = nombreNodo;
        this.releasee = releasee;
        this.version = version;
        this.arquitectura = arquitectura;
        this.procesador = procesador;
        this.memTotal = memTotal;
        this.memDisp = memDisp;
        this.usoCpu = usoCpu;
        this.mac = mac;
        this.ultHora = ultHora;
        this.ip = ip;
    }

    public InformacionSistema(String nombre, String mac, String ip) {
        this.nombre = nombre;
        this.mac = mac;
        this.ip = ip;
    }


    //Getters y Setters

    /**
     * Obtiene el id.
     *
     * @return el id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el id.
     *
     * @param id el nuevo id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Obtiene el nombre.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre.
     *
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el sistema operativo.
     *
     * @return el sistema operativo
     */
    public String getSo() {
        return so;
    }
    /**
     * Establece el sistema operativo.
     *
     * @param so el nuevo sistema operativo
     */
    public void setSo(String so) {
        this.so = so;
    }
    /**
     * Obtiene la última fecha.
     *
     * @return la última fecha
     */
    public LocalDate getUltFecha() {
        return ultFecha;
    }
    /**
     * Establece la última fecha.
     *
     * @param ultFecha la nueva última fecha
     */
    public void setUltFecha(LocalDate ultFecha) {
        this.ultFecha = ultFecha;
    }
    /**
     * Establece el nombre del nodo.
     *
     * @param nombreNodo el nuevo nombre del nodo
     */
    public String getNombreNodo() {
        return nombreNodo;
    }
    /**
     * Obtiene la versión.
     *
     * @return la versión
     */
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }
    /**
     * Establece la versión.
     *
     * @param releasee la nueva versión
     */
    public String getReleasee() {
        return releasee;
    }
    /**
     * Obtiene la versión.
     *
     * @return la versión
     */
    public void setReleasee(String releasee) {
        this.releasee = releasee;
    }
    /**
     * Establece la versión.
     *
     * @param version la nueva versión
     */
    public String getVersion() {
        return version;
    }
    /**
     * Obtiene la arquitectura.
     *
     * @return la arquitectura
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * Obtiene la arquitectura.
     *
     * @return la arquitectura
     */
    public String getArquitectura() {
        return arquitectura;
    }
    /**
     * Establece la arquitectura.
     *
     * @param arquitectura la nueva arquitectura
     */
    public void setArquitectura(String arquitectura) {
        this.arquitectura = arquitectura;
    }
    /**
     * Obtiene el procesador.
     *
     * @return el procesador
     */
    public String getProcesador() {
        return procesador;
    }
    /**
     * Establece el procesador.
     *
     * @param procesador el nuevo procesador
     */
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }
    /**
     * Obtiene la memoria total.
     *
     * @return la memoria total
     */
    public Integer getMemTotal() {
        return memTotal;
    }
    /**
     * Establece la memoria total.
     *
     * @param memTotal la nueva memoria total
     */
    public void setMemTotal(Integer memTotal) {
        this.memTotal = memTotal;
    }
    /**
     * Obtiene la memoria disponible.
     *
     * @return la memoria disponible
     */
    public Integer getMemDisp() {
        return memDisp;
    }
    /**
     * Establece la memoria disponible.
     *
     * @param memDisp la nueva memoria disponible
     */
    public void setMemDisp(Integer memDisp) {
        this.memDisp = memDisp;
    }
    /**
     * Obtiene el uso de la CPU.
     *
     * @return el uso de la CPU
     */
    public String getUsoCpu() {
        return usoCpu;
    }
    /**
     * Establece el uso de la CPU.
     *
     * @param usoCpu el nuevo uso de la CPU
     */
    public void setUsoCpu(String usoCpu) {
        this.usoCpu = usoCpu;
    }
    /**
     * Obtiene la dirección MAC.
     *
     * @return la dirección MAC
     */
    public String getMac() {
        return mac;
    }
    /**
     * Establece la dirección MAC.
     *
     * @param mac la nueva dirección MAC
     */
    public void setMac(String mac) {
        this.mac = mac;
    }
    /**
     * Obtiene la dirección IP.
     *
     * @return la dirección IP
     */
    public String getIp() {
        return ip;
    }
    /**
     * Establece la dirección IP.
     *
     * @param ip la nueva dirección IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    /**
     * Obtiene la última hora.
     *
     * @return la última hora
     */
    public LocalTime getUltHora() {
        return ultHora;
    }
    /**
     * Establece la última hora.
     *
     * @param ultHora la nueva última hora
     */
    public void setUltHora(LocalTime ultHora) {
        this.ultHora = ultHora;
    }


    /**
     * Método que genera el toString de la instancia
     * @return retorna un String con los datos de la instancia
     */
    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s", id, nombre, so);
    }
}
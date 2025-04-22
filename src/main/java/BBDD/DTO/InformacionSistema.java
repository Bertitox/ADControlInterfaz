package BBDD.DTO;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa la tabla {@link InformacionSistema}
 * @author Daniel y Alberto
 * @version 1.0
 */
@Entity
@Table(name = "informacion_sistema", schema = "infoSistema")
public class InformacionSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "so")
    private String so;

    @Column(name = "ultFecha")
    private LocalDate ultFecha;

    @Column(name = "nombre_nodo")
    private String nombreNodo;

    @Column(name = "releasee")
    private String releasee;

    @Column(name = "version")
    private String version;

    @Column(name = "arquitectura")
    private String arquitectura;

    @Column(name = "procesador")
    private String procesador;

    @Column(name = "mem_total")
    private Integer memTotal;

    @Column(name = "mem_disp")
    private Integer memDisp;

    @Column(name = "uso_cpu")
    private String usoCpu;

    @Column(name = "mac")
    private String mac;

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
     * @param nombre Campos de la BBDD
     * @param so Campos de la BBDD
     * @param ultFecha Campos de la BBDD
     * @param nombreNodo Campos de la BBDD
     * @param releasee Campos de la BBDD
     * @param version Campos de la BBDD
     * @param arquitectura Campos de la BBDD
     * @param procesador Campos de la BBDD
     * @param memTotal Campos de la BBDD
     * @param memDisp Campos de la BBDD
     * @param usoCpu Campos de la BBDD
     * @param mac Campos de la BBDD
     * @param ultHora Campos de la BBDD
     */
    public InformacionSistema(String nombre, String so, LocalDate ultFecha, String nombreNodo, String releasee, String version, String arquitectura, String procesador, Integer memTotal, Integer memDisp, String usoCpu, String mac, LocalTime ultHora) {
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
    }

    public InformacionSistema(String nombre, String mac) {
        this.nombre = nombre;
        this.mac = mac;
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
     * Obtiene el nombre del nodo.
     *
     * @return el nombre del nodo
     */
    public String getNombreNodo() {
        return nombreNodo;
    }

    /**
     * Establece el nombre del nodo.
     *
     * @param nombreNodo el nuevo nombre del nodo
     */
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    /**
     * Obtiene la versión.
     *
     * @return la versión
     */
    public String getReleasee() {
        return releasee;
    }

    /**
     * Establece la versión.
     *
     * @param releasee la nueva versión
     */
    public void setReleasee(String releasee) {
        this.releasee = releasee;
    }

    /**
     * Obtiene la versión.
     *
     * @return la versión
     */
    public String getVersion() {
        return version;
    }

    /**
     * Establece la versión.
     *
     * @param version la nueva versión
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
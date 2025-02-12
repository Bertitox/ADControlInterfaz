package BBDD.DTO;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

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

    public InformacionSistema() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public LocalDate getUltFecha() {
        return ultFecha;
    }

    public void setUltFecha(LocalDate ultFecha) {
        this.ultFecha = ultFecha;
    }

    public String getNombreNodo() {
        return nombreNodo;
    }

    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    public String getReleasee() {
        return releasee;
    }

    public void setReleasee(String releasee) {
        this.releasee = releasee;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArquitectura() {
        return arquitectura;
    }

    public void setArquitectura(String arquitectura) {
        this.arquitectura = arquitectura;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public Integer getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(Integer memTotal) {
        this.memTotal = memTotal;
    }

    public Integer getMemDisp() {
        return memDisp;
    }

    public void setMemDisp(Integer memDisp) {
        this.memDisp = memDisp;
    }

    public String getUsoCpu() {
        return usoCpu;
    }

    public void setUsoCpu(String usoCpu) {
        this.usoCpu = usoCpu;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public LocalTime getUltHora() {
        return ultHora;
    }

    public void setUltHora(LocalTime ultHora) {
        this.ultHora = ultHora;
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s", id, nombre, so);
    }
}
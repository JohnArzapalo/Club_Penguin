package pe.edu.pucp.softinv.model.biblioteca;

import java.time.LocalTime;

public class DiaAtencionDTO {
    private Integer diaAtencionId;
    private BibliotecaDTO biblioteca;
    private String nombre;
    private LocalTime horaInicioAtencion;
    private LocalTime horaFinAtencion;
    
    public DiaAtencionDTO() {
        this.diaAtencionId = null;
        this.biblioteca=null;
        this.nombre=null;
        this.horaInicioAtencion = null;
        this.horaFinAtencion = null;
    }

    public DiaAtencionDTO(Integer diaAtencionId, BibliotecaDTO biblioteca, String nombre,
                            LocalTime horaDeInicioDeAtencion, LocalTime horaDeFinDeAtencion) {
        this.diaAtencionId = diaAtencionId;
        this.biblioteca=biblioteca;
        this.nombre = nombre;
        this.horaInicioAtencion = horaDeInicioDeAtencion;
        this.horaFinAtencion = horaDeFinDeAtencion;
    }
    
    public DiaAtencionDTO(DiaAtencionDTO otro) {
        this.diaAtencionId = otro.diaAtencionId;
        this.biblioteca=otro.biblioteca;
        this.nombre = otro.nombre;
        this.horaInicioAtencion = otro.horaInicioAtencion;
        this.horaFinAtencion = otro.horaFinAtencion;
    }
    
    @Override
    public String toString() {
        return "Día de Atención {" +
                "\n  ID = " + diaAtencionId +
                ",\n Biblioteca = " + biblioteca.getBibliotecaId() +
                ",\n Nombre = " + nombre +
                ",\n Hora de inicio = " + horaInicioAtencion +
                ",\n Hora de fin = " + horaFinAtencion +
                "\n}";
    }
    
    public Integer getDiaAtencionId() {
        return diaAtencionId;
    }
    
    public void setDiaAtencionId(Integer diaAtencionId) {
        this.diaAtencionId = diaAtencionId;
    }
    
    public BibliotecaDTO getBiblioteca() {
        return biblioteca;
    }
    
    public void setBiblioteca(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public LocalTime getHoraInicioAtencion() {
        return horaInicioAtencion;
    }
    
    public void setHoraInicioAtencion(LocalTime horaInicioAtencion) {
        this.horaInicioAtencion = horaInicioAtencion;
    }
    
    public LocalTime getHoraFinAtencion() {
        return horaFinAtencion;
    }
    
    public void setHoraFinAtencion(LocalTime horaFinAtencion) {
        this.horaFinAtencion = horaFinAtencion;
    }
}

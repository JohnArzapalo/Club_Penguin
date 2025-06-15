package pe.edu.pucp.softinv.model.circulacion;

import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import java.util.Date;

public class CirculacionDTO {
    private Integer circulacionId;
    private ReservaDTO reserva;
    private UsuarioDTO usuario;
    private EjemplarDTO ejemplar;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private Date fechaVencimiento;
    private EstadoPrestamo estadoPrestamo;
    
    public CirculacionDTO() {
        this.circulacionId = null;
        this.reserva = null;
        this.usuario = null;
        this.ejemplar = null;
        this.fechaPrestamo = null;
        this.fechaDevolucion = null;
        this.fechaVencimiento = null;
        this.estadoPrestamo = null;
    }

    public CirculacionDTO(Integer circulacionId, Date fechaPrestamo, Date fechaDevolucion, Date fechaVencimiento,
                          EstadoPrestamo estadoPrestamo, UsuarioDTO usuario, EjemplarDTO ejemplar, ReservaDTO reserva) {
        this.circulacionId = circulacionId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoPrestamo = estadoPrestamo;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.reserva = reserva;
    }

    public CirculacionDTO(CirculacionDTO otro) {
        this.circulacionId = otro.circulacionId;
        this.fechaPrestamo = otro.fechaPrestamo;
        this.fechaDevolucion = otro.fechaDevolucion;
        this.fechaVencimiento = otro.fechaVencimiento;
        this.estadoPrestamo = otro.estadoPrestamo;
        this.usuario = otro.usuario;
        this.ejemplar = otro.ejemplar;
        this.reserva = otro.reserva;
    }

    @Override
    public String toString() {
        return "Circulacion {" +
               "\n  ID = " + circulacionId +
               ",\n  Reserva ID = " + reserva.getReservaId() +
               ",\n  Usuario = " + usuario.getNombres() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido() +
               ",\n  Material = " + ejemplar.getMaterial().getTitulo() +
               ",\n  Ejemplar ID = " + ejemplar.getEjemplarId() +
               ",\n  Fecha Préstamo = " + fechaPrestamo +
               ",\n  Fecha Vencimiento = " + fechaVencimiento +
               ",\n  Fecha Devolución = " + fechaDevolucion +
               ",\n  Estado = " + estadoPrestamo.getNombreMostrar() +
               "\n}";
    }

    public Integer getCirculacionId() {
        return circulacionId;
    }

    public void setCirculacionId(Integer circulacionId) {
        this.circulacionId = circulacionId;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public EjemplarDTO getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarDTO ejemplar) {
        this.ejemplar = ejemplar;
    }
    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }
}

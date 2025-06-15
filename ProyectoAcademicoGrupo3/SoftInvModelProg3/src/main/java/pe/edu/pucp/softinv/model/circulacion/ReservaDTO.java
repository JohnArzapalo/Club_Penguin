package pe.edu.pucp.softinv.model.circulacion;

import java.util.Date;
import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;

public class ReservaDTO {
    private Integer reservaId;
    private UsuarioDTO usuario;
    private MaterialDTO material;
    private Date fechaReserva;
    private Date fechaVencimiento;
    private EstadoReserva estadoReserva;
    
    public ReservaDTO() {
        this.reservaId = null;
        this.usuario = null;
        this.material = null;
        this.fechaReserva = null;
        this.fechaVencimiento = null;
        this.estadoReserva = null;
    }

    public ReservaDTO(Integer reservaId, UsuarioDTO usuario, MaterialDTO material,
            Date fechaReserva, Date fechaVencimiento, EstadoReserva estadoReserva) {
        this.reservaId = reservaId;
        this.usuario = usuario;
        this.material = material;
        this.fechaReserva = fechaReserva;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoReserva = estadoReserva;
    }

    public ReservaDTO(ReservaDTO otro) {
        this.reservaId = otro.reservaId;
        this.usuario = otro.usuario;
        this.material = otro.material;
        this.fechaReserva = otro.fechaReserva;
        this.fechaVencimiento = otro.fechaVencimiento;
        this.estadoReserva = otro.estadoReserva;
    }

    @Override
    public String toString() {
        return "Reserva {" +
               "\n  ID = " + reservaId +
               ",\n  Usuario = " + usuario.getNombres() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido() +
               ",\n  Material = '" + material.getTitulo() + '\'' +
               ",\n  Fecha de Reserva = " + fechaReserva +
               ",\n  Fecha de Vencimiento = " + getFechaVencimiento() +
               ",\n  Estado = '" + estadoReserva.getNombreMostrar() + '\'' +
               "\n}";
    }
    
    public Integer getReservaId() {
        return reservaId;
    }

    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public MaterialDTO getMaterial() {
        return material;
    }
    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}

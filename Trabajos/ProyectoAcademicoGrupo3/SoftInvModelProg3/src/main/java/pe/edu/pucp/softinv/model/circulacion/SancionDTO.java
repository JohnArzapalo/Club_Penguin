package pe.edu.pucp.softinv.model.circulacion;

import java.util.Date;

public class SancionDTO {
    private Integer sancionId;
    private CirculacionDTO circulacion;
    private Date fechaRegistro;
    private Date fechaTermino;
    private Integer diasSancion;
    private String observacion;
    
    public SancionDTO() {
        this.sancionId = null;
        this.circulacion = null;
        this.fechaRegistro = null;
        this.fechaTermino = null;
        this.diasSancion = null;
        this.observacion = null;
    }

    public SancionDTO(Integer sancionId, String observacion, CirculacionDTO circulacion,
                      Date fechaRegistro, Date fechaTermino, Integer diasSancion) {
        this.sancionId = sancionId;
        this.observacion = observacion;
        this.circulacion = circulacion;
        this.fechaRegistro = fechaRegistro;
        this.fechaTermino = fechaTermino;
        this.diasSancion = diasSancion;
    }

    public SancionDTO(SancionDTO otro) {
        this.sancionId = otro.sancionId;
        this.observacion = otro.observacion;
        this.circulacion = otro.circulacion;
        this.fechaRegistro = otro.fechaRegistro;
        this.fechaTermino = otro.fechaTermino;
        this.diasSancion = otro.diasSancion;
    }
    
    @Override
    public String toString() {
        return "Sancion {" +
               "\n  ID = " + sancionId +
               ",\n  Circulación ID = " + circulacion.getCirculacionId() +
               ",\n  Fecha Registro = " + fechaRegistro +
               ",\n  Fecha Término = " + fechaTermino +
               ",\n  Días de Sanción = " + diasSancion +
               ",\n  Observación = '" + observacion + '\'' +
               "\n}";
    }

    public Integer getSancionId() {
        return sancionId;
    }

    public void setSancionId(Integer sancionId) {
        this.sancionId = sancionId;
    }

    public CirculacionDTO getCirculacion() {
        return circulacion;
    }

    public void setCirculacion(CirculacionDTO circulacion) {
        this.circulacion = circulacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Integer getDiasSancion() {
        return diasSancion;
    }

    public void setDiasSancion(Integer diasSancion) {
        this.diasSancion = diasSancion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

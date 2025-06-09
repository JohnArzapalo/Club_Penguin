package pe.edu.pucp.softinv.model.usuario;

public class TipoUsuarioDTO {
    private Integer tipoUsuarioId;
    private String nombre;
    private Integer numeroMaxItems;
    private Integer numeroMaxDias;
    
    public TipoUsuarioDTO() {
        this.tipoUsuarioId = null;
        this.nombre = null;
        this.numeroMaxItems = null;
        this.numeroMaxDias = null;
    }

    public TipoUsuarioDTO(Integer tipoUsuarioId, String nombre, Integer numeroMaxItems, Integer numeroMaxDias) {
        this.tipoUsuarioId = tipoUsuarioId;
        this.nombre = nombre;
        this.numeroMaxItems = numeroMaxItems;
        this.numeroMaxDias = numeroMaxDias;
    }

    public TipoUsuarioDTO(TipoUsuarioDTO otro) {
        this.tipoUsuarioId = otro.tipoUsuarioId;
        this.nombre = otro.nombre;
        this.numeroMaxItems = otro.numeroMaxItems;
        this.numeroMaxDias = otro.numeroMaxDias;
    }
    
    @Override
    public String toString() {
        return "TipoUsuario {" +
               "\n  ID = " + tipoUsuarioId +
               ",\n  Nombre = '" + nombre + '\'' +
               ",\n  Máximo de ítems = " + numeroMaxItems +
               ",\n  Máximo de días = " + numeroMaxDias +
               "\n}";
    }

    public Integer getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(Integer tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroMaxItems() {
        return numeroMaxItems;
    }

    public void setNumeroMaxItems(Integer numeroMaxItems) {
        this.numeroMaxItems = numeroMaxItems;
    }

    public Integer getNumeroMaxDias() {
        return numeroMaxDias;
    }

    public void setNumeroMaxDias(Integer numeroMaxDias) {
        this.numeroMaxDias = numeroMaxDias;
    }
}

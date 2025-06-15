package pe.edu.pucp.softinv.model.material;

import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;

public class EjemplarDTO {
    private Integer ejemplarId;
    private MaterialDTO material;
    private BibliotecaDTO biblioteca;
    private String locacionEnBiblioteca;
    private EstadoEjemplar estado;
    private Boolean eliminable;

    public EjemplarDTO() {
        this.ejemplarId = null;
        this.material = null;
        this.biblioteca = null;
        this.locacionEnBiblioteca = null;
        this.estado = null;
        this.eliminable = true;
    }
    
    public EjemplarDTO(Integer ejemplarId, MaterialDTO material, BibliotecaDTO biblioteca,
                       String locacionEnBiblioteca, EstadoEjemplar estado, Boolean eliminable) {
        this.ejemplarId = ejemplarId;
        this.material = material;
        this.biblioteca = biblioteca;
        this.locacionEnBiblioteca = locacionEnBiblioteca;
        this.estado = estado;
        this.eliminable = eliminable;
    }

    public EjemplarDTO(EjemplarDTO otro) {
        this.ejemplarId = otro.ejemplarId;
        this.material = otro.material;
        this.biblioteca = otro.biblioteca;
        this.locacionEnBiblioteca = otro.locacionEnBiblioteca;
        this.estado = otro.estado;
        this.eliminable = otro.eliminable;
    }
    
    @Override
    public String toString() {
        return "Ejemplar {" +
               "\n  ID = " + ejemplarId +
               ",\n  Material ID = " + material.getMaterialId() +
               ",\n  Biblioteca ID = " + biblioteca.getBibliotecaId() +
               ",\n  Ubicación = '" + locacionEnBiblioteca + '\'' +
               ",\n  Estado = '" + estado.getNombreMostrar() + '\'' +
               ",\n  Eliminable = " + eliminable +
               "\n}";
    }

    public Integer getEjemplarId() {
        return ejemplarId;
    }

    public void setEjemplarId(Integer ejemplarId) {
        this.ejemplarId = ejemplarId;
    }
    
    public MaterialDTO getMaterial() {
        return material;
    }
    
    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }
    
    public BibliotecaDTO getBiblioteca() {
        return biblioteca;
    }
    
    public void setBiblioteca(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
    }
    
    public EstadoEjemplar getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoEjemplar estado) {
        this.estado = estado;
    }
    
    public void setLocacionEnBiblioteca(String locacionEnBiblioteca) {
        this.locacionEnBiblioteca = locacionEnBiblioteca;
    }

    public String getLocacionEnBiblioteca() {
        return locacionEnBiblioteca;
    }
    
    public Boolean getEliminable() {
        return eliminable;
    }

    public void setEliminable(Boolean eliminable) {
        this.eliminable = eliminable;
    }    
}

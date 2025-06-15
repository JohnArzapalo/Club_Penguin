package pe.edu.pucp.softinv.model.biblioteca;

public class BibliotecaDTO {
    private Integer bibliotecaId;
    private String nombre;
    private String ubicacion;
    
    public BibliotecaDTO() {
        this.bibliotecaId = null;
        this.nombre = null;
        this.ubicacion = null;
    }

    public BibliotecaDTO(Integer bibliotecaId, String nombre, String ubicacion) {
        this.bibliotecaId = bibliotecaId;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public BibliotecaDTO(BibliotecaDTO otro) {
        this.bibliotecaId = otro.bibliotecaId;
        this.nombre = otro.nombre;
        this.ubicacion = otro.ubicacion;
    }
    
    @Override
    public String toString() {
        return "Biblioteca {" +
               "\n  ID = " + bibliotecaId + 
               ",\n Nombre = " + nombre +
               ",\n Ubicación = " + ubicacion +
               "\n}";
    }

    public Integer getBibliotecaId() {
        return bibliotecaId;
    }

    public void setBibliotecaId(Integer bibliotecaId) {
        this.bibliotecaId = bibliotecaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}

package pe.edu.pucp.softinv.model.material;

public class LibroDTO extends MaterialDTO {
    private String isbn;
    private String edicion;
    private String editorial;

    public LibroDTO() {
        super();
        this.isbn = null;
        this.edicion = null;
        this.editorial = null;
    }

    public LibroDTO(Integer materialId, String titulo, String autor, Integer anioPublicacion,
            TipoMaterial tipoMaterial, Integer numeroPaginas, String tema, String idioma,
            String isbn, String edicion, String editorial) {
        super(materialId, titulo, autor, anioPublicacion, tipoMaterial, numeroPaginas,
                tema, idioma);
        this.isbn = isbn;
        this.edicion = edicion;
        this.editorial = editorial;
    }

    public LibroDTO(LibroDTO otro) {
        super(otro);
        this.isbn = otro.isbn;
        this.edicion = otro.edicion;
        this.editorial = otro.editorial;
    }
    
    @Override
    public String toString() {
        return "Libro {" +
                "\n  ID = " + getMaterialId() +
                ",\n  Título = '" + getTitulo() + '\'' +
                ",\n  Autor = '" + getAutor() + '\'' +
                ",\n  Año de Publicación = " + getAnioPublicacion() +
                ",\n  Tipo de Material = '" + getTipoMaterial().getNombreMostrar() + '\'' +
                ",\n  Número de Páginas = " + getNumeroPaginas() +
                ",\n  Tema = '" + getTema() + '\'' +
                ",\n  Idioma = '" + getIdioma() + '\'' +
                ",\n  ISBN = '" + isbn + '\'' +
                ",\n  Edición = '" + edicion + '\'' +
                ",\n  Editorial = '" + editorial + '\'' +
                "\n}";
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
}
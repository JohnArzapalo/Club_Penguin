package pe.edu.pucp.softinv.model.material;

public enum TipoMaterial {
    LIBRO, ARTICULO, TESIS;

    public String getNombreMostrar() {
        return switch (this) {
            case LIBRO -> "Libro";
            case ARTICULO -> "Artículo";
            case TESIS -> "Tesis";
        };
    }
}
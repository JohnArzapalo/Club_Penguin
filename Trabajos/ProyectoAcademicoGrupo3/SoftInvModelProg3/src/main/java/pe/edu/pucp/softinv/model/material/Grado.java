package pe.edu.pucp.softinv.model.material;

public enum Grado {
    PREGRADO, POSGRADO;

    public String getNombreMostrar() {
        return switch (this) {
            case PREGRADO -> "Pregrado";
            case POSGRADO -> "Posgrado";
        };
    }
}
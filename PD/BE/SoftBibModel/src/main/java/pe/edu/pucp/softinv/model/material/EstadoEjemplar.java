package pe.edu.pucp.softinv.model.material;

public enum EstadoEjemplar {
    DISPONIBLE, PRESTADO, EN_REPARACION;

    public String getNombreMostrar() {
        return switch (this) {
            case DISPONIBLE -> "Disponible";
            case PRESTADO -> "Prestado";
            case EN_REPARACION -> "En Reparación";
        };
    }
}

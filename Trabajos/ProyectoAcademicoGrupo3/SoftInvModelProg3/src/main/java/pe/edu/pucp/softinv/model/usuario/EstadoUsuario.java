package pe.edu.pucp.softinv.model.usuario;

public enum EstadoUsuario {
    ACTIVO, SANCIONADO;

    public String getNombreMostrar() {
        return switch (this) {
            case ACTIVO -> "Activo";
            case SANCIONADO -> "Sancionado";
        };
    }
}

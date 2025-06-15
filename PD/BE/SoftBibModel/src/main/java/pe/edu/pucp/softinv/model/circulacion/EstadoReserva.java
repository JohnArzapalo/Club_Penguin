package pe.edu.pucp.softinv.model.circulacion;

public enum EstadoReserva {
    VIGENTE, VENCIDA;

    public String getNombreMostrar() {
        return switch (this) {
            case VIGENTE -> "Vigente";
            case VENCIDA -> "Vencida";
        };
    }
}

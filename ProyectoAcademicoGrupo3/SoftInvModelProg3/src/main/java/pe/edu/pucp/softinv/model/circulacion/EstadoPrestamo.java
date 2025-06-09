package pe.edu.pucp.softinv.model.circulacion;

public enum EstadoPrestamo {
    VIGENTE,
    DEVUELTO_A_TIEMPO,
    DEVUELTO_CON_RETRASO,
    DEVUELTO_DANADO_O_PERDIDO,
    DEVUELTO_RETRASO_Y_DANO_PERDIDA,
    NO_DEVUELTO;

    public String getNombreMostrar() {
        return switch (this) {
            case VIGENTE -> "Vigente";
            case DEVUELTO_A_TIEMPO -> "Devuelto a tiempo";
            case DEVUELTO_CON_RETRASO -> "Devuelto con retraso";
            case DEVUELTO_DANADO_O_PERDIDO -> "Devuelto con daño o pérdida declarada";
            case DEVUELTO_RETRASO_Y_DANO_PERDIDA -> "Devuelto con retraso y daño/pérdida";
            case NO_DEVUELTO -> "No devuelto";
        };
    }
}

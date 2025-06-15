package pe.edu.pucp.softinv.model.usuario;

public enum TipoDocumento {
    DNI, PASAPORTE, CARNET_DE_EXTRANJERIA;

    public String getNombreMostrar() {
        return switch (this) {
            case DNI -> "DNI";
            case PASAPORTE -> "Pasaporte";
            case CARNET_DE_EXTRANJERIA -> "Carnet de Extranjería";
        };
    }
}

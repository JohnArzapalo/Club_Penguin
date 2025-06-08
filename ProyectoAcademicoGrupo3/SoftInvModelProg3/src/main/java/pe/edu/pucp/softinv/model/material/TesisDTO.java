package pe.edu.pucp.softinv.model.material;

public class TesisDTO extends MaterialDTO {

    private String nombreInstitucionPublicacion;
    private String asesorTesis;
    private String especialidad;
    private Grado grado;

    public TesisDTO() {
        super();
        this.nombreInstitucionPublicacion = null;
        this.asesorTesis = null;
        this.especialidad = null;
        this.grado = null;
    }

    public TesisDTO(Integer materialId, String titulo, String autor, Integer anioPublicacion, TipoMaterial tipoMaterial, Integer numeroPaginas,
            String tema, String idioma,String nombreInstitucionPublicacion, String asesorTesis, String especialidad, Grado grado) {
        super(materialId, titulo, autor, anioPublicacion, tipoMaterial, numeroPaginas,
                tema, idioma);
        this.nombreInstitucionPublicacion = nombreInstitucionPublicacion;
        this.asesorTesis = asesorTesis;
        this.especialidad = especialidad;
        this.grado = grado;
    }

    public TesisDTO(TesisDTO otro) {
        super(otro);
        this.nombreInstitucionPublicacion = otro.nombreInstitucionPublicacion;
        this.asesorTesis = otro.asesorTesis;
        this.especialidad = otro.especialidad;
        this.grado = otro.grado;
    }
    
    @Override
public String toString() {
    return "Tesis {" +
            "\n  ID = " + getMaterialId() +
            ",\n  Título = '" + getTitulo() + '\'' +
            ",\n  Autor = '" + getAutor() + '\'' +
            ",\n  Año de Publicación = " + getAnioPublicacion() +
            ",\n  Tipo de Material = '" + getTipoMaterial().getNombreMostrar() + '\'' +
            ",\n  Número de Páginas = " + getNumeroPaginas() +
            ",\n  Tema = '" + getTema() + '\'' +
            ",\n  Idioma = '" + getIdioma() + '\'' +
            ",\n  Institución de Publicación = '" + getNombreInstitucionPublicacion()+ '\'' +
            ",\n  Asesor de Tesis = '" + getAsesorTesis() + '\'' +
            ",\n  Especialidad = '" + getEspecialidad()+ '\'' +
            ",\n  Grado = '" + getGrado().getNombreMostrar() + '\'' +
            "\n}";
}

    public String getNombreInstitucionPublicacion() {
        return nombreInstitucionPublicacion;
    }

    public void setNombreInstitucionPublicacion(String nombreInstitucionPublicacion) {
        this.nombreInstitucionPublicacion = nombreInstitucionPublicacion;
    }

    public String getAsesorTesis() {
        return asesorTesis;
    }

    public void setAsesorTesis(String asesorTesis) {
        this.asesorTesis = asesorTesis;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }
}

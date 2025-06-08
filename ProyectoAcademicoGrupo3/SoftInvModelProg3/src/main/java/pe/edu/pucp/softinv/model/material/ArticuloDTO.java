package pe.edu.pucp.softinv.model.material;

public class ArticuloDTO extends MaterialDTO {
    private String issn;
    private String nombreRevista;
    private String editorial;
    private String volumen;
    private Integer numero;

    public ArticuloDTO() {
        super();
        this.issn = null;
        this.nombreRevista = null;
        this.editorial = null;
        this.volumen = null;
        this.numero = null;
    }

    public ArticuloDTO(Integer materialId, String titulo, String autor, Integer anioPublicacion, TipoMaterial tipoMaterial, Integer numeroPaginas,
            String tema, String idioma, String issn, String nombreRevista, String editorial, String volumen, Integer numero) {
        super(materialId, titulo, autor, anioPublicacion, tipoMaterial, numeroPaginas,
                tema, idioma);
        this.issn = issn;
        this.nombreRevista = nombreRevista;
        this.editorial = editorial;
        this.volumen = volumen;
        this.numero = numero;        
    }

    public ArticuloDTO(ArticuloDTO otro) {
        super(otro);
        this.issn = otro.issn;
        this.nombreRevista = otro.nombreRevista;
        this.editorial = otro.editorial;
        this.volumen = otro.volumen;
        this.numero = otro.numero;
    }
    
    @Override
    public String toString() {
    return "Artículo {" +
            "\n  ID = " + getMaterialId()+
            ",\n  Título = '" + getTitulo() + '\'' +
            ",\n  Autor = '" + getAutor() + '\'' +
            ",\n  Año de Publicación = " + getAnioPublicacion() +
            ",\n  Tipo de Material = '" + getTipoMaterial().getNombreMostrar() + '\'' +
            ",\n  Número de Páginas = " + getNumeroPaginas() +
            ",\n  Tema = '" + getTema() + '\'' +
            ",\n  Idioma = '" + getIdioma() + '\'' +
            ",\n  ISSN = '" + issn + '\'' +
            ",\n  Revista = '" + nombreRevista + '\'' +
            ",\n  Editorial = '" + editorial + '\'' +
            ",\n  Volumen = '" + volumen + '\'' +
            ",\n  Número = " + numero +
            "\n}";
}


    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }
    
    public String getNombreRevista() {
        return nombreRevista;
    }

    public void setNombreRevista(String nombreRevista) {
        this.nombreRevista = nombreRevista;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}

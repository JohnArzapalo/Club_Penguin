package pe.edu.pucp.softinv.model.material;

public class MaterialDTO {    
    private Integer materialId;
    private String titulo;
    private String autor;
    private Integer anioPublicacion;
    private TipoMaterial tipoMaterial;
    private Integer numeroPaginas;
    private String tema;
    private String idioma;
    
    public MaterialDTO() {
        this.materialId = null;
        this.titulo = null;
        this.autor = null;
        this.anioPublicacion = null;
        this.tipoMaterial = null;
        this.numeroPaginas = null;
        this.tema = null;
        this.idioma = null;
    }
    
    public MaterialDTO(Integer materialId, String titulo, String autor, Integer anioPublicacion, 
                         TipoMaterial tipoMaterial, Integer numeroPaginas, String tema, String idioma) {
        this.materialId = materialId;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.tipoMaterial = tipoMaterial;
        this.numeroPaginas = numeroPaginas;
        this.tema = tema;
        this.idioma = idioma;
    }
    
    public MaterialDTO(MaterialDTO otro) {
        this.materialId = otro.materialId;
        this.titulo = otro.titulo;
        this.autor = otro.autor;
        this.anioPublicacion = otro.anioPublicacion;
        this.tipoMaterial = otro.tipoMaterial;
        this.numeroPaginas = otro.numeroPaginas;
        this.tema = otro.tema;
        this.idioma = otro.idioma;
    }
    
    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
}
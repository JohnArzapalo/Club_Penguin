package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.model.material.LibroDTO;
import pe.edu.pucp.softinv.model.material.ArticuloDTO;
import pe.edu.pucp.softinv.model.material.TesisDTO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.model.material.EstadoEjemplar;
import pe.edu.pucp.softinv.model.material.TipoMaterial;
import pe.edu.pucp.softinv.dao.MaterialDAO;
import pe.edu.pucp.softinv.daoImpl.MaterialDAOImpl;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class MaterialBO {

    private final MaterialDAO materialDAO;

    public MaterialBO() {
        this.materialDAO = new MaterialDAOImpl();
    }

    public Integer insertar(MaterialDTO material) throws SQLException {
        validar(material,false);
        return materialDAO.insertar(material);
    }

    public Integer modificar(MaterialDTO material) throws SQLException {
        validar(material,true);
        return materialDAO.modificar(material);
    }

    public Integer eliminar(int id) throws SQLException {
        validar(id);
        MaterialDTO material = new MaterialDTO();
        material.setMaterialId(id);
        return materialDAO.eliminar(material);
    }

    public MaterialDTO obtenerPorId(int id) throws SQLException {
        validar(id);
        return materialDAO.obtenerPorId(id);
    }

    public List<MaterialDTO> listarTodos() throws SQLException {
        return materialDAO.listarTodos();
    }
    
    public List<MaterialDTO> buscarPorTitulo(String titulo) throws  SQLException{
        return materialDAO.buscarPorTitulo(titulo);
    }

    public int[] obtenerCopias(int id) {
        validar(id);
        return materialDAO.obtenerCopias(id);
    }
    
    public int[] obtenerEjemplaresReservadosYDisponibles(int materialId,int bibliotecaId)  throws SQLException{
        validar(materialId);
        return materialDAO.obtenerEjemplaresReservadosYDisponibles(materialId, bibliotecaId);
    }
    
    private void validar(Integer id){
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID de material inválido.");
        }
    }
    
    private void validar(MaterialDTO m, boolean esModificacion) {
        if (esModificacion) {
            if (m.getMaterialId()== null || m.getMaterialId() <= 0)
                throw new IllegalArgumentException("ID de material inválido.");
        }
        if (m.getTitulo() == null || m.getTitulo().isBlank())
            throw new IllegalArgumentException("El título es obligatorio.");

        if (m.getAutor() == null || m.getAutor().isBlank())
            throw new IllegalArgumentException("El autor es obligatorio.");

        if (m.getAnioPublicacion() == null || m.getAnioPublicacion() > 2025)
            throw new IllegalArgumentException("Año de publicación inválido.");

        if (m.getTipoMaterial() == null)
            throw new IllegalArgumentException("Debe indicarse el tipo de material.");

        if (m.getIdioma() == null || m.getIdioma().isBlank())
            throw new IllegalArgumentException("El idioma es obligatorio.");
        if (m.getTema()== null || m.getTema().isBlank())
            throw new IllegalArgumentException("El tema es obligatorio.");
        
        switch (m.getTipoMaterial().getNombreMostrar()) {
            case "LIBRO" -> validarLibro((LibroDTO) m); 
            case "ARTICULO" -> validarArticulo((ArticuloDTO) m);
            case "TESIS" -> validarTesis((TesisDTO) m);
        }
    }

    private void validarLibro(LibroDTO l) {
        if (l.getIsbn() == null || l.getIsbn().isBlank())
            throw new IllegalArgumentException("El ISBN es obligatorio.");
        
        if (l.getEdicion()== null || l.getEdicion().isBlank())
            throw new IllegalArgumentException("La edición es obligatoria.");
        
        if (l.getEditorial()== null || l.getEditorial().isBlank())
            throw new IllegalArgumentException("La editorial es obligatoria.");
    }

    private void validarArticulo(ArticuloDTO a) {
        if (a.getIssn() == null || a.getIssn().isBlank())
            throw new IllegalArgumentException("El ISSN es obligatorio.");     
        
        if (a.getEditorial()== null || a.getEditorial().isBlank())
            throw new IllegalArgumentException("La editorial es obligatoria.");
        
        if (a.getNombreRevista() == null || a.getNombreRevista().isBlank())
            throw new IllegalArgumentException("El nombre de la revista es obligatorio.");
        
        if (a.getVolumen()== null || a.getVolumen().isBlank())
            throw new IllegalArgumentException("El volumen es obligatorio.");
        
        if (a.getNumero()== null || a.getNumero() <= 0)
            throw new IllegalArgumentException("El número es inválido.");
    }

    private void validarTesis(TesisDTO t) {
        if (t.getNombreInstitucionPublicacion() == null || t.getNombreInstitucionPublicacion().isBlank())
            throw new IllegalArgumentException("La institución de publicación es obligatoria.");
        
        if (t.getAsesorTesis()== null || t.getAsesorTesis().isBlank())
            throw new IllegalArgumentException("El asesor de la tesis es obligatoria.");
        
        if (t.getEspecialidad()== null || t.getEspecialidad().isBlank())
            throw new IllegalArgumentException("La especialidad es obligatoria.");
        
        if (t.getGrado() == null)
            throw new IllegalArgumentException("El grado es obligatorio.");
    }
    
    public MaterialDTO busquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
        String anioPublicacion, String tipoMaterialTexto,String disponibilidadTexto) throws SQLException {
        int anioPublicacionInt = Integer.parseInt(anioPublicacion);
        TipoMaterial tipoDato =TipoMaterial.valueOf(tipoMaterialTexto);
        material.setAnioPublicacion(anioPublicacionInt);
        material.setTipoMaterial(tipoDato);
        validarBusquedaAvanzada(material, biblioteca, ejemplar, anioPublicacion, tipoMaterialTexto, disponibilidadTexto);
        
        return materialDAO.busquedaAvanzada(material, biblioteca, ejemplar, anioPublicacion, tipoMaterialTexto, disponibilidadTexto);
    }
    
    private void validarBusquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
        String anioPublicacion, String tipoMaterialTexto,String disponibilidadTexto) {
        validar(material, false);
        if (biblioteca.getNombre()== null || biblioteca.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre de biblioteca es obligatorio.");
         
        EstadoEjemplar tipoDato =EstadoEjemplar.valueOf(disponibilidadTexto);
        ejemplar.setEstado(tipoDato);
        
        if (ejemplar.getEstado()== null )
            throw new IllegalArgumentException("El estado del ejemplar es obligatorio.");
        if (anioPublicacion== null || anioPublicacion.isBlank())
            throw new IllegalArgumentException("El año de publicacion es obligatorio.");
        if (tipoMaterialTexto== null || tipoMaterialTexto.isBlank())
            throw new IllegalArgumentException("El tipo material es obligatorio.");
     }
     
    public int busqueda(MaterialDTO material, String anioPublicacion, String tipoMaterialTexto) throws SQLException {
        int anioPublicacionInt = Integer.parseInt(anioPublicacion);
        TipoMaterial tipoDato =TipoMaterial.valueOf(tipoMaterialTexto);
        material.setAnioPublicacion(anioPublicacionInt);
        material.setTipoMaterial(tipoDato);
        validar(material, false);
        
        if (anioPublicacion== null || anioPublicacion.isBlank())
            throw new IllegalArgumentException("El año de publicacion es obligatorio.");
        if (tipoMaterialTexto== null || tipoMaterialTexto.isBlank())
            throw new IllegalArgumentException("El tipo material es obligatorio.");
        
        return materialDAO.busqueda(material, anioPublicacion, tipoMaterialTexto);
    }
    
    public List<MaterialDTO> buscarPorTipoMaterial(String tipoMaterial) throws SQLException {
        validarTipoMaterial(tipoMaterial);
        return materialDAO.buscarPorTipoMaterial(tipoMaterial);
    }        
    
    private void validarTipoMaterial(String tipoMaterial){
        if (tipoMaterial== null || tipoMaterial.isBlank())
            throw new IllegalArgumentException("El tipo de material es obligatorio.");
    }
    
    public List<MaterialDTO> buscarPorTema(String tema) throws SQLException {
        validarTema(tema);
        return materialDAO.buscarPorTema(tema);
    }        
    
    private void validarTema(String tema){
        if (tema== null || tema.isBlank())
            throw new IllegalArgumentException("El tema es obligatorio.");
    }
    
    
    public List<MaterialDTO> buscarPorIdioma(String idioma) throws SQLException {
        validarIdioma(idioma);
        return materialDAO.buscarPorIdioma(idioma);
    }        
    
    private void validarIdioma(String idioma){
        if (idioma== null || idioma.isBlank())
            throw new IllegalArgumentException("El idioma es obligatorio.");
    }    
    
    public List<MaterialDTO> buscarPorDisponibilidad(String disponibildad) throws SQLException {
        validarDisponibilidad(disponibildad);
        return materialDAO.buscarPorDisponibilidad(disponibildad);
    }        
    
    private void validarDisponibilidad(String disponibildad){
        if (disponibildad== null || disponibildad.isBlank())
            throw new IllegalArgumentException("La disponibilidad es obligatorio.");
    }


    public List<MaterialDTO> buscarPorBiblioteca(String biblioteca) throws SQLException {
        validarBiblioteca(biblioteca);
        return materialDAO.buscarPorBiblioteca(biblioteca);
    }        
    
    private void validarBiblioteca(String biblioteca){
        if (biblioteca== null || biblioteca.isBlank())
            throw new IllegalArgumentException("La biblioteca es obligatorio.");
    } 
    
    
    public List<MaterialDTO> buscarPorAnio(String anio) throws SQLException {
        validarAnio(anio);
        return materialDAO.buscarPorAnio(anio);
    }        
    private void validarAnio(String anio){
        if (anio== null || anio.isBlank())
            throw new IllegalArgumentException("El año es obligatorio.");
    }
    
    public List<MaterialDTO> buscarPorAutor(String autor) throws SQLException {
        validarAutor(autor);
        return materialDAO.buscarPorAutor(autor);
    }    
    private void validarAutor(String autor){
        if (autor== null || autor.isBlank())
            throw new IllegalArgumentException("El autor es obligatorio.");
    }
}

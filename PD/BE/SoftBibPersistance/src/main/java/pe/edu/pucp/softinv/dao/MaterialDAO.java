package pe.edu.pucp.softinv.dao;

import pe.edu.pucp.softinv.model.material.MaterialDTO;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;

public interface MaterialDAO {

    public Integer insertar(MaterialDTO material);

    public MaterialDTO obtenerPorId(Integer materialId);

    public ArrayList<MaterialDTO> listarTodos();

    public Integer modificar(MaterialDTO material);

    public Integer eliminar(MaterialDTO material);
    
    public List<MaterialDTO> buscarPorTitulo(String titulo) throws  SQLException;
    
    public int[] obtenerCopias(int materialId);
    
    public int[] obtenerEjemplaresReservadosYDisponibles(int materialId, int bibliotecaId) throws SQLException;
    
    public int busqueda(MaterialDTO material, String anioPublicacion, String tipoMaterialTexto) throws SQLException;
    
    public ArrayList<MaterialDTO> busquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
            String anioPublicacionDesde, String anioPublicacionHasta,String tipoMaterialTexto,String disponibilidadTexto)  throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorAutor(String autor)  throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorAnio(String anioDesde, String anioHasta) throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorTipoMaterial(String tipoMaterial) throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorTema(String tema) throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorIdioma(String idioma) throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorDisponibilidad(String disponibildad) throws SQLException;
    
    public ArrayList<MaterialDTO> buscarPorBiblioteca(String biblioteca) throws SQLException;
    
    public boolean comprobarPorAutor(String autor, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorTipoMaterial(String tipoMaterial, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorTema(String tema, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorIdioma(String idioma, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorDisponibilidad(String disponibilidad, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorBiblioteca(String biblioteca, String idMaterial) throws SQLException ;
    
    public boolean comprobarPorAnio(String anioDesde, String anioHasta,String idMaterial) throws SQLException ;
    
    public String obtenerIdiomas(String idMaterial) throws SQLException;
    
    public List<String> obtenerIdiomasAvanzada() throws SQLException;
    
    public String obtenerTemas(String idMaterial) throws SQLException;
    
    public void envioCorreos(String destino, String asunto, String txt) ;
}

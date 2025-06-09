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
    
    public int busqueda(MaterialDTO material, String anioPublicacion, String tipoMaterialTexto);
    
    public MaterialDTO busquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
            String anioPublicacion, String tipoMaterialTexto,String disponibilidadTexto) ;
    
    public ArrayList<MaterialDTO> buscarPorAutor(String autor) ;
    
    public ArrayList<MaterialDTO> buscarPorAnio(String anio) ;
    
    public ArrayList<MaterialDTO> buscarPorTipoMaterial(String tipoMaterial);
    
    public ArrayList<MaterialDTO> buscarPorTema(String tema) ;
    
    public ArrayList<MaterialDTO> buscarPorIdioma(String idioma);
    
    public ArrayList<MaterialDTO> buscarPorDisponibilidad(String disponibildad);
    
    public ArrayList<MaterialDTO> buscarPorBiblioteca(String biblioteca);
    
}

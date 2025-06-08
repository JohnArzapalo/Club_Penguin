package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.dao.BibliotecaDAO;
import pe.edu.pucp.softinv.daoImpl.BibliotecaDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class BibliotecaBO {
    private final BibliotecaDAO bibliotecaDAO;

    public BibliotecaBO() {
        // Inicializamos el DAO correspondiente
        this.bibliotecaDAO = new BibliotecaDAOImpl();
    }

    // Método para insertar una nueva biblioteca
    public Integer insertar(BibliotecaDTO biblioteca) throws SQLException {
        validar(biblioteca,false);
        return bibliotecaDAO.insertar(biblioteca);
    }

    // Método para actualizar una biblioteca
    public Integer modificar(BibliotecaDTO biblioteca) throws SQLException {
        validar(biblioteca,true);
        return bibliotecaDAO.modificar(biblioteca);
    }

    // Método para eliminar una biblioteca
    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setBibliotecaId(id);
        return this.bibliotecaDAO.eliminar(biblioteca);
    }

    // Método para obtener una biblioteca por ID
    public BibliotecaDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return this.bibliotecaDAO.obtenerPorId(id);
    }
    
    public List<BibliotecaDTO> listarTodas() throws SQLException {
        return bibliotecaDAO.listarTodos();
    }
    
    public List<BibliotecaDTO> obtenerBibliotecasPorMaterial(int id) throws SQLException{
        validar(id);
        return bibliotecaDAO.obtenerBibliotecasPorMaterial(id);
    }
    
    private void validar(Integer id){
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID de biblioteca inválido.");
        }
    }
    
    private void validar(BibliotecaDTO b, boolean esModificacion) {
        if (esModificacion) {
            if (b.getBibliotecaId()== null || b.getBibliotecaId() <= 0)
                throw new IllegalArgumentException("ID de la biblioteca inválido.");
        }
        
        if (b.getNombre() == null || b.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (b.getUbicacion() == null || b.getUbicacion().isBlank())
            throw new IllegalArgumentException("La ubicación de es obligatoria.");
    }
}
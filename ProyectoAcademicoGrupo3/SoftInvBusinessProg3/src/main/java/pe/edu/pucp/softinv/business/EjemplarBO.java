package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.dao.EjemplarDAO;
import pe.edu.pucp.softinv.daoImpl.EjemplarDAOImpl;

import java.util.List;
import java.sql.SQLException;

public class EjemplarBO {
    
    private final EjemplarDAO ejemplarDAO;
    
    public EjemplarBO() {
        this.ejemplarDAO = new EjemplarDAOImpl();
    }
    
    // Método para insertar un nuevo ejemplar
    public Integer insertar(EjemplarDTO ejemplar) throws SQLException {
        validar(ejemplar, false);
        return this.ejemplarDAO.insertar(ejemplar);
    }
    
    // Método para actualizar un ejemplar
    public Integer modificar(EjemplarDTO ejemplar) throws SQLException {
        validar(ejemplar, true);
        return this.ejemplarDAO.modificar(ejemplar);
    }
    
    // Método para eliminar un ejemplar
    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        EjemplarDTO ejemplar = new EjemplarDTO();
        ejemplar.setEjemplarId(id);
        return this.ejemplarDAO.eliminar(ejemplar);
    }    
    
    // Método para obtener un ejemplar por su ID
    public EjemplarDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return this.ejemplarDAO.obtenerPorId(id);
    }
    
    // Método para listar todos los ejemplares
    public List<EjemplarDTO> listarTodos() throws SQLException {
        return this.ejemplarDAO.listarTodos();
    }
    
//    // Método para listar ejemplares por material
//    public List<EjemplarDTO> listarPorMaterial(Integer materialId) throws SQLException {
//        if (materialId == null || materialId <= 0)
//            throw new IllegalArgumentException("ID de material inválido.");
//        return ejemplarDAO.listarPorMaterial(materialId);
//    }
    
//    // Método para listar ejemplares por biblioteca
//    public List<EjemplarDTO> listarPorBiblioteca(Integer bibliotecaId) throws SQLException {
//        if (bibliotecaId == null || bibliotecaId <= 0)
//            throw new IllegalArgumentException("ID de biblioteca inválido.");
//        return ejemplarDAO.listarPorBiblioteca(bibliotecaId);
//    }
    
    public List<EjemplarDTO> obtenerEjemplaresMaterialPorBiblioteca(int materialId, int bibliotecaId) throws SQLException {
        return ejemplarDAO.obtenerEjemplaresMaterialPorBiblioteca(materialId, bibliotecaId);
    }
    
    private void validar(Integer id){
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID de ejemplar inválido.");
        }
    }
    
    private void validar(EjemplarDTO e, boolean esModificacion) {
        if (esModificacion) {
            if (e.getEjemplarId() == null || e.getEjemplarId() <= 0)
                throw new IllegalArgumentException("ID de ejemplar inválido.");
        }

        if (e.getMaterial() == null)
            throw new IllegalArgumentException("El ejemplar debe estar asociado a un material.");

        if (e.getBiblioteca() == null)
            throw new IllegalArgumentException("El ejemplar debe estar asociado a una biblioteca.");
    }
}
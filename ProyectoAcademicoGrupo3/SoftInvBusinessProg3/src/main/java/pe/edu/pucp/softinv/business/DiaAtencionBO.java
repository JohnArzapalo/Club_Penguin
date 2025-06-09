package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;
import pe.edu.pucp.softinv.dao.DiaAtencionDAO;
import pe.edu.pucp.softinv.daoImpl.DiaAtencionDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class DiaAtencionBO {
    private final DiaAtencionDAO diaAtencionDAO;

    public DiaAtencionBO() {
        this.diaAtencionDAO = new DiaAtencionDAOImpl();
    }

    // Insertar un nuevo día de atención
    public Integer insertar(DiaAtencionDTO diaAtencion) throws SQLException {
        validar(diaAtencion,false);
        return this.diaAtencionDAO.insertar(diaAtencion);
    }
    
    // Método para actualizar un día de atención
    public Integer modificar(DiaAtencionDTO diaAtencion) throws SQLException {
        if (diaAtencion.getDiaAtencionId() == null)
            throw new IllegalArgumentException("Debe indicarse el ID del día de atención para actualizar.");
        validar(diaAtencion,true);
        return this.diaAtencionDAO.modificar(diaAtencion);
    }
    
    // Eliminar un día de atención por ID
    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        DiaAtencionDTO diaAtencion = new DiaAtencionDTO();
        diaAtencion.setDiaAtencionId(id);
        return this.diaAtencionDAO.eliminar(diaAtencion);
    }
    
    // Método para obtener un día de atención por ID
    public DiaAtencionDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return this.diaAtencionDAO.obtenerPorId(id);
    }

    // Listar todos los días de atención
    public List<DiaAtencionDTO> listarTodas() throws SQLException {
        return diaAtencionDAO.listarTodos();
    }
    
    // Listar todos los días de atención de una biblioteca
    public List<DiaAtencionDTO> listarPorAtencionBiblioteca(Integer bibliotecaId) throws SQLException {
        return diaAtencionDAO.listarPorAtencionBiblioteca(bibliotecaId);
    }
    
    private void validar(Integer id){
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID de día de atención inválido.");
        }
    }
    
    private void validar(DiaAtencionDTO d, boolean esModificacion) {
        if (esModificacion) {
            if (d.getDiaAtencionId() == null || d.getDiaAtencionId()<= 0)
                throw new IllegalArgumentException("ID de día de atención inválido.");
        }
        
        if (d.getBiblioteca() == null || d.getBiblioteca().getBibliotecaId() == null)
            throw new IllegalArgumentException("Debe indicarse una biblioteca válida.");
        
        if (d.getNombre() == null || d.getNombre().isBlank())
            throw new IllegalArgumentException("Debe indicarse el nombre del día de atención.");
        
        if (d.getHoraInicioAtencion() == null)
            throw new IllegalArgumentException("Debe indicarse la hora de inicio de atención.");
        
        if (d.getHoraFinAtencion() == null)
            throw new IllegalArgumentException("Debe indicarse la hora de fin de atención.");
        
        if (d.getHoraFinAtencion().isBefore(d.getHoraInicioAtencion()))
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
    }
}
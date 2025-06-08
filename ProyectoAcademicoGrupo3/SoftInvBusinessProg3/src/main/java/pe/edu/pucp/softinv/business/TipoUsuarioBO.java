package pe.edu.pucp.softinv.business;

import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.dao.TipoUsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.TipoUsuarioDAOImpl;
import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;

public class TipoUsuarioBO {

    private final TipoUsuarioDAO tipoUsuarioDAO;

    public TipoUsuarioBO() {
        // Inicializamos el DAO correspondiente
        this.tipoUsuarioDAO = new TipoUsuarioDAOImpl();
    }
    
    // Método para insertar una biblioteca
    public Integer insertar(TipoUsuarioDTO tipoUsuario) throws SQLException {
        validar(tipoUsuario, false);
        return tipoUsuarioDAO.insertar(tipoUsuario);
    }

    // Método para modificar una biblioteca
    public Integer modificar(TipoUsuarioDTO tipoUsuario) throws SQLException {
        validar(tipoUsuario, true);
        return tipoUsuarioDAO.modificar(tipoUsuario);
    }

    // Método para eliminar una biblioteca
    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        TipoUsuarioDTO tipo = new TipoUsuarioDTO();
        tipo.setTipoUsuarioId(id);
        return tipoUsuarioDAO.eliminar(tipo);
    }

    // Método para obtener una biblioteca por ID
    public TipoUsuarioDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return tipoUsuarioDAO.obtenerPorId(id);
    }
    
    public List<TipoUsuarioDTO> listarTodos() throws SQLException {
        return tipoUsuarioDAO.listarTodos();
    }
    
    private void validar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de tipo de usuario inválido.");
        }
    }

    private void validar(TipoUsuarioDTO tipoUsuario, boolean esModificacion) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("El tipo de usuario no puede ser nulo.");
        }

        if (esModificacion) {
            if (tipoUsuario.getTipoUsuarioId() == null || tipoUsuario.getTipoUsuarioId() <= 0) {
                throw new IllegalArgumentException("ID de tipo de usuario inválido.");
            }
        }

        if (tipoUsuario.getNombre() == null || tipoUsuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (tipoUsuario.getNumeroMaxItems() == null || tipoUsuario.getNumeroMaxItems() < 0) {
            throw new IllegalArgumentException("El número máximo de ítems debe ser cero o mayor.");
        }

        if (tipoUsuario.getNumeroMaxDias() == null || tipoUsuario.getNumeroMaxDias() <= 0) {
            throw new IllegalArgumentException("El número máximo de días debe ser mayor a cero.");
        }
    }
}

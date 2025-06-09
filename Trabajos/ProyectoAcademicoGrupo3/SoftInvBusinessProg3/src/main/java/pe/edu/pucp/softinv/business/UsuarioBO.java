package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.usuario.EstudianteDTO;
import pe.edu.pucp.softinv.model.usuario.DocenteDTO;
import pe.edu.pucp.softinv.model.usuario.BibliotecarioDTO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.UsuarioDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class UsuarioBO {

    private final UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    public UsuarioDTO validarUsuario(String correo, String contrasena) throws SQLException {
        UsuarioDTO usuario = usuarioDAO.buscarPorCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(contrasena)) {
            return usuario;
        }
        return null;
    }
    
    public Integer insertar(UsuarioDTO usuario) throws SQLException {
        validar(usuario, false);
//        validar(usuario);
        return usuarioDAO.insertar(usuario);
    }

    public Integer modificar(UsuarioDTO usuario) throws SQLException {
        validar(usuario, true);
        return usuarioDAO.modificar(usuario);
    }

    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setUsuarioId(id);
        return usuarioDAO.eliminar(usuario);
    }

    public UsuarioDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return usuarioDAO.obtenerPorId(id);
    }

    public List<UsuarioDTO> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }
    
    public List<UsuarioDTO> buscarUsuariosAvanzado(String idUsuario, String tipoUsuario,
        String codigoUniversitario, String nombres, String primerApellido, String estado) throws SQLException {
        return usuarioDAO.listarPorBusquedaAvanzada(idUsuario, tipoUsuario, codigoUniversitario, nombres, primerApellido, estado);
    }
    
//    private void validar(UsuarioDTO u) {
//        // Validar código universitario único
//        UsuarioDTO existentePorCodigo = usuarioDAO.buscarPorCodigoUniversitario(u.getCodigoUniversitario());
//        if (existentePorCodigo != null) {
//            throw new IllegalArgumentException("Ya existe un usuario con ese código universitario.");
//        }
//
//        // Validar correo electrónico único
//        UsuarioDTO existentePorCorreo = usuarioDAO.buscarPorCorreo(u.getCorreoElectronico());
//        if (existentePorCorreo != null) {
//            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico.");
//        }
//    }
    
    private void validar(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido.");
        }
    }

    private void validar(UsuarioDTO u, boolean esModificacion) {
        if (u == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        if (esModificacion) {
            if (u.getUsuarioId() == null || u.getUsuarioId() <= 0) {
                throw new IllegalArgumentException("ID de usuario inválido.");
            }
        }
        
        if (u.getTipoUsuario() == null || u.getTipoUsuario().getTipoUsuarioId() == 0)
            throw new IllegalArgumentException("Debe indicarse el tipo de usuario.");
        
        if (u.getCodigoUniversitario() == null || u.getCodigoUniversitario().isBlank())
            throw new IllegalArgumentException("El código universitario es obligatorio.");
        
        if (u.getNombres() == null || u.getNombres().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        
        if (u.getPrimerApellido() == null || u.getPrimerApellido().isBlank())
            throw new IllegalArgumentException("El primer apellido es obligatorio.");
        
        if (u.getTipoDocumento() == null)
            throw new IllegalArgumentException("Debe indicar el tipo de documento.");
        
        if (u.getNumeroDocumento() == null || u.getNumeroDocumento().isBlank())
            throw new IllegalArgumentException("Debe ingresar el número de documento.");
        
        if (u.getCorreoElectronico() == null || u.getCorreoElectronico().isBlank())
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        
        if (u.getPassword() == null || u.getPassword().isBlank())
            throw new IllegalArgumentException("Debe asignarse una contraseña.");
        
        if (u.getEstado() == null)
            throw new IllegalArgumentException("Debe indicar el estado del usuario.");
        
        switch (u.getTipoUsuario().getTipoUsuarioId()) {
            case 1, 2 -> validarEstudiante((EstudianteDTO) u); //Pregrado y Posgrado
            case 3 -> validarDocente((DocenteDTO) u);
            case 4 -> validarBibliotecario((BibliotecarioDTO) u);
        }
    }

    private void validarEstudiante(EstudianteDTO e) {
        if (e.getEspecialidad() == null || e.getEspecialidad().isBlank())
            throw new IllegalArgumentException("La especialidad es obligatoria para estudiantes.");
    }

    private void validarDocente(DocenteDTO d) {
        if (d.getDepartamentoAcademico() == null || d.getDepartamentoAcademico().isBlank())
            throw new IllegalArgumentException("El departamento académico es obligatorio para docentes.");
    }

    private void validarBibliotecario(BibliotecarioDTO b) {
        if (b.getBiblioteca() == null || b.getBiblioteca().getBibliotecaId() == null)
            throw new IllegalArgumentException("El bibliotecario debe estar asociado a una biblioteca.");
    }
}

package pe.edu.pucp.softinv.dao;

import java.sql.SQLException;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;

import java.util.ArrayList;

public interface UsuarioDAO {

    public Integer insertar(UsuarioDTO usuario);

    public UsuarioDTO obtenerPorId(Integer usuarioId);

    public ArrayList<UsuarioDTO> listarTodos();

    public Integer modificar(UsuarioDTO usuario);

    public Integer eliminar(UsuarioDTO usuario);
    
//    public UsuarioDTO buscarPorCodigoUniversitario(String codigo) throws SQLException;
//    
    public UsuarioDTO buscarPorCorreo(String correo) throws SQLException;
    
    public ArrayList<UsuarioDTO> listarPorBusquedaAvanzada(
        String idUsuario, String tipoUsuario, String codigoUniversitario, 
        String nombres, String primerApellido, String estado
    );
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
}

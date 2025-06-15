package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.business.UsuarioBO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.usuario.BibliotecarioDTO;
import pe.edu.pucp.softinv.model.usuario.DocenteDTO;
import pe.edu.pucp.softinv.model.usuario.EstudianteDTO;

@WebService(serviceName = "UsuarioWS",
        targetNamespace = "http://services.pucp.edu.pe")
@XmlSeeAlso({EstudianteDTO.class, DocenteDTO.class, BibliotecarioDTO.class})
public class UsuarioWS {
    private final UsuarioBO usuarioBO;
    
    public UsuarioWS(){
        this.usuarioBO = new UsuarioBO();
    }
    
    @WebMethod(operationName = "validarUsuario")
    public UsuarioDTO validarUsuario(@WebParam(name = "correo") String correo, 
                                     @WebParam(name = "contrasena") String contrasena) throws SQLException {
        return this.usuarioBO.validarUsuario(correo, contrasena);
    }
    
    @WebMethod(operationName = "insertarUsuario")
    public Integer insertarUsuario(@WebParam(name = "usuario") UsuarioDTO usuario) throws  SQLException{
        return this.usuarioBO.insertar(usuario);
    }
    
    @WebMethod(operationName = "obtenerUsuarioPorId")
    public UsuarioDTO obtenerUsuarioPorId(@WebParam(name = "id") int id) throws  SQLException{
        return this.usuarioBO.obtenerPorId(id);
    }
    
    @WebMethod(operationName = "buscarUsuariosAvanzado")
    public List<UsuarioDTO> buscarUsuariosAvanzado(
        @WebParam(name = "idUsuario") String idUsuario,
        @WebParam(name = "tipoUsuario") String tipoUsuario,
        @WebParam(name = "codigoUniversitario") String codigoUniversitario,
        @WebParam(name = "nombres") String nombres,
        @WebParam(name = "primerApellido") String primerApellido,
        @WebParam(name = "estado") String estado
    ) {
        try {   
            UsuarioBO usuarioBO= new UsuarioBO();
                return usuarioBO.buscarUsuariosAvanzado(
                idUsuario, tipoUsuario, codigoUniversitario, nombres, primerApellido, estado
            );
        } catch (SQLException e) {
            System.err.println("Error en buscarUsuariosAvanzado: " + e.getMessage());
            return null;
        }
    }
}

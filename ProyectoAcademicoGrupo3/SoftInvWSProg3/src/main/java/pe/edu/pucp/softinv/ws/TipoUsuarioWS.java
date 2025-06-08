package pe.edu.pucp.softinv.ws;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.softinv.business.TipoUsuarioBO;
import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;
import java.sql.SQLException;
import java.util.List;

@WebService(serviceName = "TipoUsuarioWS")
public class TipoUsuarioWS {
    private final TipoUsuarioBO tipoUsuarioBO;
    
    public TipoUsuarioWS() {
        this.tipoUsuarioBO = new TipoUsuarioBO();
    }
    
    @WebMethod(operationName = "insertarTipoUsuario")
    public int insertarTipoUsuario(@WebParam(name = "tipoUsuario") TipoUsuarioDTO tipoUsuario)  throws  SQLException{
        return tipoUsuarioBO.insertar(tipoUsuario);
    }
    
    @WebMethod(operationName = "modificarTipoUsuario")
    public int modificarTipoUsuario(@WebParam(name = "tipoUsuario") TipoUsuarioDTO tipoUsuario) throws  SQLException{
        return tipoUsuarioBO.modificar(tipoUsuario);
    }
    
    @WebMethod(operationName = "eliminarTipoUsuario")
    public int eliminarTipoUsuario(@WebParam(name = "idTipoUsuario") Integer id) throws  SQLException{
        return tipoUsuarioBO.eliminar(id);
    }
    
    @WebMethod(operationName = "obtenerTipoUsuarioPorId")
    public TipoUsuarioDTO obtenerTipoUsuarioPorId(@WebParam(name = "idTipoUsuario") Integer id) throws  SQLException{
        return tipoUsuarioBO.obtenerPorId(id);
    }
    
    @WebMethod(operationName = "listarTodosTiposUsuario")
    public List<TipoUsuarioDTO> listarTodosTiposUsuario() throws  SQLException{
        return tipoUsuarioBO.listarTodos();
    }
}
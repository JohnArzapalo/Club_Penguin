package pe.edu.pucp.softinv.dao;

import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;
import java.util.ArrayList;

public interface TipoUsuarioDAO {

    public Integer insertar(TipoUsuarioDTO tipoUsuario);

    public TipoUsuarioDTO obtenerPorId(Integer tipoUsuarioId);

    public ArrayList<TipoUsuarioDTO> listarTodos();

    public Integer modificar(TipoUsuarioDTO tipoUsuario);

    public Integer eliminar(TipoUsuarioDTO tipoUsuario);
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
    
}

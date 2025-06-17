package pe.edu.pucp.softinv.dao;

import pe.edu.pucp.softinv.model.circulacion.SancionDTO;
import java.util.ArrayList;

public interface SancionDAO {

    public Integer insertar(SancionDTO sancion);

    public SancionDTO obtenerPorId(Integer sancionId);

    public ArrayList<SancionDTO> listarTodos();

    public Integer modificar(SancionDTO sancion);

    public Integer eliminar(SancionDTO sancion);
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
}

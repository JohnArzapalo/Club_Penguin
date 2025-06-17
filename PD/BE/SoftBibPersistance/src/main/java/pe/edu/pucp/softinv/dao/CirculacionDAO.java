package pe.edu.pucp.softinv.dao;

import java.sql.SQLException;
import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface CirculacionDAO {
    
    public Integer insertar(CirculacionDTO circulacion);

    public CirculacionDTO obtenerPorId(Integer circulacionId);

    public ArrayList<CirculacionDTO> listarTodos();

    public Integer modificar(CirculacionDTO circulacion);

    public Integer eliminar(CirculacionDTO circulacion);
    
    public List<CirculacionDTO> listarPrestamosPorUsuario(int usuarioId) throws SQLException ;
    
    public ArrayList<CirculacionDTO> listarPorBusquedaAvanzada(String idCirculacion, String idReserva,
        String idUsuario, String idEjemplar, String estado, Date fechaDesde, Date fechaHasta);
    
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
}

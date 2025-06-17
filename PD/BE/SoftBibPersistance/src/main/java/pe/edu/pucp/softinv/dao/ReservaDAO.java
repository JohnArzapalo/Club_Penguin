package pe.edu.pucp.softinv.dao;

import java.sql.SQLException;
import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ReservaDAO {

    public Integer insertar(ReservaDTO reserva);

    public ReservaDTO obtenerPorId(Integer reservaId);

    public ArrayList<ReservaDTO> listarTodos();

    public Integer modificar(ReservaDTO reserva);

    public Integer eliminar(ReservaDTO reserva);
    
//    List<ReservaDTO> obtenerDetallesPorPrestamoId(Integer prestamoId) throws SQLException;    
    
    public List<ReservaDTO> listarReservasVigentesPorUsuario(int usuarioId) throws SQLException;
    
    public ArrayList<ReservaDTO> listarPorBusquedaAvanzada(String reservaId, String usuarioNombre, String materialTitulo, String estado, Date fechaDesde, Date fechaHasta);
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
}

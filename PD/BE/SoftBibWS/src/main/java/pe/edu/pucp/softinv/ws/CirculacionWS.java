package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.softinv.business.CirculacionBO;
import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softinv.model.circulacion.SancionDTO;

@WebService(serviceName = "CirculacionWS",
        targetNamespace = "http://services.pucp.edu.pe")
public class CirculacionWS {

    private final CirculacionBO circulacionBO;

    public CirculacionWS() {
        this.circulacionBO = new CirculacionBO();
    }

    @WebMethod(operationName = "insertarCirculacion")
    public int insertarCirculacion(@WebParam(name = "circulacion") CirculacionDTO circulacion) throws SQLException{
        return circulacionBO.insertar(circulacion);
    }

    @WebMethod(operationName = "modificarCirculacion")
    public int modificarCirculacion(@WebParam(name = "circulacion") CirculacionDTO circulacion) throws SQLException{
        return circulacionBO.modificar(circulacion);
    }

    @WebMethod(operationName = "eliminarCirculacion")
    public int eliminarCirculacion(@WebParam(name = "idCirculacion") Integer id) throws SQLException{
        return circulacionBO.eliminar(id);
    }

    @WebMethod(operationName = "obtenerCirculacionPorId")
    public CirculacionDTO obtenerCirculacionPorId(@WebParam(name = "idCirculacion") Integer id) throws SQLException{
        return circulacionBO.obtenerPorId(id);
    }

    @WebMethod(operationName = "listarTodasCirculaciones")
    public List<CirculacionDTO> listarTodasCirculaciones() throws SQLException{
        return circulacionBO.listarTodas();
    }
    
    @WebMethod(operationName = "listarPrestamosPorUsuario")
    public List<CirculacionDTO> listarPrestamosPorUsuario(@WebParam(name = "usuarioId") Integer id) throws SQLException{
        return circulacionBO.listarPrestamosPorUsuario(id);
    }
    
    @WebMethod(operationName = "buscarCirculacionesAvanzado")
    public List<CirculacionDTO> buscarCirculacionesAvanzado(
            @WebParam(name = "idCirculacion") String idCirculacion,
            @WebParam(name = "idReserva") String idReserva,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEjemplar") String idEjemplar,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "fechaDesde") String fechaDesdeStr,
            @WebParam(name = "fechaHasta") String fechaHastaStr
    ) throws java.text.ParseException, SQLException {
        Date fechaDesde = null;
        Date fechaHasta = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (fechaDesdeStr != null && !fechaDesdeStr.trim().isEmpty()) {
            fechaDesde = sdf.parse(fechaDesdeStr.trim());
        }
        if (fechaHastaStr != null && !fechaHastaStr.trim().isEmpty()) {
            fechaHasta = sdf.parse(fechaHastaStr.trim());
        }
        
        return circulacionBO.buscarCirculacionesAvanzado(
                    idCirculacion, idReserva, idUsuario, idEjemplar, estado, fechaDesde, fechaHasta
            );
    }
    
    @WebMethod(operationName = "obtenerSancionAsociada")
    public SancionDTO obtenerSancionAsociada(@WebParam(name = "circulacionId")Integer circulacionId) throws SQLException{
        return circulacionBO.obtenerSancion(circulacionId);
    }
}
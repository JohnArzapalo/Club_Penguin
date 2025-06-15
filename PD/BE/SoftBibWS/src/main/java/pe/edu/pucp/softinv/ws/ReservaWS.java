package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.softinv.business.ReservaBO;
import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softinv.business.MaterialBO;
import pe.edu.pucp.softinv.model.material.MaterialDTO;

@WebService(serviceName = "ReservaWS",
        targetNamespace = "http://services.pucp.edu.pe")
public class ReservaWS {

    private final ReservaBO reservaBO;
    private final MaterialBO materialBO;

    public ReservaWS() {
        this.reservaBO = new ReservaBO();
        this.materialBO = new MaterialBO();
    }

    @WebMethod(operationName = "insertarReserva")
    public int insertarReserva(
            @WebParam(name = "reserva") ReservaDTO reserva,
            @WebParam(name = "bibliotecaId") Integer bibliotecaId) throws SQLException{
        return reservaBO.insertar(reserva, bibliotecaId);
    }

    @WebMethod(operationName = "modificarReserva")
    public int modificarReserva(@WebParam(name = "reserva") ReservaDTO reserva) throws SQLException{
        return reservaBO.modificar(reserva);
    }

    @WebMethod(operationName = "eliminarReserva")
    public int eliminarReserva(@WebParam(name = "idReserva") Integer id) throws SQLException{
        return reservaBO.eliminar(id);
    }

    @WebMethod(operationName = "obtenerReservaPorId")
    public ReservaDTO obtenerReservaPorId(@WebParam(name = "idReserva") Integer id) throws SQLException{
        return reservaBO.obtenerPorId(id);
    }

    @WebMethod(operationName = "listarTodasReservas")
    public List<ReservaDTO> listarTodasReservas() throws SQLException{
        return reservaBO.listarTodos();
    }
    
    @WebMethod(operationName = "listarReservasVigentesPorUsuario")
    public List<ReservaDTO> listarReservasVigentesPorUsuario(@WebParam(name = "usuarioId") Integer id) throws SQLException{
        return reservaBO.listarReservasVigentesPorUsuario(id);
    }

    @WebMethod(operationName = "calcularFechaVencimientoReserva")
    public Date calcularFechaVencimientoReserva(
            @WebParam(name = "fechaReserva") Date fechaReserva,
            @WebParam(name = "bibliotecaId") Integer bibliotecaId) throws SQLException{
        return reservaBO.calcularFechaVencimiento(fechaReserva, bibliotecaId);
    }
    
    @WebMethod(operationName = "buscarReservasAvanzado")
    public List<ReservaDTO> buscarReservasAvanzado(
            @WebParam(name = "reservaId") String reservaId,
            @WebParam(name = "usuarioNombre") String usuarioNombre,
            @WebParam(name = "materialTitulo") String materialTitulo,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "fechaDesde") String fechaDesdeStr,
            @WebParam(name = "fechaHasta") String fechaHastaStr) throws Exception {
        Date fechaDesde = null;
        Date fechaHasta = null;
        if (fechaDesdeStr != null && !fechaDesdeStr.trim().isEmpty()) {
            fechaDesde = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesdeStr.trim());
        }
        if (fechaHastaStr != null && !fechaHastaStr.trim().isEmpty()) {
            fechaHasta = new SimpleDateFormat("yyyy-MM-dd").parse(fechaHastaStr.trim());
        }
        return reservaBO.buscarReservasAvanzado(reservaId, usuarioNombre, materialTitulo, estado, fechaDesde, fechaHasta);
    }
    
    @WebMethod(operationName = "obtenerTipoDeMaterialPorId")
    public String obtenerTipoDeMaterialPorId(@WebParam(name = "idMaterial") Integer idMaterial) throws Exception {
        MaterialDTO mdto = materialBO.obtenerPorId(idMaterial);
        if (mdto != null && mdto.getTipoMaterial() != null) {
            return mdto.getTipoMaterial().getNombreMostrar();
        }
        return null;
    }
}

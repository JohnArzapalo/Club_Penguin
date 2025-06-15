package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softinv.business.SancionBO;
import pe.edu.pucp.softinv.business.CirculacionBO;
import pe.edu.pucp.softinv.model.circulacion.SancionDTO;

@WebService(serviceName = "SancionWS",
        targetNamespace = "http://services.pucp.edu.pe")
public class SancionWS {
    private final SancionBO sancionBO;
    
    public SancionWS(){
        this.sancionBO = new SancionBO();
    }
    
    @WebMethod(operationName = "listarSanciones")
    public List<SancionDTO> listarSanciones(){
        try{
            return sancionBO.listarTodas();
        }catch(SQLException e){
            throw new WebServiceException("Error al listar sanciones:"+e.getMessage());
        }
    }

    // Método auxiliar para conversión segura
    private Integer stringToInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException e) {
            return null; // o puedes lanzar una excepción personalizada
        }
    }
    
    @WebMethod(operationName = "obtenerPorId")
    public SancionDTO obtenerPorId(@WebParam(name = "id")int id){
        try {
            SancionDTO sancion= sancionBO.obtenerPorId(id);
            
            CirculacionBO circ= new CirculacionBO();
            
            int idcirc= sancion.getCirculacion().getCirculacionId();
            sancion.setCirculacion(circ.obtenerPorId(idcirc));
            
            return sancion;
        } catch (SQLException ex) {
            Logger.getLogger(SancionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @WebMethod(operationName = "buscarSancionesFlexible")
    public List<SancionDTO> buscarSancionesFlexible(
            @WebParam(name = "sancionId") String sancionIdStr,
            @WebParam(name = "circulacionId") String circulacionIdStr,
            @WebParam(name = "fechaDesde") String fechaDesdeStr,
            @WebParam(name = "fechaHasta") String fechaHastaStr) {
        try {
            // Conversión segura de parámetros
            Integer sancionId = stringToInteger(sancionIdStr);
            Integer circulacionId = stringToInteger(circulacionIdStr);

            // Validar que al menos un parámetro tenga valor
            if (sancionId == null && circulacionId == null && 
                (fechaDesdeStr == null || fechaDesdeStr.isEmpty()) && 
                (fechaHastaStr == null || fechaHastaStr.isEmpty())) {
                return sancionBO.listarTodas(); // Retorna lista completa si no hay filtros
            }
            if(sancionId==null)sancionId=-1;
            if(circulacionId==null)circulacionId=-1;
//            if(fechaDesdeStr==null)fechaDesdeStr="";
//            if(fechaHastaStr==null)fechaHastaStr="";
            return sancionBO.buscarSancionFlexible(sancionId, circulacionId, fechaDesdeStr, fechaHastaStr);
        } catch (SQLException e) {
            throw new WebServiceException("Error al buscar sanciones: " + e.getMessage());
        }
    }
    
    //NUEVA FUNCION!
    @WebMethod(operationName = "obtenerPorCirculacion")
    public SancionDTO obtenerPorCirculacion(@WebParam(name = "id")int id){
        try {
            return sancionBO.obtenerSancionPorCirculacion(id);
        } catch (SQLException ex) {
            Logger.getLogger(SancionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @WebMethod(operationName = "vistaPreviaNuevaSancion")
    public SancionDTO vistaPreviaNuevaSancion(@WebParam(name = "idCirculacion")int idCirculacion){
        try {
            return sancionBO.vistaPreviaSancion(idCirculacion);
        } catch (SQLException ex) {
            Logger.getLogger(SancionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @WebMethod(operationName = "insertarSancion")
    public int insertarSancion(@WebParam(name = "sancion") SancionDTO sancion){
        try {
            return sancionBO.insertar(sancion);
        } catch (SQLException ex) {
            Logger.getLogger(SancionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}

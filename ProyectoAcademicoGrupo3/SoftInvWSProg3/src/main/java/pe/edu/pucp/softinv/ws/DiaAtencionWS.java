package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.softinv.business.DiaAtencionBO;
import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;

import java.sql.SQLException;
import java.util.List;

@WebService(serviceName = "DiaAtencionWS")
public class DiaAtencionWS {

    private final DiaAtencionBO diaAtencionBO;

    public DiaAtencionWS() {
        this.diaAtencionBO = new DiaAtencionBO();
    }
    
    @WebMethod(operationName = "obtenerDiaAtencionPorId")
    public DiaAtencionDTO obtenerDiaAtencionPorId(@WebParam(name = "idDiaAtencion") Integer id)  throws SQLException{
        return diaAtencionBO.obtenerPorId(id);
    }

    @WebMethod(operationName = "listarTodosDiasAtencion")
    public List<DiaAtencionDTO> listarTodosDiasAtencion()  throws SQLException{
        return diaAtencionBO.listarTodas();
    }

    @WebMethod(operationName = "listarDiasAtencionPorBiblioteca")
    public List<DiaAtencionDTO> listarDiasAtencionPorBiblioteca(@WebParam(name = "idBiblioteca") Integer bibliotecaId) throws SQLException{
        return diaAtencionBO.listarPorAtencionBiblioteca(bibliotecaId);
    }
}
package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.business.BibliotecaBO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;


@WebService(serviceName = "BibliotecaWS")
public class BibliotecaWS {
    private final BibliotecaBO bibliotecaBO;
    
    public BibliotecaWS(){
        this.bibliotecaBO = new BibliotecaBO();
    }
    
    @WebMethod(operationName = "obtenerBibliotecaPorId")
    public BibliotecaDTO obtenerBibliotecaPorId(@WebParam(name = "bibliotecaId")int bibliotecaId) throws SQLException{
        return this.bibliotecaBO.obtenerPorId(bibliotecaId);
    }
    
    @WebMethod(operationName = "listarBibliotecas")
    public List<BibliotecaDTO> listarBibliotecas() throws  SQLException{
        return this.bibliotecaBO.listarTodas();
    }
    
    @WebMethod(operationName = "obtenerBibliotecasPorMaterial")
    public List<BibliotecaDTO> obtenerBibliotecasPorMaterial(@WebParam(name = "id")int materialId) throws  SQLException{
        return this.bibliotecaBO.obtenerBibliotecasPorMaterial(materialId);
    }
}

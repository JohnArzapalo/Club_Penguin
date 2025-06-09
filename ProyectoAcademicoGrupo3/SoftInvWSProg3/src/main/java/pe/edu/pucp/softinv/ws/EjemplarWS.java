package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import pe.edu.pucp.softinv.business.EjemplarBO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;

import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.business.BibliotecaBO;
import pe.edu.pucp.softinv.business.MaterialBO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.material.EstadoEjemplar;
import pe.edu.pucp.softinv.model.material.MaterialDTO;

@WebService(serviceName = "EjemplarWS")
public class EjemplarWS {
    private final EjemplarBO ejemplarBO;
    private final MaterialBO materialBO;
    private final BibliotecaBO bibliotecaBO;
    
    public EjemplarWS(){
        this.ejemplarBO = new EjemplarBO();
        this.materialBO = new MaterialBO();
        this.bibliotecaBO = new BibliotecaBO();
    }
    
    @WebMethod(operationName = "insertarEjemplar")
    public Integer insertarEjemplar(@WebParam(name = "bibliotecaId")String bibliotecaId,
            @WebParam(name = "titulo")String titulo,
            @WebParam(name = "autor")String autor,
            @WebParam(name = "tipo")String tipo,
            @WebParam(name = "anioStr")String anioStr,
            @WebParam(name = "idioma")String idioma,
            @WebParam(name = "tema")String tema,
            @WebParam(name = "locacion")String locacion,
            @WebParam(name = "estado")String estado) throws SQLException{
        
        EjemplarDTO ejemplar=new EjemplarDTO();
        
        MaterialDTO material = new MaterialDTO();
        material.setTitulo(titulo);
        material.setAutor(autor);
        material.setIdioma(idioma);
        material.setTema(tema);
        int idMaterial=materialBO.busqueda(material, anioStr, tipo);
        
        material=materialBO.obtenerPorId(idMaterial);
        BibliotecaDTO biblioteca = bibliotecaBO.obtenerPorId(Integer.valueOf(bibliotecaId));
        ejemplar.setMaterial(material);
        ejemplar.setBiblioteca(biblioteca);
        ejemplar.setLocacionEnBiblioteca(locacion);
        ejemplar.setEstado(EstadoEjemplar.valueOf(estado));
        ejemplar.setEliminable(true);
        return this.ejemplarBO.insertar(ejemplar);
    }
    
    @WebMethod(operationName = "obtenerEjemplarPorId")
    public EjemplarDTO obtenerEjemplarPorId(@WebParam(name = "idEjemplar") Integer id)  throws SQLException{
        return ejemplarBO.obtenerPorId(id);
    }

    @WebMethod(operationName = "listarTodosEjemplares")
    public List<EjemplarDTO> listarTodosEjemplares()  throws SQLException{
        return ejemplarBO.listarTodos();
    }
    
    @WebMethod(operationName = "obtenerEjemplaresMaterialPorBiblioteca")
    public List<EjemplarDTO> obtenerEjemplaresMaterialPorBiblioteca(@WebParam(name = "materialId")int materialId,@WebParam(name = "bibliotecaId")int bibliotecaId) throws SQLException {
        return this.ejemplarBO.obtenerEjemplaresMaterialPorBiblioteca(materialId, bibliotecaId);
    }
}

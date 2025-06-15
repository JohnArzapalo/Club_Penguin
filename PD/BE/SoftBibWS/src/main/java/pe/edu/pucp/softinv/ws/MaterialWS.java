package pe.edu.pucp.softinv.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.business.MaterialBO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.model.material.LibroDTO;
import pe.edu.pucp.softinv.model.material.ArticuloDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.model.material.Grado;
import pe.edu.pucp.softinv.model.material.TesisDTO;
import pe.edu.pucp.softinv.model.material.TipoMaterial;

@WebService(serviceName = "MaterialWS",
        targetNamespace = "http://services.pucp.edu.pe")
@XmlSeeAlso({LibroDTO.class, ArticuloDTO.class, TesisDTO.class})
public class MaterialWS {
    private final MaterialBO materialBO;
    
    public MaterialWS(){
        this.materialBO = new MaterialBO();
    }
    
    @WebMethod(operationName = "insertarMaterial")
    public Integer insertarMaterial(@WebParam(name = "titulo")String titulo,
            @WebParam(name = "autor")String autor,
            @WebParam(name = "tema")String tema,
            @WebParam(name = "idioma")String idioma,
            @WebParam(name = "tipo")String tipo,
            @WebParam(name = "anioStr")String anioStr,
            @WebParam(name = "numeroPaginasStr")String numeroPaginasStr,
            @WebParam(name = "isbnLibro")String isbnLibro,
            @WebParam(name = "editorialLibro")String editorialLibro,
            @WebParam(name = "edicionLibro")String edicionLibro,
            @WebParam(name = "issnArticulo")String issnArticulo,
            @WebParam(name = "revistaArticulo")String revistaArticulo,
            @WebParam(name = "editorialArticulo")String editorialArticulo,
            @WebParam(name = "volumenArticulo")String volumenArticulo,
            @WebParam(name = "numeroArticulo")String numeroArticulo,
            @WebParam(name = "institucionTesis")String institucionTesis,
            @WebParam(name = "asesorTesis")String asesorTesis,
            @WebParam(name = "especialidadTesis")String especialidadTesis,
            @WebParam(name = "gradoTesis")String gradoTesis) throws SQLException{
        MaterialDTO material=new MaterialDTO();
        switch(tipo){
            case "LIBRO":
                LibroDTO libro = new LibroDTO();
                libro.setIsbn(isbnLibro);
                libro.setEdicion(edicionLibro);
                libro.setEditorial(editorialLibro);
                material=libro;
                break;
            case "ARTICULO":
                ArticuloDTO articulo = new ArticuloDTO();
                articulo.setIssn(issnArticulo);
                articulo.setNombreRevista(revistaArticulo);
                articulo.setVolumen(volumenArticulo);
                articulo.setNumero(Integer.valueOf(numeroArticulo));
                articulo.setEditorial(editorialArticulo); // campo compartido
                material=articulo;
                break;
            case "TESIS":
                TesisDTO tesis = new TesisDTO();
                tesis.setNombreInstitucionPublicacion(institucionTesis);
                tesis.setAsesorTesis(asesorTesis);
                tesis.setEspecialidad(especialidadTesis);
                tesis.setGrado(Grado.valueOf(gradoTesis));
                material=tesis;
                break;
        }
        material.setTitulo(titulo);
        material.setAutor(autor);
        material.setTema(tema);
        material.setIdioma(idioma);
        material.setTipoMaterial(TipoMaterial.valueOf(tipo));
        material.setNumeroPaginas(Integer.valueOf(numeroPaginasStr));
        material.setAnioPublicacion(Integer.valueOf(anioStr));
        return this.materialBO.insertar(material);
    }
    
    @WebMethod(operationName = "modificarMaterial")
    public int modificarMaterial(@WebParam(name = "material") MaterialDTO material)  throws SQLException{
        return materialBO.modificar(material);
    }

    @WebMethod(operationName = "eliminarMaterial")
    public int eliminarMaterial(@WebParam(name = "idMaterial") int id)  throws SQLException{
        return materialBO.eliminar(id);
    }
    
    @WebMethod(operationName = "obtenerMaterialPorId")
    public MaterialDTO obtenerMaterialPorId(@WebParam(name = "materialId")int materialId) throws SQLException{
        return this.materialBO.obtenerPorId(materialId);
    }
    
    @WebMethod(operationName = "listarTodosMateriales")
    public List<MaterialDTO> listarTodosMateriales()  throws SQLException{
        return materialBO.listarTodos();
    }
    
    @WebMethod(operationName = "buscarPorTitulo")
    public List<MaterialDTO> buscarPorTitulo(@WebParam(name = "titulo") String titulo) throws SQLException{
        return this.materialBO.buscarPorTitulo(titulo);
    }
    
    @WebMethod(operationName = "obtenerCopias")
    public int[] obtenerCopias(@WebParam(name = "id")int materialId){
        return this.materialBO.obtenerCopias(materialId);
    }
    
    @WebMethod(operationName = "obtenerEjemplaresReservadosYDisponibles")
    public int[] obtenerEjemplaresReservadosYDisponibles(@WebParam(name = "materialId")int materialId,@WebParam(name = "bibliotecaId")int bibliotecaId)  throws  SQLException{
        return this.materialBO.obtenerEjemplaresReservadosYDisponibles(materialId,bibliotecaId);
    }
    
    @WebMethod(operationName = "materialBusquedaAvanzada")
    public MaterialDTO busqueda(        
        @WebParam(name = "busquedaAvanzada") MaterialDTO material,
        @WebParam(name = "biblioteca") BibliotecaDTO biblioteca,
        @WebParam(name = "ejemplar") EjemplarDTO ejemplar,
        @WebParam(name = "stringDato") String anioPublicacion,
        @WebParam(name = "stringTipoMaterial") String tipoMaterialTexto,
        @WebParam(name = "stringDisponibilidad") String disponibilidadTexto) throws  SQLException{
        return this.materialBO.busquedaAvanzada(material, biblioteca, ejemplar, anioPublicacion, tipoMaterialTexto, disponibilidadTexto);
    }
    
    @WebMethod(operationName = "buscarPorAutor")
    public List<MaterialDTO> buscarPorAutor(@WebParam(name = "autor") String autor) throws  SQLException{
        return this.materialBO.buscarPorAutor(autor);
    }
    @WebMethod(operationName = "buscarPorAnio")
    public List<MaterialDTO> buscarPorAnio(@WebParam(name = "autor") String anio) throws  SQLException{
        return this.materialBO.buscarPorAnio(anio);
    }
    
    @WebMethod(operationName = "buscarPorTipoMaterial")
    public List<MaterialDTO> buscarPorTipoMaterial(@WebParam(name = "autor") String tipoMaterial) throws  SQLException{
        return this.materialBO.buscarPorTipoMaterial(tipoMaterial);
    }
    
    @WebMethod(operationName = "buscarPorTema")
    public List<MaterialDTO> buscarPorTema(@WebParam(name = "autor") String tema) throws  SQLException{
        return this.materialBO.buscarPorTema(tema);
    }
    
    @WebMethod(operationName = "buscarPorIdioma")
    public List<MaterialDTO> buscarPorIdioma(@WebParam(name = "autor") String idioma) throws  SQLException{
        return this.materialBO.buscarPorIdioma(idioma);
    }
    
    @WebMethod(operationName = "buscarPorDisponibilidad")
    public List<MaterialDTO> buscarPorDisponibilidad(@WebParam(name = "autor") String disponibildad) throws  SQLException{
        return this.materialBO.buscarPorDisponibilidad(disponibildad);
    }
    
    @WebMethod(operationName = "buscarPorBiblioteca")
    public List<MaterialDTO> buscarPorBiblioteca(@WebParam(name = "autor") String biblioteca) throws  SQLException{
        return this.materialBO.buscarPorBiblioteca(biblioteca);
    }
}

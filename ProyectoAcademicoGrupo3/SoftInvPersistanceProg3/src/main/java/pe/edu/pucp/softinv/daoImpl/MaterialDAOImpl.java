package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.material.LibroDTO;
import pe.edu.pucp.softinv.dao.MaterialDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import pe.edu.pucp.softinv.model.material.ArticuloDTO;
import pe.edu.pucp.softinv.model.material.Grado;
import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.model.material.TesisDTO;
import pe.edu.pucp.softinv.model.material.TipoMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.model.material.EstadoEjemplar;

public class MaterialDAOImpl extends DAOImplBase implements MaterialDAO {
    
    private MaterialDTO material;
    private final BibliotecaDAOImpl biblioteca;
    private final EjemplarDAOImpl ejemplar;
    
    public MaterialDAOImpl() {
        super("G3_MATERIALES");
        this.retornarLlavePrimaria = true;
        this.material = null;
        this.biblioteca=new BibliotecaDAOImpl();
        this.ejemplar=new EjemplarDAOImpl();
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MATERIAL_ID", true, true));
        this.listaColumnas.add(new Columna("TITULO", false, false));
        this.listaColumnas.add(new Columna("AUTOR", false, false));
        this.listaColumnas.add(new Columna("ANIO_PUBLI", false, false));
        this.listaColumnas.add(new Columna("TIPO_MATERIAL", false, false));
        this.listaColumnas.add(new Columna("NUMERO_PAGINAS", false, false));
        this.listaColumnas.add(new Columna("TEMA", false, false));
        this.listaColumnas.add(new Columna("IDIOMA", false, false));
        
        // Columnas específicas de Libro
        this.listaColumnas.add(new Columna("ISBN", false, false));
        this.listaColumnas.add(new Columna("EDICION", false, false));
        this.listaColumnas.add(new Columna("EDITORIAL", false, false)); // También para Artículo
        
        // Columnas específicas de Artículo
        this.listaColumnas.add(new Columna("ISSN", false, false));
        this.listaColumnas.add(new Columna("NOMBRE_REVISTA", false, false));
        this.listaColumnas.add(new Columna("VOLUMEN", false, false));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
        
        // Columnas específicas de tesis
        this.listaColumnas.add(new Columna("NOMBRE_INSTITUCION_PUBLICACION", false, false));
        this.listaColumnas.add(new Columna("ASESOR_TESIS", false, false));
        this.listaColumnas.add(new Columna("ESPECIALIDAD", false, false));
        this.listaColumnas.add(new Columna("GRADO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Campos comunes
        this.statement.setString(1, this.material.getTitulo());
        this.statement.setString(2, this.material.getAutor());
        this.statement.setInt(3, this.material.getAnioPublicacion());
        this.statement.setString(4, this.material.getTipoMaterial().name());
        this.statement.setInt(5, this.material.getNumeroPaginas());
        this.statement.setString(6, this.material.getTema());
        this.statement.setString(7, this.material.getIdioma());

        // Campos específicos
        switch (this.material.getTipoMaterial()) {
            case LIBRO -> {
                LibroDTO libro = (LibroDTO) this.material;
                this.statement.setString(8, libro.getIsbn());
                this.statement.setString(9, libro.getEdicion());
                this.statement.setString(10, libro.getEditorial());

                // Vaciar campos que no aplican
                this.statement.setNull(11, java.sql.Types.VARCHAR);
                this.statement.setNull(12, java.sql.Types.VARCHAR);
                this.statement.setNull(13, java.sql.Types.VARCHAR);
                this.statement.setNull(14, java.sql.Types.INTEGER);
                this.statement.setNull(15, java.sql.Types.VARCHAR);
                this.statement.setNull(16, java.sql.Types.VARCHAR);
                this.statement.setNull(17, java.sql.Types.VARCHAR);
                this.statement.setNull(18, java.sql.Types.VARCHAR);
            }
            case ARTICULO -> {
                ArticuloDTO articulo = (ArticuloDTO) this.material;
                // Vaciar campos que no aplican
                this.statement.setNull(8, java.sql.Types.VARCHAR);
                this.statement.setNull(9, java.sql.Types.VARCHAR);
                
                this.statement.setString(10, articulo.getEditorial());
                this.statement.setString(11, articulo.getIssn());
                this.statement.setString(12, articulo.getNombreRevista());
                this.statement.setString(13, articulo.getVolumen());
                this.statement.setInt(14, articulo.getNumero());
                
                // Vaciar campos que no aplican
                this.statement.setNull(15, java.sql.Types.VARCHAR);
                this.statement.setNull(16, java.sql.Types.VARCHAR);
                this.statement.setNull(17, java.sql.Types.VARCHAR);
                this.statement.setNull(18, java.sql.Types.VARCHAR);
            }
            case TESIS -> {
                TesisDTO tesis = (TesisDTO) this.material;
                // Vaciar campos que no aplican
                this.statement.setNull(8, java.sql.Types.VARCHAR);
                this.statement.setNull(9, java.sql.Types.VARCHAR);
                this.statement.setNull(10, java.sql.Types.VARCHAR);

                this.statement.setNull(11, java.sql.Types.VARCHAR);
                this.statement.setNull(12, java.sql.Types.VARCHAR);
                this.statement.setNull(13, java.sql.Types.VARCHAR);
                this.statement.setNull(14, java.sql.Types.INTEGER);

                this.statement.setString(15, tesis.getNombreInstitucionPublicacion());
                this.statement.setString(16, tesis.getAsesorTesis());
                this.statement.setString(17, tesis.getEspecialidad());
                this.statement.setString(18, tesis.getGrado().name());
            }
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(19, this.material.getMaterialId());
    }
    
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.material.getMaterialId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.material.getMaterialId());
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.material = new MaterialDTO();
        
        TipoMaterial tipoMaterial = TipoMaterial.valueOf(this.resultSet.getString("TIPO_MATERIAL"));

        switch (tipoMaterial) {
            case LIBRO -> {
                LibroDTO libro = new LibroDTO();
                libro.setIsbn(this.resultSet.getString("ISBN"));
                libro.setEdicion(this.resultSet.getString("EDICION"));
                libro.setEditorial(this.resultSet.getString("EDITORIAL"));
                this.material = libro;
            }
            case ARTICULO -> {
                ArticuloDTO articulo = new ArticuloDTO();
                articulo.setIssn(this.resultSet.getString("ISSN"));
                articulo.setNombreRevista(this.resultSet.getString("NOMBRE_REVISTA"));
                articulo.setVolumen(this.resultSet.getString("VOLUMEN"));
                articulo.setNumero(this.resultSet.getInt("NUMERO"));
                articulo.setEditorial(this.resultSet.getString("EDITORIAL")); // campo compartido
                this.material = articulo;
            }
            case TESIS -> {
                TesisDTO tesis = new TesisDTO();
                tesis.setNombreInstitucionPublicacion(this.resultSet.getString("NOMBRE_INSTITUCION_PUBLICACION"));
                tesis.setAsesorTesis(this.resultSet.getString("ASESOR_TESIS"));
                tesis.setEspecialidad(this.resultSet.getString("ESPECIALIDAD"));
                tesis.setGrado(Grado.valueOf(this.resultSet.getString("GRADO")));
                this.material = tesis;
            }
        }

        // Campos comunes
        this.material.setMaterialId(this.resultSet.getInt("MATERIAL_ID"));
        this.material.setTitulo(this.resultSet.getString("TITULO"));
        this.material.setAutor(this.resultSet.getString("AUTOR"));
        this.material.setAnioPublicacion(this.resultSet.getInt("ANIO_PUBLI"));
        this.material.setTipoMaterial(tipoMaterial);
        this.material.setNumeroPaginas(this.resultSet.getInt("NUMERO_PAGINAS"));
        this.material.setTema(this.resultSet.getString("TEMA"));
        this.material.setIdioma(this.resultSet.getString("IDIOMA"));
    }
    
    @Override
    protected void limpiarObjetoDelResultSet() {
        this.material = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.material);
    }

    @Override
    public Integer insertar(MaterialDTO material) {
        this.material = material;
        return super.insertar();
    }

    @Override
    public MaterialDTO obtenerPorId(Integer materialId) {
        // Se inicializa material solo para pasar el ID; luego se sobrescribe
        // dinámicamente con el tipo correcto en instanciarObjetoDelResultSet()
        MaterialDTO consulta = new MaterialDTO();
        consulta.setMaterialId(materialId);
        this.material = consulta;
        super.obtenerPorId();
        return this.material;
    }

    @Override
    public ArrayList<MaterialDTO> listarTodos() {
        return (ArrayList<MaterialDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(MaterialDTO material) {
        this.material = material;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MaterialDTO material) {
        this.material = material;
        return super.eliminar();
    }
    
    @Override
    public List<MaterialDTO> buscarPorTitulo(String titulo) throws SQLException {
        List<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE TITULO LIKE ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + titulo + "%");
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                resultados.add(this.material);
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por título: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return resultados;
    }
    
    @Override
    public int[] obtenerCopias(int materialId) {
        String sql = "{ call SP_OBTENER_COPIAS_MATERIAL(?, ?, ?) }";

        List<Object> paramsIn = List.of(materialId);
        Map<Integer, Integer> paramsOut = Map.of(
            2, java.sql.Types.INTEGER, // total
            3, java.sql.Types.INTEGER  // disponibles
        );

        try {
            return this.ejecutarProcedimiento(sql, paramsIn, paramsOut, resultado -> {
                int[] copias = new int[2];
                copias[0] = (int) resultado.get(2); // total
                copias[1] = (int) resultado.get(3); // disponibles
                return copias;
            });
        } catch (SQLException e) {
            return new int[] {0, 0};
        }
    }
    
    @Override
    public int[] obtenerEjemplaresReservadosYDisponibles(int materialId, int bibliotecaId) throws SQLException {
        String sql = "{ call SP_EJEMPLARES_RESERVADOS_Y_DISPONIBLES_POR_BIBLIOTECA(?, ?, ?, ?) }";

        List<Object> parametrosEntrada = List.of(materialId, bibliotecaId);
        Map<Integer, Integer> parametrosSalida = Map.of(
            3, java.sql.Types.INTEGER, // Reservados
            4, java.sql.Types.INTEGER  // Disponibles
        );

        return this.ejecutarProcedimiento(sql, parametrosEntrada, parametrosSalida, resultado -> {
            int[] ejemplares = new int[2];
            ejemplares[0] = (int) resultado.get(3); // Reservados
            ejemplares[1] = (int) resultado.get(4); // Disponibles
            return ejemplares;
        });
    }
    
    @Override
    public int busqueda(MaterialDTO material, String anioPublicacion, String tipoMaterialTexto) {
        int anioPublicacionInt = Integer.parseInt(anioPublicacion);
        TipoMaterial tipoDato =TipoMaterial.valueOf(tipoMaterialTexto);
        material.setAnioPublicacion(anioPublicacionInt);
        material.setTipoMaterial(tipoDato);
        
        List<MaterialDTO> datos=listarTodos();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobar = datos.get(i);
            if(
            material.getTitulo().equals(comprobar.getTitulo()) &&
            material.getAutor().equals(comprobar.getAutor()) &&
            material.getTipoMaterial().getNombreMostrar().equals(comprobar.getTipoMaterial().getNombreMostrar()) &&
            material.getAnioPublicacion().intValue() == comprobar.getAnioPublicacion().intValue() &&
            material.getIdioma().equals(comprobar.getIdioma()) &&
            material.getTema().equals(comprobar.getTema())
                                    ){
                return comprobar.getMaterialId();
            }    
        }
        return 0;
    }
    
    @Override
    public MaterialDTO busquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
            String anioPublicacion, String tipoMaterialTexto,String disponibilidadTexto) {

        int idDato=busqueda(material, anioPublicacion, tipoMaterialTexto);
        if(idDato==0) return null;
        int idBiblioteca = 0;
        MaterialDTO dato=obtenerPorId(idDato);
        if (biblioteca.getNombre()== null || biblioteca.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre de biblioteca es obligatorio.");

        List<BibliotecaDTO> bibliotecasDatos=this.biblioteca.listarTodos();
        for (int i = 0; i < bibliotecasDatos.size(); i++) {
            BibliotecaDTO comprobarBiblioteca = bibliotecasDatos.get(i);
            if(comprobarBiblioteca.getNombre().equals(biblioteca.getNombre())){
                idBiblioteca=comprobarBiblioteca.getBibliotecaId();
                break;
            }
        }
        
        EstadoEjemplar tipoDato = EstadoEjemplar.valueOf(disponibilidadTexto);
        ejemplar.setEstado(tipoDato);
        
        if (ejemplar.getEstado()== null )
            throw new IllegalArgumentException("El estado del ejemplar es obligatorio.");
        
        List<EjemplarDTO> ejemplarDatos=this.ejemplar.listarTodos();
        for (int i = 0; i < ejemplarDatos.size(); i++) {
            EjemplarDTO comprobarEjemplar = ejemplarDatos.get(i);
            if(comprobarEjemplar.getBiblioteca().getBibliotecaId()==idBiblioteca && comprobarEjemplar.getMaterial().getMaterialId()==idDato){
                if(ejemplar.getEstado().getNombreMostrar().equals(comprobarEjemplar.getEstado().getNombreMostrar())){
                    return dato;
                }
            }
        }
        return null;
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorAutor(String autor) {
        ArrayList<MaterialDTO>datos=listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobarMaterial = datos.get(i);
            if(comprobarMaterial.getAutor().equals(autor) ){
                resultado.add(comprobarMaterial);
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorAnio(String anio) {
        int anioPublicacionInt = Integer.parseInt(anio);
        ArrayList<MaterialDTO>datos=listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobarMaterial = datos.get(i);
            if(comprobarMaterial.getAnioPublicacion()==anioPublicacionInt){
                resultado.add(comprobarMaterial);
            }
        }
        return resultado;
    }

    
    @Override
    public ArrayList<MaterialDTO> buscarPorTipoMaterial(String tipoMaterial) {
        TipoMaterial tipoDato =TipoMaterial.valueOf(tipoMaterial);
        ArrayList<MaterialDTO>datos=listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobarMaterial = datos.get(i);
            if(comprobarMaterial.getTipoMaterial()==tipoDato){
                resultado.add(comprobarMaterial);
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorTema(String tema) {
        ArrayList<MaterialDTO>datos=listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobarMaterial = datos.get(i);
            if(comprobarMaterial.getTema().equals(tema)){
                resultado.add(comprobarMaterial);
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorIdioma(String idioma) {
        ArrayList<MaterialDTO>datos=listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            MaterialDTO comprobarMaterial = datos.get(i);
            if(comprobarMaterial.getIdioma().equals(idioma)){
                resultado.add(comprobarMaterial);
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorDisponibilidad(String disponibildad) {
        EstadoEjemplar tipoDato =EstadoEjemplar.valueOf(disponibildad);
        
        ArrayList<EjemplarDTO>datos=this.ejemplar.listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            EjemplarDTO comprobarEjemplar = datos.get(i);
            MaterialDTO datoIngresar;
            if(comprobarEjemplar.getEstado()==tipoDato){
                datoIngresar=obtenerPorId(comprobarEjemplar.getMaterial().getMaterialId());
                if(resultado.isEmpty()){
                    resultado.add(datoIngresar);
                    continue;
                }
                for (int j = 0; j < resultado.size(); j++) {
                    
                    MaterialDTO comprobarMaterial = resultado.get(j);
                    if(comprobarMaterial.getMaterialId().equals(datoIngresar.getMaterialId())){
                        break;
                    }
                    resultado.add(datoIngresar);
                }
            }
        }
        return resultado;
    }    
    
    @Override
    public ArrayList<MaterialDTO> buscarPorBiblioteca(String biblioteca) {
        Integer idBiblioteca=0;
        List<BibliotecaDTO>datosBiblioteca=this.biblioteca.listarTodos();
        for (int i = 0; i < datosBiblioteca.size(); i++) {
            BibliotecaDTO comprobarBiblioteca = datosBiblioteca.get(i);
            if(comprobarBiblioteca.getNombre().equals(biblioteca)){
                idBiblioteca=comprobarBiblioteca.getBibliotecaId();
                break;
            }
        }
        List<EjemplarDTO>datos=this.ejemplar.listarTodos();
        ArrayList<MaterialDTO> resultado = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            EjemplarDTO comprobarEjemplar = datos.get(i);
            MaterialDTO datoIngresar;
            if(comprobarEjemplar.getBiblioteca().getBibliotecaId().equals(idBiblioteca)){
                datoIngresar=obtenerPorId(comprobarEjemplar.getMaterial().getMaterialId());
                if(resultado.isEmpty()){
                    resultado.add(datoIngresar);
                    continue;
                }
                for (int j = 0; j < resultado.size(); j++) {
                    MaterialDTO comprobarMaterial = resultado.get(j);
                    if(comprobarMaterial.getMaterialId().equals(datoIngresar.getMaterialId())){
                        break;
                    }
                    resultado.add(datoIngresar);
                }
            }
        }
        return resultado;
    }
}
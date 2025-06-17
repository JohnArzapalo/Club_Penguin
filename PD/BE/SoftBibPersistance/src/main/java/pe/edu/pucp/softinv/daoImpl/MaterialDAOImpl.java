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
    public int busqueda(MaterialDTO material, String anioPublicacion, String tipoMaterialTexto) throws SQLException{
        int anioPublicacionInt = Integer.parseInt(anioPublicacion);
        TipoMaterial tipoDato =TipoMaterial.valueOf(tipoMaterialTexto);
        material.setAnioPublicacion(anioPublicacionInt);
        material.setTipoMaterial(tipoDato);
        int materialIdEncontrado = 0;
        String sql = """
                SELECT MATERIAL_ID
                FROM G3_MATERIALES
                WHERE TITULO = ?
                  AND AUTOR = ?
                  AND IDIOMA = ?
                  AND TEMA = ?
                  AND ANIO_PUBLI = ?
                  AND TIPO_MATERIAL = ?
                LIMIT 1
            """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, material.getTitulo());
            this.statement.setString(2, material.getAutor());
            this.statement.setString(3, material.getIdioma());
            this.statement.setString(4, material.getTema());
            this.statement.setInt(5, Integer.parseInt(anioPublicacion));
            this.statement.setString(6, tipoMaterialTexto); // Debe coincidir con el ENUM de la BD

            this.resultSet = this.statement.executeQuery();

            if (this.resultSet.next()) {
                materialIdEncontrado = this.resultSet.getInt("MATERIAL_ID");
            }
        } catch (SQLException | NumberFormatException ex) {
            System.err.println("Error al buscar material exacto: " + ex.getMessage());
        } finally {
            this.cerrarConexion();
        }

    return materialIdEncontrado;
}
    @Override
 public ArrayList<MaterialDTO> busquedaAvanzada(MaterialDTO material, BibliotecaDTO biblioteca, EjemplarDTO ejemplar,
            String anioPublicacionDesde, String anioPublicacionHasta,String tipoMaterialTexto,String disponibilidadTexto)  throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
    
         String sql = "SELECT * " +
                 "FROM G3_MATERIALES m " +
                 "LEFT JOIN G3_EJEMPLARES e ON m.MATERIAL_ID = e.MATERIAL_ID " +
                 "LEFT JOIN G3_BIBLIOTECAS b ON e.BIBLIOTECA_ID = b.BIBLIOTECA_ID " +
                 "WHERE 1=1 ";

        List<Object> parametros = new ArrayList<>();

        if (material.getTitulo() != null && !material.getTitulo().isEmpty()) {
            sql += "AND m.TITULO LIKE ? ";
            parametros.add("%" + material.getTitulo() + "%");
        }
        if (material.getAutor() != null && !material.getAutor().isEmpty()) {
            sql += "AND m.AUTOR LIKE ? ";
            parametros.add("%" + material.getAutor() + "%");
        }
        if (material.getTema() != null && !material.getTema().isEmpty()) {
            sql += "AND m.TEMA LIKE ? ";
            parametros.add("%" + material.getTema() + "%");
        }
        if (anioPublicacionDesde != null && !anioPublicacionDesde.isEmpty()) {
            sql += "AND m.ANIO_PUBLI >= ? ";
            parametros.add(anioPublicacionDesde); // ← ya es String
        }

        if (anioPublicacionHasta != null && !anioPublicacionHasta.isEmpty()) {
            sql += "AND m.ANIO_PUBLI <= ? ";
            parametros.add(anioPublicacionHasta);
        }

        if (tipoMaterialTexto != null && !tipoMaterialTexto.isEmpty()) {
            sql += "AND m.TIPO_MATERIAL = ? ";
            parametros.add(tipoMaterialTexto); // debe devolver String
        }

        if (biblioteca.getNombre() != null && !biblioteca.getNombre().isEmpty()) {
             sql += "AND b.NOMBRE = ? ";
            parametros.add(biblioteca.getNombre());
        }
        if (material.getIdioma() != null && !material.getIdioma().isEmpty()) {
            sql += "AND m.IDIOMA = ? ";
            parametros.add(material.getIdioma());
        }
        if (disponibilidadTexto != null && !disponibilidadTexto.isEmpty()) {
            sql += "AND e.ESTADO = ? ";
            parametros.add(disponibilidadTexto); // también debe devolver String
        }

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);

            for (int i = 0; i < parametros.size(); i++) {
                this.statement.setString(i + 1, parametros.get(i).toString());
            }
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();

                // Verificación manual - NO necesita equals() ni hashCode()
                boolean existe = false;
                for (MaterialDTO m : resultados) {
                    if (m.getMaterialId().equals(this.material.getMaterialId())) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    resultados.add(this.material);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al realizar búsqueda avanzada: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return resultados;
}

    @Override
    public ArrayList<MaterialDTO> buscarPorAutor(String autor)  throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE AUTOR LIKE ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + autor + "%");
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
    public boolean comprobarPorAutor(String autor, String idMaterial) throws SQLException {

        String sql = "SELECT 1 FROM G3_MATERIALES WHERE AUTOR LIKE ? AND MATERIAL_ID = ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + autor + "%");
             this.statement.setString(2, idMaterial); // Agregado el segundo parámetro que faltaba
             this.resultSet = this.statement.executeQuery();

            return this.resultSet.next();
        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por título: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorAnio(String anioDesde, String anioHasta) throws SQLException {
         int desde = Integer.parseInt(anioDesde);
        int hasta = Integer.parseInt(anioHasta);
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE ANIO_PUBLI BETWEEN ? AND ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, desde);
            this.statement.setInt(2, hasta);
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
    public boolean comprobarPorAnio(String anioDesde, String anioHasta, String idMaterial) throws SQLException {
        int desde = Integer.parseInt(anioDesde);
        int hasta = Integer.parseInt(anioHasta);
        int materialId = Integer.parseInt(idMaterial);
        String sql = "SELECT 1 FROM G3_MATERIALES WHERE ANIO_PUBLI BETWEEN ? AND ? AND MATERIAL_ID = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql); // Cambiado de prepareCall a prepareStatement
            this.statement.setInt(1, desde);
            this.statement.setInt(2, hasta);
            this.statement.setInt(3, materialId);
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por tipo de material: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }

    
    @Override
    public ArrayList<MaterialDTO> buscarPorTipoMaterial(String tipoMaterial) throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE TIPO_MATERIAL LIKE ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + tipoMaterial + "%");
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
    public boolean comprobarPorTipoMaterial(String tipoMaterial, String idMaterial) throws SQLException {
        String sql = "SELECT 1 FROM G3_MATERIALES WHERE TIPO_MATERIAL LIKE ? AND MATERIAL_ID = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql); // Cambiado de prepareCall a prepareStatement
            this.statement.setString(1, "%" + tipoMaterial + "%");
            this.statement.setString(2, idMaterial); // Agregado el segundo parámetro que faltaba
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por tipo de material: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorTema(String tema) throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE TEMA LIKE ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + tema + "%");
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
    public boolean comprobarPorTema(String tema, String idMaterial) throws SQLException {
        String sql = "SELECT 1 FROM G3_MATERIALES WHERE TEMA LIKE ? AND MATERIAL_ID = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql); // Usando prepareCall como solicitaste
            this.statement.setString(1, "%" + tema + "%");
            this.statement.setString(2, idMaterial); // Agregado el segundo parámetro que faltaba
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por tema: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorIdioma(String idioma)  throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = "SELECT * FROM G3_MATERIALES WHERE IDIOMA LIKE ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + idioma + "%");
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
    public boolean comprobarPorIdioma(String idioma, String idMaterial) throws SQLException {
        String sql = "SELECT 1 FROM G3_MATERIALES WHERE IDIOMA LIKE ? AND MATERIAL_ID = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql); // Usando prepareCall como solicitaste
            this.statement.setString(1, "%" + idioma + "%");
            this.statement.setString(2, idMaterial); // Agregado el segundo parámetro que faltaba
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por idioma: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorDisponibilidad(String disponibildad) throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
         String sql = """
        SELECT DISTINCT m.*
        FROM G3_MATERIALES m
        INNER JOIN G3_EJEMPLARES e ON m.MATERIAL_ID = e.MATERIAL_ID
        WHERE e.ESTADO = ?
    """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, disponibildad);
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
    public boolean comprobarPorDisponibilidad(String disponibilidad, String idMaterial) throws SQLException {
        String sql = """
            SELECT 1 
            FROM G3_MATERIALES m
            INNER JOIN G3_EJEMPLARES e ON m.MATERIAL_ID = e.MATERIAL_ID
            WHERE e.ESTADO LIKE ? AND m.MATERIAL_ID = ?
        """;
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + disponibilidad + "%");
            this.statement.setString(2, idMaterial);
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por disponibilidad: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    
    @Override
    public ArrayList<MaterialDTO> buscarPorBiblioteca(String biblioteca) throws SQLException{
        ArrayList<MaterialDTO> resultados = new ArrayList<>();
        String sql = """
        SELECT DISTINCT m.*
        FROM G3_MATERIALES m
        INNER JOIN G3_EJEMPLARES e ON m.MATERIAL_ID = e.MATERIAL_ID
        INNER JOIN G3_BIBLIOTECAS b ON e.BIBLIOTECA_ID = b.BIBLIOTECA_ID
        WHERE b.NOMBRE LIKE ?
        """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + biblioteca + "%");
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
    public boolean comprobarPorBiblioteca(String biblioteca, String idMaterial) throws SQLException {
        String sql = """
            SELECT 1 
            FROM G3_MATERIALES m
            INNER JOIN G3_EJEMPLARES e ON m.MATERIAL_ID = e.MATERIAL_ID
            INNER JOIN G3_BIBLIOTECAS b ON e.BIBLIOTECA_ID = b.BIBLIOTECA_ID
            WHERE b.NOMBRE LIKE ? AND m.MATERIAL_ID = ?
            """;
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, "%" + biblioteca + "%");
            this.statement.setString(2, idMaterial);
            this.resultSet = this.statement.executeQuery();

            // Retorna true si encuentra al menos un registro
            return this.resultSet.next();

        } catch (SQLException ex) {
            System.err.println("Error al buscar materiales por biblioteca: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }
    }
    @Override
    public List<String> obtenerIdiomasAvanzada() throws SQLException{
        List<String> idiomas = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.IDIOMA
            FROM G3_MATERIALES m
            WHERE m.IDIOMA IS NOT NULL
            ORDER BY m.IDIOMA
        """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                String idioma = this.resultSet.getString("IDIOMA");
                if (idioma != null && !idioma.isEmpty()) {
                    idiomas.add(idioma);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener idioma por idMaterial: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return idiomas;
    }
    @Override
    public String obtenerIdiomas(String idMaterial) throws SQLException {
        String idioma = null;
        String sql = """
            SELECT DISTINCT m.IDIOMA
            FROM G3_MATERIALES m
            WHERE m.MATERIAL_ID = ?
        """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, idMaterial);
            this.resultSet = this.statement.executeQuery();

            if (this.resultSet.next()) {
                idioma = this.resultSet.getString("IDIOMA");
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener idioma por idMaterial: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return idioma;
    }

    
    @Override
    public String obtenerTemas(String idMaterial) throws SQLException {
        String tema = null;
        String sql = """
            SELECT DISTINCT m.TEMA
            FROM G3_MATERIALES m
            WHERE m.MATERIAL_ID = ? AND m.TEMA IS NOT NULL
        """;

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, idMaterial);
            this.resultSet = this.statement.executeQuery();

            if (this.resultSet.next()) {
                tema = this.resultSet.getString("TEMA");
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener tema por idMaterial: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return tema;
    }


    @Override
    public void envioCorreos(String destino, String asunto, String txt) {
        String origen= "sistema.de.bibliotecas.prog3@gmail.com";
        String contra16Digitos= "xmlm dvks ugkb cggq";
        super.envioDeCorreos(origen, destino, asunto, txt, contra16Digitos);
    }
}
package pe.edu.pucp.softinv.daoImpl;

import java.sql.ResultSet;
import pe.edu.pucp.softinv.model.usuario.EstadoUsuario;
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;
import pe.edu.pucp.softinv.model.usuario.EstudianteDTO;
import pe.edu.pucp.softinv.model.usuario.TipoDocumento;
import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;
import pe.edu.pucp.softinv.model.usuario.BibliotecarioDTO;
import pe.edu.pucp.softinv.model.usuario.DocenteDTO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.dao.TipoUsuarioDAO;
import pe.edu.pucp.softinv.dao.BibliotecaDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl extends DAOImplBase implements UsuarioDAO {

    private UsuarioDTO usuario;

    public UsuarioDAOImpl() {
        super("G3_USUARIOS");
        this.retornarLlavePrimaria = true;
        this.usuario = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("CODIGO_UNIVERSITARIO", false, false));
        this.listaColumnas.add(new Columna("NOMBRES", false, false));
        this.listaColumnas.add(new Columna("PRIMER_APELLIDO", false, false));
        this.listaColumnas.add(new Columna("SEGUNDO_APELLIDO", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("NUMERO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("CORREO_ELECTRONICO", false, false));
        this.listaColumnas.add(new Columna("PASSWORD", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        
        // Columnas específicas de Estudiante
        this.listaColumnas.add(new Columna("ESPECIALIDAD", false, false)); // Estudiante
        
        // Columnas específicas de Docente
        this.listaColumnas.add(new Columna("DEPARTAMENTO_ACADEMICO", false, false)); // Docente
        
        // Columnas específicas de Bibliotecario
        this.listaColumnas.add(new Columna("BIBLIOTECA_ID", false, false)); // Bibliotecario
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Campos comunes
        this.statement.setInt(1, this.usuario.getTipoUsuario().getTipoUsuarioId());
        this.statement.setString(2, this.usuario.getCodigoUniversitario());
        this.statement.setString(3, this.usuario.getNombres());
        this.statement.setString(4, this.usuario.getPrimerApellido());
        this.statement.setString(5, this.usuario.getSegundoApellido());
        this.statement.setString(6, this.usuario.getTipoDocumento().name());
        this.statement.setString(7, this.usuario.getNumeroDocumento());
        this.statement.setString(8, this.usuario.getCorreoElectronico());
        this.statement.setString(9, this.usuario.getPassword());
        this.statement.setString(10, this.usuario.getEstado().name());
        
        // Campos específicos
        switch (this.usuario.getTipoUsuario().getTipoUsuarioId()) {
            case 1 -> { // Estudiante
                EstudianteDTO estudiante = (EstudianteDTO) this.usuario;
                this.statement.setString(11, estudiante.getEspecialidad());
                
                // Vaciar campos que no aplican
                this.statement.setNull(12, java.sql.Types.VARCHAR);
                this.statement.setNull(13, java.sql.Types.INTEGER);
            }
            case 2 -> { // Docente
                DocenteDTO docente = (DocenteDTO) this.usuario;
                // Vaciar campos que no aplican
                this.statement.setNull(11, java.sql.Types.VARCHAR);
                
                this.statement.setString(12, docente.getDepartamentoAcademico());
                
                // Vaciar campos que no aplican
                this.statement.setNull(13, java.sql.Types.INTEGER);
            }
            case 3 -> { // Bibliotecario
                BibliotecarioDTO bibliotecario = (BibliotecarioDTO) this.usuario;
                
                // Vaciar campos que no aplican
                this.statement.setNull(11, java.sql.Types.VARCHAR);
                this.statement.setNull(12, java.sql.Types.VARCHAR);
                
                this.statement.setInt(13, bibliotecario.getBiblioteca().getBibliotecaId());
            }
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(14, usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, usuario.getUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, usuario.getUsuarioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.usuario = new UsuarioDTO();
        
        int tipoId = this.resultSet.getInt("TIPO_USUARIO_ID");
        TipoUsuarioDAO tipoDAO= new TipoUsuarioDAOImpl();
        TipoUsuarioDTO tipoUsuario = tipoDAO.obtenerPorId(tipoId);
        

        switch (tipoId) {
            case 2 -> {
                EstudianteDTO est = new EstudianteDTO();
                est.setEspecialidad(this.resultSet.getString("ESPECIALIDAD"));
                this.usuario = est;
            }
            case 3 -> {
                DocenteDTO doc = new DocenteDTO();
                doc.setDepartamentoAcademico(this.resultSet.getString("DEPARTAMENTO_ACADEMICO"));
                this.usuario = doc;
            }
            case 1 -> {
                BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
                int idbib=this.resultSet.getInt("BIBLIOTECA_ID");
                BibliotecaDAO bibDAO=new BibliotecaDAOImpl();
                BibliotecaDTO bib = bibDAO.obtenerPorId(idbib);
                
                bibliotecario.setBiblioteca(bib);
                this.usuario = bibliotecario;
            }
            default -> throw new SQLException("Tipo de usuario no reconocido");
        }

        usuario.setUsuarioId(this.resultSet.getInt("USUARIO_ID"));
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setCodigoUniversitario(this.resultSet.getString("CODIGO_UNIVERSITARIO"));
        usuario.setNombres(this.resultSet.getString("NOMBRES"));
        usuario.setPrimerApellido(this.resultSet.getString("PRIMER_APELLIDO"));
        usuario.setSegundoApellido(this.resultSet.getString("SEGUNDO_APELLIDO"));
        usuario.setTipoDocumento(TipoDocumento.valueOf(this.resultSet.getString("TIPO_DOCUMENTO")));
        usuario.setNumeroDocumento(this.resultSet.getString("NUMERO_DOCUMENTO"));
        usuario.setCorreoElectronico(this.resultSet.getString("CORREO_ELECTRONICO"));
        usuario.setPassword(this.resultSet.getString("PASSWORD"));
        usuario.setEstado(EstadoUsuario.valueOf(this.resultSet.getString("ESTADO")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.usuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.usuario);
    }

    @Override
    public Integer insertar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.insertar();
    }

    @Override
    public UsuarioDTO obtenerPorId(Integer usuarioId) {
        UsuarioDTO consulta = new UsuarioDTO();
        consulta.setUsuarioId(usuarioId);
        this.usuario = consulta;
        super.obtenerPorId();
        return this.usuario;
    }

    @Override
    public ArrayList<UsuarioDTO> listarTodos() {
        return (ArrayList<UsuarioDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuarioDTO usuario) {
        this.usuario = usuario;
        return super.eliminar();
    }
    
//    @Override
//    public UsuarioDTO buscarPorCodigoUniversitario(String codigo) throws SQLException {
//        String sql = "SELECT * FROM G3_USUARIOS WHERE codigo_universitario = ?";
//        try (Connection conn = DBManager.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, codigo);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapearUsuario(rs);
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public UsuarioDTO buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM G3_USUARIOS WHERE CORREO_ELECTRONICO = ?";
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setString(1, correo);
            this.resultSet = this.statement.executeQuery();

            if (this.resultSet.next()) {
                return mapearUsuario(this.resultSet);
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar por correo: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }
        return null;
    }
    
    private UsuarioDTO mapearUsuario(ResultSet rs) throws SQLException {
        int tipoId = rs.getInt("TIPO_USUARIO_ID");
        UsuarioDTO usuario;

        switch (tipoId) {
            case 1 -> {
                EstudianteDTO estudiante = new EstudianteDTO();
                estudiante.setEspecialidad(rs.getString("ESPECIALIDAD"));
                usuario = estudiante;
            }
            case 2 -> {
                DocenteDTO docente = new DocenteDTO();
                docente.setDepartamentoAcademico(rs.getString("DEPARTAMENTO_ACADEMICO"));
                usuario = docente;
            }
            case 3 -> {
                BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
                BibliotecaDAO bibliotecaDAO =new BibliotecaDAOImpl();
                BibliotecaDTO biblioteca = bibliotecaDAO.obtenerPorId(rs.getInt("BIBLIOTECA_ID"));
                bibliotecario.setBiblioteca(biblioteca);
                usuario = bibliotecario;
            }
            default -> throw new SQLException("Tipo de usuario no reconocido: " + tipoId);
        }

        // Asignar campos comunes
        usuario.setUsuarioId(rs.getInt("USUARIO_ID"));
        
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAOImpl();
        TipoUsuarioDTO tipoUsuario = tipoUsuarioDAO.obtenerPorId(tipoId);
        usuario.setTipoUsuario(tipoUsuario);

        usuario.setCodigoUniversitario(rs.getString("CODIGO_UNIVERSITARIO"));
        usuario.setNombres(rs.getString("NOMBRES"));
        usuario.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
        usuario.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
        usuario.setTipoDocumento(TipoDocumento.valueOf(rs.getString("TIPO_DOCUMENTO")));
        usuario.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
        usuario.setCorreoElectronico(rs.getString("CORREO_ELECTRONICO"));
        usuario.setPassword(rs.getString("PASSWORD"));
        usuario.setEstado(EstadoUsuario.valueOf(rs.getString("ESTADO")));

        return usuario;
    }
    
    @Override
    public ArrayList<UsuarioDTO> listarPorBusquedaAvanzada(String idUsuario, String tipoUsuario, 
            String codigoUniversitario, String nombres, String primerApellido, String estado) {

        ArrayList<UsuarioDTO> lista = new ArrayList<>();

        try {
            this.abrirConexion();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT u.USUARIO_ID, ")
               .append("u.TIPO_USUARIO_ID, ")
               .append("tu.NOMBRE AS TIPO_USUARIO, ")
               .append("u.CODIGO_UNIVERSITARIO, ")
               .append("u.NOMBRES, ")
               .append("u.PRIMER_APELLIDO, ")
               .append("u.SEGUNDO_APELLIDO, ")
               .append("u.TIPO_DOCUMENTO, ")
               .append("u.NUMERO_DOCUMENTO, ")
               .append("u.CORREO_ELECTRONICO, ")
               .append("u.PASSWORD, ")
               .append("u.ESTADO, ")
               .append("u.ESPECIALIDAD, ")            // solo estudiantes
               .append("u.DEPARTAMENTO_ACADEMICO, ")  // solo docentes
               .append("u.BIBLIOTECA_ID ")            // solo bibliotecarios
               .append("FROM G3_USUARIOS u ")
               .append("JOIN G3_TIPOS_USUARIO tu ON u.TIPO_USUARIO_ID = tu.TIPO_USUARIO_ID ")
               .append("WHERE 1=1 ");


            List<Object> parametros = new ArrayList<>();

            if (idUsuario != null && !idUsuario.trim().isEmpty()) {
                sql.append("AND u.USUARIO_ID = ? ");
                parametros.add(idUsuario);
            }

            if (tipoUsuario != null && !tipoUsuario.trim().isEmpty()) {
                sql.append("AND tu.NOMBRE = ? ");
                parametros.add(tipoUsuario);
            }

            if (codigoUniversitario != null && !codigoUniversitario.trim().isEmpty()) {
                sql.append("AND u.CODIGO_UNIVERSITARIO LIKE ? ");
                parametros.add("%" + codigoUniversitario.trim() + "%");
            }

            if (nombres != null && !nombres.trim().isEmpty()) {
                sql.append("AND LOWER(u.NOMBRES) LIKE ? ");
                parametros.add("%" + nombres.trim().toLowerCase() + "%");
            }

            if (primerApellido != null && !primerApellido.trim().isEmpty()) {
                sql.append("AND LOWER(u.PRIMER_APELLIDO) LIKE ? ");
                parametros.add("%" + primerApellido.trim().toLowerCase() + "%");
            }

            if (estado != null && !estado.trim().isEmpty()) {
                sql.append("AND UPPER(u.ESTADO) = ? ");
                parametros.add(estado.trim().toUpperCase());
            }

            this.colocarSQLenStatement(sql.toString());

            for (int i = 0; i < parametros.size(); i++) {
                this.statement.setObject(i + 1, parametros.get(i));
            }

            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                lista.add(this.usuario);
                this.limpiarObjetoDelResultSet();
            }

        } catch (SQLException ex) {
            System.err.println("Error en listarUsuariosAvanzado: " + ex.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }

        return lista;
    }
}

package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.dao.ReservaDAO;
import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softinv.model.circulacion.EstadoReserva;
import pe.edu.pucp.softinv.dao.MaterialDAO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;

public class ReservaDAOImpl extends DAOImplBase implements ReservaDAO{
    
    private ReservaDTO reserva;
    private MaterialDAO materialDAO;
    private UsuarioDAO usuarioDAO;

    public ReservaDAOImpl() {
        super("G3_RESERVAS");
        this.retornarLlavePrimaria = true;
        this.reserva = null;
        materialDAO = new MaterialDAOImpl();
        usuarioDAO = new UsuarioDAOImpl();
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("RESERVA_ID", true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("MATERIAL_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_RESERVA", false, false));
        this.listaColumnas.add(new Columna("FECHA_VENCIMIENTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO_RESERVA", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.reserva.getUsuario().getUsuarioId());
        this.statement.setInt(2, this.reserva.getMaterial().getMaterialId());

        // Conversión segura de java.util.Date -> java.sql.Date
        this.statement.setDate(3, new java.sql.Date(this.reserva.getFechaReserva().getTime()));
        this.statement.setDate(4, new java.sql.Date(this.reserva.getFechaVencimiento().getTime()));

        this.statement.setString(5, this.reserva.getEstadoReserva().name());
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(6, this.reserva.getReservaId());
    }
    
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.reserva.getReservaId());
    }
    
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.reserva.getReservaId());
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.reserva = new ReservaDTO();

        this.reserva.setReservaId(this.resultSet.getInt("RESERVA_ID"));
        
        UsuarioDTO usuario = usuarioDAO.obtenerPorId(this.resultSet.getInt("USUARIO_ID"));
        this.reserva.setUsuario(usuario);
        
        MaterialDTO material = materialDAO.obtenerPorId(this.resultSet.getInt("MATERIAL_ID"));
        this.reserva.setMaterial(material);
        
        this.reserva.setFechaReserva(this.resultSet.getDate("FECHA_RESERVA"));
        this.reserva.setFechaVencimiento(this.resultSet.getDate("FECHA_VENCIMIENTO"));
        this.reserva.setEstadoReserva(EstadoReserva.valueOf(this.resultSet.getString("ESTADO_RESERVA")));
    }
    
    @Override
    protected void limpiarObjetoDelResultSet() {
        this.reserva = null;
    }
    
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.reserva);
    }
    
    @Override
    public Integer insertar(ReservaDTO reserva) {
        this.reserva = reserva;
        return super.insertar();
    }
    
    @Override
    public ReservaDTO obtenerPorId(Integer reservaId) {
        this.reserva = new ReservaDTO();
        this.reserva.setReservaId(reservaId);
        super.obtenerPorId();
        return this.reserva;
    }

    @Override
    public ArrayList<ReservaDTO> listarTodos() {
        return (ArrayList<ReservaDTO>) super.listarTodos();
    }
    
    @Override
    public Integer modificar(ReservaDTO reserva) {
        this.reserva = reserva;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ReservaDTO reserva) {
        this.reserva = reserva;
        return super.eliminar();
    } 
    
//    @Override
//    public List<ReservaDTO> obtenerDetallesPorPrestamoId(Integer prestamoId) throws SQLException {
//        List<ReservaDTO> detalles = new ArrayList<>();
//
//        try {
//            // Abrir conexión utilizando método de la clase base
//            this.abrirConexion();
//
//            // Definir SQL del procedimiento almacenado
//            String sql = "{CALL sp_obtener_detalles_prestamo(?)}";
//
//            // Preparar el statement
//            this.colocarSQLenStatement(sql);
//            this.statement.setInt(1, prestamoId);
//
//            // Ejecutar la consulta y obtener el resultSet
//            this.ejecutarConsultaEnBD();
//
//            while (this.resultSet.next()) {
//                ReservaDTO detalle = new ReservaDTO();
//
//                // Mapear campos de reserva
//                detalle.setReservaId(this.resultSet.getInt("RESERVA_ID"));
//                detalle.setFechaReserva(this.resultSet.getDate("FECHA_RESERVA"));
//                detalle.setFechaVencimiento(this.resultSet.getDate("FECHA_VENCIMIENTO"));
//                detalle.setEstadoReserva(EstadoReserva.valueOf(this.resultSet.getString("ESTADO_RESERVA")));
//
//                // Mapear material asociado
//                int materialId = this.resultSet.getInt("MATERIAL_ID");
//                MaterialDTO material = materialDAO.obtenerPorId(materialId);
//
//                // Asociar material con la reserva
//                detalle.setMaterial(material);
//
//                // Agregar a la lista de resultados
//                detalles.add(detalle);
//            }
//
//        } finally {
//            // Cerrar conexión usando método de clase base
//            this.cerrarConexion();
//        }
//
//        return detalles;
//    }
    
    @Override
    public List<ReservaDTO> listarReservasVigentesPorUsuario(int usuarioId) throws SQLException {
        List<ReservaDTO> reservas = new ArrayList<>();
        String sql = "SELECT * FROM G3_RESERVAS WHERE USUARIO_ID = ? AND ESTADO_RESERVA = 'VIGENTE'";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, usuarioId);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                reservas.add(this.reserva);
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar reservas vigentes por usuario: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return reservas;
    }
    
    @Override
    public ArrayList<ReservaDTO> listarPorBusquedaAvanzada(String reservaId, String usuarioNombre, 
            String materialTitulo, String estado, Date fechaDesde, Date fechaHasta) {
        
        
        ArrayList<ReservaDTO> lista = new ArrayList<>();

        try {
            this.abrirConexion();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT r.RESERVA_ID, r.USUARIO_ID, r.MATERIAL_ID, r.FECHA_RESERVA, r.FECHA_VENCIMIENTO, r.ESTADO_RESERVA ")
               .append("FROM G3_RESERVAS r ")
               .append("JOIN G3_USUARIOS u ON r.USUARIO_ID = u.USUARIO_ID ")
               .append("JOIN G3_MATERIALES m ON r.MATERIAL_ID = m.MATERIAL_ID ")
               .append("WHERE 1=1 ");

            List<Object> parametros = new ArrayList<>();

            if (reservaId != null && !reservaId.trim().isEmpty()) {
                sql.append("AND r.RESERVA_ID = ? ");
                parametros.add(reservaId);
            }

            if (usuarioNombre != null && !usuarioNombre.trim().isEmpty()) {
                sql.append("AND LOWER(u.NOMBRES) LIKE ? ");
                parametros.add("%" + usuarioNombre.trim().toLowerCase() + "%");
            }

            if (materialTitulo != null && !materialTitulo.trim().isEmpty()) {
                sql.append("AND LOWER(m.TITULO) LIKE ? ");
                parametros.add("%" + materialTitulo.trim().toLowerCase() + "%");
            }

            if (estado != null && !estado.trim().isEmpty()) {
                sql.append("AND UPPER(r.ESTADO_RESERVA) = ? ");
                parametros.add(estado.trim().toUpperCase());
            }
            

            if (fechaDesde != null) {
                sql.append("AND r.FECHA_RESERVA >= ? ");
                parametros.add(new java.sql.Date(fechaDesde.getTime()));
            }

            if (fechaHasta != null) {
                sql.append("AND r.FECHA_RESERVA <= ? ");
                parametros.add(new java.sql.Date(fechaHasta.getTime()));
            }

            this.colocarSQLenStatement(sql.toString());

            // Asignar los parámetros al PreparedStatement
            for (int i = 0; i < parametros.size(); i++) {
                this.statement.setObject(i + 1, parametros.get(i));
            }

            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {                
                this.instanciarObjetoDelResultSet();
                lista.add(this.reserva);
                this.limpiarObjetoDelResultSet();             
            }
            

        } catch (SQLException ex) {
            System.err.println("Error en buscarReservasAvanzado: " + ex.getMessage());
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión en búsqueda avanzada: " + ex.getMessage());
            }
        }

        return lista; 
    
    }    
}

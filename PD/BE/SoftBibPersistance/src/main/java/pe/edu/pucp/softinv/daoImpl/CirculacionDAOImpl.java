package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;
import pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.dao.CirculacionDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softinv.dao.EjemplarDAO;
import pe.edu.pucp.softinv.dao.ReservaDAO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;

public class CirculacionDAOImpl extends DAOImplBase implements CirculacionDAO {

    private CirculacionDTO circulacion;

    public CirculacionDAOImpl() {
        super("G3_CIRCULACIONES");
        this.retornarLlavePrimaria = true;
        this.circulacion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("CIRCULACION_ID", true, true));
        this.listaColumnas.add(new Columna("RESERVA_ID", false, false));
        this.listaColumnas.add(new Columna("USUARIO_ID", false, false));
        this.listaColumnas.add(new Columna("EJEMPLAR_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_PRESTAMO", false, false));
        this.listaColumnas.add(new Columna("FECHA_VENCIMIENTO", false, false));
        this.listaColumnas.add(new Columna("FECHA_DEVOLUCION", false, false));
        this.listaColumnas.add(new Columna("ESTADO_PRESTAMO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.circulacion.getReserva().getReservaId());
        this.statement.setInt(2, this.circulacion.getUsuario().getUsuarioId());
        this.statement.setInt(3, this.circulacion.getEjemplar().getEjemplarId());
        this.statement.setDate(4, new Date(this.circulacion.getFechaPrestamo().getTime()));
        this.statement.setDate(5, new Date(this.circulacion.getFechaVencimiento().getTime()));
        this.statement.setDate(6, new Date(this.circulacion.getFechaDevolucion().getTime()));
        this.statement.setString(7, this.circulacion.getEstadoPrestamo().name());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(8, this.circulacion.getCirculacionId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.circulacion.getCirculacionId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.circulacion.getCirculacionId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.circulacion = new CirculacionDTO();

        this.circulacion.setCirculacionId(this.resultSet.getInt("CIRCULACION_ID"));
        //hubo un cambio acá, ahora carga todos sus objetos completos:
        int idreserva=this.resultSet.getInt("RESERVA_ID");
        ReservaDAO reservaDAO = new ReservaDAOImpl();
        ReservaDTO reserva = reservaDAO.obtenerPorId(idreserva);
        this.circulacion.setReserva(reserva);
        
        int idusuario=this.resultSet.getInt("USUARIO_ID");
        UsuarioDAO usuarioDAO= new UsuarioDAOImpl();
        UsuarioDTO usuario=usuarioDAO.obtenerPorId(idusuario);
        this.circulacion.setUsuario(usuario);
        
        int idejemplar=this.resultSet.getInt("EJEMPLAR_ID");
        EjemplarDAO ejemplarDAO = new EjemplarDAOImpl();
        EjemplarDTO ejemplar = ejemplarDAO.obtenerPorId(idejemplar);
        this.circulacion.setEjemplar(ejemplar);

        this.circulacion.setFechaPrestamo(this.resultSet.getDate("FECHA_PRESTAMO"));
        this.circulacion.setFechaVencimiento(this.resultSet.getDate("FECHA_VENCIMIENTO"));
        this.circulacion.setFechaDevolucion(this.resultSet.getDate("FECHA_DEVOLUCION"));
        this.circulacion.setEstadoPrestamo(EstadoPrestamo.valueOf(this.resultSet.getString("ESTADO_PRESTAMO")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.circulacion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException { 
        this.instanciarObjetoDelResultSet();
        lista.add(this.circulacion);
    }

    @Override
    public Integer insertar(CirculacionDTO circulacion) {//
        this.circulacion = circulacion;
        return super.insertar();
    }

    @Override
    public CirculacionDTO obtenerPorId(Integer idCirculacion) {//
        this.circulacion = new CirculacionDTO();
        this.circulacion.setCirculacionId(idCirculacion);
        super.obtenerPorId();
        return this.circulacion;
    }

    @Override
    public ArrayList<CirculacionDTO> listarTodos() { //
        return (ArrayList<CirculacionDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(CirculacionDTO circulacion) { //
        this.circulacion = circulacion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(CirculacionDTO circulacion) {//
        this.circulacion = circulacion;
        return super.eliminar();
    }
    
    @Override
    public List<CirculacionDTO> listarPrestamosPorUsuario(int usuarioId) throws SQLException {
        List<CirculacionDTO> circulaciones = new ArrayList<>();
        String sql = "SELECT * FROM G3_CIRCULACIONES WHERE USUARIO_ID = ?";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, usuarioId);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet(); // Llena this.circulacion
                circulaciones.add(this.circulacion);
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar circulaciones por usuario: " + ex);
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return circulaciones;
    }

    
    @Override
    public ArrayList<CirculacionDTO> listarPorBusquedaAvanzada(String idCirculacion, String idReserva, 
            String idUsuario, String idEjemplar, String estado, java.util.Date fechaDesde, 
            java.util.Date fechaHasta) {
        
        ArrayList<CirculacionDTO> lista = new ArrayList<>();
        try {
            this.abrirConexion();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT CIRCULACION_ID, RESERVA_ID, USUARIO_ID, EJEMPLAR_ID, ")
               .append("FECHA_PRESTAMO, FECHA_VENCIMIENTO, FECHA_DEVOLUCION, ESTADO_PRESTAMO ")
               .append("FROM G3_CIRCULACIONES WHERE 1=1 ");
            List<Object> parametros = new ArrayList<>();
            if (idCirculacion != null && !idCirculacion.trim().isEmpty()) {
                sql.append("AND CIRCULACION_ID = ? ");
                parametros.add(idCirculacion);
            }
            if (idReserva != null && !idReserva.trim().isEmpty()) {
                sql.append("AND RESERVA_ID = ? ");
                parametros.add(idReserva);
            }
            if (idUsuario != null && !idUsuario.trim().isEmpty()) {
                sql.append("AND USUARIO_ID = ? ");
                parametros.add(idUsuario);
            }
            if (idEjemplar != null && !idEjemplar.trim().isEmpty()) {
                sql.append("AND EJEMPLAR_ID = ? ");
                parametros.add(idEjemplar);
            }
            if (estado != null && !estado.trim().isEmpty()) {
                sql.append("AND UPPER(ESTADO_PRESTAMO) = ? ");
                parametros.add(estado.trim().toUpperCase());
            }
            if (fechaDesde != null) {
                sql.append("AND FECHA_PRESTAMO >= ? ");
                parametros.add(new java.sql.Date(fechaDesde.getTime()));
            }
            if (fechaHasta != null) {
                sql.append("AND FECHA_PRESTAMO <= ? ");
                parametros.add(new java.sql.Date(fechaHasta.getTime()));
            }
            this.colocarSQLenStatement(sql.toString());
            for (int i = 0; i < parametros.size(); i++) {
                this.statement.setObject(i + 1, parametros.get(i));
            }
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();                
                lista.add(circulacion);
                this.limpiarObjetoDelResultSet();
            }
        } catch (SQLException ex) {
            System.err.println("Error en listarCirculacionesAvanzado: " + ex.getMessage());
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

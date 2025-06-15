package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.dao.DiaAtencionDAO;
import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DiaAtencionDAOImpl extends DAOImplBase implements DiaAtencionDAO {
    private DiaAtencionDTO diaAtencion;

    public DiaAtencionDAOImpl() {
        super("G3_DIAS_ATENCION");
        this.retornarLlavePrimaria = true;
        this.diaAtencion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DIA_ATENCION_ID", true, true));
        this.listaColumnas.add(new Columna("BIBLIOTECA_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("HORA_INICIO_ATENCION", false, false));
        this.listaColumnas.add(new Columna("HORA_FIN_ATENCION", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.diaAtencion.getBiblioteca().getBibliotecaId());
        this.statement.setString(2, this.diaAtencion.getNombre());
        this.statement.setTime(3, Time.valueOf(this.diaAtencion.getHoraInicioAtencion()));
        this.statement.setTime(4, Time.valueOf(this.diaAtencion.getHoraFinAtencion()));
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(5, this.diaAtencion.getDiaAtencionId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.diaAtencion.getDiaAtencionId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.diaAtencion.getDiaAtencionId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.diaAtencion = new DiaAtencionDTO();

        this.diaAtencion.setDiaAtencionId(this.resultSet.getInt("DIA_ATENCION_ID"));

        BibliotecaDTO biblioteca = new BibliotecaDTO();
        biblioteca.setBibliotecaId(this.resultSet.getInt("BIBLIOTECA_ID"));
        this.diaAtencion.setBiblioteca(biblioteca);

        this.diaAtencion.setNombre(this.resultSet.getString("NOMBRE"));
        this.diaAtencion.setHoraInicioAtencion(this.resultSet.getTime("HORA_INICIO_ATENCION").toLocalTime());
        this.diaAtencion.setHoraFinAtencion(this.resultSet.getTime("HORA_FIN_ATENCION").toLocalTime());
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.diaAtencion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.diaAtencion);
    }
    
    @Override
    public Integer insertar(DiaAtencionDTO diaAtencion) {
        this.diaAtencion = diaAtencion;
        return super.insertar();
    }

    @Override
    public DiaAtencionDTO obtenerPorId(Integer id) {
        this.diaAtencion = new DiaAtencionDTO();
        this.diaAtencion.setDiaAtencionId(id);
        super.obtenerPorId();
        return this.diaAtencion;
    }

    @Override
    public ArrayList<DiaAtencionDTO> listarTodos() {
        return (ArrayList<DiaAtencionDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(DiaAtencionDTO diaAtencion) {
        this.diaAtencion = diaAtencion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DiaAtencionDTO diaAtencion) {
        this.diaAtencion = diaAtencion;
        return super.eliminar();
    }
    
    @Override
    public ArrayList<DiaAtencionDTO> listarPorAtencionBiblioteca(int bibliotecaId) {
        ArrayList<DiaAtencionDTO> lista = new ArrayList<>();

        try {
            this.abrirConexion();

            String sql = "SELECT * FROM G3_DIAS_ATENCION WHERE BIBLIOTECA_ID = ?";
            this.colocarSQLenStatement(sql);
            this.statement.setInt(1, bibliotecaId);
            this.ejecutarConsultaEnBD();

            while (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                DiaAtencionDTO copia = new DiaAtencionDTO(this.diaAtencion);
                lista.add(copia);
            }

        } catch (SQLException ex) {
            System.err.println("Error al listar días por biblioteca - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }

        return lista;
    }
}
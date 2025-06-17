package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.dao.SancionDAO;
import pe.edu.pucp.softinv.model.circulacion.SancionDTO;
import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SancionDAOImpl extends DAOImplBase implements SancionDAO {

    private SancionDTO sancion;

    public SancionDAOImpl() {
        super("G3_SANCIONES");
        this.retornarLlavePrimaria = true;
        this.sancion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SANCION_ID", true, true));
        this.listaColumnas.add(new Columna("CIRCULACION_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_REGISTRO", false, false));
        this.listaColumnas.add(new Columna("FECHA_TERMINO", false, false));
        this.listaColumnas.add(new Columna("DIAS_SANCION", false, false));
        this.listaColumnas.add(new Columna("OBSERVACION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.sancion.getCirculacion().getCirculacionId());
        this.statement.setDate(2, new Date(this.sancion.getFechaRegistro().getTime()));
        this.statement.setDate(3, new Date(this.sancion.getFechaTermino().getTime()));
        this.statement.setInt(4, this.sancion.getDiasSancion());
        this.statement.setString(5, this.sancion.getObservacion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(6, this.sancion.getSancionId()); // ID al final
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.sancion.getSancionId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.sancion.getSancionId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.sancion = new SancionDTO();
        this.sancion.setSancionId(resultSet.getInt("SANCION_ID"));
        
        CirculacionDTO circulacion = new CirculacionDTO();
        circulacion.setCirculacionId(this.resultSet.getInt("CIRCULACION_ID"));
        this.sancion.setCirculacion(circulacion);
        
        this.sancion.setFechaRegistro(resultSet.getDate("FECHA_REGISTRO"));
        this.sancion.setFechaTermino(resultSet.getDate("FECHA_TERMINO"));
        this.sancion.setDiasSancion(resultSet.getInt("DIAS_SANCION"));
        this.sancion.setObservacion(resultSet.getString("OBSERVACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.sancion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.sancion);
    }

    @Override
    public Integer insertar(SancionDTO sancion) {
        this.sancion = sancion;
        return super.insertar();
    }

    @Override
    public SancionDTO obtenerPorId(Integer sancionId) {
        this.sancion = new SancionDTO();
        this.sancion.setSancionId(sancionId);
        super.obtenerPorId();
        return this.sancion;
    }

    @Override
    public ArrayList<SancionDTO> listarTodos() {
        return (ArrayList<SancionDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(SancionDTO sancion) {
        this.sancion = sancion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(SancionDTO sancion) {
        this.sancion = sancion;
        return super.eliminar();
    }
    
    @Override
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) {
        super.envioDeCorreos(origen, destino, asunto, txt, contra16Digitos);
    }
}

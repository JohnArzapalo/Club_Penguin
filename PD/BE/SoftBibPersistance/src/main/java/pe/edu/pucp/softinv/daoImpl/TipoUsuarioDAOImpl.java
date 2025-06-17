package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;
import pe.edu.pucp.softinv.dao.TipoUsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoUsuarioDAOImpl extends DAOImplBase implements TipoUsuarioDAO {
    
    private TipoUsuarioDTO tipoUsuario;

    public TipoUsuarioDAOImpl() {
        super("G3_TIPOS_USUARIO");
        this.retornarLlavePrimaria = true;
        this.tipoUsuario = null;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_USUARIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("NUMERO_MAX_ITEMS", false, false));
        this.listaColumnas.add(new Columna("NUMERO_MAX_DIAS", false, false));
    }
    
    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoUsuario.getNombre());
        this.statement.setInt(2, this.tipoUsuario.getNumeroMaxItems());
        this.statement.setInt(3, this.tipoUsuario.getNumeroMaxDias());
    }
    
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(4, this.tipoUsuario.getTipoUsuarioId());
    }
    
    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoUsuario.getTipoUsuarioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoUsuario.getTipoUsuarioId());
    }
    
    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoUsuario = new TipoUsuarioDTO();
        this.tipoUsuario.setTipoUsuarioId(this.resultSet.getInt("TIPO_USUARIO_ID"));
        this.tipoUsuario.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipoUsuario.setNumeroMaxItems(this.resultSet.getInt("NUMERO_MAX_ITEMS"));
        this.tipoUsuario.setNumeroMaxDias(this.resultSet.getInt("NUMERO_MAX_DIAS"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoUsuario = null;
    }
    
    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoUsuario);
    }
    
    @Override
    public Integer insertar(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return super.insertar();
    }

    @Override
    public TipoUsuarioDTO obtenerPorId(Integer tipoUsuarioId) {
        this.tipoUsuario = new TipoUsuarioDTO();
        this.tipoUsuario.setTipoUsuarioId(tipoUsuarioId);
        super.obtenerPorId();
        return this.tipoUsuario;
    }
    
    @Override
    public ArrayList<TipoUsuarioDTO> listarTodos() {
        return (ArrayList<TipoUsuarioDTO>) super.listarTodos();
    }
    
    @Override
    public Integer modificar(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return super.modificar();
    }
    
    @Override
    public Integer eliminar(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return super.eliminar();
    }
    
    @Override
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) {
        super.envioDeCorreos(origen, destino, asunto, txt, contra16Digitos);
    }
}

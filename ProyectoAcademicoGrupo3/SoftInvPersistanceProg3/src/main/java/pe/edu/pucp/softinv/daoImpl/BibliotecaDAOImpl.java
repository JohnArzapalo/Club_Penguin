package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.dao.BibliotecaDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDAOImpl extends DAOImplBase implements BibliotecaDAO {

    private BibliotecaDTO biblioteca;

    public BibliotecaDAOImpl() {
        super("G3_BIBLIOTECAS");
        this.retornarLlavePrimaria = true;
        this.biblioteca = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("BIBLIOTECA_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("UBICACION", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.biblioteca.getNombre());
        this.statement.setString(2, this.biblioteca.getUbicacion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(3, this.biblioteca.getBibliotecaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.biblioteca.getBibliotecaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.biblioteca.getBibliotecaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.biblioteca = new BibliotecaDTO();
        this.biblioteca.setBibliotecaId(this.resultSet.getInt("BIBLIOTECA_ID"));
        this.biblioteca.setNombre(this.resultSet.getString("NOMBRE"));
        this.biblioteca.setUbicacion(this.resultSet.getString("UBICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.biblioteca = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.biblioteca);
    }

    @Override
    public Integer insertar(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
        return super.insertar();
    }

    @Override
    public BibliotecaDTO obtenerPorId(Integer bibliotecaId) {
        this.biblioteca = new BibliotecaDTO();
        this.biblioteca.setBibliotecaId(bibliotecaId);
        super.obtenerPorId();
        return this.biblioteca;
    }

    @Override
    public ArrayList<BibliotecaDTO> listarTodos() {
        return (ArrayList<BibliotecaDTO>) super.listarTodos();
    }
    
    @Override
    public Integer modificar(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
        return super.modificar();
    }

    @Override
    public Integer eliminar(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
        return super.eliminar();
    }
    
    @Override
    public List<BibliotecaDTO> obtenerBibliotecasPorMaterial(int materialId) throws SQLException {
        List<BibliotecaDTO> bibliotecas = new ArrayList<>();
        String sql = "{ call SP_BIBLIOTECAS_POR_MATERIAL(?) }";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, materialId);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                BibliotecaDTO biblioteca = new BibliotecaDTO();
                biblioteca.setBibliotecaId(this.resultSet.getInt("BIBLIOTECA_ID"));
                biblioteca.setNombre(this.resultSet.getString("NOMBRE_BIBLIOTECA"));
                biblioteca.setUbicacion(this.resultSet.getString("UBICACION"));
                bibliotecas.add(biblioteca);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener bibliotecas por material: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return bibliotecas;
    }
}

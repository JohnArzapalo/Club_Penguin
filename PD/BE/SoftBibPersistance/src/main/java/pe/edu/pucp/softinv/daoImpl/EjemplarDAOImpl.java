package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.dao.EjemplarDAO;
import pe.edu.pucp.softinv.daoImpl.util.Columna;
import pe.edu.pucp.softinv.model.material.MaterialDTO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softinv.dao.BibliotecaDAO;
import pe.edu.pucp.softinv.dao.MaterialDAO;
import pe.edu.pucp.softinv.model.material.EstadoEjemplar;

public class EjemplarDAOImpl extends DAOImplBase implements EjemplarDAO {

    private EjemplarDTO ejemplar;

    public EjemplarDAOImpl() {
        super("G3_EJEMPLARES");
        this.retornarLlavePrimaria = true;
        this.ejemplar = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("EJEMPLAR_ID", true, true));
        this.listaColumnas.add(new Columna("MATERIAL_ID", false, false));
        this.listaColumnas.add(new Columna("BIBLIOTECA_ID", false, false));
        this.listaColumnas.add(new Columna("LOCACION_EN_BIBLIOTECA", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("ELIMINABLE", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.ejemplar.getMaterial().getMaterialId());
        this.statement.setInt(2, this.ejemplar.getBiblioteca().getBibliotecaId());
        this.statement.setString(3, this.ejemplar.getLocacionEnBiblioteca());
        this.statement.setString(4, this.ejemplar.getEstado().name());
        this.statement.setBoolean(5, this.ejemplar.getEliminable());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.incluirValorDeParametrosParaInsercion();
        this.statement.setInt(6, this.ejemplar.getEjemplarId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.ejemplar.getEjemplarId());
    }
    

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.ejemplar.getEjemplarId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        // SE CARGARON LOS OBJETOS MATERIAL Y BIBLIOTECA
        this.ejemplar = new EjemplarDTO();
        this.ejemplar.setEjemplarId(this.resultSet.getInt("EJEMPLAR_ID"));
        
        MaterialDAO materialDAO = new MaterialDAOImpl();
        MaterialDTO material = materialDAO.obtenerPorId(this.resultSet.getInt("MATERIAL_ID"));
        this.ejemplar.setMaterial(material);
        
        BibliotecaDAO bibliotecaDAO = new BibliotecaDAOImpl();
        BibliotecaDTO biblioteca = bibliotecaDAO.obtenerPorId(this.resultSet.getInt("BIBLIOTECA_ID"));
        this.ejemplar.setBiblioteca(biblioteca);
        
        this.ejemplar.setLocacionEnBiblioteca(this.resultSet.getString("LOCACION_EN_BIBLIOTECA"));
        this.ejemplar.setEstado(EstadoEjemplar.valueOf(this.resultSet.getString("ESTADO")));
        this.ejemplar.setEliminable(this.resultSet.getBoolean("ELIMINABLE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.ejemplar = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.ejemplar);
    }
    
    @Override
    public Integer insertar(EjemplarDTO ejemplar) {
        this.ejemplar = ejemplar;
        return super.insertar();
    }
    
    @Override
    public EjemplarDTO obtenerPorId(Integer idEjemplar) {
        this.ejemplar = new EjemplarDTO();
        this.ejemplar.setEjemplarId(idEjemplar);
        super.obtenerPorId();
        return this.ejemplar;
    }

    @Override
    public ArrayList<EjemplarDTO> listarTodos() {
        return (ArrayList<EjemplarDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(EjemplarDTO ejemplar) {
        this.ejemplar = ejemplar;
        return super.modificar();
    }

    @Override
    public Integer eliminar(EjemplarDTO ejemplar) {
        this.ejemplar = ejemplar;
        return super.eliminar();
    }
    
    @Override
    public List<EjemplarDTO> obtenerEjemplaresMaterialPorBiblioteca(int materialId, int bibliotecaId) throws SQLException {
        List<EjemplarDTO> ejemplares = new ArrayList<>();
        String sql = "{ call SP_EJEMPLARES_DE_MATERIAL_POR_BIBLIOTECA(?, ?) }";

        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);
            this.statement.setInt(1, materialId);
            this.statement.setInt(2, bibliotecaId);
            this.resultSet = this.statement.executeQuery();

            while (this.resultSet.next()) {
                EjemplarDTO ejemplar = new EjemplarDTO();
                ejemplar.setEjemplarId(this.resultSet.getInt("EJEMPLAR_ID"));
                
                MaterialDTO material = new MaterialDTO();
                material.setMaterialId(this.resultSet.getInt("MATERIAL_ID"));
                ejemplar.setMaterial(material);
                
                BibliotecaDTO biblioteca = new BibliotecaDTO();
                biblioteca.setBibliotecaId(this.resultSet.getInt("BIBLIOTECA_ID"));
                ejemplar.setBiblioteca(biblioteca);

                ejemplar.setLocacionEnBiblioteca(this.resultSet.getString("LOCACION_EN_BIBLIOTECA"));
                ejemplar.setEstado(EstadoEjemplar.valueOf(this.resultSet.getString("ESTADO")));
                ejemplar.setEliminable(this.resultSet.getBoolean("ELIMINABLE"));
                
                ejemplares.add(ejemplar);
            }

        } catch (SQLException ex) {
            System.err.println("Error al obtener ejemplares por material y biblioteca: " + ex.getMessage());
            throw ex;
        } finally {
            this.cerrarConexion();
        }

        return ejemplares;
    }
    
//    @Override
//    public List<G3_EjemplarDTO> listarPorMaterial(Integer idMaterial) throws SQLException{
//        List<G3_EjemplarDTO> ejemplares = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM G3_EJEMPLARES WHERE MATERIAL_ID = ?";
//            this.abrirConexion();
//            this.statement = this.conexion.prepareCall(sql);
//            this.statement.setInt(1, idMaterial);
//            this.resultSet = this.statement.executeQuery();
//            
//            while (this.resultSet.next()) {
//                G3_EjemplarDTO ejemplar = new G3_EjemplarDTO();
//                ejemplar.setEjemplar_id(this.resultSet.getInt("EJEMPLAR_ID"));
//                // Cargar material y biblioteca si es necesario
//                ejemplar.setLocacion_en_biblioteca(this.resultSet.getString("LOCACION_EN_BIBLIOTECA"));
//                ejemplar.setEstado(this.resultSet.getString("ESTADO"));
//                ejemplar.setEliminable(this.resultSet.getBoolean("ELIMINABLE"));
//                ejemplares.add(ejemplar);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                this.cerrarConexion();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return ejemplares;
//    }
//
//    @Override
//    public List<G3_EjemplarDTO> listarPorBiblioteca(Integer idBiblioteca) {
//        List<G3_EjemplarDTO> ejemplares = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM G3_EJEMPLARES WHERE BIBLIOTECA_ID = ?";
//            this.abrirConexion();
//            this.statement = this.conexion.prepareCall(sql);
//            this.statement.setInt(1, idBiblioteca);
//            this.resultSet = this.statement.executeQuery();
//            
//            while (this.resultSet.next()) {
//                G3_EjemplarDTO ejemplar = new G3_EjemplarDTO();
//                ejemplar.setEjemplar_id(this.resultSet.getInt("EJEMPLAR_ID"));
//                // Cargar material y biblioteca si es necesario
//                ejemplar.setLocacion_en_biblioteca(this.resultSet.getString("LOCACION_EN_BIBLIOTECA"));
//                ejemplar.setEstado(this.resultSet.getString("ESTADO"));
//                ejemplar.setEliminable(this.resultSet.getBoolean("ELIMINABLE"));
//                ejemplares.add(ejemplar);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                this.cerrarConexion();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return ejemplares;
//    }
    
    @Override
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) {
        super.envioDeCorreos(origen, destino, asunto, txt, contra16Digitos);
    }
}
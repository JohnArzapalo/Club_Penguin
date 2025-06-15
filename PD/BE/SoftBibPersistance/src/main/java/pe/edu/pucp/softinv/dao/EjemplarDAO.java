package pe.edu.pucp.softinv.dao;

import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public interface EjemplarDAO {

    public Integer insertar(EjemplarDTO ejemplar);

    public EjemplarDTO obtenerPorId(Integer ejemplarId);

    public ArrayList<EjemplarDTO> listarTodos();

    public Integer modificar(EjemplarDTO ejemplar);

    public Integer eliminar(EjemplarDTO ejemplar);
    
    public List<EjemplarDTO> obtenerEjemplaresMaterialPorBiblioteca(int materialId, int bibliotecaId) throws SQLException;
    
    // public ArrayList<EjemplarDTO> listarPorMaterial(Integer materialId);
    
    // public ArrayList<EjemplarDTO> listarPorBiblioteca(Integer bibliotecaId);
    
}


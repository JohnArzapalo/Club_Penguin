package pe.edu.pucp.softinv.dao;

import java.sql.SQLException;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import java.util.ArrayList;
import java.util.List;

public interface BibliotecaDAO {
    
    public Integer insertar(BibliotecaDTO biblioteca);
    
    public BibliotecaDTO obtenerPorId(Integer bibliotecaId);
    
    public ArrayList<BibliotecaDTO> listarTodos();
    
    public Integer modificar(BibliotecaDTO biblioteca);
    
    public Integer eliminar(BibliotecaDTO biblioteca);
    
    public List<BibliotecaDTO> obtenerBibliotecasPorMaterial(int materialId) throws SQLException;
    
    public void envioCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos) ;
}

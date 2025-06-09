package pe.edu.pucp.softinv.dao;

import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;
import java.util.ArrayList;

public interface DiaAtencionDAO {

    public Integer insertar(DiaAtencionDTO diaAtencion);

    public DiaAtencionDTO obtenerPorId(Integer diaAtencionId);

    public ArrayList<DiaAtencionDTO> listarTodos();

    public Integer modificar(DiaAtencionDTO diaAtencion);

    public Integer eliminar(DiaAtencionDTO diaAtencion);
    
    public ArrayList<DiaAtencionDTO> listarPorAtencionBiblioteca(int bibliotecaId);
    
}


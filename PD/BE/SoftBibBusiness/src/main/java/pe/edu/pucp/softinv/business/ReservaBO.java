package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;
import pe.edu.pucp.softinv.dao.ReservaDAO;
import pe.edu.pucp.softinv.daoImpl.ReservaDAOImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;
import pe.edu.pucp.softinv.model.circulacion.EstadoReserva;
import pe.edu.pucp.softinv.dao.DiaAtencionDAO;
import pe.edu.pucp.softinv.daoImpl.DiaAtencionDAOImpl;

public class ReservaBO {
    private final ReservaDAO reservaDAO;
    
    public ReservaBO(){
        this.reservaDAO = new ReservaDAOImpl();
    }
    
    // Insertar reserva
    public Integer insertar(ReservaDTO reserva, Integer bibliotecaId) throws SQLException {
        Date fechaReserva = new Date();
        reserva.setFechaReserva(fechaReserva); // Asigna la fecha actual
        reserva.setEstadoReserva(EstadoReserva.VIGENTE); // Asigna el estado
        Date fechaVencimiento = calcularFechaVencimiento(fechaReserva, bibliotecaId);
        reserva.setFechaVencimiento(fechaVencimiento);
        validar(reserva, false);
        return this.reservaDAO.insertar(reserva);
    }
    
    // Modificar reserva
    public Integer modificar(ReservaDTO reserva) {
        validar(reserva, true);
        return this.reservaDAO.modificar(reserva);
    }
    
    // Eliminar reserva
    public Integer eliminar(Integer id) {
        validar(id);
        ReservaDTO reserva = new ReservaDTO();
        reserva.setReservaId(id);
        return this.reservaDAO.eliminar(reserva);
    }
    
    // Obtener por ID
    public ReservaDTO obtenerPorId(Integer id) {
        validar(id);
        return this.reservaDAO.obtenerPorId(id);
    }
    
    // Listar reservas
    public List<ReservaDTO> listarTodos() throws SQLException {
        return this.reservaDAO.listarTodos();
    }
    
    public Date calcularFechaVencimiento(Date fechaReserva, Integer bibliotecaId) throws SQLException {
        if (fechaReserva == null || bibliotecaId == null)
            throw new IllegalArgumentException("Fecha de reserva y biblioteca deben estar definidos.");

        DiaAtencionDAO diaDAO = new DiaAtencionDAOImpl();
        List<DiaAtencionDTO> diasAtencion = diaDAO.listarPorAtencionBiblioteca(bibliotecaId);

        Set<Integer> diasPermitidos = new HashSet<>();
        for (DiaAtencionDTO dia : diasAtencion) {
            switch (dia.getNombre().toLowerCase()) {
                case "domingo" -> diasPermitidos.add(Calendar.SUNDAY);
                case "lunes" -> diasPermitidos.add(Calendar.MONDAY);
                case "martes" -> diasPermitidos.add(Calendar.TUESDAY);
                case "miércoles", "miercoles" -> diasPermitidos.add(Calendar.WEDNESDAY);
                case "jueves" -> diasPermitidos.add(Calendar.THURSDAY);
                case "viernes" -> diasPermitidos.add(Calendar.FRIDAY);
                case "sábado", "sabado" -> diasPermitidos.add(Calendar.SATURDAY);
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaReserva);

        // Buscar el siguiente día hábil
        while (true) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (diasPermitidos.contains(cal.get(Calendar.DAY_OF_WEEK))) {
                break;
            }
        }

        return new Date(cal.getTimeInMillis());
    }
    
    public List<ReservaDTO> listarReservasVigentesPorUsuario(int usuarioId) throws SQLException{
        return reservaDAO.listarReservasVigentesPorUsuario(usuarioId);
    }
    
    public List<ReservaDTO> buscarReservasAvanzado(
            String reservaId,
            String usuarioNombre,
            String materialTitulo,
            String estado,
            Date fechaDesde,
            Date fechaHasta
    ) throws Exception {
        return reservaDAO.listarPorBusquedaAvanzada(reservaId, usuarioNombre, materialTitulo, estado, fechaDesde, fechaHasta);
    }  
    
    private void validar(Integer id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID de reserva inválido.");
    }

    private void validar(ReservaDTO r, boolean esModificacion) {
        if (esModificacion && (r.getReservaId() == null || r.getReservaId() <= 0))
            throw new IllegalArgumentException("ID de reserva inválido.");

        if (r.getUsuario() == null || r.getUsuario().getUsuarioId() == null)
            throw new IllegalArgumentException("Debe asignarse un usuario válido.");

        if (r.getMaterial() == null || r.getMaterial().getMaterialId() == null)
            throw new IllegalArgumentException("Debe asignarse un material válido.");
    }
}

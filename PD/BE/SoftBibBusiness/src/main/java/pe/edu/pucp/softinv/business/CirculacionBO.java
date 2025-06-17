package pe.edu.pucp.softinv.business;

import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.dao.CirculacionDAO;
import pe.edu.pucp.softinv.daoImpl.CirculacionDAOImpl;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.model.biblioteca.DiaAtencionDTO;
import pe.edu.pucp.softinv.dao.DiaAtencionDAO;
import pe.edu.pucp.softinv.daoImpl.DiaAtencionDAOImpl;

import java.util.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.DEVUELTO_A_TIEMPO;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.DEVUELTO_CON_RETRASO;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.DEVUELTO_DANADO_O_PERDIDO;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.DEVUELTO_RETRASO_Y_DANO_PERDIDA;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.NO_DEVUELTO;
import static pe.edu.pucp.softinv.model.circulacion.EstadoPrestamo.VIGENTE;
import pe.edu.pucp.softinv.model.circulacion.SancionDTO;

public class CirculacionBO {

    private final CirculacionDAO circulacionDAO;
    private final DiaAtencionDAO diaAtencionDAO;

    public CirculacionBO() {
        this.circulacionDAO = new CirculacionDAOImpl();
        this.diaAtencionDAO = new DiaAtencionDAOImpl();
    }
    
    public Integer insertar(CirculacionDTO circulacion) throws SQLException {
        validar(circulacion,false);
        return circulacionDAO.insertar(circulacion);
    }
    
    public Integer modificar(CirculacionDTO circulacion) throws SQLException {
        validar(circulacion,true);
        return circulacionDAO.modificar(circulacion);
    }
    
    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        CirculacionDTO circulacion = new CirculacionDTO();
        circulacion.setCirculacionId(id);
        return circulacionDAO.eliminar(circulacion);
    }
    
    public CirculacionDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return circulacionDAO.obtenerPorId(id);
    }
    
    public List<CirculacionDTO> listarTodas() throws SQLException {
        return circulacionDAO.listarTodos();
    }
    
    public List<CirculacionDTO> listarPrestamosPorUsuario(int usuarioId) throws SQLException {
        return circulacionDAO.listarPrestamosPorUsuario(usuarioId);
    }
    
    private void validar(Integer id){
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID de circulación inválido.");
        }
    }
    
    public List<CirculacionDTO> buscarCirculacionesAvanzado(String idCirculacion,String idReserva,String idUsuario,
            String idEjemplar,String estado,Date fechaDesde,Date fechaHasta) throws SQLException {
        return circulacionDAO.listarPorBusquedaAvanzada(idCirculacion, idReserva, idUsuario, idEjemplar, estado, fechaDesde, fechaHasta);
    }
    
    //CAMBIOS EN ESTA FUNCION 16/06/
    private void validar(CirculacionDTO c, boolean esModificacion) throws SQLException{
        if (esModificacion) {
            if (c.getCirculacionId()== null || c.getCirculacionId() <= 0)
                throw new IllegalArgumentException("ID de circulación inválido.");
        }else{
            c.setEstadoPrestamo(EstadoPrestamo.VIGENTE);
        }
        //nuevo cambio
//        if (c.getReserva() == null || c.getReserva().getReservaId() == null)
//            throw new IllegalArgumentException("Debe asignarse una reserva.");

        if (c.getUsuario() == null || c.getUsuario().getUsuarioId() == null)
            throw new IllegalArgumentException("Debe asignarse un usuario.");

        if (c.getEjemplar() == null || c.getEjemplar().getEjemplarId() == null)
            throw new IllegalArgumentException("Debe asignarse un ejemplar.");
        
        // Calcular fecha de vencimiento
//        if (c.getFechaVencimiento() == null) {
//            c.setFechaVencimiento(calcularFechaVencimiento(c.getFechaPrestamo(), c.getUsuario(), c.getEjemplar()));
//        }
        if (c.getEstadoPrestamo() == null)
            throw new IllegalArgumentException("Debe indicarse el estado del préstamo.");
        
        Calendar calendario = Calendar.getInstance();
        c.setFechaPrestamo(new java.sql.Date(calendario.getTimeInMillis()));
        
//        c.setFechaPrestamo(java.sql.Date.valueOf("2025-06-08"));
        //calculando fecha de vencimiento (provisional porque no hay dias en la bd xdddddddddddddd :,u)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getFechaPrestamo());
        int maxDias = c.getUsuario().getTipoUsuario().getNumeroMaxDias();
        calendar.add(Calendar.DAY_OF_YEAR, maxDias); // Suma los días
        c.setFechaVencimiento(calendar.getTime());
        //---------------------------------------------------------------------------------------------------
        
        // Asignar automáticamente la fecha de devolución
        switch (c.getEstadoPrestamo()) {
            case DEVUELTO_A_TIEMPO,DEVUELTO_CON_RETRASO,
                 DEVUELTO_DANADO_O_PERDIDO,DEVUELTO_RETRASO_Y_DANO_PERDIDA -> {
                if (c.getFechaDevolucion() == null)
                    c.setFechaDevolucion(new Date(System.currentTimeMillis()));
            }
            case VIGENTE,NO_DEVUELTO -> {
                c.setFechaDevolucion(null);
            }
        }
    }
    
    private Date calcularFechaVencimiento(Date fechaPrestamo, UsuarioDTO usuario, EjemplarDTO ejemplar) throws SQLException {
        int maxDias = usuario.getTipoUsuario().getNumeroMaxDias();
        
        List<DiaAtencionDTO> diasAtencion = diaAtencionDAO.listarPorAtencionBiblioteca(ejemplar.getBiblioteca().getBibliotecaId());

        Set<Integer> diasPermitidos = new HashSet<>();
        for (DiaAtencionDTO dia : diasAtencion) {
            String nombre = dia.getNombre().toLowerCase();
            switch (nombre) {
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
        cal.setTime(fechaPrestamo);
        int diasContados = 0;

        while (diasContados < maxDias) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            if (diasPermitidos.contains(cal.get(Calendar.DAY_OF_WEEK))) {
                diasContados++;
            }
        }

        return new Date(cal.getTimeInMillis());
    }
    //nueva funcion!!
    public SancionDTO obtenerSancion(Integer id) throws SQLException {
        SancionBO sancionBO=new SancionBO();
        return sancionBO.obtenerSancionPorCirculacion(id);
    }
}

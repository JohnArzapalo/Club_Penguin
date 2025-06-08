package pe.edu.pucp.softinv.business;
import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import pe.edu.pucp.softinv.dao.SancionDAO;
import pe.edu.pucp.softinv.daoImpl.SancionDAOImpl;
import pe.edu.pucp.softinv.model.circulacion.SancionDTO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softinv.model.usuario.EstadoUsuario;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.UsuarioDAOImpl;

public class SancionBO {

    private final SancionDAO sancionDAO;
    private final UsuarioDAO usuarioDAO;
    
    public SancionBO(){
        this.sancionDAO = new SancionDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    public Integer insertar(SancionDTO sancion) throws SQLException {
        CirculacionDTO circulacion = sancion.getCirculacion();
         // Fecha de registro
        Date fechaRegistro = new Date();
        sancion.setFechaRegistro(fechaRegistro);
        
        // Días de sanción
        int diasSancion = calcularDiasSancion(circulacion);
        sancion.setDiasSancion(diasSancion);
        
        // Fecha de término
        Date fechaTermino = calcularFechaTermino(fechaRegistro, diasSancion);
        sancion.setFechaTermino(fechaTermino);
        
        // Actualizar estado del usuario a SANCIONADO
        UsuarioDTO usuario = circulacion.getUsuario();
        usuario.setEstado(EstadoUsuario.SANCIONADO);
        usuarioDAO.modificar(usuario);
        
        return this.sancionDAO.insertar(sancion);
    }
    
    public Integer modificar(SancionDTO sancion) throws SQLException {
        validar(sancion);
        return this.sancionDAO.modificar(sancion);
    }

    public Integer eliminar(Integer id) throws SQLException {
        validar(id);
        SancionDTO sancion = new SancionDTO();
        sancion.setSancionId(id);
        return this.sancionDAO.eliminar(sancion);
    }
    
    public SancionDTO obtenerPorId(Integer id) throws SQLException {
        validar(id);
        return this.sancionDAO.obtenerPorId(id);
    }
    
    public List<SancionDTO> listarTodas() throws SQLException {
        return sancionDAO.listarTodos();
    }
    
    private int calcularDiasSancion(CirculacionDTO circulacion) {
        return switch (circulacion.getEstadoPrestamo()) {
            case DEVUELTO_CON_RETRASO -> {
                long retrasoMs = circulacion.getFechaDevolucion().getTime() - circulacion.getFechaVencimiento().getTime();
                int dias = (int) (retrasoMs / (1000 * 60 * 60 * 24));
                yield Math.max(dias, 1); // mínimo 1 día
            }
            case DEVUELTO_DANADO_O_PERDIDO -> 15;
            case DEVUELTO_RETRASO_Y_DANO_PERDIDA -> {
                long retrasoMs = circulacion.getFechaDevolucion().getTime() - circulacion.getFechaVencimiento().getTime();
                int dias = (int) (retrasoMs / (1000 * 60 * 60 * 24));
                yield Math.max(dias, 1) + 15;
            }
            default -> throw new IllegalArgumentException("Estado de préstamo no sancionable.");
        };
    }
    
    private Date calcularFechaTermino(Date inicio, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inicio);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return new Date(cal.getTimeInMillis());
    }
   
    private void validar(Integer id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID de sanción inválido.");
    }

    private void validar(SancionDTO s) {
        if (s.getSancionId() == null || s.getSancionId() <= 0)
            throw new IllegalArgumentException("ID de sanción inválido.");
    }
    
    private java.sql.Date convertirFechaSQL(String fechaStr) {
        if (fechaStr == null || fechaStr.isEmpty()) {
            return null;
        }
        try {
            // Primero parseamos a java.util.Date
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            // Luego convertimos a java.sql.Date
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd");
        }
    }
    
    public List<SancionDTO> buscarSancionFlexible(Integer sancionId, 
            Integer circulacionId, String strFechaDesde, String strFechaHasta) throws SQLException {
        // Convertir fechas (con manejo de nulos)
        java.sql.Date fechaDesde = null;
        java.sql.Date fechaHasta = null;

        if (strFechaDesde != null && !strFechaDesde.isEmpty()) {
            fechaDesde = convertirFechaSQL(strFechaDesde);
        }
        if (strFechaHasta != null && !strFechaHasta.isEmpty()) {
            fechaHasta = convertirFechaSQL(strFechaHasta);
        }
        
        // Validar que al menos un filtro esté presente
        if (sancionId == -1 && circulacionId == -1 && fechaDesde == null && fechaHasta == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos un criterio de búsqueda");
        }
        
        List<SancionDTO>listacompleta=sancionDAO.listarTodos();
        List<SancionDTO>listafiltrada=new ArrayList<>();
        for(SancionDTO san: listacompleta){
            int idcir=san.getCirculacion().getCirculacionId();
            if((san.getSancionId()==sancionId || sancionId==-1) && 
                    (idcir==circulacionId || circulacionId==-1)){
                boolean condicionfechadesde=true;
                boolean condicionfechahasta=true;
                if(fechaDesde!=null){
                    if(fechaDesde.getTime()>san.getFechaRegistro().getTime()){
                        condicionfechadesde=false;
                    }
                }
                if(fechaHasta!=null){
                    if(fechaHasta.getTime()<san.getFechaRegistro().getTime()){
                        condicionfechahasta=false;
                    }
                }
                if(condicionfechadesde && condicionfechahasta){
                    listafiltrada.add(san);
                }
            }
        }
        return listafiltrada;
    }    
}

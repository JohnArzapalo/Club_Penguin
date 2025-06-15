package pe.edu.pucp.softinv.dao;

//package proyprog3.softinv.dao;
//import proyprog3.usuario.G3_UsuarioDTO;
//import proyprog3.material.G3_EjemplarDTO;
//import proyprog3.circulacion.G3_CirculacionDTO;
//import proyprog3.circulacion.G3_ReservaDTO;
//import proyprog3.softinv.daoImpl.CirculacionDAOImpl;
//import java.util.Date;
//import java.util.ArrayList;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;

//public class CirculacionDAOTest {
//
//    private CirculacionDAO circulacionDAO;
//
//    public CirculacionDAOTest() {
//        this.circulacionDAO = new CirculacionDAOImpl();
//    }
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
////        eliminarTodo();
//        System.out.println("insertar");
//        ArrayList<Integer> listaIdCirculacion = new ArrayList<>();
//        insertarSanciones(listaIdCirculacion);
////        eliminarTodo();
//    }
//
//    private void insertarSanciones(ArrayList<Integer> listaIdCirculacion) {
//        for (int i = 0; i < 1; i++) {
//            G3_ReservaDTO reserva= new G3_ReservaDTO();
//            reserva.setReservaId(i + 1); // CirculacionId distintos: 1 y 2
//            G3_UsuarioDTO usuario = new G3_UsuarioDTO(){};
//            usuario.setUsuario_id(i + 1); // CirculacionId distintos: 1 y 2
//            G3_EjemplarDTO ejemplar = new G3_EjemplarDTO();
//            ejemplar.setEjemplar_id(i + 1); // CirculacionId distintos: 1 y 2
//
//            G3_CirculacionDTO circulacion = new G3_CirculacionDTO();
//            circulacion.setReserva(reserva);
//            circulacion.setUsuario(usuario);
//            circulacion.setEjemplar(ejemplar);
//            circulacion.setFecha_prestamo(new Date());
//            circulacion.setFecha_vencimiento(new Date());
//            circulacion.setFecha_devolucion(new Date());
//            circulacion.setEstado_devolucion("Vigente" );
//
//            Integer resultado = this.circulacionDAO.insertar(circulacion);
//            assertTrue(resultado != 0);
//            listaIdCirculacion.add(resultado);
//        }
//    }
//
//    @Test
//    @Order(2)
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaIdCirculacion = new ArrayList<>();
//        insertarSanciones(listaIdCirculacion);
//        for (Integer id : listaIdCirculacion) {
//            G3_CirculacionDTO circulacion = this.circulacionDAO.obtenerPorId(id);
//            assertEquals(id, circulacion.getCirculacionId());
//        }
////        eliminarTodo();
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaIdCirculacion = new ArrayList<>();
//        insertarSanciones(listaIdCirculacion);
//
//        ArrayList<G3_CirculacionDTO> lista = this.circulacionDAO.listarTodos();
//        assertTrue(lista.size() >= listaIdCirculacion.size());
//
////        eliminarTodo();
//    }
////
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaIdCirculacion = new ArrayList<>();
//        insertarSanciones(listaIdCirculacion);
//
//        ArrayList<G3_CirculacionDTO> lista = this.circulacionDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            G3_CirculacionDTO circulacion = lista.get(i);
//            circulacion.setEstado_devolucion("Devuelto con retraso");
//            this.circulacionDAO.modificar(circulacion);
//        }
//
//        ArrayList<G3_CirculacionDTO> listaModificada = this.circulacionDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            assertEquals(lista.get(i).getEstado_devolucion(), listaModificada.get(i).getEstado_devolucion());
//        }
//
////        eliminarTodo();
//    }
//
////    @Test
////    @Order(5)
////    public void testEliminar() {
////        System.out.println("eliminar");
////        ArrayList<Integer> listaIdCirculacion = new ArrayList<>();
////        insertarSanciones(listaIdCirculacion);
////        eliminarTodo();
////    }
//
//    private void eliminarTodo() {
//        ArrayList<G3_CirculacionDTO> lista = this.circulacionDAO.listarTodos();
//        for (G3_CirculacionDTO s : lista) {
//            Integer resultado = this.circulacionDAO.eliminar(s);
//            assertNotEquals(0, resultado);
//            assertNull(this.circulacionDAO.obtenerPorId(s.getCirculacionId()));
//        }
//    }
//}

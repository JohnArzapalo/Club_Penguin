package pe.edu.pucp.softinv.dao;
//
//import proyprog3.circulacion.G3_SancionDTO;
//import proyprog3.circulacion.G3_CirculacionDTO;
//import proyprog3.softinv.daoImpl.SancionDAOImpl;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//
//public class SancionDAOTest {
//
//    private SancionDAO sancionDAO;
//
//    public SancionDAOTest() {
//        this.sancionDAO = new SancionDAOImpl();
//    }
//
//    @Test
//    @Order(1)
//    public void testInsertar() {
//        eliminarTodo();
//        System.out.println("insertar");
//        ArrayList<Integer> listaIdSancion = new ArrayList<>();
//        insertarSanciones(listaIdSancion);
//        eliminarTodo();
//    }
//
//    private void insertarSanciones(ArrayList<Integer> listaIdSancion) {
//        for (int i = 0; i < 1; i++) {
//            G3_CirculacionDTO circulacion = new G3_CirculacionDTO();
//            circulacion.setCirculacionId(i + 1); // CirculacionId distintos: 1 y 2
//
//            G3_SancionDTO sancion = new G3_SancionDTO();
//            sancion.setCirculacion(circulacion);
//            sancion.setFecha_registro(new Date());
//            sancion.setFecha_termino(new Date());
//            sancion.setDias_sancion(5 + i);
//            sancion.setObservacion("Observación de prueba " + (i + 1));
//
//            Integer resultado = this.sancionDAO.insertar(sancion);
//            assertTrue(resultado != 0);
//            listaIdSancion.add(resultado);
//        }
//    }
//
//    @Test
//    @Order(2)
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaIdSancion = new ArrayList<>();
//        insertarSanciones(listaIdSancion);
//        for (Integer id : listaIdSancion) {
//            G3_SancionDTO sancion = this.sancionDAO.obtenerPorId(id);
//            assertEquals(id, sancion.getSancion_id());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    @Order(3)
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaIdSancion = new ArrayList<>();
//        insertarSanciones(listaIdSancion);
//
//        ArrayList<G3_SancionDTO> lista = this.sancionDAO.listarTodos();
//        assertTrue(lista.size() >= listaIdSancion.size());
//
//        eliminarTodo();
//    }
//
//    @Test
//    @Order(4)
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaIdSancion = new ArrayList<>();
//        insertarSanciones(listaIdSancion);
//
//        ArrayList<G3_SancionDTO> lista = this.sancionDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            G3_SancionDTO sancion = lista.get(i);
//            sancion.setDias_sancion(sancion.getDias_sancion() + 1);
//            sancion.setObservacion("Modificada obs " + i);
//            this.sancionDAO.modificar(sancion);
//        }
//
//        ArrayList<G3_SancionDTO> listaModificada = this.sancionDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            assertEquals(lista.get(i).getDias_sancion(), listaModificada.get(i).getDias_sancion());
//            assertEquals(lista.get(i).getObservacion(), listaModificada.get(i).getObservacion());
//        }
//
//        eliminarTodo();
//    }
//
//    @Test
//    @Order(5)
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaIdSancion = new ArrayList<>();
//        insertarSanciones(listaIdSancion);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<G3_SancionDTO> lista = this.sancionDAO.listarTodos();
//        for (G3_SancionDTO s : lista) {
//            Integer resultado = this.sancionDAO.eliminar(s);
//            assertNotEquals(0, resultado);
//            assertNull(this.sancionDAO.obtenerPorId(s.getSancion_id()));
//        }
//    }
//}

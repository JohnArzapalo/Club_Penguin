package pe.edu.pucp.softinv.dao;

//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.model.usuario.TipoUsuarioDTO;
//import pe.edu.pucp.persistance.daoImpl.TipoUsuarioDAOImpl;
//
//public class TipoUsuarioDAOTest {
//
//    private TipoUsuarioDAO tipoUsuarioDAO;
//
//    public TipoUsuarioDAOTest() {
//        this.tipoUsuarioDAO = new TipoUsuarioDAOImpl();
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        ArrayList<Integer> listaIds = new ArrayList<>();
//        insertarTiposUsuario(listaIds);
//        eliminarTodo();
//    }
//
//    private void insertarTiposUsuario(ArrayList<Integer> listaIds) {
//        TipoUsuarioDTO tipo = new TipoUsuarioDTO();
//        tipo.setNombre("Estudiante de Pregrado");
//        tipo.setNumeroMaxItems(7);
//        tipo.setNumeroMaxDias(7);
//        Integer resultado = this.tipoUsuarioDAO.insertar(tipo);
//        assertTrue(resultado != 0);
//        listaIds.add(resultado);
//        
//        tipo = new TipoUsuarioDTO();
//        tipo.setNombre("Estudiante de Posgrado");
//        tipo.setNumeroMaxItems(15);
//        tipo.setNumeroMaxDias(15);
//        resultado = this.tipoUsuarioDAO.insertar(tipo);
//        assertTrue(resultado != 0);
//        listaIds.add(resultado);
//
//        tipo = new TipoUsuarioDTO();
//        tipo.setNombre("Docente");
//        tipo.setNumeroMaxItems(15);
//        tipo.setNumeroMaxDias(45);
//        resultado = this.tipoUsuarioDAO.insertar(tipo);
//        assertTrue(resultado != 0);
//        listaIds.add(resultado);
//
//        tipo = new TipoUsuarioDTO();
//        tipo.setNombre("Bibliotecario");
//        tipo.setNumeroMaxItems(0);
//        tipo.setNumeroMaxDias(0);
//        resultado = this.tipoUsuarioDAO.insertar(tipo);
//        assertTrue(resultado != 0);
//        listaIds.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("obtenerPorId");
//        ArrayList<Integer> listaIds = new ArrayList<>();
//        insertarTiposUsuario(listaIds);
//        for (Integer id : listaIds) {
//            TipoUsuarioDTO tipo = this.tipoUsuarioDAO.obtenerPorId(id);
//            assertEquals(id, tipo.getTipoUsuarioId());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("listarTodos");
//        ArrayList<Integer> listaIds = new ArrayList<>();
//        insertarTiposUsuario(listaIds);
//
//        ArrayList<TipoUsuarioDTO> lista = this.tipoUsuarioDAO.listarTodos();
//        assertEquals(listaIds.size(), lista.size());
//        for (Integer i = 0; i < listaIds.size(); i++) {
//            assertEquals(listaIds.get(i), lista.get(i).getTipoUsuarioId());
//        }
//        eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("modificar");
//        ArrayList<Integer> listaIds = new ArrayList<>();
//        insertarTiposUsuario(listaIds);
//
//        ArrayList<TipoUsuarioDTO> lista = this.tipoUsuarioDAO.listarTodos();
//        for (int i = 0; i < lista.size(); i++) {
//            lista.get(i).setNombre("NuevoTipo" + i);
//            lista.get(i).setNumeroMaxItems(lista.get(i).getNumeroMaxItems() + 1);
//            lista.get(i).setNumeroMaxDias(lista.get(i).getNumeroMaxDias() + 2);
//            Integer resultado = this.tipoUsuarioDAO.modificar(lista.get(i));
//            assertNotEquals(0, resultado);
//        }
//
//        ArrayList<TipoUsuarioDTO> listaModificada = this.tipoUsuarioDAO.listarTodos();
//        for (int i = 0; i < listaModificada.size(); i++) {
//            assertEquals("NuevoTipo" + i, listaModificada.get(i).getNombre());
//        }
//
//        eliminarTodo();
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("eliminar");
//        ArrayList<Integer> listaIds = new ArrayList<>();
//        insertarTiposUsuario(listaIds);
//        eliminarTodo();
//    }
//
//    private void eliminarTodo() {
//        ArrayList<TipoUsuarioDTO> lista = this.tipoUsuarioDAO.listarTodos();
//        for (TipoUsuarioDTO tipo : lista) {
//            Integer resultado = this.tipoUsuarioDAO.eliminar(tipo);
//            assertNotEquals(0, resultado);
//            TipoUsuarioDTO eliminado = this.tipoUsuarioDAO.obtenerPorId(tipo.getTipoUsuarioId());
//            assertNull(eliminado);
//        }
//    }
//}

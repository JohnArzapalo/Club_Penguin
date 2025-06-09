package pe.edu.pucp.softinv.dao;

//import org.junit.jupiter.api.*;
//import pe.edu.pucp.model.material.*;
//import pe.edu.pucp.persistance.daoImpl.MaterialDAOImpl;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static pe.edu.pucp.model.material.TipoMaterial.ARTICULO;
//import static pe.edu.pucp.model.material.TipoMaterial.LIBRO;
//import static pe.edu.pucp.model.material.TipoMaterial.TESIS;
//
//public class MaterialDAOTest {
//
//    private MaterialDAO materialDAO;
//
//    @BeforeEach
//    public void setUp() {
//        materialDAO = new MaterialDAOImpl();
//    }
//
//    @Test
//    public void testLibroCRUD() {
////        LibroDTO libro = new LibroDTO();
////        libro.setTitulo("Libro de Prueba");
////        libro.setAutor("Autor Prueba");
////        libro.setAnioPublicacion(2020);
////        libro.setTipoMaterial(TipoMaterial.LIBRO);
////        libro.setNumeroPaginas(150);
////        libro.setTema("Tecnología");
////        libro.setIdioma("Español");
////        libro.setIsbn("1234567890");
////        libro.setEdicion("Primera");
////        libro.setEditorial("Editorial PUCP");
//
////        Integer id = materialDAO.insertar(libro);
////        assertNotNull(id);
////        libro.setMaterialId(id);
////
//        MaterialDTO obtenido = materialDAO.obtenerPorId(1);
//        switch (obtenido) {
//            case LibroDTO libro -> System.out.println(libro);
//            case ArticuloDTO articulo -> System.out.println(articulo);
//            case TesisDTO tesis -> System.out.println(tesis);
//            default -> {
//            }
//        }
////        assertEquals(libro.getTitulo(), obtenido.getTitulo());
//
////        ArrayList<MaterialDTO> todos = materialDAO.listarTodos();
////        for (MaterialDTO material : todos) {
////
////            switch (material) {
////                case LibroDTO libro -> System.out.println(libro);
////                case ArticuloDTO articulo -> System.out.println(articulo);
////                case TesisDTO tesis -> System.out.println(tesis);
////                default -> {
////                }
////            }
////
////        }
//
////        libro.setTitulo("Libro Modificado");
////        Integer modificado = materialDAO.modificar(libro);
////        assertNotEquals(0, modificado);
//
////        Integer eliminado = materialDAO.eliminar(libro);
////        assertNotEquals(0, eliminado);
////
////        MaterialDTO eliminadoObtenido = materialDAO.obtenerPorId(id);
////        assertNull(eliminadoObtenido);
//    }
//
//    @Test
//    public void testArticuloCRUD() {
//        ArticuloDTO articulo = new ArticuloDTO();
//        articulo.setTitulo("Artículo Científico");
//        articulo.setAutor("Dr. Investigador");
//        articulo.setAnioPublicacion(2021);
//        articulo.setTipoMaterial(TipoMaterial.ARTICULO);
//        articulo.setNumeroPaginas(10);
//        articulo.setTema("Salud");
//        articulo.setIdioma("Inglés");
//        articulo.setIssn("9876-5432");
//        articulo.setNombreRevista("Revista de Medicina");
//        articulo.setVolumen("Vol. 10");
//        articulo.setNumero(2);
//        articulo.setEditorial("Editorial Médica");
//
//        Integer id = materialDAO.insertar(articulo);
//        assertNotNull(id);
//        articulo.setMaterialId(id);
//
//        MaterialDTO obtenido = materialDAO.obtenerPorId(id);
//        assertEquals(articulo.getTitulo(), obtenido.getTitulo());
//
//        articulo.setTitulo("Artículo Actualizado");
//        assertNotEquals(0, materialDAO.modificar(articulo));
//
//        assertNotEquals(0, materialDAO.eliminar(articulo));
//        assertNull(materialDAO.obtenerPorId(id));
//    }
//
//    @Test
//    public void testTesisCRUD() {
//        TesisDTO tesis = new TesisDTO();
//        tesis.setTitulo("Tesis de Prueba");
//        tesis.setAutor("Tesista");
//        tesis.setAnioPublicacion(2022);
//        tesis.setTipoMaterial(TipoMaterial.TESIS);
//        tesis.setNumeroPaginas(200);
//        tesis.setTema("Ingeniería");
//        tesis.setIdioma("Español");
//        tesis.setNombreInstitucionPublicacion("PUCP");
//        tesis.setAsesorTesis("Dr. Asesor");
//        tesis.setEspecialidad("Electrónica");
//        tesis.setGrado(Grado.POSGRADO);
//
//        Integer id = materialDAO.insertar(tesis);
//        assertNotNull(id);
//        tesis.setMaterialId(id);
//
//        MaterialDTO obtenido = materialDAO.obtenerPorId(id);
//        assertEquals(tesis.getTitulo(), obtenido.getTitulo());
//
//        tesis.setTitulo("Tesis Corregida");
//        assertNotEquals(0, materialDAO.modificar(tesis));
//
//        assertNotEquals(0, materialDAO.eliminar(tesis));
//        assertNull(materialDAO.obtenerPorId(id));
//    }
//
//    @Test
//    public void testObtenerCopias() {
//        // Primero asegúrate de tener al menos un MATERIAL_ID válido en G3_EJEMPLARES
//        int materialId = 1; // cambia esto por un ID válido para tu BD de prueba
//        int[] copias = materialDAO.obtenerCopias(materialId);
//        assertNotNull(copias);
//        assertEquals(2, copias.length);
//        assertTrue(copias[0] >= copias[1]); // total >= disponibles
//    }
//}
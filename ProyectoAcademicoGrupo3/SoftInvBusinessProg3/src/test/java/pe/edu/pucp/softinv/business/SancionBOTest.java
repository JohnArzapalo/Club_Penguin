///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
//
///**
// *
// * @author letic
// */
//import java.sql.Date;
//import java.util.ArrayList;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import proyprog3.circulacion.G3_SancionDTO;
//import com.mycompany.softinvbusinessprog3.SancionBO;
//
//
///**
// *
// * @author johnm
// */
//public class SancionBOTest {
//    private SancionBO sancionBO;
//    
//    public SancionBOTest() {
//        this.sancionBO = new SancionBO();
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("insertar");
//        Integer resultado = this.sancionBO.insertar(42, Date.valueOf("2025-05-11"), Date.valueOf("2025-05-16"),10, "XDDD");
//        assertTrue(resultado>0);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
//    @Test
//    public void testModificar() {
//        System.out.println("Modificar");
//        Integer resultado = this.sancionBO.Modificar(96, 1, Date.valueOf("2025-05-11"), Date.valueOf("2025-05-16"),10, "HOLA");
//        assertTrue(resultado>0);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
//    
//        /*[TestMethod]
//        public void TestEliminar()
//        {
//            Console.WriteLine("Eliminar");
//
//            // Primero, insertamos un almacén para eliminarlo
//            int resultado = this.almacenBO.Insertar("Almacén a eliminar", false);
//            Assert.IsTrue(resultado > 0);
//
//            // Eliminamos el almacén (suponiendo que el ID es 1 para el ejemplo)
//            resultado = this.almacenBO.Eliminar(1);
//            Assert.IsTrue(resultado > 0);
//
//            // Intentamos obtener el almacén por ID, debería ser null o lanzar una excepción dependiendo de la implementación
//            var almacenesDTO = this.almacenBO.ObtenerPorId(1);
//            Assert.IsNull(almacenesDTO);
//        }*/
//    @Test
//    public void TestEliminar() {
//        System.out.println("Eliminar");
//        Integer resultado = this.sancionBO.Eliminar(98);
//        assertTrue(resultado>0);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
//    /*
//        public void TestObtenerPorId()
//        {
//            Console.WriteLine("Obtener por ID");
//
//            // Primero, insertamos un almacén para obtenerlo por ID
//            int resultado = this.almacenBO.Insertar("Almacén a obtener", false);
//            Assert.IsTrue(resultado > 0);
//
//            // Obtenemos el almacén (suponiendo que el ID es 1 para el ejemplo)
//            AlmacenesDTO almacenesDTO = this.almacenBO.ObtenerPorId(1);
//            Assert.IsNotNull(almacenesDTO);
//            Assert.AreEqual("Almacén a obtener", almacenesDTO.Nombre);
//            Assert.IsFalse(almacenesDTO.AlmacenCentral);
//        }
//    */
//    @Test
//    public void TestObtenerPorId() {
//        System.out.println("Obtener por ID");
//        G3_SancionDTO sancionDTO = sancionBO.ObtenerPorId(96);
//        assertNotNull(sancionDTO);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
//    @Test
//    public void TestListarTodos() {
//        System.out.println("Listar todos");
//        ArrayList<G3_SancionDTO> sancionDTO = sancionBO.ListarTodos();
//        assertNotNull(sancionDTO);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
//    
//}
//

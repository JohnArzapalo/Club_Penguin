///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softinv.business.CirculacionBO;
import pe.edu.pucp.softinv.dao.EjemplarDAO;
import pe.edu.pucp.softinv.dao.ReservaDAO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.EjemplarDAOImpl;
import pe.edu.pucp.softinv.daoImpl.ReservaDAOImpl;
import pe.edu.pucp.softinv.daoImpl.UsuarioDAOImpl;
import pe.edu.pucp.softinv.model.circulacion.CirculacionDTO;
import pe.edu.pucp.softinv.model.circulacion.ReservaDTO;
import pe.edu.pucp.softinv.model.material.EjemplarDTO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
///**
// *
// * @author johnm
// */
public class CirculacionBOTest {
    private CirculacionBO circulacionBO;
    
    public CirculacionBOTest() {
        this.circulacionBO = new CirculacionBO();
    }

    @Test
    public void testInsertar() throws SQLException {
        System.out.println("insertar");
        CirculacionDTO cir=new CirculacionDTO();
        UsuarioDTO usu=new UsuarioDTO();
        EjemplarDTO ejem=new EjemplarDTO();
 
        UsuarioDAO usuarioDAO=new UsuarioDAOImpl();
        usu=usuarioDAO.obtenerPorId(1);
        
        EjemplarDAO ejemplarDAO=new EjemplarDAOImpl();
        ejem=ejemplarDAO.obtenerPorId(4);
        
//        ReservaDAO reservaDAO=new ReservaDAOImpl();
//        ReservaDTO res=reservaDAO.obtenerPorId(1);
        
        cir.setEjemplar(ejem);
        cir.setUsuario(usu);
//        cir.setReserva(res);
//        cir.setFechaPrestamo(Date.valueOf("2025-05-18"));
        
        System.out.println("Fecha del prestamo: "+cir.getFechaPrestamo());
        
        Integer resultado = this.circulacionBO.insertar(cir);
        if(resultado>0){
            System.out.println("Se logró");
        }else{
            System.out.println("Nada");
        }
        
    }
//    @Test
//    public void testModificar() {
//        System.out.println("Modificar");
//        Integer resultado = this.circulacionBO.Modificar(45, 1,                              // reservaId
//        1,                               // usuarioId (por ejemplo)
//        1,                              // ejemplarId (por ejemplo)
//        Date.valueOf("2025-05-11"),      // fecha_prestamo (YYYY-MM-DD)
//        Date.valueOf("2025-05-16"),      // fecha_vencimiento
//        Date.valueOf("2025-05-18"),      // fecha_devolucion
//        "Devuelto con retraso"           // estado_devolucion
//        );
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
//        Integer resultado = this.circulacionBO.Eliminar(49);
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
//        G3_CirculacionDTO circulacionDTO = circulacionBO.ObtenerPorId(48);
//        assertNotNull(circulacionDTO);
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
//        ArrayList<G3_CirculacionDTO> circulacionDTO = circulacionBO.ListarTodos();
//        assertNotNull(circulacionDTO);
//        
////        resultado = this.sancionBO.insertar("Mi segundo almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
////        
////        resultado = this.sancionBO.insertar("Mi tercer almacen", Boolean.FALSE);
////        assertTrue(resultado>0);
//    
//    }
}
//

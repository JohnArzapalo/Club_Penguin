package pe.edu.pucp.softinv.dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.UsuarioDAOImpl;

public class UsuarioDAOTest {
    
    private UsuarioDAO usuarioDAO;

    public UsuarioDAOTest() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }
    
    @Test
    public void testBuscarPorCorreo_existente() {
        try {
            String correo = "ariana.burga@pucp.edu.pe"; // ⚠️ Usa un correo real en tu base de datos
            UsuarioDTO usuario = usuarioDAO.buscarPorCorreo(correo);
            System.out.println(usuario.getCorreoElectronico());
            assertNotNull(usuario, "El usuario no debe ser null para un correo existente.");
            assertEquals(correo, usuario.getCorreoElectronico(), "El correo debe coincidir con el buscado.");

            // Puedes agregar más validaciones si sabes el tipo esperado:
            // assertTrue(usuario instanceof EstudianteDTO);
            // assertEquals("Nombre esperado", usuario.getNombres());

        } catch (Exception e) {
            fail("Error inesperado durante la prueba: " + e.getMessage());
        }
    }

    @Test
    public void testBuscarPorCorreo_inexistente() {
        try {
            String correo = "correo.inexistente@pucp.edu.pe";
            UsuarioDTO usuario = usuarioDAO.buscarPorCorreo(correo);

            assertNull(usuario, "Debe retornar null si el correo no está registrado.");

        } catch (Exception e) {
            fail("Error inesperado durante la prueba: " + e.getMessage());
        }
    }
    
//    @Test
//    public void envioCorreo() {
//        try {
//            String origen= "sistema.de.bibliotecas.prog3@gmail.com";
//            String destino= "a20202098@pucp.edu.pe";
//            String asunto=" Test correo";
//            String txt= "Prueba";
//            String contra16Digitos= "xmlm dvks ugkb cggq";
//            usuarioDAO.envioCorreos(origen, destino, asunto, txt, contra16Digitos);
//        } catch (Exception e) {
//            fail("Error inesperado durante la prueba: " + e.getMessage());
//        }
//    }

}






package pe.edu.pucp.softinv.business;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softinv.business.UsuarioBO;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;

public class UsuarioBOTest {

    @Test
    public void testValidarUsuario_valido() {
        try {
            UsuarioBO usuarioBO = new UsuarioBO();
            String correo = "ariana.burga@pucp.edu.pe";     // ⚠️ Usa un correo real
            String contrasena = "ariana123";                     // ⚠️ Usa la contraseña real en DB

            UsuarioDTO usuario = usuarioBO.validarUsuario(correo, contrasena);
            System.out.println(usuario.getNombres());
            assertNotNull(usuario, "Debe retornar un usuario válido si el correo y contraseña son correctos.");
            assertEquals(correo, usuario.getCorreoElectronico());

        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    public void testValidarUsuario_contrasenaIncorrecta() {
        try {
            UsuarioBO usuarioBO = new UsuarioBO();
            String correo = "ariana.burga@pucp.edu.pe";
            String contrasena = "clave_incorrecta";

            UsuarioDTO usuario = usuarioBO.validarUsuario(correo, contrasena);
            assertNull(usuario, "Debe retornar null si la contraseña es incorrecta.");

        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }

    @Test
    public void testValidarUsuario_correoInexistente() {
        try {
            UsuarioBO usuarioBO = new UsuarioBO();
            String correo = "noexiste@pucp.edu.pe";
            String contrasena = "cualquier";

            UsuarioDTO usuario = usuarioBO.validarUsuario(correo, contrasena);
            assertNull(usuario, "Debe retornar null si el correo no existe.");

        } catch (Exception e) {
            fail("Error inesperado: " + e.getMessage());
        }
    }
}

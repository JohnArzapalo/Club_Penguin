package pe.edu.pucp.softinv.business.password.util;
import pe.edu.pucp.softinv.business.UsuarioBO; 
import pe.edu.pucp.softinv.dao.UsuarioDAO;
import pe.edu.pucp.softinv.daoImpl.UsuarioDAOImpl;
import pe.edu.pucp.softinv.model.usuario.UsuarioDTO;

import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softinv.business.BibliotecaBO;
import pe.edu.pucp.softinv.business.TipoUsuarioBO;
import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;
import pe.edu.pucp.softinv.model.usuario.BibliotecarioDTO;
import pe.edu.pucp.softinv.model.usuario.EstadoUsuario;
import pe.edu.pucp.softinv.model.usuario.TipoDocumento;
import pe.edu.pucp.softinv.model.usuario.TipoUsuarioDTO;

public class PasswordMigrationTool {

    public static void main(String[] args) {
//        UsuarioBO usuarioBO = new UsuarioBO();
//        try {
//            TipoUsuarioBO tpbo=new TipoUsuarioBO();
//            BibliotecaBO bbo=new BibliotecaBO();
//            
//            
//            TipoUsuarioDTO tipoBibliotecario = tpbo.obtenerPorId(4);
//
//
//
//            BibliotecaDTO biblioteca = bbo.obtenerPorId(1);
//
//
//            // 3. Crear el BibliotecarioDTO
//            String passwordPlano = "infinitywar13"; 
//            UsuarioDTO nuevoBibliotecario = new BibliotecarioDTO(
//                biblioteca, // Parámetro específico de BibliotecarioDTO
//                null, // usuarioId (null para inserción, se autogenera en BD)
//                tipoBibliotecario, 
//                "Richard", // nombres
//                "Larold", // primerApellido
//                "Valentine", // segundoApellido
//                TipoDocumento.DNI, 
//                "70000000", // numeroDocumento
//                EstadoUsuario.ACTIVO, 
//                "BIBLIOTE001", // codigoUniversitario
//                "richard.admin@pucp.edu.pe", // correoElectronico
//                passwordPlano // contraseña (en texto plano)
//            );
//
//
//            Integer idInsertado = usuarioBO.insertar(nuevoBibliotecario);
//            System.out.println("Usuario bibliotecario insertado con ID: " + idInsertado);
//
//            // --- Ahora, intenta validar el usuario recién insertado ---
//            System.out.println("\nIntentando validar el usuario recién insertado...");
//            UsuarioDTO usuarioValidado = usuarioBO.validarUsuario(nuevoBibliotecario.getCorreoElectronico(), passwordPlano);
//
//            if (usuarioValidado != null) {
//                System.out.println("¡Validación exitosa!");
//                System.out.println("Usuario validado: " + usuarioValidado.getNombres() + " " + usuarioValidado.getPrimerApellido());
//                System.out.println("Correo: " + usuarioValidado.getCorreoElectronico());
//            } else {
//                System.err.println("¡Error en la validación! El usuario no pudo ser autenticado.");
//            }
//
//        } catch (IllegalArgumentException e) {
//            System.err.println("Error de validación al insertar/validar usuario: " + e.getMessage());
//        } catch (SQLException e) {
//            System.err.println("Error de base de datos: " + e.getMessage());
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
//            e.printStackTrace();
//        }        
//        
    }
}
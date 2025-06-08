package pe.edu.pucp.softinv.model.usuario;

import pe.edu.pucp.softinv.model.biblioteca.BibliotecaDTO;

public class BibliotecarioDTO extends UsuarioDTO {
    private BibliotecaDTO biblioteca;

    public BibliotecarioDTO() {
        super();
        this.biblioteca = null;
    }
    
    public BibliotecarioDTO(BibliotecaDTO biblioteca,
                            Integer usuarioId, TipoUsuarioDTO tipoUsuario, String nombres, String primerApellido, String segundoApellido,
                            TipoDocumento tipoDocumento, String numeroDocumento, EstadoUsuario estado,
                            String codigoUniversitario, String correoElectronico, String password) {
        super(usuarioId, tipoUsuario, nombres, primerApellido, segundoApellido,
              tipoDocumento, numeroDocumento, estado, codigoUniversitario, correoElectronico, password);
        this.biblioteca = biblioteca;
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
               ",\n  Tipo = 'BIBLIOTECARIO'" +
               ",\n  Biblioteca = " + biblioteca.getNombre()+
               "\n}";
    }
    
    public BibliotecaDTO getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(BibliotecaDTO biblioteca) {
        this.biblioteca = biblioteca;
    }
}

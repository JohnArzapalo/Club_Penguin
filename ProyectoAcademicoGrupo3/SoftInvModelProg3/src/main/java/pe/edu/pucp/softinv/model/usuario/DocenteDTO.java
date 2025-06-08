package pe.edu.pucp.softinv.model.usuario;

public class DocenteDTO extends UsuarioDTO {
    private String departamentoAcademico;
    
    public DocenteDTO() {
        super();
        this.departamentoAcademico = null;
    }
    
    public DocenteDTO(String departamentoAcademico,
                      Integer usuarioId, TipoUsuarioDTO tipoUsuario, String nombres, String primerApellido, String segundoApellido,
                      TipoDocumento tipoDocumento, String numeroDocumento, EstadoUsuario estado,
                      String codigoUniversitario, String correoElectronico, String password) {
        super(usuarioId, tipoUsuario, nombres, primerApellido, segundoApellido,
              tipoDocumento, numeroDocumento, estado, codigoUniversitario, correoElectronico, password);
        this.departamentoAcademico = departamentoAcademico;
    }
    
    public DocenteDTO(DocenteDTO otro) {
        super(otro);
        this.departamentoAcademico = otro.departamentoAcademico;
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
               ",\n  Tipo = 'DOCENTE'" +
               ",\n  Departamento Académico = '" + departamentoAcademico + '\'' +
               "\n}";
    }

    public String getDepartamentoAcademico() {
        return departamentoAcademico;
    }

    public void setDepartamentoAcademico(String departamentoAcademico) {
        this.departamentoAcademico = departamentoAcademico;
    }
}

package pe.edu.pucp.softinv.model.usuario;

public class EstudianteDTO extends UsuarioDTO {
    private String especialidad;

    public EstudianteDTO() {
        super();
        this.especialidad = null;
    }

    public EstudianteDTO(String especialidad,
                         Integer usuarioId, TipoUsuarioDTO tipoUsuario, String nombres, String primerApellido,
                         String segundoApellido, TipoDocumento tipoDocumento, String numeroDocumento,
                         EstadoUsuario estado, String codigoUniversitario, String correoElectronico, String password) {
        super(usuarioId, tipoUsuario, nombres, primerApellido, segundoApellido,
              tipoDocumento, numeroDocumento, estado, codigoUniversitario, correoElectronico, password);
        this.especialidad = especialidad;
    }

    public EstudianteDTO(EstudianteDTO otro) {
        super(otro);
        this.especialidad = otro.especialidad;
    }
    
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
               ",\n  Tipo = 'ESTUDIANTE'" +
               ",\n  Especialidad = '" + especialidad + '\'' +
               "\n}";
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}

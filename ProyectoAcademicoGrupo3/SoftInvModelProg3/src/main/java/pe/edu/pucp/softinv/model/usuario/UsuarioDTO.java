package pe.edu.pucp.softinv.model.usuario;

public class UsuarioDTO {
    private Integer usuarioId;
    private TipoUsuarioDTO tipoUsuario;
    private String codigoUniversitario;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String correoElectronico;
    private String password;
    private EstadoUsuario estado;
    
    public UsuarioDTO() {
        this.usuarioId = null;
        this.tipoUsuario = null;
        this.codigoUniversitario = null;
        this.nombres = null;
        this.primerApellido = null;
        this.segundoApellido = null;
        this.tipoDocumento = null;
        this.numeroDocumento = null;
        this.correoElectronico = null;
        this.password = null;
        this.estado = null;
    }
    
    public UsuarioDTO(Integer usuarioId, TipoUsuarioDTO tipoUsuario, String nombres, String primerApellido, String segundoApellido,
                      TipoDocumento tipoDocumento, String numeroDocumento, EstadoUsuario estado,
                      String codigoUniversitario, String correoElectronico, String password) {
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.estado = estado;
        this.codigoUniversitario = codigoUniversitario;
        this.correoElectronico = correoElectronico;
        this.password = password;
    }

    public UsuarioDTO(UsuarioDTO otro) {
        this.usuarioId = otro.usuarioId;
        this.tipoUsuario = otro.tipoUsuario;
        this.nombres = otro.nombres;
        this.primerApellido = otro.primerApellido;
        this.segundoApellido = otro.segundoApellido;
        this.tipoDocumento = otro.tipoDocumento;
        this.numeroDocumento = otro.numeroDocumento;
        this.estado = otro.estado;
        this.codigoUniversitario = otro.codigoUniversitario;
        this.correoElectronico = otro.correoElectronico;
        this.password = otro.password;
    }
    
    @Override
    public String toString() {
        return "Usuario {" +
               "\n  ID = " + usuarioId +
               ",\n  Tipo Usuario = " + tipoUsuario.getNombre() +
               ",\n  Código Universitario = '" + codigoUniversitario + '\'' +
               ",\n  Nombres = '" + nombres + '\'' +
               ",\n  Apellidos = '" + primerApellido + " " + segundoApellido + '\'' +
               ",\n  Documento = '" + tipoDocumento.getNombreMostrar() + numeroDocumento + '\'' +
               ",\n  Correo = '" + correoElectronico + '\'' +
               ",\n  Estado = '" + estado.getNombreMostrar() + '\'' +
               "\n}";
    }
    
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public TipoUsuarioDTO getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.servicios;
namespace SoftInvWAProg3
{
    public partial class cuenta : System.Web.UI.Page
    {
        private UsuarioWSClient usuarioWSClient= new UsuarioWSClient();
        
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string rol = Session["rol"] as string;
                int? id = Session["id"] as int?;
                usuarioDTO usuario = usuarioWSClient.obtenerUsuarioPorId((int)id);
                
                //GENERALES
                litCodigoUsuario.Text=usuario.usuarioId.ToString();
                litCodUniv.Text = usuario.codigoUniversitario.ToString();
                litTipoUsuario.Text = rol;
                litNombres.Text = usuario.nombres;
                litPrimerA.Text=usuario.primerApellido;
                litSegundoA.Text = usuario.segundoApellido;
                litDNI.Text = usuario.numeroDocumento.ToString();
                litCorreo.Text = usuario.correoElectronico;
                litEstado.Text = usuario.estado.ToString();

                //si es bibiliotecario
                if (!string.IsNullOrEmpty(rol) && rol.Equals("Bibliotecario", StringComparison.OrdinalIgnoreCase))
                {
                    phPersoBiblioteca.Visible = true;
                    bibliotecarioDTO bibliotecario = usuario as bibliotecarioDTO;
                    litBiblio.Text = bibliotecario?.biblioteca?.nombre?? "";

                }
                //si es estudiante
                if (!string.IsNullOrEmpty(rol) && (rol.Equals("Estudiante de Pregrado", StringComparison.OrdinalIgnoreCase) ||
                    rol.Equals("Estudiante de Posgrado", StringComparison.OrdinalIgnoreCase)))
                {
                    phEstudianteEspe.Visible = true;
                    phEstuDoc.Visible = true;
                    estudianteDTO estudiante = usuario as estudianteDTO;
                    litCantMax.Text = estudiante?.tipoUsuario?.numeroMaxItems.ToString() ?? "0";
                    litDurMax.Text = estudiante?.tipoUsuario?.numeroMaxDias.ToString() ?? "0";
                    litEspe.Text = estudiante?.especialidad?? "";

                }
                //si es docente
                if (!string.IsNullOrEmpty(rol) && rol.Equals("Docente", StringComparison.OrdinalIgnoreCase))
                {
                    phDocenteDepa.Visible = true;
                    phEstuDoc.Visible = true;
                    docenteDTO docente = usuario as docenteDTO;
                    litCantMax.Text = docente?.tipoUsuario?.numeroMaxItems.ToString() ?? "0";
                    litDurMax.Text = docente?.tipoUsuario?.numeroMaxDias.ToString() ?? "0";
                    litDepaAc.Text = docente?.departamentoAcademico?? "";
                }
            }
        }
    }
}
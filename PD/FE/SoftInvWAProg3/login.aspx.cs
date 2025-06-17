using System;
using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class login : System.Web.UI.Page
    {
        private UsuarioWSClient cliente;

        protected void Page_Load(object sender, EventArgs e)
        {
            cliente = new UsuarioWSClient();
        }

        protected void btnIngresar_Click(object sender, EventArgs e)
        {
            string correo = txtCorreo.Text.Trim();
            string contrasena = txtContrasena.Text.Trim();

            usuarioDTO usuario = cliente.validarUsuario(correo, contrasena);

            if (usuario != null)
            {
                Session["usuario"] = usuario;
                Session["id"] = (int?)usuario.usuarioId;
                Session["rol"] = usuario.tipoUsuario.nombre;
                Session["correo"] = correo;
                    
                Response.Redirect("materiales.aspx");
            }
            else
            {
                lblMensaje.Text = "Correo o contraseña incorrectos.";
                lblMensaje.Visible = true;
            }
        }
    }
}

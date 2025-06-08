using System;
using System.Web.UI.WebControls;
using System.Web.UI;
using SoftInvWAProg3.UsuarioWS;

namespace SoftInvWAProg3
{
    public partial class SoftInv : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["usuario"] == null)
            {
                Response.Redirect("login.aspx");
                return;
            }

            usuarioDTO usuario = (usuarioDTO)Session["usuario"];
            string tipo = usuario.tipoUsuario.nombre;

            if (tipo == "Bibliotecario")
            {
                pnlBibliotecario.Visible = true;
                pnlGeneral.Visible = false;
            }
            else
            {
                pnlBibliotecario.Visible = false;
                pnlGeneral.Visible = true;
            }

            string currentUrl = Request.Url.AbsolutePath.ToLower();

            foreach (Control ctrl in pnlBibliotecario.Controls)
            {
                if (ctrl is HyperLink link && currentUrl.Contains(link.NavigateUrl.ToLower()))
                    link.CssClass += " active";
            }

            foreach (Control ctrl in pnlGeneral.Controls)
            {
                if (ctrl is HyperLink link && currentUrl.Contains(link.NavigateUrl.ToLower()))
                    link.CssClass += " active";
            }
        }
        protected void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            Session.Clear();      // Limpia todas las variables de sesión
            Session.Abandon();    // Finaliza la sesión actual

            Response.Redirect("login.aspx");  // Redirige al login
        }
    }
}

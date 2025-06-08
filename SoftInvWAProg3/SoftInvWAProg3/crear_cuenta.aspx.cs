using System;
using System.Web.UI;

namespace SoftInvWAProg3
{
    public partial class crear_cuenta : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            // Si es postback no cambiar visibilidad para no sobreescribir selección del usuario
            if (!IsPostBack)
            {
                // Opcional: Ocultar todos los paneles al cargar
                pnlEstudiante.Visible = false;
                pnlDocente.Visible = false;
                pnlBibliotecario.Visible = false;
            }
        }

        protected void ddlTipoUsuario_SelectedIndexChanged(object sender, EventArgs e)
        {
            pnlEstudiante.Visible = ddlTipoUsuario.SelectedValue == "Estudiante";
            pnlDocente.Visible = ddlTipoUsuario.SelectedValue == "Docente";
            pnlBibliotecario.Visible = ddlTipoUsuario.SelectedValue == "Personal Bibliotecario";
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Response.Redirect("cuentas.aspx");
        }

        protected void btnCrear_Click(object sender, EventArgs e)
        {
            // Aquí puedes agregar la lógica para crear el usuario según campos llenados y tipo
        }
    }
}

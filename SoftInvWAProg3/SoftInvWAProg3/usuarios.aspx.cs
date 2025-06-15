using System;
using System.Linq;
using System.Web.UI;
using SoftInvWAProg3.UsuarioWS; // Ajusta namespace a tu proyecto

namespace SoftInvWAProg3
{
    public partial class cuentas : Page
    {
        private UsuarioWSClient wsCliente;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                wsCliente = new UsuarioWSClient();
                gvCuentas.DataSource = null;
                gvCuentas.DataBind();
            }
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtUsuario.Text = "";
            ddlTipoUsuario.SelectedIndex = 0;
            txtCodigo.Text = "";
            txtNombres.Text = "";
            txtApellido.Text = "";
            ddlEstado.SelectedIndex = 0;

            gvCuentas.DataSource = null;
            gvCuentas.DataBind();
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarCuentas();
        }

        protected void gvCuentas_PageIndexChanging(object sender, System.Web.UI.WebControls.GridViewPageEventArgs e)
        {
            gvCuentas.PageIndex = e.NewPageIndex;
            CargarCuentas();
        }

        protected void btnCrearCuenta_Click(object sender, EventArgs e)
        {
            Response.Redirect("crear_cuenta.aspx");
        }

        private void CargarCuentas()
        {
            try
            {
                if (wsCliente == null)
                    wsCliente = new UsuarioWSClient();

                string idUsuario = string.IsNullOrWhiteSpace(txtUsuario.Text) ? null : txtUsuario.Text.Trim();
                string tipoUsuario = string.IsNullOrWhiteSpace(ddlTipoUsuario.SelectedValue) ? null : ddlTipoUsuario.SelectedValue;
                string codigoUniversitario = string.IsNullOrWhiteSpace(txtCodigo.Text) ? null : txtCodigo.Text.Trim();
                string nombres = string.IsNullOrWhiteSpace(txtNombres.Text) ? null : txtNombres.Text.Trim();
                string primerApellido = string.IsNullOrWhiteSpace(txtApellido.Text) ? null : txtApellido.Text.Trim();
                string estado = string.IsNullOrWhiteSpace(ddlEstado.SelectedValue) ? null : ddlEstado.SelectedValue;

                var listaCompleja = wsCliente.buscarUsuariosAvanzado(idUsuario, tipoUsuario, codigoUniversitario, nombres, primerApellido, estado);

                var listaPlana = listaCompleja.Select(u => new
                {
                    usuarioId = u.usuarioId,
                    tipoUsuario = u.tipoUsuario, // Mantén objeto para TemplateField
                    codigoUniversitario = u.codigoUniversitario,
                    nombres = u.nombres,
                    primerApellido = u.primerApellido,
                    segundoApellido = u.segundoApellido,
                    tipoDocumento = u.tipoDocumento,
                    numeroDocumento = u.numeroDocumento,
                    correoElectronico = u.correoElectronico,
                    estado = u.estado
                }).ToList();

                gvCuentas.DataSource = listaPlana;
                gvCuentas.DataBind();
            }
            catch (Exception ex)
            {
                gvCuentas.DataSource = null;
                gvCuentas.DataBind();

                // Puedes agregar aquí un log o mensaje de error si quieres
            }
        }
    }
}

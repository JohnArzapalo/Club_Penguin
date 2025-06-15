using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.CirculacionWS; // Asegúrate que sea el namespace correcto

namespace SoftInvWAProg3
{
    public partial class circulaciones : System.Web.UI.Page
    {
        // Cliente del servicio web como campo para reutilizar
        private CirculacionWSClient wsCliente;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                wsCliente = new CirculacionWSClient();

                // Limpia la grilla para que no muestre datos al cargar la página
                gvCirculaciones.DataSource = null;
                gvCirculaciones.DataBind();
            }
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtCirculacion.Text = "";
            txtReserva.Text = "";
            txtUsuario.Text = "";
            txtEjemplar.Text = "";
            txtFechaDesde.Text = "";
            txtFechaHasta.Text = "";
            ddlEstado.SelectedIndex = 0;

            gvCirculaciones.DataSource = null;
            gvCirculaciones.DataBind();
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            CargarCirculaciones();
        }

        protected void gvCirculaciones_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvCirculaciones.PageIndex = e.NewPageIndex;
            CargarCirculaciones();
        }

        protected void btnRegistrarPrestamo_Click(object sender, EventArgs e)
        {
            Response.Redirect("registrar_prestamo.aspx");
        }

        // Método que carga la lista real desde el servicio web
        private void CargarCirculaciones()
        {
            try
            {
                if (wsCliente == null)
                    wsCliente = new CirculacionWSClient();

                // Obtener filtros desde los controles
                string idCirculacion = txtCirculacion.Text.Trim();
                string idReserva = txtReserva.Text.Trim();
                string idUsuario = txtUsuario.Text.Trim();
                string idEjemplar = txtEjemplar.Text.Trim();
                string estado = ddlEstado.SelectedValue;

                // Formatear fechas en yyyy-MM-dd o pasar null si están vacías
                string fechaDesde = string.IsNullOrEmpty(txtFechaDesde.Text) ? null : DateTime.Parse(txtFechaDesde.Text).ToString("yyyy-MM-dd");
                string fechaHasta = string.IsNullOrEmpty(txtFechaHasta.Text) ? null : DateTime.Parse(txtFechaHasta.Text).ToString("yyyy-MM-dd");

                // Llamar al método avanzado que filtra según los parámetros
                var lista = wsCliente.buscarCirculacionesAvanzado(idCirculacion, idReserva, idUsuario, idEjemplar, estado, fechaDesde, fechaHasta);

                gvCirculaciones.DataSource = lista;
                gvCirculaciones.DataBind();
            }
            catch (Exception ex)
            {
                // Limpia la grilla y podrías loggear el error aquí si quieres
                gvCirculaciones.DataSource = null;
                gvCirculaciones.DataBind();
            }
        }
    }
}

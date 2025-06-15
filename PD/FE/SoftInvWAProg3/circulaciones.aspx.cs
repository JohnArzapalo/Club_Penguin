using System;
using System.Collections.Generic;
using System.Globalization;
using System.Web.UI;
using System.Web.UI.WebControls;

using SoftInvWAProg3.servicios; // Asegúrate que sea el namespace correcto

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
                circulacionDTO cir = new circulacionDTO();
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

        //protected void gvCirculaciones_PageIndexChanging(object sender, GridViewPageEventArgs e)
        //{
        //    gvCirculaciones.PageIndex = e.NewPageIndex;
        //    ltDEBUGEANDOOOO.Text = "Hola, soy pageindexchanging";
        //    CargarCirculaciones();
        //}

        protected void btnRegistrarPrestamo_Click(object sender, EventArgs e)
        {
            //PENDIENTE!!!!!
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
            catch (System.Exception ex)
            {
                // Limpia la grilla y podrías loggear el error aquí si quieres
                gvCirculaciones.DataSource = null;
                gvCirculaciones.DataBind();
            }
        }

        protected void gvCirculaciones_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalleCirculacion" && e.CommandArgument != null)
            {
                int circId = Convert.ToInt32(e.CommandArgument);
                Response.Redirect($"detalle_circulacion.aspx?id={circId}");
            }
        }

        protected void gvCirculaciones_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                // Obtener el valor de fechaDevolucion
                DateTime fechaDev = (DateTime)DataBinder.Eval(e.Row.DataItem, "fechaDevolucion");
                // Si es DateTime.MinValue, reemplazar el texto
                if (fechaDev == DateTime.MinValue)
                {
                    e.Row.Cells[5].Text = ""; // Índice 5 = columna "Fecha devolución"
                }
                string estado = DataBinder.Eval(e.Row.DataItem, "estadoPrestamo")?.ToString();
                // Paso 1: Reemplazar guiones bajos por espacios
                if (estado == "DEVUELTO_DANADO_O_PERDIDO") estado = "Devuelto dañado o perdido";
                if (estado == "DEVUELTO_RETRASO_Y_DANO_PERDIDA") estado = "Devuelto con retraso y daño/pérdida";
                //Formato para los demás estados
                estado = estado.Replace("_", " ");
                estado = estado.ToLower();
                estado = char.ToUpper(estado[0]) + estado.Substring(1);

                e.Row.Cells[6].Text = estado; // Índice de la columna "Estado"

                circulacionDTO circulacion = (circulacionDTO)e.Row.DataItem;
                e.Row.Attributes["onclick"] = $"window.location='detalle_circulacion.aspx?id={circulacion.circulacionId}';";
                e.Row.Style["cursor"] = "pointer";
            }
        }
    }
}

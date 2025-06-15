using System;
using System.Data;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class reservas : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarReservas();
            }
        }

        private void CargarReservas()
        {
            try
            {
                ReservaWSClient cliente = new ReservaWSClient();
                reservaDTO[] reservas = cliente.listarTodasReservas();

                DataTable dt = new DataTable();
                dt.Columns.Add("ReservaId", typeof(string));
                dt.Columns.Add("UsuarioId", typeof(string));
                dt.Columns.Add("MaterialId", typeof(string));
                dt.Columns.Add("FechaReserva", typeof(DateTime));
                dt.Columns.Add("Estado", typeof(string));

                foreach (reservaDTO reserva in reservas)
                {
                    dt.Rows.Add(
                        reserva.reservaId.ToString(),
                        reserva.usuario != null ? reserva.usuario.nombres : "N/A",
                        reserva.material != null ? reserva.material.materialId.ToString() : "N/A",
                        reserva.fechaReserva,
                        reserva.estadoReserva
                    );
                }

                gvReservas.DataSource = dt;
                gvReservas.DataBind();
            }
            catch (System.Exception ex)
            {
                MostrarError("Error al cargar reserva: " + ex.Message);
            }
        }

        protected void gvReservas_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvReservas.PageIndex = e.NewPageIndex;
            CargarReservas();
        }
        protected void gvReservas_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                // Get the DataKey value (ReservaId) for the current row
                string reservaId = gvReservas.DataKeys[e.Row.RowIndex].Value.ToString();

                // Add an onclick attribute to the row to redirect
                e.Row.Attributes["onclick"] = $"window.location='detalle_reserva.aspx?reservaId={reservaId}';";
                e.Row.Style["cursor"] = "pointer"; // Ensure cursor changes to pointer
            }
        }
        protected void gvReservas_SelectedIndexChanged(object sender, EventArgs e)
        {
            int index = gvReservas.SelectedIndex;
            if (index < 0) return;

            string reservaId = gvReservas.DataKeys[index].Value.ToString();
            Response.Redirect($"detalle_reserva.aspx?reservaId={reservaId}");
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            try
            {
                lblError.Text = "";
                lblError.Visible = false;

                ReservaWSClient cliente = new ReservaWSClient();

                // Captura los filtros desde el formulario
                string reservaId = txtReserva.Text.Trim().ToLower();
                string usuarioNombre = txtUsuario.Text.Trim().ToLower();
                string materialTitulo = txtMaterial.Text.Trim().ToLower();
                string estado = ddlEstado.SelectedValue;
                string fechaDesde = txtFechaDesde.Text.Trim();
                string fechaHasta = txtFechaHasta.Text.Trim();

                // Normalización: convierte string vacío en null
                Func<string, string> Normalize = delegate (string input)
                {
                    return string.IsNullOrWhiteSpace(input) ? null : input;
                };

                reservaDTO[] reservas = cliente.buscarReservasAvanzado(
                    Normalize(reservaId),
                    Normalize(usuarioNombre),
                    Normalize(materialTitulo),
                    Normalize(estado),
                    Normalize(fechaDesde),
                    Normalize(fechaHasta)
                );

                if (reservas == null || reservas.Length == 0)
                {
                    // Mostrar mensaje si no hay resultados
                    lblError.Text = "No se encontraron reservas con los filtros aplicados";
                    lblError.Visible = true;

                    gvReservas.DataSource = null;
                    gvReservas.DataBind();
                    return;
                }

                DataTable dt = new DataTable();
                dt.Columns.Add("ReservaId", typeof(string));
                dt.Columns.Add("UsuarioId", typeof(string));
                dt.Columns.Add("MaterialId", typeof(string));
                dt.Columns.Add("FechaReserva", typeof(DateTime));
                dt.Columns.Add("Estado", typeof(string));

                foreach (reservaDTO reserva in reservas)
                {
                    dt.Rows.Add(
                        reserva.reservaId.ToString(),
                        reserva.usuario != null ? reserva.usuario.nombres : "N/A",
                        reserva.material != null ? reserva.material.materialId.ToString() : "N/A",
                        reserva.fechaReserva,
                        reserva.estadoReserva
                    );
                }

                gvReservas.DataSource = dt;
                System.Diagnostics.Debug.WriteLine("Reservas encontradas: " + reservas.Length);
                gvReservas.DataBind();
            }
            catch (System.Exception ex)
            {
                // Mostrar mensaje si no hay resultados
                lblError.Text = "No se encontraron reservas con los filtros aplicados";
                lblError.Visible = true;
            }
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtReserva.Text = "";
            txtUsuario.Text = "";
            txtMaterial.Text = "";
            ddlEstado.SelectedIndex = 0;
            txtFechaDesde.Text = "";
            txtFechaHasta.Text = "";

            CargarReservas();
        }

        private void MostrarError(string mensaje)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                $"alert('{mensaje.Replace("'", "\\'")}');", true);
        }
    }
}

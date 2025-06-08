using System;
using System.Collections.Generic;
using System.EnterpriseServices;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.SancionWS;

namespace SoftInvWAProg3
{
    public partial class sanciones : System.Web.UI.Page
    {
        private SancionWSClient sancionWSClient;
        protected void Page_Init(object sender, EventArgs e)
        {
            sancionWSClient = new SancionWS.SancionWSClient();
            LoadSanciones();
        }
        private void LoadSanciones()
        {
            List<sancionDTO> sanciones = sancionWSClient.listarSanciones().ToList();
            gvSanciones.DataSource = sanciones;
            gvSanciones.DataBind();
            
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // Limpiar todos los campos de filtro
            txtSancion.Text = string.Empty;
            txtCirculacion.Text = string.Empty;
            txtFechaDesde.Text = string.Empty;
            txtFechaHasta.Text = string.Empty;


            LoadSanciones();
            // Ocultar mensajes de error
            lblError.Visible = false;
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            lblError.Visible = false;
            try
            {
                // Obtener valores de los filtros
                string sancionIdStr = txtSancion.Text.Trim();
                string circulacionIdStr = txtCirculacion.Text.Trim();
                string fechaDesde = txtFechaDesde.Text;
                string fechaHasta = txtFechaHasta.Text;

                // Convertir los IDs a int o null
                int? sancionId = int.TryParse(sancionIdStr, out int sId) ? sId : (int?)null;
                int? circulacionId = int.TryParse(circulacionIdStr, out int cId) ? cId : (int?)null;

                // Llamar al servicio con los filtros
                List<sancionDTO> resultados = sancionWSClient.buscarSancionesFlexible(
                    sancionId.ToString(),
                    circulacionId.ToString(),
                    string.IsNullOrEmpty(fechaDesde) ? null : fechaDesde,
                    string.IsNullOrEmpty(fechaHasta) ? null : fechaHasta
                ).ToList();

                if (resultados == null || resultados.Count == 0)
                {
                    // Mostrar mensaje si no hay resultados
                    lblError.Text = "No se encontraron sanciones con los filtros aplicados";
                    lblError.Visible = true;

                    gvSanciones.DataSource = null;
                    gvSanciones.DataBind();
                    return;
                }

                // Mostrar resultados
                gvSanciones.DataSource = resultados;
                gvSanciones.DataBind();
            }
            catch (Exception ex)
            {
                // Mostrar mensaje si no hay resultados
                lblError.Text = "No se encontraron sanciones con los filtros aplicados";
                lblError.Visible = true;
            }
        }

        protected void btnReporte_Click(object sender, EventArgs e)
        {

        }

        //protected void gvSanciones_SelectedIndexChanged(object sender, EventArgs e)
        //{
        //    //if (gvSanciones.SelectedIndex >= 0)
        //    //{
        //    //    string sancionId = gvSanciones.DataKeys[gvSanciones.SelectedIndex].Value.ToString();
        //    //    //Response.Redirect($"sanciones_detalle.aspx?id={sancionId}");
        //    //    Response.Redirect("sanciones_detalle.aspx");
        //    //}
        //}

        protected void gvSanciones_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                sancionDTO sancion = (sancionDTO)e.Row.DataItem;
                e.Row.Attributes["onclick"]= $"window.location='sanciones_detalle.aspx?id={sancion.sancionId}';";
                e.Row.Style["cursor"] = "pointer";
            }
        }

        protected void gvSanciones_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalleSancion" && e.CommandArgument != null)
            {
                int sancionId = Convert.ToInt32(e.CommandArgument);
                Response.Redirect($"sanciones_detalle.aspx?id={sancionId}");
            }
        }

        //protected void btnVerDetalle_Click(object sender, EventArgs e)
        //{
            
        //}

        //protected void btnSelectRow_Click(object sender, EventArgs e)
        //{
            
        //}
    }
}
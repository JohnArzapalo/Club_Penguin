using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.SancionWS;

namespace SoftInvWAProg3
{
    public partial class sanciones_detalle : System.Web.UI.Page
    {
        private SancionWSClient sancionWSClient;
        private int? sancionId;

        public SancionWSClient SancionWSClient { get => sancionWSClient; set => sancionWSClient = value; }
        public int? SancionId { get => sancionId; set => sancionId = value; }
        public sanciones_detalle()
        {
            this.SancionWSClient=new SancionWSClient();
            this.SancionId = null;
        }
        protected void Page_Load(object sender, EventArgs e)
        {

            if (!IsPostBack)
            {
                string idparam = Request.QueryString["id"];
                if (!string.IsNullOrEmpty(idparam) && int.TryParse(idparam, out int id))
                {
                    this.SancionId = id;
                    cargarEntidad();
                }
                else
                {
                    Response.Redirect("sanciones.aspx");
                }
            }
        }

        private void cargarEntidad()
        {
            if (!this.SancionId.HasValue)
            {
                Response.Redirect("sanciones.aspx?error=invalid_id");
                return;
            }
            sancionDTO sancion = this.SancionWSClient.obtenerPorId((int)this.SancionId);

            if (sancion != null)
            {
                ltSancionId.Text = sancion.sancionId.ToString();
                ltCirculacion.Text = sancion.circulacion.circulacionId.ToString();
                ltFechaRegistro.Text=sancion.fechaRegistro.ToString();
                ltFechaTermino.Text=sancion.fechaTermino.ToString();

                
                ltDevolucion.Text = sancion.circulacion.estadoPrestamo.ToString();
                ltDiasSancion.Text = sancion.diasSancion.ToString();
                txtObservaciones.Text = sancion.observacion;
            }
        }
    }
}
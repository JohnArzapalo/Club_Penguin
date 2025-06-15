using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class sanciones_detalle : System.Web.UI.Page
    {
        private SancionWSClient sancionWSClient;
        private int? sancionId;
        private int? circulacionId;
        private sancionDTO sancionAIns;

        public SancionWSClient SancionWSClient { get => sancionWSClient; set => sancionWSClient = value; }
        public int? SancionId { get => sancionId; set => sancionId = value; }
        public sancionDTO SancionAIns { get => sancionAIns; set => sancionAIns = value; }
        public int? CirculacionId { get => circulacionId; set => circulacionId = value; }

        public sanciones_detalle()
        {
            this.SancionWSClient = new SancionWSClient();
            this.SancionId = null;
            this.CirculacionId = null;
        }
        protected void Page_Load(object sender, EventArgs e)
        {

            //if (!IsPostBack)
            //{
            //    string mode = Request.QueryString["mode"];

            //    if (mode == "create")
            //    {
            //        // Modo creación de nueva sanción
            //        string circulacionIdParam = Request.QueryString["circulacionId"];
            //        if (!string.IsNullOrEmpty(circulacionIdParam) && int.TryParse(circulacionIdParam, 
            //            out int circulacionId))
            //        {
            //            cargarVistaPrevia(circulacionId);
            //            sancionAIns.circulacion.circulacionId = circulacionId;//
            //        }

            //        //else
            //        //{
            //        //    Response.Redirect("sanciones.aspx?error=invalid_circulacion_id");
            //        //}
            //    }
            //    else
            //    {
            //        // Modo visualización normal (existente)
            //        string idparam = Request.QueryString["id"];
            //        if (!string.IsNullOrEmpty(idparam) && int.TryParse(idparam, out int id))
            //        {
            //            this.SancionId = id;
            //            cargarEntidadExistente();
            //        }
            //        //else
            //        //{
            //        //    Response.Redirect("sanciones.aspx");
            //        //}
            //    }
            //}


            ///NUEVA FORMA
            string mode = Request.QueryString["mode"];
            if (mode == "create")
            {
                // Modo creación de nueva sanción
                string circulacionIdParam = Request.QueryString["circulacionId"];
                if (!string.IsNullOrEmpty(circulacionIdParam) && int.TryParse(circulacionIdParam,
                    out int circulacionId))
                {
                    // Se asigna en cada carga (incluyendo postbacks), fundamental para que funcione el boton de registrar sancion
                    CirculacionId = circulacionId;
                    sancionAIns = this.SancionWSClient.vistaPreviaNuevaSancion(circulacionId);
                }
                if (!IsPostBack && this.CirculacionId.HasValue)
                {
                    cargarVistaPrevia((int)CirculacionId); // Solo carga datos la primera vez
                }

            }
            else
            {
                // Modo visualización normal (existente)
                txtObservaciones.Enabled = false;
                string idparam = Request.QueryString["id"];
                if (!string.IsNullOrEmpty(idparam) && int.TryParse(idparam, out int id) && !IsPostBack)
                {
                    this.SancionId = id;
                    cargarEntidadExistente();
                }

            }
        }

        private void cargarEntidadExistente()
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
                ltFechaRegistro.Text = sancion.fechaRegistro.ToString();
                ltFechaTermino.Text = sancion.fechaTermino.ToString();

                ltDevolucion.Text = sancion.circulacion.estadoPrestamo.ToString();
                ltDiasSancion.Text = sancion.diasSancion.ToString();
                txtObservaciones.Text = sancion.observacion;
            }
        }


        //---------------NUEVO!!!
        private void cargarVistaPrevia(int circId)
        {
            btnRegistrarSancion.Visible = true;
            ltCirculacion.Text = sancionAIns.circulacion.circulacionId.ToString();
            ltFechaRegistro.Text = sancionAIns.fechaRegistro.ToString();
            ltFechaTermino.Text = sancionAIns.fechaTermino.ToString();
            ltDevolucion.Text = sancionAIns.circulacion.estadoPrestamo.ToString();
            ltDiasSancion.Text = sancionAIns.diasSancion.ToString();

        }

        protected void btnRegistrarSancion_Click(object sender, EventArgs e)
        {
            sancionAIns.observacion = txtObservaciones.Text;
            try
            {
                int resultado = this.SancionWSClient.insertarSancion(sancionAIns);
                if (resultado > 0)
                {
                    Response.Redirect("sanciones.aspx");
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "alert",
                        "alert('No se pudo registrar la sancion');", true);
                }
            }
            catch (System.Exception ex)
            {
                ClientScript.RegisterStartupScript(this.GetType(), "alert",
                    "alert('Error al procesar la solicitud');", true);
            }
        }
    }
}
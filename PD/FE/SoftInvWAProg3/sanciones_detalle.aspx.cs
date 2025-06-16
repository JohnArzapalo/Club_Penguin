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
            //Mostrar "Volver" según el rol
            string rol = Session["rol"] as string;
            bool esBibliotecario = !string.IsNullOrEmpty(rol) && rol.Equals("Bibliotecario", StringComparison.OrdinalIgnoreCase);
            pnlVolverBibliotecario.Visible = esBibliotecario;
            pnlVolverUsuario.Visible = !esBibliotecario;
            pnlUsuario.Visible = esBibliotecario;
            
            //NUEVA FORMA
            string mode = Request.QueryString["mode"];

            pnlObservacionesLectura.Visible = (mode != "create");
            pnlObservacionesEditor.Visible = (mode == "create");

            if (mode == "create")
            {
                //Modo creación de nueva sanción
                string circulacionIdParam = Request.QueryString["circulacionId"];
                if (!string.IsNullOrEmpty(circulacionIdParam) && int.TryParse(circulacionIdParam, out int circulacionId))
                {
                    CirculacionId = circulacionId;
                    sancionAIns = this.SancionWSClient.vistaPreviaNuevaSancion(circulacionId);
                }

                if (!IsPostBack && this.CirculacionId.HasValue)
                {
                    cargarVistaPrevia((int)CirculacionId);
                }
            }
            else
            {
                //Modo visualización normal (existente)
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
                ltFechaRegistro.Text = sancion.fechaRegistro.ToString("dd/MM/yyyy");
                ltFechaTermino.Text = sancion.fechaTermino.ToString("dd/MM/yyyy");

                ltDevolucion.Text = SoftInvWAProg3.prestamos.ObtenerTextoEstadoPrestamo(sancion.circulacion.estadoPrestamo.ToString());
                ltDiasSancion.Text = sancion.diasSancion.ToString();
                ltObservaciones.Text = sancion.observacion;

                if (sancion.circulacion.usuario != null)
                {
                    string nombres = sancion.circulacion.usuario.nombres;
                    string apellido = sancion.circulacion.usuario.primerApellido;
                    ltUsuario.Text = $"{sancion.circulacion.usuario.usuarioId} - {nombres} {apellido}";
                }
            }
        }


        //---------------NUEVO!!!
        private void cargarVistaPrevia(int circId)
        {
            btnRegistrarSancion.Visible = true;
            ltCirculacion.Text = sancionAIns.circulacion.circulacionId.ToString();
            ltFechaRegistro.Text = sancionAIns.fechaRegistro.ToString("dd/MM/yyyy");
            ltFechaTermino.Text = sancionAIns.fechaTermino.ToString("dd/MM/yyyy");
            ltDevolucion.Text = SoftInvWAProg3.prestamos.ObtenerTextoEstadoPrestamo(sancionAIns.circulacion.estadoPrestamo.ToString());
            ltDiasSancion.Text = sancionAIns.diasSancion.ToString();
            txtObservaciones.Text = sancionAIns.observacion;

            if (sancionAIns.circulacion.usuario != null)
            {
                string nombres = sancionAIns.circulacion.usuario.nombres;
                string apellido = sancionAIns.circulacion.usuario.primerApellido;
                ltUsuario.Text = $"{sancionAIns.circulacion.usuario.usuarioId} - {nombres} {apellido}";
            }
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
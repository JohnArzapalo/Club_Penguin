using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using SoftInvWAProg3.servicios;
//using SoftInvWAProg3.ReservaWS;
//using SoftInvWAProg3.SancionWS;
//using SoftInvWAProg3.SancionWS;

namespace SoftInvWAProg3
{
    public partial class detalle_circulacion : System.Web.UI.Page
    {
        private CirculacionWSClient wsCliente;
        private int? circulacionId;

        public CirculacionWSClient WsCliente { get => wsCliente; set => wsCliente = value; }
        public int? CirculacionId { get => circulacionId; set => circulacionId = value; }

        public detalle_circulacion()
        {
            this.CirculacionId = null;
            this.WsCliente = new CirculacionWSClient();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            string idparam = Request.QueryString["id"];
            if (!string.IsNullOrEmpty(idparam) && int.TryParse(idparam, out int id))
            {
                this.CirculacionId = id; // Se asigna en cada carga (incluyendo postbacks), fundamental para que funcione el boton de visualizar sancion
            }

            if (!IsPostBack && this.CirculacionId.HasValue)
            {
                cargarEntidad(); // Solo carga datos la primera vez
            }
        }

        private void cargarEntidad()
        {
            circulacionDTO circulacion = this.wsCliente.obtenerCirculacionPorId((int)this.CirculacionId);
            if (circulacion != null)
            {
                ltPrestamoID.Text = circulacion.circulacionId.ToString();
                ltReserva.Text = circulacion.reserva.reservaId.ToString();
                string nomUsuario = "";
                if (circulacion.usuario != null) nomUsuario = circulacion.usuario.nombres + " " + circulacion.usuario.primerApellido;

                // Lógica de habilitación/deshabilitación
                estadoPrestamo aux = circulacion.estadoPrestamo;
                bool esVigente = (aux == estadoPrestamo.VIGENTE);
                bool esNoDevuelto = (aux == estadoPrestamo.NO_DEVUELTO);
                bool esVigenteONoDev = (esVigente || esNoDevuelto);
                bool esDevueltoATiempo = (aux == estadoPrestamo.DEVUELTO_A_TIEMPO);
                bool noDebeTenerSancion = (esVigenteONoDev || esDevueltoATiempo);
                // Habilitar controles SOLO si es VIGENTE O NO_DEVUELTO
                ddlEstados.Enabled = esVigenteONoDev;
                txtFechaDev.Enabled = esVigenteONoDev;
                btnActualizarEstado.Enabled = esVigenteONoDev;

                //---------Botones de registrar sancion y visualizar sanción (importante)
                try
                {
                    sancionDTO sancionAsociada = wsCliente.obtenerSancionAsociada((int)this.CirculacionId);
                    bool existeSancion = (sancionAsociada != null);
                    btnVisualizarSancion.Visible = existeSancion;//solo visible si existe una sancion asociada para la circulacion
                    //Registrar sancion: solo visible si no hay sancion asociada y si es devuelto con retraso, con daño/perdida, con retraso y daño/perdida
                    btnRegistrarSancion.Visible = (!existeSancion && !noDebeTenerSancion);
                }
                catch (System.Exception ex)
                {
                    lblError.Text = "No existe la sancion";// Opcional: Loggear el error (ej: si falla el WS)
                    lblError.Visible = true;
                }

                ltUsuario.Text = circulacion.usuario.usuarioId.ToString() + " - " + nomUsuario;
                ltEjemplar.Text = circulacion.ejemplar.ejemplarId.ToString();
                ltFechaPrestamo.Text = circulacion.fechaPrestamo.ToString("dd/MM/yyyy");
                ltPlazoMax.Text = circulacion.fechaVencimiento.ToString("dd/MM/yyyy");
                ltBiblioteca.Text = circulacion.ejemplar.biblioteca?.nombre ?? "";
                ltHorarioBiblioteca.Text = "Lunes a viernes de 8:00 a.m. a 6:00 p.m."; //Hardcodeado porque aun los horarios no están en la BD xdd
                ddlEstados.SelectedValue = circulacion.estadoPrestamo.ToString();
                if (esVigenteONoDev) txtFechaDev.Text = "";
                else txtFechaDev.Text = circulacion.fechaDevolucion.ToString("yyyy-MM-dd");
            }
        }

        protected void btnRegistrarSancion_Click(object sender, EventArgs e)
        {
            //NUEVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
            // Redirigir a la página de detalle de sanción en modo creación
            Response.Redirect($"sanciones_detalle.aspx?circulacionId={(int)CirculacionId}&mode=create");

        }

        protected void btnActualizarEstado_Click(object sender, EventArgs e)
        {
            try
            {
                circulacionDTO actualizada = this.wsCliente.obtenerCirculacionPorId((int)this.CirculacionId);
                actualizada.estadoPrestamo = (estadoPrestamo)Enum.Parse(
                    typeof(estadoPrestamo),
                    ddlEstados.SelectedValue
                );
                actualizada.fechaDevolucion = DateTime.Parse(txtFechaDev.Text);

                int resultado = wsCliente.modificarCirculacion(actualizada);
                if (resultado > 0)
                {
                    Response.Redirect($"detalle_circulacion.aspx?id={this.CirculacionId}");
                }
                else
                {
                    ClientScript.RegisterStartupScript(this.GetType(), "alert",
                        "alert('No se pudo actualizar la circulación');", true);
                }
            }
            catch (System.Exception ex)
            {
                ClientScript.RegisterStartupScript(this.GetType(), "alert",
                    "alert('Error al procesar la solicitud');", true);
            }
        }

        protected void btnVisualizarSancion_Click(object sender, EventArgs e)
        {
            try
            {
                // Obtener el ID de la sanción asociada
                //lblDEBUGGG.Text = "Circulacion id luego del click: " + CirculacionId;
                sancionDTO sancion = wsCliente.obtenerSancionAsociada((int)this.CirculacionId);
                Response.Redirect($"sanciones_detalle.aspx?id={sancion.sancionId}");
            }
            catch (System.Exception ex)
            {
                // Manejar errores (loggear y mostrar mensaje)
                System.Diagnostics.Debug.WriteLine($"Error al obtener sanción: {ex.Message}");
                ClientScript.RegisterStartupScript(this.GetType(), "alert",
                    "alert('Error al cargar la sanción');", true);
            }
        }

        protected void ddlEstados_SelectedIndexChanged(object sender, EventArgs e)
        {
            string estadoSelec = ddlEstados.SelectedValue;
            if (estadoSelec == "VIGENTE" || estadoSelec == "NO_DEVUELTO")
            {
                txtFechaDev.Text = "";//APARECE VACIO POR DEFAULT
            }
            else
            {
                txtFechaDev.Text = DateTime.Now.ToString("yyyy-MM-dd");//EL DIA DE HOY POR DEFAULT
            }

        }
    }
}
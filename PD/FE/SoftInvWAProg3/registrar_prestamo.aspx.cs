using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class registrar_prestamo : System.Web.UI.Page
    {
        private CirculacionWSClient wsCliente;
        private MaterialWSClient materialCliente = new MaterialWSClient();

        public CirculacionWSClient WsCliente { get => wsCliente; set => wsCliente = value; }

        public registrar_prestamo()
        {
            this.WsCliente = new CirculacionWSClient();
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Response.Redirect("circulaciones.aspx");
        }

        protected void btnRegistrar_Click(object sender, EventArgs e)
        {
            UsuarioWSClient usuarioWSClient = new UsuarioWSClient();
            EjemplarWSClient ejemplarWSClient = new EjemplarWSClient();
            CirculacionWSClient circulacionWSClient = new CirculacionWSClient();
            circulacionDTO circulacion = new circulacionDTO();
            //obtener datos:
            string idusuario = txtUsuario.Text.Trim();
            string idejemplar = txtEjemplar.Text.Trim();
            //formatear
            if (!int.TryParse(txtUsuario.Text.Trim(), out int usuarioid) ||
            !int.TryParse(txtEjemplar.Text.Trim(), out int ejemplarid))
            {
                ClientScript.RegisterStartupScript(this.GetType(), "alert",
                        "alert('Cuidado con los ids');", true);
                return;
            }

            //cargar el objeto usuario y ejemplar
            usuarioDTO usuario = usuarioWSClient.obtenerUsuarioPorId(usuarioid);
            System.Diagnostics.Debug.WriteLine($"Correo: {usuario.correoElectronico}");
            ejemplarDTO ejemplar = ejemplarWSClient.obtenerEjemplarPorId(ejemplarid);
            System.Diagnostics.Debug.WriteLine($"material ID: {ejemplar.material.materialId}");
            
            circulacion.usuario = usuario;
            circulacion.ejemplar = ejemplar;
            //circulacion.fechaPrestamo = DateTime.Now;

            lblError.Text = "Circulacion a insertar: idus:" + circulacion.usuario.usuarioId + "   idej:" + circulacion.ejemplar.ejemplarId + "  nomusuario: "+circulacion.usuario.nombres;
            lblError.Text += "\n otros datos: tipoUsuario:" + circulacion.usuario.tipoUsuario.nombre + "   numero de dias: " + circulacion.usuario.tipoUsuario.numeroMaxDias;

            try
            {
                //al insertar se llenarán el resto de parámetros. Ver BO
                int resultado = this.WsCliente.insertarCirculacion(circulacion);
                System.Diagnostics.Debug.WriteLine($"QUIERO VER QUE TIENE: {resultado}");
                if (resultado > 0)
                {
                    circulacionDTO circulacionInsertada = circulacionWSClient.obtenerCirculacionPorId(resultado);
                    String fechaPrestamo = circulacionInsertada.fechaPrestamo.ToString("dd/MM/yyyy");
                    String fechaVencimiento = circulacionInsertada.fechaVencimiento.ToString("dd/MM/yyyy");
                    String nombreBiblioteca = circulacionInsertada.ejemplar.biblioteca.nombre;
                    string correo = circulacionInsertada.usuario.correoElectronico;

                    string asunto = "Prestamo registrado exitosamente";

                    string mensaje = $"Estimado/a {circulacionInsertada.usuario.nombres},\n\n" +
                          $"Nos complace confirmar que su préstamo ha sido registrado exitosamente en nuestro sistema.\n\n" +
                          $"DETALLES DEL PRÉSTAMO:\n" +
                          $"━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                          $"Fecha de préstamo: {fechaPrestamo}\n" +
                          $"Fecha de vencimiento: {fechaVencimiento}\n" +
                          $"Biblioteca: {nombreBiblioteca}\n\n" +
                          $"INFORMACIÓN DEL MATERIAL:\n" +
                          $"━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                          $"• Tipo: {char.ToUpper(circulacionInsertada.ejemplar.material.tipoMaterial.ToString()[0]) + circulacionInsertada.ejemplar.material.tipoMaterial.ToString().Substring(1).ToLower()}\n" +
                          $"• Título: {circulacionInsertada.ejemplar.material.titulo}\n" +
                          $"• Autor(es): {circulacionInsertada.ejemplar.material.autor}\n" +
                          $"• Año de publicación: {circulacionInsertada.ejemplar.material.anioPublicacion.ToString()}\n" +
                          $"• Número de páginas: {circulacionInsertada.ejemplar.material.numeroPaginas.ToString()}\n" +
                          $"• Tema: {circulacionInsertada.ejemplar.material.tema}\n" +
                          $"• Idioma: {circulacionInsertada.ejemplar.material.idioma}\n\n" +
                          $"RECORDATORIOS IMPORTANTES:\n" +
                          $"━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                          $"• La fecha límite para devolver este material es: {fechaVencimiento}\n" +
                          $"• Debe devolver el material en la biblioteca: {nombreBiblioteca}\n" +
                          $"• El retraso en la devolución puede generar sanciones\n" +
                          $"• Cuide el material durante el período de préstamo\n" +
                          $"• En caso de pérdida o daño, comuníquese inmediatamente con la biblioteca\n\n";

                    materialCliente.envioCorreos(correo, asunto, mensaje);

                    Response.Redirect("circulaciones.aspx");
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
using System;
using System.Data.SqlClient;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class detalleReserva : System.Web.UI.Page
    {
        private ReservaWSClient reservaCliente;
        private MaterialWSClient materialCliente;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                reservaCliente = new ReservaWSClient();
                materialCliente = new MaterialWSClient();

                string idReservaStr = Request.QueryString["reservaId"]; 
                if (!string.IsNullOrEmpty(idReservaStr) && int.TryParse(idReservaStr, out int idReservaInt))
                {
                    CargarDetalleDeReserva(idReservaInt);
                }
                else
                {
                    ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                        "alert('ID de reserva inválido o no proporcionado.');", true);
                    Response.Redirect("reservas.aspx?error=id_invalido_reserva");
                }
            }
        }

        private void CargarDetalleDeReserva(int idReserva)
        {
            try
            {
                reservaDTO reserva = reservaCliente.obtenerReservaPorId(idReserva);

                if (reserva == null)
                {
                    ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                        "alert('Reserva no encontrada.');", true);
                    Response.Redirect("reservas.aspx?error=notfound");
                    return;
                }

                visibilizarCamposParaBibliotecario();

                ltReservaId.Text = reserva.reservaId.ToString();
                ltUsuario.Text = reserva.usuario != null ? $"{reserva.usuario.nombres} {reserva.usuario.primerApellido} {reserva.usuario.segundoApellido}".Trim() : "N/A";
                ltFechaReserva.Text = reserva.fechaReserva.ToString("dd/MM/yyyy");
                ltFechaVencimiento.Text = reserva.fechaVencimiento.ToString("dd/MM/yyyy");
                ltEstado.Text = reserva.estadoReserva.ToString();


                if (reserva.material != null)
                {
                    int idMaterial = reserva.material.materialId;
                    string tipoMaterialString = reserva.material.tipoMaterial.ToString();

                    materialDTO materialDTO = materialCliente.obtenerMaterialPorId(idMaterial);

                    ltMaterialId.Text = materialDTO.materialId.ToString();
                    ltTitulo.Text = materialDTO.titulo;
                    ltAutor.Text = materialDTO.autor;
                    ltAnioPublicacion.Text = materialDTO.anioPublicacion.ToString();
                    ltTipo.Text = ObtenerNombreMostrar(materialDTO.tipoMaterial);

                    string icono = ObtenerIconoPorTipo(materialDTO.tipoMaterial);
                    imgTipo.Src = icono;
                    imgChipIcono.Src = icono;

                    chipTipo.Attributes["class"] += " " + ObtenerClaseChip(materialDTO.tipoMaterial);


                    switch (tipoMaterialString)
                    {
                        case "LIBRO":
                            libroDTO libro = materialDTO as libroDTO;
                            if (libro != null)
                            {
                                visibilizarCamposLibros(true);
                                ltISBN.Text = libro.isbn;
                                ltEdicion.Text = libro.edicion;
                                ltEditorialLibro.Text = libro.editorial;
                            }
                            break;

                        case "ARTICULO":
                            articuloDTO articulo = materialDTO as articuloDTO;
                            if (articulo != null)
                            {
                                visibilizarCamposArticulos(true);
                                ltISSN.Text = articulo.issn;
                                ltNombreRevista.Text = articulo.nombreRevista;
                                ltEditorialArticulo.Text = articulo.editorial;
                                ltVolumen.Text = articulo.volumen;
                                ltNumero.Text = articulo.numero.ToString();
                            }
                            break;

                        case "TESIS":
                            tesisDTO tesis = materialDTO as tesisDTO;
                            if (tesis != null)
                            {
                                visibilizarCamposTesis(true);
                                ltInstitucionPublicacion.Text = tesis.nombreInstitucionPublicacion;
                                ltAsesorTesis.Text = tesis.asesorTesis;
                                ltEspecialidad.Text = tesis.especialidad;
                                ltGrado.Text = tesis.grado.ToString();
                            }
                            break;

                        default:
                            ScriptManager.RegisterStartupScript(this, GetType(), "showWarning",
                                $"alert('Tipo de material desconocido: {tipoMaterialString}');", true);
                            break;
                    }
                }
                else
                {

                    ltMaterialId.Text = "N/A";
                    ltTitulo.Text = "N/A";
                    ltAutor.Text = "N/A";
                    ltAnioPublicacion.Text = "N/A";
                    visibilizarCamposLibros(false);
                    visibilizarCamposArticulos(false);
                    visibilizarCamposTesis(false);
                    ScriptManager.RegisterStartupScript(this, GetType(), "showInfo",
                        "alert('La reserva no tiene material asociado.');", true);
                }
            }
            catch (System.ServiceModel.EndpointNotFoundException ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                    $"alert('Error de conexión al servicio: Asegúrate de que el servicio esté corriendo. Detalles: {ex.Message.Replace("'", "\\'")}');", true);
            }
            catch (System.ServiceModel.FaultException ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                    $"alert('Error del servicio: {ex.Message.Replace("'", "\\'")}');", true);
            }
            catch (SqlException ex)
            {
                ScriptManager.RegisterStartupScript(this, GetType(), "showError",
                    $"alert('Ocurrió un error inesperado: {ex.Message.Replace("'", "\\'")}');", true);
            }
        }

        private string ObtenerIconoPorTipo(tipoMaterial tipo)
        {
            switch (tipo)
            {
                case tipoMaterial.LIBRO: return "Images/libro.svg";
                case tipoMaterial.ARTICULO: return "Images/articulo.svg";
                case tipoMaterial.TESIS: return "Images/tesis.svg";
                default: return "Images/default.svg";
            }
        }

        private string ObtenerNombreMostrar(tipoMaterial tipo)
        {
            switch (tipo)
            {
                case tipoMaterial.LIBRO: return "Libro";
                case tipoMaterial.ARTICULO: return "Artículo";
                case tipoMaterial.TESIS: return "Tesis";
                default: return "Desconocido";
            }
        }

        private string ObtenerClaseChip(tipoMaterial tipo)
        {
            switch (tipo)
            {
                case tipoMaterial.LIBRO: return "chip-libro";
                case tipoMaterial.ARTICULO: return "chip-articulo";
                case tipoMaterial.TESIS: return "chip-tesis";
                default: return string.Empty;
            }
        }

        private void visibilizarCamposParaBibliotecario()
        {
            string rol = Session["rol"] as string;

            if (rol == "Bibliotecario")
            {
                filaMaterial.Visible = true;
                filaUsuario.Visible = true;
            }
            else
            {
                filaMaterial.Visible = false;
                filaUsuario.Visible = false;
            }
        }

        private void visibilizarCamposLibros(bool valor)
        {
            filaISBN.Visible = valor;
            filaEdicion.Visible = valor;
            filaEditorialLibro.Visible = valor;
        }

        private void visibilizarCamposArticulos(bool valor)
        {
            filaISSN.Visible = valor;
            filaNombreRevista.Visible = valor;
            filaEditorialArticulo.Visible = valor;
            filaVolumen.Visible = valor;
            filaNumero.Visible = valor;
        }

        private void visibilizarCamposTesis(bool valor)
        {
            filaInstitucion.Visible = valor;
            filaAsesor.Visible = valor;
            filaEspecialidad.Visible = valor;
            filaGrado.Visible = valor;
        }
    }
}
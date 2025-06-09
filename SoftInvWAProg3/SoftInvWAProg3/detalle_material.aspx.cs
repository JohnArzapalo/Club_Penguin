using SoftInvWAProg3.BibliotecaWS;
using SoftInvWAProg3.EjemplarWS;
using SoftInvWAProg3.MaterialWS;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using System;

namespace SoftInvWAProg3
{
    public partial class detalle_material : System.Web.UI.Page
    {
        private MaterialWSClient materialCliente = new MaterialWSClient();
        private BibliotecaWSClient bibliotecaCliente = new BibliotecaWSClient();
        private EjemplarWSClient ejemplarCliente = new EjemplarWSClient();
        private int materialId;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!int.TryParse(Request.QueryString["id"], out materialId))
            {
                Response.Redirect("materiales.aspx");
                return;
            }

            if (!IsPostBack)
            {
                CargarDetalle(materialId, null);
                btnReservar.Visible = !EsBibliotecario;
                pnlOpcionesBibliotecario.Visible = EsBibliotecario;
            }
        }

        protected void ddlBibliotecaReserva_SelectedIndexChanged(object sender, EventArgs e)
        {
            btnReservar.Enabled = ddlBibliotecaReserva.SelectedIndex > 0;

            if (string.IsNullOrEmpty(ddlBibliotecaReserva.SelectedValue))
            {
                CargarDetalle(materialId, null);
                return;
            }

            if (int.TryParse(ddlBibliotecaReserva.SelectedValue, out int bibliotecaId))
                CargarDetalle(materialId, bibliotecaId);
        }

        private void CargarDetalle(int materialId, int? bibliotecaSeleccionadaId)
        {
            MaterialWS.materialDTO material = materialCliente.obtenerMaterialPorId(materialId);
            if (material == null) return;

            MostrarInformacionBasica(material);
            MostrarAtributosEspecificos(material);
            MostrarCopias(materialId);
            CargarBibliotecas(materialId, bibliotecaSeleccionadaId);
        }

        private void MostrarInformacionBasica(MaterialWS.materialDTO material)
        {
            litTitulo.Text = material.titulo;
            litAutor.Text = material.autor;
            litAnio.Text = material.anioPublicacion.ToString();
            litTipo.Text = ObtenerNombreMostrar(material.tipoMaterial);

            string icono = ObtenerIconoPorTipo(material.tipoMaterial);
            imgTipo.Src = icono;
            imgChipIcono.Src = icono;

            chipTipo.Attributes["class"] += " " + ObtenerClaseChip(material.tipoMaterial);
        }

        private void MostrarAtributosEspecificos(MaterialWS.materialDTO material)
        {
            string html = string.Empty;

            switch (material.tipoMaterial)
            {
                case MaterialWS.tipoMaterial.LIBRO:
                    libroDTO libro = material as libroDTO;
                    html = $@"
                        <p class='mb-1'><strong>ISBN</strong> {libro?.isbn ?? ""}</p>
                        <p class='mb-1'><strong>Editorial</strong> {libro?.editorial ?? ""}</p>
                        <p class='mb-1'><strong>Edición</strong> {libro?.edicion ?? ""}</p>";
                    break;

                case MaterialWS.tipoMaterial.ARTICULO:
                    articuloDTO articulo = material as articuloDTO;
                    html = $@"
                        <p class='mb-1'><strong>ISSN</strong> {articulo?.issn ?? ""}</p>
                        <p class='mb-1'><strong>Revista</strong> {articulo?.nombreRevista ?? ""}</p>
                        <p class='mb-1'><strong>Volumen</strong> {articulo?.volumen ?? ""}, Nº {articulo?.numero}</p>";
                    break;

                case MaterialWS.tipoMaterial.TESIS:
                    tesisDTO tesis = material as tesisDTO;
                    html = $@"
                        <p class='mb-1'><strong>Institución</strong> {tesis?.nombreInstitucionPublicacion ?? ""}</p>
                        <p class='mb-1'><strong>Asesor</strong> {tesis?.asesorTesis ?? ""}</p>
                        <p class='mb-1'><strong>Especialidad</strong> {tesis?.especialidad ?? ""}</p>
                        <p class='mb-1'><strong>Grado</strong> {tesis?.grado}</p>";
                    break;
            }

            phDetallesTipo.Controls.Clear();
            phDetallesTipo.Controls.Add(new Literal { Text = html });
        }

        private void MostrarCopias(int materialId)
        {
            int?[] copias = materialCliente.obtenerCopias(materialId);
            if (copias == null || copias.Length < 2) return;

            litCopias.Text = $@"
                <p class='mb-1'><strong>Copias</strong> {copias[0]}</p>
                <p class='mb-1'><strong>Disponibles</strong> {copias[1]}</p>";
        }

        private void CargarBibliotecas(int materialId, int? bibliotecaSeleccionadaId)
        {
            ddlBibliotecaReserva.Items.Clear();
            ddlBibliotecaReserva.Items.Add(
                new ListItem(EsBibliotecario ? "Selecciona biblioteca para filtrar" : "Selecciona biblioteca para reservar", "")
            );

            BibliotecaWS.bibliotecaDTO[] bibliotecas = bibliotecaCliente.obtenerBibliotecasPorMaterial(materialId);
            List<object> datos = new List<object>();

            if (bibliotecas != null && bibliotecas.Length > 0)
            {
                phSinBibliotecas.Visible = false;

                foreach (BibliotecaWS.bibliotecaDTO b in bibliotecas)
                {
                    ddlBibliotecaReserva.Items.Add(new ListItem(b.nombre, b.bibliotecaId.ToString()));

                    if (!bibliotecaSeleccionadaId.HasValue || b.bibliotecaId == bibliotecaSeleccionadaId.Value)
                    {
                        int?[] arreglo = materialCliente.obtenerEjemplaresReservadosYDisponibles(materialId, b.bibliotecaId);
                        EjemplarWS.ejemplarDTO[] ejemplares = ejemplarCliente.obtenerEjemplaresMaterialPorBiblioteca(materialId, b.bibliotecaId);

                        List<object> copias = CargarEjemplares(ejemplares);

                        datos.Add(new
                        {
                            Nombre = b.nombre,
                            Ubicacion = b.ubicacion,
                            Disponibles = arreglo[1],
                            Reservados = arreglo[0],
                            Copias = copias
                        });
                    }
                }

                ddlBibliotecaReserva.Enabled = true;

                if (bibliotecaSeleccionadaId.HasValue)
                {
                    ddlBibliotecaReserva.SelectedValue = bibliotecaSeleccionadaId.Value.ToString();
                }
            }
            else
            {
                phSinBibliotecas.Visible = true;
                ddlBibliotecaReserva.Enabled = false;
            }

            rptBibliotecas.DataSource = datos;
            rptBibliotecas.DataBind();

            HabilitarReserva(bibliotecaSeleccionadaId);
        }

        private void HabilitarReserva(int? bibliotecaSeleccionadaId)
        {
            btnReservar.Enabled = false;

            if (bibliotecaSeleccionadaId.HasValue)
            {
                int bibliotecaId = bibliotecaSeleccionadaId.Value;
                int?[] stats = materialCliente.obtenerEjemplaresReservadosYDisponibles(materialId, bibliotecaId);

                // Habilitar solo si hay ejemplares disponibles
                if (stats != null && stats[1].GetValueOrDefault() > 0)
                {
                    btnReservar.Enabled = true;
                }
            }

        }

        private List<object> CargarEjemplares(EjemplarWS.ejemplarDTO[] ejemplares)
        {
            List<object> resultado = new List<object>();

            foreach (EjemplarWS.ejemplarDTO e in ejemplares)
            {
                resultado.Add(new
                {
                    Codigo = e.ejemplarId,
                    Ubicacion = e.locacionEnBiblioteca,
                    Estado = ObtenerEstado(e.estado)
                });
            }

            return resultado;
        }

        private bool EsBibliotecario
        {
            get
            {
                return Session["rol"] != null && Session["rol"].ToString().Equals("Bibliotecario", StringComparison.OrdinalIgnoreCase);
            }
        }

        private string ObtenerNombreMostrar(MaterialWS.tipoMaterial tipo)
        {
            switch (tipo)
            {
                case MaterialWS.tipoMaterial.LIBRO: return "Libro";
                case MaterialWS.tipoMaterial.ARTICULO: return "Artículo";
                case MaterialWS.tipoMaterial.TESIS: return "Tesis";
                default: return "Desconocido";
            }
        }

        private string ObtenerIconoPorTipo(MaterialWS.tipoMaterial tipo)
        {
            switch (tipo)
            {
                case MaterialWS.tipoMaterial.LIBRO: return "Images/libro.svg";
                case MaterialWS.tipoMaterial.ARTICULO: return "Images/articulo.svg";
                case MaterialWS.tipoMaterial.TESIS: return "Images/tesis.svg";
                default: return "Images/default.svg";
            }
        }

        private string ObtenerClaseChip(MaterialWS.tipoMaterial tipo)
        {
            switch (tipo)
            {
                case MaterialWS.tipoMaterial.LIBRO: return "chip-libro";
                case MaterialWS.tipoMaterial.ARTICULO: return "chip-articulo";
                case MaterialWS.tipoMaterial.TESIS: return "chip-tesis";
                default: return string.Empty;
            }
        }

        protected string ObtenerEstado(EjemplarWS.estadoEjemplar estado)
        {
            switch (estado)
            {
                case EjemplarWS.estadoEjemplar.PRESTADO:
                    return "<span class='text-primary fw-semibold'>Prestado</span>";
                case EjemplarWS.estadoEjemplar.EN_REPARACION:
                    return "<span class='text-danger fw-semibold'>En reparación</span>";
                default:
                    return string.Empty;
            }
        }
    }
}

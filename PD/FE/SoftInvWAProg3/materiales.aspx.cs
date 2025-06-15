using System;
using System.Collections.Generic;

using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.servicios;


namespace SoftInvWAProg3
{
    public partial class materiales : System.Web.UI.Page
    {
        private MaterialWSClient materialCliente;
        private BibliotecaWSClient bibliotecaCliente;

        protected void Page_Load(object sender, EventArgs e)
        {
            materialCliente = new MaterialWSClient();
            bibliotecaCliente = new BibliotecaWSClient();

            if (!IsPostBack)
            {
                string rol = Session["rol"] as string;
                if (!string.IsNullOrEmpty(rol) && rol.Equals("Bibliotecario", StringComparison.OrdinalIgnoreCase))
                {
                    phBotonRegistrar.Visible = true;
                }
                divResultados.Visible = true;
            }
            else
            {
                divResultados.Visible = false;
            }
        }

        protected void btnFiltroTema_Click(object sender, EventArgs e)
        {
            string tema = hdnTema.Value; // Ejemplo: "Base de Datos"

            // Búsqueda exacta por un solo tipo
            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorTema(tema);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        protected void btnFiltroIdioma_Click(object sender, EventArgs e)
        {
            string idioma = hdnIdioma.Value; // "Español" o "Inglés"

            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorIdioma(idioma);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        protected void btnFiltroTipoMaterial_Click(object sender, EventArgs e)
        {
            string tipoSeleccionado = hdnTipoMaterial.Value; // Por ejemplo: "Libro"

            // Búsqueda exacta por un solo tipo
            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorTipoMaterial(tipoSeleccionado);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        protected void btnFiltroAnio_Click(object sender, EventArgs e)
        {
            string anio = hdnAnioDesde.Value;

            // Convertir el array retornado por buscarPorAutor a una lista
            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorAnio(anio);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        private void ProcesarFiltroAutor()
        {
            string autor = hdnAutores.Value;

            // Aquí puedes aplicar tu lógica de filtrado parcial, sin hacer búsqueda avanzada completa
            // Ejemplo: filtrar resultados ya cargados, hacer una búsqueda parcial, etc.
        }
        protected void btnFiltroDisponibilidad_Click(object sender, EventArgs e)
        {
            string estado = hdnDisponibilidad.Value; // "Disponible", "Prestado" o "En reparacion"

            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorDisponibilidad(estado);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        protected void btnFiltroAutor_Click(object sender, EventArgs e)
        {
            string autor = hdnAutores.Value;

            // Convertir el array retornado por buscarPorAutor a una lista
            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorAutor(autor);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }
        protected void btnFiltroBiblioteca_Click(object sender, EventArgs e)
        {
            string biblioteca = hdnBiblioteca.Value; // Ejemplo: "Biblioteca Central"

            // Convertir el array retornado por buscarPorAutor a una lista
            materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorBiblioteca(biblioteca);

            rptMateriales.DataSource = resultadoFiltradoArray;

            rptMateriales.DataBind();
        }

        protected void btnBusquedaAvanzadaServidor_Click(object sender, EventArgs e)
        {
            try
            {
                // Crear DTOs
                materialDTO MaterialDTO = new materialDTO();
                bibliotecaDTO BibliotecaDTO = new bibliotecaDTO();
                ejemplarDTO EjemplarDTO = new ejemplarDTO();

                // Obtener valores de los campos ocultos
                string titulo = hdnTitulo.Value;
                string autores = hdnAutores.Value;
                string tema = hdnTema.Value;
                string anioDesdeStr = hdnAnioDesde.Value;
                string tipoMaterialTexto = hdnTipoMaterial.Value;
                string biblioteca = hdnBiblioteca.Value;
                string idioma = hdnIdioma.Value;
                string disponibilidadTexto = hdnDisponibilidad.Value;

                Session["disponibilidadActual"] = disponibilidadTexto;
                Session["bibliotecaActual"] = biblioteca;

                // Asignar valores básicos al DTO
                MaterialDTO.titulo = titulo;
                MaterialDTO.autor = autores;
                MaterialDTO.tema = tema;
                MaterialDTO.idioma = idioma;
                BibliotecaDTO.nombre = biblioteca;

                // Procesar año desde
                if (!string.IsNullOrEmpty(anioDesdeStr) && int.TryParse(anioDesdeStr, out int anioDesdeInt))
                {
                    if (anioDesdeInt > 0 && anioDesdeInt <= 2025)
                    {
                        MaterialDTO.anioPublicacion = anioDesdeInt;
                    }
                }

                // Procesar tipo de material
                if (!string.IsNullOrEmpty(tipoMaterialTexto) && Enum.TryParse(tipoMaterialTexto, out tipoMaterial tipoMaterialEnum))
                {
                    MaterialDTO.tipoMaterial = tipoMaterialEnum;
                }

                // Procesar disponibilidad
                if (!string.IsNullOrEmpty(disponibilidadTexto) && Enum.TryParse(disponibilidadTexto, out estadoEjemplar estadoEnum))
                {
                    EjemplarDTO.estado = estadoEnum;
                }

                // Llamar al servicio web
                var resultados = materialCliente.materialBusquedaAvanzada(MaterialDTO, BibliotecaDTO, EjemplarDTO, anioDesdeStr, tipoMaterialTexto, disponibilidadTexto);

                // Si el servicio retorna un solo objeto, crear una lista
                List<MaterialDisplay> materialesDisplay = new List<MaterialDisplay>();

                if (resultados != null)
                {
                    materialesDisplay.Add(new MaterialDisplay
                    {
                        MaterialId = resultados.materialId,
                        Titulo = resultados.titulo ?? "Sin título",
                        Autor = resultados.autor ?? "Sin autor",
                        AnioPublicacion = resultados.anioPublicacion,
                        TipoMaterial = resultados.tipoMaterial.ToString(),
                        NumeroPaginas = resultados.numeroPaginas,
                        Tema = resultados.tema ?? "Sin tema",
                        Idioma = resultados.idioma ?? "Sin idioma"
                    });
                }

                // Vincular al Repeater
                rptMateriales.DataSource = materialesDisplay;
                rptMateriales.DataBind();

                // Mostrar mensaje si no hay resultados
                if (materialesDisplay.Count == 0)
                {
                    divResultados.Controls.Clear();
                    divResultados.Controls.Add(new Literal
                    {
                        Text = @"<div class='text-center text-muted mt-5'>
                                   <i class='bi bi-search' style='font-size: 3rem;'></i>
                                   <p class='mt-3'>No se encontraron materiales con los criterios especificados</p>
                                </div>"
                    });
                }
                else
                {
                    divResultados.Controls.Clear();
                }
            }
            catch (System.Exception ex)
            {
                divResultados.Controls.Clear();
                divResultados.Controls.Add(new Literal
                {
                    Text = $@"<div class='alert alert-danger' role='alert'>
                               <i class='bi bi-exclamation-triangle'></i>
                               Error al realizar la búsqueda: {ex.Message}
                             </div>"
                });
            }
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            string titulo = txtBuscar.Text.Trim();

            if (string.IsNullOrEmpty(titulo))
            {
                MostrarMensajeInicial();
                return;
            }

            materialDTO[] resultadoObtenido = materialCliente.buscarPorTitulo(titulo);

            List<materialDTO> resultados = resultadoObtenido != null
                ? new List<materialDTO>(resultadoObtenido)
                : new List<materialDTO>();

            if (resultados.Count > 0)
            {
                MostrarResultados(resultados);
            }
            else
            {
                MostrarMensajeNoEncontrado();
            }
        }

        private void MostrarResultados(List<materialDTO> resultados)
        {
            rptMateriales.DataSource = resultados;
            rptMateriales.DataBind();

        }

        private void MostrarMensajeInicial()
        {
            rptMateriales.DataSource = null;
            rptMateriales.DataBind();

        }

        private void MostrarMensajeNoEncontrado()
        {
            rptMateriales.DataSource = null;
            rptMateriales.DataBind();

        }

        protected void rptMateriales_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalle")
            {
                int materialId = Convert.ToInt32(e.CommandArgument);
                Response.Redirect("detalle_material.aspx?id=" + materialId);
            }
        }

        protected string ObtenerIconoPorTipo(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "Images/libro.svg";
                case "ARTICULO": return "Images/articulo.svg";
                case "TESIS": return "Images/tesis.svg";
                default: return "Images/default.svg";
            }
        }

        protected string ObtenerClaseChip(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "chip-libro";
                case "ARTICULO": return "chip-articulo";
                case "TESIS": return "chip-tesis";
                default: return string.Empty;
            }
        }

        protected string ObtenerNombreMostrar(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "Libro";
                case "ARTICULO": return "Artículo";
                case "TESIS": return "Tesis";
                default: return tipo;
            }
        }

        protected string ObtenerTextoDisponibilidad(object materialIdObj)
        {
            int materialId = Convert.ToInt32(materialIdObj);
            int?[] copias = materialCliente.obtenerCopias(materialId);

            if (copias != null && copias.Length > 1)
            {
                int disponibles = copias[1].GetValueOrDefault();
                if (disponibles > 0)
                {
                    return $"<span class='text-success fw-semibold'>Disponible ({disponibles})</span>";
                }
                else
                {
                    return "<span class='text-danger fw-semibold'>No disponible</span>";
                }
            }

            return "<span class='text-muted'>No disponible</span>";
        }

        protected string ObtenerTextoBibliotecas(object materialIdObj)
        {
            int materialId = Convert.ToInt32(materialIdObj);
            bibliotecaDTO[] bibliotecas = bibliotecaCliente.obtenerBibliotecasPorMaterial(materialId);

            if (bibliotecas != null && bibliotecas.Length > 0)
            {
                List<string> nombres = new List<string>();
                foreach (bibliotecaDTO b in bibliotecas)
                {
                    nombres.Add(b.nombre);
                }
                return string.Join(", ", nombres);
            }

            return "No disponible";
        }

        // Clase auxiliar para mostrar los datos en el Repeater
        public class MaterialDisplay
        {
            public int MaterialId { get; set; }
            public string Titulo { get; set; }
            public string Autor { get; set; }
            public int AnioPublicacion { get; set; }
            public string TipoMaterial { get; set; }
            public int NumeroPaginas { get; set; }
            public string Tema { get; set; }
            public string Idioma { get; set; }
        }
    }
}

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
                CargarIdiomasAvanzado();
                string rol = Session["rol"] as string;
                if (!string.IsNullOrEmpty(rol) && rol.Equals("Bibliotecario", StringComparison.OrdinalIgnoreCase))
                {
                    phBotonRegistrar.Visible = true;
                }
                divResultados.Visible = true;
                // Asegurar que los filtros estén deshabilitados al cargar la página
                ScriptManager.RegisterStartupScript(this, GetType(), "inicializarFiltros",
                    "busquedaEjecutada = false; validarAccesoFiltros();", true);
            }
            else
            {
                divResultados.Visible = false;

            }
        }

        private void CargarIdiomasAvanzado()
        {
            // Ejemplo con datos estáticos (reemplazar con tu fuente de datos)
            string[] idiomasArray = materialCliente.obtenerIdiomasAvanzada();
            List<string> idiomas = new List<string>(idiomasArray); // Convertir el array a una lista
            ddlIdiomaAvanzado.Items.Clear();
            ddlIdiomaAvanzado.DataSource = idiomas;
            ddlIdiomaAvanzado.DataBind();
            ddlIdiomaAvanzado.Items.Insert(0, new ListItem("", ""));
        }
        private void CargarTemas()
        {
            ddlTema.Items.Clear();

            // Insertar ítem por defecto
            ddlTema.Items.Add(new ListItem("Selecciona un tema", ""));





            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            var temas = new List<String>();

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    String tema = materialCliente.obtenerTemas(idMaterialStr);

                    if (!string.IsNullOrWhiteSpace(tema) && !temas.Contains(tema))
                    {
                        temas.Add(tema);
                    }
                }
            }


            if (temas.Count > 0)
            {
                foreach (string tema in temas)
                {
                    ddlTema.Items.Add(new ListItem(tema, tema));
                }
            }

            // Asegurarse de que quede seleccionado el ítem por defecto
            ddlTema.SelectedIndex = 0;
        }


        private void CargarIdiomas()
        {

            ddlIdioma.Items.Clear();

            // Insertar ítem por defecto
            ddlIdioma.Items.Add(new ListItem("Selecciona un tema", ""));

            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            var idiomas = new List<String>();

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    String idioma = materialCliente.obtenerIdiomas(idMaterialStr);

                    if (!string.IsNullOrWhiteSpace(idioma) && !idiomas.Contains(idioma))
                    {
                        idiomas.Add(idioma);
                    }
                }
            }


            if (idiomas.Count > 0)
            {
                foreach (string idioma in idiomas)
                {
                    ddlIdioma.Items.Add(new ListItem(idioma, idioma));
                }
            }

            // Asegurarse de que quede seleccionado el ítem por defecto
            ddlIdioma.SelectedIndex = 0;

        }


        protected void ddlTema_SelectedIndexChanged(object sender, EventArgs e)
        {
            string temaSeleccionado = ddlTema.SelectedValue;
            hdnTema.Value = temaSeleccionado;

            // Ejecutar filtro
            btnFiltroTema_Click(null, null);

            // Resetear el valor seleccionado a "Selecciona un tema"
            ddlTema.SelectedIndex = 0;
        }
        protected void ddlIdioma_SelectedIndexChanged(object sender, EventArgs e)
        {
            string idiomaSeleccionado = ddlIdioma.SelectedValue;
            hdnIdioma.Value = idiomaSeleccionado;

            // Ejecutar filtro
            btnFiltroIdioma_Click(null, null);

            // Resetear el valor seleccionado a "Selecciona un idioma"
            ddlIdioma.SelectedIndex = 0;
        }

        protected void btnFiltroTema_Click(object sender, EventArgs e)
        {
            string tema = hdnTema.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorTema(tema, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con tema: {tema}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                // Lógica normal cuando no hay búsqueda previa
                System.Diagnostics.Debug.WriteLine("ENTRAAAAAAAAA");
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorTema(tema);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroIdioma_Click(object sender, EventArgs e)
        {
            string idioma = hdnIdioma.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorIdioma(idioma, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con idioma: {idioma}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorIdioma(idioma);
                System.Diagnostics.Debug.WriteLine("ENTRAAAAAAAAA");
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroTipoMaterial_Click(object sender, EventArgs e)
        {
            string tipoSeleccionado = hdnTipoMaterial.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorTipoMaterial(tipoSeleccionado, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con tipo de material: {tipoSeleccionado}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorTipoMaterial(tipoSeleccionado);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroAnio_Click(object sender, EventArgs e)
        {
            string anioDesde = hdnAnioDesde.Value;
            string anioHasta = hdnAnioHasta.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorAnio(anioDesde, anioHasta, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con anio: {anioDesde} {anioHasta}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorAnio(anioDesde, anioHasta);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroDisponibilidad_Click(object sender, EventArgs e)
        {
            string estado = hdnDisponibilidad.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorDisponibilidad(estado, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con disponibilidad: {estado}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorDisponibilidad(estado);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroAutor_Click(object sender, EventArgs e)
        {
            string autor = hdnAutores.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorAutor(autor, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con autor: {autor}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);
            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorAutor(autor);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnFiltroBiblioteca_Click(object sender, EventArgs e)
        {
            string biblioteca = hdnBiblioteca.Value;
            var resultadosGuardados = Session["resultadosBusquedaSimple"] as List<materialDTO>;

            if (resultadosGuardados != null && resultadosGuardados.Count > 0)
            {
                var resultadosFiltrados = new List<materialDTO>();

                foreach (var material in resultadosGuardados)
                {
                    // Obtener el ID del material actual
                    string idMaterialStr = material.materialId.ToString();

                    // Aquí puedes hacer lo que necesites con cada ID
                    System.Diagnostics.Debug.WriteLine($"Procesando material ID: {idMaterialStr}");

                    // Aplicar el filtro
                    if (materialCliente.comprobarPorBiblioteca(biblioteca, idMaterialStr))
                    {
                        resultadosFiltrados.Add(material);
                        System.Diagnostics.Debug.WriteLine($"Material ID {idMaterialStr} coincide con biblioteca: {biblioteca}");
                    }
                }

                Session["resultadosBusquedaSimple"] = resultadosFiltrados;
                MostrarResultados(resultadosFiltrados);

            }
            else
            {
                materialDTO[] resultadoFiltradoArray = materialCliente.buscarPorBiblioteca(biblioteca);
                rptMateriales.DataSource = resultadoFiltradoArray;
                rptMateriales.DataBind();
            }
        }
        protected void btnBusquedaAvanzadaServidor_Click(object sender, EventArgs e)
        {
            try
            {
                // Crear DTOs vacíos
                materialDTO MaterialDTO = new materialDTO();
                bibliotecaDTO BibliotecaDTO = new bibliotecaDTO();
                ejemplarDTO EjemplarDTO = new ejemplarDTO();

                // Obtener valores desde los campos ocultos (pueden venir vacíos y está permitido)
                string titulo = hdnTitulo.Value?.Trim() ?? "";
                string autores = hdnAutores.Value?.Trim() ?? "";
                string tema = hdnTema.Value?.Trim() ?? "";
                string anioDesde = hdnAnioDesde.Value?.Trim() ?? "";
                string anioHasta = hdnAnioHasta.Value?.Trim() ?? "";
                string tipoMaterialTexto = hdnTipoMaterial.Value?.Trim() ?? "";
                string biblioteca = hdnBiblioteca.Value?.Trim() ?? "";
                string idioma = hdnIdioma.Value?.Trim() ?? "";
                string disponibilidadTexto = hdnDisponibilidad.Value?.Trim() ?? "";

                System.Diagnostics.Debug.WriteLine($"Título: {titulo}");
                System.Diagnostics.Debug.WriteLine($"Autores: {autores}");
                System.Diagnostics.Debug.WriteLine($"Tema: {tema}");
                System.Diagnostics.Debug.WriteLine($"Año desde: {anioDesde}");
                System.Diagnostics.Debug.WriteLine($"Año hasta: {anioHasta}");
                System.Diagnostics.Debug.WriteLine($"Tipo de material: {tipoMaterialTexto}");
                System.Diagnostics.Debug.WriteLine($"Biblioteca: {biblioteca}");
                System.Diagnostics.Debug.WriteLine($"Idioma: {idioma}");
                System.Diagnostics.Debug.WriteLine($"Disponibilidad: {disponibilidadTexto}");

                // Guardar algunos datos en sesión si aplica
                Session["disponibilidadActual"] = disponibilidadTexto;
                Session["bibliotecaActual"] = biblioteca;

                // Asignar campos directamente, aunque estén vacíos
                MaterialDTO.titulo = titulo;
                MaterialDTO.autor = autores;
                MaterialDTO.tema = tema;
                MaterialDTO.idioma = idioma;
                BibliotecaDTO.nombre = biblioteca;

                // Si el añoDesde es válido, asignar (sino, dejarlo vacío)
                if (int.TryParse(anioDesde, out int anioDesdeInt) && anioDesdeInt > 0 && anioDesdeInt <= 2100)
                {
                    MaterialDTO.anioPublicacion = anioDesdeInt;
                }

                // Si el tipo de material es válido, asignar (sino, dejarlo vacío)
                if (Enum.TryParse(tipoMaterialTexto, out tipoMaterial tipoEnum))
                {
                    MaterialDTO.tipoMaterial = tipoEnum;
                }

                // Si la disponibilidad es válida, asignar (sino, dejarlo vacío)
                if (Enum.TryParse(disponibilidadTexto, out estadoEjemplar estadoEnum))
                {
                    EjemplarDTO.estado = estadoEnum;
                }

                // Llamar al servicio web (pasando todos los campos, aunque estén vacíos)
                var resultadoObtenido = materialCliente.materialBusquedaAvanzada(
                    MaterialDTO, BibliotecaDTO, EjemplarDTO, anioDesde, anioHasta, tipoMaterialTexto, disponibilidadTexto);

                // Convertir a List<materialDTO> para consistencia con otras búsquedas
                List<materialDTO> resultados = resultadoObtenido != null
                    ? new List<materialDTO>(resultadoObtenido)
                    : new List<materialDTO>();

                // Guardar los resultados en sesión para que los filtros puedan usarlos
                Session["resultadosBusquedaSimple"] = resultados;

                // Usar las funciones auxiliares existentes
                if (resultados.Count > 0)
                {
                    MostrarResultados(resultados);
                }
                else
                {
                    MostrarMensajeNoEncontrado();
                }
            }
            catch (System.Exception ex)
            {
                // Limpiar sesión en caso de error
                Session["resultadosBusquedaSimple"] = null;

                divResultados.Controls.Clear();
                divResultados.Controls.Add(new Literal
                {
                    Text = $@"<div class='alert alert-danger' role='alert'>
               <i class='bi bi-exclamation-triangle'></i>
               Error al realizar la búsqueda: {ex.Message}
             </div>"
                });

                // Deshabilitar filtros por el error
                ScriptManager.RegisterStartupScript(this, GetType(), "bloquearFiltrosPorError",
                    "busquedaEjecutada = false; validarAccesoFiltros();", true);
            }
        }
        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            string titulo = txtBuscar.Text.Trim();

            if (string.IsNullOrEmpty(titulo))
            {
                // Limpiar sesión al hacer búsqueda vacía
                Session["resultadosBusquedaSimple"] = null;
                MostrarMensajeInicial();
                return;
            }

            materialDTO[] resultadoObtenido = materialCliente.buscarPorTitulo(titulo);

            List<materialDTO> resultados = resultadoObtenido != null
                ? new List<materialDTO>(resultadoObtenido)
                : new List<materialDTO>();

            // CLAVE: Guardar los resultados en sesión para que los filtros puedan usarlos
            Session["resultadosBusquedaSimple"] = resultados;

            if (resultados.Count > 0)
            {

                MostrarResultados(resultados);
                MarcarBusquedaEjecutada();
            }
            else
            {
                MostrarMensajeNoEncontrado();
            }
        }


        private void MostrarResultados(List<materialDTO> resultados)
        {
            // Verificar primero si hay resultados ANTES del DataBind
            if (resultados == null || resultados.Count == 0)
            {
                MostrarMensajeNoEncontrado();
                return; // Salir del método sin ejecutar el resto
            }

            // Solo si hay resultados, proceder con el DataBind
            rptMateriales.DataSource = resultados;
            rptMateriales.DataBind();

            ScriptManager.RegisterStartupScript(this, GetType(), "habilitarFiltros",
                "marcarBusquedaEjecutada();", true);

            CargarTemas();
            CargarIdiomas();
            ddlTema.SelectedIndex = 0;
            ddlIdioma.SelectedIndex = 0;
        }

        private void MostrarMensajeInicial()
        {
            rptMateriales.DataSource = null;
            rptMateriales.DataBind();

        }


        private void MostrarMensajeNoEncontrado()
        {
            // Limpiar el Repeater completamente
            rptMateriales.DataSource = null;
            rptMateriales.DataBind();

            // Limpiar el div de resultados
            divResultados.Controls.Clear();

            // Agregar el mensaje de "no encontrado"
            divResultados.Controls.Add(new Literal
            {
                Text = @"<div class='text-center text-muted mt-5'>
                   <i class='bi bi-search' style='font-size: 3rem;'></i>
                   <p class='mt-3'>No se encontraron materiales con los criterios especificados</p>
                 </div>"
            });

            // Hacer visible el div (por si no lo estaba)
            divResultados.Visible = true;

            // Script para deshabilitar los filtros porque no hay resultados
            ScriptManager.RegisterStartupScript(this, GetType(), "bloquearFiltrosNoResultados",
                "busquedaEjecutada = false; validarAccesoFiltros();", true);
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
        private void MarcarBusquedaEjecutada()
        {
            ViewState["BusquedaEjecutada"] = true;
            ScriptManager.RegisterStartupScript(this, GetType(), "marcarBusqueda",
                "marcarBusquedaEjecutada();", true);
        }
    }
}

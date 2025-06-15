using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftInvWAProg3.MaterialWS;
using SoftInvWAProg3.BibliotecaWS;
using SoftInvWAProg3.EjemplarWS;
using System.Linq;

namespace SoftInvWAProg3
{
    public partial class registrar_material : Page
    {
        private MaterialWSClient materialCliente = new MaterialWSClient();
        private BibliotecaWSClient bibliotecaCliente = new BibliotecaWSClient();
        private EjemplarWSClient ejemplarCliente = new EjemplarWSClient();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CantidadEjemplares = 1;
            }

            lblCantidadEjemplares.Text = CantidadEjemplares.ToString();
            btnMenosEjemplar.Enabled = CantidadEjemplares > 1;

            CargarCamposTipo(ddlTipoMaterial.SelectedValue);
            CargarEjemplaresDesdeViewState();

            ScriptManager.RegisterStartupScript(this, GetType(), "validar", "setTimeout(activarEventosDinamicos, 100);", true);
        }

        private int CantidadEjemplares
        {
            get => ViewState["CantidadEjemplares"] != null ? (int)ViewState["CantidadEjemplares"] : 1;
            set => ViewState["CantidadEjemplares"] = value;
        }

        private void CargarEjemplaresDesdeViewState(List<EjemplarWS.ejemplarDTO> valores = null)
        {
            List<BibliotecaWS.bibliotecaDTO> bibliotecas = new List<BibliotecaWS.bibliotecaDTO>(bibliotecaCliente.listarBibliotecas());
            phEjemplares.Controls.Clear();

            for (int i = 1; i <= CantidadEjemplares; i++)
            {
                string idBiblio = $"ddlBiblioteca_{i}";
                string idLoc = $"txtLocacion_{i}";
                string idEstado = $"ddlEstado_{i}";

                // Obtener los valores del ejemplar en la lista, si está disponible
                string valorBiblio = valores != null && i <= valores.Count && valores[i - 1].biblioteca != null
                    ? valores[i - 1].biblioteca.bibliotecaId.ToString()
                    : null;

                string valorLoc = valores != null && i <= valores.Count
                    ? valores[i - 1].locacionEnBiblioteca
                    : null;

                string valorEstado = valores != null && i <= valores.Count
                    ? valores[i - 1].estado.ToString()
                    : null;

                Panel panel = new Panel { CssClass = "ejemplar-block" };
                panel.Controls.Add(new Literal { Text = $"<h6 class='mb-3'>Ejemplar {i}</h6>" });

                // DropDownList de biblioteca
                DropDownList ddl = new DropDownList { ID = idBiblio, CssClass = "form-select mb-2" };
                ddl.Items.Add(new ListItem("Selecciona una biblioteca", ""));
                foreach (BibliotecaWS.bibliotecaDTO b in bibliotecas)
                {
                    ddl.Items.Add(new ListItem(b.nombre, b.bibliotecaId.ToString()));
                }
                if (!string.IsNullOrEmpty(valorBiblio))
                {
                    ddl.SelectedValue = valorBiblio;
                }
                panel.Controls.Add(ddl);

                // TextBox de locación
                Panel contenedorLoc = new Panel { CssClass = "form-group-material mb-2" };
                TextBox txtLoc = new TextBox { ID = idLoc, CssClass = "form-control" };
                txtLoc.Attributes["placeholder"] = " ";
                if (!string.IsNullOrEmpty(valorLoc))
                {
                    txtLoc.Text = valorLoc;
                }
                Label lblLoc = new Label { AssociatedControlID = idLoc, Text = "Locación en biblioteca" };
                contenedorLoc.Controls.Add(txtLoc);
                contenedorLoc.Controls.Add(lblLoc);
                panel.Controls.Add(contenedorLoc);

                // DropDownList de estado
                DropDownList ddlEstado = new DropDownList { ID = idEstado, CssClass = "form-select mb-2" };
                ddlEstado.Items.Add(new ListItem("Selecciona el estado del ejemplar", ""));
                ddlEstado.Items.Add(new ListItem("Disponible", EjemplarWS.estadoEjemplar.DISPONIBLE.ToString()));
                ddlEstado.Items.Add(new ListItem("Prestado", EjemplarWS.estadoEjemplar.PRESTADO.ToString()));
                ddlEstado.Items.Add(new ListItem("En reparación", EjemplarWS.estadoEjemplar.EN_REPARACION.ToString()));

                if (!string.IsNullOrEmpty(valorEstado))
                {
                    ddlEstado.SelectedValue = valorEstado;
                }
                else
                {
                    ddlEstado.SelectedValue = EjemplarWS.estadoEjemplar.DISPONIBLE.ToString();
                }

                panel.Controls.Add(ddlEstado);
                phEjemplares.Controls.Add(panel);
            }
        }

        protected void ddlTipoMaterial_SelectedIndexChanged(object sender, EventArgs e)
        {
            string tipoSeleccionado = ddlTipoMaterial.SelectedValue;
            if (!string.IsNullOrEmpty(tipoSeleccionado))
            {
                CargarCamposTipo(tipoSeleccionado);
            }
        }

        private void CargarCamposTipo(string tipo)
        {
            phCamposTipo.Controls.Clear();

            void AgregarCampo(string id, string labelText)
            {
                Panel grupo = new Panel { CssClass = "form-group-material mb-3" };

                TextBox txt = new TextBox
                {
                    ID = id,
                    CssClass = "form-control",
                    TextMode = TextBoxMode.SingleLine
                };
                txt.Attributes["placeholder"] = " ";

                Label lbl = new Label
                {
                    AssociatedControlID = id,
                    Text = labelText
                };

                grupo.Controls.Add(txt);
                grupo.Controls.Add(lbl);
                phCamposTipo.Controls.Add(grupo);
            }

            switch (tipo)
            {
                case "LIBRO":
                    AgregarCampo("txtISBN", "ISBN");
                    AgregarCampo("txtEditorial", "Editorial");
                    AgregarCampo("txtEdicion", "Edición");
                    break;

                case "ARTICULO":
                    AgregarCampo("txtISSN", "ISSN");
                    AgregarCampo("txtRevista", "Revista");
                    AgregarCampo("txtEditorial", "Editorial");
                    AgregarCampo("txtVolumen", "Volumen");
                    AgregarCampo("txtNumero", "Número");
                    break;

                case "TESIS":
                    AgregarCampo("txtInstitucion", "Institución de publicación");
                    AgregarCampo("txtAsesor", "Asesor");
                    AgregarCampo("txtEspecialidad", "Especialidad");
                    AgregarCampo("txtGrado", "Grado");
                    break;
            }
        }

        private void AgregarEjemplarUI(int index, List<BibliotecaWS.bibliotecaDTO> bibliotecas, bool soloUno)
        {
            Panel panel = new Panel { CssClass = "ejemplar-block" };
            panel.Controls.Add(new Literal { Text = $"<h6 class='mb-3'>Ejemplar {index}</h6>" });

            DropDownList ddl = new DropDownList { ID = $"ddlBiblioteca_{index}", CssClass = "form-select mb-2" };
            ddl.Items.Add(new ListItem("Selecciona una biblioteca", ""));
            foreach (var b in bibliotecas)
            {
                ddl.Items.Add(new ListItem(b.nombre, b.bibliotecaId.ToString()));
            }
            panel.Controls.Add(ddl);

            Panel contenedorLocacion = new Panel { CssClass = "form-group-material mb-2" };
            TextBox txtLoc = new TextBox {ID = $"txtLocacion_{index}", CssClass = "form-control"};
                    txtLoc.Attributes["placeholder"] = " ";
            Label lblLoc = new Label {AssociatedControlID = txtLoc.ID, Text = "Locación en biblioteca"};
            contenedorLocacion.Controls.Add(txtLoc);
            contenedorLocacion.Controls.Add(lblLoc);
            panel.Controls.Add(contenedorLocacion);

            DropDownList ddlEstado = new DropDownList { ID = $"ddlEstado_{index}", CssClass = "form-select mb-2" };
            ddlEstado.Items.Add(new ListItem("Selecciona el estado del ejemplar", ""));
            ddlEstado.Items.Add(new ListItem("Disponible", EjemplarWS.estadoEjemplar.DISPONIBLE.ToString()));
            ddlEstado.Items.Add(new ListItem("Prestado", EjemplarWS.estadoEjemplar.PRESTADO.ToString()));
            ddlEstado.Items.Add(new ListItem("En reparación", EjemplarWS.estadoEjemplar.EN_REPARACION.ToString()));
            ddlEstado.SelectedValue = EjemplarWS.estadoEjemplar.DISPONIBLE.ToString();
            panel.Controls.Add(ddlEstado);

            phEjemplares.Controls.Add(panel);
        }

        protected void btnRegistrar_Click(object sender, EventArgs e)
        {
            // Validar y construir DTO de material
            string tipo = ddlTipoMaterial.SelectedValue;
            if (string.IsNullOrEmpty(tipo)) return;

            string titulo = txtTitulo.Text.Trim();
            string autor = txtAutores.Text.Trim();
            int anio;
            if (!int.TryParse(txtAnio.Text, out anio) || anio < 1000 || anio > DateTime.Now.Year)
            {
                System.Diagnostics.Debug.WriteLine(anio);
                return;
            }
            string anioStr = txtAnio.Text.Trim();
            string numeroPaginasStr = txtPaginas.Text.Trim();
            string tema = txtTema.Text.Trim();
            string idioma = txtIdioma.Text.Trim();

            string isbnLibro = string.Empty,
                   editorialLibro = string.Empty,
                   edicionLibro = string.Empty,
                   issnArticulo = string.Empty,
                   revistaArticulo = string.Empty,
                   editorialArticulo = string.Empty,
                   volumenArticulo = string.Empty,
                   numeroArticulo = string.Empty,
                   institucionTesis = string.Empty,
                   asesorTesis = string.Empty,
                   especialidadTesis = string.Empty,
                   gradoTesis = string.Empty;

            switch (tipo)
            {
                case "LIBRO":
                    isbnLibro = Request.Form["txtISBN"];
                    editorialLibro = Request.Form["txtEditorial"];
                    edicionLibro = Request.Form["txtEdicion"];
                    break;
                case "ARTICULO":
                    issnArticulo = Request.Form["txtISSN"];
                    revistaArticulo = Request.Form["txtRevista"];
                    editorialArticulo = Request.Form["txtEditorial"];
                    volumenArticulo = Request.Form["txtVolumen"];
                    numeroArticulo = Request.Form["txtNumero"];
                    break;
                case "TESIS":
                    institucionTesis = Request.Form["txtInstitucion"];
                    asesorTesis = Request.Form["txtAsesor"];
                    especialidadTesis = Request.Form["txtEspecialidad"];
                    gradoTesis = Request.Form["txtGrado"];
                    break;
            }

            // SOLUCIÓN ROBUSTA: Buscar ejemplares por patrón independientemente del prefijo
            List<string[]> ejemplares = new List<string[]>();

            // Obtener todas las claves que terminan en "ddlBiblioteca_" seguido de un número
            var bibliotecaKeys = Request.Form.AllKeys
                .Where(key => key.Contains("ddlBiblioteca_"))
                .OrderBy(key =>
                {
                    // Extraer el número del final para ordenar correctamente
                    string numero = key.Substring(key.LastIndexOf("_") + 1);
                    return int.TryParse(numero, out int n) ? n : 0;
                })
                .ToList();

            System.Diagnostics.Debug.WriteLine($"Claves de biblioteca encontradas: {bibliotecaKeys.Count}");

            foreach (string bibliotecaKey in bibliotecaKeys)
            {
                // Extraer el índice del nombre del control
                string indexStr = bibliotecaKey.Substring(bibliotecaKey.LastIndexOf("_") + 1);

                if (int.TryParse(indexStr, out int index))
                {
                    // Construir los nombres de los otros controles basándose en el patrón
                    string prefijo = bibliotecaKey.Substring(0, bibliotecaKey.LastIndexOf("ddlBiblioteca_"));

                    string bId = Request.Form[bibliotecaKey];
                    string loc = Request.Form[$"{prefijo}txtLocacion_{index}"];
                    string estado = Request.Form[$"{prefijo}ddlEstado_{index}"];

                    // DEPURACIÓN: Mostrar valores leídos
                    System.Diagnostics.Debug.WriteLine($"Índice {index}: BibliotecaId={bId}, Locacion={loc}, Estado={estado}");

                    // Solo agregar si la biblioteca tiene un valor válido
                    if (!string.IsNullOrEmpty(bId))
                    {
                        string[] ejemplarData = new string[3];
                        ejemplarData[0] = bId;
                        ejemplarData[1] = loc ?? string.Empty;
                        ejemplarData[2] = estado ?? string.Empty;
                        ejemplares.Add(ejemplarData);
                    }
                }
            }

            // DEPURACIÓN: Verificar cuántos ejemplares se leyeron
            System.Diagnostics.Debug.WriteLine($"Total de ejemplares leídos: {ejemplares.Count}");

            // Mostrar todos los elementos del Request.Form para depuración
            System.Diagnostics.Debug.WriteLine("=== TODOS LOS ELEMENTOS EN REQUEST.FORM ===");
            foreach (string key in Request.Form.AllKeys)
            {
                if (key.Contains("Biblioteca") || key.Contains("Locacion") || key.Contains("Estado"))
                {
                    System.Diagnostics.Debug.WriteLine($"{key} = {Request.Form[key]}");
                }
            }
            System.Diagnostics.Debug.WriteLine("=== FIN ELEMENTOS REQUEST.FORM ===");

            // Registrar material y ejemplares
            System.Diagnostics.Debug.WriteLine("Insertando material...");
            int nuevoId = materialCliente.insertarMaterial(titulo, autor, tema, idioma, tipo, anioStr, numeroPaginasStr,
                    isbnLibro, editorialLibro, edicionLibro, issnArticulo, revistaArticulo, editorialArticulo
                , volumenArticulo, numeroArticulo, institucionTesis, asesorTesis, especialidadTesis, gradoTesis);

            System.Diagnostics.Debug.WriteLine($"Material insertado con ID: {nuevoId}");
            System.Diagnostics.Debug.WriteLine($"Procediendo a insertar {ejemplares.Count} ejemplares...");

            // DEPURACIÓN: Verificar si entra al foreach
            System.Diagnostics.Debug.WriteLine("=== INICIANDO FOREACH DE EJEMPLARES ===");
            int contadorEjemplares = 0;

            foreach (string[] ej in ejemplares)
            {
                contadorEjemplares++;
                System.Diagnostics.Debug.WriteLine($"Procesando ejemplar #{contadorEjemplares}");

                string bibliotecaId = ej[0];
                string locacion = ej[1];
                string estado = ej[2];
                string idMaterialString = nuevoId.ToString();

                System.Diagnostics.Debug.WriteLine($"Datos del ejemplar: BibliotecaId={bibliotecaId}, Locacion={locacion}, Estado={estado}");
                System.Diagnostics.Debug.WriteLine("Llamando a ejemplarCliente.insertarEjemplar...");

                try
                {
                    ejemplarCliente.insertarEjemplar(bibliotecaId, titulo, autor, tipo, anioStr, idioma, tema, locacion, estado);
                    System.Diagnostics.Debug.WriteLine($"Ejemplar #{contadorEjemplares} insertado exitosamente");
                }
                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine($"ERROR al insertar ejemplar #{contadorEjemplares}: {ex.Message}");
                    System.Diagnostics.Debug.WriteLine($"Stack trace: {ex.StackTrace}");
                }
            }

            System.Diagnostics.Debug.WriteLine($"=== FOREACH COMPLETADO. Total procesados: {contadorEjemplares} ===");

            // Confirmación
            Session["mensajeRegistro"] = "Material registrado con éxito.";
            System.Diagnostics.Debug.WriteLine("Redirigiendo a materiales.aspx");
            Response.Redirect("materiales.aspx");
        }
        private List<EjemplarWS.ejemplarDTO> LeerEjemplaresDesdeRequest()
        {
            List<EjemplarWS.ejemplarDTO> lista = new List<EjemplarWS.ejemplarDTO>();
            for (int i = 1; i <= CantidadEjemplares; i++)
            {
                string biblio = Request.Form[$"ddlBiblioteca_{i}"];
                string loc = Request.Form[$"txtLocacion_{i}"];
                string estado = Request.Form[$"ddlEstado_{i}"];

                EjemplarWS.ejemplarDTO ej = new EjemplarWS.ejemplarDTO();

                if (!string.IsNullOrEmpty(biblio))
                {
                    ej.biblioteca = new EjemplarWS.bibliotecaDTO
                    {
                        bibliotecaId = int.Parse(biblio)
                    };
                }

                ej.locacionEnBiblioteca = loc;

                if (!string.IsNullOrEmpty(estado))
                {
                    ej.estado = (EjemplarWS.estadoEjemplar)Enum.Parse(typeof(EjemplarWS.estadoEjemplar), estado);
                }

                lista.Add(ej);
            }

            return lista;
        }

        protected void btnMasEjemplar_Click(object sender, EventArgs e)
        {
            List<EjemplarWS.ejemplarDTO> existentes = LeerEjemplaresDesdeRequest();
            CantidadEjemplares++;
            lblCantidadEjemplares.Text = CantidadEjemplares.ToString();
            btnMenosEjemplar.Enabled = CantidadEjemplares > 1;
            CargarEjemplaresDesdeViewState(existentes);
        }

        protected void btnMenosEjemplar_Click(object sender, EventArgs e)
        {
            if (CantidadEjemplares > 1)
            {
                List<EjemplarWS.ejemplarDTO> existentes = LeerEjemplaresDesdeRequest();
                CantidadEjemplares--;
                lblCantidadEjemplares.Text = CantidadEjemplares.ToString();
                btnMenosEjemplar.Enabled = CantidadEjemplares > 1;
                CargarEjemplaresDesdeViewState(existentes);
            }
        }

        protected void BtnEliminarEjemplar_Click(object sender, EventArgs e)
        {
            Button btn = sender as Button;
            int index = int.Parse(btn.CommandArgument);

            if (CantidadEjemplares > 1)
            {
                CantidadEjemplares--;
                // Nota: no estás eliminando un ejemplar específico, solo decrementas y regeneras
                lblCantidadEjemplares.Text = CantidadEjemplares.ToString();
                CargarEjemplaresDesdeViewState();
            }
        }
    }
}

<%@ Page Title="Registrar material" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="registrar_material.aspx.cs" Inherits="SoftInvWAProg3.registrar_material" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_registrar_material.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container mt-4">
        <a href="materiales.aspx" class="text-decoration-none mb-3 d-inline-block">
          <i class="bi bi-arrow-left"></i> Volver a Búsqueda de Materiales
        </a>
        <div class="container mt-4">

            <!-- Sección de datos generales -->
            <div class="form-group-material mb-3">
                <asp:DropDownList ID="ddlTipoMaterial" runat="server" CssClass="form-select form-selec" AutoPostBack="true"
                    OnSelectedIndexChanged="ddlTipoMaterial_SelectedIndexChanged">
                    <asp:ListItem Text="Selecciona tipo de material" Value="" />
                    <asp:ListItem Text="Libro" Value="LIBRO" />
                    <asp:ListItem Text="Artículo" Value="ARTICULO" />
                    <asp:ListItem Text="Tesis" Value="TESIS" />
                </asp:DropDownList>
            </div>

            <div class="form-group-material mb-3">
                <asp:TextBox ID="txtTitulo" runat="server" CssClass="form-control" placeholder=" " />
                <label for="txtTitulo">Título</label>
            </div>

            <div class="form-group-material mb-3">
                <asp:TextBox ID="txtAutores" runat="server" CssClass="form-control" placeholder=" " />
                <label for="txtAutores">Autores</label>
            </div>

            <div class="form-group-material mb-3">
                <asp:TextBox ID="txtAnio" runat="server" CssClass="form-control" TextMode="Number" placeholder=" " min="1000" max="2025" />
                <label for="txtAnio">Año de publicación</label>
            </div>

            <div class="form-group-material mb-3">
                <asp:TextBox ID="txtPaginas" runat="server" CssClass="form-control" TextMode="Number" placeholder=" " />
                <label for="txtPaginas">Número de páginas</label>
            </div>

            <div class="form-group-material mb-3">
                <asp:TextBox ID="txtTema" runat="server" CssClass="form-control" placeholder=" " />
                <label for="txtTema">Tema</label>
            </div>

            <div class="form-group-material mb-4">
                <asp:TextBox ID="txtIdioma" runat="server" CssClass="form-control" placeholder=" " />
                <label for="txtIdioma">Idioma</label>
            </div>

            <!-- Placeholder para campos específicos por tipo -->
            <div id="contenedorTipo">
                <asp:PlaceHolder ID="phCamposTipo" runat="server" />
            </div>

            <!-- Sección de ejemplares -->
            <h5 class="mb-3">Ejemplares</h5>

            <!-- Controlador de la cantidad de ejemplares -->
            <div class="d-flex align-items-center gap-2 mb-3">
                <span>Número de ejemplares por añadir:</span>
                <asp:Button ID="btnMenosEjemplar" runat="server" Text="–" CssClass="btn btn-outline-secondary btn-sm" OnClick="btnMenosEjemplar_Click" />
                <asp:Label ID="lblCantidadEjemplares" runat="server" CssClass="fw-semibold" Text="1" />
                <asp:Button ID="btnMasEjemplar" runat="server" Text="+" CssClass="btn btn-outline-primary btn-sm" OnClick="btnMasEjemplar_Click" />
            </div>

            <asp:PlaceHolder ID="phEjemplares" runat="server" />

            <div class="d-flex justify-content-end gap-2 mt-4">
                <asp:Button ID="btnCancelar" runat="server" CssClass="btn-accion btn-cancelar" Text="Cancelar" OnClientClick="window.location.href='materiales.aspx'; return false;" />
                <asp:Button ID="btnRegistrar" runat="server" CssClass="btn-accion btn-registrar" Text="Registrar material" Enabled="false" OnClick="btnRegistrar_Click" />
            </div>

        </div>
    </div>

    <script>
        function validarFormularioMaterial() {
            let valido = true;

            const ddlTipo = document.getElementById("<%= ddlTipoMaterial.ClientID %>");
            if (!ddlTipo || ddlTipo.value === "") valido = false;

            const camposComunes = [
                "<%= txtTitulo.ClientID %>",
                "<%= txtAutores.ClientID %>",
                "<%= txtAnio.ClientID %>",
                "<%= txtPaginas.ClientID %>",
                "<%= txtTema.ClientID %>",
                "<%= txtIdioma.ClientID %>"
            ];

            for (let i = 0; i < camposComunes.length; i++) {
                const input = document.getElementById(camposComunes[i]);
                if (!input || input.value.trim() === "") {
                    valido = false;
                }
            }

            // ✅ Validar año como entero positivo y menor o igual al actual
            const anioInput = document.getElementById("<%= txtAnio.ClientID %>");
            if (anioInput) {
                const anio = parseInt(anioInput.value);
                const anioValido = !isNaN(anio) && anio >= 1000 && anio <= new Date().getFullYear();
                if (!anioValido) {
                    valido = false;
                }
            }

            // ✅ Validar campos específicos dentro de phCamposTipo
            const contenedorTipo = document.getElementById("contenedorTipo");
            const inputsTipo = contenedorTipo.querySelectorAll("input.form-control");
            inputsTipo.forEach(input => {
                if (input.offsetParent !== null && input.value.trim() === "") {
                    valido = false;
                }
            });

            // ✅ Validar ejemplares
            const bloquesEjemplar = document.querySelectorAll(".ejemplar-block");
            bloquesEjemplar.forEach(bloque => {
                const ddlBiblio = bloque.querySelector("select[id*='ddlBiblioteca_']");
                const txtLoc = bloque.querySelector("input[id*='txtLocacion_']");
                const ddlEstado = bloque.querySelector("select[id*='ddlEstado_']");

                if (!ddlBiblio || ddlBiblio.value === "") valido = false;
                if (!txtLoc || txtLoc.value.trim() === "") valido = false;
                if (!ddlEstado || ddlEstado.value === "") valido = false;
            });

            // ✅ Habilitar o deshabilitar el botón
            document.getElementById("<%= btnRegistrar.ClientID %>").disabled = !valido;
        }

        document.addEventListener("DOMContentLoaded", function () {
            validarFormularioMaterial();
            document.addEventListener("input", validarFormularioMaterial);
            document.addEventListener("change", validarFormularioMaterial);
        });
    </script>


</asp:Content>


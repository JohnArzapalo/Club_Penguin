<%@ Page Title="Crear Cuenta" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="crear_cuenta.aspx.cs" Inherits="SoftInvWAProg3.crear_cuenta" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_cuentas.css" rel="stylesheet" />
    <script>
        function validarCampos() {
            var btnCrear = document.getElementById('<%= btnCrear.ClientID %>');
            var inputs = document.querySelectorAll('.validar-campo');
            for (var i = 0; i < inputs.length; i++) {
                var input = inputs[i];
                if (input.offsetParent !== null) {
                    if (input.value.trim() === '') {
                        btnCrear.disabled = true;
                        return;
                    }
                }
            }
            btnCrear.disabled = false;
        }
    </script>
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container mt-3">
        <a href="usuarios.aspx" class="text-decoration-none mb-3 d-inline-block">
          <i class="bi bi-arrow-left"></i> Volver a Cuentas
        </a>

        <div class="card p-4 shadow-sm">
            <h5><i class="bi bi-person"></i> Usuario <asp:Label ID="lblCodigoUsuario" runat="server" Text="Nuevo"></asp:Label></h5>
            <hr />

            <div class="mb-3">
                <asp:DropDownList ID="ddlTipoUsuario" CssClass="form-select validar-campo" runat="server" AutoPostBack="true" OnSelectedIndexChanged="ddlTipoUsuario_SelectedIndexChanged">
                    <asp:ListItem Value="" Text="Tipo de usuario" />
                    <asp:ListItem Value="Estudiante" Text="Estudiante" />
                    <asp:ListItem Value="Docente" Text="Docente" />
                    <asp:ListItem Value="Personal Bibliotecario" Text="Personal Bibliotecario" />
                </asp:DropDownList>
            </div>

            <!-- Campos comunes -->
            <div class="mb-3">
                <asp:TextBox ID="txtCodigo" CssClass="form-control validar-campo" Placeholder="Código universitario" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtNombres" CssClass="form-control validar-campo" Placeholder="Nombre(s)" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtApellido1" CssClass="form-control validar-campo" Placeholder="Primer Apellido" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtApellido2" CssClass="form-control validar-campo" Placeholder="Segundo Apellido" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:DropDownList ID="ddlTipoDoc" CssClass="form-select validar-campo" runat="server" onchange="validarCampos()">
                    <asp:ListItem Value="" Text="Tipo de Documento Oficial de Identidad" />
                    <asp:ListItem Value="DNI" Text="DNI" />
                    <asp:ListItem Value="Pasaporte" Text="Pasaporte" />
                    <asp:ListItem Value="Carnet de extranjería" Text="Carnet de extranjería" />
                </asp:DropDownList>
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtNroDoc" CssClass="form-control validar-campo" Placeholder="Número de documento" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtCorreo" CssClass="form-control validar-campo" Placeholder="Correo electrónico" runat="server" onkeyup="validarCampos()" />
            </div>
            <div class="mb-3">
                <asp:TextBox ID="txtPassword" CssClass="form-control validar-campo" TextMode="Password" Placeholder="Contraseña" runat="server" onkeyup="validarCampos()" />
            </div>

            <!-- Panel Estudiante: solo especialidad -->
            <asp:Panel ID="pnlEstudiante" runat="server" Visible="false">
                <div class="mb-3">
                    <asp:DropDownList ID="ddlEspecialidad" CssClass="form-select validar-campo" runat="server" onchange="validarCampos()">
                        <asp:ListItem Value="" Text="Especialidad" />
                        <asp:ListItem Value="Inteligencia Artificial" Text="Inteligencia Artificial" />
                        <asp:ListItem Value="Derecho Penal" Text="Derecho Penal" />
                        <asp:ListItem Value="Urbanismo" Text="Urbanismo" />
                    </asp:DropDownList>
                </div>
            </asp:Panel>

            <!-- Panel Docente: solo departamento académico -->
            <asp:Panel ID="pnlDocente" runat="server" Visible="false">
                <div class="mb-3">
                    <asp:TextBox ID="txtDepartamento" CssClass="form-control validar-campo" Placeholder="Departamento académico" runat="server" onkeyup="validarCampos()" />
                </div>
            </asp:Panel>

            <!-- Panel Personal Bibliotecario: solo biblioteca (sin fecha) -->
            <asp:Panel ID="pnlBibliotecario" runat="server" Visible="false">
                <div class="mb-3">
                    <asp:DropDownList ID="ddlBiblioteca" CssClass="form-select validar-campo" runat="server" onchange="validarCampos()">
                        <asp:ListItem Value="" Text="Biblioteca" />
                        <asp:ListItem Value="Central" Text="Central" />
                        <asp:ListItem Value="Ciencias" Text="Ciencias" />
                        <asp:ListItem Value="Humanidades" Text="Humanidades" />
                    </asp:DropDownList>
                </div>
            </asp:Panel>

            <div class="d-flex justify-content-end gap-2">
                <asp:Button ID="btnCancelar" CssClass="btn btn-outline-primary" Text="Cancelar" runat="server" OnClick="btnCancelar_Click" />
                <asp:Button ID="btnCrear" CssClass="btn btn-primary" Text="Crear Cuenta" runat="server" Enabled="false" OnClick="btnCrear_Click" />
            </div>
        </div>
    </div>
</asp:Content>

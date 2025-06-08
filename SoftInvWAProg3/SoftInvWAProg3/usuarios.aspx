<%@ Page Title="Cuentas" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="cuentas.aspx.cs" Inherits="SoftInvWAProg3.cuentas" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_cuentas.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">

        <!-- Botón Crear Cuenta -->
        <div class="d-flex justify-content-end mb-3">
            <asp:Button ID="btnCrearCuenta" CssClass="btn btn-outline-primary" Text="+ Crear cuenta" runat="server" OnClick="btnCrearCuenta_Click" />
        </div>

        <!-- Filtros de búsqueda -->
        <div class="card p-4 mb-4 shadow-sm">
            <div class="row g-3">
                <div class="col-md-4">
                    <asp:TextBox ID="txtUsuario" CssClass="form-control" placeholder="Usuario" runat="server" />
                </div>
                <div class="col-md-4">
                    <asp:DropDownList ID="ddlTipoUsuario" CssClass="form-select" runat="server">
                        <asp:ListItem Text="Tipo de usuario" Value="" />
                        <asp:ListItem Text="Estudiante" Value="Estudiante" />
                        <asp:ListItem Text="Docente" Value="Docente" />
                        <asp:ListItem Text="Personal Bibliotecario" Value="Personal Bibliotecario" />
                    </asp:DropDownList>
                </div>
                <div class="col-md-4">
                    <asp:TextBox ID="txtCodigo" CssClass="form-control" placeholder="Código universitario" runat="server" />
                </div>
            </div>

            <div class="row g-3 mt-3">
                <div class="col-md-4">
                    <asp:TextBox ID="txtNombres" CssClass="form-control" placeholder="Nombre(s)" runat="server" />
                </div>
                <div class="col-md-4">
                    <asp:TextBox ID="txtApellido" CssClass="form-control" placeholder="Primer Apellido" runat="server" />
                </div>
                <div class="col-md-4">
                    <asp:DropDownList ID="ddlEstado" CssClass="form-select" runat="server">
                        <asp:ListItem Text="Estado" Value="" />
                        <asp:ListItem Text="Activo" Value="Activo" />
                        <asp:ListItem Text="Sancionado" Value="Sancionado" />
                    </asp:DropDownList>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col d-flex justify-content-end gap-2">
                    <asp:Button ID="btnLimpiar" CssClass="btn btn-outline-secondary" Text="Limpiar filtros" runat="server" OnClick="btnLimpiar_Click" />
                    <asp:Button ID="btnBuscar" CssClass="btn btn-primary" Text="Buscar Cuenta" runat="server" OnClick="btnBuscar_Click" />
                </div>
            </div>
        </div>

        <!-- Tabla de resultados -->
        <div class="card p-4 shadow-sm">
            <div class="table-responsive">
                <asp:GridView ID="gvCuentas" CssClass="table table-striped table-hover" runat="server"
                    AutoGenerateColumns="false" AllowPaging="true" PageSize="10"
                    OnPageIndexChanging="gvCuentas_PageIndexChanging">
                    <Columns>
                        <asp:TemplateField HeaderText="ID Usuario">
                            <ItemTemplate><%# Eval("usuarioId") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Tipo Usuario">
                            <ItemTemplate><%# Eval("tipoUsuario.tipoUsuarioId") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Código Universitario">
                            <ItemTemplate><%# Eval("codigoUniversitario") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Nombres">
                            <ItemTemplate><%# Eval("nombres") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Primer Apellido">
                            <ItemTemplate><%# Eval("primerApellido") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Segundo Apellido">
                            <ItemTemplate><%# Eval("segundoApellido") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Tipo Documento">
                            <ItemTemplate><%# Eval("tipoDocumento") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Número Documento">
                            <ItemTemplate><%# Eval("numeroDocumento") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Correo Electrónico">
                            <ItemTemplate><%# Eval("correoElectronico") ?? "" %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Estado">
                            <ItemTemplate><%# Eval("estado") ?? "" %></ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                </asp:GridView>
            </div>
        </div>

    </div>
</asp:Content>

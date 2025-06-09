<%@ Page Title="Cuenta" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="cuenta.aspx.cs" Inherits="SoftInvWAProg3.cuenta" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_cuenta.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-8 mx-auto">
                <div class="user-card">
                    <div class="user-header">
                        <div>
                        <i class="bi bi-person-circle"></i>
                        Código de Usuario <asp:Literal ID="litCodigoUsuario" runat="server" />
                        </div>
                        <span class="user-status">
                            <asp:Literal ID="litEstado" runat="server" />
                        </span>
                    </div>

                    <div class="user-info mt-3">

                        <%--generales--%>
                        <p><b>Tipo de usuario </b><asp:Literal ID="litTipoUsuario" runat="server" /></p>
                        <p><b>Código universitario</b> <asp:Literal ID="litCodUniv" runat="server" /></p>
                        <p><b>Nombre(s)</b><asp:Literal ID="litNombres" runat="server" /></p>
                        <p><b>Primer Apellido</b><asp:Literal ID="litPrimerA" runat="server" /></p>
                        <p><b>Segundo Apellido</b> <asp:Literal ID="litSegundoA" runat="server" /></p>
                        <p><b>DNI</b> <asp:Literal ID="litDNI" runat="server" /></p>

                        <%--estudiante--%>
                        <asp:PlaceHolder ID="phEstudianteEspe" runat="server" Visible="false">
                            <p><b>Especialidad</b> <asp:Literal ID="litEspe" runat="server" /></p>
                        </asp:PlaceHolder>
                        <%--docente--%>
                        <asp:PlaceHolder ID="phDocenteDepa" runat="server" Visible="false">
                            <p><b>Departamento académico</b> <asp:Literal ID="litDepaAc" runat="server" /></p>
                        </asp:PlaceHolder>
                        <%--biblio--%>
                        <asp:PlaceHolder ID="phPersoBiblioteca" runat="server" Visible="false">
                            <p><b>Biblioteca</b> <asp:Literal ID="litBiblio" runat="server" /></p>
                        </asp:PlaceHolder>
                        <%--generales--%>
                        <p><b>Correo electrónico</b> <asp:Literal ID="litCorreo" runat="server" /></p>

                        <%--estudiantedocente--%>
                        <asp:PlaceHolder ID="phEstuDoc" runat="server" Visible="false">
                            <p><b>Cantidad máxima de materiales</b> <asp:Literal ID="litCantMax" runat="server" /></p>
                            <p><b>Duración máxima del préstamo</b> <asp:Literal ID="litDurMax" runat="server" /></p>
                        </asp:PlaceHolder>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>
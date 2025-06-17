<%@ Page Title="Detalle del préstamo" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="registrar_prestamo.aspx.cs" Inherits="SoftInvWAProg3.registrar_prestamo" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_circulaciones.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-8 mx-auto">

                <a href="circulaciones.aspx" class="text-decoration-none mb-3 d-inline-block">
                  <i class="bi bi-arrow-left"></i> Volver a Circulaciones
                </a>

                <div class="card p-4 mb-4">

                    <h4 class="card-title fw-bold">
                        <i class="bi bi-check-circle-fill text-success"></i> Código de Préstamoo
                        <asp:Literal ID="ltPrestamoID" runat="server"></asp:Literal>
                    </h4>

                    <div class="form-group-material mb-3">
                        <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" placeholder=" " />
                        <label for="txtUsuario">Usuario</label>
                    </div>

                    <div class="form-group-material mb-3">
                        <asp:TextBox ID="txtEjemplar" runat="server" CssClass="form-control" placeholder=" " />
                        <label for="txtEjemplar">Ejemplar</label>
                    </div>

                    <!-- Botones -->
                    <div class="d-flex justify-content-end gap-2 mt-4">
                        <asp:Button ID="btnCancelar" runat="server" Text="Cancelar" 
                            CssClass="btn btn-outline-primary" OnClick="btnCancelar_Click" />
                        <asp:Button ID="btnRegistrar" runat="server" Text="Registrar préstamo" 
                            CssClass="btn btn-primary text-white" OnClick="btnRegistrar_Click" />
                    </div>

                    <asp:Label ID="lblError" runat="server" CssClass="alert alert-danger mt-3" Visible="true" />

                </div>
            </div>
        </div>
    </div>
</asp:Content>
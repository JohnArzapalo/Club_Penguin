<%@ Page Title="Detalle de Material" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="detalle_material.aspx.cs" Inherits="SoftInvWAProg3.detalle_material" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_detalle_material.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
  <div class="container my-4">

    <a href="materiales.aspx" class="text-decoration-none mb-3 d-inline-block">
      <i class="bi bi-arrow-left"></i> Volver a Búsqueda de Materiales
    </a>

    <asp:Panel ID="pnlDetalle" runat="server" CssClass="rounded shadow p-4 bg-white">
      <div class="d-flex gap-3 align-items-start">
        <!-- Icono -->
        <img id="imgTipo" runat="server" style="width: 64px; height: 64px;" />

        <!-- Contenido -->
        <div class="flex-grow-1">
          <h4 class="fw-bold"><asp:Literal ID="litTitulo" runat="server" /></h4>

          <p class="mb-1"><strong>Autor(es)</strong> <asp:Literal ID="litAutor" runat="server" /></p>
          <p class="mb-1"><strong>Año de publicación</strong> <asp:Literal ID="litAnio" runat="server" /></p>

          <!-- Chip tipo -->
          <div class="mb-2">
            <span id="chipTipo" runat="server" class="chip-material d-inline-flex align-items-center px-2 py-1">
              <img id="imgChipIcono" runat="server" style="width: 20px; height: 20px;" class="me-1" src="Images/default.svg" />
              <asp:Literal ID="litTipo" runat="server" />
            </span>
          </div>

          <!-- Detalles según tipo -->
          <asp:PlaceHolder ID="phDetallesTipo" runat="server" />

          <!-- Copias -->
          <asp:Literal ID="litCopias" runat="server" />
        </div>
      </div>

      <!-- Selector dinámico según tipo de usuario -->
        <div class="mt-4">
          <!-- Fila responsive -->
          <div class="row g-2">
            <!-- Columna dropdown -->
            <div class="col-12 col-md">
              <asp:DropDownList ID="ddlBibliotecaReserva" runat="server"
                  CssClass="form-select focus-outline w-100"
                  AutoPostBack="true"
                  OnSelectedIndexChanged="ddlBibliotecaReserva_SelectedIndexChanged">
              </asp:DropDownList>
            </div>

            <!-- Columna botón Reservar (solo para usuarios no bibliotecarios) -->
            <div class="col-auto d-flex align-items-center">
              <asp:Button ID="btnReservar" runat="server"
                  Text="Reservar"
                  CssClass="btn btn-secondary"
                  Enabled="false"
                  Visible="false" />
            </div>
          </div>

          <!-- Botones para bibliotecario -->
          <asp:Panel ID="pnlOpcionesBibliotecario" runat="server"
              CssClass="d-flex flex-column flex-md-row justify-content-md-end align-items-md-center gap-2 mt-3"
              Visible="false">
            <asp:Button ID="btnEliminar" runat="server" Text="Eliminar Material" CssClass="btn btn-outline-danger rounded-pill fw-semibold px-4" />
            <asp:Button ID="btnEditar" runat="server" Text="Editar Material" CssClass="btn btn-primary rounded-pill fw-semibold px-4" />
          </asp:Panel>
        </div>


        <!-- Repeater de bibliotecas con sus copias -->
        <div class="mt-4">
          <asp:Repeater ID="rptBibliotecas" runat="server">
            <ItemTemplate>
              <div class="bg-light p-2 rounded mb-3">
                <div class="d-flex justify-content-between mb-2">
                  <strong><%# Eval("Nombre") %></strong>
                  <span>
                    Disponibles <%# Eval("Disponibles") %> · Reservados <%# Eval("Reservados") %>
                  </span>
                </div>
                <asp:Repeater ID="rptCopias" runat="server" DataSource='<%# Eval("Copias") %>'>
                  <ItemTemplate>
                    <div class="border rounded mb-2 p-2">
                      <p class="mb-1"><strong>Código</strong> <%# Eval("Codigo") %></p>
                      <p class="mb-1"><strong>Locación</strong> <%# Eval("Ubicacion") %></p>
                      <%# Eval("Estado") %>
                    </div>
                  </ItemTemplate>
                </asp:Repeater>
              </div>
            </ItemTemplate>
          </asp:Repeater>
            <asp:PlaceHolder ID="phSinBibliotecas" runat="server" Visible="false">
              <div class="alert alert-warning mt-3" role="alert">
                Este material no está disponible en ninguna biblioteca.
              </div>
            </asp:PlaceHolder>
        </div>

      
    </asp:Panel>
  </div>
</asp:Content>

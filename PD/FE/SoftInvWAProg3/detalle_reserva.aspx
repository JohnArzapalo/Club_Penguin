<%@ Page Title="Detalle de Reserva" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="detalle_reserva.aspx.cs" Inherits="SoftInvWAProg3.detalleReserva" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_detalle_material.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container py-4">
        <!-- Para bibliotecarios -->
        <asp:Panel ID="pnlVolverBibliotecario" runat="server" Visible="false">
            <a href="reservas.aspx" class="text-decoration-none mb-3 d-inline-block">
                <i class="bi bi-arrow-left"></i> Volver a Reservas
            </a>
        </asp:Panel>

        <!-- Para usuarios normales -->
        <asp:Panel ID="pnlVolverUsuario" runat="server" Visible="false">
            <a href="prestamos.aspx" class="text-decoration-none mb-3 d-inline-block">
                <i class="bi bi-arrow-left"></i> Volver a Reservas y Préstamos
            </a>
        </asp:Panel>

        <div class="card shadow-sm p-4 mb-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="card-title fw-bold">
                    <i class="bi bi-bookmark"></i> Código de Reserva
                    <asp:Literal ID="ltReservaId" runat="server"></asp:Literal>
                </h4>
                <span class="badge bg-success fs-6">
                    <asp:Literal ID="ltEstado" runat="server"></asp:Literal>
                </span>
            </div>
            <div class="row mb-3">
                <div class="col-md-6">
                    <p id="filaUsuario" runat="server" Visible="false" class="mb-1"><strong>Usuario</strong> <asp:Literal ID="ltUsuario" runat="server" /></p>
                </div>
            </div>

            <div class="d-flex align-items-start gap-3 mb-4">
                <img id="imgTipo" runat="server" style="width: 64px; height: 64px;" />

                <div>
                    <h4 class="fw-bold mb-1">
                        <asp:Literal ID="ltTitulo" runat="server" />
                    </h4>
                    <p id="filaMaterial" runat="server" Visible="false" class="mb-1"><strong>Material</strong> <asp:Literal ID="ltMaterialId" runat="server" /></p>
                    <p class="mb-1"><strong>Autor(es)</strong> <asp:Literal ID="ltAutor" runat="server" /></p>
                    <p class="mb-1"><strong>Año de publicación</strong> <asp:Literal ID="ltAnioPublicacion" runat="server" /></p>
                    <asp:Label ID="lblTipoMaterial" runat="server" CssClass="badge rounded-pill bg-light text-dark border" />
                    
                    <!-- Chip tipo -->
                    <div class="mb-2">
                      <span id="chipTipo" runat="server" class="chip-material d-inline-flex align-items-center px-2 py-1">
                        <img id="imgChipIcono" runat="server" style="width: 20px; height: 20px;" class="me-1" src="Images/default.svg" />
                        <asp:Literal ID="ltTipo" runat="server" />
                      </span>
                    </div>

                    <!-- Detalles específicos de Libro -->
                    <p id="filaISBN" runat="server" Visible="false" class="mb-1"><strong>ISBN</strong> <asp:Literal ID="ltISBN" runat="server" /></p>
                    <p id="filaEdicion" runat="server" Visible="false" class="mb-1"><strong>Edición</strong> <asp:Literal ID="ltEdicion" runat="server" /></p>
                    <p id="filaEditorialLibro" runat="server" Visible="false" class="mb-1"><strong>Editorial</strong> <asp:Literal ID="ltEditorialLibro" runat="server" /></p>

                    <!-- Detalles específicos de Artículo -->
                    <p id="filaISSN" runat="server" Visible="false" class="mb-1"><strong>ISSN</strong> <asp:Literal ID="ltISSN" runat="server" /></p>
                    <p id="filaNombreRevista" runat="server" Visible="false" class="mb-1"><strong>Nombre de revista</strong> <asp:Literal ID="ltNombreRevista" runat="server" /></p>
                    <p id="filaEditorialArticulo" runat="server" Visible="false" class="mb-1"><strong>Editorial</strong> <asp:Literal ID="ltEditorialArticulo" runat="server" /></p>
                    <p id="filaVolumen" runat="server" Visible="false" class="mb-1"><strong>Volumen</strong> <asp:Literal ID="ltVolumen" runat="server" /></p>
                    <p id="filaNumero" runat="server" Visible="false" class="mb-1"><strong>Número</strong> <asp:Literal ID="ltNumero" runat="server" /></p>

                    <!-- Detalles específicos de Tesis -->
                    <p id="filaInstitucion" runat="server" Visible="false" class="mb-1"><strong>Institución</strong> <asp:Literal ID="ltInstitucionPublicacion" runat="server" /></p>
                    <p id="filaAsesor" runat="server" Visible="false" class="mb-1"><strong>Asesor</strong> <asp:Literal ID="ltAsesorTesis" runat="server" /></p>
                    <p id="filaEspecialidad" runat="server" Visible="false" class="mb-1"><strong>Especialidad</strong> <asp:Literal ID="ltEspecialidad" runat="server" /></p>
                    <p id="filaGrado" runat="server" Visible="false" class="mb-1"><strong>Grado</strong> <asp:Literal ID="ltGrado" runat="server" /></p>

                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <p class="mb-1"><strong>Fecha de reserva</strong> <asp:Literal ID="ltFechaReserva" runat="server"></asp:Literal></p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="mb-1">Puede recogerlo hasta el día <strong><asp:Literal ID="ltFechaVencimiento" runat="server"></asp:Literal></strong> dentro del horario de atención:</p>
                </div>
            </div>


        </div>
    </div>
</asp:Content>
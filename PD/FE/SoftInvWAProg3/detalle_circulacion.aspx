<%@ Page Title="Detalle del préstamo" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="detalle_circulacion.aspx.cs" Inherits="SoftInvWAProg3.detalle_circulacion" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_circulaciones.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-8 mx-auto">

                <!-- Para bibliotecarios -->
                <asp:Panel ID="pnlVolverBibliotecario" runat="server" Visible="false">
                    <a href="circulaciones.aspx" class="text-decoration-none mb-3 d-inline-block">
                        <i class="bi bi-arrow-left"></i> Volver a Circulaciones
                    </a>
                </asp:Panel>

                <!-- Para usuarios normales -->
                <asp:Panel ID="pnlVolverUsuario" runat="server" Visible="false">
                    <a href="prestamos.aspx" class="text-decoration-none mb-3 d-inline-block">
                        <i class="bi bi-arrow-left"></i> Volver a Reservas y Préstamos
                    </a>
                </asp:Panel>

                <div class="card p-4 mb-4">

                    <h4 class="card-title fw-bold">
                        <i class="bi bi-check-circle-fill text-success"></i> Código de Préstamo
                        <asp:Literal ID="ltPrestamoID" runat="server"></asp:Literal>
                    </h4>


                    <asp:Panel ID="pnlReserva" runat="server" Visible="false">
                        <div class="mb-1">
                            <span class="detail-label">Reserva</span>
                            <span class="detail-value"><asp:Literal ID="ltReserva" runat="server" /></span>
                        </div>
                    </asp:Panel>

                    <!-- Solo para bibliotecarios -->
                    <asp:Panel ID="pnlUsuario" runat="server" Visible="false">
                        <div class="mb-1">
                            <span class="detail-label">Usuario</span>
                            <span class="detail-value"><asp:Literal ID="ltUsuario" runat="server" /></span>
                        </div>
                    </asp:Panel>

                    <div class="mb-1">
                        <span class="detail-label">Ejemplar</span>
                        <span class="detail-value"><asp:Literal ID="ltEjemplar" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <span class="detail-label">Fecha del préstamo</span>
                        <span class="detail-value"><asp:Literal ID="ltFechaPrestamo" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <p class="mb-1">Puede devolverlo hasta el día <strong><asp:Literal ID="ltPlazoMax" runat="server"></asp:Literal></strong> dentro del horario de atención:</p>
                    </div>

                    <div class="info-wrapper">
                        <div class="info-box biblioteca">
                            <strong><asp:Literal ID="ltBiblioteca" runat="server" /></strong>
                        </div>
                        <div class="info-box horario">
                            <p class="mb-1"><strong>Horario </strong><asp:Literal ID="ltHorarioBiblioteca" runat="server" /></p>
                        </div>
                    </div>

                    <!-- Estado y fecha para usuarios comunes (solo lectura) -->
                    <asp:Panel ID="pnlSoloLecturaUsuario" runat="server" Visible="false">
                        <div class="mb-1">
                            <span class="detail-label">Estado del préstamo</span>
                            <span class="detail-value"><asp:Literal ID="ltEstadoTexto" runat="server" /></span>
                        </div>
                        <asp:Panel ID="pnlFechaDevolucion" runat="server" Visible="false">
                            <div class="mb-1">
                                <span class="detail-label">Fecha de devolución</span>
                                <span class="detail-value"><asp:Literal ID="ltFechaDevolucion" runat="server" /></span>
                            </div>
                        </asp:Panel>
                    </asp:Panel>

                    <!-- Panel completo de edición para bibliotecarios -->
                    <asp:Panel ID="pnlEditorBibliotecario" runat="server" Visible="false">
                        <div class="row align-items-end mb-3">
                            <div class="col">
                                <div class="form-group-material">
                                    <asp:DropDownList ID="ddlEstados" runat="server" CssClass="form-select" 
                                        AutoPostBack="true" OnSelectedIndexChanged="ddlEstados_SelectedIndexChanged">
                                        <asp:ListItem Text="Selecciona el estado" Value="" />
                                        <asp:ListItem Text="Vigente" Value="VIGENTE" />
                                        <asp:ListItem Text="Devuelto a tiempo" Value="DEVUELTO_A_TIEMPO" />
                                        <asp:ListItem Text="Devuelto con retraso" Value="DEVUELTO_CON_RETRASO" />
                                        <asp:ListItem Text="Devuelto dañado o perdido" Value="DEVUELTO_DANADO_O_PERDIDO" />
                                        <asp:ListItem Text="No devuelto" Value="NO_DEVUELTO" />
                                    </asp:DropDownList>
                                </div>
                            </div>

                            <div class="col">
                                <div class="form-group-material">
                                    <asp:TextBox ID="txtFechaDev" runat="server" 
                                        TextMode="Date" CssClass="form-control" placeholder=" " />
                                    <label for="txtFechaDev">Fecha de devolución</label>
                                </div>
                            </div>

                            <div class="col-auto">
                                <asp:Button ID="btnActualizarEstado" runat="server" Text="Actualizar estado" 
                                    CssClass="btn btn-primary text-white" Enabled="true" OnClick="btnActualizarEstado_Click" />
                            </div>

                        </div>

                        <div class="d-flex justify-content-end gap-2">
                            <asp:Button ID="btnRegistrarSancion" runat="server" Text="Registrar sanción" 
                                CssClass="btn btn-primary text-white" Visible="false" OnClick="btnRegistrarSancion_Click" />
                        </div>
                    </asp:Panel>

                    <div class="d-flex justify-content-end gap-2">
                        <asp:LinkButton ID="btnVisualizarSancion" runat="server" 
                            Text="Visualizar sanción" 
                            CssClass="btn btn-primary text-white" 
                            Enabled="true" 
                            Visible="false" 
                            OnClick="btnVisualizarSancion_Click" />
                    </div>

                    <%--Labels que SIRVIERON para DEBUGEAR--%>
                    <asp:Label ID="lblError" runat="server" Visible="false" CssClass="alert alert-danger mt-3" />
                    <asp:Label ID="lblDEBUGGG" runat="server" Visible="false" CssClass="alert alert-danger mt-3" />

                </div>
            </div>
        </div>
    </div>
</asp:Content>
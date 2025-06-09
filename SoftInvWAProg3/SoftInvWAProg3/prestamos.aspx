<%@ Page Title="Reservas y Préstamos" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="prestamos.aspx.cs" Inherits="SoftInvWAProg3.prestamos" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_prestamos.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <div class="contenedor-principal">
        <!-- Sección de Reservas -->
        <h2 class="seccion-titulo">Reservas</h2>
        <asp:Repeater ID="rptReservas" runat="server">
            <ItemTemplate>
                <!-- Card Reserva -->
                 <div class="card rounded-4 p-3 border-0 d-flex flex-row align-items-start gap-3 mb-4"
                    style="box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);">
                    <!-- Icono del tipo de material -->
                    <div style="flex-shrink: 0;">
                        <img src='<%# ObtenerIconoPorTipo(Eval("Tipo")) %>' alt="Icono" style="width: 48px; height: 48px;" />
                    </div>
                    <div class="flex-grow-1">
                        <!-- Reserva -->
                        <h5 class="fw-semibold mb-2">Reserva <%# Eval("ReservaId") %><br /></h5>
                        <!-- Título -->
                        <p class="mb-1">
                            <strong>Título</strong> <%# Eval("Titulo") %><br />
                        </p>
                        <!-- Chip: tipo de material -->
                        <div class="mb-1">
                            <span class='chip-material <%# ObtenerClaseChip(Eval("Tipo")) %>'>
                                <img src='<%# ObtenerIconoPorTipo(Eval("Tipo")) %>' alt="tipo" class="me-1" style="width: 20px; height: 20px;" />
                                <%# ObtenerNombreMostrar(Eval("Tipo")) %>
                            </span>
                        </div>
                        <!-- Biblioteca -->
                        <p class="mb-1">
                            <strong>Biblioteca</strong> <%# Eval("Biblioteca") %><br />
                        </p>
                        <!-- Fecha Vencimiento -->
                        <p class="mb-1">
                            <strong>Recoger hasta</strong> <%# Eval("FechaVencimiento") %><br />
                        </p>
                        <!-- Estado Reserva -->
                        <div class="mb-1">
                            <span class='estado <%# SoftInvWAProg3.prestamos.ObtenerClaseEstado(Eval("EstadoReserva").ToString()) %>'>
                                <%# SoftInvWAProg3.prestamos.ObtenerTextoEstadoReserva(Eval("EstadoReserva").ToString()) %>
                            </span>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>

        <!-- Sección de Préstamos -->
        <h2 class="seccion-titulo">Préstamos</h2>
        <asp:Repeater ID="rptPrestamos" runat="server">
            <ItemTemplate>
                <!-- Card Préstamo -->
                <div class="card rounded-4 p-3 border-0 d-flex flex-row align-items-start gap-3 mb-4"
                    style="box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);">
                    <!-- Icono del tipo de material -->
                    <div style="flex-shrink: 0;">
                        <img src='<%# ObtenerIconoPorTipo(Eval("Tipo")) %>' alt="Icono" style="width: 48px; height: 48px;" />
                    </div>
                    <div class="flex-grow-1">
                        <!-- Préstamo -->
                        <h5 class="fw-semibold mb-2">Préstamo <%# Eval("PrestamoId") %><br /></h5>
                        <!-- Título -->
                        <p class="mb-1">
                            <strong>Título</strong> <%# Eval("Titulo") %><br />
                        </p>
                        <!-- Chip: tipo de material -->
                        <div class="mb-1">
                            <span class='chip-material <%# ObtenerClaseChip(Eval("Tipo")) %>'>
                                <img src='<%# ObtenerIconoPorTipo(Eval("Tipo")) %>' alt="tipo" class="me-1" style="width: 20px; height: 20px;" />
                                <%# ObtenerNombreMostrar(Eval("Tipo")) %>
                            </span>
                        </div>
                        <!-- Biblioteca -->
                        <p class="mb-1">
                            <strong>Biblioteca</strong> <%# Eval("Biblioteca") %><br />
                        </p>
                        <!-- Fecha Vencimiento -->
                        <div class="mb-1">
                            <strong>
                                <%# (Eval("EstadoPrestamo").ToString() == "VIGENTE" || Eval("EstadoPrestamo").ToString() == "NO_DEVUELTO") 
                                    ? "Devolver el" : "Devuelto el" %>
                            </strong> <%# Eval("FechaDevolucion") %><br />                            
                        </div>
                        <!-- Estado Reserva -->
                        <div class="mb-1">
                            <span class='estado <%# SoftInvWAProg3.prestamos.ObtenerClaseEstado(Eval("EstadoPrestamo").ToString()) %>'>
                                <%# SoftInvWAProg3.prestamos.ObtenerTextoEstadoPrestamo(Eval("EstadoPrestamo").ToString()) %>
                            </span>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>
    </div>
</asp:Content>

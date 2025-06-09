<%@ Page Title="Reservas" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="reservas.aspx.cs" Inherits="SoftInvWAProg3.reservas" %>

<asp:Content ID="Content3" ContentPlaceHolderID="MainContent" runat="server">

    <!-- Sección de filtros -->
    <div class="card p-4 mb-4 shadow-sm">
        <div class="row g-3">
            <div class="col-md-3">
                <asp:TextBox ID="txtReserva" runat="server" CssClass="form-control" placeholder="Código Reserva" />
            </div>
            <div class="col-md-3">
                <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" placeholder="Nombre del Usuario" />
            </div>
            <div class="col-md-3">
                <asp:TextBox ID="txtMaterial" runat="server" CssClass="form-control" placeholder="Título del Material" />
            </div>
            <div class="col-md-3">
                <asp:DropDownList ID="ddlEstado" runat="server" CssClass="form-select">
                    <asp:ListItem Text="Estado" Value="" />
                    <asp:ListItem Text="Activo" Value="Activo" />
                    <asp:ListItem Text="Devuelto" Value="Devuelto" />
                    <asp:ListItem Text="Vencido" Value="Vencido" />
                </asp:DropDownList>
            </div>
            <div class="col-md-3">
                <asp:TextBox ID="txtFechaDesde" runat="server" CssClass="form-control" TextMode="Date" placeholder="Desde (Fecha reserva)" />
            </div>
            <div class="col-md-3">
                <asp:TextBox ID="txtFechaHasta" runat="server" CssClass="form-control" TextMode="Date" placeholder="Hasta (Fecha reserva)" />
            </div>
        </div>

        <!-- Fila de botones -->
        <div class="row mt-3">
            <div class="col d-flex gap-2">
                <asp:Button ID="btnLimpiar" runat="server" Text="Limpiar filtros" CssClass="btn btn-outline-primary rounded-pill px-4" OnClick="btnLimpiar_Click" />
                <asp:Button ID="btnBuscar" runat="server" Text="Buscar reserva" CssClass="btn btn-primary rounded-pill px-4" OnClick="btnBuscar_Click" />
            </div>
        </div>
    </div>

    <!-- Sección de resultados de reservas -->
    <div class="card shadow-sm p-3 mb-4">
        <asp:GridView ID="gvReservas" runat="server" 
            CssClass="table table-bordered table-striped mb-0" 
            AutoGenerateColumns="False"
            AllowPaging="True" 
            PageSize="8" 
            OnPageIndexChanging="gvReservas_PageIndexChanging"
            OnSelectedIndexChanged="gvReservas_SelectedIndexChanged"
            DataKeyNames="ReservaId">
                <Columns>
                    <asp:BoundField DataField="ReservaId" HeaderText="Reserva" />
                    <asp:BoundField DataField="UsuarioId" HeaderText="Usuario" />
                    <asp:BoundField DataField="MaterialId" HeaderText="Material" />
                    <asp:BoundField DataField="FechaReserva" HeaderText="Fecha de reserva" DataFormatString="{0:dd/MM/yyyy}" />
                    <asp:BoundField DataField="Estado" HeaderText="Estado" />
                    <asp:CommandField ShowSelectButton="True" SelectText="Ver Detalle" ControlStyle-CssClass="btn btn-sm btn-info" />
                </Columns>

        </asp:GridView>
    </div>
    <asp:Label ID="lblError" runat="server" CssClass="alert alert-danger mt-3" Visible="false" />
    
</asp:Content>
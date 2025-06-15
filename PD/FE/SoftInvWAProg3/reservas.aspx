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
                    <asp:ListItem Text="--Seleccione--" Value="" />
                    <asp:ListItem Text="Vigente" Value="Vigente" />
                    <asp:ListItem Text="Vencida" Value="Vencida" />
                    
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
            CssClass="table table-bordered table-hover" 
            AutoGenerateColumns="False"
            AllowPaging="True" 
            PageSize="8" 
            OnPageIndexChanging="gvReservas_PageIndexChanging"

             OnRowDataBound="gvReservas_RowDataBound"

            OnSelectedIndexChanged="gvReservas_SelectedIndexChanged"
            DataKeyNames="ReservaId">
                <Columns>
                    <asp:BoundField DataField="ReservaId" HeaderText="Reserva" />
                    <asp:BoundField DataField="UsuarioId" HeaderText="Usuario" />
                    <asp:BoundField DataField="MaterialId" HeaderText="Material" />
                    <asp:BoundField DataField="FechaReserva" HeaderText="Fecha de reserva" DataFormatString="{0:dd/MM/yyyy}" />
                    <asp:BoundField DataField="Estado" HeaderText="Estado" />
                    <asp:TemplateField ItemStyle-CssClass="d-none" HeaderStyle-CssClass="d-none">
                        <ItemTemplate>
                            <asp:LinkButton runat="server" 
                                CommandName="Select" 
                                CommandArgument='<%# Eval("ReservaId") %>'
                                CssClass="position-absolute w-100 h-100 opacity-0">
                                Ver detalle
                            </asp:LinkButton>
                        </ItemTemplate>
                    </asp:TemplateField>                 
                </Columns>
            <RowStyle CssClass="cursor-pointer" />
        </asp:GridView>
    </div>
    <asp:Label ID="lblError" runat="server" CssClass="alert alert-danger mt-3" Visible="false" />


    <script type="text/javascript">
        // Referencia al contenedor del GridView y al spinner
        var gvContainer = document.getElementById('gvReservasContainer');
        var spinner = document.getElementById('loadingSpinner');

        // Función para mostrar el spinner y ocultar el GridView
        function mostrarSpinner() {
            if (gvContainer) {
                gvContainer.style.display = 'none'; // Oculta el contenedor del GridView
            }
            if (spinner) {
                spinner.style.display = 'block'; // Muestra el spinner
            }
        }

        // Función para ocultar el spinner y mostrar el GridView
        function ocultarSpinner() {
            if (spinner) {
                spinner.style.display = 'none'; // Oculta el spinner
            }
            if (gvContainer) {
                gvContainer.style.display = 'block'; // Muestra el contenedor del GridView
            }
        }

        // Al cargar la página (después del postback)
        window.onload = function () {
            ocultarSpinner(); // Asegura que el spinner esté oculto cuando la página se carga completamente
        };
    </script>
    



    
</asp:Content>

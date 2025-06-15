<%@ Page Title="Circulaciones" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="circulaciones.aspx.cs" Inherits="SoftInvWAProg3.circulaciones" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_circulaciones.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">

        <!-- Botón Registrar Préstamo arriba a la derecha, sin texto a la izquierda -->
        <div class="d-flex justify-content-end mb-3">
            <asp:Button ID="btnRegistrarPrestamo" CssClass="btn btn-outline-primary" Text="+ Registrar préstamo" runat="server" OnClick="btnRegistrarPrestamo_Click" />
        </div>

        <!-- Filtros de búsqueda con card -->
        <div class="card p-4 mb-4 shadow-sm">
            <div class="row g-3">
                <div class="col-md-3">
                    <asp:TextBox ID="txtCirculacion" CssClass="form-control" placeholder="Circulación" runat="server" />
                </div>
                <div class="col-md-3">
                    <asp:TextBox ID="txtReserva" CssClass="form-control" placeholder="Reserva" runat="server" />
                </div>
                <div class="col-md-3">
                    <asp:TextBox ID="txtUsuario" CssClass="form-control" placeholder="Usuario" runat="server" />
                </div>
                <div class="col-md-3">
                    <asp:TextBox ID="txtEjemplar" CssClass="form-control" placeholder="Ejemplar" runat="server" />
                </div>
            </div>

            <div class="row g-3 mt-3">
                <div class="col-md-3">
                    <asp:TextBox ID="txtFechaDesde" CssClass="form-control" placeholder="Desde (Fecha de préstamo)" runat="server" TextMode="Date" />
                </div>
                <div class="col-md-3">
                    <asp:TextBox ID="txtFechaHasta" CssClass="form-control" placeholder="Hasta (Fecha de préstamo)" runat="server" TextMode="Date" />
                </div>
                <div class="col-md-6">
                    <asp:DropDownList ID="ddlEstado" CssClass="form-select" runat="server" style="width: 100%;">
                        <asp:ListItem Text="Seleccione Estado" Value="" />
                        <asp:ListItem Text="Vigente" Value="VIGENTE" />
                        <asp:ListItem Text="Devuelto a tiempo" Value="DEVUELTO_A_TIEMPO" />
                        <asp:ListItem Text="Devuelto con retraso" Value="DEVUELTO_CON_RETRASO" />
                        <asp:ListItem Text="Devuelto dañado o perdido" Value="DEVUELTO_DANADO_O_PERDIDO" />
                        <asp:ListItem Text="Devuelto retraso y daño pérdida" Value="DEVUELTO_RETRASO_Y_DANO_PERDIDA" />
                        <asp:ListItem Text="No devuelto" Value="NO_DEVUELTO" />
                    </asp:DropDownList>
                </div>
            </div>

            <!-- Botones alineados a la derecha -->
            <div class="row mt-3">
                <div class="col d-flex justify-content-end gap-2">
                    <asp:Button ID="btnLimpiar" CssClass="btn btn-outline-secondary" Text="Limpiar filtros" runat="server" OnClick="btnLimpiar_Click" />
                    <asp:Button ID="btnBuscar" CssClass="btn btn-primary" Text="Buscar circulación" runat="server" OnClick="btnBuscar_Click" />
                </div>
            </div>
        </div>

        <!-- Tabla de resultados con card -->
        <div class="card p-4 shadow-sm">
            <div class="table-responsive">
                <asp:GridView ID="gvCirculaciones" CssClass="table table-striped table-hover" runat="server" AutoGenerateColumns="false" AllowPaging="true" PageSize="10" OnPageIndexChanging="gvCirculaciones_PageIndexChanging">
                    <Columns>
                        <asp:BoundField DataField="circulacionId" HeaderText="Circulación" />
                        <asp:TemplateField HeaderText="Reserva">
                            <ItemTemplate><%# Eval("reserva.reservaId") %></ItemTemplate>
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Usuario">
                            <ItemTemplate><%# Eval("usuario.usuarioId") %></ItemTemplate>
                        </asp:TemplateField>
                        <asp:TemplateField HeaderText="Ejemplar">
                            <ItemTemplate><%# Eval("ejemplar.ejemplarId") %></ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Fecha de préstamo">
                            <ItemTemplate>
                                <%# 
                                    Eval("fechaPrestamo") == null || 
                                    Convert.ToDateTime(Eval("fechaPrestamo")) == DateTime.MinValue 
                                    ? "" 
                                    : String.Format("{0:dd/MM/yyyy}", Eval("fechaPrestamo")) 
                                %>
                            </ItemTemplate>
                        </asp:TemplateField>

                        <asp:TemplateField HeaderText="Fecha de devolución">
                            <ItemTemplate>
                                <%# 
                                    Eval("fechaDevolucion") == null || 
                                    Convert.ToDateTime(Eval("fechaDevolucion")) == DateTime.MinValue 
                                    ? "" 
                                    : String.Format("{0:dd/MM/yyyy}", Eval("fechaDevolucion")) 
                                %>
                            </ItemTemplate>
                        </asp:TemplateField>

                        <asp:BoundField DataField="estadoPrestamo" HeaderText="Estado" />
                    </Columns>
                </asp:GridView>
            </div>
        </div>
    </div>
</asp:Content>

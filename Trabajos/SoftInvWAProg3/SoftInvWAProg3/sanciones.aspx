<%@ Page Title="Sanciones" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="sanciones.aspx.cs" Inherits="SoftInvWAProg3.sanciones" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_sanciones.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-8 mx-auto">
                <!-- Filtros de búsqueda -->
                <div class="card p-4 mb-4">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="form-group-material">
                                <asp:TextBox ID="txtSancion" runat="server" CssClass="input" placeholder=" " />
                                <label for="txtSancion">Sanción</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group-material">
                                <asp:TextBox ID="txtCirculacion" runat="server" CssClass="input" placeholder=" " />
                                <label for="txtCirculacion">Circulación</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group-material">
                                <asp:TextBox ID="txtFechaDesde" runat="server" TextMode="Date" CssClass="input" placeholder=" " />
                                <label for="txtFechaDesde">Desde (Fecha de registro)</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group-material">
                                <asp:TextBox ID="txtFechaHasta" runat="server" TextMode="Date" CssClass="input" placeholder=" " />
                                <label for="txtFechaHasta">Hasta (Fecha de registro)</label>
                            </div>
                        </div>
                    </div>

                    <!-- Botones -->
                    <div class="d-flex justify-content-end gap-2 mt-4">
                        <asp:Button ID="btnLimpiar" runat="server" Text="Limpiar filtros" 
                            CssClass="btn btn-outline-primary" OnClick="btnLimpiar_Click" />
                        <asp:Button ID="btnBuscar" runat="server" Text="Buscar Sanción" 
                            CssClass="btn btn-primary text-white" OnClick="btnBuscar_Click" />
                    </div>
                </div>


                <!-- Tabla de resultados -->
                <div class="card p-3 mb-4">
                    <div class="table-responsive">

                        <asp:GridView ID="gvSanciones" runat="server" 
                            AutoGenerateColumns="False"
                            CssClass="table table-bordered table-hover"
                            OnRowCommand="gvSanciones_RowCommand"
                            OnRowDataBound="gvSanciones_RowDataBound"
                            DataKeyNames="sancionId">
                            <Columns>
                                <asp:BoundField DataField="sancionId" HeaderText="Sanción" />
                                <asp:BoundField DataField="circulacion.circulacionId" HeaderText="Circulación" />
                                <asp:BoundField DataField="fechaRegistro" HeaderText="Fecha Registro" DataFormatString="{0:dd/MM/yyyy}" />
                                <asp:BoundField DataField="fechaTermino" HeaderText="Fecha Término" DataFormatString="{0:dd/MM/yyyy}" />
                                <asp:BoundField DataField="diasSancion" HeaderText="Días" />
        
                                <asp:TemplateField ItemStyle-CssClass="d-none" HeaderStyle-CssClass="d-none">
                                    <ItemTemplate>
                                        <asp:LinkButton runat="server" 
                                            CommandName="VerDetalleSancion" 
                                            CommandArgument='<%# Eval("sancionId") %>'
                                            CssClass="position-absolute w-100 h-100 opacity-0">
                                            Ver detalle
										</asp:LinkButton>
                                    </ItemTemplate>
                                </asp:TemplateField>
                            </Columns>
							<RowStyle CssClass="cursor-pointer" />
                        </asp:GridView>
                    </div>
					
					
                    <!-- Botón de reporte -->
                    <div class="d-flex justify-content-end mt-3">
                        <asp:Button ID="btnReporte" runat="server" Text="Generar reporte" CssClass="btn btn-secondary" OnClick="btnReporte_Click" />
                    </div>
                </div>

                <asp:Label ID="lblError" runat="server" CssClass="alert alert-danger mt-3" Visible="false" />
            </div>
        </div>
    </div>

</asp:Content>

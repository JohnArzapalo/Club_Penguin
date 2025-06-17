<%@ Page Title="Detalle de Sanción" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="sanciones_detalle.aspx.cs" Inherits="SoftInvWAProg3.sanciones_detalle" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_sanciones.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="MainContent" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-12 col-md-10 col-lg-8 mx-auto">

                <a href="sanciones.aspx" class="text-decoration-none mb-3 d-inline-block">
                  <i class="bi bi-arrow-left"></i> Volver a Sanciones
                </a>

                <div class="card p-4 mb-4">

                    <h4 class="card-title fw-bold">
                        <i class="bi bi-exclamation-triangle-fill text-danger"></i> 
                        Código de Sanción
                        <asp:Literal ID="ltSancionId" runat="server" />
                    </h4>

                    <div class="mb-1">
                        <span class="detail-label">Circulación</span>
                        <span class="detail-value"><asp:Literal ID="ltCirculacion" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <span class="detail-label">Fecha de registro</span>
                        <span class="detail-value"><asp:Literal ID="ltFechaRegistro" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <span class="detail-label">Fecha de término</span>
                        <span class="detail-value"><asp:Literal ID="ltFechaTermino" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <span class="detail-label">Devolución</span>
                        <span class="detail-value"><asp:Literal ID="ltDevolucion" runat="server" /></span>
                    </div>

                    <div class="mb-1">
                        <span class="detail-label">Días de sanción</span>
                        <span class="detail-value"><asp:Literal ID="ltDiasSancion" runat="server" /></span>
                    </div>

                    <div class="form-group-observaciones">
                        <asp:TextBox ID="txtObservaciones" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" placeholder=" "></asp:TextBox>
                        <label for="txtObservaciones">Observaciones</label>
                    </div>

                    <div class="d-flex justify-content-end gap-2">
                        <asp:LinkButton ID="btnRegistrarSancion" runat="server" 
                            Text="Registrar sanción" 
                            CssClass="btn btn-primary text-white" 
                            Enabled="true" 
                            Visible="false" 
                            OnClick="btnRegistrarSancion_Click" />
                    </div>
                    <asp:Label ID="lblDEBUGGG" runat="server" Visible="false" CssClass="alert alert-danger mt-3" />
                </div>
            </div>
        </div>
    </div>
</asp:Content>

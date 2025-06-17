<%@ Page Title="Búsqueda de Materiales" Language="C#" MasterPageFile="~/SoftInv.Master" AutoEventWireup="true" CodeBehind="materiales.aspx.cs" Inherits="SoftInvWAProg3.materiales" %>

<asp:Content ID="HeadContent" ContentPlaceHolderID="HeadContent" runat="server">
    <link href="Content/estilos_materiales.css?v=1.2" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    <div class="container-fluid">
        <!-- 🔒 Campos ocultos para mantener el estado de los filtros -->
        <asp:HiddenField ID="hdnTitulo" runat="server" />
        <asp:HiddenField ID="hdnAutores" runat="server" />
        <asp:HiddenField ID="hdnTema" runat="server" />
        <asp:HiddenField ID="hdnAnioDesde" runat="server" />
        <asp:HiddenField ID="hdnAnioHasta" runat="server" />
        <asp:HiddenField ID="hdnTipoMaterial" runat="server" />
        <asp:HiddenField ID="hdnBiblioteca" runat="server" />
        <asp:HiddenField ID="hdnIdioma" runat="server" />
        <asp:HiddenField ID="hdnDisponibilidad" runat="server" />

        <!-- 🎯 Botones invisibles para disparar eventos del servidor -->
        <asp:Button ID="btnFiltroAutor" runat="server" Style="display:none;" OnClick="btnFiltroAutor_Click" />
        <asp:Button ID="btnFiltroTema" runat="server" Style="display:none;" OnClick="btnFiltroTema_Click" />
        <asp:Button ID="btnFiltroAnio" runat="server" Style="display:none;" OnClick="btnFiltroAnio_Click" />
        <asp:Button ID="btnFiltroBiblioteca" runat="server" Style="display:none;" OnClick="btnFiltroBiblioteca_Click" />
        <asp:Button ID="btnFiltroIdioma" runat="server" Style="display:none;" OnClick="btnFiltroIdioma_Click" />
        <asp:Button ID="btnFiltroDisponibilidad" runat="server" Style="display:none;" OnClick="btnFiltroDisponibilidad_Click" />
        <asp:Button ID="btnFiltroTipoMaterial" runat="server" Style="display:none;" OnClick="btnFiltroTipoMaterial_Click" />

        <!-- 🔍 Botón invisible para ejecutar la búsqueda avanzada -->
        <asp:Button ID="btnBusquedaAvanzadaServidor" runat="server"
        OnClick="btnBusquedaAvanzadaServidor_Click"
        UseSubmitBehavior="false"
        Style="display:none;" />
        <div class="row">
          
          <!-- Panel lateral de filtros -->
            <!-- Filtros: aparecerán arriba en pantallas pequeñas -->
                <div class="col-md-3 order-1 order-md-0">
                    <div class="accordion" id="filtroAccordion">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingAutor">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseAutor"
                                        aria-expanded="false" aria-controls="collapseAutor">
                                    Autor
                                </button>
                            </h2>
                            <div id="collapseAutor" class="accordion-collapse collapse" aria-labelledby="headingAutor">
                                <div class="accordion-body">
                                    <div class="floating-label-group">
                                        <input type="text"
                                               class="form-control floating-input"
                                               id="autorFiltro"
                                               style="border-radius: 8px; border: 1px solid #dee2e6;"
                                               onkeydown="verificarEnterAutor(event)" />
                                        <label for="autorFiltro" class="floating-label">Nombre del autor</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Año: Desde y Hasta en una misma fila -->
                        <div class="accordion-item">
                          <h2 class="accordion-header" id="headingAnio">
                            <button class="accordion-button collapsed" type="button"
                                    data-bs-toggle="collapse" data-bs-target="#collapseAnio"
                                    aria-expanded="false" aria-controls="collapseAnio">
                              Año
                            </button>
                          </h2>
                          <div id="collapseAnio" class="accordion-collapse collapse" aria-labelledby="headingAnio">
                            <div class="accordion-body">
      
                              <div class="row g-2">
                                <!-- Desde -->
                                <div class="col-6">
                                  <div class="floating-label-group">
                                    <input type="number"
                                           class="form-control floating-input text-center"
                                           id="anioDesdeFiltro"
                                           min="1900" max="2100"
                                           placeholder=" "
                                           onkeydown="verificarEnterAnio(event)" />
                                    <label for="anioDesdeFiltro" class="floating-label">Desde</label>
                                  </div>
                                </div>

                                <!-- Hasta -->
                                <div class="col-6">
                                  <div class="floating-label-group">
                                    <input type="number"
                                           class="form-control floating-input text-center"
                                           id="anioHastaFiltro"
                                           min="1900" max="2100"
                                           placeholder=" "
                                           onkeydown="verificarEnterAnio(event)" />
                                    <label for="anioHastaFiltro" class="floating-label">Hasta</label>
                                  </div>
                                </div>
                              </div>

                              <!-- Campos ocultos y botón invisible -->
                              <asp:HiddenField ID="HiddenField1" runat="server" />
                              <asp:HiddenField ID="HiddenField2" runat="server" />
                              <asp:Button ID="Button1" runat="server" Style="display:none;" OnClick="btnFiltroAnio_Click" />

                            </div>
                          </div>
                        </div>





                        <!-- Resto de accordion items permanecen igual -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingTipoMaterial">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTipoMaterial"
                                        aria-expanded="false" aria-controls="collapseTipoMaterial">
                                    Tipo de material
                                </button>
                            </h2>
                            <div id="collapseTipoMaterial" class="accordion-collapse collapse" aria-labelledby="headingTipoMaterial">
                                <div class="accordion-body">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="tipoMaterialFiltro"
                                               value="LIBRO" id="radioTipoLibro"
                                               onchange="seleccionarTipoMaterial('<%= hdnTipoMaterial.ClientID %>', '<%= btnFiltroTipoMaterial.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTipoLibro">Libro</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="tipoMaterialFiltro"
                                               value="ARTICULO" id="radioTipoArticulo"
                                               onchange="seleccionarTipoMaterial('<%= hdnTipoMaterial.ClientID %>', '<%= btnFiltroTipoMaterial.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTipoArticulo">Artículo</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="tipoMaterialFiltro"
                                               value="TESIS" id="radioTipoTesis"
                                               onchange="seleccionarTipoMaterial('<%= hdnTipoMaterial.ClientID %>', '<%= btnFiltroTipoMaterial.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTipoTesis">Tesis</label>
                                    </div>
                                </div>  
                            </div>
                        </div>

                        <!-- Disponibilidad -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingDisponibilidad">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseDisponibilidad"
                                        aria-expanded="false" aria-controls="collapseDisponibilidad">
                                    Disponibilidad
                                </button>
                            </h2>
                            <div id="collapseDisponibilidad" class="accordion-collapse collapse" aria-labelledby="headingDisponibilidad">
                                <div class="accordion-body">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="disponibilidadFiltro"
                                               value="DISPONIBLE" id="radioDisponibilidadDisponible"
                                               onchange="seleccionarDisponibilidad('<%= hdnDisponibilidad.ClientID %>', '<%= btnFiltroDisponibilidad.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioDisponibilidadDisponible">Disponible</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="disponibilidadFiltro"
                                               value="PRESTADO" id="radioDisponibilidadPrestado"
                                               onchange="seleccionarDisponibilidad('<%= hdnDisponibilidad.ClientID %>', '<%= btnFiltroDisponibilidad.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioDisponibilidadPrestado">Prestado</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="disponibilidadFiltro"
                                               value="EN_REPARACION" id="radioDisponibilidadReparacion"
                                               onchange="seleccionarDisponibilidad('<%= hdnDisponibilidad.ClientID %>', '<%= btnFiltroDisponibilidad.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioDisponibilidadReparacion">En reparación</label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Biblioteca -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingBiblioteca">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseBiblioteca"
                                        aria-expanded="false" aria-controls="collapseBiblioteca">
                                    Biblioteca
                                </button>
                            </h2>
                            <div id="collapseBiblioteca" class="accordion-collapse collapse" aria-labelledby="headingBiblioteca">
                                <div class="accordion-body">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="bibliotecaFiltro"
                                               value="Biblioteca Central" id="radioBibliotecaCentral"
                                               onchange="seleccionarBiblioteca('<%= hdnBiblioteca.ClientID %>', '<%= btnFiltroBiblioteca.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioBibliotecaCentral">Biblioteca Central</label>
                                    </div>
                                    <div class="form-check">
                                       <input class="form-check-input" type="radio"
                                               name="bibliotecaFiltro"
                                               value="Biblioteca del Complejo de Innovación Académica" id="radioBibliotecaInnovacion"
                                               onchange="seleccionarBiblioteca('<%= hdnBiblioteca.ClientID %>', '<%= btnFiltroBiblioteca.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioBibliotecaInnovacion">Biblioteca del Complejo de Innovación Académica</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="bibliotecaFiltro"
                                               value="Biblioteca de Ciencias Sociales" id="radioBibliotecaSociales"
                                               onchange="seleccionarBiblioteca('<%= hdnBiblioteca.ClientID %>', '<%= btnFiltroBiblioteca.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioBibliotecaSociales">Biblioteca de Ciencias Sociales</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- REEMPLAZA tus DropDownList actuales con estos: -->

                     <!-- REEMPLAZA tus DropDownList con estos (SIN AutoPostBack): -->

                     <!-- REEMPLAZA tus DropDownList con estos (SIN onchange): -->

                        <!-- Tema (con DropDownList) -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingTema">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapseTema" aria-expanded="false" aria-controls="collapseTema">
                                    Tema
                                </button>
                            </h2>
                            <div id="collapseTema" class="accordion-collapse collapse" aria-labelledby="headingTema">
                                <div class="accordion-body">
                                    <asp:DropDownList ID="ddlTema" runat="server"
                                        CssClass="form-select form-select-lg floating-input"
                                        AutoPostBack="True"
                                        OnSelectedIndexChanged="ddlTema_SelectedIndexChanged">
                                        <asp:ListItem Text="Selecciona un tema" Value="" Enabled="false" />
                                    </asp:DropDownList>
                                </div>
                            </div>
                        </div>

                        <!-- Idioma (DropDownList) -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingIdioma">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapseIdioma" aria-expanded="false" aria-controls="collapseIdioma">
                                    Idioma
                                </button>
                            </h2>
                            <div id="collapseIdioma" class="accordion-collapse collapse" aria-labelledby="headingIdioma">
                                <div class="accordion-body">
                                    <asp:DropDownList ID="ddlIdioma" runat="server"
                                        CssClass="form-select form-select-lg floating-input"
                                        AutoPostBack="True"
                                        OnSelectedIndexChanged="ddlIdioma_SelectedIndexChanged">
                                        <asp:ListItem Text="Selecciona un idioma" Value="" Enabled="false" />
                                    </asp:DropDownList>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        <!-- Contenido principal: búsqueda + resultados -->
        <div class="col-md-9 order-0 order-md-1">

            <!-- Spinner Overlay -->
            <div id="spinnerOverlay" style="display: none; position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(255,255,255,0.7); z-index: 1050;">
              <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center;">
                <div class="spinner-border text-primary mb-3" role="status">
                  <span class="visually-hidden">Cargando...</span>
                </div>
                <p class="text-muted">Buscando materiales...</p>
              </div>
            </div>

            <!-- Botón Registrar Material solo para bibliotecarios -->
            <asp:PlaceHolder ID="phBotonRegistrar" runat="server" Visible="false">
                <div class="text-end mb-3">
                    <a href="registrar_material.aspx" class="btn btn-outline-primary rounded-pill px-4 py-2 fw-semibold">
                        <i class="bi bi-plus-lg me-2"></i> Registrar material
                    </a>
                </div>
            </asp:PlaceHolder>

          <div class="d-flex flex-column flex-md-row gap-2 mb-4 align-items-stretch">
            <!-- Campo de búsqueda -->
              <div class="form-group-material flex-grow-1 d-flex">
                  <div class="position-relative w-100">
                    <asp:TextBox ID="txtBuscar" runat="server" CssClass="form-control rounded-end-0 material-input h-100" placeholder=" "></asp:TextBox>
                      <label for="txtBuscar">Buscar</label>
                  </div>
                    <asp:LinkButton ID="btnBuscar" runat="server"
                        CssClass="btn btn-search d-flex align-items-center px-3"
                        OnClick="btnBuscar_Click"
                        ToolTip="Buscar"
                        OnClientClick="mostrarIndicadorCarga();">
                        <i class="bi bi-search"></i>
                    </asp:LinkButton>
                </div>


            <!-- Botón de búsqueda avanzada -->
        <button class="btn btn-outline-primary text-nowrap px-3" type="button" 
                data-bs-toggle="modal" data-bs-target="#busquedaAvanzadaModal">
          Búsqueda Avanzada
        </button>

          </div>
                <div class="mt-4">
                    <!-- Repeater para mostrar los materiales -->
                    <asp:Repeater ID="rptMateriales" runat="server" OnItemCommand="rptMateriales_ItemCommand">
                        <ItemTemplate>
                            <div class="col-12 mb-3">
                                <asp:LinkButton runat="server" CommandName="VerDetalle" CommandArgument='<%# Eval("MaterialId") %>' CssClass="text-decoration-none text-reset w-100 link-card-hover">
                                    <div class="card rounded-4 p-3 border-0 d-flex flex-row align-items-start gap-3"
                                         style="box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);">
                                        <!-- Icono del tipo de material -->
                                        <div style="flex-shrink: 0;">
                                            <img src='<%# ObtenerIconoPorTipo(Eval("TipoMaterial")) %>' alt="Icono" style="width: 48px; height: 48px;" />
                                        </div>
                                        <!-- Contenido textual -->
                                        <div class="flex-grow-1">
                                            <h5 class="fw-semibold mb-2"><%# Eval("Titulo") %></h5>
                                            <p class="mb-1">
                                                <strong>Autor(es):</strong> <%# Eval("Autor") %>
                                            </p>
                                            <p class="mb-1">
                                                <strong>Año de publicación:</strong> <%# Eval("AnioPublicacion") %>
                                            </p>
                                            <!-- Chip: tipo de material -->
                                            <div class="mb-1">
                                                <span class='chip-material <%# ObtenerClaseChip(Eval("TipoMaterial")) %>'>
                                                    <img src='<%# ObtenerIconoPorTipo(Eval("TipoMaterial")) %>' alt="tipo" class="me-1" style="width: 20px; height: 20px;" />
                                                    <%# ObtenerNombreMostrar(Eval("TipoMaterial")) %>
                                                </span>
                                            </div>
                                            <!-- Disponibilidad -->
                                            <div class="mb-1">
                                                <asp:Literal runat="server" Text='<%# ObtenerTextoDisponibilidad(Eval("MaterialId")) %>' />
                                            </div>
                                            <!-- Biblioteca(s) -->
                                            <p class="mb-1">
                                                <strong>Biblioteca(s):</strong> <%# ObtenerTextoBibliotecas(Eval("MaterialId")) %>
                                            </p>
                                            <!-- Información adicional -->
                                            <%-- 
                                            <div class="d-flex gap-3 text-muted small">
                                                <span><i class="bi bi-file-earmark-text me-1"></i><%# Eval("NumeroPaginas") %> páginas</span>
                                                <span><i class="bi bi-tag me-1"></i><%# Eval("Tema") %></span>
                                                <span><i class="bi bi-globe me-1"></i><%# Eval("Idioma") %></span>
                                            </div>
                                            --%>
                                        </div>
                                    </div>
                                </asp:LinkButton>
                            </div>
                        </ItemTemplate>
                        <FooterTemplate>
                            <asp:PlaceHolder runat="server" Visible='<%# ((Repeater)Container.NamingContainer).Items.Count == 0 %>'>
                                <div class="text-center text-muted mt-5">
                                    <i class="bi bi-search" style="font-size: 3rem;"></i>
                                    <p class="mt-3">No se encontraron materiales con los criterios especificados</p>
                                </div>
                            </asp:PlaceHolder>
                        </FooterTemplate>
                    </asp:Repeater>

                    <!-- Panel para mensajes (errores, carga, etc.) -->
                    <asp:Panel ID="divResultados" runat="server" CssClass="text-center text-muted mt-5">
                        <p>Aún no se han buscado materiales</p>
                    </asp:Panel>
                </div>
    <!-- AQUIIIIII-->
    <!-- Reemplaza la sección del modal de búsqueda avanzada en tu ASPX -->
    <div class="modal fade" id="busquedaAvanzadaModal" tabindex="-1" aria-labelledby="busquedaAvanzadaModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header border-bottom-0 pb-0">
                    <div class="d-flex align-items-center">
                        <a href="materiales.aspx" class="text-decoration-none mb-3 d-inline-block">
                         <i class="bi bi-arrow-left"></i> Volver a Búsqueda simple
                       </a>
                    </div>
                    <button type="button" class="btn-close modal-close-btn" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body pt-4">
                    <div class="container-fluid">
                        <div id="contenedorBusquedaAvanzada">
                           <!-- Título -->
                           <div class="mb-4 form-group-material">
                               <input type="text" id="titulo" placeholder=" ">
                               <label for="titulo">Título</label>
                           </div>

                           <!-- Autor(es) -->
                           <div class="mb-4 form-group-material">
                               <input type="text" id="autores" placeholder=" ">
                               <label for="autores">Autor(es)</label>
                           </div>

                           <!-- Tema -->
                           <div class="mb-4 form-group-material">
                               <input type="text" id="tema" placeholder=" ">
                               <label for="tema">Tema</label>
                           </div>

                           <!-- Año de publicación: Desde y Hasta en una misma fila -->
                           <div class="mb-4">
                               <div class="row g-2">
                                   <!-- Desde -->
                                   <div class="col-6">
                                       <div class="floating-label-group">
                                           <input type="number"
                                                  class="form-control floating-input text-center"
                                                  id="anioDesde"
                                                  min="1900" max="2100"
                                                  placeholder=" " />
                                           <label for="anioDesde" class="floating-label">Año desde</label>
                                       </div>
                                   </div>

                                   <!-- Hasta -->
                                   <div class="col-6">
                                       <div class="floating-label-group">
                                           <input type="number"
                                                  class="form-control floating-input text-center"
                                                  id="anioHasta"
                                                  min="1900" max="2100"
                                                  placeholder=" " />
                                           <label for="anioHasta" class="floating-label">Año hasta</label>
                                       </div>
                                   </div>
                               </div>
                           </div>

                            <!-- Tipo de material -->
                            <div class="mb-4 floating-label-group">
                                <select class="form-select form-select-lg floating-input" id="tipoMaterial" style="border-radius: 8px; border: 1px solid #dee2e6;">
                                    <option value="" disabled selected></option>
                                    <option value="LIBRO">Libro</option>
                                    <option value="ARTICULO">Artículo</option>
                                    <option value="TESIS">Tesis</option>
                                </select>
                                <label for="tipoMaterial" class="floating-label">Tipo de material</label>
                            </div>

                            <!-- Biblioteca -->
                            <div class="mb-4 floating-label-group">
                                <select class="form-select form-select-lg floating-input" id="biblioteca" style="border-radius: 8px; border: 1px solid #dee2e6;">
                                    <option value="" disabled selected></option>
                                    <option value="Biblioteca Central">Biblioteca Central</option>
                                    <option value="Biblioteca del Complejo de Innovación Académica">Biblioteca del Complejo de Innovación Académica</option>
                                    <option value="Biblioteca de Ciencias Sociales">Biblioteca de Ciencias Sociales</option>
                                </select>
                                <label for="biblioteca" class="floating-label">Biblioteca</label>
                            </div>

                            <!-- Idioma -->
                            <div class="mb-4 floating-label-group">
                                <asp:DropDownList ID="ddlIdiomaAvanzado" runat="server" 
                                    CssClass="form-select form-select-lg floating-input" 
                                    style="border-radius: 8px; border: 1px solid #dee2e6;">
                                    <asp:ListItem Text="" Value="" Selected="True" />
                                </asp:DropDownList>
                                <label for="<%= ddlIdiomaAvanzado.ClientID %>" class="floating-label">Idioma</label>
                            </div>

                            <!-- Disponibilidad -->
                            <div class="mb-5 floating-label-group">
                                <select class="form-select form-select-lg floating-input" id="disponibilidad" style="border-radius: 8px; border: 1px solid #dee2e6;">
                                    <option value="" disabled selected></option>
                                    <option value="DISPONIBLE">Disponible</option>
                                    <option value="PRESTADO">Prestado</option>
                                    <option value="EN_REPARACION">En reparacion</option>
                                </select>
                                <label for="disponibilidad" class="floating-label">Disponibilidad</label>
                            </div>

                            <!-- Botón azul que activa el botón invisible del servidor -->
                            <div class="d-flex justify-content-end">
                                <button id="btnBusquedaAvanzada"
                                        type="button"
                                        class="btn btn-busqueda-avanzada btn-lg px-5"
                                        onclick="ejecutarBusquedaAvanzada()" disabled>
                                    Búsqueda Avanzada
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <style>
        /* ===== ESTILOS PARA FLOATING LABELS ===== */
        .floating-label-group {
            position: relative;
            margin-bottom: 1.5rem;
        }

        /* INPUT y SELECT uniformes */
        .floating-input {
            width: 100%;
            padding: 12px 12px;
            font-size: 14px !important;
            border: 1px solid #ccc !important;
            border-radius: 8px !important;
            outline: none;
            background-color: #fff;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            min-height: 40px;
            appearance: none;
        }

        /* LABEL flotante */
        .floating-label {
            position: absolute;
            top: 12px;
            left: 12px;
            color: #999;
            font-size: 14px !important;
            pointer-events: none;
            background: #fff;
            padding: 0 4px;
            transition: all 0.2s ease;
        }

        /* ENFOQUE: borde y color azul */
        .floating-input:focus {
            border-color: #0a4fd5 !important;
            box-shadow: none;
        }

        /* LABEL arriba al enfocar o con valor */
        .floating-input:focus + .floating-label,
        .floating-input.has-focus + .floating-label,
        .floating-input.has-value + .floating-label {
            top: -8px;
            font-size: 12px !important;
            color: #0a4fd5;
        }

        /* LABEL gris cuando tiene valor pero no foco */
        .floating-input.has-value:not(:focus) + .floating-label {
            color: #999;
        }
        select.floating-input option:disabled {
            color: #999 !important;
            pointer-events: none;
        }

        /* Aplica solo a los <select> del modal, no a los DropDownList del servidor */
        #busquedaAvanzadaModal select option:first-child {
            display: none;
        }

        /* Padding extra cuando hay icono en campo de búsqueda */
        .floating-input[style*="padding-right"] {
            padding-right: 3.5rem !important;
        }/* ===== ESTILOS DEL MODAL ===== */

        .btn-busqueda-avanzada {
            background-color: #094bca;
            border-color: #094bca;
            color: white;
            transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease;
            border-radius: 24px;
            padding: 0.5rem 2rem;
            font-size: 1rem;
        }

        .btn-busqueda-avanzada:hover {
            background-color: #4d7ce3;
            border-color: #4d7ce3;
            color: white;
        }

        .btn-busqueda-avanzada:active {
            background-color: #4d7ce3;
            border-color: #4d7ce3;
            color: white;
        }

    </style>
    <script type="text/javascript">
        // Variable global para controlar si se ha ejecutado una búsqueda
        var busquedaEjecutada = false;

        // Función para marcar que se ejecutó una búsqueda
        function marcarBusquedaEjecutada() {
            busquedaEjecutada = true;
            habilitarFiltros();
        }

        // Función para habilitar filtros (SIN mostrar mensaje)
        function habilitarFiltros() {
            const panelFiltros = document.getElementById('filtroAccordion');
            panelFiltros.style.opacity = '1';
            panelFiltros.style.pointerEvents = 'auto';
            // Removido: ocultarMensajeBloqueo();
        }

        // Función para deshabilitar filtros (SIN mostrar mensaje)
        function deshabilitarFiltros() {
            const panelFiltros = document.getElementById('filtroAccordion');
            panelFiltros.style.opacity = '0.5';
            panelFiltros.style.pointerEvents = 'none';
            // Removido: mostrarMensajeBloqueo();
        }

        // Función actualizada para validar acceso a filtros (SIN mensaje)
        function validarAccesoFiltros() {
            if (!busquedaEjecutada) {
                deshabilitarFiltros();
                return false;
            } else {
                habilitarFiltros();
                return true;
            }
        }
        // Función de validación para el rango de años (opcional, para usar con eventos blur)
        function validarRangoAniosAvanzado() {
            const anioDesde = document.getElementById('anioDesde').value;
            const anioHasta = document.getElementById('anioHasta').value;

            if (anioDesde && anioHasta) {
                const desdeInt = parseInt(anioDesde);
                const hastaInt = parseInt(anioHasta);

                if (desdeInt > hastaInt) {
                    alert("El año 'Desde' no puede ser mayor que el año 'Hasta'");
                    document.getElementById('anioHasta').focus();
                    return false;
                }
            }
            return true;
        }
        // REMOVIDAS las funciones mostrarMensajeBloqueo() y ocultarMensajeBloqueo()

        // Función corregida para limpiar filtros y dropdowns
        function limpiarFiltros() {
            // Limpiar campos de texto
            document.getElementById('autorFiltro').value = '';
            document.getElementById('anioDesdeFiltro').value = '';
            document.getElementById('anioHastaFiltro').value = '';

            // Limpiar radio buttons
            const radioButtons = document.querySelectorAll('input[type="radio"]');
            radioButtons.forEach(radio => {
                radio.checked = false;
            });

            // Limpiar dropdowns de forma más robusta
            const ddlTema = document.getElementById('<%=ddlTema.ClientID%>');
            const ddlIdioma = document.getElementById('<%=ddlIdioma.ClientID%>');

            if (ddlTema) {
                ddlTema.value = "";
                ddlTema.selectedIndex = 0;
                $(ddlTema).removeClass('has-value has-focus');
                // Disparar evento change para actualizar UI
                $(ddlTema).trigger('change');
            }

            if (ddlIdioma) {
                ddlIdioma.value = "";
                ddlIdioma.selectedIndex = 0;
                $(ddlIdioma).removeClass('has-value has-focus');
                // Disparar evento change para actualizar UI
                $(ddlIdioma).trigger('change');
            }

    // Limpiar hidden fields
    document.getElementById('<%=hdnTitulo.ClientID%>').value = '';
    document.getElementById('<%=hdnAutores.ClientID%>').value = '';
    document.getElementById('<%=hdnTema.ClientID%>').value = '';
    document.getElementById('<%=hdnAnioDesde.ClientID%>').value = '';
    document.getElementById('<%=hdnAnioHasta.ClientID%>').value = '';
    document.getElementById('<%=hdnTipoMaterial.ClientID%>').value = '';
    document.getElementById('<%=hdnBiblioteca.ClientID%>').value = '';
    document.getElementById('<%=hdnIdioma.ClientID%>').value = '';
    document.getElementById('<%=hdnDisponibilidad.ClientID%>').value = '';
}

// OPCIONAL: Función adicional para resetear completamente los dropdowns
function resetearDropdownsCompleto() {
    // Para ddlTema
    const ddlTema = document.getElementById('<%=ddlTema.ClientID%>');
    if (ddlTema) {
        ddlTema.value = ""; // Establecer valor vacío
        ddlTema.selectedIndex = 0;
        $(ddlTema).removeClass('has-value has-focus');
    }

    // Para ddlIdioma  
    const ddlIdioma = document.getElementById('<%=ddlIdioma.ClientID%>');
    if (ddlIdioma) {
        ddlIdioma.value = ""; // Establecer valor vacío
        ddlIdioma.selectedIndex = 0;
        $(ddlIdioma).removeClass('has-value has-focus');
    }
}

        // Función modificada para mostrar indicador de carga Y marcar búsqueda ejecutada
        function mostrarIndicadorCarga() {
            document.getElementById("spinnerOverlay").style.display = "block";
            // Marcar que se ejecutó una búsqueda
            marcarBusquedaEjecutada();
        }

        // Función específica para búsqueda simple (modificada)
        function realizarBusqueda() {
            var terminoBusqueda = $('#<%= txtBuscar.ClientID %>').val().trim();

            if (terminoBusqueda === '') {
                alert('Por favor, ingresa un término de búsqueda');
                return;
            }

            // Marcar que se ejecutó una búsqueda
            marcarBusquedaEjecutada();

            console.log('Realizando búsqueda simple con término:', terminoBusqueda);
        }

        // Función modificada para búsqueda avanzada
        function ejecutarBusquedaAvanzada() {
            const values = {
                titulo: document.getElementById('titulo').value,
                autores: document.getElementById('autores').value,
                tema: document.getElementById('tema').value,
                anioDesde: document.getElementById('anioDesde').value,
                anioHasta: document.getElementById('anioHasta').value,
                tipoMaterial: document.getElementById('tipoMaterial').value,
                biblioteca: document.getElementById('biblioteca').value,
                idioma: document.getElementById('idioma').value,
                disponibilidad: document.getElementById('disponibilidad').value
            };

            // Validar rango de años si ambos campos tienen valores
            if (values.anioDesde && values.anioHasta) {
                const anioDesdeInt = parseInt(values.anioDesde);
                const anioHastaInt = parseInt(values.anioHasta);

                if (anioDesdeInt > anioHastaInt) {
                    alert("El año 'Desde' no puede ser mayor que el año 'Hasta'");
                    return;
                }

                if (anioDesdeInt < 1900 || anioHastaInt > 2100) {
                    alert("Los años deben estar entre 1900 y 2100");
                    return;
                }
            }

            // Asignar los valores a los HiddenFields ASP.NET
            document.getElementById('<%= hdnTitulo.ClientID %>').value = values.titulo;
            document.getElementById('<%= hdnAutores.ClientID %>').value = values.autores;
            document.getElementById('<%= hdnTema.ClientID %>').value = values.tema;
            document.getElementById('<%= hdnAnioDesde.ClientID %>').value = values.anioDesde;
            document.getElementById('<%= hdnAnioHasta.ClientID %>').value = values.anioHasta;
            document.getElementById('<%= hdnTipoMaterial.ClientID %>').value = values.tipoMaterial;
            document.getElementById('<%= hdnBiblioteca.ClientID %>').value = values.biblioteca;
            document.getElementById('<%= hdnIdioma.ClientID %>').value = values.idioma;
            document.getElementById('<%= hdnDisponibilidad.ClientID %>').value = values.disponibilidad;

            // Ocultar el modal y mostrar el indicador
            $('#busquedaAvanzadaModal').modal('hide');

            // Marcar que se ejecutó una búsqueda
            marcarBusquedaEjecutada();

            // Mostrar indicador de carga
            document.getElementById("spinnerOverlay").style.display = "block";

            // Hacer postback al botón oculto
                    __doPostBack('<%= btnBusquedaAvanzadaServidor.UniqueID %>', '');
        }

        // FUNCIONES DE FILTRO ACTUALIZADAS (sin alertas molestas)
        function verificarEnterAutor(event) {
            if (!busquedaEjecutada) {
                event.preventDefault();
                return;
            }

            if (event.key === "Enter") {
                event.preventDefault();
                aplicarFiltroLateral('autorFiltro', '<%= hdnAutores.ClientID %>', '<%= btnFiltroAutor.UniqueID %>');
            }
        }

        function verificarEnterAnio(event) {
            if (!busquedaEjecutada) {
                event.preventDefault();
                return;
            }

            if (event.key === "Enter") {
                event.preventDefault();

                const desde = document.getElementById('anioDesdeFiltro').value.trim();
                const hasta = document.getElementById('anioHastaFiltro').value.trim();

                if (!desde || !hasta) {
                    alert("Por favor ingresa ambos valores: Desde y Hasta.");
                    return;
                }

                const desdeInt = parseInt(desde);
                const hastaInt = parseInt(hasta);

                if (isNaN(desdeInt) || isNaN(hastaInt) || desdeInt > hastaInt || desdeInt < 1900 || hastaInt > 2100) {
                    alert("Año inválido. Asegúrate que 'Desde' sea menor o igual a 'Hasta' y estén entre 1900 y 2100.");
                    return;
                }

                document.getElementById('<%= hdnAnioDesde.ClientID %>').value = desde;
                document.getElementById('<%= hdnAnioHasta.ClientID %>').value = hasta;

                mostrarIndicadorCarga();
                __doPostBack('<%= btnFiltroAnio.UniqueID %>', '');
            }
        }

        // Funciones de selección actualizadas (sin alertas)
        function seleccionarDisponibilidad(hiddenFieldId, botonUniqueID, radio) {
            if (!busquedaEjecutada) {
                radio.checked = false;
                return;
            }

            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        function seleccionarBiblioteca(hiddenFieldId, botonUniqueID, radio) {
            if (!busquedaEjecutada) {
                radio.checked = false;
                return;
            }

            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        function seleccionarTipoMaterial(hiddenFieldId, botonUniqueID, radio) {
            if (!busquedaEjecutada) {
                radio.checked = false;
                return;
            }

            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        // Función para aplicar filtros laterales
        function aplicarFiltroLateral(idCampo, hiddenFieldId, botonUniqueID) {
            const valor = $(`#${idCampo}`).val();
            $(`#${hiddenFieldId}`).val(valor);
            mostrarIndicadorCarga();
            __doPostBack(botonUniqueID, '');
        }

        // Funciones adicionales para mantener compatibilidad
        function seleccionarIdioma(hiddenFieldId, botonUniqueID, radio) {
            if (!busquedaEjecutada) {
                radio.checked = false;
                return;
            }

            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        function seleccionarTema(hiddenFieldId, botonUniqueID, radio) {
            if (!busquedaEjecutada) {
                radio.checked = false;
                return;
            }

            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        function aplicarFiltroSelect(select, hiddenFieldId, botonUniqueID) {
            if (!busquedaEjecutada) {
                select.selectedIndex = 0;
                return;
            }

            const valor = select.value;
            if (valor && valor.trim() !== "") {
                document.getElementById(hiddenFieldId).value = valor;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }

        // Resto de funciones sin cambios...
        function validarRangoAnios() {
            var anioDesde = parseInt($('#anioPublicacion').val());
            if (isNaN(anioDesde) || anioDesde === 0) {
                alert('El año publicación no puede ser null');
                return false;
            }
            return true;
        }

        function limpiarFormularioAvanzado() {
            $('#busquedaAvanzadaModal input, #busquedaAvanzadaModal select').each(function () {
                $(this).val('').removeClass('has-value has-focus');
            });
        }

        function aplicarCheckboxYBuscar(grupoSelector, hiddenFieldId, botonUniqueID) {
            if (!busquedaEjecutada) {
                return;
            }

            let valores = [];
            $(`${grupoSelector}:checked`).each(function () {
                valores.push($(this).val());
            });
            $(`#${hiddenFieldId}`).val(valores.join(','));

            mostrarIndicadorCarga();
            __doPostBack(botonUniqueID, '');
        }


        function validarCamposBusquedaAvanzada() {
            const titulo = document.getElementById('titulo').value.trim();
            const autores = document.getElementById('autores').value.trim();
            const tema = document.getElementById('tema').value.trim();
            const anioDesde = document.getElementById('anioDesde').value.trim();
            const anioHasta = document.getElementById('anioHasta').value.trim();
            const tipoMaterial = document.getElementById('tipoMaterial').value;
            const biblioteca = document.getElementById('biblioteca').value;
            const idioma = document.getElementById('idioma').value;
            const disponibilidad = document.getElementById('disponibilidad').value;

            const boton = document.getElementById('btnBusquedaAvanzada');

            const algunCampoLleno = (
                titulo !== "" ||
                autores !== "" ||
                tema !== "" ||
                tipoMaterial !== "" ||
                biblioteca !== "" ||
                idioma !== "" ||
                disponibilidad !== "" ||
                (anioDesde !== "" && anioHasta !== "")
            );

            boton.disabled = !algunCampoLleno;
        }

        function aplicarCheckboxLateral(grupoSelector, hiddenFieldId) {
            if (!busquedaEjecutada) {
                return;
            }

            let valores = [];
            $(`${grupoSelector}:checked`).each(function () {
                valores.push($(this).val());
            });
            $(`#${hiddenFieldId}`).val(valores.join(','));
            mostrarIndicadorCarga();
            __doPostBack('<%= btnBusquedaAvanzadaServidor.UniqueID %>', '');
        }

        // Funciones de inicialización con jQuery
        $(document).ready(function () {
            // Inicializar el estado de los filtros (deshabilitados al cargar)
            validarAccesoFiltros();

            // Función para manejar floating labels
            function handleFloatingLabels() {
                $('.floating-input').each(function () {
                    const $input = $(this);

                    // Para inputs de texto y número
                    if ($input.is('input')) {
                        // Al hacer focus, mover label hacia arriba
                        $input.on('focus', function () {
                            $(this).addClass('has-focus');
                        });

                        // Al perder focus, verificar si tiene contenido
                        $input.on('blur', function () {
                            $(this).removeClass('has-focus');
                            if ($(this).val().trim() !== '') {
                                $(this).addClass('has-value');
                            } else {
                                $(this).removeClass('has-value');
                            }
                        });

                        // Al escribir, mantener label arriba
                        $input.on('input', function () {
                            if ($(this).val().trim() !== '') {
                                $(this).addClass('has-value');
                            } else if (!$(this).is(':focus')) {
                                $(this).removeClass('has-value');
                            }
                        });
                    }

                    // Para selects
                    if ($input.is('select')) {
                        $input.on('focus', function () {
                            $(this).addClass('has-focus');
                        });

                        $input.on('blur', function () {
                            $(this).removeClass('has-focus');
                            if ($(this).val() && $(this).val() !== '') {
                                $(this).addClass('has-value');
                            } else {
                                $(this).removeClass('has-value');
                            }
                        });

                        $input.on('change', function () {
                            if ($(this).val() && $(this).val() !== '') {
                                $(this).addClass('has-value');
                            } else {
                                $(this).removeClass('has-value');
                            }
                        });
                    }
                });
            }

            // Inicializar floating labels
            handleFloatingLabels();

            // INTERCEPTORES ACTUALIZADOS (sin alertas molestas)
            $('.accordion-button').on('click', function (e) {
                if (!validarAccesoFiltros()) {
                    e.preventDefault();
                    e.stopPropagation();
                    // Removida la alerta molesta
                }
            });

            // Interceptar clicks en los controles de filtro (sin alerta)
            $('#filtroAccordion input, #filtroAccordion select').on('focus click', function (e) {
                if (!busquedaEjecutada) {
                    e.preventDefault();
                    e.blur();
                    // Removida la alerta molesta
                }
            });

            // Prevenir que el modal se cierre automáticamente
            $('#busquedaAvanzadaModal').on('hide.bs.modal', function (e) {
                if (!$(e.clickTarget).hasClass('modal-close-btn')) {
                    // e.preventDefault();
                }
            });

            // Manejar click del botón de búsqueda avanzada
            $('[data-bs-target="#busquedaAvanzadaModal"]').on('click', function (e) {
                e.preventDefault();
                $('#busquedaAvanzadaModal').modal('show');
            });

            // Re-inicializar cuando se abre el modal
            $('#busquedaAvanzadaModal').on('shown.bs.modal', function () {
                handleFloatingLabels();
            });

            // Agregar validación de años al ejecutar búsqueda
            $('#anioPublicacion').on('blur', function () {
                validarRangoAnios();
            });

            // MANEJO ACTUALIZADO DE DROPDOWNS (sin alertas y con limpieza)
            $('#<%= ddlTema.ClientID %>, #<%= ddlIdioma.ClientID %>').on('change', function (e) {
                if (!busquedaEjecutada) {
                    e.preventDefault();
                    this.selectedIndex = 0; // Resetear selección
                    // Removida la alerta molesta
                    return false;
                } else {
                    // Permitir el postback solo si hay búsqueda ejecutada
                    mostrarIndicadorCarga();
                }
            });

            // Agregar botón para limpiar filtros (opcional)
            // Puedes agregar esto al HTML si quieres un botón de "Limpiar filtros"
            /*
            $('#btnLimpiarFiltros').on('click', function(e) {
                e.preventDefault();
                limpiarFiltros();
            });
            */

            // Validar cada vez que el usuario escribe o selecciona
            $('#titulo, #autores, #tema, #anioDesde, #anioHasta, #tipoMaterial, #biblioteca, #idioma, #disponibilidad')
                .on('input change', validarCamposBusquedaAvanzada);

            // Validar al abrir el modal
            $('#busquedaAvanzadaModal').on('shown.bs.modal', validarCamposBusquedaAvanzada);



        });
    </script>
</asp:Content>
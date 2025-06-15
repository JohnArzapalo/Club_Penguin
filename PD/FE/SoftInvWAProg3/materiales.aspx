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
                        <!-- Fecha -->
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
                              <div class="floating-label-group">
                                <input type="number"
                                       class="form-control floating-input text-center"
                                       id="anioFiltro"
                                       min="1900" max="2100"
                                       style="border-radius: 8px; border: 1px solid #dee2e6;"
                                       onkeydown="verificarEnterAnio(event)" />
                                <label for="anioFiltro" class="floating-label">Año</label>
                              </div>
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

                        <!-- Tema -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingTema">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTema"
                                        aria-expanded="false" aria-controls="collapseTema">
                                    Tema
                                </button>
                            </h2>
                            <div id="collapseTema" class="accordion-collapse collapse" aria-labelledby="headingTema">
                                <div class="accordion-body">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="temaFiltro"
                                               value="Informática" id="radioTemaInformatica"
                                               onchange="seleccionarTema('<%= hdnTema.ClientID %>', '<%= btnFiltroTema.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTemaInformatica">Informática</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="temaFiltro"
                                               value="Base de Datos" id="radioTemaBaseDatos"
                                               onchange="seleccionarTema('<%= hdnTema.ClientID %>', '<%= btnFiltroTema.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTemaBaseDatos">Base de Datos</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="temaFiltro"
                                               value="Inteligencia Artificial" id="radioTemaIA"
                                               onchange="seleccionarTema('<%= hdnTema.ClientID %>', '<%= btnFiltroTema.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioTemaIA">Inteligencia Artificial</label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Idioma -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingIdioma">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseIdioma"
                                        aria-expanded="false" aria-controls="collapseIdioma">
                                    Idioma
                                </button>
                            </h2>
                            <div id="collapseIdioma" class="accordion-collapse collapse" aria-labelledby="headingIdioma">
                                <div class="accordion-body">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="idiomaFiltro"
                                               value="Español" id="radioIdiomaEspanol"
                                               onchange="seleccionarIdioma('<%= hdnIdioma.ClientID %>', '<%= btnFiltroIdioma.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioIdiomaEspanol">Español</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="idiomaFiltro"
                                               value="Inglés" id="radioIdiomaIngles"
                                               onchange="seleccionarIdioma('<%= hdnIdioma.ClientID %>', '<%= btnFiltroIdioma.UniqueID %>', this)" />
                                        <label class="form-check-label" for="radioIdiomaIngles">Inglés</label>
                                    </div>
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

                            <!-- Año de publicación -->
                            <div class="mb-4 form-group-material">
                                <input type="number" id="anioPublicacion" min="1900" max="2100" placeholder=" ">
                                <label for="anioPublicacion">Año de publicación</label>
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
                                 <select class="form-select form-select-lg floating-input" id="idioma" style="border-radius: 8px; border: 1px solid #dee2e6;">
                                     <option value="" disabled selected></option>
                                     <option value="Español">Español</option>
                                     <option value="Inglés">Inglés</option>
                                 </select>
                                 <label for="idioma" class="floating-label">Idioma</label>
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
                                <button type="button"
                                        class="btn btn-busqueda-avanzada btn-lg px-5"
                                        onclick="ejecutarBusquedaAvanzada()">
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

        /* Ocultar la primera opción vacía en los SELECT */
        .floating-input option:first-child {
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

        $(document).ready(function () {
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
        });
        function verificarEnterAutor(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                aplicarFiltroLateral('autorFiltro', '<%= hdnAutores.ClientID %>', '<%= btnFiltroAutor.UniqueID %>');
            }
        }
        function seleccionarDisponibilidad(hiddenFieldId, botonUniqueID, radio) {
            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }
        // Función para búsqueda simple (botón de lupa)
        function realizarBusqueda() {
            var terminoBusqueda = $('#<%= txtBuscar.ClientID %>').val().trim();

            if (terminoBusqueda === '') {
                alert('Por favor, ingresa un término de búsqueda');
                return;
            }

            // Aquí puedes agregar la lógica para realizar la búsqueda simple
            console.log('Realizando búsqueda simple con término:', terminoBusqueda);

            // Si necesitas enviar al servidor, puedes usar:
            // __doPostBack('btnBusquedaSimpleServidor', terminoBusqueda);
        }
        function seleccionarBiblioteca(hiddenFieldId, botonUniqueID, radio) {
            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }
        function aplicarFiltroAnio() {
            const valor = document.getElementById('anioFiltro').value.trim();
            document.getElementById('<%= hdnAnioDesde.ClientID %>').value = valor;
            document.getElementById('<%= hdnAnioHasta.ClientID %>').value = valor;
    mostrarIndicadorCarga();
            __doPostBack('<%= btnFiltroAnio.UniqueID %>', '');
        }
        function seleccionarIdioma(hiddenFieldId, botonUniqueID, radio) {
            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }
        function aplicarCheckboxYBuscar(grupoSelector, hiddenFieldId, botonUniqueID) {
            let valores = [];
            $(`${grupoSelector}:checked`).each(function () {
                valores.push($(this).val());
            });
            $(`#${hiddenFieldId}`).val(valores.join(','));

            mostrarIndicadorCarga();

            __doPostBack(botonUniqueID, '');
        }
        // Función alternativa para ejecutar búsqueda avanzada sin JSON
        function ejecutarBusquedaAvanzada() {
            const values = {
                titulo: document.getElementById('titulo').value,
                autores: document.getElementById('autores').value,
                tema: document.getElementById('tema').value,
                anioPublicacion: document.getElementById('anioPublicacion').value,
                tipoMaterial: document.getElementById('tipoMaterial').value,
                biblioteca: document.getElementById('biblioteca').value,
                idioma: document.getElementById('idioma').value,
                disponibilidad: document.getElementById('disponibilidad').value
            };

            // Asignar los valores a los HiddenFields ASP.NET
            document.getElementById('<%= hdnTitulo.ClientID %>').value = values.titulo;
            document.getElementById('<%= hdnAutores.ClientID %>').value = values.autores;
            document.getElementById('<%= hdnTema.ClientID %>').value = values.tema;
            document.getElementById('<%= hdnAnioDesde.ClientID %>').value = values.anioPublicacion;
            document.getElementById('<%= hdnTipoMaterial.ClientID %>').value = values.tipoMaterial;
            document.getElementById('<%= hdnBiblioteca.ClientID %>').value = values.biblioteca;
    document.getElementById('<%= hdnIdioma.ClientID %>').value = values.idioma;
    document.getElementById('<%= hdnDisponibilidad.ClientID %>').value = values.disponibilidad;

    // Ocultar el modal y mostrar el indicador
    $('#busquedaAvanzadaModal').modal('hide');
    mostrarIndicadorCarga();

    // Hacer postback al botón oculto
           __doPostBack('<%= btnBusquedaAvanzadaServidor.UniqueID %>', '');
        }

        function aplicarFiltroLateral(idCampo, hiddenFieldId, botonUniqueID) {
            const valor = $(`#${idCampo}`).val();
            $(`#${hiddenFieldId}`).val(valor);
            mostrarIndicadorCarga();
            __doPostBack(botonUniqueID, ''); // ✅ Usa el botón correcto
        }
        function seleccionarTipoMaterial(hiddenFieldId, botonUniqueID, radio) {
            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }
        function aplicarCheckboxLateral(grupoSelector, hiddenFieldId) {
            let valores = [];
            $(`${grupoSelector}:checked`).each(function () {
                valores.push($(this).val());
            });
            $(`#${hiddenFieldId}`).val(valores.join(','));
            mostrarIndicadorCarga();
            __doPostBack('<%= btnBusquedaAvanzadaServidor.UniqueID %>', '');
        }

        function seleccionarTema(hiddenFieldId, botonUniqueID, radio) {
            if (radio.checked) {
                document.getElementById(hiddenFieldId).value = radio.value;
                mostrarIndicadorCarga();
                __doPostBack(botonUniqueID, '');
            }
        }
        // Función para mostrar indicador de carga (opcional)
        function mostrarIndicadorCarga() {
            document.getElementById("spinnerOverlay").style.display = "block";
        }
        function verificarEnterAnio(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                aplicarFiltroAnio();
            }
        }
        // Función para limpiar el formulario de búsqueda avanzada
        function limpiarFormularioAvanzado() {
            $('#busquedaAvanzadaModal input, #busquedaAvanzadaModal select').each(function () {
                $(this).val('').removeClass('has-value has-focus');
            });
        }

        // Función para validar rangos de años
        function validarRangoAnios() {
            var anioDesde = parseInt($('#anioPublicacion').val());
            if (isNaN(anioDesde) || anioDesde === 0) {
                alert('El año publicacion no puede ser null');
                return false;
            }
            return true;
        }

        // Agregar validación de años al ejecutar búsqueda
        $('#anioPublicacion').on('blur', function () {
            validarRangoAnios();
        });
    </script>
</asp:Content>

using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using SoftInvWAProg3.servicios;

namespace SoftInvWAProg3
{
    public partial class prestamos : System.Web.UI.Page
    {
        private MaterialWSClient materialCliente = new MaterialWSClient();
        private BibliotecaWSClient bibliotecaCliente = new BibliotecaWSClient();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                List<dynamic> reservas = ObtenerReservasUsuario();
                rptReservas.DataSource = reservas;
                rptReservas.DataBind();
                pnlSinReservas.Visible = (reservas.Count == 0);

                List<dynamic> prestamos = ObtenerPrestamosUsuario();
                rptPrestamos.DataSource = prestamos;
                rptPrestamos.DataBind();
                pnlSinPrestamos.Visible = (prestamos.Count == 0);
            }
        }

        protected List<dynamic> ObtenerReservasUsuario()
        {
            int usuarioId = Convert.ToInt32(Session["id"]);

            ReservaWSClient cliente = new ReservaWSClient();
            reservaDTO[] reservas = cliente.listarReservasVigentesPorUsuario(usuarioId);

            List<dynamic> resultados = new List<dynamic>();

            if (reservas != null)
            {
                foreach (reservaDTO r in reservas)
                {
                    materialDTO material = materialCliente.obtenerMaterialPorId(r.material.materialId);

                    resultados.Add(new
                    {
                        ReservaId = r.reservaId,
                        Titulo = material.titulo,
                        Biblioteca = "Biblioteca de Ciencias Sociales",
                        FechaVencimiento = r.fechaVencimiento.ToString("dd/MM/yyyy"),
                        Tipo = material.tipoMaterial,
                        EstadoReserva = r.estadoReserva
                    });
                }
            }

            return resultados;
        }

        protected List<dynamic> ObtenerPrestamosUsuario()
        {
            int usuarioId = Convert.ToInt32(Session["id"]);

            CirculacionWSClient cliente = new CirculacionWSClient();
            circulacionDTO[] prestamos = cliente.listarPrestamosPorUsuario(usuarioId);

            List<dynamic> resultados = new List<dynamic>();

            if (prestamos != null)
            {
                foreach (circulacionDTO p in prestamos)
                {
                    materialDTO material = materialCliente.obtenerMaterialPorId(p.ejemplar.material.materialId);
                    bibliotecaDTO biblioteca = bibliotecaCliente.obtenerBibliotecaPorId(p.ejemplar.biblioteca.bibliotecaId);

                    resultados.Add(new
                    {
                        PrestamoId = p.circulacionId,
                        Titulo = material.titulo,
                        Biblioteca = biblioteca.nombre,
                        FechaDevolucion = p.fechaDevolucion.ToString("dd/MM/yyyy"),
                        Tipo = material.tipoMaterial,
                        EstadoPrestamo = p.estadoPrestamo
                    });
                }
            }

            return resultados;
        }

        protected void rptReservas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerDetalleReserva")
            {
                int id = Convert.ToInt32(e.CommandArgument);
                Response.Redirect("~/detalle_reserva.aspx?reservaId=" + id);
            }
        }

        protected void rptPrestamos_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "VerDetallePrestamo")
            {
                int id = Convert.ToInt32(e.CommandArgument);
                Response.Redirect("~/detalle_circulacion.aspx?id=" + id);
            }
        }

        public static string ObtenerClaseEstado(string estado)
        {
            switch (estado)
            {
                case "VIGENTE": return "verde";
                case "DEVUELTO_A_TIEMPO": return "azul";
                case "DEVUELTO_CON_RETRASO":
                case "DEVUELTO_DANADO_O_PERDIDO":
                case "DEVUELTO_RETRASO_Y_DANO_PERDIDA":
                case "NO_DEVUELTO": return "rojo";
                default: return "gris";
            }
        }

        protected string ObtenerIconoPorTipo(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "Images/libro.svg";
                case "ARTICULO": return "Images/articulo.svg";
                case "TESIS": return "Images/tesis.svg";
                default: return "Images/default.svg";
            }
        }

        protected string ObtenerClaseChip(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "chip-libro";
                case "ARTICULO": return "chip-articulo";
                case "TESIS": return "chip-tesis";
                default: return string.Empty;
            }
        }

        protected string ObtenerNombreMostrar(object tipoObj)
        {
            string tipo = tipoObj.ToString().ToUpper();

            switch (tipo)
            {
                case "LIBRO": return "Libro";
                case "ARTICULO": return "Artículo";
                case "TESIS": return "Tesis";
                default: return tipo;
            }
        }

        public static string ObtenerTextoEstadoPrestamo(string estado)
        {
            switch (estado)
            {
                case "VIGENTE": return "Vigente";
                case "DEVUELTO_A_TIEMPO": return "Devuelto a tiempo";
                case "DEVUELTO_CON_RETRASO": return "Devuelto con retraso";
                case "DEVUELTO_DANADO_O_PERDIDO": return "Devuelto con daño o pérdida declarada";
                case "DEVUELTO_RETRASO_Y_DANO_PERDIDA": return "Devuelto con retraso y daño/pérdida";
                case "NO_DEVUELTO": return "No devuelto";
                default: return estado;
            }
        }

        public static string ObtenerTextoEstadoReserva(string estado)
        {
            switch (estado)
            {
                case "VIGENTE": return "Vigente";
                case "VENCIDA": return "Vencida";
                default: return estado;
            }
        }

    }
}

using System;
using System.Collections.Generic;
using SoftInvWAProg3.ReservaWS;
using SoftInvWAProg3.CirculacionWS;
using SoftInvWAProg3.MaterialWS;
using SoftInvWAProg3.BibliotecaWS;

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

                rptReservas.DataSource = ObtenerReservasUsuario();
                rptReservas.DataBind();

                rptPrestamos.DataSource = ObtenerPrestamosUsuario();
                rptPrestamos.DataBind();
            }
        }

        protected List<dynamic> ObtenerReservasUsuario()
        {
            return new List<dynamic>
            {
                new {
                    ReservaId = 202505121,
                    Titulo = "Introducción a la Programación",
                    Biblioteca = "Biblioteca Central",
                    FechaVencimiento = "13/05/2025",
                    Tipo = "LIBRO",
                    EstadoReserva = "VIGENTE"
                }
            };
        }

        /*protected List<dynamic> ObtenerReservasUsuario()
        {
            int usuarioId = Convert.ToInt32(Session["id"]);

            ReservaWS.ReservaWSClient cliente = new ReservaWS.ReservaWSClient();
            ReservaWS.reservaDTO[] reservas = cliente.listarReservasVigentesPorUsuario(usuarioId);

            List<dynamic> resultados = new List<dynamic>();

            foreach (ReservaWS.reservaDTO r in reservas)
            {
                MaterialWS.materialDTO material = materialCliente.obtenerMaterialPorId(r.material.materialId);
                
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

            return resultados;
        }*/

        protected List<dynamic> ObtenerPrestamosUsuario()
        {
            return new List<dynamic>
            {
                new {
                    PrestamoId = 202505121,
                    Titulo = "Programación en Java",
                    Biblioteca = "Biblioteca de Ciencias",
                    FechaDevolucion = "20/05/2025",
                    Tipo = "LIBRO",
                    EstadoPrestamo = "VIGENTE"
                },
                new {
                    PrestamoId = 202505012,
                    Titulo = "Tendencias actuales...",
                    Biblioteca = "Biblioteca Central",
                    FechaDevolucion = "08/05/2025",
                    Tipo = "ARTICULO",
                    EstadoPrestamo = "DEVUELTO_A_TIEMPO"
                },
                new {
                    PrestamoId = 202504043,
                    Titulo = "Metodologías activas...",
                    Biblioteca = "Biblioteca Central",
                    FechaDevolucion = "12/04/2025",
                    Tipo = "TESIS",
                    EstadoPrestamo = "DEVUELTO_CON_RETRASO"
                },
                new {
                    PrestamoId = 202506021,
                    Titulo = "La ira de Melgar...",
                    Biblioteca = "Biblioteca del Complejo de Innovación Académica",
                    FechaDevolucion = "12/04/2025",
                    Tipo = "TESIS",
                    EstadoPrestamo = "NO_DEVUELTO"
                }
            };
        }

        /*protected List<dynamic> ObtenerPrestamosUsuario()
        {
            int usuarioId = Convert.ToInt32(Session["id"]);

            CirculacionWS.CirculacionWSClient cliente = new CirculacionWS.CirculacionWSClient();
            CirculacionWS.circulacionDTO[] prestamos = cliente.listarPrestamosPorUsuario(usuarioId);

            List<dynamic> resultados = new List<dynamic>();

            foreach (CirculacionWS.circulacionDTO p in prestamos)
            {
                MaterialWS.materialDTO material = materialCliente.obtenerMaterialPorId(p.ejemplar.material.materialId);
                BibliotecaWS.bibliotecaDTO biblioteca = bibliotecaCliente.obtenerBibliotecaPorId(p.ejemplar.biblioteca.bibliotecaId);

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

            return resultados;
        }*/

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
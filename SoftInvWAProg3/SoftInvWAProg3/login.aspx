
<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="login.aspx.cs" Inherits="SoftInvWAProg3.login" %>

<!DOCTYPE html>
<html lang="es">
<head runat="server">
    <meta charset="utf-8" />
    <title>Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    <link href="Content/estilos_login.css" rel="stylesheet" />
</head>
<body>
    <form id="form1" runat="server">
        <div class="container">
            <div class="left-panel">
                <div class="logo d-flex align-items-center gap-2">
                  <img src="Images/logo.png" alt="Logo" style="width: 28px; height: 28px;" />
                  <span class="fw-semibold text-dark" style="line-height: 1.2;">
                    Sistema de Bibliotecas Universitarias
                  </span>
                </div>
                <h2>Iniciar Sesión</h2>
                <p>Para acceder a tu cuenta, ingresa tu correo electrónico y contraseña en los campos correspondientes.</p>
                <div class="input-group">
                    <asp:TextBox ID="txtCorreo" runat="server" CssClass="input" TextMode="SingleLine" placeholder=" " />
                    <label for="txtCorreo">Correo electrónico</label>
                </div>
                <div class="input-group">
                    <asp:TextBox ID="txtContrasena" runat="server" CssClass="input" TextMode="Password" placeholder=" " />
                    <label for="txtContrasena">Contraseña</label>
                </div>
                <asp:Button ID="btnIngresar" runat="server" Text="Ingresar" CssClass="btn" OnClick="btnIngresar_Click" />
                <asp:Label ID="lblMensaje" runat="server" CssClass="mensaje-error" Visible="false" />
            </div>
            <div class="right-panel">
                <img src="Images/login.jpg" alt="Biblioteca" />
            </div>
        </div>
    </form>

    <script>
        function validarCampos() {
            const correo = document.getElementById('<%= txtCorreo.ClientID %>');
            const contrasena = document.getElementById('<%= txtContrasena.ClientID %>');
            const boton = document.getElementById('<%= btnIngresar.ClientID %>');

            if (correo.value.trim() !== "" && contrasena.value.trim() !== "") {
                boton.disabled = false;
            } else {
                boton.disabled = true;
            }
        }

        window.onload = function () {
            const correo = document.getElementById('<%= txtCorreo.ClientID %>');
            const contrasena = document.getElementById('<%= txtContrasena.ClientID %>');
            const boton = document.getElementById('<%= btnIngresar.ClientID %>');

            boton.disabled = true;

            correo.addEventListener("input", validarCampos);
            contrasena.addEventListener("input", validarCampos);
        };
    </script>
</body>
</html>

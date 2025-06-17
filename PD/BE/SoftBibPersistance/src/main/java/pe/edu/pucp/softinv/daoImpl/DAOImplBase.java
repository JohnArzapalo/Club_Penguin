package pe.edu.pucp.softinv.daoImpl;

import pe.edu.pucp.softinv.db.DBManager;
import pe.edu.pucp.softinv.daoImpl.util.Columna;
import pe.edu.pucp.softinv.daoImpl.util.TipoOperacion;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;


public abstract class DAOImplBase {
    
    private String correoDeOrigen;
    private String correoDeDestino;
    private String asunto;
    private String mensajeDeTexto;
    private String contraseña16Digitos;
    protected String nombreTabla;
    protected ArrayList<Columna> listaColumnas;
    protected Boolean retornarLlavePrimaria;
    protected Boolean mostrarSentenciaSQL;
    protected Connection conexion;
    protected CallableStatement statement;
    protected ResultSet resultSet;
    
    
private boolean envioDeMensajes(){
    try{
        Properties p = new Properties();  
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        p.setProperty("mail.smtp.port", "587");
        p.setProperty("mail.smtp.user", correoDeOrigen);
        p.setProperty("mail.smtp.auth", "true");
        
        Session s = Session.getInstance(p);
        MimeMessage mensaje = new MimeMessage(s);
        mensaje.setFrom(new InternetAddress(correoDeOrigen));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoDeDestino));
        mensaje.setSubject(asunto);
        mensaje.setText(mensajeDeTexto, "UTF-8");
        
        Transport t = s.getTransport("smtp");
        t.connect(correoDeOrigen, contraseña16Digitos);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        
        Logger.getLogger(DAOImplBase.class.getName()).log(Level.INFO, "Correo enviado exitosamente");
        return true;
        
    } catch (MessagingException e) {
        Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, "Error al enviar correo", e);
        return false;
    }
}

// Actualizar el método público:
public boolean envioDeCorreos(String origen, String destino, String asunto, String txt, String contra16Digitos){
    this.correoDeOrigen = origen;
    this.correoDeDestino = destino;
    this.asunto = asunto;
    this.mensajeDeTexto = txt;
    this.contraseña16Digitos = contra16Digitos;
    return envioDeMensajes();
}
    
    public DAOImplBase(String nombreTabla) {
        this.nombreTabla = nombreTabla;
        this.retornarLlavePrimaria = false;
        this.mostrarSentenciaSQL = true;
        this.incluirListaDeColumnas();
    }

    private void incluirListaDeColumnas() {
        this.listaColumnas = new ArrayList<>();
        this.configurarListaDeColumnas();
    }

    protected abstract void configurarListaDeColumnas();

    protected void abrirConexion() {
        this.conexion = DBManager.getInstance().getConnection();
    }

    protected void cerrarConexion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.close();
            this.conexion = null;
        }
    }

    protected void iniciarTransaccion() throws SQLException {
        this.abrirConexion();
        this.conexion.setAutoCommit(false);
    }

    protected void comitarTransaccion() throws SQLException {
        this.conexion.commit();
    }

    protected void rollbackTransaccion() throws SQLException {
        if (this.conexion != null) {
            this.conexion.rollback();
        }
    }

    protected void colocarSQLenStatement(String sql) throws SQLException {
        this.statement = this.conexion.prepareCall(sql);
    }

    protected Integer ejecutarModificacionEnBD() throws SQLException {
        if (this.mostrarSentenciaSQL) {
            Logger.getLogger(DAOImplBase.class.getName()).log(Level.INFO, this.statement.toString());
        }
        return this.statement.executeUpdate();
    }

    protected void ejecutarConsultaEnBD() throws SQLException {
        this.resultSet = this.statement.executeQuery();
    }

    protected Integer insertar() {
        return this.ejecuta_DML(TipoOperacion.INSERTAR);
    }

    protected Integer modificar() {
        return this.ejecuta_DML(TipoOperacion.MODIFICAR);
    }

    protected Integer eliminar() {
        return this.ejecuta_DML(TipoOperacion.ELIMINAR);
    }

    private Integer ejecuta_DML(TipoOperacion tipo_Operacion) {
    int resultado = 0;
    try {
        this.iniciarTransaccion();
        String sql = null;

        // Switch para generar SQL
        switch (tipo_Operacion) {
            case INSERTAR -> sql = this.generarSQLParaInsercion();
            case MODIFICAR -> sql = this.generarSQLParaModificacion();
            case ELIMINAR -> sql = this.generarSQLParaEliminacion();
        }

        this.colocarSQLenStatement(sql);

        // Switch para incluir parámetros
        switch (tipo_Operacion) {
            case INSERTAR -> this.incluirValorDeParametrosParaInsercion();
            case MODIFICAR -> this.incluirValorDeParametrosParaModificacion();
            case ELIMINAR -> this.incluirValorDeParametrosParaEliminacion();
        }

        resultado = this.ejecutarModificacionEnBD();

        // Si se desea retornar el ID autogenerado
        if (this.retornarLlavePrimaria && tipo_Operacion == TipoOperacion.INSERTAR) {
            resultado = this.retornarUltimoAutoGenerado();
        }

        this.comitarTransaccion();
        
    } catch (SQLException ex) {
        System.err.println("Error al intentar ejecutar consulta - " + tipo_Operacion.toString() + ": " + ex);
        try {
            this.rollbackTransaccion();
        } catch (SQLException ex1) {
            System.err.println("Error al hacer rollback - " + ex1);
        }
    } finally {
        try {
            this.cerrarConexion();
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexión - " + ex);
        }
    }
    return resultado;
}

    protected String generarSQLParaInsercion() {
        String sql = "INSERT INTO ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat("(");
        String sql_columnas = "";
        String sql_parametros = "";
        for (Columna columna : this.listaColumnas) {
            if (!columna.getEsAutoGenerado()) {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                    sql_parametros = sql_parametros.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_parametros = sql_parametros.concat("?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(") VALUES (");
        sql = sql.concat(sql_parametros);
        sql = sql.concat(")");
        return sql;
    }

    protected String generarSQLParaModificacion() {
        String sql = "UPDATE ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" SET ");
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            } else {
                if (!sql_columnas.isBlank()) {
                    sql_columnas = sql_columnas.concat(", ");
                }
                sql_columnas = sql_columnas.concat(columna.getNombre());
                sql_columnas = sql_columnas.concat("=?");
            }
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM ";
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" WHERE ");
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
        }
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaObtenerPorId() {
        String sql = "SELECT ";
        String sql_columnas = "";
        String sql_predicado = "";
        for (Columna columna : this.listaColumnas) {
            if (columna.getEsLlavePrimaria()) {
                if (!sql_predicado.isBlank()) {
                    sql_predicado = sql_predicado.concat(", ");
                }
                sql_predicado = sql_predicado.concat(columna.getNombre());
                sql_predicado = sql_predicado.concat("=?");
            }
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombreTabla);
        sql = sql.concat(" WHERE ");
        sql = sql.concat(sql_predicado);
        return sql;
    }

    protected String generarSQLParaListarTodos() {
        String sql = "SELECT ";
        String sql_columnas = "";
        for (Columna columna : this.listaColumnas) {
            if (!sql_columnas.isBlank()) {
                sql_columnas = sql_columnas.concat(", ");
            }
            sql_columnas = sql_columnas.concat(columna.getNombre());
        }
        sql = sql.concat(sql_columnas);
        sql = sql.concat(" FROM ");
        sql = sql.concat(this.nombreTabla);
        return sql;
    }

    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    public Integer retornarUltimoAutoGenerado() {
        Integer resultado = null;
        try {
            String sql = "select @@last_insert_id as id";
            this.statement = this.conexion.prepareCall(sql);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.next()) {
                resultado = this.resultSet.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar retornarUltimoAutoGenerado - " + ex);
        }
        return resultado;
    }

    public void obtenerPorId() {
        try {
            this.abrirConexion();
            String sql = this.generarSQLParaObtenerPorId();
            this.colocarSQLenStatement(sql);
            this.incluirValorDeParametrosParaObtenerPorId();
            this.ejecutarConsultaEnBD();
            if (this.resultSet.next()) {
                instanciarObjetoDelResultSet();
            } else {
                limpiarObjetoDelResultSet();
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar obtenerPorId - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
    }

    public List listarTodos() {
        List lista = new ArrayList<>();
        try {
            this.abrirConexion();
            String sql = this.generarSQLParaListarTodos();
            this.colocarSQLenStatement(sql);
            this.ejecutarConsultaEnBD();
            while (this.resultSet.next()) {
                agregarObjetoALaLista(lista);
            }
        } catch (SQLException ex) {
            System.err.println("Error al intentar listarTodos - " + ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión - " + ex);
            }
        }
        return lista;
    }

    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void instanciarObjetoDelResultSet() throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void limpiarObjetoDelResultSet() {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void agregarObjetoALaLista(List lista) throws SQLException {
        throw new UnsupportedOperationException("Método no sobreescrito.");
    }

    protected void ejecutarProcedimientoAlmacenado(String sql, Boolean conTransaccion) {
        Consumer incluirValorDeParametros = null;
        Object parametros = null;
        this.ejecutarProcedimientoAlmacenado(sql, incluirValorDeParametros, parametros, conTransaccion);
    }

    protected void ejecutarProcedimientoAlmacenado(String sql, Consumer incluirValorDeParametros, Object parametros,
            Boolean conTransaccion) {
        try {
            if (conTransaccion) {
                this.iniciarTransaccion();
            } else {
                this.abrirConexion();
            }
            this.colocarSQLenStatement(sql);
            if (incluirValorDeParametros != null) {
                incluirValorDeParametros.accept(parametros);
            }
            this.ejecutarModificacionEnBD();
            if (conTransaccion) {
                this.comitarTransaccion();
            }
        } catch (SQLException ex) {
            if (conTransaccion)
                try {
                    this.rollbackTransaccion();
                } catch (SQLException ex1) {
                    Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex1);
                }
            Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected <T> T ejecutarProcedimiento(
    String sql,
    List<Object> parametrosEntrada,
    Map<Integer, Integer> parametrosSalida,
    Function<Map<Integer, Object>, T> procesadorResultado
    ) throws SQLException {
        try {
            this.abrirConexion();
            this.statement = this.conexion.prepareCall(sql);

            // Parámetros de entrada
            for (int i = 0; i < parametrosEntrada.size(); i++) {
                this.statement.setObject(i + 1, parametrosEntrada.get(i));
            }

            // Parámetros de salida
            for (Map.Entry<Integer, Integer> entry : parametrosSalida.entrySet()) {
                this.statement.registerOutParameter(entry.getKey(), entry.getValue());
            }

            this.statement.execute();

            // Recolectar resultados de salida
            Map<Integer, Object> resultados = new HashMap<>();
            for (Integer idx : parametrosSalida.keySet()) {
                resultados.put(idx, this.statement.getObject(idx));
            }

            return procesadorResultado.apply(resultados);
        } finally {
            this.cerrarConexion();
        }
    }
}

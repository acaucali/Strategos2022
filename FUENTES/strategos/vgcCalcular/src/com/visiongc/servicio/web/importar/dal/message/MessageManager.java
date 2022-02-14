/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.message;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.visiongc.servicio.strategos.message.model.Message;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class MessageManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public MessageManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public int saveMessage(Message message, Statement stmExt)
	{
		String CLASS_METHOD = "MessageManager.saveMessage";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int resultado = 10000;

	    try
	    {
			int respuesta = 0;
	    	if (stmExt != null)
	    		stm = stmExt;
	    	else
	    	{
		    	cn = new ConnectionManager(pm).getConnection();
				ConexAbierta = true;
				cn.setAutoCommit(false);
				transActiva = true;
				
				stm = cn.createStatement();
	    	}
			
			sql = "UPDATE AFW_MESSAGE ";
			sql = sql + "SET mensaje = '" + message.getMensaje() + "'";
			sql = sql + ", estatus = " + message.getEstatus();
			sql = sql + ", tipo = " + message.getTipo();
			sql = sql + ", fuente = '" + message.getFuente() + "'";
			sql = sql + " WHERE usuario_id = " + message.getUsuarioId().toString();
			sql = sql + " AND fecha = " + "{ts '" + message.getFecha() + "'}";
	    			
		    respuesta = stm.executeUpdate(sql);

		    if (respuesta == 0)
		    {
    			sql = "INSERT INTO AFW_MESSAGE ";
    			sql = sql + "(usuario_id, fecha, estatus, mensaje, tipo, fuente) ";
    			sql = sql + "VALUES (";
    			sql = sql + message.getUsuarioId().toString() + ", ";
    			sql = sql + "" + "{ts '" + message.getFecha() + "'}, ";
    		    sql = sql + message.getEstatus() + ", ";
    		    sql = sql + "'" + message.getMensaje() + "', ";
    		    sql = sql + message.getTipo() + ", ";
    		    sql = sql + "'" + message.getFuente() + "'";
    		    sql = sql + ")";

    		    respuesta = stm.executeUpdate(sql);

    		    if (respuesta != 0)
    		    	resultado = 10000;
		    }
		    else
		    	resultado = 10000;

		    if (resultado == 10000)
		    {
		    	if (transActiva && stmExt == null) 
		    	{
					cn.commit();
				    cn.setAutoCommit(true);
					transActiva = false;
		    	}
		    }
		    else if (transActiva && stmExt == null) 
		    {
				cn.rollback();
			    cn.setAutoCommit(true);
				transActiva = false;
		    }
    	}
	    catch (Exception e) 
	    {
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

			if (transActiva && stmExt == null) 
			{
				try {
					cn.rollback();
				} catch (SQLException e1) {
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
				}
			    try {
					cn.setAutoCommit(true);
				} catch (SQLException e1) {
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
				}
			}

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
    	}
		finally
		{
			if (stmExt == null)
			{
				try 
				{
					stm.close();
				} 
				catch (Exception localException8) { }
				      
				try 
				{
					if (transActiva)
						cn.setAutoCommit(true);
					if (ConexAbierta) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}
	    	
	    return resultado;
	}
}

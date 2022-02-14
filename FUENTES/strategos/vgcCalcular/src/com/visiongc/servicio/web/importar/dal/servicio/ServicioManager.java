/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class ServicioManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public ServicioManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public int saveServicio(Servicio servicio, Statement stmExt)
	{
		String CLASS_METHOD = "ServicioManager.saveServicio";
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
			
			sql = "UPDATE AFW_SERVICIO ";
			sql = sql + "SET mensaje = '" + servicio.getMensaje() + "'";
			sql = sql + ", estatus = " + servicio.getEstatus();
			sql = sql + ", log = " + (!servicio.getLog().equals("") ? ("'" + servicio.getLog() + "'") : "null");
			sql = sql + " WHERE usuario_id = " + servicio.getUsuarioId().toString();
			sql = sql + " AND fecha = " + "{ts '" + servicio.getFecha() + "'}";
		    sql = sql + " AND nombre = '" + servicio.getNombre() + "'";
	    			
		    respuesta = stm.executeUpdate(sql);

		    if (respuesta == 0)
		    {
    			sql = "INSERT INTO AFW_SERVICIO ";
    			sql = sql + "(usuario_id, fecha, nombre, estatus, mensaje, log) ";
    			sql = sql + "VALUES (";
    			sql = sql + servicio.getUsuarioId().toString() + ", ";
    			sql = sql + "" + "{ts '" + servicio.getFecha() + "'}, ";
    		    sql = sql + "'" + servicio.getNombre() + "', ";
    		    sql = sql + servicio.getEstatus() + ", ";
    		    sql = sql + "'" + servicio.getMensaje() + "', ";
    		    sql = sql + (!servicio.getLog().equals("") ? ("'" + servicio.getLog() + "'") : "null");
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

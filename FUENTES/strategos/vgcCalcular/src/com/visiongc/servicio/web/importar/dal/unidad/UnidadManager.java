/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.unidad;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.visiongc.servicio.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class UnidadManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public UnidadManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
  	public UnidadMedida getUnidadMedidaPorcentaje(Statement stmExt)
  	{
		String CLASS_METHOD = "UnidadManager.getUnidadMedidaPorcentaje";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		UnidadMedida porcentaje = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean ConexAbierta = false;

		String unidadId = null;
		String nombre = null;
		String tipo = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
				ConexAbierta = true;
			}
		
			sql = "SELECT ";
			sql = sql + "unidad_id, ";
			sql = sql + "nombre, ";
			sql = sql + "tipo ";
			sql = sql + "FROM unidad ";
			sql = sql + "WHERE nombre = '%'";
			
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				porcentaje = new UnidadMedida(); 
				unidadId = rs.getString("unidad_id");
				nombre = rs.getString("nombre");
				tipo = rs.getString("tipo");

				if (unidadId != null)
					porcentaje.setUnidadId(Long.parseLong(unidadId));
				if (nombre != null)
					porcentaje.setNombre(nombre);
				if (tipo != null)
					porcentaje.setTipo(Boolean.parseBoolean(tipo));
			}
			rs.close();

	  		if (porcentaje == null)
	  			porcentaje = crearUnidadPorcentaje(stmExt);
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	porcentaje = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			if (stmExt == null)
			{
				try { stm.close(); } catch (Exception localException5) { }
				if (ConexAbierta)
					try { cn.close(); cn = null;} catch (Exception localException6) { }
			}
		}
		
		return porcentaje;		
  	}
  	
  	private UnidadMedida crearUnidadPorcentaje(Statement stmExt)
  	{
		String CLASS_METHOD = "UnidadManager.crearUnidadPorcentaje";
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
			if (stmExt != null)
				stm = stmExt;
			else
			{
		    	cn = new ConnectionManager(pm).getConnection();
				ConexAbierta = true;
				cn.setAutoCommit(false);
				stm = cn.createStatement();
				transActiva = true;
			}

			sql = "INSERT INTO UNIDAD ";
			sql = sql + "(Unidad_Id, nombre, tipo) ";
			sql = sql + "VALUES (1, '%', 0)";

		    int respuesta = stm.executeUpdate(sql);
	    	
	    	if (respuesta != 0)
	    	{
    			if (transActiva && stmExt == null) 
    			{
    				if (resultado != 10000) 
    				{
    					cn.rollback();
    				    cn.setAutoCommit(true);
    					transActiva = false;
    				} 
    				else 
    				{
    					cn.commit();
    				    cn.setAutoCommit(true);
    					transActiva = false;
    				}
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
				try 
				{
					cn.rollback();
				} 
				catch (SQLException e2) 
				{
					argsReemplazo[1] = argsReemplazo[1] + e2.getMessage();
				}
			    
				try 
				{
					cn.setAutoCommit(true);
				} 
				catch (SQLException e1) 
				{
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
				}
			}

	    	resultado = 10003;
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
	    
	    UnidadMedida unidad = new UnidadMedida();
	    unidad.setUnidadId(1L);
	    unidad.setNombre("%");
	    unidad.setTipo(false);
	    
  		return unidad;
  	}
}

/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.visiongc.framework.model.Usuario;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class UsuarioManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public UsuarioManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	public Long LoadAdmin(Statement stmExt)
	{
		String CLASS_METHOD = "UsuarioManager.LoadAdmin";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String usuarioId = null;
		boolean ConexAbierta = false;
		
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
			sql = sql + "usuario_id ";
			sql = sql + "FROM afw_usuario ";
			sql = sql + "WHERE u_id = 'admin'";
			
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
				usuarioId = rs.getString("usuario_id");
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	usuarioId = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			if (stmExt == null)
			{
				try { stm.close(); } catch (Exception localException5) { }
				if (ConexAbierta) try { cn.close(); cn = null;} catch (Exception localException6) { }
			}
		}
		
		return usuarioId != null ? Long.parseLong(usuarioId) : null;		
	}
	
	public Usuario Load(Long usuarioId, Statement stmExt)
	{
		String CLASS_METHOD = "UsuarioManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		Usuario usuario = null;
		boolean ConexAbierta = false;
		
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
			sql = sql + "usuario_id, ";
			sql = sql + "full_name ";
			sql = sql + "FROM afw_usuario ";
			sql = sql + "WHERE usuario_id = " + usuarioId;
			
			rs = stm.executeQuery(sql);

			if (rs.next()) 
			{
				usuario = new Usuario();
				usuario.setUsuarioId(usuarioId);
				usuario.setFullName(rs.getString("full_name"));
			}
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	usuario = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			if (stmExt == null)
			{
				try { stm.close(); } catch (Exception localException5) { }
				if (ConexAbierta) try { cn.close(); cn = null;} catch (Exception localException6) { }
			}
		}
		
		return usuario;		
	}
}

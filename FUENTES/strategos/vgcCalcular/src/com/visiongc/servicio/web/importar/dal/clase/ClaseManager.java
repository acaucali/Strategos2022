/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.clase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.visiongc.servicio.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;

import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class ClaseManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public ClaseManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public ClaseIndicadores Load(Long claseId, Statement stmExt)
	{
		String CLASS_METHOD = "ClaseManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		ClaseIndicadores clase = new ClaseIndicadores();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String padreId = null;
		String id = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
		
			sql = "SELECT ";
			sql = sql + "Clase.Clase_ID, ";
			sql = sql + "Clase.Padre_ID ";
			sql = sql + "FROM Clase ";
			sql = sql + "WHERE Clase_Id = " + claseId;
		    
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				id = rs.getString("Clase_ID");
				padreId = rs.getString("Padre_ID");
				
				clase = new ClaseIndicadores();
				if (id != null)
				{
					clase.setClaseId(Long.parseLong(id));
					if (padreId != null)
						clase.setPadreId(Long.parseLong(padreId));
				}
				else
					clase = null;
			}
			rs.close();

			if (clase != null && clase.getClaseId() != null)
			{
				clase.setHijos(new ArrayList<ClaseIndicadores>());
				clase.getHijos().clear();
				sql = "SELECT ";
				sql = sql + "Clase.Clase_ID, ";
				sql = sql + "Clase.Padre_ID ";
				sql = sql + "FROM Clase ";
				sql = sql + "WHERE Padre_Id = " + clase.getClaseId();
			    
				rs = stm.executeQuery(sql);
				ClaseIndicadores hijo;
				while (rs.next())
				{
					hijo = new ClaseIndicadores();
					id = rs.getString("Clase_ID");
					padreId = rs.getString("Padre_ID");

					if (id != null)
					{
						hijo.setClaseId(Long.parseLong(id));
						if (padreId != null)
							hijo.setPadreId(Long.parseLong(padreId));
					}
					else
						hijo = null;
					
			        clase.getHijos().add(hijo);
				}
				rs.close();
			}
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	clase = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return clase;		
	}
	
	public List<ClaseIndicadores> getArbolCompletoClaseIndicadores(Long claseIndicadorId, Statement stmExt)
	{
		List<ClaseIndicadores> arbol = new ArrayList<ClaseIndicadores>();
	    ClaseIndicadores claseIndicadoresActual = Load(claseIndicadorId, stmExt);
	    if (claseIndicadoresActual != null) 
	    {
	    	arbol.add(claseIndicadoresActual);
	    	List<ClaseIndicadores> hijos = claseIndicadoresActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		ClaseIndicadores hijo = (ClaseIndicadores)i.next();
	    		getArbolCompletoClaseIndicadoresInterno(hijo.getClaseId(), arbol, stmExt);
	    	}
	    }
	    
	    return arbol;
	}

	private void getArbolCompletoClaseIndicadoresInterno(Long claseIndicadorId, List<ClaseIndicadores> arbol, Statement stmExt) 
	{
		ClaseIndicadores claseIndicadoresActual = Load(claseIndicadorId, stmExt);
	    if (claseIndicadoresActual != null) 
	    {
	    	arbol.add(claseIndicadoresActual);
	    	List<ClaseIndicadores> hijos = claseIndicadoresActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		ClaseIndicadores hijo = (ClaseIndicadores)i.next();
	    		getArbolCompletoClaseIndicadoresInterno(hijo.getClaseId(), arbol, stmExt);
	    	}
	    }
	}
}

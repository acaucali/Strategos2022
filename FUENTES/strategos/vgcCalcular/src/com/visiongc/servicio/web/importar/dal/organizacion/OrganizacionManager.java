/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.organizacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class OrganizacionManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public OrganizacionManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	public OrganizacionStrategos Load(Long organizacionId, Statement stmExt)
	{
		String CLASS_METHOD = "OrganizacionManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		OrganizacionStrategos organizacion = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean ConexAbierta = false;

		String padreId = null;
		String id = null;
		String alertaMinMax = null;
		String alertaMetaN1 = null;
		String alertaMetaN2 = null;
		String enlaceParcial = null;
		
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
			sql = sql + "Padre_Id, ";
			sql = sql + "Alerta_Meta_N1, ";
			sql = sql + "Alerta_Meta_N2, ";
			sql = sql + "Alerta_Min_Max, ";
			sql = sql + "Enlace_Parcial ";
			sql = sql + "FROM Organizacion ";
			sql = sql + "WHERE Organizacion_Id = " + organizacionId;
			
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				alertaMinMax = rs.getString("alerta_min_max");
				alertaMetaN1 = rs.getString("alerta_meta_n1");
				alertaMetaN2 = rs.getString("alerta_meta_n2");
				padreId = rs.getString("Padre_Id");
				enlaceParcial = rs.getString("enlace_parcial");

				organizacion = new OrganizacionStrategos();
				organizacion.setOrganizacionId(organizacionId);
				if (padreId != null)
					organizacion.setPadreId(Long.parseLong(padreId));
				if (alertaMinMax != null)
					organizacion.setPorcentajeZonaAmarillaMinMaxIndicadores(Byte.parseByte(alertaMinMax));
				if (alertaMetaN1 != null)
					organizacion.setPorcentajeZonaVerdeMetaIndicadores(Byte.parseByte(alertaMetaN1));
				if (alertaMetaN2 != null)
					organizacion.setPorcentajeZonaAmarillaMetaIndicadores(Byte.parseByte(alertaMetaN2));
				if (enlaceParcial != null)
					organizacion.setEnlaceParcial(enlaceParcial);
			}
			rs.close();

			if (organizacion != null && organizacion.getOrganizacionId() != null)
			{
				organizacion.setHijos(new ArrayList<OrganizacionStrategos>());
				organizacion.getHijos().clear();
				sql = "SELECT ";
				sql = sql + "Organizacion.Organizacion_Id, ";
				sql = sql + "Organizacion.Padre_ID ";
				sql = sql + "FROM Organizacion ";
				sql = sql + "WHERE Padre_Id = " + organizacion.getOrganizacionId();
			    
				rs = stm.executeQuery(sql);
				OrganizacionStrategos hijo;
				while (rs.next())
				{
					hijo = new OrganizacionStrategos();
					id = rs.getString("Organizacion_Id");
					padreId = rs.getString("Padre_Id");

					if (id != null)
					{
						hijo.setOrganizacionId(Long.parseLong(id));
						if (padreId != null)
							hijo.setPadreId(Long.parseLong(padreId));
					}
					else
						hijo = null;
					
					organizacion.getHijos().add(hijo);
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
	    	
	    	organizacion = null;
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
		
		return organizacion;		
	}
	
	public String GetRutaCompletaIds(OrganizacionStrategos organizacion, String separador)
	{
		String ruta = "";
		
		ruta = organizacion.getOrganizacionId().toString();
		if (organizacion.getPadreId() != null && organizacion.getPadre() != null)
			ruta = ruta + separador + GetRutaCompletaIds(organizacion.getPadre(), separador); 
		
		return ruta;
	}
	
	public List<OrganizacionStrategos> getArbolCompletoOrganizaciones(Long organizacionId, Statement stmExt)
	{
		List<OrganizacionStrategos> arbol = new ArrayList<OrganizacionStrategos>();
		OrganizacionStrategos organizacionActual = Load(organizacionId, stmExt);
	    if (organizacionActual != null) 
	    {
	    	arbol.add(organizacionActual);
	    	List<OrganizacionStrategos> hijos = organizacionActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		OrganizacionStrategos hijo = (OrganizacionStrategos)i.next();
	    		getArbolCompletoOrganizacioneInterno(hijo.getOrganizacionId(), arbol, stmExt);
	    	}
	    }
	    
	    return arbol;
	}

	private void getArbolCompletoOrganizacioneInterno(Long organizacionId, List<OrganizacionStrategos> arbol, Statement stmExt) 
	{
		OrganizacionStrategos organizacionActual = Load(organizacionId, stmExt);
	    if (organizacionActual != null) 
	    {
	    	arbol.add(organizacionActual);
	    	List<OrganizacionStrategos> hijos = organizacionActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		OrganizacionStrategos hijo = (OrganizacionStrategos)i.next();
	    		getArbolCompletoOrganizacioneInterno(hijo.getOrganizacionId(), arbol, stmExt);
	    	}
	    }
	}
}

/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.perspectiva;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.servicio.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlan;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.strategos.planes.model.PerspectivaEstado;
import com.visiongc.servicio.strategos.planes.model.PerspectivaEstadoPK;
import com.visiongc.servicio.strategos.planes.model.Plan;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.web.importar.dal.medicion.MedicionManager;
import com.visiongc.servicio.web.importar.dal.plan.PlanManager;
import com.visiongc.servicio.web.importar.dal.util.DalUtil;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class PerspectivaManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public PerspectivaManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public List<Perspectiva> getIndicadoresPorPerspectiva(Long indicadorId, Long planId, Statement stmExt)
	{
		String CLASS_METHOD = "PerspectivaManager.getIndicadoresPorPerspectiva";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Perspectiva> perspectivas = new ArrayList<Perspectiva>();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		String perspectivaId = null;
		String planIdRs = null;
		
		try
		{
			Perspectiva perspectiva;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
		
			sql = "SELECT ";
			sql = sql + "perspectiva.perspectiva_id, ";
			sql = sql + "perspectiva.plan_id ";
		    sql = sql + "from perspectiva, indicador_por_perspectiva";

		    if ((indicadorId != null) || (planId != null)) 
		    	sql = sql + " where ";

		    sql = sql + "indicador_por_perspectiva.indicador_Id = " + indicadorId.toString() + " and perspectiva.perspectiva_id = indicador_por_perspectiva.perspectiva_id";

			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				perspectivaId = rs.getString("perspectiva_id");
				planIdRs = rs.getString("plan_id");

				perspectiva = new Perspectiva();
				perspectiva.setPerspectivaId(Long.parseLong(perspectivaId));
				perspectiva.setPlanId(Long.parseLong(planIdRs));
				
				perspectivas.add(perspectiva);
			}
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return perspectivas;		
	}
	
	public Perspectiva Load(Long perspectivaId, Statement stmExt)
	{
		String CLASS_METHOD = "PerspectivaManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Perspectiva perspectiva = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		String id = null;
		String padreId = null;
		String planId = null;
		String nlParIndicadorId = null;
		String nlAnoIndicadorId = null;
		String nombre = null;
		String tipoCalculo = null;
		String frecuencia = null;
		
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
			sql = sql + "Perspectiva.Perspectiva_ID, ";
			sql = sql + "Perspectiva.Padre_ID, ";
			sql = sql + "Perspectiva.nl_par_indicador_id, ";
			sql = sql + "Perspectiva.nl_ano_indicador_id, ";
			sql = sql + "Perspectiva.nombre, ";
			sql = sql + "Perspectiva.tipo_calculo, ";
			sql = sql + "Perspectiva.plan_Id, ";
			sql = sql + "Perspectiva.frecuencia ";
			sql = sql + "FROM Perspectiva ";
			sql = sql + "WHERE Perspectiva_Id = " + perspectivaId;
		    
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				padreId = rs.getString("Padre_ID");
				nombre = rs.getString("nombre");
				tipoCalculo = rs.getString("tipo_calculo");
				planId = rs.getString("plan_Id");
				nlParIndicadorId = rs.getString("nl_par_indicador_id");
				nlAnoIndicadorId = rs.getString("nl_ano_indicador_id");
				frecuencia = rs.getString("frecuencia");
				
				perspectiva = new Perspectiva();
				perspectiva.setPerspectivaId(perspectivaId);
				if (padreId != null)
					perspectiva.setPadreId(Long.parseLong(padreId));
				if (tipoCalculo != null)
					perspectiva.setTipoCalculo(Byte.parseByte(tipoCalculo));
				if (planId != null)
					perspectiva.setPlanId(Long.parseLong(planId));
				if (nlAnoIndicadorId != null)
					perspectiva.setNlAnoIndicadorId(Long.parseLong(nlAnoIndicadorId));
				if (nlParIndicadorId != null)
					perspectiva.setNlParIndicadorId(Long.parseLong(nlParIndicadorId));
				if (frecuencia != null)
					perspectiva.setFrecuencia(Byte.parseByte(frecuencia));
				if (nombre != null)
					perspectiva.setNombre(nombre);
			}
			rs.close();

			if (perspectiva != null && perspectiva.getPerspectivaId() != null)
			{
				perspectiva.setHijos(new ArrayList<Perspectiva>());
				perspectiva.getHijos().clear();
				sql = "SELECT ";
				sql = sql + "Perspectiva.Perspectiva_ID, ";
				sql = sql + "Perspectiva.Padre_ID ";
				sql = sql + "FROM Perspectiva ";
				sql = sql + "WHERE Padre_Id = " + perspectiva.getPerspectivaId();
			    
				rs = stm.executeQuery(sql);
				Perspectiva hijo;
				while (rs.next())
				{
					hijo = new Perspectiva();
					id = rs.getString("Perspectiva_ID");
					padreId = rs.getString("Padre_ID");

					if (id != null)
					{
						hijo.setPerspectivaId(Long.parseLong(id));
						if (padreId != null)
							hijo.setPadreId(Long.parseLong(padreId));
					}
					else
						hijo = null;
					
					perspectiva.getHijos().add(hijo);
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
	    	
	    	perspectiva = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return perspectiva;		
	}
	
	public String GetRutaCompletaIds(Perspectiva perspectiva, String separador)
	{
		String ruta = "";
		
		ruta = perspectiva.getPerspectivaId().toString();
		if (perspectiva.getPadreId() != null && perspectiva.getPadre() != null)
			ruta = ruta + separador + GetRutaCompletaIds(perspectiva.getPadre(), separador); 
		
		return ruta;
	}
	
	public List<Perspectiva> getPerspectivas(String[] orden, String[] tipoOrden, Map<String, Object> filtros, Statement stmExt)
	{
		String CLASS_METHOD = "PerspectivaManager.getPerspectivas";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String tablasConsulta = "";
	    String condicionesConsulta = " where ";
	    boolean hayCondicionesConsulta = false;
	    boolean hayIndicador = false;
	    if (filtros != null) 
	    {
	    	Iterator<?> iter = filtros.keySet().iterator();
	    	String fieldName = null;
	    	String fieldValue = null;
	    	while (iter.hasNext()) 
	    	{
	    		fieldName = (String)iter.next();
	    		if (filtros.get(fieldName) == null)
	    			fieldValue = null;
	    		else if ((filtros.get(fieldName) instanceof String)) 
	    			fieldValue = (String)filtros.get(fieldName);
				else if (!(filtros.get(fieldName) instanceof List))
					fieldValue = new DalUtil().getValorCondicionConsulta(filtros.get(fieldName));

	    		if (fieldName.equals("nombre")) 
	    		{
	    			condicionesConsulta = condicionesConsulta + "lower(perspectiva." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("descripcion")) 
	    		{
	    			condicionesConsulta = condicionesConsulta + "lower(perspectiva." + fieldName + ") like '%" + fieldValue.toLowerCase() + "%' and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("frecuencia")) 
	    		{
	    			condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("excluirIds")) 
	    		{
	    			String[] ids = fieldValue.split(",");
	    			for (int i = 0; i < ids.length; i++) 
	    			{
	    				Long id = new Long(ids[i]);
	    				condicionesConsulta = condicionesConsulta + "perspectiva.perspectiva_Id != " + id.toString() + " and ";
	    				hayCondicionesConsulta = true;
	    			}
	    		} 
	    		else if (fieldName.equals("indicador_Id")) 
	    		{
	    			tablasConsulta = tablasConsulta + ", indicador_por_perspectiva ";
	    			condicionesConsulta = condicionesConsulta + "perspectiva.perspectiva_Id = indicador_por_perspectiva.perspectiva_Id and indicador_por_perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			hayCondicionesConsulta = true;
	    			hayIndicador = true;
	    		} 
	    		else if (fieldName.equalsIgnoreCase("plan_id")) 
	    		{
	    			condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("padre_Id")) 
	    		{
	    			if (fieldValue != null)
	    				condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			else 
	    				condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + " is null and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("perspectiva_id")) 
	    		{
    				condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			hayCondicionesConsulta = true;
	    		} 
	    		else if (fieldName.equals("ano")) 
	    		{
	    			if (fieldValue != null)
	    				condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
	    			else 
	    				condicionesConsulta = condicionesConsulta + "perspectiva." + fieldName + " is null and ";
	    			hayCondicionesConsulta = true;
	    		}
	    	}
	    }

	    String ordenConsulta = "";
	    if ((orden != null) && (orden.length > 0)) 
	    {
	    	ordenConsulta = " order by ";
	    	for (int i = 0; i < orden.length; i++) 
	    	{
	    		String ordenActual = orden[i];
	    		String tipoOrdenActual = tipoOrden[i];
	    		if ((tipoOrdenActual == null) || (tipoOrdenActual.equals(""))) 
	    			ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " asc,";
	    		else if (tipoOrdenActual.equalsIgnoreCase("asc"))
	    			ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " asc,";
	    		else 
	    			ordenConsulta = ordenConsulta + "perspectiva." + ordenActual + " desc,";
	    	}

	    	ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 1);
	    }

	    if (hayCondicionesConsulta)
	    	condicionesConsulta = condicionesConsulta.substring(0, condicionesConsulta.length() - 5);
	    else 
	      condicionesConsulta = "";

		String objetoConsulta = "distinct ";
		objetoConsulta = objetoConsulta + "perspectiva.perspectiva_id, ";
		objetoConsulta = objetoConsulta + "perspectiva.nombre, ";
		objetoConsulta = objetoConsulta + "perspectiva.padre_Id, ";
		objetoConsulta = objetoConsulta + "perspectiva.tipo_calculo, ";
		objetoConsulta = objetoConsulta + "perspectiva.plan_Id, ";
		objetoConsulta = objetoConsulta + "perspectiva.nl_par_indicador_id, ";
		objetoConsulta = objetoConsulta + "perspectiva.nl_ano_indicador_id, ";
		objetoConsulta = objetoConsulta + "perspectiva.frecuencia";
		
		if (tablasConsulta.indexOf("indicador_por_perspectiva") == -1 && hayIndicador)
			tablasConsulta = tablasConsulta + ", indicador_por_perspectiva ";

		condicionesConsulta = "select " + objetoConsulta + " from perspectiva " + tablasConsulta + condicionesConsulta + ordenConsulta;
	    
		List<Perspectiva> perspectivas = new ArrayList<Perspectiva>();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		String id = null;
		String padreId = null;
		String planId = null;
		String nlParIndicadorId = null;
		String nlAnoIndicadorId = null;
		String nombre = null;
		String tipoCalculo = null;
		String frecuencia = null;
		
		try
		{
			Perspectiva perspectiva;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			
			rs = stm.executeQuery(condicionesConsulta);
			
			while (rs.next()) 
			{
				id = rs.getString("perspectiva_id");
				nombre = rs.getString("nombre");
				padreId = rs.getString("padre_Id");
				tipoCalculo = rs.getString("tipo_calculo");
				planId = rs.getString("plan_Id");
				nlParIndicadorId = rs.getString("nl_par_indicador_id");
				nlAnoIndicadorId = rs.getString("nl_ano_indicador_id");
				frecuencia = rs.getString("frecuencia");
				if (id != null)
				{
					perspectiva = new Perspectiva();
					perspectiva.setPerspectivaId(Long.parseLong(id));
					if (padreId != null)
						perspectiva.setPadreId(Long.parseLong(padreId));
					if (tipoCalculo != null)
						perspectiva.setTipoCalculo(Byte.parseByte(tipoCalculo));
					if (planId != null)
						perspectiva.setPlanId(Long.parseLong(planId));
					if (nlAnoIndicadorId != null)
						perspectiva.setNlAnoIndicadorId(Long.parseLong(nlAnoIndicadorId));
					if (nlParIndicadorId != null)
						perspectiva.setNlParIndicadorId(Long.parseLong(nlParIndicadorId));
					if (frecuencia != null)
						perspectiva.setFrecuencia(Byte.parseByte(frecuencia));
					if (nombre != null)
						perspectiva.setNombre(nombre);
					perspectivas.add(perspectiva);
				}
			}
			rs.close();

		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return perspectivas;		
	}
	
	public List<Perspectiva> getArbolCompletoPerspectivas(Long perspectivaId, Statement stmExt)
	{
		List<Perspectiva> arbol = new ArrayList<Perspectiva>();
		Perspectiva perspectivaActual = Load(perspectivaId, stmExt);
	    if (perspectivaActual != null) 
	    {
	    	arbol.add(perspectivaActual);
	    	List<Perspectiva> hijos = perspectivaActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		Perspectiva hijo = (Perspectiva)i.next();
	    		getArbolCompletoPerspectivaInterno(hijo.getPerspectivaId(), arbol, stmExt);
	    	}
	    }
	    
	    return arbol;
	}

	private void getArbolCompletoPerspectivaInterno(Long perspectivaId, List<Perspectiva> arbol, Statement stmExt) 
	{
		Perspectiva perspectivaActual = Load(perspectivaId, stmExt);
	    if (perspectivaActual != null) 
	    {
	    	arbol.add(perspectivaActual);
	    	List<Perspectiva> hijos = perspectivaActual.getHijos();
	    	for (Iterator<?> i = hijos.iterator(); i.hasNext(); ) 
	    	{
	    		Perspectiva hijo = (Perspectiva)i.next();
	    		getArbolCompletoPerspectivaInterno(hijo.getPerspectivaId(), arbol, stmExt);
	    	}
	    }
	}
	
	public int deletePerspectivaEstados(Long perspectivaId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Statement stmExt)
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PerspectivaManager.deletePerspectivaEstados";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";

		try
		{
			ConexAbierta = true;
			transActiva = true;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
			}

			sql = "delete from perspectiva_nivel where perspectiva_Id = " + perspectivaId.longValue() + " and tipo = " + tipoEstado.byteValue();
		    boolean continuar = false;

		    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) 
		    {
		    	if (anoInicio.intValue() != anoFinal.intValue()) 
		    	{
		    		sql = sql + " and (((ano = " + anoInicio.intValue() + ") and (periodo >= " + periodoInicio.intValue() + "))";
		    		sql = sql + " or ((ano > " + anoInicio.intValue() + ") and (ano < " + anoFinal.intValue() + "))";
		    		sql = sql + " or ((ano = " + anoFinal.intValue() + ") and (periodo <= " + periodoFinal.intValue() + ")))";
		    	} 
		    	else 
		    	{
		    		sql = sql + " and ((ano = " + anoInicio.intValue() + ") and (periodo >= " + periodoInicio.intValue() + ")";
		    		sql = sql + " and (ano = " + anoFinal.intValue() + ") and (periodo <= " + periodoFinal.intValue() + "))";
		    	}
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    	continuar = true;
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano >= " + anoInicio.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano <= " + anoFinal.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano >= " + anoInicio.intValue();
		    	sql = sql + " and ano <= " + anoFinal.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and (((ano = " + anoInicio.intValue() + ") and (periodo >= " + periodoInicio.intValue() + "))";
		    	sql = sql + " or (ano > " + anoInicio.intValue() + "))";
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) 
		    {
		    	sql = sql + " and (((ano = " + anoFinal.intValue() + ") and (periodo <= " + periodoFinal.intValue() + "))";
		    	sql = sql + " or (ano < " + anoFinal.intValue() + "))";
		    	continuar = true;
		    }

		    if (continuar) 
		    	stm.executeUpdate(sql);
		    
			if (stmExt == null)
			{
				cn.commit();
				cn.setAutoCommit(true);
			}
				
			transActiva = false;
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
					if (transActiva && stmExt == null)
						cn.setAutoCommit(true);
					if (ConexAbierta && stmExt == null) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}
			
	    return resultado;
	}
	
	public List<IndicadorPerspectiva> getIndicadoresPerspectiva(Long perspectivaId, Statement stmExt) 
	{
		String CLASS_METHOD = "PerspectivaManager.getIndicadoresPerspectiva";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		IndicadorPerspectiva indicadorPerspectiva;
		IndicadorPerspectivaPK pk;
		List<IndicadorPerspectiva> indicadoresPerspectivas = new ArrayList<IndicadorPerspectiva>();

		String indicadorId = null;
		String peso = null;
		
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
			sql = sql + "Indicador_Id, ";
			sql = sql + "peso ";
			sql = sql + "FROM indicador_por_perspectiva ";
			sql = sql + "WHERE Perspectiva_Id = " + perspectivaId;
		    
			rs = stm.executeQuery(sql);

			while (rs.next())
			{
				indicadorPerspectiva = new IndicadorPerspectiva();
				
				indicadorId = rs.getString("Indicador_Id");
				peso = rs.getString("peso");

				if (indicadorId != null)
				{
					indicadorPerspectiva = new IndicadorPerspectiva();
					indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
					pk = new IndicadorPerspectivaPK();
					pk.setIndicadorId(Long.parseLong(indicadorId));
					pk.setPerspectivaId(perspectivaId);
					indicadorPerspectiva.setPk(pk);
					
					if (peso != null)
						indicadorPerspectiva.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(peso)));
					
					indicadoresPerspectivas.add(indicadorPerspectiva);
				}
					
			}

			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}

		return indicadoresPerspectivas;
	}
	
	public IndicadorPerspectiva LoadIndicadorPerspectiva(Long perspectivaId, Long indicadorId, Statement stmExt)
	{
		String CLASS_METHOD = "PerspectivaManager.LoadIndicadorPerspectiva";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		IndicadorPerspectiva perspectiva = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		String peso = null;
		
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
			sql = sql + "Indicador_Id, ";
			sql = sql + "peso ";
			sql = sql + "FROM indicador_por_perspectiva ";
			sql = sql + "WHERE Perspectiva_Id = " + perspectivaId;
			sql = sql + " AND Indicador_Id = " + indicadorId;
		    
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				peso = rs.getString("peso");
				
				perspectiva = new IndicadorPerspectiva();
				if (peso != null)
					perspectiva.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(peso)));
				else
					perspectiva = null;
			}
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	perspectiva = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return perspectiva;		
	}
	
	public int savePerspectivaEstado(Long perspectivaId, Byte tipoEstado, Integer ano, Integer periodo, Double valorEstado, Statement stmExt)
	{
	    int resultado = 10000;
		int actualizados = 0;
		String CLASS_METHOD = "PerspectivaManager.savePerspectivaEstado";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";

		try
		{
			ConexAbierta = true;
			transActiva = true;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
			}

			sql = "UPDATE perspectiva_nivel";
			if (valorEstado == null)
				sql = sql + " SET nivel = NULL";
			else
				sql = sql + " SET nivel = " + valorEstado;
			sql = sql + " WHERE perspectiva_Id = " + perspectivaId;
			sql = sql + " AND Tipo = " + tipoEstado;
			sql = sql + " AND Ano = " + ano;
			sql = sql + " AND Periodo = " + periodo;
			
			actualizados = stm.executeUpdate(sql);
			
		    if (actualizados == 0) 
		    {
				sql = "INSERT INTO perspectiva_nivel (perspectiva_Id, Tipo, Ano, Periodo, nivel)";
				sql = sql + " VALUES (" + perspectivaId + ", ";
				sql = sql + tipoEstado + ", ";
				sql = sql + ano + ", ";
				sql = sql + periodo + ", ";
				if (valorEstado == null)
					sql = sql + "NULL)";
				else
					sql = sql + valorEstado + ")";
				
				actualizados = stm.executeUpdate(sql);
		    } 

		    if (actualizados != 0) 
				resultado = 10000;
			else
				resultado = 10001;
		    
			if (stmExt == null && resultado == 10000)
			{
				cn.commit();
				cn.setAutoCommit(true);
			}
			else if (stmExt == null && resultado != 10000)
			{
				cn.rollback();
			    cn.setAutoCommit(true);
			}
				
			transActiva = false;
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
					if (transActiva && stmExt == null)
						cn.setAutoCommit(true);
					if (ConexAbierta && stmExt == null) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}

	    return resultado;
	}
	
	public int actualizarPerspectivaUltimoEstado(Long perspectivaId, Statement stmExt) 
	{
		String CLASS_METHOD = "PerspectivaManager.actualizarPerspectivaUltimoEstado";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		PerspectivaEstado estado = null;
		List<PerspectivaEstado> estados = new ArrayList<PerspectivaEstado>();
		ResultSet rs = null;
		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int resultado = 10000;
		
		String nivel = null;
		String periodo = null;
		String ano = null;
		
		try
		{
			ConexAbierta = true;
			transActiva = true;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
			}
			
			Perspectiva perspectiva = Load(perspectivaId, stmExt);
			Medicion ultimaMedicion = new MedicionManager(pm, log, messageResources).getUltimaMedicion(perspectiva.getNlAnoIndicadorId(), SerieTiempo.getSerieRealId(), stmExt);

			sql = "SELECT ";
			sql = sql + "periodo, ";
			sql = sql + "ano, ";
			sql = sql + "nivel ";
			sql = sql + "FROM perspectiva_nivel ";
			sql = sql + "WHERE Perspectiva_Id = " + perspectivaId.longValue();
			sql = sql + " AND tipo = " + TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue();
			if (ultimaMedicion != null && ultimaMedicion.getMedicionId() != null)
			{
				sql = sql + " AND ano = " + ultimaMedicion.getMedicionId().getAno().intValue();
				sql = sql + " AND periodo = " + ultimaMedicion.getMedicionId().getPeriodo().intValue();
			}
			sql = sql + " order by ano desc, periodo desc";
			
			rs = stm.executeQuery(sql);
			
			while (rs.next())
			{
				estado = new PerspectivaEstado();
				
				periodo = rs.getString("periodo");
				ano = rs.getString("ano");
				nivel = rs.getString("nivel");

				estado = new PerspectivaEstado();
				if (nivel != null)
					estado.setEstado(new Double(VgcFormatter.parsearNumeroFormateado(nivel)));
				estado.setPk(new PerspectivaEstadoPK());
				if (ano != null)
					estado.getPk().setAno(Integer.parseInt(ano));
				if (periodo != null)
					estado.getPk().setPeriodo(Integer.parseInt(periodo));
					
				estados.add(estado);
			}

			rs.close();
			
		    boolean actualizado = false;
		    int index = 0;
		    while ((!actualizado) && (index < estados.size())) 
		    {
		    	estado = (PerspectivaEstado)estados.get(index);

		    	if (estado.getEstado() != null) 
		    	{
					sql = "UPDATE perspectiva";
					sql = sql + " SET fecha_ultima_medicion = '" + estado.getPk().getPeriodo().toString() + "/" + estado.getPk().getAno() + "'";
					sql = sql + ", ultima_medicion_anual = " + estado.getEstado().doubleValue();
					sql = sql + " WHERE perspectiva_Id = " + perspectivaId;
		    	} 
		    	else
		    	{
					sql = "UPDATE perspectiva";
					sql = sql + " SET fecha_ultima_medicion = null";
					sql = sql + ", ultima_medicion_anual = null";
					sql = sql + " WHERE perspectiva_Id = " + perspectivaId;
		    	}

		    	stm.executeUpdate(sql);
	    		actualizado = true;
		    }

			ultimaMedicion = new MedicionManager(pm, log, messageResources).getUltimaMedicion(perspectiva.getNlParIndicadorId(), SerieTiempo.getSerieRealId(), stmExt);
		    
		    estados = new ArrayList<PerspectivaEstado>();
			sql = "SELECT ";
			sql = sql + "periodo, ";
			sql = sql + "ano, ";
			sql = sql + "nivel ";
			sql = sql + "FROM perspectiva_nivel ";
			sql = sql + "WHERE Perspectiva_Id = " + perspectivaId.longValue();
			sql = sql + " AND tipo = " + TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue();
			if (ultimaMedicion != null && ultimaMedicion.getMedicionId() != null)
			{
				sql = sql + " AND ano = " + ultimaMedicion.getMedicionId().getAno().intValue();
				sql = sql + " AND periodo = " + ultimaMedicion.getMedicionId().getPeriodo().intValue();
			}
			sql = sql + " order by ano desc, periodo desc";
			
			rs = stm.executeQuery(sql);
			
			while (rs.next())
			{
				estado = new PerspectivaEstado();
				
				periodo = rs.getString("periodo");
				ano = rs.getString("ano");
				nivel = rs.getString("nivel");

				estado = new PerspectivaEstado();
				if (nivel != null)
					estado.setEstado(new Double(VgcFormatter.parsearNumeroFormateado(nivel)));
				estado.setPk(new PerspectivaEstadoPK());
				if (ano != null)
					estado.getPk().setAno(Integer.parseInt(ano));
				if (periodo != null)
					estado.getPk().setPeriodo(Integer.parseInt(periodo));
					
				estados.add(estado);
			}

			rs.close();

		    actualizado = false;

		    index = 0;
		    while ((!actualizado) && (index < estados.size())) 
		    {
		    	estado = (PerspectivaEstado)estados.get(index);

		    	if (estado.getEstado() != null) 
		    	{
					sql = "UPDATE perspectiva";
					sql = sql + " SET ultima_medicion_parcial = " + estado.getEstado().doubleValue();
					sql = sql + " WHERE perspectiva_Id = " + perspectivaId;
		    	} 
		    	else
		    	{
					sql = "UPDATE perspectiva";
					sql = sql + " SET ultima_medicion_parcial = null";
					sql = sql + " WHERE perspectiva_Id = " + perspectivaId;
		    	}

		    	stm.executeUpdate(sql);
	    		actualizado = true;
		    }
		    
			if (stmExt == null)
			{
				cn.commit();
				cn.setAutoCommit(true);
			}
				
			transActiva = false;
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
	    	
	    	resultado = 10003;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			if (stmExt == null)
			{
				try 
				{
					stm.close();
				} 
				catch (Exception localException8) { }
				      
				try 
				{
					if (transActiva && stmExt == null)
						cn.setAutoCommit(true);
					if (ConexAbierta && stmExt == null) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}

		return resultado;
	}
	
	public int actualizarPerspectivaCrecimiento(Perspectiva perspectiva, Byte tipoEstado, Statement stmExt) 
	{
		String CLASS_METHOD = "PerspectivaManager.actualizarPerspectivaCrecimiento";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

	    int resultado = 10000;
		int actualizados = 0;
		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		Byte alerta = null;

		try
		{
			ConexAbierta = true;
			transActiva = true;
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
			}

		    IndicadorPlan indicadorplan = null;
		    if (perspectiva.getPadreId() != null)
		    {
			    if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
			    	indicadorplan = new PlanManager(this.pm, this.log, this.messageResources).getIndicadorPlan(perspectiva.getPlanId(), perspectiva.getNlParIndicadorId(), stm);
			    else if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
			    	indicadorplan = new PlanManager(this.pm, this.log, this.messageResources).getIndicadorPlan(perspectiva.getPlanId(), perspectiva.getNlAnoIndicadorId(), stm);
		    }
		    else
		    {
		    	Plan plan = new PlanManager(pm, log, messageResources).Load(perspectiva.getPlanId(), stm);
			    if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
			    	indicadorplan = new PlanManager(this.pm, this.log, this.messageResources).getIndicadorPlan(plan.getPlanId(), plan.getNlParIndicadorId(), stm);
			    else if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
			    	indicadorplan = new PlanManager(this.pm, this.log, this.messageResources).getIndicadorPlan(plan.getPlanId(), plan.getNlAnoIndicadorId(), stm);
		    }
		    	
		    if (indicadorplan != null) 
		    	alerta = indicadorplan.getCrecimiento();

		    if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
		    {
				if (alerta != null)
					sql = "update Perspectiva set crecimiento_parcial = " + alerta + " where perspectiva.perspectiva_Id = " + perspectiva.getPerspectivaId().longValue();
				else 
					sql = "update Perspectiva set crecimiento_parcial = null where perspectiva.perspectiva_Id = " + perspectiva.getPerspectivaId().longValue();
		    }
		    else if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
		    {
				if (alerta != null)
					sql = "update Perspectiva set crecimiento_anual = " + alerta + " where perspectiva.perspectiva_Id = " + perspectiva.getPerspectivaId().longValue();
				else 
					sql = "update Perspectiva set crecimiento_anual = null where perspectiva.perspectiva_Id = " + perspectiva.getPerspectivaId().longValue();
		    }
	
			actualizados = stm.executeUpdate(sql);
			
			if (actualizados != 0) 
				resultado = 10000;
			else
				resultado = 10001;
		    
			if (stmExt == null && resultado == 10000)
			{
				cn.commit();
				cn.setAutoCommit(true);
			}
			else if (stmExt == null && resultado != 10000)
			{
				cn.rollback();
			    cn.setAutoCommit(true);
			}
				
			transActiva = false;
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
					if (transActiva && stmExt == null)
						cn.setAutoCommit(true);
					if (ConexAbierta && stmExt == null) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}
		
		return resultado;
	}
}

/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.plan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.servicio.strategos.planes.model.IndicadorEstado;
import com.visiongc.servicio.strategos.planes.model.IndicadorEstadoPK;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlan;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.servicio.strategos.planes.model.Plan;
import com.visiongc.servicio.strategos.planes.model.PlanEstado;
import com.visiongc.servicio.strategos.planes.model.PlanEstadoPK;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.util.DalUtil;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class PlanManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public PlanManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	public Plan Load(Long planId, Statement stmExt)
	{
		String CLASS_METHOD = "PlanManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Plan plan = new Plan();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		String id = null;
		String nlParIndicadorId = null;
		String nlAnoIndicadorId = null;
		String nombre = null;
		
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
			sql = sql + "nombre, ";
			sql = sql + "plan_Id, ";
			sql = sql + "nl_par_indicador_id, ";
			sql = sql + "nl_ano_indicador_id ";
			sql = sql + "FROM Planes ";
			sql = sql + "WHERE Plan_Id = " + planId;
			
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				nombre = rs.getString("nombre");
				id = rs.getString("plan_Id");
				nlParIndicadorId = rs.getString("nl_par_indicador_id");
				nlAnoIndicadorId = rs.getString("nl_ano_indicador_id");
			
				plan = new Plan();
				plan.setPlanId(planId);
				
				if (nlAnoIndicadorId != null)
					plan.setNlAnoIndicadorId(Long.parseLong(nlAnoIndicadorId));
				if (nlParIndicadorId != null)
					plan.setNlParIndicadorId(Long.parseLong(nlParIndicadorId));
				if (nombre != null)
					plan.setNombre(nombre);
			}
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	plan = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return plan;		
	}
	
	public List<Plan> getPlanesAsociadosPorIndicador(Long indicadorId, boolean activos, boolean inactivos, Statement stmExt)
	{
		String CLASS_METHOD = "PlanManager.getPlanesAsociadosPorIndicador";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Plan> planes = new ArrayList<Plan>();
		Plan plan = new Plan();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		String planId = null;
		String activo = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
		
			sql = "SELECT Planes.Plan_Id, Planes.activo ";
			sql = sql + "FROM Planes, Perspectiva perspectiva, Indicador_Por_Perspectiva ";
			sql = sql + "WHERE Planes.plan_Id = perspectiva.plan_Id ";
			sql = sql + "and perspectiva.perspectiva_Id = Indicador_Por_Perspectiva.perspectiva_Id ";
			sql = sql + "and Indicador_Por_Perspectiva.indicador_Id = " + indicadorId;
			sql = sql + " and Planes.activo = 1";
			
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				planId = rs.getString("Plan_Id");
				activo = rs.getString("activo");

				plan = new Plan();
				if (planId != null)
					plan.setPlanId(Long.parseLong(planId));
				if (activo != null)
					plan.setActivo(Boolean.parseBoolean(activo));
				
				planes.add(plan);
			}
			rs.close();
			
		    boolean removerActivos = (inactivos) && (!activos);
		    boolean removerInactivos = (!inactivos) && (activos);

		    if ((removerActivos) || (removerInactivos)) 
		    {
		    	int index = 0;
		    	while (index < planes.size()) 
		    	{
		    		plan = (Plan)planes.get(index);
		    		if ((plan.getActivo().booleanValue()) && (removerActivos))
		    			planes.remove(index);
		    		else if ((!plan.getActivo().booleanValue()) && (removerInactivos))
		    			planes.remove(index);
		    		else 
		    			index++;
		    	}
		    }			
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
		
	    return planes;
	}
	
	public List<Plan> getPlanes(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map<String, Object> filtros, Statement stmExt)
	{
		String CLASS_METHOD = "PlanManager.getPlanes";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = " where ";
		boolean hayCondicionesConsulta = false;
		List<Plan> planes = new ArrayList<Plan>();
		Plan plan = new Plan();

		String planId = null;
		String activo = null;
		String nlParIndicadorId = null;
		String nlAnoIndicadorId = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}

		    if (filtros != null)
		    {
		    	Iterator<String> iter = filtros.keySet().iterator();
		    	String fieldName = null;

		    	while (iter.hasNext()) 
		    	{
		    		fieldName = (String)iter.next();

		    		if (fieldName.equals("nombre"))
		    		{
		    			sql = sql + "lower(Planes." + fieldName + ")" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "like") + " and ";
		    			hayCondicionesConsulta = true;		    			
		    		}
		    		else if (fieldName.equals("organizacion_Id"))
		    		{
		    			sql = sql + "Planes." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
		    			hayCondicionesConsulta = true;		    			
		    		}
		    		else if (fieldName.equals("plan_id"))
		    		{
		    			sql = sql + "Planes." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
		    			hayCondicionesConsulta = true;		    			
		    		}
		    		else if (fieldName.equals("activo"))
		    		{
		    			sql = sql + "Planes." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
		    			hayCondicionesConsulta = true;		    			
		    		}
		    		else if (fieldName.equals("tipo"))
		    		{
		    			sql = sql + "Planes." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
		    			hayCondicionesConsulta = true;		    			
		    		}
		    	}
		    }
			
			if (hayCondicionesConsulta)
				sql = sql.substring(0, sql.length() - 5);
			else 
				sql = "";

			String objetoConsulta = "distinct " +
					"Planes.Plan_Id, " +
					"Planes.nl_ano_indicador_id, " +
					"Planes.nl_par_indicador_id, " +
					"Planes.activo ";
			
			sql = "select " + objetoConsulta + " from Planes " + sql;
			
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				planId = rs.getString("Plan_Id");
				activo = rs.getString("activo");
				nlParIndicadorId = rs.getString("nl_par_indicador_id");
				nlAnoIndicadorId = rs.getString("nl_ano_indicador_id");

				plan = new Plan();
				if (planId != null)
					plan.setPlanId(Long.parseLong(planId));
				if (activo != null)
					plan.setActivo(Boolean.parseBoolean(activo));
				if (nlAnoIndicadorId != null)
					plan.setNlAnoIndicadorId(Long.parseLong(nlAnoIndicadorId));
				if (nlParIndicadorId != null)
					plan.setNlParIndicadorId(Long.parseLong(nlParIndicadorId));
				
				planes.add(plan);
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
		
	    return planes;
	}
	
	public int deletePlanEstados(Long planId, Byte tipoEstado, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Statement stmExt)
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PlanManager.deletePlanEstados";
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

			sql = "delete FROM Plan_Nivel where Plan_Nivel.plan_Id = " + planId + " and Plan_Nivel.tipo = " + tipoEstado;
		    boolean continuar = false;

		    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) 
		    {
		    	if (anoInicio.intValue() != anoFinal.intValue()) 
		    	{
		    		sql = sql + " and (((Plan_Nivel.ano = " + anoInicio + ") and (Plan_Nivel.periodo >= " + periodoInicio + "))";
		    		sql = sql + " or ((Plan_Nivel.ano > " + anoInicio + ") and (Plan_Nivel.ano < " + anoFinal + "))";
		    		sql = sql + " or ((Plan_Nivel.ano = " + anoFinal + ") and (Plan_Nivel.periodo <= " + periodoFinal + ")))";
		    	} 
		    	else 
		    	{
		    		sql = sql + " and ((Plan_Nivel.ano = " + anoInicio + ") and (Plan_Nivel.periodo >= " + periodoInicio + ")";
		    		sql = sql + " and (Plan_Nivel.ano = " + anoFinal + ") and (Plan_Nivel.periodo <= " + periodoFinal + "))";
		    	}	
		      
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    	continuar = true;
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and Plan_Nivel.ano >= " + anoInicio;
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and Plan_Nivel.ano <= " + anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and Plan_Nivel.ano >= " + anoInicio;
		    	sql = sql + " and Plan_Nivel.ano <= " + anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and (((Plan_Nivel.ano = " + anoInicio + ") and (Plan_Nivel.periodo >= " + periodoInicio + "))";
		    	sql = sql + " or (Plan_Nivel.ano > " + anoInicio + "))";
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) 
		    {
		    	sql = sql + " and (((Plan_Nivel.ano = " + anoFinal + ") and (Plan_Nivel.periodo <= " + periodoFinal + "))";
		    	sql = sql + " or (Plan_Nivel.ano < " + anoFinal + "))";
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
	
	public List<IndicadorPlan> getIndicadoresPlan(Long planId, Statement stmExt) 
	{
		String CLASS_METHOD = "PlanManager.getIndicadoresPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "";
		List<IndicadorPlan> indicadores = new ArrayList<IndicadorPlan>();
		IndicadorPlan indicador = new IndicadorPlan();
		IndicadorPlanPK indicadorpk = new IndicadorPlanPK();

		String valor = null;
		String indicadorId = null;
		Byte alerta = null;
		Byte tipoMedicion = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}

			sql = "select ";
			sql = sql + "indicador_id, ";
			sql = sql + "plan_id, ";
			sql = sql + "peso, ";
			sql = sql + "crecimiento, ";
			sql = sql + "tipo_medicion ";
			sql = sql + "FROM indicador_por_plan ";
			sql = sql + "where indicador_por_plan.plan_Id = " + planId; 
					
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				indicadorId = rs.getString("indicador_id");
				valor = rs.getString("peso");
				alerta = rs.getByte("crecimiento");
				tipoMedicion = rs.getByte("tipo_medicion");

				indicadorpk = new IndicadorPlanPK();
				if (indicadorId != null)
					indicadorpk.setIndicadorId(Long.parseLong(indicadorId));
				indicadorpk.setPlanId(planId);
				
				indicador = new IndicadorPlan();
				indicador.setPk(indicadorpk);
				if (valor != null)
					indicador.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				if (alerta != null)
					indicador.setCrecimiento(alerta);
				if (tipoMedicion != null)
					indicador.setTipoMedicion(tipoMedicion);

				indicadores.add(indicador);
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

		return indicadores;
	}
	
	public IndicadorPlan getIndicadorPlan(Long planId, Long indicadorId, Statement stmExt) 
	{
		String CLASS_METHOD = "PlanManager.getIndicadorPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "";
		IndicadorPlan indicador = null;
		IndicadorPlanPK indicadorpk = null;
		String valor = null;
		String alerta = null;
		String tipoMedicion = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}

			sql = "select ";
			sql = sql + "peso, ";
			sql = sql + "crecimiento, ";
			sql = sql + "tipo_medicion ";
			sql = sql + "FROM indicador_por_plan ";
			sql = sql + "where indicador_por_plan.plan_Id = " + planId;
			sql = sql + " and indicador_por_plan.indicador_id = " + indicadorId;
					
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				indicadorpk = new IndicadorPlanPK();
				indicadorpk.setIndicadorId(indicadorId);
				indicadorpk.setPlanId(planId);
				
				indicador = new IndicadorPlan();
				indicador.setPk(indicadorpk);
				valor = rs.getString("peso");
				alerta = rs.getString("crecimiento");
				tipoMedicion = rs.getString("tipo_medicion");
				if (valor != null)
					indicador.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				if (alerta != null)
					indicador.setCrecimiento(Byte.parseByte(alerta));
				if (tipoMedicion != null)
					indicador.setTipoMedicion(Byte.parseByte(tipoMedicion));
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

		return indicador;
	}
	
	public Byte getFrecuenciaPlan(Long planId, Byte tipoEstado, Statement stmExt) 
	{
		String CLASS_METHOD = "PlanManager.getFrecuenciaPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "";
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

			sql = "select ";
			sql = sql + "max(indicador.frecuencia) AS frecuencia ";
			sql = sql + "FROM Indicador indicador ";
			sql = sql + "where indicador.indicador_Id in (";
			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
				sql = sql + "SELECT nl_par_indicador_id ";
			else
				sql = sql + "SELECT nl_ano_indicador_id ";
			sql = sql + "FROM perspectiva where plan_Id = " + planId.toString() + " AND padre_id in (select perspectiva_id from perspectiva where plan_id = " + planId.toString() + " and padre_id is null))";

			rs = stm.executeQuery(sql);
			
			if (rs.next())
				frecuencia = rs.getString("frecuencia");
			
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
		
		return frecuencia != null ? Byte.parseByte(frecuencia) : null;
	}
	
	public int saveIndicadorPlan(Long indicadorId, Long planId, Double peso, Byte alerta, Byte tipoEstado, Statement stmExt)
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PlanManager.saveIndicadorPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int actualizados = 0;
		
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
		
			sql = "UPDATE indicador_por_plan SET ";
			String query = "";
			if (peso == null)
				query = query + " peso = NULL";
			else
				query = query + " peso = " + peso;
			if (alerta == null)
			{
				if (!query.equals("")) query = query + ", ";
				query = query + " crecimiento = NULL";
			}
			else
			{
				if (!query.equals("")) query = query + ", ";
				query = query + " crecimiento = " + alerta;
			}
			if (tipoEstado == null)
			{
				if (!query.equals("")) query = query + ", ";
				query = query + " tipo_medicion = NULL";
			}
			else
			{
				if (!query.equals("")) query = query + ", ";
				query = query + " tipo_medicion = " + tipoEstado;
			}
			
			sql = sql + query + " WHERE plan_id = " + planId;
			sql = sql + " AND indicador_Id = " + indicadorId;
			
			actualizados = stm.executeUpdate(sql);
			
		    if (actualizados == 0) 
		    {
				sql = "INSERT INTO indicador_por_plan (plan_id, indicador_Id, peso, crecimiento, tipo_medicion)";
				sql = sql + " VALUES (" + planId + ", ";
				sql = sql + indicadorId + ", ";
				query = "";
				if (peso == null)
					query = query + "NULL";
				else
					query = query + peso;
				if (alerta == null)
				{
					if (!query.equals("")) query = query + ", ";
					query = query + "NULL";
				}
				else
				{
					if (!query.equals("")) query = query + ", ";
					query = query + alerta;
				}
				if (tipoEstado == null)
				{
					if (!query.equals("")) query = query + ", ";
					query = query + "NULL";
				}
				else
				{
					if (!query.equals("")) query = query + ", ";
					query = query + tipoEstado;
				}
				sql = sql + query + ")";
				
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
				try 
				{
					cn.rollback();
				} 
				catch (SQLException e1) 
				{
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
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
	
	public int savePlanEstado(Long planId, Byte tipoEstado, Integer ano, Integer periodo, Double valorEstado, Statement stmExt)
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PlanManager.savePlanEstado";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int actualizados = 0;
		
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
		
			sql = "UPDATE plan_nivel";
			if (valorEstado == null)
				sql = sql + " SET nivel = NULL";
			else
				sql = sql + " SET nivel = " + valorEstado;
			sql = sql + " WHERE plan_id = " + planId;
			sql = sql + " AND Tipo = " + tipoEstado;
			sql = sql + " AND Ano = " + ano;
			sql = sql + " AND Periodo = " + periodo;
			
			actualizados = stm.executeUpdate(sql);
			
		    if (actualizados == 0) 
		    {
				sql = "INSERT INTO plan_nivel (Plan_Id, Tipo, Ano, Periodo, nivel)";
				sql = sql + " VALUES (" + planId + ", ";
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
	
	public int actualizarPlanUltimoEstado(Long planId, Statement stmExt)
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PlanManager.savePlanEstado";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean ConexAbierta = false;
		String sql = "";
		int actualizados = 0;
		List<PlanEstado> estados = new ArrayList<PlanEstado>(); 
		PlanEstado planEstado = null;
		PlanEstadoPK planEstadoPK = null;
		
		String valor = null;
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

		    sql = "select ";
		    sql = sql + "ano, ";
		    sql = sql + "periodo, ";
		    sql = sql + "nivel ";
		    sql = sql + "from plan_nivel ";
		    sql = sql + "where plan_nivel.plan_Id = " + planId;
		    sql = sql + " and plan_nivel.tipo = " + TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue();
		    sql = sql + " order by plan_nivel.ano desc, plan_nivel.periodo desc";
		    		
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				ano = rs.getString("ano");
				periodo = rs.getString("periodo");
				valor = rs.getString("nivel");

				planEstadoPK = new PlanEstadoPK();
				if (ano != null)
					planEstadoPK.setAno(Integer.parseInt(ano));
				if (periodo != null)
					planEstadoPK.setPeriodo(Integer.parseInt(periodo));
				planEstadoPK.setPlanId(planId);
				planEstadoPK.setTipo(TipoIndicadorEstado.getTipoIndicadorEstadoAnual());
				planEstado = new PlanEstado();
				planEstado.setPk(planEstadoPK);
				if (valor != null)
					planEstado.setEstado(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				estados.add(planEstado);
				break;
			}
			rs.close();

		    boolean actualizado = false;

		    int index = 0;
		    while ((!actualizado) && (index < estados.size())) 
		    {
		    	PlanEstado estado = (PlanEstado)estados.get(index);

		    	if (estado.getEstado() != null) 
		    	{
					sql = "UPDATE planes";
					sql = sql + " set fecha_ultima_medicion = '" + estado.getPk().getPeriodo().toString() + "/" + estado.getPk().getAno() + "'";
					sql = sql + ", ultima_medicion_anual = " + estado.getEstado();
					sql = sql + " where planes.plan_Id = " + planId;
					
					actualizados = stm.executeUpdate(sql);
		    		actualizado = actualizados > 0;
		    	} 
		    	else 
		    		index++;
		    }
		    
		    estados = new ArrayList<PlanEstado>();

		    sql = "select ";
		    sql = sql + "ano, ";
		    sql = sql + "periodo, ";
		    sql = sql + "nivel ";
		    sql = sql + "from plan_nivel ";
		    sql = sql + "where plan_nivel.plan_Id = " + planId;
		    sql = sql + " and plan_nivel.tipo = " + TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue();
		    sql = sql + " order by plan_nivel.ano desc, plan_nivel.periodo desc";
		    		
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				ano = rs.getString("ano");
				periodo = rs.getString("periodo");
				valor = rs.getString("nivel");

				planEstadoPK = new PlanEstadoPK();
				if (ano != null)
					planEstadoPK.setAno(Integer.parseInt(ano));
				if (periodo != null)
					planEstadoPK.setPeriodo(Integer.parseInt(periodo));
				planEstadoPK.setPlanId(planId);
				planEstadoPK.setTipo(TipoIndicadorEstado.getTipoIndicadorEstadoParcial());
				
				planEstado.setPk(planEstadoPK);
				if (valor != null)
					planEstado.setEstado(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				estados.add(planEstado);
				break;
			}
			rs.close();
		    
		    actualizado = false;

		    index = 0;
		    while ((!actualizado) && (index < estados.size())) 
		    {
		    	PlanEstado estado = (PlanEstado)estados.get(index);

		    	if (estado.getEstado() != null) 
		    	{
					sql = "UPDATE planes";
					sql = sql + " set ultima_medicion_parcial = " + estado.getEstado();
					sql = sql + " where planes.plan_Id = " + planId;
					
					actualizados = stm.executeUpdate(sql);
		    		actualizado = actualizados > 0;
		    	} 
		    	else 
		    		index++;
		    }
			
			if (actualizado) 
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

	public int actualizarPlanAlerta(Plan plan, Statement stmExt) 
	{
	    int resultado = 10000;
		String CLASS_METHOD = "PlanManager.savePlanEstado";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int actualizados = 0;
		
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

		    Byte alerta = null;

		    IndicadorPlan indicadorplan = getIndicadorPlan(plan.getPlanId(), plan.getNlParIndicadorId(), stm);
		    if (indicadorplan != null) 
		    	alerta = indicadorplan.getCrecimiento();

			sql = "UPDATE planes";
			if (alerta != null)
				sql = sql + " set crecimiento = " + alerta;
			else
				sql = sql + " set crecimiento = null";
			sql = sql + " where planes.plan_Id = " + plan.getPlanId();
			
			actualizados = stm.executeUpdate(sql);
			
			if (actualizados > 0)
			{
				alerta = null;
				indicadorplan = getIndicadorPlan(plan.getPlanId(), plan.getNlParIndicadorId(), stm);
			    if (indicadorplan != null) 
			    	alerta = indicadorplan.getCrecimiento();
				
				sql = "UPDATE perspectiva";
				if (alerta != null)
					sql = sql + " set crecimiento_parcial = " + alerta;
				else
					sql = sql + " set crecimiento_parcial = null";
				sql = sql + " where padre_id is null";
				sql = sql + " and plan_Id = " + plan.getPlanId();
				
				actualizados = stm.executeUpdate(sql);

				if (actualizados > 0)
				{
					alerta = null;
					indicadorplan = getIndicadorPlan(plan.getPlanId(), plan.getNlAnoIndicadorId(), stm);
				    if (indicadorplan != null) 
				    	alerta = indicadorplan.getCrecimiento();
					
					sql = "UPDATE perspectiva";
					if (alerta != null)
						sql = sql + " set crecimiento_anual = " + alerta;
					else
						sql = sql + " set crecimiento_anual = null";
					sql = sql + " where padre_id is null";
					sql = sql + " and plan_Id = " + plan.getPlanId();
					
					actualizados = stm.executeUpdate(sql);
				}
			}
		    
			if (actualizados > 0) 
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
	
	public List<IndicadorEstado> getIndicadorEstados(Long indicadorId, Long planId, Byte frecuencia, Byte tipoEstado, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Statement stmExt) 
	{
		String CLASS_METHOD = "IndicadorManager.getIndicadorEstados";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		List<IndicadorEstado> estadosExistentes = new ArrayList<IndicadorEstado>();
		List<IndicadorEstado> estados = new ArrayList<IndicadorEstado>();
		IndicadorEstado estado = null;
		IndicadorEstadoPK estadopk = null;
		String valor = null;
		String anoRs = null;
		String periodoRs = null;

		Integer ano = null;
		Integer periodo = null;
		
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
			sql = sql + "estado, ";
			sql = sql + "ano, ";
			sql = sql + "periodo ";
			sql = sql + "FROM indicador_estado ";
			
		    if ((indicadorId != null) || (planId != null) || (tipoEstado != null) || (anoDesde != null) || (anoHasta != null) || (periodoDesde != null) || (periodoHasta != null)) 
		    	sql = sql + " where ";

		    sql = sql + "indicador_estado.indicador_Id = " + indicadorId.longValue() + " and indicador_estado.plan_Id = " + planId.longValue() + " and indicador_estado.tipo = " + tipoEstado.byteValue();

		    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (anoDesde.intValue() == anoHasta.intValue()))
		    	sql = sql + " and (indicador_estado.periodo >= " + periodoDesde.intValue() + " and indicador_estado.ano = " + anoDesde.intValue() + " and indicador_estado.periodo <= " + periodoHasta.intValue() + " and indicador_estado.ano = " + anoHasta.intValue() + ")";
		    else if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null))
		    	sql = sql + " and ((indicador_estado.periodo >= " + periodoDesde.intValue() + " and indicador_estado.ano = " + anoDesde.intValue() + ") or " + "(indicador_estado.periodo <= " + periodoHasta.intValue() + " and indicador_estado.ano = " + anoHasta.intValue() + ") " + " or (indicador_estado.ano > " + anoDesde.intValue() + " and indicador_estado.ano < " + anoHasta.intValue() + "))";
		    else if ((anoDesde != null) && (periodoDesde != null))
		    	sql = sql + " and (" + "(indicador_estado.periodo >= " + periodoDesde.intValue() + " and indicador_estado.ano = " + anoDesde.intValue() + ") or " + "(indicador_estado.ano > " + anoDesde.intValue() + ")" + ")";
		    else if ((anoHasta != null) && (periodoHasta != null))
		    	sql = sql + " and (" + "(indicador_estado.periodo <= " + periodoHasta.intValue() + " and indicador_estado.ano = " + anoHasta.intValue() + ") or " + "(indicador_estado.ano < " + anoHasta.intValue() + ")" + ")";
		    else if ((anoDesde != null) && (anoHasta != null))
		    	sql = sql + " and (indicador_estado.ano >= " + anoDesde.intValue() + " and indicador_estado.ano <= " + anoHasta.intValue() + ")";
		    else if (anoDesde != null)
		    	sql = sql + " and indicador_estado.ano >= " + anoDesde.intValue();
		    else if (anoHasta != null) 
		    	sql = sql + " and indicador_estado.ano <= " + anoHasta.intValue();

		    sql = sql + " order by indicador_estado.indicador_Id, indicador_estado.plan_Id, indicador_estado.tipo, indicador_estado.ano, indicador_estado.periodo";
			
			rs = stm.executeQuery(sql);
			
			while (rs.next())
			{
				valor = rs.getString("estado");
				anoRs = rs.getString("ano");
				periodoRs = rs.getString("periodo");
				
				estadopk = new IndicadorEstadoPK();
				if (anoRs != null)
					estadopk.setAno(Integer.parseInt(anoRs));
				if (periodoRs != null)
					estadopk.setPeriodo(Integer.parseInt(periodoRs));
				estadopk.setIndicadorId(indicadorId);
				estadopk.setPlanId(planId);
				estadopk.setTipo(tipoEstado);
				estado = new IndicadorEstado();
				estado.setPk(estadopk);
				if (valor != null)
					estado.setEstado(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				estadosExistentes.add(estado);
			}

			rs.close();
		    
		    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (frecuencia != null))
		    {
		      ano = anoDesde.intValue();
		      Iterator<IndicadorEstado> iterEstadosExistentes = estadosExistentes.iterator();
		      boolean getExistente = iterEstadosExistentes.hasNext();
		      IndicadorEstado indicadorEstado = null;
		      IndicadorEstado indicadorEstadoExistente = null;
		      Double estadoAnterior = null;

		      while (ano <= anoHasta.intValue())
		      {
		    	  periodo = 1;
		    	  if (ano == anoDesde.intValue()) 
		    		  periodo = periodoDesde.intValue();
		    	  int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    	  if (ano == anoHasta.intValue()) 
		    		  periodoMaximo = periodoHasta.intValue();
		        
		    	  while (periodo <= periodoMaximo) 
		    	  {
		    		  if (getExistente) 
		    			  indicadorEstadoExistente = (IndicadorEstado)iterEstadosExistentes.next();

		    		  if (indicadorEstadoExistente != null) 
		    		  {
		    			  if ((indicadorEstadoExistente.getPk().getAno().intValue() == ano) && (indicadorEstadoExistente.getPk().getPeriodo().intValue() == periodo)) 
		    			  {
		    				  indicadorEstado = indicadorEstadoExistente;
		    				  if (indicadorEstado != null && indicadorEstado.getEstado() != null)
		    					  estadoAnterior = indicadorEstado.getEstado();
		    				  getExistente = iterEstadosExistentes.hasNext();
		    				  indicadorEstadoExistente = null;
		    			  } 
		    			  else 
		    			  {
		    				  indicadorEstado = null;
		    				  getExistente = false;
		    			  }
		    		  } 
		    		  else 
		    		  {
		    			  indicadorEstado = null;
		    			  getExistente = false;
		    		  }
		    		  
		    		  if (indicadorEstado == null) 
		    		  {
		    			  indicadorEstado = new IndicadorEstado();
		    			  IndicadorEstadoPK indicadorEstadoPk = new IndicadorEstadoPK();
		    			  indicadorEstadoPk.setIndicadorId(indicadorId);
		    			  indicadorEstadoPk.setPlanId(planId);
		    			  indicadorEstadoPk.setTipo(tipoEstado);
		    			  indicadorEstadoPk.setAno(new Integer(ano));
		    			  indicadorEstadoPk.setPeriodo(new Integer(periodo));
		    			  indicadorEstado.setPk(indicadorEstadoPk);
		    			  if (estadoAnterior != null && estadoAnterior.doubleValue() == 100D)
		    				  indicadorEstado.setEstado(estadoAnterior);
		    		  }

		    		  estados.add(indicadorEstado);
		    		  periodo++;
		    	  }
		    	  ano++;
		      }
		    } 
		    else 
		    	estados = estadosExistentes;
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
		    
		return estados;
	}	
}

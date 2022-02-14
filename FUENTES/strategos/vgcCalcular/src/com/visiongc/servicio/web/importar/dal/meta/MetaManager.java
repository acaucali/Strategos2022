/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.meta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlan;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.servicio.strategos.planes.model.Meta;
import com.visiongc.servicio.strategos.planes.model.MetaPK;
import com.visiongc.servicio.strategos.planes.model.util.TipoMeta;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.dal.medicion.MedicionManager;
import com.visiongc.servicio.web.importar.dal.plan.PlanManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class MetaManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public MetaManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public List<Meta> getMetasAnuales(Long indicadorId, Long planId, Integer anoInicial, Integer anoFinal, Statement stmExt)
	{
		String CLASS_METHOD = "MetaManager.getMetasAnuales";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Meta> metas = new ArrayList<Meta>();
		List<Meta> metasExist = new ArrayList<Meta>();
		Iterator<Meta> metasExistentes = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String valor = null;
		
		try
		{
			Meta meta;
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;
		
			sql = "SELECT ";
			sql = sql + "ano, ";
			sql = sql + "periodo, ";
			sql = sql + "valor ";
			sql = sql + "from Meta where meta.indicador_Id = " + indicadorId + " and meta.plan_Id = " + planId;
			sql = sql + " and meta.serie_Id = " + SerieTiempo.getSerieMetaId() + " and meta.tipo = " + TipoMeta.getTipoMetaAnual();
			sql = sql + " and (meta.ano >= " + anoInicial + " and meta.ano <= " + anoFinal +")";
			sql = sql + " order by meta.ano";

			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				anoRs = rs.getString("ano");
				valor = rs.getString("valor");

				meta = new Meta();

				MetaPK metaPk = new MetaPK();
				metaPk.setIndicadorId(indicadorId);
				metaPk.setPlanId(planId);
				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
				metaPk.setTipo(TipoMeta.getTipoMetaAnual());
				metaPk.setPeriodo(new Integer(0));
				if (anoRs != null)
					metaPk.setAno(Integer.parseInt(anoRs));
				meta.setMetaId(metaPk);
				meta.setProtegido(new Boolean(false));
				if (valor != null)
					meta.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				metasExist.add(meta);
			}
			rs.close();
			metasExistentes = metasExist.iterator(); 
			
			int ano = anoInicial.intValue();
			boolean getExistente = metasExistentes.hasNext();
			meta = null;
			Meta metaExistente = null;

			while (ano <= anoFinal.intValue()) 
			{
				if (getExistente) 
					metaExistente = (Meta)metasExistentes.next();
	      
				if (metaExistente != null) 
				{
					if (metaExistente.getMetaId().getAno().intValue() == ano) 
					{
						meta = metaExistente;
						getExistente = metasExistentes.hasNext();
						metaExistente = null;
					} 
					else 
					{
						meta = null;
						getExistente = false;
					}
				} 
				else 
				{
					meta = null;
					getExistente = false;
				}
	      
				if (meta == null) 
				{
					meta = new Meta();
					MetaPK metaPk = new MetaPK();
					metaPk.setIndicadorId(indicadorId);
					metaPk.setPlanId(planId);
					metaPk.setSerieId(SerieTiempo.getSerieMetaId());
					metaPk.setTipo(TipoMeta.getTipoMetaAnual());
					metaPk.setPeriodo(new Integer(0));
					metaPk.setAno(new Integer(ano));
					meta.setMetaId(metaPk);
					meta.setProtegido(new Boolean(false));
				}

				metas.add(meta);
				ano++;
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
		
		return metas;		
	}
	
	public List<Meta> getMetasParciales(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Byte corte, Byte tipoCargaMedicion, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Statement stmExt)
	{
	    List<Meta> metas = null;
	    if (corte.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) 
	    {
	    	if (tipoCargaMedicion != null && tipoCargaMedicion.byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
	    		metas = getMetasParcialesAcumuladas(indicadorId, planId, frecuencia, mesCierre, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta, stmExt);
	    	else
	    		metas = getMetasParciales(indicadorId, planId, frecuencia, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta, stmExt);
    	}
    	else 
    		metas = getMetasParciales(indicadorId, planId, frecuencia, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta, stmExt);
	    
	    return metas;
	}

	private List<Meta> getMetasParcialesAcumuladas(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Statement stmExt)
	{
		String CLASS_METHOD = "MetaManager.getMetasParcialesAcumuladas";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Meta> metas = new ArrayList<Meta>();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String periodoRs = null;
		String valor = null;
		
		try
		{
			Meta meta;
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;
		
			sql = "SELECT ";
			sql = sql + "ano, ";
			sql = sql + "periodo, ";
			sql = sql + "valor ";
			sql = sql + "from Meta where meta.indicador_Id = " + indicadorId + " and meta.plan_Id = " + planId +" and meta.tipo = " + tipoMeta.toString();			
			
		    int anoDesdeInterno = 0;
		    int anoHastaInterno = 0;
		    int periodoDesdeInterno = 0;
		    int periodoHastaInterno = 0;
		    int periodoDesdeCierre = 0;
		    int periodoHastaCierre = 0;
		    Byte mesInicio = null;

		    if (mesCierre != null) 
		    	mesInicio = PeriodoUtil.getMesInicio(mesCierre);
		    else 
		    {
		    	mesCierre = new Byte("12");
		    	mesInicio = new Byte("1");
		    }
		    
		    if (anoDesde != null) 
		    	anoDesdeInterno = anoDesde.intValue();
		    
		    if (anoHasta != null) 
		    	anoHastaInterno = anoHasta.intValue();
		    
		    if (periodoDesde != null) 
		    	periodoDesdeInterno = periodoDesde.intValue();
		    if (periodoHasta != null) 
		    	periodoHastaInterno = periodoHasta.intValue();

		    if ((anoDesdeInterno > 0) && (anoHastaInterno > 0) && (periodoDesdeInterno > 0) && (periodoHastaInterno > 0)) 
		    {
		    	List<Meta> metasExistentes = new ArrayList<Meta>();
		    	List<Meta> metasTemp = new ArrayList<Meta>();
		    	boolean hayValorAcumuladoInicial = false;
		    	Double valorAcumuladoInicial = null;
		    	boolean hayMetas = false;
		    	double acumulado = 0.0D;

		    	for (int ano = anoDesdeInterno; ano <= anoHastaInterno; ano++) 
		    	{
		    		acumulado = 0.0D;
		    		if (mesCierre.byteValue() == 12) 
		    		{
		    			periodoDesdeCierre = 1;
		    			periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    		} 
		    		else 
		    		{
		    			periodoDesdeCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesInicio.intValue(), ano - 1), frecuencia.byteValue());
		    			periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), frecuencia.byteValue());
		    		}
		        
		    		String sqlInt = sql + " and meta.ano = " + ano + " ";
		    		int anoAdicional = ano;
		    		int periodoCierre = 0;
		    		if (mesCierre.byteValue() != 12) 
		    		{
		    			if (ano == anoDesdeInterno) 
		    			{
		    				if (periodoDesdeInterno <= periodoHastaCierre) 
		    				{
		    					anoAdicional = ano - 1;
		    					sqlInt = sqlInt + " or (meta.ano = " + anoAdicional + " and meta.periodo >= " + periodoCierre + ")";
		    				} 
		    				else 
		    					sqlInt = sqlInt + " and meta.periodo >= " + periodoCierre;
		    			}
		    			else if (1 <= periodoHastaCierre) 
		    			{
		    				anoAdicional = ano - 1;
		    				sqlInt = sqlInt + " or (meta.ano = " + anoAdicional + " and meta.periodo >= " + periodoCierre + ")";
		    			} 
		    			else 
		    				sqlInt = sqlInt + " and meta.periodo >= " + periodoCierre;

		    			periodoCierre = periodoDesdeCierre;
		    		}
		    		
		    		sqlInt = sqlInt + " order by meta.ano asc, meta.periodo asc";

					rs = stm.executeQuery(sqlInt);
					
					while (rs.next()) 
					{
						anoRs = rs.getString("ano");
						periodoRs = rs.getString("periodo");
						valor = rs.getString("valor");

						meta = new Meta();

						MetaPK metaPk = new MetaPK();
						metaPk.setIndicadorId(indicadorId);
						metaPk.setPlanId(planId);
						metaPk.setSerieId(SerieTiempo.getSerieMetaId());
						metaPk.setTipo(TipoMeta.getTipoMetaAnual());
						if (anoRs != null)
							metaPk.setAno(Integer.parseInt(anoRs));
						if (periodoRs != null)
							metaPk.setPeriodo(Integer.parseInt(periodoRs));
						meta.setMetaId(metaPk);
						meta.setProtegido(new Boolean(false));
						if (valor != null)
							meta.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
						
						metasTemp.add(meta);
					}
					rs.close();
		        
					if (anoDesdeInterno == anoHastaInterno)
					{
						for (Iterator<Meta> m = metasTemp.iterator(); m.hasNext(); ) 
						{
							Meta metaTemp = (Meta)m.next();

							if ((!hayMetas) && (hayValorAcumuladoInicial)) 
								valorAcumuladoInicial = new Double(acumulado);

							acumulado += metaTemp.getValor().doubleValue();
							if ((metaTemp.getMetaId().getPeriodo().intValue() >= periodoDesdeInterno) && (metaTemp.getMetaId().getPeriodo().intValue() <= periodoHastaInterno)) 
							{
								hayMetas = true;
								metaTemp.setValor(new Double(acumulado));
								metasExistentes.add(metaTemp);
							}
							hayValorAcumuladoInicial = true;
						}
					}
					else if (ano == anoDesdeInterno)
					{
						for (Iterator<Meta> m = metasTemp.iterator(); m.hasNext(); ) 
						{
							Meta metaTemp = (Meta)m.next();
							if ((!hayMetas) && (hayValorAcumuladoInicial)) 
								valorAcumuladoInicial = new Double(acumulado);

							acumulado += metaTemp.getValor().doubleValue();
							if (metaTemp.getMetaId().getPeriodo().intValue() >= periodoDesdeInterno) 
							{
								hayMetas = true;
								metaTemp.setValor(new Double(acumulado));
								metasExistentes.add(metaTemp);
							}
							hayValorAcumuladoInicial = true;
						}
					}
					else if (ano == anoHastaInterno)
					{
						for (Iterator<Meta> m = metasTemp.iterator(); m.hasNext(); ) 
						{
							Meta metaTemp = (Meta)m.next();
							acumulado += metaTemp.getValor().doubleValue();
							if (metaTemp.getMetaId().getPeriodo().intValue() <= periodoHastaInterno) 
							{
								hayMetas = true;
								metaTemp.setValor(new Double(acumulado));
								metasExistentes.add(metaTemp);
							}
						}
					}
					else 
					{
						for (Iterator<Meta> m = metasTemp.iterator(); m.hasNext(); ) 
						{
							Meta metaTemp = (Meta)m.next();
							acumulado += metaTemp.getValor().doubleValue();
							hayMetas = true;
							metaTemp.setValor(new Double(acumulado));
							metasExistentes.add(metaTemp);
						}
					}
		    	}

		    	int ano = anoDesdeInterno;
		    	Iterator<Meta> iterMetasExistentes = metasExistentes.iterator();
		    	Meta primeraMetaExistente = null;
		    	if (metasExistentes.size() > 0) 
		    		primeraMetaExistente = (Meta)metasExistentes.get(0);
		    	boolean getExistente = iterMetasExistentes.hasNext();
		    	meta = null;
		    	Meta metaExistente = null;

		    	while (ano <= anoHastaInterno)
		    	{
		    		int periodo = 1;
		    		if (ano == anoDesdeInterno) 
		    			periodo = periodoDesdeInterno;

		    		int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    		if (ano == anoHastaInterno) 
		    			periodoMaximo = periodoHastaInterno;

		    		while (periodo <= periodoMaximo) 
		    		{
		    			if (getExistente) 
		    				metaExistente = (Meta)iterMetasExistentes.next();

		    			if (metaExistente != null) 
		    			{
		    				if ((metaExistente.getMetaId().getAno().intValue() == ano) && (metaExistente.getMetaId().getPeriodo().intValue() == periodo)) 
		    				{
		    					meta = metaExistente;
		    					getExistente = iterMetasExistentes.hasNext();
		    					metaExistente = null;
		    				} 
		    				else 
		    				{
		    					meta = null;
		    					getExistente = false;
		    				}
		    			} 
		    			else 
		    			{
		    				meta = null;
		    				getExistente = false;
		    			}
		    			
		    			if (meta == null) 
		    			{
		    				meta = new Meta();
		    				MetaPK metaPk = new MetaPK();
		    				metaPk.setIndicadorId(indicadorId);
		    				metaPk.setPlanId(planId);
		    				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
		    				metaPk.setAno(new Integer(ano));
		    				metaPk.setPeriodo(new Integer(periodo));
		    				meta.setMetaId(metaPk);
		    				
		    				if ((valorAcumuladoInicial != null) && (primeraMetaExistente != null) && ((ano < primeraMetaExistente.getMetaId().getAno().intValue()) || ((ano == primeraMetaExistente.getMetaId().getAno().intValue()) && (periodo < primeraMetaExistente.getMetaId().getPeriodo().intValue())))) 
		    				{
		    					meta.setValor(valorAcumuladoInicial);
		    				}

		    				meta.setProtegido(new Boolean(false));
		    			}

		    			metas.add(meta);
		    			periodo++;
		    		}
		    		ano++;
		    	}
		    }

			return metas;
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
		
		return metas;
	}
	  
	private List<Meta> getMetasParciales(Long indicadorId, Long planId, Byte frecuencia, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Statement stmExt) 
	{
		String CLASS_METHOD = "MetaManager.getMetasParciales";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Meta> metas = new ArrayList<Meta>();
		List<Meta> metasExistentes = new ArrayList<Meta>();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String periodoRs = null;
		String valor = null;
		
		try
		{
			Meta meta;
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;
		
			sql = "SELECT ";
			sql = sql + "ano, ";
			sql = sql + "periodo, ";
			sql = sql + "valor ";
			sql = sql + "from Meta where meta.indicador_Id = " + indicadorId + " and meta.plan_Id = " + planId +" and meta.tipo = " + tipoMeta.toString();			

		    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (anoDesde.intValue() == anoHasta.intValue()))
		    {
		    	sql = sql + " and (" + "((meta.periodo >= " + periodoDesde + " and meta.ano = " + anoDesde + ") and " + "(meta.periodo <= " + periodoHasta + " and meta.ano = " + anoHasta + ")) ";
		    	sql = sql + ")";
		    } 
		    else if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null))
		    	sql = sql + " and ((meta.periodo >= " + periodoDesde + " and meta.ano = " + anoDesde + ") or " + "(meta.periodo <= " + periodoHasta + " and meta.ano = " + anoHasta + ") " + " or (meta.ano > " + anoDesde + " and meta.ano < " + anoHasta + "))";
		    else if ((anoDesde != null) && (periodoDesde != null)) 
		    	sql = sql + " and (" + "(meta.periodo >= " + periodoDesde + " and meta.ano = " + anoDesde + ") or " + "(meta.ano > " + anoDesde + ")" + ")";
		    else if ((anoHasta != null) && (periodoHasta != null)) 
		    	sql = sql + " and (" + "(meta.periodo <= " + periodoHasta + " and meta.ano = " + anoHasta + ") or " + "(meta.ano < " + anoHasta + ")" + ")";
		    else if ((anoDesde != null) && (anoHasta != null)) 
		    	sql = sql + " and (meta.ano >= " + anoDesde + " and meta.ano <= " + anoHasta + ")";
		    else if (anoDesde != null) 
		    	sql = sql + " and meta.ano >= " + anoDesde;
		    else if (anoHasta != null) 
		    	sql = sql + " and meta.ano <= " + anoHasta;

		    sql = sql + " order by meta.indicador_Id, meta.plan_Id, meta.ano, meta.periodo";

			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				anoRs = rs.getString("ano");
				periodoRs = rs.getString("periodo");
				valor = rs.getString("valor");

				meta = new Meta();

				MetaPK metaPk = new MetaPK();
				metaPk.setIndicadorId(indicadorId);
				metaPk.setPlanId(planId);
				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
				metaPk.setTipo(TipoMeta.getTipoMetaAnual());
				if (anoRs != null)
					metaPk.setAno(Integer.parseInt(anoRs));
				if (periodoRs != null)
					metaPk.setPeriodo(Integer.parseInt(periodoRs));
				meta.setMetaId(metaPk);
				meta.setProtegido(new Boolean(false));
				if (valor != null)
					meta.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				metasExistentes.add(meta);
			}
			rs.close();

		    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (frecuencia != null))
		    {
		    	int ano = anoDesde.intValue();
		    	Iterator<Meta> iterMetasExistentes = metasExistentes.iterator();
		    	boolean getExistente = iterMetasExistentes.hasNext();
		    	meta = null;
		    	Meta metaExistente = null;

		    	while (ano <= anoHasta.intValue())
		    	{
		    		int periodo = 1;
		    		if (ano == anoDesde.intValue()) 
		    			periodo = periodoDesde.intValue();
		    		int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    		if (ano == anoHasta.intValue()) 
		    			periodoMaximo = periodoHasta.intValue();
		        
		    		while (periodo <= periodoMaximo) 
		    		{
		    			if (getExistente) 
		    				metaExistente = (Meta)iterMetasExistentes.next();
		          
		    			if (metaExistente != null) 
		    			{
		    				if ((metaExistente.getMetaId().getAno().intValue() == ano) && (metaExistente.getMetaId().getPeriodo().intValue() == periodo)) 
		    				{
		    					meta = metaExistente;
		    					getExistente = iterMetasExistentes.hasNext();
		    					metaExistente = null;
		    				} 
		    				else 
		    				{
		    					meta = null;
		    					getExistente = false;
		    				}
		    			} 
		    			else 
		    			{
		    				meta = null;
		    				getExistente = false;
		    			}
		          
		    			if (meta == null) 
		    			{
		    				meta = new Meta();
		    				MetaPK metaPk = new MetaPK();
		    				metaPk.setIndicadorId(indicadorId);
		    				metaPk.setPlanId(SerieTiempo.getSerieMetaId());
		    				metaPk.setAno(new Integer(ano));
		    				metaPk.setPeriodo(new Integer(periodo));
		    				meta.setMetaId(metaPk);
		    				meta.setProtegido(new Boolean(false));
		    			}

		    			metas.add(meta);
		    			periodo++;
		    		}
		    		ano++;
		    	}
	    	} 
		    else 
		      metas = metasExistentes;

			return metas;
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
		
		return metas;
	}
	
	public int saveMetas(List<Meta> metas, Statement stmExt)
	{
		String CLASS_METHOD = "MetaManager.saveMetas";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int resultado = 10000;

		Map<String, String> tiposCargaMeta = new HashMap<String, String>();
	    Map<String, Indicador> indicadores = new HashMap<String, Indicador>();
	    
	    try
	    {
			int respuesta = 0;
	    	if (stmExt != null)
	    		stm = stmExt;
	    	else
	    	{
		    	cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();

				transActiva = true;
				ConexAbierta = true;
	    	}
			
			for (Iterator<Meta> iter = metas.iterator(); iter.hasNext(); )
			{
				Meta meta = (Meta)iter.next();

		        if ((meta.getMetaId().getTipo().byteValue() == TipoMeta.getTipoMetaParcial().byteValue()) && (!tiposCargaMeta.containsKey("indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString()))) 
		        {
		        	IndicadorPlan indicadorPlan = new PlanManager(pm, log, messageResources).getIndicadorPlan(meta.getMetaId().getPlanId(), meta.getMetaId().getIndicadorId(), stm);
  					if (indicadorPlan == null)
  					{
  						IndicadorPlanPK indicadorpk = new IndicadorPlanPK();
  						indicadorpk.setIndicadorId(meta.getMetaId().getIndicadorId());
  						indicadorpk.setPlanId(meta.getMetaId().getPlanId());
  						
  						indicadorPlan = new IndicadorPlan();
  						indicadorPlan.setPk(indicadorpk);
  					}
		        	indicadorPlan.setTipoMedicion(meta.getTipoCargaMeta());
		        	
	        		new PlanManager(pm, log, messageResources).saveIndicadorPlan(indicadorPlan.getPk().getIndicadorId(), indicadorPlan.getPk().getPlanId(), indicadorPlan.getPeso(), indicadorPlan.getCrecimiento(), indicadorPlan.getTipoMedicion(), stm);
		        	tiposCargaMeta.put("indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString(), "indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString());
		        }

		        if (meta.getValor() == null)
		        {
	    			sql = "DELETE FROM META ";
	    			sql = sql + "WHERE Plan_Id = " + meta.getMetaId().getPlanId();
	    			sql = sql + " AND Serie_Id = " + meta.getMetaId().getSerieId();
	    		    sql = sql + " AND Indicador_Id = " + meta.getMetaId().getIndicadorId();
	    		    sql = sql + " AND Tipo = " + meta.getMetaId().getTipo();
	    		    sql = sql + " AND Ano = " + meta.getMetaId().getAno();
	    		    sql = sql + " AND Periodo = " + meta.getMetaId().getPeriodo();
	    			
	    		    stm.executeUpdate(sql);
	    			
	        		resultado = 10000;
		        }
		        else if (meta.getMetaId().getSerieId().byteValue() == SerieTiempo.getSerieValorInicialId().byteValue())
		        {
	    			sql = "DELETE FROM META ";
	    			sql = sql + "WHERE Plan_Id = " + meta.getMetaId().getPlanId();
	    			sql = sql + " AND Serie_Id = " + meta.getMetaId().getSerieId();
	    		    sql = sql + " AND Indicador_Id = " + meta.getMetaId().getIndicadorId();
	    		    sql = sql + " AND Tipo = " + meta.getMetaId().getTipo();
	    			
	    		    stm.executeUpdate(sql);

	    			sql = "INSERT INTO META ";
	    			sql = sql + "(Serie_Id, Indicador_Id, Plan_Id, Tipo, Ano, Periodo, Valor, Protegido) ";
	    			sql = sql + "VALUES (" + meta.getMetaId().getSerieId() + ", ";
	    		    sql = sql + meta.getMetaId().getIndicadorId() + ", ";
	    		    sql = sql + meta.getMetaId().getPlanId() + ", ";
	    		    sql = sql + meta.getMetaId().getTipo() + ", ";
	    		    sql = sql + meta.getMetaId().getAno() + ", ";
	    		    sql = sql + meta.getMetaId().getPeriodo() + ", ";
	    		    sql = sql + meta.getValor() + ", ";
	    		    sql = sql + "1)";

	    		    respuesta = stm.executeUpdate(sql);
	    		    
	        		resultado = 10000;
		        }
		        else 
		        {
	    			sql = "UPDATE META ";
	    			sql = sql + "SET Valor = " + meta.getValor();
	    			sql = sql + ", Protegido = 1";
	    			sql = sql + " WHERE Plan_Id = " + meta.getMetaId().getPlanId();
	    			sql = sql + " AND Serie_Id = " + meta.getMetaId().getSerieId();
	    		    sql = sql + " AND Indicador_Id = " + meta.getMetaId().getIndicadorId();
	    		    sql = sql + " AND Tipo = " + meta.getMetaId().getTipo();
	    		    sql = sql + " AND Ano = " + meta.getMetaId().getAno();
	    		    sql = sql + " AND Periodo = " + meta.getMetaId().getPeriodo();
	    			
	    		    respuesta = stm.executeUpdate(sql);
	    			
	    		    if (respuesta == 0)
	    		    {
		    			sql = "INSERT INTO META ";
		    			sql = sql + "(Serie_Id, Indicador_Id, Plan_Id, Tipo, Ano, Periodo, Valor, Protegido) ";
		    			sql = sql + "VALUES (" + meta.getMetaId().getSerieId() + ", ";
		    		    sql = sql + meta.getMetaId().getIndicadorId() + ", ";
		    		    sql = sql + meta.getMetaId().getPlanId() + ", ";
		    		    sql = sql + meta.getMetaId().getTipo() + ", ";
		    		    sql = sql + meta.getMetaId().getAno() + ", ";
		    		    sql = sql + meta.getMetaId().getPeriodo() + ", ";
		    		    sql = sql + meta.getValor() + ", ";
		    		    sql = sql + "1)";

		    		    respuesta = stm.executeUpdate(sql);

		    		    if (respuesta != 0)
		    		    	resultado = 10000;
	    		    }
	    		    else
	    		    	resultado = 10000;
		        }

		        if (resultado != 10000) 
		        	break;
		        
		        if (!indicadores.containsKey("indicadorId" + meta.getMetaId().getIndicadorId() + "planId" + meta.getMetaId().getPlanId())) 
		        {
		        	Indicador indicador = new IndicadorManager(pm, log, messageResources).Load(meta.getMetaId().getIndicadorId(), stm);
		        	indicadores.put("indicadorId" + meta.getMetaId().getIndicadorId() + "planId" + meta.getMetaId().getPlanId(), indicador);
		        }
			}

		    for (Iterator<String> iter = indicadores.keySet().iterator(); iter.hasNext(); ) 
		    {
		    	String clave = (String)iter.next();
		    	Indicador indicador = (Indicador)indicadores.get(clave);
		    	Long planId = new Long(clave.substring(clave.indexOf("planId") + "planId".length()));
		    	resultado = new CalculoManager(pm, log, messageResources).calcularAlertaIndicadorPorPlan(indicador, planId, stm);
		    	if (resultado != 10000) 
		    	{
		    		resultado = 10007;
		    		break;
		    	}
		    }

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
	
	public Meta getValorInicial(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Integer ano, Statement stmExt)
	{
		int periodoDesdeCierre = 0;
		int periodoHastaCierre = 0;
		Byte mesInicio = null;

		if (mesCierre != null) 
		{
			mesInicio = PeriodoUtil.getMesInicio(mesCierre);
		} 
		else 
		{
			mesCierre = new Byte("12");
			mesInicio = new Byte("1");
		}

		if (mesCierre.byteValue() == 12) 
		{
			periodoDesdeCierre = 1;
			periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano.intValue());
		} 
		else 
		{
			periodoDesdeCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesInicio.intValue(), ano.intValue() - 1), frecuencia.byteValue());
			periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano.intValue()), frecuencia.byteValue());
		}

		Meta valorInicial = getValorInicial(indicadorId, planId, new Integer(ano.intValue() - 1), new Integer(periodoDesdeCierre), stmExt);

		if (valorInicial == null) 
		{
			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), new Integer(ano.intValue() - 1), new Integer(ano.intValue() - 1), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), frecuencia, stmExt);

			if (mediciones.size() > 0) 
			{
				Medicion medicion = (Medicion)mediciones.get(0);
				if (medicion.getValor() != null)
				{
					valorInicial = new Meta();
					MetaPK metaId = new MetaPK();
					metaId.setIndicadorId(indicadorId);
					metaId.setPlanId(planId);
					metaId.setTipo(TipoMeta.getTipoMetaValorInicial());
					metaId.setSerieId(SerieTiempo.getSerieMetaId());
					metaId.setAno(ano);
					metaId.setPeriodo(new Integer(periodoHastaCierre));
					valorInicial.setMetaId(metaId);
					valorInicial.setProtegido(new Boolean(false));
					valorInicial.setValor(medicion.getValor());
				}
			}
		}

		if (valorInicial == null) 
			valorInicial = getUltimoValorInicial(indicadorId, planId, stmExt);

		return valorInicial;
	}
	
	public Meta getValorInicial(Long indicadorId, Long planId, Integer ano, Integer periodo, Statement stmExt) 
	{
		String CLASS_METHOD = "MetaManager.getValorInicial";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Meta meta = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String periodoRs = null;
		String valor = null;
		
		try
		{
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;

			sql = "SELECT ";
			sql = sql + "ano, ";
			sql = sql + "periodo, ";
			sql = sql + "valor ";
			sql = sql + "from meta ";
			sql = sql + "where meta.indicador_Id = " + indicadorId;
			sql = sql + " and meta.plan_Id = " + planId;
			sql = sql + " and meta.ano = " + ano;
			sql = sql + " and meta.periodo = " + periodo;
			sql = sql + " and meta.tipo = " + TipoMeta.getTipoMetaValorInicial().toString();
			sql = sql + " order by meta.indicador_Id, meta.plan_Id, meta.ano, meta.periodo";

			rs = stm.executeQuery(sql);
		
			while (rs.next()) 
			{
				anoRs = rs.getString("ano");
				periodoRs = rs.getString("periodo");
				valor = rs.getString("valor");

				meta = new Meta();
	
				MetaPK metaPk = new MetaPK();
				metaPk.setIndicadorId(indicadorId);
				metaPk.setPlanId(planId);
				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
				metaPk.setTipo(TipoMeta.getTipoMetaValorInicial());
				if (ano != null)
					metaPk.setAno(Integer.parseInt(anoRs));
				if (periodo != null)
					metaPk.setPeriodo(Integer.parseInt(periodoRs));
				meta.setMetaId(metaPk);
				meta.setProtegido(new Boolean(false));
				if (valor != null)
					meta.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				break;
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
		
		return meta;		
	}

	public Meta getUltimoValorInicial(Long indicadorId, Long planId, Statement stmExt) 
	{
		String CLASS_METHOD = "MetaManager.getUltimoValorInicial";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Meta meta = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String ano = null;
		String periodo = null;
		String valor = null;
		
		try
		{
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;

			sql = "SELECT ";
			sql = sql + "ano, ";
			sql = sql + "periodo, ";
			sql = sql + "valor ";
			sql = sql + "from meta ";
			sql = sql + "where meta.indicador_Id = " + indicadorId;
			sql = sql + " and meta.plan_Id = " + planId;
			sql = sql + " and meta.tipo = " + TipoMeta.getTipoMetaValorInicial().toString();
			sql = sql + " order by meta.ano desc, meta.periodo desc";

			rs = stm.executeQuery(sql);
		
			while (rs.next()) 
			{
				ano = rs.getString("ano");
				periodo = rs.getString("periodo");
				valor = rs.getString("valor");

				meta = new Meta();
	
				MetaPK metaPk = new MetaPK();
				metaPk.setIndicadorId(indicadorId);
				metaPk.setPlanId(planId);
				metaPk.setSerieId(SerieTiempo.getSerieMetaId());
				metaPk.setTipo(TipoMeta.getTipoMetaValorInicial());
				if (ano != null)
					metaPk.setAno(Integer.parseInt(ano));
				if (periodo != null)
					metaPk.setPeriodo(Integer.parseInt(periodo));
				meta.setMetaId(metaPk);
				meta.setProtegido(new Boolean(false));
				if (valor != null)
					meta.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				break;
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
		
		return meta;		
	}
	
	public int deleteMetas(Long indicadorId, Long planId, Byte tipoMeta, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Long serieId, Statement stmExt) 
	{
		String CLASS_METHOD = "MetaManager.deleteMetas";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		String sql = "";
		boolean ConexAbierta = false;
	
		int resultado = 10000;
		
		try
		{
			if (stmExt == null)
			{
		    	cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
				ConexAbierta = true;

				transActiva = true;
			}
			else
				stm = stmExt;

		    sql = "delete from Meta where meta.indicador_Id = " + indicadorId;
		    boolean continuar = false;

		    if (serieId != null)
		    	sql = sql + " and meta.serie_Id = " + serieId;
		    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) 
		    {
		    	if (anoInicio.intValue() != anoFinal.intValue()) 
		    	{
		    		sql = sql + " and (((meta.ano = " + anoInicio.intValue() + ") and (meta.periodo >= " + periodoInicio.intValue() + "))";
		    		sql = sql + " or ((meta.ano > " + anoInicio.intValue() + ") and (meta.ano < " + anoFinal.intValue() + "))";
		    		sql = sql + " or ((meta.ano = " + anoFinal.intValue() + ") and (meta.periodo <= " + periodoFinal.intValue() + ")))";
		    	} 
		    	else 
		    	{
		    		sql = sql + " and ((meta.ano = " + anoInicio.intValue() + ") and (meta.periodo >= " + periodoInicio.intValue() + ")";
		    		sql = sql + " and (meta.ano = " + anoFinal.intValue() + ") and (meta.periodo <= " + periodoFinal.intValue() + "))";
		    	}

		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		      continuar = true;
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and meta.ano >= " + anoInicio.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and meta.ano <= " + anoFinal.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and meta.ano >= " + anoInicio.intValue();
		    	sql = sql + " and meta.ano <= " + anoFinal.intValue();
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and (((meta.ano = " + anoInicio.intValue() + ") and (meta.periodo >= " + periodoInicio.intValue() + "))";
		    	sql = sql + " or (meta.ano > " + anoInicio.intValue() + "))";
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) 
		    {
		    	sql = sql + " and (((meta.ano = " + anoFinal.intValue() + ") and (meta.periodo <= " + periodoFinal.intValue() + "))";
		    	sql = sql + " or (meta.ano < " + anoFinal.intValue() + "))";
		    	continuar = true;
		    }

		    if (continuar) 
		    {
		    	if (planId != null) 
		    		sql = sql + " and meta.plan_Id = " + planId.longValue();
		      
		    	if (tipoMeta != null) 
		    		sql = sql + " and meta.tipo = " + tipoMeta.byteValue();

		      	stm.executeUpdate(sql);
		    }
			
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

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		}
		finally
		{
			try 
			{
				if (transActiva && stmExt == null)
					cn.setAutoCommit(true);
				if (ConexAbierta && stmExt == null) {cn.close(); cn = null;}
			} 
			catch (Exception localException9) { }
		}
		
		return resultado;
	}
	
	public int saveMeta(Meta meta, Statement stmExt)
	{
	    int respuesta = 10000;
		int actualizados = 0;
		String CLASS_METHOD = "MetaManager.saveMeta";
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

				transActiva = true;
				ConexAbierta = true;
			}

    		if (meta.getValor() == null) 
    		{
    			sql = "DELETE FROM META ";
    		    sql = sql + "WHERE Serie_Id = " + meta.getMetaId().getSerieId();
    		    sql = sql + " AND Indicador_Id = " + meta.getMetaId().getIndicadorId();
    		    sql = sql + " AND Ano = " + meta.getMetaId().getAno();
    		    sql = sql + " AND Periodo = " + meta.getMetaId().getPeriodo();
    		    sql = sql + " AND Plan_Id = " + meta.getMetaId().getPlanId();
    		    sql = sql + " AND tipo = " + meta.getMetaId().getTipo();
    			
    		    stm.executeUpdate(sql);
    			
    		    respuesta = 10000;
    		}
    		else 
    		{
    			sql = "UPDATE META ";
    			sql = sql + "SET valor = " + meta.getValor();
    			sql = sql + " WHERE Serie_Id = " + meta.getMetaId().getSerieId();
    		    sql = sql + " AND Indicador_Id = " + meta.getMetaId().getIndicadorId();
    		    sql = sql + " AND Ano = " + meta.getMetaId().getAno();
    		    sql = sql + " AND Periodo = " + meta.getMetaId().getPeriodo();
    		    sql = sql + " AND Plan_Id = " + meta.getMetaId().getPlanId();
    		    sql = sql + " AND tipo = " + meta.getMetaId().getTipo();

    		    actualizados = stm.executeUpdate(sql);
    			
    			if (actualizados == 0)
    			{
	    			sql = "INSERT INTO META ";
	    			sql = sql + "(Serie_Id, Indicador_Id, Ano, Periodo, Valor, Plan_Id, Tipo, Protegido) ";
	    			sql = sql + "VALUES (" + meta.getMetaId().getSerieId() + ", ";
	    		    sql = sql + meta.getMetaId().getIndicadorId() + ", ";
	    		    sql = sql + meta.getMetaId().getAno() + ", ";
	    		    sql = sql + meta.getMetaId().getPeriodo() + ", ";
	    		    sql = sql + meta.getValor().doubleValue() + ", ";
	    		    sql = sql + meta.getMetaId().getPlanId() + ", ";
	    		    sql = sql + meta.getMetaId().getTipo() + ", ";
	    		    sql = sql + "1)";

	    		    actualizados = stm.executeUpdate(sql);
    			}
    		}

			if (actualizados != 0) 
				respuesta = 10000;
			else
				respuesta = 10001;
		    
			if (stmExt == null && respuesta == 10000)
			{
				cn.commit();
				cn.setAutoCommit(true);
			}
			else if (stmExt == null && respuesta != 10000)
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
	    	
	    	respuesta = 10003;
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

	    return respuesta;
	}
	
  	public List<Integer> getAnosConMetaParcial(Long indicadorId, Long planId, Statement stmExt) 
  	{
		String CLASS_METHOD = "MetaManager.getAnosConMetaParcial";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		List<Integer> anos = new ArrayList<Integer>();
		String ano = null;
		
		try
		{
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;
		
			sql = "SELECT distinct ";
			sql = sql + "ano ";
			sql = sql + "FROM META ";
			sql = sql + "WHERE Plan_Id = " + planId;
			sql = sql + " and Indicador_Id = " + indicadorId;
			sql = sql + " and tipo = " + TipoMeta.getTipoMetaParcial().toString();
			sql = sql + " order by meta.ano asc";
			
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				ano = rs.getString("ano");
				if (ano != null)
					anos.add(Integer.parseInt(ano));
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

    	return anos;
  	}
}

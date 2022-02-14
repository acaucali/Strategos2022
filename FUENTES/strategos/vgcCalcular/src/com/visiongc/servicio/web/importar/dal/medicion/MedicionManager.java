/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.medicion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.MedicionPK;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.framework.model.Usuario;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class MedicionManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public MedicionManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	private List<Medicion> getMedicionesPeriodo(Long indicadorId, Long serieId, Integer anoInicial, Integer anoFinal, Integer periodoInicial, Integer periodoFinal, Statement stmExt) 
	{
		String CLASS_METHOD = "MedicionManager.getMedicionesPeriodo - Query";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Medicion> mediciones = new ArrayList<Medicion>();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		String ano = null;
		String periodo = null;
		String valor = null;
		
		try
		{
			Medicion medicion;
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
		    sql = sql + "from Medicion ";

		    if ((indicadorId != null) || (serieId != null) || (anoInicial != null) || (anoFinal != null) || (periodoInicial != null) || (periodoFinal != null)) 
		    	sql = sql + " where ";

		    sql = sql + "Medicion.indicador_Id = " + indicadorId.toString() + " and Medicion.serie_Id = " + serieId.toString();

		    if ((anoInicial != null) && (periodoInicial != null) && (anoFinal != null) && (periodoFinal != null) && (anoInicial.intValue() == anoFinal.intValue()))
		    {
		    	sql = sql + " and (" + "((Medicion.periodo >= " + periodoInicial + " and Medicion.ano = " + anoInicial + ") and " + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ")) ";
		    	sql = sql + ")";
		    } 
		    else if ((anoInicial != null) && (periodoInicial != null) && (anoFinal != null) && (periodoFinal != null))
		    	sql = sql + " and ((Medicion.periodo >= " + periodoInicial + " and Medicion.ano = " + anoInicial + ") or " + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ") " + " or (Medicion.ano > " + anoInicial + " and Medicion.ano < " + anoFinal + "))";
		    else if ((anoInicial != null) && (periodoInicial != null))
		    	sql = sql + " and (" + "(Medicion.periodo >= " + periodoInicial + " and Medicion.ano = " + anoInicial + ") or " + "(Medicion.ano > " + anoInicial + ")" + ")";
		    else if ((anoFinal != null) && (periodoFinal != null))
		    	sql = sql + " and (" + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ") or " + "(Medicion.ano < " + anoFinal + ")" + ")";
		    else if ((anoInicial != null) && (anoFinal != null)) 
		    	sql = sql + " and (Medicion.ano >= " + anoInicial + " and Medicion.ano <= " + anoFinal + ")";
		    else if (anoInicial != null) 
		    	sql = sql + " and Medicion.ano >= " + anoInicial;
		    else if (anoFinal != null) 
		    	sql = sql + " and Medicion.ano <= " + anoFinal;

		    sql = sql + " order by Medicion.indicador_Id, Medicion.serie_Id, Medicion.ano, Medicion.periodo";
			
			rs = stm.executeQuery(sql);
			while (rs.next()) 
			{
				ano = rs.getString("ano");
				periodo = rs.getString("periodo");
				valor = rs.getString("valor");
				
				medicion = new Medicion();
				MedicionPK medicionPk = new MedicionPK();
				if (ano != null)
					medicionPk.setAno(Integer.parseInt(ano));
				if (periodo != null)
					medicionPk.setPeriodo(Integer.parseInt(periodo));
				medicionPk.setIndicadorId(indicadorId);
				medicionPk.setSerieId(serieId);
				medicion.setMedicionId(medicionPk);
				if (valor != null)
					medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				mediciones.add(medicion);
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
		
		return mediciones;		
	}
	
	public int saveMediciones(Indicador indicador, List<Medicion> mediciones, Long planId, Boolean actualizarUltimaMedicion, Statement stmExt)
	{
		return saveMediciones(indicador, mediciones, planId, actualizarUltimaMedicion, false, stmExt); 
	}
	
	public int saveMediciones(Indicador indicador, List<Medicion> mediciones, Long planId, Boolean actualizarUltimaMedicion, boolean addIndicador, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.saveMediciones";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int resultado = 10000;

		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	    
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
			int respuesta = 0;

			long num = 0L;
	    	for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext(); ) 
	    	{
	    		Medicion medicion = (Medicion)iter.next();

        		num ++;
        		if (this.logConsolaDetallado)
        			System.out.println("Indicador importado numero: " + num + " --> indicador Id: " + medicion.getMedicionId().getIndicadorId());
	    		
	    		if ((indicador != null) && (!indicadores.contains(indicador))) 
	    			indicadores.add(indicador);
	    		else if (indicador == null)
	    		{
	    			Indicador indicadorLocal = new IndicadorManager(pm, log, messageResources).Load(medicion.getMedicionId().getIndicadorId(), stmExt);
	    			if (indicadorLocal != null &&!indicadores.contains(indicadorLocal))
	    				indicadores.add(indicadorLocal);
	    		}

	    		if (medicion.getValor() == null) 
	    		{
	    			sql = "DELETE FROM MEDICION ";
	    		    sql = sql + "WHERE Serie_Id = " + medicion.getMedicionId().getSerieId();
	    		    sql = sql + " AND Indicador_Id = " + medicion.getMedicionId().getIndicadorId();
	    		    sql = sql + " AND Ano = " + medicion.getMedicionId().getAno();
	    		    sql = sql + " AND Periodo = " + medicion.getMedicionId().getPeriodo();
	    			
	    		    stm.executeUpdate(sql);
	    			
    				resultado = 10000;
	    		}
	    		else 
	    		{
	    			sql = "UPDATE MEDICION ";
	    			sql = sql + "SET valor = " + medicion.getValor();
	    			sql = sql + ", Protegido = " + (medicion.getProtegido() ? "1" : "0");
	    			sql = sql + " WHERE Serie_Id = " + medicion.getMedicionId().getSerieId();
	    		    sql = sql + " AND Indicador_Id = " + medicion.getMedicionId().getIndicadorId();
	    		    sql = sql + " AND Ano = " + medicion.getMedicionId().getAno();
	    		    sql = sql + " AND Periodo = " + medicion.getMedicionId().getPeriodo();

	    		    respuesta = stm.executeUpdate(sql);
	    			
	    			if (respuesta == 0)
	    			{
		    			sql = "INSERT INTO MEDICION ";
		    			sql = sql + "(Serie_Id, Indicador_Id, Ano, Periodo, Valor, Protegido) ";
		    			sql = sql + "VALUES (" + medicion.getMedicionId().getSerieId() + ", ";
		    		    sql = sql + medicion.getMedicionId().getIndicadorId() + ", ";
		    		    sql = sql + medicion.getMedicionId().getAno() + ", ";
		    		    sql = sql + medicion.getMedicionId().getPeriodo() + ", ";
		    		    sql = sql + medicion.getValor().doubleValue() + ", ";
		    		    sql = sql + (medicion.getProtegido() ? "1" : "0") + ")";

		    		    respuesta = stm.executeUpdate(sql);
	    			}
	    		}

	    		if (respuesta == 0) 
	    			break;
	    	}
	    	
	    	if (respuesta != 0)
	    	{
	    		if ((actualizarUltimaMedicion != null) && (actualizarUltimaMedicion.booleanValue())) 
	    		{
	    			if (indicadores.size() > 0 && Boolean.parseBoolean(pm.getProperty("calcular", "false")))
	    				resultado = actualizarUltimaMedicionIndicadores(indicadores, planId, stm);

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
	    	
	    return resultado;
	}
	
	public int actualizarUltimaMedicionIndicadores(List<?> indicadores) 
	{
		return actualizarUltimaMedicionIndicadores(indicadores, null, null);
	}
	
	public int actualizarUltimaMedicionIndicadores(List<?> indicadores, Long planId, Statement stmExt) 
	{
		String CLASS_METHOD = "MedicionManager.actualizarUltimaMedicionIndicadores";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		int resultado = 10000;
		
		try
	    {
	    	Object objeto = indicadores.get(0);
	    	boolean esObjetoIndicador = true;
	    	if (objeto.getClass().getName().equals("java.lang.Long")) 
	    		esObjetoIndicador = false;

	    	long num = 0L;
	    	if (!esObjetoIndicador) 
	    	{
	    		for (Iterator<Long> iter = (Iterator<Long>) indicadores.listIterator(); iter.hasNext(); )
		        {
	    			Long indicadorId = (Long)iter.next();
	        		num ++;
	        		if (this.logConsolaDetallado)
	        			System.out.println("Indicador actualizar medicion numero: " + num + " indicador id = " + indicadorId);

	    			Indicador indicador = new IndicadorManager(pm, log, messageResources).Load(indicadorId, stmExt);
	    			if (indicador == null)
	    			{
	    				resultado = 10003;
	    				break;
	    			}
	    			Medicion ultimaMedicion = getUltimaMedicion(indicadorId, indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);
	    			Medicion ultimoProgramado = null;
	    			if (ultimaMedicion != null) 
	    			{
	    				List<Medicion> mediciones = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieProgramadoId(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo(), ultimaMedicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), stmExt);
	    				if ((mediciones != null) && (mediciones.size() > 0)) 
	    					ultimoProgramado = (Medicion)mediciones.get(0);
	    			}
	    			
	    			resultado = 10000;
	    			Double valorUltimaMedicion = null;
	    			Double valorUltimoProgramado = null;
	    			String fechaUltimaMedicion = null;
	    			if (ultimaMedicion != null && ultimaMedicion.getValor() != null) 
	    			{
	    				valorUltimaMedicion = ultimaMedicion.getValor();
	    				if (ultimoProgramado != null && ultimoProgramado.getValor() != null) 
	    					valorUltimoProgramado = ultimoProgramado.getValor();
	    				fechaUltimaMedicion = ultimaMedicion.getMedicionId().getPeriodo().toString() + "/" + ultimaMedicion.getMedicionId().getAno().toString();
	    			}
		          
	    			resultado = new IndicadorManager(pm, log, messageResources).actualizarDatosIndicador(indicador.getIndicadorId(), valorUltimaMedicion, valorUltimoProgramado, fechaUltimaMedicion, stmExt);
	    			if (resultado != 10000)
	    				break;
	    			
	    			new CalculoManager(pm, log, messageResources).calcularAlertaIndicador(indicador, stmExt);
	    			if (planId != null && planId != 0)
	    				new CalculoManager(pm, log, messageResources).calcularAlertaIndicadorPorPlan(indicador, planId, stmExt);
	        	}
	    	}
	    	else 
	    	{
	    		for (ListIterator<Indicador> iter = (ListIterator<Indicador>) indicadores.listIterator(); iter.hasNext(); )
	    		{
	    			Indicador indicador = (Indicador)iter.next();
	        		num ++;
	        		if (this.logConsolaDetallado)
	        			System.out.println("Indicador actualizar medicion numero: " + num + " indicador id = " + indicador.getIndicadorId());

	    			if (indicador == null)
	    			{
	    				resultado = 10003;
	    				break;
	    			}
	    			Medicion ultimaMedicion = getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieReal().getSerieId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);
	    			Medicion ultimoProgramado = null;
	    			if (ultimaMedicion != null) 
	    			{
	    				List<Medicion> mediciones = getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo(), ultimaMedicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), stmExt);
	    				if ((mediciones != null) && (mediciones.size() > 0)) 
	    					ultimoProgramado = (Medicion)mediciones.get(0);
	    			}
	    			resultado = 10000;
	    			Double valorUltimaMedicion = null;
	    			Double valorUltimoProgramado = null;
	    			String fechaUltimaMedicion = null;
	    			if (ultimaMedicion != null && ultimaMedicion.getValor() != null) 
	    			{
	    				valorUltimaMedicion = ultimaMedicion.getValor();
	    				if (ultimoProgramado != null && ultimoProgramado.getValor() != null) 
	    					valorUltimoProgramado = ultimoProgramado.getValor();
	    				fechaUltimaMedicion = ultimaMedicion.getMedicionId().getPeriodo().toString() + "/" + ultimaMedicion.getMedicionId().getAno().toString();
	    			}
		          
	    			resultado = new IndicadorManager(pm, log, messageResources).actualizarDatosIndicador(indicador.getIndicadorId(), valorUltimaMedicion, valorUltimoProgramado, fechaUltimaMedicion, stmExt);
	    			if (resultado != 10000)
	    				break;
		          
	    			new CalculoManager(pm, log, messageResources).calcularAlertaIndicador(indicador, stmExt);
	    			if (planId != null && planId != 0) 
	    				new CalculoManager(pm, log, messageResources).calcularAlertaIndicadorPorPlan(indicador, planId, stmExt);
	    		}
	    	}
	    }
		catch (Exception e)
		{
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
	    	
	    	resultado = 10003;
		}
	    
	    return resultado;
	}
	
	public Medicion getUltimaMedicion(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion, Statement stmExt)
	{
		List<Medicion> mediciones = null;

	    mediciones = getMedicionesPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, corte, tipoMedicion, null, null, null, null, false, stmExt);

	    Medicion medicion = null;
	    if ((mediciones != null) && (mediciones.size() > 0)) 
	    	medicion = (Medicion)mediciones.get(mediciones.size() - 1);

	    return medicion;
	}
	
	public List<Medicion> getMedicionesPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Boolean orderDesc, Statement stmExt)
	{
		List<Medicion> mediciones = null;
	    if (corte.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) 
	    {
	    	if (tipoMedicion.byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
	    		mediciones = getMedicionesAcumuladasPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, anoInicio, anoFinal, periodoInicio, periodoFinal, orderDesc, stmExt);
	    	else
	    		mediciones = getMedicionesPeriodo(indicadorId, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal, frecuencia, stmExt);
	    }
	    else 
	    	mediciones = getMedicionesPeriodo(indicadorId, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal, frecuencia, stmExt);
	    
	    return mediciones;
	}
	
	public List<Medicion> getMedicionesAcumuladasPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Boolean orderDesc, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.getMedicionesAcumuladasPeriodo";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Medicion> mediciones = new ArrayList<Medicion>();
		List<Medicion> medicionesFinales = new ArrayList<Medicion>();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		try
		{
			Medicion medicion;
			
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
			sql = sql + "from Medicion where Medicion.indicador_Id = " + indicadorId + " and Medicion.serie_Id = " + serieId;

		    int anoInicioInterno = 0;
		    int anoFinalInterno = 0;
		    int periodoInicioInterno = 0;
		    int periodoFinalInterno = 0;
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
		    if (anoInicio != null) 
		    	anoInicioInterno = anoInicio.intValue();
	    	if (anoFinal != null) 
	    		anoFinalInterno = anoFinal.intValue();
		    if (periodoInicio != null) 
		    	periodoInicioInterno = periodoInicio.intValue();
		    if (periodoFinal != null) 
		    	periodoFinalInterno = periodoFinal.intValue();
		    if ((anoInicio == null) || (periodoInicio == null)) 
		    {
		    	Medicion medTemp = getPrimeraMedicion(indicadorId, serieId, stmExt);
		    	if (medTemp != null && medTemp.getMedicionId() != null) 
		    	{
		    		if (anoInicio == null) 
		    			anoInicioInterno = medTemp.getMedicionId().getAno().intValue();
		    		if (periodoInicio == null) 
		    			periodoInicioInterno = medTemp.getMedicionId().getPeriodo().intValue();
		    	}
		    }
		    if ((anoFinal == null) || (periodoFinal == null)) 
		    {
		    	Medicion medTemp = getUltimaMedicion(indicadorId, serieId, stm);
		    	if (medTemp != null && medTemp.getMedicionId() != null) 
		    	{
		    		if (anoFinal == null) 
		    			anoFinalInterno = medTemp.getMedicionId().getAno().intValue();
		    		if (periodoFinal == null) 
		    			periodoFinalInterno = medTemp.getMedicionId().getPeriodo().intValue();
		    	}
		    }

		    if ((anoInicioInterno > 0) && (anoFinalInterno > 0) && (periodoInicioInterno > 0) && (periodoFinalInterno > 0)) 
		    {
		    	List<Medicion> medicionesExistentes = new ArrayList<Medicion>();
		    	boolean hayValorAcumuladoInicial = false;
		    	Double valorAcumuladoInicial = null;
		    	boolean hayMediciones = false;
		    	double acumulado = 0.0D;
		    	if (valorInicialCero != null && valorInicialCero.booleanValue()) 
		    	{
		    		for (int ano = anoInicioInterno; ano <= anoFinalInterno; ano++) 
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
		          
		    			String sqlInt = sql + " and Medicion.ano = " + ano;
		          
		    			int anoAdicional = ano;
		    			int periodoCierre = 0;
		    			if (mesCierre.byteValue() != 12) 
		    			{
		    				if (ano == anoInicioInterno) 
		    				{
		    					if (periodoInicioInterno <= periodoHastaCierre) 
		    					{
		    						anoAdicional = ano - 1;
		    						sqlInt = sqlInt + " or (Medicion.ano = " + anoAdicional + " and Medicion.periodo >= " +periodoCierre + ")";
		    					} 
		    					else 
		    						sqlInt = sqlInt + " and Medicion.periodo >= " + periodoCierre;
		    				}
		    				else if (1 <= periodoHastaCierre) 
		    				{
		    					anoAdicional = ano - 1;
		    					sqlInt = sqlInt + " or (Medicion.ano = " + anoAdicional + " and Medicion.periodo >= " + periodoCierre + ")";
		    				} 
		    				else 
		    					sqlInt = sqlInt + " and Medicion.periodo >= " + periodoCierre;

		    				periodoCierre = periodoDesdeCierre;
		    			}
		    			
		    			sqlInt = sqlInt + "order by Medicion.ano asc, Medicion.periodo asc";
		    			List<Medicion> medicionesTemp = new ArrayList<Medicion>();
		    			String valor = null;
		    			String anoRs = null;
		    			String periodo = null;

		    			rs = stm.executeQuery(sqlInt);
		    			
		    			while (rs.next()) 
		    			{
		    				anoRs = rs.getString("ano");
		    				periodo = rs.getString("periodo");
		    				valor = rs.getString("valor");

		    				medicion = new Medicion();
		    				MedicionPK medicionPk = new MedicionPK();
		    				if (anoRs != null)
		    					medicionPk.setAno(Integer.parseInt(anoRs));
		    				if (periodo != null)
		    					medicionPk.setPeriodo(Integer.parseInt(periodo));
		    				medicionPk.setIndicadorId(indicadorId);
		    				medicionPk.setSerieId(serieId);
		    				medicion.setMedicionId(medicionPk);
		    				if (valor != null)
		    					medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
		    				
		    				medicionesTemp.add(medicion);
		    			}
		    			rs.close();
		    			
		    			if (anoInicioInterno == anoFinalInterno)
		    			{
		    				for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    				{
		    					Medicion medTemp = (Medicion)m.next();

		    					if ((!hayMediciones) && (hayValorAcumuladoInicial)) 
		    					{
		    						valorAcumuladoInicial = new Double(acumulado);
		    					}
		    					acumulado += medTemp.getValor().doubleValue();
		    					if ((medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) && (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno)) 
		    					{
		    						hayMediciones = true;
		    						medTemp.setValor(new Double(acumulado));
		    						medicionesExistentes.add(medTemp);
		    					}
		    					hayValorAcumuladoInicial = true;
		    				}
		    			}
		    			else if (ano == anoInicioInterno)
		    			{
		    				for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    				{
		    					Medicion medTemp = (Medicion)m.next();
		    					if ((!hayMediciones) && (hayValorAcumuladoInicial)) 
		    					{
		    						valorAcumuladoInicial = new Double(acumulado);
		    					}
		    					acumulado += medTemp.getValor().doubleValue();
		    					if (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) 
		    					{
		    						hayMediciones = true;
		    						medTemp.setValor(new Double(acumulado));
		    						medicionesExistentes.add(medTemp);
		    					}
		    					hayValorAcumuladoInicial = true;
		    				}
		    			}
		    			else if (ano == anoFinalInterno)
		    			{
		    				for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    				{
		    					Medicion medTemp = (Medicion)m.next();
		    					acumulado += medTemp.getValor().doubleValue();
		    					if (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno) 
		    					{
		    						hayMediciones = true;
		    						medTemp.setValor(new Double(acumulado));
		    						medicionesExistentes.add(medTemp);
		    					}
		    				}
		    			}
		    			else
		    			{
		    				for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    				{
		    					Medicion medTemp = (Medicion)m.next();
		    					acumulado += medTemp.getValor().doubleValue();
		    					hayMediciones = true;
		    					medTemp.setValor(new Double(acumulado));
		    					medicionesExistentes.add(medTemp);
		    				}
		    			}
		    		}
		      }
		      else 
		      {
		    	  acumulado = 0.0D;
		    	  String sqlInt = sql + " and Medicion.ano <= " + anoFinalInterno + " order by Medicion.ano asc, Medicion.periodo asc";

		    	  List<Medicion> medicionesTemp = new ArrayList<Medicion>();
		    	  String valor = null;
		    	  String anoRs = null;
		    	  String periodo = null;
		    	  
		    	  rs = stm.executeQuery(sqlInt);

		    	  while (rs.next()) 
		    	  {
		    		  anoRs = rs.getString("ano");
		    		  periodo = rs.getString("periodo");
		    		  valor = rs.getString("valor");

		    		  medicion = new Medicion();
		    		  MedicionPK medicionPk = new MedicionPK();
		    		  if (anoRs != null)
		    			  medicionPk.setAno(Integer.parseInt(anoRs));
		    		  if (periodo != null)
		    			  medicionPk.setPeriodo(Integer.parseInt(periodo));
		    		  medicionPk.setIndicadorId(indicadorId);
		    		  medicionPk.setSerieId(serieId);
		    		  medicion.setMedicionId(medicionPk);
		    		  if (valor !=null)
		    			  medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
		    		  
		    		  medicionesTemp.add(medicion);
		    	  }
		    	  rs.close();
		        
		    	  if (anoInicioInterno == anoFinalInterno)
		    	  {
		    		  for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    		  {
		    			  Medicion medTemp = (Medicion)m.next();
		    			  if ((!hayMediciones) && (hayValorAcumuladoInicial)) 
		    				  valorAcumuladoInicial = new Double(acumulado);
		    			  acumulado += medTemp.getValor().doubleValue();
		    			  if (medTemp.getMedicionId().getAno().intValue() == anoInicioInterno && (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) && (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno)) 
		    			  {
		    				  hayMediciones = true;
		    				  medTemp.setValor(new Double(acumulado));
		    				  medicionesExistentes.add(medTemp);
		    			  }
		    			  hayValorAcumuladoInicial = true;
		    		  }
		    	  }
		    	  else 
		    	  {
		    		  for (Iterator<Medicion> m = medicionesTemp.iterator(); m.hasNext(); ) 
		    		  {
		    			  Medicion medTemp = (Medicion)m.next();
		    			  if ((!hayMediciones) && (hayValorAcumuladoInicial)) 
		    				  valorAcumuladoInicial = new Double(acumulado);
		    			  acumulado += medTemp.getValor().doubleValue();
		    			  medTemp.setValor(new Double(acumulado));
		    			  if (medTemp.getMedicionId().getAno().intValue() == anoInicioInterno) 
		    			  {
		    				  if (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) 
		    				  {
		    					  hayMediciones = true;
		    					  medicionesExistentes.add(medTemp);
		    				  }
		    				  hayValorAcumuladoInicial = true;
		    			  } 
		    			  else if (medTemp.getMedicionId().getAno().intValue() == anoFinalInterno) 
		    			  {
		    				  if (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno) 
		    				  {
		    					  hayMediciones = true;
		    					  medicionesExistentes.add(medTemp);
		    				  }
		    			  } 
		    			  else if ((medTemp.getMedicionId().getAno().intValue() > anoInicioInterno) && (medTemp.getMedicionId().getAno().intValue() < anoFinalInterno)) 
		    			  {
		    				  hayMediciones = true;
		    				  medicionesExistentes.add(medTemp);
		    			  }
		    		  }
		    	  }
		      }

		      int ano = anoInicioInterno;
		      Iterator<Medicion> iterMedicionesExistentes = medicionesExistentes.iterator();
		      Medicion primeraMedicionExistente = null;
		      if (medicionesExistentes.size() > 0) 
		    	  primeraMedicionExistente = (Medicion)medicionesExistentes.get(0);
		      boolean getExistente = iterMedicionesExistentes.hasNext();
		      medicion = null;
		      Medicion medicionExistente = null;

		      while (ano <= anoFinalInterno)
		      {
		    	  int periodo = 1;
		    	  if (ano == anoInicioInterno) 
		    		  periodo = periodoInicioInterno;
		    	  int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    	  if (ano == anoFinalInterno) 
		    		  periodoMaximo = periodoFinalInterno;

		    	  while (periodo <= periodoMaximo) 
		    	  {
		    		  if (getExistente) 
		    			  medicionExistente = (Medicion)iterMedicionesExistentes.next();
		    		  if (medicionExistente != null) 
		    		  {
		    			  if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo)) 
		    			  {
		    				  medicion = medicionExistente;
		    				  getExistente = iterMedicionesExistentes.hasNext();
		    				  medicionExistente = null;
		    			  } 
		    			  else 
		    			  {
		    				  medicion = null;
		    				  getExistente = false;
		    			  }
		    		  } 
		    		  else 
		    		  {
		    			  medicion = null;
		    			  getExistente = false;
		    		  }
		          
		    		  if (medicion == null) 
		    		  {
		    			  medicion = new Medicion();
		    			  MedicionPK medicionPk = new MedicionPK();
		    			  medicionPk.setIndicadorId(indicadorId);
		    			  medicionPk.setSerieId(SerieTiempo.getSerieMetaId());
		    			  medicionPk.setAno(new Integer(ano));
		    			  medicionPk.setPeriodo(new Integer(periodo));
		    			  medicion.setMedicionId(medicionPk);
		    			  if ((valorAcumuladoInicial != null) && (primeraMedicionExistente != null) && ((ano < primeraMedicionExistente.getMedicionId().getAno().intValue()) || ((ano == primeraMedicionExistente.getMedicionId().getAno().intValue()) && (periodo < primeraMedicionExistente.getMedicionId().getPeriodo().intValue())))) 
		    				  medicion.setValor(valorAcumuladoInicial);

		    			  medicion.setProtegido(new Boolean(false));
		    		  }

		    		  mediciones.add(medicion);
		    		  periodo++;
		    	  }
		    	  ano++;
      			}
		    }
		    
		    if (orderDesc)
		    {
		    	for (int i = mediciones.size()-1; i > -1; i--) 
		    		medicionesFinales.add(mediciones.get(i)); 
		    }
		    else
		    	medicionesFinales.addAll(mediciones);
		    
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
		
		return medicionesFinales;		
	}
	
	public Medicion getPrimeraMedicion(Long indicadorId, Long serieId, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.getPrimeraMedicion";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Medicion medicion = new Medicion();
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String valor = null;
  	  	String ano = null;
  	  	String periodo = null;
  	  
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
		    sql = sql + "from Medicion ";
		    sql = sql + "WHERE Medicion.indicador_Id = " + indicadorId;
		    sql = sql + " and Medicion.serie_Id = " + serieId;
		    sql = sql + " order by Medicion.ano, Medicion.periodo";

			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				ano = rs.getString("ano");
		  		periodo = rs.getString("periodo");
		  		valor = rs.getString("valor");

		  		medicion = new Medicion();
				MedicionPK medicionPk = new MedicionPK();
	  		  	if (ano != null)
	  		  		medicionPk.setAno(Integer.parseInt(ano));
	  		  	if (periodo != null)
	  		  		medicionPk.setPeriodo(Integer.parseInt(periodo));
				medicionPk.setIndicadorId(indicadorId);
				medicionPk.setSerieId(serieId);
				medicion.setMedicionId(medicionPk);
				if (valor != null)
					medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
			}
			rs.close();
		}
		catch (Exception e)
		{
			String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return medicion;		
	}
	
	public Medicion getUltimaMedicion(Long indicadorId, Long serieId, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.getUltimaMedicion";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Medicion medicion = new Medicion();
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
		    sql = sql + "from Medicion ";
		    sql = sql + " WHERE Medicion.indicador_Id = " + indicadorId;
		    sql = sql + " and Medicion.serie_Id = " + serieId;
		    sql = sql + " order by Medicion.ano desc, Medicion.periodo desc";

			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				ano = rs.getString("ano");
		  		periodo = rs.getString("periodo");
				valor = rs.getString("valor");

				medicion = new Medicion();
				MedicionPK medicionPk = new MedicionPK();
	  		  	if (ano != null)
	  		  		medicionPk.setAno(Integer.parseInt(ano));
	  		  	if (periodo != null)
	  		  		medicionPk.setPeriodo(Integer.parseInt(periodo));
				medicionPk.setIndicadorId(indicadorId);
				medicionPk.setSerieId(serieId);
				medicion.setMedicionId(medicionPk);
				if (valor != null)
					medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
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
		
		return medicion;		
	}
	
	public List<Medicion> getMedicionesPeriodo(Long indicadorId, Byte frecuencia, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.getMedicionesPeriodo Con Frecuencia";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Medicion> mediciones = new ArrayList<Medicion>();
		List<Medicion> medicionesExistentes = new ArrayList<Medicion>();
		Medicion medicion = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String periodoRs = null;
		String valor = null;
		Integer ano = null;
		Integer periodo = null;

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
		    sql = sql + "from Medicion ";
	
		    if ((indicadorId != null) || (serieId != null) || (anoInicio != null) || (anoFinal != null) || (periodoInicio != null) || (periodoFinal != null)) 
		    	sql = sql + " where ";
	
		    sql = sql + "Medicion.indicador_Id = " + indicadorId + " and Medicion.serie_Id = " + serieId;
	
		    if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null) && (anoInicio.intValue() == anoFinal.intValue()))
		    {
		    	sql = sql + " and (" + "((Medicion.periodo >= " + periodoInicio + " and Medicion.ano = " + anoInicio + ") and " + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ")) ";
		    	sql = sql + ")";
		    } 
		    else if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null))
		    	sql = sql + " and ((Medicion.periodo >= " + periodoInicio + " and Medicion.ano = " + anoInicio + ") or " + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ") " + " or (Medicion.ano > " + anoInicio + " and Medicion.ano < " + anoFinal + "))";
		    else if ((anoInicio != null) && (periodoInicio != null)) 
		    	sql = sql + " and (" + "(Medicion.periodo >= " + periodoInicio + " and Medicion.ano = " + anoInicio + ") or " + "(Medicion.ano > " + anoInicio + ")" + ")";
		    else if ((anoFinal != null) && (periodoFinal != null)) 
		    	sql = sql + " and (" + "(Medicion.periodo <= " + periodoFinal + " and Medicion.ano = " + anoFinal + ") or " + "(Medicion.ano < " + anoFinal + ")" + ")";
		    else if ((anoInicio != null) && (anoFinal != null)) 
		    	sql = sql + " and (Medicion.ano >= " + anoInicio + " and Medicion.ano <= " + anoFinal + ")";
		    else if (anoInicio != null) 
		    	sql = sql + " and Medicion.ano >= " + anoInicio;
		    else if (anoFinal != null) 
		    	sql = sql + " and Medicion.ano <= " + anoFinal;
	
		    sql = sql + " order by Medicion.indicador_Id, Medicion.serie_Id, Medicion.ano, Medicion.periodo";

			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				anoRs = rs.getString("ano");
		  		periodoRs = rs.getString("periodo");
				valor = rs.getString("valor");

				medicion = new Medicion();
				MedicionPK medicionPk = new MedicionPK();
	  		  	if (anoRs != null)
	  		  		medicionPk.setAno(Integer.parseInt(anoRs));
	  		  	if (periodoRs != null)
	  		  		medicionPk.setPeriodo(Integer.parseInt(periodoRs));
				medicionPk.setIndicadorId(indicadorId);
				medicionPk.setSerieId(serieId);
				medicion.setMedicionId(medicionPk);
				if (valor != null)
					medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
				
				medicionesExistentes.add(medicion);
			}
			rs.close();
		    
		    if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null) && (frecuencia != null))
		    {
		    	ano = anoInicio.intValue();
		    	Iterator<Medicion> iterMedicionesExistentes = medicionesExistentes.iterator();
		    	boolean getExistente = iterMedicionesExistentes.hasNext();
		    	medicion = null;
		    	Medicion medicionExistente = null;
	
		    	while (ano <= anoFinal.intValue())
		    	{
		    		periodo = 1;
		    		if (ano == anoInicio.intValue()) 
		    			periodo = periodoInicio.intValue();
		        
		    		int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
		    		if (ano == anoFinal.intValue()) 
		    			periodoMaximo = periodoFinal.intValue();
		        
		    		while (periodo <= periodoMaximo) 
		    		{
		    			if (getExistente) 
		    				medicionExistente = (Medicion)iterMedicionesExistentes.next();
		    			if (medicionExistente != null) 
		    			{
		    				if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo)) 
		    				{
		    					medicion = medicionExistente;
		    					getExistente = iterMedicionesExistentes.hasNext();
		    					medicionExistente = null;
		    				} 
		    				else 
		    				{
		    					medicion = null;
		    					getExistente = false;
		    				}
		    			} 
		    			else 
		    			{
		    				medicion = null;
		    				getExistente = false;
		    			}
		    			if (medicion == null) 
		    			{
		    				medicion = new Medicion();
		    				MedicionPK medicionPk = new MedicionPK();
		    				medicionPk.setIndicadorId(indicadorId);
		    				medicionPk.setSerieId(SerieTiempo.getSerieMetaId());
		    				medicionPk.setAno(new Integer(ano));
		    				medicionPk.setPeriodo(new Integer(periodo));
		    				medicion.setMedicionId(medicionPk);
		    				medicion.setProtegido(new Boolean(false));
		    			}
	
		    			mediciones.add(medicion);
		    			periodo++;
		    		}
		    		ano++;
		    	}
	    	} 
		    else 
		    	mediciones = medicionesExistentes;
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
		
		return mediciones;		
	}
	
	public int deleteMediciones(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Statement stmExt) 
	{
		String CLASS_METHOD = "MedicionManager.deleteMediciones";
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
				transActiva = true;
				ConexAbierta = true;
				
				stm = cn.createStatement();
			}
			else
				stm = stmExt;

			sql = "DELETE FROM Medicion where indicador_Id = " + indicadorId;

		    boolean continuar = false;

		    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) 
		    {
		    	if (anoInicio.intValue() != anoFinal.intValue()) 
		    	{
		    		sql = sql + " and (((ano = " + anoInicio + ") and (periodo >= " + periodoInicio + "))";
		    		sql = sql + " or ((ano > " + anoInicio + ") and (ano < " + anoFinal + "))";
		    		sql = sql + " or ((ano = " + anoFinal + ") and (periodo <= " + periodoFinal + ")))";
		    	} 
		    	else 
		    	{
		    		sql = sql + " and ((ano = " + anoInicio + ") and (periodo >= " + periodoInicio + ")";
		    		sql = sql + " and (ano = " + anoFinal + ") and (periodo <= " + periodoFinal + "))";
		    	}
		    	continuar = true;
	    	} 
		    else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    	continuar = true;
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano >= " + anoInicio;
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano <= " + anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and ano >= " + anoInicio;
		    	sql = sql + " and ano <= " + anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and (((ano = " + anoInicio + ") and (periodo >= " + periodoInicio + "))";
		    	sql = sql + " or (ano > " + anoInicio + "))";
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) 
		    {
		    	sql = sql + " and (((ano = " + anoFinal + ") and (periodo <= " + periodoFinal + "))";
		    	sql = sql + " or (ano < " + anoFinal + "))";
		    	continuar = true;
		    }

		    if (continuar) 
		    {
		    	if (serieId != null) 
		    		sql = sql + " and serie_Id = " + serieId;
		    }
			
		    stm.executeUpdate(sql);
			
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
	
	public List<Medicion> getMedicionesPeriodo(Long indicadorId, Long serieId, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Byte frecuencia, Statement stmExt)
	{
	    List<Medicion> medicionesTemp = getMedicionesPeriodo(indicadorId, serieId, anoDesde, anoHasta, periodoDesde, periodoHasta, stmExt);
	    List<Medicion> mediciones = new ArrayList<Medicion>();
	    if ((frecuencia != null) && (anoDesde != null) && (anoHasta != null) && (periodoDesde != null) && (periodoHasta != null)) 
	    {
	    	Iterator<Medicion> medicionesExistentes = medicionesTemp.iterator();
	    	boolean getExistente = medicionesExistentes.hasNext();
	    	Medicion medicion = null;
	    	Medicion medicionExistente = null;

	    	for (int ano = anoDesde.intValue(); ano <= anoHasta.intValue(); ano++) 
	    	{
	    		int periodoDesdeInterno = 1;
	    		int periodoHastaInterno = 0;
	    		if (ano == anoDesde.intValue()) 
	    			periodoDesdeInterno = periodoDesde.intValue();
	    		if (ano == anoHasta.intValue()) 
	    			periodoHastaInterno = periodoHasta.intValue();
	    		if (periodoHastaInterno == 0) 
	    			periodoHastaInterno = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
	    		
	    		for (int periodo = periodoDesdeInterno; periodo <= periodoHastaInterno; periodo++) 
	    		{
	    			if (getExistente) 
	    				medicionExistente = (Medicion)medicionesExistentes.next();
	          
	    			if (medicionExistente != null) 
	    			{
	    				if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo)) 
	    				{
	    					medicion = medicionExistente;
	    					getExistente = medicionesExistentes.hasNext();
	    					medicionExistente = null;
	    				} 
	    				else 
	    				{
	    					medicion = null;
	    					getExistente = false;
	    				}
	    			} 
	    			else 
	    			{
	    				medicion = null;
	    				getExistente = false;
	    			}
	    			
	    			if (medicion == null) 
	    			{
	    				medicion = new Medicion();
	    				MedicionPK medicionPk = new MedicionPK();
	    				medicionPk.setIndicadorId(indicadorId);
	    				medicionPk.setSerieId(serieId);
	    				medicionPk.setAno(new Integer(ano));
	    				medicionPk.setPeriodo(new Integer(periodo));
	    				medicion.setMedicionId(medicionPk);
	    				medicion.setProtegido(new Boolean(false));
	    			}
	    			
	    			mediciones.add(medicion);
	    		}
	    	}
	    }
	    else 
	    	mediciones = medicionesTemp;

	    return mediciones;
	}
	
	public int deleteMedicion(Medicion medicion, Usuario usuario, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.deleteMedicion";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		String sql = "";
		int resultado = 10000;
		boolean ConexAbierta = false;
	
		try
		{
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				transActiva = true;
				ConexAbierta = true;
				stm = cn.createStatement();
			}
			else
				stm = stmExt;

	    	if (medicion.getMedicionId() != null)
	    	{
	    		sql = "DELETE FROM Medicion where indicador_Id = " + medicion.getMedicionId().getIndicadorId().longValue() + " and serie_Id = " +  medicion.getMedicionId().getSerieId().longValue() + " and ano = " + medicion.getMedicionId().getAno().intValue() + " and periodo = " + medicion.getMedicionId().getPeriodo().intValue();
	    		stm.executeUpdate(sql);
	    		
	    		resultado = 10000;
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
	
	public List<Medicion> getUltimasMedicionesIndicadores(List<Long> indicadoresIds, Long serieId, Statement stmExt)
	{
		String CLASS_METHOD = "MedicionManager.getUltimasMedicionesIndicadores";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		List<Medicion> ultimasMediciones = new ArrayList<Medicion>();
    	List<Medicion> mediciones = new ArrayList<Medicion>();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		String anoRs = null;
		String periodoRs = null;
		String valor = null;
		String indicadorId = null;

		Integer ano = null;
		Integer periodo = null;
		
		try
		{
			Medicion medicion;
			if (stmExt == null)
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			else
				stm = stmExt;

		    if ((indicadoresIds != null) && (indicadoresIds.size() > 0)) 
		    {
		    	String sql = "SELECT ";
		    	sql = sql + "indicador_Id, ";
		    	sql = sql + "serie_Id, ";
				sql = sql + "ano, ";
				sql = sql + "periodo, ";
				sql = sql + "valor ";
			    sql = sql + "from Medicion medicion";
			    sql = sql + " where serie_Id = " + serieId;
			    sql = sql + " and medicion.indicador_Id in (";
		    	
		    	for (Iterator<Long> iter = indicadoresIds.iterator(); iter.hasNext(); ) 
		    	{
		    		Long indicador = (Long)iter.next();
		    		sql = sql + indicador.toString() + ", ";
		    	}
		      
		    	sql = sql.substring(0, sql.length() - 2) + ") order by medicion.ano desc, medicion.periodo desc";

				rs = stm.executeQuery(sql);
				
				while (rs.next()) 
				{
					indicadorId = rs.getString("indicador_Id");
					anoRs = rs.getString("ano");
					periodoRs = rs.getString("periodo");
					valor = rs.getString("valor");
					
					medicion = new Medicion();
					MedicionPK medicionPk = new MedicionPK();
					if (anoRs != null)
						medicionPk.setAno(Integer.parseInt(anoRs));
					if (periodoRs != null)
						medicionPk.setPeriodo(Integer.parseInt(periodoRs));
					if (indicadorId != null)
						medicionPk.setIndicadorId(Long.parseLong(indicadorId));
					medicionPk.setSerieId(serieId);
					medicion.setMedicionId(medicionPk);
					if (valor != null)
						medicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(valor)));
					
					mediciones.add(medicion);
				}
				rs.close();
		    	
		    	if (mediciones.size() > 0) 
		    	{
		    		medicion = (Medicion)mediciones.get(0);
	
		    		ano = medicion.getMedicionId().getAno().intValue();
		    		periodo = medicion.getMedicionId().getPeriodo().intValue();
	
		    		for (Iterator<Medicion> iter = mediciones.iterator(); iter.hasNext(); ) 
		    		{
		    			medicion = (Medicion)iter.next();
	
		    			if ((medicion.getMedicionId().getAno().intValue() != ano) || (medicion.getMedicionId().getPeriodo().intValue() != periodo))
		    				break;
		    			
		    			ultimasMediciones.add(medicion);
		    		}
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

	    return ultimasMediciones;
	}
	
	public int saveMedicion(Medicion medicion, Statement stmExt)
	{
	    int respuesta = 10000;
		int actualizados = 0;
		String CLASS_METHOD = "MedicionManager.saveMedicion";
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

    		if (medicion.getValor() == null) 
    		{
    			sql = "DELETE FROM MEDICION ";
    		    sql = sql + "WHERE Serie_Id = " + medicion.getMedicionId().getSerieId();
    		    sql = sql + " AND Indicador_Id = " + medicion.getMedicionId().getIndicadorId();
    		    sql = sql + " AND Ano = " + medicion.getMedicionId().getAno();
    		    sql = sql + " AND Periodo = " + medicion.getMedicionId().getPeriodo();
    			
    		    stm.executeUpdate(sql);
    			
    		    respuesta = 10000;
    		}
    		else 
    		{
    			sql = "UPDATE MEDICION ";
    			sql = sql + "SET valor = " + medicion.getValor();
    			sql = sql + " WHERE Serie_Id = " + medicion.getMedicionId().getSerieId();
    		    sql = sql + " AND Indicador_Id = " + medicion.getMedicionId().getIndicadorId();
    		    sql = sql + " AND Ano = " + medicion.getMedicionId().getAno();
    		    sql = sql + " AND Periodo = " + medicion.getMedicionId().getPeriodo();

    		    actualizados = stm.executeUpdate(sql);
    			
    			if (actualizados == 0)
    			{
	    			sql = "INSERT INTO MEDICION ";
	    			sql = sql + "(Serie_Id, Indicador_Id, Ano, Periodo, Valor, Protegido) ";
	    			sql = sql + "VALUES (" + medicion.getMedicionId().getSerieId() + ", ";
	    		    sql = sql + medicion.getMedicionId().getIndicadorId() + ", ";
	    		    sql = sql + medicion.getMedicionId().getAno() + ", ";
	    		    sql = sql + medicion.getMedicionId().getPeriodo() + ", ";
	    		    sql = sql + medicion.getValor().doubleValue() + ", ";
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
}

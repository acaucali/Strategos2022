/**
 * 
 */
package com.visiongc.servicio.strategos.calculos.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.visiongc.servicio.strategos.calculos.model.util.TipoMacro;
import com.visiongc.servicio.strategos.calculos.model.util.VgcFormulaEvaluator;
import com.visiongc.servicio.strategos.indicadores.model.Formula;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.InsumoFormula;
import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.MedicionPK;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicador;
import com.visiongc.servicio.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.servicio.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.servicio.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoAlerta;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoSuma;
import com.visiongc.servicio.strategos.iniciativas.model.Iniciativa;
import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.model.util.Periodo;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.servicio.strategos.planes.model.IndicadorEstado;
import com.visiongc.servicio.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlan;
import com.visiongc.servicio.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.servicio.strategos.planes.model.Meta;
import com.visiongc.servicio.strategos.planes.model.MetaPK;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.strategos.planes.model.PerspectivaEstado;
import com.visiongc.servicio.strategos.planes.model.PerspectivaEstadoPK;
import com.visiongc.servicio.strategos.planes.model.Plan;
import com.visiongc.servicio.strategos.planes.model.PlanEstado;
import com.visiongc.servicio.strategos.planes.model.PlanEstadoPK;
import com.visiongc.servicio.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.servicio.strategos.planes.model.util.TipoMeta;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.servicio.strategos.util.LapsoTiempo;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.dal.iniciativa.IniciativaManager;
import com.visiongc.servicio.web.importar.dal.medicion.MedicionManager;
import com.visiongc.servicio.web.importar.dal.meta.MetaManager;
import com.visiongc.servicio.web.importar.dal.organizacion.OrganizacionManager;
import com.visiongc.servicio.web.importar.dal.perspectiva.PerspectivaManager;
import com.visiongc.servicio.web.importar.dal.plan.PlanManager;
import com.visiongc.servicio.web.importar.dal.unidad.UnidadManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.commons.util.MathUtil;
import com.visiongc.framework.model.Usuario;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class CalculoManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public CalculoManager()
	{
	}

	public CalculoManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	public int calcularAlertaIndicador(Indicador indicador, Statement stmExt)
	{
		String CLASS_METHOD = "CalculoManager.calcularAlertaIndicador";

		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);
		
		int resultado = 10000;
		
	    try
	    {
		    Byte alerta = AlertaIndicador.getAlertaNoDefinible();
		    Medicion ultimaMedicion = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), stmExt);

		    if (ultimaMedicion != null) 
		    	alerta = calcularAlertaIndicadorPorTipoCalculo(indicador, ultimaMedicion, stmExt);

	    	resultado = new IndicadorManager(pm, log, messageResources).updateAlerta(indicador.getIndicadorId(), alerta, stmExt);
	    }
		catch (Exception e)
		{
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		}

	    return resultado;
	}
	
  	public Byte calcularAlertaIndicadorPorTipoCalculo(Indicador indicador, Medicion ejecutado, Statement stmExt)
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();
  		Medicion ultimaMedicionProgramado = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), stmExt);
  		if (ultimaMedicionProgramado == null || (ultimaMedicionProgramado != null && ultimaMedicionProgramado.getValor() == null))
  			ultimaMedicionProgramado = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), SerieTiempo.getSerieMaximoId(), stmExt);
  		if (ultimaMedicionProgramado == null || (ultimaMedicionProgramado != null && ultimaMedicionProgramado.getValor() == null))
  			ultimaMedicionProgramado = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), SerieTiempo.getSerieMinimoId(), stmExt);

  		if ((ultimaMedicionProgramado != null) && (ultimaMedicionProgramado.getValor() != null))
  			alerta = calcularAlertaIndicadorPorProgramado(indicador, stmExt);
  		else 
			alerta = calcularAlertaIndicadorPorPeriodo(indicador, ejecutado, stmExt);

  		return alerta;
  	}
  	
  	private Byte calcularAlertaIndicadorPorProgramadoMeta(Indicador indicador, Medicion ejecutado, Double valorProgramadoMeta, Double valorProgramadoMetaInferior, Double porcentajeAlertaZonaVerde, Double porcentajeAlertaZonaAmarilla, Statement stmExt) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();
  		if (valorProgramadoMeta == null)
  			return alerta;

  		if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoAumento()))
  			alerta = calcularAlertaIndicadorPorProgramadoRetoAumento(indicador, ejecutado, valorProgramadoMeta, porcentajeAlertaZonaVerde, porcentajeAlertaZonaAmarilla, stmExt);
  		else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaCondicionValorMaximo()))
  			alerta = calcularAlertaIndicadorPorProgramadoValorMaximo(indicador, ejecutado, valorProgramadoMeta, porcentajeAlertaZonaAmarilla, stmExt);
  		else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaRetoDisminucion()))
  			alerta = calcularAlertaIndicadorPorProgramadoRetoDisminucion(indicador, ejecutado, valorProgramadoMeta, porcentajeAlertaZonaVerde, porcentajeAlertaZonaAmarilla, stmExt);
  		else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaCondicionValorMinimo()))
  			alerta = calcularAlertaIndicadorPorProgramadoValorMinimo(indicador, ejecutado, valorProgramadoMeta, porcentajeAlertaZonaAmarilla, stmExt);
  		else if (indicador.getCaracteristica().equals(Caracteristica.getCaracteristicaCondicionBandas()))
  			alerta = calcularAlertaIndicadorPorCondicionBandas(ejecutado.getValor(), porcentajeAlertaZonaAmarilla, valorProgramadoMeta, valorProgramadoMetaInferior);

  		return alerta;
  	}
  	
  	public Byte calcularAlertaIndicador(Byte caracteristica, Double zonaVerde, Double zonaAmarilla, Double ejecutado, Double meta, Double metaInferior, Double ejecutadoAnterior)
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		if (meta == null)
  		{
			if ((caracteristica.equals(Caracteristica.getCaracteristicaRetoAumento())) || (caracteristica.equals(Caracteristica.getCaracteristicaCondicionValorMaximo()))) 
			{
				if (ejecutado > ejecutadoAnterior)
					alerta = AlertaIndicador.getAlertaVerde();
				else if (ejecutado < ejecutadoAnterior)
					alerta = AlertaIndicador.getAlertaRoja();
				else 
					alerta = AlertaIndicador.getAlertaInalterada();
			}
			else if (ejecutado < ejecutadoAnterior)
				alerta = AlertaIndicador.getAlertaVerde();
			else if (ejecutado > ejecutadoAnterior)
				alerta = AlertaIndicador.getAlertaRoja();
			else 
				alerta = AlertaIndicador.getAlertaInalterada();
  		}
  		else if (caracteristica.equals(Caracteristica.getCaracteristicaRetoAumento()))
  		{
			if ((zonaAmarilla != null) && (zonaVerde.doubleValue() >= zonaAmarilla.doubleValue())) 
			{
				if (ejecutado >= meta)
					alerta = AlertaIndicador.getAlertaVerde();
				else if (ejecutado >= zonaVerde.doubleValue())
					alerta = AlertaIndicador.getAlertaVerde();
				else if (ejecutado >= zonaAmarilla.doubleValue())
					alerta = AlertaIndicador.getAlertaAmarilla();
				else 
					alerta = AlertaIndicador.getAlertaRoja();
			}
  		} 
  		else if (caracteristica.equals(Caracteristica.getCaracteristicaCondicionValorMaximo()))
  		{
  	  		if (zonaAmarilla != null) 
  	  		{
  	  			if (ejecutado > meta) 
  	  				alerta = AlertaIndicador.getAlertaRoja();
  	  			else if (zonaAmarilla.doubleValue() <= meta)
  	  			{
  	  				if (ejecutado >= zonaAmarilla.doubleValue()) 
  	  				{
  	  					if (zonaAmarilla.doubleValue() == meta)
  	  						alerta = AlertaIndicador.getAlertaVerde();
  	  					else
  	  						alerta = AlertaIndicador.getAlertaAmarilla();
  	  				}
  	  				else 
  	  					alerta = AlertaIndicador.getAlertaVerde();
  	  			}
  	  		}
  	  		else if (ejecutado > meta)
  	  			alerta = AlertaIndicador.getAlertaRoja();
  	  		else 
  	  			alerta = AlertaIndicador.getAlertaVerde();
  		}
  		else if (caracteristica.equals(Caracteristica.getCaracteristicaRetoDisminucion()))
  		{
  			if ((zonaAmarilla != null) && (zonaVerde.doubleValue() <= zonaAmarilla.doubleValue())) 
  			{
  				if (ejecutado <= meta)
  					alerta = AlertaIndicador.getAlertaVerde();
  				else if (ejecutado <= zonaVerde.doubleValue())
  					alerta = AlertaIndicador.getAlertaVerde();
  				else if (ejecutado <= zonaAmarilla.doubleValue())
  					alerta = AlertaIndicador.getAlertaAmarilla();
  				else 
  					alerta = AlertaIndicador.getAlertaRoja();
  			}
  		}
  		else if (caracteristica.equals(Caracteristica.getCaracteristicaCondicionValorMinimo()))
  		{
  	  		if (zonaAmarilla != null)
  	  		{
  	  			if (ejecutado < meta) 
  	  				alerta = AlertaIndicador.getAlertaRoja();
  	  			else if (zonaAmarilla.doubleValue() >= meta)
  	  			{
  	  				if (ejecutado <= zonaAmarilla.doubleValue()) 
  	  				{
  	  					if (zonaAmarilla.doubleValue() == meta)
  	  						alerta = AlertaIndicador.getAlertaVerde();
  	  					else
  	  						alerta = AlertaIndicador.getAlertaAmarilla();
  	  				}
  	  				else 
  	  					alerta = AlertaIndicador.getAlertaVerde();
  	  			}
  	  		}
  	  		else if (ejecutado < meta)
  	  			alerta = AlertaIndicador.getAlertaRoja();
  	  		else 
  	  			alerta = AlertaIndicador.getAlertaVerde();
  		}
  		else if (caracteristica.equals(Caracteristica.getCaracteristicaCondicionBandas()))
  			alerta = calcularAlertaIndicadorPorCondicionBandas(ejecutado, zonaAmarilla, meta, metaInferior);
  		
  		return alerta;
  	}
  	
  	private Byte calcularAlertaIndicadorPorProgramado(Indicador indicador, Statement stmExt)
  	{
  		UnidadMedida unidad = new UnidadManager(pm, log, messageResources).getUnidadMedidaPorcentaje(stmExt);
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();
  		Medicion ejecutado = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);

  		if ((ejecutado != null) && (ejecutado.getValor() != null))
  		{
  			Double porcentajeAlertaZonaVerde = indicador.getAlertaMetaZonaVerde();
  			Double porcentajeAlertaZonaAmarilla = indicador.getAlertaMetaZonaAmarilla();

  			OrganizacionStrategos organizacion = null;
  			if ((indicador.getAlertaMetaZonaVerde() == null && indicador.getAlertaMetaZonaAmarilla() == null) && ((indicador.getAlertaTipoZonaVerde() == null && indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje()) && indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje()))))
  			{
  				organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  	  			if (organizacion.getPorcentajeZonaVerdeMetaIndicadores() != null)
  	  				porcentajeAlertaZonaVerde = new Double(organizacion.getPorcentajeZonaVerdeMetaIndicadores().doubleValue());
  	  			if (organizacion.getPorcentajeZonaAmarillaMetaIndicadores() != null)
  	  				porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMetaIndicadores().doubleValue());
  			}
  			else if (indicador.getAlertaMetaZonaVerde() == null && (indicador.getAlertaTipoZonaVerde() == null || indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())))
  			{
  				organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  	  			if (organizacion.getPorcentajeZonaVerdeMetaIndicadores() != null)
  	  				porcentajeAlertaZonaVerde = new Double(organizacion.getPorcentajeZonaVerdeMetaIndicadores().doubleValue());
  			}
  			else if (indicador.getAlertaMetaZonaAmarilla() == null && (indicador.getAlertaTipoZonaAmarilla() == null || indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
  			{
  				organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  	  			if (organizacion.getPorcentajeZonaAmarillaMetaIndicadores() != null)
  	  				porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMetaIndicadores().doubleValue());
  			}

  			Medicion programado = null;
  			Medicion programadoInferior = null;
  			if (indicador.getTipoFuncion().byteValue() != TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
  			{
  				List<Medicion> programados = null;
  				if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
  				{
  					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), ejecutado.getMedicionId().getPeriodo(), true, stmExt);
			  		if ((programados != null) && (programados.size() > 0)) 
		  				programado = (Medicion)programados.get(0);
  				}
				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
				{
					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), ejecutado.getMedicionId().getPeriodo(), true, stmExt);	
			  		if ((programados != null) && (programados.size() > 0)) 
		  				programado = (Medicion)programados.get(0);
				}
				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
				{
  					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), ejecutado.getMedicionId().getPeriodo(), true, stmExt);
			  		if ((programados != null) && (programados.size() > 0)) 
		  				programado = (Medicion)programados.get(0);
					List<Medicion> programadosInferior = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), ejecutado.getMedicionId().getPeriodo(), true, stmExt);
			  		if ((programadosInferior != null) && (programadosInferior.size() > 0)) 
			  			programadoInferior = (Medicion)programadosInferior.get(0);
				}
				else
				{
					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), ejecutado.getMedicionId().getPeriodo(), true, stmExt);
			  		if ((programados != null) && (programados.size() > 0)) 
		  				programado = (Medicion)programados.get(0);
				}
  			}
  			else
  			{
  				if (unidad != null && indicador.getUnidadId() != null && indicador.getUnidadId().longValue() == unidad.getUnidadId().longValue() && ejecutado.getValor() == 100D)
  				{
  					List<Medicion> programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  			  		if ((programados != null) && (programados.size() > 0)) 
  		  				programado = (Medicion)programados.get(0);
  				}
  				else
  				{
  	  				List<Medicion> programados = null;
  	  				if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
  	  				{
  	  					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  	  			  		if ((programados != null) && (programados.size() > 0)) 
  	  		  				programado = (Medicion)programados.get(0);
  	  				}
  					else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
  					{
  						programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  	  			  		if ((programados != null) && (programados.size() > 0)) 
  	  		  				programado = (Medicion)programados.get(0);
  					}
  					else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
  					{
  	  					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  	  			  		if ((programados != null) && (programados.size() > 0)) 
  	  		  				programado = (Medicion)programados.get(0);
  						List<Medicion> programadosInferior = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  				  		if ((programadosInferior != null) && (programadosInferior.size() > 0)) 
  				  			programadoInferior = (Medicion)programadosInferior.get(0);
  					}
  					else
  					{
  	  					programados = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, null, null, null, true, stmExt);
  	  			  		if ((programados != null) && (programados.size() > 0))
  	  			  		{
  	  			  			//boolean hayProgramado = false;
  	  			  			//if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue())
  	  			  			//{
  	  			  				//if (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
  	  			  				//{
  	    	  		  				//programado = (Medicion)programados.get(0);
  	    	  		  				//hayProgramado = true;
  	  			  				//}
  	  			  			//}
  	  			  			
  	  			  			//if (!hayProgramado)
  	  			  			//{
  	  	  			  			DecimalFormat nf3 = new DecimalFormat("#000");
  	  			  				int anoPeriodoBuscar = Integer.parseInt(((Integer)ejecutado.getMedicionId().getAno()).toString() + nf3.format(ejecutado.getMedicionId().getPeriodo()).toString());
  	  	  			  			for (Iterator<?> iterMed = programados.iterator(); iterMed.hasNext(); ) 
  	  	  			  			{
  	  	  			  				Medicion medicion = (Medicion)iterMed.next();
  	  	  			  				int anoPeriodo = Integer.parseInt(medicion.getMedicionId().getAno().toString() + nf3.format(medicion.getMedicionId().getPeriodo()).toString());
  	  	  			  				if (anoPeriodo <= anoPeriodoBuscar)
  	  	  			  				{
  	  	    	  			  			programado = medicion;
  	  	  			  					if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
  	  	  			  						break;
  	  	  			  				}
  	  	  			  			}
  	  			  			//}
  	  			  		}
  					}
  				}
  			}

  			if (programado.getValor() != null)
  			{
  	  			if (indicador.getCaracteristica().byteValue() != Caracteristica.getCaracteristicaCondicionBandas().byteValue())
  					alerta = calcularAlertaIndicadorPorProgramadoMeta(indicador, ejecutado, programado.getValor(), null, porcentajeAlertaZonaVerde, porcentajeAlertaZonaAmarilla, stmExt);
  				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue() && programado != null && programado.getValor() != null && programadoInferior != null && programadoInferior.getValor() != null)
  					alerta = calcularAlertaIndicadorPorProgramadoMeta(indicador, ejecutado, programado.getValor(), programadoInferior.getValor(), porcentajeAlertaZonaVerde, porcentajeAlertaZonaAmarilla, stmExt);
  			}
  		}

  		return alerta;
  	}

  	private Byte calcularAlertaIndicadorPorProgramadoRetoAumento(Indicador indicador, Medicion ejecutado, Double meta, Double porcentajeAlertaZonaVerde, Double porcentajeAlertaZonaAmarilla, Statement stmExt) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		Double zonaVerde = calcularZonaVerdeAlertaIndicadorPorProgramadoRetoAumento(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaVerde, stmExt);

  		if (zonaVerde != null)
  		{
  			Double zonaAmarilla = calcularZonaAmarillaAlertaIndicadorPorProgramadoRetoAumento(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaAmarilla, zonaVerde.doubleValue(), stmExt);

  			alerta = calcularAlertaIndicador(Caracteristica.getCaracteristicaRetoAumento(), zonaVerde, zonaAmarilla, ejecutado.getValor(), meta, null, null);
  		}

  		return alerta;
  	}
  	
  	private Double calcularZonaVerdeAlertaIndicadorPorProgramadoRetoAumento(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaVerde, Statement stmExt)
  	{
  		Double zonaVerde = null;

  		if ((indicador.getAlertaTipoZonaVerde() == null) || (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaVerde = indicador.getAlertaMetaZonaVerde();
  			if (zonaVerde == null) 
  				zonaVerde = porcentajeAlertaZonaVerde;
  			if (zonaVerde != null)
  				zonaVerde = new Double(meta * (1.0D - MathUtil.sign(meta) * zonaVerde.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaVerde = indicador.getAlertaMetaZonaVerde();
  			if ((zonaVerde != null) && 
  					(indicador.getAlertaValorVariableZonaVerde().booleanValue()))
  				zonaVerde = new Double(meta - zonaVerde.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaVerde(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);
  			
  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaVerde = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaVerde.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaVerde().booleanValue())
  						zonaVerde = new Double(meta - medicionAlertaZonaVerde.getValor().doubleValue());
  					else 
  						zonaVerde = medicionAlertaZonaVerde.getValor();
  				}
  			}
  		}

  		return zonaVerde;
  	}
  	
  	private Double calcularZonaAmarillaAlertaIndicadorPorProgramadoRetoAumento(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Double zonaVerde, Statement stmExt) 
  	{
  		Double zonaAmarilla = null;

  		if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if (zonaAmarilla == null) 
  				zonaAmarilla = porcentajeAlertaZonaAmarilla;
  			if (zonaAmarilla != null)
  				zonaAmarilla = new Double(zonaVerde - MathUtil.sign(meta) * meta * (zonaAmarilla.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()))
  				zonaAmarilla = new Double(zonaVerde - zonaAmarilla.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);
  			
  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaAmarilla.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaAmarilla().booleanValue())
  						zonaAmarilla = new Double(zonaVerde - medicionAlertaZonaAmarilla.getValor().doubleValue());
  					else 
  						zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
  				}
  			}
  		}

  		return zonaAmarilla;
  	}
  	
  	private Byte calcularAlertaIndicadorPorProgramadoValorMaximo(Indicador indicador, Medicion ejecutado, Double meta, Double porcentajeAlertaZonaAmarilla, Statement stmExt)
  	{
  		Double zonaAmarilla = calcularZonaAmarillaAlertaIndicadorPorProgramadoValorMaximo(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaAmarilla, stmExt);

  		return calcularAlertaIndicador(Caracteristica.getCaracteristicaCondicionValorMaximo(), null, zonaAmarilla, ejecutado.getValor().doubleValue(), meta, null, null);
  	}
  	
  	private Double calcularZonaAmarillaAlertaIndicadorPorProgramadoValorMaximo(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Statement stmExt)
  	{
  		Double zonaAmarilla = null;

  		if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if (zonaAmarilla == null) 
  				zonaAmarilla = porcentajeAlertaZonaAmarilla;
  			if (zonaAmarilla != null)
  				zonaAmarilla = new Double(meta * (1.0D - MathUtil.sign(meta) * zonaAmarilla.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()))
  				zonaAmarilla = new Double(meta - zonaAmarilla.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);
  			
  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaAmarilla.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaAmarilla().booleanValue())
  						zonaAmarilla = new Double(meta - medicionAlertaZonaAmarilla.getValor().doubleValue());
  					else 
  						zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
  				}
  			}
  		}

  		return zonaAmarilla;
  	}

  	private Byte calcularAlertaIndicadorPorProgramadoRetoDisminucion(Indicador indicador, Medicion ejecutado, Double meta, Double porcentajeAlertaZonaVerde, Double porcentajeAlertaZonaAmarilla, Statement stmExt)
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		Double zonaVerde = calcularZonaVerdeAlertaIndicadorPorProgramadoRetoDisminucion(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaVerde, stmExt);

  		if (zonaVerde != null)
  		{
  			Double zonaAmarilla = calcularZonaAmarillaAlertaIndicadorPorProgramadoRetoDisminucion(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaAmarilla, zonaVerde, stmExt);
  			
  			alerta = calcularAlertaIndicador(Caracteristica.getCaracteristicaRetoDisminucion(), zonaVerde, zonaAmarilla, ejecutado.getValor(), meta, null, null);
  		}
  		
  		return alerta;
  	}
  	
  	private Double calcularZonaVerdeAlertaIndicadorPorProgramadoRetoDisminucion(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaVerde, Statement stmExt) 
  	{
  		Double zonaVerde = null;

  		if ((indicador.getAlertaTipoZonaVerde() == null) || (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaVerde = indicador.getAlertaMetaZonaVerde();
  			if (zonaVerde == null) 
  				zonaVerde = porcentajeAlertaZonaVerde;
  			if (zonaVerde != null)
  				zonaVerde = new Double(meta * (1.0D + MathUtil.sign(meta) * zonaVerde.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaVerde = indicador.getAlertaMetaZonaVerde();
  			if ((zonaVerde != null) && (indicador.getAlertaValorVariableZonaVerde().booleanValue()))
  				zonaVerde = new Double(meta + zonaVerde.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaVerde(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);
  			
  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaVerde = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaVerde.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaVerde().booleanValue())
  						zonaVerde = new Double(meta + medicionAlertaZonaVerde.getValor().doubleValue());
  					else 
  						zonaVerde = medicionAlertaZonaVerde.getValor();
  				}
  			}
  		}

  		return zonaVerde;
  	}
  	
  	private Byte calcularAlertaIndicadorPorProgramadoValorMinimo(Indicador indicador, Medicion ejecutado, Double meta, Double porcentajeAlertaZonaAmarilla, Statement stmExt) 
  	{
  		Double zonaAmarilla = calcularZonaAmarillaAlertaIndicadorPorProgramadoValorMinimo(indicador, ejecutado.getMedicionId().getAno(), ejecutado.getMedicionId().getPeriodo(), meta, porcentajeAlertaZonaAmarilla, stmExt);

  		return calcularAlertaIndicador(Caracteristica.getCaracteristicaCondicionValorMinimo(), null, zonaAmarilla, ejecutado.getValor(), meta, null, null);
  	}
  	
  	private Double calcularZonaAmarillaAlertaIndicadorPorProgramadoValorMinimo(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Statement stmExt) 
  	{
  		Double zonaAmarilla = null;

  		if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if (zonaAmarilla == null) 
  				zonaAmarilla = porcentajeAlertaZonaAmarilla;
  			
  			if (zonaAmarilla != null)
  				zonaAmarilla = new Double(meta + MathUtil.sign(meta) * meta * (zonaAmarilla.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if ((zonaAmarilla != null) && 
  					(indicador.getAlertaValorVariableZonaAmarilla().booleanValue()))
  				zonaAmarilla = new Double(meta + zonaAmarilla.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);

  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaAmarilla.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaAmarilla().booleanValue())
  						zonaAmarilla = new Double(meta + medicionAlertaZonaAmarilla.getValor().doubleValue());
  					else 
  						zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
  				}
  			}
  		}

  		return zonaAmarilla;
  	}
  	
  	private Double calcularZonaAmarillaAlertaIndicadorPorProgramadoRetoDisminucion(Indicador indicador, Integer ano, Integer periodo, Double meta, Double porcentajeAlertaZonaAmarilla, Double zonaVerde, Statement stmExt) 
  	{
  		Double zonaAmarilla = null;

  		if ((indicador.getAlertaTipoZonaAmarilla() == null) || (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje()))) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if (zonaAmarilla == null) 
  				zonaAmarilla = porcentajeAlertaZonaAmarilla;
  			if (zonaAmarilla != null)
  				zonaAmarilla = new Double(zonaVerde + MathUtil.sign(meta) * meta * (zonaAmarilla.doubleValue() / 100.0D));
  		}
  		else if (indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaValorAbsolutoMagnitud())) 
  		{
  			zonaAmarilla = indicador.getAlertaMetaZonaAmarilla();
  			if ((zonaAmarilla != null) && (indicador.getAlertaValorVariableZonaAmarilla().booleanValue()))
  				zonaAmarilla = new Double(zonaVerde + zonaAmarilla.doubleValue());
  		}
  		else
  		{
  			List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getAlertaIndicadorIdZonaAmarilla(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ano, ano, periodo, periodo, true, stmExt);

  			if (mediciones.size() > 0) 
  			{
  				Medicion medicionAlertaZonaAmarilla = (Medicion)mediciones.get(0);
  				if (medicionAlertaZonaAmarilla.getValor() != null) 
  				{
  					if (indicador.getAlertaValorVariableZonaAmarilla().booleanValue())
  						zonaAmarilla = new Double(zonaVerde + medicionAlertaZonaAmarilla.getValor().doubleValue());
  					else 
  						zonaAmarilla = medicionAlertaZonaAmarilla.getValor();
  				}
  			}
  		}

  		return zonaAmarilla;
  	}
  	
  	/*
  	private Byte calcularAlertaIndicadorPorMinimoMaximo(Indicador indicador, boolean tieneMinimo, boolean tieneMaximo, Statement stmExt) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		Medicion ejecutado = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);

  		if ((ejecutado != null) && (ejecutado.getValor() != null)) 
  		{
  			boolean calcular = true;
  			Double valorMinimo = null;
  			Double valorMaximo = null;
  			if (tieneMinimo) 
  			{
  				Medicion minimo = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);
  				if (minimo != null)
  					valorMinimo = minimo.getValor();
  				else 
  					calcular = false;
  			}
  			if (tieneMaximo) 
  			{
  				Medicion maximo = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);
  				if (maximo != null) 
  					valorMaximo = maximo.getValor();
  			}
  			
  			if (calcular) 
  			{
  				Double porcentajeAlertaZonaAmarilla = null;
  				if (indicador.getAlertaMinMax() == null) 
  				{
  					OrganizacionStrategos organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  					if (organizacion.getPorcentajeZonaAmarillaMinMaxIndicadores() != null)
  						porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMinMaxIndicadores().doubleValue());
  				}
  				else 
  					porcentajeAlertaZonaAmarilla = new Double(indicador.getAlertaMinMax().doubleValue());

  				if (porcentajeAlertaZonaAmarilla != null) 
  					alerta = calcularAlertaIndicadorPorMinimoMaximo(porcentajeAlertaZonaAmarilla.doubleValue(), ejecutado.getValor().doubleValue(), valorMinimo, valorMaximo);
  			}
  		}

  		return alerta;
  	}

  	private Byte calcularAlertaIndicadorPorMinimoMaximo(Double porcentajeAlertaZonaAmarilla, Double ejecutado, Double valorMinimo, Double valorMaximo) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		Double minimo = 0.0D;
  		Double maximo = 0.0D;

  		if ((valorMinimo != null) && (valorMaximo != null)) 
  		{
  			minimo = valorMinimo.doubleValue();
  			maximo = valorMinimo.doubleValue();
  			Double tamanoZonaAmarilla = (maximo - minimo) * porcentajeAlertaZonaAmarilla / 100.0D / 2.0D;

  			if ((ejecutado > maximo) && (ejecutado < minimo))
  				alerta = AlertaIndicador.getAlertaRoja();
  			else if ((ejecutado >= maximo - tamanoZonaAmarilla) || (ejecutado <= minimo + tamanoZonaAmarilla))
  				alerta = AlertaIndicador.getAlertaAmarilla();
  			else
  				alerta = AlertaIndicador.getAlertaVerde();
  		}
  		else if (valorMinimo != null) 
  		{
  			minimo = valorMinimo.doubleValue();

  			if (ejecutado < minimo)
  				alerta = AlertaIndicador.getAlertaRoja();
  			else if (ejecutado <= minimo * (1.0D + porcentajeAlertaZonaAmarilla / 100.0D))
  				alerta = AlertaIndicador.getAlertaAmarilla();
  			else
  				alerta = AlertaIndicador.getAlertaVerde();
  		}
  		else 
  		{
  			maximo = valorMaximo.doubleValue();

  			if (ejecutado > maximo)
  				alerta = AlertaIndicador.getAlertaRoja();
  			else if (ejecutado >= maximo * (1.0D - porcentajeAlertaZonaAmarilla / 100.0D))
  				alerta = AlertaIndicador.getAlertaAmarilla();
  			else 
  				alerta = AlertaIndicador.getAlertaVerde();
  		}

  		return alerta;
  	}
  	*/
  	
  	private Byte calcularAlertaIndicadorPorPeriodo(Indicador indicador, Medicion ejecutado, Statement stmExt)
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();
    
  		if (ejecutado.getMedicionId() != null)
  		{
	  		int periodoAnterior = PeriodoUtil.getPeriodoAnterior(ejecutado.getMedicionId().getPeriodo().intValue(), ejecutado.getMedicionId().getAno().intValue(), indicador.getFrecuencia().byteValue());
	  		
	  		int anoPeriodoAnterior = PeriodoUtil.getAnoPeriodoAnterior(ejecutado.getMedicionId().getPeriodo().intValue(), ejecutado.getMedicionId().getAno().intValue());
	
	  		Medicion ejecutadoAnterior = null;
	
	  		List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(anoPeriodoAnterior), new Integer(anoPeriodoAnterior), new Integer(periodoAnterior), new Integer(periodoAnterior), indicador.getFrecuencia(), stmExt);
	
	  		if (mediciones.size() > 0) 
	  		{
	  			ejecutadoAnterior = (Medicion)mediciones.get(mediciones.size() - 1);
	
	  			if ((ejecutado.getValor() != null) && (ejecutadoAnterior.getValor() != null)) 
	  				alerta = calcularAlertaIndicador(indicador.getCaracteristica(), null, null, ejecutado.getValor(), null, null, ejecutadoAnterior.getValor());
	  		}
  		}

  		return alerta;
  	}
  	
    private int iniciarCalcularEstadoIndicadorPlan(Indicador indicador, Long planId, int anoInicio, int anoFinal, byte mesInicio, byte mesFinal, byte mesCierre, Statement stmExt)
    {
		String CLASS_METHOD = "CalculoManager.iniciarCalcularEstadoIndicadorPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		int resultado = 10000;
		
    	String[] argsMensajeLog = new String[2];
    	argsMensajeLog[0] = indicador.getIndicadorId().toString();
    	argsMensajeLog[1] = indicador.getNombre();

    	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicio", argsMensajeLog) + "\n");
    	try
    	{
    		List<Plan> planesAsociados = new ArrayList<Plan>();

  			if (planId != null) 
  			{
  				Plan planAsociado = new PlanManager(pm, log, messageResources).Load(planId, stmExt);
  				if (planAsociado != null)
  					planesAsociados.add(planAsociado);
  			}
  			else
  				planesAsociados = new PlanManager(pm, log, messageResources).getPlanesAsociadosPorIndicador(indicador.getIndicadorId(), true, false, stmExt);

  			if (indicador.getFrecuencia() != null)
  			{
  	  			LapsoTiempo lapsoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, indicador.getFrecuencia().byteValue());
  	    		for (Iterator<Plan> iter = planesAsociados.iterator(); iter.hasNext(); ) 
  	    		{
  	    			Plan plan = (Plan)iter.next();
  	    			resultado = calcularEstadoIndicadorPlan(indicador, plan.getPlanId(), anoInicio, lapsoEnPeriodos.getPeriodoInicio().intValue(), lapsoEnPeriodos.getPeriodoFin().intValue(), stmExt);
  	    			if (resultado != 10000)
  	    				break;
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
		}
    	
	    return resultado;
    }
    
    private int calcularEstadoIndicadorPlan(Indicador indicador, Long planId, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
		String CLASS_METHOD = "CalculoManager.calcularEstadoIndicadorPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		int resultado = 10000;
		
    	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.limpiandoestados") + "\n");
    	for (int periodo = periodoInicio; periodo <= periodoFin; periodo++)
    	{
    		resultado = new IndicadorManager(pm, log, messageResources).deleteIndicadorEstados(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), new Integer(ano), new Integer(ano), new Integer(periodo), new Integer(periodo), stmExt);
    		resultado = new IndicadorManager(pm, log, messageResources).deleteIndicadorEstados(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), new Integer(ano), new Integer(ano), new Integer(periodo), new Integer(periodo), stmExt);
    	}

    	List<Medicion> mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicador.getCorte(), indicador.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), false, stmExt);

    	if ((indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue()) || (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue())) 
    		resultado = calcularEstadoIndicadorRetoAumentoDisminucion(indicador, planId, mediciones, ano, periodoInicio, periodoFin, stmExt);
    	else if ((indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) || (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()))
    	{
    		log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioanual") + "\n");
    		resultado = calcularEstadoIndicadorCondicionValorMaximoMinimo(indicador, planId, mediciones, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ano, periodoInicio, periodoFin, stmExt);
    		if (resultado == 10000)
    		{
    			log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioparcial") + "\n");
    			resultado = calcularEstadoIndicadorCondicionValorMaximoMinimo(indicador, planId, mediciones, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ano, periodoInicio, periodoFin, stmExt);
    		}
    	}
    	else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
    	{
    		log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioanual") + "\n");
    		resultado = calcularEstadoIndicadorCondicionBanda(indicador, planId, mediciones, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ano, periodoInicio, periodoFin, stmExt);
    		if (resultado == 10000)
    		{
    			log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioparcial") + "\n");
    			resultado = calcularEstadoIndicadorCondicionBanda(indicador, planId, mediciones, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ano, periodoInicio, periodoFin, stmExt);
    		}
    	}
	    
	    return resultado;
    }
  	
    private int calcularEstadoIndicadorRetoAumentoDisminucion(Indicador indicador, Long planId, List<Medicion> mediciones, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
    	int resultado = 10000;
    	Double valorInicial = null;
    	Meta metaValorInicial = new MetaManager(pm, log, messageResources).getValorInicial(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), indicador.getMesCierre(), new Integer(ano), stmExt);
    	if (metaValorInicial != null) 
    		valorInicial = metaValorInicial.getValor();

    	if (indicador.getValorInicialCero() != null && indicador.getValorInicialCero().booleanValue()) 
    		valorInicial = new Double(0.0D);

    	Byte tipoCargaMedicionMeta = indicador.getTipoCargaMedicion();
    	if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) 
    	{
		    IndicadorPlan indicadorPlan = new PlanManager(pm, log, messageResources).getIndicadorPlan(planId, indicador.getIndicadorId(), stmExt);
		    if (indicadorPlan != null && indicadorPlan.getTipoMedicion() != null)
		    	tipoCargaMedicionMeta = indicadorPlan.getTipoMedicion();
    	}

    	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioanual") + "\n");
    	resultado = calcularEstadoIndicadorRetoAumentoDisminucion(indicador, planId, mediciones, valorInicial, tipoCargaMedicionMeta, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ano, periodoInicio, periodoFin, stmExt);
    	if (resultado == 10000)
    	{
    		log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.inicioparcial") + "\n");
    		resultado = calcularEstadoIndicadorRetoAumentoDisminucion(indicador, planId, mediciones, valorInicial, tipoCargaMedicionMeta, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ano, periodoInicio, periodoFin, stmExt);
    	}
    	
    	return resultado;
    }
    
    private int calcularEstadoIndicadorRetoAumentoDisminucion(Indicador indicador, Long planId, List<Medicion> mediciones, Double valorInicial, Byte tipoCargaMedicionMeta, Byte tipoEstado, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
    	int indexMedicion = 0;
    	int resultado = 10000;

    	if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue()) 
    	{
    		if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue()) 
    		{
    			log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinvalorinicial") + "\n");
    			return resultado;
    		}
    		
    		if (mediciones.size() == 0)
    		{
    			log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmediciones") + "\n");
    			return resultado;
    		}
        
    		List<Meta> metasParciales = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), tipoCargaMedicionMeta, TipoMeta.getTipoMetaParcial(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), stmExt);
    		List<Medicion> medicionesParametro = getMedicionesParametroRetoAumentoDisminucion(indicador, ano, periodoInicio, periodoFin, stmExt);
        
    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
    		{
    			String[] argsMensajeLog = new String[2];
    			argsMensajeLog[0] = Integer.toString(periodo);
    			argsMensajeLog[1] = Integer.toString(ano);
    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
    			Meta metaParcial = (Meta)metasParciales.get(indexMedicion);
    			Double valorTotal = null;
    			Double valorParametro = null;
    			Double valorMetaParcial = null;
    			if (medicion.getValor() != null) 
    			{
    				valorTotal = medicion.getValor();
    				if (metaParcial.getValor() != null) 
    					valorMetaParcial = metaParcial.getValor();

    				valorParametro = getValorParametroRetoAumentoDisminucion(indicador, medicionesParametro, indexMedicion);
    			} 
    			else 
    				log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmedicion", argsMensajeLog) + "\n");
    		  
    			if (valorTotal != null) 
    			{
    				if (metaParcial.getValor() == null) 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmeta", argsMensajeLog) + "\n");
    				if (valorParametro == null) 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinparametro", argsMensajeLog) + "\n");
    			  
    				Double valorEstadoParcial = null;
    				if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue())
    					valorEstadoParcial = calcularEstadoParcialIndicadorRetoAumento(periodo, ano, valorTotal, valorMetaParcial, valorParametro);
    				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue()) 
    					valorEstadoParcial = calcularEstadoParcialIndicadorRetoDisminucion(periodo, ano, valorTotal, valorMetaParcial, valorInicial, valorParametro);
    				if (valorEstadoParcial != null) 
    					resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), new Integer(ano), new Integer(periodo), valorEstadoParcial, stmExt);
    				if (resultado != 10000)
    					break;
    			}
    		  
    			if ((indexMedicion + 1) > (mediciones.size() - 1))
    				break;
				indexMedicion++;
    		}
    	}
    	else 
    	{
    		int periodoHastaCierre = 0;
    		Byte mesCierre = indicador.getMesCierre();
    	  
    		if (mesCierre.byteValue() == 12)
    			periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
    		else 
    			periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), indicador.getFrecuencia().byteValue());

    		List<Meta> metasAnuales = new MetaManager(pm, log, messageResources).getMetasAnuales(indicador.getIndicadorId(), planId, new Integer(ano - 1), new Integer(ano), stmExt);
    		List<Medicion> medicionesParametro = getMedicionesParametroRetoAumentoDisminucion(indicador, ano, periodoInicio, periodoFin, stmExt);
    		Meta metaAnoAnterior = (Meta)metasAnuales.get(0);
    		Meta metaAno = (Meta)metasAnuales.get(1);
    		
    		if (mediciones.size() > 0)
    		{
	    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
	    		{
	    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
	
	    			Double valorEstadoAnual = null;
	    			if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue())
	    				valorEstadoAnual = calcularEstadoAnualIndicadorRetoAumento(medicion, metaAno, metaAnoAnterior, periodo, periodoHastaCierre, indicador.getMesCierre().intValue(), valorInicial, getValorParametroRetoAumentoDisminucion(indicador, medicionesParametro, indexMedicion));
	    			else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue()) 
	    				valorEstadoAnual = calcularEstadoAnualIndicadorRetoDisminucion(medicion, metaAno, periodo, valorInicial, getValorParametroRetoAumentoDisminucion(indicador, medicionesParametro, indexMedicion));
	          
	    			if (valorEstadoAnual != null) 
	    				resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), new Integer(ano), new Integer(periodo), valorEstadoAnual, stmExt);
					if (resultado != 10000)
						break;
					if ((indexMedicion + 1) > (mediciones.size() - 1))
						break;
					indexMedicion++;
	    		}
    		}
    	}
    	
    	return resultado;
    }
    
  	public int calcularAlertaIndicadorPorPlan(Indicador indicador, Long planId, Statement stmExt)
  	{
		String CLASS_METHOD = "CalculoManager.calcularAlertaIndicadorPorPlan";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		int resultado = 10000;
		
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		boolean transActiva = false;
	    
	    try
	    {
	    	if (stmExt != null) 
	    		stm = stmExt;
	    	else
	    	{
	    		cn = new ConnectionManager(pm).getConnection();
	    		ConexAbierta = true;
				transActiva = true;
	    		cn.setAutoCommit(false);
	    		stm = cn.createStatement();
	    	}
	    	
  			List<Plan> planesAsociados = new ArrayList<Plan>();

  			if (planId != null) 
  			{
  				Plan planAsociado = new PlanManager(pm, log, messageResources).Load(planId, stm);
  				if (planAsociado != null)
  					planesAsociados.add(planAsociado);
  			}
  			else
  				planesAsociados = new PlanManager(pm, log, messageResources).getPlanesAsociadosPorIndicador(indicador.getIndicadorId(), true, false, stm);

  			if (planesAsociados.size() > 0)
  			{
  				for (Iterator<Plan> iter = planesAsociados.iterator(); iter.hasNext(); ) 
  				{
  					Plan plan = (Plan)iter.next();
  					Byte alerta = calcularAlertaIndicadorPorPlan(indicador, plan, stm);
  					IndicadorPlan indicadorPlan = new PlanManager(pm, log, messageResources).getIndicadorPlan(plan.getPlanId(), indicador.getIndicadorId(), stm);
  					if (indicadorPlan == null)
  					{
  						IndicadorPlanPK indicadorpk = new IndicadorPlanPK();
  						indicadorpk.setIndicadorId(indicador.getIndicadorId());
  						indicadorpk.setPlanId(plan.getPlanId());
  						
  						indicadorPlan = new IndicadorPlan();
  						indicadorPlan.setPk(indicadorpk);
  					}
  					indicadorPlan.setCrecimiento(alerta);
  					
					resultado = new PlanManager(pm, log, messageResources).saveIndicadorPlan(indicadorPlan.getPk().getIndicadorId(), indicadorPlan.getPk().getPlanId(), indicadorPlan.getPeso(), indicadorPlan.getCrecimiento(), indicadorPlan.getTipoMedicion(), stm);
  					if (resultado != 10000)
  						break;
  				}
  			}
	    	if (transActiva) 
	    	{
	    		if (resultado == 10000 && stmExt == null)
					cn.commit();
	    		else if (stmExt == null)
					cn.rollback();
			    
	    		if (stmExt == null)
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
	    		transActiva = false;
			}

    		if (stmExt == null)
    		{
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
			try 
			{
				if (transActiva && stmExt == null)
					cn.setAutoCommit(true);
				if (ConexAbierta && stmExt == null)
				{
			    	cn.close(); 
			    	cn = null;
				}
			} 
			catch (Exception localException9) { }
		}
	    
	    return resultado;
  	}
    
  	private Byte calcularAlertaIndicadorPorPlan(Indicador indicador, Plan plan, Statement stmExt)
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();
  		
  		Medicion ultimaMedicion = new MedicionManager(pm, log, messageResources).getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), stmExt);

  		if ((ultimaMedicion != null) && (ultimaMedicion.getValor() != null)) 
  		{
  			Byte tipoCargaMedicion = indicador.getTipoCargaMedicion();
  			if ((indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) && (indicador.getValorInicialCero() != null && !indicador.getValorInicialCero().booleanValue()) && (indicador.getTipoFuncion().byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()))
 				tipoCargaMedicion = TipoMedicion.getTipoMedicionEnPeriodo();

  			List<Meta> metasParciales = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), plan.getPlanId(), indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), tipoCargaMedicion, TipoMeta.getTipoMetaParcial(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo(), ultimaMedicion.getMedicionId().getPeriodo(), stmExt);
  			if (metasParciales.size() > 0) 
  			{
  				Meta metaParcial = (Meta)metasParciales.get(0);
  				if (metaParcial.getValor() != null) 
  					alerta = calcularAlertaIndicadorPorPlan(indicador, plan, tipoCargaMedicion, metaParcial, ultimaMedicion, stmExt);
  			}
  		}

  		return alerta;
  	}
  	
  	public Byte calcularAlertaIndicadorPorPlan(Indicador indicador, Plan plan, Byte tipoCargaMedicion, Meta metaParcial, Medicion medicion, Statement stmExt) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue()) 
  		{
  			Double metaSuperior = metaParcial.getValor();
  			List<Meta> metasParciales = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), plan.getPlanId(), indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), tipoCargaMedicion, TipoMeta.getTipoMetaParcialInferior(), medicion.getMedicionId().getAno(), medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), medicion.getMedicionId().getPeriodo(), stmExt);
  			if (metasParciales.size() > 0) 
  			{
  				metaParcial = (Meta)metasParciales.get(0);
  				if (metaParcial.getValor() != null) 
  				{
  					Double metaInferior = metaParcial.getValor();
  					Double porcentajeAlertaZonaAmarilla = null;
  					if (indicador.getAlertaMinMax() != null) 
  						porcentajeAlertaZonaAmarilla = new Double(indicador.getAlertaMinMax().doubleValue());
  					if (porcentajeAlertaZonaAmarilla == null) 
  					{
  						OrganizacionStrategos organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  						if (organizacion.getPorcentajeZonaAmarillaMinMaxIndicadores() != null) 
  							porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMinMaxIndicadores().doubleValue());
  					}
  					if (porcentajeAlertaZonaAmarilla != null)
  						alerta = calcularAlertaIndicadorPorCondicionBandas(medicion.getValor(), porcentajeAlertaZonaAmarilla, metaSuperior, metaInferior);
  				}
  			}
  		}
  		else
  		{
  			Double porcentajeAlertaZonaVerde = null;
  			Double porcentajeAlertaZonaAmarilla = null;
  			
  			if ((indicador.getAlertaMetaZonaVerde() == null) && (indicador.getAlertaMetaZonaAmarilla() == null) && (indicador.getAlertaTipoZonaVerde() != null && indicador.getAlertaTipoZonaVerde().equals(TipoAlerta.getTipoAlertaPorcentaje())) && (indicador.getAlertaTipoZonaAmarilla() != null && indicador.getAlertaTipoZonaAmarilla().equals(TipoAlerta.getTipoAlertaPorcentaje())))
  			{
  				OrganizacionStrategos organizacion = new OrganizacionManager(pm, log, messageResources).Load(indicador.getOrganizacionId(), stmExt);
  	  			if (organizacion.getPorcentajeZonaVerdeMetaIndicadores() != null)
  	  				porcentajeAlertaZonaVerde = new Double(organizacion.getPorcentajeZonaVerdeMetaIndicadores().doubleValue());
  	  			if (organizacion.getPorcentajeZonaAmarillaMetaIndicadores() != null)
  	  				porcentajeAlertaZonaAmarilla = new Double(organizacion.getPorcentajeZonaAmarillaMetaIndicadores().doubleValue());
  			}
  			alerta = calcularAlertaIndicadorPorProgramadoMeta(indicador, medicion, ((metaParcial != null && metaParcial.getValor() != null) ? metaParcial.getValor().doubleValue() : null), null, porcentajeAlertaZonaVerde, porcentajeAlertaZonaAmarilla, stmExt);
  		}

  		return alerta;
  	}
  	
  	private Byte calcularAlertaIndicadorPorCondicionBandas(Double ejecutado, Double porcentajeAlertaZonaAmarilla, Double metaSuperior, Double metaInferior) 
  	{
  		Byte alerta = AlertaIndicador.getAlertaNoDefinible();

  		if (metaSuperior != null && metaInferior != null)
  		{
  			Double limiteZonaAmarillaSuperior = metaSuperior + MathUtil.sign(metaSuperior) * ((metaSuperior - metaInferior) / 2.0D * (porcentajeAlertaZonaAmarilla / 100.0D));
  	  		Double limiteZonaAmarillaInferior = metaInferior - MathUtil.sign(metaInferior) * ((metaSuperior - metaInferior) / 2.0D * (porcentajeAlertaZonaAmarilla / 100.0D));

  	  		if ((ejecutado >= metaInferior) && (ejecutado <= metaSuperior))
  	  			alerta = AlertaIndicador.getAlertaVerde();
  	  		else if ((ejecutado >= metaSuperior) && (ejecutado < limiteZonaAmarillaSuperior))
  	  			alerta = AlertaIndicador.getAlertaAmarilla();
  	  		else if ((ejecutado <= metaInferior) && (ejecutado > limiteZonaAmarillaInferior))
  	  			alerta = AlertaIndicador.getAlertaAmarilla();
  	  		else 
  	  			alerta = AlertaIndicador.getAlertaRoja();
  		}

  		return alerta;
  	}
  	
  	public StringBuffer calcularEstadoObjetivos(Long planId, Long organizacionId, Long perspectivaId, int ano, byte mesInicio, byte mesFinal, Statement stmExt)
  	{
  		StringBuffer log = new StringBuffer(); 
  		String[] argsReemplazo = new String[4];
  		argsReemplazo[0] = Byte.toString(mesInicio);
  		argsReemplazo[1] = Integer.toString(ano);
  		argsReemplazo[2] = Byte.toString(mesFinal);
  		argsReemplazo[3] = Integer.toString(ano);

  		log.append("\n" + this.messageResources.getResource("calcularindicadores.titulocalculo", argsReemplazo) + "\n");

  		Calendar ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append(this.messageResources.getResource("calcularindicadores.fechainiciocalculo", argsReemplazo) + "\n\n");

  		ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append("\n" + this.messageResources.getResource("calcularindicadores.fechafincalculo", argsReemplazo) + "\n\n");

		if (planId != null)
		{
			List<LapsoTiempo> lapsosTiempoEnPeriodos = PeriodoUtil.getLapsosTiempoEnPeriodosPorFrecuenciaMes(ano, ano, mesInicio, mesFinal);

			//Inició cálculo de nivel de logro de los objetivos
			log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.objetivos") + "\n");
			calcularEstadoObjetivos(planId, organizacionId, perspectivaId, ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stmExt);
		}
  		
  		return log;
  	}
  	
  	public StringBuffer calcularIniciativas(List<Iniciativa> iniciativas, Long planId, Long organizacionId, Long perspectivaId, boolean reportarIniciativas, boolean reportarErrores, Usuario usuario, Statement stmExt) throws SQLException 
	{
  		StringBuffer log = new StringBuffer(); 
  		String[] argsReemplazo = new String[4];

  		Calendar ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append(this.messageResources.getResource("calcularindicadores.fechainiciocalculo", argsReemplazo) + "\n\n");

  		long num = 0L;
  		int res = 10000;
  		for (Iterator<Iniciativa> i = iniciativas.iterator(); i.hasNext(); )
  		{
  			Iniciativa iniciativa = (Iniciativa)i.next();

    		num ++;
    		if (this.logConsolaDetallado)
    			System.out.println("Iniciativa calculado numero: " + num);
  			
  			res = new IniciativaManager(pm, log, messageResources).actualizarDatosIniciativa(iniciativa.getIniciativaId(), iniciativa.getIndicador().getAlerta(), iniciativa.getIndicador().getUltimaMedicion(), iniciativa.getIndicador().getFechaUltimaMedicion(), stmExt);

  			if (res == 10000) 
  			{
  				if (reportarIniciativas)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.success"));
  			}
  			else if (res == 10006) 
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorpadrenoencontrado") + "\n");
  			}
  			else if (res == 10001)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorinsumosnoencontrados") + "\n");
  			}
  			else if (res == 10003)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorinsumosinmedicion") + "\n");
  			}
  			else if (res == 10004)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorvalornonumerico") + "\n");
  			}
  			else if (res == 10005)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorformula") + "\n");
  			}
  			else if (reportarErrores) 
  				log.append("* - " + this.messageResources.getResource("calcularindicadores.error") + "\n");

  			log.append("\n");
  		}

  		ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append("\n" + this.messageResources.getResource("calcularindicadores.fechafincalculo", argsReemplazo) + "\n\n");

  		return log;
	}
  	
  	public StringBuffer calcularMedicionesIndicadores(List<Indicador> indicadores, Long planId, Long organizacionId, Long perspectivaId, int ano, byte mesInicio, byte mesFinal, boolean tomarPeriodosNulosComoCero, boolean reportarIndicadores, boolean reportarErrores, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException 
	{
  		StringBuffer log = new StringBuffer(); 
  		String[] argsReemplazo = new String[4];
  		argsReemplazo[0] = Byte.toString(mesInicio);
  		argsReemplazo[1] = Integer.toString(ano);
  		argsReemplazo[2] = Byte.toString(mesFinal);
  		argsReemplazo[3] = Integer.toString(ano);

  		log.append("\n" + this.messageResources.getResource("calcularindicadores.titulocalculo", argsReemplazo) + "\n");

  		Calendar ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append(this.messageResources.getResource("calcularindicadores.fechainiciocalculo", argsReemplazo) + "\n\n");

  		Map<Long, Long> idsIndicadoresCalculados = new HashMap<Long, Long>();

  		long num = 0L;
  		String codigoEnlace = "";
  		int res = 10000;
  		for (Iterator<Indicador> i = indicadores.iterator(); i.hasNext(); )
  		{
  			Indicador indicador = (Indicador)i.next();

    		num ++;
    		if (this.logConsolaDetallado)
    			System.out.println("Indicador calculado numero: " + num);
  			
  			res = calcularMedicionesIndicador(indicador, planId, ano, ano, mesInicio, mesFinal, indicador.getOrganizacion().getMesCierre().byteValue(), tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);

  			if (indicador.getCodigoEnlace() == null)
  				codigoEnlace = "No Existe, Código de Enlace";
  			else
  				codigoEnlace = indicador.getCodigoEnlace();
  			log.append("id: " + indicador.getIndicadorId() + " - " + indicador.getNombre() + ": '" + codigoEnlace + "' ");

  			if (res == 10000) 
  			{
  				if (reportarIndicadores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.success"));
  			}
  			else if (res == 10006) 
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorpadrenoencontrado") + "\n");
  			}
  			else if (res == 10001)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorinsumosnoencontrados") + "\n");
  			}
  			else if (res == 10003)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorinsumosinmedicion") + "\n");
  			}
  			else if (res == 10004)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorvalornonumerico") + "\n");
  			}
  			else if (res == 10005)
  			{
  				if (reportarErrores)
  					log.append("* - " + this.messageResources.getResource("calcularindicadores.errorformula") + "\n");
  			}
  			else if (reportarErrores) 
  				log.append("* - " + this.messageResources.getResource("calcularindicadores.error") + "\n");

  			log.append("\n");
  		}

  		if ((indicadores != null) && (indicadores.size() > 0))
  		{
  	  		argsReemplazo[0] = "" + indicadores.size();
  	  		argsReemplazo[1] = "";
  	  		argsReemplazo[2] = "";
  	  		argsReemplazo[3] = "";
  			log.append("* - " + this.messageResources.getResource("calcularindicadores.actualizar.ultima.medicion", argsReemplazo) + "\n");
  			res = new MedicionManager(pm, log, messageResources).actualizarUltimaMedicionIndicadores(indicadores, null, stmExt);
  			
  			if (res == 10000)
  			{
  				num = 0L;
  				int totalIndicadores = indicadores.size();
  				for (Iterator<Indicador> i = indicadores.iterator(); i.hasNext(); ) 
	  			{
	  				Indicador indicador = (Indicador)i.next();
	  				
	  				num ++;
	  	  	  		argsReemplazo[0] = "" + num;
	  	  	  		argsReemplazo[1] = "" + totalIndicadores;
	  	  	  		argsReemplazo[2] = "";
	  	  	  		argsReemplazo[3] = "";
	  	  			log.append("* - " + this.messageResources.getResource("calcularindicadores.actualizar.alerta.plan", argsReemplazo) + "\n");
	  				res = calcularAlertaIndicadorPorPlan(indicador, planId, stmExt);
	  	  			if (res == 10000)
	  	  			{
	  	    	  		argsReemplazo[0] = "" + num;
	  	    	  		argsReemplazo[1] = "" + totalIndicadores;
	  	    	  		argsReemplazo[2] = "";
	  	    	  		argsReemplazo[3] = "";
	  	    			log.append("* - " + this.messageResources.getResource("calcularindicadores.actualizar.estado.indicador", argsReemplazo) + "\n");
	  	    			
	  	  				res = iniciarCalcularEstadoIndicadorPlan(indicador, planId, ano, ano, mesInicio, mesFinal, indicador.getOrganizacion().getMesCierre().byteValue(), stmExt);
	  	  			}
	  	  			else
	  	  				break;
	  			}
	  			
	  			if (res == 10000 && planId != null)
	  			{
	  				List<LapsoTiempo> lapsosTiempoEnPeriodos = PeriodoUtil.getLapsosTiempoEnPeriodosPorFrecuenciaMes(ano, ano, mesInicio, mesFinal);

	  				//Inició cálculo de nivel de logro de los objetivos
	  				log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.objetivos") + "\n");
	  				res = calcularEstadoObjetivos(planId, organizacionId, perspectivaId, ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stmExt);
	  			}
  			}
  		}

  		ahora = Calendar.getInstance();
  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

  		log.append("\n" + this.messageResources.getResource("calcularindicadores.fechafincalculo", argsReemplazo) + "\n\n");

  		return log;
	}
  	
    private int calcularEstadoObjetivos(Long planId, Long organizacionId, Long perspectivaId, int ano, List<LapsoTiempo> lapsosTiempoEnPeriodos, byte mesInicio, byte mesFinal, Statement stmExt)
    {
		String CLASS_METHOD = "CalculoManager.calcularEstadoObjetivos";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		int resultado = 10000;
		
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		boolean transActiva = false;
	    
    	try
    	{
	    	if (stmExt != null) 
	    		stm = stmExt;
	    	else
	    	{
	    		cn = new ConnectionManager(pm).getConnection();
	    		ConexAbierta = true;
				transActiva = true;
	    		cn.setAutoCommit(false);
	    		stm = cn.createStatement();
	    	}

    		Map<String, Object> filtros = new HashMap<String, Object>();

    		if (planId != null)
    			filtros.put("plan_id", planId.toString());
    		else if (organizacionId != null) 
    			filtros.put("organizacion_Id", organizacionId.toString());

    		List<Plan> planes = new PlanManager(pm, log, messageResources).getPlanes(0, 0, null, null, false, filtros, stm);

    		filtros = new HashMap<String, Object>();

    		if (perspectivaId != null) 
    			filtros.put("perspectiva_id", perspectivaId);
    		else 
    		{
    			List<Long> planIds = new ArrayList<Long>();

    			for (Iterator<Plan> iter = planes.iterator(); iter.hasNext(); ) 
    			{
    				Plan plan = (Plan)iter.next();
    				planIds.add(plan.getPlanId());
    			}
    			
    			if (planIds.size() > 0) 
    				filtros.put("plan_id", planIds);

    			filtros.put("padre_Id", null);
    		}

    		List<Perspectiva> perspectivas = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(null, null, filtros, stm);

    		for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext(); ) 
    		{
    			Perspectiva perspectiva = (Perspectiva)iter.next();

    			resultado = calcularEstadoPerspectivasObjetivosHijos(perspectiva, ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stm);
    			
    			if (resultado != 10000)
    				break;
    		}
    		
    		if (resultado == 10000) 
    		{
    			for (Iterator<Plan> iter = planes.iterator(); iter.hasNext(); ) 
    			{
    				Plan plan = (Plan)iter.next();

    				filtros = new HashMap<String, Object>();
    				filtros.put("plan_id", plan.getPlanId());
    				filtros.put("padre_Id", null);
    				
    				perspectivas = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(null, null, filtros, stm);
    				
    	    		for (Iterator<Perspectiva> iterPerspectiva = perspectivas.iterator(); iterPerspectiva.hasNext(); ) 
    	    		{
    	    			Perspectiva perspectiva = (Perspectiva)iterPerspectiva.next();
    	    			
        				resultado = calcularEstadoPlan(plan, perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ano, lapsosTiempoEnPeriodos, stm);
        				resultado = calcularEstadoPlan(plan, perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ano, lapsosTiempoEnPeriodos, stm);
        				
        		    	new PlanManager(pm, log, messageResources).actualizarPlanUltimoEstado(plan.getPlanId(), stm);
        		    	new PlanManager(pm, log, messageResources).actualizarPlanAlerta(plan, stm);
        				
        				if (resultado != 10000)
        					break;
    	    		}
    				
    				if (resultado != 10000)
    					break;
    			}
    		}
        
	    	if (transActiva) 
	    	{
	    		if (resultado == 10000 && stmExt == null)
					cn.commit();
	    		else if (stmExt == null)
					cn.rollback();
			    
	    		if (stmExt == null)
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
				catch (SQLException e1) 
				{
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
				}
	    		transActiva = false;
			}

    		if (stmExt == null)
    		{
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
				if (ConexAbierta && stmExt == null)
				{
			    	cn.close(); 
			    	cn = null;
				}
			} 
			catch (Exception localException9) { }
		}
    	
    	return resultado;
    }
    
    private int calcularEstadoPerspectivasObjetivosHijos(Perspectiva perspectiva, int ano, List<LapsoTiempo> lapsosTiempoEnPeriodos, byte mesInicio, byte mesFinal, Statement stmExt)
    {
    	Map<String, Object> filtros = new HashMap<String, Object>();
    	int resultado = 10000;

    	filtros.put("padre_Id", perspectiva.getPerspectivaId().toString());
    	List<Perspectiva> perspectivasHijas = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(null, null, filtros, stmExt);

    	for (Iterator<Perspectiva> iter = perspectivasHijas.iterator(); iter.hasNext(); ) 
    	{
    		Perspectiva perspectivaHija = (Perspectiva)iter.next();
    		resultado = calcularEstadoPerspectivasObjetivosHijos(perspectivaHija, ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stmExt);
    		if (resultado != 10000)
    			break;
    	}
    	
    	if (resultado == 10000)
    	{
			resultado = calcularEstadoPerspectivaObjetivo(perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stmExt);
    		if (resultado == 10000)
				resultado = calcularEstadoPerspectivaObjetivo(perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), ano, lapsosTiempoEnPeriodos, mesInicio, mesFinal, stmExt);
    		
    		if (resultado == 10000)
    			resultado = new PerspectivaManager(this.pm, this.log, this.messageResources).actualizarPerspectivaUltimoEstado(perspectiva.getPerspectivaId(), stmExt);
    		if (resultado == 10000)
    			resultado = new PerspectivaManager(this.pm, this.log, this.messageResources).actualizarPerspectivaCrecimiento(perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), stmExt);
    		if (resultado == 10000)
    			resultado = new PerspectivaManager(this.pm, this.log, this.messageResources).actualizarPerspectivaCrecimiento(perspectiva, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), stmExt);
    	}

    	return resultado;
    }
  	
    private int calcularEstadoPerspectivaObjetivo(Perspectiva perspectiva, Byte tipoEstado, int ano, List<LapsoTiempo> lapsosTiempoEnPeriodos, byte mesInicio, byte mesFinal, Statement stmExt)
    {
    	int resultado = 10000; 
    	
    	Map<String, Object> filtros = new HashMap<String, Object>();
		List<Long> indicadores = new ArrayList<Long>();

    	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
    	{
        	filtros = new HashMap<String, Object>();
        	filtros.put("padre_Id", perspectiva.getPerspectivaId().toString());
        	List<Perspectiva> perspectivasHijas = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(null, null, filtros, stmExt);
        	for (Iterator<Perspectiva> iter = perspectivasHijas.iterator(); iter.hasNext(); ) 
        	{
        		Perspectiva perspectivaHija = (Perspectiva)iter.next();
        		if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
        			indicadores.add(perspectivaHija.getNlParIndicadorId());
        		else
        			indicadores.add(perspectivaHija.getNlAnoIndicadorId());
        	}
        	
        	filtros = new HashMap<String, Object>();
        	if (indicadores.size() > 0)
        		filtros.put("indicador_Id", indicadores);
        	else
        	{
        		log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.perspectiva.sinindicadores") + "\n");
        		return resultado;
        	}
    	}
    	
    	filtros.put("perspectiva_id", perspectiva.getPerspectivaId());
    	filtros.put("noCualitativos", "true");
    	filtros.put("guia", false);
    	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual())) 
    		filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
    	else 
    		filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
    	
    	List<Indicador> indicadoresAsociados = new IndicadorManager(this.pm, this.log, this.messageResources).getIndicadores(filtros, stmExt);
    	List<Long> indicadoresIds = new ArrayList<Long>();

    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
    	{
    		Indicador indicador = (Indicador)iter.next();
    		indicadoresIds.add(indicador.getIndicadorId());
    	}

    	List<Medicion> ultimasMediciones = new MedicionManager(this.pm, this.log, this.messageResources).getUltimasMedicionesIndicadores(indicadoresIds, SerieTiempo.getSerieRealId(), stmExt);

    	Integer anoBorrado = null;
    	Integer periodoBorrado = null;

    	if (ultimasMediciones.size() > 0) 
    	{
    		Medicion medicion = (Medicion)ultimasMediciones.get(0);
    		anoBorrado = medicion.getMedicionId().getAno();
    		periodoBorrado = new Integer(medicion.getMedicionId().getPeriodo().intValue());
    	}

    	resultado = new PerspectivaManager(this.pm, this.log, this.messageResources).deletePerspectivaEstados(perspectiva.getPerspectivaId(), tipoEstado, anoBorrado, null, periodoBorrado, null, stmExt);
    	List<IndicadorPerspectiva> indicadoresPerspectiva = new PerspectivaManager(this.pm, this.log, this.messageResources).getIndicadoresPerspectiva(perspectiva.getPerspectivaId(), stmExt);

    	Double pesoTotal = null;
    	Double pesoRestante = null;
    	int indicadoresSinPeso = 0;

    	for (Iterator<IndicadorPerspectiva> iter = indicadoresPerspectiva.iterator(); iter.hasNext(); ) 
    	{
    		IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)iter.next();
    		for (Iterator<Indicador> iterIndicador = indicadoresAsociados.iterator(); iterIndicador.hasNext(); ) 
        	{
        		Indicador indicador = (Indicador)iterIndicador.next();
        		if (indicador.getIndicadorId().longValue() == indicadorPerspectiva.getPk().getIndicadorId().longValue())
        		{
            		if (indicadorPerspectiva.getPeso() != null) 
            		{
            			if (pesoTotal == null)
            				pesoTotal = new Double(indicadorPerspectiva.getPeso().doubleValue());
            			else
            				pesoTotal = new Double(pesoTotal.doubleValue() + indicadorPerspectiva.getPeso().doubleValue());
            		}
            		else 
            			indicadoresSinPeso++;
        		}
    		}
    	}

    	if (pesoTotal != null) 
    	{
    		pesoTotal = new Double(pesoTotal.doubleValue() / 2.0D);
    		pesoRestante = new Double(100.0D - pesoTotal.doubleValue());
    	}

    	if ((pesoRestante != null) && (pesoRestante.doubleValue() != new Double(0.0D).doubleValue()) && (indicadoresSinPeso > 0)) 
    		pesoRestante = new Double(pesoRestante.doubleValue() / indicadoresSinPeso);

    	filtros = new HashMap<String, Object>();

    	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual())) 
    	{
    		filtros.put("perspectiva_id", perspectiva.getPerspectivaId());
    		filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
    		filtros.put("noCualitativos", "true");
    	}
    	else 
    	{
        	filtros.put("perspectiva_id", perspectiva.getPerspectivaId());
        	filtros.put("noCualitativos", "true");

        	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual())) 
        		filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
        	else 
        		filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
    	} 

    	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaAutomatico()))
    		filtros.put("indicador_Id", indicadores);

    	filtros.put("guia", false);
    	indicadoresAsociados = new IndicadorManager(this.pm, this.log, this.messageResources).getIndicadores(filtros, stmExt);
    	int total = indicadoresAsociados.size(); 
    	
    	filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionSeguimiento());
    	//int totalIndicaresSeguimiento = new IndicadorManager(this.pm, this.log, this.messageResources).getIndicadores(filtros, stmExt).size();

    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
    	{
    		Indicador indicador = (Indicador)iter.next();
    		IndicadorPerspectiva indicadorPerspectiva = new PerspectivaManager(this.pm, this.log, this.messageResources).LoadIndicadorPerspectiva(perspectiva.getPerspectivaId(), indicador.getIndicadorId(), stmExt);
    		if (indicadorPerspectiva != null && indicadorPerspectiva.getPeso() != null) 
    			indicador.setPeso(indicadorPerspectiva.getPeso());
    		else if (total != 0)
    			indicador.setPeso(new Double(new Double(100.0D) / total));
    	}

    	int periodoMaximoPerspectivaObjetivo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(perspectiva.getFrecuencia().byteValue(), ano);
    	List<PerspectivaEstado> perspectivaEstados = new ArrayList<PerspectivaEstado>();
      
    	for (int index = 1; index <= periodoMaximoPerspectivaObjetivo; index++) 
    	{
    		PerspectivaEstado perspectivaEstado = new PerspectivaEstado();

    		perspectivaEstado.setPk(new PerspectivaEstadoPK());
    		perspectivaEstado.getPk().setPerspectivaId(perspectiva.getPerspectivaId());
    		perspectivaEstado.getPk().setTipo(tipoEstado);
    		perspectivaEstado.getPk().setAno(new Integer(ano));
    		perspectivaEstado.getPk().setPeriodo(new Integer(index));
    		perspectivaEstado.setTotal(new Integer(0));
    		perspectivaEstados.add(perspectivaEstado);
    	}

    	if (perspectiva.getTipoCalculo().equals(TipoCalculoPerspectiva.getTipoCalculoPerspectivaManual()) && indicadoresAsociados.size() == 0)
    	{
    		filtros = new HashMap<String, Object>();
			filtros.put("padre_Id", perspectiva.getPerspectivaId());

    		List<Perspectiva> perspectivasHijas = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(null, null, filtros, stmExt);
    		for (Iterator<Perspectiva> iter = perspectivasHijas.iterator(); iter.hasNext(); )
    		{
    			Perspectiva perspectivaHija = (Perspectiva)iter.next();
    			
    			Long indicadorId = 0L;
    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    				indicadorId = perspectivaHija.getNlParIndicadorId();
    			else
    				indicadorId = perspectivaHija.getNlAnoIndicadorId();
    				
    			List<Medicion> aportesIndicador = getAportesIndicadorEstadoPerspectivaObjetivo(perspectivaHija.getPlanId(), indicadorId, perspectivaHija.getFrecuencia(), perspectivaHija.getFrecuencia(), perspectivaEstados, ano, tipoEstado, true, stmExt);

	    		int indexEstado = 0;
	    		for (Iterator<Medicion> iterAportes = aportesIndicador.iterator(); iterAportes.hasNext(); ) 
	    		{
	    			Medicion aporte = (Medicion)iterAportes.next();
	    			PerspectivaEstado perspectivaEstado = (PerspectivaEstado)perspectivaEstados.get(indexEstado);
	    			if (aporte.getValor() != null) 
	    			{
	    				Double valor = aporte.getValor();
	    				if (valor > 100D)
	    					valor = 100D;
	    				if (perspectivaEstado.getEstado() == null) 
	    				{
	    					if (pesoRestante != null)  
	    						perspectivaEstado.setEstado(new Double((valor.doubleValue() * pesoRestante.doubleValue()) / 100.0D));
	    					else if (pesoTotal == null) 
	    						perspectivaEstado.setEstado(new Double(valor.doubleValue()));
	
	    					perspectivaEstado.setTotalConValor(new Integer(1));
	    				} 
	    				else 
	    				{
	    					if (pesoRestante != null)
	    						perspectivaEstado.setEstado(new Double(perspectivaEstado.getEstado().doubleValue() + ((valor.doubleValue() * pesoRestante.doubleValue()) / 100.0D)));
	    					else if (pesoTotal == null) 
	    						perspectivaEstado.setEstado(new Double(perspectivaEstado.getEstado().doubleValue() + valor.doubleValue()));
	
	    					perspectivaEstado.setTotalConValor(new Integer(perspectivaEstado.getTotalConValor().intValue() + 1));
	    				}
	    			}
	          
	    			perspectivaEstado.setTotal(new Integer(perspectivaEstado.getTotal().intValue() + 1));
	    			indexEstado++;
	    		}
    		}
    		
    		if (perspectivasHijas.size() > 0)
    		{
            	for (Iterator<PerspectivaEstado> iterEstados = perspectivaEstados.iterator(); iterEstados.hasNext(); ) 
            	{
            		PerspectivaEstado perspectivaEstado = (PerspectivaEstado)iterEstados.next();
            		if (perspectivaEstado.getEstado() != null && perspectivaEstado.getEstado() != 0D)
            			perspectivaEstado.setEstado(perspectivaEstado.getEstado() / perspectivasHijas.size());
            	}
    		}
    	}
    	else
    	{
	    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
	    	{
	    		Indicador indicador = (Indicador)iter.next();
	
	    		List<Medicion> aportesIndicador = null;
	    		if (indicador.getTipoFuncion().byteValue() != TipoFuncionIndicador.getTipoFuncionPerspectiva().byteValue())
	    			aportesIndicador = getAportesIndicadorEstadoPerspectivaObjetivo(perspectiva.getPlanId(), indicador.getIndicadorId(), indicador.getFrecuencia(), perspectiva.getFrecuencia(), perspectivaEstados, ano, tipoEstado, false, stmExt);
	    		else 
	    			aportesIndicador = getAportesIndicadorEstadoPerspectivaObjetivo(perspectiva.getPlanId(), indicador.getIndicadorId(), indicador.getFrecuencia(), perspectiva.getFrecuencia(), perspectivaEstados, ano, tipoEstado, true, stmExt);
	
	    		int indexEstado = 0;
	    		for (Iterator<Medicion> iterAportes = aportesIndicador.iterator(); iterAportes.hasNext(); ) 
	    		{
	    			Medicion aporte = (Medicion)iterAportes.next();
	    			PerspectivaEstado perspectivaEstado = (PerspectivaEstado)perspectivaEstados.get(indexEstado);
	    			if (aporte.getValor() != null) 
	    			{
	    				Double valor = aporte.getValor();
	    				if (valor > 100D)
	    					valor = 100D;
	    				if (perspectivaEstado.getEstado() == null) 
	    				{
	    					if (indicador.getPeso() != null)
    							perspectivaEstado.setEstado(new Double((valor.doubleValue() * indicador.getPeso().doubleValue()) / 100.0D));
	    					else if (pesoRestante != null)  
	    						perspectivaEstado.setEstado(new Double((valor.doubleValue() * pesoRestante.doubleValue()) / 100.0D));
	    					else if (pesoTotal == null) 
	    						perspectivaEstado.setEstado(new Double(valor.doubleValue()));
	
	    					perspectivaEstado.setTotalConValor(new Integer(1));
	    				} 
	    				else 
	    				{
	    					if (indicador.getPeso() != null)
    							perspectivaEstado.setEstado(new Double(perspectivaEstado.getEstado().doubleValue() + ((valor.doubleValue() * indicador.getPeso().doubleValue()) / 100.0D)));
	    					else if (pesoRestante != null)
	    						perspectivaEstado.setEstado(new Double(perspectivaEstado.getEstado().doubleValue() + ((valor.doubleValue() * pesoRestante.doubleValue()) / 100.0D)));
	    					else if (pesoTotal == null) 
	    						perspectivaEstado.setEstado(new Double(perspectivaEstado.getEstado().doubleValue() + valor.doubleValue()));
	
	    					perspectivaEstado.setTotalConValor(new Integer(perspectivaEstado.getTotalConValor().intValue() + 1));
	    				}
	    			}
	          
	    			perspectivaEstado.setTotal(new Integer(perspectivaEstado.getTotal().intValue() + 1));
	          
	    			indexEstado++;
	    		}
	    	}
    	}

    	int periodoInicio = getLapsoTiempoEnPeriodosPorFrecuencia(lapsosTiempoEnPeriodos, perspectiva.getFrecuencia().byteValue()).getPeriodoInicio().intValue();
    	int periodoFin = getLapsoTiempoEnPeriodosPorFrecuencia(lapsosTiempoEnPeriodos, perspectiva.getFrecuencia().byteValue()).getPeriodoFin().intValue();
    	
    	for (Iterator<PerspectivaEstado> iterEstados = perspectivaEstados.iterator(); iterEstados.hasNext(); ) 
    	{
    		PerspectivaEstado perspectivaEstado = (PerspectivaEstado)iterEstados.next();

    		if ((perspectivaEstado.getPk().getAno().intValue() == ano) && (perspectivaEstado.getPk().getPeriodo().intValue() >= periodoInicio) && (perspectivaEstado.getPk().getPeriodo().intValue() <= periodoFin)) 
    		{
    			new PerspectivaManager(this.pm, this.log, this.messageResources).savePerspectivaEstado(perspectivaEstado.getPk().getPerspectivaId(), tipoEstado, perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getPeriodo(), perspectivaEstado.getEstado(), stmExt);

    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    				new MedicionManager(this.pm, this.log, this.messageResources).deleteMediciones(perspectiva.getNlParIndicadorId(), SerieTiempo.getSerieRealId(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getPeriodo(), perspectivaEstado.getPk().getPeriodo(), stmExt);
    			else 
    				new MedicionManager(this.pm, this.log, this.messageResources).deleteMediciones(perspectiva.getNlAnoIndicadorId(), SerieTiempo.getSerieRealId(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getPeriodo(), perspectivaEstado.getPk().getPeriodo(), stmExt);

    			if (perspectivaEstado.getEstado() != null) 
    			{
    				Medicion medicion = new Medicion();
    				medicion.setMedicionId(new MedicionPK());
    				if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    					medicion.getMedicionId().setIndicadorId(perspectiva.getNlParIndicadorId());
    				else 
    					medicion.getMedicionId().setIndicadorId(perspectiva.getNlAnoIndicadorId());

    				medicion.getMedicionId().setAno(perspectivaEstado.getPk().getAno());
    				medicion.getMedicionId().setPeriodo(perspectivaEstado.getPk().getPeriodo());
    				medicion.getMedicionId().setSerieId(SerieTiempo.getSerieRealId());
    				medicion.setValor(perspectivaEstado.getEstado());
    				medicion.setProtegido(new Boolean(false));

    				new MedicionManager(this.pm, this.log, this.messageResources).saveMedicion(medicion, stmExt);
    			}
          
    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    				new MetaManager(this.pm, this.log, this.messageResources).deleteMetas(perspectiva.getNlParIndicadorId(), perspectiva.getPlanId(), TipoMeta.getTipoMetaParcial(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getPeriodo(), perspectivaEstado.getPk().getPeriodo(), SerieTiempo.getSerieMetaId(), stmExt);
    			else 
    				new MetaManager(this.pm, this.log, this.messageResources).deleteMetas(perspectiva.getNlAnoIndicadorId(), perspectiva.getPlanId(), TipoMeta.getTipoMetaParcial(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getAno(), perspectivaEstado.getPk().getPeriodo(), perspectivaEstado.getPk().getPeriodo(), SerieTiempo.getSerieMetaId(), stmExt);
    			
    			if (perspectivaEstado.getEstado() != null) 
    			{
    				Meta meta = new Meta();
    				meta.setMetaId(new MetaPK());

    				if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    					meta.getMetaId().setIndicadorId(perspectiva.getNlParIndicadorId());
    				else 
    					meta.getMetaId().setIndicadorId(perspectiva.getNlAnoIndicadorId());

    				meta.getMetaId().setPlanId(perspectiva.getPlanId());
    				meta.getMetaId().setTipo(TipoMeta.getTipoMetaParcial());
    				meta.getMetaId().setSerieId(SerieTiempo.getSerieMetaId());
    				meta.getMetaId().setAno(perspectivaEstado.getPk().getAno());
    				meta.getMetaId().setPeriodo(perspectivaEstado.getPk().getPeriodo());
    				meta.setValor(new Double(100.0D));
    				meta.setProtegido(new Boolean(false));
    				
    				new MetaManager(this.pm, this.log, this.messageResources).saveMeta(meta, stmExt);
    			}
    		}
    	}

    	if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    		resultado = actualizarIndicadorObjetivo(perspectiva.getNlParIndicadorId(), perspectiva.getPlanId(), perspectiva.getFrecuencia(), ano, mesInicio, mesFinal, stmExt);
    	else 
    		resultado = actualizarIndicadorObjetivo(perspectiva.getNlAnoIndicadorId(), perspectiva.getPlanId(), perspectiva.getFrecuencia(), ano, mesInicio, mesFinal, stmExt);

    	return resultado;
    }
    
    private int calcularMedicionesIndicador(Indicador indicador, Long planId, int anoInicio, int anoFinal, byte mesInicio, byte mesFinal, byte mesCierre, boolean tomarPeriodosNulosComoCero, Map<Long, Long> idsIndicadoresCalculados, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException 
    {
    	String CLASS_METHOD = "CalculoManager.calcularMedicionesIndicador";    	
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

        int resDb = 10000;
        if (indicador == null)
        	return 10006;

        try
        {
        	if (idsIndicadoresCalculados.containsKey(indicador.getIndicadorId())) 
        		return resDb;

        	idsIndicadoresCalculados.put(indicador.getIndicadorId(), indicador.getIndicadorId());

        	Set<?> conjuntoSeriesIndicador = indicador.getSeriesIndicador();

        	for (Iterator<?> i = conjuntoSeriesIndicador.iterator(); i.hasNext(); )
        	{
        		SerieIndicador serieIndicadorFormula = (SerieIndicador)i.next();

        		if (serieIndicadorFormula.getPk().getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue()) 
        		{
        			if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue())
        				resDb = calcularIndicadorFormula(indicador, planId, serieIndicadorFormula, anoInicio, anoFinal, mesInicio, mesFinal, mesCierre, tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
        			else if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaPromedio().byteValue())
        				resDb = calcularIndicadorSumatoriaPromedio(indicador, planId, serieIndicadorFormula, anoInicio, mesInicio, mesFinal, mesCierre, tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
        			else if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaSumatoria().byteValue())
        				resDb = calcularIndicadorSumatoria(indicador, planId, serieIndicadorFormula, anoInicio, mesInicio, mesFinal, mesCierre, tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
        			else if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaIndice().byteValue()) 
        				resDb = calcularIndicadorIndice(indicador, planId, serieIndicadorFormula, anoInicio, mesInicio, mesFinal, mesCierre, tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
        		}
        	}
        }
        catch (Exception e)
        {
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";
	    	argsReemplazo[1] = argsReemplazo[1] + "indicadorId: " + indicador.getIndicadorId();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
        }

        return resDb;
    }
    
  	private int calcularIndicadorSumatoriaPromedio(Indicador indicador, Long planId, SerieIndicador serieIndicador, int ano, byte mesInicio, byte mesFinal, byte mesCierre, boolean tomarPeriodosNulosComoCero, Map<Long, Long> idsIndicadoresCalculados, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException
  	{
  		int resDb = 10000;
  		if (serieIndicador.getFormulas() == null) 
  			return resDb;

  		LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(ano, ano, mesInicio, mesFinal, indicador.getFrecuencia().byteValue());
  		int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
  		int periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

  		Indicador indicadorInsumo = null;

  		if (serieIndicador.getFormulas().size() > 0)
  		{
  			Formula formula = (Formula)serieIndicador.getFormulas().toArray()[0];
  			Set<?> insumosFormula = formula.getInsumos();

			if (insumosFormula.size() > 0) 
			{
				InsumoFormula insumo = (InsumoFormula)insumosFormula.iterator().next();
				indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(insumo.getPk().getIndicadorId(), stmExt);
			}
  		}

  		if (indicadorInsumo == null) 
  		{
  			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
  			return 10001;
  		}

  		if (indicadorInsumo.getNaturaleza().byteValue() != Naturaleza.getNaturalezaSimple().byteValue())
  		{
  	    	indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(indicadorInsumo.getIndicadorId(), stmExt);
  	    	if (indicadorInsumo != null) 
  	    		resDb = calcularMedicionesIndicador(indicadorInsumo, planId, ano, ano, mesInicio, mesFinal, indicadorInsumo.getOrganizacion().getMesCierre().byteValue(), tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
  	    	else
  	    		resDb = 10006;
  		}

  		if ((resDb != 10000) && (resDb != 0)) 
  		{
  			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
  			return resDb;
  		}

  		List<Periodo> periodosIndicadorInsumo = PeriodoUtil.generarPeriodosFrecuencia(ano, indicadorInsumo.getFrecuencia().byteValue());
  		int numeroMaximoPeriodosIndicadorInsumo = periodosIndicadorInsumo.size();
  		int perInicio = new Integer(1); 
  		int perFin = new Integer(numeroMaximoPeriodosIndicadorInsumo);

  		List<Medicion> medicionesIndicadorInsumo = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorInsumo.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(ano), new Integer(ano), perInicio, perFin, indicadorInsumo.getFrecuencia(), stmExt);
  		Double[] mediciones = new Double[numeroMaximoPeriodosIndicadorInsumo];

  		int indexMedicion = 0;
  		for (Iterator<Medicion> iter = medicionesIndicadorInsumo.iterator(); iter.hasNext(); ) 
  		{
  			Medicion medicionInsumo = (Medicion)iter.next();

  			if (medicionInsumo.getValor() != null) 
  			{
  				if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue())
  				{
  					Double valor = medicionInsumo.getValor().doubleValue();
  					if ((indexMedicion > 0) && (mediciones[(indexMedicion - 1)] != null)) 
  						valor += mediciones[(indexMedicion - 1)].doubleValue();

  					mediciones[indexMedicion] = new Double(valor);
  				} 
  				else 
  					mediciones[indexMedicion] = new Double(medicionInsumo.getValor().doubleValue());
  			}
  			else if (tomarPeriodosNulosComoCero) 
  			{
  				if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  				{
  					if (indexMedicion > 0)
  						mediciones[indexMedicion] = new Double(mediciones[(indexMedicion - 1)].doubleValue());
  					else
  						mediciones[indexMedicion] = new Double(0.0D);
  				}
  				else
  					mediciones[indexMedicion] = new Double(0.0D);
  			}
  			else 
  				mediciones[indexMedicion] = null;

  			indexMedicion++;
  		}

    	if (indicador.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    	{
    		periodoInicio = periodoInicial;
    		periodoFin = periodoFinal;
    	}

  		return finalizarCalculoIndicadorSumatoriaPromedio(ano, periodoInicio, periodoFin, indicador, serieIndicador, mediciones, indicadorInsumo, periodosIndicadorInsumo, tomarPeriodosNulosComoCero, usuario, stmExt);
  	}
    
  	private int finalizarCalculoIndicadorSumatoriaPromedio(int ano, int periodoInicio, int periodoFinal, Indicador indicador, SerieIndicador serieIndicador, Double[] mediciones, Indicador indicadorInsumo, List<Periodo> periodosIndicadorInsumo, boolean tomarNulosComoCero, Usuario usuario, Statement stmExt) throws SQLException
  	{
  		int resDb = 10000;

  		List<?> periodosIndicador = PeriodoUtil.generarPeriodosFrecuencia(ano, indicador.getFrecuencia().byteValue());
  		int numeroMaximoPeriodosIndicador = periodosIndicador.size();
  		int numeroMaximoPeriodosIndicadorInsumo = periodosIndicadorInsumo.size();

  		for (int numeroPeriodoIndicador = periodoFinal; numeroPeriodoIndicador >= periodoInicio; numeroPeriodoIndicador--) 
  		{
  			Periodo periodoSumatoria = (Periodo)periodosIndicador.get(numeroPeriodoIndicador - 1);
  			int numeroLecturas = 0;
  			Double sumatoria = 0.0D;

  			if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  			{
  				for (int numeroPeriodoIndicadorInsumo = 0; numeroPeriodoIndicadorInsumo < numeroPeriodoIndicador; numeroPeriodoIndicadorInsumo++) 
  				{
  					Double valor = mediciones[numeroPeriodoIndicadorInsumo];
  					if (valor == null) 
  					{
  						if (tomarNulosComoCero)
  							numeroLecturas++;
  					}
  					else 
  						numeroLecturas++;
  				}
  				
  				if (numeroLecturas == numeroPeriodoIndicador)
  					sumatoria = mediciones[(numeroPeriodoIndicador - 1)].doubleValue();
  			}
  			else 
  			{
  				for (int periodoIndicadorInsumo = 0; periodoIndicadorInsumo < numeroMaximoPeriodosIndicadorInsumo; periodoIndicadorInsumo++) 
  				{
  					Periodo periodoInsumo = (Periodo)periodosIndicadorInsumo.get(periodoIndicadorInsumo);

  					if ((periodoInsumo.getFechaInicio().compareTo(periodoSumatoria.getFechaInicio()) >= 0) && (periodoInsumo.getFechaFinal().compareTo(periodoSumatoria.getFechaFinal()) <= 0)) {
  						if (mediciones[periodoIndicadorInsumo] == null) 
  						{
  							if (tomarNulosComoCero)
  								numeroLecturas++;
  						}
  						else 
  						{
  							sumatoria += mediciones[periodoIndicadorInsumo].doubleValue();
  							numeroLecturas++;
  						}
  					}
  				}
  			}
  			boolean totalLecturasCorrecto = false;
  			if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  				totalLecturasCorrecto = numeroLecturas == numeroPeriodoIndicador;
  			else if (indicadorInsumo.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
  				totalLecturasCorrecto = numeroLecturas <= PeriodoUtil.getNumeroPeriodosFrecuenciaDiariaEnFrecuencia(indicador.getFrecuencia().byteValue(), indicadorInsumo.getFrecuencia().byteValue(), ano, numeroPeriodoIndicador);
			else 
				totalLecturasCorrecto = numeroLecturas >= numeroMaximoPeriodosIndicadorInsumo / numeroMaximoPeriodosIndicador;

  			if (totalLecturasCorrecto && numeroLecturas > 0) 
  			{
  				Double valorMedicion = null;
  				if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaSumatoria().byteValue()) 
  					valorMedicion = new Double(sumatoria);
  				else if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue())
  					valorMedicion = new Double(sumatoria / numeroPeriodoIndicador);
  				else 
  					valorMedicion = new Double(sumatoria / numeroLecturas);

  				Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(ano), new Integer(numeroPeriodoIndicador), serieIndicador.getPk().getSerieId()), valorMedicion, new Boolean(false));

  				List<Medicion> medicionesEditadas = new ArrayList<Medicion>();
  				medicionesEditadas.add(medicionEditada);
  				resDb = new MedicionManager(pm, log, messageResources).saveMediciones(indicador, medicionesEditadas, null, new Boolean(false), stmExt);
  			}
  			else 
  			{
  				Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(ano), new Integer(numeroPeriodoIndicador), serieIndicador.getPk().getSerieId()), null, new Boolean(false));
  				resDb = new MedicionManager(pm, log, messageResources).deleteMedicion(medicionEditada, null, stmExt);
  			}
  		}
  		
  		return resDb;
  	}
  	
    private int calcularIndicadorFormula(Indicador indicador, Long planId, SerieIndicador serieIndicador, int anoInicio, int anoFinal, byte mesInicio, byte mesFinal, byte mesCierre, boolean tomarPeriodosNulosComoCero, Map<Long, Long> idsIndicadoresCalculados, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException
    {
    	String CLASS_METHOD = "CalculoManager.calcularIndicadorFormula";
    	int resDb = 10000;
  		if (serieIndicador.getFormulas() == null) 
  			return resDb;

        try
        {
	    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, indicador.getFrecuencia().byteValue());
	    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
	    	int periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
	    	
	    	Formula formula = new Formula();
	    	Set<?> insumosFormula = new HashSet<Object>();
	
	    	if (serieIndicador.getFormulas().size() > 0)
	    	{
	    		formula = (Formula)serieIndicador.getFormulas().toArray()[0];
	    		insumosFormula = formula.getInsumos();
	    		if (formula.getExpresion() != null && formula.getExpresion().indexOf("ERROR") != -1) 
	    			return 10005;
	    	}
	
	    	insumosFormula.size();
	
	    	for (Iterator<?> i = insumosFormula.iterator(); i.hasNext(); ) 
	    	{
	    		InsumoFormula insumoFormula = (InsumoFormula)i.next();
	
	        	Indicador indicadorFomula = new IndicadorManager(pm, log, messageResources).Load(insumoFormula.getPk().getIndicadorId(), stmExt);
	        	if (indicadorFomula == null) 
	        		break;
	        	insumoFormula.setInsumo(indicadorFomula);
	        	if (idsIndicadoresCalculados.containsKey(indicadorFomula.getIndicadorId())) 
	        		continue;
	        	if (indicadorFomula.getNaturaleza().byteValue() != Naturaleza.getNaturalezaSimple().byteValue())
	        	{
	        		resDb = calcularMedicionesIndicador(indicadorFomula, planId, anoInicio, anoFinal, mesInicio, mesFinal, mesCierre, tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
	        		if (resDb != 10000)
	        			break;
	        	}
	    	}
	      
	    	StringBuffer expresionFormula = new StringBuffer("");
	
	    	if ((formula != null) && (formula.getExpresion() != null))
	    		expresionFormula = new StringBuffer(formula.getExpresion());
	
	    	List<InsumoFormula> listaInsumosFormula = getMacrosFormula(expresionFormula, insumosFormula);
	    	Double valorAnterior = null; 
	    	for (int periodoSeleccionado = periodoInicio; periodoSeleccionado <= periodoFin; periodoSeleccionado++)
	    	{
	    		resDb = 10000;
	
	    		if (listaInsumosFormula.size() > 0)
	    		{
	    			for (Iterator<InsumoFormula> i = listaInsumosFormula.iterator(); i.hasNext(); ) 
	    			{
	    				InsumoFormula insumoFormula = (InsumoFormula)i.next();
	    				int[] anoMacro = new int[1];
	    				int[] periodoMacro = new int[1];
	    				List<?> medicionSeleccionada = null;
	    				
	    				getMacroPeriodo(indicador.getFrecuencia().byteValue(), insumoFormula.getMacro(), anoInicio, periodoSeleccionado, mesCierre, anoMacro, periodoMacro);
	
	    				if (insumoFormula.getMacro() == TipoMacro.getTipoMacroSumatoria().byteValue())
	    					medicionSeleccionada = new MedicionManager(pm, log, messageResources).getMedicionesAcumuladasPeriodo(insumoFormula.getPk().getIndicadorId(), insumoFormula.getInsumo().getFrecuencia(), insumoFormula.getInsumo().getMesCierre(), insumoFormula.getPk().getInsumoSerieId(), insumoFormula.getInsumo().getValorInicialCero(), new Integer(anoMacro[0]), new Integer(anoMacro[0]), new Integer(periodoMacro[0]), new Integer(periodoMacro[0]), true, stmExt);
	    				else 
	    					medicionSeleccionada = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(insumoFormula.getPk().getIndicadorId(), insumoFormula.getPk().getInsumoSerieId(), new Integer(anoMacro[0]), new Integer(anoMacro[0]), new Integer(periodoMacro[0]), new Integer(periodoMacro[0]), insumoFormula.getInsumo().getFrecuencia(), stmExt);
	
	    				if (tomarPeriodosNulosComoCero)
	    					insumoFormula.setValor("0");
	    				else 
	    					insumoFormula.setValor(null);
	
	    				if (medicionSeleccionada.size() > 0 && medicionSeleccionada.get(0) != null && ((Medicion)medicionSeleccionada.get(0)).getValor() != null) 
	    					insumoFormula.setValor(String.valueOf(((Medicion)medicionSeleccionada.get(0)).getValor()));

		    			if (indicador.getAsignarInventario() != null && indicador.getAsignarInventario() && valorAnterior == null)
		    			{
		    				int ano = anoMacro[0];
		    				int periodo = periodoMacro[0];
		    	    		int numeroPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), ano);
		    	    		periodo--;
		    	    		if (periodo == 0) 
		    	    		{
		    	    			periodo = numeroPeriodos;
		    	    			ano--;
		    	    		}
		    				medicionSeleccionada = new MedicionManager(pm, log, messageResources).getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), serieIndicador.getSerieTiempo().getSerieRealId().longValue(), indicador.getValorInicialCero(), new Integer(ano), new Integer(ano), new Integer(periodo), new Integer(periodo), true, stmExt);
		    				if (tomarPeriodosNulosComoCero)
		    					valorAnterior = 0D;
		    				if (medicionSeleccionada.size() > 0 && medicionSeleccionada.get(0) != null && ((Medicion)medicionSeleccionada.get(0)).getValor() != null) 
		    					valorAnterior = ((Medicion)medicionSeleccionada.get(0)).getValor();
		    			}
	    				
	    				if (insumoFormula.getValor() != null)
	    					continue;
	
	    				deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), anoInicio, anoFinal, periodoSeleccionado, periodoSeleccionado, stmExt);
	
	    				resDb = 10003;
	    				break;
	    			}
	    			
	    		}
	    			
	    		Double valor = null;
	    		if (resDb == 10000) 
	    		{
	        		String[] arrFormula = new String[1];
	        		arrFormula[0] = expresionFormula.toString();
	        		convertirMacrosFormula(arrFormula, listaInsumosFormula, periodoSeleccionado);
	
	        		VgcFormulaEvaluator evaluador = new VgcFormulaEvaluator();
	        		evaluador.setExpresion(arrFormula[0]);
	        		try 
	    			{
	    				valor = new Double(evaluador.evaluar());
	    				if (indicador.getAsignarInventario() != null && indicador.getAsignarInventario() && valorAnterior != null)
	    					valor = valorAnterior + valor;
	    				valorAnterior = valor;
	    			} 
	    			catch (Exception e) 
	    			{
	    				valor = null;
	    			}
	    		}
	
	    		if ((valor == null) || (valor.isNaN())) 
	    			resDb = 10004;
	    		else 
	    		{
	    			Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoInicio), new Integer(periodoSeleccionado), serieIndicador.getPk().getSerieId()), valor, new Boolean(false));
	
	    			List<Medicion> medicionesEditadas = new ArrayList<Medicion>();
	    			medicionesEditadas.add(medicionEditada);
	    			resDb = new MedicionManager(pm, log, messageResources).saveMediciones(indicador, medicionesEditadas, null, new Boolean(false), stmExt);
	    		}
	
	    		if (resDb != 10000) 
	    		{
	    			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), anoInicio, anoFinal, periodoSeleccionado, periodoFin, stmExt);
	    			resDb = 10000;
	    		}
	    	}
        }
        catch (Exception e)
        {
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = (e.getMessage() != null ? e.getMessage() : "") + indicador.getIndicadorId();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
        }

    	return resDb;
    }
    
    private List<InsumoFormula> getMacrosFormula(StringBuffer formula, Set<?> insumosFormula)
    {
    	int posMacro = 0;
    	String macro = "";
    	int j = 0;
    	List<InsumoFormula> listaInsumosFormula = new ArrayList<InsumoFormula>();
    	int posInicioBusqueda = 0;
    	String formulaLocal = formula.toString();

    	InsumoFormula insumoFormula = null;

    	for (Iterator<?> i = insumosFormula.iterator(); i.hasNext(); )
    	{
    		insumoFormula = (InsumoFormula)i.next();
    		insumoFormula = new InsumoFormula(insumoFormula.getPk(), insumoFormula.getPadre(), insumoFormula.getInsumo(), insumoFormula.getSerieTiempo(), insumoFormula.getInsumoSerieTiempo(), insumoFormula.getFormula());

    		String tokenInsumo = "[" + insumoFormula.getPk().getIndicadorId().longValue() + "." + insumoFormula.getPk().getInsumoSerieId().longValue() + "]:";

    		posInicioBusqueda = 0;
    		posMacro = 0;

    		while (posMacro > -1)
    		{
    			posMacro = formulaLocal.indexOf(tokenInsumo, posInicioBusqueda);
    			macro = "";

    			if (posMacro > -1) 
    			{
        			int intPosMacro = posMacro; 
    				posMacro += tokenInsumo.length();

    				for (j = posMacro; j < formulaLocal.length(); j++)
    				{
    					if (j == posMacro ? "-1234567890@#%S".indexOf(formulaLocal.substring(j, j + 1)) == -1 : "1234567890@#%S".indexOf(formulaLocal.substring(j, j + 1)) == -1)
    						break;
    					macro = macro + formulaLocal.substring(j, j + 1);
    				}

    				posInicioBusqueda = j;

    				if (macro.equals("")) 
    					insumoFormula.setMacro(0);
    				else if (macro.equals("@")) 
    					insumoFormula.setMacro(1);
    				else if (macro.equals("#")) 
    					insumoFormula.setMacro(2);
    				else if (macro.equals("%")) 
    					insumoFormula.setMacro(3);
    				else if (macro.equals("S")) 
    					insumoFormula.setMacro(4);
    				else 
    				{
    					insumoFormula.setMacro(Integer.valueOf(macro).intValue());
    					formula.insert(posInicioBusqueda, "Ã‡");
    				}
    	    		listaInsumosFormula.add(insumoFormula);
    	    		formulaLocal = formulaLocal.substring(0, intPosMacro) + formulaLocal.substring(posInicioBusqueda, formulaLocal.length());
    	    		while (formulaLocal.length() > 1 && "-1234567890@#%S]".indexOf(formulaLocal.substring((formulaLocal.length()-1), formulaLocal.length())) == -1)
    	    			formulaLocal = formulaLocal.substring(0, (formulaLocal.length()-1));
    			}
    		}
    		
    		insumoFormula = new InsumoFormula(insumoFormula.getPk(), insumoFormula.getPadre(), insumoFormula.getInsumo(), insumoFormula.getSerieTiempo(), insumoFormula.getInsumoSerieTiempo(), insumoFormula.getFormula());
    		tokenInsumo = "[" + insumoFormula.getPk().getIndicadorId().longValue() + "." + insumoFormula.getPk().getInsumoSerieId().longValue() + "]";

    		posInicioBusqueda = 0;
    		posMacro = 0;

    		while (posMacro > -1)
    		{
    			posMacro = formulaLocal.indexOf(tokenInsumo, posInicioBusqueda);
    			macro = "";

    			if (posMacro > -1) 
    			{
    				int intPosMacro = posMacro;
    				if (intPosMacro == 0)
    					intPosMacro = 1;
    				posMacro += tokenInsumo.length();
    				posInicioBusqueda = posMacro;

					insumoFormula.setMacro(0);
    	    		listaInsumosFormula.add(insumoFormula);
    	    		formulaLocal = formulaLocal.replace(tokenInsumo, "E");
    	    		while (formulaLocal.length() > 1 && "-1234567890@#%S]".indexOf(formulaLocal.substring((formulaLocal.length()-1), formulaLocal.length())) == -1)
    	    			formulaLocal = formulaLocal.substring(0, (formulaLocal.length()-1));
    			}
    		}
    	}
      
    	return listaInsumosFormula;
	}
    
    private void getMacroPeriodo(byte frecuencia, int macro, int anoCalculo, int periodoCalculo, int mesCierre, int[] anoMacro, int[] periodoMacro)
    {
    	int numeroPeriodos = 0;
    	int na = 0;
    	int np = 0;
    	int c = 0;

    	switch (macro) 
    	{
	    	case 0:
	    		anoMacro[0] = anoCalculo;
	    		periodoMacro[0] = periodoCalculo;
	    		break;
	    	case 1:
	    		anoMacro[0] = (anoCalculo - 1);
	    		periodoMacro[0] = periodoCalculo;
	    		break;
	    	case 2:
	    		anoMacro[0] = (anoCalculo - 1);
	    		periodoMacro[0] = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.fechaCalendario(anoCalculo - 1, mesCierre, PeriodoUtil.ultimoDiaMes(mesCierre, anoCalculo - 1), false), frecuencia);
	    		break;
	    	case 3:
	    		anoMacro[0] = anoCalculo;
	    		periodoMacro[0] = 1;
	    		break;
	    	case 4:
	    		anoMacro[0] = anoCalculo;
	    		periodoMacro[0] = periodoCalculo;
	    		break;
	    	default:
	    		numeroPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoCalculo);
	    		np = periodoCalculo;
	    		na = anoCalculo;
	    		for (c = 1; c <= Math.abs(macro); c++) 
	    		{
	    			np--;
	    			if (np == 0) 
	    			{
	    				np = numeroPeriodos;
	    				na--;
	    			}
	    		}
	    		anoMacro[0] = na;
	    		periodoMacro[0] = np;
    	}
    }
    
    private int deleteMedicionesRangoPeriodo(long indicadorId, long serieId, int anoInicio, int anoFinal, int periodoInicio, int periodoFinal, Statement stmExt) throws SQLException
    {
    	String CLASS_METHOD = "CalculoManager.deleteMedicionesRangoPeriodo";    	
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

    	int resDb = 10000;

    	try
    	{
    		resDb = new MedicionManager(pm, log, messageResources).deleteMediciones(new Long(indicadorId), new Long(serieId), new Integer(anoInicio), new Integer(anoFinal), new Integer(periodoInicio), new Integer(periodoFinal), stmExt);
    	} 
    	catch (Exception e) 
    	{
	    	String[] argsReemplazo = new String[2];
			
	    	argsReemplazo[0] = CLASS_METHOD;
	    	argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
    	}
    	
    	return resDb;
    }
    
    private void convertirMacrosFormula(String[] formula, List<InsumoFormula> insumosFormula, int periodoSeleccionado)
    {
    	String tokenInsumo = "";
    	String expresionFormula = formula[0];
    	InsumoFormula insumoFormula = null;

		if (expresionFormula.indexOf(".") == -1)
		{
	    	for (Iterator<?> i = insumosFormula.iterator(); i.hasNext(); )
	    	{
	    		insumoFormula = (InsumoFormula)i.next();
    	    	insumoFormula = new InsumoFormula(insumoFormula.getPk(), insumoFormula.getPadre(), insumoFormula.getInsumo(), insumoFormula.getSerieTiempo(), insumoFormula.getInsumoSerieTiempo(), insumoFormula.getFormula());

    	    	tokenInsumo = insumoFormula.getPk().getIndicadorId().longValue() + "." + insumoFormula.getPk().getInsumoSerieId().longValue();
    	    	expresionFormula = expresionFormula.replaceAll("" + insumoFormula.getPk().getIndicadorId().longValue() + "", tokenInsumo);
	    	}
		}
    	
    	for (Iterator<InsumoFormula> i = insumosFormula.iterator(); i.hasNext(); )
    	{
    		insumoFormula = (InsumoFormula)i.next();
    		tokenInsumo = "\\[" + insumoFormula.getPk().getIndicadorId().longValue() + "." + insumoFormula.getPk().getInsumoSerieId().longValue() + "\\]";

    		switch (insumoFormula.getMacro()) 
    		{
		        case 0:
		        	break;
		        case 1:
		        	tokenInsumo = tokenInsumo + ":@";
		        	break;
		        case 2:
		        	tokenInsumo = tokenInsumo + ":#";
		        	break;
		        case 3:
		        	tokenInsumo = tokenInsumo + ":%";
		        	break;
		        case 4:
		        	tokenInsumo = tokenInsumo + ":S";
		        	break;
		        default:
		        	tokenInsumo = tokenInsumo + ":" + insumoFormula.getMacro() + "Ã‡";
	        }

    		if (insumoFormula.getValor() != null) 
    			expresionFormula = expresionFormula.replaceAll(tokenInsumo, insumoFormula.getValor());
    		else
    			expresionFormula = expresionFormula.replaceAll(tokenInsumo, "null");
    	}

    	expresionFormula = expresionFormula.replaceAll("\\[P\\]", String.valueOf(periodoSeleccionado));
    	formula[0] = expresionFormula;
    }
    
  	private int calcularIndicadorSumatoria(Indicador indicador, Long planId, SerieIndicador serieIndicador, int ano, byte mesInicio, byte mesFinal, byte mesCierre, boolean tomarPeriodosNulosComoCero, Map<Long, Long> idsIndicadoresCalculados, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException
  	{
  		int resDb = 10000;
  		if (serieIndicador.getFormulas() == null) 
  			return resDb;
  		
  		LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(ano, ano, mesInicio, mesFinal, indicador.getFrecuencia().byteValue());
  		int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
  		int periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
  		
  		Indicador indicadorInsumo = null;

  		if (serieIndicador.getFormulas().size() > 0)
  		{
  			Formula formula = (Formula)serieIndicador.getFormulas().toArray()[0];
  			Set<?> insumosFormula = formula.getInsumos();

			if (insumosFormula.size() > 0) 
			{
				InsumoFormula insumo = (InsumoFormula)insumosFormula.iterator().next();
				indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(insumo.getPk().getIndicadorId(), stmExt);
			}
  		}

  		if (indicadorInsumo == null) 
  		{
  			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
  			return 10001;
  		}

  		if (indicadorInsumo.getNaturaleza().byteValue() != Naturaleza.getNaturalezaSimple().byteValue()) 
  		{
  	    	indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(indicadorInsumo.getIndicadorId(), stmExt);
  	    	if (indicadorInsumo != null) 
  	    		resDb = calcularMedicionesIndicador(indicadorInsumo, planId, ano, ano, mesInicio, mesFinal, indicadorInsumo.getOrganizacion().getMesCierre().byteValue(), tomarPeriodosNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
  	    	else
  	    		resDb = 10006;
  		}

  		if ((resDb != 10000) && (resDb != 0)) 
  		{
  			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
  			return resDb;
  		}

  		List<?> periodosIndicadorInsumo = PeriodoUtil.generarPeriodosFrecuencia(ano, indicadorInsumo.getFrecuencia().byteValue());
  		int numeroMaximoPeriodosIndicadorInsumo = periodosIndicadorInsumo.size();
  		int periodoInicialIndicadorInsumo = 1;
  		if (indicadorInsumo.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue() && numeroMaximoPeriodosIndicadorInsumo > periodoFinal)
  		{
  	  		lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(ano, ano, mesInicio, mesFinal, indicadorInsumo.getFrecuencia().byteValue());
  	  		//periodoInicialIndicadorInsumo = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
  	  		numeroMaximoPeriodosIndicadorInsumo = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
  		}

  		List<?> medicionesIndicadorInsumo = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorInsumo.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(ano), new Integer(ano), new Integer(periodoInicialIndicadorInsumo), new Integer(numeroMaximoPeriodosIndicadorInsumo), indicadorInsumo.getFrecuencia(), stmExt);
  		Double[] mediciones = new Double[numeroMaximoPeriodosIndicadorInsumo];

  		int indexMedicion = 0;
  		for (Iterator<?> iter = medicionesIndicadorInsumo.iterator(); iter.hasNext(); ) 
  		{
  			Medicion medicionInsumo = (Medicion)iter.next();

  			if (medicionInsumo.getValor() != null) 
  			{
  				if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue())
  				{
  					Double valor = medicionInsumo.getValor().doubleValue();
  					if ((indexMedicion > 0) && (mediciones[(indexMedicion - 1)] != null) && indicador.getTipoSumaMedicion().byteValue() == TipoSuma.getTipoSumaSumarMediciones().byteValue()) 
  						valor += mediciones[(indexMedicion - 1)].doubleValue();

  					mediciones[indexMedicion] = new Double(valor);
  				} 
  				else 
  					mediciones[indexMedicion] = new Double(medicionInsumo.getValor().doubleValue());
  			}
  			else if (tomarPeriodosNulosComoCero) 
  			{
  				if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  				{
  					if (indexMedicion > 0 && indicador.getTipoSumaMedicion().byteValue() == TipoSuma.getTipoSumaSumarMediciones().byteValue())
  						mediciones[indexMedicion] = new Double(mediciones[(indexMedicion - 1)].doubleValue());
  					else
  						mediciones[indexMedicion] = new Double(0.0D);
  				}
  				else
  					mediciones[indexMedicion] = new Double(0.0D);
  			}
  			else 
  				mediciones[indexMedicion] = null;

  			indexMedicion++;
  		}

    	if (indicador.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    	{
    		periodoInicio = periodoInicial;
    		periodoFin = periodoFinal;
    	}
  		
    	return finalizarCalculoIndicadorSumatoria(ano, periodoInicio, periodoFin, indicador, serieIndicador, mediciones, indicadorInsumo, periodosIndicadorInsumo, tomarPeriodosNulosComoCero, usuario, stmExt);
  	}
  	
  	private int finalizarCalculoIndicadorSumatoria(int ano, int periodoInicio, int periodoFinal, Indicador indicador, SerieIndicador serieIndicador, Double[] mediciones, Indicador indicadorInsumo, List<?> periodosIndicadorInsumo, boolean tomarNulosComoCero, Usuario usuario, Statement stmExt) throws SQLException
  	{
  		int resDb = 10000;

  		List<?> periodosIndicador = PeriodoUtil.generarPeriodosFrecuencia(ano, indicador.getFrecuencia().byteValue());
  		int numeroMaximoPeriodosIndicador = periodosIndicador.size();
  		int numeroMaximoPeriodosIndicadorInsumo = periodosIndicadorInsumo.size();
  		if (numeroMaximoPeriodosIndicadorInsumo > mediciones.length) numeroMaximoPeriodosIndicadorInsumo = mediciones.length;

  		for (int numeroPeriodoIndicador = periodoFinal; numeroPeriodoIndicador >= periodoInicio; numeroPeriodoIndicador--) 
  		{
  			Periodo periodoSumatoria = (Periodo)periodosIndicador.get(numeroPeriodoIndicador - 1);
  			int numeroLecturas = 0;
  			Double sumatoria = 0.0D;

  			if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  			{
  				for (int numeroPeriodoIndicadorInsumo = 0; numeroPeriodoIndicadorInsumo < numeroPeriodoIndicador; numeroPeriodoIndicadorInsumo++) 
  				{
  					Double valor = mediciones[numeroPeriodoIndicadorInsumo];
  					if (valor == null) 
  					{
  						if (tomarNulosComoCero)
  							numeroLecturas++;
  					}
  					else 
  						numeroLecturas++;
  				}
  				
  				if (numeroLecturas == numeroPeriodoIndicador)
  					sumatoria = mediciones[(numeroPeriodoIndicador - 1)].doubleValue();
  			}
  			else 
  			{
  				for (int periodoIndicadorInsumo = 0; periodoIndicadorInsumo < numeroMaximoPeriodosIndicadorInsumo; periodoIndicadorInsumo++) 
  				{
  					Periodo periodoInsumo = (Periodo)periodosIndicadorInsumo.get(periodoIndicadorInsumo);

  					if ((periodoInsumo.getFechaInicio().compareTo(periodoSumatoria.getFechaInicio()) >= 0) && (periodoInsumo.getFechaFinal().compareTo(periodoSumatoria.getFechaFinal()) <= 0)) {
  						if (mediciones[periodoIndicadorInsumo] == null) 
  						{
  							if (tomarNulosComoCero)
  								numeroLecturas++;
  						}
  						else 
  						{
  							if (indicador.getTipoSumaMedicion().byteValue() == TipoSuma.getTipoSumaSumarMediciones().byteValue())
  								sumatoria += mediciones[periodoIndicadorInsumo].doubleValue();
  							else if (indicador.getTipoSumaMedicion().byteValue() == TipoSuma.getTipoSumaUltimoPeriodo().byteValue())
  								sumatoria = mediciones[periodoIndicadorInsumo].doubleValue();
  							
  							numeroLecturas++;
  						}
  					}
  				}
  			}
  			boolean totalLecturasCorrecto = false;
  			if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue()) 
  				totalLecturasCorrecto = numeroLecturas == numeroPeriodoIndicador;
  			else if (indicadorInsumo.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
  				totalLecturasCorrecto = numeroLecturas <= PeriodoUtil.getNumeroPeriodosFrecuenciaDiariaEnFrecuencia(indicador.getFrecuencia().byteValue(), indicadorInsumo.getFrecuencia().byteValue(), ano, numeroPeriodoIndicador);
			else 
				totalLecturasCorrecto = numeroLecturas >= numeroMaximoPeriodosIndicadorInsumo / numeroMaximoPeriodosIndicador;

  			if (totalLecturasCorrecto && numeroLecturas > 0) 
  			{
  				Double valorMedicion = null;
  				if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaSumatoria().byteValue()) 
  					valorMedicion = new Double(sumatoria);
  				else if (indicador.getFrecuencia().byteValue() == indicadorInsumo.getFrecuencia().byteValue())
  					valorMedicion = new Double(sumatoria / numeroPeriodoIndicador);
  				else 
  					valorMedicion = new Double(sumatoria / numeroLecturas);

  				Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(ano), new Integer(numeroPeriodoIndicador), serieIndicador.getPk().getSerieId()), valorMedicion, new Boolean(false));

  				List<Medicion> medicionesEditadas = new ArrayList<Medicion>();
  				medicionesEditadas.add(medicionEditada);
  				resDb = new MedicionManager(pm, log, messageResources).saveMediciones(indicador, medicionesEditadas, null, new Boolean(false), stmExt);
  			}
  			else 
  			{
  				Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(ano), new Integer(numeroPeriodoIndicador), serieIndicador.getPk().getSerieId()), null, new Boolean(false));
  				resDb = new MedicionManager(pm, log, messageResources).deleteMedicion(medicionEditada, null, stmExt);
  			}
  		}
  		
  		return resDb;
  	}
  	
    private int calcularIndicadorIndice(Indicador indicador, Long planId, SerieIndicador serieIndicador, int ano, byte mesInicio, byte mesFinal, byte mesCierre, boolean tomarNulosComoCero, Map<Long, Long> idsIndicadoresCalculados, Usuario usuario, Statement stmExt, int periodoInicial, int periodoFinal) throws SQLException
    {
    	int resDb = 10000;
  		if (serieIndicador.getFormulas() == null) 
  			return resDb;

    	Byte frecuencia = indicador.getFrecuencia();

		LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(ano, ano, mesInicio, mesFinal, indicador.getFrecuencia().byteValue());
		int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
		int periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

		Indicador indicadorInsumo = null;
		String expresionFormula = null;

		if (serieIndicador.getFormulas().size() > 0)
		{
			Formula formula = (Formula)serieIndicador.getFormulas().toArray()[0];
			Set<?> insumosFormula = formula.getInsumos();
			expresionFormula = formula.getExpresion();

			if (insumosFormula.size() > 0) 
			{
				InsumoFormula insumo = (InsumoFormula)insumosFormula.iterator().next();
				indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(insumo.getPk().getIndicadorId(), stmExt);
			}
		}

		if (indicadorInsumo == null) 
		{
			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
			return 10001;
		}

		if (indicadorInsumo.getNaturaleza().byteValue() != Naturaleza.getNaturalezaSimple().byteValue())
		{
  	    	indicadorInsumo = new IndicadorManager(pm, log, messageResources).Load(indicadorInsumo.getIndicadorId(), stmExt);
  	    	if (indicadorInsumo != null) 
  				resDb = calcularMedicionesIndicador(indicadorInsumo, planId, ano, ano, mesInicio, mesFinal, indicadorInsumo.getOrganizacion().getMesCierre().byteValue(), tomarNulosComoCero, idsIndicadoresCalculados, usuario, stmExt, periodoInicial, periodoFinal);
  	    	else
  	    		resDb = 10006;
		}

		if ((resDb != 10000) && (resDb != 0)) 
		{
			deleteMedicionesRangoPeriodo(indicador.getIndicadorId().longValue(), serieIndicador.getPk().getSerieId().longValue(), ano, ano, periodoInicio, periodoFin, stmExt);
			return resDb;
		}

		int numeroPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);

		List<?> medicionesIndicadorInsumo = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorInsumo.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(ano), new Integer(ano), new Integer(1), new Integer(numeroPeriodos), frecuencia, stmExt);
		List<?> medicionesAnoAnteriorIndicadorInsumo = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorInsumo.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(ano - 1), new Integer(ano - 1), new Integer(1), new Integer(numeroPeriodos), frecuencia, stmExt);
		Double[] mediciones = new Double[numeroPeriodos];
		Double[] medicionesAnoAnterior = new Double[numeroPeriodos];

		for (int indexMedicion = 0; indexMedicion < numeroPeriodos; indexMedicion++) 
		{
			Medicion medicionInsumo = (Medicion)medicionesIndicadorInsumo.get(indexMedicion);
			Medicion medicionAnoAnteriorInsumo = (Medicion)medicionesAnoAnteriorIndicadorInsumo.get(indexMedicion);

			if (medicionInsumo.getValor() != null) 
				mediciones[indexMedicion] = new Double(medicionInsumo.getValor().doubleValue());
			else if (tomarNulosComoCero)
				mediciones[indexMedicion] = new Double(0.0D);
			else 
				mediciones[indexMedicion] = null;

			if (medicionAnoAnteriorInsumo.getValor() != null) 
				medicionesAnoAnterior[indexMedicion] = new Double(medicionAnoAnteriorInsumo.getValor().doubleValue());
			else if (tomarNulosComoCero)
				medicionesAnoAnterior[indexMedicion] = new Double(0.0D);
			else 
				medicionesAnoAnterior[indexMedicion] = null;
		}

		int crecimiento = Integer.parseInt(expresionFormula.substring(0, expresionFormula.indexOf("\\")));
		int comparacion = Integer.parseInt(expresionFormula.substring(expresionFormula.indexOf("\\") + 1));

		int periodoCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(Byte.valueOf(mesCierre).intValue(), ano - 1), frecuencia.byteValue());

    	if (indicador.getFrecuencia().byteValue() == Frecuencia.getFrecuenciaDiaria().byteValue())
    	{
    		periodoInicio = periodoInicial;
    		periodoFin = periodoFinal;
    	}
		for (int periodo = periodoFin; periodo >= periodoInicio; periodo--) 
		{
			Double valor = null;
			switch (comparacion) 
			{
				case -3:
					if (periodo == 1) 
					{
						if ((mediciones[0] == null) || (medicionesAnoAnterior[(numeroPeriodos - 1)] == null)) break;
						if (crecimiento == 0)
							valor = new Double((mediciones[0].doubleValue() - medicionesAnoAnterior[(numeroPeriodos - 1)].doubleValue()) / medicionesAnoAnterior[(numeroPeriodos - 1)].doubleValue() * 100.0D);
						else if (crecimiento == 1)
							valor = new Double(mediciones[0].doubleValue() - medicionesAnoAnterior[(numeroPeriodos - 1)].doubleValue());
						else
							valor = new Double(mediciones[0].doubleValue() / medicionesAnoAnterior[(numeroPeriodos - 1)].doubleValue() * 100.0D);
					}
					else
					{
						if ((mediciones[(periodo - 1)] == null) || (mediciones[(periodo - 2)] == null)) break;
						if (crecimiento == 0)
							valor = new Double((mediciones[(periodo - 1)].doubleValue() - mediciones[(periodo - 2)].doubleValue()) / mediciones[(periodo - 2)].doubleValue() * 100.0D);
						else if (crecimiento == 1)
							valor = new Double(mediciones[(periodo - 1)].doubleValue() - mediciones[(periodo - 2)].doubleValue());
						else 
							valor = new Double(mediciones[(periodo - 1)].doubleValue() / mediciones[(periodo - 2)].doubleValue() * 100.0D);
					}
					break;
				case -2:
					if ((mediciones[(periodo - 1)] == null) || (medicionesAnoAnterior[(periodo - 1)] == null)) break;
					if (crecimiento == 0)
						valor = new Double((mediciones[(periodo - 1)].doubleValue() - medicionesAnoAnterior[(periodo - 1)].doubleValue()) / medicionesAnoAnterior[(periodo - 1)].doubleValue() * 100.0D);
					else if (crecimiento == 1)
						valor = new Double(mediciones[(periodo - 1)].doubleValue() - medicionesAnoAnterior[(periodo - 1)].doubleValue());
					else 
						valor = new Double(mediciones[(periodo - 1)].doubleValue() / medicionesAnoAnterior[(periodo - 1)].doubleValue() * 100.0D);
					break;
				case -1:
					if ((mediciones[(periodo - 1)] == null) || (medicionesAnoAnterior[(periodoCierre - 1)] == null)) break;
					if (crecimiento == 0)
						valor = new Double((mediciones[(periodo - 1)].doubleValue() - medicionesAnoAnterior[(periodoCierre - 1)].doubleValue()) / medicionesAnoAnterior[(periodoCierre - 1)].doubleValue() * 100.0D);
					else if (crecimiento == 1)
						valor = new Double(mediciones[(periodo - 1)].doubleValue() - medicionesAnoAnterior[(periodoCierre - 1)].doubleValue());
					else 
						valor = new Double(mediciones[(periodo - 1)].doubleValue() / medicionesAnoAnterior[(periodoCierre - 1)].doubleValue() * 100.0D);
					break;
				default:
					if ((mediciones[(periodo - 1)] == null) || (mediciones[(comparacion - 1)] == null)) break;
					if (crecimiento == 0)
						valor = new Double((mediciones[(periodo - 1)].doubleValue() - mediciones[(comparacion - 1)].doubleValue()) / mediciones[(comparacion - 1)].doubleValue() * 100.0D);
					else if (crecimiento == 1)
						valor = new Double(mediciones[(periodo - 1)].doubleValue() - mediciones[(comparacion - 1)].doubleValue());
					else 
						valor = new Double(mediciones[(periodo - 1)].doubleValue() / mediciones[(comparacion - 1)].doubleValue() * 100.0D);
					break;
			}

			Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(ano), new Integer(periodo), serieIndicador.getPk().getSerieId()), valor, new Boolean(false));
			if (valor != null)
			{
				List<Medicion> medicionesEditadas = new ArrayList<Medicion>();
				medicionesEditadas.add(medicionEditada);
				resDb = new MedicionManager(pm, log, messageResources).saveMediciones(indicador, medicionesEditadas, null, new Boolean(false), stmExt);
			} 
			else 
				resDb = new MedicionManager(pm, log, messageResources).deleteMedicion(medicionEditada, null, stmExt);
		}

    	return resDb;
    }
    
    private List<Medicion> getMedicionesParametroRetoAumentoDisminucion(Indicador indicador, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
    	List<Medicion> medicionesParametro = null;
    	if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue()) 
    	{
    		if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
    		{
    			Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
    			if (indicadorParametro != null)
    				medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
    		}	
    	}
    	else if ((indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue()) && (indicador.getParametroSuperiorValorFijo() == null) && (indicador.getParametroSuperiorIndicadorId() != null)) 
    	{
    		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroSuperiorIndicadorId(), stmExt);
    		if (indicadorParametro != null) 
    			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
    	}

    	return medicionesParametro;
    }
    
    private Double getValorParametroRetoAumentoDisminucion(Indicador indicador, List<Medicion> medicionesParametro, int indexMedicion) 
    {
    	Double valorParametro = null;
        if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoAumento().byteValue()) 
        {
        	if (indicador.getParametroInferiorValorFijo() != null) 
        		valorParametro = indicador.getParametroInferiorValorFijo();
        	else if ((medicionesParametro != null) && (medicionesParametro.size() > indexMedicion)) 
        		valorParametro = ((Medicion)medicionesParametro.get(indexMedicion)).getValor();
        }
        else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaRetoDisminucion().byteValue()) 
        {
        	if (indicador.getParametroSuperiorValorFijo() != null) 
        		valorParametro = indicador.getParametroSuperiorValorFijo();
        	else if ((medicionesParametro != null) && (medicionesParametro.size() > indexMedicion)) 
        		valorParametro = ((Medicion)medicionesParametro.get(indexMedicion)).getValor();
        }

        return valorParametro;
    }
    
    private Double calcularEstadoAnualIndicadorRetoAumento(Medicion medicion, Meta metaAno, Meta metaAnoAnterior, int periodo, int periodoHastaCierre, int mesCierre, Double valorInicial, Double valorParametro) 
    {
        Double valorEstadoAnual = null;
        Double valorTotal = null;
        Double valorMetaAnual = null;
        String[] argsMensajeLog = new String[2];
        argsMensajeLog[0] = medicion.getMedicionId().getPeriodo().toString();
        argsMensajeLog[1] = medicion.getMedicionId().getAno().toString();
        if (medicion.getValor() != null) 
        {
        	valorTotal = medicion.getValor();
        	valorMetaAnual = metaAno.getValor();
        	if ((mesCierre != 12) && (periodo <= periodoHastaCierre))
        		valorMetaAnual = metaAnoAnterior.getValor();

        	if (valorMetaAnual == null) 
            	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmeta", argsMensajeLog) + "\n");
            else 
            	valorEstadoAnual = new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorRetoAumento(valorInicial, valorTotal, valorMetaAnual, valorParametro, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), this.log, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), this.messageResources);
        }
        else
        	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmedicion", argsMensajeLog) + "\n");

        return valorEstadoAnual;
    }

    private Double calcularEstadoAnualIndicadorRetoDisminucion(Medicion medicion, Meta metaAno, int periodo, Double valorInicial, Double valorParametro) 
    {
        String[] argsMensajeLog = new String[2];
        argsMensajeLog[0] = medicion.getMedicionId().getPeriodo().toString();
        argsMensajeLog[1] = medicion.getMedicionId().getAno().toString();
        Double valorEstadoAnual = null;
        Double valorTotal = null;
        Double valorMetaAnual = null;
        if (medicion.getValor() != null) 
        {
        	valorTotal = medicion.getValor();
        	valorMetaAnual = metaAno.getValor();
        } 
        else if (medicion.getValor() == null)	
        	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmedicion", argsMensajeLog) + "\n");
        	
        if (valorMetaAnual == null) 
        	log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmeta", argsMensajeLog) + "\n");
        else if ((valorTotal != null) && (valorMetaAnual != null)) 
    		valorEstadoAnual = new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorRetoDisminucion(valorInicial, valorTotal, valorMetaAnual, valorParametro, this.log, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), this.messageResources);
        
        return valorEstadoAnual;
    }
    
    private int calcularEstadoIndicadorCondicionValorMaximoMinimo(Indicador indicador, Long planId, List<Medicion> mediciones, Byte tipoEstado, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
    	int indexMedicion = 0;
    	int resultado = 10000;

    	if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue()) 
    	{
    		List<Meta> metasParciales = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), stmExt);
    		List<Medicion> medicionesParametro = getMedicionesParametroAlertaParcialCondicionValorMaximoMinimo(indicador, ano, periodoInicio, periodoFin, stmExt);
    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
    		{
    			String[] argsMensajeLog = new String[2];
    			argsMensajeLog[0] = Integer.toString(periodo);
    			argsMensajeLog[1] = Integer.toString(ano);
    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
    			Meta metaParcial = (Meta)metasParciales.get(indexMedicion);
    			if (medicion.getValor() != null) 
    			{
    				Double valorTotal = medicion.getValor();
    				Double valorMetaParcial = null;
    				if (metaParcial.getValor() != null)
    					valorMetaParcial = metaParcial.getValor();
    				else 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmeta", argsMensajeLog) + "\n");

    				Double valorParametro = getValorParametroAlertaParcialCondicionValorMaximoMinimo(indicador, medicionesParametro, indexMedicion);
    				if (valorParametro == null) 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinparametro", argsMensajeLog) + "\n");
    				Double valorEstadoParcial = null;
    				if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
    					valorEstadoParcial = calcularEstadoIndicadorCondicionValorMaximo(periodo, ano, valorTotal, valorMetaParcial, valorParametro);
    				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
    					valorEstadoParcial = calcularEstadoIndicadorCondicionValorMinimo(periodo, ano, valorTotal, valorMetaParcial, valorParametro);
    				if (valorEstadoParcial != null)
    					resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), new Integer(ano), new Integer(periodo), valorEstadoParcial, stmExt);
    				if (resultado != 10000)
    					break;
    			}
    			else 
    				log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmedicion", argsMensajeLog) + "\n");

    			if ((indexMedicion + 1) > (mediciones.size() - 1))
    				break;
				indexMedicion++;
    		}
    	} 
    	else 
    	{
    		Double valorMedicionParametro = getValorMedicionParametroAlertaAnualCondicionValorMaximoMinimo(indicador, ano, stmExt);
    		List<Meta> metasAnuales = new MetaManager(pm, log, messageResources).getMetasAnuales(indicador.getIndicadorId(), planId, new Integer(ano), new Integer(ano), stmExt);
    		Meta metaAno = (Meta)metasAnuales.get(0);

    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
    		{
    			String[] argsMensajeLog = new String[2];
    			argsMensajeLog[0] = Integer.toString(periodo);
    			argsMensajeLog[1] = Integer.toString(ano);
    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
    			if (medicion.getValor() != null) 
    			{
    				Double valorParametro = getValorParametroAlertaAnualCondicionValorMaximoMinimo(indicador, valorMedicionParametro);
    				if (metaAno.getValor() == null) 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmeta", argsMensajeLog) + "\n");
    				if (valorParametro == null) 
    					log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinparametro", argsMensajeLog) + "\n");
    				
    				Double valorEstadoAnual = null;
    				if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
    					valorEstadoAnual = calcularEstadoIndicadorCondicionValorMaximo(periodo, ano, medicion.getValor(), metaAno.getValor(), valorParametro);
    				else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
    					valorEstadoAnual = calcularEstadoIndicadorCondicionValorMinimo(periodo, ano, medicion.getValor(), metaAno.getValor(), valorParametro);
            
    				if (valorEstadoAnual != null)
    					resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), new Integer(ano), new Integer(periodo), valorEstadoAnual, stmExt);
    				if (resultado != 10000)
    					break;
    			}
    			else 
    				log.append("* _ " + this.messageResources.getResource("calcularindicadores.estado.plan.sinmedicion", argsMensajeLog) + "\n");

    			if ((indexMedicion + 1) > (mediciones.size() - 1))
    				break;
				indexMedicion++;
    		}
    	}
    	
    	return resultado;
    }
    
    private List<Medicion> getMedicionesParametroAlertaParcialCondicionValorMaximoMinimo(Indicador indicador, int ano, int periodoInicio, int periodoFin, Statement stmExt) 
    {
        List<Medicion> medicionesParametro = null;
        if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
        {
        	if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
        	{
        		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
        		if (indicadorParametro != null)
        			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
        	}
        	else if ((indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) && (indicador.getParametroSuperiorValorFijo() == null) && (indicador.getParametroSuperiorIndicadorId() != null)) 
        	{
        		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroSuperiorIndicadorId(), stmExt);
        		if (indicadorParametro != null) 
        			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
        	}
        }
        
        return medicionesParametro;
    }
    
    private Double getValorParametroAlertaParcialCondicionValorMaximoMinimo(Indicador indicador, List<Medicion> medicionesParametro, int indexMedicion) 
    {
        Double valorParametro = null;
        if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
        {
        	if (indicador.getParametroInferiorValorFijo() != null) 
        		valorParametro = indicador.getParametroInferiorValorFijo();
        	else if ((medicionesParametro != null) && (medicionesParametro.size() > indexMedicion)) 
        		valorParametro = ((Medicion)medicionesParametro.get(indexMedicion)).getValor();
        }
        else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) 
        {
        	if (indicador.getParametroSuperiorValorFijo() != null) 
        		valorParametro = indicador.getParametroSuperiorValorFijo();
        	else if ((medicionesParametro != null) && (medicionesParametro.size() > indexMedicion)) 
        		valorParametro = ((Medicion)medicionesParametro.get(indexMedicion)).getValor();
        }

        return valorParametro;
    }
    
    private Double getValorMedicionParametroAlertaAnualCondicionValorMaximoMinimo(Indicador indicador, int ano, Statement stmExt) 
    {
        Double valorParametro = null;

        int periodoHastaCierre = 0;
        Byte mesCierre = indicador.getMesCierre();

        if (mesCierre.byteValue() == 12)
        	periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
        else 
        	periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), indicador.getFrecuencia().byteValue());

        List<Medicion> medicionesParametro = null;
        if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
        {
        	if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
        	{
        		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
        		if (indicadorParametro != null)
        			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), true, stmExt);
        	}
        }
        else if ((indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) && (indicador.getParametroSuperiorValorFijo() == null) && (indicador.getParametroSuperiorIndicadorId() != null)) 
        {
        	Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroSuperiorIndicadorId(), stmExt);
        	if (indicadorParametro != null) 
        		medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), true, stmExt);
        }

        if ((medicionesParametro != null) && (medicionesParametro.size() > 0)) 
        	valorParametro = ((Medicion)medicionesParametro.get(0)).getValor();

        return valorParametro;
    }
    
    private Double getValorParametroAlertaAnualCondicionValorMaximoMinimo(Indicador indicador, Double valorMedicionParametro) 
    {
        Double valorParametro = null;
        if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue()) 
        {
        	if (indicador.getParametroInferiorValorFijo() != null)
        		valorParametro = indicador.getParametroInferiorValorFijo();
        	else
        		valorParametro = valorMedicionParametro;
        }
        else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue()) 
        {
        	if (indicador.getParametroSuperiorValorFijo() != null)
        		valorParametro = indicador.getParametroSuperiorValorFijo();
        	else 
        		valorParametro = valorMedicionParametro;
        }

        return valorParametro;
    }
    
    private int calcularEstadoIndicadorCondicionBanda(Indicador indicador, Long planId, List<Medicion> mediciones, Byte tipoEstado, int ano, int periodoInicio, int periodoFin, Statement stmExt)
    {
    	int indexMedicion = 0;
    	int resultado = 10000;

    	if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue()) 
    	{
    		List<Meta> metasParcialesSuperiores = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcialSuperior(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), stmExt);
    		List<Meta> metasParcialesInferiores = new MetaManager(pm, log, messageResources).getMetasParciales(indicador.getIndicadorId(), planId, indicador.getFrecuencia(), indicador.getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcialInferior(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), stmExt);
    		List<Medicion> medicionesParametroSuperior = getMedicionesParametroAlertaParcialCondicionBanda(indicador, true, ano, periodoInicio, periodoFin, stmExt);
    		List<Medicion> medicionesParametroInferior = getMedicionesParametroAlertaParcialCondicionBanda(indicador, false, ano, periodoInicio, periodoFin, stmExt);
    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
    		{
    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
    			Meta metaParcialSuperior = (Meta)metasParcialesSuperiores.get(indexMedicion);
    			Meta metaParcialInferior = (Meta)metasParcialesInferiores.get(indexMedicion);
    			if (medicion.getValor() != null) 
    			{
    				Double valorTotal = medicion.getValor();
    				Double valorMetaParcialSuperior = null;
    				Double valorMetaParcialInferior = null;
    				if (metaParcialSuperior.getValor() != null) 
    					valorMetaParcialSuperior = metaParcialSuperior.getValor();
    				if (metaParcialInferior.getValor() != null) 
    					valorMetaParcialInferior = metaParcialInferior.getValor();

    				Double valorParametroSuperior = getValorParametroAlertaParcialCondicionBanda(indicador, true, medicionesParametroSuperior, medicionesParametroInferior, indexMedicion);
    				Double valorParametroInferior = getValorParametroAlertaParcialCondicionBanda(indicador, false, medicionesParametroSuperior, medicionesParametroInferior, indexMedicion);
    				Double valorEstadoParcial = null;
    				valorEstadoParcial = calcularEstadoIndicadorCondicionBanda(valorTotal, valorMetaParcialSuperior, valorMetaParcialInferior, valorParametroSuperior, valorParametroInferior);
    				if (valorEstadoParcial != null) 
    					resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), new Integer(ano), new Integer(periodo), valorEstadoParcial, stmExt);
    				if (resultado != 10000)
    					break;
    			}

    			if ((indexMedicion + 1) > (mediciones.size() - 1))
    				break;
				indexMedicion++;
    		}
    	} 
    	else 
    	{
    		Double valorMedicionParametroSuperior = getValorMedicionParametroAlertaAnualCondicionBanda(indicador, true, ano, stmExt);
    		Double valorMedicionParametroInferior = getValorMedicionParametroAlertaAnualCondicionBanda(indicador, false, ano, stmExt);
    		List<Meta> metasAnuales = new MetaManager(pm, log, messageResources).getMetasAnuales(indicador.getIndicadorId(), planId, new Integer(ano), new Integer(ano), stmExt);
    		Meta metaAnoSuperior = (Meta)metasAnuales.get(0);
    		Double valorMetaAnualSuperior = metaAnoSuperior.getValor();
    		Double valorMetaAnualInferior = getValorMetaAnualInferiorEstadoAnualCondicionBanda(indicador, ano, stmExt);

    		for (int periodo = periodoInicio; periodo <= periodoFin; periodo++) 
    		{
    			Medicion medicion = (Medicion)mediciones.get(indexMedicion);
    			if (medicion.getValor() != null) 
    			{
    				Double valorTotal = medicion.getValor();
    				Double valorParametroSuperior = getValorParametroEstadoAnualCondicionBanda(indicador, true, valorMedicionParametroSuperior, valorMedicionParametroInferior);
    				Double valorParametroInferior = getValorParametroEstadoAnualCondicionBanda(indicador, false, valorMedicionParametroSuperior, valorMedicionParametroInferior);
    				Double valorEstadoAnual = null;
    				valorEstadoAnual = calcularEstadoIndicadorCondicionBanda(valorTotal, valorMetaAnualSuperior, valorMetaAnualInferior, valorParametroSuperior, valorParametroInferior);

    				if (valorEstadoAnual != null) 
    					resultado = new IndicadorManager(pm, log, messageResources).saveIndicadorEstado(indicador.getIndicadorId(), planId, TipoIndicadorEstado.getTipoIndicadorEstadoAnual(), new Integer(ano), new Integer(periodo), valorEstadoAnual, stmExt);
    				if (resultado != 10000)
    					break;
    			}
    			
    			if ((indexMedicion + 1) > (mediciones.size() - 1))
    				break;
				indexMedicion++;
    		}
    	}
    	
    	return resultado;
    }
    
    private List<Medicion> getMedicionesParametroAlertaParcialCondicionBanda(Indicador indicador, boolean superior, int ano, int periodoInicio, int periodoFin, Statement stmExt) 
    {
        List<Medicion> medicionesParametro = null;
        if (superior) 
        {
        	if ((indicador.getParametroSuperiorValorFijo() == null) && (indicador.getParametroSuperiorIndicadorId() != null)) 
        	{
        		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroSuperiorIndicadorId(), stmExt);
        		if (indicadorParametro != null) 
        			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
        	}
        }
        else if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
        {
        	Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
        	if (indicadorParametro != null) 
        		medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoInicio), new Integer(periodoFin), true, stmExt);
        }

        return medicionesParametro;
    }
    
    private Double getValorParametroAlertaParcialCondicionBanda(Indicador indicador, boolean superior, List<Medicion> medicionesParametroSuperior, List<Medicion> medicionesParametroInferior, int indexMedicion) 
    {
    	Double valorParametro = null;
        if (superior) 
        {
        	if (indicador.getParametroSuperiorValorFijo() != null) 
        		valorParametro = indicador.getParametroSuperiorValorFijo();
        	else if ((medicionesParametroSuperior != null) && (medicionesParametroSuperior.size() > indexMedicion)) 
        		valorParametro = ((Medicion)medicionesParametroSuperior.get(indexMedicion)).getValor();
        }
        else if (indicador.getParametroInferiorValorFijo() != null) 
        	valorParametro = indicador.getParametroInferiorValorFijo();
        else if ((medicionesParametroInferior != null) && (medicionesParametroInferior.size() > indexMedicion)) 
        	valorParametro = ((Medicion)medicionesParametroInferior.get(indexMedicion)).getValor();

        return valorParametro;
    }

    private Double calcularEstadoParcialIndicadorRetoAumento(int periodo, int ano, double ejecutado, Double valorMetaParcial, Double valorParametro) 
    {
        return new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorRetoAumento(null, ejecutado, valorMetaParcial, valorParametro, TipoIndicadorEstado.getTipoIndicadorEstadoParcial(), this.log, ano, periodo, this.messageResources);
    }
    
    private Double calcularEstadoParcialIndicadorRetoDisminucion(int periodo, int ano, double ejecutado, Double valorMetaParcial, Double valorInicial, Double valorParametro) 
    {
        return new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorRetoDisminucion(valorInicial, ejecutado, valorMetaParcial, valorParametro, this.log, ano, periodo, this.messageResources);
    }

    private Double calcularEstadoIndicadorCondicionValorMaximo(int periodo, int ano, double ejecutado, Double valorMeta, Double valorParametro) 
    {
        return new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorCondicionValorMaximo(valorParametro, ejecutado, valorMeta, this.log, ano, periodo, this.messageResources);
	}

    private Double calcularEstadoIndicadorCondicionValorMinimo(int periodo, int ano, double ejecutado, Double valorMeta, Double valorParametro) 
    {
        return new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorCondicionValorMinimo(valorParametro, ejecutado, valorMeta, this.log, ano, periodo, this.messageResources);
    }
    
    private Double calcularEstadoIndicadorCondicionBanda(double ejecutado, Double valorMetaSuperior, Double valorMetaInferior, Double valorParametroSuperior, Double valorParametroInferior) 
    {
        return new com.visiongc.servicio.strategos.calculos.util.CalculoUtil().calcularPorcentajeLogroIndicadorCondicionDeBandas(valorParametroSuperior, valorParametroInferior, valorMetaSuperior, valorMetaInferior, ejecutado);
	}
    
    private Double getValorMedicionParametroAlertaAnualCondicionBanda(Indicador indicador, boolean superior, int ano, Statement stmExt) 
    {
        Double valorParametro = null;

        int periodoHastaCierre = 0;
        Byte mesCierre = indicador.getMesCierre();

        if (mesCierre.byteValue() == 12)
        	periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
    	else 
    		periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), indicador.getFrecuencia().byteValue());

        List<Medicion> medicionesParametro = null;
        if (superior) 
        {
        	if ((indicador.getParametroSuperiorValorFijo() == null) && (indicador.getParametroSuperiorIndicadorId() != null)) 
        	{
        		Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroSuperiorIndicadorId(), stmExt);
        		if (indicadorParametro != null) 
        			medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), true, stmExt);
        	}
        }
        else if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
        {
        	Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
        	if (indicadorParametro != null) 
        		medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), true, stmExt);
        }

        if ((medicionesParametro != null) && (medicionesParametro.size() > 0)) 
        	valorParametro = ((Medicion)medicionesParametro.get(0)).getValor();

        return valorParametro;
    }
    
    private Double getValorMetaAnualInferiorEstadoAnualCondicionBanda(Indicador indicador, int ano, Statement stmExt) 
    {
        Double valorParametro = null;

        int periodoHastaCierre = 0;
        Byte mesCierre = indicador.getMesCierre();

        if (mesCierre.byteValue() == 12)
        	periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), ano);
        else 
        	periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), indicador.getFrecuencia().byteValue());

        List<Medicion> medicionesParametro = null;
        if ((indicador.getParametroInferiorValorFijo() == null) && (indicador.getParametroInferiorIndicadorId() != null)) 
        {
        	Indicador indicadorParametro = new IndicadorManager(pm, log, messageResources).Load(indicador.getParametroInferiorIndicadorId(), stmExt);
        	if (indicadorParametro != null) 
        		medicionesParametro = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorParametro.getIndicadorId(), indicadorParametro.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), new Boolean(true), indicadorParametro.getCorte(), indicadorParametro.getTipoCargaMedicion(), new Integer(ano), new Integer(ano), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre), true, stmExt);
        }
        if ((medicionesParametro != null) && (medicionesParametro.size() > 0)) 
        	valorParametro = ((Medicion)medicionesParametro.get(0)).getValor();

        return valorParametro;
    }

    private Double getValorParametroEstadoAnualCondicionBanda(Indicador indicador, boolean superior, Double valorMedicionParametroSuperior, Double valorMedicionParametroInferior) 
    {
        Double valorParametro = null;
        if (superior) 
        {
        	if (indicador.getParametroSuperiorValorFijo() != null)
        		valorParametro = indicador.getParametroSuperiorValorFijo();
        	else 
        		valorParametro = valorMedicionParametroSuperior;
        }
        else if (indicador.getParametroInferiorValorFijo() != null)
        	valorParametro = indicador.getParametroInferiorValorFijo();
        else 
        	valorParametro = valorMedicionParametroInferior;

        return valorParametro;
    }
    
    private List<Medicion> getAportesIndicadorEstadoPerspectivaObjetivo(Long planId, Long indicadorId, Byte frecuenciaIndicador, Byte frecuenciaPerspectivaObjetivo, List<?> perspectivaPlanEstados, int ano, Byte tipoEstado, boolean estadoPlan, Statement stmExt)
    {
    	int periodoMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaIndicador.byteValue(), ano);

    	List<Medicion> aportes = new ArrayList<Medicion>();
    	List<IndicadorEstado> estados = null;
    	List<Medicion> mediciones = null;
    	if (!estadoPlan)
    		estados = new PlanManager(pm, log, messageResources).getIndicadorEstados(indicadorId, planId, frecuenciaIndicador, tipoEstado, new Integer(ano), new Integer(ano), new Integer(1), new Integer(periodoMaximoIndicador), stmExt);
    	else 
    		mediciones = new MedicionManager(pm, log, messageResources).getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), new Integer(ano), new Integer(ano), new Integer(1), new Integer(periodoMaximoIndicador), frecuenciaIndicador, stmExt);

    	for (Iterator<?> iter = perspectivaPlanEstados.iterator(); iter.hasNext(); ) 
    	{
    		Object objeto = iter.next();
    		Integer periodo = null;
    		if ((objeto instanceof PerspectivaEstado)) 
    		{
    			PerspectivaEstado perspectivaEstado = (PerspectivaEstado)objeto;
    			periodo = perspectivaEstado.getPk().getPeriodo();
    		} 
    		else 
    		{
    			PlanEstado planEstado = (PlanEstado)objeto;
    			periodo = planEstado.getPk().getPeriodo();
    		}

    		Medicion medicion = new Medicion();

    		medicion.setMedicionId(new MedicionPK());
    		medicion.getMedicionId().setIndicadorId(indicadorId);
    		medicion.getMedicionId().setAno(new Integer(ano));
    		medicion.getMedicionId().setPeriodo(periodo);
    		medicion.getMedicionId().setSerieId(SerieTiempo.getSerieRealId());

    		Integer periodoAporte = PeriodoUtil.getPeriodoCompatible(frecuenciaPerspectivaObjetivo.byteValue(), periodo.intValue(), frecuenciaIndicador.byteValue());

    		if (periodoAporte != null)
    		{
    			if (!estadoPlan)
    			{
    				for (Iterator<IndicadorEstado> iterValores = estados.iterator(); iterValores.hasNext(); ) 
    				{
    					IndicadorEstado indicadorEstado = (IndicadorEstado)iterValores.next();
    					if ((indicadorEstado.getPk().getAno().intValue() == ano) && (indicadorEstado.getPk().getPeriodo().intValue() == periodoAporte.intValue())) 
    					{
    						medicion.setValor(indicadorEstado.getEstado());
    						break;
    					}
    				}
    			}
    			else 
    			{
    				for (Iterator<Medicion> iterValores = mediciones.iterator(); iterValores.hasNext(); ) 
    				{
    					Medicion medicionIndicador = (Medicion)iterValores.next();
    					if ((medicionIndicador.getMedicionId().getAno().intValue() == ano) && (medicionIndicador.getMedicionId().getPeriodo().intValue() == periodoAporte.intValue())) 
    					{
    						medicion.setValor(medicionIndicador.getValor());
    						break;
    					}
    				}
    			}
    		}
        
    		aportes.add(medicion);
    	}

    	return aportes;
    }
    
    private LapsoTiempo getLapsoTiempoEnPeriodosPorFrecuencia(List<LapsoTiempo> lapsosTiempoEnPeriodos, byte frecuencia)
    {
    	return (LapsoTiempo)lapsosTiempoEnPeriodos.get(frecuencia);
    }
    
    private int actualizarIndicadorObjetivo(Long indicadorId, Long planId, Byte frecuenciaPerspectivaObjetivo, Integer ano, int periodoInicio, int periodoFin, Statement stmExt) 
    {
    	int resultado = 10000;
    	
    	// Meta Anual
    	Meta meta = new Meta();
    	meta.setMetaId(new MetaPK());
    	meta.getMetaId().setIndicadorId(indicadorId);
    	meta.getMetaId().setPlanId(planId);
    	meta.getMetaId().setTipo(TipoMeta.getTipoMetaAnual());
    	meta.getMetaId().setSerieId(SerieTiempo.getSerieMetaId());
    	meta.getMetaId().setAno(ano);
    	meta.getMetaId().setPeriodo(new Integer(0));
    	meta.setValor(new Double(100.0D));
    	meta.setProtegido(new Boolean(false));
    	
    	new MetaManager(pm, log, messageResources).saveMeta(meta, stmExt);

    	// Meta Inicial
    	meta = new Meta();
    	meta.setMetaId(new MetaPK());
    	meta.getMetaId().setIndicadorId(indicadorId);
    	meta.getMetaId().setPlanId(planId);
    	meta.getMetaId().setTipo(TipoMeta.getTipoMetaValorInicial());
    	meta.getMetaId().setSerieId(SerieTiempo.getSerieValorInicialId());
    	meta.getMetaId().setAno(ano-1);
    	meta.getMetaId().setPeriodo(PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaPerspectivaObjetivo.byteValue(), ano.intValue()));
    	meta.setValor(new Double(100.0D));
    	meta.setProtegido(new Boolean(false));
        
    	resultado = new MetaManager(pm, log, messageResources).saveMeta(meta, stmExt);
    	
        Indicador indicador = new IndicadorManager(pm, log, messageResources).Load(indicadorId, stmExt);
        if (indicador != null) 
        {
        	resultado = calcularAlertaIndicador(indicador, stmExt);
        	resultado = calcularAlertaIndicadorPorPlan(indicador, planId, stmExt);
        	resultado = iniciarCalcularEstadoIndicadorPlan(indicador, planId, ano, ano, new Byte((byte) periodoInicio), new Byte((byte) periodoFin), indicador.getOrganizacion().getMesCierre().byteValue(), stmExt);
        	
        	List<Long> indicadores = new ArrayList<Long>();
        	indicadores.add(indicadorId);
        	resultado = new MedicionManager(pm, log, messageResources).actualizarUltimaMedicionIndicadores(indicadores, planId, stmExt);
        }
        
        return resultado;
    }
    
    private int calcularEstadoPlan(Plan plan, Perspectiva perspectivaPadre, Byte tipoEstado, int ano, List<LapsoTiempo> lapsosTiempoEnPeriodos, Statement stmExt)
    {
    	Map<String, Object> filtros = new HashMap<String, Object>();
    	int resultado = 10000;

    	filtros.put("plan_id", plan.getPlanId());
    	filtros.put("tipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
    	filtros.put("noCualitativos", "true");
    	filtros.put("guia", false);

    	List<Indicador> indicadoresAsociados = new IndicadorManager(this.pm, this.log, this.messageResources).getIndicadores(filtros, stmExt);
    	List<Long> indicadoresIds = new ArrayList<Long>();

    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
    	{
    		Indicador indicador = (Indicador)iter.next();

    		indicadoresIds.add(indicador.getIndicadorId());
    	}

    	List<Medicion> ultimasMediciones = new MedicionManager(this.pm, this.log, this.messageResources).getUltimasMedicionesIndicadores(indicadoresIds, SerieTiempo.getSerieRealId(), stmExt);

    	Integer anoBorrado = null;
    	Integer periodoBorrado = null;

    	if (ultimasMediciones.size() > 0) 
    	{
    		Medicion medicion = (Medicion)ultimasMediciones.get(0);
    		anoBorrado = medicion.getMedicionId().getAno();
    		periodoBorrado = new Integer(medicion.getMedicionId().getPeriodo().intValue() + 1);
    	}

    	new PlanManager(pm, log, messageResources).deletePlanEstados(plan.getPlanId(), tipoEstado, anoBorrado, null, periodoBorrado, null, stmExt);

    	List<IndicadorPlan> indicadoresPlan = new PlanManager(pm, log, messageResources).getIndicadoresPlan(plan.getPlanId(), stmExt);

    	Double pesoTotal = null;
    	Double pesoRestante = null;
    	int indicadoresSinPeso = 0;

    	for (Iterator<IndicadorPlan> iter = indicadoresPlan.iterator(); iter.hasNext(); ) 
    	{
    		IndicadorPlan indicadorPlan = (IndicadorPlan)iter.next();

    		if (indicadorPlan.getPeso() != null) 
    		{
    			if (pesoTotal == null)
    				pesoTotal = new Double(indicadorPlan.getPeso().doubleValue());
    			else
    				pesoTotal = new Double(pesoTotal.doubleValue() + indicadorPlan.getPeso().doubleValue());
    		}
    		else 
    			indicadoresSinPeso++;
    	}

    	if (pesoTotal != null) 
    	{
    		pesoTotal = new Double(pesoTotal.doubleValue() / 2.0D);
    		pesoRestante = new Double(100.0D - pesoTotal.doubleValue());
    	}
    	
    	if ((pesoRestante != null) && (pesoRestante.doubleValue() != new Double(0.0D).doubleValue()) && (indicadoresSinPeso > 0)) 
    		pesoRestante = new Double(pesoRestante.doubleValue() / indicadoresSinPeso);

    	filtros = new HashMap<String, Object>();
    	filtros.put("plan_id", plan.getPlanId().toString());
    	filtros.put("padre_Id", perspectivaPadre.getPerspectivaId());
    	List<Perspectiva> perspectivas = new PerspectivaManager(pm, log, messageResources).getPerspectivas(null, null, filtros, stmExt);
    	
    	List<Long> indicadorIds = new ArrayList<Long>();

    	filtros = new HashMap<String, Object>();
    	filtros.put("noCualitativos", "true");
    	filtros.put("guia", false);
    	for (Iterator<Perspectiva> iter = perspectivas.iterator(); iter.hasNext(); ) 
    	{
    		Perspectiva perspectiva = (Perspectiva)iter.next();

    		if (perspectiva.getPadreId() != null && perspectiva.getPadreId() != 0) 
    		{
    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
    				indicadorIds.add(perspectiva.getNlAnoIndicadorId());
    			else 
    				indicadorIds.add(perspectiva.getNlParIndicadorId());
    		}
    	}
    	
    	filtros.put("indicador_Id", indicadorIds);

    	if (indicadorIds.size() > 0)
    		indicadoresAsociados = new IndicadorManager(this.pm, this.log, this.messageResources).getIndicadores(filtros, stmExt);
    	else 
    		indicadoresAsociados = new ArrayList<Indicador>();
    	int total = indicadoresAsociados.size();

    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
    	{
    		Indicador indicador = (Indicador)iter.next();
    		IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
    		indicadorPlanPk.setIndicadorId(indicador.getIndicadorId());
    		indicadorPlanPk.setPlanId(plan.getPlanId());
    		IndicadorPlan indicadorPlan = new PlanManager(pm, log, messageResources).getIndicadorPlan(indicadorPlanPk.getPlanId(), indicadorPlanPk.getIndicadorId(), stmExt);
    		if (indicadorPlan != null && indicadorPlan.getPeso() != null) 
    			indicador.setPeso(indicadorPlan.getPeso());
    		else if (total != 0)
    			indicador.setPeso(new Double(new Double(100.0D) / total));
    	}

    	Byte frecuenciaPlan = new PlanManager(pm, log, messageResources).getFrecuenciaPlan(plan.getPlanId(), tipoEstado, stmExt);
    	int periodoMaximoPlan = 0;
    	if (frecuenciaPlan != null)
    		periodoMaximoPlan = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaPlan.byteValue(), ano);
    	List<PlanEstado> planEstados = new ArrayList<PlanEstado>();
    	for (int index = 1; index <= periodoMaximoPlan; index++) 
    	{
    		PlanEstado planEstado = new PlanEstado();

    		planEstado.setPk(new PlanEstadoPK());
    		planEstado.getPk().setPlanId(plan.getPlanId());
    		planEstado.getPk().setTipo(tipoEstado);
    		planEstado.getPk().setAno(new Integer(ano));
    		planEstado.getPk().setPeriodo(new Integer(index));
    		planEstado.setTotal(new Integer(0));
    		planEstados.add(planEstado);
    	}

    	for (Iterator<Indicador> iter = indicadoresAsociados.iterator(); iter.hasNext(); ) 
    	{
    		Indicador indicador = (Indicador)iter.next();

    		List<Medicion> aportesIndicador = null;
    		if (indicador.getTipoFuncion().byteValue() != TipoFuncionIndicador.getTipoFuncionPerspectiva().byteValue())
    			aportesIndicador = getAportesIndicadorEstadoPerspectivaObjetivo(plan.getPlanId(), indicador.getIndicadorId(), indicador.getFrecuencia(), frecuenciaPlan, planEstados, ano, tipoEstado, false, stmExt);
    		else 
    			aportesIndicador = getAportesIndicadorEstadoPerspectivaObjetivo(plan.getPlanId(), indicador.getIndicadorId(), indicador.getFrecuencia(), frecuenciaPlan, planEstados, ano, tipoEstado, true, stmExt);

    		int indexEstado = 0;
    		for (Iterator<Medicion> iterAportes = aportesIndicador.iterator(); iterAportes.hasNext(); ) 
    		{
    			Medicion aporte = (Medicion)iterAportes.next();
    			PlanEstado planEstado = (PlanEstado)planEstados.get(indexEstado);

    			if (aporte.getValor() != null) 
    			{
    				Double valor = aporte.getValor();
    				if (valor > 100D)
    					valor = 100D;
    				if (planEstado.getEstado() == null) 
    				{
    					if (pesoTotal == null) 
    						planEstado.setEstado(new Double(valor.doubleValue()));
    					else if (indicador.getPeso() != null)
    						planEstado.setEstado(new Double(valor.doubleValue() * indicador.getPeso().doubleValue() / 100.0D));
    					else 
    						planEstado.setEstado(new Double(valor.doubleValue() * pesoRestante.doubleValue() / 100.0D));
    					
    					planEstado.setTotalConValor(new Integer(1));
    				} 
    				else 
    				{
    					if (pesoTotal == null) 
    						planEstado.setEstado(new Double(planEstado.getEstado().doubleValue() + valor.doubleValue()));
    					else if (indicador.getPeso() != null)
    						planEstado.setEstado(new Double(planEstado.getEstado().doubleValue() + valor.doubleValue() * indicador.getPeso().doubleValue() / 100.0D));
    					else 
    						planEstado.setEstado(new Double(planEstado.getEstado().doubleValue() + valor.doubleValue() * pesoRestante.doubleValue() / 100.0D));
    					
    					planEstado.setTotalConValor(new Integer(planEstado.getTotalConValor().intValue() + 1));
    				}
    				
    				planEstado.setTotal(new Integer(planEstado.getTotal().intValue() + 1));
    			}
    			else if (indicador.getTipoFuncion().byteValue() != TipoFuncionIndicador.getTipoFuncionPerspectiva().byteValue()) 
    				planEstado.setTotal(new Integer(planEstado.getTotal().intValue() + 1));
    			else if ((indicador.getPeso() != null) && (indicador.getPeso().doubleValue() != Double.valueOf(0.0D).doubleValue())) 
    				planEstado.setTotal(new Integer(planEstado.getTotal().intValue() + 1));
    			indexEstado++;
    		}
    	}
	
    	int periodoInicio = 0;
    	if (frecuenciaPlan != null)
    		periodoInicio = getLapsoTiempoEnPeriodosPorFrecuencia(lapsosTiempoEnPeriodos, frecuenciaPlan.byteValue()).getPeriodoInicio().intValue();
    	int periodoFin = 0;
    	if (frecuenciaPlan != null)
    		periodoFin = getLapsoTiempoEnPeriodosPorFrecuencia(lapsosTiempoEnPeriodos, frecuenciaPlan.byteValue()).getPeriodoFin().intValue();
    	for (Iterator<PlanEstado> iterEstados = planEstados.iterator(); iterEstados.hasNext(); ) 
    	{
    		PlanEstado planEstado = (PlanEstado)iterEstados.next();

    		if ((planEstado.getPk().getAno().intValue() == ano) && (planEstado.getPk().getPeriodo().intValue() >= periodoInicio) && (planEstado.getPk().getPeriodo().intValue() <= periodoFin)) 
    		{
				if ((planEstado.getEstado() != null) && (pesoTotal == null) && planEstado.getTotalConValor() != null) 
					planEstado.setEstado(new Double(planEstado.getEstado().doubleValue() / planEstado.getTotalConValor().doubleValue()));
          
    			new PlanManager(pm, log, messageResources).savePlanEstado(planEstado.getPk().getPlanId(), tipoEstado, planEstado.getPk().getAno(), planEstado.getPk().getPeriodo(), planEstado.getEstado(), stmExt);

    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    				new MedicionManager(pm, log, messageResources).deleteMediciones(plan.getNlParIndicadorId(), SerieTiempo.getSerieRealId(), planEstado.getPk().getAno(), planEstado.getPk().getAno(), planEstado.getPk().getPeriodo(), planEstado.getPk().getPeriodo(), stmExt);
    			else 
    				new MedicionManager(pm, log, messageResources).deleteMediciones(plan.getNlAnoIndicadorId(), SerieTiempo.getSerieRealId(), planEstado.getPk().getAno(), planEstado.getPk().getAno(), planEstado.getPk().getPeriodo(), planEstado.getPk().getPeriodo(), stmExt);
          
    			if (planEstado.getEstado() != null) 
    			{
    				Medicion medicion = new Medicion();
    				medicion.setMedicionId(new MedicionPK());
    				if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    					medicion.getMedicionId().setIndicadorId(plan.getNlParIndicadorId());
    				else 
    					medicion.getMedicionId().setIndicadorId(plan.getNlAnoIndicadorId());

    				medicion.getMedicionId().setAno(planEstado.getPk().getAno());
    				medicion.getMedicionId().setPeriodo(planEstado.getPk().getPeriodo());
    				medicion.getMedicionId().setSerieId(SerieTiempo.getSerieRealId());
    				medicion.setValor(planEstado.getEstado());
    				medicion.setProtegido(new Boolean(false));

    				new MedicionManager(pm, log, messageResources).saveMedicion(medicion, stmExt);
    			}
          
    			if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    				new MetaManager(pm, log, messageResources).deleteMetas(plan.getNlParIndicadorId(), plan.getPlanId(), TipoMeta.getTipoMetaParcial(), planEstado.getPk().getAno(), planEstado.getPk().getAno(), planEstado.getPk().getPeriodo(), planEstado.getPk().getPeriodo(), SerieTiempo.getSerieMetaId(), stmExt);
    			else 
    				new MetaManager(pm, log, messageResources).deleteMetas(plan.getNlAnoIndicadorId(), plan.getPlanId(), TipoMeta.getTipoMetaParcial(), planEstado.getPk().getAno(), planEstado.getPk().getAno(), planEstado.getPk().getPeriodo(), planEstado.getPk().getPeriodo(), SerieTiempo.getSerieMetaId(), stmExt);

    			if (planEstado.getEstado() != null) 
    			{
    				Meta meta = new Meta();
    				meta.setMetaId(new MetaPK());
    				
    				if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
    					meta.getMetaId().setIndicadorId(plan.getNlParIndicadorId());
    				else 
    					meta.getMetaId().setIndicadorId(plan.getNlAnoIndicadorId());
            
    				meta.getMetaId().setPlanId(plan.getPlanId());
    				meta.getMetaId().setTipo(TipoMeta.getTipoMetaParcial());
    				meta.getMetaId().setSerieId(SerieTiempo.getSerieMetaId());
    				meta.getMetaId().setAno(planEstado.getPk().getAno());
    				meta.getMetaId().setPeriodo(planEstado.getPk().getPeriodo());
    				meta.setValor(new Double(100.0D));
    				meta.setProtegido(new Boolean(false));
            
    				new MetaManager(pm, log, messageResources).saveMeta(meta, stmExt);
    			}
    		}
    	}

    	if (frecuenciaPlan != null)
    	{
        	if (tipoEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
        		actualizarIndicadorObjetivo(plan.getNlParIndicadorId(), plan.getPlanId(), frecuenciaPlan, ano, periodoInicio, periodoFin, stmExt);
        	else 
        		actualizarIndicadorObjetivo(plan.getNlAnoIndicadorId(), plan.getPlanId(), frecuenciaPlan, ano, periodoInicio, periodoFin, stmExt);
    	}

    	return resultado;
    }
}

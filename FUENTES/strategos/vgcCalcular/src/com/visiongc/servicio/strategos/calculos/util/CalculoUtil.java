/**
 * 
 */
package com.visiongc.servicio.strategos.calculos.util;

import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;

/**
 * @author Kerwin
 *
 */
public class CalculoUtil 
{
	public static Double calcularPorcentajeLogroIndicadorRetoAumento(Double valorInicial, Double valorEjecutado, Double valorMeta, Double parametro, Byte tipoIndicadorEstado, StringBuffer log, Integer ano, Integer periodo, VgcMessageResources messageResources)
	{
		Double logro = null;
		boolean divisionPorCero = false;
		
		if ((valorEjecutado != null) && (valorMeta != null)) 
		{
			if (valorEjecutado > 0 && valorMeta > 0)
			{
				if (tipoIndicadorEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoAnual().byteValue())
				{
					if (valorInicial != null && valorEjecutado <= valorInicial)
						logro = new Double(0.0D);
					else if (valorInicial != null && valorEjecutado > valorInicial)
					{
						if ((valorMeta.doubleValue() - valorInicial.doubleValue()) != new Double(0.0D))
							logro = ((valorEjecutado.doubleValue() - valorInicial.doubleValue()) / (valorMeta.doubleValue() - valorInicial.doubleValue())) * 100.0D;
						else
							divisionPorCero = true;
					}
					else if (valorInicial == null)
						logro = new Double(0.0D);
				}
				else if (tipoIndicadorEstado.byteValue() == TipoIndicadorEstado.getTipoIndicadorEstadoParcial().byteValue())
				{
					if (valorMeta.doubleValue() != new Double(0.0D))
						logro = (valorEjecutado.doubleValue() / valorMeta.doubleValue()) * 100.0D;
					else
						divisionPorCero = true;
				}
			}
			else if (valorEjecutado <= 0 && valorMeta > 0)
				logro = new Double(0.0D);
			else if (valorEjecutado < 0 && valorMeta < 0)
			{
				if (valorEjecutado.doubleValue() < valorMeta.doubleValue())
				{
					if (valorEjecutado.doubleValue() != new Double(0.0D))
						logro = Math.abs((valorMeta.doubleValue() / valorEjecutado.doubleValue()) * 100.0D);
					else
						divisionPorCero = true;
				}
				else
				{
					if (valorMeta.doubleValue() != new Double(0.0D))
						logro = Math.abs((valorEjecutado.doubleValue() / valorMeta.doubleValue()) * 100.0D);
					else
						divisionPorCero = true;
				}
			}
			else if (valorMeta.doubleValue() == new Double(0.0D))
			{
				if (valorEjecutado.doubleValue() >= valorMeta.doubleValue())
					logro = new Double(100.0D);
				else if (parametro != null)
				{
					if (valorEjecutado.doubleValue() <= parametro.doubleValue())
						logro = new Double(0.0D);
					else if (parametro.doubleValue() < valorEjecutado.doubleValue() && parametro.doubleValue() > new Double(0.0D))
					{
						if (parametro.doubleValue() != new Double(0.0D))
							logro = new Double(100.0D) * (1 - (valorEjecutado.doubleValue() / parametro.doubleValue()));
						else
							logro = new Double(0.0D);
					}
					else
						logro = new Double(0.0D);
				}
				else if (parametro == null)
					logro = new Double(0.0D);
			}
			else if (valorMeta.doubleValue() == new Double(0.0D) && valorEjecutado.doubleValue() == new Double(0.0D))
				logro = new Double(100.0D);
		}
		
        if (divisionPorCero && log != null) 
        {
        	String[] argsMensajeLog = new String[2];
        	argsMensajeLog[0] = Integer.toString(periodo);
        	argsMensajeLog[1] = Integer.toString(ano);
        	log.append("* _ " + messageResources.getResource("calcularindicadores.estado.plan.divisionporcero", argsMensajeLog) + "\n");
        }

		return logro;
	}

	public static Double calcularPorcentajeLogroIndicadorRetoDisminucion(Double valorInicial, Double valorEjecutado, Double valorMeta, Double parametro, StringBuffer log, Integer ano, Integer periodo, VgcMessageResources messageResources)
	{
		Double logro = null;
		boolean divisionPorCero = false;

		if ((valorEjecutado != null) && (valorMeta != null)) 
		{
			if (valorEjecutado.doubleValue() <= valorMeta.doubleValue())
				logro = new Double(100.0D);
			else if (valorInicial != null && valorEjecutado.doubleValue() >= valorInicial.doubleValue())
				logro = new Double(0.0D);
			else if (valorInicial == null && parametro != null && valorMeta.doubleValue() != new Double(0.0D))
			{
				if (valorEjecutado.doubleValue() > parametro.doubleValue())
					logro = new Double(0.0D);
				else if (valorMeta.doubleValue() <= valorEjecutado.doubleValue() && valorEjecutado.doubleValue() <= parametro.doubleValue())
				{
					if ((parametro.doubleValue() - valorMeta.doubleValue()) != new Double(0.0D))
						logro = new Double(((parametro.doubleValue() - valorEjecutado.doubleValue()) / (parametro.doubleValue() - valorMeta.doubleValue())) * 100.0D);
					else
						divisionPorCero = true;
				}
			}
			else if (valorInicial == null && parametro == null && valorMeta.doubleValue() != new Double(0.0D))
				logro = new Double(0.0D);
			else if ((valorInicial != null && valorInicial.doubleValue() == new Double(0.0D)) || valorMeta.doubleValue() == new Double(0.0D)) 
				logro = new Double(0.0D);
			else if (valorInicial != null && (valorInicial.doubleValue() - valorMeta.doubleValue()) != new Double(0.0D))
				logro = new Double(((valorInicial.doubleValue() - valorEjecutado.doubleValue()) / (valorInicial.doubleValue() - valorMeta.doubleValue())) * 100.0D);
			else if (valorInicial != null && (valorInicial.doubleValue() - valorMeta.doubleValue()) == new Double(0.0D))
				divisionPorCero = true;
		}

        if (divisionPorCero && log != null) 
        {
        	String[] argsMensajeLog = new String[2];
        	argsMensajeLog[0] = Integer.toString(periodo);
        	argsMensajeLog[1] = Integer.toString(ano);
        	log.append("* _ " + messageResources.getResource("calcularindicadores.estado.plan.divisionporcero", argsMensajeLog) + "\n");
        }
		
		return logro;
	}

	public static Double calcularPorcentajeLogroIndicadorCondicionValorMinimo(Double parametroAcotamiento, Double valorEjecutado, Double valorMeta, StringBuffer log, Integer ano, Integer periodo, VgcMessageResources messageResources)
	{
		Double logro = null;
		boolean divisionPorCero = false;
		
		if ((valorEjecutado != null) && (valorMeta != null)) 
		{
			if (valorMeta.doubleValue() != new Double(0.0D))
			{
				if (parametroAcotamiento != null && valorEjecutado.doubleValue() < parametroAcotamiento.doubleValue())
					logro = new Double(0.0D);
				else if (parametroAcotamiento == null && valorEjecutado.doubleValue() < valorMeta.doubleValue())
					logro = new Double(0.0D);
				else
		        {
					if (parametroAcotamiento == null)
						parametroAcotamiento= new Double(0.0D);
					
					if (valorEjecutado.doubleValue() > valorMeta.doubleValue()) 
						logro = new Double(100.0D);
					else if (valorEjecutado.doubleValue() == valorMeta.doubleValue() && valorEjecutado.doubleValue() == parametroAcotamiento.doubleValue())
						logro = new Double(100.0D);
					else if ((valorMeta.doubleValue() - parametroAcotamiento.doubleValue()) == new Double(0.0D))
						divisionPorCero = true;
					else if ((valorMeta.doubleValue() - parametroAcotamiento.doubleValue()) != new Double(0.0D))
						logro = new Double(((valorEjecutado.doubleValue() - parametroAcotamiento.doubleValue()) / (valorMeta.doubleValue() - parametroAcotamiento.doubleValue())) * 100.0D);
		        }
			}
			else if (parametroAcotamiento != null && parametroAcotamiento.doubleValue() == new Double(0.0D) && valorMeta.doubleValue() == new Double(0.0D))
				logro = new Double(0.0D);
			else if (parametroAcotamiento != null && parametroAcotamiento.doubleValue() != new Double(0.0D) && valorMeta.doubleValue() == new Double(0.0D))
	        {
				if (parametroAcotamiento != null && valorEjecutado.doubleValue() < parametroAcotamiento.doubleValue())
					logro = new Double(0.0D);
				else
		        {
					if (valorEjecutado.doubleValue() > valorMeta.doubleValue()) 
						logro = new Double(100.0D);
					else if (valorEjecutado.doubleValue() == valorMeta.doubleValue() && valorEjecutado.doubleValue() == parametroAcotamiento.doubleValue())
						logro = new Double(100.0D);
					else if (parametroAcotamiento.doubleValue() == new Double(0.0D))
						divisionPorCero = true;
					else if (parametroAcotamiento.doubleValue() != new Double(0.0D))
						logro = Math.abs((valorEjecutado.doubleValue() / parametroAcotamiento.doubleValue()) * 100.0D);
		        }
	        }
			else if (parametroAcotamiento == null || parametroAcotamiento.doubleValue() == new Double(0.0D))
				logro = new Double(0.0D);
		}

        if (divisionPorCero && log != null) 
        {
        	String[] argsMensajeLog = new String[2];
        	argsMensajeLog[0] = Integer.toString(periodo);
        	argsMensajeLog[1] = Integer.toString(ano);
        	log.append("* _ " + messageResources.getResource("calcularindicadores.estado.plan.divisionporcero", argsMensajeLog) + "\n");
        }
		
		return logro;
	}
	
	public static Double calcularPorcentajeLogroIndicadorCondicionValorMaximo(Double parametroAcotamiento, Double valorEjecutado, Double valorMeta, StringBuffer log, Integer ano, Integer periodo, VgcMessageResources messageResources)
	{
		Double logro = null;
		boolean divisionPorCero = false;

		if ((valorEjecutado != null) && (valorMeta != null)) 
		{
			if (valorMeta.doubleValue() != new Double(0.0D))
			{
				if (parametroAcotamiento != null && valorEjecutado.doubleValue() > parametroAcotamiento.doubleValue())
					logro = new Double(0.0D);
				else if (parametroAcotamiento == null && valorEjecutado.doubleValue() > valorMeta.doubleValue())
					logro = new Double(0.0D);
				else
		        {
					if (parametroAcotamiento == null)
						parametroAcotamiento= new Double(0.0D);
					
					if (valorEjecutado.doubleValue() < valorMeta.doubleValue()) 
						logro = new Double(100.0D);
					else if (valorEjecutado.doubleValue() == valorMeta.doubleValue() && valorEjecutado.doubleValue() == parametroAcotamiento.doubleValue())
						logro = new Double(100.0D);
					else if ((parametroAcotamiento.doubleValue() - valorMeta.doubleValue()) == new Double(0.0D))
						divisionPorCero = true;
					else if ((valorMeta.doubleValue() - parametroAcotamiento.doubleValue()) != new Double(0.0D))
						logro = new Double(((parametroAcotamiento.doubleValue() - valorEjecutado.doubleValue()) / (parametroAcotamiento.doubleValue() - valorMeta.doubleValue())) * 100.0D);
		        }
			}
			else if (parametroAcotamiento != null && parametroAcotamiento.doubleValue() == new Double(0.0D) && valorMeta.doubleValue() == new Double(0.0D))
				logro = new Double(0.0D);
			else if (parametroAcotamiento != null && parametroAcotamiento.doubleValue() != new Double(0.0D) && valorMeta.doubleValue() == new Double(0.0D))
	        {
				if (parametroAcotamiento != null && valorEjecutado.doubleValue() > parametroAcotamiento.doubleValue())
					logro = new Double(0.0D);
				else
		        {
					if (valorEjecutado.doubleValue() < valorMeta.doubleValue()) 
						logro = new Double(100.0D);
					else if (valorEjecutado.doubleValue() == valorMeta.doubleValue() && valorEjecutado.doubleValue() == parametroAcotamiento.doubleValue())
						logro = new Double(100.0D);
					else if (parametroAcotamiento.doubleValue() == new Double(0.0D))
						divisionPorCero = true;
					else if (parametroAcotamiento.doubleValue() != new Double(0.0D))
						logro = Math.abs((valorEjecutado.doubleValue() / parametroAcotamiento.doubleValue()) * 100.0D);
		        }
	        }
			else if (parametroAcotamiento == null || parametroAcotamiento.doubleValue() == new Double(0.0D))
				logro = new Double(0.0D);
		}

        if (divisionPorCero && log != null) 
        {
        	String[] argsMensajeLog = new String[2];
        	argsMensajeLog[0] = Integer.toString(periodo);
        	argsMensajeLog[1] = Integer.toString(ano);
        	log.append("* _ " + messageResources.getResource("calcularindicadores.estado.plan.divisionporcero", argsMensajeLog) + "\n");
        }

        return logro;
	}

	public static Double calcularPorcentajeLogroIndicadorCondicionDeBandas(Double parametroAcotamientoSuperior, Double parametroAcotamientoInferior, Double valorMetaSuperior, Double valorMetaInferior, Double valorEjecutado)
	{
		Double logro = null;
		if (valorEjecutado != null)
        {
			if (parametroAcotamientoSuperior != null || parametroAcotamientoInferior != null)
			{
		    	if (parametroAcotamientoSuperior != null && valorEjecutado.doubleValue() >= parametroAcotamientoSuperior.doubleValue())
		    		logro = new Double(0.0D);
		    	else if (parametroAcotamientoInferior != null && valorEjecutado.doubleValue() <= parametroAcotamientoInferior.doubleValue())
		    		logro = new Double(0.0D);
		    	else if (parametroAcotamientoSuperior != null && parametroAcotamientoInferior != null && valorEjecutado.doubleValue() < parametroAcotamientoSuperior.doubleValue() && valorEjecutado.doubleValue() > parametroAcotamientoInferior.doubleValue())
		    		logro = new Double(100.0D);
		    	else if (parametroAcotamientoSuperior == null && parametroAcotamientoInferior == null)
		    		logro = new Double(0.0D);
			}
			else if (valorMetaSuperior != null || valorMetaInferior != null)
			{
		    	if (valorMetaSuperior != null && valorEjecutado.doubleValue() >= valorMetaSuperior.doubleValue())
		    		logro = new Double(0.0D);
		    	else if (valorMetaInferior != null && valorEjecutado.doubleValue() <= valorMetaInferior.doubleValue())
		    		logro = new Double(0.0D);
		    	else if (valorMetaSuperior != null && valorMetaInferior != null && valorEjecutado.doubleValue() < valorMetaSuperior.doubleValue() && valorEjecutado.doubleValue() > valorMetaInferior.doubleValue())
		    		logro = new Double(100.0D);
		    	else if (valorMetaSuperior == null && valorMetaInferior == null)
		    		logro = new Double(0.0D);
			}
        }

		return logro;
	}
}

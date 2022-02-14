/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.indicador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.visiongc.servicio.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.servicio.strategos.indicadores.model.Formula;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.InsumoFormula;
import com.visiongc.servicio.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicador;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.servicio.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.web.importar.dal.clase.ClaseManager;
import com.visiongc.servicio.web.importar.dal.organizacion.OrganizacionManager;
import com.visiongc.servicio.web.importar.dal.perspectiva.PerspectivaManager;
import com.visiongc.servicio.web.importar.dal.util.DalUtil;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class IndicadorManager 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public IndicadorManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}
	
	public List<Indicador> getIndicadores(Map<?, ?> filtros, Statement stmExt) 
	{
		String CLASS_METHOD = "IndicadorManager.getIndicadores";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String tablasConsulta = "";
		String sql = " where ";
		String ordenConsulta = " order by ";
		boolean ordenNombre = false;
		boolean hayOrden = false;
		boolean hayCondicionesConsulta = false;

		Indicador indicador;

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
					sql = sql + "lower(indicador." + fieldName + ")" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "like") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("organizacion_Id")) 
				{
					sql = sql + "indicador." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("clase_Id")) 
				{
					sql = sql + "indicador." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("indicador_Id")) 
				{
					sql = sql + "indicador." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("frecuencia")) 
				{
					sql = sql + "indicador." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("tipo")) 
				{
					sql = sql + "indicador.tipo" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("excluirTipoFuncion")) 
				{
					sql = sql + "indicador.tipo" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "!=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("frecuenciasContenidas")) 
				{
					List<Frecuencia> frecuencias = Frecuencia.getFrecuenciasContenidas(Byte.parseByte(fieldValue));
					sql = sql + "(";
					for (int i = 0; i < frecuencias.size(); i++) 
					{
						Frecuencia frecuencia = (Frecuencia)frecuencias.get(i);
						sql = sql + "indicador.frecuencia = " + frecuencia.getFrecuenciaId().toString() + " or ";
					}
					sql = sql.substring(0, sql.length() - 4);
					sql = sql + ") and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("noCualitativos")) 
				{
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) 
					{
						sql = sql + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} 
				else if (fieldName.equals("noCompuestos")) 
				{
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) 
					{
						sql = sql + "indicador.naturaleza != " + Naturaleza.getNaturalezaFormula().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaIndice().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaPromedio() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSumatoria().toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} 
				else if (fieldName.equals("soloCompuestos")) 
				{
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) 
					{
						sql = sql + "indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoNominal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and indicador.naturaleza != " + Naturaleza.getNaturalezaSimple().toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} 
				else if (fieldName.equals("excluirIds")) 
				{
					String[] ids = fieldValue.split(",");
					for (int i = 0; i < ids.length; i++) 
					{
						Long id = new Long(ids[i]);
						sql = sql + "indicador.indicador_Id != " + id.toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} 
				else if (fieldName.equals("excluirId")) 
				{
					sql = sql + "indicador." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "!=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("perspectiva_id") && fieldValue != null && (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL")))
				{
					tablasConsulta = tablasConsulta + ", indicador_por_perspectiva ";
					sql = sql + "indicador.indicador_Id = indicador_por_perspectiva.indicador_Id and indicador_por_perspectiva." + fieldName + " " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("perspectiva_id") && (filtros.get(fieldName) instanceof List)) 
				{
					tablasConsulta = tablasConsulta + ", indicador_por_perspectiva ";
					sql = sql + "indicador.indicador_Id = indicador_por_perspectiva.indicador_Id and indicador_por_perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("perspectiva_id") && fieldValue != null)
				{
					tablasConsulta = tablasConsulta + ", indicador_por_perspectiva ";
					sql = sql + "indicador.indicador_Id = indicador_por_perspectiva.indicador_Id and indicador_por_perspectiva." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("plan_id") && fieldValue != null && (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL"))) 
				{
					tablasConsulta = tablasConsulta + ", indicador_por_plan ";
					sql = sql + "indicador.indicador_Id = indicador_por_plan.indicador_Id and indicador_por_plan." + fieldName + " " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("plan_id") && (filtros.get(fieldName) instanceof List)) 
				{
					tablasConsulta = tablasConsulta + ", indicador_por_plan ";
					sql = sql + "indicador.indicador_Id = indicador_por_plan.indicador_Id and indicador_por_plan." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("plan_id") && fieldValue != null) 
				{
					tablasConsulta = tablasConsulta + ", indicador_por_plan ";
					sql = sql + "indicador.indicador_Id = indicador_por_plan.indicador_Id and indicador_por_plan." + fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("codigo_enlace") && (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL")))
				{
					sql = sql + "indicador." + fieldName + " " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				}
				else if (fieldName.equals("codigo_enlace") && !fieldValue.equals(""))
				{
					sql = sql + "indicador." + fieldName + " = " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("indicadoresLogroPlanId")) 
				{
					tablasConsulta = tablasConsulta + ", planes ";
					sql = sql + "(indicador.indicador_Id = planes.nl_Ano_Indicador_Id or indicador.indicador_Id = planes.nl_Par_Indicador_Id) and planes.plan_Id" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} 
				else if (fieldName.equals("guia")) 
				{
					sql = sql + "indicador.lag_lead" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				}
				else if (fieldName.equals("tipoFuncion")) 
				{
					sql = sql + "indicador.tipo" + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				}
				else if (fieldName.equals("orderBy")) 
				{
					List<String> idList = (List<String>)filtros.get(fieldName);
					int j = 1;
					boolean desc = false;
					for (Iterator<String> i = idList.listIterator(); i.hasNext(); ) 
					{
						String param = (String)i.next();
						if (j % 2 == 1) 
						{
							if (param.equals("asc"))
								desc = false;
							else 
								desc = true;
						}
						if (j % 2 == 0) 
						{
							if (param.equals("nombre")) 
								ordenNombre = true;
							if (desc)
								ordenConsulta = ordenConsulta + "indicador." + param + " desc, ";
							else 
								ordenConsulta = ordenConsulta + "indicador." + param + " asc, ";
							hayOrden = true;
						}
						j++;
					}
				}
			}
		}
		sql = sql + "indicador.organizacion_Id = organizacion.organizacion_Id and "; 

		if (hayCondicionesConsulta)
			sql = sql.substring(0, sql.length() - 5);
		else 
			sql = "";

		if (!ordenNombre)
			ordenConsulta = ordenConsulta + "indicador.nombre asc";
		else if (hayOrden)
			ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
		else 
			ordenConsulta = "";

		String objetoConsulta = "distinct " +
				"indicador.indicador_id, " +
				"indicador.nombre, " +
				"indicador.codigo_enlace, " +
				"indicador.frecuencia, " +
				"indicador.corte, " +
				"indicador.tipo_medicion, " +
				"organizacion.mes_cierre, " +
				"indicador.VALOR_INICIAL_CERO, " +
				"indicador.CARACTERISTICA, " +
				"indicador.NATURALEZA, " +
				"indicador.TIPO_SUMA, " +
				"indicador.Organizacion_id, " +
				"indicador.clase_id, " +
				"indicador.alerta_n2_ind_id, " +
				"indicador.alerta_n1_ind_id, " +
				"indicador.alerta_min_max, " +
				"indicador.alerta_meta_n1, " +
				"indicador.alerta_meta_n2, " +
				"indicador.alerta_n1_tipo, " +
				"indicador.alerta_n2_tipo, " +
				"indicador.alerta_n1_fv, " +
				"indicador.alerta_n2_fv, " +
				"indicador.psuperior_id, " +
				"indicador.pinferior_id, " +
				"indicador.psuperior_vf, " +
				"indicador.pinferior_vf, " +
				"indicador.tipo, " +
				"indicador.asignar_Inventario, " +
				"indicador.unidad_Id";

		sql = "select " + objetoConsulta + " from indicador, organizacion " + tablasConsulta + sql + ordenConsulta;
		
		List<Indicador> indicadores = new ArrayList<Indicador>();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		Formula formulaIndicador = null;

		String indId = null;
		String nombre = null;
		String codEnlace = null;
		String xFrecuencia = null;
		String corte = null;
		String mescierre = null;
		String tipomedicion = null;
		String valorInicialCero = null;
		String caracteristica = null;
		String naturaleza = null;
		String tipoSuma = null;
		String orgId = null;
		String clsId = null;
		String alertaN1IndId = null;
		String alertaN2IndId = null;
		String alertaMinMax = null;
		String alertaMetaN1 = null;
		String alertaMetaN2 = null;
		String alertaN1Tipo = null;
		String alertaN2Tipo = null;
		String alertaN1Fv = null;
		String alertaN2Fv = null;
		String psuperiorId = null;
		String pinferiorId = null;
		String psuperiorVf = null;
		String pinferiorVf = null;
		String tipoFuncion = null;
		String asignarInventario = null;
		String unidadId = null;
		
		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			
			rs = stm.executeQuery(sql);
			
			while (rs.next()) 
			{
				indId = rs.getString("indicador_id");
				nombre = rs.getString("nombre");
				codEnlace = rs.getString("codigo_enlace");
				xFrecuencia = rs.getString("frecuencia");
				corte = rs.getString("corte");
				mescierre = rs.getString("mes_cierre");
				tipomedicion = rs.getString("tipo_medicion");
				valorInicialCero = rs.getString("valor_inicial_cero");
				caracteristica = rs.getString("CARACTERISTICA");
				naturaleza = rs.getString("NATURALEZA");
				tipoSuma = rs.getString("TIPO_SUMA");
				orgId = rs.getString("Organizacion_id");
				clsId = rs.getString("clase_id");
				alertaN2IndId = rs.getString("alerta_n2_ind_id");
				alertaN1IndId = rs.getString("alerta_n1_ind_id");
				alertaMinMax = rs.getString("alerta_min_max");
				alertaMetaN1 = rs.getString("alerta_meta_n1");
				alertaMetaN2 = rs.getString("alerta_meta_n2");
				alertaN1Tipo = rs.getString("alerta_n1_tipo");
				alertaN2Tipo = rs.getString("alerta_n2_tipo");
				alertaN1Fv = rs.getString("alerta_n1_fv");
				alertaN2Fv = rs.getString("alerta_n2_fv");
				psuperiorId = rs.getString("psuperior_id");
				pinferiorId = rs.getString("pinferior_id");
				psuperiorVf = rs.getString("psuperior_vf");
				pinferiorVf = rs.getString("pinferior_vf");
				tipoFuncion = rs.getString("tipo");
				asignarInventario = rs.getString("asignar_Inventario");
				unidadId = rs.getString("unidad_id");

				if (indId != null)
				{
					indicador = new Indicador();
					if (indId != null)
						indicador.setIndicadorId(Long.parseLong(indId));
					if (nombre != null)
						indicador.setNombre(nombre);
					if (codEnlace != null)
						indicador.setCodigoEnlace(codEnlace);
					if (xFrecuencia != null)
						indicador.setFrecuencia(Byte.parseByte(xFrecuencia));
					if (corte != null)
						indicador.setCorte(Byte.parseByte(corte));
					OrganizacionStrategos organizacion = new OrganizacionStrategos(); 
					indicador.setOrganizacion(organizacion);
					if (mescierre != null)
						indicador.getOrganizacion().setMesCierre(Byte.parseByte(mescierre));
					if (tipomedicion != null)
						indicador.setTipoCargaMedicion(Byte.parseByte(tipomedicion));
					if (valorInicialCero != null)
						indicador.setValorInicialCero(valorInicialCero.equals("1") ? true : false);
					if (caracteristica != null)
						indicador.setCaracteristica(Byte.parseByte(caracteristica));
					if (naturaleza != null)
						indicador.setNaturaleza(Byte.parseByte(naturaleza));
					if (tipoSuma != null)
						indicador.setTipoSumaMedicion(Byte.parseByte(tipoSuma));
					if (orgId != null)
						indicador.setOrganizacionId(Long.parseLong(orgId));
					if (clsId != null)
						indicador.setClaseId(Long.parseLong(clsId));
					if (alertaN1IndId != null)
						indicador.setAlertaIndicadorIdZonaVerde(Long.parseLong(alertaN1IndId));
					if (alertaN2IndId != null)
						indicador.setAlertaIndicadorIdZonaAmarilla(Long.parseLong(alertaN2IndId));
					if (alertaMinMax != null)
						indicador.setAlertaMinMax(Integer.parseInt(alertaMinMax));
					if (alertaMetaN1 != null)
						indicador.setAlertaMetaZonaVerde(new Double(VgcFormatter.parsearNumeroFormateado(alertaMetaN1)));
					if (alertaMetaN2 != null)
						indicador.setAlertaMetaZonaAmarilla(new Double(VgcFormatter.parsearNumeroFormateado(alertaMetaN2)));
					if (alertaN1Tipo != null)
						indicador.setAlertaTipoZonaVerde(Byte.parseByte(alertaN1Tipo));
					if (alertaN2Tipo != null)
						indicador.setAlertaTipoZonaAmarilla(Byte.parseByte(alertaN2Tipo));
					if (alertaN1Fv != null)
						indicador.setAlertaValorVariableZonaVerde(alertaN1Fv.equals("1") ? true : false);
					if (alertaN2Fv != null)
						indicador.setAlertaValorVariableZonaAmarilla(alertaN2Fv.equals("1") ? true : false);
					if (psuperiorId != null)
						indicador.setParametroSuperiorIndicadorId(Long.parseLong(psuperiorId));
					if (pinferiorId != null)
						indicador.setParametroInferiorIndicadorId(Long.parseLong(pinferiorId));
					if (psuperiorVf != null)
						indicador.setParametroSuperiorValorFijo(new Double(VgcFormatter.parsearNumeroFormateado(psuperiorVf)));
					if (pinferiorVf != null)
						indicador.setParametroInferiorValorFijo(new Double(VgcFormatter.parsearNumeroFormateado(pinferiorVf)));
					if (tipoFuncion != null)
						indicador.setTipoFuncion(Byte.parseByte(tipoFuncion));
					if (asignarInventario != null)
						indicador.setAsignarInventario(asignarInventario.equals("1") ? true : false);
					if (unidadId != null)
						indicador.setUnidadId(Long.parseLong(unidadId));

					indicadores.add(indicador);
				}
			}
			rs.close();
			
			String serieId = null;
	    	for (Iterator<?> iter = indicadores.iterator(); iter.hasNext(); ) 
	        {
	        	indicador = (Indicador)iter.next();
				indicador.setSeriesIndicador(new HashSet<SerieIndicador>());
				indicador.getSeriesIndicador().clear();
				SerieIndicador serieIndicador = null;
	        	
  				sql = "SELECT ";
  				sql = sql + "serie_id ";
  				sql = sql + "FROM serie_indicador ";
  				sql = sql + "WHERE indicador_id = " + indicador.getIndicadorId().toString();
  				sql = sql + " ORDER BY serie_id";

  				rs = stm.executeQuery(sql);

  				while (rs.next())
  				{
  					serieId = rs.getString("serie_id");
  					
  					serieIndicador = new SerieIndicador();
  			        serieIndicador.setPk(new SerieIndicadorPK());
  			        if (serieId != null)
  			        	serieIndicador.getPk().setSerieId(Long.parseLong(serieId));
  			        
  			        indicador.getSeriesIndicador().add(serieIndicador);
  				}
  				rs.close();
  				
  				if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue() || 
  					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaIndice().byteValue() ||
  					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaPromedio().byteValue() ||
  					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaSumatoria().byteValue())
  				{
	  		    	for (Iterator<?> iterSerie = indicador.getSeriesIndicador().iterator(); iterSerie.hasNext(); ) 
	  		        {
	  		    		serieIndicador = (SerieIndicador)iterSerie.next();
		  			    serieIndicador.setFormulas(new HashSet<Object>());
		  			    serieIndicador.getFormulas().clear();

		  				sql = "SELECT ";
		  				sql = sql + "Expresion ";
		  				sql = sql + "FROM Formula ";
		  				sql = sql + "WHERE indicador_id = " + indicador.getIndicadorId().toString();
		  				sql = sql + " AND serie_id = " + serieIndicador.getPk().getSerieId().toString();
	
		  				rs = stm.executeQuery(sql);
		  				
		  				while (rs.next())
		  				{
		  		    		formulaIndicador = new Formula();
			  			    formulaIndicador.setExpresion(rs.getString("Expresion"));
			  			    
			  			    serieIndicador.getFormulas().add(formulaIndicador);
		  				}
		  				rs.close();

	  		    		for (Iterator<?> iterFormula = serieIndicador.getFormulas().iterator(); iterFormula.hasNext(); )
		  		    	{
		  		    		Formula formula = (Formula)iterFormula.next();
		  		    		formula.setInsumos(new HashSet<Object>());
		  		    		
			  				sql = "SELECT ";
			  				sql = sql + "insumo_serie_id, ";
			  				sql = sql + "indicador_id ";
			  				sql = sql + "FROM Conjunto_Formula ";
			  				sql = sql + "WHERE padre_id = " + indicador.getIndicadorId().toString();
			  				sql = sql + " AND serie_id = " + serieIndicador.getPk().getSerieId().toString();
		
			  				rs = stm.executeQuery(sql);

			  				while (rs.next())
			  				{
			  					indId = rs.getString("indicador_id");
			  					serieId = rs.getString("insumo_serie_id");

			  					InsumoFormula insumoFormula = new InsumoFormula();
		  		    			insumoFormula.setPk(new InsumoFormulaPK());
		  		    			insumoFormula.setPadre(indicador);
		  		    			insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		  		    			insumoFormula.getPk().setSerieId(serieIndicador.getPk().getSerieId());
		  		    			if (indId != null)
		  		    				insumoFormula.getPk().setIndicadorId(Long.parseLong(indId));
		  		    			if (serieId != null)
		  		    				insumoFormula.getPk().setInsumoSerieId(Long.parseLong(serieId));
		  		    			formula.getInsumos().add(insumoFormula);
			  				}
			  				rs.close();
		  			    }
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
		
		return indicadores;		
	}
	
	public Indicador Load(Long indicadorId, Statement stmExt)
	{
		String CLASS_METHOD = "IndicadorManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Indicador indicador = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		try
		{
			if (stmExt != null)
				stm = stmExt;
			else
			{
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}
			
			String indId = null;
			String nombre = null;
			String codEnlace = null;
			String frecuencia = null;
			String corte = null;
			String mescierre = null;
			String tipomedicion = null;
			String valorInicialCero = null;
			String caracteristica = null;
			String naturaleza = null;
			String tipoSuma = null;
			String orgId = null;
			String clsId = null;
			String alertaN1IndId = null;
			String alertaN2IndId = null;
			String alertaMinMax = null;
			String alertaMetaN1 = null;
			String alertaMetaN2 = null;
			String alertaN1Tipo = null;
			String alertaN2Tipo = null;
			String alertaN1Fv = null;
			String alertaN2Fv = null;
			String psuperiorId = null;
			String pinferiorId = null;
			String psuperiorVf = null;
			String pinferiorVf = null;
			String tipoFuncion = null;
			Formula formulaIndicador = null;
			String asignarInventario = null;
			String unidadId = null;
			String alerta = null;
			String ultimaMedicion = null;
			String ultimaFechaMedicion = null;
			
			sql = "SELECT ";
			sql = sql + "Indicador.INDICADOR_ID, ";
			sql = sql + "Indicador.NOMBRE, ";
			sql = sql + "Indicador.CODIGO_ENLACE, ";
			sql = sql + "Indicador.FRECUENCIA, ";
			sql = sql + "Indicador.CORTE, ";
			sql = sql + "Organizacion.MES_CIERRE, ";
			sql = sql + "Indicador.TIPO_MEDICION, ";
			sql = sql + "Indicador.VALOR_INICIAL_CERO, ";
			sql = sql + "Indicador.CARACTERISTICA, ";
			sql = sql + "Indicador.NATURALEZA, ";
			sql = sql + "indicador.TIPO_SUMA, ";
			sql = sql + "indicador.Organizacion_id, ";
			sql = sql + "indicador.clase_id, ";
			sql = sql + "indicador.alerta_n2_ind_id, ";
			sql = sql + "indicador.alerta_n1_ind_id, ";
			sql = sql + "indicador.alerta_min_max, ";
			sql = sql + "indicador.alerta_meta_n1, ";
			sql = sql + "indicador.alerta_meta_n2, ";
			sql = sql + "indicador.alerta_n1_tipo, ";
			sql = sql + "indicador.alerta_n2_tipo, ";
			sql = sql + "indicador.alerta_n1_fv, ";
			sql = sql + "indicador.alerta_n2_fv, ";
			sql = sql + "indicador.psuperior_id, ";
			sql = sql + "indicador.pinferior_id, ";
			sql = sql + "indicador.psuperior_vf, ";
			sql = sql + "indicador.pinferior_vf, ";
			sql = sql + "indicador.tipo, ";
			sql = sql + "indicador.asignar_Inventario, ";
			sql = sql + "indicador.unidad_id, ";
			sql = sql + "indicador.crecimiento_ind, ";
			sql = sql + "indicador.ultima_medicion, ";
			sql = sql + "indicador.fecha_ultima_medicion ";
			sql = sql + "FROM Indicador, Organizacion ";
			sql = sql + "WHERE Indicador_Id = " + indicadorId;
			sql = sql + " AND Indicador.organizacion_Id = Organizacion.organizacion_Id";
		    
			rs = stm.executeQuery(sql);
			
			if (rs.next()) 
			{
				indId = rs.getString("indicador_id");
				nombre = rs.getString("nombre");
				codEnlace = rs.getString("codigo_enlace");
				frecuencia = rs.getString("frecuencia");
				corte = rs.getString("corte");
				mescierre = rs.getString("mes_cierre");
				tipomedicion = rs.getString("tipo_medicion");
				valorInicialCero = rs.getString("valor_inicial_cero");
				caracteristica = rs.getString("CARACTERISTICA");
				naturaleza = rs.getString("NATURALEZA");
				tipoSuma = rs.getString("TIPO_SUMA");
				orgId = rs.getString("Organizacion_id");
				clsId = rs.getString("clase_id");
				alertaN2IndId = rs.getString("alerta_n2_ind_id");
				alertaN1IndId = rs.getString("alerta_n1_ind_id");
				alertaMinMax = rs.getString("alerta_min_max");
				alertaMetaN1 = rs.getString("alerta_meta_n1");
				alertaMetaN2 = rs.getString("alerta_meta_n2");
				alertaN1Tipo = rs.getString("alerta_n1_tipo");
				alertaN2Tipo = rs.getString("alerta_n2_tipo");
				alertaN1Fv = rs.getString("alerta_n1_fv");
				alertaN2Fv = rs.getString("alerta_n2_fv");
				psuperiorId = rs.getString("psuperior_id");
				pinferiorId = rs.getString("pinferior_id");
				psuperiorVf = rs.getString("psuperior_vf");
				pinferiorVf = rs.getString("pinferior_vf");
				tipoFuncion = rs.getString("tipo");
				asignarInventario = rs.getString("asignar_Inventario");
				unidadId = rs.getString("unidad_Id");
				alerta = rs.getString("crecimiento_ind");
				ultimaMedicion = rs.getString("ultima_medicion");
				ultimaFechaMedicion = rs.getString("fecha_ultima_medicion");

				indicador = new Indicador();
				if (indId != null)
				{
					indicador.setIndicadorId(Long.parseLong(indId));
					if (nombre != null)
						indicador.setNombre(nombre);
					if (codEnlace != null)
						indicador.setCodigoEnlace(codEnlace);
					if (frecuencia != null)
						indicador.setFrecuencia(Byte.parseByte(frecuencia));
					if (corte != null)
						indicador.setCorte(Byte.parseByte(corte));
					OrganizacionStrategos organizacion = new OrganizacionStrategos(); 
					indicador.setOrganizacion(organizacion);
					if (mescierre != null)
						indicador.getOrganizacion().setMesCierre(Byte.parseByte(mescierre));
					if (tipomedicion != null)
						indicador.setTipoCargaMedicion(Byte.parseByte(tipomedicion));
					if (valorInicialCero != null)
						indicador.setValorInicialCero(valorInicialCero.equals("1") ? true : false);
					if (caracteristica != null)
						indicador.setCaracteristica(Byte.parseByte(caracteristica));
					if (naturaleza != null)
						indicador.setNaturaleza(Byte.parseByte(naturaleza));
					if (tipoSuma != null)
						indicador.setTipoSumaMedicion(Byte.parseByte(tipoSuma));
					if (orgId != null)
						indicador.setOrganizacionId(Long.parseLong(orgId));
					if (clsId != null)
						indicador.setClaseId(Long.parseLong(clsId));
					if (alertaN1IndId != null)
						indicador.setAlertaIndicadorIdZonaVerde(Long.parseLong(alertaN1IndId));
					if (alertaN2IndId != null)
						indicador.setAlertaIndicadorIdZonaAmarilla(Long.parseLong(alertaN2IndId));
					if (alertaMinMax != null)
						indicador.setAlertaMinMax(Integer.parseInt(alertaMinMax));
					if (alertaMetaN1 != null)
						indicador.setAlertaMetaZonaVerde(new Double(VgcFormatter.parsearNumeroFormateado(alertaMetaN1)));
					if (alertaMetaN2 != null)
						indicador.setAlertaMetaZonaAmarilla(new Double(VgcFormatter.parsearNumeroFormateado(alertaMetaN2)));
					if (alertaN1Tipo != null)
						indicador.setAlertaTipoZonaVerde(Byte.parseByte(alertaN1Tipo));
					if (alertaN2Tipo != null)
						indicador.setAlertaTipoZonaAmarilla(Byte.parseByte(alertaN2Tipo));
					if (alertaN1Fv != null)
						indicador.setAlertaValorVariableZonaVerde(alertaN1Fv.equals("1") ? true : false);
					if (alertaN2Fv != null)
						indicador.setAlertaValorVariableZonaAmarilla(alertaN2Fv.equals("1") ? true : false);
					if (psuperiorId != null)
						indicador.setParametroSuperiorIndicadorId(Long.parseLong(psuperiorId));
					if (pinferiorId != null)
						indicador.setParametroInferiorIndicadorId(Long.parseLong(pinferiorId));
					if (psuperiorVf != null)
						indicador.setParametroSuperiorValorFijo(new Double(VgcFormatter.parsearNumeroFormateado(psuperiorVf)));
					if (pinferiorVf != null)
						indicador.setParametroInferiorValorFijo(new Double(VgcFormatter.parsearNumeroFormateado(pinferiorVf)));
					if (tipoFuncion != null)
						indicador.setTipoFuncion(Byte.parseByte(tipoFuncion));
					if (asignarInventario != null)
						indicador.setAsignarInventario(asignarInventario.equals("1") ? true : false);
					if (unidadId != null)
						indicador.setUnidadId(Long.parseLong(unidadId));
					if (alerta != null)
						indicador.setAlerta(Byte.parseByte(alerta));
					if (ultimaMedicion != null)
						indicador.setUltimaMedicion(new Double(VgcFormatter.parsearNumeroFormateado(ultimaMedicion)));
					if (ultimaFechaMedicion != null)
						indicador.setFechaUltimaMedicion(ultimaFechaMedicion);
				}
				else
					indicador = null;
			}
			rs.close();
			
			String serieId = null;			
			if (indicador != null)
			{
				indicador.setSeriesIndicador(new HashSet<SerieIndicador>());
				indicador.getSeriesIndicador().clear();
				SerieIndicador serieIndicador = null;
	        	
				sql = "SELECT ";
				sql = sql + "serie_id ";
				sql = sql + "FROM serie_indicador ";
				sql = sql + "WHERE indicador_id = " + indicador.getIndicadorId().toString();
				sql = sql + " ORDER BY serie_id";
				
				rs = stm.executeQuery(sql);
	
				while (rs.next())
				{
					serieId = rs.getString("serie_id");
					
					serieIndicador = new SerieIndicador();
			        serieIndicador.setPk(new SerieIndicadorPK());
  			        if (serieId != null)
  			        	serieIndicador.getPk().setSerieId(Long.parseLong(serieId));
			        
			        indicador.getSeriesIndicador().add(serieIndicador);
				}
				rs.close();
				
				if (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaFormula().byteValue() || 
					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaIndice().byteValue() ||
					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaPromedio().byteValue() ||
					indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaSumatoria().byteValue())
				{
	  		    	for (Iterator<?> iterSerie = indicador.getSeriesIndicador().iterator(); iterSerie.hasNext(); ) 
	  		        {
	  		    		serieIndicador = (SerieIndicador)iterSerie.next();
		  			    serieIndicador.setFormulas(new HashSet<Object>());
		  			    serieIndicador.getFormulas().clear();

		  				sql = "SELECT ";
		  				sql = sql + "Expresion ";
		  				sql = sql + "FROM Formula ";
		  				sql = sql + "WHERE indicador_id = " + indicador.getIndicadorId().toString();
		  				sql = sql + " AND serie_id = " + serieIndicador.getPk().getSerieId().toString();
	
		  				rs = stm.executeQuery(sql);
		  				
		  				while (rs.next())
		  				{
		  		    		formulaIndicador = new Formula();
			  			    formulaIndicador.setExpresion(rs.getString("Expresion"));
			  			    
			  			    serieIndicador.getFormulas().add(formulaIndicador);
		  				}
		  				rs.close();
		  				
		  		    	for (Iterator<?> iterFormula = serieIndicador.getFormulas().iterator(); iterFormula.hasNext(); )
		  		    	{
		  		    		Formula formula = (Formula)iterFormula.next();
		  		    		formula.setInsumos(new HashSet<Object>());
		  		    		
			  				sql = "SELECT ";
			  				sql = sql + "insumo_serie_id, ";
			  				sql = sql + "indicador_id ";
			  				sql = sql + "FROM Conjunto_Formula ";
			  				sql = sql + "WHERE padre_id = " + indicador.getIndicadorId().toString();
			  				sql = sql + " AND serie_id = " + serieIndicador.getPk().getSerieId().toString();
		
			  				rs = stm.executeQuery(sql);

			  				while (rs.next())
			  				{
			  					indId = rs.getString("indicador_id");
			  					serieId = rs.getString("insumo_serie_id");

			  					InsumoFormula insumoFormula = new InsumoFormula();
		  		    			insumoFormula.setPk(new InsumoFormulaPK());
		  		    			insumoFormula.setPadre(indicador);
		  		    			insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		  		    			insumoFormula.getPk().setSerieId(serieIndicador.getPk().getSerieId());
		  		    			if (indId != null)
		  		    				insumoFormula.getPk().setIndicadorId(Long.parseLong(indId));
		  		    			if (serieId != null)
		  		    				insumoFormula.getPk().setInsumoSerieId(Long.parseLong(serieId));
		  		    			formula.getInsumos().add(insumoFormula);
			  				}
			  				rs.close();
		  			    }
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
	    	
	    	indicador = null;
		} 
		finally 
		{
			try { rs.close(); } catch (Exception localException4) { }
			try { if (stmExt == null) stm.close(); } catch (Exception localException5) { }
			try { if (stmExt == null) {cn.close(); cn = null;}} catch (Exception localException6) { }
		}
		
		return indicador;		
	}
	
	public int actualizarDatosIndicador(Long indicadorId, Double ultimaMedicion, Double ultimoProgramado, String fechaUltimaMedicion, Statement stmExt)
	{
		int actualizados = 0;
		String CLASS_METHOD = "IndicadorManager.actualizarDatosIndicador";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		int respuesta = 10000;
		
		try
		{
			String hqlUpdate = "update Indicador set";

			if (ultimaMedicion == null)
				hqlUpdate = hqlUpdate + " ultima_medicion = null";
			else 
				hqlUpdate = hqlUpdate + " ultima_medicion = " + ultimaMedicion;
			
			if (fechaUltimaMedicion == null)
				hqlUpdate = hqlUpdate + ", fecha_ultima_medicion = null";
			else 
				hqlUpdate = hqlUpdate + ", fecha_ultima_medicion = '" + fechaUltimaMedicion + "'";

			if (ultimoProgramado == null)
				hqlUpdate = hqlUpdate + ", ultimo_programado = null where indicador_Id = " + indicadorId;
			else 
				hqlUpdate = hqlUpdate + ", ultimo_programado = " + ultimoProgramado +" where indicador_Id = " + indicadorId;

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

			actualizados = stm.executeUpdate(hqlUpdate);
			
			if (stmExt == null)
			{
				cn.commit();
				cn.setAutoCommit(true);
				transActiva = false;
			}
			
			respuesta = 10000;
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
					if (transActiva)
						cn.setAutoCommit(true);
					if (ConexAbierta) {cn.close();cn = null;} 
				} 
				catch (Exception localException9) { }
			}
		}

		if (actualizados != 0) 
			respuesta = 10000;
		else
			respuesta = 10001;
		
		return respuesta;
	}
	
	public int updateAlerta(Long indicadorId, Byte alerta, Statement stmExt) 
	{
		int actualizados = 0;
		String CLASS_METHOD = "IndicadorManager.updateAlerta";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int respuesta = 10000;

		try
		{
		    if (alerta == null) 
		    	sql = "update Indicador set crecimiento_ind = null where indicador_Id = " + indicadorId;
		    else 
		    	sql = "update Indicador set crecimiento_ind = " + alerta + " where indicador_Id = " + indicadorId;

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

			actualizados = stm.executeUpdate(sql);
			
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
					if (transActiva)
						cn.setAutoCommit(true);
					if (ConexAbierta) {cn.close(); cn = null;}
				} 
				catch (Exception localException9) { }
			}
		}

		if (actualizados != 0) 
			respuesta = 10000;
		else
			respuesta = 10001;
		
		return respuesta;
	}
	
  	public List<Indicador> getIndicadoresParaCalcular(Long indicadorId, boolean porNombre, String nombreIndicador, Long claseId, Long organizacionId, boolean arbolCompletoOrganizacion, boolean todasOrganizaciones, Long perspectivaId, Long planId, Byte frecuencia, Statement stmExt) 
  	{
  		Map<String, Object> filtros = new HashMap<String, Object>();
  		List<String> orderList = new ArrayList<String>();
  		List<String> campoIdList = new ArrayList<String>();
  		List<Indicador> indicadores = null;

  		filtros.put("noCualitativos", "true");
  		if ((indicadorId != null) && (!porNombre)) 
  		{
  			filtros.put("indicador_Id", indicadorId.toString());
  			indicadores = getIndicadores(filtros, stmExt);
  		} 
  		else 
  		{
  			if ((porNombre) && (nombreIndicador != null) && (!nombreIndicador.equals("")))
  				filtros.put("nombre", nombreIndicador);
  			if (frecuencia != null)
  				filtros.put("frecuencia", frecuencia);
  			if (claseId != null) 
  			{
  				ClaseIndicadores clase = new ClaseManager(this.pm, this.log, this.messageResources).Load(claseId, stmExt); 
  				if (clase != null) 
  				{
  			  		List<ClaseIndicadores> listaIds = new ClaseManager(this.pm, this.log, this.messageResources).getArbolCompletoClaseIndicadores(claseId, stmExt);
					campoIdList = new ArrayList<String>();
					
					for (Iterator<ClaseIndicadores> i = listaIds.iterator(); i.hasNext(); ) 
					{
						ClaseIndicadores claseIndicadicadores = (ClaseIndicadores)i.next();
						campoIdList.add(claseIndicadicadores.getClaseId().toString());
					}

					filtros.put("clase_Id", campoIdList);
					orderList.add("asc");
					orderList.add("clase_Id");
					filtros.put("orderBy", orderList);
  					
  					indicadores = getIndicadores(filtros, stmExt);
  				}
  			} 
  			else if (perspectivaId != null)
  			{
  				campoIdList = new ArrayList<String>();
  				
  				Perspectiva perspectiva = new PerspectivaManager(this.pm, this.log, this.messageResources).Load(perspectivaId, stmExt);
  				if (perspectiva != null) 
  				{
  					List<Perspectiva> listaIds = new PerspectivaManager(this.pm, this.log, this.messageResources).getArbolCompletoPerspectivas(perspectivaId, stmExt);
					for (Iterator<Perspectiva> i = listaIds.iterator(); i.hasNext(); ) 
					{
						Perspectiva perspectivaActual = (Perspectiva)i.next();
						campoIdList.add(perspectivaActual.getPerspectivaId().toString());
					}
  					
  					filtros.put("perspectiva_id", campoIdList);
  				}

  				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
  				
  				indicadores = getIndicadores(filtros, stmExt);
  			} 
  			else if (planId != null) 
  			{
  				indicadores = new ArrayList<Indicador>();
  				Map<Long, Long> indicadoresIds = new HashMap<Long, Long>();
  				
  				campoIdList = new ArrayList<String>();
  				Map<String, Object> filtrosPerspectivas = new HashMap<String, Object>();
  				String[] orden = new String[1];
  				String[] tipoOrden = new String[1];
  				orden[0] = "perspectiva_id";
  				tipoOrden[0] = "asc";
  				filtrosPerspectivas.put("plan_Id", planId.toString());
  				List<Perspectiva> perspectivasPlan = new PerspectivaManager(this.pm, this.log, this.messageResources).getPerspectivas(orden, tipoOrden, filtrosPerspectivas, stmExt);
  				for (Iterator<Perspectiva> iter = perspectivasPlan.iterator(); iter.hasNext(); ) 
  				{
  					Perspectiva perspectiva = (Perspectiva)iter.next();
  					campoIdList.add(perspectiva.getPerspectivaId().toString());
  				}
  				
  				filtros.put("perspectiva_id", campoIdList);
  				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());

  				List<Indicador> indicadoresPerspectivas = getIndicadores(filtros, stmExt);
  				for (Iterator<Indicador> iter = indicadoresPerspectivas.iterator(); iter.hasNext(); ) 
  				{
  					Indicador indicador = (Indicador)iter.next();
  					if (!indicadoresIds.containsKey(indicador.getIndicadorId())) 
  					{
  						indicadores.add(indicador);
  						indicadoresIds.put(indicador.getIndicadorId(), indicador.getIndicadorId());
  					}
  				}
  				
  				filtros = new HashMap<String, Object>();
  				filtros.put("noCualitativos", "true");
  				filtros.put("plan_id", planId);
  				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());

  				List<Indicador> indicadoresPlan = getIndicadores(filtros, stmExt);
  				for (Iterator<Indicador> iter = indicadoresPlan.iterator(); iter.hasNext(); ) 
  				{
  					Indicador indicador = (Indicador)iter.next();
  					if (!indicadoresIds.containsKey(indicador.getIndicadorId())) 
  					{
  						indicadores.add(indicador);
  						indicadoresIds.put(indicador.getIndicadorId(), indicador.getIndicadorId());
  					}
  				}
  			}
  			else if (todasOrganizaciones) 
  			{
  				orderList.add("asc");
  				orderList.add("organizacion_Id");
  				orderList.add("asc");
  				orderList.add("clase_Id");
  				filtros.put("orderBy", orderList);

  				indicadores = getIndicadores(filtros, stmExt);
  			} 
  			else if (organizacionId != null) 
  			{
  				if (arbolCompletoOrganizacion) 
  				{
  					OrganizacionStrategos organizacion = new OrganizacionManager(this.pm, this.log, this.messageResources).Load(organizacionId, stmExt);
  					if (organizacion != null)
  					{
	  					List<OrganizacionStrategos> listaIds = new OrganizacionManager(this.pm, this.log, this.messageResources).getArbolCompletoOrganizaciones(organizacionId, stmExt);
						for (Iterator<OrganizacionStrategos> i = listaIds.iterator(); i.hasNext(); ) 
						{
							OrganizacionStrategos organizacionActual = (OrganizacionStrategos)i.next();
							campoIdList.add(organizacionActual.getOrganizacionId().toString());
						}
	
	  					filtros.put("organizacion_Id", campoIdList);
  					}
  					orderList.add("asc");
  					orderList.add("organizacion_Id");
  					orderList.add("asc");
  					orderList.add("clase_Id");
  					
  					filtros.put("orderBy", orderList);

  					indicadores = getIndicadores(filtros, stmExt);
  				} 
  				else 
  				{
  					filtros.put("organizacion_Id", organizacionId);
  					
  					orderList.add("asc");
  					orderList.add("clase_Id");
  					filtros.put("orderBy", orderList);

  					indicadores = getIndicadores(filtros, stmExt);
  				}
  			}
  		}

    	return indicadores;
  	}
	
	public int deleteIndicadorEstados(Long indicadorId, Long planId, Byte tipo, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, Statement stmExt) 
	{
	    int resultado = 10000;
		String CLASS_METHOD = "IndicadorManager.saveAlertaIndicadorPlan";
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

			sql = "delete from indicador_estado ";
			sql = sql + "where indicador_estado.indicador_Id = " + indicadorId;
			sql = sql + " and indicador_estado.plan_Id = " + planId;
			sql = sql + " and indicador_estado.tipo = " + tipo;
			
		    boolean continuar = false;

		    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) 
		    {
		    	if (anoInicio.intValue() != anoFinal.intValue()) 
		    	{
		    		sql = sql + " and (((indicador_estado.ano = " + anoInicio + ") and (indicador_estado.periodo >= " + periodoInicio + "))";
		    		sql = sql + " or ((indicador_estado.ano > " + anoInicio + ") and (indicador_estado.ano < " + anoFinal + "))";
		    		sql = sql + " or ((indicador_estado.ano = " + anoFinal + ") and (indicador_estado.periodo <= " + periodoFinal + ")))";
		    	} 
		    	else 
		    	{
		    		sql = sql + " and ((indicador_estado.ano = " + anoInicio + ") and (indicador_estado.periodo >= " + periodoInicio + ")";
		    		sql = sql + " and (indicador_estado.ano = " + anoFinal + ") and (indicador_estado.periodo <= " + periodoFinal + "))";
		    	}
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    	continuar = true;
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and indicador_estado.ano >= " + anoInicio;
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and indicador_estado.ano <= " +anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and indicador_estado.ano >= " + anoInicio;
		    	sql = sql + " and indicador_estado.ano <= " + anoFinal;
		    	continuar = true;
		    } 
		    else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) 
		    {
		    	sql = sql + " and (((indicador_estado.ano = " + anoInicio + ") and (indicador_estado.periodo >= " + periodoInicio + "))";
		    	sql = sql + " or (indicador_estado.ano > " + anoInicio + "))";
		    	continuar = true;
		    } 
		    else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) 
		    {
		    	sql = sql + " and (((indicador_estado.ano = " + anoFinal + ") and (indicador_estado.periodo <= " + periodoFinal + "))";
		    	sql = sql + " or (indicador_estado.ano < " + anoFinal + "))";
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
	
	public int saveIndicadorEstado(Long indicadorId, Long planId, Byte tipo, Integer ano, Integer periodo, Double valor, Statement stmExt)
	{
	    int resultado = 10000;
		int actualizados = 0;
		String CLASS_METHOD = "IndicadorManager.saveIndicadorEstado";
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

			sql = "UPDATE indicador_estado";
			sql = sql + " SET Estado = " + valor;
			sql = sql + " WHERE indicador_Id = " + indicadorId;
			sql = sql + " AND Plan_Id = " + planId;
			sql = sql + " AND Tipo = " + tipo;
			sql = sql + " AND Ano = " + ano;
			sql = sql + " AND Periodo = " + periodo;
			
			actualizados = stm.executeUpdate(sql);
			
		    if (actualizados == 0) 
		    {
				sql = "INSERT INTO indicador_estado (Indicador_Id, Plan_Id, Tipo, Ano, Periodo, Estado)";
				sql = sql + " VALUES (" + indicadorId + ", ";
				sql = sql + planId + ", ";
				sql = sql + tipo + ", ";
				sql = sql + ano + ", ";
				sql = sql + periodo + ", ";
				sql = sql + valor + ")";
				
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
}

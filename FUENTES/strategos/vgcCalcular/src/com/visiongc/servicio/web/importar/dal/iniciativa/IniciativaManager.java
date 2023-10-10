/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.iniciativa;

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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.framework.model.Usuario;
import com.visiongc.servicio.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.servicio.strategos.indicadores.model.Formula;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.InsumoFormula;
import com.visiongc.servicio.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicador;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.servicio.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.servicio.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.servicio.strategos.iniciativas.model.Iniciativa;
import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.web.importar.dal.clase.ClaseManager;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
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
public class IniciativaManager {
	PropertyCalcularManager pm;
	StringBuffer log;
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public IniciativaManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources) {
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public List<Iniciativa> getIniciativas(Map<?, ?> filtros, Statement stmExt) {
		String CLASS_METHOD = "IniciativaManager.getIniciativas";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		String tablasConsulta = "";
		String sql = " where ";
		String ordenConsulta = " order by ";
		boolean ordenNombre = false;
		boolean hayOrden = false;
		boolean hayCondicionesConsulta = false;
		boolean addIndicador = false;

		Iniciativa iniciativa;

		if (filtros != null) {
			Iterator<?> iter = filtros.keySet().iterator();
			String fieldName = null;
			String fieldValue = null;

			while (iter.hasNext()) {
				fieldName = (String) iter.next();
				if (filtros.get(fieldName) == null)
					fieldValue = null;
				else if ((filtros.get(fieldName) instanceof String))
					fieldValue = (String) filtros.get(fieldName);
				else if (!(filtros.get(fieldName) instanceof List))
					fieldValue = new DalUtil().getValorCondicionConsulta(filtros.get(fieldName));

				if (fieldName.equals("nombre")) {
					sql = sql + "lower(iniciativa." + fieldName + ")"
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "like") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("organizacion_Id")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("clase_Id")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("iniciativa_Id")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("frecuencia")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("codigo")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("frecuenciasContenidas")) {
					List<Frecuencia> frecuencias = Frecuencia.getFrecuenciasContenidas(Byte.parseByte(fieldValue));
					sql = sql + "(";
					for (int i = 0; i < frecuencias.size(); i++) {
						Frecuencia frecuencia = (Frecuencia) frecuencias.get(i);
						sql = sql + "iniciativa.frecuencia = " + frecuencia.getFrecuenciaId().toString() + " or ";
					}
					sql = sql.substring(0, sql.length() - 4);
					sql = sql + ") and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("noCualitativos")) {
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
						sql = sql + "iniciativa.naturaleza != "
								+ Naturaleza.getNaturalezaCualitativoNominal().toString()
								+ " and iniciativa.naturaleza != "
								+ Naturaleza.getNaturalezaCualitativoOrdinal().toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} else if (fieldName.equals("noCompuestos")) {
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
						sql = sql + "iniciativa.naturaleza != " + Naturaleza.getNaturalezaFormula().toString()
								+ " and iniciativa.naturaleza != " + Naturaleza.getNaturalezaIndice().toString()
								+ " and iniciativa.naturaleza != " + Naturaleza.getNaturalezaPromedio()
								+ " and iniciativa.naturaleza != " + Naturaleza.getNaturalezaSumatoria().toString()
								+ " and ";
						hayCondicionesConsulta = true;
					}
				} else if (fieldName.equals("soloCompuestos")) {
					if ((fieldValue != null) && (fieldValue.equalsIgnoreCase("true"))) {
						sql = sql + "iniciativa.naturaleza != "
								+ Naturaleza.getNaturalezaCualitativoNominal().toString()
								+ " and iniciativa.naturaleza != "
								+ Naturaleza.getNaturalezaCualitativoOrdinal().toString()
								+ " and iniciativa.naturaleza != " + Naturaleza.getNaturalezaSimple().toString()
								+ " and ";
						hayCondicionesConsulta = true;
					}
				} else if (fieldName.equals("excluirIds")) {
					String[] ids = fieldValue.split(",");
					for (int i = 0; i < ids.length; i++) {
						Long id = new Long(ids[i]);
						sql = sql + "iniciativa.iniciativa_Id != " + id.toString() + " and ";
						hayCondicionesConsulta = true;
					}
				} else if (fieldName.equals("excluirId")) {
					sql = sql + "iniciativa." + fieldName
							+ new DalUtil().getCondicionConsulta(filtros.get(fieldName), "!=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("perspectiva_id") && fieldValue != null
						&& (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL"))) {
					tablasConsulta = tablasConsulta + ", iniciativa_por_perspectiva ";
					sql = sql
							+ "iniciativa.iniciativa_Id = iniciativa_por_perspectiva.iniciativa_Id and iniciativa_por_perspectiva."
							+ fieldName + " " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("perspectiva_id") && (filtros.get(fieldName) instanceof List)) {
					tablasConsulta = tablasConsulta + ", iniciativa_por_perspectiva ";
					sql = sql
							+ "iniciativa.iniciativa_Id = iniciativa_por_perspectiva.iniciativa_Id and iniciativa_por_perspectiva."
							+ fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("perspectiva_id") && fieldValue != null) {
					tablasConsulta = tablasConsulta + ", iniciativa_por_perspectiva ";
					sql = sql
							+ "iniciativa.iniciativa_Id = iniciativa_por_perspectiva.iniciativa_Id and iniciativa_por_perspectiva."
							+ fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("plan_id") && fieldValue != null
						&& (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL"))) {
					tablasConsulta = tablasConsulta + ", iniciativa_plan ";
					sql = sql + "iniciativa.iniciativa_Id = iniciativa_plan.iniciativa_Id and iniciativa_plan."
							+ fieldName + " " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("plan_id") && (filtros.get(fieldName) instanceof List)) {
					tablasConsulta = tablasConsulta + ", iniciativa_plan ";
					sql = sql + "iniciativa.iniciativa_Id = iniciativa_plan.iniciativa_Id and iniciativa_plan."
							+ fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("plan_id") && fieldValue != null) {
					tablasConsulta = tablasConsulta + ", iniciativa_plan ";
					sql = sql + "iniciativa.iniciativa_Id = iniciativa_plan.iniciativa_Id and iniciativa_plan."
							+ fieldName + new DalUtil().getCondicionConsulta(filtros.get(fieldName), "=") + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("indicadorAvance") && fieldValue != null) {
					addIndicador = true;
					tablasConsulta = tablasConsulta + ", indicador_por_iniciativa, indicador ";
					sql = sql
							+ "iniciativa.iniciativa_Id = indicador_por_iniciativa.iniciativa_Id and indicador_por_iniciativa.tipo = "
							+ TipoFuncionIndicador.getTipoFuncionSeguimiento() + " and ";
					sql = sql + "indicador.indicador_Id = indicador_por_iniciativa.indicador_Id and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("historicoDate")
						&& (fieldValue.equals("IS NOT NULL") || fieldValue.equals("IS NULL"))) {
					sql = sql + "iniciativa.historico_date " + fieldValue + " and ";
					hayCondicionesConsulta = true;
				} else if (fieldName.equals("orderBy")) {
					List<String> idList = (List<String>) filtros.get(fieldName);
					int j = 1;
					boolean desc = false;
					for (Iterator<String> i = idList.listIterator(); i.hasNext();) {
						String param = (String) i.next();
						if (j % 2 == 1) {
							if (param.equals("asc"))
								desc = false;
							else
								desc = true;
						}
						if (j % 2 == 0) {
							if (param.equals("nombre"))
								ordenNombre = true;
							if (desc)
								ordenConsulta = ordenConsulta + "iniciativa." + param + " desc, ";
							else
								ordenConsulta = ordenConsulta + "iniciativa." + param + " asc, ";
							hayOrden = true;
						}
						j++;
					}
				}
			}
		}
		sql = sql + "iniciativa.organizacion_Id = organizacion.organizacion_Id and ";

		if (hayCondicionesConsulta)
			sql = sql.substring(0, sql.length() - 5);
		else
			sql = "";

		if (!ordenNombre)
			ordenConsulta = ordenConsulta + "iniciativa.nombre asc";
		else if (hayOrden)
			ordenConsulta = ordenConsulta.substring(0, ordenConsulta.length() - 2);
		else
			ordenConsulta = "";

		String objetoConsulta = "distinct ";
		objetoConsulta = objetoConsulta + "iniciativa.iniciativa_id, ";
		objetoConsulta = objetoConsulta + "iniciativa.nombre, ";
		objetoConsulta = objetoConsulta + "iniciativa.organizacion_Id, ";
		if (addIndicador)
			objetoConsulta = objetoConsulta + "indicador.indicador_id, ";
		objetoConsulta = objetoConsulta + "iniciativa.crecimiento, ";
		objetoConsulta = objetoConsulta + "iniciativa.porcentaje_completado, ";
		objetoConsulta = objetoConsulta + "iniciativa.fecha_ultima_medicion, ";
		objetoConsulta = objetoConsulta + "iniciativa.codigo, ";
		objetoConsulta = objetoConsulta + "iniciativa.proyecto_id";

		sql = "select " + objetoConsulta + " from iniciativa, organizacion " + tablasConsulta + sql + ordenConsulta;
				

		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		String iniId = null;
		String indicadorId = null;
		String alerta = null;
		String porcentajeCompletado = null;
		String fechaUltimaMedicion = null;
		String codigoIniciativa = null;
		String proyectoId = null;

		try {
			if (stmExt != null)
				stm = stmExt;
			else {
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}

			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				iniId = rs.getString("iniciativa_id");
				if (addIndicador)
					indicadorId = rs.getString("indicador_id");
				alerta = rs.getString("crecimiento");
				porcentajeCompletado = rs.getString("porcentaje_completado");
				fechaUltimaMedicion = rs.getString("fecha_ultima_medicion");
				codigoIniciativa = rs.getString("codigo");
				proyectoId = rs.getString("proyecto_id");

				if (iniId != null) {
					iniciativa = new Iniciativa();
					if (iniId != null)
						iniciativa.setIniciativaId(Long.parseLong(iniId));
					if (addIndicador && indicadorId != null)
						iniciativa.setIndicadorId(Long.parseLong(indicadorId));
					if (alerta != null)
						iniciativa.setAlerta(Byte.parseByte(alerta));
					if (porcentajeCompletado != null)
						iniciativa.setPorcentajeCompletado(
								new Double(VgcFormatter.parsearNumeroFormateado(porcentajeCompletado)));
					if (fechaUltimaMedicion != null)
						iniciativa.setFechaUltimaMedicion(fechaUltimaMedicion);
					if (codigoIniciativa != null)
						iniciativa.setCodigoIniciativa(codigoIniciativa);
					if(proyectoId != null)
						iniciativa.setProyectoId(Long.parseLong(proyectoId));

					iniciativas.add(iniciativa);
				}
			}
			rs.close();

			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
				iniciativa = (Iniciativa) iter.next();
				if (addIndicador && iniciativa.getIndicadorId() != null)
					iniciativa.setIndicador(
							new IndicadorManager(pm, log, messageResources).Load(iniciativa.getIndicadorId(), stmExt));
			}
		} catch (Exception e) {
			String[] argsReemplazo = new String[2];

			argsReemplazo[0] = CLASS_METHOD;
			argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

			log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");
		} finally {
			try {
				rs.close();
			} catch (Exception localException4) {
			}
			try {
				if (stmExt == null)
					stm.close();
			} catch (Exception localException5) {
			}
			try {
				if (stmExt == null) {
					cn.close();
					cn = null;
				}
			} catch (Exception localException6) {
			}
		}

		return iniciativas;
	}

	public Iniciativa Load(Long iniciativaId, Statement stmExt) {
		String CLASS_METHOD = "IniciativaManager.Load";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		Iniciativa iniciativa = null;
		String sql = "";
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			if (stmExt != null)
				stm = stmExt;
			else {
				cn = new ConnectionManager(pm).getConnection();
				stm = cn.createStatement();
			}

			String indId = null;
			String alerta = null;
			String porcentajeCompletado = null;
			String fechaUltimaMedicion = null;

			sql = "SELECT ";
			sql = sql + "iniciativa.iniciativa_id, ";
			sql = sql + "iniciativa.crecimiento, ";
			sql = sql + "iniciativa.porcentaje_completado, ";
			sql = sql + "iniciativa.fecha_ultima_medicion ";
			sql = sql + "FROM iniciativa, Organizacion ";
			sql = sql + "WHERE iniciativa_Id = " + iniciativaId;
			sql = sql + " AND iniciativa.organizacion_Id = Organizacion.organizacion_Id";

			rs = stm.executeQuery(sql);

			if (rs.next()) {
				indId = rs.getString("iniciativa_id");
				alerta = rs.getString("crecimiento");
				porcentajeCompletado = rs.getString("porcentaje_completado");
				fechaUltimaMedicion = rs.getString("fecha_ultima_medicion");

				iniciativa = new Iniciativa();
				if (indId != null) {
					iniciativa.setIniciativaId(Long.parseLong(indId));
					if (alerta != null)
						iniciativa.setAlerta(Byte.parseByte(alerta));
					if (porcentajeCompletado != null)
						iniciativa.setPorcentajeCompletado(Double.parseDouble(porcentajeCompletado));
					if (fechaUltimaMedicion != null)
						iniciativa.setFechaUltimaMedicion(fechaUltimaMedicion);
				} else
					iniciativa = null;
			}
			rs.close();
		} catch (Exception e) {
			String[] argsReemplazo = new String[2];

			argsReemplazo[0] = CLASS_METHOD;
			argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

			log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");

			iniciativa = null;
		} finally {
			try {
				rs.close();
			} catch (Exception localException4) {
			}
			try {
				if (stmExt == null)
					stm.close();
			} catch (Exception localException5) {
			}
			try {
				if (stmExt == null) {
					cn.close();
					cn = null;
				}
			} catch (Exception localException6) {
			}
		}

		return iniciativa;
	}

	public int actualizarDatosIniciativa(Long iniciativaId, Byte alerta, Double porcentajeCompletado,
			String fechaUltimaMedicion, Statement stmExt) {
		int actualizados = 0;
		String CLASS_METHOD = "IniciativaManager.actualizarDatosIniciativa";
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		int respuesta = 10000;

		try {
			String hqlUpdate = "update Iniciativa set";

			if (alerta == null)
				hqlUpdate = hqlUpdate + " crecimiento = null";
			else
				hqlUpdate = hqlUpdate + " crecimiento = " + alerta;

			if (porcentajeCompletado == null)
				hqlUpdate = hqlUpdate + ", porcentaje_completado = null";
			else
				hqlUpdate = hqlUpdate + ", porcentaje_completado = '" + porcentajeCompletado + "'";

			if (fechaUltimaMedicion == null)
				hqlUpdate = hqlUpdate + ", fecha_ultima_medicion = null where iniciativa_Id = " + iniciativaId;
			else
				hqlUpdate = hqlUpdate + ", fecha_ultima_medicion = '" + fechaUltimaMedicion + "' where iniciativa_Id = "
						+ iniciativaId;

			ConexAbierta = true;
			transActiva = true;

			if (stmExt != null)
				stm = stmExt;
			else {
				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
			}

			actualizados = stm.executeUpdate(hqlUpdate);

			if (stmExt == null) {
				cn.commit();
				cn.setAutoCommit(true);
				transActiva = false;
			}

			respuesta = 10000;
		} catch (Exception e) {
			String[] argsReemplazo = new String[2];

			argsReemplazo[0] = CLASS_METHOD;
			argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

			if (transActiva && stmExt == null) {
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
		} finally {
			if (stmExt == null) {
				try {
					stm.close();
				} catch (Exception localException8) {
				}

				try {
					if (transActiva)
						cn.setAutoCommit(true);
					if (ConexAbierta) {
						cn.close();
						cn = null;
					}
				} catch (Exception localException9) {
				}
			}
		}

		if (actualizados != 0)
			respuesta = 10000;
		else
			respuesta = 10001;

		return respuesta;
	}

	public List<Iniciativa> getIniciativasParaCalcular(Long iniciativaId, boolean porNombre, String nombreIniciativa,
			Long claseId, Long organizacionId, boolean arbolCompletoOrganizacion, boolean todasOrganizaciones,
			Long perspectivaId, Long planId, Byte frecuencia, Statement stmExt) {
		Map<String, Object> filtros = new HashMap<String, Object>();
		List<String> orderList = new ArrayList<String>();
		List<String> campoIdList = new ArrayList<String>();
		List<Iniciativa> iniciativas = null;

		filtros.put("noCualitativos", "true");
		filtros.put("indicadorAvance", "true");
		if ((iniciativaId != null) && (!porNombre)) {
			iniciativas = new ArrayList<Iniciativa>();
			filtros.put("iniciativa_Id", iniciativaId.toString());
			filtros.put("historicoDate", "IS NULL");

			iniciativas = getIniciativas(filtros, stmExt);
		} else {
			iniciativas = new ArrayList<Iniciativa>();

			if ((porNombre) && (nombreIniciativa != null) && (!nombreIniciativa.equals("")))
				filtros.put("nombre", nombreIniciativa);
			if (frecuencia != null)
				filtros.put("frecuencia", frecuencia);
			if (claseId != null) {
				ClaseIndicadores clase = new ClaseManager(this.pm, this.log, this.messageResources).Load(claseId,
						stmExt);
				if (clase != null) {
					List<ClaseIndicadores> listaIds = new ClaseManager(this.pm, this.log, this.messageResources)
							.getArbolCompletoClaseIndicadores(claseId, stmExt);
					campoIdList = new ArrayList<String>();

					for (Iterator<ClaseIndicadores> i = listaIds.iterator(); i.hasNext();) {
						ClaseIndicadores claseIndicadicadores = (ClaseIndicadores) i.next();
						campoIdList.add(claseIndicadicadores.getClaseId().toString());
					}

					filtros.put("clase_Id", campoIdList);
					orderList.add("asc");
					orderList.add("clase_Id");
					filtros.put("orderBy", orderList);
					filtros.put("historicoDate", "IS NULL");

					iniciativas = getIniciativas(filtros, stmExt);
				}
			} else if (perspectivaId != null) {
				campoIdList = new ArrayList<String>();

				Perspectiva perspectiva = new PerspectivaManager(this.pm, this.log, this.messageResources)
						.Load(perspectivaId, stmExt);
				if (perspectiva != null) {
					List<Perspectiva> listaIds = new PerspectivaManager(this.pm, this.log, this.messageResources)
							.getArbolCompletoPerspectivas(perspectivaId, stmExt);
					for (Iterator<Perspectiva> i = listaIds.iterator(); i.hasNext();) {
						Perspectiva perspectivaActual = (Perspectiva) i.next();
						campoIdList.add(perspectivaActual.getPerspectivaId().toString());
					}

					filtros.put("perspectiva_id", campoIdList);
				}

				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
				filtros.put("historicoDate", "IS NULL");

				iniciativas = getIniciativas(filtros, stmExt);
			} else if (planId != null) {
				Map<Long, Long> iniciativasIds = new HashMap<Long, Long>();

				campoIdList = new ArrayList<String>();
				Map<String, Object> filtrosPerspectivas = new HashMap<String, Object>();
				String[] orden = new String[1];
				String[] tipoOrden = new String[1];
				orden[0] = "perspectiva_id";
				tipoOrden[0] = "asc";
				filtrosPerspectivas.put("plan_Id", planId.toString());
				List<Perspectiva> perspectivasPlan = new PerspectivaManager(this.pm, this.log, this.messageResources)
						.getPerspectivas(orden, tipoOrden, filtrosPerspectivas, stmExt);
				for (Iterator<Perspectiva> iter = perspectivasPlan.iterator(); iter.hasNext();) {
					Perspectiva perspectiva = (Perspectiva) iter.next();
					campoIdList.add(perspectiva.getPerspectivaId().toString());
				}

				filtros.put("perspectiva_id", campoIdList);
				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
				filtros.put("historicoDate", "IS NULL");

				List<Iniciativa> iniciativasPerspectivas = getIniciativas(filtros, stmExt);
				for (Iterator<Iniciativa> iter = iniciativasPerspectivas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					if (!iniciativasIds.containsKey(iniciativa.getIniciativaId())) {
						iniciativas.add(iniciativa);
						iniciativasIds.put(iniciativa.getIniciativaId(), iniciativa.getIniciativaId());
					}
				}

				filtros = new HashMap<String, Object>();
				filtros.put("noCualitativos", "true");
				filtros.put("plan_id", planId);
				filtros.put("excluirTipoFuncion", TipoFuncionIndicador.getTipoFuncionPerspectiva());
				filtros.put("historicoDate", "IS NULL");

				List<Iniciativa> iniciativasPlan = getIniciativas(filtros, stmExt);
				for (Iterator<Iniciativa> iter = iniciativasPlan.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = (Iniciativa) iter.next();
					if (!iniciativasIds.containsKey(iniciativa.getIniciativaId())) {
						iniciativas.add(iniciativa);
						iniciativasIds.put(iniciativa.getIniciativaId(), iniciativa.getIniciativaId());
					}
				}
			} else if (todasOrganizaciones) {
				filtros.put("historicoDate", "IS NULL");

				orderList.add("asc");
				orderList.add("organizacion_Id");
				filtros.put("orderBy", orderList);

				iniciativas = getIniciativas(filtros, stmExt);
			} else if (organizacionId != null) {
				if (arbolCompletoOrganizacion) {
					OrganizacionStrategos organizacion = new OrganizacionManager(this.pm, this.log,
							this.messageResources).Load(organizacionId, stmExt);
					if (organizacion != null) {
						List<OrganizacionStrategos> listaIds = new OrganizacionManager(this.pm, this.log,
								this.messageResources).getArbolCompletoOrganizaciones(organizacionId, stmExt);
						for (Iterator<OrganizacionStrategos> i = listaIds.iterator(); i.hasNext();) {
							OrganizacionStrategos organizacionActual = (OrganizacionStrategos) i.next();
							campoIdList.add(organizacionActual.getOrganizacionId().toString());
						}

						filtros.put("organizacion_Id", campoIdList);
					}
					filtros.put("historicoDate", "IS NULL");

					orderList.add("asc");
					orderList.add("organizacion_Id");
					filtros.put("orderBy", orderList);

					iniciativas = getIniciativas(filtros, stmExt);
				} else {
					filtros.put("organizacion_Id", organizacionId);
					filtros.put("historicoDate", "IS NULL");

					iniciativas = getIniciativas(filtros, stmExt);
				}
			}
		}

		return iniciativas;
	}
		
	
	public int saveIniciativas(List<Iniciativa> iniciativas, Statement stmExt) {
		String CLASS_METHOD = "IniciativaManager.saveIniciativas";		
		
		if (this.logConsolaMetodos)
			System.out.println(CLASS_METHOD);

		boolean transActiva = false;
		Connection cn = null;
		Statement stm = null;
		boolean ConexAbierta = false;
		String sql = "";
		int resultado = 10000;	
		
		try {
			if (stmExt != null)
				stm = stmExt;
			else {
				cn = new ConnectionManager(pm).getConnection();
				ConexAbierta = true;
				cn.setAutoCommit(false);
				stm = cn.createStatement();
				transActiva = true;
			}
			int respuesta = 0;
			
			int respuestaService = 10000;

			long num = 0L;

			
			for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
				Iniciativa iniciativa = (Iniciativa) iter.next();

				num++;
				if (this.logConsolaDetallado)
					System.out.println("Iniciativa importada numero: " + num + " --> iniciativa Id: "
							+ iniciativa.getIniciativaId());				
				
				sql = "UPDATE INICIATIVA ";

				sql = sql + "SET tipoid = " + iniciativa.getProyectoId();

				sql = sql + ", anio_form_proy = '" + iniciativa.getAnioFormulacion();

				sql = sql + "', frecuencia = " + iniciativa.getFrecuencia();

				sql = sql + ", tipo_medicion = " + iniciativa.getTipoMedicion();

				sql = sql + ", alerta_zv = " + iniciativa.getAlertaZonaVerde();

				sql = sql + ", alerta_za = " + iniciativa.getAlertaZonaAmarilla();

				sql = sql + ", codigo = '" + iniciativa.getCodigoIniciativa();

				sql = sql + "' WHERE nombre = '" + iniciativa.getNombre();

				sql = sql + "' AND organizacion_id = " + iniciativa.getOrganizacionId();
				
				respuesta = stm.executeUpdate(sql);					
			
				if (respuesta == 0) {

					sql = "INSERT INTO INICIATIVA ";

					sql = sql
							+ "(iniciativa_id, organizacion_id, codigo, nombre, nombre_largo, tipoid, anio_form_proy, frecuencia, tipo_medicion, alerta_zv, alerta_za, unidad_medida, tipo_alerta, naturaleza, estatusid) ";

					sql = sql + "VALUES (" + iniciativa.getIniciativaId() + ", ";

					sql = sql + iniciativa.getOrganizacionId() + ", '";

					sql = sql + iniciativa.getCodigoIniciativa() + "', '";

					sql = sql + iniciativa.getNombre() + "', '";
					
					sql = sql + iniciativa.getNombre() + "', ";

					sql = sql + iniciativa.getProyectoId() + ", '";

					sql = sql + iniciativa.getAnioFormulacion() + "', ";

					sql = sql + iniciativa.getFrecuencia() + ", ";

					sql = sql + iniciativa.getTipoMedicion() + ", ";

					sql = sql + iniciativa.getAlertaZonaVerde() + ", ";

					sql = sql + iniciativa.getAlertaZonaAmarilla() + ", ";
					
					sql = sql + iniciativa.getUnidadMedida() + ", ";

					sql = sql + 0 + ", ";

					sql = sql + 0 + ", ";

					sql = sql + 1 + ");\n";
					
					sql = sql + "INSERT INTO INICIATIVA_OBJETO ";
					
					sql = sql + "(iniciativa_id, objeto) ";
					
					sql = sql + "VALUES (" + iniciativa.getIniciativaId() + ", '";
					
					sql = sql + iniciativa.getMemoIniciativa() + "')";					
					
					respuesta = stm.executeUpdate(sql);													

				}
				if (respuesta == 0)
					break;
			}
			if (transActiva && stmExt == null) {
				cn.rollback();
				cn.setAutoCommit(true);
				transActiva = false;
			}
		} catch (Exception e) {
			String[] argsReemplazo = new String[2];

			argsReemplazo[0] = CLASS_METHOD;
			argsReemplazo[1] = e.getMessage() != null ? e.getMessage() : "";

			if (transActiva && stmExt == null) {
				try {
					cn.rollback();
				} catch (SQLException e2) {
					argsReemplazo[1] = argsReemplazo[1] + e2.getMessage();
				}

				try {
					cn.setAutoCommit(true);
				} catch (SQLException e1) {
					argsReemplazo[1] = argsReemplazo[1] + (e1.getMessage() != null ? e1.getMessage() : "");
				}
			}

			log.append(messageResources.getResource("jsp.asistente.importacion.log.bd.error", argsReemplazo) + "\n\n");

			resultado = 10003;
		} finally {
			if (stmExt == null) {
				try {
					stm.close();
				} catch (Exception localException8) {
				}

				try {
					if (transActiva)
						cn.setAutoCommit(true);
					if (ConexAbierta) {
						cn.close();
						cn = null;
					}
				} catch (Exception localException9) {
				}
			}
		}
		return resultado;
	}
}

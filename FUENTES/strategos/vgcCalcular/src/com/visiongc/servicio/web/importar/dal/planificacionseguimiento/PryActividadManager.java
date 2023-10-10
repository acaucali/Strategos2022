package com.visiongc.servicio.web.importar.dal.planificacionseguimiento;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Iterator;

import com.visiongc.servicio.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

public class PryActividadManager {
	PropertyCalcularManager pm;
	StringBuffer log;
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;

	public PryActividadManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources) {
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
	}

	public int saveActividades(List<PryActividad> actividades, Statement stmExt) {
		String CLASS_METHOD = "PryActividadManager.saveActividades";
		
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
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			long num = 0L;			
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				
				num++;
				if (this.logConsolaDetallado)
					System.out.println("Actividad importada numero: " + num + " --> actividad Id: "
							+ actividad.getActividadId());	
								
				
				sql = "UPDATE PRY_ACTIVIDAD ";
				
				sql = sql + "SET descripcion = '" + actividad.getDescripcion();
				
				sql = sql + "' WHERE nombre = '" + actividad.getNombre();
				
				sql = sql + "' AND proyecto_id = " + actividad.getProyectoId();
								
				respuesta = stm.executeUpdate(sql);										
				
				if (respuesta == 0) {
					
					sql = "INSERT INTO PRY_ACTIVIDAD ";
					
					sql = sql + "(actividad_id, proyecto_id, indicador_id, clase_id, nombre, descripcion, comienzo_plan, comienzo_real, fin_plan, fin_real, duracion_plan, unidad_id, fila, nivel, compuesta, creado, creado_id, naturaleza, tipo_medicion, crecimiento, porcentaje_completado, porcentaje_esperado, porcentaje_ejecutado) ";
					
					sql = sql + "VALUES (" + actividad.getActividadId() + ", ";
					
					sql = sql + actividad.getProyectoId() + ", ";
					
					sql = sql + actividad.getIndicadorId() + ", ";
					
					sql = sql + actividad.getClaseId() + ", '";
					
					sql = sql + actividad.getNombre() + "', '";
					
					sql = sql + actividad.getDescripcion() + "', '";
					
					sql = sql + formato.format(actividad.getComienzoPlan()) + "', '";
					
					sql = sql + formato.format(actividad.getComienzoReal()) + "', '";
					
					sql = sql + formato.format(actividad.getFinPlan()) + "', '";
					
					sql = sql + formato.format(actividad.getFinReal()) + "', ";
					
					sql = sql + actividad.getDuracionPlan() + ", ";
					
					sql = sql + actividad.getUnidadId() + ", ";
					
					sql = sql + actividad.getFila() + ", ";
					
					sql = sql + actividad.getNivel() + ", ";
									
					sql = sql + 0 + ", '";
					
					sql = sql + formato.format(actividad.getCreado()) + "', ";
					
					sql = sql + actividad.getCreadoId() + ", ";
					
					sql = sql + actividad.getNaturaleza() + ", ";
					
					sql = sql + actividad.getTipoMedicion() + ", ";
					
					sql = sql + actividad.getCrecimiento() + ", ";
					
					sql = sql + actividad.getPorcentajeCompletado() + ", ";
					
					sql = sql + actividad.getPorcentajeEsperado() + ", ";

					sql = sql + actividad.getPorcentajeEjecutado() + ");\n";
					
					sql = sql + "INSERT INTO INC_ACTIVIDAD ";
					
					sql = sql + "(actividad_id, alerta_za, alerta_zv) ";
					
					sql = sql + "VALUES (" + actividad.getActividadId() + ", ";
					
					sql = sql + actividad.getAlertaZonaAmarilla() + ", ";
					
					sql = sql + actividad.getAlertaZonaVerde() + ");";
										
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
		}catch (Exception e) {
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

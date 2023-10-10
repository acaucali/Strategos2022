/**
 * 
 */
package com.visiongc.servicio.strategos.importar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;


import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativasPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario;
import com.visiongc.app.strategos.planificacionseguimiento.util.PryCalendarioUtil;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.Columna.ColumnaTipo;
import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.MedicionPK;
import com.visiongc.servicio.strategos.indicadores.model.SerieIndicador;
import com.visiongc.servicio.strategos.iniciativas.model.Iniciativa;
import com.visiongc.servicio.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.servicio.strategos.message.model.Message;
import com.visiongc.servicio.strategos.message.model.Message.MessageStatus;
import com.visiongc.servicio.strategos.message.model.Message.MessageType;
import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.planes.model.Meta;
import com.visiongc.servicio.strategos.planes.model.MetaPK;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.strategos.planes.model.util.TipoMeta;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.strategos.servicio.model.Servicio.ServicioStatus;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.dal.iniciativa.IniciativaManager;
import com.visiongc.servicio.web.importar.dal.medicion.MedicionManager;
import com.visiongc.servicio.web.importar.dal.message.MessageManager;
import com.visiongc.servicio.web.importar.dal.meta.MetaManager;
import com.visiongc.servicio.web.importar.dal.organizacion.OrganizacionManager;
import com.visiongc.servicio.web.importar.dal.perspectiva.PerspectivaManager;
import com.visiongc.servicio.web.importar.dal.planificacionseguimiento.PryActividadManager;
import com.visiongc.servicio.web.importar.dal.servicio.ServicioManager;
import com.visiongc.servicio.web.importar.dal.usuario.UsuarioManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class ImportarManager {
	PropertyCalcularManager pm;
	StringBuffer log;
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;
	Servicio servicio;

	public ImportarManager(String[][] configuracion, StringBuffer log, VgcMessageResources messageResources) {
		this.pm = new PropertyCalcularManager().Set(configuracion);
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));

		if (this.servicio == null) {
			Long usuarioId = (pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", ""))
					: new UsuarioManager(this.pm, this.log, this.messageResources).LoadAdmin(null));
			this.servicio = new Servicio(usuarioId, null,
					this.messageResources.getResource("jsp.servicio.importar.titulo"), null,
					this.messageResources.getResource("jsp.servicio.inicio"), "");
		}
	}

	public ImportarManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources,
			Servicio servicio) {
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
		this.servicio = servicio;

		if (this.servicio == null) {
			Long usuarioId = (pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", ""))
					: new UsuarioManager(this.pm, this.log, this.messageResources).LoadAdmin(null));
			this.servicio = new Servicio(usuarioId, null,
					this.messageResources.getResource("jsp.servicio.importar.titulo"), null,
					this.messageResources.getResource("jsp.servicio.inicio"), "");
		}
	}

	public boolean EjecutarIniciativa(String[][] datos, Usuario usuario) {

		boolean respuesta = false;
		byte tipoImportacion = Byte.parseByte(pm.getProperty("tipoImportacion", "1"));
		respuesta = (new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio,
				null) == 10000 ? true : false);

		boolean activarSheduler = Boolean.parseBoolean(this.pm.getProperty("activarSheduler", "false"));
		if (!activarSheduler) {
			respuesta = Importar(datos);

			String[] argsReemplazo = new String[2];
			Calendar ahora = Calendar.getInstance();

			argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
			argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

			log.append("\n\n");
			log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafincalculo", argsReemplazo)
					+ "\n\n");
			if (!respuesta) {
				argsReemplazo[0] = messageResources.getResource("jsp.asistente.importacion.log.error.inesperado");
				argsReemplazo[1] = "";

				log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");
				this.servicio.setMensaje(
						this.messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo));
			}
			this.servicio.setLog(this.log.toString());

			new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
		} else {
			int terminar = Integer.parseInt(this.pm.getProperty("numeroEjecucion", "1"));
			int unidadTiempo = Integer.parseInt(this.pm.getProperty("unidadTiempo", "3"));

			Calendar inicio = Calendar.getInstance();
			String[] fecha = this.servicio.getFecha().split("-");

			int dia = Integer.parseInt(fecha[2].substring(0, 2));
			int mes = Integer.parseInt(fecha[1]);
			int ano = Integer.parseInt(fecha[0]);

			if (unidadTiempo != 0)
				inicio.set(ano, mes - 1, dia);
			else
				inicio.set(ano, mes - 1, dia, 8, 0, 0);

			Tarea t1 = new Tarea();
			TimeUnit timeUnit;
			long duracion;
			if (unidadTiempo == 0) {
				timeUnit = TimeUnit.DAYS;
				duracion = 1000 * 60 * 60 * 24 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 1) {
				timeUnit = TimeUnit.HOURS;
				duracion = 1000 * 60 * 60 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 2) {
				timeUnit = TimeUnit.MINUTES;
				duracion = 1000 * 60 + (inicio.getTimeInMillis());
			} else {
				timeUnit = TimeUnit.SECONDS;
				duracion = 1000 + (inicio.getTimeInMillis());
			}

			Calendar nowDuracion = Calendar.getInstance();
			nowDuracion.setTimeInMillis(duracion);

			t1.programarIniciativa(duracion, terminar, (terminar == 0), timeUnit, this.log, this.messageResources,
					this.pm, datos, this.servicio, usuario);
		}

		return respuesta;
	}

	public boolean EjecutarActividad(String[][] datos, Usuario usuario) {

		boolean respuesta = false;
		byte tipoImportacion = Byte.parseByte(pm.getProperty("tipoImportacion", "1"));
		respuesta = (new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio,
				null) == 10000 ? true : false);

		boolean activarSheduler = Boolean.parseBoolean(this.pm.getProperty("activarSheduler", "false"));
		if (!activarSheduler) {
			respuesta = Importar(datos);

			String[] argsReemplazo = new String[2];
			Calendar ahora = Calendar.getInstance();

			argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
			argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

			log.append("\n\n");
			log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafincalculo", argsReemplazo)
					+ "\n\n");
			if (!respuesta) {
				argsReemplazo[0] = messageResources.getResource("jsp.asistente.importacion.log.error.inesperado");
				argsReemplazo[1] = "";

				log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");
				this.servicio.setMensaje(
						this.messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo));
			}
			this.servicio.setLog(this.log.toString());

			new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
		} else {
			int terminar = Integer.parseInt(this.pm.getProperty("numeroEjecucion", "1"));
			int unidadTiempo = Integer.parseInt(this.pm.getProperty("unidadTiempo", "3"));

			Calendar inicio = Calendar.getInstance();
			String[] fecha = this.servicio.getFecha().split("-");

			int dia = Integer.parseInt(fecha[2].substring(0, 2));
			int mes = Integer.parseInt(fecha[1]);
			int ano = Integer.parseInt(fecha[0]);

			if (unidadTiempo != 0)
				inicio.set(ano, mes - 1, dia);
			else
				inicio.set(ano, mes - 1, dia, 8, 0, 0);

			Tarea t1 = new Tarea();
			TimeUnit timeUnit;
			long duracion;
			if (unidadTiempo == 0) {
				timeUnit = TimeUnit.DAYS;
				duracion = 1000 * 60 * 60 * 24 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 1) {
				timeUnit = TimeUnit.HOURS;
				duracion = 1000 * 60 * 60 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 2) {
				timeUnit = TimeUnit.MINUTES;
				duracion = 1000 * 60 + (inicio.getTimeInMillis());
			} else {
				timeUnit = TimeUnit.SECONDS;
				duracion = 1000 + (inicio.getTimeInMillis());
			}

			Calendar nowDuracion = Calendar.getInstance();
			nowDuracion.setTimeInMillis(duracion);

			t1.programarIniciativa(duracion, terminar, (terminar == 0), timeUnit, this.log, this.messageResources,
					this.pm, datos, this.servicio, usuario);
		}

		return respuesta;
	}

	public boolean Ejecutar(String[][] datos) {
		boolean respuesta = false;
		byte tipoImportacion = Byte.parseByte(pm.getProperty("tipoImportacion", "1"));
		respuesta = (new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio,
				null) == 10000 ? true : false);

		boolean activarSheduler = Boolean.parseBoolean(this.pm.getProperty("activarSheduler", "false"));
		if (!activarSheduler) {
			respuesta = Importar(datos);

			String[] argsReemplazo = new String[2];
			Calendar ahora = Calendar.getInstance();

			argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
			argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

			log.append("\n\n");
			log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafincalculo", argsReemplazo)
					+ "\n\n");
			if (!respuesta) {
				argsReemplazo[0] = messageResources.getResource("jsp.asistente.importacion.log.error.inesperado");
				argsReemplazo[1] = "";

				log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");
				this.servicio.setMensaje(
						this.messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo));
			}
			this.servicio.setLog(this.log.toString());

			new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
		} else {
			int terminar = Integer.parseInt(this.pm.getProperty("numeroEjecucion", "1"));
			int unidadTiempo = Integer.parseInt(this.pm.getProperty("unidadTiempo", "3"));

			Calendar inicio = Calendar.getInstance();
			String[] fecha = this.servicio.getFecha().split("-");

			int dia = Integer.parseInt(fecha[2].substring(0, 2));
			int mes = Integer.parseInt(fecha[1]);
			int ano = Integer.parseInt(fecha[0]);

			if (unidadTiempo != 0)
				inicio.set(ano, mes - 1, dia);
			else
				inicio.set(ano, mes - 1, dia, 8, 0, 0);

			Tarea t1 = new Tarea();
			TimeUnit timeUnit;
			long duracion;
			if (unidadTiempo == 0) {
				timeUnit = TimeUnit.DAYS;
				duracion = 1000 * 60 * 60 * 24 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 1) {
				timeUnit = TimeUnit.HOURS;
				duracion = 1000 * 60 * 60 + (inicio.getTimeInMillis());
			} else if (unidadTiempo == 2) {
				timeUnit = TimeUnit.MINUTES;
				duracion = 1000 * 60 + (inicio.getTimeInMillis());
			} else {
				timeUnit = TimeUnit.SECONDS;
				duracion = 1000 + (inicio.getTimeInMillis());
			}

			Calendar nowDuracion = Calendar.getInstance();
			nowDuracion.setTimeInMillis(duracion);

			t1.programar(duracion, terminar, (terminar == 0), timeUnit, this.log, this.messageResources, this.pm, datos,
					this.servicio);
		}

		return respuesta;
	}

	public List<List<Columna>> getDatos(boolean contar, Transaccion transaccion) {
		List<List<Columna>> datos = new ArrayList<List<Columna>>();
		Connection cn = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean transActiva = false;

		try {
			cn = new ConnectionManager(pm).getConnection();
			stm = cn.createStatement();
			transActiva = true;

			String sql = "";
			String campos = "";
			String orden = "";
			String campoFecha = "";
			String campoFechaFormato = "";
			String campoNumeroFormato = "";
			for (Iterator<Columna> iterc = transaccion.getTabla().getColumnas().iterator(); iterc.hasNext();) {
				Columna columna = (Columna) iterc.next();
				campos = campos + columna.getNombre() + ",";
				if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					orden = orden + columna.getNombre() + " DESC,";
				else
					orden = orden + columna.getNombre() + " ASC,";
				if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue()) {
					campoFecha = columna.getNombre();
					campoFechaFormato = columna.getFormato();
				} else if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
					campoNumeroFormato = columna.getFormato();
			}
			if (!campos.equals(""))
				campos = campos.substring(0, campos.length() - 1);
			if (!orden.equals(""))
				orden = orden.substring(0, orden.length() - 1);
			else
				orden = campoFecha + " ASC";

			Calendar fechaCal = Calendar.getInstance();
			String[] fecha = transaccion.getFechaInicial().split("/");
			int day = Integer.valueOf(fecha[0]);
			int mes = Integer.valueOf(fecha[1]);
			int ano = Integer.valueOf(fecha[2]);
			fechaCal.set(Calendar.YEAR, ano);
			fechaCal.set(Calendar.MONTH, mes - 1);
			fechaCal.set(Calendar.DATE, day);
			String fechaInicial = "{ts '" + FechaUtil.dateToStringformatoTS(fechaCal.getTime()) + "'}";

			fechaCal = Calendar.getInstance();
			fecha = transaccion.getFechaFinal().split("/");
			day = Integer.valueOf(fecha[0]);
			mes = Integer.valueOf(fecha[1]);
			ano = Integer.valueOf(fecha[2]);
			fechaCal.set(Calendar.YEAR, ano);
			fechaCal.set(Calendar.MONTH, mes - 1);
			fechaCal.set(Calendar.DATE, day);
			String fechaFinal = "{ts '" + FechaUtil.dateToStringformatoTS(fechaCal.getTime()) + "'}";

			String rdbmsid = new ConnectionManager(pm).getRdbmsid();
			String limite = "";
			if (!contar) {
				if (transaccion.getNumeroRegistros() != null && transaccion.getNumeroRegistros().intValue() != 0) {
					if (rdbmsid.equals("SQL_SERVER") || rdbmsid.equals("ORACLE"))
						limite = "TOP " + transaccion.getNumeroRegistros().toString();
					else if (rdbmsid.equals("POSTGRESQL"))
						limite = "LIMIT " + transaccion.getNumeroRegistros().toString();
				}
			} else
				campos = "COUNT(*) AS Total";

			sql = "SELECT " + ((rdbmsid.equals("SQL_SERVER") || rdbmsid.equals("ORACLE")) ? limite + " " : "") + campos
					+ " FROM " + transaccion.getTabla().getNombre() + " WHERE " + campoFecha + " BETWEEN "
					+ fechaInicial + " AND " + fechaFinal;
			if (!contar)
				sql = sql + " ORDER BY " + orden + ((rdbmsid.equals("POSTGRESQL")) ? (" " + limite) : "");

			rs = stm.executeQuery(sql);

			List<Columna> campo = null;

			Locale currentLocale = new Locale("en", "US");
			NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
			DecimalFormat decimalformat = (DecimalFormat) numberFormatter;
			decimalformat.applyPattern(!campoNumeroFormato.equals("") ? campoNumeroFormato : "#,##0.00");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(campoFechaFormato);

			if (!contar) {
				List<Columna> columnaAnterior = transaccion.getTabla().getColumnas();
				String valor = "";
				while (rs.next()) {
					campo = new ArrayList<Columna>();
					Columna c = null;
					for (Iterator<Columna> col = transaccion.getTabla().getColumnas().iterator(); col.hasNext();) {
						Columna columna = (Columna) col.next();
						if (columna.getVisible()) {
							c = new Columna();
							c.setNombre(columna.getNombre());
							c.setAlias(columna.getAlias());
							valor = "";
							if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue())
								valor = decimalformat.format(new Double(
										VgcFormatter.parsearNumeroFormateado(rs.getString(columna.getNombre()))));
							else if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue()) {
								fecha = rs.getString(columna.getNombre()).substring(0, 10).split("-");
								fechaCal = Calendar.getInstance();
								ano = Integer.valueOf(fecha[0]);
								mes = Integer.valueOf(fecha[1]);
								day = Integer.valueOf(fecha[2]);
								fechaCal.set(Calendar.YEAR, ano);
								fechaCal.set(Calendar.MONTH, mes - 1);
								fechaCal.set(Calendar.DATE, day);
								if (campoFechaFormato.equals("dd/mm/yyyy"))
									valor = day + "/" + (mes < 10 ? "0" + mes : mes) + "/" + ano;
								else
									valor = day + "/" + (mes < 10 ? "0" + mes : mes) + "/" + ano;
								valor = VgcFormatter.formatearFecha(simpleDateFormat.parse(valor), campoFechaFormato);
							} else
								valor = rs.getString(columna.getNombre());

							if (columna.getAgrupar()) {
								for (Iterator<Columna> colAnt = columnaAnterior.iterator(); colAnt.hasNext();) {
									Columna cAnterior = (Columna) colAnt.next();
									if (cAnterior.getNombre().equals(columna.getNombre())) {
										if (!valor.equals(cAnterior.getValorArchivo())) {
											c.setValorArchivo(valor);
											cAnterior.setValorArchivo(valor);
										} else
											c.setValorArchivo("");
										break;
									}
								}
							} else
								c.setValorArchivo(valor);
							c.setValorReal(valor);
							c.setTipo(columna.getTipo());
							c.setFormato(columna.getFormato());
							campo.add(c);
						}
					}

					if (campo.size() > 0) {
						boolean hayFilaVacia = true;
						for (Iterator<Columna> col = campo.iterator(); col.hasNext();) {
							Columna columna = (Columna) col.next();
							if (!columna.getValorArchivo().equals("")) {
								hayFilaVacia = false;
								break;
							}
						}
						if (!hayFilaVacia)
							datos.add(campo);
					}
				}
			} else {
				if (rs.next()) {
					campo = new ArrayList<Columna>();
					Columna c = new Columna();
					c.setNombre("Total");
					c.setValorArchivo(rs.getString("Total"));
					campo.add(c);
					datos.add(campo);
				}
			}
			rs.close();
		} catch (Exception e) {

		} finally {
			try {
				if (transActiva)
					stm.close();
			} catch (Exception localException8) {
			}

			try {
				if (transActiva) {
					cn.setAutoCommit(true);
					cn.close();
					cn = null;
				}
			} catch (Exception localException9) {
			}
		}

		return datos;
	}

	public int Ejecutar(List<List<ObjetoClaveValor>> datos, Transaccion transaccion) {
		int respuesta = 10000;
		// boolean logMediciones = Boolean.parseBoolean(pm.getProperty("logMediciones",
		// "false"));
		boolean logErrores = Boolean.parseBoolean(pm.getProperty("logErrores", "false"));
		String[] argsReemplazo = new String[5];

		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;
		List<Medicion> mediciones = new ArrayList<Medicion>();

		try {
			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;

			List<String> clavesPrimarias = new ArrayList<String>();
			String clavePrimariaNombre = null;
			String clavePrimariaFormato = null;
			Byte clavePrimariaTipo = null;
			for (Iterator<Columna> iterc = transaccion.getTabla().getColumnas().iterator(); iterc.hasNext();) {
				Columna columna = (Columna) iterc.next();
				if (columna.getClavePrimaria()) {
					clavePrimariaNombre = columna.getNombre();
					clavePrimariaFormato = columna.getFormato();
					clavePrimariaTipo = columna.getTipo();
					break;
				}
			}

			String sql = "";
			Calendar fechaCal = Calendar.getInstance();
			String[] fecha;
			Date valorFecha = null;
			int ano = 0;
			int mes = 0;
			int dia = 0;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
			if (clavePrimariaNombre != null && !clavePrimariaNombre.equals("")) {
				for (Iterator<?> iter = datos.iterator(); iter.hasNext();) {
					List<ObjetoClaveValor> columnas = (List<ObjetoClaveValor>) iter.next();
					for (Iterator<ObjetoClaveValor> col = columnas.iterator(); col.hasNext();) {
						ObjetoClaveValor campo = (ObjetoClaveValor) col.next();
						if (campo.getClave().equals(clavePrimariaNombre)) {
							if (!clavesPrimarias.contains(campo.getValor()))
								clavesPrimarias.add(campo.getValor());
							break;
						}
					}
				}
				for (Iterator<?> iter = clavesPrimarias.iterator(); iter.hasNext();) {
					String campo = (String) iter.next();
					sql = "DELETE FROM " + transaccion.getTabla().getNombre() + " WHERE " + clavePrimariaNombre + " = ";
					if (clavePrimariaTipo.byteValue() == ColumnaTipo.getTipoDate().byteValue()) {
						if (clavePrimariaFormato.equals("dd/mm/yyyy")) {
							fecha = campo.substring(0, 10).split("/");
							dia = Integer.valueOf(fecha[0]);
							mes = Integer.valueOf(fecha[1]);
							ano = Integer.valueOf(fecha[2]);
							fechaCal = Calendar.getInstance();
							fechaCal.set(Calendar.YEAR, ano);
							fechaCal.set(Calendar.MONTH, mes - 1);
							fechaCal.set(Calendar.DATE, dia);
							valorFecha = fechaCal.getTime();
						} else {
							simpleDateFormat = new SimpleDateFormat(clavePrimariaFormato);
							valorFecha = simpleDateFormat.parse(campo);
						}
						sql = sql + "{ts '" + FechaUtil.dateToStringformatoTS(valorFecha) + "'}";
					} else if (clavePrimariaTipo.byteValue() == ColumnaTipo.getTipoFloat().byteValue())
						sql = sql + campo;
					else if (clavePrimariaTipo.byteValue() == ColumnaTipo.getTipoString().byteValue())
						sql = sql + "'" + campo + "'";
					stm.executeUpdate(sql);
				}
			}

			sql = "";
			String tabla = "INSERT INTO " + transaccion.getTabla().getNombre() + " (";
			String campos = "";
			String valor = "";
			boolean hayCampos = false;
			Long registros = 0L;
			String valorMonto = null;
			Long indicadorMonto = null;
			if (respuesta == 10000) {
				for (Iterator<?> iter = datos.iterator(); iter.hasNext();) {
					List<ObjetoClaveValor> columnas = (List<ObjetoClaveValor>) iter.next();
					campos = "";
					valor = "";
					valorFecha = null;
					valorMonto = null;
					indicadorMonto = null;
					registros++;
					for (Iterator<ObjetoClaveValor> col = columnas.iterator(); col.hasNext();) {
						ObjetoClaveValor campo = (ObjetoClaveValor) col.next();
						campos = campos + campo.getClave() + ",";
						for (Iterator<Columna> iterc = transaccion.getTabla().getColumnas().iterator(); iterc
								.hasNext();) {
							Columna columna = (Columna) iterc.next();
							if (columna.getNombre().equalsIgnoreCase(campo.getClave())) {
								hayCampos = true;
								if (columna.getTipo().byteValue() == ColumnaTipo.getTipoString().byteValue())
									valor = valor + "'" + campo.getValor().replace("'", "") + "',";
								else if (columna.getTipo().byteValue() == ColumnaTipo.getTipoDate().byteValue()) {
									if (columna.getFormato().equals("dd/mm/yyyy")) {
										fecha = campo.getValor().substring(0, 10).split("/");
										dia = Integer.valueOf(fecha[0]);
										mes = Integer.valueOf(fecha[1]);
										ano = Integer.valueOf(fecha[2]);
										fechaCal = Calendar.getInstance();
										fechaCal.set(Calendar.YEAR, ano);
										fechaCal.set(Calendar.MONTH, mes - 1);
										fechaCal.set(Calendar.DATE, dia);
										valorFecha = fechaCal.getTime();
									} else {
										simpleDateFormat = new SimpleDateFormat(columna.getFormato());
										valorFecha = simpleDateFormat.parse(campo.getValor());
									}
									valor = valor + "{ts '" + FechaUtil.dateToStringformatoTS(valorFecha) + "'}" + ",";
								} else if (columna.getTipo().byteValue() == ColumnaTipo.getTipoFloat().byteValue()) {
									valorMonto = campo.getValor();
									valor = valor + valorMonto + ",";
									if (columna.getIndicadorId() != null && columna.getIndicadorId() != 0L)
										indicadorMonto = columna.getIndicadorId();
								}
								break;
							}
						}
					}

					// Calcular Indicadores Transaccion
					if (transaccion.getIndicadorId() != null && transaccion.getIndicadorId() != 0L) {
						ano = FechaUtil.getAno(valorFecha);
						int periodo = PeriodoUtil.getPeriodoDate(valorFecha, transaccion.getFrecuencia());
						Double valorMedicion = 1D;
						boolean registroEncontrado = false;
						Medicion medicion = new Medicion(new MedicionPK(transaccion.getIndicadorId(), ano, periodo,
								SerieTiempo.getSerieRealId()), valorMedicion, new Boolean(false));
						for (Iterator<Medicion> iterm = mediciones.iterator(); iterm.hasNext();) {
							Medicion med = (Medicion) iterm.next();
							if (med.getMedicionId().getIndicadorId().longValue() == medicion.getMedicionId()
									.getIndicadorId().longValue()
									&& med.getMedicionId().getAno().intValue() == medicion.getMedicionId().getAno()
											.intValue()
									&& med.getMedicionId().getPeriodo().intValue() == medicion.getMedicionId()
											.getPeriodo().intValue()
									&& med.getMedicionId().getSerieId().longValue() == medicion.getMedicionId()
											.getSerieId().longValue()) {
								registroEncontrado = true;
								med.setValor((med.getValor() + medicion.getValor()));
							}
						}
						if (!registroEncontrado)
							mediciones.add(medicion);
					}

					// Calcular Indicadores Monto
					if (indicadorMonto != null && indicadorMonto != 0L) {
						ano = FechaUtil.getAno(valorFecha);
						int periodo = PeriodoUtil.getPeriodoDate(valorFecha, transaccion.getFrecuencia());
						Double valorMedicion = null;
						if (valorMonto != null)
							valorMedicion = new Double(VgcFormatter.parsearNumeroFormateado(valorMonto));
						boolean registroEncontrado = false;
						Medicion medicion = new Medicion(
								new MedicionPK(indicadorMonto, ano, periodo, SerieTiempo.getSerieRealId()),
								valorMedicion, new Boolean(false));
						for (Iterator<Medicion> iterm = mediciones.iterator(); iterm.hasNext();) {
							Medicion med = (Medicion) iterm.next();
							if (med.getMedicionId().getIndicadorId().longValue() == medicion.getMedicionId()
									.getIndicadorId().longValue()
									&& med.getMedicionId().getAno().intValue() == medicion.getMedicionId().getAno()
											.intValue()
									&& med.getMedicionId().getPeriodo().intValue() == medicion.getMedicionId()
											.getPeriodo().intValue()
									&& med.getMedicionId().getSerieId().longValue() == medicion.getMedicionId()
											.getSerieId().longValue()) {
								registroEncontrado = true;
								med.setValor((med.getValor() + medicion.getValor()));
							}
						}
						if (!registroEncontrado)
							mediciones.add(medicion);
					}

					if (hayCampos && !valor.equals(""))
						valor = valor.substring(0, valor.length() - 1);
					else
						valor = "";
					if (hayCampos && !campos.equals(""))
						campos = campos.substring(0, campos.length() - 1);
					else
						campos = "";

					sql = tabla + campos + ") values (" + valor + ")";

					int actualizados = 0;
					if (registros == 1490)
						actualizados = 0;
					if (!tabla.equals("") && !campos.equals("") && !valor.equals(""))
						actualizados = stm.executeUpdate(sql);
					if (actualizados == 0) {
						respuesta = 10003;
						if (logErrores) {
							argsReemplazo[0] = registros + "";
							argsReemplazo[1] = "";
							argsReemplazo[2] = "";
							argsReemplazo[3] = "";
							argsReemplazo[4] = "";

							log.append(messageResources.getResource(
									"jsp.asistente.importacion.transaccion.log.medicion.general.registro.error",
									argsReemplazo) + "\n\n");
						}
						break;
					}
				}
			}

			if (respuesta == 10000 && mediciones.size() > 0)
				respuesta = new MedicionManager(pm, log, messageResources).saveMediciones(null, mediciones, null, true,
						true, stm);

			if (respuesta == 10000) {
				cn.commit();
				cn.setAutoCommit(true);
				transActiva = false;
			}
		} catch (Exception e) {
			if (logErrores) {
				argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
				argsReemplazo[1] = "";
				argsReemplazo[2] = "";
				argsReemplazo[3] = "";
				argsReemplazo[4] = "";

				log.append(messageResources.getResource(
						"jsp.asistente.importacion.transaccion.log.medicion.general.error", argsReemplazo) + "\n\n");
			}
			respuesta = 10003;
		} finally {
			try {
				if (transActiva)
					stm.close();
			} catch (Exception localException8) {
			}

			try {
				if (transActiva) {
					cn.setAutoCommit(true);
					cn.close();
					cn = null;
				}
			} catch (Exception localException9) {
			}
		}

		return respuesta;
	}

	public boolean ImportarIniciativa(String[][] datos, Usuario usuario) {
		boolean respuesta = false;

		boolean logMediciones = Boolean.parseBoolean(pm.getProperty("logMediciones", "false"));
		boolean logErrores = Boolean.parseBoolean(pm.getProperty("logErrores", "false"));

		String[] argsReemplazo = new String[13];

		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();

		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;
		boolean existe = false;

		try {
			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;

			List<OrganizacionStrategos> organizaciones = new ArrayList<OrganizacionStrategos>();
			OrganizacionStrategos organizacion = new OrganizacionStrategos();
			boolean hayOrganizacion = false;
			long num = 0L;

			Long organizacionSeleccionadaId = Long.parseLong(pm.getProperty("organizacionId", "0"));
			int totalDatos = datos.length;
			organizaciones = new OrganizacionManager(pm, log, messageResources)
					.getArbolCompletoOrganizaciones(organizacionSeleccionadaId, stm);

			if (organizaciones.size() > 0) {
				String codigoOrgArchivo = "";
				String codigoIniArchivo = "";
				String nombreArchivo = "";
				String descripcionArchivo = "";
				String tipoIniciativaArchivo = "";
				String anioArchivo = "";
				String frecuenciaArchivo = "";
				String tipoMedicionArchivo = "";
				String alertaVerdeArchivo = "";
				String alertaAmarillaArchivo = "";
				String crearCuentasArchivo = "";
				String unidadMedidaArchivo = "";

				for (int f = 0; f < datos.length; f++) {
					codigoOrgArchivo = datos[f][0] != null ? datos[f][0] : "";
					codigoIniArchivo = datos[f][1] != null ? datos[f][1] : "";
					nombreArchivo = datos[f][2] != null ? datos[f][2] : "";
					descripcionArchivo = datos[f][3] != null ? datos[f][3] : "";
					tipoIniciativaArchivo = datos[f][4] != null ? datos[f][4] : "";
					anioArchivo = datos[f][5] != null ? datos[f][5] : "";
					frecuenciaArchivo = datos[f][6] != null ? datos[f][6] : "";
					tipoMedicionArchivo = datos[f][7] != null ? datos[f][7] : "";
					alertaVerdeArchivo = datos[f][8] != null ? datos[f][8] : "";
					alertaAmarillaArchivo = datos[f][9] != null ? datos[f][9] : "";
					crearCuentasArchivo = datos[f][10] != null ? datos[f][10] : "";
					unidadMedidaArchivo = datos[f][11] != null ? datos[f][11] : "";

					if (!codigoOrgArchivo.equals("") && !codigoIniArchivo.equals("") && !nombreArchivo.equals("")
							&& !descripcionArchivo.equals("") && !tipoIniciativaArchivo.equals("")
							&& !anioArchivo.equals("") && !frecuenciaArchivo.equals("")
							&& !tipoMedicionArchivo.equals("") && !alertaVerdeArchivo.equals("")
							&& !alertaAmarillaArchivo.equals("") && !crearCuentasArchivo.equals("")
							&& !unidadMedidaArchivo.equals("")) {
						hayOrganizacion = false;
						for (Iterator<?> iter = organizaciones.iterator(); iter.hasNext();) {
							organizacion = (OrganizacionStrategos) iter.next();
							if (organizacion.getEnlaceParcial() != null
									&& organizacion.getEnlaceParcial().equalsIgnoreCase(codigoOrgArchivo)) {
								num++;
								if (this.logConsolaDetallado)
									System.out.println("Leyendo indicador numero: " + num + " de " + totalDatos
											+ "--> codigo enlace: " + organizacion.getEnlaceParcial());

								this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
								this.servicio.setMensaje("Leyendo indicador numero: " + num + " --> codigo enlace: "
										+ organizacion.getEnlaceParcial());
								this.servicio.setLog(log.toString());
								new ServicioManager(this.pm, this.log, this.messageResources)
										.saveServicio(this.servicio, null);

								StrategosIniciativasService strategosIniciativaService = StrategosServiceFactory
										.getInstance().openStrategosIniciativasService();

								Iniciativa iniciativa = new Iniciativa();
								iniciativa.setIniciativaId(strategosIniciativaService.getUniqueId());
								iniciativa.setOrganizacionId(organizacion.getOrganizacionId());
								iniciativa.setCodigoIniciativa(codigoIniArchivo);

								iniciativa.setNombre(nombreArchivo);
								iniciativa.setAlertaZonaVerde(Double.valueOf(alertaVerdeArchivo));
								iniciativa.setAlertaZonaAmarilla(Double.valueOf(alertaAmarillaArchivo));
								iniciativa.setMemoIniciativa(descripcionArchivo);

								iniciativa.setTipoMedicion(Byte.parseByte(tipoMedicionArchivo));
								iniciativa.setProyectoId(Long.parseLong(tipoIniciativaArchivo));
								iniciativa.setAnioFormulacion(anioArchivo);

								if (frecuenciaArchivo.equals("DIARIA"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 0));
								else if (frecuenciaArchivo.equals("SEMANAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 1));
								else if (frecuenciaArchivo.equals("QUINCENAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 2));
								else if (frecuenciaArchivo.equals("MENSUAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 3));
								else if (frecuenciaArchivo.equals("BIMENSUAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 4));
								else if (frecuenciaArchivo.equals("TRIMESTRAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 5));
								else if (frecuenciaArchivo.equals("CUATRIMESTRAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 6));
								else if (frecuenciaArchivo.equals("SEMESTRAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 7));
								else if (frecuenciaArchivo.equals("ANUAL"))
									iniciativa.setFrecuencia(Byte.valueOf((byte) 8));

								if (crearCuentasArchivo.equals("SI"))
									iniciativa.setUnidadMedida(Long.parseLong(unidadMedidaArchivo));

								iniciativas.add(iniciativa);
								argsReemplazo[0] = organizacion.getEnlaceParcial();
								argsReemplazo[1] = codigoIniArchivo;
								argsReemplazo[2] = nombreArchivo;
								argsReemplazo[3] = descripcionArchivo;
								argsReemplazo[4] = tipoIniciativaArchivo;
								argsReemplazo[5] = anioArchivo;
								argsReemplazo[6] = frecuenciaArchivo;
								argsReemplazo[7] = tipoMedicionArchivo;
								argsReemplazo[8] = alertaVerdeArchivo;
								argsReemplazo[9] = alertaAmarillaArchivo;
								argsReemplazo[10] = crearCuentasArchivo;
								argsReemplazo[11] = unidadMedidaArchivo;
								argsReemplazo[12] = "";

								if (logMediciones)
									log.append(messageResources.getResource(
											"jsp.asistente.importacion.log.indicador.success", argsReemplazo) + "\n");

							}
							break;
						}
						if (!hayOrganizacion && logErrores) {
							argsReemplazo[0] = codigoOrgArchivo;
							argsReemplazo[1] = "";
							argsReemplazo[2] = "";
							argsReemplazo[3] = "";
							argsReemplazo[4] = "";
							argsReemplazo[5] = "";
							argsReemplazo[6] = "";
							argsReemplazo[7] = "";
							argsReemplazo[8] = "";
							argsReemplazo[9] = "";
							argsReemplazo[10] = "";
							argsReemplazo[11] = "";
							argsReemplazo[12] = "";
							log.append(messageResources.getResource(
									"jsp.asistente.importacion.log.error.nohaycodigoenlace", argsReemplazo) + "\n");
						}
					}
				}
			} else if (logErrores)
				log.append(messageResources.getResource("jsp.asistente.importacion.log.nohayindicadores") + "\n");

			int totalIniciativas = 0;
			if (iniciativas.size() > 0)
				totalIniciativas = iniciativas.size();

			if (totalIniciativas > 0) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
				this.servicio.setMensaje("Salvando iniciativas, total de iniciativas a salvar: " + totalIniciativas);
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
			}

			int res = 10000;
			if (iniciativas.size() > 0) {
				res = new IniciativaManager(pm, log, messageResources).saveIniciativas(iniciativas, stm);
			}

			if (res == 10000) {
				cn.commit();
				cn.setAutoCommit(true);
			} else if (res != 10000) {
				cn.rollback();
				cn.setAutoCommit(true);
			}
			stm.close();
			cn.close();
			cn = null;
			transActiva = false;

			Message message = new Message(this.servicio.getUsuarioId(), this.servicio.getFecha(),
					MessageStatus.getStatusPendiente(), "", MessageType.getTypeAlerta(), this.servicio.getNombre());
			if (res == 10000) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusSuccess());
				this.servicio.setMensaje(messageResources.getResource("importarindicadores.success"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("importarindicadores.success"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);

				respuesta = true;
			} else {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusNoSuccess());
				this.servicio.setMensaje(messageResources.getResource("calcularindicadores.error"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("calcularindicadores.error"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);
			}
		} catch (Exception e) {
			argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
			argsReemplazo[1] = "";
			argsReemplazo[2] = "";
			argsReemplazo[3] = "";
			argsReemplazo[4] = "";
			argsReemplazo[5] = "";
			argsReemplazo[6] = "";
			argsReemplazo[7] = "";
			argsReemplazo[8] = "";
			argsReemplazo[9] = "";

			log.append(
					messageResources.getResource("jsp.asistente.importacion.log.medicion.general.error", argsReemplazo)
							+ "\n\n");
		} finally {
			try {
				if (transActiva)
					stm.close();
			} catch (Exception localException8) {
			}

			try {
				if (transActiva) {
					cn.setAutoCommit(true);
					cn.close();
					cn = null;
				}
			} catch (Exception localException9) {
			}
		}
		return respuesta;
	}

	public boolean ImportarActividad(String[][] datos, Usuario usuario) {
		boolean respuesta = false;

		boolean logMediciones = Boolean.parseBoolean(pm.getProperty("logMediciones", "false"));
		boolean logErrores = Boolean.parseBoolean(pm.getProperty("logErrores", "false"));

		String[] argsReemplazo = new String[10];

		List<PryActividad> actividades = new ArrayList<PryActividad>();

		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;
		boolean existe = false;

		try {
			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;

			List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
			Iniciativa iniciativa = new Iniciativa();
			boolean hayIniciativa = false;
			long num = 0L;

			Long iniciativaSeleccionadaId = Long.parseLong(pm.getProperty("iniciativaId", "0"));
			int totalDatos = datos.length;
			iniciativas = new IniciativaManager(pm, log, messageResources).getIniciativas(null, stm);

			if (iniciativas.size() > 0) {
				String codigoIniArchivo = "";
				String nombreArchivo = "";
				String descripcionArchivo = "";
				String fechaInicioArchivo = "";
				String fechaCulminacionArchivo = "";
				String alertaVerdeArchivo = "";
				String alertaAmarillaArchivo = "";
				String unidadMedidaArchivo = "";
				String numeroActividadArchivo = "";

				for (int f = 0; f < datos.length; f++) {

					codigoIniArchivo = datos[f][0] != null ? datos[f][0] : "";
					nombreArchivo = datos[f][1] != null ? datos[f][1] : "";
					descripcionArchivo = datos[f][2] != null ? datos[f][2] : "";
					fechaInicioArchivo = datos[f][3] != null ? datos[f][3] : "";
					fechaCulminacionArchivo = datos[f][4] != null ? datos[f][4] : "";
					alertaVerdeArchivo = datos[f][5] != null ? datos[f][5] : "";
					alertaAmarillaArchivo = datos[f][6] != null ? datos[f][6] : "";
					unidadMedidaArchivo = datos[f][7] != null ? datos[f][7] : "";
					numeroActividadArchivo = datos[f][8] != null ? datos[f][8] : "";

					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					PryCalendario calendario = new PryCalendario();

					if (!codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("")
							&& !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("")
							&& !unidadMedidaArchivo.equals("") && !fechaCulminacionArchivo.equals("")
							&& !fechaInicioArchivo.equals("") && !numeroActividadArchivo.equals("")) {
						hayIniciativa = false;

						for (Iterator<?> iter = iniciativas.iterator(); iter.hasNext();) {
							iniciativa = (Iniciativa) iter.next();

							if (iniciativa.getCodigoIniciativa() != null
									&& iniciativa.getCodigoIniciativa().equalsIgnoreCase(codigoIniArchivo)) {
								num++;

								if (this.logConsolaDetallado)
									System.out.println("Leyendo actividad numero: " + num + " de " + totalDatos
											+ "--> codigo iniciativa: " + iniciativa.getCodigoIniciativa());								
								this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
								this.servicio.setMensaje("Leyendo actividad numero: " + num + " --> codigo iniciativa: "
										+ iniciativa.getCodigoIniciativa());
								this.servicio.setLog(log.toString());
								new ServicioManager(this.pm, this.log, this.messageResources)
										.saveServicio(this.servicio, null);

								Date fechaInicio = formato.parse(fechaInicioArchivo);
								Date fechaFinal = formato.parse(fechaCulminacionArchivo);
								Date fechaActual = new Date();
								
								Set<Date> diasFestivos = new HashSet<>();
						        // Agrega los días festivos a la lista (en formato Date)

								int duracion = calcularDiasHabiles(fechaInicio, fechaFinal, diasFestivos);		
																
								StrategosPryActividadesService strategosActividadesService = StrategosServiceFactory
										.getInstance().openStrategosPryActividadesService();
								
								
								Long claseId = strategosActividadesService.crearClaseIndicador(iniciativa.getProyectoId(), nombreArchivo, usuario);														
			
								Long indicadorId = strategosActividadesService.crearIndicador(iniciativa.getProyectoId(), claseId, nombreArchivo, Long.parseLong(unidadMedidaArchivo), Double.parseDouble(alertaVerdeArchivo), Double.parseDouble(alertaAmarillaArchivo), usuario);																
								
								PryActividad actividad = new PryActividad();
								
								actividad.setActividadId(strategosActividadesService.getUniqueId());
								actividad.setProyectoId(iniciativa.getProyectoId());
								actividad.setNombre(nombreArchivo);
								actividad.setIndicadorId(indicadorId);
								actividad.setClaseId(claseId);
								actividad.setDescripcion(descripcionArchivo);
								actividad.setComienzoPlan(fechaInicio);
								actividad.setFinPlan(fechaFinal);
								actividad.setComienzoReal(fechaInicio);
								actividad.setFinReal(fechaFinal);
								actividad.setDuracionPlan(new Double(duracion));
								actividad.setUnidadId(new Long(unidadMedidaArchivo));
								actividad.setFila(Integer.parseInt(numeroActividadArchivo));
								actividad.setNivel(1);
								actividad.setCompuesta(false);
								actividad.setCreado(fechaActual);
								actividad.setCreadoId(new Long(1));
								actividad.setNaturaleza((byte) 0);
								actividad.setTipoMedicion((byte) 0);
								actividad.setCrecimiento((byte) 3);							
								actividad.setAlertaZonaAmarilla(Double.parseDouble(alertaAmarillaArchivo));
								actividad.setAlertaZonaVerde(Double.parseDouble(alertaVerdeArchivo));

								actividades.add(actividad);

								argsReemplazo[0] = iniciativa.getCodigoIniciativa();
								argsReemplazo[1] = nombreArchivo;
								argsReemplazo[2] = descripcionArchivo;
								argsReemplazo[3] = fechaInicioArchivo;
								argsReemplazo[4] = fechaCulminacionArchivo;
								argsReemplazo[5] = alertaVerdeArchivo;
								argsReemplazo[6] = alertaAmarillaArchivo;
								argsReemplazo[7] = unidadMedidaArchivo;
								argsReemplazo[8] = numeroActividadArchivo;
								argsReemplazo[9] = "";

								if (logMediciones)
									log.append(messageResources.getResource(
											"jsp.asistente.importacion.log.indicador.success", argsReemplazo) + "\n");
							}

						}
						if (!hayIniciativa && logErrores) {
							argsReemplazo[0] = codigoIniArchivo;
							argsReemplazo[1] = "";
							argsReemplazo[2] = "";
							argsReemplazo[3] = "";
							argsReemplazo[4] = "";
							argsReemplazo[5] = "";
							argsReemplazo[6] = "";
							argsReemplazo[7] = "";
							argsReemplazo[8] = "";
							argsReemplazo[9] = "";
							log.append(messageResources.getResource(
									"jsp.asistente.importacion.log.error.nohaycodigoenlace", argsReemplazo) + "\n");
						}
					}
				}
			} else if (logErrores)
				log.append(messageResources.getResource("jsp.asistente.importacion.log.nohayindicadores") + "\n");
			
			int totalActividades = 0;
			if (actividades.size() > 0)
				totalActividades = actividades.size();

			if (totalActividades > 0) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
				this.servicio.setMensaje("Salvando actividades, total de actividades a salvar: " + totalActividades);
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
			}
			
			int res = 10000;
			if (actividades.size() > 0) {
				res = new PryActividadManager(pm, log, messageResources).saveActividades(actividades, stm);
			}

			if (res == 10000) {
				cn.commit();
				cn.setAutoCommit(true);
			} else if (res != 10000) {
				cn.rollback();
				cn.setAutoCommit(true);
			}
			stm.close();
			cn.close();
			cn = null;
			transActiva = false;

			Message message = new Message(this.servicio.getUsuarioId(), this.servicio.getFecha(),
					MessageStatus.getStatusPendiente(), "", MessageType.getTypeAlerta(), this.servicio.getNombre());
			if (res == 10000) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusSuccess());
				this.servicio.setMensaje(messageResources.getResource("importarindicadores.success"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("importarindicadores.success"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);

				respuesta = true;
			} else {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusNoSuccess());
				this.servicio.setMensaje(messageResources.getResource("calcularindicadores.error"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("calcularindicadores.error"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);
			}

		} catch (Exception e) {
			argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
			argsReemplazo[1] = "";
			argsReemplazo[2] = "";
			argsReemplazo[3] = "";
			argsReemplazo[4] = "";
			argsReemplazo[5] = "";
			argsReemplazo[6] = "";
			argsReemplazo[7] = "";
			argsReemplazo[8] = "";
			argsReemplazo[9] = "";

			log.append(
					messageResources.getResource("jsp.asistente.importacion.log.medicion.general.error", argsReemplazo)
							+ "\n\n");
		} finally {
			try {
				if (transActiva)
					stm.close();
			} catch (Exception localException8) {
			}

			try {
				if (transActiva) {
					cn.setAutoCommit(true);
					cn.close();
					cn = null;
				}
			} catch (Exception localException9) {
			}
		}

		return respuesta;
	}

	public static int calcularDiasHabiles(Date fechaInicio, Date fechaFin, Set<Date> diasFestivos) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaInicio);

		int diasHabiles = 0;

		while (cal.getTime().before(fechaFin) || cal.getTime().equals(fechaFin)) {
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

			if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY
					&& !diasFestivos.contains(cal.getTime())) {
				diasHabiles++;
			}

			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return diasHabiles;
	}

	public boolean Importar(String[][] datos) {
		boolean respuesta = false;

		byte tipoMedicion = Byte.parseByte(pm.getProperty("tipoMedicion", "0"));
		byte tipoImportacion = Byte.parseByte(pm.getProperty("tipoImportacion", "1"));
		boolean logMediciones = Boolean.parseBoolean(pm.getProperty("logMediciones", "false"));
		boolean logErrores = Boolean.parseBoolean(pm.getProperty("logErrores", "false"));
		String[] argsReemplazo = new String[5];

		List<Medicion> mediciones = new ArrayList<Medicion>();
		List<Meta> metas = new ArrayList<Meta>();
		Medicion medicion = null;
		Meta meta = null;
		Integer anoInicial = 0;
		Integer periodoInicial = 0;
		Integer anoFinal = 0;
		Integer periodoFinal = 0;
		boolean hayDiferentesFrecuencias = false;
		Byte frecuencia = null;
		Long organizacionId = 0L;
		boolean hayDiferentesOrganizaciones = false;

		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;

		try {
			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;

			List<Indicador> indicadores = new ArrayList<Indicador>();
			Indicador indicador = new Indicador();
			List<Indicador> indicadoresCalcular = new ArrayList<Indicador>();
			Long serieId = null;
			boolean haySerie = false;
			boolean hayIndicador = false;
			long num = 0L;

			Map<String, String> filtros = new HashMap<String, String>();
			filtros.put("codigo_enlace", "IS NOT NULL");
			if (tipoMedicion == 1)
				filtros.put("perspectiva_id", "IS NOT NULL");
			Long organizacionSeleccionadaId = Long.parseLong(pm.getProperty("organizacionId", "0"));
			if (organizacionSeleccionadaId != 0L)
				filtros.put("organizacion_Id", organizacionSeleccionadaId.toString());

			int totalDatos = datos.length;
			indicadores = new IndicadorManager(pm, log, messageResources).getIndicadores(filtros, stm);
			if (indicadores.size() > 0) {
				String codigoArchivo = "";
				String anoArchivo = "";
				String periodoArchivo = "";
				String medicionArchivo = "";
				for (int f = 0; f < datos.length; f++) {
					codigoArchivo = datos[f][0] != null ? datos[f][0] : "";
					anoArchivo = datos[f][1] != null ? datos[f][1] : "";
					periodoArchivo = datos[f][2] != null ? datos[f][2] : "";
					medicionArchivo = datos[f][3] != null ? datos[f][3] : "";

					if (!codigoArchivo.equals("") && !anoArchivo.equals("") && !periodoArchivo.equals("")
							&& !medicionArchivo.equals("")) {
						hayIndicador = false;
						for (Iterator<?> iter = indicadores.iterator(); iter.hasNext();) {
							indicador = (Indicador) iter.next();
							if (indicador.getCodigoEnlace() != null
									&& indicador.getCodigoEnlace().equalsIgnoreCase(codigoArchivo)) {
								num++;
								if (this.logConsolaDetallado)
									System.out.println("Leyendo indicador numero: " + num + " de " + totalDatos
											+ "--> codigo enlace: " + indicador.getCodigoEnlace());

								this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
								this.servicio.setMensaje("Leyendo indicador numero: " + num + " --> codigo enlace: "
										+ indicador.getCodigoEnlace());
								this.servicio.setLog(log.toString());
								new ServicioManager(this.pm, this.log, this.messageResources)
										.saveServicio(this.servicio, null);

								if (organizacionId.longValue() == 0)
									organizacionId = indicador.getOrganizacionId();
								if (organizacionId.longValue() != indicador.getOrganizacionId().longValue())
									hayDiferentesOrganizaciones = true;

								serieId = null;
								hayIndicador = true;
								if (tipoImportacion == 1)
									serieId = SerieTiempo.getSerieRealId();
								else if (tipoImportacion == 2)
									serieId = SerieTiempo.getSerieMinimoId();
								else if (tipoImportacion == 3)
									serieId = SerieTiempo.getSerieMaximoId();
								else if (tipoImportacion == 4)
									serieId = SerieTiempo.getSerieProgramadoId();
								haySerie = false;
								if (!indicadoresCalcular.contains(indicador))
									indicadoresCalcular.add(indicador);

								if (tipoMedicion == 0) {
									for (Iterator<SerieIndicador> i = indicador.getSeriesIndicador().iterator(); i
											.hasNext();) {
										SerieIndicador serie = i.next();
										if (serie.getPk().getSerieId().longValue() == serieId.longValue()) {
											haySerie = true;
											break;
										}
									}
									if (!haySerie) {
										argsReemplazo[0] = indicador.getNombre();
										argsReemplazo[1] = "";
										argsReemplazo[2] = "";
										argsReemplazo[3] = "";
										argsReemplazo[4] = "";
										if (logErrores)
											log.append(messageResources.getResource(
													"jsp.asistente.importacion.log.error.nohayserie", argsReemplazo)
													+ "\n");
										break;
									}

									if (anoInicial == 0) {
										anoInicial = Integer.parseInt(anoArchivo);
										anoFinal = Integer.parseInt(anoArchivo);
										periodoInicial = Integer.parseInt(periodoArchivo);
										periodoFinal = Integer.parseInt(periodoArchivo);
										frecuencia = indicador.getFrecuencia();
									}

									if (anoInicial > Integer.parseInt(anoArchivo))
										anoInicial = Integer.parseInt(anoArchivo);
									else if (anoFinal < Integer.parseInt(anoArchivo))
										anoFinal = Integer.parseInt(anoArchivo);

									if (periodoInicial > Integer.parseInt(periodoArchivo))
										periodoInicial = Integer.parseInt(periodoArchivo);
									else if (periodoFinal < Integer.parseInt(periodoArchivo))
										periodoFinal = Integer.parseInt(periodoArchivo);

									if (frecuencia.byteValue() != indicador.getFrecuencia().byteValue())
										hayDiferentesFrecuencias = true;

									List<Medicion> medicionesX = (List<Medicion>) new MedicionManager(pm, log,
											messageResources).getMedicionesPeriodo(indicador.getIndicadorId(), serieId,
													Integer.parseInt(anoArchivo), Integer.parseInt(anoArchivo),
													Integer.parseInt(periodoArchivo), Integer.parseInt(periodoArchivo),
													indicador.getFrecuencia(), stm);
									if (medicionesX.size() == 0) {
										medicion = new Medicion();
										MedicionPK medicionPk = new MedicionPK();
										medicionPk.setIndicadorId(indicador.getIndicadorId());
										medicionPk.setSerieId(serieId);
										medicionPk.setAno(Integer.parseInt(anoArchivo));
										medicionPk.setPeriodo(Integer.parseInt(periodoArchivo));
										medicion.setMedicionId(medicionPk);
									} else
										medicion = medicionesX.get(0);
									medicion.setValor(Double.parseDouble(medicionArchivo));
									medicion.setProtegido(new Boolean(false));
									mediciones.add(medicion);

									argsReemplazo[0] = indicador.getNombre();
									argsReemplazo[1] = anoArchivo;
									argsReemplazo[2] = periodoArchivo;
									argsReemplazo[3] = codigoArchivo;
									argsReemplazo[4] = "";

									if (logMediciones)
										log.append(messageResources.getResource(
												"jsp.asistente.importacion.log.indicador.success", argsReemplazo)
												+ "\n");
								} else if (tipoMedicion == 1) {
									if (tipoImportacion == 6 && periodoArchivo != "0") {
										if (logErrores)
											log.append(messageResources.getResource(
													"jsp.asistente.importacion.log.error.nohayperiodocero") + "\n");

										break;
									}

									if (anoInicial == 0) {
										anoInicial = Integer.parseInt(anoArchivo);
										anoFinal = Integer.parseInt(anoArchivo);
										periodoInicial = Integer.parseInt(periodoArchivo);
										periodoFinal = Integer.parseInt(periodoArchivo);
										frecuencia = indicador.getFrecuencia();
									}

									if (anoInicial > Integer.parseInt(anoArchivo))
										anoInicial = Integer.parseInt(anoArchivo);
									else if (anoFinal < Integer.parseInt(anoArchivo))
										anoFinal = Integer.parseInt(anoArchivo);

									if (periodoInicial > Integer.parseInt(periodoArchivo))
										periodoInicial = Integer.parseInt(periodoArchivo);
									else if (periodoFinal < Integer.parseInt(periodoArchivo))
										periodoFinal = Integer.parseInt(periodoArchivo);

									if (frecuencia.byteValue() != indicador.getFrecuencia().byteValue())
										hayDiferentesFrecuencias = true;

									Long plan = 0L;
									List<Perspectiva> perspectivas = new PerspectivaManager(pm, log, messageResources)
											.getIndicadoresPorPerspectiva(indicador.getIndicadorId(), plan, stm);

									for (Iterator<?> iterPlan = perspectivas.iterator(); iterPlan.hasNext();) {
										Perspectiva perspectiva = (Perspectiva) iterPlan.next();
										List<Meta> metasX = new ArrayList<Meta>();
										if (tipoImportacion == 6)
											metasX = new MetaManager(pm, log, messageResources).getMetasAnuales(
													indicador.getIndicadorId(), perspectiva.getPlanId(),
													Integer.parseInt(anoArchivo), Integer.parseInt(anoArchivo), stm);
										else if (tipoImportacion == 5)
											metasX = new MetaManager(pm, log, messageResources).getMetasParciales(
													indicador.getIndicadorId(), perspectiva.getPlanId(),
													indicador.getFrecuencia(),
													indicador.getOrganizacion().getMesCierre(), indicador.getCorte(),
													indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(),
													Integer.parseInt(anoArchivo), Integer.parseInt(anoArchivo),
													Integer.parseInt(periodoArchivo), Integer.parseInt(periodoArchivo),
													stm);

										if (metasX.size() == 0) {
											meta = new Meta();
											MetaPK metaPk = new MetaPK();
											meta.setMetaId(metaPk);
										} else
											meta = metasX.get(0);

										MetaPK metaPk = meta.getMetaId();
										metaPk.setIndicadorId(indicador.getIndicadorId());
										metaPk.setPlanId(perspectiva.getPlanId());
										metaPk.setAno(Integer.parseInt(anoArchivo));
										metaPk.setSerieId(SerieTiempo.getSerieMetaId());
										if (tipoImportacion == 6) {
											metaPk.setTipo(TipoMeta.getTipoMetaAnual());
											metaPk.setPeriodo(new Integer(0));
										} else if (tipoImportacion == 5) {
											metaPk.setTipo(TipoMeta.getTipoMetaParcial());
											metaPk.setPeriodo(Integer.parseInt(periodoArchivo));
										}
										meta.setProtegido(new Boolean(false));
										meta.setValor(Double.parseDouble(medicionArchivo));
										metas.add(meta);

										argsReemplazo[0] = indicador.getNombre();
										argsReemplazo[1] = anoArchivo;
										if (tipoImportacion == 5)
											argsReemplazo[2] = periodoArchivo;
										else
											argsReemplazo[2] = "";
										argsReemplazo[3] = perspectiva.getPlanId().toString();
										argsReemplazo[4] = codigoArchivo;
										if (tipoImportacion == 5 && logMediciones)
											log.append(messageResources.getResource(
													"jsp.asistente.importacion.log.indicador.plan.parcial.success",
													argsReemplazo) + "\n");
										else if (tipoImportacion == 6 && logMediciones)
											log.append(messageResources.getResource(
													"jsp.asistente.importacion.log.indicador.plan.anual.success",
													argsReemplazo) + "\n");
									}
								}
								break;
							}
						}

						if (!hayIndicador && logErrores) {
							argsReemplazo[0] = codigoArchivo;
							argsReemplazo[1] = "";
							argsReemplazo[2] = "";
							argsReemplazo[3] = "";
							argsReemplazo[4] = "";
							log.append(messageResources.getResource(
									"jsp.asistente.importacion.log.error.nohaycodigoenlace", argsReemplazo) + "\n");
						}
					}
				}
			} else if (logErrores)
				log.append(messageResources.getResource("jsp.asistente.importacion.log.nohayindicadores") + "\n");

			int totalMediciones = 0;
			if (tipoMedicion == 0 && mediciones.size() > 0)
				totalMediciones = mediciones.size();
			else if (tipoMedicion == 1 && metas.size() > 0)
				totalMediciones = metas.size();

			if (totalMediciones > 0) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
				this.servicio.setMensaje("Salvando mediciones, total de mediciones a salvar: " + totalMediciones);
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
			}

			int res = 10000;
			if (tipoMedicion == 0 && mediciones.size() > 0)
				res = new MedicionManager(pm, log, messageResources).saveMediciones(null, mediciones, 0L,
						new Boolean(true), stm);
			else if (tipoMedicion == 1 && metas.size() > 0)
				res = new MetaManager(pm, log, messageResources).saveMetas(metas, stm);

			if (res == 10000 && Boolean.parseBoolean(pm.getProperty("calcular", "false"))
					&& indicadoresCalcular.size() > 0) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
				this.servicio.setMensaje(
						"Actulizando indicadores, total de indicadores a actualizar: " + indicadoresCalcular.size());
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				res = new MedicionManager(pm, log, messageResources)
						.actualizarUltimaMedicionIndicadores(indicadoresCalcular, 0L, stm);
			}

			if (res == 10000) {
				cn.commit();
				cn.setAutoCommit(true);
			} else if (res != 10000) {
				cn.rollback();
				cn.setAutoCommit(true);
			}
			stm.close();
			cn.close();
			cn = null;
			transActiva = false;

			boolean calcular = Boolean.parseBoolean(pm.getProperty("calcular", "false"));
			if (calcular && res == 10000 && indicadoresCalcular.size() > 0) {
				boolean tomarPeriodosNulosComoCero = Boolean
						.parseBoolean(pm.getProperty("tomarPeriodosSinMedicionConValorCero", "false"));

				Integer mesInicial = 1;
				Integer mesFinal = 12;
				if (!hayDiferentesFrecuencias && anoInicial.intValue() == anoFinal.intValue()
						&& frecuencia.byteValue() != Frecuencia.getFrecuenciaSemanal().byteValue()
						&& frecuencia.byteValue() != Frecuencia.getFrecuenciaQuincenal().byteValue()) {
					Calendar fechaInicial = PeriodoUtil.getDateByPeriodo(frecuencia, anoInicial, periodoInicial, false);
					Calendar fechaFinal = PeriodoUtil.getDateByPeriodo(frecuencia, anoInicial, periodoFinal, false);
					mesInicial = fechaInicial.get(2) + 1;
					mesFinal = fechaFinal.get(2) + 1;
				}

				argsReemplazo[0] = anoInicial + "";
				argsReemplazo[1] = anoFinal + "";
				argsReemplazo[2] = "";
				argsReemplazo[3] = "";
				argsReemplazo[4] = "";

				this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
				this.servicio
						.setMensaje(messageResources.getResource("calcularindicadores.calculando.anos", argsReemplazo));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				for (int f = anoInicial; f < (anoFinal + 1); f++) {
					argsReemplazo[0] = f + "";
					argsReemplazo[1] = mesInicial + "";
					argsReemplazo[2] = mesFinal + "";
					argsReemplazo[3] = "";
					argsReemplazo[4] = "";

					log.append(messageResources.getResource("calcularindicadores.calculando", argsReemplazo) + "\n");

					// Calcular todos los indicadores involucrados en la importacion
					StringBuffer logCalculo = new CalculoManager(pm, log, messageResources)
							.calcularMedicionesIndicadores(indicadoresCalcular, null, null, null, f,
									mesInicial.byteValue(), mesFinal.byteValue(), tomarPeriodosNulosComoCero,
									logMediciones, logErrores, null, null, periodoInicial, periodoFinal);
					log.append(logCalculo.toString());
					res = 10000;
				}

				cn = new ConnectionManager(pm).getConnection();
				cn.setAutoCommit(false);
				stm = cn.createStatement();
				transActiva = true;

				// Calcular todos los indicador formulas
				filtros = new HashMap<String, String>();
				filtros.put("soloCompuestos", "true");
				if (!Boolean.parseBoolean(pm.getProperty("todosOrganizacion", "false"))
						&& !hayDiferentesOrganizaciones) {
					argsReemplazo[0] = organizacionId.toString();
					argsReemplazo[1] = "";
					argsReemplazo[2] = "";
					argsReemplazo[3] = "";
					argsReemplazo[4] = "";

					log.append(
							this.messageResources.getResource("calcularindicadores.filtroorganizacion", argsReemplazo)
									+ "\n\n");
					filtros.put("organizacion_Id", organizacionId.toString());
				}
				// filtros.put("indicador_Id", "85190"); //Indicador Formula
				indicadores = new IndicadorManager(pm, log, messageResources).getIndicadores(filtros, stm);

				cn.commit();
				cn.setAutoCommit(true);
				stm.close();
				cn.close();
				cn = null;
				transActiva = false;

				Calendar ahora = Calendar.getInstance();
				argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
				argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
				argsReemplazo[2] = "";
				argsReemplazo[3] = "";
				argsReemplazo[4] = "";

				log.append(this.messageResources.getResource("calcularindicadores.fechainiciocalculocompuesto",
						argsReemplazo) + "\n\n");

				if (indicadores.size() > 0) {
					argsReemplazo[0] = anoInicial + "";
					argsReemplazo[1] = anoFinal + "";
					argsReemplazo[2] = "";
					argsReemplazo[3] = "";
					argsReemplazo[4] = "";

					this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
					this.servicio.setMensaje(
							messageResources.getResource("calcularindicadores.calculando.anos.formula", argsReemplazo));
					this.servicio.setLog(log.toString());
					new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

					for (int f = anoInicial; f < (anoFinal + 1); f++) {
						argsReemplazo[0] = f + "";
						argsReemplazo[1] = mesInicial + "";
						argsReemplazo[2] = mesFinal + "";
						argsReemplazo[3] = "";
						argsReemplazo[4] = "";

						log.append(
								messageResources.getResource("calcularindicadores.calculando", argsReemplazo) + "\n");

						StringBuffer logCalculo = new CalculoManager(pm, log, messageResources)
								.calcularMedicionesIndicadores(indicadores, null, null, null, f, mesInicial.byteValue(),
										mesFinal.byteValue(), tomarPeriodosNulosComoCero, logMediciones, logErrores,
										null, null, periodoInicial, periodoFinal);
						log.append(logCalculo.toString());
						res = 10000;
					}
				}

				argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
				argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
				argsReemplazo[2] = "";
				argsReemplazo[3] = "";
				argsReemplazo[4] = "";

				log.append(
						this.messageResources.getResource("calcularindicadores.fechafincalculocompuesto", argsReemplazo)
								+ "\n\n");
				log.append(messageResources.getResource("importarindicadores.success") + "\n");
			}

			if (tipoMedicion == 0 && res != 10000)
				log.append(messageResources.getResource("action.guardarmediciones.mensaje.guardarmediciones.related")
						+ "\n");
			else if (tipoMedicion == 1 && res != 10000)
				log.append(
						messageResources.getResource("action.guardarmetas.mensaje.guardarmetas.relacionados") + "\n");

			Message message = new Message(this.servicio.getUsuarioId(), this.servicio.getFecha(),
					MessageStatus.getStatusPendiente(), "", MessageType.getTypeAlerta(), this.servicio.getNombre());
			if (res == 10000) {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusSuccess());
				this.servicio.setMensaje(messageResources.getResource("importarindicadores.success"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("importarindicadores.success"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);

				respuesta = true;
			} else {
				this.servicio.setEstatus(ServicioStatus.getServicioStatusNoSuccess());
				this.servicio.setMensaje(messageResources.getResource("calcularindicadores.error"));
				this.servicio.setLog(log.toString());
				new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);

				message.setMensaje(messageResources.getResource("calcularindicadores.error"));
				new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, null);
			}
		} catch (Exception e) {
			argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
			argsReemplazo[1] = "";
			argsReemplazo[2] = "";
			argsReemplazo[3] = "";
			argsReemplazo[4] = "";

			log.append(
					messageResources.getResource("jsp.asistente.importacion.log.medicion.general.error", argsReemplazo)
							+ "\n\n");
		} finally {
			try {
				if (transActiva)
					stm.close();
			} catch (Exception localException8) {
			}

			try {
				if (transActiva) {
					cn.setAutoCommit(true);
					cn.close();
					cn = null;
				}
			} catch (Exception localException9) {
			}
		}

		return respuesta;
	}
}

class Tarea implements Runnable {
	ScheduledExecutorService timer;
	int counter;
	int terminar;
	long duracion;
	boolean infinito = false;
	StringBuffer log;
	TimeUnit timeUnit;
	VgcMessageResources messageResources;
	PropertyCalcularManager pm;
	String[][] datos;
	Servicio servicio;
	Usuario usuario;

	public void programarIniciativa(long duracion, int terminar, boolean infinito, TimeUnit timeUnit, StringBuffer log,
			VgcMessageResources messageResources, PropertyCalcularManager pm, String[][] datos, Servicio servicio,
			Usuario usuario) {
		this.terminar = terminar;
		this.infinito = infinito;
		this.log = log;
		this.timeUnit = timeUnit;
		this.messageResources = messageResources;
		this.pm = pm;
		this.datos = datos;
		this.servicio = servicio;
		this.usuario = usuario;

		timer = Executors.newSingleThreadScheduledExecutor();

		// Ejecutar dentro de 2 milisegundos, repetir cada 24 Horass
		if (timeUnit == TimeUnit.DAYS)
			timer.scheduleAtFixedRate(this, 1, 24, TimeUnit.HOURS);
		else if (timeUnit == TimeUnit.HOURS)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MINUTES);
		else if (timeUnit == TimeUnit.MINUTES)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.SECONDS);
		else
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MILLISECONDS);

		this.duracion = duracion;
	}

	public void programarActividad(long duracion, int terminar, boolean infinito, TimeUnit timeUnit, StringBuffer log,
			VgcMessageResources messageResources, PropertyCalcularManager pm, String[][] datos, Servicio servicio,
			Usuario usuario) {
		this.terminar = terminar;
		this.infinito = infinito;
		this.log = log;
		this.timeUnit = timeUnit;
		this.messageResources = messageResources;
		this.pm = pm;
		this.datos = datos;
		this.servicio = servicio;
		this.usuario = usuario;

		timer = Executors.newSingleThreadScheduledExecutor();

		// Ejecutar dentro de 2 milisegundos, repetir cada 24 Horass
		if (timeUnit == TimeUnit.DAYS)
			timer.scheduleAtFixedRate(this, 1, 24, TimeUnit.HOURS);
		else if (timeUnit == TimeUnit.HOURS)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MINUTES);
		else if (timeUnit == TimeUnit.MINUTES)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.SECONDS);
		else
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MILLISECONDS);

		this.duracion = duracion;
	}

	public void programar(long duracion, int terminar, boolean infinito, TimeUnit timeUnit, StringBuffer log,
			VgcMessageResources messageResources, PropertyCalcularManager pm, String[][] datos, Servicio servicio) {
		this.terminar = terminar;
		this.infinito = infinito;
		this.log = log;
		this.timeUnit = timeUnit;
		this.messageResources = messageResources;
		this.pm = pm;
		this.datos = datos;
		this.servicio = servicio;

		timer = Executors.newSingleThreadScheduledExecutor();

		// Ejecutar dentro de 2 milisegundos, repetir cada 24 Horass
		if (timeUnit == TimeUnit.DAYS)
			timer.scheduleAtFixedRate(this, 1, 24, TimeUnit.HOURS);
		else if (timeUnit == TimeUnit.HOURS)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MINUTES);
		else if (timeUnit == TimeUnit.MINUTES)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.SECONDS);
		else
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MILLISECONDS);

		this.duracion = duracion;
	}

	public void run() {
		counter++;
		int dormir = 1;
		boolean respuesta = false;
		byte tipoImportacion = Byte.parseByte(pm.getProperty("tipoImportacion", "1"));
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String[] argsReemplazo = new String[4];

		Calendar nowNext = Calendar.getInstance();
		nowNext.setTimeInMillis(duracion);
		if (timeUnit == TimeUnit.DAYS)
			duracion = 1000 * 60 * 60 * 24 + (nowNext.getTimeInMillis());
		else if (timeUnit == TimeUnit.HOURS)
			duracion = 1000 * 60 * 60 + (nowNext.getTimeInMillis());
		else if (timeUnit == TimeUnit.MINUTES) {
			duracion = 1000 * 60 + (nowNext.getTimeInMillis());
			dormir = 4;
		} else {
			duracion = 1000 + (nowNext.getTimeInMillis());
			dormir = 4;
		}

		if (tipoImportacion == 7)
			respuesta = new ImportarManager(this.pm, this.log, this.messageResources, this.servicio)
					.ImportarIniciativa(this.datos, usuario);
		else if (tipoImportacion == 8) {
			System.out.print(
					"\nllega a el programar, previo a importar actividad, tipo Importacion : " + tipoImportacion);
			respuesta = new ImportarManager(this.pm, this.log, this.messageResources, this.servicio)
					.ImportarActividad(this.datos, usuario);
		} else
			respuesta = new ImportarManager(this.pm, this.log, this.messageResources, this.servicio)
					.Importar(this.datos);

		if (respuesta)
			log.append("\n\nEjecuci�n [Thread " + Thread.currentThread().getName() + "] " + counter + " Ejecuci�n="
					+ sdf.format(now.getTime()) + " proxima Ejecuci�n=" + sdf.format(nowNext.getTime()) + "\n");
		else
			this.detener();

		// Poner a dormir 4 segundos, como si fuera una tarea demasiado larga
		try {
			Thread.sleep(dormir * 1000);
		} catch (InterruptedException e) {
			argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
			argsReemplazo[1] = "";
			argsReemplazo[2] = "";
			argsReemplazo[3] = "";

			log.append(
					messageResources.getResource("jsp.asistente.importacion.log.errordormir", argsReemplazo) + "\n\n");
		}

		if (counter == terminar && !infinito) {
			this.detener();
		}
	}

	void detener() {
		timer.shutdown();

		String[] argsReemplazo = new String[2];
		Calendar ahora = Calendar.getInstance();

		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

		log.append("\n\n");
		log.append(
				messageResources.getResource("jsp.asistente.importacion.log.fechafincalculo", argsReemplazo) + "\n\n");

		this.servicio.setLog(this.log.toString());

		new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
	}
}

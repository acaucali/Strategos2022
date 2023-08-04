DELETE FROM afw_objeto_auditable_atributo;
DELETE FROM afw_objeto_auditable;
DELETE FROM afw_auditoria_entero;
DELETE FROM afw_auditoria_string;
DELETE FROM afw_auditoria_memo;
DELETE FROM afw_auditoria_flotante;
DELETE FROM afw_auditoria_fecha;

-- Grupo
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (71, 'com.visiongc.framework.model.Grupo', 'grupoId', 'grupo', 1);

-- Configuracion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (70, 'com.visiongc.framework.model.Configuracion', 'parametro', 'parametro', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (70, 'parametro', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (70, 'valor', 2); 

-- User Session (Colocar nombre de usuario)
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (69, 'com.visiongc.framework.model.UserSession', 'sessionId', 'UsuarioLogin', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'usuarioLogin', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteAddress', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteHost', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteUser', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'url', 1); 

-- Usuario
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (68, 'com.visiongc.framework.model.Usuario', 'usuarioId', 'fullName', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'UId', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'fullName', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'isAdmin', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'estatus', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'bloqueado', 4); 

-- UnidadMedida
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (67, 'com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida', 'unidadId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (67, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (67, 'tipo', 4); 

-- SerieTiempo
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (66, 'com.visiongc.app.strategos.seriestiempo.model.SerieTiempo', 'serieId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'fija', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'oculta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'identificador', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'preseleccionada', 4); 

-- Responsable
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (65, 'com.visiongc.app.strategos.responsables.model.Responsable', 'responsableId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'usuarioId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'cargo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'email', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'ubicacion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'notas', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'esGrupo', 4); 

-- GrupoResponsable
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (64, 'com.visiongc.app.strategos.responsables.model.GrupoResponsable', 'pk', 'pk', 1);

-- Reporte
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (63, 'com.visiongc.app.strategos.reportes.model.Reporte', 'reporteId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (63, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (63, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (63, 'configuracion', 1); 

-- Seguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (62, 'com.visiongc.app.strategos.problemas.model.Seguimiento', 'seguimientoId', 'fechaEmision', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaEmision', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'estadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'accionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaRecepcion', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaAprobacion', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'preparadoPor', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'aprobadoPor', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'nota', 1); 

-- ResponsableAccion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (61, 'com.visiongc.app.strategos.problemas.model.ResponsableAccion', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (61, 'tipo', 4); 

-- Problema
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (60, 'com.visiongc.app.strategos.problemas.model.Problema', 'problemaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaEstimadaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaRealInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaEstimadaFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaRealFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'indicadorEfectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'iniciativaEfectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'auxiliarId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'estadoId', 4); 
  
-- MemoSeguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (59, 'com.visiongc.app.strategos.problemas.model.MemoSeguimiento', ' pk', 'memo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (59, 'memo', 1); 

-- MemoProblema
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (58, 'com.visiongc.app.strategos.problemas.model.MemoProblema', 'pk', 'memo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (58, 'memo', 1); 
 
-- ConfiguracionMensajeEmailSeguimientos
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (57, 'com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos', 'mensajeId', 'mensaje', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'mensaje', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'acuseRecibo', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'soloAuxiliar', 4);
 
-- ClaseProblemas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (56, 'com.visiongc.app.strategos.problemas.model.ClaseProblemas', 'claseId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'padreId', 4); 

-- Accion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (55, 'com.visiongc.app.strategos.problemas.model.Accion', 'accionId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'estadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'problemaId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaEstimadaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaRealInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaEstimadaFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaRealFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'frecuencia', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'padreId', 4); 

-- Portafolio
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (50, 'com.visiongc.app.strategos.portafolios.model.Portafolio', 'portafolioId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'estatus', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'situacion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'estatusQ', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'desviacionParcial', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'desviacionAcumulada', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'desviacionPresupuestaria', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'porcentajeAmarillo', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'porcentajeVerde', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'parcial', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'acumulado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'presupuestoReal', 5);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'presupuestoEjecutado', 5);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'frecuencia', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'visible', 4);

-- PryVista
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (49, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryVista', 'vistaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (49, 'nombre', 1); 

-- PryProyecto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (48, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto', 'proyectoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzo', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzoPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzoReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'finPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'finReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'duracionPlan', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'duracionReal', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionComienzo', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionFin', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionDuracion', 5); 
  
-- PryInformacionActividad
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (47, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad', 'actividadId', 'productoEsperado', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'productoEsperado', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'recursosHumanos', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'recursosMateriales', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'peso', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'porcentajeAmarillo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'porcentajeVerde', 4); 
 
-- PryColumnaVista
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (46, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryColumnaVista', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'alineacion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'ancho', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'orden', 4); 

-- PryColumna
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (45, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryColumna', 'columnaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'numerico', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'alineacion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'formato', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'tamano', 4); 

-- PryCalendarioDetalle
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (44, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendarioDetalle', 'calendarioId', 'fecha', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (44, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (44, 'feriado', 4); 

-- PryCalendario
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (43, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario', 'calendarioId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'proyectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'domingo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'lunes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'martes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'miercoles', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'jueves', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'viernes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'sabado', 4); 
 
-- PryActividad
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (42, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad', 'actividadId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'proyectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'unidadId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'comienzoPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'comienzoReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'finPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'finReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'duracionPlan', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'duracionReal', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'fila', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'nivel', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'compuesta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'indicadorId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'naturaleza', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableFijarMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableLograrMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableSeguimientoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableCargarMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableCargarEjecutadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'tipoMedicion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'tieneHijos', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'peso', 5); 

-- PrdSeguimientoProducto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (41, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'fechaInicio', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'fechaFin', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'alerta', 4); 

-- PrdSeguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (40, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'seguimiento', 1); 

-- PrdProducto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (39, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto', 'productoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'iniciativaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'fechaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'fechaFin', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'alerta', 4); 

-- PryActividadPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (38, 'com.visiongc.app.strategos.planes.model.PryActividadPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeEjecutado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeEsperado', 5); 

-- PlantillaPlanes
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (37, 'com.visiongc.app.strategos.planes.model.PlantillaPlanes', 'plantillaPlanesId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreIndicadorSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreIniciativaSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreActividadSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'tipo', 4); 

-- PlanEstado
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (36, 'com.visiongc.app.strategos.planes.model.PlanEstado', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'estado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'totalConValor', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'total', 4); 
  
-- Plan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (35, 'com.visiongc.app.strategos.planes.model.Plan', 'planId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'planImpactaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'anoInicial', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'anoFinal', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'activo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'metodologiaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'esquema', 1); 
  
-- PerspectivaEstado
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (34, 'com.visiongc.app.strategos.planes.model.PerspectivaEstado', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'estado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'totalConValor', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'total', 4); 

-- Perspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (33, 'com.visiongc.app.strategos.planes.model.Perspectiva', 'perspectivaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'planId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nombreObjetoPerspectiva', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'frecuencia', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'titulo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nivel', 4); 
  
--Meta
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (30, 'com.visiongc.app.strategos.planes.model.Meta', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (30, 'valor', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (30, 'protegido', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (30, 'tipoCargaMeta', 4); 

--IniciativaPerspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (29, 'com.visiongc.app.strategos.planes.model.IniciativaPerspectiva', 'pk', 'pk', 1);

--IniciativaPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (28, 'com.visiongc.app.strategos.planes.model.IniciativaPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'ano', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'periodo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'claseId', 4); 

--IndicadorPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (27, 'com.visiongc.app.strategos.planes.model.IndicadorPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (27, 'peso', 5); 

--IndicadorPerspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (26, 'com.visiongc.app.strategos.planes.model.IndicadorPerspectiva', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (26, 'peso', 5); 

--IndicadorCrecimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (25, 'com.visiongc.app.strategos.planes.model.IndicadorCrecimiento', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (25, 'crecimiento', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (25, 'tipoMedicion', 4); 

--ElementoPlantillaPlanes
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (24, 'com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes', 'elementoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'plantillaPlanesId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'tipo', 4); 

--GrupoMascaraCodigoPlanCuentas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (23, 'com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas', 'pk', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'mascara', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'madre', 1); --****

--MascaraCodigoPlanCuentas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (22, 'com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas', 'mascaraId', 'mascara', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (22, 'mascara', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (22, 'niveles', 4); 

--Cuenta
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (21, 'com.visiongc.app.strategos.plancuentas.model.Cuenta', 'cuentaId', 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'codigo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'hijosCargados', 4); 

--MemoOrganizacion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (20, 'com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion', 'pk', 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (20, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (20, 'organizacion', 1); 

--OrganizacionStrategos
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (19, 'com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos', 'organizacionId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'direccion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'rif', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'telefono', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'fax', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'mesCierre', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'enlaceParcial', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'visible', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'soloLectura', 4); 

--ResultadoEspecificoIniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (18, 'com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa', 'pk', 'resultado', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (18, 'resultado', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (18, 'iniciativa', 1); --****

--MemoIniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (17, 'com.visiongc.app.strategos.iniciativas.model.MemoIniciativa', 'iniciativaId', 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (17, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (17, 'resultado', 1);

--Iniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (16, 'com.visiongc.app.strategos.iniciativas.model.Iniciativa', 'iniciativaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'proyectoId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'tipoAlerta', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'frecuencia', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'nombreLargo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'enteEjecutor', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'visible', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'indicadorId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'claseId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'porcentajeCompletado', 5);

--SerieIndicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (15, 'com.visiongc.app.strategos.indicadores.model.SerieIndicador', 'pk', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'naturaleza', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'fechaBloqueo', 3);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'indicador', 1); --****

--Medicion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (14, 'com.visiongc.app.strategos.indicadores.model.Medicion', 'medicionId', 'valor', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'valor', 5);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'valorString', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'protegido', 4);
--INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'serieIndicador', 4);

--Indicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (13, 'com.visiongc.app.strategos.indicadores.model.Indicador', 'indicadorId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'claseId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'nombreCorto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'naturaleza', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'frecuencia', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'comportamiento', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'unidadId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'codigoEnlace', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'prioridad', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'fuente', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'orden', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'soloLectura', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'estaBloqueado', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'peso', 5);

--InsumoFormula
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (12, 'com.visiongc.app.strategos.indicadores.model.InsumoFormula', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (12, 'macro', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (12, 'valor', 1);

--Formula
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (11, 'com.visiongc.app.strategos.indicadores.model.Formula', 'pk', 'expresion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (11, 'expresion', 1);

--ClaseIndicadores
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (10, 'com.visiongc.app.strategos.indicadores.model.ClaseIndicadores', 'claseId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'enlaceParcial', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'tipo', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'visible', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'hijosCargados', 4);

--CategoriaIndicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (9, 'com.visiongc.app.strategos.indicadores.model.CategoriaIndicador', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (9, 'orden', 4);

--Grafico
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (8, 'com.visiongc.app.strategos.graficos.model.Grafico', 'graficoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'configuracion', 2);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'objetoId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'className', 1);

--Foro
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (7, 'com.visiongc.app.strategos.foros.model.Foro', 'foroId', 'asunto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'asunto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'email', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'comentario', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'numeroRespuestas', 4);

--AdjuntoExplicacion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (6, 'com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion', 'pk', 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (6, 'titulo', 1);

--Explicacion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (5, 'com.visiongc.app.strategos.explicaciones.model.Explicacion', 'explicacionId', 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'fecha', 3);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'numeroAdjuntos', 4);

--EstadoAcciones
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (4, 'com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones', 'estadoId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'aplicaSeguimiento', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'orden', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'condicion', 4);

-- Causa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (3, 'com.visiongc.app.strategos.causas.model.Causa', 'causaId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'nivel', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'hijosCargados', 4);

-- CategoriaMedicion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (2, 'com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion', 'categoriaId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (2, 'nombre', 1);

-- ClaseProblemas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (1, 'com.visiongc.app.strategos.problemas.model.ClaseProblemas', 'claseId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'padreId', 4);

COMMIT;

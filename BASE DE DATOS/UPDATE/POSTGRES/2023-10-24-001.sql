alter table instrumentos 
	add column is_historico numeric(1,0);
    

INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DETALLADO',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Detallado',  4,   1,   0,  'Reporte Detallado');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_RESUMIDO_VIGENTES',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Resumido Vigentes',  4,   1,   0,  'Reporte Resumido Vigentes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DATOS_BASICOS',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Datos Basicos',  4,   1,   0,  'Reporte Datos Basicos');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_MEDICIONES_ATRASADAS',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Mediciones Atrasadas',  4,   1,   0,  'Reporte Mediciones Atrasadas');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DETALLADO_PLANES_ACCION',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Detallado Detallado Planes de Accion',  4,   1,   0,  'Reporte Detallado Planes de Accion');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_INDICADORES',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Indicadores',  4,   1,   0,  'Reporte Indicadores');
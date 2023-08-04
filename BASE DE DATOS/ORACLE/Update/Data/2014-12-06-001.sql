INSERT INTO afw_permiso ( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR',  'INICIATIVA',  'Evaluación de Iniciativas',  2,   12,   0,  'Evaluación de Iniciativas');
INSERT INTO afw_permiso ( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO',  'INICIATIVA_EVALUAR',  'Graficar',  3,   1,   0,  'Graficar');
INSERT INTO afw_permiso ( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO_ESTATUS',  'INICIATIVA_EVALUAR_GRAFICO',  'Iniciativa por Estatus',  4,   1,   0,  'Iniciativa por Estatus');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO_PORCENTAJE',  'INICIATIVA_EVALUAR_GRAFICO',  'Iniciativa por Porcentaje',  4,   2,   0,  'Iniciativa por Porcentaje');
INSERT INTO afw_permiso ( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE',  'INICIATIVA_EVALUAR',  'Reportes',  3,   2,   0,  'Reportes');
INSERT INTO afw_permiso (permiso_id, padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_RESUMIDO',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Resumido',  4,   1,   0,  'Reporte Resumido');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_ACTIVO', 'Activar/Desactivar Plan', 'PLAN', 2, 11, 0, 'Activar/Desactivar Plan');

UPDATE indicador_por_plan 
SET tipo_medicion = 0
WHERE indicador_id IN (SELECT indicador_id FROM Indicador WHERE tipo_medicion = 0)
AND tipo_medicion IS NULL;

UPDATE indicador_por_plan 
SET tipo_medicion = 1
WHERE indicador_id IN (SELECT indicador_id FROM Indicador WHERE tipo_medicion = 1)
AND tipo_medicion IS NULL;

UPDATE indicador_por_plan 
SET tipo_medicion = 0
WHERE tipo_medicion IS NULL;

COMMIT;

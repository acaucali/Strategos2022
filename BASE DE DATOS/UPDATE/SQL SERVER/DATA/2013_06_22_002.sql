INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('MEDICION_ADD',  'MEDICION',  'Cargar o Modificar',  5,   1,   0,  'Cargar o Modificar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('MEDICION_IMPORTAR',  'MEDICION',  'Importar',  5,   2,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('MEDICION_CALCULAR',  'MEDICION',  'Calcular',  5,   3,   0,  'Calcular');
UPDATE afw_permiso SET nivel = 4 WHERE permiso_id = 'MEDICION_PROTEGER';
UPDATE afw_permiso SET nivel = 5 WHERE permiso_id = 'MEDICION_DESPROTEGER';
DELETE FROM afw_permiso WHERE permiso_id = 'INDICADOR_CALCULAR';

INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR',  'INDICADOR',  'Evaluación de Indicadores',  4,   5,   0,  'Evaluación de Indicadores');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO',  'INDICADOR_EVALUAR',  'Graficar',  5,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_GRAFICO',  'INDICADOR_EVALUAR_GRAFICO',  'Graficar',  6,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_PLANTILLA',  'INDICADOR_EVALUAR_GRAFICO',  'Plantilla',  6,   2,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_ASISTENTE',  'INDICADOR_EVALUAR_GRAFICO',  'Asistente',  6,   3,   0,  'Asistente');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE',  'INDICADOR_EVALUAR',  'Reportes',  5,   2,   0,  'Reportes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_PLANTILLA',  'INDICADOR_EVALUAR_REPORTE',  'Plantilla',  6,   1,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_ASISTENTE',  'INDICADOR_EVALUAR_REPORTE',  'Asistente',  6,   2,   0,  'Asistente');
--INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_COMITE',  'INDICADOR_EVALUAR_REPORTE',  'Reporte Comité Ejecutivo',  6,   3,   0,  'Reporte Comité Ejecutivo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_ARBOL',  'INDICADOR_EVALUAR',  'Arbol de Dupont',  5,   3,   0,  'Arbol de Dupont');

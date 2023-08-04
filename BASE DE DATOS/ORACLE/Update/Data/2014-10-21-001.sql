INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION',  'INDICADOR',  'Transacciones',  3,   14,   0,  'Transacciones');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION_IMPORTAR',  'INDICADOR_MEDICION_TRANSACCION',  'Importar',  4,   1,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION_REPORTE',  'INDICADOR_MEDICION_TRANSACCION',  'Reporte',  4,   2,   0,  'Reporte');

COMMIT;

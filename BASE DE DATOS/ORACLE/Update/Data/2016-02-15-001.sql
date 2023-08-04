INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR', 'Calcular', 'ORGANIZACION', 1, 10, 1, 'Calcular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR_INDICADOR', 'Indicadores', 'ORGANIZACION_CALCULAR', 2, 1, 1, 'Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR_INICIATIVA', 'Iniciativas', 'ORGANIZACION_CALCULAR', 2, 2, 1, 'Iniciativas');

UPDATE afw_sistema set actual = '8.01-160215';

COMMIT;

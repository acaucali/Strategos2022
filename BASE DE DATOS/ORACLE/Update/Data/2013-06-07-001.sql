INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('SERVICIO_LOG', 'SERVICIO', 'Ejecutar Reporte Log', 3, 2, 1, 'Ejecutar Reporte Log');
DELETE FROM afw_objeto_auditable WHERE objeto_id = '30';
DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = '30';

COMMIT;
--Configuracion
UPDATE AFW_PERMISO SET grupo = 10 WHERE permiso_id = 'ORGANIZACION';
UPDATE AFW_PERMISO SET grupo = 11 WHERE permiso_id = 'CONFIGURACION';
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIG', 'Configuraci�n del Sistema', NULL, 0, 9, 1, 'Configuraci�n del Sistema');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIGURACION_SISTEMA', 'Gestionar Configuraci�n del Sistema', 'CONFIG', 1, 1, 0, 'Gestionar Configuraci�n del Sistema');

COMMIT;

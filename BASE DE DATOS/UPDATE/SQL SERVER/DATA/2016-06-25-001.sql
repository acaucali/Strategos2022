--Estatus de Iniciativa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS', 'Gestionar Estatus de Iniciativa', NULL, 0, 12, 1, 'Gestionar Estatus de Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_ADD', 'Crear', 'INICIATIVA_ESTATUS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_EDIT', 'Modificar', 'INICIATIVA_ESTATUS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_DELETE', 'Eliminar', 'INICIATIVA_ESTATUS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_VIEW', 'Ver', 'INICIATIVA_ESTATUS', 1, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_PRINT', 'Imprimir', 'INICIATIVA_ESTATUS', 1, 5, 1, 'Imprimir');

UPDATE afw_sistema set actual = '8.01-160625';

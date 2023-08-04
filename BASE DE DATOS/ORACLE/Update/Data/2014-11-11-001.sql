INSERT INTO AFW_PERMISO ( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('INDICADOR_EMAIL',  'INDICADOR',  'Enviar Correo',  3,   15,   0,  'Enviar Correo');
INSERT INTO AFW_PERMISO ( permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_EMAIL', 'Enviar Correo', 'PLAN_PERSPECTIVA', 3, 7, 0, 'Enviar Correo');
INSERT INTO AFW_PERMISO ( permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EMAIL', 'Enviar Correo', 'INICIATIVA', 2, 10, 0, 'Enviar Correo');
INSERT INTO AFW_PERMISO ( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_EMAIL',  'ACTIVIDAD',  'Enviar Correo',  2,   12,   0,  'Enviar Correo');

UPDATE indicador SET tipo = 1 WHERE indicador_id IN (SELECT indicador_id FROM pry_actividad);
UPDATE indicador SET tipo = 1 WHERE indicador_id IN (SELECT indicador_id FROM iniciativa);

COMMIT;

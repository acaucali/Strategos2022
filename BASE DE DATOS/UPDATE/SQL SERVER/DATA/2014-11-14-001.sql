INSERT INTO AFW_PERMISO ( permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_RELACION', 'Iniciativas Relacionadas', 'INICIATIVA', 2, 11, 0, 'Iniciativas Relacionadas');
UPDATE pry_actividad SET objeto_asociado_classname = 'Iniciativa' WHERE objeto_asociado_classname = 'Inicitiva';
UPDATE indicador SET corte = 0 WHERE indicador_id IN (SELECT indicador_id FROM iniciativa) AND corte <> 0;
UPDATE indicador SET corte = 0 WHERE indicador_id IN (SELECT indicador_id FROM pry_actividad) AND corte <> 0;

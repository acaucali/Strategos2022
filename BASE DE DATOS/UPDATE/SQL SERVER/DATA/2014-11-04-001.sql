UPDATE indicador SET constante = 1 WHERE indicador_id IN (SELECT indicador_id FROM iniciativa);
UPDATE indicador SET constante = 1 WHERE indicador_id IN (SELECT indicador_id FROM pry_actividad);

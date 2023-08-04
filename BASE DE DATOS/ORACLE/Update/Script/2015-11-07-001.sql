UPDATE indicador SET corte = 0 WHERE indicador_id in (SELECT indicador_id FROM pry_actividad);

COMMIT;

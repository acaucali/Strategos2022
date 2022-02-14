UPDATE pry_actividad SET tipo_medicion = 0;

UPDATE Indicador 
SET tipo_medicion = 0
WHERE indicador_id IN (SELECT indicador_id
			FROM Pry_Actividad
			WHERE tipo_medicion = 0);

UPDATE Indicador 
SET tipo_medicion = 1
WHERE indicador_id IN (SELECT indicador_id
			FROM Pry_Actividad
			WHERE tipo_medicion = 1);

UPDATE Indicador 
SET tipo_medicion = 0
WHERE indicador_id IN (SELECT indicador_id
						FROM iniciativa);

UPDATE indicador SET tipo_medicion = 0
WHERE indicador_id IN (SELECT indicador_id 
						FROM pry_actividad 
						WHERE actividad_id IN (SELECT padre_id 
												FROM pry_actividad 
												WHERE padre_id IS NOT NULL GROUP BY padre_id));

UPDATE pry_actividad SET tipo_medicion = 0
WHERE actividad_id IN (SELECT padre_id 
						FROM pry_actividad 
						WHERE padre_id IS NOT NULL GROUP BY padre_id);
						
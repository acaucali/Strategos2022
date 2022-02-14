UPDATE Organizacion SET read_only = 0;

UPDATE pry_actividad SET naturaleza = 0 WHERE naturaleza = 7 OR naturaleza = 8 OR naturaleza IS NULL;
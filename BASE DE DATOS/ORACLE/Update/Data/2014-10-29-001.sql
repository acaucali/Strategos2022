update indicador set unidad_id = (SELECT Unidad_ID FROM Unidad WHERE nombre = '%') where indicador_id in (select indicador_id from iniciativa);
update indicador set unidad_id = (SELECT Unidad_ID FROM Unidad WHERE nombre = '%') where indicador_id in (select indicador_id from pry_actividad);

COMMIT;

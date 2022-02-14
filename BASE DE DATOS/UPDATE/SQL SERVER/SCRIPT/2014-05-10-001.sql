ALTER TABLE indicador ADD COLUMN asignar_Inventario numeric(1,0);
UPDATE Indicador set asignar_Inventario = 0 WHERE asignar_Inventario IS NULL;


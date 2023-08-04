ALTER TABLE indicador ADD asignar_Inventario NUMBER(1);
UPDATE Indicador set asignar_Inventario = 0 WHERE asignar_Inventario IS NULL;

COMMIT;
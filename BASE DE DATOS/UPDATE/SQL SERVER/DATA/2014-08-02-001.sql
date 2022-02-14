UPDATE indicador_por_plan 
SET crecimiento = b.crecimiento, 
tipo_medicion = b.tipo_medicion
FROM indicador_por_plan a, indicador_crecimiento b
WHERE a.plan_id = b.plan_id 
AND a.indicador_id = b.indicador_id;

DROP TABLE indicador_crecimiento;

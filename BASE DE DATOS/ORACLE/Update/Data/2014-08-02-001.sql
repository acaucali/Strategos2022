UPDATE (SELECT t1.crecimiento t1crecimiento, 
               t1.tipo_medicion t1tipo_medicion,
               t2.crecimiento t2crecimiento,
               t2.tipo_medicion t2tipo_medicion
          FROM indicador_por_plan t1,
               indicador_crecimiento t2
         WHERE t1.plan_id = t2.plan_id
         AND t1.indicador_id = t2.indicador_id)
   SET t1crecimiento = t2crecimiento,
       t1tipo_medicion = t2tipo_medicion;
       
DROP TABLE indicador_crecimiento;
	
COMMIT;

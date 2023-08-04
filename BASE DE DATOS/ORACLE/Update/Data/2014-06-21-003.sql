DECLARE
	vActividad_id pry_actividad.actividad_id%TYPE;
	vAlerta pry_actividad_plan.alerta%TYPE;
	vPorcentaje_completado pry_actividad_plan.porcentaje_completado%TYPE;
	vPorcentaje_esperado pry_actividad_plan.porcentaje_esperado%TYPE;
	vPorcentaje_ejecutado pry_actividad_plan.porcentaje_ejecutado%TYPE;
	vAno medicion.Ano%TYPE;
	vPeriodo medicion.Periodo%TYPE;
	ultimoperiodo VARCHAR2(10);
	valorEsperado FLOAT;
	valorEjecutado FLOAT;
	vCount NUMBER(2);
	CURSOR curTable IS SELECT actividad_id FROM pry_actividad;

BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vActividad_id;
    EXIT WHEN curTable%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM pry_actividad_plan WHERE actividad_id = vActividad_id AND rownum = 1;
		
		IF vCount = 1 THEN
		BEGIN
			SELECT 
				alerta, 
				porcentaje_completado,
				porcentaje_esperado,
				porcentaje_ejecutado
			INTO
				vAlerta, vPorcentaje_completado, vPorcentaje_esperado, vPorcentaje_ejecutado
			FROM pry_actividad_plan 
			WHERE actividad_id = vActividad_id AND rownum = 1;
			
			SELECT COUNT(*) INTO vCount FROM medicion WHERE indicador_id in (SELECT indicador_id FROM pry_actividad WHERE actividad_id = vActividad_id) AND serie_id = 0 AND rownum = 1 ORDER BY Ano DESC, Periodo DESC;
			IF vCount = 1 THEN
			BEGIN
				SELECT 
					ano, 
					periodo 
				INTO 
					vAno, vPeriodo
				FROM medicion 
				WHERE indicador_id in (SELECT indicador_id FROM pry_actividad WHERE actividad_id = vActividad_id) AND serie_id = 0 AND rownum = 1 ORDER BY Ano DESC, Periodo DESC;
				ultimoperiodo := vAno || '/' || vPeriodo;
			END;
			ELSE
				ultimoperiodo := null;
			END IF;
			
			IF vPorcentaje_esperado IS NULL THEN
				SELECT SUM(valor) INTO valorEsperado from medicion WHERE indicador_id in (SELECT indicador_id FROM pry_actividad WHERE actividad_id = vActividad_id) AND serie_id = 1;
			ELSE
				valorEsperado := vPorcentaje_esperado;
			END IF;
			IF vPorcentaje_ejecutado IS NULL THEN
				valorEjecutado := vPorcentaje_completado;
			ELSE
				valorEjecutado := vPorcentaje_ejecutado;
			END IF;
			
			UPDATE pry_actividad 
			SET crecimiento = vAlerta, 
			porcentaje_completado = vPorcentaje_completado, 
			porcentaje_esperado = valorEsperado, 
			porcentaje_ejecutado = valorEjecutado, 
			fecha_ultima_medicion = ultimoperiodo 
			WHERE actividad_id = vActividad_id;
		END;
		END IF;
  END LOOP;
  CLOSE curTable;
  COMMIT;
END;
/

COMMIT;

DECLARE
	vIniciativa_id Iniciativa.Iniciativa_id%TYPE;
	vAno iniciativa_por_plan.Ano%TYPE;
	vPeriodo iniciativa_por_plan.Periodo%TYPE;
	vAlerta iniciativa_por_plan.alerta%TYPE;
	vPorcentaje_completado iniciativa_por_plan.porcentaje_completado%TYPE;
	ultimoperiodo VARCHAR2(10);
	vCount NUMBER(2);
	CURSOR curTable IS SELECT DISTINCT iniciativa_id FROM iniciativa;

BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vIniciativa_id;
    EXIT WHEN curTable%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM iniciativa_por_plan WHERE iniciativa_id = vIniciativa_id AND rownum = 1 ORDER BY Ano DESC, Periodo DESC;
		
		IF vCount = 1 THEN
		BEGIN
			SELECT 
				ano, 
				periodo, 
				alerta, 
				porcentaje_completado 
			INTO vAno, vPeriodo, vAlerta, vPorcentaje_completado 
			FROM iniciativa_por_plan WHERE iniciativa_id = vIniciativa_id AND rownum = 1 ORDER BY Ano DESC, Periodo DESC;
			ultimoperiodo := vAno || '/' || vPeriodo;
			UPDATE iniciativa SET crecimiento = vAlerta, porcentaje_completado = vPorcentaje_completado, fecha_ultima_medicion = ultimoperiodo WHERE iniciativa_id = vIniciativa_id;
		END;
		END IF;
  END LOOP;
  CLOSE curTable;
  COMMIT;
END;
/

COMMIT;

UPDATE perspectiva SET crecimiento_parcial = crecimiento;

DECLARE
	vIndicadorId perspectiva.nl_ano_indicador_id%TYPE;
	vPlanId perspectiva.plan_id%TYPE;
	vPerspectivaId perspectiva.perspectiva_id%TYPE;
	vAlerta perspectiva.crecimiento_anual%TYPE;
	vCount NUMBER(2);
	CURSOR curItems IS SELECT nl_ano_indicador_id, plan_id, perspectiva_id FROM perspectiva;

BEGIN
  OPEN curItems;
  LOOP  -- Fetches 2 columns into variables
    FETCH curItems INTO vIndicadorId, vPlanId, vPerspectivaId;
    EXIT WHEN curItems%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM indicador_por_plan WHERE indicador_id = vIndicadorId AND plan_id = vPlanId;
		
		IF vCount > 0 THEN 
		BEGIN
			SELECT crecimiento INTO vAlerta FROM indicador_por_plan WHERE indicador_id = vIndicadorId AND plan_id = vPlanId;
			UPDATE perspectiva SET crecimiento_anual = vAlerta WHERE perspectiva_id = vPerspectivaId;
		END;
		END IF;
  END LOOP;
  CLOSE curItems;
  COMMIT;
END;
/

COMMIT;

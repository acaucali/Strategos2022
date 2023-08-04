--Vincular Iniciativas
DECLARE
	vIniciativaId iniciativa.iniciativa_id%TYPE;
	vIniciativaAsociadaId iniciativa.iniciativa_asociada_id%TYPE;
	vCount NUMBER(2);
	CURSOR curTable IS SELECT iniciativa_id, iniciativa_asociada_id FROM iniciativa WHERE iniciativa_asociada_id IS NOT NULL;
	
BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vIniciativaId, vIniciativaAsociadaId;
    EXIT WHEN curTable%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM iniciativa_plan WHERE iniciativa_id = vIniciativaAsociadaId AND plan_id in (SELECT plan_id FROM iniciativa_plan WHERE iniciativa_id = vIniciativaId);
		
		-- Buscar valor Ejecutado
		IF vCount > 0 THEN 
		BEGIN
			INSERT INTO iniciativa_plan
			SELECT vIniciativaAsociadaId, plan_id FROM iniciativa_plan WHERE iniciativa_id = vIniciativaId;
	
			INSERT INTO iniciativa_por_perspectiva
			SELECT vIniciativaAsociadaId, perspectiva_id FROM iniciativa_por_perspectiva WHERE iniciativa_id = vIniciativaId;

			DELETE FROM iniciativa_plan WHERE iniciativa_id = vIniciativaId;
			DELETE FROM iniciativa_por_perspectiva WHERE iniciativa_id = vIniciativaId;
			DELETE FROM iniciativa_objeto WHERE iniciativa_id = vIniciativaId;
			DELETE FROM iniciativa WHERE iniciativa_id = vIniciativaId;				
		END;
		END IF;
	END LOOP;
  CLOSE curTable;
  COMMIT;
END;
/

COMMIT;

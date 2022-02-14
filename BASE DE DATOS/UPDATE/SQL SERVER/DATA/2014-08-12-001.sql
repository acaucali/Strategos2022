--Vincular Iniciativas
CREATE OR REPLACE FUNCTION plpgsql_call_handler() RETURNS language_handler AS '$libdir/plpgsql', 'plpgsql_call_handler' LANGUAGE 'c' VOLATILE;
CREATE PROCEDURAL LANGUAGE 'plpgsql' HANDLER plpgsql_call_handler;
CREATE LANGUAGE plpgsql HANDLER funcion_handler;
--DROP FUNCTION getMigrarVincularIniciativa();

CREATE OR REPLACE FUNCTION getMigrarVincularIniciativa() RETURNS SETOF iniciativa AS
$BODY$
DECLARE
    r iniciativa%rowtype;
BEGIN
    FOR r IN SELECT * FROM iniciativa WHERE iniciativa_asociada_id is not null
    AND iniciativa_id > 0
    LOOP
	IF NOT EXISTS (SELECT * FROM iniciativa_plan WHERE iniciativa_id = r.iniciativa_asociada_id AND plan_id in (SELECT plan_id FROM iniciativa_plan WHERE iniciativa_id = r.iniciativa_id))
	THEN
		INSERT INTO iniciativa_plan
		SELECT r.iniciativa_asociada_id, plan_id FROM iniciativa_plan WHERE iniciativa_id = r.iniciativa_id;
	
		INSERT INTO iniciativa_por_perspectiva
		SELECT r.iniciativa_asociada_id, perspectiva_id FROM iniciativa_por_perspectiva WHERE iniciativa_id = r.iniciativa_id;

		DELETE FROM iniciativa_plan WHERE iniciativa_id = r.iniciativa_id;
		DELETE FROM iniciativa_por_perspectiva WHERE iniciativa_id = r.iniciativa_id;
		DELETE FROM iniciativa_objeto WHERE iniciativa_id = r.iniciativa_id;
		DELETE FROM iniciativa WHERE iniciativa_id = r.iniciativa_id;	
	END IF;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT getMigrarVincularIniciativa();

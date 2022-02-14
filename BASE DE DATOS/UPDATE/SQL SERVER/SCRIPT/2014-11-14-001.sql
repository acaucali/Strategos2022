CREATE OR REPLACE FUNCTION checkColumn() RETURNS SETOF iniciativa AS
$BODY$
DECLARE
    r iniciativa%rowtype;
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'iniciativa' AND column_name = 'iniciativa_asociada_id')
	THEN
		DROP INDEX ie_iniciativa_asociado;
		ALTER TABLE iniciativa DROP CONSTRAINT fk_iniciativa_asociadaid;
		ALTER TABLE iniciativa DROP COLUMN iniciativa_asociada_id;
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'iniciativa' AND column_name = 'iniciativa_asociada_plan_id')
	THEN
		DROP INDEX ie_iniciativa_asociado_plan;
		ALTER TABLE iniciativa DROP CONSTRAINT fk_iniciativa_asociada_plan;
		ALTER TABLE iniciativa DROP COLUMN iniciativa_asociada_plan_id;
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'pry_actividad' AND column_name = 'iniciativa_asociada_plan_id')
	THEN
		ALTER TABLE pry_actividad DROP COLUMN iniciativa_asociada_plan_id;
	END IF;
	
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT checkColumn();
DROP FUNCTION checkColumn();

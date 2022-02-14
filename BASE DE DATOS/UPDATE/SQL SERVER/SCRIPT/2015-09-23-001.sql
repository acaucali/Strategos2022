CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('explicacion') AND column_name = LOWER('modificado_id'))	
	THEN
		execute 'ALTER TABLE explicacion DROP COLUMN modificado_id';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('explicacion') AND column_name = LOWER('modificado'))	
	THEN
		execute 'ALTER TABLE explicacion DROP COLUMN modificado';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

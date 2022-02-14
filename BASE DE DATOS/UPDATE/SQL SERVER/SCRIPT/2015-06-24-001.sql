CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('celda') AND column_name = LOWER('titulo'))	
	THEN
		execute 'ALTER TABLE celda ALTER COLUMN titulo TYPE character varying(100)';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

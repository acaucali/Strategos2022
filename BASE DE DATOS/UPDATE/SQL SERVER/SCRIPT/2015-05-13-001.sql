CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('perspectiva') AND column_name = LOWER('crecimiento_parcial'))	
	THEN
		execute 'ALTER TABLE perspectiva ADD COLUMN crecimiento_parcial numeric(1, 0) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('perspectiva') AND column_name = LOWER('crecimiento_anual'))	
	THEN
		execute 'ALTER TABLE perspectiva ADD COLUMN crecimiento_anual numeric(1, 0) NULL';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

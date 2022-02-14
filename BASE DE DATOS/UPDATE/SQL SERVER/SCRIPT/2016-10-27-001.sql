CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('clase') AND column_name = LOWER('nombre') and character_maximum_length = 310)
	THEN
		execute 'ALTER TABLE clase ALTER COLUMN nombre VARCHAR(310)';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

DROP TABLE IF EXISTS indicador_por_reporte;

UPDATE afw_sistema set actual = '8.01-161027';
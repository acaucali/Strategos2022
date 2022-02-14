CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('portafolio') AND column_name = LOWER('fecha_ultimo_calculo'))
	THEN
		execute 'ALTER TABLE portafolio ADD COLUMN fecha_ultimo_calculo character varying(10)';
	END IF;				
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160924';

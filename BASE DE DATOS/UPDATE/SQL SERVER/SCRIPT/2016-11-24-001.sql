CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM afw_transaccion)
	THEN
		execute 'DROP TABLE IF EXISTS transaccion_indicador';
		execute 'DROP TABLE IF EXISTS afw_transaccion';
	END IF;				
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-161124';

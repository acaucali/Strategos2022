CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'afw_transaccion') 
	THEN
		DELETE FROM afw_permiso WHERE permiso_id LIKE '%INDICADOR_MEDICION_TRANSACCION%';
	END IF;				
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-161124';

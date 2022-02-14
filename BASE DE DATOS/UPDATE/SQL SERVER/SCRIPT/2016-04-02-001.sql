CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('afw_auditoria_memo') AND column_name = LOWER('valor'))	
	THEN
		execute 'ALTER TABLE afw_auditoria_memo ALTER COLUMN valor TYPE character varying(2000)';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('afw_auditoria_memo') AND column_name = LOWER('valor_anterior'))	
	THEN
		execute 'ALTER TABLE afw_auditoria_memo ALTER COLUMN valor_anterior TYPE character varying(2000)';
	END IF;	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160402';

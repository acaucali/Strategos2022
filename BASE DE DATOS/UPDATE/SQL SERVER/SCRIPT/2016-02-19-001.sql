CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('afw_usuario') AND column_name = LOWER('u_id'))	
	THEN
		execute 'ALTER TABLE afw_usuario ALTER COLUMN u_id TYPE character varying(50)';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160219';

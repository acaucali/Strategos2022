CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('explicacion') AND column_name = LOWER('publico'))
	THEN
		execute 'ALTER TABLE explicacion ADD publico numeric(1) NULL';
		execute 'UPDATE explicacion SET publico = 1';
		execute 'ALTER TABLE explicacion ALTER COLUMN publico SET NOT NULL';
	END IF;					
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();
	
UPDATE afw_sistema set actual = '8.01-170912';

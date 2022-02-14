CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('responsable') AND column_name = LOWER('tipo'))
	THEN
		execute 'UPDATE responsable SET tipo = 0 WHERE tipo IS NULL';
		execute 'ALTER TABLE responsable ALTER COLUMN tipo SET NOT NULL';	
	END IF;				
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('responsable') AND column_name = LOWER('grupo'))
	THEN
		execute 'UPDATE responsable SET grupo = 0 WHERE grupo IS NULL';
		execute 'ALTER TABLE responsable ALTER COLUMN grupo SET NOT NULL';	
	END IF;				
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

DROP TABLE IF EXISTS indicador_por_reporte;

UPDATE afw_sistema set actual = '8.01-161112';
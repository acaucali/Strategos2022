CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('perspectiva_relacionada') AND constraint_name = LOWER('pk_perspectiva_relacionada'))
	THEN
		execute 'ALTER TABLE perspectiva_relacionada DROP CONSTRAINT pk_perspectiva_relacionada';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('perspectiva_relacionada') AND constraint_name = LOWER('pk_perspectiva_persrelacionada'))
	THEN
		execute 'ALTER TABLE perspectiva_relacionada DROP CONSTRAINT pk_perspectiva_persrelacionada';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

DROP TABLE IF EXISTS indicador_por_reporte;

UPDATE afw_sistema set actual = '8.01-161011';
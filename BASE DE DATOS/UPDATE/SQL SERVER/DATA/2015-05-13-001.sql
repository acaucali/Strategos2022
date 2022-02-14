UPDATE perspectiva
SET crecimiento_parcial = crecimiento;

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    r perspectiva%rowtype;
	alerta numeric;
BEGIN
    FOR r IN SELECT * FROM perspectiva
    LOOP
	IF EXISTS (SELECT * FROM indicador_por_plan WHERE indicador_id = r.nl_ano_indicador_id AND plan_id = r.plan_id)
	THEN
		SELECT INTO alerta crecimiento FROM indicador_por_plan WHERE indicador_id = r.nl_ano_indicador_id AND plan_id = r.plan_id;
		UPDATE perspectiva SET crecimiento_anual = alerta WHERE perspectiva_id = r.perspectiva_id;
	END IF;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('perspectiva') AND column_name = LOWER('crecimiento'))	
	THEN
		execute 'ALTER TABLE perspectiva DROP COLUMN crecimiento';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

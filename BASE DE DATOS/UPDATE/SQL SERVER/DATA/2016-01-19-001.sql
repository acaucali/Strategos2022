--Setear las Frecuencias de las perspectivas, el plan y los indicadores de porcentajes de logro 
--a la frecuencia mayor que tienen todas sus hijas
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    plan planes%rowtype;
    perspectiva perspectiva%rowtype;
    frecuenciaHijas numeric(1,0);
    frecuenciaPadre numeric(1,0);
BEGIN
    FOR plan IN SELECT * FROM planes
    LOOP
		SELECT INTO frecuenciaHijas MAX(frecuencia) from perspectiva where padre_id in (select perspectiva_id from perspectiva where padre_id is null and plan_id = plan.plan_id) and plan_id = plan.plan_id;
		SELECT INTO frecuenciaPadre frecuencia from perspectiva where padre_id is null and plan_id = plan.plan_id;
		
		IF frecuenciaHijas <> frecuenciaPadre
		THEN
			UPDATE indicador SET FRECUENCIA = frecuenciaHijas WHERE indicador_id IN (
												SELECT NL_ano_indicador_id FROM perspectiva where padre_id is null and plan_id = plan.plan_id
												UNION
												SELECT NL_par_indicador_id FROM perspectiva where padre_id is null and plan_id = plan.plan_id
												UNION
												SELECT NL_ano_indicador_id FROM planes where plan_id = plan.plan_id
												UNION
												SELECT NL_par_indicador_id FROM planes where plan_id = plan.plan_id
												);
			UPDATE perspectiva SET FRECUENCIA = frecuenciaHijas WHERE padre_id is null and plan_id = plan.plan_id;
		END IF;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

--Limpiar la tabla afw_version
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    tableVersion afw_version%rowtype;
    numeroVersion numeric(10,0);
    
BEGIN
    FOR tableVersion IN SELECT version, build, datebuild, MIN(createdat) AS createdat FROM afw_version GROUP BY version, build, datebuild ORDER BY version
    LOOP
	SELECT INTO numeroVersion COUNT(*) FROM afw_version WHERE version = tableVersion.version AND build = tableVersion.build AND datebuild = tableVersion.datebuild AND createdat <> tableVersion.createdat;
	IF numeroVersion > 0
	THEN
		--raise notice 'Value: %', numeroVersion;
		DELETE FROM afw_version WHERE version = tableVersion.version AND build = tableVersion.build AND datebuild = tableVersion.datebuild AND createdat <> tableVersion.createdat;
	END IF;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160119';

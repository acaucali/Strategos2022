CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('tipo_medicion'))	
	THEN
		execute 'ALTER TABLE iniciativa ADD COLUMN tipo_medicion numeric(1,0) NULL';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    r iniciativa%rowtype;
    f RECORD;
BEGIN
    FOR r IN SELECT * FROM iniciativa WHERE tipo_medicion IS NULL AND proyecto_id IS NOT NULL
    LOOP
		SELECT INTO f * FROM pry_actividad WHERE proyecto_id = r.proyecto_id LIMIT 1;
        UPDATE iniciativa SET tipo_medicion = f.tipo_medicion WHERE proyecto_id = r.proyecto_id;
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
	IF EXISTS (SELECT * FROM iniciativa WHERE tipo_medicion IS NULL)	
	THEN
		execute 'UPDATE iniciativa SET tipo_medicion = 0 WHERE tipo_medicion IS NULL';
	END IF;
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('tipo_medicion'))	
	THEN
		execute 'ALTER TABLE iniciativa ALTER COLUMN tipo_medicion SET NOT NULL';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

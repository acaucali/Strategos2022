CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
	r RECORD;
	vindexname character varying(100);
BEGIN
    FOR r IN SELECT indexname FROM pg_indexes WHERE tablename = 'pry_actividad' AND indexname <> 'pk_pry_actividad'
    LOOP
	execute 'DROP INDEX ' || r.indexname;
	--vindexname := 'DROP INDEX ' || r.indexname;
	--raise notice 'DROP INDEX  %', vindexname ;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE INDEX IE_pry_actividad_RespEjecutado ON pry_actividad (resp_cargar_ejecutado_id  ASC);
CREATE INDEX IE_pry_actividad_RespMeta ON pry_actividad (resp_cargar_meta_id   ASC);
CREATE INDEX IE_pry_actividad_Proyecto ON pry_actividad (proyecto_id   ASC);
CREATE INDEX IE_pry_actividad_Padre ON pry_actividad (padre_id   ASC);
CREATE INDEX IE_pry_actividad_Unidad ON pry_actividad (unidad_id   ASC);
CREATE INDEX IE_pry_actividad_Indicador ON pry_actividad (indicador_id   ASC);
CREATE INDEX IE_pry_actividad_Clase ON pry_actividad (clase_id   ASC);

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pry_actividad') AND constraint_name = LOWER('FK_indicador_actividad'))
	THEN
		execute 'ALTER TABLE pry_actividad ADD CONSTRAINT FK_indicador_actividad FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pry_actividad') AND constraint_name = LOWER('FK_clase_actividad'))
	THEN
		execute 'ALTER TABLE pry_actividad ADD CONSTRAINT FK_clase_actividad FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();


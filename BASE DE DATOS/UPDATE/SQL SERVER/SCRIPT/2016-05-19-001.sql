CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('historico_date'))	
	THEN
		execute 'ALTER TABLE iniciativa ADD COLUMN historico_date timestamp without time zone';
	END IF;
	
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('iniciativa') AND indexrelname = LOWER('IE_iniciativa_modificado'))
	THEN
		execute 'DROP INDEX IE_iniciativa_modificado';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('iniciativa') AND indexrelname = LOWER('IE_iniciativa_creado'))
	THEN
		execute 'DROP INDEX IE_iniciativa_creado';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('iniciativa') AND constraint_name = LOWER('fk_modificado_iniciativa'))
	THEN
		execute 'ALTER TABLE iniciativa DROP CONSTRAINT fk_modificado_iniciativa';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('iniciativa') AND constraint_name = LOWER('fk_creado_iniciativa'))
	THEN
		execute 'ALTER TABLE iniciativa DROP CONSTRAINT fk_creado_iniciativa';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('modificado'))	
	THEN
		execute 'ALTER TABLE iniciativa DROP COLUMN modificado';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('creado'))	
	THEN
		execute 'ALTER TABLE iniciativa DROP COLUMN creado';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('modificado_id'))	
	THEN
		execute 'ALTER TABLE iniciativa DROP COLUMN modificado_id';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('creado_id'))	
	THEN
		execute 'ALTER TABLE iniciativa DROP COLUMN creado_id';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160519';
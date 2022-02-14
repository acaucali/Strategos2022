CREATE OR REPLACE PROCEDURE checkSql() IS

BEGIN
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('responsable') AND constraint_name = LOWER('ak_responsable'))
	THEN
		execute 'ALTER TABLE responsable DROP CONSTRAINT ak_responsable';
	END IF;	

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('responsable') AND indexrelname = LOWER('ak_responsable'))
	THEN 
		execute 'DROP INDEX ak_responsable';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('responsable') AND constraint_name = LOWER('ak1_responsable'))
	THEN
		execute 'ALTER TABLE responsable DROP CONSTRAINT ak1_responsable';
	END IF;	

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('responsable') AND indexrelname = LOWER('ak1_responsable'))
	THEN 
		execute 'DROP INDEX ak1_responsable';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('responsable') AND indexrelname = LOWER('ak_responsable'))
	THEN 
		execute 'CREATE UNIQUE INDEX ak_responsable ON responsable USING btree (organizacion_id   ASC,nombre   ASC,cargo   ASC)';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('responsable') AND indexrelname = LOWER('akc_responsable'))
	THEN 
		execute 'ALTER TABLE responsable ADD CONSTRAINT  akc_responsable UNIQUE (organizacion_id,nombre,cargo)';
	END IF;
END;

UPDATE afw_sistema set actual = '8.01-170928';
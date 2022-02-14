--Create Constrins en la tabla version
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('afw_version') AND constraint_name = LOWER('pk_afw_version'))
	THEN
		execute 'ALTER TABLE afw_version DROP CONSTRAINT pk_afw_version';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('afw_version') AND constraint_name = LOWER('pk_afw_version'))
	THEN
		execute 'ALTER TABLE afw_version ADD CONSTRAINT pk_afw_version PRIMARY KEY (version, build, datebuild)';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

-- Created Index
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('afw_version') AND indexrelname = LOWER('ak_afw_version'))
	THEN
		execute 'DROP INDEX ak_afw_version';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('afw_version') AND indexrelname = LOWER('ak_afw_version'))
	THEN
		execute 'CREATE UNIQUE INDEX ak_afw_version ON afw_version (version ASC, build ASC, datebuild ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('afw_version') AND indexrelname = LOWER('ie_afw_version'))
	THEN
		execute 'CREATE  INDEX ie_afw_version ON afw_version (createdat   ASC)';
	END IF;

END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160120';

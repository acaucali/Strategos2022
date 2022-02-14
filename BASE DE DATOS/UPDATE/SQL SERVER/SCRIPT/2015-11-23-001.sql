CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('afw_sistema') AND column_name = LOWER('actual'))	
	THEN
		execute 'ALTER TABLE afw_sistema ADD COLUMN actual character varying(50) NULL';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM afw_sistema WHERE actual IS NULL)	
	THEN
		UPDATE afw_sistema SET actual = version || '-' || build;
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('afw_sistema') AND column_name = LOWER('actual'))	
	THEN
		execute 'ALTER TABLE afw_sistema ALTER COLUMN actual SET NOT NULL';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

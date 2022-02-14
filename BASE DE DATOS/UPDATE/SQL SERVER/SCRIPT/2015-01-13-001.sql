CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('FORMULA') AND constraint_name = LOWER('fk_formula_serie'))
	THEN
		execute 'ALTER TABLE FORMULA DROP CONSTRAINT fk_formula_serie';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('pk_conjunto_formula'))
	THEN
		execute 'ALTER TABLE conjunto_formula DROP CONSTRAINT pk_conjunto_formula';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_serid_serie'))
	THEN
		execute 'ALTER TABLE CONJUNTO_FORMULA DROP CONSTRAINT fk_conjfor_serid_serie';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_insid_serie'))
	THEN
		execute 'ALTER TABLE CONJUNTO_FORMULA DROP CONSTRAINT fk_conjfor_insid_serie';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('pk_serie_tiempo'))
	THEN
		execute 'ALTER TABLE serie_tiempo DROP CONSTRAINT pk_serie_tiempo';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('ak1_serie_tiempo'))
	THEN
		execute 'ALTER TABLE serie_tiempo DROP CONSTRAINT ak1_serie_tiempo';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('ak2_serie_tiempo'))
	THEN
		execute 'ALTER TABLE serie_tiempo DROP CONSTRAINT ak2_serie_tiempo';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'pk_serie_tiempo')
	THEN
		execute 'DROP INDEX pk_serie_tiempo';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'ak1_serie_tiempo')
	THEN
		execute 'DROP INDEX ak1_serie_tiempo';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'ak2_serie_tiempo')
	THEN
		execute 'DROP INDEX ak2_serie_tiempo';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'ak_serie_tiempo')
	THEN
		execute 'CREATE UNIQUE INDEX ak_serie_tiempo ON serie_tiempo (serie_id   ASC)';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('pk_serie_tiempo'))
	THEN
		execute 'ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo PRIMARY KEY (serie_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'ak_serie_tiempo_nombre')
	THEN
		execute 'CREATE UNIQUE INDEX ak_serie_tiempo_nombre ON serie_tiempo (nombre   ASC)';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('pk_serie_tiempo_nombre'))
	THEN
		execute 'ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_nombre UNIQUE (nombre)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'serie_tiempo' AND indexrelname = 'ak_serie_tiempo_identificador')
	THEN
		execute 'CREATE UNIQUE INDEX ak_serie_tiempo_identificador ON serie_tiempo (identificador   ASC)';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('serie_tiempo') AND constraint_name = LOWER('pk_serie_tiempo_identificador'))
	THEN
		execute 'ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_identificador UNIQUE (identificador)';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

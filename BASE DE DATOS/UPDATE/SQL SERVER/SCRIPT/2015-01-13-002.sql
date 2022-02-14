CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('FORMULA') AND constraint_name = LOWER('pk_formula'))
	THEN
		execute 'ALTER TABLE FORMULA DROP CONSTRAINT pk_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('pk_formula'))
	THEN
		execute 'DROP INDEX pk_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('xif1formula'))
	THEN
		execute 'DROP INDEX xif1formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('xif2formula'))
	THEN
		execute 'DROP INDEX xif2formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('xif3formula'))
	THEN
		execute 'DROP INDEX xif3formula';
	END IF;

	IF EXISTS (SELECT * FROM INDICADOR WHERE CLASE_ID NOT IN (SELECT CLASE_ID FROM CLASE))
	THEN
		execute 'DELETE FROM INDICADOR WHERE CLASE_ID NOT IN (SELECT CLASE_ID FROM CLASE)';
	END IF;

	IF EXISTS (SELECT * FROM CONJUNTO_FORMULA WHERE INDICADOR_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR))
	THEN
		execute 'DELETE FROM CONJUNTO_FORMULA WHERE INDICADOR_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR)';
	END IF;

	IF EXISTS (SELECT * FROM FORMULA WHERE INDICADOR_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR))
	THEN
		execute 'DELETE FROM FORMULA WHERE INDICADOR_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('ak_formula'))
	THEN
		execute 'CREATE UNIQUE INDEX ak_formula ON formula (indicador_id ASC,serie_id ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('FORMULA') AND constraint_name = LOWER('pk_formula'))
	THEN
		execute 'ALTER TABLE FORMULA ADD CONSTRAINT pk_formula PRIMARY KEY (indicador_id, serie_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('ie_formula_indicadorid'))
	THEN
		execute 'CREATE  INDEX ie_formula_indicadorid ON FORMULA (indicador_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('FORMULA') AND indexrelname = LOWER('ie_formula_serie'))
	THEN
		execute 'CREATE  INDEX ie_formula_serie ON FORMULA (serie_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('FORMULA') AND constraint_name = LOWER('fk_formula_serie'))
	THEN
		execute 'ALTER TABLE FORMULA ADD CONSTRAINT fk_formula_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('FORMULA') AND constraint_name = LOWER('FK_FORMULA_INDICADOR'))
	THEN
		execute 'ALTER TABLE FORMULA ADD CONSTRAINT FK_FORMULA_INDICADOR FOREIGN KEY (INDICADOR_ID) REFERENCES INDICADOR (INDICADOR_ID) ON DELETE CASCADE';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('pk_conjunto_formula'))
	THEN
		execute 'ALTER TABLE conjunto_formula DROP CONSTRAINT pk_conjunto_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('pk_conjunto_formula'))
	THEN
		execute 'DROP INDEX pk_conjunto_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('xif1conjunto_formula'))
	THEN
		execute 'DROP INDEX xif1conjunto_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('xif2conjunto_formula'))
	THEN
		execute 'DROP INDEX xif2conjunto_formula';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('xif3conjunto_formula'))
	THEN
		execute 'DROP INDEX xif3conjunto_formula';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('ak_conjunto_formula'))
	THEN
		execute 'CREATE UNIQUE INDEX ak_conjunto_formula ON conjunto_formula (insumo_serie_id   ASC,indicador_id   ASC,padre_id   ASC,serie_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('pk_conjunto_formula'))
	THEN
		execute 'ALTER TABLE conjunto_formula ADD CONSTRAINT pk_conjunto_formula PRIMARY KEY (insumo_serie_id, indicador_id, padre_id, serie_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('ie_conjunto_formula_padreid'))
	THEN
		execute 'CREATE  INDEX ie_conjunto_formula_padreid ON conjunto_formula (padre_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('ie_conjunto_formula_indid'))
	THEN
		execute 'CREATE  INDEX ie_conjunto_formula_indid ON conjunto_formula (indicador_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('ie_conjunto_formula_serieid'))
	THEN
		execute 'CREATE  INDEX ie_conjunto_formula_serieid ON conjunto_formula (serie_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('conjunto_formula') AND indexrelname = LOWER('ie_conjunto_formula_insserieid'))
	THEN
		execute 'CREATE  INDEX ie_conjunto_formula_insserieid ON conjunto_formula (insumo_serie_id   ASC)';
	END IF;

	IF EXISTS (SELECT * FROM CONJUNTO_FORMULA WHERE PADRE_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR))
	THEN
		execute 'DELETE FROM CONJUNTO_FORMULA WHERE PADRE_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_padre_indicador'))
	THEN
		execute 'ALTER TABLE conjunto_formula ADD CONSTRAINT fk_conjfor_padre_indicador FOREIGN KEY (padre_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_indid_indicador'))
	THEN
		execute 'ALTER TABLE conjunto_formula ADD CONSTRAINT fk_conjfor_indid_indicador FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_serid_serie'))
	THEN
		execute 'ALTER TABLE conjunto_formula ADD CONSTRAINT fk_conjfor_serid_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('conjunto_formula') AND constraint_name = LOWER('fk_conjfor_insid_serie'))
	THEN
		execute 'ALTER TABLE conjunto_formula ADD CONSTRAINT fk_conjfor_insid_serie FOREIGN KEY (insumo_serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

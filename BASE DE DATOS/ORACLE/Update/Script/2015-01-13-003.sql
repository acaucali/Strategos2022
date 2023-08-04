DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('conjunto_formula') AND CONSTRAINT_NAME = UPPER('pk_conjunto_formula');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula DROP CONSTRAINT pk_conjunto_formula';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('pk_conjunto_formula') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_conjunto_formula';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('xif1conjunto_formula') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX xif1conjunto_formula';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('xif2conjunto_formula') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX xif2conjunto_formula';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('xif3conjunto_formula') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX xif3conjunto_formula';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('ak_conjunto_formula') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_conjunto_formula ON conjunto_formula (insumo_serie_id   ASC,indicador_id   ASC,padre_id   ASC,serie_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('pk_conjunto_formula') AND TABLE_NAME = UPPER('conjunto_formula');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula ADD CONSTRAINT pk_conjunto_formula PRIMARY KEY (insumo_serie_id, indicador_id, padre_id, serie_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('ie_conjunto_formula_padreid') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_conjunto_formula_padreid ON conjunto_formula (padre_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('ie_conjunto_formula_indid') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_conjunto_formula_indid ON conjunto_formula (indicador_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('ie_conjunto_formula_serieid') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_conjunto_formula_serieid ON conjunto_formula (serie_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('conjunto_formula') AND INDEX_NAME = UPPER('ie_conjunto_formula_insserieid') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_conjunto_formula_insserieid ON conjunto_formula (insumo_serie_id   ASC)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM CONJUNTO_FORMULA WHERE PADRE_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR);
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DELETE FROM CONJUNTO_FORMULA WHERE PADRE_ID NOT IN (SELECT INDICADOR_ID FROM INDICADOR)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('fk_conjfor_padre_indicador') AND TABLE_NAME = UPPER('conjunto_formula');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula ADD (CONSTRAINT fk_conjfor_padre_indicador FOREIGN KEY (padre_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('fk_conjfor_indid_indicador') AND TABLE_NAME = UPPER('conjunto_formula');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula ADD (CONSTRAINT fk_conjfor_indid_indicador FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('fk_conjfor_serid_serie') AND TABLE_NAME = UPPER('conjunto_formula');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula ADD (CONSTRAINT fk_conjfor_serid_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('fk_conjfor_insid_serie') AND TABLE_NAME = UPPER('conjunto_formula');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula ADD (CONSTRAINT fk_conjfor_insid_serie FOREIGN KEY (insumo_serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE)';
	END;
	END IF;
	
END;
/

COMMIT;

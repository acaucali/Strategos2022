DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('FORMULA') AND CONSTRAINT_NAME = UPPER('fk_formula_serie');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE FORMULA DROP CONSTRAINT fk_formula_serie';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('conjunto_formula') AND CONSTRAINT_NAME = UPPER('pk_conjunto_formula');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE conjunto_formula DROP CONSTRAINT pk_conjunto_formula';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('CONJUNTO_FORMULA') AND CONSTRAINT_NAME = UPPER('fk_conjfor_serid_serie');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE CONJUNTO_FORMULA DROP CONSTRAINT fk_conjfor_serid_serie';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('CONJUNTO_FORMULA') AND CONSTRAINT_NAME = UPPER('fk_conjfor_insid_serie');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE CONJUNTO_FORMULA DROP CONSTRAINT fk_conjfor_insid_serie';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('serie_tiempo') AND CONSTRAINT_NAME = UPPER('pk_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo DROP CONSTRAINT pk_serie_tiempo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('serie_tiempo') AND CONSTRAINT_NAME = UPPER('ak1_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo DROP CONSTRAINT ak1_serie_tiempo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('serie_tiempo') AND CONSTRAINT_NAME = UPPER('ak2_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo DROP CONSTRAINT ak2_serie_tiempo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('pk_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_serie_tiempo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('ak1_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak1_serie_tiempo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('ak2_serie_tiempo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak2_serie_tiempo';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('ak_serie_tiempo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_serie_tiempo ON serie_tiempo (serie_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('pk_serie_tiempo') AND TABLE_NAME = UPPER('serie_tiempo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo ADD CONSTRAINT  pk_serie_tiempo PRIMARY KEY (serie_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('ak_serie_tiempo_nombre');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_serie_tiempo_nombre ON serie_tiempo (nombre   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('pk_serie_tiempo_nombre') AND TABLE_NAME = UPPER('serie_tiempo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_nombre UNIQUE (nombre)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('serie_tiempo') AND INDEX_NAME = UPPER('ak_serie_tiempo_identificador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_serie_tiempo_identificador ON serie_tiempo (identificador   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE CONSTRAINT_NAME = UPPER('pk_serie_tiempo_identificador') AND TABLE_NAME = UPPER('serie_tiempo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_identificador UNIQUE (identificador)';
	END;
	END IF;
	
END;
/

COMMIT;

DECLARE
	vCount NUMBER(5);

BEGIN
  --SELECT COUNT(*) FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('ak_responsable')
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('ak_responsable');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable DROP CONSTRAINT ak_responsable';
	END;
	END IF;

  --SELECT COUNT(*) FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('ak1_responsable');
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('AKC_RESPONSABLE');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable DROP CONSTRAINT AKC_RESPONSABLE';
	END;
	END IF;

  --SELECT COUNT(*) FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('ak1_responsable');
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable') AND CONSTRAINT_NAME = UPPER('ak1_responsable');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable DROP CONSTRAINT ak1_responsable';
	END;
	END IF;

  --SELECT COUNT(*) FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak_responsable');
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak_responsable');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak_responsable';
	END;
	END IF;

  --SELECT COUNT(*) FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak1_responsable');
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak1_responsable');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak1_responsable';
	END;
	END IF;

  --SELECT COUNT(*) FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('AKC_RESPONSABLE');
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('AKC_RESPONSABLE');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX AKC_RESPONSABLE';
	END;
	END IF;
    
  --SELECT COUNT(*) FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak_responsable');
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('ak_responsable');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_responsable ON responsable (organizacion_id   ASC,nombre   ASC,cargo   ASC)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('responsable') AND INDEX_NAME = UPPER('akc_responsable');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable ADD CONSTRAINT  akc_responsable UNIQUE (organizacion_id,nombre,cargo)';
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-170928';

COMMIT;

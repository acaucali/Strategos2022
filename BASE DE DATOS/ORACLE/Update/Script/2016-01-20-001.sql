DECLARE
	vCount NUMBER(5);

BEGIN
	--Create Constrins en la tabla version
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('afw_version') AND CONSTRAINT_NAME = UPPER('pk_afw_version');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_version DROP CONSTRAINT pk_afw_version';
	END;
	END IF;
	
	-- Created Index
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('afw_version') AND INDEX_NAME = UPPER('ak_afw_version');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak_afw_version';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('afw_version') AND INDEX_NAME = UPPER('pk_afw_version');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_afw_version';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('afw_version') AND INDEX_NAME = UPPER('ak_afw_version');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_afw_version ON afw_version (version   ASC, build   ASC, dateBuild   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('afw_version') AND INDEX_NAME = UPPER('ie_afw_version');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE INDEX ie_afw_version ON afw_version (createdat   ASC)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('afw_version') AND CONSTRAINT_NAME = UPPER('pk_afw_version');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_version ADD CONSTRAINT pk_afw_version PRIMARY KEY (version, build, dateBuild)';
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-160120';

COMMIT;

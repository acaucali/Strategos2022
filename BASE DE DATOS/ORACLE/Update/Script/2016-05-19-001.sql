DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('historico_date') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa ADD historico_date TIMESTAMP NULL';
	END;
	END IF;		

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('iniciativa') AND INDEX_NAME = UPPER('IE_iniciativa_modificado');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX IE_iniciativa_modificado';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('iniciativa') AND INDEX_NAME = UPPER('IE_iniciativa_creado');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX IE_iniciativa_creado';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('iniciativa') AND CONSTRAINT_NAME = UPPER('fk_modificado_iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP CONSTRAINT fk_modificado_iniciativa';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('iniciativa') AND CONSTRAINT_NAME = UPPER('fk_creado_iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP CONSTRAINT fk_creado_iniciativa';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP COLUMN modificado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP COLUMN creado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado_id') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP COLUMN modificado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado_id') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa DROP COLUMN creado_id';
	END;
	END IF;				
	
END;
/

UPDATE afw_sistema set actual = '8.01-160519';

COMMIT;


DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('vista_id') AND TABLE_NAME = UPPER('pagina') AND NULLABLE = 'N';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina MODIFY vista_id numeric(10) NULL';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('alerta') AND TABLE_NAME = UPPER('perspectiva_nivel');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva_nivel ADD alerta NUMBER(1) NULL';
	END;
	END IF;					

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('meta') AND TABLE_NAME = UPPER('perspectiva_nivel');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva_nivel ADD meta FLOAT NULL';
	END;
	END IF;					
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('perspectiva_nivel') AND INDEX_NAME = UPPER('IE_perspectiva_nivel');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX IE_perspectiva_nivel ON perspectiva_nivel (perspectiva_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('perspectiva_nivel') AND CONSTRAINT_NAME = UPPER('FK_perspectiva_perspectiva_niv');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva_nivel ADD (CONSTRAINT FK_perspectiva_perspectiva_niv FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('EXPLICACION') AND CONSTRAINT_NAME = UPPER('FK_usuario_explicacion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE EXPLICACION ADD (CONSTRAINT FK_usuario_explicacion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE)';
	END;
	END IF;	
END;
/

UPDATE afw_sistema set actual = '8.01-170325';

COMMIT;

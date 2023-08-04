DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('INDICADOR_ID') AND TABLE_NAME = UPPER('mb_atributo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_atributo ADD INDICADOR_ID NUMBER(10) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('APC') AND TABLE_NAME = UPPER('mb_atributo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_atributo ADD APC NUMBER(10) NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('COMENTARIO') AND TABLE_NAME = UPPER('mb_encuesta_atributo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_encuesta_atributo ADD COMENTARIO LONG VARCHAR NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM dba_tables WHERE table_name = 'mb_histograma';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE mb_histograma';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('ENCSTAS_X_ENCSTADO') AND TABLE_NAME = UPPER('mb_medicion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_medicion ADD ENCSTAS_X_ENCSTADO NUMBER(10) NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('COMENT_X_PREGUNTA') AND TABLE_NAME = UPPER('mb_medicion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_medicion ADD COMENT_X_PREGUNTA NUMBER(10) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('ASUNTO_CORREO') AND TABLE_NAME = UPPER('mb_medicion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_medicion ADD ASUNTO_CORREO NUMBER(10) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('CODIGO_ENLACE') AND TABLE_NAME = UPPER('mb_organizacion_variable');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_organizacion_variable ADD CODIGO_ENLACE VARCHAR2(100) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('CODIGO_ENLACE') AND TABLE_NAME = UPPER('mb_sector');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_sector ADD CODIGO_ENLACE VARCHAR2(100) NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM dba_tables WHERE table_name = UPPER('mb_atrib_coment_cat');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE mb_atrib_coment_cat (atributo_id          NUMBER(10) NOT NULL, orden                NUMBER(3) NOT NULL)';
		execute immediate 'CREATE UNIQUE INDEX AK_mb_atrib_coment_cat ON mb_atrib_coment_cat (atributo_id   ASC,orden   ASC)';
		execute immediate 'ALTER TABLE mb_atrib_coment_cat ADD CONSTRAINT  PK_mb_atrib_coment_cat PRIMARY KEY (atributo_id,orden)';
		execute immediate 'CREATE  INDEX IE_mb_atrib_coment_cat ON mb_atrib_coment_cat (atributo_id   ASC)';
		execute immediate 'ALTER TABLE mb_atrib_coment_cat ADD (CONSTRAINT PK_mb_atributo_commnet_orden FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('cirterio_desempate') AND TABLE_NAME = UPPER('mb_medicion');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_medicion DROP COLUMN cirterio_desempate';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('CRITERIO_DESEMPATE') AND TABLE_NAME = UPPER('mb_medicion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE mb_medicion ADD CRITERIO_DESEMPATE NUMBER(1) NULL';
	END;
	END IF;				
	
END;
/

COMMIT;

DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('enlace_parcial') AND TABLE_NAME = UPPER('Indicador');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE Indicador MODIFY enlace_parcial VARCHAR2(100) NULL';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('enlace_parcial') AND TABLE_NAME = UPPER('clase');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE clase MODIFY enlace_parcial VARCHAR2(100) NULL';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('enlace_parcial') AND TABLE_NAME = UPPER('organizacion');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE organizacion MODIFY enlace_parcial VARCHAR2(100) NULL';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('medicion_bloqueo');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE medicion_bloqueo (organizacion_id NUMBER(10) NOT NULL, bloqueo_inicio DATE NOT NULL, bloqueo_fin DATE NOT NULL)';	
		execute immediate 'CREATE UNIQUE INDEX AK_medicion_bloqueo ON medicion_bloqueo (organizacion_id   ASC)';
		execute immediate 'ALTER TABLE medicion_bloqueo ADD CONSTRAINT  PK_medicion_bloqueo PRIMARY KEY (organizacion_id)';
		execute immediate 'ALTER TABLE medicion_bloqueo ADD (CONSTRAINT FK_organizacion_medicionbloque FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
	END;
	END IF;
	
END;
/
	
UPDATE afw_sistema set actual = '8.01-170618';

COMMIT;

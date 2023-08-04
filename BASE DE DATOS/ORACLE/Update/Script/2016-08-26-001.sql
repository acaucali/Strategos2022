DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('indicador_por_portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE indicador_por_portafolio (indicador_id NUMBER(10) NOT NULL, portafolioId NUMBER(10) NOT NULL, tipo NUMBER(1) NOT NULL)';	
		execute immediate 'CREATE UNIQUE INDEX AK_indicador_por_portafolio ON indicador_por_portafolio (indicador_id   ASC,portafolioId   ASC)';
		execute immediate 'ALTER TABLE indicador_por_portafolio ADD CONSTRAINT PK_indicador_por_portafolio PRIMARY KEY (indicador_id,portafolioId)';
		execute immediate 'CREATE INDEX IE_indicador_por_portafolio_in ON indicador_por_portafolio (indicador_id   ASC)';
		execute immediate 'CREATE INDEX IE_indicador_por_portafolio_po ON indicador_por_portafolio (portafolioId   ASC)';
		execute immediate 'ALTER TABLE indicador_por_portafolio ADD (CONSTRAINT FK_indicador_indXpor FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
		execute immediate 'ALTER TABLE indicador_por_portafolio ADD (CONSTRAINT FK_portafolio_indXpor FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON DELETE CASCADE)';		
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('frecuencia') AND TABLE_NAME = UPPER('portafolio') AND NULLABLE = 'N';
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE portafolio ADD frecuencia NUMERIC(1) NULL';
		execute immediate 'UPDATE portafolio SET frecuencia = 3';
		execute immediate 'ALTER TABLE portafolio MODIFY frecuencia NUMERIC(1) NOT NULL';	
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('clase_id') AND TABLE_NAME = UPPER('portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE portafolio ADD clase_id NUMERIC(10) NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('portafolio') AND INDEX_NAME = UPPER('IE_portafolio_clase');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX IE_portafolio_clase ON portafolio (clase_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('portafolio') AND CONSTRAINT_NAME = UPPER('FK_clase_portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE portafolio ADD (CONSTRAINT FK_clase_portafolio FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('estatusid') AND TABLE_NAME = UPPER('portafolio') AND NULLABLE = 'Y';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'UPDATE portafolio SET estatusid = 1';
		execute immediate 'ALTER TABLE portafolio MODIFY estatusid NUMERIC(10) NOT NULL';	
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('peso') AND TABLE_NAME = UPPER('portafolio_iniciativa');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE portafolio_iniciativa ADD peso FLOAT  NULL';
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-160826';

COMMIT;

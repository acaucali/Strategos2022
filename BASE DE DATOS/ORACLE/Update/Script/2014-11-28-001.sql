DELETE FROM Iniciativa_objeto where iniciativa_id in (SELECT iniciativa_id from iniciativa where indicador_id is null);
DELETE FROM Iniciativa_Por_Perspectiva where iniciativa_id in (SELECT iniciativa_id from iniciativa where indicador_id is null);
DELETE FROM INICIATIVA WHERE indicador_id is null;

DECLARE
	vCount NUMBER(2);

BEGIN
	SELECT COUNT(*) INTO vCount FROM dba_tables WHERE table_name = 'indicador_por_iniciativa';
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE indicador_por_iniciativa
							(
								iniciativa_id        NUMBER(10) NOT NULL ,
								indicador_id         NUMBER(10) NOT NULL,
								tipo         		 NUMBER(1) NOT NULL 
							)';
							
		execute immediate 'CREATE UNIQUE INDEX AK_indicador_por_iniciativa ON indicador_por_iniciativa (iniciativa_id   ASC,indicador_id   ASC)';
		execute immediate 'ALTER TABLE indicador_por_iniciativa ADD CONSTRAINT  PK_indicador_por_iniciativa PRIMARY KEY (iniciativa_id,indicador_id)';						
		execute immediate 'CREATE  INDEX IE_ind_por_ini_iniciativaId ON indicador_por_iniciativa (iniciativa_id   ASC)';
		execute immediate 'CREATE  INDEX IE_ind_por_ini_indicadorId ON indicador_por_iniciativa (indicador_id   ASC)';
		execute immediate 'ALTER TABLE indicador_por_iniciativa ADD (CONSTRAINT FK_ind_por_ini_IniciativaId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE)';
		execute immediate 'ALTER TABLE indicador_por_iniciativa ADD (CONSTRAINT FK_ind_por_ini_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
		execute immediate 'INSERT INTO indicador_por_iniciativa (iniciativa_id, indicador_id, tipo) SELECT iniciativa_id, indicador_id, 1 FROM iniciativa';
		
		SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('INICIATIVA') AND INDEX_NAME = UPPER('IE_INICIATIVA_INDICADOR');
		IF vCount > 0 THEN 
		BEGIN
			execute immediate 'DROP INDEX IE_INICIATIVA_INDICADOR';
		END;
		END IF;

		SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('INICIATIVA') AND CONSTRAINT_NAME = UPPER('fk_indicador_iniciativa');
		IF vCount > 0 THEN 
		BEGIN
			execute immediate 'ALTER TABLE INICIATIVA DROP CONSTRAINT fk_indicador_iniciativa';
		END;
		END IF;
		
		SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = 'INDICADOR_ID' AND TABLE_NAME = 'INICIATIVA';
		IF vCount > 0 THEN 
		BEGIN
			execute immediate 'ALTER TABLE INICIATIVA DROP COLUMN INDICADOR_ID';
		END;
		END IF;				
	END;
	END IF;
END;
/

COMMIT;

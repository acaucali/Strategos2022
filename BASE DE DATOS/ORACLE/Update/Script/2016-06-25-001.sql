DECLARE
	vCount NUMBER(5);

BEGIN

	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('iniciativa_estatus');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE iniciativa_estatus (id NUMBER(10) NOT NULL, nombre VARCHAR(50) NOT NULL, porcentaje_inicial FLOAT NULL, porcentaje_final FLOAT NULL, sistema NUMBER(1) NOT NULL, bloquear_medicion NUMBER(1) NOT NULL, bloquear_indicadores NUMBER(1) NOT NULL)';
		execute immediate 'CREATE UNIQUE INDEX IE_Iniciativa_Estatus ON iniciativa_estatus (id   ASC)';
		execute immediate 'CREATE UNIQUE INDEX AK_iniciativa_estatus ON iniciativa_estatus (nombre   ASC)';
		execute immediate 'ALTER TABLE iniciativa_estatus ADD CONSTRAINT  PK_Iniciativa_Estatus PRIMARY KEY (id)';
		execute immediate 'ALTER TABLE iniciativa_estatus ADD CONSTRAINT  PK_iniciativa_estatus_nombre UNIQUE (nombre)';
		
		execute immediate 'INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (1, ''Sin Iniciar'', 0, 0, 1, 0, 0)';
		execute immediate 'INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (2, ''En Ejecucion'', 0.01, 99.99, 0, 0, 0)';
		execute immediate 'INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (3, ''Cancelado'', NULL, NULL, 1, 1, 1)';
		execute immediate 'INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (4, ''Suspendido'', NULL, NULL, 1, 1, 1)';
		execute immediate 'INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (5, ''Culminado'', 100, 100, 1, 1, 0)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('estatusId') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa ADD estatusId NUMBER(10) NULL';
		
		execute immediate 'UPDATE iniciativa SET estatusId = 1 WHERE porcentaje_completado = 0 OR porcentaje_completado IS NULL';
		execute immediate 'UPDATE iniciativa SET estatusId = 5 WHERE porcentaje_completado = 100';
		execute immediate 'UPDATE iniciativa SET estatusId = 2 WHERE estatusId IS NULL';
		
		execute immediate 'ALTER TABLE iniciativa MODIFY estatusId NUMBER(10) NOT NULL';
		execute immediate 'CREATE  INDEX IE_iniciativa_estatusId ON iniciativa (estatusId   ASC)';
		execute immediate 'ALTER TABLE iniciativa ADD (CONSTRAINT PK_Iniciativa_EstatusId FOREIGN KEY (estatusId) REFERENCES iniciativa_estatus (id) ON DELETE CASCADE)';
	END;
	END IF;			
END;
/

UPDATE afw_sistema set actual = '8.01-160625';

COMMIT;

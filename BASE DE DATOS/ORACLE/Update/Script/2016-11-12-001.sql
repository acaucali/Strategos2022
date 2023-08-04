DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('tipo') AND TABLE_NAME = UPPER('responsable') AND NULLABLE = 'Y';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'UPDATE responsable SET tipo = 0 WHERE tipo IS NULL';
		execute immediate 'ALTER TABLE responsable MODIFY tipo NUMERIC(1) NOT NULL';	
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('grupo') AND TABLE_NAME = UPPER('responsable') AND NULLABLE = 'Y';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'UPDATE responsable SET grupo = 0 WHERE grupo IS NULL';
		execute immediate 'ALTER TABLE responsable MODIFY grupo NUMERIC(1) NOT NULL';	
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-161112';

COMMIT;

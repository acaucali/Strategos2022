DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('nombre') AND TABLE_NAME = UPPER('clase') AND data_length = 310;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE clase MODIFY nombre varchar(310)';	
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-161027';

COMMIT;

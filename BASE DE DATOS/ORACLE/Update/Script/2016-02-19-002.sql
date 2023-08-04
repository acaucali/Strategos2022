DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('u_id') AND TABLE_NAME = UPPER('afw_usuario') AND NULLABLE = 'Y';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_usuario MODIFY u_id varchar(50) NOT NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('u_id') AND TABLE_NAME = UPPER('afw_usuario') AND NULLABLE = 'N';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_usuario MODIFY u_id varchar(50)';
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-160219';

COMMIT;

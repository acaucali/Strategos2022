DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('actual') AND TABLE_NAME = UPPER('afw_sistema');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_sistema ADD actual varchar(50) NULL';
	END;
	END IF;				
END;
/
COMMIT;

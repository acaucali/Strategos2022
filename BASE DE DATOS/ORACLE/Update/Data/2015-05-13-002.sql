DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('crecimiento') AND TABLE_NAME = UPPER('perspectiva');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva DROP COLUMN crecimiento';
	END;
	END IF;				
	
END;
/

COMMIT;

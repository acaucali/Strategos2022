DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('crecimiento_parcial') AND TABLE_NAME = UPPER('perspectiva');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva ADD crecimiento_parcial NUMBER(1) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('crecimiento_anual') AND TABLE_NAME = UPPER('perspectiva');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva ADD crecimiento_anual NUMBER(1) NULL';
	END;
	END IF;				
	
END;
/

COMMIT;

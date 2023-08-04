DECLARE
	vCount NUMBER(5);

BEGIN

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado_id') AND TABLE_NAME = UPPER('explicacion');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE explicacion DROP COLUMN modificado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado') AND TABLE_NAME = UPPER('explicacion');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE explicacion DROP COLUMN modificado';
	END;
	END IF;				

END;
/

COMMIT;

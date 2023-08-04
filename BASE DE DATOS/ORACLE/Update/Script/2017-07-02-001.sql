DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM DBA_TABLES WHERE TABLE_NAME = UPPER('medicion_bloqueo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE medicion_bloqueo';
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-170702';

COMMIT;

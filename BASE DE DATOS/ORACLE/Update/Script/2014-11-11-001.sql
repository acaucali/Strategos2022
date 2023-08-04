DECLARE
	vCount NUMBER(2);

BEGIN
	SELECT COUNT(*) INTO vCount FROM dba_tables WHERE table_name = 'VECTOR_MODELO_7';
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE VECTOR_MODELO_7';
	END;
	END IF;
END;
/

UPDATE afw_sistema SET build = '141111';

COMMIT;

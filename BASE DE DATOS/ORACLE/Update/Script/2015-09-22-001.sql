DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM DBA_TABLES WHERE TABLE_NAME = UPPER('grafico_memo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE grafico_memo';
	END;
	END IF;
	
END;
/

COMMIT;

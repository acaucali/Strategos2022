DECLARE
	vCount NUMBER(5);

BEGIN

	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('objetos_calcular');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE objetos_calcular (Id RAW(32) NOT NULL, Objeto_Id NUMBER(10) NOT NULL, Usuario_Id NUMBER(10) NOT NULL, Calculado NUMBER(1) NULL)';
	END;
	END IF;

END;
/

UPDATE afw_sistema set actual = '8.01-160721';

COMMIT;

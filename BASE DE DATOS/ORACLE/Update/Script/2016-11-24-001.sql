DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM afw_transaccion;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE transaccion_indicador';
		execute immediate 'DROP TABLE afw_transaccion';	
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-161124';

COMMIT;

DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('fecha_ultimo_calculo') AND TABLE_NAME = UPPER('portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE portafolio ADD fecha_ultimo_calculo VARCHAR2(10) NULL';
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-160924';

COMMIT;

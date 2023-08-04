DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('publico') AND TABLE_NAME = UPPER('explicacion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE explicacion ADD publico NUMBER(1) NULL';
		execute immediate 'UPDATE explicacion SET publico = 1';
		execute immediate 'ALTER TABLE explicacion MODIFY publico NUMBER(1) NOT NULL';
	END;
	END IF;					
END;
/

UPDATE afw_sistema set actual = '8.01-170912';

COMMIT;

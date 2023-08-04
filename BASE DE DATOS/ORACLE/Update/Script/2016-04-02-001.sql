DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('valor') AND TABLE_NAME = UPPER('afw_auditoria_memo') AND NULLABLE = 'Y';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_auditoria_memo MODIFY valor varchar(2000)';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('valor') AND TABLE_NAME = UPPER('afw_auditoria_memo') AND NULLABLE = 'N';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_auditoria_memo MODIFY valor varchar(2000) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('valor_anterior') AND TABLE_NAME = UPPER('afw_auditoria_memo') AND NULLABLE = 'N';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_auditoria_memo MODIFY valor_anterior varchar(2000) NULL';
	END;
	END IF;				
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('valor_anterior') AND TABLE_NAME = UPPER('afw_auditoria_memo') AND NULLABLE = 'Y';
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_auditoria_memo MODIFY valor_anterior varchar(2000)';
	END;
	END IF;				
	
END;
/

UPDATE afw_sistema set actual = '8.01-160402';

COMMIT;

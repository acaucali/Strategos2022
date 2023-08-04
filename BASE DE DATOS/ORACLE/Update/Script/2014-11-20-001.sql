DECLARE
	vCount NUMBER(2);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = 'CONEXION' AND TABLE_NAME = 'AFW_SISTEMA';
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE AFW_SISTEMA ADD CONEXION VARCHAR2(50) NULL';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM dba_tables WHERE table_name = 'AFW_LOG';
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE afw_log 
						(fecha DATE NULL, 
						objeto_id NUMBER(10) NULL, 
						usuario_id NUMBER(10) NULL, 
						nombre VARCHAR2(50) NULL, 
						descripcion VARCHAR2(2000) NULL, 
						categoria NUMBER(2) NULL)';
	END;
	END IF;
END;
/

COMMIT;

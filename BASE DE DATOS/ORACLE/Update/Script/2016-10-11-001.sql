DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM DBA_TABLES WHERE TABLE_NAME = UPPER('indicador_por_reporte');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE indicador_por_reporte';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('perspectiva_relacionada') AND CONSTRAINT_NAME = UPPER('pk_perspectiva_relacionada');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva_relacionada DROP CONSTRAINT pk_perspectiva_relacionada';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('perspectiva_relacionada') AND CONSTRAINT_NAME = UPPER('pk_perspectiva_persrelacionada');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE perspectiva_relacionada DROP CONSTRAINT pk_perspectiva_persrelacionada';
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-161011';

COMMIT;

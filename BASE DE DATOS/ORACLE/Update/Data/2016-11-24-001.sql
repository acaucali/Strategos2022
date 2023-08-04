DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('indicador_por_portafolio');
	IF vCount = 0 THEN 
	BEGIN
		DELETE FROM afw_permiso WHERE permiso_id LIKE '%INDICADOR_MEDICION_TRANSACCION%';
	END;
	END IF;				
END;
/

UPDATE afw_sistema set actual = '8.01-161124';

COMMIT;

DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('tipo_medicion') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa ADD tipo_medicion NUMBER(1) NULL';
	END;
	END IF;				
END;
/

COMMIT;

DECLARE
	vTipo pry_actividad.tipo_medicion%TYPE;
	vProyecto pry_actividad.proyecto_id%TYPE;
	
	CURSOR curItems IS SELECT pry_actividad.tipo_medicion,  pry_actividad.proyecto_id FROM pry_actividad, iniciativa WHERE iniciativa.proyecto_id = pry_actividad.proyecto_id AND iniciativa.tipo_medicion IS NULL AND rownum = 1;

BEGIN
  OPEN curItems;
  LOOP  
    FETCH curItems INTO vTipo, vProyecto;
    EXIT WHEN curItems%NOTFOUND;
		UPDATE iniciativa SET tipo_medicion = vTipo WHERE proyecto_id = vProyecto;
  END LOOP;
  CLOSE curItems;
END;
/

COMMIT;

UPDATE Iniciativa SET Tipo_Medicion = 0 WHERE Tipo_Medicion IS NULL;
DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('tipo_medicion') AND TABLE_NAME = UPPER('iniciativa');
	IF vCount <> 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa MODIFY tipo_medicion NUMBER(1) NOT NULL';
	END;
	END IF;				
END;
/

COMMIT;

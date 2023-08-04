DECLARE
	vId ALL_IND_COLUMNS.INDEX_NAME%TYPE;
	vCount NUMBER(5);
	
	CURSOR curItems IS SELECT INDEX_NAME FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pry_actividad') AND INDEX_NAME <> UPPER('pk_pry_actividad');

BEGIN
  OPEN curItems;
  LOOP  
    FETCH curItems INTO vId;
    EXIT WHEN curItems%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pry_actividad') AND INDEX_NAME = UPPER(vId);
		IF vCount > 0 THEN 
		BEGIN
			execute immediate 'DROP INDEX '||vId;
		END;
		END IF;
  END LOOP;
  CLOSE curItems;
END;
/

CREATE INDEX IE_pry_actividad_RespEjecutado ON pry_actividad (resp_cargar_ejecutado_id  ASC);
CREATE INDEX IE_pry_actividad_RespMeta ON pry_actividad (resp_cargar_meta_id   ASC);
CREATE INDEX IE_pry_actividad_Proyecto ON pry_actividad (proyecto_id   ASC);
CREATE INDEX IE_pry_actividad_Padre ON pry_actividad (padre_id   ASC);
CREATE INDEX IE_pry_actividad_Unidad ON pry_actividad (unidad_id   ASC);
CREATE INDEX IE_pry_actividad_Indicador ON pry_actividad (indicador_id   ASC);
CREATE INDEX IE_pry_actividad_Clase ON pry_actividad (clase_id   ASC);

COMMIT;

DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_actividad') AND CONSTRAINT_NAME = UPPER('FK_indicador_actividad');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_actividad ADD CONSTRAINT FK_indicador_actividad FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_actividad') AND CONSTRAINT_NAME = UPPER('FK_clase_actividad');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_actividad ADD CONSTRAINT FK_clase_actividad FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE';
	END;
	END IF;
	
END;
/

COMMIT;

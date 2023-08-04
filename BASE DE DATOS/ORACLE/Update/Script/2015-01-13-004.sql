DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('XIF10INDICADOR') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX XIF10INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('XIF1INDICADOR') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX XIF1INDICADOR';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('XIF2INDICADOR') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX XIF2INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('XIF3INDICADOR') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX XIF3INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('IE_INDICADOR_CLASEID') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX IE_INDICADOR_CLASEID';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('AK1_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT AK1_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('AK1_INDICADOR') ;
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX AK1_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPEJECUTADO_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPEJECUTADO_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPCARGARMETA_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPCARGARMETA_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPSEGUI_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPSEGUI_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPLOGRARMETA_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPLOGRARMETA_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPFIJARMETA_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPFIJARMETA_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_CLASE_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_CLASE_INDICADOR';
	END;
	END IF;
		
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado') AND TABLE_NAME = UPPER('indicador');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP COLUMN creado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado') AND TABLE_NAME = UPPER('indicador');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP COLUMN modificado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado_id') AND TABLE_NAME = UPPER('indicador');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP COLUMN creado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado_id') AND TABLE_NAME = UPPER('indicador');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP COLUMN modificado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('pk_indicador') ;
	IF vCount = 0 THEN 
	BEGIN
		SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ak_indicador') ;
		IF vCount = 0 THEN 
		BEGIN
			execute immediate 'CREATE UNIQUE INDEX ak_indicador ON indicador (indicador_id   ASC)';
		END;
		END IF;
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('pk_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD CONSTRAINT  pk_indicador PRIMARY KEY (indicador_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ak_indicador_clase_nombre') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ak_indicador_clase_nombre ON indicador (clase_id   ASC,nombre   ASC)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('pk_indicador_clase_nombre');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD CONSTRAINT  pk_indicador_clase_nombre UNIQUE (clase_id, nombre)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_respfijarmeta') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_respfijarmeta ON indicador (resp_fijar_meta_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_resplograrmeta') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_resplograrmeta ON indicador (resp_lograr_meta_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_resseguimiento') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_resseguimiento ON indicador (resp_seguimiento_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_rescargameta') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_rescargameta ON indicador (resp_cargar_meta_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_resejecutado') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_resejecutado ON indicador (resp_cargar_ejecutado_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_claseid') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_claseid ON indicador (clase_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_organizacion') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_organizacion ON indicador (organizacion_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('indicador') AND INDEX_NAME = UPPER('ie_indicador_unidad') ;
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_indicador_unidad ON indicador (unidad_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_respfijarmeta_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_resplograrmeta_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_respsegui_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_respcargarmeta_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_respejecutado_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_clase_indicador');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_clase_indicador FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_indicador_organizacion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_indicador_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('fk_indicador_unidad');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador ADD (CONSTRAINT fk_indicador_unidad FOREIGN KEY (unidad_id) REFERENCES unidad (unidad_id) ON DELETE SET NULL)';
	END;
	END IF;
	
END;
/

COMMIT;

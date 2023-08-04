DECLARE
	vCount NUMBER(5);

BEGIN
	execute immediate 'DELETE FROM Medicion WHERE indicador_id not in (SELECT Indicador_id from indicador)';
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('MEDICION') AND CONSTRAINT_NAME = UPPER('FK_INDICADOR_MEDICION');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE MEDICION DROP CONSTRAINT FK_INDICADOR_MEDICION';
		execute immediate 'ALTER TABLE medicion ADD (CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
	END;
	END IF;
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('MEDICION') AND CONSTRAINT_NAME = UPPER('FK_INDICADOR_MEDICION');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE medicion ADD (CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
	END;
	END IF;

	execute immediate 'DELETE FROM pry_calendario WHERE proyecto_id not in (SELECT proyecto_id from pry_proyecto) AND proyecto_id IS NOT NULL';
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_calendario') AND CONSTRAINT_NAME = UPPER('FK_PRY_PROYECTO_CALENDARIO');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_calendario DROP CONSTRAINT FK_PRY_PROYECTO_CALENDARIO';
		execute immediate 'ALTER TABLE pry_calendario ADD (CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE CASCADE)';
	END;
	END IF;
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_calendario') AND CONSTRAINT_NAME = UPPER('FK_PRY_PROYECTO_CALENDARIO');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_calendario ADD (CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE CASCADE)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('INDICADOR') AND CONSTRAINT_NAME = UPPER('FK_ORGANIZACION_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE INDICADOR DROP CONSTRAINT FK_ORGANIZACION_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('ORGANIZACION_MEMO') AND CONSTRAINT_NAME = UPPER('FK_ORGANIZACION_MEMO');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE ORGANIZACION_MEMO DROP CONSTRAINT FK_ORGANIZACION_MEMO';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('afw_usuario_grupo') AND CONSTRAINT_NAME = UPPER('FK_ORGANIZACION_USUARIO_GRUPO');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE afw_usuario_grupo DROP CONSTRAINT FK_ORGANIZACION_USUARIO_GRUPO';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('clase') AND CONSTRAINT_NAME = UPPER('FK_ORGANIZACION_CLASE');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE clase DROP CONSTRAINT FK_ORGANIZACION_CLASE';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador_por_perspectiva') AND CONSTRAINT_NAME = UPPER('FK_PERSPECTIVA_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador_por_perspectiva DROP CONSTRAINT FK_PERSPECTIVA_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('Clase_problema') AND CONSTRAINT_NAME = UPPER('FK_ORGANIZACION_CLASE_PROBLEMA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE Clase_problema DROP CONSTRAINT FK_ORGANIZACION_CLASE_PROBLEMA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('accion') AND CONSTRAINT_NAME = UPPER('FK_PROBLEMA_ACCION');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE accion DROP CONSTRAINT FK_PROBLEMA_ACCION';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('problema_memo') AND CONSTRAINT_NAME = UPPER('FK_PROBLEMA_MEMO');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE problema_memo DROP CONSTRAINT FK_PROBLEMA_MEMO';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPONSABLE_INDICADOR_1');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_1';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPONSABLE_INDICADOR_2');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_2';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador') AND CONSTRAINT_NAME = UPPER('FK_RESPONSABLE_INDICADOR_3');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_3';
	END;
	END IF;	

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('RESPONSABLE_GRUPO') AND CONSTRAINT_NAME = UPPER('FK_RESPONSABLE_GRUPO');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE RESPONSABLE_GRUPO DROP CONSTRAINT FK_RESPONSABLE_GRUPO';
	END;
	END IF;	
	
	execute immediate 'UPDATE indicador SET resp_fijar_meta_id = null where resp_fijar_meta_id not in (SELECT responsable_id From responsable)';
	execute immediate 'UPDATE indicador SET resp_lograr_meta_id = null where resp_lograr_meta_id not in (SELECT responsable_id From responsable)';
	execute immediate 'UPDATE indicador SET resp_seguimiento_id = null where resp_seguimiento_id not in (SELECT responsable_id From responsable)';
	execute immediate 'UPDATE indicador SET resp_cargar_meta_id = null where resp_cargar_meta_id not in (SELECT responsable_id From responsable)';
	execute immediate 'UPDATE indicador SET resp_cargar_ejecutado_id = null where resp_cargar_ejecutado_id not in (SELECT responsable_id From responsable)';
	execute immediate 'DELETE from responsable_grupo WHERE responsable_id not in (SELECT responsable_id From responsable)';

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable_grupo') AND CONSTRAINT_NAME = UPPER('FK_RESP_RESPONSABLE_GRUPO');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable_grupo ADD (CONSTRAINT FK_RESP_RESPONSABLE_GRUPO FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE CASCADE)';
	END;
	END IF;	
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('grafico') AND CONSTRAINT_NAME = UPPER('fk_organizacion_grafico');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE grafico DROP CONSTRAINT fk_organizacion_grafico';
		execute immediate 'ALTER TABLE grafico ADD (CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
	END;
	END IF;
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('grafico') AND CONSTRAINT_NAME = UPPER('fk_organizacion_grafico');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE grafico ADD (CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
	END;
	END IF;
END;
/

UPDATE afw_sistema set actual = '8.01-170920';

COMMIT;

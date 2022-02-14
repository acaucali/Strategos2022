CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	execute 'DELETE FROM Medicion WHERE indicador_id not in (SELECT Indicador_id from indicador)';
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('MEDICION') AND constraint_name = LOWER('FK_INDICADOR_MEDICION'))
	THEN
		execute 'ALTER TABLE MEDICION DROP CONSTRAINT FK_INDICADOR_MEDICION';
		execute 'ALTER TABLE medicion ADD CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('MEDICION') AND constraint_name = LOWER('FK_INDICADOR_MEDICION'))
	THEN
		execute 'ALTER TABLE medicion ADD CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	

	execute 'DELETE FROM pry_calendario WHERE proyecto_id not in (SELECT proyecto_id from pry_proyecto) AND proyecto_id IS NOT NULL';
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pry_calendario') AND constraint_name = LOWER('FK_PRY_PROYECTO_CALENDARIO'))
	THEN
		execute 'ALTER TABLE pry_calendario DROP CONSTRAINT FK_PRY_PROYECTO_CALENDARIO';
		execute 'ALTER TABLE pry_calendario ADD CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pry_calendario') AND constraint_name = LOWER('FK_PRY_PROYECTO_CALENDARIO'))
	THEN
		execute 'ALTER TABLE pry_calendario ADD CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('INDICADOR') AND constraint_name = LOWER('FK_ORGANIZACION_INDICADOR'))
	THEN
		execute 'ALTER TABLE INDICADOR DROP CONSTRAINT FK_ORGANIZACION_INDICADOR';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('ORGANIZACION_MEMO') AND constraint_name = LOWER('FK_ORGANIZACION_MEMO'))
	THEN
		execute 'ALTER TABLE ORGANIZACION_MEMO DROP CONSTRAINT FK_ORGANIZACION_MEMO';
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('afw_usuario_grupo') AND constraint_name = LOWER('FK_ORGANIZACION_USUARIO_GRUPO'))
	THEN
		execute 'ALTER TABLE afw_usuario_grupo DROP CONSTRAINT FK_ORGANIZACION_USUARIO_GRUPO';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('clase') AND constraint_name = LOWER('FK_ORGANIZACION_CLASE'))
	THEN
		execute 'ALTER TABLE clase DROP CONSTRAINT FK_ORGANIZACION_CLASE';
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador_por_perspectiva') AND constraint_name = LOWER('FK_PERSPECTIVA_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador_por_perspectiva DROP CONSTRAINT FK_PERSPECTIVA_INDICADOR';
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('Clase_problema') AND constraint_name = LOWER('FK_ORGANIZACION_CLASE_PROBLEMA'))
	THEN
		execute 'ALTER TABLE Clase_problema DROP CONSTRAINT FK_ORGANIZACION_CLASE_PROBLEMA';
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('accion') AND constraint_name = LOWER('FK_PROBLEMA_ACCION'))
	THEN
		execute 'ALTER TABLE accion DROP CONSTRAINT FK_PROBLEMA_ACCION';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('problema_memo') AND constraint_name = LOWER('FK_PROBLEMA_MEMO'))
	THEN
		execute 'ALTER TABLE problema_memo DROP CONSTRAINT FK_PROBLEMA_MEMO';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPONSABLE_INDICADOR_1'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_1';
	END IF;	
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPONSABLE_INDICADOR_2'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_2';
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPONSABLE_INDICADOR_3'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPONSABLE_INDICADOR_3';
	END IF;	
	
	execute 'UPDATE indicador SET resp_fijar_meta_id = null where resp_fijar_meta_id not in (SELECT responsable_id From responsable)';
	execute 'UPDATE indicador SET resp_lograr_meta_id = null where resp_lograr_meta_id not in (SELECT responsable_id From responsable)';
	execute 'UPDATE indicador SET resp_seguimiento_id = null where resp_seguimiento_id not in (SELECT responsable_id From responsable)';
	execute 'UPDATE indicador SET resp_cargar_meta_id = null where resp_cargar_meta_id not in (SELECT responsable_id From responsable)';
	execute 'UPDATE indicador SET resp_cargar_ejecutado_id = null where resp_cargar_ejecutado_id not in (SELECT responsable_id From responsable)';
	execute 'DELETE from responsable_grupo WHERE responsable_id not in (SELECT responsable_id From responsable)';

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('responsable_grupo') AND constraint_name = LOWER('FK_RESP_RESPONSABLE_GRUPO'))
	THEN
		execute 'ALTER TABLE responsable_grupo ADD CONSTRAINT FK_RESP_RESPONSABLE_GRUPO FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;		
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('grafico') AND constraint_name = LOWER('fk_organizacion_grafico'))
	THEN
		execute 'ALTER TABLE grafico DROP CONSTRAINT fk_organizacion_grafico';
		execute 'ALTER TABLE grafico ADD CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('grafico') AND constraint_name = LOWER('fk_organizacion_grafico'))
	THEN
		execute 'ALTER TABLE grafico ADD CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;		
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170920';

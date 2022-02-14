CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('XIF10INDICADOR'))
	THEN
		execute 'DROP INDEX XIF10INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('XIF1INDICADOR'))
	THEN
		execute 'DROP INDEX XIF1INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('XIF2INDICADOR'))
	THEN
		execute 'DROP INDEX XIF2INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('XIF3INDICADOR'))
	THEN
		execute 'DROP INDEX XIF3INDICADOR';
	END IF;
	
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('IE_INDICADOR_CLASEID'))
	THEN
		execute 'DROP INDEX IE_INDICADOR_CLASEID';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('AK1_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT AK1_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('AK1_INDICADOR'))
	THEN
		execute 'DROP INDEX AK1_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPEJECUTADO_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPEJECUTADO_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPCARGARMETA_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPCARGARMETA_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPSEGUI_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPSEGUI_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPLOGRARMETA_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPLOGRARMETA_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_RESPFIJARMETA_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_RESPFIJARMETA_INDICADOR';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('FK_CLASE_INDICADOR'))
	THEN
		execute 'ALTER TABLE indicador DROP CONSTRAINT FK_CLASE_INDICADOR';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('indicador') AND column_name = LOWER('creado'))	
	THEN
		execute 'ALTER TABLE indicador DROP COLUMN creado';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('indicador') AND column_name = LOWER('modificado'))	
	THEN
		execute 'ALTER TABLE indicador DROP COLUMN modificado';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('indicador') AND column_name = LOWER('creado_id'))	
	THEN
		execute 'ALTER TABLE indicador DROP COLUMN creado_id';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('indicador') AND column_name = LOWER('modificado_id'))	
	THEN
		execute 'ALTER TABLE indicador DROP COLUMN modificado_id';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('pk_indicador'))
	THEN
		IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ak_indicador'))
		THEN
			execute 'CREATE UNIQUE INDEX ak_indicador ON indicador (indicador_id   ASC)';
		END IF;
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('pk_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT  pk_indicador PRIMARY KEY (indicador_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ak_indicador_clase_nombre'))
	THEN
		execute 'CREATE UNIQUE INDEX ak_indicador_clase_nombre ON indicador (clase_id   ASC,nombre   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('pk_indicador_clase_nombre'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT  pk_indicador_clase_nombre UNIQUE (clase_id, nombre)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_respfijarmeta'))
	THEN
		execute 'CREATE  INDEX ie_indicador_respfijarmeta ON indicador (resp_fijar_meta_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_resplograrmeta'))
	THEN
		execute 'CREATE  INDEX ie_indicador_resplograrmeta ON indicador (resp_lograr_meta_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_resseguimiento'))
	THEN
		execute 'CREATE  INDEX ie_indicador_resseguimiento ON indicador (resp_seguimiento_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_rescargameta'))
	THEN
		execute 'CREATE  INDEX ie_indicador_rescargameta ON indicador (resp_cargar_meta_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_resejecutado'))
	THEN
		execute 'CREATE  INDEX ie_indicador_resejecutado ON indicador (resp_cargar_ejecutado_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_claseid'))
	THEN
		execute 'CREATE  INDEX ie_indicador_claseid ON indicador (clase_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_organizacion'))
	THEN
		execute 'CREATE  INDEX ie_indicador_organizacion ON indicador (organizacion_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('indicador') AND indexrelname = LOWER('ie_indicador_unidad'))
	THEN
		execute 'CREATE  INDEX ie_indicador_unidad ON indicador (unidad_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_respfijarmeta_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_resplograrmeta_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_respsegui_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_respcargarmeta_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_respejecutado_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_clase_indicador'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_clase_indicador FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_indicador_organizacion'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_indicador_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('indicador') AND constraint_name = LOWER('fk_indicador_unidad'))
	THEN
		execute 'ALTER TABLE indicador ADD CONSTRAINT fk_indicador_unidad FOREIGN KEY (unidad_id) REFERENCES unidad (unidad_id) ON DELETE SET NULL';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

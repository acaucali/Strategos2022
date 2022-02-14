CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('pagina') AND column_name = LOWER('vista_id'))
	THEN
		execute 'ALTER TABLE pagina ALTER COLUMN vista_id DROP NOT NULL';
	END IF;		

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('perspectiva_nivel') AND column_name = LOWER('alerta'))
	THEN
		execute 'ALTER TABLE perspectiva_nivel ADD alerta numeric(1) NULL';
	END IF;					

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('perspectiva_nivel') AND column_name = LOWER('meta'))
	THEN
		execute 'ALTER TABLE perspectiva_nivel ADD meta double precision NULL';
	END IF;					
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('perspectiva_nivel') AND indexrelname = LOWER('IE_perspectiva_nivel'))
	THEN
		execute 'CREATE  INDEX IE_perspectiva_nivel ON perspectiva_nivel (perspectiva_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('perspectiva_nivel') AND constraint_name = LOWER('FK_perspectiva_perspectiva_niv'))
	THEN
		execute 'ALTER TABLE perspectiva_nivel ADD CONSTRAINT FK_perspectiva_perspectiva_niv FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;	
	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('EXPLICACION') AND constraint_name = LOWER('FK_usuario_explicacion'))
	THEN
	    execute 'UPDATE EXPLICACION SET creado_id = (SELECT usuario_id FROM afw_usuario WHERE u_id = ''admin'') WHERE creado_id NOT IN (SELECT usuario_id FROM afw_usuario)';
		execute 'ALTER TABLE EXPLICACION ADD CONSTRAINT FK_usuario_explicacion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;		
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170325';

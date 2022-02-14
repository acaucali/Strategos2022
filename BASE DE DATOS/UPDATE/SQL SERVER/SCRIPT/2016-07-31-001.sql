CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'portafolio') 
	THEN
		CREATE TABLE portafolio
		(
			organizacion_id      NUMERIC(10, 0) NOT NULL ,
			id                   NUMERIC(10, 0) NOT NULL ,
			nombre               character varying(50) NOT NULL ,
			activo               NUMERIC(1, 0) NOT NULL ,
			porcentaje_completado double precision NULL ,
			estatusid            NUMERIC(10, 0) NULL 
		);

		CREATE UNIQUE INDEX PK_Portafolio ON portafolio USING btree (id   ASC);
		ALTER TABLE portafolio ADD CONSTRAINT  PK_PortafolioId PRIMARY KEY (id);
		CREATE UNIQUE INDEX AK_Portafolio ON portafolio USING btree (organizacion_id   ASC,nombre   ASC);
		ALTER TABLE portafolio ADD CONSTRAINT  AK_Portafolio_Nombre UNIQUE (organizacion_id,nombre);
		CREATE  INDEX IE_Portafolio_OrganizacionId ON portafolio USING btree (organizacion_id   ASC);
		CREATE  INDEX IE_portafolio_estatus ON portafolio USING btree (estatusid   ASC);
		ALTER TABLE portafolio ADD CONSTRAINT FK_ORG_PORTAFOLIO FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;
		ALTER TABLE portafolio ADD CONSTRAINT FK_estatus_portafolio FOREIGN KEY (estatusid) REFERENCES iniciativa_estatus (id) ON UPDATE NO ACTION ON DELETE SET NULL;		
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'portafolio_iniciativa') 
	THEN
		CREATE TABLE portafolio_iniciativa
		(
			portafolio_id        NUMERIC(10, 0) NOT NULL ,
			iniciativa_id        NUMERIC(10, 0) NOT NULL 
		);

		CREATE UNIQUE INDEX PK_portafolio_iniciativa ON portafolio_iniciativa USING btree (portafolio_id   ASC,iniciativa_id   ASC);
		ALTER TABLE portafolio_iniciativa ADD CONSTRAINT PK_portafolio_iniciativa_portId PRIMARY KEY (portafolio_id,iniciativa_id);
		CREATE  INDEX IE_portafolio_iniciativa_porta ON portafolio_iniciativa USING btree (portafolio_id   ASC);
		CREATE  INDEX IE_portafolio_iniciativa_inici ON portafolio_iniciativa USING btree (iniciativa_id   ASC);
		ALTER TABLE portafolio_iniciativa ADD CONSTRAINT FK_portafolio_portafolioId FOREIGN KEY (portafolio_id) REFERENCES portafolio (id) ON UPDATE NO ACTION ON DELETE CASCADE;
		ALTER TABLE portafolio_iniciativa ADD CONSTRAINT FK_portafolio_iniciativa_iniId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON UPDATE NO ACTION ON DELETE CASCADE;
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pagina') AND constraint_name = LOWER('fk_vista_pagina'))
	THEN
		execute 'ALTER TABLE pagina DROP CONSTRAINT fk_vista_pagina';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('vista') AND constraint_name = LOWER('pk_vista'))
	THEN
		execute 'ALTER TABLE vista DROP CONSTRAINT pk_vista';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('pk_vista'))
	THEN
		execute 'DROP INDEX pk_vista';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('vista') AND constraint_name = LOWER('ak1_vista'))
	THEN
		execute 'ALTER TABLE vista DROP CONSTRAINT ak1_vista';
	END IF;
	
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('ak1_vista'))
	THEN
		execute 'DROP INDEX ak1_vista';
	END IF;
	
	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('xif1vista'))
	THEN
		execute 'DROP INDEX xif1vista';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('ie_vista'))
	THEN
		execute 'CREATE UNIQUE INDEX ie_vista ON vista USING btree (vista_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('ak_vista'))
	THEN
		execute 'CREATE UNIQUE INDEX ak_vista ON vista USING btree (organizacion_id   ASC,nombre   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('vista') AND indexrelname = LOWER('ie_vista_organizacion'))
	THEN
		execute 'CREATE INDEX ie_vista_organizacion ON vista USING btree (organizacion_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('vista') AND constraint_name = LOWER('pk_vista'))
	THEN
		execute 'ALTER TABLE vista ADD CONSTRAINT pk_vista PRIMARY KEY (vista_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('vista') AND constraint_name = LOWER('ak_vista_nombre'))
	THEN
		execute 'ALTER TABLE vista ADD CONSTRAINT ak_vista_nombre UNIQUE (organizacion_id, nombre)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('vista') AND constraint_name = LOWER('FK_organizacion_vista'))
	THEN
		execute 'ALTER TABLE vista ADD CONSTRAINT FK_organizacion_vista FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('pagina') AND column_name = LOWER('vista_id'))	
	THEN
		execute 'ALTER TABLE pagina ALTER COLUMN vista_id DROP NOT NULL';
	END IF;	

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('pagina') AND column_name = LOWER('portafolioId'))	
	THEN
		execute 'ALTER TABLE pagina ADD COLUMN portafolioId numeric(10,0) NULL';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('fk_pagina_celda'))
	THEN
		execute 'ALTER TABLE celda DROP CONSTRAINT fk_pagina_celda';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pagina') AND constraint_name = LOWER('pk_pagina'))
	THEN
		execute 'ALTER TABLE pagina DROP CONSTRAINT pk_pagina';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('pagina') AND indexrelname = LOWER('pk_pagina'))
	THEN
		execute 'DROP INDEX pk_pagina';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('pagina') AND indexrelname = LOWER('xif1pagina'))
	THEN
		execute 'DROP INDEX xif1pagina';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('pagina') AND indexrelname = LOWER('ie_pagina'))
	THEN
		execute 'CREATE UNIQUE INDEX ie_pagina ON pagina USING btree (pagina_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('pagina') AND indexrelname = LOWER('ie_pagina_vista'))
	THEN
		execute 'CREATE INDEX ie_pagina_vista ON pagina USING btree (vista_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('pagina') AND indexrelname = LOWER('ie_pagina_portafolio'))
	THEN
		execute 'CREATE INDEX ie_pagina_portafolio ON pagina USING btree (portafolioId   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pagina') AND constraint_name = LOWER('pk_pagina'))
	THEN
		execute 'ALTER TABLE pagina ADD CONSTRAINT  pk_pagina PRIMARY KEY (pagina_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pagina') AND constraint_name = LOWER('FK_vista_pagina'))
	THEN
		execute 'ALTER TABLE pagina ADD CONSTRAINT FK_vista_pagina FOREIGN KEY (vista_id) REFERENCES vista (vista_id) ON UPDATE NO ACTION ON DELETE NO ACTION';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('pagina') AND constraint_name = LOWER('FK_portafolio_vista'))
	THEN
		execute 'ALTER TABLE pagina ADD CONSTRAINT FK_portafolio_vista FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON DELETE CASCADE';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('celda') AND column_name = LOWER('modificado'))	
	THEN
		execute 'ALTER TABLE celda DROP COLUMN modificado';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('celda') AND column_name = LOWER('creado'))	
	THEN
		execute 'ALTER TABLE celda DROP COLUMN creado';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('celda') AND column_name = LOWER('modificado_id'))	
	THEN
		execute 'ALTER TABLE celda DROP COLUMN modificado_id';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('celda') AND column_name = LOWER('creado_id'))	
	THEN
		execute 'ALTER TABLE celda DROP COLUMN creado_id';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('ak1_celda'))
	THEN
		execute 'ALTER TABLE celda DROP CONSTRAINT ak1_celda';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('explicacion') AND constraint_name = LOWER('fk_celda_explicacion'))
	THEN
		execute 'ALTER TABLE explicacion DROP CONSTRAINT fk_celda_explicacion';
	END IF;

	IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('pk_celda'))
	THEN
		execute 'ALTER TABLE celda DROP CONSTRAINT pk_celda';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('pk_celda'))
	THEN
		execute 'DROP INDEX pk_celda';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('ak1_celda'))
	THEN
		execute 'DROP INDEX ak1_celda';
	END IF;

	IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('xif1celda'))
	THEN
		execute 'DROP INDEX xif1celda';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('ie_celda'))
	THEN
		execute 'CREATE UNIQUE INDEX ie_celda ON celda USING btree (celda_id   ASC)';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('ie_celda_pagina_celda'))
	THEN
		execute 'CREATE UNIQUE INDEX ie_celda_pagina_celda ON celda USING btree (pagina_id   ASC,fila   ASC,columna   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('celda') AND indexrelname = LOWER('ie_celda_pagina'))
	THEN
		execute 'CREATE INDEX ie_celda_pagina ON celda USING btree (pagina_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('pk_celda_pagina_celda'))
	THEN
		execute 'ALTER TABLE celda ADD CONSTRAINT pk_celda_pagina_celda UNIQUE (pagina_id,fila,columna)';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('pk_celda'))
	THEN
		execute 'ALTER TABLE celda ADD CONSTRAINT pk_celda PRIMARY KEY (celda_id)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('celda') AND constraint_name = LOWER('FK_pagina_celda'))
	THEN
		execute 'ALTER TABLE celda ADD CONSTRAINT FK_pagina_celda FOREIGN KEY (pagina_id) REFERENCES pagina (pagina_id) ON UPDATE NO ACTION ON DELETE CASCADE;';
	END IF;

END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160731';

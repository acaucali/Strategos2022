CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'indicador_por_iniciativa') 
	THEN
		CREATE TABLE indicador_por_iniciativa
		(
			iniciativa_id        numeric(10,0) NOT NULL ,
			indicador_id         numeric(10,0) NOT NULL,
			tipo         		 numeric(1, 0) NOT NULL
		);

		CREATE UNIQUE INDEX AK_indicador_por_iniciativa ON indicador_por_iniciativa USING btree (iniciativa_id   ASC,indicador_id   ASC);

		ALTER TABLE indicador_por_iniciativa
			ADD CONSTRAINT  PK_indicador_por_iniciativa PRIMARY KEY (iniciativa_id,indicador_id);
		
		CREATE INDEX IE_ind_por_ini_iniciativaId ON indicador_por_iniciativa USING btree (iniciativa_id   ASC);

		CREATE  INDEX IE_ind_por_ini_indicadorId ON indicador_por_iniciativa USING btree (indicador_id   ASC);

		ALTER TABLE indicador_por_iniciativa
			ADD CONSTRAINT FK_ind_por_ini_IniciativaId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON UPDATE NO ACTION ON DELETE CASCADE;

		ALTER TABLE indicador_por_iniciativa
			ADD CONSTRAINT FK_ind_por_ini_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;		
			
		INSERT INTO indicador_por_iniciativa (iniciativa_id, indicador_id, tipo)
		SELECT iniciativa_id, indicador_id, 1 FROM iniciativa;

		IF EXISTS (SELECT * FROM indicador_por_iniciativa)
		THEN
			IF EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = 'iniciativa' AND constraint_name = 'fk_indicador_iniciativa')
			THEN
				execute 'ALTER TABLE iniciativa DROP CONSTRAINT fk_indicador_iniciativa';
			END IF;

			IF EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = 'iniciativa' AND indexrelname = 'ie_iniciativa_indicador')
			THEN
				execute 'DROP INDEX ie_iniciativa_indicador';
			END IF;
			
			IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'iniciativa' AND column_name = 'indicador_id')
			THEN
				execute 'ALTER TABLE iniciativa DROP COLUMN indicador_id';
			END IF;	
		END IF;	
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

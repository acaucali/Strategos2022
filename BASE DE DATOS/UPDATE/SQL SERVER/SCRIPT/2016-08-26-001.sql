CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'indicador_por_portafolio') 
	THEN
		CREATE TABLE indicador_por_portafolio
		(
			indicador_id         NUMERIC(10, 0) NOT NULL,
			portafolioId         NUMERIC(10, 0) NOT NULL,
			tipo                 NUMERIC(1, 0) NOT NULL 
		);

		CREATE UNIQUE INDEX AK_indicador_por_portafolio ON indicador_por_portafolio USING btree (indicador_id   ASC,portafolioId   ASC);
		ALTER TABLE indicador_por_portafolio ADD CONSTRAINT PK_indicador_por_portafolio PRIMARY KEY (indicador_id,portafolioId);
		CREATE INDEX IE_indicador_por_portafolio_in ON indicador_por_portafolio USING btree (indicador_id   ASC);
		CREATE INDEX IE_indicador_por_portafolio_po ON indicador_por_portafolio USING btree (portafolioId   ASC);
		ALTER TABLE indicador_por_portafolio ADD CONSTRAINT FK_indicador_indXpor FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;
		ALTER TABLE indicador_por_portafolio ADD CONSTRAINT FK_portafolio_indXpor FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON UPDATE NO ACTION ON DELETE CASCADE;
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('portafolio') AND column_name = LOWER('frecuencia'))
	THEN
		execute 'ALTER TABLE portafolio ADD COLUMN frecuencia NUMERIC(1) NULL';
		execute 'UPDATE portafolio SET frecuencia = 3';
		execute 'ALTER TABLE portafolio ALTER COLUMN frecuencia SET NOT NULL';	
	END IF;				

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('portafolio') AND column_name = LOWER('clase_id'))
	THEN
		execute 'ALTER TABLE portafolio ADD COLUMN clase_id NUMERIC(10) NULL';
	END IF;				
	
	IF NOT EXISTS (SELECT * FROM pg_stat_all_indexes WHERE relname = LOWER('portafolio') AND indexrelname = LOWER('IE_portafolio_clase'))
	THEN
		execute 'CREATE INDEX IE_portafolio_clase ON portafolio USING btree (clase_id   ASC)';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE table_name = LOWER('portafolio') AND constraint_name = LOWER('FK_clase_portafolio'))
	THEN
		execute 'ALTER TABLE portafolio ADD CONSTRAINT FK_clase_portafolio FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('portafolio') AND column_name = LOWER('estatusid') AND is_nullable = 'YES')
	THEN
		execute 'UPDATE portafolio SET estatusid = 1';
		execute 'ALTER TABLE portafolio ALTER COLUMN estatusid SET NOT NULL';	
	END IF;	
	
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('portafolio_iniciativa') AND column_name = LOWER('peso'))
	THEN
		execute 'ALTER TABLE portafolio_iniciativa ADD COLUMN peso double precision';
	END IF;				
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160826';

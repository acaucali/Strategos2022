CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'medicion_bloqueo') 
	THEN
		CREATE TABLE medicion_bloqueo
		(
			organizacion_id      numeric(10,0) NOT NULL ,
			bloqueo_inicio       timestamp without time zone NOT NULL ,
			bloqueo_fin          timestamp without time zone NOT NULL 
		);

		CREATE UNIQUE INDEX AK_medicion_bloqueo ON medicion_bloqueo (organizacion_id   ASC);
		ALTER TABLE medicion_bloqueo ADD CONSTRAINT PK_medicion_bloqueo PRIMARY KEY (organizacion_id);
		ALTER TABLE medicion_bloqueo ADD CONSTRAINT FK_organizacion_medicionbloque FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;
	END IF;

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('indicador') AND column_name = LOWER('enlace_parcial'))
	THEN
		execute 'ALTER TABLE indicador ALTER COLUMN enlace_parcial TYPE character varying(100)';	
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('clase') AND column_name = LOWER('enlace_parcial'))
	THEN
		execute 'ALTER TABLE clase ALTER COLUMN enlace_parcial TYPE character varying(100)';	
	END IF;	

	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('organizacion') AND column_name = LOWER('enlace_parcial'))
	THEN
		execute 'ALTER TABLE organizacion ALTER COLUMN enlace_parcial TYPE character varying(100)';	
	END IF;	
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170618';

DROP TABLE IF EXISTS mb_histograma;

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_atributo') AND column_name = LOWER('INDICADOR_ID'))	
	THEN
		execute 'ALTER TABLE mb_atributo ADD COLUMN INDICADOR_ID numeric(10, 0) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_atributo') AND column_name = LOWER('APC'))	
	THEN
		execute 'ALTER TABLE mb_atributo ADD COLUMN APC numeric(10, 0) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_encuesta_atributo') AND column_name = LOWER('COMENTARIO'))	
	THEN
		execute 'ALTER TABLE mb_encuesta_atributo ADD COLUMN COMENTARIO text NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_medicion') AND column_name = LOWER('ENCSTAS_X_ENCSTADO'))	
	THEN
		execute 'ALTER TABLE mb_medicion ADD COLUMN ENCSTAS_X_ENCSTADO numeric(10, 0) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_medicion') AND column_name = LOWER('COMENT_X_PREGUNTA'))	
	THEN
		execute 'ALTER TABLE mb_medicion ADD COLUMN COMENT_X_PREGUNTA numeric(10, 0) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_medicion') AND column_name = LOWER('ASUNTO_CORREO'))	
	THEN
		execute 'ALTER TABLE mb_medicion ADD COLUMN ASUNTO_CORREO text NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_organizacion_variable') AND column_name = LOWER('CODIGO_ENLACE'))	
	THEN
		execute 'ALTER TABLE mb_organizacion_variable ADD COLUMN CODIGO_ENLACE character varying(100) NULL';
	END IF;

	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_sector') AND column_name = LOWER('CODIGO_ENLACE'))	
	THEN
		execute 'ALTER TABLE mb_sector ADD COLUMN CODIGO_ENLACE character varying(100) NULL';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'mb_atrib_coment_cat') 
	THEN
		CREATE TABLE mb_atrib_coment_cat
		(
			atributo_id          numeric(10, 0) NOT NULL ,
			orden                numeric(3, 0) NOT NULL 
		);

		CREATE UNIQUE INDEX AK_mb_atrib_coment_cat ON mb_atrib_coment_cat USING btree (atributo_id   ASC,orden   ASC);

		ALTER TABLE mb_atrib_coment_cat ADD CONSTRAINT  PK_mb_atrib_coment_cat PRIMARY KEY (atributo_id,orden);

		CREATE  INDEX IE_mb_atrib_coment_cat ON mb_atrib_coment_cat USING btree (atributo_id   ASC);

		ALTER TABLE mb_atrib_coment_cat
			ADD CONSTRAINT PK_mb_atributo_commnet_orden FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id) ON UPDATE NO ACTION ON DELETE CASCADE;
	END IF;
	
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_medicion') AND column_name = LOWER('cirterio_desempate'))	
	THEN
		execute 'ALTER TABLE mb_medicion DROP COLUMN cirterio_desempate';
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('mb_medicion') AND column_name = LOWER('CRITERIO_DESEMPATE'))	
	THEN
		execute 'ALTER TABLE mb_medicion ADD COLUMN CRITERIO_DESEMPATE numeric(1, 0) NULL';
	END IF;
	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

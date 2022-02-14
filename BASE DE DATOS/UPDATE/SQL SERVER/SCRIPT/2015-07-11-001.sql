CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'duppont') 
	THEN
		CREATE TABLE duppont
		(
			indicador_id        numeric(10,0) NOT NULL ,
			usuario_id         	numeric(10,0) NOT NULL,
			configuracion       text NOT NULL
		);

		CREATE UNIQUE INDEX AK_duppont ON duppont USING btree (indicador_id   ASC, usuario_id   ASC);
		ALTER TABLE duppont ADD CONSTRAINT  PK_duppont PRIMARY KEY (indicador_id, usuario_id);
		CREATE  INDEX IE_duppont_indicadorId ON duppont USING btree (indicador_id   ASC);
		CREATE  INDEX IE_duppont_usuarioId ON duppont USING btree (usuario_id   ASC);
		ALTER TABLE duppont ADD CONSTRAINT FK_indicador_duppont FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;
		ALTER TABLE duppont ADD CONSTRAINT FK_usuario_duppont FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE;
		
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

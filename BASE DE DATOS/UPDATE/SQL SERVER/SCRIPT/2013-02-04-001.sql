ALTER TABLE afw_usuario ADD COLUMN ultima_modif_pwd timestamp without time zone;

CREATE TABLE afw_pwd_historia
(
	usuario_id           numeric(10) NOT NULL ,
	pwd                  character varying(100) NULL ,
	fecha                timestamp without time zone NOT NULL,
	CONSTRAINT PK_afw_pwd_historia PRIMARY KEY (usuario_id,fecha),
	CONSTRAINT FK_USER_PWDHISTORIA FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF_afw_pwd_historia ON afw_pwd_historia USING btree (usuario_id   ASC);

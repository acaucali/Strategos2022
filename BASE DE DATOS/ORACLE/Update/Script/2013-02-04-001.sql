ALTER TABLE afw_usuario ADD ultima_modif_pwd TIMESTAMP NULL;

CREATE TABLE afw_pwd_historia
(
	usuario_id           NUMBER(10) NOT NULL ,
	pwd                  VARCHAR(100) NULL ,
	fecha                TIMESTAMP NOT NULL 
);

CREATE UNIQUE INDEX PK_afw_pwd_historia ON afw_pwd_historia
(usuario_id   ASC,fecha   ASC);

ALTER TABLE afw_pwd_historia
	ADD CONSTRAINT  PK_afw_pwd_historia PRIMARY KEY (usuario_id,fecha);

CREATE  INDEX IF_afw_pwd_historia ON afw_pwd_historia
(usuario_id   ASC);

ALTER TABLE afw_pwd_historia
	ADD (CONSTRAINT FK_USER_PWDHISTORIA FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id));

COMMIT;
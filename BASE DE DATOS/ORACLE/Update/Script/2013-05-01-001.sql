CREATE TABLE afw_message
(
	usuario_id           NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NOT NULL ,
	estatus              NUMBER(1) NOT NULL ,
	mensaje              VARCHAR(500) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL
);

CREATE UNIQUE INDEX PK_afw_message ON afw_message (usuario_id   ASC,fecha   ASC);

ALTER TABLE afw_message ADD CONSTRAINT  PK_afw_message PRIMARY KEY (usuario_id,fecha);

CREATE UNIQUE INDEX AK_afw_message ON afw_message (usuario_id   ASC,estatus   ASC);

ALTER TABLE afw_message ADD CONSTRAINT  AK_afw_message UNIQUE (usuario_id,estatus);

CREATE  INDEX IF_afw_message ON afw_message (usuario_id   ASC);

COMMIT;

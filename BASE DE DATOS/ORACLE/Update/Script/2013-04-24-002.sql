CREATE TABLE afw_servicio
(
	usuario_id           NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NOT NULL ,
	nombre               VARCHAR(50) NOT NULL ,
	estatus              NUMBER(1) NOT NULL ,
	datos                VARCHAR2(1000) NOT NULL 
);

CREATE UNIQUE INDEX PK_afw_servicio ON afw_servicio
(usuario_id   ASC,fecha   ASC,nombre   ASC);

ALTER TABLE afw_servicio
	ADD CONSTRAINT  PK_afw_servicio PRIMARY KEY (usuario_id,fecha,nombre);

CREATE  INDEX IF_afw_servicio ON afw_servicio
(usuario_id   ASC);

COMMIT;
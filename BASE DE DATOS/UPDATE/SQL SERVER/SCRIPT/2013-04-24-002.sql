CREATE TABLE afw_servicio
(
	usuario_id           numeric(10,0) NOT NULL ,
	fecha                timestamp without time zone NOT NULL,
	nombre               character varying(50) NOT NULL,
	estatus              numeric(1,0) NOT NULL,
	datos                character varying(1000) NOT NULL,
	CONSTRAINT PK_afw_servicio PRIMARY KEY (usuario_id, fecha, nombre)
);
ALTER TABLE afw_servicio OWNER TO postgres;

CREATE UNIQUE INDEX UK_afw_servicio ON afw_servicio USING btree
(usuario_id   ASC,fecha   ASC,nombre   ASC);

CREATE  INDEX IF_afw_servicio ON afw_servicio USING btree (usuario_id   ASC);

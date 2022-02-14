CREATE TABLE afw_importacion
(
	id           		 numeric(10, 0) NOT NULL, 
	usuario_id           numeric(10, 0) NOT NULL, 
	nombre               character varying(100) NOT NULL,
	tipo                 numeric(1, 0) NOT NULL,
	configuracion        character varying(2000) NOT NULL
);

CREATE UNIQUE INDEX AK_afw_importacion ON afw_importacion USING btree (id   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT  PK_afw_importacion PRIMARY KEY (id);

CREATE UNIQUE INDEX AK_fw_importacion_usuario_nomb ON afw_importacion USING btree (usuario_id   ASC,nombre   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT PK_fw_importacion_usuario_nomb UNIQUE (usuario_id,nombre);

CREATE  INDEX IE_afw_importacion_nombre ON afw_importacion USING btree (nombre   ASC);

CREATE  INDEX IE_afw_importacion_usuarioId ON afw_importacion USING btree (usuario_id   ASC);

ALTER TABLE afw_importacion
	ADD CONSTRAINT FK_usuario_importacion FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE;

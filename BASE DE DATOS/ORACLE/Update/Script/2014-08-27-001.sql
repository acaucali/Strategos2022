CREATE TABLE afw_importacion
(
	id                   NUMBER(10) NOT NULL, 
	usuario_id           NUMBER(10) NOT NULL,
	nombre               VARCHAR2(100) NOT NULL,
	tipo                 NUMBER(1) NOT NULL,
	configuracion        VARCHAR2(2000) NOT NULL
);

CREATE UNIQUE INDEX AK_afw_importacion ON afw_importacion (id   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT  PK_afw_importacion PRIMARY KEY (id);

CREATE UNIQUE INDEX AK_fw_importacion_usuario_nomb ON afw_importacion (usuario_id   ASC,nombre   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT PK_fw_importacion_usuario_nomb UNIQUE (usuario_id,nombre);

CREATE  INDEX IE_afw_importacion_nombre ON afw_importacion (nombre   ASC);

CREATE  INDEX IE_afw_importacion_usuarioId ON afw_importacion (usuario_id   ASC);

ALTER TABLE afw_importacion
	ADD (CONSTRAINT FK_usuario_importacion FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);
	
COMMIT;

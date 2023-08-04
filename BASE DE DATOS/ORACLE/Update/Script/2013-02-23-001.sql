CREATE TABLE afw_objeto_binario
(
	objeto_binario_id    NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(200) NOT NULL ,
	mime_type            VARCHAR2(200) NULL ,
	data                 BLOB NULL 
);

CREATE UNIQUE INDEX pk_afw_objeto_binario ON afw_objeto_binario (objeto_binario_id   ASC);

ALTER TABLE afw_objeto_binario ADD CONSTRAINT pk_afw_objeto_binario PRIMARY KEY (objeto_binario_id);

COMMIT;
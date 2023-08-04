CREATE TABLE afw_transaccion
(
	id                   NUMBER(10) NOT NULL,
	nombre               VARCHAR2(50) NOT NULL,
	frecuencia           NUMBER(1) NOT NULL,
	configuracion        LONG VARCHAR NOT NULL 
);

ALTER TABLE afw_transaccion ADD CONSTRAINT PK_afw_transaccion PRIMARY KEY (id);

ALTER TABLE afw_transaccion ADD CONSTRAINT  AK_afw_transaccion UNIQUE (nombre);

CREATE TABLE transaccion_indicador
(
	transaccion_Id       NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	campo                VARCHAR2(50) NOT NULL 
);

CREATE UNIQUE INDEX IE_transaccion_indicador ON transaccion_indicador (transaccion_Id   ASC,indicador_id   ASC,campo   ASC);

ALTER TABLE transaccion_indicador
	ADD CONSTRAINT  PK_transaccion_indicador PRIMARY KEY (transaccion_Id, indicador_id, campo);

CREATE  INDEX IE_transaccion_indicador_trans ON transaccion_indicador (transaccion_Id   ASC);

CREATE  INDEX IE_transaccion_indicador_indic ON transaccion_indicador (indicador_id   ASC);

ALTER TABLE transaccion_indicador
	ADD (CONSTRAINT FK_afw_transaccion_indicador FOREIGN KEY (transaccion_Id) REFERENCES afw_transaccion (id) ON DELETE CASCADE);

ALTER TABLE transaccion_indicador
	ADD (CONSTRAINT FK_indicador_afw_transaccion FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

COMMIT;

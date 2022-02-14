CREATE TABLE afw_transaccion
(
	id                   numeric(10, 0) NOT NULL,
	nombre               character varying(50) NOT NULL,
	frecuencia           numeric(1, 0) NOT NULL,
	configuracion        text NOT NULL 
);

ALTER TABLE afw_transaccion ADD CONSTRAINT PK_afw_transaccion PRIMARY KEY (id);

CREATE UNIQUE INDEX IE_afw_transaccion ON afw_transaccion USING btree (id   ASC);

ALTER TABLE afw_transaccion ADD CONSTRAINT AK_afw_transaccion UNIQUE (nombre);

CREATE UNIQUE INDEX AK_afw_transaccion_nombre ON afw_transaccion USING btree (nombre   ASC);

CREATE TABLE transaccion_indicador
(
	transaccion_Id       numeric(10, 0) NOT NULL ,
	indicador_id         numeric(10, 0) NOT NULL ,
	campo                character varying(50) NOT NULL 
);

CREATE UNIQUE INDEX IE_transaccion_indicador ON transaccion_indicador USING btree (transaccion_Id   ASC,indicador_id   ASC,campo   ASC);

ALTER TABLE transaccion_indicador
	ADD CONSTRAINT  PK_transaccion_indicador PRIMARY KEY (transaccion_Id, indicador_id, campo);

CREATE  INDEX IE_transaccion_indicador_trans ON transaccion_indicador USING btree (transaccion_Id   ASC);

CREATE  INDEX IE_transaccion_indicador_indic ON transaccion_indicador USING btree (indicador_id   ASC);

ALTER TABLE transaccion_indicador
	ADD CONSTRAINT FK_afw_transaccion_indicador FOREIGN KEY (transaccion_Id) REFERENCES afw_transaccion (id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE transaccion_indicador
	ADD CONSTRAINT FK_indicador_afw_transaccion FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

UPDATE afw_sistema set actual = '8.01-220307';  
UPDATE afw_sistema set build = 220307;

CREATE TABLE instrumento_iniciativa
(
	instrumento_id        NUMERIC(10, 0) NOT NULL ,
	iniciativa_id        NUMERIC(10, 0) NOT NULL
);

CREATE UNIQUE INDEX PK_instrumento_iniciativa ON instrumento_iniciativa USING btree (instrumento_id   ASC,iniciativa_id   ASC);
ALTER TABLE instrumento_iniciativa ADD CONSTRAINT PK_instrumento_iniciativa_portId PRIMARY KEY (instrumento_id, iniciativa_id);
CREATE  INDEX IE_instrumento_iniciativa_instru ON instrumento_iniciativa USING btree (instrumento_id   ASC);
CREATE  INDEX IE_instrumento_iniciativa_inici ON instrumento_iniciativa USING btree (iniciativa_id   ASC);


alter table iniciativa_objeto
    alter column objeto type varchar(1024);
    

ALTER TABLE iniciativa 
    add column responsable_proyecto character varying(150),
	add column cargo_responsable character varying(50),
	add column organizaciones_involucradas character varying(250),
	add column objetivo_estrategico character varying(1024),
	add column fuente_financiacion character varying(50),
	add column monto_financiamiento character varying(50),
	add column iniciativa_estrategica character varying(150),
	add column lider_iniciativa character varying(150),
	add column tipo_iniciativa character varying(150),
	add column poblacion_beneficiada character varying(1024),
	add column contexto character varying(1024),
	add column definicion_problema character varying(1024),
	add column alcance character varying(1024),
	add column objetivo_general character varying(1024),
	add column objetivos_especificos character varying(1024);        
    
    
alter table instrumento_iniciativa add peso float;

CREATE TABLE indicador_por_instrumento
(
	instrumento_id       serial NOT NULL ,
	indicador_id         numeric(10,0) NOT NULL,
	tipo         		 numeric(1, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_instrumento ON indicador_por_instrumento(instrumento_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT  PK_indicador_por_instrumento PRIMARY KEY (instrumento_id,indicador_id);

CREATE INDEX IE_ind_por_ins_instrumentoId ON indicador_por_instrumento  (instrumento_id   ASC);

CREATE  INDEX IE_ind_por_ins_indicadorId ON indicador_por_instrumento  (indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_InstrumentoId FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE instrumentos ADD clase_id numeric (10,0);

INSERT INTO afw_permiso (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EDIT_COOPERACION', 'Modificacion Otras Organizaciones', 'INICIATIVA', 2, 14, 1, 'Modificacion Otras Organizaciones');

CREATE TABLE instrumento_peso (
	instrumento_id serial NOT NULL,
	anio varchar(4) NOT NULL,
	peso float
);

CREATE  INDEX IE_instrumento_peso ON instrumento_peso (instrumento_id   ASC);

ALTER TABLE instrumento_peso
    ADD CONSTRAINT pk_instrumento_peso PRIMARY KEY (instrumento_id);
    
ALTER TABLE instrumento_peso
	ADD CONSTRAINT fk_instrumento_peso FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON UPDATE NO ACTION ON DELETE CASCADE;
  
UPDATE afw_permiso set permiso = 'Desasociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_DELETE';
UPDATE afw_permiso set permiso = 'Asociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_ADD';

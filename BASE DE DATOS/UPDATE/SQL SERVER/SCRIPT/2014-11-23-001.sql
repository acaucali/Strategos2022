CREATE TABLE mb_atributo
(
	atributo_id          numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	variable_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	pregunta             character varying(2000) NOT NULL ,
	prioridad            numeric(2, 0),
	orden                numeric(2, 0) NOT NULL 
);

ALTER TABLE mb_atributo
	ADD CONSTRAINT  PK_MB_ATRIBUTO PRIMARY KEY (atributo_id);

ALTER TABLE mb_atributo ADD CONSTRAINT  AK_MB_ATRIBUTO UNIQUE (sector_id,nombre);

CREATE  INDEX IE_MB_ATRIBUTO_SECTOR_ID ON mb_atributo USING btree (sector_id   ASC);

CREATE TABLE mb_categoria_criterio
(
	criterio_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	categoria            character varying(50) NULL 
);

ALTER TABLE mb_categoria_criterio
	ADD CONSTRAINT  PK_MB_CATEGORIA_CRITERIO PRIMARY KEY (criterio_id,orden);

CREATE TABLE mb_categoria_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	categoria_id         numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL 
);

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT  AK_MB_CATEGORIA_VARIABLE PRIMARY KEY (variable_id,categoria_id);

CREATE  INDEX IE_MBCATEGORIA_VARIABLEID ON mb_categoria_variable USING btree (variable_id   ASC);

CREATE  INDEX IE_MBVARIABLE_CATEGORIAID ON mb_categoria_variable USING btree (categoria_id   ASC);

CREATE TABLE mb_criterio_estratificacion
(
	criterio_id          numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	pregunta             character varying(2000) NOT NULL 
);

ALTER TABLE mb_criterio_estratificacion
	ADD CONSTRAINT  PK_MB_CRITERIO_ESTRATIFICACION PRIMARY KEY (criterio_id);

ALTER TABLE mb_criterio_estratificacion ADD CONSTRAINT  AK_MB_CRITERIO_ESTRATIFICACION UNIQUE (medicion_id,nombre);

CREATE TABLE mb_encuesta
(
	encuesta_id          numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	encuestado_id        numeric(10, 0),
	numero               numeric(10, 0),
	fecha                timestamp without time zone NOT NULL ,
	persona              character varying(50),
	ci                   character varying(10),
	estado               numeric(2, 0),
	comentario           character varying(2000) NULL 
);

ALTER TABLE mb_encuesta
	ADD CONSTRAINT  PK_MB_ENCUESTA PRIMARY KEY (encuesta_id);

ALTER TABLE mb_encuesta ADD CONSTRAINT  AK_MB_ENCUESTA UNIQUE (sector_id,numero);

CREATE  INDEX IE_MB_ENCUESTA_SECTOR_ID ON mb_encuesta USING btree (sector_id   ASC);

CREATE TABLE mb_encuesta_atributo
(
	atributo_id          numeric(10, 0) NOT NULL ,
	encuesta_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0),
	prioridad            numeric(2, 0) 
);

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT  PK_MB_ENCUESTA_ATRIBUTO PRIMARY KEY (atributo_id,encuesta_id);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ENC ON mb_encuesta_atributo USING btree (encuesta_id   ASC);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ATR ON mb_encuesta_atributo USING btree (atributo_id   ASC);

CREATE TABLE mb_encuesta_criterio
(
	encuesta_id          numeric(10, 0) NOT NULL ,
	c1                   numeric(10, 0),
	c2                   numeric(10, 0),
	c3                   numeric(10, 0),
	c4                   numeric(10, 0),
	c5                   numeric(10, 0),
	c6                   numeric(10, 0),
	c7                   numeric(10, 0),
	c8                   numeric(10, 0),
	c9                   numeric(10, 0),
	c10                  numeric(10, 0) 
);

ALTER TABLE mb_encuesta_criterio
	ADD CONSTRAINT  PK_MB_ENCUESTA_CRITERIO PRIMARY KEY (encuesta_id);

CREATE TABLE mb_encuesta_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	encuesta_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	validas              numeric(10, 0),
	novalidas            numeric(10, 0),
	estatus              numeric(1, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_red_id      numeric(10, 0),
	promedio             double precision NULL 
);

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT  PK_MB_ENCUESTA_VARIABLE PRIMARY KEY (encuesta_id,variable_id);

CREATE TABLE mb_encuestado
(
	encuestado_id        numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	persona_id           numeric(10, 0),
	nombre               character varying(100),
	cedula               character varying(20),
	cargo                character varying(50),
	email                character varying(50),
	estado               numeric(2, 0),
	numero_encuesta      numeric(10, 0),
	encuesta_id          numeric(10, 0),
	notas                character varying(2000),
	web_address          character varying(20) 
);

ALTER TABLE mb_encuestado
	ADD CONSTRAINT  PK_MB_ENCUESTADO PRIMARY KEY (encuestado_id);

CREATE TABLE mb_etiqueta
(
	atributo_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL 
);

ALTER TABLE mb_etiqueta
	ADD CONSTRAINT  PK_MB_ETIQUETA PRIMARY KEY (atributo_id,orden);

CREATE TABLE mb_formato_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	texto_ayuda          character varying(2000),
	mostrar_ayuda        numeric(1, 0),
	preenunciado         character varying(100),
	mostrar_pre          numeric(1, 0),
	postenunciado        character varying(100),
	mostrar_post         numeric(1, 0),
	mostrar_variable     numeric(1, 0) 
);

ALTER TABLE mb_formato_variable
	ADD CONSTRAINT  PK_MB_FORMATO_VARIABLE PRIMARY KEY (variable_id);

CREATE TABLE mb_grafico
(
	grafico_id           numeric(10, 0) NOT NULL ,
	nombre               character varying(50),
	tipo                 numeric(1, 0) NOT NULL ,
	elemento_id          numeric(10, 0) 
);

ALTER TABLE mb_grafico
	ADD CONSTRAINT  PK_MB_GRAFICO PRIMARY KEY (grafico_id);

CREATE TABLE mb_grafico_binario
(
	grafico_id           numeric(10, 0) NOT NULL ,
	binario              text NULL 
);

ALTER TABLE mb_grafico_binario
	ADD CONSTRAINT  PK_MB_GRAFICO_BINARIO PRIMARY KEY (grafico_id);

CREATE TABLE mb_histograma
(
	elemento_id          numeric(10, 0) NOT NULL ,
	fila_id              numeric(10, 0) NOT NULL ,
	categoria_id         numeric(10, 0) NOT NULL ,
	frecuencia           numeric(5, 0) 
);

ALTER TABLE mb_histograma
	ADD CONSTRAINT  AK_MB_HISTOGRAMA PRIMARY KEY (elemento_id,categoria_id,fila_id);

CREATE  INDEX IE_MBHISTOGRAMA_ELEMENTOID ON mb_histograma USING btree (elemento_id   ASC);

CREATE  INDEX IE_MBHISTOGRAMA_CATEGORIAID ON mb_histograma USING btree (categoria_id   ASC);

CREATE TABLE mb_medicion
(
	medicion_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	address_id           numeric(10, 0),
	fecha                timestamp without time zone NOT NULL ,
	modalidad            numeric(1, 0) NOT NULL ,
	responsable          character varying(20),
	creado               timestamp without time zone NULL ,
	creado_id            numeric(10, 0),
	modificado           timestamp without time zone NULL ,
	modificado_id        numeric(10, 0),
	criterio_validez     numeric(3, 0),
	cirterio_desempate   numeric(1, 0),
	define_prioridad     numeric(1, 0),
	tipo_sintesis        numeric(2, 0),
	sintetizado          numeric(1, 0),
	anonimo              numeric(1, 0),
	etapa                numeric(1, 0),
	mensaje_correo       character varying(4000) 
);

ALTER TABLE mb_medicion
	ADD CONSTRAINT  PK_MB_MEDICION PRIMARY KEY (medicion_id);

CREATE  INDEX IE_MB_MEDICION_FECHA ON mb_medicion USING btree (fecha   ASC);

CREATE TABLE mb_medicion_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	padre_id             numeric(10, 0),
	variable_ref_id      numeric(10, 0),
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	nivel                numeric(4, 0),
	porcentaje           double precision NULL 
);

ALTER TABLE mb_medicion_variable
	ADD CONSTRAINT  PK_MB_MEDICION_VARIABLE PRIMARY KEY (variable_id);

CREATE  INDEX IE_MB_MED_VAR_MEDICION_ID ON mb_medicion_variable USING btree (medicion_id   ASC);

CREATE TABLE mb_numero_encuesta
(
	sector_id            numeric(10, 0) NOT NULL ,
	numero               numeric(10, 0) NOT NULL 
);

ALTER TABLE mb_numero_encuesta
	ADD CONSTRAINT  PK_MB_NUMERO_ENCUESTA PRIMARY KEY (sector_id,numero);

CREATE TABLE mb_orden_criterio
(
	medicion_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL 
);

ALTER TABLE mb_orden_criterio
	ADD CONSTRAINT  PK_MB_ORDEN_CRITERIO PRIMARY KEY (medicion_id,orden);

CREATE TABLE mb_organizacion
(
	organizacion_id      numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	padre_id             numeric(10, 0),
	organizacion_ref_id  numeric(10, 0),
	nombre               character varying(50) NOT NULL ,
	porcentaje           double precision NULL ,
	porcentaje_propio    double precision NULL 
);

ALTER TABLE mb_organizacion
	ADD CONSTRAINT  PK_MB_ORGANIZACION PRIMARY KEY (organizacion_id);

ALTER TABLE mb_organizacion ADD CONSTRAINT  AK_MB_ORGANIZACION UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_ORGANIZACION_PADRE_ID ON mb_organizacion USING btree (padre_id   ASC);

CREATE TABLE mb_organizacion_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	apc_propio           numeric(10, 0),
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	indicador_id         numeric(10, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_red_id      numeric(10, 0),
	organizacion_id      numeric(10, 0) NOT NULL 
);

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT  AK_MB_ORGANIZACION_VARIABLE PRIMARY KEY (variable_id,organizacion_id);

CREATE  INDEX IE_MB_VARIABLE_ORGANIZACIONID ON mb_organizacion_variable USING btree (organizacion_id   ASC);

CREATE  INDEX IE_mborgvariable_indicadorId ON mb_organizacion_variable USING btree (indicador_id   ASC);

CREATE TABLE mb_sector
(
	sector_id            numeric(10, 0) NOT NULL ,
	padre_id             numeric(10, 0),
	organizacion_id      numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	porcentaje           double precision NULL ,
	nodo_final           numeric(1, 0),
	definidos            numeric(10, 0),
	tamano_muestra       numeric(10, 0) 
);

ALTER TABLE mb_sector
	ADD CONSTRAINT  PK_MB_SECTOR PRIMARY KEY (sector_id);

ALTER TABLE mb_sector ADD CONSTRAINT  AK_MB_SECTOR UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_SECTOR_PADRE_ID ON mb_sector USING btree (padre_id   ASC);

CREATE TABLE mb_sector_variable
(
	sector_id            numeric(10, 0) NOT NULL ,
	variable_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	estatus              numeric(1, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_ref_id      numeric(10, 0) 
);

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT  PK_MB_SECTOR_VARIABLE PRIMARY KEY (sector_id,variable_id);

CREATE TABLE mb_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	frecuencia           numeric(1, 0) NOT NULL ,
	creado               timestamp without time zone NULL ,
	creado_id            numeric(10, 0),
	modificado           timestamp without time zone NULL ,
	modificado_id        numeric(10, 0) 
);

ALTER TABLE mb_variable
	ADD CONSTRAINT  PK_MB_VARIABLE PRIMARY KEY (variable_id);

ALTER TABLE mb_variable ADD CONSTRAINT  AK_MB_VARIABLE UNIQUE (nombre);

CREATE TABLE mb_webaddress
(
	address_id           numeric(10, 0) NOT NULL ,
	address              character varying(100) NOT NULL 
);

ALTER TABLE mb_webaddress
	ADD CONSTRAINT  PK_MB_WEBADDRESS PRIMARY KEY (address_id);

ALTER TABLE mb_atributo
	ADD CONSTRAINT FK_MB_MED_VARIABLE_ATRIBUTO FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_atributo
	ADD CONSTRAINT FK_MB_SECTOR_ATRIBUTO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_criterio
	ADD CONSTRAINT FK_MB_CRITERIO_CATEGORIA FOREIGN KEY (criterio_id) REFERENCES mb_criterio_estratificacion (criterio_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT FK_MB_VARIABLE_CATEGORIA FOREIGN KEY (variable_id) REFERENCES mb_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT FK_mdvariable_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_criterio_estratificacion
	ADD CONSTRAINT FK_MB_MEDICION_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta
	ADD CONSTRAINT FK_MB_ENCUESTADO_ENCUESTA FOREIGN KEY (encuestado_id) REFERENCES mb_encuestado (encuestado_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE mb_encuesta
	ADD CONSTRAINT FK_MB_SECTOR_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT FK_MB_ENCUESTA_ATRIBUTO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT FK_MB_ATRIBUTO_ENCUESTA FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id);

ALTER TABLE mb_encuesta_criterio
	ADD CONSTRAINT FK_MB_ENCUESTA_CRITERIO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT FK_MB_MEDICION_ENC_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT FK_MB_ENCUESTA_VARIABLE FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuestado
	ADD CONSTRAINT FK_MB_SECTOR_ENCUESTADO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id);

ALTER TABLE mb_formato_variable
	ADD CONSTRAINT FK_MB_MEDICION_VARIABLE_FORMAT FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_grafico_binario
	ADD CONSTRAINT FK_MB_GRAFICO_BINARIO FOREIGN KEY (grafico_id) REFERENCES mb_grafico (grafico_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_histograma
	ADD CONSTRAINT FK_mbhistograma_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_medicion
	ADD CONSTRAINT FK_MB_WEBADDRESS_MEDICION FOREIGN KEY (address_id) REFERENCES mb_webaddress (address_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE mb_medicion_variable
	ADD CONSTRAINT FK_MB_MEDICION_VARIABLE FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_numero_encuesta
	ADD CONSTRAINT FK_MB_SECTOR_NUMERO_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_orden_criterio
	ADD CONSTRAINT FK_MB_MEDICION_ORDEN_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion
	ADD CONSTRAINT FK_MB_MEDICION_ORGANIZACION FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_MB_MEDICION_ORG_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_organizacion_variable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_mdorgvariable_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_sector
	ADD CONSTRAINT FK_MB_ORGANIZACION_SECTOR FOREIGN KEY (organizacion_id) REFERENCES mb_organizacion (organizacion_id);

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT FK_MB_MEDICION_SECTOR_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT FK_MB_SECTOR_VARIABLE FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

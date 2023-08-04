CREATE TABLE mb_atributo
(
	atributo_id          NUMBER(10) NOT NULL ,
	sector_id            NUMBER(10) NOT NULL ,
	variable_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	pregunta             VARCHAR2(2000) NOT NULL ,
	prioridad            NUMBER(2) NULL ,
	orden                NUMBER(2) NOT NULL 
);

ALTER TABLE mb_atributo
	ADD CONSTRAINT  PK_MB_ATRIBUTO PRIMARY KEY (atributo_id);

ALTER TABLE mb_atributo ADD CONSTRAINT  AK_MB_ATRIBUTO UNIQUE (sector_id,nombre);

CREATE  INDEX IE_MB_ATRIBUTO_SECTOR_ID ON mb_atributo (sector_id   ASC);

CREATE TABLE mb_categoria_criterio
(
	criterio_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NOT NULL ,
	categoria            VARCHAR2(50) NULL 
);

ALTER TABLE mb_categoria_criterio
	ADD CONSTRAINT  PK_MB_CATEGORIA_CRITERIO PRIMARY KEY (criterio_id,orden);

CREATE TABLE mb_categoria_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	categoria_id         NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NOT NULL 
);

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT  AK_MB_CATEGORIA_VARIABLE PRIMARY KEY (variable_id,categoria_id);

CREATE  INDEX IE_MBCATEGORIA_VARIABLEID ON mb_categoria_variable (variable_id   ASC);

CREATE  INDEX IE_MBVARIABLE_CATEGORIAID ON mb_categoria_variable (categoria_id   ASC);

CREATE TABLE mb_criterio_estratificacion
(
	criterio_id          NUMBER(10) NOT NULL ,
	medicion_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	pregunta             VARCHAR2(2000) NOT NULL 
);

ALTER TABLE mb_criterio_estratificacion
	ADD CONSTRAINT  PK_MB_CRITERIO_ESTRATIFICACION PRIMARY KEY (criterio_id);

ALTER TABLE mb_criterio_estratificacion ADD CONSTRAINT  AK_MB_CRITERIO_ESTRATIFICACION UNIQUE (medicion_id,nombre);

CREATE TABLE mb_encuesta
(
	encuesta_id          NUMBER(10) NOT NULL ,
	sector_id            NUMBER(10) NOT NULL ,
	encuestado_id        NUMBER(10) NULL ,
	numero               NUMBER(10) NULL ,
	fecha                DATE NOT NULL ,
	persona              VARCHAR2(50) NULL ,
	ci                   VARCHAR2(10) NULL ,
	estado               NUMBER(2) NULL ,
	comentario           VARCHAR2(2000) NULL 
);

ALTER TABLE mb_encuesta
	ADD CONSTRAINT  PK_MB_ENCUESTA PRIMARY KEY (encuesta_id);

ALTER TABLE mb_encuesta ADD CONSTRAINT  AK_MB_ENCUESTA UNIQUE (sector_id,numero);

CREATE  INDEX IE_MB_ENCUESTA_SECTOR_ID ON mb_encuesta (sector_id   ASC);

CREATE TABLE mb_encuesta_atributo
(
	atributo_id          NUMBER(10) NOT NULL ,
	encuesta_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NULL ,
	prioridad            NUMBER(2) NULL 
);

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT  PK_MB_ENCUESTA_ATRIBUTO PRIMARY KEY (atributo_id,encuesta_id);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ENC ON mb_encuesta_atributo (encuesta_id   ASC);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ATR ON mb_encuesta_atributo (atributo_id   ASC);

CREATE TABLE mb_encuesta_criterio
(
	encuesta_id          NUMBER(10) NOT NULL ,
	c1                   NUMBER(10) NULL ,
	c2                   NUMBER(10) NULL ,
	c3                   NUMBER(10) NULL ,
	c4                   NUMBER(10) NULL ,
	c5                   NUMBER(10) NULL ,
	c6                   NUMBER(10) NULL ,
	c7                   NUMBER(10) NULL ,
	c8                   NUMBER(10) NULL ,
	c9                   NUMBER(10) NULL ,
	c10                  NUMBER(10) NULL 
);

ALTER TABLE mb_encuesta_criterio
	ADD CONSTRAINT  PK_MB_ENCUESTA_CRITERIO PRIMARY KEY (encuesta_id);

CREATE TABLE mb_encuesta_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	encuesta_id          NUMBER(10) NOT NULL ,
	apc                  NUMBER(10) NULL ,
	validas              NUMBER(10) NULL ,
	novalidas            NUMBER(10) NULL ,
	estatus              NUMBER(1) NULL ,
	padre_id             NUMBER(10) NULL ,
	nivel                NUMBER(4) NULL ,
	variable_red_id      NUMBER(10) NULL ,
	promedio             FLOAT NULL 
);

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT  PK_MB_ENCUESTA_VARIABLE PRIMARY KEY (encuesta_id,variable_id);

CREATE TABLE mb_encuestado
(
	encuestado_id        NUMBER(10) NOT NULL ,
	sector_id            NUMBER(10) NOT NULL ,
	persona_id           NUMBER(10) NULL ,
	nombre               VARCHAR2(100) NULL ,
	cedula               VARCHAR2(20) NULL ,
	cargo                VARCHAR2(50) NULL ,
	email                VARCHAR2(50) NULL ,
	estado               NUMBER(2) NULL ,
	numero_encuesta      NUMBER(10) NULL ,
	encuesta_id          NUMBER(10) NULL ,
	notas                VARCHAR2(2000) NULL ,
	web_address          VARCHAR2(20) NULL 
);

ALTER TABLE mb_encuestado
	ADD CONSTRAINT  PK_MB_ENCUESTADO PRIMARY KEY (encuestado_id);

CREATE TABLE mb_etiqueta
(
	atributo_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL 
);

ALTER TABLE mb_etiqueta
	ADD CONSTRAINT  PK_MB_ETIQUETA PRIMARY KEY (atributo_id,orden);

CREATE TABLE mb_formato_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	texto_ayuda          VARCHAR2(2000) NULL ,
	mostrar_ayuda        NUMBER(1) NULL ,
	preenunciado         VARCHAR2(100) NULL ,
	mostrar_pre          NUMBER(1) NULL ,
	postenunciado        VARCHAR2(100) NULL ,
	mostrar_post         NUMBER(1) NULL ,
	mostrar_variable     NUMBER(1) NULL 
);

ALTER TABLE mb_formato_variable
	ADD CONSTRAINT  PK_MB_FORMATO_VARIABLE PRIMARY KEY (variable_id);

CREATE TABLE mb_grafico
(
	grafico_id           NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	elemento_id          NUMBER(10) NULL 
);

ALTER TABLE mb_grafico
	ADD CONSTRAINT  PK_MB_GRAFICO PRIMARY KEY (grafico_id);

CREATE TABLE mb_grafico_binario
(
	grafico_id           NUMBER(10) NOT NULL ,
	binario              LONG RAW NULL 
);

ALTER TABLE mb_grafico_binario
	ADD CONSTRAINT  PK_MB_GRAFICO_BINARIO PRIMARY KEY (grafico_id);

CREATE TABLE mb_histograma
(
	elemento_id          NUMBER(10) NOT NULL ,
	fila_id              NUMBER(10) NOT NULL ,
	categoria_id         NUMBER(10) NOT NULL ,
	frecuencia           NUMBER(5) NULL 
);

ALTER TABLE mb_histograma
	ADD CONSTRAINT  AK_MB_HISTOGRAMA PRIMARY KEY (elemento_id,categoria_id,fila_id);

CREATE  INDEX IE_MBHISTOGRAMA_ELEMENTOID ON mb_histograma (elemento_id   ASC);

CREATE  INDEX IE_MBHISTOGRAMA_CATEGORIAID ON mb_histograma (categoria_id   ASC);

CREATE TABLE mb_medicion
(
	medicion_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	address_id           NUMBER(10) NULL ,
	fecha                DATE NOT NULL ,
	modalidad            NUMBER(1) NOT NULL ,
	responsable          VARCHAR2(20) NULL ,
	creado               DATE NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado           DATE NULL ,
	modificado_id        NUMBER(10) NULL ,
	criterio_validez     NUMBER(3) NULL ,
	cirterio_desempate   NUMBER(1) NULL ,
	define_prioridad     NUMBER(1) NULL ,
	tipo_sintesis        NUMBER(2) NULL ,
	sintetizado          NUMBER(1) NULL ,
	anonimo              NUMBER(1) NULL ,
	etapa                NUMBER(1) NULL ,
	mensaje_correo       VARCHAR2(4000) NULL 
);

ALTER TABLE mb_medicion
	ADD CONSTRAINT  PK_MB_MEDICION PRIMARY KEY (medicion_id);

CREATE  INDEX IE_MB_MEDICION_FECHA ON mb_medicion (fecha   ASC);

CREATE TABLE mb_medicion_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	medicion_id          NUMBER(10) NOT NULL ,
	apc                  NUMBER(10) NULL ,
	numero_instrumentos_validos NUMBER(4) NULL ,
	numero_instrumentos_novalidos NUMBER(4) NULL ,
	padre_id             NUMBER(10) NULL ,
	variable_ref_id      NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	nivel                NUMBER(4) NULL ,
	porcentaje           FLOAT NULL 
);

ALTER TABLE mb_medicion_variable
	ADD CONSTRAINT  PK_MB_MEDICION_VARIABLE PRIMARY KEY (variable_id);

CREATE  INDEX IE_MB_MED_VAR_MEDICION_ID ON mb_medicion_variable (medicion_id   ASC);

CREATE TABLE mb_numero_encuesta
(
	sector_id            NUMBER(10) NOT NULL ,
	numero               NUMBER(10) NOT NULL 
);

ALTER TABLE mb_numero_encuesta
	ADD CONSTRAINT  PK_MB_NUMERO_ENCUESTA PRIMARY KEY (sector_id,numero);

CREATE TABLE mb_orden_criterio
(
	medicion_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(2) NOT NULL 
);

ALTER TABLE mb_orden_criterio
	ADD CONSTRAINT  PK_MB_ORDEN_CRITERIO PRIMARY KEY (medicion_id,orden);

CREATE TABLE mb_organizacion
(
	organizacion_id      NUMBER(10) NOT NULL ,
	medicion_id          NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	organizacion_ref_id  NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	porcentaje           FLOAT NULL ,
	porcentaje_propio    FLOAT NULL 
);

ALTER TABLE mb_organizacion
	ADD CONSTRAINT  PK_MB_ORGANIZACION PRIMARY KEY (organizacion_id);

ALTER TABLE mb_organizacion ADD CONSTRAINT  AK_MB_ORGANIZACION UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_ORGANIZACION_PADRE_ID ON mb_organizacion (padre_id   ASC);

CREATE TABLE mb_organizacion_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	apc_propio           NUMBER(10) NULL ,
	apc                  NUMBER(10) NULL ,
	numero_instrumentos_validos NUMBER(4) NULL ,
	numero_instrumentos_novalidos NUMBER(4) NULL ,
	indicador_id         NUMBER(10) NULL ,
	padre_id             NUMBER(10) NULL ,
	nivel                NUMBER(4) NULL ,
	variable_red_id      NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NOT NULL 
);

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT  AK_MB_ORGANIZACION_VARIABLE PRIMARY KEY (variable_id,organizacion_id);

CREATE  INDEX IE_MB_VARIABLE_ORGANIZACIONID ON mb_organizacion_variable (organizacion_id   ASC);

CREATE  INDEX IE_mborgvariable_indicadorId ON mb_organizacion_variable (indicador_id   ASC);

CREATE TABLE mb_sector
(
	sector_id            NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	porcentaje           FLOAT NULL ,
	nodo_final           NUMBER(1) NULL ,
	definidos            NUMBER(10) NULL ,
	tamano_muestra       NUMBER(10) NULL 
);

ALTER TABLE mb_sector
	ADD CONSTRAINT  PK_MB_SECTOR PRIMARY KEY (sector_id);

ALTER TABLE mb_sector ADD CONSTRAINT  AK_MB_SECTOR UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_SECTOR_PADRE_ID ON mb_sector (padre_id   ASC);

CREATE TABLE mb_sector_variable
(
	sector_id            NUMBER(10) NOT NULL ,
	variable_id          NUMBER(10) NOT NULL ,
	apc                  NUMBER(10) NULL ,
	numero_instrumentos_validos NUMBER(4) NULL ,
	numero_instrumentos_novalidos NUMBER(4) NULL ,
	estatus              NUMBER(1) NULL ,
	padre_id             NUMBER(10) NULL ,
	nivel                NUMBER(4) NULL ,
	variable_ref_id      NUMBER(10) NULL 
);

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT  PK_MB_SECTOR_VARIABLE PRIMARY KEY (sector_id,variable_id);

CREATE TABLE mb_variable
(
	variable_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	frecuencia           NUMBER(1) NOT NULL ,
	creado               DATE NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado           DATE NULL ,
	modificado_id        NUMBER(10) NULL 
);

ALTER TABLE mb_variable
	ADD CONSTRAINT  PK_MB_VARIABLE PRIMARY KEY (variable_id);

ALTER TABLE mb_variable ADD CONSTRAINT  AK_MB_VARIABLE UNIQUE (nombre);

CREATE TABLE mb_webaddress
(
	address_id           NUMBER(10) NOT NULL ,
	address              VARCHAR2(100) NOT NULL 
);

ALTER TABLE mb_webaddress
	ADD CONSTRAINT  PK_MB_WEBADDRESS PRIMARY KEY (address_id);

ALTER TABLE mb_atributo
	ADD (CONSTRAINT FK_MB_MED_VARIABLE_ATRIBUTO FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_atributo
	ADD (CONSTRAINT FK_MB_SECTOR_ATRIBUTO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON DELETE CASCADE);

ALTER TABLE mb_categoria_criterio
	ADD (CONSTRAINT FK_MB_CRITERIO_CATEGORIA FOREIGN KEY (criterio_id) REFERENCES mb_criterio_estratificacion (criterio_id) ON DELETE CASCADE);

ALTER TABLE mb_categoria_variable
	ADD (CONSTRAINT FK_MB_VARIABLE_CATEGORIA FOREIGN KEY (variable_id) REFERENCES mb_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_categoria_variable
	ADD (CONSTRAINT FK_mdvariable_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id) ON DELETE CASCADE);

ALTER TABLE mb_criterio_estratificacion
	ADD (CONSTRAINT FK_MB_MEDICION_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON DELETE CASCADE);

ALTER TABLE mb_encuesta
	ADD (CONSTRAINT FK_MB_ENCUESTADO_ENCUESTA FOREIGN KEY (encuestado_id) REFERENCES mb_encuestado (encuestado_id) ON DELETE SET NULL);

ALTER TABLE mb_encuesta
	ADD (CONSTRAINT FK_MB_SECTOR_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON DELETE CASCADE);

ALTER TABLE mb_encuesta_atributo
	ADD (CONSTRAINT FK_MB_ENCUESTA_ATRIBUTO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON DELETE CASCADE);

ALTER TABLE mb_encuesta_atributo
	ADD (CONSTRAINT FK_MB_ATRIBUTO_ENCUESTA FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id));

ALTER TABLE mb_encuesta_criterio
	ADD (CONSTRAINT FK_MB_ENCUESTA_CRITERIO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON DELETE CASCADE);

ALTER TABLE mb_encuesta_variable
	ADD (CONSTRAINT FK_MB_MEDICION_ENC_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_encuesta_variable
	ADD (CONSTRAINT FK_MB_ENCUESTA_VARIABLE FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON DELETE CASCADE);

ALTER TABLE mb_encuestado
	ADD (CONSTRAINT FK_MB_SECTOR_ENCUESTADO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id));

ALTER TABLE mb_formato_variable
	ADD (CONSTRAINT FK_MB_MEDICION_VARIABLE_FORMAT FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_grafico_binario
	ADD (CONSTRAINT FK_MB_GRAFICO_BINARIO FOREIGN KEY (grafico_id) REFERENCES mb_grafico (grafico_id) ON DELETE CASCADE);

ALTER TABLE mb_histograma
	ADD (CONSTRAINT FK_mbhistograma_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id) ON DELETE CASCADE);

ALTER TABLE mb_medicion
	ADD (CONSTRAINT FK_MB_WEBADDRESS_MEDICION FOREIGN KEY (address_id) REFERENCES mb_webaddress (address_id) ON DELETE SET NULL);

ALTER TABLE mb_medicion_variable
	ADD (CONSTRAINT FK_MB_MEDICION_VARIABLE FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON DELETE CASCADE);

ALTER TABLE mb_numero_encuesta
	ADD (CONSTRAINT FK_MB_SECTOR_NUMERO_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON DELETE CASCADE);

ALTER TABLE mb_orden_criterio
	ADD (CONSTRAINT FK_MB_MEDICION_ORDEN_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON DELETE CASCADE);

ALTER TABLE mb_organizacion
	ADD (CONSTRAINT FK_MB_MEDICION_ORGANIZACION FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON DELETE CASCADE);

ALTER TABLE mb_organizacion_variable
	ADD (CONSTRAINT FK_MB_MEDICION_ORG_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_organizacion_variable
	ADD (CONSTRAINT FK_organizacion_variable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE mb_organizacion_variable
	ADD (CONSTRAINT FK_mdorgvariable_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE mb_sector
	ADD (CONSTRAINT FK_MB_ORGANIZACION_SECTOR FOREIGN KEY (organizacion_id) REFERENCES mb_organizacion (organizacion_id));

ALTER TABLE mb_sector_variable
	ADD (CONSTRAINT FK_MB_MEDICION_SECTOR_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON DELETE CASCADE);

ALTER TABLE mb_sector_variable
	ADD (CONSTRAINT FK_MB_SECTOR_VARIABLE FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON DELETE CASCADE);

COMMIT;

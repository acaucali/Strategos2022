
CREATE TABLE accion
(
	accion_id            NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	problema_id          NUMBER(10) NOT NULL ,
	estado_id            NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	fecha_estimada_inicio TIMESTAMP NULL ,
	fecha_estimada_final TIMESTAMP NULL ,
	fecha_real_inicio    TIMESTAMP NULL ,
	fecha_real_final     TIMESTAMP NULL ,
	frecuencia           NUMBER(5) NOT NULL ,
	orden                NUMBER(2) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	descripcion          LONG VARCHAR NULL ,
	read_only            NUMBER(1) NULL 
);

CREATE UNIQUE INDEX pk_accion ON accion
(accion_id   ASC);

ALTER TABLE accion
	ADD CONSTRAINT  pk_accion PRIMARY KEY (accion_id);

CREATE UNIQUE INDEX ak_accion ON accion
(problema_id   ASC,padre_id   ASC,nombre   ASC);

ALTER TABLE accion
ADD CONSTRAINT  ak_accion UNIQUE (problema_id,padre_id,nombre);

CREATE  INDEX IE_accion_estado ON accion
(estado_id   ASC);

CREATE  INDEX IE_accion_problema ON accion
(problema_id   ASC);

CREATE  INDEX IE_accion_creado ON accion
(creado_id   ASC);

CREATE  INDEX IE_accion_modificado ON accion
(modificado_id   ASC);


CREATE TABLE afw_auditoria_entero
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                NUMBER(10) NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL,
	valor_anterior 		 NUMBER(10) NULL
);

CREATE UNIQUE INDEX pk_afw_auditoria_entero ON afw_auditoria_entero
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);

ALTER TABLE afw_auditoria_entero
	ADD CONSTRAINT  pk_afw_auditoria_entero PRIMARY KEY (fecha,instancia_id,nombre_atributo);
	
CREATE  INDEX IF_afw_auditoria_entero ON afw_auditoria_entero (usuario_id   ASC);


CREATE TABLE afw_auditoria_evento
(
	objeto_id            NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL ,
	instancia_nombre     VARCHAR2(300) NULL ,
	usuario_id           NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_afw_auditoria_evento ON afw_auditoria_evento
(fecha   ASC,instancia_id   ASC,tipo_evento   ASC);



ALTER TABLE afw_auditoria_evento
	ADD CONSTRAINT  pk_afw_auditoria_evento PRIMARY KEY (fecha,instancia_id,tipo_evento);



CREATE TABLE afw_auditoria_fecha
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                TIMESTAMP NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL,
	valor_anterior 		 TIMESTAMP NULL
);



CREATE UNIQUE INDEX pk_afw_auditoria_fecha ON afw_auditoria_fecha
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);



ALTER TABLE afw_auditoria_fecha
	ADD CONSTRAINT  pk_afw_auditoria_fecha PRIMARY KEY (fecha,instancia_id,nombre_atributo);



CREATE TABLE afw_auditoria_flotante
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                FLOAT NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL,
	valor_anterior 		 FLOAT NULL
);



CREATE UNIQUE INDEX pk_afw_auditoria_flotante ON afw_auditoria_flotante
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);



ALTER TABLE afw_auditoria_flotante
	ADD CONSTRAINT  pk_afw_auditoria_flotante PRIMARY KEY (fecha,instancia_id,nombre_atributo);



CREATE TABLE afw_auditoria_memo
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                VARCHAR2(2000) NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL,
	valor_anterior 		 VARCHAR2(2000) NULL
);

CREATE UNIQUE INDEX pk_afw_auditoria_memo ON afw_auditoria_memo
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);



ALTER TABLE afw_auditoria_memo
	ADD CONSTRAINT  pk_afw_auditoria_memo PRIMARY KEY (fecha,instancia_id,nombre_atributo);



CREATE TABLE afw_auditoria_string
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                VARCHAR2(500) NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL,
	valor_anterior 		 VARCHAR2(500) NULL
);



CREATE UNIQUE INDEX pk_afw_auditoria_string ON afw_auditoria_string
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);



ALTER TABLE afw_auditoria_string
	ADD CONSTRAINT  pk_afw_auditoria_string PRIMARY KEY (fecha,instancia_id,nombre_atributo);



CREATE TABLE afw_config_usuario
(
	usuario_id           NUMBER(10) NOT NULL ,
	configuracion_base   VARCHAR2(200) NOT NULL ,
	objeto               VARCHAR2(100) NOT NULL ,
	data                 LONG VARCHAR NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_config_usuario ON afw_config_usuario
(usuario_id   ASC,objeto   ASC,configuracion_base   ASC);



ALTER TABLE afw_config_usuario
	ADD CONSTRAINT  pk_afw_config_usuario PRIMARY KEY (usuario_id,objeto,configuracion_base);



CREATE  INDEX xif1afw_config_usuario ON afw_config_usuario
(usuario_id   ASC);



CREATE TABLE afw_configuracion
(
	parametro            VARCHAR2(200) NOT NULL ,
	valor                LONG VARCHAR NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_configuracion ON afw_configuracion
(parametro   ASC);



ALTER TABLE afw_configuracion
	ADD CONSTRAINT  pk_afw_configuracion PRIMARY KEY (parametro);



CREATE TABLE afw_error
(
	err_number           NUMBER(10) NOT NULL ,
	err_source           VARCHAR2(2000) NOT NULL ,
	err_description      VARCHAR2(2000) NULL ,
	err_timestamp        TIMESTAMP NOT NULL ,
	err_user_id          VARCHAR2(20) NOT NULL ,
	err_version          VARCHAR2(20) NULL ,
	err_step             VARCHAR2(20) NULL ,
	err_stacktrace       LONG VARCHAR NULL ,
	err_cause            VARCHAR2(2000) NULL 
);



CREATE UNIQUE INDEX XPKafw_error ON afw_error
(err_number   ASC,err_timestamp   ASC,err_user_id   ASC,err_source   ASC);



ALTER TABLE afw_error
	ADD CONSTRAINT  XPKafw_error PRIMARY KEY (err_number,err_timestamp,err_user_id,err_source);



CREATE TABLE afw_grupo
(
	grupo_id             NUMBER(10) NOT NULL ,
	grupo                VARCHAR2(50) NOT NULL ,
	creado               TIMESTAMP NOT NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NOT NULL ,
	modificado_id        NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_afw_grupo ON afw_grupo
(grupo_id   ASC);



ALTER TABLE afw_grupo
	ADD CONSTRAINT  pk_afw_grupo PRIMARY KEY (grupo_id);



CREATE UNIQUE INDEX ak1_afw_grupo ON afw_grupo
(grupo   ASC);



ALTER TABLE afw_grupo
ADD CONSTRAINT  ak1_afw_grupo UNIQUE (grupo);



CREATE TABLE afw_lock
(
	objeto_id            NUMBER(10) NOT NULL ,
	tipo                 VARCHAR2(1) NOT NULL ,
	instancia            VARCHAR2(50) NULL ,
	time_stamp           TIMESTAMP NULL 
);



CREATE UNIQUE INDEX pk_afw_lock ON afw_lock
(objeto_id   ASC,tipo   ASC);



ALTER TABLE afw_lock
	ADD CONSTRAINT  pk_afw_lock PRIMARY KEY (objeto_id,tipo);



CREATE TABLE afw_lock_read
(
	objeto_id            NUMBER(10) NOT NULL ,
	instancia            VARCHAR2(50) NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_lock_read ON afw_lock_read
(objeto_id   ASC,instancia   ASC);



ALTER TABLE afw_lock_read
	ADD CONSTRAINT  pk_afw_lock_read PRIMARY KEY (objeto_id,instancia);



CREATE TABLE afw_message_resource
(
	clave                VARCHAR2(200) NOT NULL ,
	valor                VARCHAR2(2000) NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_message_resource ON afw_message_resource
(clave   ASC);



ALTER TABLE afw_message_resource
	ADD CONSTRAINT  pk_afw_message_resource PRIMARY KEY (clave);



CREATE TABLE afw_objeto_auditable
(
	objeto_id            NUMBER(10) NOT NULL ,
	nombre_clase         VARCHAR2(200) NULL ,
	nombre_campo_id      VARCHAR2(100) NULL ,
	nombre_campo_nombre  VARCHAR2(100) NULL ,
	auditoria_activa     NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_afw_objeto_auditable ON afw_objeto_auditable
(objeto_id   ASC);



ALTER TABLE afw_objeto_auditable
	ADD CONSTRAINT  pk_afw_objeto_auditable PRIMARY KEY (objeto_id);



CREATE TABLE afw_objeto_auditable_atributo
(
	objeto_id            NUMBER(10) NOT NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	configuracion		 VARCHAR2(2000) NULL
);


CREATE UNIQUE INDEX pk_afw_objeto_auditable_atribu ON afw_objeto_auditable_atributo
(objeto_id   ASC,nombre_atributo   ASC);



ALTER TABLE afw_objeto_auditable_atributo
	ADD CONSTRAINT  pk_afw_objeto_auditable_atribu PRIMARY KEY (objeto_id,nombre_atributo);


CREATE TABLE afw_objeto_binario
(
	objeto_binario_id    NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(200) NOT NULL ,
	mime_type            VARCHAR2(200) NULL ,
	data                 BLOB NULL 
);

CREATE UNIQUE INDEX pk_afw_objeto_binario ON afw_objeto_binario (objeto_binario_id   ASC);

ALTER TABLE afw_objeto_binario ADD CONSTRAINT pk_afw_objeto_binario PRIMARY KEY (objeto_binario_id);	
	

CREATE TABLE afw_objeto_sistema
(
	objeto_id            VARCHAR2(50) NOT NULL ,
	art_singular         VARCHAR2(10) NOT NULL ,
	nombre_singular      VARCHAR2(100) NOT NULL ,
	art_plural           VARCHAR2(20) NOT NULL ,
	nombre_plural        VARCHAR2(100) NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_objeto_sistema ON afw_objeto_sistema
(objeto_id   ASC);



ALTER TABLE afw_objeto_sistema
	ADD CONSTRAINT  pk_afw_objeto_sistema PRIMARY KEY (objeto_id);



CREATE UNIQUE INDEX ak1_afw_objeto_sistema ON afw_objeto_sistema
(nombre_singular   ASC);



ALTER TABLE afw_objeto_sistema
ADD CONSTRAINT  ak1_afw_objeto_sistema UNIQUE (nombre_singular);



CREATE TABLE afw_permiso
(
	permiso_id           VARCHAR2(50) NOT NULL ,
	padre_id             VARCHAR2(50) NULL ,
	permiso              VARCHAR2(50) NOT NULL ,
	nivel                NUMBER(2) NULL ,
	grupo                NUMBER(2) NULL ,
	global               NUMBER(1) NULL ,
	descripcion          VARCHAR2(2000) NULL 
);



CREATE UNIQUE INDEX pk_afw_permiso ON afw_permiso
(permiso_id   ASC);



ALTER TABLE afw_permiso
	ADD CONSTRAINT  pk_afw_permiso PRIMARY KEY (permiso_id);



CREATE UNIQUE INDEX ak1_afw_permiso ON afw_permiso
(padre_id   ASC,permiso_id   ASC);



ALTER TABLE afw_permiso
ADD CONSTRAINT  ak1_afw_permiso UNIQUE (padre_id,permiso_id);



CREATE  INDEX xif1afw_permiso ON afw_permiso
(padre_id   ASC);



CREATE TABLE afw_permiso_grupo
(
	permiso_id           VARCHAR2(50) NOT NULL ,
	grupo_id             NUMBER(10) NOT NULL 
);



CREATE UNIQUE INDEX pk_afw_permiso_grupo ON afw_permiso_grupo
(permiso_id   ASC,grupo_id   ASC);



ALTER TABLE afw_permiso_grupo
	ADD CONSTRAINT  pk_afw_permiso_grupo PRIMARY KEY (permiso_id,grupo_id);



CREATE  INDEX xif1afw_permiso_grupo ON afw_permiso_grupo
(permiso_id   ASC);



CREATE  INDEX xif2afw_permiso_grupo ON afw_permiso_grupo
(grupo_id   ASC);



CREATE TABLE afw_plantilla
(
	plantilla_id         NUMBER(10) NOT NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre               VARCHAR2(100) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	publico              NUMBER(1) NULL ,
	tipo                 VARCHAR2(20) NULL ,
	xml                  LONG VARCHAR NULL ,
	usuario_id           NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_afw_plantilla ON afw_plantilla
(plantilla_id   ASC);



ALTER TABLE afw_plantilla
	ADD CONSTRAINT  pk_afw_plantilla PRIMARY KEY (plantilla_id);



CREATE  INDEX xif1afw_plantilla ON afw_plantilla
(usuario_id   ASC);



CREATE TABLE afw_sistema
(
	id                   NUMBER(10) NOT NULL ,
	version              VARCHAR2(20) NULL ,
	build				 NUMBER(10) NULL,
	fecha				 TIMESTAMP NULL ,
	rdbms_id             VARCHAR2(20) NULL ,
	producto             VARCHAR2(50) NULL,
	cmaxc 				 VARCHAR2(1000) NULL,
	conexion			 VARCHAR2(50) NULL,
	actual			 	 VARCHAR2(50) NULL
);


CREATE UNIQUE INDEX XPKafw_sistema ON afw_sistema
(id   ASC);



ALTER TABLE afw_sistema
	ADD CONSTRAINT  XPKafw_sistema PRIMARY KEY (id);



CREATE TABLE afw_user_session
(
	session_id           VARCHAR2(100) NOT NULL ,
	usuario_id           NUMBER(10) NOT NULL ,
	login_ts             TIMESTAMP NOT NULL ,
	remote_address       VARCHAR2(200) NULL ,
	remote_host          VARCHAR2(200) NULL ,
	remote_user          VARCHAR2(1000) NULL ,
	url                  VARCHAR2(100) NULL ,
	persona_id           NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_afw_usersession ON afw_user_session
(session_id   ASC);



ALTER TABLE afw_user_session
	ADD CONSTRAINT  pk_afw_usersession PRIMARY KEY (session_id);



CREATE  INDEX xif1afw_usersession ON afw_user_session
(usuario_id   ASC);



CREATE TABLE afw_usuario
(
	usuario_id           NUMBER(10) NOT NULL ,
	full_name            VARCHAR2(50) NOT NULL ,
	u_id                 VARCHAR2(50) NOT NULL ,
	pwd                  VARCHAR2(100) NOT NULL ,
	is_admin             NUMBER(1) NULL ,
	is_connected         NUMBER(1) NULL ,
	is_system         	 NUMBER(1) NULL ,
	time_stamp           TIMESTAMP NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	instancia            VARCHAR2(40) NULL ,
	inactivo             NUMBER(1) NULL ,
	estatus              NUMBER(1) NULL ,
	bloqueado            NUMBER(1) NULL ,
	ultima_modif_pwd 	 TIMESTAMP NULL,
	deshabilitado		 NUMBER(1) NULL,
	forzarCambiarPwd	 NUMBER(1) NULL
);



CREATE UNIQUE INDEX pk_afw_usuario ON afw_usuario
(usuario_id   ASC);



ALTER TABLE afw_usuario
	ADD CONSTRAINT  pk_afw_usuario PRIMARY KEY (usuario_id);



CREATE UNIQUE INDEX ak1_afw_usuario ON afw_usuario
(u_id   ASC);



ALTER TABLE afw_usuario
ADD CONSTRAINT  ak1_afw_usuario UNIQUE (u_id);


CREATE TABLE afw_pwd_historia
(
	usuario_id           NUMBER(10) NOT NULL ,
	pwd                  VARCHAR2(100) NULL ,
	fecha                TIMESTAMP NOT NULL 
);

CREATE UNIQUE INDEX PK_afw_pwd_historia ON afw_pwd_historia
(usuario_id   ASC,fecha   ASC);

ALTER TABLE afw_pwd_historia
	ADD CONSTRAINT  PK_afw_pwd_historia PRIMARY KEY (usuario_id,fecha);

CREATE  INDEX IF_afw_pwd_historia ON afw_pwd_historia
(usuario_id   ASC);

ALTER TABLE afw_pwd_historia
	ADD (CONSTRAINT FK_USER_PWDHISTORIA FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id));


CREATE TABLE afw_usuario_grupo
(
	usuario_id           NUMBER(10) NOT NULL ,
	grupo_id             NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	modificado           TIMESTAMP NULL ,
	modificado_id        NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_afw_usuario_grupo ON afw_usuario_grupo
(usuario_id   ASC,grupo_id   ASC,organizacion_id   ASC);



ALTER TABLE afw_usuario_grupo
	ADD CONSTRAINT  pk_afw_usuario_grupo PRIMARY KEY (usuario_id,grupo_id,organizacion_id);



CREATE  INDEX xif1afw_usuario_grupo ON afw_usuario_grupo
(usuario_id   ASC);



CREATE  INDEX xif2afw_usuario_grupo ON afw_usuario_grupo
(grupo_id   ASC);



CREATE  INDEX xif3afw_usuario_grupo ON afw_usuario_grupo
(organizacion_id   ASC);



CREATE TABLE afw_usuarioweb
(
	usuario_id           NUMBER(10) NOT NULL ,
	sessionweb           VARCHAR2(50) NOT NULL ,
	time_stamp_conex     TIMESTAMP NOT NULL 
);



CREATE UNIQUE INDEX XPKafw_usuarioweb ON afw_usuarioweb
(usuario_id   ASC);



ALTER TABLE afw_usuarioweb
	ADD CONSTRAINT  XPKafw_usuarioweb PRIMARY KEY (usuario_id);



CREATE TABLE categoria
(
	categoria_id         NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL 
);



CREATE UNIQUE INDEX pk_categoria ON categoria
(categoria_id   ASC);



ALTER TABLE categoria
	ADD CONSTRAINT  pk_categoria PRIMARY KEY (categoria_id);



CREATE UNIQUE INDEX ak1_categoria ON categoria
(nombre   ASC);



ALTER TABLE categoria
ADD CONSTRAINT  ak1_categoria UNIQUE (nombre);



CREATE TABLE categoria_por_indicador
(
	indicador_id         NUMBER(10) NOT NULL ,
	categoria_id         NUMBER(10) NOT NULL ,
	orden                NUMBER(4) NULL 
);



CREATE UNIQUE INDEX pk_categoria_por_indicador ON categoria_por_indicador
(indicador_id   ASC,categoria_id   ASC);



ALTER TABLE categoria_por_indicador
	ADD CONSTRAINT  pk_categoria_por_indicador PRIMARY KEY (indicador_id,categoria_id);



CREATE  INDEX xif1categoria_por_indicador ON categoria_por_indicador
(indicador_id   ASC);



CREATE  INDEX xif2categoria_por_indicador ON categoria_por_indicador
(categoria_id   ASC);



CREATE TABLE causa
(
	causa_id             NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          LONG VARCHAR NULL ,
	nivel                NUMBER(2) NULL 
);



CREATE UNIQUE INDEX pk_causa ON causa
(causa_id   ASC);



ALTER TABLE causa
	ADD CONSTRAINT  pk_causa PRIMARY KEY (causa_id);



CREATE UNIQUE INDEX ak1_causa ON causa
(padre_id   ASC,nombre   ASC);



ALTER TABLE causa
ADD CONSTRAINT  ak1_causa UNIQUE (padre_id,nombre);



CREATE  INDEX if_causa_padre_id ON causa
(padre_id   ASC);



CREATE TABLE causa_por_problema
(
	problema_id          NUMBER(10) NOT NULL ,
	causa_id             NUMBER(10) NOT NULL ,
	orden                NUMBER(5) NULL 
);



CREATE UNIQUE INDEX pk_causa_por_problema ON causa_por_problema
(problema_id   ASC,causa_id   ASC);



ALTER TABLE causa_por_problema
	ADD CONSTRAINT  pk_causa_por_problema PRIMARY KEY (problema_id,causa_id);



CREATE  INDEX xif1causa_por_problema ON causa_por_problema
(problema_id   ASC);



CREATE  INDEX xif2causa_por_problema ON causa_por_problema
(causa_id   ASC);


CREATE TABLE celda
(
	celda_id             NUMBER(10) NOT NULL ,
	pagina_id            NUMBER(10) NOT NULL ,
	titulo               VARCHAR2(100) NULL ,
	fila                 NUMBER(2) NOT NULL ,
	columna              NUMBER(2) NOT NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	configuracion        LONG VARCHAR NULL 
);

CREATE UNIQUE INDEX ie_celda ON celda (celda_id   ASC);
CREATE UNIQUE INDEX ie_celda_pagina_celda ON celda (pagina_id   ASC,fila   ASC,columna   ASC);
CREATE  INDEX ie_celda_pagina ON celda (pagina_id   ASC);
ALTER TABLE celda ADD CONSTRAINT  pk_celda_pagina_celda UNIQUE (pagina_id,fila,columna);
ALTER TABLE celda ADD CONSTRAINT  pk_celda PRIMARY KEY (celda_id);

CREATE TABLE clase
(
	clase_id             NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NULL ,
	nombre               VARCHAR2(310) NOT NULL ,
	descripcion          VARCHAR2(250) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	modificado_id        NUMBER(10) NULL ,
	creado_id            NUMBER(10) NULL ,
	enlace_parcial       VARCHAR2(100) NULL ,
	tipo                 NUMBER(2) NULL ,
	visible              NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_clase ON clase
(clase_id   ASC);



ALTER TABLE clase
	ADD CONSTRAINT  pk_clase PRIMARY KEY (clase_id);



CREATE UNIQUE INDEX ak1_clase ON clase (padre_id   ASC,organizacion_id   ASC,nombre   ASC);



ALTER TABLE clase
ADD CONSTRAINT  ak1_clase UNIQUE (padre_id,organizacion_id,nombre);



CREATE  INDEX xif1clase ON clase
(organizacion_id   ASC);



CREATE  INDEX xif2clase ON clase
(padre_id   ASC);



CREATE TABLE clase_problema
(
	clase_id             NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	tipo        		 NUMBER(1) NOT NULL
);

CREATE UNIQUE INDEX pk_clase_problema ON clase_problema
(clase_id   ASC);

ALTER TABLE clase_problema
	ADD CONSTRAINT  pk_clase_problema PRIMARY KEY (clase_id);

CREATE UNIQUE INDEX ak_clase_problema ON clase_problema
(organizacion_id   ASC,padre_id   ASC,nombre   ASC);

ALTER TABLE clase_problema
ADD CONSTRAINT  ak_clase_problema UNIQUE (organizacion_id,padre_id,nombre);

CREATE  INDEX IE_padre_claseproblema ON clase_problema
(padre_id   ASC);

CREATE  INDEX IE_organizacion_claseproblema ON clase_problema
(organizacion_id   ASC);

CREATE  INDEX IE_creado_claseproblema ON clase_problema
(creado_id   ASC);

CREATE  INDEX IE_modificado_claseproblema ON clase_problema
(modificado_id   ASC);

CREATE TABLE conjunto_formula
(
	padre_id             NUMBER(10) NOT NULL ,
	serie_id             NUMBER(10) NOT NULL ,
	insumo_serie_id      NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX ak_conjunto_formula ON conjunto_formula (insumo_serie_id   ASC,indicador_id   ASC,padre_id   ASC,serie_id   ASC);

ALTER TABLE conjunto_formula ADD CONSTRAINT pk_conjunto_formula PRIMARY KEY (insumo_serie_id, indicador_id, padre_id, serie_id);

CREATE  INDEX ie_conjunto_formula_padreid ON conjunto_formula (padre_id   ASC);

CREATE  INDEX ie_conjunto_formula_indid ON conjunto_formula (indicador_id   ASC);

CREATE  INDEX ie_conjunto_formula_serieid ON conjunto_formula (serie_id   ASC);

CREATE  INDEX ie_conjunto_formula_insserieid ON conjunto_formula (insumo_serie_id   ASC);

CREATE TABLE cuenta
(
	cuenta_id            NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	codigo               VARCHAR2(10) NOT NULL ,
	descripcion          VARCHAR2(250) NOT NULL 
);



CREATE UNIQUE INDEX pk_cuenta ON cuenta
(cuenta_id   ASC);



ALTER TABLE cuenta
	ADD CONSTRAINT  pk_cuenta PRIMARY KEY (cuenta_id);



CREATE UNIQUE INDEX ak1_cuenta ON cuenta
(padre_id   ASC,codigo   ASC);



ALTER TABLE cuenta
ADD CONSTRAINT  ak1_cuenta UNIQUE (padre_id,codigo);



CREATE  INDEX if_cuenta_padre_id ON cuenta
(padre_id   ASC);



CREATE TABLE estado_acciones
(
	estado_id            NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(15) NOT NULL ,
	aplica_seguimiento   NUMBER(1) NULL ,
	orden                NUMBER(4) NULL ,
	condicion            NUMBER(4) NULL 
);



CREATE UNIQUE INDEX pk_estado_acciones ON estado_acciones
(estado_id   ASC);



ALTER TABLE estado_acciones
	ADD CONSTRAINT  pk_estado_acciones PRIMARY KEY (estado_id);



CREATE UNIQUE INDEX ak1_estado_acciones ON estado_acciones
(nombre   ASC);



ALTER TABLE estado_acciones
ADD CONSTRAINT  ak1_estado_acciones UNIQUE (nombre);



CREATE TABLE explicacion
(
	explicacion_id       NUMBER(10) NOT NULL ,
	creado_id            NUMBER(10) NULL ,
	creado               TIMESTAMP NULL ,
	objeto_id            NUMBER(10) NOT NULL ,
	objeto_key           NUMBER(2) NOT NULL ,
	titulo               VARCHAR2(250) NOT NULL ,
	fecha                TIMESTAMP NULL,
	tipo            	 NUMBER(1) NOT NULL,
	fecha_compromiso     TIMESTAMP NULL,
	fecha_real           TIMESTAMP NULL,
	publico           	 NUMBER(1) NOT NULL
);



CREATE UNIQUE INDEX pk_explicacion ON explicacion
(explicacion_id   ASC);



ALTER TABLE explicacion
	ADD CONSTRAINT  pk_explicacion PRIMARY KEY (explicacion_id);



CREATE TABLE explicacion_adjunto
(
	explicacion_id       NUMBER(10) NOT NULL ,
	adjunto_id           NUMBER(10) NOT NULL ,
	documento            LONG VARCHAR NULL ,
	mime_type            VARCHAR2(50) NULL ,
	titulo               VARCHAR2(100) NULL 
);



CREATE UNIQUE INDEX pk_explicacion_adjunto ON explicacion_adjunto
(explicacion_id   ASC,adjunto_id   ASC);



ALTER TABLE explicacion_adjunto
	ADD CONSTRAINT  pk_explicacion_adjunto PRIMARY KEY (explicacion_id,adjunto_id);



CREATE  INDEX xif1explicacion_adjunto ON explicacion_adjunto
(explicacion_id   ASC);



CREATE TABLE explicacion_memo
(
	explicacion_id       NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	memo                 LONG VARCHAR NULL 
);



CREATE UNIQUE INDEX pk_explicacion_memo ON explicacion_memo
(explicacion_id   ASC,tipo   ASC);



ALTER TABLE explicacion_memo
	ADD CONSTRAINT  pk_explicacion_memo PRIMARY KEY (explicacion_id,tipo);



CREATE  INDEX xif1explicacion_memo ON explicacion_memo
(explicacion_id   ASC);



CREATE TABLE formula
(
	indicador_id         NUMBER(10) NOT NULL ,
	serie_id             NUMBER(10) NOT NULL ,
	expresion            VARCHAR2(4000) NULL 
);

CREATE UNIQUE INDEX ak_formula ON formula (indicador_id ASC,serie_id ASC);

ALTER TABLE FORMULA ADD CONSTRAINT pk_formula PRIMARY KEY (indicador_id, serie_id);

CREATE  INDEX ie_formula_indicadorid ON formula (indicador_id   ASC);

CREATE  INDEX ie_formula_serie ON formula (serie_id   ASC);


CREATE TABLE foro
(
	foro_id              NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	creado_id            NUMBER(10) NULL ,
	creado               TIMESTAMP NULL ,
	objeto_id            NUMBER(10) NOT NULL ,
	objeto_key           NUMBER(2) NOT NULL ,
	asunto               VARCHAR2(100) NULL ,
	email                VARCHAR2(100) NULL ,
	comentario           LONG VARCHAR NOT NULL ,
	modificado_id        NUMBER(10) NULL ,
	modificado           TIMESTAMP NULL ,
	tipo                 NUMBER(1) NOT NULL 
);



CREATE UNIQUE INDEX pk_foro ON foro
(foro_id   ASC);



ALTER TABLE foro
	ADD CONSTRAINT  pk_foro PRIMARY KEY (foro_id);



CREATE  INDEX xif1foro ON foro
(padre_id   ASC);



CREATE TABLE grafico
(
	grafico_id           NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(100) NOT NULL ,
	configuracion        LONG VARCHAR NOT NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	className            VARCHAR2(50) NULL 
);



CREATE UNIQUE INDEX pk_grafico ON grafico
(grafico_id   ASC);



ALTER TABLE grafico
	ADD CONSTRAINT  pk_grafico PRIMARY KEY (grafico_id);



CREATE UNIQUE INDEX ak_grafico ON grafico
(organizacion_id   ASC,usuario_id   ASC,nombre   ASC,objeto_id   ASC);


ALTER TABLE grafico
ADD CONSTRAINT  ak_grafico UNIQUE (organizacion_id,usuario_id,nombre,objeto_id);


CREATE TABLE inc_actividad
(
	actividad_id         NUMBER(10) NOT NULL ,
	producto_esperado    VARCHAR2(2000) NULL ,
	recurso_humano       VARCHAR2(2000) NULL ,
	recurso_material     VARCHAR2(2000) NULL ,
	peso                 FLOAT NULL ,
	alerta_za  			 FLOAT NULL ,
	alerta_zv     		 FLOAT NULL ,
	porcentaje_ejecutado FLOAT NULL ,
	porcentaje_esperado  FLOAT NULL ,
	porcentaje_completado FLOAT NULL ,
	alerta               NUMBER(4) NULL 
);



CREATE UNIQUE INDEX pk_inc_actividad ON inc_actividad
(actividad_id   ASC);



ALTER TABLE inc_actividad
	ADD CONSTRAINT  pk_inc_actividad PRIMARY KEY (actividad_id);

CREATE TABLE indicador
(
	indicador_id         NUMBER(10) NOT NULL ,
	alerta_n2_ind_id     NUMBER(10) NULL ,
	alerta_n1_ind_id     NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NULL ,
	clase_id             NUMBER(10) NULL ,
	nombre               VARCHAR2(150) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	naturaleza           NUMBER(1) NOT NULL ,
	frecuencia           NUMBER(1) NOT NULL ,
	guia                 VARCHAR2(50) NULL ,
	unidad_id            NUMBER(10) NULL ,
	codigo_enlace        VARCHAR2(100) NULL ,
	nombre_corto         VARCHAR2(150) NOT NULL ,
	prioridad            NUMBER(1) NOT NULL ,
	constante            NUMBER(1) NOT NULL ,
	fuente               VARCHAR2(2000) NULL ,
	orden                VARCHAR2(10) NULL ,
	read_only            NUMBER(1) NULL ,
	caracteristica       NUMBER(1) NULL ,
	crecimiento_ind      NUMBER(1) NULL ,
	fecha_ultima_medicion VARCHAR2(10) NULL ,
	ultima_medicion      FLOAT NULL ,
	resp_fijar_meta_id   NUMBER(10) NULL ,
	resp_lograr_meta_id  NUMBER(10) NULL ,
	resp_seguimiento_id  NUMBER(10) NULL ,
	resp_cargar_meta_id  NUMBER(10) NULL ,
	resp_cargar_ejecutado_id NUMBER(10) NULL ,
	lag_lead             NUMBER(1) NULL ,
	corte                NUMBER(1) NULL ,
	enlace_parcial       VARCHAR2(100) NULL ,
	alerta_min_max       NUMBER(2) NULL ,
	alerta_meta_n1       FLOAT NULL ,
	alerta_meta_n2       FLOAT NULL ,
	intranet             NUMBER(1) NULL ,
	alerta_n1_tipo       NUMBER(1) NULL ,
	alerta_n2_tipo       NUMBER(1) NULL ,
	alerta_n1_fv         NUMBER(1) NULL ,
	alerta_n2_fv         NUMBER(1) NULL ,
	valor_inicial_cero   NUMBER(1) NULL ,
	mascara_decimales    NUMBER(2) NULL ,
	tipo_medicion        NUMBER(1) NULL ,
	url                  VARCHAR2(500) NULL ,
	fecha_bloqueo_ejecutado TIMESTAMP NULL ,
	fecha_bloqueo_meta   TIMESTAMP NULL ,
	psuperior_id         NUMBER(10) NULL ,
	pinferior_id         NUMBER(10) NULL ,
	psuperior_vf         FLOAT NULL ,
	pinferior_vf         FLOAT NULL ,
	multidimensional     NUMBER(1) NULL ,
	tipo                 NUMBER(1) NULL ,
	tipo_asociado        NUMBER(1) NULL ,
	asociado_id          NUMBER(10) NULL ,
	revision             NUMBER(3) NULL ,
	ultimo_programado    FLOAT NULL ,
	tipo_suma            NUMBER(1) NULL, 
	asignar_Inventario	 NUMBER(1) NULL
);

CREATE UNIQUE INDEX ak_indicador ON indicador (indicador_id   ASC);

ALTER TABLE indicador ADD CONSTRAINT  pk_indicador PRIMARY KEY (indicador_id);

CREATE UNIQUE INDEX ak_indicador_clase_nombre ON indicador (clase_id   ASC,nombre   ASC);

ALTER TABLE indicador ADD CONSTRAINT  pk_indicador_clase_nombre UNIQUE (clase_id, nombre);

CREATE  INDEX ie_indicador_respfijarmeta ON indicador (resp_fijar_meta_id   ASC);

CREATE  INDEX ie_indicador_resplograrmeta ON indicador (resp_lograr_meta_id   ASC);

CREATE  INDEX ie_indicador_resseguimiento ON indicador (resp_seguimiento_id   ASC);

CREATE  INDEX ie_indicador_rescargameta ON indicador (resp_cargar_meta_id   ASC);

CREATE  INDEX ie_indicador_resejecutado ON indicador (resp_cargar_ejecutado_id   ASC);

CREATE  INDEX ie_indicador_claseid ON indicador (clase_id   ASC);

CREATE  INDEX ie_indicador_organizacion ON indicador (organizacion_id   ASC);

CREATE  INDEX ie_indicador_unidad ON indicador (unidad_id   ASC);


CREATE TABLE indicador_estado
(
	plan_id              NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	estado               FLOAT NULL 
);

CREATE UNIQUE INDEX pk_indicador_estado ON indicador_estado (plan_id   ASC,indicador_id   ASC,tipo   ASC,ano   ASC,periodo   ASC);

ALTER TABLE indicador_estado 
	ADD CONSTRAINT pk_indicador_estado PRIMARY KEY (plan_id,indicador_id,tipo,ano,periodo);

CREATE INDEX ie_indicadorid_indicador_estad ON indicador_estado (indicador_id ASC);
CREATE INDEX ie_planid_indicador_estado ON indicador_estado (plan_id ASC);  


CREATE TABLE indicador_por_celda
(
	celda_id             NUMBER(10) NOT NULL ,
	serie_id             NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	serie_color          VARCHAR2(20) NULL ,
	serie_estilo         NUMBER(1) NULL ,
	leyenda              VARCHAR2(20) NULL ,
	plan_id              NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_indicador_por_celda ON indicador_por_celda
(celda_id   ASC,serie_id   ASC,indicador_id   ASC);



ALTER TABLE indicador_por_celda
	ADD CONSTRAINT  pk_indicador_por_celda PRIMARY KEY (celda_id,serie_id,indicador_id);



CREATE  INDEX xif1indicador_por_celda ON indicador_por_celda
(celda_id   ASC);



CREATE  INDEX xif2indicador_por_celda ON indicador_por_celda
(serie_id   ASC,indicador_id   ASC);



CREATE  INDEX xif3indicador_por_celda ON indicador_por_celda
(plan_id   ASC);



CREATE TABLE indicador_por_perspectiva
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	peso                 FLOAT NULL 
);



CREATE UNIQUE INDEX pk_indicador_por_perspectiva ON indicador_por_perspectiva
(perspectiva_id   ASC,indicador_id   ASC);



ALTER TABLE indicador_por_perspectiva
	ADD CONSTRAINT  pk_indicador_por_perspectiva PRIMARY KEY (perspectiva_id,indicador_id);



CREATE  INDEX xif1indicador_por_perspectiva ON indicador_por_perspectiva
(perspectiva_id   ASC);



CREATE  INDEX xif2indicador_por_perspectiva ON indicador_por_perspectiva
(indicador_id   ASC);



CREATE TABLE indicador_por_plan
(
	plan_id              NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	peso                 FLOAT NULL,
    crecimiento			 NUMBER(1),
    tipo_medicion	     NUMBER(1)
);

CREATE UNIQUE INDEX pk_indicador_por_plan ON indicador_por_plan (plan_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_plan ADD CONSTRAINT pk_indicador_por_plan PRIMARY KEY (plan_id,indicador_id);

CREATE INDEX ie_indicadorid_indicador_por_p ON indicador_por_plan (indicador_id ASC);
CREATE INDEX ie_planid_indicador_por_plan ON indicador_por_plan (plan_id ASC);

CREATE TABLE iniciativa_estatus
(
	id                   NUMBER(10) NOT NULL ,
	nombre               VARCHAR(50) NOT NULL ,
	porcentaje_inicial   FLOAT NULL ,
	porcentaje_final     FLOAT NULL,
    sistema				 NUMBER(1) NOT NULL,
	bloquear_medicion	 NUMBER(1) NOT NULL,
	bloquear_indicadores NUMBER(1) NOT NULL
);

CREATE UNIQUE INDEX IE_Iniciativa_Estatus ON iniciativa_estatus (id   ASC);
CREATE UNIQUE INDEX AK_iniciativa_estatus ON iniciativa_estatus (nombre   ASC);

ALTER TABLE iniciativa_estatus ADD CONSTRAINT  PK_Iniciativa_Estatus PRIMARY KEY (id);
ALTER TABLE iniciativa_estatus ADD CONSTRAINT  PK_iniciativa_estatus_nombre UNIQUE (nombre);

CREATE TABLE iniciativa
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NULL ,
	nombre               VARCHAR2(250) NOT NULL ,
	nombre_largo         VARCHAR2(300) NULL ,
	alerta_zv 			 FLOAT NULL ,
	alerta_za 			 FLOAT NULL ,
	frecuencia           NUMBER(2) NOT NULL ,
	tipo_alerta          NUMBER(2) NOT NULL ,
	contratista          VARCHAR2(50) NULL ,
	naturaleza           NUMBER(1) NOT NULL ,
	resp_fijar_meta_id   NUMBER(10) NULL ,
	resp_lograr_meta_id  NUMBER(10) NULL ,
	resp_seguimiento_id  NUMBER(10) NULL ,
	resp_cargar_meta_id  NUMBER(10) NULL ,
	resp_cargar_ejecutado_id NUMBER(10) NULL ,
	proyecto_id          NUMBER(10) NULL ,
	visible              NUMBER(1) NULL ,
	clase_id             NUMBER(10) NULL ,
	read_only            NUMBER(1) NULL ,
	porcentaje_completado FLOAT NULL,
	crecimiento			 NUMBER(1),
	fecha_ultima_medicion VARCHAR2(10) NULL,
	tipo_medicion 		NUMBER(1) NOT NULL,
	historico_date		TIMESTAMP,
	estatusId            NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX pk_iniciativa ON iniciativa (iniciativa_id   ASC);

ALTER TABLE iniciativa ADD CONSTRAINT  pk_iniciativa PRIMARY KEY (iniciativa_id);

CREATE UNIQUE INDEX ak_iniciativa ON iniciativa (organizacion_id   ASC, nombre   ASC);

ALTER TABLE iniciativa ADD CONSTRAINT  ak_iniciativa UNIQUE (organizacion_id, nombre);

CREATE  INDEX IE_iniciativa_organizacionid ON iniciativa (organizacion_id   ASC);
CREATE  INDEX IE_iniciativa_proyecto ON iniciativa (proyecto_id   ASC);
CREATE  INDEX IE_iniciativa_clase ON iniciativa (clase_id   ASC);
CREATE  INDEX IE_iniciativa_resp_ejecutar ON iniciativa (resp_cargar_ejecutado_id   ASC);
CREATE  INDEX IE_iniciativa_resp_meta ON iniciativa (resp_fijar_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_lograr ON iniciativa (resp_lograr_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_seguimiento ON iniciativa (resp_seguimiento_id   ASC);
CREATE  INDEX IE_iniciativa_resp_cargar ON iniciativa (resp_cargar_meta_id   ASC);
CREATE  INDEX IE_iniciativa_estatusId ON iniciativa (estatusId   ASC);

CREATE TABLE iniciativa_ano
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	resultado            LONG VARCHAR NULL 
);

CREATE UNIQUE INDEX pk_iniciativa_ano ON iniciativa_ano
(iniciativa_id   ASC,ano   ASC);

ALTER TABLE iniciativa_ano
	ADD CONSTRAINT  pk_iniciativa_ano PRIMARY KEY (iniciativa_id,ano);

CREATE  INDEX IE_iniciativaId_ano ON iniciativa_ano
(iniciativa_id   ASC);

CREATE TABLE iniciativa_objeto
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	objeto               VARCHAR2(500) NULL ,
	resultado            LONG VARCHAR NULL 
);

CREATE UNIQUE INDEX pk_iniciativa_objeto ON iniciativa_objeto
(iniciativa_id   ASC);

ALTER TABLE iniciativa_objeto
	ADD CONSTRAINT  pk_iniciativa_objeto PRIMARY KEY (iniciativa_id);

CREATE TABLE iniciativa_por_perspectiva
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	iniciativa_id        NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX ak_iniciativa_por_perspectiva ON iniciativa_por_perspectiva (perspectiva_id   ASC,iniciativa_id   ASC);

ALTER TABLE iniciativa_por_perspectiva
	ADD CONSTRAINT  pk_iniciativa_por_perspectiva PRIMARY KEY (perspectiva_id,iniciativa_id);

CREATE  INDEX IE_inicia_pers_iniciativaid ON iniciativa_por_perspectiva (iniciativa_id   ASC);
CREATE  INDEX IE_inic_pers_persid ON iniciativa_por_perspectiva (perspectiva_id   ASC);

CREATE TABLE iniciativa_plan
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	plan_id              NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX AK_iniciativa_plan ON iniciativa_plan (iniciativa_id   ASC,plan_id   ASC);

ALTER TABLE iniciativa_plan
	ADD CONSTRAINT  PK_iniciativa_plan PRIMARY KEY (iniciativa_id,plan_id);

CREATE  INDEX IE_iniciativa_iniciativa_plan ON iniciativa_plan (iniciativa_id   ASC);
CREATE  INDEX IE_plan_iniciativa_plan ON iniciativa_plan (plan_id   ASC);


CREATE TABLE masc_cod_plan_cuentas_grupo
(
	mascara_id           NUMBER(10) NOT NULL ,
	nivel                NUMBER(4) NOT NULL ,
	mascara              VARCHAR2(10) NULL ,
	nombre               VARCHAR2(50) NULL 
);



CREATE UNIQUE INDEX PK_masc_cod_plan_cuentas_grupo ON masc_cod_plan_cuentas_grupo
(nivel   ASC,mascara_id   ASC);



ALTER TABLE masc_cod_plan_cuentas_grupo
	ADD CONSTRAINT  PK_masc_cod_plan_cuentas_grupo PRIMARY KEY (nivel,mascara_id);



CREATE TABLE mascara_codigo_plan_cuentas
(
	mascara_id           NUMBER(10) NOT NULL ,
	niveles              NUMBER(4) NOT NULL ,
	mascara              VARCHAR2(200) NULL 
);



CREATE UNIQUE INDEX pk_mascara_codigo_plan_cuentas ON mascara_codigo_plan_cuentas
(mascara_id   ASC);



ALTER TABLE mascara_codigo_plan_cuentas
	ADD CONSTRAINT  pk_mascara_codigo_plan_cuentas PRIMARY KEY (mascara_id);



CREATE TABLE medicion
(
	serie_id             NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	valor                FLOAT NULL ,
	protegido            NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_medicion ON medicion
(serie_id   ASC,indicador_id   ASC,ano   ASC,periodo   ASC);



ALTER TABLE medicion
	ADD CONSTRAINT  pk_medicion PRIMARY KEY (indicador_id, serie_id, ano, periodo);



CREATE  INDEX xif1medicion ON medicion
(serie_id   ASC);



CREATE  INDEX xif2medicion ON medicion
(indicador_id   ASC);



CREATE  INDEX xif3medicion ON medicion
(serie_id   ASC,indicador_id   ASC);



CREATE TABLE meta
(
	plan_id              NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	valor                FLOAT NULL ,
	serie_id             NUMBER(10) NOT NULL ,
	protegido            NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_meta ON meta
(plan_id   ASC,indicador_id   ASC,tipo   ASC,ano   ASC,periodo   ASC,serie_id   ASC);



ALTER TABLE meta
	ADD CONSTRAINT  pk_meta PRIMARY KEY (plan_id, indicador_id, serie_id, tipo, ano, periodo);



CREATE  INDEX xif1meta ON meta
(plan_id   ASC);



CREATE  INDEX xif2meta ON meta
(indicador_id   ASC);

CREATE INDEX IE_meta_tipo ON meta (tipo   ASC);

CREATE TABLE metodologia
(
	metodologia_id       NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          LONG VARCHAR NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	indicador_nombre     VARCHAR2(50) NULL ,
	iniciativa_nombre    VARCHAR2(50) NULL ,
	actividad_nombre     VARCHAR2(50) NULL ,
	indicador_nombre_plural VARCHAR2(50) NULL ,
	iniciativa_nombre_plural VARCHAR2(50) NULL ,
	actividad_nombre_plural VARCHAR2(50) NULL ,
	tipo                 NUMBER(1) NULL ,
	indicador_articulo   VARCHAR2(50) NULL ,
	iniciativa_articulo  VARCHAR2(50) NULL ,
	actividad_articulo   VARCHAR2(50) NULL ,
	indicador_articulo_plural VARCHAR2(50) NULL ,
	iniciativa_articulo_plural VARCHAR2(50) NULL ,
	actividad_articulo_plural VARCHAR2(50) NULL 
);



CREATE UNIQUE INDEX pk_metodologia ON metodologia
(metodologia_id   ASC);



ALTER TABLE metodologia
	ADD CONSTRAINT  pk_metodologia PRIMARY KEY (metodologia_id);



CREATE UNIQUE INDEX ak1_metodologia ON metodologia
(nombre   ASC);



ALTER TABLE metodologia
ADD CONSTRAINT  ak1_metodologia UNIQUE (nombre);



CREATE TABLE metodologia_template
(
	metodologia_template_id NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	metodologia_id       NUMBER(10) NULL ,
	orden                NUMBER(4) NULL ,
	tipo                 NUMBER(2) NULL 
);



CREATE UNIQUE INDEX pk_metodologia_template ON metodologia_template
(metodologia_template_id   ASC);



ALTER TABLE metodologia_template
	ADD CONSTRAINT  pk_metodologia_template PRIMARY KEY (metodologia_template_id);



CREATE UNIQUE INDEX ak1_metodologia_template ON metodologia_template
(metodologia_id   ASC,nombre   ASC);



ALTER TABLE metodologia_template
ADD CONSTRAINT  ak1_metodologia_template UNIQUE (metodologia_id,nombre);



CREATE  INDEX xif1metodologia_template ON metodologia_template
(metodologia_id   ASC);



CREATE TABLE modelo
(
	modelo_id      NUMBER(10) NOT NULL ,
	plan_id              NUMBER(10) NOT NULL ,
	nombre               VARCHAR(100) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	binario              LONG VARCHAR NULL
);

CREATE UNIQUE INDEX ak_modelo ON modelo (modelo_id   ASC,plan_id   ASC);

ALTER TABLE modelo ADD CONSTRAINT pk_id_plan_modelo PRIMARY KEY (modelo_id,plan_id);

CREATE  INDEX IE_plan_modelo ON modelo (plan_id   ASC);

CREATE TABLE organizacion
(
	organizacion_id      NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	nombre               VARCHAR2(150) NOT NULL ,
	rif                  VARCHAR2(15) NULL ,
	direccion            VARCHAR2(150) NULL ,
	telefono             VARCHAR2(50) NULL ,
	fax                  VARCHAR2(50) NULL ,
	mes_cierre           NUMBER(4) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	alerta_min_max       NUMBER(4) NULL ,
	alerta_meta_n1       NUMBER(4) NULL ,
	alerta_meta_n2       NUMBER(4) NULL ,
	alerta_iniciativa_zv NUMBER(4) NULL ,
	alerta_iniciativa_za NUMBER(4) NULL ,
	enlace_parcial       VARCHAR2(100) NULL ,
	subclase             VARCHAR2(4) NULL ,
	visible              NUMBER(1) NOT NULL ,
	read_only            NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_organizacion ON organizacion
(organizacion_id   ASC);



ALTER TABLE organizacion
	ADD CONSTRAINT  pk_organizacion PRIMARY KEY (organizacion_id);



CREATE UNIQUE INDEX ak1_organizacion ON organizacion
(padre_id   ASC,nombre   ASC);



ALTER TABLE organizacion
ADD CONSTRAINT  ak1_organizacion UNIQUE (padre_id,nombre);



CREATE  INDEX if_organizacion_padre_id ON organizacion
(padre_id   ASC);



CREATE TABLE organizacion_memo
(
	organizacion_id      NUMBER(10) NOT NULL ,
	tipo                 NUMBER(2) NOT NULL ,
	descripcion          LONG VARCHAR NULL 
);

CREATE UNIQUE INDEX pk_organizacion_memo ON organizacion_memo (organizacion_id   ASC,tipo   ASC);
ALTER TABLE organizacion_memo ADD CONSTRAINT  pk_organizacion_memo PRIMARY KEY (organizacion_id,tipo);
CREATE  INDEX xif1organizacion_memo ON organizacion_memo (organizacion_id   ASC);

CREATE TABLE pagina
(
	pagina_id            NUMBER(10) NOT NULL ,
	vista_id             NUMBER(10) NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	filas                NUMBER(2) NOT NULL ,
	columnas             NUMBER(2) NOT NULL ,
	ancho                NUMBER(4) NOT NULL ,
	alto                 NUMBER(4) NOT NULL ,
	numero               NUMBER(4) NULL ,
	portafolioId         NUMBER(10) NULL 
);

CREATE UNIQUE INDEX ie_pagina ON pagina (pagina_id   ASC);
ALTER TABLE pagina 	ADD CONSTRAINT pk_pagina PRIMARY KEY (pagina_id);
CREATE INDEX ie_pagina_vista ON pagina (vista_id   ASC);
CREATE INDEX ie_pagina_portafolio ON pagina (portafolioId   ASC);

CREATE TABLE perspectiva
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	plan_id              NUMBER(10) NOT NULL ,
	responsable_id       NUMBER(10) NULL ,
	nombre               VARCHAR2(250) NOT NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	orden                NUMBER(2) NULL ,
	frecuencia           NUMBER(1) NULL ,
	titulo               VARCHAR2(50) NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	fecha_ultima_medicion VARCHAR2(10) NULL ,
	ultima_medicion_anual FLOAT NULL ,
	ultima_medicion_parcial FLOAT NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	crecimiento_parcial  NUMBER(1) NULL ,
	crecimiento_anual    NUMBER(1) NULL ,
	ano                  NUMBER(10) NULL ,
	clase_id             NUMBER(10) NULL ,
	tipo_calculo         NUMBER(1) NULL ,
	nl_par_indicador_id  NUMBER(10) NULL ,
	nl_ano_indicador_id  NUMBER(10) NULL 
);

CREATE UNIQUE INDEX pk_perspectiva ON perspectiva (perspectiva_id   ASC);

ALTER TABLE perspectiva
	ADD CONSTRAINT  pk_perspectiva PRIMARY KEY (perspectiva_id);

CREATE UNIQUE INDEX ak_perspectiva ON perspectiva (plan_id ASC, padre_id ASC, nombre ASC, ano ASC);

ALTER TABLE perspectiva
	ADD CONSTRAINT ak_perspectiva UNIQUE (plan_id, padre_id, nombre, ano);

CREATE  INDEX IE_perspectiva_plan ON perspectiva (plan_id   ASC);

CREATE  INDEX IE_perspectiva_responsable ON perspectiva (responsable_id   ASC);

CREATE  INDEX IE_perspectiva_padre ON perspectiva (padre_id   ASC);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_padre FOREIGN KEY (padre_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);	
	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_creado FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_modificado FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_clase FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_logropacial FOREIGN KEY (nl_par_indicador_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);
	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_logroanual FOREIGN KEY (nl_ano_indicador_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);

CREATE TABLE perspectiva_nivel
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	nivel                FLOAT NULL,
	meta                 FLOAT NULL,
	alerta               NUMBER(1) NULL 
);

CREATE UNIQUE INDEX ak_perspectiva_estado ON perspectiva_nivel (perspectiva_id   ASC,tipo   ASC,ano   ASC,periodo   ASC);
CREATE INDEX IE_perspectiva_nivel ON perspectiva_nivel (perspectiva_id   ASC);
ALTER TABLE perspectiva_nivel ADD CONSTRAINT  pk_perspectiva_estado PRIMARY KEY (perspectiva_id,tipo,ano,periodo);

CREATE TABLE planes
(
	plan_id              NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	plan_impacta_id      NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	ano_inicial          NUMBER(4) NOT NULL ,
	ano_final            NUMBER(4) NOT NULL ,
	tipo                 NUMBER(1) NULL ,
	activo               NUMBER(1) NULL ,
	revision             NUMBER(2) NOT NULL ,
	metodologia_id       NUMBER(10) NOT NULL ,
	clase_id             NUMBER(10) NOT NULL ,
	clase_id_indicadores_totales NUMBER(10) NULL ,
	ind_total_imputacion_id NUMBER(10) NULL ,
	ind_total_iniciativa_id NUMBER(10) NULL ,
	ultima_medicion_anual FLOAT NULL ,
	ultima_medicion_parcial FLOAT NULL ,
	nl_ano_indicador_id  NUMBER(10) NULL ,
	nl_par_indicador_id  NUMBER(10) NULL ,
	fecha_ultima_medicion VARCHAR2(10) NULL ,
	crecimiento          NUMBER(1) NULL ,
	esquema              VARCHAR2(50) NULL 
);

CREATE UNIQUE INDEX pk_planes ON planes
(plan_id   ASC);

ALTER TABLE planes
	ADD CONSTRAINT  pk_planes PRIMARY KEY (plan_id);

CREATE  INDEX IE_padre_plan ON planes
(padre_id   ASC);

CREATE  INDEX IE_impacta_plan ON planes
(plan_impacta_id   ASC);

CREATE  INDEX IE_organizacion_plan ON planes
(organizacion_id   ASC);

CREATE  INDEX IE_metodologia_plan ON planes
(metodologia_id   ASC);

CREATE  INDEX IE_clase_plan ON planes
(clase_id   ASC);

CREATE  INDEX IE_clasetotales_plan ON planes
(clase_id_indicadores_totales   ASC);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

CREATE TABLE plan_nivel
(
	plan_id              NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	nivel                FLOAT NULL 
);



CREATE UNIQUE INDEX pk_plan_estado ON plan_nivel
(plan_id   ASC,tipo   ASC,ano   ASC,periodo   ASC);



ALTER TABLE plan_nivel
	ADD CONSTRAINT  pk_plan_estado PRIMARY KEY (plan_id,tipo,ano,periodo);



CREATE TABLE prd_producto
(
	producto_id          NUMBER(10) NOT NULL ,
	iniciativa_id        NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	fecha_inicio         TIMESTAMP NULL ,
	descripcion          VARCHAR2(2000) NULL ,
	responsable_id       NUMBER(10) NULL 
);

CREATE UNIQUE INDEX pk_prd_producto ON prd_producto
(producto_id   ASC);

ALTER TABLE prd_producto
	ADD CONSTRAINT  pk_prd_producto PRIMARY KEY (producto_id);

CREATE  INDEX IE_prd_producto_iniciativaid ON prd_producto
(iniciativa_id   ASC);

CREATE  INDEX xif2prd_producto ON prd_producto
(responsable_id   ASC);

CREATE TABLE prd_seg_producto
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	producto_id          NUMBER(10) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	fecha_ini            TIMESTAMP NULL ,
	fecha_fin            TIMESTAMP NULL ,
	alerta               NUMBER(1) NULL 
);

CREATE UNIQUE INDEX pk_prd_seg_producto ON prd_seg_producto
(iniciativa_id   ASC,producto_id   ASC,ano   ASC,periodo   ASC);

ALTER TABLE prd_seg_producto
	ADD CONSTRAINT  pk_prd_seg_producto PRIMARY KEY (iniciativa_id,producto_id,ano,periodo);

CREATE  INDEX IE_prd_seg_producto_inicitiva ON prd_seg_producto
(iniciativa_id   ASC);

CREATE  INDEX IE_prd_seg_producto_producto ON prd_seg_producto
(producto_id   ASC);

CREATE TABLE prd_seguimiento
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	ano                  NUMBER(4) NOT NULL ,
	periodo              NUMBER(3) NOT NULL ,
	fecha                TIMESTAMP NULL ,
	alerta               NUMBER(1) NULL ,
	seguimiento          LONG VARCHAR NULL 
);

CREATE UNIQUE INDEX pk_prd_seguimiento ON prd_seguimiento
(iniciativa_id   ASC,ano   ASC,periodo   ASC);

ALTER TABLE prd_seguimiento
	ADD CONSTRAINT  pk_prd_seguimiento PRIMARY KEY (iniciativa_id,ano,periodo);

CREATE  INDEX IE_prd_seguimiento ON prd_seguimiento
(iniciativa_id   ASC);

CREATE TABLE problema
(
	problema_id          NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	clase_id             NUMBER(10) NOT NULL ,
	indicador_efecto_id  NUMBER(10) NULL ,
	iniciativa_efecto_id NUMBER(10) NULL ,
	fecha                TIMESTAMP NOT NULL ,
	responsable_id       NUMBER(10) NULL ,
	auxiliar_id          NUMBER(10) NULL ,
	estado_id            NUMBER(10) NULL ,
	fecha_estimada_inicio TIMESTAMP NULL ,
	fecha_estimada_final TIMESTAMP NULL ,
	fecha_real_inicio    TIMESTAMP NULL ,
	fecha_real_final     TIMESTAMP NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	read_only            NUMBER(1) NULL 
);

CREATE UNIQUE INDEX pk_problema ON problema
(problema_id   ASC);

ALTER TABLE problema
	ADD CONSTRAINT  pk_problema PRIMARY KEY (problema_id);

CREATE UNIQUE INDEX ak_problema ON problema
(organizacion_id   ASC,clase_id   ASC,nombre   ASC);

ALTER TABLE problema
ADD CONSTRAINT  ak_problema UNIQUE (organizacion_id,clase_id,nombre);

CREATE  INDEX IE_problema_organizacion ON problema
(organizacion_id   ASC);

CREATE  INDEX IE_problema_iniciativa_efecto ON problema
(iniciativa_efecto_id   ASC);

CREATE  INDEX IE_problema_auxiliar ON problema
(auxiliar_id   ASC);

CREATE  INDEX XIF12problema ON problema
(clase_id   ASC);

CREATE  INDEX IE_problema_estado ON problema
(estado_id   ASC);

CREATE  INDEX IE_problema_responsable ON problema
(responsable_id   ASC);

CREATE  INDEX IE_problema_creado ON problema
(creado_id   ASC);

CREATE  INDEX IE_problema_modificado ON problema
(modificado_id   ASC);

CREATE  INDEX IE_problema_indicador_efecto ON problema
(indicador_efecto_id   ASC);

CREATE TABLE problema_memo
(
	problema_id          NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	memo                 LONG VARCHAR NULL 
);



CREATE UNIQUE INDEX pk_memo_problema ON problema_memo
(problema_id   ASC,tipo   ASC);



ALTER TABLE problema_memo
	ADD CONSTRAINT  pk_memo_problema PRIMARY KEY (problema_id,tipo);



CREATE  INDEX xif1memo_problema ON problema_memo
(problema_id   ASC);



CREATE TABLE pry_actividad
(
	actividad_id         NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	proyecto_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(250) NOT NULL ,
	descripcion          LONG VARCHAR NULL ,
	comienzo_plan        TIMESTAMP NULL ,
	comienzo_real        TIMESTAMP NULL ,
	fin_plan             TIMESTAMP NULL ,
	fin_real             TIMESTAMP NULL ,
	duracion_plan        FLOAT NULL ,
	duracion_real        FLOAT NULL ,
	unidad_id            NUMBER(10) NULL ,
	fila                 NUMBER(10) NULL ,
	nivel                NUMBER(4) NULL ,
	compuesta            NUMBER(1) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	indicador_id         NUMBER(10) NULL ,
	naturaleza           NUMBER(1) NULL ,
	clase_id             NUMBER(10) NULL ,
	resp_fijar_meta_id   NUMBER(10) NULL ,
	resp_lograr_meta_id  NUMBER(10) NULL ,
	resp_seguimiento_id  NUMBER(10) NULL ,
	resp_cargar_ejecutado_id NUMBER(10) NULL ,
	resp_cargar_meta_id  NUMBER(10) NULL ,
	tipo_medicion        NUMBER(1) NULL ,
	objeto_asociado_id   NUMBER(10) NULL ,
	objeto_asociado_plan_id NUMBER(10) NULL,
	objeto_asociado_className VARCHAR2(50) NULL,
	crecimiento				NUMBER(1) NULL,
	porcentaje_completado	FLOAT NULL,
	porcentaje_esperado		FLOAT NULL,
	porcentaje_ejecutado	FLOAT NULL,
	fecha_ultima_medicion 	VARCHAR2(10) NULL
);



CREATE UNIQUE INDEX pk_pry_actividad ON pry_actividad
(actividad_id   ASC);



ALTER TABLE pry_actividad
	ADD CONSTRAINT  pk_pry_actividad PRIMARY KEY (actividad_id);



CREATE  INDEX xif10pry_actividad ON pry_actividad
(resp_cargar_ejecutado_id   ASC);



CREATE  INDEX xif11pry_actividad ON pry_actividad
(resp_cargar_meta_id   ASC);



CREATE  INDEX xif1pry_actividad ON pry_actividad
(proyecto_id   ASC);



CREATE  INDEX xif2pry_actividad ON pry_actividad
(padre_id   ASC);



CREATE  INDEX xif3pry_actividad ON pry_actividad
(unidad_id   ASC);


CREATE TABLE pry_calendario
(
	calendario_id        NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NULL ,
	dom                  NUMBER(1) NULL ,
	lun                  NUMBER(1) NULL ,
	mar                  NUMBER(1) NULL ,
	mie                  NUMBER(1) NULL ,
	jue                  NUMBER(1) NULL ,
	vie                  NUMBER(1) NULL ,
	sab                  NUMBER(1) NULL ,
	proyecto_id          NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_pry_calendario ON pry_calendario
(calendario_id   ASC);



ALTER TABLE pry_calendario
	ADD CONSTRAINT  pk_pry_calendario PRIMARY KEY (calendario_id);



CREATE UNIQUE INDEX ak1_pry_calendario ON pry_calendario
(proyecto_id   ASC,nombre   ASC);



ALTER TABLE pry_calendario
ADD CONSTRAINT  ak1_pry_calendario UNIQUE (proyecto_id,nombre);



CREATE  INDEX xif1pry_calendario ON pry_calendario
(proyecto_id   ASC);



CREATE TABLE pry_calendario_detalle
(
	calendario_id        NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NULL ,
	feriado              NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_pry_calendario_detalle ON pry_calendario_detalle
(calendario_id   ASC);



ALTER TABLE pry_calendario_detalle
	ADD CONSTRAINT  pk_pry_calendario_detalle PRIMARY KEY (calendario_id);



CREATE TABLE pry_columna
(
	columna_id           NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NULL ,
	numerico             NUMBER(1) NULL ,
	alineacion           NUMBER(1) NULL ,
	formato_java         VARCHAR2(50) NULL ,
	tamano_java          NUMBER(4) NULL 
);



CREATE UNIQUE INDEX pk_pry_columna ON pry_columna
(columna_id   ASC);



ALTER TABLE pry_columna
	ADD CONSTRAINT  pk_pry_columna PRIMARY KEY (columna_id);



CREATE UNIQUE INDEX ak1_pry_columna ON pry_columna
(nombre   ASC);



ALTER TABLE pry_columna
ADD CONSTRAINT  ak1_pry_columna UNIQUE (nombre);



CREATE TABLE pry_columna_por_vista
(
	vista_id             NUMBER(10) NOT NULL ,
	columna_id           NUMBER(10) NOT NULL ,
	alineacion           NUMBER(1) NULL ,
	ancho_java           NUMBER(4) NULL ,
	orden                NUMBER(2) NULL 
);



CREATE UNIQUE INDEX pk_pry_columna_por_vista ON pry_columna_por_vista
(vista_id   ASC,columna_id   ASC);



ALTER TABLE pry_columna_por_vista
	ADD CONSTRAINT  pk_pry_columna_por_vista PRIMARY KEY (vista_id,columna_id);



CREATE  INDEX xif1pry_columna_por_vista ON pry_columna_por_vista
(vista_id   ASC);



CREATE  INDEX xif2pry_columna_por_vista ON pry_columna_por_vista
(columna_id   ASC);



CREATE TABLE pry_proyecto
(
	proyecto_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(250) NULL ,
	comienzo             DATE NULL ,
	comienzo_plan        DATE NULL ,
	comienzo_real        DATE NULL ,
	fin_plan             DATE NULL ,
	fin_real             DATE NULL ,
	duracion_plan        FLOAT NULL ,
	duracion_real        FLOAT NULL ,
	variacion_comienzo   FLOAT NULL ,
	variacion_fin        FLOAT NULL ,
	variacion_duracion   FLOAT NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_pry_proyecto ON pry_proyecto
(proyecto_id   ASC);



ALTER TABLE pry_proyecto
	ADD CONSTRAINT  pk_pry_proyecto PRIMARY KEY (proyecto_id);



CREATE TABLE pry_vista
(
	vista_id             NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL 
);



CREATE UNIQUE INDEX pk_pry_vista ON pry_vista
(vista_id   ASC);



ALTER TABLE pry_vista
	ADD CONSTRAINT  pk_pry_vista PRIMARY KEY (vista_id);



CREATE UNIQUE INDEX ak1_pry_vista ON pry_vista
(nombre   ASC);



ALTER TABLE pry_vista
ADD CONSTRAINT  ak1_pry_vista UNIQUE (nombre);



CREATE TABLE reporte
(
	reporte_id           NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	configuracion        LONG VARCHAR NOT NULL ,
	descripcion          VARCHAR2(1000) NULL ,
	publico              NUMBER(1) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	corte				 NUMBER(1) NOT NULL ,
	usuario_id           NUMBER(10) NOT NULL 
);

ALTER TABLE reporte ADD CONSTRAINT  pk_reporte PRIMARY KEY (reporte_id);
CREATE UNIQUE INDEX ak_reporte ON reporte (organizacion_id   ASC,nombre   ASC);
ALTER TABLE reporte ADD CONSTRAINT  ak_reporte UNIQUE (organizacion_id,nombre);
CREATE  INDEX IE_usuario_reporte ON reporte (usuario_id   ASC);


CREATE TABLE responsable
(
	responsable_id       NUMBER(10) NOT NULL ,
	usuario_id           NUMBER(10) NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	cargo                VARCHAR2(50) NOT NULL ,
	ubicacion            VARCHAR2(250) NULL ,
	email                VARCHAR2(50) NULL ,
	notas                LONG VARCHAR NULL ,
	children_count       NUMBER(1) NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	grupo                NUMBER(1) NOT NULL ,
	organizacion_id      NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_responsable ON responsable
(responsable_id   ASC);



ALTER TABLE responsable
	ADD CONSTRAINT  pk_responsable PRIMARY KEY (responsable_id);

CREATE UNIQUE INDEX ak_responsable ON responsable (organizacion_id   ASC,nombre   ASC,cargo   ASC);
ALTER TABLE responsable ADD CONSTRAINT akc_responsable UNIQUE (organizacion_id,nombre,cargo);



CREATE  INDEX if_responsable_usu_id ON responsable
(usuario_id   ASC);



CREATE  INDEX xif2responsable ON responsable
(organizacion_id   ASC);

	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_responsable FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);


CREATE TABLE responsable_grupo
(
	padre_id             NUMBER(10) NOT NULL ,
	responsable_id       NUMBER(10) NOT NULL 
);



CREATE UNIQUE INDEX pk_responsable_grupo ON responsable_grupo
(padre_id   ASC,responsable_id   ASC);



ALTER TABLE responsable_grupo
	ADD CONSTRAINT  pk_responsable_grupo PRIMARY KEY (padre_id,responsable_id);



CREATE  INDEX if_resp_grupo_pad_id ON responsable_grupo
(padre_id   ASC);



CREATE  INDEX if_resp_grupo_res_id ON responsable_grupo
(responsable_id   ASC);



CREATE TABLE responsable_por_accion
(
	accion_id            NUMBER(10) NOT NULL ,
	responsable_id       NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL 
);



CREATE UNIQUE INDEX xpkresponsable_por_accion ON responsable_por_accion
(accion_id   ASC,responsable_id   ASC);



ALTER TABLE responsable_por_accion
	ADD CONSTRAINT  xpkresponsable_por_accion PRIMARY KEY (accion_id,responsable_id);



CREATE  INDEX xif1responsable_por_accion ON responsable_por_accion
(accion_id   ASC);



CREATE  INDEX xif2responsable_por_accion ON responsable_por_accion
(responsable_id   ASC);



CREATE TABLE seguimiento
(
	seguimiento_id       NUMBER(10) NOT NULL ,
	accion_id            NUMBER(10) NOT NULL ,
	estado_id            NUMBER(10) NULL ,
	fecha_emision        TIMESTAMP NULL ,
	emision_enviado      NUMBER(1) NULL ,
	fecha_recepcion      TIMESTAMP NULL ,
	recepcion_enviado    NUMBER(1) NULL ,
	fecha_aprobacion     TIMESTAMP NULL ,
	preparado_por        VARCHAR2(50) NULL ,
	numero_de_reporte    NUMBER(5) NULL ,
	aprobado             NUMBER(1) NULL ,
	aprobado_por         VARCHAR2(50) NULL ,
	clave_correo         VARCHAR2(50) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	read_only            NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_seguimiento ON seguimiento
(seguimiento_id   ASC);



ALTER TABLE seguimiento
	ADD CONSTRAINT  pk_seguimiento PRIMARY KEY (seguimiento_id);



CREATE  INDEX xif1seguimiento ON seguimiento
(accion_id   ASC);



CREATE  INDEX xif2seguimiento ON seguimiento
(estado_id   ASC);



CREATE TABLE seguimiento_memo
(
	seguimiento_id       NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	memo                 LONG VARCHAR NULL 
);



CREATE UNIQUE INDEX pk_seguimiento_memo ON seguimiento_memo
(seguimiento_id   ASC,tipo   ASC);



ALTER TABLE seguimiento_memo
	ADD CONSTRAINT  pk_seguimiento_memo PRIMARY KEY (seguimiento_id,tipo);



CREATE  INDEX xif1seguimiento_memo ON seguimiento_memo
(seguimiento_id   ASC);



CREATE TABLE seguimiento_mensaje_email
(
	mensaje_id           NUMBER(10) NOT NULL ,
	descripcion          VARCHAR2(50) NOT NULL ,
	mensaje              LONG VARCHAR NOT NULL ,
	acuse_recibo         NUMBER(1) NULL ,
	solo_auxiliar        NUMBER(1) NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL 
);



CREATE UNIQUE INDEX pk_seguimiento_mensaje_email ON seguimiento_mensaje_email
(mensaje_id   ASC);



ALTER TABLE seguimiento_mensaje_email
	ADD CONSTRAINT  pk_seguimiento_mensaje_email PRIMARY KEY (mensaje_id);



CREATE TABLE serie_indicador
(
	serie_id             NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	naturaleza           NUMBER(1) NULL ,
	fecha_bloqueo        TIMESTAMP NULL 
);



CREATE UNIQUE INDEX pk_serie_indicador ON serie_indicador
(serie_id   ASC,indicador_id   ASC);



ALTER TABLE serie_indicador
	ADD CONSTRAINT  pk_serie_indicador PRIMARY KEY (indicador_id, serie_id);



CREATE  INDEX xif1serie_indicador ON serie_indicador
(serie_id   ASC);



CREATE  INDEX xif2serie_indicador ON serie_indicador
(indicador_id   ASC);



CREATE TABLE serie_plan
(
	plan_id              NUMBER(10) NOT NULL ,
	serie_id             NUMBER(10) NOT NULL 
);



CREATE UNIQUE INDEX xpkserie_plan ON serie_plan
(plan_id   ASC,serie_id   ASC);



ALTER TABLE serie_plan
	ADD CONSTRAINT  xpkserie_plan PRIMARY KEY (plan_id,serie_id);



CREATE  INDEX xif1serie_plan ON serie_plan
(plan_id   ASC);



CREATE  INDEX xif2serie_plan ON serie_plan
(serie_id   ASC);

CREATE TABLE serie_tiempo
(
	serie_id             NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	fija                 NUMBER(1) NOT NULL ,
	oculta               NUMBER(1) NOT NULL ,
	identificador        VARCHAR2(5) NOT NULL ,
	preseleccionada      NUMBER(1) NOT NULL 
);

CREATE UNIQUE INDEX ak_serie_tiempo ON serie_tiempo (serie_id   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT  pk_serie_tiempo PRIMARY KEY (serie_id);

CREATE UNIQUE INDEX ak_serie_tiempo_nombre ON serie_tiempo (nombre   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_nombre UNIQUE (nombre);

CREATE UNIQUE INDEX ak_serie_tiempo_identificador ON serie_tiempo (identificador   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_identificador UNIQUE (identificador);


CREATE TABLE tipo_moneda
(
	tipo_moneda_id       NUMBER(10) NOT NULL ,
	moneda               VARCHAR2(50) NOT NULL ,
	identificador        VARCHAR2(5) NOT NULL ,
	factor_conversion_bolivares FLOAT NOT NULL ,
	creado               TIMESTAMP NULL ,
	modificado           TIMESTAMP NULL ,
	creado_id            NUMBER(10) NULL ,
	modificado_id        NUMBER(10) NULL ,
	solo_lectura         NUMBER(1) NULL 
);



CREATE UNIQUE INDEX pk_tipo_moneda ON tipo_moneda
(tipo_moneda_id   ASC);



ALTER TABLE tipo_moneda
	ADD CONSTRAINT  pk_tipo_moneda PRIMARY KEY (tipo_moneda_id);



CREATE UNIQUE INDEX ak1_tipo_moneda ON tipo_moneda
(moneda   ASC);



ALTER TABLE tipo_moneda
ADD CONSTRAINT  ak1_tipo_moneda UNIQUE (moneda);



CREATE UNIQUE INDEX ak2_tipo_moneda ON tipo_moneda
(identificador   ASC);



ALTER TABLE tipo_moneda
ADD CONSTRAINT  ak2_tipo_moneda UNIQUE (identificador);



CREATE TABLE unidad
(
	unidad_id            NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(100) NOT NULL ,
	tipo                 NUMBER(2) NULL 
);

CREATE UNIQUE INDEX pk_unidad ON unidad (unidad_id   ASC);

ALTER TABLE unidad ADD CONSTRAINT  pk_unidad PRIMARY KEY (unidad_id);

CREATE UNIQUE INDEX ak1_unidad ON unidad (nombre   ASC);

ALTER TABLE unidad ADD CONSTRAINT  ak1_unidad UNIQUE (nombre);

CREATE TABLE vista
(
	vista_id             NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	descripcion          LONG VARCHAR NULL ,
	visible              NUMBER(1) NULL ,
	fecha_inicio         VARCHAR2(10) NULL ,
	fecha_fin            VARCHAR2(10) NULL 
);

CREATE UNIQUE INDEX ie_vista ON vista (vista_id   ASC);
ALTER TABLE vista ADD CONSTRAINT  pk_vista PRIMARY KEY (vista_id);
CREATE UNIQUE INDEX ak_vista ON vista (organizacion_id   ASC,nombre   ASC);
ALTER TABLE vista ADD CONSTRAINT ak_vista_nombre UNIQUE (organizacion_id,nombre);
CREATE  INDEX ie_vista_organizacion ON vista (organizacion_id   ASC);

ALTER TABLE grafico
	ADD (CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE grafico
	ADD (CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);

ALTER TABLE reporte
	ADD (CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id));


CREATE TABLE afw_servicio
(
	usuario_id           NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	estatus              NUMBER(1) NOT NULL ,
	mensaje              VARCHAR2(1000) NOT NULL,
	log					 LONG VARCHAR NULL
);

CREATE UNIQUE INDEX PK_afw_servicio ON afw_servicio
(usuario_id   ASC,fecha   ASC,nombre   ASC);

ALTER TABLE afw_servicio
	ADD CONSTRAINT  PK_afw_servicio PRIMARY KEY (usuario_id,fecha,nombre);

CREATE  INDEX IF_afw_servicio ON afw_servicio
(usuario_id   ASC);

CREATE TABLE afw_message
(
	usuario_id           NUMBER(10) NOT NULL ,
	fecha                TIMESTAMP NOT NULL ,
	estatus              NUMBER(1) NOT NULL ,
	mensaje              VARCHAR2(500) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL,
	fuente				 VARCHAR2(50) NULL
);

CREATE UNIQUE INDEX PK_afw_message ON afw_message (usuario_id   ASC,fecha   ASC);

ALTER TABLE afw_message ADD CONSTRAINT  PK_afw_message PRIMARY KEY (usuario_id,fecha);

CREATE  INDEX IF_afw_message ON afw_message (usuario_id   ASC);

-- perspectiva_relacion
CREATE TABLE perspectiva_relacion
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	relacion_id          NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX PK_perspectiva_relacion ON perspectiva_relacion (perspectiva_id   ASC,relacion_id   ASC);

ALTER TABLE perspectiva_relacion
	ADD CONSTRAINT  PK_perspectiva_relacion PRIMARY KEY (perspectiva_id,relacion_id);

CREATE  INDEX IE_relacion_perspectiva ON perspectiva_relacion (perspectiva_id   ASC);

CREATE  INDEX IE_relacion_relacion ON perspectiva_relacion (relacion_id   ASC);

--Version
CREATE TABLE afw_version
(
	version              VARCHAR2(50) NOT NULL ,
	build                VARCHAR2(8) NOT NULL ,
	dateBuild            VARCHAR2(50) NOT NULL ,
	createdAt            TIMESTAMP NOT NULL 
);

CREATE UNIQUE INDEX AK_afw_version ON afw_version (version   ASC, build   ASC, dateBuild   ASC);

CREATE INDEX ie_afw_version ON afw_version (createdat   ASC);

ALTER TABLE afw_version ADD CONSTRAINT PK_afw_version PRIMARY KEY (version, build, dateBuild);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_organizacion_iniciativa FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_fijar_meta FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_lograr_meta FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_seguimiento FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_cargar_meta FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_cargar_ejecutado FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_proyecto_iniciativa FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_clase_iniciativa FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);
	
ALTER TABLE iniciativa
	ADD (CONSTRAINT PK_Iniciativa_EstatusId FOREIGN KEY (estatusId) REFERENCES iniciativa_estatus (id) ON DELETE CASCADE);	

ALTER TABLE iniciativa_ano
	ADD (CONSTRAINT fk_iniciativa_ano FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_objeto
	ADD (CONSTRAINT fK_iniciativa_objeto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_perspectiva
	ADD (CONSTRAINT fk_iniciativa_perspectiva FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_perspectiva
	ADD (CONSTRAINT fK_perspectiva_iniciativa FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_plan
	ADD (CONSTRAINT FK_iniciativa_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_plan
	ADD (CONSTRAINT FK_plan_iniciaitva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);
	
ALTER TABLE prd_producto
	ADD (CONSTRAINT fk_iniciativa_producto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE prd_seg_producto
	ADD (CONSTRAINT fk_iniciativa_pro_seg FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE prd_seg_producto
	ADD (CONSTRAINT fk_producto_pro_seg FOREIGN KEY (producto_id) REFERENCES prd_producto (producto_id) ON DELETE CASCADE);

ALTER TABLE perspectiva_relacion
	ADD (CONSTRAINT FK_relacion_perspectiva FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);

ALTER TABLE perspectiva_relacion
	ADD (CONSTRAINT FK_relacion_relacion FOREIGN KEY (relacion_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);

ALTER TABLE accion
	ADD (CONSTRAINT FK_ACCION_ESTADO FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_ACCION_Problema FOREIGN KEY (problema_id) REFERENCES problema (problema_id) ON DELETE CASCADE);

ALTER TABLE accion
	ADD (CONSTRAINT FK_Accion_PadreId FOREIGN KEY (accion_id) REFERENCES accion (accion_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_creado_accion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_modificado_accion FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);
	
ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_padre_claseproblema FOREIGN KEY (padre_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_organizacion_claseproblema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE SET NULL);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_creado_claseproblema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_modificado_claseproblema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_organizacion_problema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE problema
	ADD (CONSTRAINT FK_estado_problema FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_responsable_problema FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_creado_problema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_modificado_problema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_indicador_efecto_problema FOREIGN KEY (indicador_efecto_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_iniciativa_efecto_problema FOREIGN KEY (iniciativa_efecto_id) REFERENCES iniciativa (iniciativa_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_auxiliar_problema FOREIGN KEY (auxiliar_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_problema_claseproblema FOREIGN KEY (clase_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_padre_planes FOREIGN KEY (padre_id) REFERENCES planes (plan_id) ON DELETE SET NULL);

ALTER TABLE planes
	ADD (CONSTRAINT FK_planImpacta_plan FOREIGN KEY (plan_impacta_id) REFERENCES planes (plan_id) ON DELETE SET NULL);

ALTER TABLE planes
	ADD (CONSTRAINT FK_organizacion_plan FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_metodologia_plan FOREIGN KEY (metodologia_id) REFERENCES metodologia (metodologia_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_clase_plan FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_clasetotales_plan FOREIGN KEY (clase_id_indicadores_totales) REFERENCES clase (clase_id) ON DELETE SET NULL);

ALTER TABLE responsable
	ADD (CONSTRAINT FK_organizacion_responsable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);
	
ALTER TABLE responsable
	ADD (CONSTRAINT FK_usuario_responsable FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);	

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_clase_indicador FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_indicador_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE indicador 
	ADD (CONSTRAINT fk_indicador_unidad FOREIGN KEY (unidad_id) REFERENCES unidad (unidad_id) ON DELETE SET NULL);

ALTER TABLE reporte
	ADD (CONSTRAINT FK_usuario_reporte FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);

ALTER TABLE indicador_estado 
	ADD (CONSTRAINT fk_indicador_indicador_estado FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE indicador_estado 
	ADD (CONSTRAINT fk_plan_indicador_estado FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

ALTER TABLE indicador_por_plan 
	ADD (CONSTRAINT FK_ind_indicador_plan FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE indicador_por_plan 
	ADD (CONSTRAINT FK_plan_indicador_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

ALTER TABLE modelo 
	ADD (CONSTRAINT FK_planes_modelo FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);
	
	
CREATE TABLE afw_modulo
(
	Id                   VARCHAR2(50) NOT NULL ,
	Modulo               VARCHAR2(100) NOT NULL ,
	Activo               NUMBER(1) NOT NULL
);

CREATE UNIQUE INDEX IE_afw_modulo ON afw_modulo (Id   ASC);

ALTER TABLE afw_modulo ADD CONSTRAINT  IE_afw_modulo PRIMARY KEY (Id);

--Importacion
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

ALTER TABLE meta
	ADD CONSTRAINT fk_plan_meta FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;	
	
CREATE TABLE mb_atributo
(
	atributo_id          NUMBER(10) NOT NULL ,
	sector_id            NUMBER(10) NOT NULL ,
	variable_id          NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	pregunta             VARCHAR2(2000) NOT NULL ,
	prioridad            NUMBER(2) NULL ,
	orden                NUMBER(2) NOT NULL,
	indicador_id 		 NUMBER(10) NULL,
	apc 				 NUMBER(10) NULL
);

ALTER TABLE mb_atributo
	ADD CONSTRAINT  PK_MB_ATRIBUTO PRIMARY KEY (atributo_id);

ALTER TABLE mb_atributo ADD CONSTRAINT  AK_MB_ATRIBUTO UNIQUE (sector_id,nombre);

CREATE  INDEX IE_MB_ATRIBUTO_SECTOR_ID ON mb_atributo (sector_id   ASC);


CREATE TABLE mb_atrib_coment_cat
(
	atributo_id          NUMBER(10) NOT NULL ,
	orden                NUMBER(3) NOT NULL 
);

CREATE UNIQUE INDEX AK_mb_atrib_coment_cat ON mb_atrib_coment_cat (atributo_id   ASC,orden   ASC);

ALTER TABLE mb_atrib_coment_cat ADD CONSTRAINT  PK_mb_atrib_coment_cat PRIMARY KEY (atributo_id,orden);

CREATE  INDEX IE_mb_atrib_coment_cat ON mb_atrib_coment_cat (atributo_id   ASC);

ALTER TABLE mb_atrib_coment_cat 
	ADD (CONSTRAINT PK_mb_atributo_commnet_orden FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id) ON DELETE CASCADE);


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
	prioridad            NUMBER(2) NULL,
	comentario 			 LONG VARCHAR NULL
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
	criterio_desempate   NUMBER(1) NULL ,
	define_prioridad     NUMBER(1) NULL ,
	tipo_sintesis        NUMBER(2) NULL ,
	sintetizado          NUMBER(1) NULL ,
	anonimo              NUMBER(1) NULL ,
	etapa                NUMBER(1) NULL ,
	mensaje_correo       VARCHAR2(4000) NULL,
	encstas_x_encstado 	 NUMBER(10) NULL,
	coment_x_pregunta 	 NUMBER(10) NULL,
	asunto_correo 		 LONG VARCHAR NULL
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
	organizacion_id      NUMBER(10) NOT NULL,
	codigo_enlace 		 VARCHAR2(100) NULL 
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
	tamano_muestra       NUMBER(10) NULL,
	codigo_enlace 		 VARCHAR2(100) NULL
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

CREATE TABLE indicador_por_iniciativa
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL,
	tipo         		 NUMBER(1) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_iniciativa ON indicador_por_iniciativa (iniciativa_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_iniciativa
	ADD CONSTRAINT  PK_indicador_por_iniciativa PRIMARY KEY (iniciativa_id,indicador_id);

CREATE  INDEX IE_ind_por_ini_iniciativaId ON indicador_por_iniciativa (iniciativa_id   ASC);

CREATE  INDEX IE_ind_por_ini_indicadorId ON indicador_por_iniciativa (indicador_id   ASC);

ALTER TABLE indicador_por_iniciativa
	ADD (CONSTRAINT FK_ind_por_ini_IniciativaId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE indicador_por_iniciativa
	ADD (CONSTRAINT FK_ind_por_ini_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);
	
ALTER TABLE FORMULA 
	ADD (CONSTRAINT FK_FORMULA_INDICADOR FOREIGN KEY (INDICADOR_ID) REFERENCES INDICADOR (INDICADOR_ID) ON DELETE CASCADE);
ALTER TABLE FORMULA 
	ADD (CONSTRAINT fk_formula_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE);	
	
ALTER TABLE conjunto_formula
	ADD (CONSTRAINT fk_conjfor_padre_indicador FOREIGN KEY (padre_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE conjunto_formula
	ADD (CONSTRAINT fk_conjfor_indid_indicador FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE conjunto_formula
	ADD (CONSTRAINT fk_conjfor_serid_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE);

ALTER TABLE conjunto_formula
	ADD (CONSTRAINT fk_conjfor_insid_serie FOREIGN KEY (insumo_serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE);

CREATE TABLE duppont
(
	indicador_id         NUMBER(10) NOT NULL ,
	usuario_id           NUMBER(10) NOT NULL ,
	configuracion        VARCHAR2(2000) NULL 
);

CREATE UNIQUE INDEX AK_duppont ON duppont (indicador_id   ASC,usuario_id   ASC);

ALTER TABLE duppont ADD CONSTRAINT  PK_duppont PRIMARY KEY (indicador_id, usuario_id);

CREATE  INDEX IE_duppont_indicadorId ON duppont (indicador_id   ASC);

CREATE  INDEX IE_duppont_usuarioId ON duppont (usuario_id   ASC);

ALTER TABLE duppont
	ADD (CONSTRAINT FK_indicador_duppont FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE duppont
	ADD (CONSTRAINT FK_usuario_duppont FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);

CREATE TABLE objetos_calcular
(
	Id                   RAW(32) NOT NULL ,
	Objeto_Id            NUMBER(10) NOT NULL ,
	Usuario_Id           NUMBER(10) NOT NULL ,
	Calculado            NUMBER(1) NULL 
);

CREATE TABLE portafolio
(
	organizacion_id      NUMBER(10) NOT NULL ,
	id                   NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	activo               NUMBER(1) NOT NULL ,
	porcentaje_completado FLOAT NULL ,
	estatusid            NUMBER(10) NOT NULL,
	frecuencia           NUMBER(1) NOT NULL,
	clase_id             NUMBER(10) NOT NULL,
	fecha_ultimo_calculo VARCHAR2(10) NULL 
);

CREATE UNIQUE INDEX PK_Portafolio ON portafolio (id   ASC);
ALTER TABLE portafolio ADD CONSTRAINT  PK_PortafolioId PRIMARY KEY (id);
CREATE UNIQUE INDEX AK_Portafolio ON portafolio (organizacion_id   ASC,nombre   ASC);
ALTER TABLE portafolio ADD CONSTRAINT  AK_Portafolio_Nombre UNIQUE (organizacion_id,nombre);
CREATE  INDEX IE_Portafolio_OrganizacionId ON portafolio (organizacion_id   ASC);
CREATE  INDEX IE_portafolio_estatus ON portafolio (estatusid   ASC);
CREATE  INDEX IE_portafolio_clase ON portafolio (clase_id   ASC);

CREATE TABLE portafolio_iniciativa
(
	portafolio_id        NUMBER(10) NOT NULL ,
	iniciativa_id        NUMBER(10) NOT NULL, 
	peso                 FLOAT NULL
);

CREATE UNIQUE INDEX PK_portafolio_iniciativa ON portafolio_iniciativa (portafolio_id   ASC,iniciativa_id   ASC);
ALTER TABLE portafolio_iniciativa ADD CONSTRAINT PK_portafolio_ini_portId PRIMARY KEY (portafolio_id,iniciativa_id);
CREATE  INDEX IE_portafolio_iniciativa_porta ON portafolio_iniciativa (portafolio_id   ASC);
CREATE  INDEX IE_portafolio_iniciativa_inici ON portafolio_iniciativa (iniciativa_id   ASC);
ALTER TABLE portafolio ADD (CONSTRAINT FK_ORG_PORTAFOLIO FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);
ALTER TABLE portafolio ADD (CONSTRAINT FK_estatus_portafolio FOREIGN KEY (estatusid) REFERENCES iniciativa_estatus (id) ON DELETE SET NULL);
ALTER TABLE portafolio ADD (CONSTRAINT FK_clase_portafolio FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);
ALTER TABLE portafolio_iniciativa ADD (CONSTRAINT FK_portafolio_portafolioId FOREIGN KEY (portafolio_id) REFERENCES portafolio (id) ON DELETE CASCADE);
ALTER TABLE portafolio_iniciativa ADD (CONSTRAINT FK_portafolio_iniciativa_iniId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);
ALTER TABLE vista ADD (CONSTRAINT FK_organizacion_vista FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);
ALTER TABLE pagina ADD (CONSTRAINT FK_vista_pagina FOREIGN KEY (vista_id) REFERENCES vista (vista_id));
ALTER TABLE pagina ADD (CONSTRAINT FK_portafolio_vista FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON DELETE CASCADE);
ALTER TABLE celda ADD (CONSTRAINT FK_pagina_celda FOREIGN KEY (pagina_id) REFERENCES pagina (pagina_id) ON DELETE CASCADE);

CREATE TABLE indicador_por_portafolio
(
	indicador_id         NUMBER(10) NOT NULL ,
	portafolioId         NUMBER(10) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_portafolio ON indicador_por_portafolio (indicador_id   ASC,portafolioId   ASC);
ALTER TABLE indicador_por_portafolio ADD CONSTRAINT PK_indicador_por_portafolio PRIMARY KEY (indicador_id,portafolioId);
CREATE  INDEX IE_indicador_por_portafolio_in ON indicador_por_portafolio (indicador_id   ASC);
CREATE  INDEX IE_indicador_por_portafolio_po ON indicador_por_portafolio (portafolioId   ASC);

ALTER TABLE indicador_por_portafolio
	ADD (CONSTRAINT FK_indicador_indXpor FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE indicador_por_portafolio
	ADD (CONSTRAINT FK_portafolio_indXpor FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON DELETE CASCADE);
	
ALTER TABLE perspectiva_nivel
	ADD (CONSTRAINT FK_perspectiva_perspectiva_niv FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);	
	
ALTER TABLE EXPLICACION 
	ADD (CONSTRAINT FK_usuario_explicacion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);	

ALTER TABLE medicion
	ADD (CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE pry_calendario
	ADD (CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE CASCADE);

ALTER TABLE organizacion 
    ADD administrador VARCHAR2(500);
	
COMMIT;

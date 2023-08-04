CREATE TABLE indicador_asignar_inventario(
	asignar_id NUMBER(10) NOT NULL,
	indicador_id NUMBER(10) NOT NULL,
	indicador_insumo_id NUMBER(10) NOT NULL
);

CREATE UNIQUE INDEX pk_asignar ON 
(asignar_id   ASC);

ALTER TABLE indicador_asignar_inventario
	ADD CONSTRAINT  pk_asignar PRIMARY KEY (asignar_id);
    
CREATE TABLE tipo_proyecto(
	tipo_proyecto_id NUMBER(10) NOT NULL,
	nombre_proyecto VARCHAR(50) NOT NULL
);

ALTER TABLE tipo_proyecto
	ADD CONSTRAINT  pk_tipo_proyecto PRIMARY KEY (tipo_proyecto_id);

CREATE UNIQUE INDEX ak_tipo_proyecto ON tipo_proyecto (tipo_proyecto_id);

ALTER TABLE INICIATIVA ADD tipoId NUMBER;

ALTER TABLE iniciativa ADD CONSTRAINT pk_proyecto_id FOREIGN KEY (tipoId) REFERENCES tipo_proyecto (tipo_proyecto_id);

CREATE INDEX indx_tipo_proyecto ON iniciativa (tipoId);

CREATE TABLE auditoria_medicion
(
  auditoria_medicion_id NUMBER(10) NOT NULL,
  sesion varchar(200) NULL,
  fecha_ejecucion TIMESTAMP NULL, 
  accion varchar(50) NOT NULL,
  organizacion varchar(100) NOT NULL,
  organizacion_id integer NOT NULL,
  periodo varchar(10) NOT NULL,
  periodo_final varchar(10) NOT NULL,
  usuario varchar(20) NOT NULL,
  indicador varchar(200) NOT NULL,
  clase varchar(500) NOT NULL,
  iniciativa varchar(300) NULL,
  indicador_id integer NOT NULL,
  detalle varchar(4000) NULL
);

ALTER TABLE auditoria_medicion
	ADD CONSTRAINT  pk_auditoria PRIMARY KEY (auditoria_medicion_id);
	
CREATE TABLE auditoria(
  auditoria_id numeric(10, 0) NOT NULL,
  fecha_ejecucion TIMESTAMP NULL,
  usuario varchar(200) NULL,
  entidad varchar(500) NULL,  
  clase_entidad varchar(4000) NOT NULL,
  tipo_evento varchar(50) NOT NULL,
  detalle varchar(4000) NULL
);

CREATE UNIQUE INDEX ak_auditoria ON auditoria (auditoria_id   ASC);

ALTER TABLE indicador ADD resp_notificacion_id [numeric](10, 0);

CREATE INDEX ie_indicador_respnotificacion ON indicador (resp_notificacion_id );

ALTER TABLE indicador ADD CONSTRAINT fk_respnotificacion_indicador FOREIGN KEY (resp_notificacion_id) REFERENCES responsable (responsable_id);

CREATE TABLE reporte_servicio (
  reporte_id numeric(10, 0) NOT NULL,	
  responsable_id numeric(10, 0) NOT NULL,
  medicion varchar(4000) NOT NULL,
  indicador_id numeric(10, 0) NOT NULL
);

CREATE UNIQUE INDEX ak_reporte_servicio ON reporte_servicio (responsable_id   ASC);

CREATE TABLE reporte_grafico
(
  reporte_id numeric(10) NOT NULL,
  nombre varchar(50) NOT NULL,
  configuracion varchar(4000) NOT NULL,
  descripcion varchar(1000) NULL ,
  publico NUMERIC(1) NOT NULL ,
  tipo NUMERIC(1) NOT NULL ,
  fecha TIMESTAMP NULL,
  indicadores varchar(4000) NULL ,
  organizaciones varchar(4000) NULL ,
  periodo_ini  varchar(4000) NULL,
  periodo_fin varchar(4000) NULL,
  tiempo varchar(4000) NULL,  
  usuario_id NUMERIC(10) NOT NULL, 
  grafico_id numeric(10) NULL,  
  CONSTRAINT pk_reporte_grafico PRIMARY KEY (reporte_id),
  CONSTRAINT FK_usuario_reporte_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id),
	  
  CONSTRAINT ak_reporte_grafico UNIQUE (nombre)
);

UPDATE afw_sistema set actual = '8.01-200327';  
UPDATE afw_sistema set build = 200327;

insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_OP', 'PLAN', 'Ver Operativo', 2, 6, 0, 'Ver Opeartivo');

insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_ES', 'PLAN', 'Ver Estrategico', 2, 7, 0, 'Ver Estrategico');

CREATE TABLE tipo_convenio (
  tipos_convenio_id numeric(10, 0) NOT NULL,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL,
  CONSTRAINT PK_tipo_convenio PRIMARY KEY (tipos_convenio_id)
);

CREATE TABLE cooperante (
  cooperante_id numeric(10, 0) NOT NULL,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL,
  pais varchar(150) NULL,
  CONSTRAINT PK_cooperante PRIMARY KEY (cooperante_id)
);

CREATE TABLE instrumentos (
  instrumento_id numeric(10, 0) NOT NULL,
  nombre_corto varchar(50) NULL,
  nombre_instrumento varchar(4000) NULL,
  objetivo_instrumento varchar(4000) NULL,
  productos varchar(4000) NULL,
  cooperante_id numeric(10, 0) NULL,
  tipos_convenio_id numeric(10, 0) NULL,
  anio varchar(4) NULL,
  instrumento_marco varchar(4000) NULL,
  fecha_inicio TIMESTAMP NULL,
  fecha_terminacion TIMESTAMP NULL,
  fecha_prorroga TIMESTAMP NULL,
  recursos_pesos float NULL,
  recursos_dolares float NULL,
  nombre_ejecutante varchar(250) NULL,
  estatus numeric NULL,
  areas_cargo varchar(500) NULL,
  nombre_responsable_areas varchar(250) NULL,
  responsable_cgi varchar(250) NULL,
  observaciones varchar(250) NULL,
  CONSTRAINT PK_instrumento PRIMARY KEY (instrumento_id)
);

ALTER TABLE instrumentos ADD CONSTRAINT pk_cooperante_id FOREIGN KEY (cooperante_id) REFERENCES cooperante (cooperante_id);

CREATE INDEX indx_cooperante ON instrumentos (cooperante_id);


ALTER TABLE instrumentos ADD CONSTRAINT pk_tipos_convenio_id FOREIGN KEY (tipos_convenio_id) REFERENCES tipo_convenio (tipos_convenio_id);

CREATE INDEX indx_tipo_convenio ON instrumentos (tipos_convenio_id);

CREATE TABLE instrumento_iniciativa
(
	instrumento_id NUMERIC(10, 0) NOT NULL ,
	iniciativa_id  NUMERIC(10, 0) NOT NULL
);

CREATE UNIQUE INDEX PK_instrumento_iniciativa ON instrumento_iniciativa (instrumento_id   ASC,iniciativa_id   ASC);
ALTER TABLE instrumento_iniciativa ADD CONSTRAINT PK_instru_inici_portId PRIMARY KEY (instrumento_id, iniciativa_id);
CREATE  INDEX IE_instrum_inic_instru ON instrumento_iniciativa (instrumento_id   ASC);
CREATE  INDEX IE_instrum_inic_inici ON instrumento_iniciativa (iniciativa_id   ASC);

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) values ('INSTRUMENTOS', 'Gestionar Instrumentos',NULL, 0, 17, 1, 'Instrumentos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_ADD', 'Crear', 'INSTRUMENTOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_EDIT', 'Modificar', 'INSTRUMENTOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_DELETE', 'Eliminar', 'INSTRUMENTOS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_VIEW_TIPO', 'Ver Tipos', 'INSTRUMENTOS', 1, 4, 1, 'Ver Tipos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_VIEW_COOP', 'Ver Cooperante', 'INSTRUMENTOS', 1, 5, 1, 'Ver Cooperante');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA', 'Iniciativa', 'INSTRUMENTOS', 1, 6, 1, 'Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA_ADD', 'Agregar', 'INSTRUMENTOS_INICIATIVA', 2, 1, 1, 'Agregar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA_DELETE', 'Remover', 'INSTRUMENTOS_INICIATIVA', 2, 2, 1, 'Remover');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_ASOCIAR', 'Asociar', 'INSTRUMENTOS', 1, 8, 1, 'Asociar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_DESASOCIAR', 'Desasociar', 'INSTRUMENTOS', 1, 9, 1, 'Desasociar');

UPDATE afw_sistema set actual = '8.01-210505';  
UPDATE afw_sistema set build = 210505;

alter table iniciativa_objeto
    modify objeto varchar(1024);
	
ALTER TABLE iniciativa 
    add(
        responsable_proyecto varchar(150),
        cargo_responsable varchar(50),
        organizaciones_involucradas varchar(250),
        objetivo_estrategico varchar(1024),
        fuente_financiacion varchar(50),
        monto_financiamiento varchar(50),
        iniciativa_estrategica varchar(150),
        lider_iniciativa varchar(150),
        tipo_iniciativa varchar(150),
        poblacion_beneficiada varchar(1024),
        contexto varchar(1024),
        definicion_problema varchar(1024),
        alcance varchar(1024),
        objetivo_general varchar(1024),
        objetivos_especificos varchar(1024) 
    ); 
           
    
alter table instrumento_iniciativa add peso float;

CREATE TABLE indicador_por_instrumento
(
	instrumento_id       numeric(10,0) NOT NULL ,
	indicador_id         numeric(10,0) NOT NULL,
	tipo         		 numeric(1, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_instrumento ON indicador_por_instrumento(instrumento_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT  PK_indicador_por_instrumento PRIMARY KEY (instrumento_id,indicador_id);

CREATE INDEX IE_ind_por_ins_instrumentoId ON indicador_por_instrumento  (instrumento_id   ASC);

CREATE  INDEX IE_ind_por_ins_indicadorId ON indicador_por_instrumento  (indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_InstrumentoId FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON DELETE CASCADE;

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

ALTER TABLE instrumentos ADD clase_id numeric (10,0);

INSERT INTO afw_permiso (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EDIT_COOPERACION', 'Modificacion Otras Organizaciones', 'INICIATIVA', 2, 14, 1, 'Modificacion Otras Organizaciones');

CREATE  INDEX IE_instrumento_peso ON instrumento_peso (instrumento_id   ASC);

ALTER TABLE instrumento_peso
    ADD CONSTRAINT pk_instrumento_peso PRIMARY KEY (instrumento_id);
    
ALTER TABLE instrumento_peso
	ADD CONSTRAINT fk_instrumento_peso FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON DELETE CASCADE;
    
UPDATE afw_permiso set permiso = 'Desasociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_DELETE';
UPDATE afw_permiso set permiso = 'Asociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_ADD';

UPDATE afw_sistema set actual = '8.01-230505';  
UPDATE afw_sistema set build = 230505;

-- alter table explicacion_adjunto drop column ruta;

alter table explicacion_adjunto add adjunto_binario BLOB;

ALTER TABLE instrumentos
    modify nombre_corto varchar(150) ;
ALTER TABLE instrumentos 
    modify observaciones varchar(2500) ;

ALTER TABLE planes
    modify nombre varchar(150) ;

CREATE TABLE cargo (
	cargo_id numeric(10,0) NOT NULL,
	nombre character varying(50) NOT NULL
);

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS', 'Gestionar Cargos', NULL, 0, 3, 1, 'Gestionar Categor�as de Medici�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_ADD', 'Crear', 'CARGOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_EDIT', 'Modificar', 'CARGOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_DELETE', 'Eliminar', 'CARGOS', 1, 3, 1, 'Eliminar');

ALTER TABLE iniciativa ADD cargo_id numeric(10);


UPDATE afw_sistema set actual = '9.01-230727';  
UPDATE afw_sistema set build = 230727;

ALTER TABLE vista 
    modify nombre varchar(250);
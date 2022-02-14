insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_OP', 'PLAN', 'Ver Operativo', 2, 6, 0, 'Ver Opeartivo');

insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_ES', 'PLAN', 'Ver Estrategico', 2, 7, 0, 'Ver Estrategico');

CREATE TABLE tipo_convenio (
  tipos_convenio_id serial PRIMARY KEY,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL
);

CREATE TABLE cooperante (
  cooperante_id serial PRIMARY KEY,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL,
  pais varchar(150) NULL
);

CREATE TABLE instrumentos (
  instrumento_id serial PRIMARY KEY,
  nombre_corto varchar(50) NULL,
  nombre_instrumento varchar NULL,
  objetivo_instrumento varchar NULL,
  productos varchar NULL,
  cooperante_id serial,
  tipos_convenio_id serial ,
  anio varchar(4) NULL,
  instrumento_marco varchar NULL,
  fecha_inicio timestamp without time zone,
  fecha_terminacion timestamp without time zone,
  fecha_prorroga timestamp without time zone,
  recursos_pesos float NULL,
  recursos_dolares float NULL,
  nombre_ejecutante varchar(250) NULL,
  estatus numeric(1) NULL,
  areas_cargo varchar(500) NULL,
  nombre_responsable_areas varchar(250) NULL,
  responsable_cgi varchar(250) NULL,
  observaciones varchar(250) NULL

);


ALTER TABLE instrumentos ADD CONSTRAINT pk_cooperante_id FOREIGN KEY (cooperante_id) REFERENCES cooperante (cooperante_id);

CREATE INDEX indx_cooperante ON instrumentos (cooperante_id);


ALTER TABLE instrumentos ADD CONSTRAINT pk_tipos_convenio_id FOREIGN KEY (tipos_convenio_id) REFERENCES tipo_convenio (tipos_convenio_id);

CREATE INDEX indx_tipo_convenio ON instrumentos (tipos_convenio_id);

CREATE TABLE instrumento_iniciativa
(
	instrumento_id        NUMERIC(10, 0) NOT NULL ,
	iniciativa_id        NUMERIC(10, 0) NOT NULL
);

CREATE UNIQUE INDEX PK_instrumento_iniciativa ON instrumento_iniciativa USING btree (instrumento_id   ASC,iniciativa_id   ASC);
ALTER TABLE instrumento_iniciativa ADD CONSTRAINT PK_instrumento_iniciativa_portId PRIMARY KEY (instrumento_id, iniciativa_id);
CREATE  INDEX IE_instrumento_iniciativa_instru ON instrumento_iniciativa USING btree (instrumento_id   ASC);
CREATE  INDEX IE_instrumento_iniciativa_inici ON instrumento_iniciativa USING btree (iniciativa_id   ASC);

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) values ('INSTRUMENTOS', 'Gestionar Instrumentos', NULL, 0, 17, 1, 'Instrumentos');
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

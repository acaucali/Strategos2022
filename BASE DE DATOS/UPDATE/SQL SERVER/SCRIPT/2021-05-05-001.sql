insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_OP', 'PLAN', 'Ver Operativo', 2, 6, 0, 'Ver Opeartivo');

insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_ES', 'PLAN', 'Ver Estrategico', 2, 7, 0, 'Ver Estrategico');

CREATE TABLE dbo.tipo_convenio (
  tipos_convenio_id [numeric](10, 0) NOT NULL,
  nombre [varchar](50) NULL,
  descripcion [varchar](150) NULL,
  CONSTRAINT PK_tipo_convenio PRIMARY KEY (tipos_convenio_id)
);

CREATE TABLE dbo.cooperante (
  cooperante_id [numeric](10, 0) NOT NULL,
  nombre [varchar](50) NULL,
  descripcion [varchar](150) NULL,
  pais [varchar](150) NULL,
  CONSTRAINT PK_cooperante PRIMARY KEY (cooperante_id)
);

CREATE TABLE dbo.instrumentos (
  instrumento_id [numeric](10, 0) NOT NULL,
  nombre_corto [varchar](50) NULL,
  nombre_instrumento [varchar](max) NULL,
  objetivo_instrumento [varchar](max) NULL,
  productos [varchar](max) NULL,
  cooperante_id [numeric](10, 0) NULL,
  tipos_convenio_id [numeric](10, 0) NULL,
  anio [varchar](4) NULL,
  instrumento_marco [varchar](max) NULL,
  fecha_inicio [datetime] NULL,
  fecha_terminacion [datetime] NULL,
  fecha_prorroga [datetime] NULL,
  recursos_pesos [float] NULL,
  recursos_dolares[float] NULL,
  nombre_ejecutante[varchar](250) NULL,
  estatus[numeric] NULL,
  areas_cargo [varchar](500) NULL,
  nombre_responsable_areas[varchar](250) NULL,
  responsable_cgi[varchar](250) NULL,
  observaciones[varchar](250) NULL,
  CONSTRAINT PK_instrumento PRIMARY KEY (instrumento_id)
);


ALTER TABLE instrumentos WITH CHECK ADD CONSTRAINT [pk_cooperante_id] FOREIGN KEY ([cooperante_id]) REFERENCES [dbo].[cooperante] ([cooperante_id]);

CREATE INDEX indx_cooperante ON instrumentos (cooperante_id);


ALTER TABLE instrumentos WITH CHECK ADD CONSTRAINT [pk_tipos_convenio_id] FOREIGN KEY ([tipos_convenio_id]) REFERENCES [dbo].[tipo_convenio] ([tipos_convenio_id]);

CREATE INDEX indx_tipo_convenio ON instrumentos (tipos_convenio_id);

CREATE TABLE dbo.instrumento_iniciativa
(
	instrumento_id NUMERIC(10, 0) NOT NULL ,
	iniciativa_id  NUMERIC(10, 0) NOT NULL
);

CREATE UNIQUE INDEX PK_instrumento_iniciativa ON instrumento_iniciativa (instrumento_id   ASC,iniciativa_id   ASC);
ALTER TABLE instrumento_iniciativa ADD CONSTRAINT PK_instrumento_iniciativa_portId PRIMARY KEY (instrumento_id, iniciativa_id);
CREATE  INDEX IE_instrumento_iniciativa_instru ON instrumento_iniciativa (instrumento_id   ASC);
CREATE  INDEX IE_instrumento_iniciativa_inici ON instrumento_iniciativa (iniciativa_id   ASC);


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

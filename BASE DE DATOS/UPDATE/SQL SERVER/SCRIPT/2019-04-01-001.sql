CREATE TABLE indicador_asignar_inventario(
	asignar_id serial PRIMARY KEY,
	indicador_id integer NOT NULL,
	indicador_insumo_id integer NOT NULL
);

CREATE UNIQUE INDEX ak_asignar ON indicador_asignar_inventario (asignar_id);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (6, 'ruta', 1, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'tipo', 4, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'fechaCompromiso', 3, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'fechaReal', 3, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'creadoId', 4, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'objetoId', 4, null);

CREATE TABLE tipo_proyecto(
	tipo_proyecto_id serial PRIMARY KEY,
	nombre_proyecto VARCHAR(50) NOT NULL
);

CREATE UNIQUE INDEX ak_tipo_proyecto ON tipo_proyecto (tipo_proyecto_id);

ALTER TABLE iniciativa ADD COLUMN tipoId integer;

ALTER TABLE iniciativa ADD CONSTRAINT pk_proyecto_id FOREIGN KEY (tipoId) REFERENCES tipo_proyecto (tipo_proyecto_id);

CREATE INDEX indx_tipo_proyecto ON iniciativa (tipoId);


CREATE TABLE auditoria_medicion (
  auditoria_medicion_id serial PRIMARY KEY,
  sesion varchar(200) NULL,
  fecha_ejecucion timestamp without time zone,
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
  detalle varchar NULL
);

CREATE UNIQUE INDEX ak_auditoria_medicion ON auditoria_medicion (auditoria_medicion_id   ASC);

insert into afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) values ('CLASE_VISTA','CLASE','Ver Objetos',3,8,0,'Ver Objetos Visibles');


UPDATE afw_sistema set actual = '8.01-190401';  
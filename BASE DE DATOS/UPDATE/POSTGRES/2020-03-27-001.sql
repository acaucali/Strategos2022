
CREATE TABLE auditoria(
  auditoria_id serial PRIMARY KEY,
  fecha_ejecucion timestamp without time zone,
  usuario varchar(200) NULL,
  entidad varchar(500) NULL,  
  clase_entidad varchar NOT NULL,
  tipo_evento varchar(50) NOT NULL,
  detalle varchar NULL
);

ALTER TABLE indicador ADD COLUMN resp_notificacion_id integer;

CREATE INDEX ie_indicador_respnotificacion ON indicador (resp_notificacion_id );

ALTER TABLE indicador ADD CONSTRAINT fk_respnotificacion_indicador FOREIGN KEY (resp_notificacion_id) REFERENCES responsable (responsable_id);

CREATE TABLE reporte_servicio (
  reporte_id numeric (10, 0) NOT NULL,	
  responsable_id numeric(10, 0) NOT NULL,
  medicion varchar NOT NULL,
  indicador_id numeric(10, 0) NOT NULL
);

CREATE UNIQUE INDEX ak_reporte_servicio ON reporte_servicio (responsable_id );


CREATE TABLE reporte_grafico
(
  reporte_id serial PRIMARY KEY,
  nombre varchar(50) NOT NULL,
  configuracion text NOT NULL,
  descripcion varchar(1000) NULL ,
  publico NUMERIC(1) NOT NULL ,
  tipo NUMERIC(1) NOT NULL ,
  fecha timestamp without time zone,
  indicadores varchar NULL ,
  organizaciones varchar NULL ,
  periodo_ini  varchar NULL,
  periodo_fin varchar NULL,
  tiempo varchar NULL,  
  usuario_id NUMERIC(10) NOT NULL, 
  grafico_id numeric(10) NULL,  
  CONSTRAINT FK_usuario_reporte_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id),
	  
  CONSTRAINT ak_reporte_grafico UNIQUE (nombre)
);
CREATE INDEX IE_usuario_reporte ON reporte_grafico (usuario_id );


UPDATE afw_sistema set actual = '8.01-200327';  
UPDATE afw_sistema set build = 200327;
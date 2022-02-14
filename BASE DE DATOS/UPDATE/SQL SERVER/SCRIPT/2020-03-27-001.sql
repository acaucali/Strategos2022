CREATE TABLE dbo.auditoria(
  auditoria_id [numeric](10, 0) NOT NULL,
  fecha_ejecucion [datetime] NULL,
  usuario [varchar](200) NULL,
  entidad [varchar](500) NULL,  
  clase_entidad [varchar](max) NOT NULL,
  tipo_evento [varchar](50) NOT NULL,
  detalle varchar(max) NULL
)

CREATE UNIQUE INDEX ak_auditoria ON auditoria (auditoria_id   ASC);

ALTER TABLE indicador ADD resp_notificacion_id [numeric](10, 0);

CREATE INDEX ie_indicador_respnotificacion ON indicador (resp_notificacion_id );

ALTER TABLE indicador ADD CONSTRAINT fk_respnotificacion_indicador FOREIGN KEY (resp_notificacion_id) REFERENCES responsable (responsable_id);



CREATE TABLE dbo.reporte_servicio (
  reporte_id [numeric](10, 0) NOT NULL,	
  responsable_id [numeric](10, 0) NOT NULL,
  medicion [varchar](max) NOT NULL,
  indicador_id [numeric](10, 0) NOT NULL
);

CREATE UNIQUE INDEX ak_reporte_servicio ON reporte_servicio (responsable_id   ASC);


CREATE TABLE dbo.reporte_grafico
(
  reporte_id numeric(10) NOT NULL,
  nombre [varchar](50) NOT NULL,
  configuracion text NOT NULL,
  descripcion [varchar](1000) NULL ,
  publico NUMERIC(1) NOT NULL ,
  tipo NUMERIC(1) NOT NULL ,
  fecha [datetime] NULL,
  indicadores [varchar](max) NULL ,
  organizaciones [varchar](max) NULL ,
  periodo_ini  [varchar](max) NULL,
  periodo_fin [varchar](max) NULL,
  tiempo [varchar](max) NULL,  
  usuario_id NUMERIC(10) NOT NULL, 
  grafico_id numeric(10) NULL,  
  CONSTRAINT pk_reporte_grafico PRIMARY KEY (reporte_id),
  CONSTRAINT FK_usuario_reporte_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id),
	  
  CONSTRAINT ak_reporte_grafico UNIQUE (nombre)
);
CREATE INDEX IE_usuario_reporte ON reporte_grafico (usuario_id   ASC);



UPDATE afw_sistema set actual = '8.01-200327';  
UPDATE afw_sistema set build = 200327;
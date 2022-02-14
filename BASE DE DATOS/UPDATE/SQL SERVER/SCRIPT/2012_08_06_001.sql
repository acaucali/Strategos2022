CREATE TABLE reporte
(
  reporte_id numeric(10) NOT NULL,
  organizacion_id numeric(10) NOT NULL,
  nombre character varying(50) NOT NULL,
  configuracion character varying(2000) NOT NULL,
  creado timestamp without time zone,
  modificado timestamp without time zone,
  creado_id numeric(10),
  modificado_id numeric(10),
  CONSTRAINT pk_reporte PRIMARY KEY (reporte_id),
  CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ak_reporte UNIQUE (organizacion_id, nombre)
);
ALTER TABLE reporte OWNER TO postgres;

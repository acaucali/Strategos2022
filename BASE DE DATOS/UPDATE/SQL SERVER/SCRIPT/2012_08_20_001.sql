CREATE TABLE grafico
(
  grafico_id numeric(10) NOT NULL,
  organizacion_id numeric(10) NOT NULL,
  nombre character varying(50) NOT NULL,
  configuracion character varying(2000) NOT NULL,
  creado timestamp without time zone,
  modificado timestamp without time zone,
  creado_id numeric(10),
  modificado_id numeric(10),
  usuario_id numeric(10),
  CONSTRAINT pk_grafico PRIMARY KEY (grafico_id),
  CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT ak_grafico UNIQUE (organizacion_id, usuario_id, nombre)
);

ALTER TABLE grafico ADD COLUMN objeto_id numeric(10) NULL;
ALTER TABLE grafico ADD COLUMN className character varying(50) NULL;
ALTER TABLE celda DROP COLUMN tipo;
ALTER TABLE celda DROP COLUMN frecuencia;
ALTER TABLE celda DROP COLUMN acumulado;
ALTER TABLE celda DROP COLUMN ejey0;
ALTER TABLE celda DROP COLUMN fecha_inicio;
ALTER TABLE celda DROP COLUMN fecha_fin;

ALTER TABLE grafico DROP CONSTRAINT fk_organizacion_grafico;
ALTER TABLE grafico DROP CONSTRAINT fk_usuario_grafico;
ALTER TABLE grafico DROP CONSTRAINT ak_grafico;
ALTER TABLE grafico ADD CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE grafico ADD CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE; 
	  
ALTER TABLE grafico DROP CONSTRAINT fk_organizacion_grafico;
ALTER TABLE grafico DROP CONSTRAINT fk_usuario_grafico;
ALTER TABLE grafico DROP CONSTRAINT ak_grafico;
ALTER TABLE grafico ADD CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE grafico ADD CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE grafico ADD CONSTRAINT ak_grafico UNIQUE (organizacion_id, usuario_id, nombre, objeto_id);

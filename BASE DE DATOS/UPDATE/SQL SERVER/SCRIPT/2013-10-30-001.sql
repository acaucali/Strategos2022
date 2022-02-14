ALTER TABLE responsable
	ADD CONSTRAINT FK_organizacion_responsable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;
	
ALTER TABLE responsable
	ADD CONSTRAINT FK_usuario_responsable FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;	

ALTER TABLE indicador
	ADD CONSTRAINT FK_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE indicador
	ADD CONSTRAINT FK_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE indicador
	ADD CONSTRAINT FK_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE indicador
	ADD CONSTRAINT FK_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE indicador
	ADD CONSTRAINT FK_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;
	
DROP TABLE reporte;
CREATE TABLE reporte
(
  reporte_id numeric(10) NOT NULL,
  organizacion_id numeric(10) NOT NULL,
  nombre character varying(50) NOT NULL,
  configuracion character varying(2000) NOT NULL,
  descripcion          character varying(1000) NULL ,
  publico              NUMERIC(1) NOT NULL ,
  tipo                 NUMERIC(1) NOT NULL ,
  usuario_id           NUMERIC(10) NOT NULL,
  CONSTRAINT pk_reporte PRIMARY KEY (reporte_id),
  CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT FK_usuario_reporte FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT ak_reporte UNIQUE (organizacion_id, nombre)
);

CREATE INDEX IE_usuario_reporte ON reporte USING btree (usuario_id   ASC);

ALTER TABLE grafico
   ALTER COLUMN nombre TYPE character varying(100);
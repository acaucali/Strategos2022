ALTER TABLE responsable
	ADD (CONSTRAINT FK_organizacion_responsable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);
	
ALTER TABLE responsable
	ADD (CONSTRAINT FK_usuario_responsable FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);	

ALTER TABLE indicador
	ADD (CONSTRAINT FK_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador
	ADD (CONSTRAINT FK_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador
	ADD (CONSTRAINT FK_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador
	ADD (CONSTRAINT FK_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE indicador
	ADD (CONSTRAINT FK_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

DROP TABLE reporte;
CREATE TABLE reporte
(
	reporte_id           NUMBER(10) NOT NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	configuracion        LONG VARCHAR NOT NULL ,
	descripcion          VARCHAR2(1000) NULL ,
	publico              NUMBER(1) NOT NULL ,
	tipo                 NUMBER(1) NOT NULL ,
	usuario_id           NUMBER(10) NOT NULL 
);

ALTER TABLE reporte ADD CONSTRAINT  pk_reporte PRIMARY KEY (reporte_id);
CREATE UNIQUE INDEX ak_reporte ON reporte (organizacion_id   ASC,nombre   ASC);
ALTER TABLE reporte ADD CONSTRAINT  ak_reporte UNIQUE (organizacion_id,nombre);
CREATE  INDEX IE_usuario_reporte ON reporte (usuario_id   ASC);
	
ALTER TABLE reporte
	ADD (CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE reporte
	ADD (CONSTRAINT FK_usuario_reporte FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE);

ALTER TABLE grafico MODIFY nombre VARCHAR2(100);
   
COMMIT;
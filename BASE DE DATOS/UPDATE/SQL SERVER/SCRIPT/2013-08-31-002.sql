ALTER TABLE iniciativa DROP CONSTRAINT ak1_iniciativa;
CREATE UNIQUE INDEX ak_iniciativa ON iniciativa USING btree (organizacion_id   ASC,nombre   ASC);

DROP INDEX xif1iniciativa;
CREATE  INDEX IE_iniciativa_organizacionid ON iniciativa USING btree (organizacion_id   ASC);

DROP INDEX xif10iniciativa;
CREATE  INDEX IE_iniciativa_proyecto ON iniciativa USING btree (proyecto_id   ASC);

CREATE  INDEX IE_iniciativa_clase ON iniciativa USING btree (clase_id   ASC);
CREATE  INDEX IE_iniciativa_indicador ON iniciativa USING btree (indicador_id   ASC);
CREATE  INDEX IE_iniciativa_modificado ON iniciativa USING btree (modificado_id   ASC);
CREATE  INDEX IE_iniciativa_creado ON iniciativa USING btree (creado_id   ASC);

DROP INDEX xif3iniciativa;
CREATE  INDEX IE_iniciativa_asociado ON iniciativa USING btree (iniciativa_asociada_id   ASC);

CREATE  INDEX IE_iniciativa_asociado_plan ON iniciativa USING btree (iniciativa_asociada_plan_id   ASC);
CREATE  INDEX IE_iniciativa_resp_ejecutar ON iniciativa USING btree (resp_cargar_ejecutado_id   ASC);
CREATE  INDEX IE_iniciativa_resp_meta ON iniciativa USING btree (resp_fijar_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_lograr ON iniciativa USING btree (resp_lograr_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_seguimiento ON iniciativa USING btree (resp_seguimiento_id   ASC);
CREATE  INDEX IE_iniciativa_resp_cargar ON iniciativa USING btree (resp_cargar_meta_id   ASC);

DROP INDEX xif1iniciativa_ano;
CREATE  INDEX IE_iniciativaId_ano ON iniciativa_ano USING btree (iniciativa_id   ASC);

DROP INDEX xif2iniciativa_por_perspectiva;
CREATE  INDEX IE_inicia_pers_iniciativaid ON iniciativa_por_perspectiva USING btree (iniciativa_id   ASC);

DROP INDEX xif1iniciativa_por_perspectiva;
CREATE  INDEX IE_inic_pers_persid ON iniciativa_por_perspectiva USING btree (perspectiva_id   ASC);

DROP INDEX xif3iniciativa_por_plan; 
CREATE  INDEX IE_iniciativa_por_plan_claseId ON iniciativa_por_plan USING btree (clase_id   ASC);

DROP INDEX xif2iniciativa_por_plan;
CREATE  INDEX IE_iniciativa_por_plan_iniid ON iniciativa_por_plan USING btree (iniciativa_id   ASC);

DROP INDEX xif1iniciativa_por_plan;
CREATE  INDEX IE_iniciativa_por_plan_planid ON iniciativa_por_plan USING btree (plan_id   ASC);

DROP INDEX xif1prd_producto;
CREATE  INDEX IE_prd_producto_iniciativaid ON prd_producto USING btree (iniciativa_id   ASC);

DROP INDEX xif1prd_seg_producto;
CREATE  INDEX IE_prd_seg_producto_inicitiva ON prd_seg_producto USING btree (iniciativa_id   ASC);

DROP INDEX xif2prd_seg_producto;
CREATE  INDEX IE_prd_seg_producto_producto ON prd_seg_producto USING btree (producto_id   ASC);

DROP INDEX xif1prd_seguimiento;
CREATE  INDEX xif1prd_seguimiento ON prd_seguimiento USING btree (iniciativa_id   ASC);

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_organizacion_iniciativa FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_modificado_iniciativa FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_creado_iniciativa FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_iniciativa_asociadaid FOREIGN KEY (iniciativa_asociada_id) REFERENCES iniciativa (iniciativa_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_iniciativa_asociada_plan FOREIGN KEY (iniciativa_asociada_plan_id) REFERENCES plan (plan_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_fijar_meta FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_lograr_meta FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_seguimiento FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_cargar_meta FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_cargar_ejecutado FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_proyecto_iniciativa FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_clase_iniciativa FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_indicador_iniciativa FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_ano
	ADD CONSTRAINT fk_iniciativa_ano FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_objeto
	ADD CONSTRAINT fK_iniciativa_objeto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_perspectiva
	ADD CONSTRAINT fk_iniciativa_perspectiva FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_perspectiva
	ADD CONSTRAINT fK_perspectiva_iniciativa FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_plan
	ADD CONSTRAINT fk_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_plan
	ADD CONSTRAINT fk_plan_iniciativa FOREIGN KEY (plan_id) REFERENCES plan (plan_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_plan
	ADD CONSTRAINT fk_clase_iniciativa_ano FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE;

ALTER TABLE prd_producto
	ADD CONSTRAINT fk_iniciativa_producto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE prd_seg_producto
	ADD CONSTRAINT fk_iniciativa_pro_seg FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE prd_seg_producto
	ADD CONSTRAINT fk_producto_pro_seg FOREIGN KEY (producto_id) REFERENCES prd_producto (producto_id) ON DELETE CASCADE;

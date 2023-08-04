ALTER TABLE iniciativa DROP CONSTRAINT ak1_iniciativa;
ALTER TABLE iniciativa
ADD CONSTRAINT  ak1_iniciativa UNIQUE (organizacion_id,nombre);

DROP INDEX XIF1INICIATIVA;
CREATE  INDEX IE_iniciativa_organizacionid ON iniciativa
(organizacion_id   ASC);

DROP INDEX XIF10INICIATIVA;
CREATE  INDEX IE_iniciativa_proyecto ON iniciativa
(proyecto_id   ASC);

CREATE  INDEX IE_iniciativa_clase ON iniciativa
(clase_id   ASC);

CREATE  INDEX IE_iniciativa_indicador ON iniciativa
(indicador_id   ASC);

CREATE  INDEX IE_iniciativa_modificado ON iniciativa
(modificado_id   ASC);

CREATE  INDEX IE_iniciativa_creado ON iniciativa
(creado_id   ASC);

DROP INDEX XIF3INICIATIVA;
CREATE  INDEX IE_iniciativa_asociado ON iniciativa
(iniciativa_asociada_id   ASC);

CREATE  INDEX IE_iniciativa_asociado_plan ON iniciativa
(iniciativa_asociada_plan_id   ASC);

CREATE  INDEX IE_iniciativa_resp_ejecutar ON iniciativa
(resp_cargar_ejecutado_id   ASC);

CREATE  INDEX IE_iniciativa_resp_meta ON iniciativa
(resp_fijar_meta_id   ASC);

CREATE  INDEX IE_iniciativa_resp_lograr ON iniciativa
(resp_lograr_meta_id   ASC);

CREATE  INDEX IE_iniciativa_resp_seguimiento ON iniciativa
(resp_seguimiento_id   ASC);

CREATE  INDEX IE_iniciativa_resp_cargar ON iniciativa
(resp_cargar_meta_id   ASC);

DROP INDEX XIF1INICIATIVA_ANO;
CREATE  INDEX IE_iniciativaId_ano ON iniciativa_ano
(iniciativa_id   ASC);

DROP INDEX XIF2INICIATIVA_POR_PERSPECTIVA;
CREATE  INDEX IE_inicia_pers_iniciativaid ON iniciativa_por_perspectiva
(iniciativa_id   ASC);

DROP INDEX XIF1INICIATIVA_POR_PERSPECTIVA;
CREATE  INDEX IE_inic_pers_persid ON iniciativa_por_perspectiva
(perspectiva_id   ASC);

DROP INDEX XIF3INICIATIVA_POR_PLAN;
CREATE  INDEX IE_iniciativa_por_plan_claseId ON iniciativa_por_plan
(clase_id   ASC);

DROP INDEX XIF2INICIATIVA_POR_PLAN;
CREATE  INDEX IE_iniciativa_por_plan_iniid ON iniciativa_por_plan
(iniciativa_id   ASC);

DROP INDEX XIF1INICIATIVA_POR_PLAN;
CREATE  INDEX IE_iniciativa_por_plan_planid ON iniciativa_por_plan
(plan_id   ASC);

DROP INDEX XIF1PRD_PRODUCTO;
CREATE  INDEX IE_prd_producto_iniciativaid ON prd_producto
(iniciativa_id   ASC);

DROP INDEX XIF1PRD_SEG_PRODUCTO;
CREATE  INDEX IE_prd_seg_producto_inicitiva ON prd_seg_producto
(iniciativa_id   ASC);

DROP INDEX XIF2PRD_SEG_PRODUCTO;
CREATE  INDEX IE_prd_seg_producto_producto ON prd_seg_producto
(producto_id   ASC);

DROP INDEX XIF1PRD_SEGUIMIENTO;
CREATE  INDEX IE_prd_seguimiento ON prd_seguimiento
(iniciativa_id   ASC);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_organizacion_iniciativa FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_modificado_iniciativa FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_creado_iniciativa FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_iniciativa_asociadaid FOREIGN KEY (iniciativa_asociada_id) REFERENCES iniciativa (iniciativa_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_iniciativa_asociada_plan FOREIGN KEY (iniciativa_asociada_plan_id) REFERENCES plan (plan_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_fijar_meta FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_lograr_meta FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_seguimiento FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_resp_cargar_meta FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_cargar_ejecutado FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_proyecto_iniciativa FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE SET NULL);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_clase_iniciativa FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);

ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_indicador_iniciativa FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_ano
	ADD (CONSTRAINT fk_iniciativa_ano FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_objeto
	ADD (CONSTRAINT fK_iniciativa_objeto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_perspectiva
	ADD (CONSTRAINT fk_iniciativa_perspectiva FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_perspectiva
	ADD (CONSTRAINT fK_perspectiva_iniciativa FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_plan
	ADD (CONSTRAINT fk_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_plan
	ADD (CONSTRAINT fk_plan_iniciativa FOREIGN KEY (plan_id) REFERENCES plan (plan_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_por_plan
	ADD (CONSTRAINT fk_clase_iniciativa_ano FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);

ALTER TABLE prd_producto
	ADD (CONSTRAINT fk_iniciativa_producto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE prd_seg_producto
	ADD (CONSTRAINT fk_iniciativa_pro_seg FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE prd_seg_producto
	ADD (CONSTRAINT fk_producto_pro_seg FOREIGN KEY (producto_id) REFERENCES prd_producto (producto_id) ON DELETE CASCADE);
	
COMMIT;

ALTER TABLE pry_actividad ADD objeto_asociado_className VARCHAR2(50) NULL;

ALTER TABLE perspectiva DROP COLUMN objetivo_impacta_id;

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_padre FOREIGN KEY (padre_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);	
	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES plan (plan_id) ON DELETE CASCADE);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_responsable FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);
	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_creado FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_modificado FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_clase FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE SET NULL);

ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_logropacial FOREIGN KEY (nl_par_indicador_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);
	
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_logroanual FOREIGN KEY (nl_ano_indicador_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);

-- perspectiva_relacion
CREATE TABLE perspectiva_relacion
(
	perspectiva_id       NUMBER(10) NOT NULL ,
	relacion_id          NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX PK_perspectiva_relacion ON perspectiva_relacion (perspectiva_id   ASC,relacion_id   ASC);

ALTER TABLE perspectiva_relacion
	ADD CONSTRAINT  PK_perspectiva_relacion PRIMARY KEY (perspectiva_id,relacion_id);

CREATE  INDEX IE_relacion_perspectiva ON perspectiva_relacion (perspectiva_id   ASC);

CREATE  INDEX IE_relacion_relacion ON perspectiva_relacion (relacion_id   ASC);

ALTER TABLE perspectiva_relacion
	ADD (CONSTRAINT FK_relacion_perspectiva FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);

ALTER TABLE perspectiva_relacion
	ADD (CONSTRAINT FK_relacion_relacion FOREIGN KEY (relacion_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE);
	
COMMIT;
--Indicador Por Plan
ALTER TABLE indicador_por_plan ADD COLUMN crecimiento numeric(1,0);
ALTER TABLE indicador_por_plan ADD COLUMN tipo_medicion numeric(1,0);

DROP INDEX xif1indicador_por_plan;
DROP INDEX xif2indicador_por_plan;

CREATE INDEX IE_indicadorId_indicador_por_p ON indicador_por_plan (indicador_id   ASC);
CREATE INDEX IE_planId_indicador_por_plan ON indicador_por_plan (plan_id   ASC);

ALTER TABLE indicador_por_plan
	ADD CONSTRAINT FK_ind_indicador_plan FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

ALTER TABLE indicador_por_plan
	ADD CONSTRAINT FK_plan_indicador_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;	

--Indicador Estado	
CREATE  INDEX IE_planId_indicador_estado ON indicador_estado (plan_id   ASC);
CREATE  INDEX IE_indicadorId_indicador_estad ON indicador_estado (indicador_id   ASC);

ALTER TABLE indicador_estado
	ADD CONSTRAINT FK_plan_indicador_estado FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;

ALTER TABLE indicador_estado
	ADD CONSTRAINT FK_indicador_indicador_estado FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

-- Index Meta		
CREATE  INDEX IE_meta_tipo ON meta (tipo   ASC);

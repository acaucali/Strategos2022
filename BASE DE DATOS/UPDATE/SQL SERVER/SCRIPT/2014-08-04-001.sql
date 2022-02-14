--Borrar Index
DROP INDEX ie_modelo_plan_id;

-- Borrar Contrains
ALTER TABLE modelo DROP CONSTRAINT modelo_pkey;

-- Elimianr Campos Inecesarios
ALTER TABLE modelo DROP COLUMN creado;
ALTER TABLE modelo DROP COLUMN modificado;
ALTER TABLE modelo DROP COLUMN creado_id;
ALTER TABLE modelo DROP COLUMN modificado_id;

-- Crear Campos nuevos
ALTER TABLE modelo ADD COLUMN modelo_id numeric(10,0);
ALTER TABLE modelo ADD COLUMN nombre character varying(100);
ALTER TABLE modelo ADD COLUMN binario text;

-- Actualizar campos necesarios 
UPDATE modelo 
SET modelo_id = plan_id, nombre = 'Principal';

UPDATE modelo 
SET binario = b.binario
FROM modelo a, modelo_binario b
WHERE a.plan_id = b.plan_id;

ALTER TABLE modelo ALTER COLUMN modelo_id SET NOT NULL;
ALTER TABLE modelo ALTER COLUMN nombre SET NOT NULL;

CREATE UNIQUE INDEX ak_modelo ON modelo USING btree (modelo_id, plan_id);

ALTER TABLE modelo 
	ADD CONSTRAINT pk_id_plan_modelo PRIMARY KEY (modelo_id,plan_id);

CREATE INDEX IE_plan_modelo ON modelo USING btree (plan_id);

ALTER TABLE modelo 
	ADD CONSTRAINT FK_planes_modelo FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;

--DROP TABLE modelo_binario;

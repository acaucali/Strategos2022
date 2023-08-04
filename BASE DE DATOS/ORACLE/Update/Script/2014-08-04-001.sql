-- Elimianr Campos Inecesarios
ALTER TABLE modelo DROP COLUMN creado;
ALTER TABLE modelo DROP COLUMN modificado;
ALTER TABLE modelo DROP COLUMN creado_id;

-- Crear Campos nuevos
ALTER TABLE modelo ADD modelo_id NUMBER(10,0);
ALTER TABLE modelo ADD nombre VARCHAR2(100);
ALTER TABLE modelo ADD binario LONG RAW;

-- Actualizar campos necesarios 
UPDATE modelo SET modelo_id = plan_id, nombre = 'Principal';

ALTER TABLE modelo MODIFY modelo_id NUMBER(10,0) NOT NULL;
ALTER TABLE modelo MODIFY nombre VARCHAR2(100) NOT NULL;
  
CREATE UNIQUE INDEX ak_modelo ON modelo (modelo_id   ASC,plan_id   ASC);

ALTER TABLE modelo DROP CONSTRAINT MODELO_PKEY CASCADE;
ALTER TABLE modelo ADD CONSTRAINT pk_id_plan_modelo PRIMARY KEY (modelo_id,plan_id);

ALTER TABLE modelo 
	ADD (CONSTRAINT FK_planes_modelo FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

COMMIT;

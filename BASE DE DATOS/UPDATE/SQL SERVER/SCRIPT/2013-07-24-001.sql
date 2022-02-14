ALTER TABLE grafico ADD COLUMN plan_id numeric(10);

ALTER TABLE grafico DROP CONSTRAINT ak_grafico;
ALTER TABLE grafico ADD CONSTRAINT ak_grafico UNIQUE (organizacion_id, usuario_id, nombre, objeto_id, plan_Id);

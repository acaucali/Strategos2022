ALTER TABLE grafico ADD plan_Id NUMBER(10) NULL;

ALTER TABLE Grafico DROP CONSTRAINT ak_grafico;
DROP INDEX ak_grafico;

CREATE UNIQUE INDEX ak_grafico ON grafico
(organizacion_id   ASC,usuario_id   ASC,nombre   ASC,objeto_id   ASC, plan_Id   ASC);

ALTER TABLE grafico ADD CONSTRAINT ak_grafico UNIQUE (organizacion_id,usuario_id,nombre,objeto_id, plan_Id);

COMMIT;
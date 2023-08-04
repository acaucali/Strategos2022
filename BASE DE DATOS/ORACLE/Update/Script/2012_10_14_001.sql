ALTER TABLE grafico ADD objeto_id NUMBER(10) NULL;
ALTER TABLE grafico ADD className VARCHAR2(50) NULL;
ALTER TABLE CELDA DROP COLUMN Tipo;
ALTER TABLE CELDA DROP COLUMN Frecuencia;
ALTER TABLE CELDA DROP COLUMN Acumulado;
ALTER TABLE CELDA DROP COLUMN EjeY0;
ALTER TABLE CELDA DROP COLUMN Fecha_Inicio;
ALTER TABLE CELDA DROP COLUMN Fecha_Fin;

ALTER TABLE Grafico DROP CONSTRAINT ak_grafico;
DROP INDEX ak_grafico;
CREATE UNIQUE INDEX ak_grafico ON grafico
(organizacion_id   ASC,usuario_id   ASC,nombre   ASC,objeto_id   ASC);
ALTER TABLE grafico
ADD CONSTRAINT  ak_grafico UNIQUE (organizacion_id,usuario_id,nombre,objeto_id);

COMMIT;

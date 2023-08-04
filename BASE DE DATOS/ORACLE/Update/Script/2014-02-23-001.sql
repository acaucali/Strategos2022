ALTER TABLE reporte ADD corte NUMBER(1) NULL;
UPDATE reporte SET corte = 1;
ALTER TABLE reporte MODIFY corte NUMBER(1) NOT NULL;

ALTER TABLE afw_sistema DROP COLUMN configuracion;
ALTER TABLE afw_sistema DROP COLUMN creado;
ALTER TABLE afw_sistema DROP COLUMN modificado;
ALTER TABLE afw_sistema ADD cmaxc VARCHAR2(1000);
UPDATE afw_sistema SET producto = 'PROTOTIPO';

COMMIT;
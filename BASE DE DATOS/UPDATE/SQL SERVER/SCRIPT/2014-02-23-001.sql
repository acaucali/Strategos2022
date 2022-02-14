ALTER TABLE reporte ADD COLUMN corte numeric(1,0);
UPDATE reporte SET corte = 1;
ALTER TABLE reporte ALTER COLUMN corte SET NOT NULL;

ALTER TABLE afw_sistema DROP COLUMN configuracion;
ALTER TABLE afw_sistema DROP COLUMN creado;
ALTER TABLE afw_sistema DROP COLUMN modificado;
ALTER TABLE afw_sistema ADD COLUMN cmaxc character varying(1000);
UPDATE afw_sistema SET producto = 'PROTOTIPO';
ALTER TABLE reporte ALTER COLUMN configuracion TYPE text;

ALTER TABLE afw_sistema ADD COLUMN cmaxcold character varying(1000);
UPDATE afw_sistema SET cmaxcold = cmaxc;
UPDATE afw_sistema SET producto = 'PROTOTIPO', cmaxc = NULL;
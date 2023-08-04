ALTER TABLE afw_sistema ADD cmaxcold VARCHAR2(1000);
UPDATE afw_sistema SET cmaxcold = cmaxc;
UPDATE afw_sistema SET producto = 'PROTOTIPO', cmaxc = NULL;

COMMIT;
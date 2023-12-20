ALTER TABLE iniciativa ADD codigo VARCHAR2(50);
ALTER TABLE iniciativa ADD unidad_medida NUMBER(10);

UPDATE afw_sistema set actual = '9.01-230901';  
UPDATE afw_sistema set build = 230901;
alter table iniciativa add column codigo varchar(50);
alter table iniciativa add column unidad_medida numeric(10); 

UPDATE afw_sistema set actual = '9.01-230901';  
UPDATE afw_sistema set build = 230901;
UPDATE afw_sistema set actual = '9.01-230727';  
UPDATE afw_sistema set build = 230727;

ALTER TABLE vista 
    ALTER COLUMN nombre varchar(250);
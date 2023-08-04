CREATE TABLE afw_modulo
(
	Id                   VARCHAR2(50) NOT NULL ,
	Modulo               VARCHAR2(100) NOT NULL ,
	Activo               NUMBER(1) NOT NULL 
);

CREATE UNIQUE INDEX IE_afw_modulo ON afw_modulo (Id   ASC);

ALTER TABLE afw_modulo ADD CONSTRAINT  IE_afw_modulo PRIMARY KEY (Id);
   
COMMIT;
CREATE TABLE afw_modulo
(
	Id                   character varying(50) NOT NULL ,
	Modulo               character varying(100) NOT NULL ,
	Activo               numeric(1, 0) NOT NULL 
);

ALTER TABLE afw_modulo ADD CONSTRAINT  IE_afw_modulo PRIMARY KEY (Id);
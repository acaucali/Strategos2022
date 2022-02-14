ALTER TABLE afw_servicio ADD COLUMN log text;
ALTER TABLE afw_servicio DROP COLUMN datos;
ALTER TABLE afw_servicio ADD COLUMN mensaje character varying(1000) NOT NULL;

DROP INDEX ak_afw_servicio_estatus;
DROP INDEX ak_afw_servicio_usuario;
DROP INDEX AK_afw_message;

ALTER TABLE afw_message ADD COLUMN fuente  character varying(50) NULL;
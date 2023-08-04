ALTER TABLE afw_servicio ADD log LONG VARCHAR;
ALTER TABLE afw_servicio DROP COLUMN datos;
ALTER TABLE afw_servicio ADD mensaje VARCHAR2(1000) NOT NULL;

ALTER TABLE afw_message DROP CONSTRAINT AK_afw_message;
ALTER TABLE afw_servicio DROP CONSTRAINT AK_afw_servicio_usuario;
ALTER TABLE afw_servicio DROP CONSTRAINT AK_afw_servicio_estatus;

ALTER TABLE afw_message ADD fuente VARCHAR(50);

COMMIT;
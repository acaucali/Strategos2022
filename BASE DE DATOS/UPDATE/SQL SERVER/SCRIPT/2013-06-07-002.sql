ALTER TABLE afw_auditoria_entero ADD valor_anterior NUMERIC(10, 0) NULL;
ALTER TABLE afw_auditoria_string ADD valor_anterior character varying(500) NULL;
ALTER TABLE afw_auditoria_memo ADD valor_anterior character varying(2000) NULL;
ALTER TABLE afw_auditoria_flotante ADD valor_anterior double precision NULL;
ALTER TABLE afw_auditoria_fecha ADD valor_anterior TIMESTAMP without time zone NULL;

ALTER TABLE afw_auditoria_entero ADD valor_anterior NUMBER(10) NULL;
ALTER TABLE afw_auditoria_string ADD valor_anterior VARCHAR2(500) NULL;
ALTER TABLE afw_auditoria_memo ADD valor_anterior VARCHAR2(2000) NULL;
ALTER TABLE afw_auditoria_flotante ADD valor_anterior FLOAT NULL;
ALTER TABLE afw_auditoria_fecha ADD valor_anterior TIMESTAMP NULL;

COMMIT;
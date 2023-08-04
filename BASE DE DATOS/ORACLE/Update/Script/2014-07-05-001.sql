ALTER TABLE afw_auditoria_evento MODIFY instancia_nombre VARCHAR2(300);
ALTER TABLE indicador MODIFY nombre VARCHAR2(150);
ALTER TABLE indicador MODIFY nombre_corto VARCHAR2(150);

COMMIT;

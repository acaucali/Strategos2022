UPDATE afw_usuario SET estatus = 0, bloqueado = 0;
UPDATE afw_usuario SET estatus = 1 WHERE inactivo = 1;

ALTER TABLE afw_usuario DROP COLUMN Inactivo;

COMMIT;


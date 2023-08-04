ALTER TABLE afw_usuario ADD deshabilitado NUMBER(1) NULL;
ALTER TABLE afw_usuario ADD forzarCambiarPwd NUMBER(1) NULL;
COMMIT;
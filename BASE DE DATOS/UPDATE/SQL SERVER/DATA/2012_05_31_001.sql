INSERT INTO afw_permiso(
            permiso_id, permiso, padre_id, nivel, grupo, global, descripcion)
    VALUES ('CONFIGURACION', 'Configuración', NULL, 0, NULL, 1, 'Configuración');

INSERT INTO afw_permiso(
            permiso_id, permiso, padre_id, nivel, grupo, global, descripcion)
    VALUES ('CONFIGURACION_SET', 'Configuración del Login', 'CONFIGURACION', 1, 1, 1, 'Configuración del Login');

INSERT INTO afw_permiso(
            permiso_id, permiso, padre_id, nivel, grupo, global, descripcion)
    VALUES ('CONFIGURACION_LOGS', 'Logs de Auditoria de Acceso al Sistema', 'CONFIGURACION', 1, 1, 1, 'Logs de Auditoria de Acceso al Sistema');
	
------------------------------------
-- afw_usuario  
------------------------------------
--ALTER TABLE afw_usuario ADD COLUMN CambiarPassword numeric(1,0);
--UPDATE afw_usuario SET CambiarPassword = 0;
--ALTER TABLE afw_usuario ALTER COLUMN cambiarpassword SET NOT NULL;

--ALTER TABLE afw_usuario ADD COLUMN Bloqueado numeric(1,0);
--UPDATE afw_usuario SET Bloqueado = 0;
--ALTER TABLE afw_usuario ALTER COLUMN Bloqueado SET NOT NULL;

--ALTER TABLE afw_usuario ADD COLUMN Inhabilitado numeric(1,0);
--UPDATE afw_usuario SET Inhabilitado = 0;
--ALTER TABLE afw_usuario ALTER COLUMN Inhabilitado SET NOT NULL;

--ALTER TABLE afw_usuario ADD COLUMN NumeroEntradasFallidas numeric(2,0);
--UPDATE afw_usuario SET NumeroEntradasFallidas = 0;
--ALTER TABLE afw_usuario ALTER COLUMN NumeroEntradasFallidas SET NOT NULL;
--Configuracion
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	UPDATE AFW_PERMISO SET grupo = 10 WHERE permiso_id = 'ORGANIZACION';
	UPDATE AFW_PERMISO SET grupo = 11 WHERE permiso_id = 'CONFIGURACION';
	
	IF NOT EXISTS (SELECT * FROM AFW_PERMISO WHERE permiso_id = 'CONFIG')	
	THEN
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIG', 'Configuración del Sistema', NULL, 0, 9, 1, 'Configuración del Sistema');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIGURACION_SISTEMA', 'Gestionar Configuración del Sistema', 'CONFIG', 1, 1, 0, 'Gestionar Configuración del Sistema');
	ELSE
		UPDATE AFW_PERMISO SET grupo = 9 WHERE permiso_id = 'CONFIG';
	END IF;	
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

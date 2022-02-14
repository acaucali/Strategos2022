-- Servicio
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_REAL',  'INDICADOR_MEDICION_CARGAR',  'Real',  5,   1,   0,  'Real');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_PROG',  'INDICADOR_MEDICION_CARGAR',  'Programado',  5,   2,   0,  'Programado');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MAX',  'INDICADOR_MEDICION_CARGAR',  'Máximo',  5,   3,   0,  'Máximo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MIN',  'INDICADOR_MEDICION_CARGAR',  'Mínimo',  5,   4,   0,  'Mínimo');

--Limpiar la tabla afw_version
CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
	r afw_permiso_grupo%rowtype;
	m afw_permiso%rowtype;
	vCount numeric(5,0);
    
BEGIN
    FOR r IN SELECT permiso_id, grupo_id from afw_permiso_grupo where permiso_id = 'INDICADOR_MEDICION_CARGAR'
    LOOP

		FOR m IN SELECT permiso_id FROM afw_permiso WHERE padre_id = r.permiso_id
		LOOP

			SELECT INTO vCount COUNT(*) FROM afw_permiso_grupo WHERE permiso_id = m.permiso_id AND grupo_id = r.grupo_id;
			IF vCount = 0
			THEN
				INSERT INTO afw_permiso_grupo (permiso_id, grupo_id) VALUES (m.permiso_id, r.grupo_id);
			END IF;
		END LOOP;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

SELECT permiso_id, grupo_id 
from afw_permiso_grupo 
where permiso_id IN (SELECT permiso_id FROM afw_permiso WHERE padre_id = 'INDICADOR_MEDICION_CARGAR')
ORDER BY grupo_id, permiso_id;

UPDATE afw_sistema set actual = '8.01-160514';

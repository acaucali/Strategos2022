-- Servicio
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_REAL',  'INDICADOR_MEDICION_CARGAR',  'Real',  5,   1,   0,  'Real');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_PROG',  'INDICADOR_MEDICION_CARGAR',  'Programado',  5,   2,   0,  'Programado');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MAX',  'INDICADOR_MEDICION_CARGAR',  'Máximo',  5,   3,   0,  'Máximo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MIN',  'INDICADOR_MEDICION_CARGAR',  'Mínimo',  5,   4,   0,  'Mínimo');

DECLARE
	vpermisoId afw_permiso_grupo.permiso_id%TYPE;
	vgrupoId afw_permiso_grupo.grupo_id%TYPE;
	vpermisoIdAdd afw_permiso.permiso_id%TYPE;
	vCount NUMBER(5);
	
	CURSOR curItems IS SELECT permiso_id, grupo_id from afw_permiso_grupo where permiso_id = 'INDICADOR_MEDICION_CARGAR';
	CURSOR curPermiso IS SELECT permiso_id FROM afw_permiso WHERE padre_id = vpermisoId;

BEGIN
  OPEN curItems;
  LOOP  
    FETCH curItems INTO vpermisoId, vgrupoId;
    EXIT WHEN curItems%NOTFOUND;

		BEGIN
			OPEN curPermiso;
			LOOP
				FETCH curPermiso INTO vpermisoIdAdd;
				EXIT WHEN curPermiso%NOTFOUND;
				SELECT COUNT(*) INTO vCount FROM afw_permiso_grupo WHERE permiso_id = vpermisoIdAdd AND grupo_id = vgrupoId;
				IF vCount = 0 THEN 
				BEGIN
					INSERT INTO afw_permiso_grupo (permiso_id, grupo_id) VALUES (vpermisoIdAdd, vgrupoId);
				END;
				END IF;
			END LOOP;
			CLOSE curPermiso;
		END;
  END LOOP;
  CLOSE curItems;
END;
/

UPDATE afw_sistema set actual = '8.01-160514';

SELECT permiso_id, grupo_id 
from afw_permiso_grupo 
where permiso_id IN (SELECT permiso_id FROM afw_permiso WHERE padre_id = 'INDICADOR_MEDICION_CARGAR')
ORDER BY grupo_id, permiso_id;

COMMIT;

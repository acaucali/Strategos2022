UPDATE PERSPECTIVA SET clase_id = null WHERE clase_id NOT IN (SELECT clase_id FROM clase);
DECLARE
	vnombrePerspectiva PERSPECTIVA.Nombre%TYPE;
	vperspectivaId PERSPECTIVA.perspectiva_id%TYPE;
	vclaseIdPadre Planes.clase_id%TYPE;
	vorganizacionId Planes.organizacion_id%TYPE;
	vplanId Planes.plan_id%TYPE;
	vclaseId NUMBER(10);
	vCount NUMBER(5);

	CURSOR curItems IS SELECT
							Perspectiva.Perspectiva_Id,
							Perspectiva.nombre, 
							Planes.clase_ID, 
							Planes.organizacion_id,
							Planes.plan_id
						FROM Perspectiva 
						INNER JOIN planes ON Perspectiva.PLAN_ID = planes.plan_id
						WHERE Perspectiva.clase_id IS NULL;
	
	BEGIN
	OPEN curItems;
	LOOP  
		FETCH curItems INTO vPerspectivaId, vnombrePerspectiva, vclaseIdPadre, vorganizacionId, vplanId;
		EXIT WHEN curItems%NOTFOUND;
		BEGIN

			SELECT COUNT(*) INTO vCount FROM clase WHERE nombre = vnombrePerspectiva AND organizacion_id = vorganizacionId;
			IF vCount = 0 THEN 
			BEGIN
				SELECT VISION_UNIQUE_ID.NEXTVAL INTO vclaseId FROM DUAL;
				
				INSERT INTO Clase (clase_id, padre_id, organizacion_id, nombre, tipo, visible)
				VALUES (vclaseId, vclaseIdPadre, vorganizacionId, vnombrePerspectiva, 3, 0);
			END;
			ELSE
			BEGIN
				SELECT clase_id INTO vclaseId FROM clase WHERE nombre = vnombrePerspectiva AND organizacion_id = vorganizacionId AND rownum = 1;
			END;
			END IF;				
			
			UPDATE PERSPECTIVA SET Clase_Id = vclaseId WHERE perspectiva_id = vPerspectivaId;

		END;
	END LOOP;
	CLOSE curItems;
END;
/

UPDATE afw_sistema set actual = '8.01-170618';

COMMIT;

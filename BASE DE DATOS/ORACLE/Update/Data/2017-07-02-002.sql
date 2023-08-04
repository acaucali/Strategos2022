DECLARE
	vActividad_id pry_actividad.actividad_id%TYPE;
	vIndicadorId pry_actividad.indicador_id%TYPE;
	vActividadNombre pry_actividad.nombre%TYPE;
	vIndicadorNombre indicador.nombre%TYPE;
	vClaseId pry_actividad.clase_id%TYPE;
	vorganizacionId iniciativa.ORGANIZACION_ID%TYPE;
	vunidadId pry_actividad.UNIDAD_ID%TYPE;
	valertaZV INC_ACTIVIDAD.alerta_zv%TYPE;
	valertaZA INC_ACTIVIDAD.alerta_za%TYPE;
	vClaseIdPadre iniciativa.clase_ID%TYPE;
	vCount NUMBER(5);
	CURSOR curTable IS SELECT 
							pry_actividad.actividad_id, 
							pry_actividad.nombre, 
							pry_actividad.clase_id, 
							iniciativa.ORGANIZACION_ID, 
							pry_actividad.UNIDAD_ID, 
							INC_ACTIVIDAD.alerta_za, 
							INC_ACTIVIDAD.alerta_zv, 
							iniciativa.clase_ID
						FROM pry_actividad 
						INNER JOIN INC_ACTIVIDAD ON pry_actividad.actividad_id = INC_ACTIVIDAD.actividad_id 
						INNER JOIN iniciativa ON pry_actividad.proyecto_id = iniciativa.proyecto_id
						WHERE indicador_id IS NULL;
	
BEGIN
	UPDATE pry_actividad SET indicador_id = null, clase_id = null WHERE indicador_id NOT IN (select indicador_id from indicador);

	OPEN curTable;
	LOOP  -- Fetches 2 columns into variables
	FETCH curTable INTO vActividad_id, vActividadNombre, vClaseId, vorganizacionId, vunidadId, valertaZA, valertaZV, vClaseIdPadre;
    EXIT WHEN curTable%NOTFOUND;
		vIndicadorNombre := 'Avance - ' || SUBSTR(vActividadNombre, 0, 140);
		SELECT COUNT(*) INTO vCount FROM indicador WHERE nombre = vIndicadorNombre AND clase_id = vClaseId;
	
		IF vCount = 0 THEN 
		BEGIN
			SELECT COUNT(*) INTO vCount FROM Clase WHERE nombre = vActividadNombre AND padre_id = vclaseIdPadre;
			IF vCount = 0 THEN
			BEGIN
				SELECT VISION_UNIQUE_ID.NEXTVAL INTO vClaseId FROM DUAL;
				
				INSERT INTO Clase (clase_id, padre_id, organizacion_id, nombre, tipo, visible)
				VALUES (vclaseId, vclaseIdPadre, vorganizacionId, vActividadNombre, 1, 0);
			END;
			ELSE
			BEGIN
				SELECT clase_id INTO vClaseId FROM Clase WHERE nombre = vActividadNombre AND padre_id = vclaseIdPadre;
			END;
			END IF;

			SELECT COUNT(*) INTO vCount FROM indicador WHERE nombre = vIndicadorNombre AND clase_id = vClaseId;
			IF vCount = 0 THEN 
			BEGIN
				SELECT VISION_UNIQUE_ID.NEXTVAL INTO vIndicadorId FROM DUAL;
				
				INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
				VALUES (vIndicadorId, vClaseId, vorganizacionId, vIndicadorNombre, 0, 5, vunidadId, vIndicadorNombre, 0, 0, 0, 0, 1, 0, 0, valertaZV, valertaZA, 0, 0, 0, 0, 1, 2, 0, 0);
			END;
			ELSE
			BEGIN
				SELECT indicador_id INTO vIndicadorId FROM indicador WHERE nombre = vIndicadorNombre AND clase_id = vClaseId;
			END;
			END IF;				
		END;
		ELSE
		BEGIN
			SELECT indicador_id INTO vIndicadorId FROM indicador WHERE nombre = vIndicadorNombre AND clase_id = vClaseId;
		END;
		END IF;				

		UPDATE pry_actividad SET indicador_id = vIndicadorId WHERE actividad_id = vActividad_id;
	END LOOP;
	CLOSE curTable;
  
	UPDATE indicador SET corte = 0, tipo_medicion = 0 WHERE indicador_id IN (SELECT indicador_id from pry_actividad where tipo_medicion = 0);
END;
/

UPDATE afw_sistema set actual = '8.01-170702';

COMMIT;

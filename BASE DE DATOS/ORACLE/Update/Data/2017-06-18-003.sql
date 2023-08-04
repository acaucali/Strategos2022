DECLARE
	vnombreIndicadorAnual INDICADOR.Nombre%TYPE;
	vnombreIndicadorParcial INDICADOR.Nombre%TYPE;
	vnombrePerspectiva PERSPECTIVA.Nombre%TYPE;
	vindicadorIdAnual INDICADOR.Indicador_id%TYPE;
	vindicadorIdParcial INDICADOR.Indicador_id%TYPE;
	valertaZV Organizacion.alerta_meta_n1%TYPE;
	valertaZA Organizacion.alerta_meta_n2%TYPE;
	vorganizacionId CLASE.ORGANIZACION_ID%TYPE;
	vplanId Planes.plan_id%TYPE;
	vfrecuencia perspectiva.frecuencia%TYPE;
	vperspectivaId Perspectiva.Perspectiva_Id%TYPE;
	vpadrePerspectivaId Perspectiva.Padre_Id%TYPE;
	vabueloPerspectivaId Perspectiva.Padre_Id%TYPE;
	vclasePerspectiva Perspectiva.clase_id%TYPE;
	vunidadId Unidad.Unidad_id%TYPE;
	vCount NUMBER(5);

CURSOR curItems IS SELECT
            CLASE.ORGANIZACION_ID,
            Planes.plan_id,
            Organizacion.alerta_meta_n1, 
            Organizacion.alerta_meta_n2,
            perspectiva.frecuencia,
            Perspectiva.Perspectiva_Id,
            Perspectiva.nombre,
            Perspectiva.clase_id,
            Perspectiva.padre_id,
            abuelo.padre_id
          FROM Perspectiva 
          INNER JOIN planes ON Perspectiva.PLAN_ID = planes.plan_id
          INNER JOIN Organizacion ON planes.Organizacion_id = Organizacion.Organizacion_ID
          INNER JOIN CLASE ON Perspectiva.CLASE_ID = CLASE.CLASE_ID
          LEFT JOIN Perspectiva abuelo ON Perspectiva.padre_id = abuelo.perspectiva_ID
          WHERE Perspectiva.nl_ano_indicador_id IS NULL AND Perspectiva.nl_par_indicador_id IS NULL
          ORDER BY CLASE.ORGANIZACION_ID;
	
BEGIN
		SELECT unidad_id INTO vunidadId FROM Unidad WHERE nombre = '%';
		IF vunidadId IS NULL
		THEN
		BEGIN
			SELECT VISION_UNIQUE_ID.NEXTVAL INTO vunidadId FROM DUAL;

			INSERT INTO UNIDAD (UNIDAD_ID, NOMBRE, Tipo)
			VALUES (vunidadId, '%', 0);
		END;
		END IF;
    
		OPEN curItems;
		LOOP  
			FETCH curItems INTO vorganizacionId, vplanId, valertaZV, valertaZA, vfrecuencia, vperspectivaId, vnombrePerspectiva, vclasePerspectiva, vpadrePerspectivaId, vabueloPerspectivaId;
			EXIT WHEN curItems%NOTFOUND;
			BEGIN
				vnombreIndicadorAnual := SUBSTR(vnombrePerspectiva, 0, 130) || ' ... (Logro Anual)';
				vnombreIndicadorParcial := SUBSTR(vnombrePerspectiva, 0, 130) || ' ... (Logro Parcial)';
			
				SELECT COUNT(*) INTO vCount FROM indicador WHERE nombre = vnombreIndicadorAnual AND organizacion_id = vorganizacionId;
				IF vCount = 0 THEN 
				BEGIN
					SELECT VISION_UNIQUE_ID.NEXTVAL INTO vindicadorIdAnual FROM DUAL;
					
					INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
					VALUES (vindicadorIdAnual, vclasePerspectiva, vorganizacionId, vnombreIndicadorAnual, 0, vfrecuencia, vunidadId, vnombreIndicadorAnual, 0, 0, 0, 0, 3, 0, 1, valertaZV, valertaZA, 0, 0, 0, 0, 1, 2, 0, 0);
				END;
				ELSE
				BEGIN
					SELECT indicador_id INTO vindicadorIdAnual FROM indicador WHERE nombre = vnombreIndicadorAnual AND organizacion_id = vorganizacionId AND rownum = 1;
				END;
				END IF;				

				SELECT COUNT(*) INTO vCount FROM indicador WHERE nombre = vnombreIndicadorParcial AND organizacion_id = vorganizacionId;
				IF vCount = 0 THEN 
				BEGIN
					SELECT VISION_UNIQUE_ID.NEXTVAL INTO vindicadorIdParcial FROM DUAL;
					
					INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
					VALUES (vindicadorIdParcial, vclasePerspectiva, vorganizacionId, vnombreIndicadorParcial, 0, vfrecuencia, vunidadId, vnombreIndicadorParcial, 0, 0, 0, 0, 3, 0, 1, valertaZV, valertaZA, 0, 0, 0, 0, 1, 2, 0, 0);
				END;
				ELSE
				BEGIN
					SELECT indicador_id INTO vindicadorIdParcial FROM indicador WHERE nombre = vnombreIndicadorParcial AND organizacion_id = vorganizacionId AND rownum = 1;
				END;
				END IF;
				
				UPDATE PERSPECTIVA SET nl_ano_indicador_id = vindicadorIdAnual, nl_par_indicador_id = vindicadorIdParcial WHERE perspectiva_id = vperspectivaId;

				IF vpadrePerspectivaId IS NULL
				THEN
				BEGIN
					INSERT INTO indicador_por_plan (plan_id, indicador_id)
					VALUES (vplanId, vindicadorIdAnual);
					
					INSERT INTO indicador_por_plan (plan_id, indicador_id)
					VALUES (vplanId, vindicadorIdParcial);
				END;
				ELSE
				BEGIN
					IF vabueloPerspectivaId IS NULL
					THEN
					BEGIN
						INSERT INTO indicador_por_plan (plan_id, indicador_id)
						VALUES (vplanId, vindicadorIdAnual);
						
						INSERT INTO indicador_por_plan (plan_id, indicador_id)
						VALUES (vplanId, vindicadorIdParcial);
					END;
					ELSE
					BEGIN
						INSERT INTO indicador_por_perspectiva (perspectiva_id, indicador_id)
						VALUES (vpadrePerspectivaId, vindicadorIdAnual);
						
						INSERT INTO indicador_por_perspectiva (perspectiva_id, indicador_id)
						VALUES (vpadrePerspectivaId, vindicadorIdParcial);
					END;
					END IF;
				END;
				END IF;
			END;
		END LOOP;
CLOSE curItems;
END;
/

UPDATE afw_sistema set actual = '8.01-170618';

COMMIT;

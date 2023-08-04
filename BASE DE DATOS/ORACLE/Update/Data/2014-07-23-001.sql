--Calcular Alerta Actividad
DECLARE
	vActividad_id pry_actividad.actividad_id%TYPE;
	vIndicadorId pry_actividad.indicador_id%TYPE;
	vProyectoId pry_actividad.proyecto_id%TYPE;
	vanoProgramado medicion.ano%TYPE;
	vperiodoProgramado medicion.periodo%TYPE;
	vvalorProgramado medicion.valor%TYPE;
	valorEsperado FLOAT;
	valorEjecutado FLOAT;
	periodoInicio NUMBER(7);
	periodoControl NUMBER(7);
	za FLOAT;
	zv FLOAT;
	vano NUMBER(4);
	vperiodo NUMBER(3);
	alerta NUMBER(1);
	vCount NUMBER(2);
	CURSOR curTable IS SELECT actividad_id, indicador_id, proyecto_id FROM pry_actividad;
	CURSOR curTableMed IS SELECT ano, periodo, valor FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 1 ORDER BY Ano ASC, PERIODO ASC;
	
BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vActividad_id, vIndicadorId, vProyectoId;
    EXIT WHEN curTable%NOTFOUND;
		-- Buscar Alertas
		SELECT alerta_za, alerta_zv INTO za, zv FROM inc_actividad WHERE actividad_id = vActividad_id AND rownum = 1;
		SELECT COUNT(*) INTO vCount FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 0;
		
		-- Buscar valor Ejecutado
		IF vCount > 0 THEN 
		BEGIN
			SELECT SUM(valor) INTO valorEjecutado FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 0;
			SELECT ano, periodo INTO vano, vperiodo FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 0 AND rownum = 1 ORDER BY Ano DESC, PERIODO DESC;
			periodoInicio := to_number(trim(to_char(vano, '0000')) || trim(to_char(vperiodo, '000')), '0000000');
		
			-- Buscar valor Esperado
			valorEsperado := 0;
			BEGIN
				OPEN curTableMed;
				LOOP
					FETCH curTableMed INTO vanoProgramado, vperiodoProgramado, vvalorProgramado;
					EXIT WHEN curTableMed%NOTFOUND;
						periodoControl := to_number(trim(to_char(vanoProgramado, '0000')) || trim(to_char(vperiodoProgramado, '000')), '0000000');
						IF periodoControl <= periodoInicio THEN
							valorEsperado := valorEsperado + vvalorProgramado;
						END IF;
				END LOOP;
				CLOSE curTableMed;
			END;
			
			IF valorEsperado = 0 THEN
				SELECT SUM(valor) INTO valorEsperado FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 1;
			END IF;
			
			-- Setear Porcentaje de Alerta
			IF za IS NULL THEN
			   SELECT alerta_za INTO za FROM iniciativa WHERE proyecto_id = vProyectoId AND rownum = 1;
			END IF;
			IF za IS NULL THEN
			   SELECT alerta_iniciativa_za INTO za FROM organizacion WHERE organizacion_id in (SELECT organizacion_id FROM iniciativa WHERE proyecto_id = vProyectoId) AND rownum = 1;
			END IF;
			IF zv IS NULL THEN
			   SELECT alerta_zv INTO zv FROM iniciativa WHERE proyecto_id = vProyectoId AND rownum = 1;
			END IF;
			IF zv IS NULL THEN
			   SELECT alerta_iniciativa_zv INTO zv FROM organizacion WHERE organizacion_id in (SELECT organizacion_id FROM iniciativa WHERE proyecto_id = vProyectoId) AND rownum = 1;
			END IF;
			
			-- Calcular Alerta
			alerta := null;
			IF valorEjecutado IS NOT NULL AND valorEsperado IS NOT NULL AND za IS NOT NULL AND zv IS NOT NULL THEN
			BEGIN
				zv := valorEsperado - ((zv * valorEsperado)/100);
				za := zv - ((za * valorEsperado)/100);
				IF valorEjecutado > valorEsperado THEN
					alerta := 2;
				ELSE 
				BEGIN
					IF valorEjecutado >= zv THEN
						alerta := 2;
					ELSE 
					BEGIN
						IF valorEjecutado >= za THEN
							alerta := 3;
						ELSE
							alerta := 0;
						END IF;
					END;
					END IF;
				END;
				END IF;
			END;
			END IF;


			-- Grabar Alerta
			UPDATE pry_actividad 
			SET crecimiento = alerta
			WHERE actividad_id = vActividad_id;
		END;
		END IF;
	END LOOP;
  CLOSE curTable;
END;
/

COMMIT;

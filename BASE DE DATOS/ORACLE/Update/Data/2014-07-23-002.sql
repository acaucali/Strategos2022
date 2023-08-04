--Calcular Alerta Iniciativa
DECLARE
	vIniciativaId iniciativa.iniciativa_id%TYPE;
	vIndicadorId iniciativa.indicador_id%TYPE;
	vOrganizacionId iniciativa.organizacion_id%TYPE;
	za iniciativa.alerta_za%TYPE;
	zv iniciativa.alerta_zv%TYPE;
	vCount NUMBER(2);
	valorEjecutado FLOAT;
	vano NUMBER(4);
	vperiodo NUMBER(3);
	periodoInicio NUMBER(7);
	vanoProgramado medicion.ano%TYPE;
	vperiodoProgramado medicion.periodo%TYPE;
	vvalorProgramado medicion.valor%TYPE;
	periodoControl NUMBER(7);
	valorEsperado FLOAT;
	alerta NUMBER(1);
	CURSOR curTable IS SELECT iniciativa_id, indicador_id, organizacion_id, alerta_za, alerta_zv FROM iniciativa;
	CURSOR curTableMed IS SELECT ano, periodo, valor FROM medicion WHERE indicador_id = vIndicadorId AND serie_id = 1 ORDER BY Ano ASC, PERIODO ASC;
	
BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vIniciativaId, vIndicadorId, vOrganizacionId, za, zv;
    EXIT WHEN curTable%NOTFOUND;
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
			   SELECT alerta_iniciativa_za INTO za FROM organizacion WHERE organizacion_id = vOrganizacionId AND rownum = 1;
			END IF;
			IF zv IS NULL THEN
			   SELECT alerta_iniciativa_zv INTO zv FROM organizacion WHERE organizacion_id = vOrganizacionId AND rownum = 1;
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
			UPDATE iniciativa 
			SET crecimiento = alerta
			WHERE iniciativa_id = vIniciativaId;
		END;
		END IF;
	END LOOP;
  CLOSE curTable;
  COMMIT;
END;
/

COMMIT;

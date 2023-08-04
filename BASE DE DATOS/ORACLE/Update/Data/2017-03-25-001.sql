UPDATE PERSPECTIVA_NIVEL SET Meta = 100;
DECLARE
	vIndicadorParId PERSPECTIVA.NL_PAR_INDICADOR_ID%TYPE;
	vIndicadorAnoId PERSPECTIVA.nl_ano_indicador_id%TYPE;
	vPerspectivaId PERSPECTIVA.perspectiva_id%TYPE;
	valertaZA ORGANIZACION.ALERTA_INICIATIVA_ZA%TYPE;
	valertaZV ORGANIZACION.ALERTA_INICIATIVA_ZV%TYPE;
	valerta perspectiva_nivel.alerta%TYPE;
	vmeta perspectiva_nivel.meta%TYPE;
	vmedicion perspectiva_nivel.nivel%TYPE;
	vAno perspectiva_nivel.ano%TYPE;
	vPeriodo perspectiva_nivel.periodo%TYPE;
	vtipo perspectiva_nivel.tipo%TYPE;
	vCaracIndAno indicador.CARACTERISTICA%TYPE;
	vCaracIndPar indicador.CARACTERISTICA%TYPE;
	valertaZAL FLOAT;
	valertaZVL FLOAT;
	vCount NUMBER(5);
	
	CURSOR curItems IS SELECT 
						PERSPECTIVA.perspectiva_id, 
						PERSPECTIVA.NL_PAR_INDICADOR_ID, 
						PERSPECTIVA.nl_ano_indicador_id,
						ORGANIZACION.alerta_meta_n2,
						ORGANIZACION.alerta_meta_n1,
						(SELECT CARACTERISTICA from indicador WHERE INDICADOR_ID = PERSPECTIVA.NL_ANO_INDICADOR_ID) AS CaracIndAno,
						(SELECT CARACTERISTICA from indicador WHERE INDICADOR_ID = PERSPECTIVA.NL_PAR_INDICADOR_ID) AS CaracIndPar
						FROM PERSPECTIVA 
						INNER JOIN planes ON PERSPECTIVA.PLAN_ID = planes.plan_id
						INNER JOIN ORGANIZACION ON planes.organizacion_id = ORGANIZACION.ORGANIZACION_ID;
						
	CURSOR curItemInternos IS SELECT Ano, PERIODO, Tipo, NIVEL FROM PERSPECTIVA_NIVEL
								WHERE PERSPECTIVA_ID = vPerspectivaId ORDER BY Tipo, Ano, PERIODO;

BEGIN
  OPEN curItems;
  LOOP  
    FETCH curItems INTO vPerspectivaId, vIndicadorParId, vIndicadorAnoId, valertaZA, valertaZV, vCaracIndAno, vCaracIndPar;
    EXIT WHEN curItems%NOTFOUND;
		BEGIN
			-- Buscar Alertas
			valertaZVL := 100 - valertaZV;
			valertaZAL := valertaZVL - valertaZA;
		
			OPEN curItemInternos;
			LOOP
				FETCH curItemInternos INTO vAno, vPeriodo, vtipo, vmedicion;
				EXIT WHEN curItemInternos%NOTFOUND;
				
				valerta := null;
				IF vtipo = 0 THEN
				BEGIN
					IF vCaracIndAno = 0 THEN
					BEGIN
						IF vmedicion >= 100 THEN
						BEGIN
							valerta := 2;
						END;
						ELSE
						BEGIN
							IF vmedicion >= valertaZVL THEN
							BEGIN
								valerta := 2;
							END;
							ELSE
							BEGIN
								IF vmedicion >= valertaZAL THEN
								BEGIN
									valerta := 3;
								END;
								ELSE
								BEGIN
									valerta := 0;
								END;
								END IF;
							END;
							END IF;
						END;
						END IF;
					END;
					ELSE
					BEGIN
						IF vCaracIndAno = 1 THEN
						BEGIN
							IF vmedicion <= 100 THEN
							BEGIN
								valerta := 2;
							END;
							ELSE
							BEGIN
								IF vmedicion <= valertaZVL THEN
								BEGIN
									valerta := 2;
								END;
								ELSE
								BEGIN
									IF vmedicion <= valertaZAL THEN
									BEGIN
										valerta := 3;
									END;
									ELSE
									BEGIN
										valerta := 0;
									END;
									END IF;
								END;
								END IF;
							END;
							END IF;
						END;
						END IF;
					END;
					END IF;
				END;
				ELSE
				BEGIN
					IF vtipo = 1 THEN
					BEGIN
						IF vCaracIndPar = 0 THEN
						BEGIN
							IF vmedicion >= 100 THEN
							BEGIN
								valerta := 2;
							END;
							ELSE
							BEGIN
								IF vmedicion >= valertaZVL THEN
								BEGIN
									valerta := 2;
								END;
								ELSE
								BEGIN
									IF vmedicion >= valertaZAL THEN
									BEGIN
										valerta := 3;
									END;
									ELSE
									BEGIN
										valerta := 0;
									END;
                  END IF;
								END;
								END IF;
							END;
							END IF;
						END;
						ELSE
						BEGIN
							IF vCaracIndPar = 1 THEN
							BEGIN
								IF vmedicion <= 100 THEN
								BEGIN
									valerta := 2;
								END;
								ELSE
								BEGIN
									IF vmedicion <= valertaZVL THEN
									BEGIN
										valerta := 2;
									END;
									ELSE
									BEGIN
										IF vmedicion <= valertaZAL THEN
										BEGIN
											valerta := 3;
										END;
										ELSE
										BEGIN
											valerta := 0;
										END;
										END IF;
									END;
									END IF;
								END;
								END IF;
							END;
							END IF;
						END;
						END IF;
					END;
					END IF;
				END;
				END IF;
				
				UPDATE PERSPECTIVA_NIVEL SET alerta = valerta
				WHERE ANO = vAno AND PERIODO = vPeriodo 
				AND TIPO = vtipo AND PERSPECTIVA_ID = vPerspectivaId;
				
			END LOOP;
			CLOSE curItemInternos;
		END;
  END LOOP;
  CLOSE curItems;
END;
/

UPDATE afw_sistema set actual = '8.01-170325';

COMMIT;

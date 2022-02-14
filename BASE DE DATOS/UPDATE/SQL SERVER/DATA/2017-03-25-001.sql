UPDATE PERSPECTIVA_NIVEL SET Meta = 100;

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    vperspectiva PERSPECTIVA%rowtype;
    perspectivaNivel PERSPECTIVA_NIVEL%rowtype;
    vorganizacion RECORD;
    p RECORD;
	CaracIndAno numeric(1,0);
	CaracIndPar numeric(1,0);
	alertaZAL double precision;
	alertaZVL double precision;
	valerta double precision;

BEGIN
    FOR vperspectiva IN SELECT * FROM PERSPECTIVA 
    LOOP
		SELECT INTO p * FROM planes WHERE plan_Id = vperspectiva.plan_Id LIMIT 1;
		SELECT INTO vorganizacion * FROM ORGANIZACION WHERE ORGANIZACION_ID = p.ORGANIZACION_ID LIMIT 1;
		SELECT INTO CaracIndAno Caracteristica FROM Indicador WHERE Indicador_id = vperspectiva.nl_ano_indicador_id LIMIT 1;
		SELECT INTO CaracIndPar Caracteristica FROM Indicador WHERE Indicador_id = vperspectiva.NL_PAR_INDICADOR_ID LIMIT 1;
		
		alertaZVL := 100 - vorganizacion.alerta_meta_n2;
		alertaZAL := alertaZVL - vorganizacion.alerta_meta_n1;
		--raise notice 'DROP INDEX  %', vperspectiva.plan_Id ;
		FOR perspectivaNivel IN SELECT * FROM PERSPECTIVA_NIVEL WHERE PERSPECTIVA_ID = vperspectiva.perspectiva_id 
								ORDER BY Tipo, Ano, PERIODO
		LOOP
			valerta := null;
			IF perspectivaNivel.tipo = 0 THEN
				--raise notice 'perspect! Anual';
				IF CaracIndAno = 0 THEN
					--raise notice 'perspect! Anual aumento';

					IF perspectivaNivel.nivel >= 100 THEN
						valerta := 2;
					ELSE
						IF perspectivaNivel.nivel >= alertaZVL THEN
							valerta := 2;
						ELSE
							IF perspectivaNivel.nivel >= alertaZAL THEN
								valerta := 3;
							ELSE
								valerta := 0;
							END IF;
						END IF;
					END IF;
				ELSE
					IF CaracIndAno = 1 THEN
						--raise notice 'perspect! Anual disminucion';
						IF perspectivaNivel.nivel <= 100 THEN
							valerta := 2;
						ELSE
							IF perspectivaNivel.nivel <= alertaZVL THEN
								valerta := 2;
							ELSE
								IF perspectivaNivel.nivel <= alertaZAL THEN
									valerta := 3;
								ELSE
									valerta := 0;
								END IF;
							END IF;
						END IF;
					END IF;
				END IF;
			ELSE
				IF perspectivaNivel.tipo = 1 THEN
					--raise notice 'perspect! parcial';
					IF CaracIndPar = 0 THEN
						--raise notice 'perspect! parcial aumento';
						IF perspectivaNivel.nivel >= 100 THEN
							valerta := 2;
						ELSE
							IF perspectivaNivel.nivel >= alertaZVL THEN
								valerta := 2;
							ELSE
								IF perspectivaNivel.nivel >= alertaZAL THEN
									valerta := 3;
								ELSE
									valerta := 0;
								END IF;
							END IF;
						END IF;
					ELSE
						IF CaracIndPar = 1 THEN
							--raise notice 'perspect! parcial disminucion';
							IF perspectivaNivel.nivel <= 100 THEN
								valerta := 2;
							ELSE
								IF perspectivaNivel.nivel <= alertaZVL THEN
									valerta := 2;
								ELSE
									IF perspectivaNivel.nivel <= alertaZAL THEN
										valerta := 3;
									ELSE
										valerta := 0;
									END IF;
								END IF;
							END IF;
						END IF;
					END IF;
				END IF;
			END IF;
			
			UPDATE PERSPECTIVA_NIVEL SET alerta = valerta
			WHERE ANO = perspectivaNivel.Ano AND PERIODO = perspectivaNivel.Periodo 
			AND TIPO = perspectivaNivel.tipo AND PERSPECTIVA_ID = vperspectiva.perspectiva_id;

			END LOOP;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170325';

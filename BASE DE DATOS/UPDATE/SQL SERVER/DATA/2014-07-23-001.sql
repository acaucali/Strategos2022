CREATE OR REPLACE FUNCTION plpgsql_call_handler() RETURNS language_handler AS '$libdir/plpgsql', 'plpgsql_call_handler' LANGUAGE 'c' VOLATILE;
CREATE PROCEDURAL LANGUAGE 'plpgsql' HANDLER plpgsql_call_handler;
CREATE LANGUAGE plpgsql HANDLER funcion_handler;
--DROP FUNCTION getCalcularAlertaActividad();

CREATE OR REPLACE FUNCTION getCalcularAlertaActividad() RETURNS SETOF pry_actividad AS
$BODY$
DECLARE
    r pry_actividad%rowtype;
    f RECORD;
    c RECORD;
	vem RECORD;
	m medicion%rowtype;
    valorEsperado double precision;
    valorEjecutado double precision;
    za double precision;
    zv double precision;
    alerta numeric;
	periodoInicio numeric;
	periodoControl numeric;
BEGIN
    FOR r IN SELECT actividad_id FROM pry_actividad
    WHERE actividad_id > 0
    LOOP
	SELECT INTO f * FROM pry_actividad WHERE actividad_id = r.actividad_id LIMIT 1;
	SELECT INTO c * FROM inc_actividad WHERE actividad_id = r.actividad_id LIMIT 1;

	-- Buscar valor Ejecutado
	SELECT INTO valorEjecutado SUM(valor) FROM medicion WHERE indicador_id = f.indicador_id AND serie_id = 0;
	SELECT INTO vem * FROM medicion WHERE indicador_id = f.indicador_id AND serie_id = 0 ORDER BY Ano DESC, PERIODO DESC LIMIT 1;
	periodoInicio := to_number(trim(to_char(vem.ano, '0000')) || trim(to_char(vem.periodo, '000')), '0000000');

	-- Buscar valor Esperado
	valorEsperado := 0;
	FOR m IN SELECT * FROM medicion WHERE indicador_id = f.indicador_id AND serie_id = 1 ORDER BY Ano ASC, PERIODO ASC
    LOOP
		periodoControl := to_number(trim(to_char(m.ano, '0000')) || trim(to_char(m.periodo, '000')), '0000000');
		IF periodoControl <= periodoInicio THEN
			valorEsperado := valorEsperado + m.valor;
		END IF;
	END LOOP;

	IF valorEsperado = 0 THEN
		SELECT INTO valorEsperado SUM(valor) FROM medicion WHERE indicador_id = f.indicador_id AND serie_id = 1;
	END IF;
	
	-- Setear Porcentaje de Alerta
	za := c.alerta_za;
	zv := c.alerta_zv;
	IF za IS NULL THEN
	   SELECT INTO za alerta_za FROM iniciativa WHERE proyecto_id = f.proyecto_id LIMIT 1;
	END IF;
	IF za IS NULL THEN
	   SELECT INTO za alerta_iniciativa_za FROM organizacion WHERE organizacion_id in (SELECT iniciativa_id FROM iniciativa WHERE proyecto_id = f.proyecto_id) LIMIT 1;
	END IF;
	IF zv IS NULL THEN
	   SELECT INTO zv alerta_zv FROM iniciativa WHERE proyecto_id = f.proyecto_id LIMIT 1;
	END IF;
	IF zv IS NULL THEN
	   SELECT INTO zv alerta_iniciativa_zv FROM organizacion WHERE organizacion_id in (SELECT iniciativa_id FROM iniciativa WHERE proyecto_id = f.proyecto_id) LIMIT 1;
	END IF;

	-- Calcular Alerta
	alerta := null;
	IF valorEjecutado IS NOT NULL AND valorEsperado IS NOT NULL AND za IS NOT NULL AND zv IS NOT NULL THEN
		zv := valorEsperado - ((zv * valorEsperado)/100);
		za := zv - ((za * valorEsperado)/100);
		IF valorEjecutado > valorEsperado THEN
			alerta := 2;
		ELSE 
			IF valorEjecutado >= zv THEN
				alerta := 2;
			ELSE 
				IF valorEjecutado >= za THEN
					alerta := 3;
				ELSE
					alerta := 0;
				END IF;
			END IF;
		END IF;
	END IF;
	
	-- Grabar Alerta
    UPDATE pry_actividad 
	SET crecimiento = alerta
	WHERE actividad_id = r.actividad_id;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT getCalcularAlertaActividad();

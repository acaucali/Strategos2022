CREATE OR REPLACE FUNCTION plpgsql_call_handler() RETURNS language_handler AS '$libdir/plpgsql', 'plpgsql_call_handler' LANGUAGE 'c' VOLATILE;
CREATE PROCEDURAL LANGUAGE 'plpgsql' HANDLER plpgsql_call_handler;
CREATE LANGUAGE plpgsql HANDLER funcion_handler;
--DROP FUNCTION getMigrarAlertaActividad();

CREATE OR REPLACE FUNCTION getMigrarAlertaActividad() RETURNS SETOF pry_actividad AS
$BODY$
DECLARE
    r pry_actividad%rowtype;
    f RECORD;
    m RECORD;
    ultimoperiodo character varying(10);
    valorEsperado double precision;
    valorEjecutado double precision;
BEGIN
    FOR r IN SELECT actividad_id FROM pry_actividad
    WHERE actividad_id > 0
    LOOP
		SELECT INTO f * FROM pry_actividad_plan WHERE actividad_id = r.actividad_id LIMIT 1;
		SELECT INTO m * FROM medicion WHERE indicador_id in (SELECT indicador_id FROM pry_actividad WHERE actividad_id = r.actividad_id) AND serie_id = 0 ORDER BY Ano DESC, Periodo DESC LIMIT 1;
		IF f.porcentaje_esperado IS NULL THEN
		    SELECT INTO valorEsperado SUM(valor) from medicion WHERE indicador_id in (SELECT indicador_id FROM pry_actividad WHERE actividad_id = r.actividad_id) AND serie_id = 1;
		ELSE
		    valorEsperado := f.porcentaje_esperado;
		END IF;
		IF f.porcentaje_ejecutado IS NULL THEN
		    valorEjecutado := f.porcentaje_completado;
		ELSE
		    valorEjecutado := f.porcentaje_ejecutado;
		END IF;
		ultimoperiodo := m.ano || '/' || m.periodo;
        
		-- can do some processing here
        UPDATE pry_actividad 
		SET crecimiento = f.alerta, 
		porcentaje_completado = f.porcentaje_completado, 
		porcentaje_esperado = valorEsperado, 
		porcentaje_ejecutado = valorEjecutado, 
		fecha_ultima_medicion = ultimoperiodo 
		WHERE actividad_id = r.actividad_id;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT getMigrarAlertaActividad();

--select actividad_id, crecimiento, porcentaje_completado, porcentaje_esperado, porcentaje_ejecutado, fecha_ultima_medicion from pry_actividad WHERE actividad_id =131205 
--select actividad_id, alerta, porcentaje_completado, porcentaje_esperado, porcentaje_ejecutado from pry_actividad_plan WHERE actividad_id = 131205 
--GROUP BY actividad_id HAVING count(actividad_id) > 1
--ORDER BY actividad_id, Ano DESC, Periodo DESC;
--SELECT indicador_id from pry_actividad WHERE actividad_id = 131205 
--select * from medicion where indicador_id = 131027 and serie_id = 1;

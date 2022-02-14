CREATE OR REPLACE FUNCTION plpgsql_call_handler() RETURNS language_handler AS '$libdir/plpgsql', 'plpgsql_call_handler' LANGUAGE 'c' VOLATILE;
CREATE PROCEDURAL LANGUAGE 'plpgsql' HANDLER plpgsql_call_handler;
CREATE LANGUAGE plpgsql HANDLER funcion_handler;
--DROP FUNCTION getmigraralertainiciativa();

CREATE OR REPLACE FUNCTION getMigrarAlertaIniciativa() RETURNS SETOF iniciativa AS
$BODY$
DECLARE
    r iniciativa%rowtype;
    f RECORD;
    ultimoperiodo character varying(10);
BEGIN
    FOR r IN SELECT DISTINCT iniciativa_id FROM iniciativa
    WHERE iniciativa_id > 0
    LOOP
		SELECT INTO f * FROM iniciativa_por_plan WHERE iniciativa_id = r.iniciativa_id ORDER BY Ano DESC, Periodo DESC LIMIT 1;
		ultimoperiodo := f.ano || '/' || f.periodo;
        -- can do some processing here
        UPDATE iniciativa SET crecimiento = f.alerta, porcentaje_completado = f.porcentaje_completado, fecha_ultima_medicion = ultimoperiodo WHERE iniciativa_id = r.iniciativa_id;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT getMigrarAlertaIniciativa();

--select iniciativa_id, crecimiento, porcentaje_completado, fecha_ultima_medicion from iniciativa WHERE iniciativa_id = 123018 
--select * from iniciativa_por_plan 
--WHERE iniciativa_id = 123018 
--GROUP BY iniciativa_id HAVING count(iniciativa_id) > 1
--ORDER BY iniciativa_id, Ano DESC, Periodo DESC;
--SELECT indicador_id from iniciativa WHERE iniciativa_id = 123018 
--select * from medicion where indicador_id = 123020 and serie_id = 0 ORDER BY Ano DESC, Periodo DESC;
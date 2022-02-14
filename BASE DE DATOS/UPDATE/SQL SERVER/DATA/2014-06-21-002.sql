CREATE OR REPLACE FUNCTION plpgsql_call_handler() RETURNS language_handler AS '$libdir/plpgsql', 'plpgsql_call_handler' LANGUAGE 'c' VOLATILE;
CREATE PROCEDURAL LANGUAGE 'plpgsql' HANDLER plpgsql_call_handler;
CREATE LANGUAGE plpgsql HANDLER funcion_handler;
--DROP FUNCTION getProgramadoActividad();

CREATE OR REPLACE FUNCTION getProgramadoActividad() RETURNS SETOF meta AS
$BODY$
DECLARE
    r meta%rowtype;
BEGIN
    FOR r IN select DISTINCT * FROM META WHERE indicador_id in (select indicador_id from pry_actividad) AND tipo = 1
    LOOP
	IF NOT EXISTS (SELECT * FROM medicion WHERE indicador_id = r.indicador_id AND ano = r.ano AND periodo = r.periodo AND serie_id = 1)
	THEN
		INSERT INTO medicion (indicador_id, ano, periodo, valor, protegido, serie_id)
		VALUES (r.indicador_id, r.ano, r.periodo, r.valor, 0, 1);
	END IF;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql';

SELECT getProgramadoActividad();

--SELECT * from medicion where indicador_id = 131027
--SELECT * from meta where indicador_id = 131027

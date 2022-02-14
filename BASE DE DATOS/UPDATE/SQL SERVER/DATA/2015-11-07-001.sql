CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
    a inc_actividad%rowtype;
    i RECORD;
    f RECORD;
    alertaAmarilla double precision;
    alertaVerde double precision;
BEGIN
    FOR a IN SELECT * FROM inc_actividad
    LOOP
	alertaAmarilla := null;
	alertaVerde := null;
	SELECT INTO f * FROM pry_actividad WHERE actividad_id = a.actividad_id;
	SELECT INTO i * FROM iniciativa WHERE proyecto_id = f.proyecto_id;

	IF a.alerta_za IS NOT NULL
	THEN
	   alertaAmarilla := a.alerta_za;
	ELSE
	    IF i.alerta_za IS NOT NULL
	    THEN
			alertaAmarilla := i.alerta_za;
	    END IF;
	END IF;

	IF a.alerta_zv IS NOT NULL
	THEN
	   alertaVerde := a.alerta_zv;
	ELSE
	    IF i.alerta_zv IS NOT NULL
	    THEN
			alertaVerde := i.alerta_zv;
	    END IF;
	END IF;
	
        UPDATE indicador SET alerta_meta_n1 = alertaVerde, alerta_meta_n2 = alertaAmarilla WHERE indicador_id = f.indicador_id;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

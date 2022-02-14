UPDATE PERSPECTIVA SET clase_id = null WHERE clase_id NOT IN (SELECT clase_id FROM clase);

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
	vperspectiva PERSPECTIVA%rowtype;
	vclaseIdPadre numeric(10,0);
	vorganizacionId numeric(10,0);
	vclaseId numeric(10,0);

BEGIN
    FOR vperspectiva IN SELECT * FROM PERSPECTIVA WHERE clase_id IS NULL
    LOOP
		SELECT INTO vclaseIdPadre, vorganizacionId clase_id, organizacion_id FROM Planes WHERE plan_id = vperspectiva.plan_id LIMIT 1;
		IF NOT EXISTS (SELECT * FROM clase WHERE nombre = vperspectiva.nombre AND organizacion_id = vorganizacionId)
		THEN
			SELECT INTO vclaseId nextval('AFW_IDGEN');
			INSERT INTO Clase (clase_id, padre_id, organizacion_id, nombre, tipo, visible)
			VALUES (vclaseId, vclaseIdPadre, vorganizacionId, vperspectiva.nombre, 3, 0);
		ELSE
			SELECT INTO vclaseId clase_id FROM clase WHERE nombre = vperspectiva.nombre AND organizacion_id = vorganizacionId;
		END IF;
		
		UPDATE PERSPECTIVA SET Clase_Id = vclaseId WHERE perspectiva_id = vperspectiva.perspectiva_id;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170618';

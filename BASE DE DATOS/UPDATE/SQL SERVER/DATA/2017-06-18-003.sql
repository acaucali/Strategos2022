CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
	vperspectiva PERSPECTIVA%rowtype;
	vNombreIndicadorAnual character varying(150);
	vNombreIndicadorParcial character varying(150);
	vindicadorIdAnual numeric(10,0);
	vindicadorIdParcial numeric(10,0);
	valertaZV numeric(4,0);
	valertaZA numeric(4,0);
	vorganizacionId numeric(4,0);
	p RECORD;
	vunidadId numeric(10,0);

BEGIN
	SELECT INTO vunidadId unidad_id FROM Unidad WHERE nombre = '%' LIMIT 1;
	IF vunidadId IS NULL
	THEN
		SELECT INTO vunidadId nextval('AFW_IDGEN');

		INSERT INTO UNIDAD (UNIDAD_ID, NOMBRE, Tipo)
		VALUES (vunidadId, '%', 0);
	END IF;

    FOR vperspectiva IN SELECT * FROM PERSPECTIVA WHERE nl_ano_indicador_id IS NULL AND nl_par_indicador_id IS NULL
    LOOP
		SELECT INTO vorganizacionId organizacion_id FROM Clase WHERE clase_id = vperspectiva.clase_id LIMIT 1;
		SELECT INTO valertaZV, valertaZA alerta_meta_n1, alerta_meta_n2 FROM organizacion WHERE organizacion_id = vorganizacionId LIMIT 1;
		vNombreIndicadorAnual := substr(vperspectiva.nombre, 0, 130) || ' ... (Logro Anual)';
		vNombreIndicadorParcial := substr(vperspectiva.nombre, 0, 130) || ' ... (Logro Parcial)';
		IF NOT EXISTS (SELECT * FROM indicador WHERE nombre = vNombreIndicadorAnual AND organizacion_id = vorganizacionId)
		THEN
			SELECT INTO vindicadorIdAnual nextval('AFW_IDGEN');
			INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
			VALUES (vindicadorIdAnual, vperspectiva.clase_id, vorganizacionId, vNombreIndicadorAnual, 0, vperspectiva.frecuencia, vunidadId, vNombreIndicadorAnual, 0, 0, 0, 0, 3, 0, 1, valertaZV, valertaZA, 0, 0, 0, 0, 1, 2, 0, 0);
		ELSE
			SELECT INTO vindicadorIdAnual indicador_id FROM indicador WHERE nombre = vNombreIndicadorAnual AND organizacion_id = vorganizacionId;
		END IF;

		IF NOT EXISTS (SELECT * FROM indicador WHERE nombre = vNombreIndicadorParcial AND organizacion_id = vorganizacionId)
		THEN
			SELECT INTO vindicadorIdParcial nextval('AFW_IDGEN');
			INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
			VALUES (vindicadorIdParcial, vperspectiva.clase_id, vorganizacionId, vNombreIndicadorParcial, 0, vperspectiva.frecuencia, vunidadId, vNombreIndicadorParcial, 0, 0, 0, 0, 3, 0, 1, valertaZV, valertaZA, 0, 0, 0, 0, 1, 2, 0, 0);
		ELSE
			SELECT INTO vindicadorIdParcial indicador_id FROM indicador WHERE nombre = vNombreIndicadorParcial AND organizacion_id = vorganizacionId;
		END IF;
		
		UPDATE PERSPECTIVA SET nl_ano_indicador_id = vindicadorIdAnual, nl_par_indicador_id = vindicadorIdParcial WHERE perspectiva_id = vperspectiva.perspectiva_id;
		
		IF vperspectiva.padre_id IS NULL
		THEN
			INSERT INTO indicador_por_plan (plan_id, indicador_id)
			VALUES (vperspectiva.plan_id, vindicadorIdAnual);
			
			INSERT INTO indicador_por_plan (plan_id, indicador_id)
			VALUES (vperspectiva.plan_id, vindicadorIdParcial);
		ELSE
			SELECT INTO p * FROM PERSPECTIVA WHERE perspectiva_id = vperspectiva.padre_id LIMIT 1;
			IF p.padre_id IS NULL
			THEN
				INSERT INTO indicador_por_plan (plan_id, indicador_id)
				VALUES (vperspectiva.plan_id, vindicadorIdAnual);
				
				INSERT INTO indicador_por_plan (plan_id, indicador_id)
				VALUES (vperspectiva.plan_id, vindicadorIdParcial);
			ELSE
				INSERT INTO indicador_por_perspectiva (perspectiva_id, indicador_id)
				VALUES (vperspectiva.padre_id, vindicadorIdAnual);
				
				INSERT INTO indicador_por_perspectiva (perspectiva_id, indicador_id)
				VALUES (vperspectiva.padre_id, vindicadorIdParcial);
			END IF;
		END IF;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-170618';

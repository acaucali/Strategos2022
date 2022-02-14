CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
DECLARE
	p RECORD;
	vClaseId numeric(10,0);
	vIndicadorId numeric(10,0);
	vIndicadorNombre character varying(150);
	
BEGIN
	UPDATE pry_actividad SET indicador_id = null, clase_id = null WHERE indicador_id NOT IN (select indicador_id from indicador);

    FOR p IN SELECT 
				pry_actividad.actividad_id, 
				pry_actividad.nombre, 
				pry_actividad.clase_id, 
				iniciativa.ORGANIZACION_ID, 
				pry_actividad.UNIDAD_ID, 
				INC_ACTIVIDAD.alerta_za, 
				INC_ACTIVIDAD.alerta_zv, 
				iniciativa.clase_ID AS clasePadreId
			FROM pry_actividad 
			INNER JOIN INC_ACTIVIDAD ON pry_actividad.actividad_id = INC_ACTIVIDAD.actividad_id 
			INNER JOIN iniciativa ON pry_actividad.proyecto_id = iniciativa.proyecto_id
			WHERE indicador_id IS NULL
    LOOP
		vIndicadorNombre := 'Avance - ' || SUBSTR(p.nombre, 0, 140);
		IF NOT EXISTS (SELECT * FROM indicador WHERE nombre = vIndicadorNombre AND clase_id = p.clase_id)
		THEN
			SELECT INTO vClaseId nextval('AFW_IDGEN');
			
			INSERT INTO Clase (clase_id, padre_id, organizacion_id, nombre, tipo, visible)
			VALUES (vClaseId, p.clasePadreId, p.ORGANIZACION_ID, p.nombre, 1, 0);
			
			SELECT INTO vIndicadorId nextval('AFW_IDGEN');
			
			INSERT INTO Indicador (indicador_id, clase_id, organizacion_id, nombre, naturaleza, frecuencia, unidad_id, nombre_corto, prioridad, constante, read_only, caracteristica, tipo, lag_lead, corte, alerta_meta_n1, alerta_meta_n2, alerta_n1_tipo, alerta_n2_tipo, alerta_n1_fv, alerta_n2_fv, valor_inicial_cero, mascara_decimales, multidimensional, asignar_inventario)
			VALUES (vIndicadorId, vClaseId, p.ORGANIZACION_ID, vIndicadorNombre, 0, 5, p.UNIDAD_ID, vIndicadorNombre, 0, 0, 0, 0, 1, 0, 0, p.alerta_zv, p.alerta_za, 0, 0, 0, 0, 1, 2, 0, 0);
		ELSE
			vClaseId := p.clase_id;
		END IF;
		
		UPDATE pry_actividad SET indicador_id = vIndicadorId WHERE actividad_id = p.actividad_id;
    END LOOP;
    RETURN;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE indicador SET corte = 0, tipo_medicion = 0 WHERE indicador_id IN (SELECT indicador_id from pry_actividad where tipo_medicion = 0);
UPDATE afw_sistema set actual = '8.01-170702';

DELETE from inc_actividad WHERE actividad_id in (SELECT actividad_id FROM pry_actividad where indicador_id not in (SELECT indicador_id FROM indicador));
DELETE from pry_actividad where indicador_id not in (SELECT indicador_id FROM indicador);
DECLARE
	vactividadId inc_actividad.actividad_id%TYPE;
	vaalertaAmarilla inc_actividad.alerta_za%TYPE;
	vaalertaVerde inc_actividad.alerta_zv%TYPE;
	vproyectoId pry_actividad.proyecto_id%TYPE;
	vindicadorId pry_actividad.indicador_id%TYPE;
	vialertaAmarilla iniciativa.alerta_za%TYPE;
	vialertaVerde iniciativa.alerta_zv%TYPE;
	vzv inc_actividad.alerta_zv%TYPE;
	vza iniciativa.alerta_za%TYPE;
	vCount NUMBER(5);
  
	CURSOR curItems IS SELECT actividad_id, alerta_za, alerta_zv FROM inc_actividad;

BEGIN
  OPEN curItems;
  LOOP  
    FETCH curItems INTO vactividadId, vaalertaAmarilla, vaalertaVerde;
    EXIT WHEN curItems%NOTFOUND;
		vzv := null;
		vza := null;
		SELECT proyecto_id, indicador_id INTO vproyectoId, vindicadorId FROM pry_actividad WHERE actividad_id = vactividadId;
    SELECT COUNT(*) INTO vCount FROM iniciativa WHERE proyecto_id = vproyectoId;
    IF vCount <> 0 THEN 
    BEGIN
      SELECT alerta_za, alerta_zv INTO vialertaAmarilla, vialertaVerde FROM iniciativa WHERE proyecto_id = vproyectoId;	
  
      IF vaalertaAmarilla IS NOT NULL THEN
      BEGIN
        vza := vaalertaAmarilla;
      END;
      ELSE
      BEGIN
        IF vialertaAmarilla IS NOT NULL THEN
        BEGIN
          vza := vialertaAmarilla;
        END;
        END IF;
      END;
      END IF;
  
      IF vaalertaVerde IS NOT NULL THEN
      BEGIN
        vzv := vaalertaVerde;
      END;
      ELSE
      BEGIN
        IF vialertaVerde IS NOT NULL THEN
        BEGIN
          vzv := vialertaVerde;
        END;
        END IF;
      END;
      END IF;
      
      UPDATE indicador SET alerta_meta_n1 = vzv, alerta_meta_n2 = vza WHERE indicador_id = vindicadorId;
    END;
    END IF;				
      
  END LOOP;
  CLOSE curItems;
END;
/

COMMIT;

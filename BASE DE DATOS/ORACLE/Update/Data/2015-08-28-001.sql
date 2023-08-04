DECLARE
	vReporteId reporte.reporte_Id%TYPE;
	vConfiguracion reporte.configuracion%TYPE;
	vVar VARCHAR2(2000);
	
	CURSOR curItems IS SELECT reporte_id, configuracion FROM reporte;

BEGIN
  OPEN curItems;
  LOOP  -- Fetches 2 columns into variables
    FETCH curItems INTO vReporteId, vConfiguracion;
    EXIT WHEN curItems%NOTFOUND;
		vVar := replace(substr(vConfiguracion, 1, 2000), ',', '%-%');
		vVar := replace(substr(vConfiguracion, 1, 2000), 'atributos="1%-%1%-%Nombre%-%1%-%150%-%1|2%-%2%-%Organización%-%0%-%80%-%0|3%-%3%-%Clase%-%0%-%80%-%0|4%-%4%-%Unidad de Medida%-%1%-%80%-%1|5%-%5%-%Frecuencia%-%0%-%80%-%0|6%-%6%-%Serie%-%1%-%80%-%1"', 'atributos="1,1,Nombre,1,80,0|2,2,Organización,1,80,0|3,3,Clase,1,80,0|4,4,Unidad de Medida,1,80,0|5,5,Frecuencia,1,80,0|6,6,Serie,1,80,0"');
		
		UPDATE reporte SET configuracion = vVar WHERE reporte_id = vReporteId;
  END LOOP;
  CLOSE curItems;
  COMMIT;
END;
/

COMMIT;

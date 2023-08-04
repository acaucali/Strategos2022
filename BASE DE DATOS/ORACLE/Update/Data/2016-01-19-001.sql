--Setear las Frecuencias de las perspectivas, el plan y los indicadores de porcentajes de logro 
--a la frecuencia mayor que tienen todas sus hijas
DECLARE 
	vfrecuenciaHijas perspectiva.frecuencia%TYPE;
	vfrecuenciaPadre perspectiva.frecuencia%TYPE;
	vplanId NUMBER(10);
	vcount NUMBER(10); 
	CURSOR curItems IS SELECT plan_id FROM planes;
	
BEGIN
  OPEN curItems;
  LOOP  
	FETCH curItems INTO vplanId;
	EXIT WHEN curItems%NOTFOUND;
		SELECT COUNT(*) INTO vcount from perspectiva where padre_id in (select perspectiva_id from perspectiva where padre_id is null and plan_id = vplanId) and plan_id = vplanId;
		IF vcount > 0 THEN
		BEGIN
			SELECT MAX(frecuencia) INTO vfrecuenciaHijas from perspectiva where padre_id in (select perspectiva_id from perspectiva where padre_id is null and plan_id = vplanId) and plan_id = vplanId;
			SELECT COUNT(*) INTO vcount from perspectiva where padre_id is null and plan_id = vplanId;
			IF vcount > 0 THEN
			BEGIN
				SELECT frecuencia INTO vfrecuenciaPadre from perspectiva where padre_id is null and plan_id = vplanId;
				IF vfrecuenciaHijas <> vfrecuenciaPadre THEN
				BEGIN
					UPDATE indicador SET FRECUENCIA = vfrecuenciaHijas WHERE indicador_id IN (
														SELECT NL_ano_indicador_id FROM perspectiva where padre_id is null and plan_id = vplanId
														UNION
														SELECT NL_par_indicador_id FROM perspectiva where padre_id is null and plan_id = vplanId
														UNION
														SELECT NL_ano_indicador_id FROM planes where plan_id = vplanId
														UNION
														SELECT NL_par_indicador_id FROM planes where plan_id = vplanId
														);
					UPDATE perspectiva SET FRECUENCIA = vfrecuenciaHijas WHERE padre_id is null and plan_id = vplanId;
				END;
				END IF;
			END;
			END IF;
		END;
		END IF;
		
  END LOOP;
  CLOSE curItems;
END;
/

--Limpiar la tabla afw_version
DECLARE
    vnumeroVersion NUMBER(10);
    vversion afw_version.version%TYPE;
    vbuild afw_version.build%TYPE;
    vdatebuild afw_version.datebuild%TYPE;
    vdateCreated afw_version.createdat%TYPE;

	CURSOR curItems IS SELECT version, build, datebuild, MIN(createdat) AS createdat FROM afw_version GROUP BY version, build, datebuild ORDER BY version;
BEGIN
  OPEN curItems;
  LOOP  
	FETCH curItems INTO vversion, vbuild, vdatebuild, vdateCreated;
	EXIT WHEN curItems%NOTFOUND;
		SELECT COUNT(*) INTO vnumeroVersion FROM afw_version WHERE version = vversion AND build = vbuild AND datebuild = vdatebuild AND createdat <> vdateCreated;
		IF vnumeroVersion > 0 THEN
		BEGIN
			DELETE FROM afw_version WHERE version = vversion AND build = vbuild AND datebuild = vdatebuild AND createdat <> vdateCreated;
		END;
		END IF;
  END LOOP;
  CLOSE curItems;
END;
/

UPDATE afw_sistema set actual = '8.01-160119';

COMMIT;

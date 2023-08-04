DECLARE
	vIndicador_Id Meta.Indicador_Id%TYPE;
	vAno Meta.Ano%TYPE;
	vPeriodo Meta.Periodo%TYPE;
	vValor Meta.Valor%TYPE;
	vCount NUMBER(2);
	CURSOR curTable IS SELECT DISTINCT indicador_id, ano, periodo, valor FROM meta WHERE indicador_id IN (SELECT indicador_id FROM pry_actividad) AND tipo = 1;

BEGIN
  OPEN curTable;
  LOOP  -- Fetches 2 columns into variables
    FETCH curTable INTO vIndicador_Id, vAno, vPeriodo, vValor;
    EXIT WHEN curTable%NOTFOUND;
		SELECT COUNT(*) INTO vCount FROM medicion WHERE Indicador_id = vIndicador_Id AND Ano = vAno AND Periodo = vPeriodo AND Serie_Id = 1;
	
		IF vCount = 0 THEN
			INSERT INTO medicion (indicador_id, ano, periodo, valor, protegido, serie_id) VALUES (vIndicador_Id, vAno, vPeriodo, vValor, 0, 1);
		END IF;
  END LOOP;
  CLOSE curTable;
  COMMIT;
END;
/

COMMIT;
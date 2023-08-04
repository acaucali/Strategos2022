ALTER TABLE FORMULA DROP COLUMN Expresion;
ALTER TABLE FORMULA ADD Expresion VARCHAR2(4000);

DECLARE
	vSerie_Id Formula.Serie_Id%TYPE;
	vIndicador_Id Formula.Indicador_Id%TYPE;
	vFormula Formula.Expresion%TYPE;
	CURSOR curFormula IS SELECT Indicador_Id, Serie_Id, Expresion FROM Formula_temp;

BEGIN
  OPEN curFormula;
  LOOP  -- Fetches 2 columns into variables
    FETCH curFormula INTO vIndicador_Id, vSerie_Id, vFormula;
    EXIT WHEN curFormula%NOTFOUND;
	UPDATE Formula SET Expresion = vFormula WHERE Indicador_Id = vIndicador_Id AND Serie_Id = vSerie_Id;
  END LOOP;
  CLOSE curFormula;
  COMMIT;
END;
/

COMMIT;

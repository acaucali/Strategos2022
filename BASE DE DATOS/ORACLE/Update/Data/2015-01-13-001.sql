CREATE TABLE Formula_Temp
(
	indicador_id         NUMBER(10) NOT NULL ,
	serie_id             NUMBER(10) NOT NULL ,
	expresion            LONG VARCHAR NULL 
);

DECLARE
	vSerie_Id Formula.Serie_Id%TYPE;
	vIndicador_Id Formula.Indicador_Id%TYPE;
	vFormula Formula.Expresion%TYPE;
	CURSOR curFormula IS SELECT Indicador_Id, Serie_Id, Expresion FROM Formula;

BEGIN
  OPEN curFormula;
  LOOP  -- Fetches 2 columns into variables
    FETCH curFormula INTO vIndicador_Id, vSerie_Id, vFormula;
    EXIT WHEN curFormula%NOTFOUND;
	INSERT INTO Formula_Temp (indicador_id, serie_id, expresion) VALUES (vIndicador_Id, vSerie_Id, vFormula);
  END LOOP;
  CLOSE curFormula;
  COMMIT;
END;
/

COMMIT;

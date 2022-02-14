CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'objetos_calcular') 
	THEN
		CREATE TABLE objetos_calcular
		(
			Id                   UUID NOT NULL ,
			Objeto_Id            numeric(10, 0) NOT NULL ,
			Usuario_Id           numeric(10, 0) NOT NULL ,
			Calculado            numeric(1, 0) NULL 
		);
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160721';

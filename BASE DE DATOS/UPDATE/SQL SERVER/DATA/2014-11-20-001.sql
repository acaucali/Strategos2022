CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'afw_sistema' AND column_name = 'conexion')
	THEN
		UPDATE AFW_SISTEMA SET CONEXION = '2063<46,2451?589><=-(''#';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

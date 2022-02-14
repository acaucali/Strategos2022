CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = 'afw_sistema' AND column_name = 'conexion')
	THEN
		execute 'ALTER TABLE afw_sistema ADD COLUMN CONEXION character varying(50)';
	END IF;

    IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'afw_log') 
	THEN
        execute 'CREATE TABLE afw_log 
				(fecha timestamp without time zone, 
				objeto_id numeric(10,0), 
				usuario_id numeric(10,0), 
				nombre character varying(50), 
				descripcion character varying(2000), 
				categoria numeric(2,0))';
    END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

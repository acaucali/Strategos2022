CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'iniciativa_estatus') 
	THEN
		CREATE TABLE iniciativa_estatus (id numeric(10,0) NOT NULL, nombre character varying(50) NOT NULL, porcentaje_inicial double precision NULL, porcentaje_final double precision NULL, sistema numeric(1,0) NOT NULL, bloquear_medicion numeric(1,0) NOT NULL, bloquear_indicadores numeric(1,0) NOT NULL);
		CREATE UNIQUE INDEX IE_Iniciativa_Estatus ON iniciativa_estatus USING btree (id   ASC);
		CREATE UNIQUE INDEX AK_iniciativa_estatus ON iniciativa_estatus USING btree (nombre   ASC);
		ALTER TABLE ONLY iniciativa_estatus ADD CONSTRAINT PK_Iniciativa_Estatus PRIMARY KEY (id);
		ALTER TABLE iniciativa_estatus ADD CONSTRAINT PK_iniciativa_estatus_nombre UNIQUE (nombre);
		
		INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (1, 'Sin Iniciar', 0, 0, 1, 0, 0);
		INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (2, 'En Ejecucion', 0.01, 99.99, 0, 0, 0);
		INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (3, 'Cancelado', NULL, NULL, 1, 1, 1);
		INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (4, 'Suspendido', NULL, NULL, 1, 1, 1);
		INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (5, 'Culminado', 100, 100, 1, 1, 0);
	END IF;
	
	IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_name = LOWER('iniciativa') AND column_name = LOWER('estatusId'))	
	THEN
		execute 'ALTER TABLE iniciativa ADD COLUMN estatusId numeric(10,0)';
		
		UPDATE iniciativa SET estatusId = 1 WHERE porcentaje_completado = 0 OR porcentaje_completado IS NULL;
		UPDATE iniciativa SET estatusId = 5 WHERE porcentaje_completado = 100;
		UPDATE iniciativa SET estatusId = 2 WHERE estatusId IS NULL;
		
		execute 'ALTER TABLE iniciativa ALTER COLUMN estatusId SET NOT NULL';
		execute 'CREATE  INDEX IE_iniciativa_estatusId ON iniciativa USING btree (estatusId   ASC)';
		execute 'ALTER TABLE iniciativa ADD CONSTRAINT PK_Iniciativa_EstatusId FOREIGN KEY (estatusId) REFERENCES iniciativa_estatus (id) ON UPDATE NO ACTION ON DELETE CASCADE';
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

UPDATE afw_sistema set actual = '8.01-160625';

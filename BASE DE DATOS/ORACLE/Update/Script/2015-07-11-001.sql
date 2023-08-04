DECLARE
	vCount NUMBER(5);

BEGIN

	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('duppont');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE duppont (indicador_id NUMBER(10) NOT NULL, usuario_id NUMBER(10) NOT NULL, configuracion VARCHAR2(2000) NULL )';
		execute immediate 'CREATE UNIQUE INDEX AK_duppont ON duppont (indicador_id   ASC, usuario_id   ASC)';
		execute immediate 'ALTER TABLE duppont ADD CONSTRAINT  PK_duppont PRIMARY KEY (indicador_id, usuario_id)';
		execute immediate 'CREATE  INDEX IE_duppont_indicadorId ON duppont (indicador_id   ASC)';
		execute immediate 'CREATE  INDEX IE_duppont_usuarioId ON duppont (usuario_id   ASC)';
		execute immediate 'ALTER TABLE duppont ADD (CONSTRAINT FK_indicador_duppont FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE)';
		execute immediate 'ALTER TABLE duppont ADD (CONSTRAINT FK_usuario_duppont FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE)';
	END;
	END IF;
END;
/

COMMIT;

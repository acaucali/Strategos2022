DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE portafolio(organizacion_id NUMBER(10) NOT NULL, id NUMBER(10) NOT NULL, nombre VARCHAR2(50) NOT NULL, activo NUMBER(1) NOT NULL, porcentaje_completado FLOAT NULL, estatusid NUMBER(10) NULL)';
		execute immediate 'CREATE UNIQUE INDEX PK_Portafolio ON portafolio (id   ASC)';
		execute immediate 'ALTER TABLE portafolio ADD CONSTRAINT  PK_PortafolioId PRIMARY KEY (id)';
		execute immediate 'CREATE UNIQUE INDEX AK_Portafolio ON portafolio (organizacion_id   ASC,nombre   ASC)';
		execute immediate 'ALTER TABLE portafolio ADD CONSTRAINT AK_Portafolio_Nombre UNIQUE (organizacion_id,nombre)';
		execute immediate 'CREATE  INDEX IE_Portafolio_OrganizacionId ON portafolio (organizacion_id   ASC)';
		execute immediate 'CREATE  INDEX IE_portafolio_estatus ON portafolio (estatusid   ASC)';
		execute immediate 'ALTER TABLE portafolio ADD (CONSTRAINT FK_ORG_PORTAFOLIO FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
		execute immediate 'ALTER TABLE portafolio ADD (CONSTRAINT FK_estatus_portafolio FOREIGN KEY (estatusid) REFERENCES iniciativa_estatus (id) ON DELETE SET NULL)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_TABLES WHERE table_name = UPPER('portafolio_iniciativa');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE TABLE portafolio_iniciativa (portafolio_id NUMBER(10) NOT NULL, iniciativa_id NUMBER(10) NOT NULL)';
		execute immediate 'CREATE UNIQUE INDEX PK_portafolio_iniciativa ON portafolio_iniciativa (portafolio_id   ASC,iniciativa_id   ASC)';
		execute immediate 'ALTER TABLE portafolio_iniciativa ADD CONSTRAINT  PK_port_iniciativa_portId PRIMARY KEY (portafolio_id,iniciativa_id)';
		execute immediate 'CREATE  INDEX IE_portafolio_iniciativa_porta ON portafolio_iniciativa (portafolio_id   ASC)';
		execute immediate 'CREATE  INDEX IE_portafolio_iniciativa_inici ON portafolio_iniciativa (iniciativa_id   ASC)';
		execute immediate 'ALTER TABLE portafolio_iniciativa ADD (CONSTRAINT FK_portafolio_portafolioId FOREIGN KEY (portafolio_id) REFERENCES portafolio (id) ON DELETE CASCADE)';
		execute immediate 'ALTER TABLE portafolio_iniciativa ADD (CONSTRAINT FK_portafolio_iniciativa_iniId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE)';
	END;
	END IF;	

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('FK_VISTA_PAGINA');
	IF vCount > 0 THEN 
	BEGIN
    execute immediate 'ALTER TABLE pagina DROP CONSTRAINT FK_VISTA_PAGINA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vista') AND CONSTRAINT_NAME = UPPER('pk_vista');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vista DROP CONSTRAINT pk_vista';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('vista') AND INDEX_NAME = UPPER('pk_vista');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_vista';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('vista') AND INDEX_NAME = UPPER('ak1_vista');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak1_vista';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('vista') AND INDEX_NAME = UPPER('xif1vista');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX xif1vista';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vista') AND CONSTRAINT_NAME = UPPER('ak1_vista');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vista DROP CONSTRAINT ak1_vista';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('vista') AND INDEX_NAME = UPPER('ie_vista');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ie_vista ON vista (vista_id   ASC)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('vista') AND INDEX_NAME = UPPER('ie_vista_organizacion');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_vista_organizacion ON vista (organizacion_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vista') AND CONSTRAINT_NAME = UPPER('pk_vista');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vista ADD CONSTRAINT pk_vista PRIMARY KEY (vista_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vista') AND CONSTRAINT_NAME = UPPER('ak_vista_nombre');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vista DROP CONSTRAINT SYS_C0015625';
		execute immediate 'ALTER TABLE vista DROP CONSTRAINT SYS_C0015626';
		execute immediate 'ALTER TABLE vista DROP CONSTRAINT AK_VISTA';
		execute immediate 'ALTER TABLE vista ADD CONSTRAINT ak_vista_nombre UNIQUE (organizacion_id,nombre)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vista') AND CONSTRAINT_NAME = UPPER('FK_organizacion_vista');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vista ADD (CONSTRAINT FK_organizacion_vista FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('xif1pagina');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina DROP CONSTRAINT xif1pagina';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('SYS_C0015918');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina DROP CONSTRAINT SYS_C0015918';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('FK_PAGINA_CELDA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT FK_PAGINA_CELDA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('PK_PAGINA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina DROP CONSTRAINT PK_PAGINA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pagina') AND INDEX_NAME = UPPER('pk_pagina');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_pagina';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pagina') AND INDEX_NAME = UPPER('XIF1PAGINA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX XIF1PAGINA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('portafolioId') AND TABLE_NAME = UPPER('pagina');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina ADD portafolioId NUMBER(10) NULL';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pagina') AND INDEX_NAME = UPPER('ie_pagina');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ie_pagina ON pagina (pagina_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('pagina') AND INDEX_NAME = UPPER('ie_pagina_portafolio');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_pagina_portafolio ON pagina (portafolioId   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('pk_pagina');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina 	ADD CONSTRAINT  pk_pagina PRIMARY KEY (pagina_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('FK_vista_pagina');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina ADD (CONSTRAINT FK_vista_pagina FOREIGN KEY (vista_id) REFERENCES vista (vista_id))';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pagina') AND CONSTRAINT_NAME = UPPER('FK_portafolio_vista');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pagina ADD (CONSTRAINT FK_portafolio_vista FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON DELETE CASCADE)';
	END;
	END IF;
	
	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado') AND TABLE_NAME = UPPER('celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP COLUMN modificado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado') AND TABLE_NAME = UPPER('celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP COLUMN creado';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('modificado_id') AND TABLE_NAME = UPPER('celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP COLUMN modificado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM USER_TAB_COLUMNS WHERE COLUMN_NAME = UPPER('creado_id') AND TABLE_NAME = UPPER('celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP COLUMN creado_id';
	END;
	END IF;				

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('ak1_celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT ak1_celda';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('INDICADOR_POR_CELDA') AND CONSTRAINT_NAME = UPPER('FK_CELDA_INDICADOR');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE INDICADOR_POR_CELDA DROP CONSTRAINT FK_CELDA_INDICADOR';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('pk_celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT pk_celda';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('pk_celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX pk_celda';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('ak1_celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX ak1_celda';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('xif1celda');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX xif1celda';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('ie_celda');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ie_celda ON celda (celda_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('SYS_C0015863');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT SYS_C0015863';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('SYS_C0015862');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT SYS_C0015862';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('SYS_C0015861');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT SYS_C0015861';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('SYS_C0015860');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT SYS_C0015860';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('AK_CELDA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda DROP CONSTRAINT AK_CELDA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('AK_CELDA');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP INDEX AK_CELDA';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('ie_celda_pagina_celda');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE UNIQUE INDEX ie_celda_pagina_celda ON celda (pagina_id   ASC,fila   ASC,columna   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_IND_COLUMNS WHERE TABLE_NAME = UPPER('celda') AND INDEX_NAME = UPPER('ie_celda_pagina');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'CREATE  INDEX ie_celda_pagina ON celda (pagina_id   ASC)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('pk_celda_pagina_celda');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda ADD CONSTRAINT  pk_celda_pagina_celda UNIQUE (pagina_id,fila,columna)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('pk_celda');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda ADD CONSTRAINT  pk_celda PRIMARY KEY (celda_id)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('celda') AND CONSTRAINT_NAME = UPPER('FK_pagina_celda');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE celda ADD (CONSTRAINT FK_pagina_celda FOREIGN KEY (pagina_id) REFERENCES pagina (pagina_id) ON DELETE CASCADE)';
	END;
	END IF;
	
END;
/

UPDATE afw_sistema set actual = '8.01-160731';

COMMIT;

--=========================================
--Requerimiento para Ministerio de Finanzas
--=========================================
CREATE TABLE afw_transaccion
(
	id                   NUMBER(10) NOT NULL,
	nombre               VARCHAR2(50) NOT NULL,
	frecuencia           NUMBER(1) NOT NULL,
	configuracion        LONG VARCHAR NOT NULL 
);

ALTER TABLE afw_transaccion ADD CONSTRAINT PK_afw_transaccion PRIMARY KEY (id);

ALTER TABLE afw_transaccion ADD CONSTRAINT  AK_afw_transaccion UNIQUE (nombre);

CREATE TABLE transaccion_indicador
(
	transaccion_Id       NUMBER(10) NOT NULL ,
	indicador_id         NUMBER(10) NOT NULL ,
	campo                VARCHAR2(50) NOT NULL 
);

CREATE UNIQUE INDEX IE_transaccion_indicador ON transaccion_indicador (transaccion_Id   ASC,indicador_id   ASC,campo   ASC);

ALTER TABLE transaccion_indicador
	ADD CONSTRAINT  PK_transaccion_indicador PRIMARY KEY (transaccion_Id, indicador_id, campo);

CREATE  INDEX IE_transaccion_indicador_trans ON transaccion_indicador (transaccion_Id   ASC);

CREATE  INDEX IE_transaccion_indicador_indic ON transaccion_indicador (indicador_id   ASC);

ALTER TABLE transaccion_indicador
	ADD (CONSTRAINT FK_afw_transaccion_indicador FOREIGN KEY (transaccion_Id) REFERENCES afw_transaccion (id) ON DELETE CASCADE);

ALTER TABLE transaccion_indicador
	ADD (CONSTRAINT FK_indicador_afw_transaccion FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE);

CREATE TABLE MinFinanzas
(
	fecha  		 			TIMESTAMP, 
	mo_operac           	FLOAT, 
	pc_operac           	VARCHAR2(50),
	co_institucion_vend 	VARCHAR2(50),
	co_tipo_titulo      	VARCHAR2(50),
	nu_ci_rif_cliente   	VARCHAR2(50),
	co_institucion_comp     VARCHAR2(50),
	nu_ci_rif_cliente_comp  VARCHAR2(50)
);
	
INSERT INTO afw_transaccion (id, nombre, frecuencia, configuracion) VALUES (10, 'MinFinanzas', 0, '<?xml version="1.0" encoding="UTF-8" standalone="no"?><transaccion><properties><tabla>MinFinanzas</tabla><campos><campo><nombre>fecha</nombre><tipo>3</tipo><tamano>10</tamano><alias>Fecha Operación</alias><formato>dd/mm/yyyy</formato><clavePrimaria>1</clavePrimaria></campo><campo><nombre>mo_operac</nombre><tipo>2</tipo><tamano>30</tamano><alias>Monto Operación</alias><formato>#,##0.00</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>pc_operac</nombre><tipo>1</tipo><tamano>50</tamano><alias>Tipo de Cambio</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>co_institucion_vend</nombre><tipo>1</tipo><tamano>50</tamano><alias>Código Inst. que Vende</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>co_tipo_titulo</nombre><tipo>1</tipo><tamano>50</tamano><alias>Tipo de Moneda</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>nu_ci_rif_cliente</nombre><tipo>1</tipo><tamano>50</tamano><alias>RIF Institución que Vende</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>co_institucion_comp</nombre><tipo>1</tipo><tamano>50</tamano><alias>Código Inst. que Tramita</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo><campo><nombre>nu_ci_rif_cliente_comp</nombre><tipo>1</tipo><tamano>50</tamano><alias>RIF del Cliente</alias><formato>center</formato><clavePrimaria>0</clavePrimaria></campo></campos></properties></transaccion>');
INSERT INTO afw_modulo (id, modulo, activo) VALUES ('0DC25625-AF06-4F7C-B981-258AEAF18C01', 'Reporte Ministeerio de Finazas', 1);
	
COMMIT;

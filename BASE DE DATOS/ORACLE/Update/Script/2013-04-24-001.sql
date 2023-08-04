DELETE FROM afw_objeto_auditable_atributo;
DELETE FROM afw_objeto_auditable;
DELETE FROM afw_auditoria_entero;
DELETE FROM afw_auditoria_string;
DELETE FROM afw_auditoria_memo;
DELETE FROM afw_auditoria_flotante;
DELETE FROM afw_auditoria_fecha;

ALTER TABLE afw_objeto_auditable_atributo MODIFY nombre_atributo VARCHAR(100);
ALTER TABLE afw_auditoria_string MODIFY nombre_atributo VARCHAR(100);
ALTER TABLE afw_auditoria_memo MODIFY nombre_atributo VARCHAR(100);
ALTER TABLE afw_auditoria_flotante MODIFY nombre_atributo VARCHAR(100);
ALTER TABLE afw_auditoria_fecha MODIFY nombre_atributo VARCHAR(100);

DROP TABLE afw_auditoria_entero;
 
CREATE TABLE afw_auditoria_entero
(
	fecha                TIMESTAMP NOT NULL ,
	instancia_id         VARCHAR2(100) NOT NULL ,
	valor                NUMBER(10) NULL ,
	usuario_id           NUMBER(10) NULL ,
	objeto_id            NUMBER(10) NULL ,
	nombre_atributo      VARCHAR2(100) NOT NULL ,
	tipo_evento          NUMBER(1) NOT NULL 
);

CREATE UNIQUE INDEX pk_afw_auditoria_entero ON afw_auditoria_entero
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);

ALTER TABLE afw_auditoria_entero
	ADD CONSTRAINT  pk_afw_auditoria_entero PRIMARY KEY (fecha,instancia_id,nombre_atributo);
	
CREATE  INDEX IF_afw_auditoria_entero ON afw_auditoria_entero (usuario_id   ASC);
	
COMMIT;
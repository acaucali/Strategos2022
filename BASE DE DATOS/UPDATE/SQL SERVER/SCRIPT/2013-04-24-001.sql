DELETE FROM afw_objeto_auditable_atributo;
DELETE FROM afw_objeto_auditable;
DELETE FROM afw_auditoria_entero;
DELETE FROM afw_auditoria_string;
DELETE FROM afw_auditoria_memo;
DELETE FROM afw_auditoria_flotante;
DELETE FROM afw_auditoria_fecha;

ALTER TABLE afw_objeto_auditable_atributo ALTER nombre_atributo  TYPE character varying(100);
ALTER TABLE afw_auditoria_string ALTER nombre_atributo  TYPE character varying(100);
ALTER TABLE afw_auditoria_memo ALTER nombre_atributo  TYPE character varying(100);
ALTER TABLE afw_auditoria_flotante ALTER nombre_atributo  TYPE character varying(100);
ALTER TABLE afw_auditoria_fecha ALTER nombre_atributo  TYPE character varying(100);

DROP TABLE afw_auditoria_entero;
 
CREATE TABLE afw_auditoria_entero
(
	fecha timestamp without time zone NOT NULL,
	instancia_id character varying(100) NOT NULL,
	valor numeric(10,0),
	usuario_id numeric(10,0),
	objeto_id numeric(10,0),
	nombre_atributo character varying(100) NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	CONSTRAINT pk_afw_auditoria_entero PRIMARY KEY (fecha, instancia_id, nombre_atributo)
);
ALTER TABLE afw_auditoria_entero OWNER TO postgres;


CREATE UNIQUE INDEX UN_afw_auditoria_entero ON afw_auditoria_entero USING btree
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);

CREATE  INDEX IF_afw_auditoria_entero ON afw_auditoria_entero USING btree (usuario_id   ASC);

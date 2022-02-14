CREATE TABLE afw_message
(
	usuario_id           NUMERIC(10, 0) NOT NULL ,
	fecha                TIMESTAMP without time zone NOT NULL ,
	estatus              NUMERIC(1, 0) NOT NULL ,
	mensaje              character varying(500) NOT NULL,
	tipo                 NUMERIC(1, 0) NOT NULL,
	CONSTRAINT PK_afw_message PRIMARY KEY (usuario_id, fecha)
);
ALTER TABLE afw_message OWNER TO postgres;

CREATE UNIQUE INDEX AK_afw_message ON afw_message USING btree (usuario_id   ASC,estatus   ASC);

CREATE INDEX IF_afw_message ON afw_message USING btree (usuario_id   ASC);

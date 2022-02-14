--CREATE TABLE afw_configuracion
--(
--  id numeric(10,0) NOT NULL,
--  usuario_id numeric(10,0) NULL,
--  clave character varying(200) NOT NULL,
--  valor character varying(2000),
--  tipo  character varying(200),
--  ultima_actualizacion timestamp without time zone,
--  CONSTRAINT pk_afw_configuracion PRIMARY KEY (id),
--  CONSTRAINT fk_afw_configuracion FOREIGN KEY (usuario_id)
--      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
--      ON UPDATE NO ACTION ON DELETE NO ACTION
--);
--ALTER TABLE afw_configuracion
--  OWNER TO postgres;


--CREATE INDEX ie_afw_configuracion_usuario_id
--  ON afw_configuracion
--  USING btree
--  (usuario_id );  
  
------------------------------  
----  afw_passwordHistoria
------------------------------
--CREATE TABLE afw_passwordHistoria
--(
--  usuario_id numeric(10,0) NULL,
--  fecha timestamp without time zone,
--  password character varying(100) NOT NULL,
--  CONSTRAINT fk_afw_passwordHistoria FOREIGN KEY (usuario_id)
--      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
--      ON UPDATE NO ACTION ON DELETE NO ACTION
--);
--ALTER TABLE afw_passwordHistoria
--  OWNER TO postgres;

--CREATE INDEX ie_afw_passwordHistoria_usuario_id
--  ON afw_passwordHistoria
--  USING btree
--  (usuario_id);  
  
--------------------------------------
---- afw_usuario  
--------------------------------------
--ALTER TABLE afw_usuario ADD COLUMN RestriccionDesde timestamp without time zone;
--ALTER TABLE afw_usuario ADD COLUMN RestriccionHasta timestamp without time zone;

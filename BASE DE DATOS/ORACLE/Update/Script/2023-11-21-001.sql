CREATE TABLE explicacion_pgn (
  explicacion_id NUMBER(10) NOT NULL,
  objeto_id NUMBER(10) NOT NULL,
  titulo VARCHAR2(150),
  fecha TIMESTAMP,
  creado TIMESTAMP,
  creado_id NUMBER(10) NOT NULL,
  is_fechas NUMBER(1,0),
  explicacion_fechas VARCHAR2(500),
  is_recibido NUMBER(1,0),
  explicacion_recibido VARCHAR2(500)
);

ALTER TABLE explicacion_pgn
  ADD CONSTRAINT pk_explicacion_pgn PRIMARY KEY (explicacion_id);

ALTER TABLE explicacion_pgn
  ADD CONSTRAINT fk_usuario_explicacion_pgn FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE CASCADE;

UPDATE afw_sistema SET actual = '9.01-231121';
UPDATE afw_sistema SET build = 231121;
UPDATE afw_sistema set version = '9.01';

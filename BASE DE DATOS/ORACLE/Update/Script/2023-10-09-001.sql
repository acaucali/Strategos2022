-- Modificar la tabla iniciativa
ALTER TABLE iniciativa
  MODIFY nombre VARCHAR2(300);

ALTER TABLE iniciativa
  ADD justificacion VARCHAR2(500);

ALTER TABLE iniciativa
  ADD fecha_inicio TIMESTAMP;

ALTER TABLE iniciativa
  ADD fecha_fin TIMESTAMP;

ALTER TABLE iniciativa
  ADD monto_total VARCHAR2(50);

ALTER TABLE iniciativa
  ADD monto_moneda_extr VARCHAR2(50); 

ALTER TABLE iniciativa
  ADD sit_presupuestaria VARCHAR2(150);

ALTER TABLE iniciativa
  ADD hitos_relevantes VARCHAR2(500);

ALTER TABLE iniciativa
  ADD sector VARCHAR2(150);

ALTER TABLE iniciativa
  ADD fecha_acta_inicio TIMESTAMP;

ALTER TABLE iniciativa
  ADD gerencia_general_resp VARCHAR2(150); 

ALTER TABLE iniciativa
  ADD codigo_sipe VARCHAR2(50);

ALTER TABLE iniciativa
  ADD proyecto_presup_asociado VARCHAR2(300); 

ALTER TABLE iniciativa
  ADD estado VARCHAR2(100);

ALTER TABLE iniciativa
  ADD municipio VARCHAR2(300);

ALTER TABLE iniciativa
  ADD parroquia VARCHAR2(300);

ALTER TABLE iniciativa
  ADD direccion_proyecto VARCHAR2(500);

ALTER TABLE iniciativa
  ADD obj_historico VARCHAR2(500);

ALTER TABLE iniciativa
  ADD obj_nacional VARCHAR2(500); 

ALTER TABLE iniciativa
  ADD obj_estrategico_v VARCHAR2(500); 

ALTER TABLE iniciativa
  ADD obj_general_v VARCHAR2(500);

ALTER TABLE iniciativa
  ADD obj_especifico VARCHAR2(500); 

ALTER TABLE iniciativa
  ADD programa VARCHAR2(500);

ALTER TABLE iniciativa
  ADD problemas VARCHAR2(500);

ALTER TABLE iniciativa
  ADD causas VARCHAR2(500);

ALTER TABLE iniciativa
  ADD lineas_estrategicas VARCHAR2(500);

ALTER TABLE iniciativa
  ADD gerente_proy_nombre VARCHAR2(50); 

ALTER TABLE iniciativa
  ADD gerente_proy_cedula VARCHAR2(20); 

ALTER TABLE iniciativa
  ADD gerente_proy_email VARCHAR2(50);

ALTER TABLE iniciativa
  ADD gerente_proy_telefono VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_tecnico_nombre VARCHAR2(50); 

ALTER TABLE iniciativa
  ADD resp_tecnico_cedula VARCHAR2(20); 

ALTER TABLE iniciativa
  ADD resp_tecnico_email VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_tecnico_telefono VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_registrador_nombre VARCHAR2(50); 

ALTER TABLE iniciativa
  ADD resp_registrador_cedula VARCHAR2(20); 

ALTER TABLE iniciativa
  ADD resp_registrador_email VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_registrador_telefono VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_administrativo_nombre VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_administrativo_cedula VARCHAR2(20); 

ALTER TABLE iniciativa
  ADD resp_administrativo_email VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_administrativo_telefono VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_admin_contratos_nombre VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_admin_contratos_cedula VARCHAR2(20);

ALTER TABLE iniciativa
  ADD resp_admin_contratos_email VARCHAR2(50);

ALTER TABLE iniciativa
  ADD resp_admin_contratos_telefono VARCHAR2(50);

ALTER TABLE iniciativa
  ADD fase_id NUMBER(10);

CREATE TABLE fases_proyecto (
    fase_id NUMBER(10) NOT NULL,
    nombre VARCHAR2(50) NOT NULL
);


ALTER TABLE fases_proyecto
  ADD CONSTRAINT ak1_fase UNIQUE (nombre);

ALTER TABLE fases_proyecto
  ADD CONSTRAINT pk_fase PRIMARY KEY (fase_id);


INSERT INTO fases_proyecto (fase_id, nombre) VALUES (1, 'Planificación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (2, 'Ingeniería');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (3, 'Contratación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (4, 'Procura');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (5, 'Puesta en Marcha');


CREATE TABLE afw_lic (
    id NUMBER(10) NOT NULL,
    corporacion VARCHAR2(100),
    serial VARCHAR2(50),
    licenciamiento VARCHAR2(100)
);


INSERT INTO afw_lic (id, corporacion, serial, licenciamiento) VALUES (1, 'Nombre Corporacion', 'Serial Corporacion', 'Tipo Licenciamiento');

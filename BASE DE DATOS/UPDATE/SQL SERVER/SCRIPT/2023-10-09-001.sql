-- Cambios en la tabla iniciativa
ALTER TABLE iniciativa 
    ALTER COLUMN nombre VARCHAR(300);

-- Cambios en la tabla iniciativa
ALTER TABLE iniciativa 
    ALTER COLUMN nombre VARCHAR(300);

ALTER TABLE iniciativa 
    ADD justificacion VARCHAR(500);

ALTER TABLE iniciativa 
    ADD fecha_inicio DATETIME;

ALTER TABLE iniciativa 
    ADD fecha_fin DATETIME;

ALTER TABLE iniciativa 
    ADD monto_total VARCHAR(50);

ALTER TABLE iniciativa 
    ADD monto_moneda_extranjera VARCHAR(50);

ALTER TABLE iniciativa 
    ADD situacion_presupuestaria VARCHAR(150);

ALTER TABLE iniciativa 
    ADD hitos_relevantes VARCHAR(500);

ALTER TABLE iniciativa 
    ADD sector VARCHAR(150);

ALTER TABLE iniciativa 
    ADD fecha_acta_inicio DATETIME;

ALTER TABLE iniciativa 
    ADD gerencia_general_responsable VARCHAR(150);

ALTER TABLE iniciativa 
    ADD codigo_sipe VARCHAR(50);

ALTER TABLE iniciativa 
    ADD proyecto_presupuestario_asociado VARCHAR(300);

ALTER TABLE iniciativa 
    ADD estado VARCHAR(100);

ALTER TABLE iniciativa 
    ADD municipio VARCHAR(300);

ALTER TABLE iniciativa 
    ADD parroquia VARCHAR(300);

ALTER TABLE iniciativa 
    ADD direccion_proyecto VARCHAR(500);

ALTER TABLE iniciativa 
    ADD objetivo_historico VARCHAR(500);

ALTER TABLE iniciativa 
    ADD objetivo_nacional VARCHAR(500);

ALTER TABLE iniciativa 
    ADD objetivo_estrategico_v VARCHAR(500);

ALTER TABLE iniciativa 
    ADD objetivo_general_v VARCHAR(500);

ALTER TABLE iniciativa 
    ADD objetivo_especifico VARCHAR(500);

ALTER TABLE iniciativa 
    ADD programa VARCHAR(500);

ALTER TABLE iniciativa 
    ADD problemas VARCHAR(500);

ALTER TABLE iniciativa 
    ADD causas VARCHAR(500);

ALTER TABLE iniciativa 
    ADD lineas_estrategicas VARCHAR(500);

ALTER TABLE iniciativa 
    ADD gerente_proyecto_nombre VARCHAR(50);

ALTER TABLE iniciativa 
    ADD gerente_proyecto_cedula VARCHAR(20);

ALTER TABLE iniciativa 
    ADD gerente_proyecto_email VARCHAR(50);

ALTER TABLE iniciativa 
    ADD gerente_proyecto_telefono VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_tecnico_nombre VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_tecnico_cedula VARCHAR(20);

ALTER TABLE iniciativa 
    ADD responsable_tecnico_email VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_tecnico_telefono VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_registrador_nombre VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_registrador_cedula VARCHAR(20);

ALTER TABLE iniciativa 
    ADD responsable_registrador_email VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_registrador_telefono VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_administrativo_nombre VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_administrativo_cedula VARCHAR(20);

ALTER TABLE iniciativa 
    ADD responsable_administrativo_email VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_administrativo_telefono VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_admin_contratos_nombre VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_admin_contratos_cedula VARCHAR(20);

ALTER TABLE iniciativa 
    ADD responsable_admin_contratos_email VARCHAR(50);

ALTER TABLE iniciativa 
    ADD responsable_admin_contratos_telefono VARCHAR(50);

ALTER TABLE iniciativa 
    ADD fase_id NUMERIC(10);


-- Crear la tabla fases_proyecto
CREATE TABLE fases_proyecto (
    fase_id numeric(10,0) NOT NULL,
    nombre varchar(50) NOT NULL,
    CONSTRAINT ak1_fase UNIQUE (nombre),
    CONSTRAINT pk_fase PRIMARY KEY (fase_id)
);

-- Insertar datos en la tabla fases_proyecto
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (1, 'Planificación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (2, 'Ingeniería');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (3, 'Contratación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (4, 'Procura');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (5, 'Puesta en Marcha');

-- Crear la tabla afw_lic
CREATE TABLE afw_lic (
    id numeric(10,0) NOT NULL,
    corporacion varchar(100),
    serial varchar(50),
    licenciamiento varchar(100)
);

-- Insertar datos en la tabla afw_lic
INSERT INTO afw_lic (id, corporacion, serial, licenciamiento) VALUES (1, 'Nombre Corporacion', 'Serial Corporacion', 'Tipo Licenciamiento');

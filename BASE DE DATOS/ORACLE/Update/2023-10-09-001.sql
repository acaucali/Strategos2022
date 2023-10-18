alter table iniciativa 
	alter column nombre type varchar(300),
	add column justificacion varchar(500),
	add column fecha_inicio  TIMESTAMP,
	add column fecha_fin  TIMESTAMP,
	add column monto_total  varchar(50),
	add column monto_moneda_extranjera  varchar(50),
	add column situacion_presupuestaria  varchar(150),
	add column hitos_relevantes  varchar(500),
	add column sector  varchar(150),
	add column fecha_acta_inicio  TIMESTAMP,
	add column gerencia_general_responsable  varchar(150),
	add column codigo_sipe  varchar(50),
	add column proyecto_presupuestario_asociado  varchar(300),
	add column estado  varchar(100),
	add column municipio  varchar(300),
	add column parroquia  varchar(300),
	add column direccion_proyecto varchar(500),
	add column objetivo_historico varchar(500),
	add column objetivo_nacional varchar(500),
	add column objetivo_estrategico_v varchar(500),
	add column objetivo_general_v varchar(500),
	add column objetivo_especifico varchar(500),
	add column programa varchar(500),
	add column problemas varchar(500),
	add column causas varchar(500),
	add column lineas_estrategicas varchar(500),	
	add column gerente_proyecto_nombre varchar(50),
	add column gerente_proyecto_cedula varchar(20),
	add column gerente_proyecto_email varchar(50),
	add column gerente_proyecto_telefono varchar(50),	
	add column responsable_tecnico_nombre varchar(50),
	add column responsable_tecnico_cedula varchar(20),
	add column responsable_tecnico_email varchar(50),
	add column responsable_tecnico_telefono varchar(50),
	add column responsable_registrador_nombre varchar(50),
	add column responsable_registrador_cedula varchar(20),
	add column responsable_registrador_email varchar(50),
	add column responsable_registrador_telefono varchar(50),
	add column responsable_administrativo_nombre varchar(50),
	add column responsable_administrativo_cedula varchar(20),
	add column responsable_administrativo_email varchar(50),
	add column responsable_administrativo_telefono varchar(50),
	add column responsable_admin_contratos_nombre varchar(50),
	add column responsable_admin_contratos_cedula varchar(20),
	add column responsable_admin_contratos_email varchar(50),
	add column responsable_admin_contratos_telefono varchar(50),
	add column fase_id numeric(10);
	
CREATE TABLE fases_proyecto (
    fase_id numeric(10) NOT NULL,
    nombre character varying(50) NOT NULL
);

alter table fases_proyecto
    add constraint ak1_fase unique (nombre);

alter table fases_proyecto
    add constraint pk_fase primary key (fase_id);

insert into fases_proyecto (fase_id, nombre) values (1, 'Planificación');
insert into fases_proyecto (fase_id, nombre) values (2, 'Ingeniería');
insert into fases_proyecto (fase_id, nombre) values (3, 'Contratación');
insert into fases_proyecto (fase_id, nombre) values (4, 'Procura');
insert into fases_proyecto (fase_id, nombre) values (5, 'Puesta en Marcha');

    
CREATE TABLE afw_lic (
    id numeric(10,0) NOT NULL,
    corporacion character varying(100),	    
    serial character varying(50),
	licenciamiento character varying(100)	
);

insert into afw_lic (id, corporacion, serial, licenciamiento) values (1, 'Nombre Corporacion', 'Serial Corporacion', 'Tipo Licenciamiento');

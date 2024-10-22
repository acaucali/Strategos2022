-- Strategos2022.dbo.AFW_IDGEN definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.AFW_IDGEN;

CREATE TABLE AFW_IDGEN (
	ID int IDENTITY(1,1) NOT NULL,
	IDGEN char(1) COLLATE Modern_Spanish_CI_AS NULL
);


-- Strategos2022.dbo.ENLACE definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ENLACE;

CREATE TABLE ENLACE (
	enlace_tipo nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion nvarchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	BI numeric(10,0) NULL,
	Categoria numeric(10,0) NULL,
	CONSTRAINT PK_ENLACE PRIMARY KEY (enlace_tipo)
);


-- Strategos2022.dbo.ENLACE2013 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ENLACE2013;

CREATE TABLE ENLACE2013 (
	enlace_tipo nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion nvarchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	BI int NULL,
	Categoria int NULL
);


-- Strategos2022.dbo.ENLACE2016 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ENLACE2016;

CREATE TABLE ENLACE2016 (
	enlace_tipo nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion nvarchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	BI int NULL,
	Categoria int NULL
);


-- Strategos2022.dbo.ENLACE2017 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ENLACE2017;

CREATE TABLE ENLACE2017 (
	enlace_tipo nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion nvarchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	BI numeric(10,0) NULL,
	Categoria numeric(10,0) NULL
);


-- Strategos2022.dbo.INDICADOR2016 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.INDICADOR2016;

CREATE TABLE INDICADOR2016 (
	INDICADOR_ID int NOT NULL,
	CLASE_ID int NOT NULL,
	ORGANIZACION_ID int NOT NULL,
	NOMBRE varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	DESCRIPCION text COLLATE Modern_Spanish_CI_AS NULL,
	NATURALEZA tinyint NOT NULL,
	FRECUENCIA tinyint NOT NULL,
	GUIA varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	UNIDAD_ID int NULL,
	CODIGO_ENLACE varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	NOMBRE_CORTO varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	PRIORIDAD tinyint NOT NULL,
	CONSTANTE tinyint NOT NULL,
	FUENTE text COLLATE Modern_Spanish_CI_AS NULL,
	ALIAS varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	ORDEN varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	FUENTE_UBICACION text COLLATE Modern_Spanish_CI_AS NULL,
	FUENTE_ORGANIZACION_ID int NULL,
	FUENTE_INDICADOR_ID int NULL,
	TIPO_ASOCIADO tinyint NULL,
	ASOCIADO_ID int NULL,
	REVISION smallint NULL,
	LOCKBIT tinyint NULL,
	USUARIO_ID int NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	CREADO_ID int NULL,
	MODIFICADO_ID int NULL,
	CALCULADO datetime NULL,
	CALCULADO_ID int NULL,
	READ_ONLY tinyint NULL,
	CARACTERISTICA tinyint NULL,
	CRECIMIENTO_IND tinyint NULL,
	CRECIMIENTO_PLAN tinyint NULL,
	FECHA_ULTIMA_MEDICION varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	ULTIMA_MEDICION float NULL,
	ESTADO_MEDICION tinyint NULL,
	TIPO tinyint NULL,
	RESP_FIJAR_META_ID int NULL,
	RESP_LOGRAR_META_ID int NULL,
	RESP_SEGUIMIENTO_ID int NULL,
	RESP_CARGAR_META_ID int NULL,
	RESP_CARGAR_EJECUTADO_ID int NULL,
	META text COLLATE Modern_Spanish_CI_AS NULL,
	INDICADOR_IMPACTA_ID int NULL,
	LAG_LEAD tinyint NULL,
	CORTE tinyint NULL,
	ENLACE_PARCIAL varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	ALERTA_MIN_MAX smallint NULL,
	ALERTA_META_N1 float NULL,
	ALERTA_META_N2 float NULL,
	INTRANET tinyint NULL,
	ALERTA_N1_TIPO tinyint NULL,
	ALERTA_N2_TIPO tinyint NULL,
	ALERTA_N1_FV tinyint NULL,
	ALERTA_N2_FV tinyint NULL,
	ALERTA_N1_IND_ID int NULL,
	ALERTA_N2_IND_ID int NULL,
	VALOR_INICIAL_CERO tinyint NULL,
	MB_VARIABLE_ID int NULL,
	MASCARA_DECIMALES smallint NULL,
	TIPO_MEDICION tinyint NULL,
	PSUPERIOR_ID int NULL,
	PINFERIOR_ID int NULL,
	LINFERIOR_ID int NULL,
	LSUPERIOR_ID int NULL,
	AINFERIOR_ID int NULL,
	ASUPERIOR_ID int NULL,
	PSUPERIOR_VF float NULL,
	PINFERIOR_VF float NULL,
	LINFERIOR_VF float NULL,
	LSUPERIOR_VF float NULL,
	AINFERIOR_VF float NULL,
	ASUPERIOR_VF float NULL,
	URL text COLLATE Modern_Spanish_CI_AS NULL,
	FECHA_BOQUEO_EJECUTADO datetime NULL,
	FECHA_BOQUEO_META datetime NULL,
	GRAFICO_WEB text COLLATE Modern_Spanish_CI_AS NULL,
	PRIVADO int NULL,
	ALERTA_META_N0 float NULL,
	ALERTA_N0_TIPO tinyint NULL,
	ALERTA_N0_FV tinyint NULL,
	ALERTA_N0_IND_ID int NULL,
	ALERTA_META_N3 float NULL,
	ALERTA_N3_TIPO tinyint NULL,
	ALERTA_N3_FV tinyint NULL,
	ALERTA_N3_IND_ID int NULL
);


-- Strategos2022.dbo.INDICADOR2017 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.INDICADOR2017;

CREATE TABLE INDICADOR2017 (
	INDICADOR_ID numeric(10,0) NOT NULL,
	CLASE_ID numeric(10,0) NOT NULL,
	ORGANIZACION_ID numeric(10,0) NOT NULL,
	NOMBRE varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	DESCRIPCION varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	NATURALEZA numeric(1,0) NOT NULL,
	FRECUENCIA numeric(1,0) NOT NULL,
	GUIA varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	UNIDAD_ID numeric(10,0) NULL,
	CODIGO_ENLACE varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	NOMBRE_CORTO varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	PRIORIDAD numeric(1,0) NOT NULL,
	CONSTANTE numeric(1,0) NOT NULL,
	FUENTE varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	ALIAS varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	ORDEN varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	FUENTE_UBICACION text COLLATE Modern_Spanish_CI_AS NULL,
	FUENTE_ORGANIZACION_ID numeric(10,0) NULL,
	FUENTE_INDICADOR_ID numeric(10,0) NULL,
	TIPO_ASOCIADO tinyint NULL,
	ASOCIADO_ID numeric(10,0) NULL,
	REVISION smallint NULL,
	LOCKBIT tinyint NULL,
	USUARIO_ID numeric(10,0) NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	CREADO_ID numeric(10,0) NULL,
	MODIFICADO_ID numeric(10,0) NULL,
	CALCULADO datetime NULL,
	CALCULADO_ID numeric(10,0) NULL,
	READ_ONLY numeric(1,0) NULL,
	CARACTERISTICA numeric(1,0) NULL,
	CRECIMIENTO_IND numeric(1,0) NULL,
	CRECIMIENTO_PLAN tinyint NULL,
	FECHA_ULTIMA_MEDICION varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	ULTIMA_MEDICION float NULL,
	ESTADO_MEDICION tinyint NULL,
	TIPO tinyint NULL,
	RESP_FIJAR_META_ID numeric(10,0) NULL,
	RESP_LOGRAR_META_ID numeric(10,0) NULL,
	RESP_SEGUIMIENTO_ID numeric(10,0) NULL,
	RESP_CARGAR_META_ID numeric(10,0) NULL,
	RESP_CARGAR_EJECUTADO_ID numeric(10,0) NULL,
	META text COLLATE Modern_Spanish_CI_AS NULL,
	INDICADOR_IMPACTA_ID numeric(10,0) NULL,
	LAG_LEAD numeric(1,0) NULL,
	CORTE numeric(1,0) NULL,
	ENLACE_PARCIAL varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	ALERTA_MIN_MAX numeric(2,0) NULL,
	ALERTA_META_N1 float NULL,
	ALERTA_META_N2 float NULL,
	INTRANET numeric(1,0) NULL,
	ALERTA_N1_TIPO numeric(1,0) NULL,
	ALERTA_N2_TIPO numeric(1,0) NULL,
	ALERTA_N1_FV numeric(1,0) NULL,
	ALERTA_N2_FV numeric(1,0) NULL,
	ALERTA_N1_IND_ID numeric(10,0) NULL,
	ALERTA_N2_IND_ID numeric(10,0) NULL,
	VALOR_INICIAL_CERO numeric(1,0) NULL,
	MB_VARIABLE_ID numeric(10,0) NULL,
	MASCARA_DECIMALES numeric(2,0) NULL,
	TIPO_MEDICION numeric(1,0) NULL,
	PSUPERIOR_ID numeric(10,0) NULL,
	PINFERIOR_ID numeric(10,0) NULL,
	LINFERIOR_ID numeric(10,0) NULL,
	LSUPERIOR_ID numeric(10,0) NULL,
	AINFERIOR_ID numeric(10,0) NULL,
	ASUPERIOR_ID numeric(10,0) NULL,
	PSUPERIOR_VF float NULL,
	PINFERIOR_VF float NULL,
	LINFERIOR_VF float NULL,
	LSUPERIOR_VF float NULL,
	AINFERIOR_VF float NULL,
	ASUPERIOR_VF float NULL,
	URL varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	FECHA_BOQUEO_EJECUTADO datetime NULL,
	FECHA_BOQUEO_META datetime NULL,
	GRAFICO_WEB text COLLATE Modern_Spanish_CI_AS NULL,
	PRIVADO numeric(10,0) NULL,
	ALERTA_META_N0 float NULL,
	ALERTA_N0_TIPO tinyint NULL,
	ALERTA_N0_FV tinyint NULL,
	ALERTA_N0_IND_ID numeric(10,0) NULL,
	ALERTA_META_N3 float NULL,
	ALERTA_N3_TIPO tinyint NULL,
	ALERTA_N3_FV tinyint NULL,
	ALERTA_N3_IND_ID numeric(10,0) NULL,
	multidimensional numeric(1,0) NULL,
	ultimo_programado float NULL,
	tipo_suma numeric(1,0) NULL,
	asignar_Inventario numeric(1,0) NULL,
	fecha_bloqueo_meta datetime NULL,
	fecha_bloqueo_ejecutado datetime NULL
);


-- Strategos2022.dbo.MEDICION2016 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.MEDICION2016;

CREATE TABLE MEDICION2016 (
	INDICADOR_ID int NOT NULL,
	ANO smallint NOT NULL,
	PERIODO smallint NOT NULL,
	VALOR float NULL,
	PROTEGIDO tinyint NOT NULL
);


-- Strategos2022.dbo.MEDICION2017 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.MEDICION2017;

CREATE TABLE MEDICION2017 (
	INDICADOR_ID numeric(10,0) NOT NULL,
	ANO numeric(4,0) NOT NULL,
	PERIODO numeric(3,0) NOT NULL,
	VALOR float NULL,
	PROTEGIDO numeric(1,0) NULL,
	serie_id numeric(10,0) NOT NULL
);


-- Strategos2022.dbo.MEDICION_CARGUE definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.MEDICION_CARGUE;

CREATE TABLE MEDICION_CARGUE (
	enlace_tipo nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	SIAF nvarchar(13) COLLATE Modern_Spanish_CI_AS NOT NULL,
	ANO smallint NOT NULL,
	PERIODO smallint NOT NULL,
	VALOR float NOT NULL
);


-- Strategos2022.dbo.MEDICION_I definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.MEDICION_I;

CREATE TABLE MEDICION_I (
	INDICADOR_ID numeric(10,0) NOT NULL,
	ANO smallint NOT NULL,
	PERIODO smallint NOT NULL,
	VALOR float NULL,
	PROTEGIDO tinyint NULL
);


-- Strategos2022.dbo.ORGANIZACION2016 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ORGANIZACION2016;

CREATE TABLE ORGANIZACION2016 (
	ORGANIZACION_ID int NOT NULL,
	PADRE_ID int NULL,
	NOMBRE varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	RIF varchar(15) COLLATE Modern_Spanish_CI_AS NULL,
	DIRECCION varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	TELEFONO varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	FAX varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	MES_CIERRE smallint NOT NULL,
	HITS int NULL,
	CALCULANDO tinyint NULL,
	ULTIMA_FECHA_CALCULO datetime NULL,
	IMPORTANDO tinyint NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	LOCKBIT tinyint NULL,
	USUARIO_ID int NULL,
	CREADO_ID int NULL,
	MODIFICADO_ID int NULL,
	READ_ONLY tinyint NULL,
	ENLACE_PARCIAL varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	ALERTA_MIN_MAX smallint NULL,
	ALERTA_META_N1 smallint NULL,
	ALERTA_META_N2 smallint NULL,
	MODELO tinyint NULL,
	ALERTA_INICIATIVA_ZV smallint NULL,
	ALERTA_INICIATIVA_ZA smallint NULL,
	VISIBLE int NULL,
	ATRASO_F0 int NULL,
	ATRASO_F1 int NULL,
	ATRASO_F2 int NULL,
	ATRASO_F3 int NULL,
	ATRASO_F4 int NULL,
	ATRASO_F5 int NULL,
	ATRASO_F6 int NULL,
	ATRASO_F7 int NULL,
	ATRASO_F8 int NULL,
	ALERTA_META_N0 float NULL,
	ALERTA_META_N3 float NULL,
	subclase varchar(4) COLLATE Modern_Spanish_CI_AS NULL
);


-- Strategos2022.dbo.ORGANIZACION2017 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ORGANIZACION2017;

CREATE TABLE ORGANIZACION2017 (
	ORGANIZACION_ID numeric(10,0) NOT NULL,
	PADRE_ID numeric(10,0) NULL,
	NOMBRE varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	RIF varchar(15) COLLATE Modern_Spanish_CI_AS NULL,
	DIRECCION varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	TELEFONO varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	FAX varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	MES_CIERRE numeric(4,0) NULL,
	HITS numeric(10,0) NULL,
	CALCULANDO tinyint NULL,
	ULTIMA_FECHA_CALCULO datetime NULL,
	IMPORTANDO tinyint NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	LOCKBIT tinyint NULL,
	USUARIO_ID numeric(10,0) NULL,
	CREADO_ID numeric(10,0) NULL,
	MODIFICADO_ID numeric(10,0) NULL,
	READ_ONLY numeric(1,0) NULL,
	ENLACE_PARCIAL varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	ALERTA_MIN_MAX numeric(4,0) NULL,
	ALERTA_META_N1 numeric(4,0) NULL,
	ALERTA_META_N2 numeric(4,0) NULL,
	MODELO tinyint NULL,
	ALERTA_INICIATIVA_ZV numeric(4,0) NULL,
	ALERTA_INICIATIVA_ZA numeric(4,0) NULL,
	VISIBLE numeric(1,0) NOT NULL,
	ATRASO_F0 numeric(10,0) NULL,
	ATRASO_F1 numeric(10,0) NULL,
	ATRASO_F2 numeric(10,0) NULL,
	ATRASO_F3 numeric(10,0) NULL,
	ATRASO_F4 numeric(10,0) NULL,
	ATRASO_F5 numeric(10,0) NULL,
	ATRASO_F6 numeric(10,0) NULL,
	ATRASO_F7 numeric(10,0) NULL,
	ATRASO_F8 numeric(10,0) NULL,
	ALERTA_META_N0 float NULL,
	ALERTA_META_N3 float NULL,
	subclase varchar(4) COLLATE Modern_Spanish_CI_AS NULL
);


-- Strategos2022.dbo.View_consulta_indicadores_que_falta_excluye_codigo_enlace definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.View_consulta_indicadores_que_falta_excluye_codigo_enlace;

CREATE TABLE View_consulta_indicadores_que_falta_excluye_codigo_enlace (
	INDICADOR_ID int NOT NULL,
	CLASE_ID int NOT NULL,
	ORGANIZACION_ID int NOT NULL,
	NOMBRE varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	DESCRIPCION text COLLATE Modern_Spanish_CI_AS NULL,
	NATURALEZA tinyint NOT NULL,
	FRECUENCIA tinyint NOT NULL,
	UNIDAD_ID int NULL,
	CODIGO_ENLACE varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	NOMBRE_CORTO varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	PRIORIDAD tinyint NOT NULL,
	CONSTANTE tinyint NOT NULL,
	USUARIO_ID int NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	FECHA_BOQUEO_EJECUTADO datetime NULL
);


-- Strategos2022.dbo.View_indicadores_excluidos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.View_indicadores_excluidos;

CREATE TABLE View_indicadores_excluidos (
	INDICADOR_ID int NOT NULL,
	CLASE_ID int NOT NULL,
	ORGANIZACION_ID int NOT NULL,
	NOMBRE varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	DESCRIPCION text COLLATE Modern_Spanish_CI_AS NULL,
	NATURALEZA tinyint NOT NULL,
	FRECUENCIA tinyint NOT NULL,
	UNIDAD_ID int NULL,
	CODIGO_ENLACE varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	NOMBRE_CORTO varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	PRIORIDAD tinyint NOT NULL,
	CONSTANTE tinyint NOT NULL,
	USUARIO_ID int NULL,
	CREADO datetime NULL,
	MODIFICADO datetime NULL,
	FECHA_BOQUEO_EJECUTADO datetime NULL
);


-- Strategos2022.dbo.afw_config_usuario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_config_usuario;

CREATE TABLE afw_config_usuario (
	usuario_id numeric(10,0) NOT NULL,
	configuracion_base varchar(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
	objeto varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	[data] text COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT pk_afw_config_usuario PRIMARY KEY (usuario_id,objeto,configuracion_base)
);


-- Strategos2022.dbo.afw_configuracion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_configuracion;

CREATE TABLE afw_configuracion (
	parametro varchar(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor text COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT pk_afw_configuracion PRIMARY KEY (parametro)
);


-- Strategos2022.dbo.afw_error definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_error;

CREATE TABLE afw_error (
	err_number numeric(10,0) NOT NULL,
	err_source varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	err_description varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	err_timestamp datetime NOT NULL,
	err_user_id varchar(20) COLLATE Modern_Spanish_CI_AS NOT NULL,
	err_version varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	err_step varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	err_stacktrace text COLLATE Modern_Spanish_CI_AS NULL,
	err_cause varchar(2000) COLLATE Modern_Spanish_CI_AS NULL
);


-- Strategos2022.dbo.afw_grupo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_grupo;

CREATE TABLE afw_grupo (
	grupo_id numeric(10,0) NOT NULL,
	grupo varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	creado datetime NOT NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NOT NULL,
	modificado_id numeric(10,0) NULL,
	CONSTRAINT ak1_afw_grupo UNIQUE (grupo),
	CONSTRAINT pk_afw_grupo PRIMARY KEY (grupo_id)
);


-- Strategos2022.dbo.afw_lic definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_lic;

CREATE TABLE afw_lic (
	id numeric(10,0) NOT NULL,
	corporacion varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	serial varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	licenciamiento varchar(100) COLLATE Modern_Spanish_CI_AS NULL
);


-- Strategos2022.dbo.afw_lock definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_lock;

CREATE TABLE afw_lock (
	objeto_id numeric(10,0) NOT NULL,
	tipo varchar(1) COLLATE Modern_Spanish_CI_AS NOT NULL,
	instancia varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	time_stamp datetime NULL,
	CONSTRAINT pk_afw_lock PRIMARY KEY (objeto_id,tipo)
);


-- Strategos2022.dbo.afw_lock_read definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_lock_read;

CREATE TABLE afw_lock_read (
	objeto_id numeric(10,0) NOT NULL,
	instancia varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT pk_afw_lock_read PRIMARY KEY (objeto_id,instancia)
);


-- Strategos2022.dbo.afw_message_resource definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_message_resource;

CREATE TABLE afw_message_resource (
	clave varchar(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT pk_afw_message_resource PRIMARY KEY (clave)
);


-- Strategos2022.dbo.afw_modulo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_modulo;

CREATE TABLE afw_modulo (
	Id varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	Modulo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	Activo numeric(1,0) NOT NULL,
	CONSTRAINT IE_afw_modulo PRIMARY KEY (Id)
);


-- Strategos2022.dbo.afw_objeto_auditable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_objeto_auditable;

CREATE TABLE afw_objeto_auditable (
	objeto_id numeric(10,0) NOT NULL,
	nombre_clase varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_campo_id varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_campo_nombre varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	auditoria_activa numeric(1,0) NULL,
	CONSTRAINT pk_afw_objeto_auditable PRIMARY KEY (objeto_id)
);


-- Strategos2022.dbo.afw_objeto_auditable_atributo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_objeto_auditable_atributo;

CREATE TABLE afw_objeto_auditable_atributo (
	objeto_id numeric(10,0) NOT NULL,
	nombre_atributo varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo numeric(1,0) NOT NULL,
	configuracion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_afw_objeto_auditable_atribu PRIMARY KEY (objeto_id,nombre_atributo)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_afw_objeto_auditable_atribu ON dbo.afw_objeto_auditable_atributo (  objeto_id ASC  , nombre_atributo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_objeto_binario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_objeto_binario;

CREATE TABLE afw_objeto_binario (
	objeto_binario_id numeric(10,0) NOT NULL,
	nombre varchar(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
	mime_type varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	[data] varbinary(1) NULL,
	CONSTRAINT pk_afw_objeto_binario PRIMARY KEY (objeto_binario_id)
);


-- Strategos2022.dbo.afw_objeto_sistema definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_objeto_sistema;

CREATE TABLE afw_objeto_sistema (
	objeto_id varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	art_singular varchar(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
	nombre_singular varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	art_plural varchar(20) COLLATE Modern_Spanish_CI_AS NOT NULL,
	nombre_plural varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_afw_objeto_sistema UNIQUE (nombre_singular),
	CONSTRAINT pk_afw_objeto_sistema PRIMARY KEY (objeto_id)
);


-- Strategos2022.dbo.afw_permiso definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_permiso;

CREATE TABLE afw_permiso (
	permiso_id varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	padre_id varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	permiso varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	nivel numeric(2,0) NULL,
	grupo numeric(2,0) NULL,
	[global] numeric(1,0) NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_afw_permiso PRIMARY KEY (permiso_id)
);


-- Strategos2022.dbo.afw_permiso_grupo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_permiso_grupo;

CREATE TABLE afw_permiso_grupo (
	permiso_id varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	grupo_id numeric(10,0) NOT NULL,
	CONSTRAINT pk_afw_permiso_grupo PRIMARY KEY (permiso_id,grupo_id)
);


-- Strategos2022.dbo.afw_plantilla definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_plantilla;

CREATE TABLE afw_plantilla (
	plantilla_id numeric(10,0) NOT NULL,
	objeto_id numeric(10,0) NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	publico numeric(1,0) NULL,
	tipo varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	[xml] text COLLATE Modern_Spanish_CI_AS NULL,
	usuario_id numeric(10,0) NULL,
	CONSTRAINT pk_afw_plantilla PRIMARY KEY (plantilla_id)
);
 CREATE NONCLUSTERED INDEX xif1afw_plantilla ON dbo.afw_plantilla (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_sistema definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_sistema;

CREATE TABLE afw_sistema (
	id numeric(10,0) NOT NULL,
	version varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	build numeric(10,0) NULL,
	fecha datetime NULL,
	rdbms_id varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	producto varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	cmaxc varchar(1000) COLLATE Modern_Spanish_CI_AS NULL,
	conexion varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	actual varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT XPKafw_sistema PRIMARY KEY (id)
);


-- Strategos2022.dbo.afw_usuario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_usuario;

CREATE TABLE afw_usuario (
	usuario_id numeric(10,0) NOT NULL,
	full_name varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	u_id varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	pwd varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	is_admin numeric(1,0) NULL,
	is_connected numeric(1,0) NULL,
	is_system numeric(1,0) NULL,
	time_stamp datetime NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	instancia varchar(40) COLLATE Modern_Spanish_CI_AS NULL,
	inactivo numeric(1,0) NULL,
	estatus numeric(1,0) NULL,
	bloqueado numeric(1,0) NULL,
	ultima_modif_pwd datetime NULL,
	deshabilitado numeric(1,0) NULL,
	forzarCambiarPwd numeric(1,0) NULL,
	CONSTRAINT ak1_afw_usuario UNIQUE (u_id),
	CONSTRAINT pk_afw_usuario PRIMARY KEY (usuario_id)
);


-- Strategos2022.dbo.afw_usuario_grupo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_usuario_grupo;

CREATE TABLE afw_usuario_grupo (
	usuario_id numeric(10,0) NOT NULL,
	grupo_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NOT NULL,
	modificado datetime NULL,
	modificado_id numeric(10,0) NULL,
	CONSTRAINT pk_afw_usuario_grupo PRIMARY KEY (usuario_id,grupo_id,organizacion_id)
);
 CREATE NONCLUSTERED INDEX missing_index_2_1 ON dbo.afw_usuario_grupo (  grupo_id ASC  )  
	 INCLUDE ( usuario_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX missing_index_45_44 ON dbo.afw_usuario_grupo (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_usuarioweb definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_usuarioweb;

CREATE TABLE afw_usuarioweb (
	usuario_id numeric(10,0) NOT NULL,
	sessionweb varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	time_stamp_conex datetime NOT NULL,
	CONSTRAINT XPKafw_usuarioweb PRIMARY KEY (usuario_id)
);


-- Strategos2022.dbo.afw_version definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_version;

CREATE TABLE afw_version (
	version varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	build varchar(8) COLLATE Modern_Spanish_CI_AS NOT NULL,
	dateBuild varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	createdAt datetime NOT NULL,
	CONSTRAINT PK_afw_version PRIMARY KEY (version,build,dateBuild)
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_afw_version ON dbo.afw_version (  version ASC  , build ASC  , dateBuild ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_afw_version ON dbo.afw_version (  createdAt ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.auditoria definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.auditoria;

CREATE TABLE auditoria (
	auditoria_id numeric(10,0) NOT NULL,
	fecha_ejecucion datetime NULL,
	usuario varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	entidad varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	clase_entidad varchar(MAX) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	detalle varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_auditoria11319_11318 ON dbo.auditoria (  fecha_ejecucion ASC  )  
	 INCLUDE ( auditoria_id , clase_entidad , detalle , entidad , tipo_evento , usuario ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_auditoria11322_11321 ON dbo.auditoria (  auditoria_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.auditoria_medicion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.auditoria_medicion;

CREATE TABLE auditoria_medicion (
	auditoria_medicion_id numeric(10,0) NOT NULL,
	sesion varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_ejecucion datetime NULL,
	accion varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	organizacion varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	organizacion_id numeric(10,0) NOT NULL,
	periodo varchar(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
	periodo_final varchar(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
	usuario varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	indicador varchar(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
	clase varchar(500) COLLATE Modern_Spanish_CI_AS NOT NULL,
	iniciativa varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	indicador_id numeric(10,0) NOT NULL,
	detalle varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_auditoria_medicion PRIMARY KEY (auditoria_medicion_id)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_auditoria_medicion ON dbo.auditoria_medicion (  auditoria_medicion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.calificacion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.calificacion_riesgos;

CREATE TABLE calificacion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	nombre_calificacion_riesgo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	rango_minimo int NOT NULL,
	rango_maximo int NOT NULL,
	color varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	accion_tomar varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK__califica__3213E83F8EBD6FF7 PRIMARY KEY (id)
);


-- Strategos2022.dbo.cargo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.cargo;

CREATE TABLE cargo (
	cargo_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_cargo UNIQUE (nombre),
	CONSTRAINT pk_cargo PRIMARY KEY (cargo_id)
);


-- Strategos2022.dbo.categoria definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.categoria;

CREATE TABLE categoria (
	categoria_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_categoria UNIQUE (nombre),
	CONSTRAINT pk_categoria PRIMARY KEY (categoria_id)
);


-- Strategos2022.dbo.categoria_por_indicador definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.categoria_por_indicador;

CREATE TABLE categoria_por_indicador (
	indicador_id numeric(10,0) NOT NULL,
	categoria_id numeric(10,0) NOT NULL,
	orden numeric(4,0) NULL,
	CONSTRAINT pk_categoria_por_indicador PRIMARY KEY (indicador_id,categoria_id)
);
 CREATE NONCLUSTERED INDEX xif1categoria_por_indicador ON dbo.categoria_por_indicador (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2categoria_por_indicador ON dbo.categoria_por_indicador (  categoria_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.causa definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.causa;

CREATE TABLE causa (
	causa_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion text COLLATE Modern_Spanish_CI_AS NULL,
	nivel numeric(2,0) NULL,
	CONSTRAINT ak1_causa UNIQUE (padre_id,nombre),
	CONSTRAINT pk_causa PRIMARY KEY (causa_id)
);
 CREATE NONCLUSTERED INDEX if_causa_padre_id ON dbo.causa (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.causa_por_problema definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.causa_por_problema;

CREATE TABLE causa_por_problema (
	problema_id numeric(10,0) NOT NULL,
	causa_id numeric(10,0) NOT NULL,
	orden numeric(5,0) NULL,
	CONSTRAINT pk_causa_por_problema PRIMARY KEY (problema_id,causa_id)
);
 CREATE NONCLUSTERED INDEX xif1causa_por_problema ON dbo.causa_por_problema (  problema_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2causa_por_problema ON dbo.causa_por_problema (  causa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.causas_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.causas_riesgos;

CREATE TABLE causas_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	causa_riesgo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__causas_r__3213E83F73E3D3E9 PRIMARY KEY (id)
);


-- Strategos2022.dbo.clase definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.clase;

CREATE TABLE clase (
	clase_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NULL,
	nombre varchar(310) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	creado datetime NULL,
	modificado datetime NULL,
	modificado_id numeric(10,0) NULL,
	creado_id numeric(10,0) NULL,
	enlace_parcial varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	tipo numeric(2,0) NULL,
	visible numeric(1,0) NULL,
	CONSTRAINT ak1_clase UNIQUE (padre_id,organizacion_id,nombre),
	CONSTRAINT pk_clase PRIMARY KEY (clase_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_clase50130_50129 ON dbo.clase (  organizacion_id ASC  )  
	 INCLUDE ( clase_id , creado , creado_id , descripcion , enlace_parcial , modificado , modificado_id , nombre , padre_id , tipo , visible ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_clase934_933 ON dbo.clase (  padre_id ASC  , visible ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_clase_organizacion ON dbo.clase (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_clase_padre ON dbo.clase (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_clase_padre_id_organizacion_id_tipo ON dbo.clase (  padre_id ASC  , organizacion_id ASC  , tipo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.cooperante definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.cooperante;

CREATE TABLE cooperante (
	cooperante_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	descripcion varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	pais varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_cooperante PRIMARY KEY (cooperante_id)
);


-- Strategos2022.dbo.cuenta definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.cuenta;

CREATE TABLE cuenta (
	cuenta_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	codigo varchar(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(250) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_cuenta UNIQUE (padre_id,codigo),
	CONSTRAINT pk_cuenta PRIMARY KEY (cuenta_id)
);
 CREATE NONCLUSTERED INDEX if_cuenta_padre_id ON dbo.cuenta (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.efectividad_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.efectividad_riesgos;

CREATE TABLE efectividad_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	efectividad_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	puntaje int NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__efectivi__3213E83FBF0D7D62 PRIMARY KEY (id)
);


-- Strategos2022.dbo.ejercicios_evaluacion_estatus definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ejercicios_evaluacion_estatus;

CREATE TABLE ejercicios_evaluacion_estatus (
	id bigint IDENTITY(1,1) NOT NULL,
	estatus varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK__ejercici__3213E83F2690A877 PRIMARY KEY (id)
);


-- Strategos2022.dbo.estado_acciones definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.estado_acciones;

CREATE TABLE estado_acciones (
	estado_id numeric(10,0) NOT NULL,
	nombre varchar(15) COLLATE Modern_Spanish_CI_AS NOT NULL,
	aplica_seguimiento numeric(1,0) NULL,
	orden numeric(4,0) NULL,
	condicion numeric(4,0) NULL,
	CONSTRAINT pk_estado_acciones PRIMARY KEY (estado_id)
);


-- Strategos2022.dbo.estatus_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.estatus_riesgos;

CREATE TABLE estatus_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	estatus_riesgo_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK__estatus___3213E83F49D3A743 PRIMARY KEY (id)
);


-- Strategos2022.dbo.explicacion_adjunto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.explicacion_adjunto;

CREATE TABLE explicacion_adjunto (
	explicacion_id numeric(10,0) NOT NULL,
	adjunto_id numeric(10,0) NOT NULL,
	titulo varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	ruta varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	adjunto_binario varbinary(MAX) NULL,
	CONSTRAINT pk_explicacion_adjunto PRIMARY KEY (explicacion_id,adjunto_id)
);
 CREATE NONCLUSTERED INDEX xif1explicacion_adjunto ON dbo.explicacion_adjunto (  explicacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.explicacion_memo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.explicacion_memo;

CREATE TABLE explicacion_memo (
	explicacion_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	memo text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_explicacion_memo PRIMARY KEY (explicacion_id,tipo)
);
 CREATE NONCLUSTERED INDEX xif1explicacion_memo ON dbo.explicacion_memo (  explicacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.fases_proyecto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.fases_proyecto;

CREATE TABLE fases_proyecto (
	fase_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_fase UNIQUE (nombre),
	CONSTRAINT pk_fase PRIMARY KEY (fase_id)
);


-- Strategos2022.dbo.foro definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.foro;

CREATE TABLE foro (
	foro_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	creado_id numeric(10,0) NULL,
	creado datetime NULL,
	objeto_id numeric(10,0) NOT NULL,
	objeto_key numeric(2,0) NOT NULL,
	asunto varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	email varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	comentario text COLLATE Modern_Spanish_CI_AS NOT NULL,
	modificado_id numeric(10,0) NULL,
	modificado datetime NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT pk_foro PRIMARY KEY (foro_id)
);
 CREATE NONCLUSTERED INDEX xif1foro ON dbo.foro (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.impacto_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.impacto_riesgos;

CREATE TABLE impacto_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	impacto varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	puntaje int NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__impacto___3213E83FDAE3A2B4 PRIMARY KEY (id)
);


-- Strategos2022.dbo.inc_actividad definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.inc_actividad;

CREATE TABLE inc_actividad (
	actividad_id numeric(10,0) NOT NULL,
	producto_esperado varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	recurso_humano varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	recurso_material varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	peso float NULL,
	alerta_za float NULL,
	alerta_zv float NULL,
	porcentaje_ejecutado float NULL,
	porcentaje_esperado float NULL,
	porcentaje_completado float NULL,
	alerta numeric(4,0) NULL,
	CONSTRAINT pk_inc_actividad PRIMARY KEY (actividad_id)
);


-- Strategos2022.dbo.indicador_asignar_inventario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_asignar_inventario;

CREATE TABLE indicador_asignar_inventario (
	asignar_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	indicador_insumo_id numeric(10,0) NOT NULL,
	CONSTRAINT pk_asignar PRIMARY KEY (asignar_id)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_asignar ON dbo.indicador_asignar_inventario (  asignar_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_celda definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_celda;

CREATE TABLE indicador_por_celda (
	celda_id numeric(10,0) NOT NULL,
	serie_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	serie_color varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	serie_estilo numeric(1,0) NULL,
	leyenda varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	plan_id numeric(10,0) NULL,
	CONSTRAINT pk_indicador_por_celda PRIMARY KEY (celda_id,serie_id,indicador_id)
);
 CREATE NONCLUSTERED INDEX xif1indicador_por_celda ON dbo.indicador_por_celda (  celda_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2indicador_por_celda ON dbo.indicador_por_celda (  serie_id ASC  , indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif3indicador_por_celda ON dbo.indicador_por_celda (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_perspectiva definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_perspectiva;

CREATE TABLE indicador_por_perspectiva (
	perspectiva_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	peso float NULL,
	CONSTRAINT pk_indicador_por_perspectiva PRIMARY KEY (perspectiva_id,indicador_id)
);
 CREATE NONCLUSTERED INDEX JESB_IX1_indicador_por_perspectiva_20180504 ON dbo.indicador_por_perspectiva (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa_estatus definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa_estatus;

CREATE TABLE iniciativa_estatus (
	id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	porcentaje_inicial float NULL,
	porcentaje_final float NULL,
	sistema numeric(1,0) NOT NULL,
	bloquear_medicion numeric(1,0) NOT NULL,
	bloquear_indicadores numeric(1,0) NOT NULL,
	CONSTRAINT PK_Iniciativa_Estatus PRIMARY KEY (id),
	CONSTRAINT PK_iniciativa_estatus_nombre UNIQUE (nombre)
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_iniciativa_estatus ON dbo.iniciativa_estatus (  nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX IE_Iniciativa_Estatus ON dbo.iniciativa_estatus (  id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.instrumento_iniciativa definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.instrumento_iniciativa;

CREATE TABLE instrumento_iniciativa (
	instrumento_id numeric(10,0) NOT NULL,
	iniciativa_id numeric(10,0) NOT NULL,
	peso float NULL,
	CONSTRAINT PK_instrumento_iniciativa_portId PRIMARY KEY (instrumento_id,iniciativa_id)
);
 CREATE NONCLUSTERED INDEX IE_instrumento_iniciativa_inici ON dbo.instrumento_iniciativa (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_instrumento_iniciativa_instru ON dbo.instrumento_iniciativa (  instrumento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX PK_instrumento_iniciativa ON dbo.instrumento_iniciativa (  instrumento_id ASC  , iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.masc_cod_plan_cuentas_grupo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.masc_cod_plan_cuentas_grupo;

CREATE TABLE masc_cod_plan_cuentas_grupo (
	mascara_id numeric(10,0) NOT NULL,
	nivel numeric(4,0) NOT NULL,
	mascara varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_masc_cod_plan_cuentas_grupo PRIMARY KEY (nivel,mascara_id)
);


-- Strategos2022.dbo.mascara_codigo_plan_cuentas definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mascara_codigo_plan_cuentas;

CREATE TABLE mascara_codigo_plan_cuentas (
	mascara_id numeric(10,0) NOT NULL,
	niveles numeric(4,0) NOT NULL,
	mascara varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_mascara_codigo_plan_cuentas PRIMARY KEY (mascara_id)
);


-- Strategos2022.dbo.mb_etiqueta definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_etiqueta;

CREATE TABLE mb_etiqueta (
	atributo_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK_MB_ETIQUETA PRIMARY KEY (atributo_id,orden)
);


-- Strategos2022.dbo.mb_grafico definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_grafico;

CREATE TABLE mb_grafico (
	grafico_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	tipo numeric(1,0) NOT NULL,
	elemento_id numeric(10,0) NULL,
	CONSTRAINT PK_MB_GRAFICO PRIMARY KEY (grafico_id)
);


-- Strategos2022.dbo.mb_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_variable;

CREATE TABLE mb_variable (
	variable_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	frecuencia numeric(1,0) NOT NULL,
	creado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado datetime NULL,
	modificado_id numeric(10,0) NULL,
	CONSTRAINT AK_MB_VARIABLE UNIQUE (nombre),
	CONSTRAINT PK_MB_VARIABLE PRIMARY KEY (variable_id)
);


-- Strategos2022.dbo.mb_webaddress definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_webaddress;

CREATE TABLE mb_webaddress (
	address_id numeric(10,0) NOT NULL,
	address varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK_MB_WEBADDRESS PRIMARY KEY (address_id)
);


-- Strategos2022.dbo.medicion_bckup182018 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.medicion_bckup182018;

CREATE TABLE medicion_bckup182018 (
	serie_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	valor float NULL,
	protegido numeric(1,0) NULL
);


-- Strategos2022.dbo.metodologia definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.metodologia;

CREATE TABLE metodologia (
	metodologia_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion text COLLATE Modern_Spanish_CI_AS NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	indicador_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	iniciativa_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	actividad_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	indicador_nombre_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	iniciativa_nombre_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	actividad_nombre_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	tipo numeric(1,0) NULL,
	indicador_articulo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	iniciativa_articulo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	actividad_articulo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	indicador_articulo_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	iniciativa_articulo_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	actividad_articulo_plural varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT ak1_metodologia UNIQUE (nombre),
	CONSTRAINT pk_metodologia PRIMARY KEY (metodologia_id)
);


-- Strategos2022.dbo.metodologia_template definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.metodologia_template;

CREATE TABLE metodologia_template (
	metodologia_template_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	metodologia_id numeric(10,0) NULL,
	orden numeric(4,0) NULL,
	tipo numeric(2,0) NULL,
	CONSTRAINT ak1_metodologia_template UNIQUE (metodologia_id,nombre),
	CONSTRAINT pk_metodologia_template PRIMARY KEY (metodologia_template_id)
);
 CREATE NONCLUSTERED INDEX xif1metodologia_template ON dbo.metodologia_template (  metodologia_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.objetos_calcular definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.objetos_calcular;

CREATE TABLE objetos_calcular (
	Id uniqueidentifier NOT NULL,
	Objeto_Id numeric(10,0) NOT NULL,
	Usuario_Id numeric(10,0) NOT NULL,
	Calculado numeric(1,0) NULL
);


-- Strategos2022.dbo.organizacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.organizacion;

CREATE TABLE organizacion (
	organizacion_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	nombre varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	rif varchar(15) COLLATE Modern_Spanish_CI_AS NULL,
	direccion varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	fax varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	mes_cierre numeric(4,0) NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	alerta_min_max numeric(4,0) NULL,
	alerta_meta_n1 numeric(4,0) NULL,
	alerta_meta_n2 numeric(4,0) NULL,
	alerta_iniciativa_zv numeric(4,0) NULL,
	alerta_iniciativa_za numeric(4,0) NULL,
	enlace_parcial varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	subclase varchar(4) COLLATE Modern_Spanish_CI_AS NULL,
	visible numeric(1,0) NOT NULL,
	read_only numeric(1,0) NULL,
	administrador varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT ak1_organizacion UNIQUE (padre_id,nombre),
	CONSTRAINT pk_organizacion PRIMARY KEY (organizacion_id)
);
 CREATE NONCLUSTERED INDEX if_organizacion_padre_id ON dbo.organizacion (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.organizacion_memo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.organizacion_memo;

CREATE TABLE organizacion_memo (
	organizacion_id numeric(10,0) NOT NULL,
	tipo numeric(2,0) NOT NULL,
	descripcion text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_organizacion_memo PRIMARY KEY (organizacion_id,tipo)
);
 CREATE NONCLUSTERED INDEX xif1organizacion_memo ON dbo.organizacion_memo (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.pgn_enlace definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pgn_enlace;

CREATE TABLE pgn_enlace (
	id numeric(10,0) NOT NULL,
	enlace_tipo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	bi numeric(10,0) NULL,
	categoria numeric(10,0) NULL,
	CONSTRAINT CA_pgn_enlace UNIQUE (enlace_tipo),
	CONSTRAINT CE_pgn_enlace UNIQUE (enlace_descripcion),
	CONSTRAINT CP_pgn_enlace PRIMARY KEY (id)
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_pgn_enlace ON dbo.pgn_enlace (  enlace_tipo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX IE_pgn_enlace ON dbo.pgn_enlace (  enlace_descripcion ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX PK_pgn_enlace ON dbo.pgn_enlace (  id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.pgn_enlace2017 definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pgn_enlace2017;

CREATE TABLE pgn_enlace2017 (
	id numeric(10,0) NOT NULL,
	enlace_tipo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	enlace_descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	bi numeric(10,0) NULL,
	categoria numeric(10,0) NULL
);


-- Strategos2022.dbo.plan_nivel definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.plan_nivel;

CREATE TABLE plan_nivel (
	plan_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	nivel float NULL,
	CONSTRAINT pk_plan_estado PRIMARY KEY (plan_id,tipo,ano,periodo)
);


-- Strategos2022.dbo.prd_seguimiento definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.prd_seguimiento;

CREATE TABLE prd_seguimiento (
	iniciativa_id numeric(10,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	fecha datetime NULL,
	alerta numeric(1,0) NULL,
	seguimiento text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_prd_seguimiento PRIMARY KEY (iniciativa_id,ano,periodo)
);
 CREATE NONCLUSTERED INDEX xif1prd_seguimiento ON dbo.prd_seguimiento (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.probabilidad_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.probabilidad_riesgos;

CREATE TABLE probabilidad_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	probabilidad varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	puntaje int NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__probabil__3213E83F3FD1CF57 PRIMARY KEY (id)
);


-- Strategos2022.dbo.problema_memo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.problema_memo;

CREATE TABLE problema_memo (
	problema_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	memo text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_memo_problema PRIMARY KEY (problema_id,tipo)
);
 CREATE NONCLUSTERED INDEX xif1memo_problema ON dbo.problema_memo (  problema_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.procesos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.procesos;

CREATE TABLE procesos (
	proceso_id bigint IDENTITY(1,1) NOT NULL,
	proceso_padre_id bigint NULL,
	proceso_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	proceso_descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	proceso_codigo varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	proceso_tipo varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	proceso_responsable_id varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	proceso_documento varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__procesos__3768458FE66707B0 PRIMARY KEY (proceso_id)
);


-- Strategos2022.dbo.pry_actividad definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_actividad;

CREATE TABLE pry_actividad (
	actividad_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	proyecto_id numeric(10,0) NOT NULL,
	nombre varchar(250) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(5000) COLLATE Modern_Spanish_CI_AS NULL,
	comienzo_plan datetime NULL,
	comienzo_real datetime NULL,
	fin_plan datetime NULL,
	fin_real datetime NULL,
	duracion_plan float NULL,
	duracion_real float NULL,
	unidad_id numeric(10,0) NULL,
	fila numeric(10,0) NULL,
	nivel numeric(4,0) NULL,
	compuesta numeric(1,0) NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	indicador_id numeric(10,0) NULL,
	naturaleza numeric(1,0) NULL,
	clase_id numeric(10,0) NULL,
	resp_fijar_meta_id numeric(10,0) NULL,
	resp_lograr_meta_id numeric(10,0) NULL,
	resp_seguimiento_id numeric(10,0) NULL,
	resp_cargar_ejecutado_id numeric(10,0) NULL,
	resp_cargar_meta_id numeric(10,0) NULL,
	tipo_medicion numeric(1,0) NULL,
	objeto_asociado_id numeric(10,0) NULL,
	objeto_asociado_plan_id numeric(10,0) NULL,
	objeto_asociado_className varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	crecimiento numeric(1,0) NULL,
	porcentaje_completado float NULL,
	porcentaje_esperado float NULL,
	porcentaje_ejecutado float NULL,
	fecha_ultima_medicion varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_pry_actividad PRIMARY KEY (actividad_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_pry_actividad2_1 ON dbo.pry_actividad (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_pry_actividad_objeto_asociado_id ON dbo.pry_actividad (  objeto_asociado_id ASC  )  
	 INCLUDE ( actividad_id , clase_id , comienzo_plan , comienzo_real , compuesta , creado , creado_id , crecimiento , descripcion , duracion_plan , duracion_real , fecha_ultima_medicion , fila , fin_plan , fin_real , indicador_id , modificado , modificado_id , naturaleza , nivel , nombre , objeto_asociado_className , padre_id , porcentaje_completado , porcentaje_ejecutado , porcentaje_esperado , proyecto_id , resp_cargar_ejecutado_id , resp_cargar_meta_id , resp_fijar_meta_id , resp_lograr_meta_id , resp_seguimiento_id , tipo_medicion , unidad_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif10pry_actividad ON dbo.pry_actividad (  resp_cargar_ejecutado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif11pry_actividad ON dbo.pry_actividad (  resp_cargar_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif1pry_actividad ON dbo.pry_actividad (  proyecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2pry_actividad ON dbo.pry_actividad (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif3pry_actividad ON dbo.pry_actividad (  unidad_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.pry_calendario_detalle definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_calendario_detalle;

CREATE TABLE pry_calendario_detalle (
	calendario_id numeric(10,0) NOT NULL,
	fecha datetime NULL,
	feriado numeric(1,0) NULL,
	CONSTRAINT pk_pry_calendario_detalle PRIMARY KEY (calendario_id)
);


-- Strategos2022.dbo.pry_columna definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_columna;

CREATE TABLE pry_columna (
	columna_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	numerico numeric(1,0) NULL,
	alineacion numeric(1,0) NULL,
	formato_java varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	tamano_java numeric(4,0) NULL,
	CONSTRAINT ak1_pry_columna UNIQUE (nombre),
	CONSTRAINT pk_pry_columna PRIMARY KEY (columna_id)
);


-- Strategos2022.dbo.pry_columna_por_vista definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_columna_por_vista;

CREATE TABLE pry_columna_por_vista (
	vista_id numeric(10,0) NOT NULL,
	columna_id numeric(10,0) NOT NULL,
	alineacion numeric(1,0) NULL,
	ancho_java numeric(4,0) NULL,
	orden numeric(2,0) NULL,
	CONSTRAINT pk_pry_columna_por_vista PRIMARY KEY (vista_id,columna_id)
);
 CREATE NONCLUSTERED INDEX xif1pry_columna_por_vista ON dbo.pry_columna_por_vista (  vista_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2pry_columna_por_vista ON dbo.pry_columna_por_vista (  columna_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.pry_proyecto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_proyecto;

CREATE TABLE pry_proyecto (
	proyecto_id numeric(10,0) NOT NULL,
	nombre varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	comienzo datetime NULL,
	comienzo_plan datetime NULL,
	comienzo_real datetime NULL,
	fin_plan datetime NULL,
	fin_real datetime NULL,
	duracion_plan float NULL,
	duracion_real float NULL,
	variacion_comienzo float NULL,
	variacion_fin float NULL,
	variacion_duracion float NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	CONSTRAINT pk_pry_proyecto PRIMARY KEY (proyecto_id)
);


-- Strategos2022.dbo.pry_vista definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_vista;

CREATE TABLE pry_vista (
	vista_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT ak1_pry_vista UNIQUE (nombre),
	CONSTRAINT pk_pry_vista PRIMARY KEY (vista_id)
);


-- Strategos2022.dbo.reporte_servicio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.reporte_servicio;

CREATE TABLE reporte_servicio (
	reporte_id numeric(10,0) NOT NULL,
	responsable_id numeric(10,0) NOT NULL,
	medicion varchar(MAX) COLLATE Modern_Spanish_CI_AS NOT NULL,
	indicador_id numeric(10,0) NOT NULL
);


-- Strategos2022.dbo.responsable_grupo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.responsable_grupo;

CREATE TABLE responsable_grupo (
	padre_id numeric(10,0) NOT NULL,
	responsable_id numeric(10,0) NOT NULL,
	CONSTRAINT pk_responsable_grupo PRIMARY KEY (padre_id,responsable_id)
);
 CREATE NONCLUSTERED INDEX if_resp_grupo_pad_id ON dbo.responsable_grupo (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX if_resp_grupo_res_id ON dbo.responsable_grupo (  responsable_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.responsable_por_accion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.responsable_por_accion;

CREATE TABLE responsable_por_accion (
	accion_id numeric(10,0) NOT NULL,
	responsable_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT xpkresponsable_por_accion PRIMARY KEY (accion_id,responsable_id)
);
 CREATE NONCLUSTERED INDEX xif1responsable_por_accion ON dbo.responsable_por_accion (  accion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2responsable_por_accion ON dbo.responsable_por_accion (  responsable_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.respuestas_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.respuestas_riesgos;

CREATE TABLE respuestas_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	respuesta_riesgo_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__respuest__3213E83FEAFA0D84 PRIMARY KEY (id)
);


-- Strategos2022.dbo.riesgos_logs_auditoria definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.riesgos_logs_auditoria;

CREATE TABLE riesgos_logs_auditoria (
	id bigint IDENTITY(1,1) NOT NULL,
	tipo_log varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	clase_origen varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	mensaje varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fecha_creacion bigint NOT NULL,
	CONSTRAINT PK__riesgos___3213E83FB40A74EF PRIMARY KEY (id)
);


-- Strategos2022.dbo.schema_version definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.schema_version;

CREATE TABLE schema_version (
	installed_rank int NOT NULL,
	version nvarchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	description nvarchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	[type] nvarchar(20) COLLATE Modern_Spanish_CI_AS NOT NULL,
	script nvarchar(1000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	checksum int NULL,
	installed_by nvarchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	installed_on datetime DEFAULT getdate() NOT NULL,
	execution_time int NOT NULL,
	success bit NOT NULL,
	CONSTRAINT schema_version_pk PRIMARY KEY (installed_rank)
);
 CREATE NONCLUSTERED INDEX schema_version_s_idx ON dbo.schema_version (  success ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.seguimiento definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.seguimiento;

CREATE TABLE seguimiento (
	seguimiento_id numeric(10,0) NOT NULL,
	accion_id numeric(10,0) NOT NULL,
	estado_id numeric(10,0) NULL,
	fecha_emision datetime NULL,
	emision_enviado numeric(1,0) NULL,
	fecha_recepcion datetime NULL,
	recepcion_enviado numeric(1,0) NULL,
	fecha_aprobacion datetime NULL,
	preparado_por varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	numero_de_reporte numeric(5,0) NULL,
	aprobado numeric(1,0) NULL,
	aprobado_por varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	clave_correo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	read_only numeric(1,0) NULL,
	CONSTRAINT pk_seguimiento PRIMARY KEY (seguimiento_id)
);
 CREATE NONCLUSTERED INDEX xif1seguimiento ON dbo.seguimiento (  accion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2seguimiento ON dbo.seguimiento (  estado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.seguimiento_memo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.seguimiento_memo;

CREATE TABLE seguimiento_memo (
	seguimiento_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	memo text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_seguimiento_memo PRIMARY KEY (seguimiento_id,tipo)
);
 CREATE NONCLUSTERED INDEX xif1seguimiento_memo ON dbo.seguimiento_memo (  seguimiento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.seguimiento_mensaje_email definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.seguimiento_mensaje_email;

CREATE TABLE seguimiento_mensaje_email (
	mensaje_id numeric(10,0) NOT NULL,
	descripcion varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	mensaje text COLLATE Modern_Spanish_CI_AS NOT NULL,
	acuse_recibo numeric(1,0) NULL,
	solo_auxiliar numeric(1,0) NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	CONSTRAINT pk_seguimiento_mensaje_email PRIMARY KEY (mensaje_id)
);


-- Strategos2022.dbo.serie_indicador definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.serie_indicador;

CREATE TABLE serie_indicador (
	serie_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	naturaleza numeric(1,0) NULL,
	fecha_bloqueo datetime NULL,
	CONSTRAINT pk_serie_indicador PRIMARY KEY (serie_id,indicador_id)
);
 CREATE NONCLUSTERED INDEX ie_serie_indicador_indicador_serie ON dbo.serie_indicador (  indicador_id ASC  , serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.serie_plan definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.serie_plan;

CREATE TABLE serie_plan (
	plan_id numeric(10,0) NOT NULL,
	serie_id numeric(10,0) NOT NULL,
	CONSTRAINT xpkserie_plan PRIMARY KEY (plan_id,serie_id)
);
 CREATE NONCLUSTERED INDEX xif1serie_plan ON dbo.serie_plan (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2serie_plan ON dbo.serie_plan (  serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.serie_tiempo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.serie_tiempo;

CREATE TABLE serie_tiempo (
	serie_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fija numeric(1,0) NOT NULL,
	oculta numeric(1,0) NOT NULL,
	identificador varchar(5) COLLATE Modern_Spanish_CI_AS NOT NULL,
	preseleccionada numeric(1,0) NOT NULL,
	CONSTRAINT pk_serie_tiempo PRIMARY KEY (serie_id),
	CONSTRAINT pk_serie_tiempo_identificador UNIQUE (identificador),
	CONSTRAINT pk_serie_tiempo_nombre UNIQUE (nombre)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_serie_tiempo ON dbo.serie_tiempo (  serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_serie_tiempo_identificador ON dbo.serie_tiempo (  identificador ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_serie_tiempo_nombre ON dbo.serie_tiempo (  nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.sysdiagrams definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.sysdiagrams;

CREATE TABLE sysdiagrams (
	name sysname COLLATE Modern_Spanish_CI_AS NOT NULL,
	principal_id int NOT NULL,
	diagram_id int IDENTITY(1,1) NOT NULL,
	version int NULL,
	definition varbinary(MAX) NULL,
	CONSTRAINT PK__sysdiagr__C2B05B61CFB1629B PRIMARY KEY (diagram_id),
	CONSTRAINT UK_principal_name UNIQUE (principal_id,name)
);


-- Strategos2022.dbo.tipo_convenio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.tipo_convenio;

CREATE TABLE tipo_convenio (
	tipos_convenio_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	descripcion varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_tipo_convenio PRIMARY KEY (tipos_convenio_id)
);


-- Strategos2022.dbo.tipo_moneda definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.tipo_moneda;

CREATE TABLE tipo_moneda (
	tipo_moneda_id numeric(10,0) NOT NULL,
	moneda varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	identificador varchar(5) COLLATE Modern_Spanish_CI_AS NOT NULL,
	factor_conversion_bolivares float NOT NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	solo_lectura numeric(1,0) NULL,
	CONSTRAINT ak1_tipo_moneda UNIQUE (moneda),
	CONSTRAINT ak2_tipo_moneda UNIQUE (identificador),
	CONSTRAINT pk_tipo_moneda PRIMARY KEY (tipo_moneda_id)
);


-- Strategos2022.dbo.tipo_proyecto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.tipo_proyecto;

CREATE TABLE tipo_proyecto (
	tipo_proyecto_id numeric(10,0) NOT NULL,
	nombre_proyecto varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT pk_tipo_proyecto PRIMARY KEY (tipo_proyecto_id)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_tipo_proyecto ON dbo.tipo_proyecto (  tipo_proyecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.tipo_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.tipo_riesgos;

CREATE TABLE tipo_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	tipo_riesgo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK__tipo_rie__3213E83F62B4CBCC PRIMARY KEY (id)
);


-- Strategos2022.dbo.unidad definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.unidad;

CREATE TABLE unidad (
	unidad_id numeric(10,0) NOT NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo numeric(2,0) NULL,
	CONSTRAINT ak1_unidad UNIQUE (nombre),
	CONSTRAINT pk_unidad PRIMARY KEY (unidad_id)
);


-- Strategos2022.dbo.afw_auditoria_entero definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_entero;

CREATE TABLE afw_auditoria_entero (
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor numeric(10,0) NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	nombre_atributo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	valor_anterior numeric(10,0) NULL,
	CONSTRAINT pk_afw_auditoria_entero PRIMARY KEY (fecha,instancia_id,nombre_atributo),
	CONSTRAINT FK_USER_AUDIT_ENT FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IF_afw_auditoria_entero ON dbo.afw_auditoria_entero (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_afw_auditoria_entero_20180425 ON dbo.afw_auditoria_entero (  nombre_atributo ASC  , instancia_id ASC  )  
	 INCLUDE ( fecha , objeto_id , tipo_evento , usuario_id , valor , valor_anterior ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_auditoria_evento definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_evento;

CREATE TABLE afw_auditoria_evento (
	objeto_id numeric(10,0) NOT NULL,
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	instancia_nombre varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	usuario_id numeric(10,0) NULL,
	CONSTRAINT pk_afw_auditoria_evento PRIMARY KEY (fecha,instancia_id,tipo_evento),
	CONSTRAINT FK_USER_AUDITEVENT FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_afw_auditoria_evento11077_11076 ON dbo.afw_auditoria_evento (  usuario_id ASC  )  
	 INCLUDE ( fecha , instancia_id , instancia_nombre , objeto_id , tipo_evento ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IF_afw_auditoria_evento ON dbo.afw_auditoria_evento (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_auditoria_fecha definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_fecha;

CREATE TABLE afw_auditoria_fecha (
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor datetime NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	nombre_atributo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	valor_anterior datetime NULL,
	CONSTRAINT pk_afw_auditoria_fecha PRIMARY KEY (fecha,instancia_id,nombre_atributo),
	CONSTRAINT FK_USER_AUDITFECHA FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX XIF1afw_auditoria_fecha ON dbo.afw_auditoria_fecha (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 95   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_auditoria_flotante definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_flotante;

CREATE TABLE afw_auditoria_flotante (
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor float NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	nombre_atributo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	valor_anterior float NULL,
	CONSTRAINT pk_afw_auditoria_flotante PRIMARY KEY (fecha,instancia_id,nombre_atributo),
	CONSTRAINT FK_USER_AUDITFLOTANTE FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IF_afw_auditoria_flotante ON dbo.afw_auditoria_flotante (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_afw_auditoria_flotante_20180504 ON dbo.afw_auditoria_flotante (  instancia_id ASC  , nombre_atributo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_auditoria_memo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_memo;

CREATE TABLE afw_auditoria_memo (
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	nombre_atributo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	valor_anterior varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_afw_auditoria_memo PRIMARY KEY (fecha,instancia_id,nombre_atributo),
	CONSTRAINT FK_USER_AUDITMEMO FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX XIF1afw_auditoria_memo ON dbo.afw_auditoria_memo (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_auditoria_string definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_auditoria_string;

CREATE TABLE afw_auditoria_string (
	fecha datetime NOT NULL,
	instancia_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	valor varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	nombre_atributo varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo_evento numeric(1,0) NOT NULL,
	valor_anterior varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_afw_auditoria_string PRIMARY KEY (fecha,instancia_id,nombre_atributo),
	CONSTRAINT FK_USER_AUDITSTRING FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IF_afw_auditoria_string ON dbo.afw_auditoria_string (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_importacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_importacion;

CREATE TABLE afw_importacion (
	id numeric(10,0) NOT NULL,
	usuario_id numeric(10,0) NOT NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo numeric(1,0) NOT NULL,
	configuracion varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK_afw_importacion PRIMARY KEY (id),
	CONSTRAINT PK_fw_importacion_usuario_nomb UNIQUE (usuario_id,nombre),
	CONSTRAINT FK_usuario_importacion FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_afw_importacion ON dbo.afw_importacion (  id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX AK_fw_importacion_usuario_nomb ON dbo.afw_importacion (  usuario_id ASC  , nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_afw_importacion_nombre ON dbo.afw_importacion (  nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_afw_importacion_usuarioId ON dbo.afw_importacion (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_message definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_message;

CREATE TABLE afw_message (
	usuario_id numeric(10,0) NOT NULL,
	fecha datetime NOT NULL,
	estatus numeric(1,0) NOT NULL,
	mensaje varchar(500) COLLATE Modern_Spanish_CI_AS NOT NULL,
	tipo numeric(1,0) NOT NULL,
	fuente varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_afw_message PRIMARY KEY (usuario_id,fecha),
	CONSTRAINT R_17 FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_afw_message58_57 ON dbo.afw_message (  usuario_id ASC  , estatus ASC  , tipo ASC  )  
	 INCLUDE ( fecha , fuente , mensaje ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IF_afw_message ON dbo.afw_message (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_pwd_historia definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_pwd_historia;

CREATE TABLE afw_pwd_historia (
	usuario_id numeric(10,0) NOT NULL,
	pwd varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	fecha datetime NOT NULL,
	CONSTRAINT PK_afw_pwd_historia PRIMARY KEY (usuario_id,fecha),
	CONSTRAINT FK_USER_PWDHISTORIA FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IF_afw_pwd_historia ON dbo.afw_pwd_historia (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_servicio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_servicio;

CREATE TABLE afw_servicio (
	usuario_id numeric(10,0) NOT NULL,
	fecha datetime NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	estatus numeric(1,0) NOT NULL,
	mensaje varchar(1000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	log text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_afw_servicio PRIMARY KEY (usuario_id,fecha,nombre),
	CONSTRAINT FK_USER_SERVICIO FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IF_afw_servicio ON dbo.afw_servicio (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.afw_user_session definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.afw_user_session;

CREATE TABLE afw_user_session (
	session_id varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	usuario_id numeric(10,0) NOT NULL,
	login_ts datetime NOT NULL,
	remote_address varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	remote_host varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	remote_user varchar(1000) COLLATE Modern_Spanish_CI_AS NULL,
	url varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	persona_id numeric(10,0) NULL,
	CONSTRAINT pk_afw_usersession PRIMARY KEY (session_id),
	CONSTRAINT FK_USER_USERSESSION FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX XIF1afw_user_session ON dbo.afw_user_session (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif1afw_usersession ON dbo.afw_user_session (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.clase_problema definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.clase_problema;

CREATE TABLE clase_problema (
	clase_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT pk_clase_problema PRIMARY KEY (clase_id),
	CONSTRAINT FK_creado_claseproblema FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT FK_modificado_claseproblema FOREIGN KEY (modificado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT FK_organizacion_claseproblema FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id),
	CONSTRAINT FK_padre_claseproblema FOREIGN KEY (padre_id) REFERENCES clase_problema(clase_id)
);
 CREATE NONCLUSTERED INDEX IE_creado_claseproblema ON dbo.clase_problema (  creado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_modificado_claseproblema ON dbo.clase_problema (  modificado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_organizacion_claseproblema ON dbo.clase_problema (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_padre_claseproblema ON dbo.clase_problema (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.ejercicios_evaluacion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.ejercicios_evaluacion_riesgos;

CREATE TABLE ejercicios_evaluacion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	proceso_id bigint NOT NULL,
	estatus_id bigint NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_creacion_ejercicio bigint NOT NULL,
	CONSTRAINT PK__ejercici__3213E83FBA00F400 PRIMARY KEY (id),
	CONSTRAINT FK__ejercicio__estat__405A880E FOREIGN KEY (estatus_id) REFERENCES ejercicios_evaluacion_estatus(id) ON UPDATE CASCADE,
	CONSTRAINT FK__ejercicio__proce__3F6663D5 FOREIGN KEY (proceso_id) REFERENCES procesos(proceso_id) ON UPDATE CASCADE
);


-- Strategos2022.dbo.explicacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.explicacion;

CREATE TABLE explicacion (
	explicacion_id numeric(10,0) NOT NULL,
	creado_id numeric(10,0) NULL,
	creado datetime NULL,
	objeto_id numeric(10,0) NOT NULL,
	objeto_key numeric(2,0) NOT NULL,
	titulo varchar(250) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fecha datetime NULL,
	tipo numeric(1,0) NOT NULL,
	fecha_compromiso datetime NULL,
	fecha_real datetime NULL,
	publico numeric(1,0) NOT NULL,
	CONSTRAINT pk_explicacion PRIMARY KEY (explicacion_id),
	CONSTRAINT FK_usuario_explicacion FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion139_138 ON dbo.explicacion (  objeto_id ASC  , tipo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion13_12 ON dbo.explicacion (  tipo ASC  )  
	 INCLUDE ( creado , creado_id , explicacion_id , fecha , fecha_compromiso , fecha_real , objeto_id , objeto_key , publico , titulo ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion15_14 ON dbo.explicacion (  objeto_id ASC  , objeto_key ASC  , tipo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion562_561 ON dbo.explicacion (  objeto_id ASC  , tipo ASC  , explicacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion640_639 ON dbo.explicacion (  objeto_id ASC  , objeto_key ASC  , tipo ASC  , fecha ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_explicacion7965_7964 ON dbo.explicacion (  objeto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_explicacion_creado_id ON dbo.explicacion (  creado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.explicacion_pgn definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.explicacion_pgn;

CREATE TABLE explicacion_pgn (
	explicacion_id numeric(10,0) NOT NULL,
	objeto_id numeric(10,0) NOT NULL,
	titulo varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	fecha datetime NULL,
	creado datetime NULL,
	creado_id numeric(10,0) NOT NULL,
	is_fechas numeric(1,0) NULL,
	explicacion_fechas varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	is_recibido numeric(1,0) NULL,
	explicacion_recibido varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_explicacion_pgn PRIMARY KEY (explicacion_id),
	CONSTRAINT FK_usuario_explicacion_pgn FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.grafico definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.grafico;

CREATE TABLE grafico (
	grafico_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	configuracion varchar(5000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	usuario_id numeric(10,0) NULL,
	objeto_id numeric(10,0) NULL,
	className varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT ak_grafico UNIQUE (organizacion_id,usuario_id,nombre,objeto_id),
	CONSTRAINT pk_grafico PRIMARY KEY (grafico_id),
	CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE,
	CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.instrumentos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.instrumentos;

CREATE TABLE instrumentos (
	instrumento_id numeric(10,0) NOT NULL,
	nombre_corto varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_instrumento varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	objetivo_instrumento varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	productos varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	cooperante_id numeric(10,0) NULL,
	tipos_convenio_id numeric(10,0) NULL,
	anio varchar(4) COLLATE Modern_Spanish_CI_AS NULL,
	instrumento_marco varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_inicio datetime NULL,
	fecha_terminacion datetime NULL,
	fecha_prorroga datetime NULL,
	recursos_pesos float NULL,
	recursos_dolares float NULL,
	nombre_ejecutante varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	estatus numeric(18,0) NULL,
	areas_cargo varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_responsable_areas varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	responsable_cgi varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	observaciones varchar(2500) COLLATE Modern_Spanish_CI_AS NULL,
	clase_id numeric(10,0) NULL,
	is_historico numeric(1,0) NULL,
	CONSTRAINT PK_instrumento PRIMARY KEY (instrumento_id),
	CONSTRAINT pk_cooperante_id FOREIGN KEY (cooperante_id) REFERENCES cooperante(cooperante_id),
	CONSTRAINT pk_tipos_convenio_id FOREIGN KEY (tipos_convenio_id) REFERENCES tipo_convenio(tipos_convenio_id)
);
 CREATE NONCLUSTERED INDEX indx_cooperante ON dbo.instrumentos (  cooperante_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX indx_tipo_convenio ON dbo.instrumentos (  tipos_convenio_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_categoria_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_categoria_variable;

CREATE TABLE mb_categoria_variable (
	variable_id numeric(10,0) NOT NULL,
	categoria_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NOT NULL,
	CONSTRAINT AK_MB_CATEGORIA_VARIABLE PRIMARY KEY (variable_id,categoria_id),
	CONSTRAINT FK_MB_VARIABLE_CATEGORIA FOREIGN KEY (variable_id) REFERENCES mb_variable(variable_id) ON DELETE CASCADE,
	CONSTRAINT FK_mdvariable_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MBCATEGORIA_VARIABLEID ON dbo.mb_categoria_variable (  variable_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_MBVARIABLE_CATEGORIAID ON dbo.mb_categoria_variable (  categoria_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_grafico_binario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_grafico_binario;

CREATE TABLE mb_grafico_binario (
	grafico_id numeric(10,0) NOT NULL,
	binario text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_MB_GRAFICO_BINARIO PRIMARY KEY (grafico_id),
	CONSTRAINT FK_MB_GRAFICO_BINARIO FOREIGN KEY (grafico_id) REFERENCES mb_grafico(grafico_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_histograma definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_histograma;

CREATE TABLE mb_histograma (
	elemento_id numeric(10,0) NOT NULL,
	fila_id numeric(10,0) NOT NULL,
	categoria_id numeric(10,0) NOT NULL,
	frecuencia numeric(5,0) NULL,
	CONSTRAINT AK_MB_HISTOGRAMA PRIMARY KEY (elemento_id,categoria_id,fila_id),
	CONSTRAINT FK_mbhistograma_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MBHISTOGRAMA_CATEGORIAID ON dbo.mb_histograma (  categoria_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_MBHISTOGRAMA_ELEMENTOID ON dbo.mb_histograma (  elemento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_medicion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_medicion;

CREATE TABLE mb_medicion (
	medicion_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	address_id numeric(10,0) NULL,
	fecha datetime NOT NULL,
	modalidad numeric(1,0) NOT NULL,
	responsable varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	creado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado datetime NULL,
	modificado_id numeric(10,0) NULL,
	criterio_validez numeric(3,0) NULL,
	criterio_desempate numeric(1,0) NULL,
	define_prioridad numeric(1,0) NULL,
	tipo_sintesis numeric(2,0) NULL,
	sintetizado numeric(1,0) NULL,
	anonimo numeric(1,0) NULL,
	etapa numeric(1,0) NULL,
	mensaje_correo varchar(4000) COLLATE Modern_Spanish_CI_AS NULL,
	encstas_x_encstado numeric(10,0) NULL,
	coment_x_pregunta numeric(10,0) NULL,
	asunto_correo text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_MB_MEDICION PRIMARY KEY (medicion_id),
	CONSTRAINT FK_MB_WEBADDRESS_MEDICION FOREIGN KEY (address_id) REFERENCES mb_webaddress(address_id) ON DELETE SET NULL
);
 CREATE NONCLUSTERED INDEX IE_MB_MEDICION_FECHA ON dbo.mb_medicion (  fecha ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_medicion_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_medicion_variable;

CREATE TABLE mb_medicion_variable (
	variable_id numeric(10,0) NOT NULL,
	medicion_id numeric(10,0) NOT NULL,
	apc numeric(10,0) NULL,
	numero_instrumentos_validos numeric(4,0) NULL,
	numero_instrumentos_novalidos numeric(4,0) NULL,
	padre_id numeric(10,0) NULL,
	variable_ref_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	nivel numeric(4,0) NULL,
	porcentaje float NULL,
	CONSTRAINT PK_MB_MEDICION_VARIABLE PRIMARY KEY (variable_id),
	CONSTRAINT FK_MB_MEDICION_VARIABLE FOREIGN KEY (medicion_id) REFERENCES mb_medicion(medicion_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_MED_VAR_MEDICION_ID ON dbo.mb_medicion_variable (  medicion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_orden_criterio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_orden_criterio;

CREATE TABLE mb_orden_criterio (
	medicion_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NOT NULL,
	CONSTRAINT PK_MB_ORDEN_CRITERIO PRIMARY KEY (medicion_id,orden),
	CONSTRAINT FK_MB_MEDICION_ORDEN_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion(medicion_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_organizacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_organizacion;

CREATE TABLE mb_organizacion (
	organizacion_id numeric(10,0) NOT NULL,
	medicion_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	organizacion_ref_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	porcentaje float NULL,
	porcentaje_propio float NULL,
	CONSTRAINT AK_MB_ORGANIZACION UNIQUE (padre_id,nombre),
	CONSTRAINT PK_MB_ORGANIZACION PRIMARY KEY (organizacion_id),
	CONSTRAINT FK_MB_MEDICION_ORGANIZACION FOREIGN KEY (medicion_id) REFERENCES mb_medicion(medicion_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_ORGANIZACION_PADRE_ID ON dbo.mb_organizacion (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_sector definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_sector;

CREATE TABLE mb_sector (
	sector_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	porcentaje float NULL,
	nodo_final numeric(1,0) NULL,
	definidos numeric(10,0) NULL,
	tamano_muestra numeric(10,0) NULL,
	codigo_enlace varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT AK_MB_SECTOR UNIQUE (padre_id,nombre),
	CONSTRAINT PK_MB_SECTOR PRIMARY KEY (sector_id),
	CONSTRAINT FK_MB_ORGANIZACION_SECTOR FOREIGN KEY (organizacion_id) REFERENCES mb_organizacion(organizacion_id)
);
 CREATE NONCLUSTERED INDEX IE_MB_SECTOR_PADRE_ID ON dbo.mb_sector (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_sector_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_sector_variable;

CREATE TABLE mb_sector_variable (
	sector_id numeric(10,0) NOT NULL,
	variable_id numeric(10,0) NOT NULL,
	apc numeric(10,0) NULL,
	numero_instrumentos_validos numeric(4,0) NULL,
	numero_instrumentos_novalidos numeric(4,0) NULL,
	estatus numeric(1,0) NULL,
	padre_id numeric(10,0) NULL,
	nivel numeric(4,0) NULL,
	variable_ref_id numeric(10,0) NULL,
	CONSTRAINT PK_MB_SECTOR_VARIABLE PRIMARY KEY (sector_id,variable_id),
	CONSTRAINT FK_MB_MEDICION_SECTOR_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable(variable_id) ON DELETE CASCADE,
	CONSTRAINT FK_MB_SECTOR_VARIABLE FOREIGN KEY (sector_id) REFERENCES mb_sector(sector_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.planes definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.planes;

CREATE TABLE planes (
	plan_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	plan_impacta_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	ano_inicial numeric(4,0) NOT NULL,
	ano_final numeric(4,0) NOT NULL,
	tipo numeric(1,0) NULL,
	activo numeric(1,0) NULL,
	revision numeric(2,0) NOT NULL,
	metodologia_id numeric(10,0) NOT NULL,
	clase_id numeric(10,0) NOT NULL,
	clase_id_indicadores_totales numeric(10,0) NULL,
	ind_total_imputacion_id numeric(10,0) NULL,
	ind_total_iniciativa_id numeric(10,0) NULL,
	ultima_medicion_anual float NULL,
	ultima_medicion_parcial float NULL,
	nl_ano_indicador_id numeric(10,0) NULL,
	nl_par_indicador_id numeric(10,0) NULL,
	fecha_ultima_medicion varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	crecimiento numeric(1,0) NULL,
	esquema varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_planes PRIMARY KEY (plan_id),
	CONSTRAINT FK_clase_plan FOREIGN KEY (clase_id) REFERENCES clase(clase_id),
	CONSTRAINT FK_clasetotales_plan FOREIGN KEY (clase_id_indicadores_totales) REFERENCES clase(clase_id),
	CONSTRAINT FK_metodologia_plan FOREIGN KEY (metodologia_id) REFERENCES metodologia(metodologia_id) ON DELETE CASCADE,
	CONSTRAINT FK_organizacion_plan FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id),
	CONSTRAINT FK_padre_planes FOREIGN KEY (padre_id) REFERENCES planes(plan_id),
	CONSTRAINT FK_planImpacta_plan FOREIGN KEY (plan_impacta_id) REFERENCES planes(plan_id)
);
 CREATE NONCLUSTERED INDEX IE_clase_plan ON dbo.planes (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_clasetotales_plan ON dbo.planes (  clase_id_indicadores_totales ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_impacta_plan ON dbo.planes (  plan_impacta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_metodologia_plan ON dbo.planes (  metodologia_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_organizacion_plan ON dbo.planes (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_padre_plan ON dbo.planes (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.portafolio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.portafolio;

CREATE TABLE portafolio (
	organizacion_id numeric(10,0) NOT NULL,
	id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	activo numeric(1,0) NOT NULL,
	porcentaje_completado float NULL,
	estatusid numeric(10,0) NOT NULL,
	frecuencia numeric(1,0) NOT NULL,
	clase_id numeric(10,0) NOT NULL,
	fecha_ultimo_calculo varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT AK_Portafolio_Nombre UNIQUE (organizacion_id,nombre),
	CONSTRAINT PK_PortafolioId PRIMARY KEY (id),
	CONSTRAINT FK_ORG_PORTAFOLIO FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE,
	CONSTRAINT FK_clase_portafolio FOREIGN KEY (clase_id) REFERENCES clase(clase_id) ON DELETE CASCADE,
	CONSTRAINT FK_estatus_portafolio FOREIGN KEY (estatusid) REFERENCES iniciativa_estatus(id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_Portafolio ON dbo.portafolio (  organizacion_id ASC  , nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_Portafolio_OrganizacionId ON dbo.portafolio (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_portafolio_clase ON dbo.portafolio (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_portafolio_estatus ON dbo.portafolio (  estatusid ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX PK_Portafolio ON dbo.portafolio (  id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.proceso_caracterizaciones definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.proceso_caracterizaciones;

CREATE TABLE proceso_caracterizaciones (
	caraceterizacion_id bigint IDENTITY(1,1) NOT NULL,
	proceso_id bigint NULL,
	procedimiento_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	procedimiento_objetivo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	procedimiento_codigo varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__proceso___38C71339160E2DC9 PRIMARY KEY (caraceterizacion_id),
	CONSTRAINT FK__proceso_c__proce__2E3BD7D3 FOREIGN KEY (proceso_id) REFERENCES procesos(proceso_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Strategos2022.dbo.proceso_documentos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.proceso_documentos;

CREATE TABLE proceso_documentos (
	procedimiento_documento_id bigint IDENTITY(1,1) NOT NULL,
	caraceterizacion_id bigint NULL,
	procedimiento_documento_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	procedimiento_documento_descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	procedimiento_documento_codigo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	procedimiento_documento_arch varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK__proceso___EB48AD571DDB714E PRIMARY KEY (procedimiento_documento_id),
	CONSTRAINT FK__proceso_d__carac__3118447E FOREIGN KEY (caraceterizacion_id) REFERENCES proceso_caracterizaciones(caraceterizacion_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Strategos2022.dbo.productos_servicios definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.productos_servicios;

CREATE TABLE productos_servicios (
	producto_servicio_id bigint IDENTITY(1,1) NOT NULL,
	proceso_id bigint NULL,
	producto_servicio_codigo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	producto_servicio_nombre varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	producto_caracteristicas varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__producto__FA3CEA20509BF264 PRIMARY KEY (producto_servicio_id),
	CONSTRAINT FK__productos__proce__2B5F6B28 FOREIGN KEY (proceso_id) REFERENCES procesos(proceso_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Strategos2022.dbo.pry_calendario definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pry_calendario;

CREATE TABLE pry_calendario (
	calendario_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	dom numeric(1,0) NULL,
	lun numeric(1,0) NULL,
	mar numeric(1,0) NULL,
	mie numeric(1,0) NULL,
	jue numeric(1,0) NULL,
	vie numeric(1,0) NULL,
	sab numeric(1,0) NULL,
	proyecto_id numeric(10,0) NULL,
	CONSTRAINT ak1_pry_calendario UNIQUE (proyecto_id,nombre),
	CONSTRAINT pk_pry_calendario PRIMARY KEY (calendario_id),
	CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto(proyecto_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX xif1pry_calendario ON dbo.pry_calendario (  proyecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.reporte definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.reporte;

CREATE TABLE reporte (
	reporte_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	configuracion text COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(1000) COLLATE Modern_Spanish_CI_AS NULL,
	publico numeric(1,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	corte numeric(1,0) NOT NULL,
	usuario_id numeric(10,0) NOT NULL,
	CONSTRAINT ak_reporte UNIQUE (organizacion_id,nombre),
	CONSTRAINT pk_reporte PRIMARY KEY (reporte_id),
	CONSTRAINT FK_usuario_reporte FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE,
	CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_usuario_reporte ON dbo.reporte (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.reporte_grafico definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.reporte_grafico;

CREATE TABLE reporte_grafico (
	reporte_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	configuracion text COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(1000) COLLATE Modern_Spanish_CI_AS NULL,
	publico numeric(1,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	fecha datetime NULL,
	indicadores varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	organizaciones varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	usuario_id numeric(10,0) NOT NULL,
	periodo_ini varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	periodo_fin varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	tiempo varchar(MAX) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT ak_reporte_grafico UNIQUE (nombre),
	CONSTRAINT pk_reporte_grafico PRIMARY KEY (reporte_id),
	CONSTRAINT FK_usuario_reporte_grafico FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IE_usuario_reporte ON dbo.reporte_grafico (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.responsable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.responsable;

CREATE TABLE responsable (
	responsable_id numeric(10,0) NOT NULL,
	usuario_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	cargo varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	ubicacion varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	notas text COLLATE Modern_Spanish_CI_AS NULL,
	children_count numeric(1,0) NULL,
	tipo numeric(1,0) NOT NULL,
	grupo numeric(1,0) NOT NULL,
	organizacion_id numeric(10,0) NULL,
	CONSTRAINT akc_responsable UNIQUE (organizacion_id,nombre,cargo),
	CONSTRAINT pk_responsable PRIMARY KEY (responsable_id),
	CONSTRAINT FK_organizacion_responsable FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE,
	CONSTRAINT FK_usuario_responsable FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) ON DELETE SET NULL
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_responsable ON dbo.responsable (  organizacion_id ASC  , nombre ASC  , cargo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX if_responsable_usu_id ON dbo.responsable (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX xif2responsable ON dbo.responsable (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.vista definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.vista;

CREATE TABLE vista (
	vista_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NOT NULL,
	nombre varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	descripcion text COLLATE Modern_Spanish_CI_AS NULL,
	visible numeric(1,0) NULL,
	fecha_inicio varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_fin varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT ak_vista_nombre UNIQUE (organizacion_id,nombre),
	CONSTRAINT pk_vista PRIMARY KEY (vista_id),
	CONSTRAINT FK_organizacion_vista FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_vista ON dbo.vista (  organizacion_id ASC  , nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ie_vista ON dbo.vista (  vista_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_vista_organizacion ON dbo.vista (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.declaracion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.declaracion_riesgos;

CREATE TABLE declaracion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	proceso_id bigint NOT NULL,
	ejercicio_riesgo_id bigint NOT NULL,
	tipo_riesgo_id bigint NOT NULL,
	respuesta_riesgo_id bigint NOT NULL,
	estatus_riesgo_id bigint NOT NULL,
	factor_riesgo varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	efectividad_controles varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	probabilidad varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	historico bit NOT NULL,
	impacto varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	severidad varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	riesgo_residual varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fecha_creacion bigint NOT NULL,
	fecha_actualizacion bigint NOT NULL,
	CONSTRAINT PK__declarac__3213E83F5C187C90 PRIMARY KEY (id),
	CONSTRAINT FK__declaraci__ejerc__49E3F248 FOREIGN KEY (ejercicio_riesgo_id) REFERENCES ejercicios_evaluacion_riesgos(id),
	CONSTRAINT FK__declaraci__estat__4CC05EF3 FOREIGN KEY (estatus_riesgo_id) REFERENCES estatus_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__declaraci__proce__48EFCE0F FOREIGN KEY (proceso_id) REFERENCES procesos(proceso_id) ON UPDATE CASCADE,
	CONSTRAINT FK__declaraci__respu__4BCC3ABA FOREIGN KEY (respuesta_riesgo_id) REFERENCES respuestas_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__declaraci__tipo___4AD81681 FOREIGN KEY (tipo_riesgo_id) REFERENCES tipo_riesgos(id) ON UPDATE CASCADE
);


-- Strategos2022.dbo.efectos_declaracion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.efectos_declaracion_riesgos;

CREATE TABLE efectos_declaracion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	impacto_riesgos_id bigint NULL,
	declaracion_riesgo_id bigint NULL,
	impacto varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__efectos___3213E83F46796F8F PRIMARY KEY (id),
	CONSTRAINT FK__efectos_d__decla__5555A4F4 FOREIGN KEY (declaracion_riesgo_id) REFERENCES declaracion_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__efectos_d__impac__546180BB FOREIGN KEY (impacto_riesgos_id) REFERENCES impacto_riesgos(id) ON UPDATE CASCADE
);


-- Strategos2022.dbo.indicador definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador;

CREATE TABLE indicador (
	indicador_id numeric(10,0) NOT NULL,
	alerta_n2_ind_id numeric(10,0) NULL,
	alerta_n1_ind_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NULL,
	clase_id numeric(10,0) NULL,
	nombre varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	naturaleza numeric(1,0) NOT NULL,
	frecuencia numeric(1,0) NOT NULL,
	guia varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	unidad_id numeric(10,0) NULL,
	codigo_enlace varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_corto varchar(150) COLLATE Modern_Spanish_CI_AS NOT NULL,
	prioridad numeric(1,0) NOT NULL,
	constante numeric(1,0) NOT NULL,
	fuente varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	orden varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	read_only numeric(1,0) NULL,
	caracteristica numeric(1,0) NULL,
	crecimiento_ind numeric(1,0) NULL,
	fecha_ultima_medicion varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	ultima_medicion float NULL,
	resp_fijar_meta_id numeric(10,0) NULL,
	resp_lograr_meta_id numeric(10,0) NULL,
	resp_seguimiento_id numeric(10,0) NULL,
	resp_cargar_meta_id numeric(10,0) NULL,
	resp_cargar_ejecutado_id numeric(10,0) NULL,
	lag_lead numeric(1,0) NULL,
	corte numeric(1,0) NULL,
	enlace_parcial varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	alerta_min_max numeric(2,0) NULL,
	alerta_meta_n1 float NULL,
	alerta_meta_n2 float NULL,
	intranet numeric(1,0) NULL,
	alerta_n1_tipo numeric(1,0) NULL,
	alerta_n2_tipo numeric(1,0) NULL,
	alerta_n1_fv numeric(1,0) NULL,
	alerta_n2_fv numeric(1,0) NULL,
	valor_inicial_cero numeric(1,0) NULL,
	mascara_decimales numeric(2,0) NULL,
	tipo_medicion numeric(1,0) NULL,
	url varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_bloqueo_ejecutado datetime NULL,
	fecha_bloqueo_meta datetime NULL,
	psuperior_id numeric(10,0) NULL,
	pinferior_id numeric(10,0) NULL,
	psuperior_vf float NULL,
	pinferior_vf float NULL,
	multidimensional numeric(1,0) NULL,
	tipo numeric(1,0) NULL,
	tipo_asociado numeric(1,0) NULL,
	asociado_id numeric(10,0) NULL,
	revision numeric(3,0) NULL,
	ultimo_programado float NULL,
	tipo_suma numeric(1,0) NULL,
	asignar_Inventario numeric(1,0) NULL,
	resp_notificacion_id numeric(10,0) NULL,
	CONSTRAINT pk_indicador PRIMARY KEY (indicador_id),
	CONSTRAINT pk_indicador_clase_nombre UNIQUE (clase_id,nombre),
	CONSTRAINT fk_clase_indicador FOREIGN KEY (clase_id) REFERENCES clase(clase_id),
	CONSTRAINT fk_indicador_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id),
	CONSTRAINT fk_indicador_unidad FOREIGN KEY (unidad_id) REFERENCES unidad(unidad_id) ON DELETE SET NULL,
	CONSTRAINT fk_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable(responsable_id) ON DELETE SET NULL,
	CONSTRAINT fk_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_respnotificacion_indicador FOREIGN KEY (resp_notificacion_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable(responsable_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador11052_11051 ON dbo.indicador (  organizacion_id ASC  , nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador11267_11266 ON dbo.indicador (  organizacion_id ASC  , frecuencia ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , caracteristica , clase_id , codigo_enlace , corte , indicador_id , naturaleza , nombre , pinferior_id , pinferior_vf , psuperior_id , psuperior_vf , tipo , tipo_medicion , tipo_suma , unidad_id , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador11269_11268 ON dbo.indicador (  frecuencia ASC  , naturaleza ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , caracteristica , clase_id , codigo_enlace , corte , indicador_id , nombre , organizacion_id , pinferior_id , pinferior_vf , psuperior_id , psuperior_vf , tipo , tipo_medicion , tipo_suma , unidad_id , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador24865_24864 ON dbo.indicador (  organizacion_id ASC  )  
	 INCLUDE ( codigo_enlace , indicador_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador2890_2889 ON dbo.indicador (  organizacion_id ASC  , codigo_enlace ASC  )  
	 INCLUDE ( indicador_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador32993_32992 ON dbo.indicador (  organizacion_id ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , asociado_id , caracteristica , clase_id , codigo_enlace , constante , corte , crecimiento_ind , descripcion , enlace_parcial , fecha_bloqueo_ejecutado , fecha_bloqueo_meta , fecha_ultima_medicion , frecuencia , fuente , guia , indicador_id , intranet , lag_lead , mascara_decimales , multidimensional , naturaleza , nombre , nombre_corto , orden , pinferior_id , pinferior_vf , prioridad , psuperior_id , psuperior_vf , read_only , resp_cargar_ejecutado_id , resp_cargar_meta_id , resp_fijar_meta_id , resp_lograr_meta_id , resp_notificacion_id , resp_seguimiento_id , revision , tipo , tipo_asociado , tipo_medicion , tipo_suma , ultima_medicion , ultimo_programado , unidad_id , url , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador7536_7535 ON dbo.indicador (  organizacion_id ASC  , codigo_enlace ASC  )  
	 INCLUDE ( clase_id , indicador_id , naturaleza , nombre ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador9335_9334 ON dbo.indicador (  organizacion_id ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , asociado_id , caracteristica , clase_id , codigo_enlace , constante , corte , crecimiento_ind , descripcion , enlace_parcial , fecha_bloqueo_ejecutado , fecha_bloqueo_meta , fecha_ultima_medicion , frecuencia , fuente , guia , indicador_id , lag_lead , mascara_decimales , multidimensional , naturaleza , nombre , nombre_corto , orden , pinferior_id , pinferior_vf , prioridad , psuperior_id , psuperior_vf , read_only , resp_cargar_ejecutado_id , resp_cargar_meta_id , resp_fijar_meta_id , resp_lograr_meta_id , resp_notificacion_id , resp_seguimiento_id , revision , tipo , tipo_asociado , tipo_medicion , tipo_suma , ultima_medicion , ultimo_programado , unidad_id , url , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador9348_9347 ON dbo.indicador (  organizacion_id ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , asociado_id , caracteristica , clase_id , codigo_enlace , constante , corte , crecimiento_ind , descripcion , enlace_parcial , fecha_bloqueo_ejecutado , fecha_bloqueo_meta , fecha_ultima_medicion , frecuencia , fuente , guia , indicador_id , lag_lead , mascara_decimales , multidimensional , naturaleza , nombre , nombre_corto , orden , pinferior_id , pinferior_vf , prioridad , psuperior_id , psuperior_vf , read_only , resp_cargar_ejecutado_id , resp_cargar_meta_id , resp_fijar_meta_id , resp_lograr_meta_id , resp_seguimiento_id , revision , tipo , tipo_asociado , tipo_medicion , tipo_suma , ultima_medicion , ultimo_programado , unidad_id , url , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador9355_9354 ON dbo.indicador (  organizacion_id ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , caracteristica , clase_id , codigo_enlace , corte , frecuencia , indicador_id , naturaleza , nombre , pinferior_id , pinferior_vf , psuperior_id , psuperior_vf , tipo , tipo_medicion , tipo_suma , unidad_id , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador9357_9356 ON dbo.indicador (  naturaleza ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , caracteristica , clase_id , codigo_enlace , corte , frecuencia , indicador_id , nombre , organizacion_id , pinferior_id , pinferior_vf , psuperior_id , psuperior_vf , tipo , tipo_medicion , tipo_suma , unidad_id , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador9360_9359 ON dbo.indicador (  organizacion_id ASC  , naturaleza ASC  )  
	 INCLUDE ( alerta_meta_n1 , alerta_meta_n2 , alerta_min_max , alerta_n1_fv , alerta_n1_ind_id , alerta_n1_tipo , alerta_n2_fv , alerta_n2_ind_id , alerta_n2_tipo , asignar_Inventario , caracteristica , clase_id , codigo_enlace , corte , frecuencia , indicador_id , nombre , pinferior_id , pinferior_vf , psuperior_id , psuperior_vf , tipo , tipo_medicion , tipo_suma , unidad_id , valor_inicial_cero ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_indicadorD22F8 ON dbo.indicador (  clase_id ASC  , frecuencia ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_indicador_20180504 ON dbo.indicador (  organizacion_id ASC  , clase_id ASC  , frecuencia ASC  , indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_indicador_organizacion_clase_20180504 ON dbo.indicador (  organizacion_id ASC  , clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_indicador ON dbo.indicador (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_indicador_clase_nombre ON dbo.indicador (  clase_id ASC  , nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_claseid ON dbo.indicador (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_organizacion ON dbo.indicador (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_rescargameta ON dbo.indicador (  resp_cargar_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_resejecutado ON dbo.indicador (  resp_cargar_ejecutado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_respfijarmeta ON dbo.indicador (  resp_fijar_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_resplograrmeta ON dbo.indicador (  resp_lograr_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_respnotificacion ON dbo.indicador (  resp_notificacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_resseguimiento ON dbo.indicador (  resp_seguimiento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_indicador_unidad ON dbo.indicador (  unidad_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_estado definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_estado;

CREATE TABLE indicador_estado (
	plan_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	estado float NULL,
	CONSTRAINT pk_indicador_estado PRIMARY KEY (plan_id,indicador_id,tipo,ano,periodo),
	CONSTRAINT FK_indicador_indicador_estado FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT FK_plan_indicador_estado FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_indicadorId_indicador_estad ON dbo.indicador_estado (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_planId_indicador_estado ON dbo.indicador_estado (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_instrumento definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_instrumento;

CREATE TABLE indicador_por_instrumento (
	instrumento_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT PK_indicador_por_instrumento PRIMARY KEY (instrumento_id,indicador_id),
	CONSTRAINT FK_ind_por_ins_InstrumentoId FOREIGN KEY (instrumento_id) REFERENCES instrumentos(instrumento_id) ON DELETE CASCADE,
	CONSTRAINT FK_ind_por_ins_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_indicador_por_instrumento ON dbo.indicador_por_instrumento (  instrumento_id ASC  , indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_ind_por_ins_indicadorId ON dbo.indicador_por_instrumento (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_ind_por_ins_instrumentoId ON dbo.indicador_por_instrumento (  instrumento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_plan definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_plan;

CREATE TABLE indicador_por_plan (
	plan_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	peso float NULL,
	crecimiento numeric(1,0) NULL,
	tipo_medicion numeric(1,0) NULL,
	CONSTRAINT pk_indicador_por_plan PRIMARY KEY (plan_id,indicador_id),
	CONSTRAINT FK_ind_indicador_plan FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT FK_plan_indicador_plan FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_indicador_por_plan505_504 ON dbo.indicador_por_plan (  plan_id ASC  )  
	 INCLUDE ( crecimiento , indicador_id , peso , tipo_medicion ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IF_indicadorId_indicador_por_p ON dbo.indicador_por_plan (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IF_planId_indicador_por_plan ON dbo.indicador_por_plan (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_portafolio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_portafolio;

CREATE TABLE indicador_por_portafolio (
	indicador_id numeric(10,0) NOT NULL,
	portafolioId numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT PK_indicador_por_portafolio PRIMARY KEY (indicador_id,portafolioId),
	CONSTRAINT FK_indicador_indXpor FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT FK_portafolio_indXpor FOREIGN KEY (portafolioId) REFERENCES portafolio(id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_indicador_por_portafolio ON dbo.indicador_por_portafolio (  indicador_id ASC  , portafolioId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_indicador_por_portafolio_in ON dbo.indicador_por_portafolio (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_indicador_por_portafolio_po ON dbo.indicador_por_portafolio (  portafolioId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa;

CREATE TABLE iniciativa (
	iniciativa_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NULL,
	nombre varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	nombre_largo varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	alerta_za float NULL,
	alerta_zv float NULL,
	frecuencia numeric(2,0) NOT NULL,
	tipo_alerta numeric(2,0) NOT NULL,
	contratista varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	naturaleza numeric(1,0) NOT NULL,
	resp_fijar_meta_id numeric(10,0) NULL,
	resp_lograr_meta_id numeric(10,0) NULL,
	resp_seguimiento_id numeric(10,0) NULL,
	resp_cargar_meta_id numeric(10,0) NULL,
	resp_cargar_ejecutado_id numeric(10,0) NULL,
	proyecto_id numeric(10,0) NULL,
	visible numeric(1,0) NULL,
	clase_id numeric(10,0) NULL,
	read_only numeric(1,0) NULL,
	porcentaje_completado float NULL,
	crecimiento numeric(1,0) NULL,
	fecha_ultima_medicion varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	tipo_medicion numeric(1,0) NOT NULL,
	historico_date datetime NULL,
	estatusId numeric(10,0) NOT NULL,
	anio_form_proy varchar(4) COLLATE Modern_Spanish_CI_AS NULL,
	tipoId numeric(10,0) NULL,
	responsable_proyecto varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	cargo_responsable varchar(200) COLLATE Modern_Spanish_CI_AS NULL,
	organizaciones_involucradas varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	objetivo_estrategico varchar(1024) COLLATE Modern_Spanish_CI_AS NULL,
	fuente_financiacion varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	monto_financiamiento varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	iniciativa_estrategica varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	lider_iniciativa varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	tipo_iniciativa varchar(250) COLLATE Modern_Spanish_CI_AS NULL,
	poblacion_beneficiada varchar(1024) COLLATE Modern_Spanish_CI_AS NULL,
	contexto varchar(1024) COLLATE Modern_Spanish_CI_AS NULL,
	definicion_problema varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	alcance varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	objetivo_general varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	objetivos_especificos varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	cargo_id numeric(10,0) NULL,
	codigo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	unidad_medida numeric(10,0) NULL,
	justificacion varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_inicio datetime NULL,
	fecha_fin datetime NULL,
	monto_total varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	monto_moneda_extr varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	sit_presupuestaria varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	hitos_relevantes varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	sector varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	fecha_acta_inicio datetime NULL,
	gerencia_general_resp varchar(150) COLLATE Modern_Spanish_CI_AS NULL,
	codigo_sipe varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	proyecto_presup_asociado varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	estado varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	municipio varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	parroquia varchar(300) COLLATE Modern_Spanish_CI_AS NULL,
	direccion_proyecto varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	obj_historico varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	obj_nacional varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	obj_estrategico_v varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	obj_general_v varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	obj_especifico varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	programa varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	problemas varchar(750) COLLATE Modern_Spanish_CI_AS NULL,
	causas varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	lineas_estrategicas varchar(500) COLLATE Modern_Spanish_CI_AS NULL,
	gerente_proy_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	gerente_proy_cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	gerente_proy_email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	gerente_proy_telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_tecnico_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_tecnico_cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	resp_tecnico_email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_tecnico_telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_registrador_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_registrador_cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	resp_registrador_email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_registrador_telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_administrativo_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_administrativo_cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	resp_administrativo_email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_administrativo_telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_admin_contratos_nombre varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_admin_contratos_cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	resp_admin_contratos_email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	resp_admin_contratos_telefono varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	fase_id numeric(10,0) NULL,
	CONSTRAINT ak1_iniciativa UNIQUE (organizacion_id,nombre),
	CONSTRAINT pk_iniciativa PRIMARY KEY (iniciativa_id),
	CONSTRAINT PK_Iniciativa_EstatusId FOREIGN KEY (estatusId) REFERENCES iniciativa_estatus(id) ON DELETE CASCADE,
	CONSTRAINT fk_cargar_ejecutado FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_clase_iniciativa FOREIGN KEY (clase_id) REFERENCES clase(clase_id) ON DELETE CASCADE,
	CONSTRAINT fk_organizacion_iniciativa FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE,
	CONSTRAINT fk_proyecto_iniciativa FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto(proyecto_id) ON DELETE SET NULL,
	CONSTRAINT fk_resp_cargar_meta FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_resp_fijar_meta FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_resp_lograr_meta FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable(responsable_id),
	CONSTRAINT fk_resp_seguimiento FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable(responsable_id),
	CONSTRAINT pk_proyecto_id FOREIGN KEY (tipoId) REFERENCES tipo_proyecto(tipo_proyecto_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_iniciativa48238_48237 ON dbo.iniciativa (  organizacion_id ASC  , historico_date ASC  , tipoId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_iniciativa5466_5465 ON dbo.iniciativa (  organizacion_id ASC  , historico_date ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_clase ON dbo.iniciativa (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_estatusId ON dbo.iniciativa (  estatusId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_organizacionid ON dbo.iniciativa (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_proyecto ON dbo.iniciativa (  proyecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_resp_cargar ON dbo.iniciativa (  resp_cargar_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_resp_ejecutar ON dbo.iniciativa (  resp_cargar_ejecutado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_resp_lograr ON dbo.iniciativa (  resp_lograr_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_resp_meta ON dbo.iniciativa (  resp_fijar_meta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_resp_seguimiento ON dbo.iniciativa (  resp_seguimiento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX indx_tipo_proyecto ON dbo.iniciativa (  tipoId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX iniciativa50125_50124 ON dbo.iniciativa (  organizacion_id ASC  , frecuencia ASC  , historico_date ASC  , iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa_ano definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa_ano;

CREATE TABLE iniciativa_ano (
	iniciativa_id numeric(10,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	resultado text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_iniciativa_ano PRIMARY KEY (iniciativa_id,ano),
	CONSTRAINT fk_iniciativa_ano FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_iniciativaId_ano ON dbo.iniciativa_ano (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa_objeto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa_objeto;

CREATE TABLE iniciativa_objeto (
	iniciativa_id numeric(10,0) NOT NULL,
	objeto varchar(1500) COLLATE Modern_Spanish_CI_AS NULL,
	resultado text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_iniciativa_objeto PRIMARY KEY (iniciativa_id),
	CONSTRAINT fK_iniciativa_objeto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX IE_inicia_objeto_iniciativaid ON dbo.iniciativa_objeto (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa_plan definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa_plan;

CREATE TABLE iniciativa_plan (
	iniciativa_id numeric(10,0) NOT NULL,
	plan_id numeric(10,0) NOT NULL,
	CONSTRAINT PK_iniciativa_plan PRIMARY KEY (iniciativa_id,plan_id),
	CONSTRAINT FK_iniciativa_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE,
	CONSTRAINT FK_plan_iniciaitva_plan FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_iniciativa_plan ON dbo.iniciativa_plan (  iniciativa_id ASC  , plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_iniciativa_iniciativa_plan ON dbo.iniciativa_plan (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_plan_iniciativa_plan ON dbo.iniciativa_plan (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.instrumento_peso definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.instrumento_peso;

CREATE TABLE instrumento_peso (
	instrumento_id numeric(10,0) NOT NULL,
	anio varchar(4) COLLATE Modern_Spanish_CI_AS NOT NULL,
	peso float NULL,
	CONSTRAINT pk_instrumento_peso PRIMARY KEY (instrumento_id),
	CONSTRAINT fk_instrumento_peso FOREIGN KEY (instrumento_id) REFERENCES instrumentos(instrumento_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_instrumento_peso ON dbo.instrumento_peso (  instrumento_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_atributo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_atributo;

CREATE TABLE mb_atributo (
	atributo_id numeric(10,0) NOT NULL,
	sector_id numeric(10,0) NOT NULL,
	variable_id numeric(10,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	pregunta varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	prioridad numeric(2,0) NULL,
	orden numeric(2,0) NOT NULL,
	indicador_id numeric(10,0) NULL,
	apc numeric(10,0) NULL,
	CONSTRAINT AK_MB_ATRIBUTO UNIQUE (sector_id,nombre),
	CONSTRAINT PK_MB_ATRIBUTO PRIMARY KEY (atributo_id),
	CONSTRAINT FK_MB_MED_VARIABLE_ATRIBUTO FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable(variable_id) ON DELETE CASCADE,
	CONSTRAINT FK_MB_SECTOR_ATRIBUTO FOREIGN KEY (sector_id) REFERENCES mb_sector(sector_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_ATRIBUTO_SECTOR_ID ON dbo.mb_atributo (  sector_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_criterio_estratificacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_criterio_estratificacion;

CREATE TABLE mb_criterio_estratificacion (
	criterio_id numeric(10,0) NOT NULL,
	medicion_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NOT NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	pregunta varchar(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT AK_MB_CRITERIO_ESTRATIFICACION UNIQUE (medicion_id,nombre),
	CONSTRAINT PK_MB_CRITERIO_ESTRATIFICACION PRIMARY KEY (criterio_id),
	CONSTRAINT FK_MB_MEDICION_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion(medicion_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_encuestado definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_encuestado;

CREATE TABLE mb_encuestado (
	encuestado_id numeric(10,0) NOT NULL,
	sector_id numeric(10,0) NOT NULL,
	persona_id numeric(10,0) NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	cedula varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	cargo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	email varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	estado numeric(2,0) NULL,
	numero_encuesta numeric(10,0) NULL,
	encuesta_id numeric(10,0) NULL,
	notas varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	web_address varchar(20) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_MB_ENCUESTADO PRIMARY KEY (encuestado_id),
	CONSTRAINT FK_MB_SECTOR_ENCUESTADO FOREIGN KEY (sector_id) REFERENCES mb_sector(sector_id)
);


-- Strategos2022.dbo.mb_formato_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_formato_variable;

CREATE TABLE mb_formato_variable (
	variable_id numeric(10,0) NOT NULL,
	texto_ayuda varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	mostrar_ayuda numeric(1,0) NULL,
	preenunciado varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	mostrar_pre numeric(1,0) NULL,
	postenunciado varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	mostrar_post numeric(1,0) NULL,
	mostrar_variable numeric(1,0) NULL,
	CONSTRAINT PK_MB_FORMATO_VARIABLE PRIMARY KEY (variable_id),
	CONSTRAINT FK_MB_MEDICION_VARIABLE_FORMAT FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable(variable_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_numero_encuesta definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_numero_encuesta;

CREATE TABLE mb_numero_encuesta (
	sector_id numeric(10,0) NOT NULL,
	numero numeric(10,0) NOT NULL,
	CONSTRAINT PK_MB_NUMERO_ENCUESTA PRIMARY KEY (sector_id,numero),
	CONSTRAINT FK_MB_SECTOR_NUMERO_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector(sector_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_organizacion_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_organizacion_variable;

CREATE TABLE mb_organizacion_variable (
	variable_id numeric(10,0) NOT NULL,
	apc_propio numeric(10,0) NULL,
	apc numeric(10,0) NULL,
	numero_instrumentos_validos numeric(4,0) NULL,
	numero_instrumentos_novalidos numeric(4,0) NULL,
	indicador_id numeric(10,0) NULL,
	padre_id numeric(10,0) NULL,
	nivel numeric(4,0) NULL,
	variable_red_id numeric(10,0) NULL,
	organizacion_id numeric(10,0) NOT NULL,
	codigo_enlace varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT AK_MB_ORGANIZACION_VARIABLE PRIMARY KEY (variable_id,organizacion_id),
	CONSTRAINT FK_MB_MEDICION_ORG_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable(variable_id) ON DELETE CASCADE,
	CONSTRAINT FK_mdorgvariable_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT FK_organizacion_variable FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_VARIABLE_ORGANIZACIONID ON dbo.mb_organizacion_variable (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_mborgvariable_indicadorId ON dbo.mb_organizacion_variable (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.medicion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.medicion;

CREATE TABLE medicion (
	serie_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	valor float NULL,
	protegido numeric(1,0) NULL,
	CONSTRAINT pk_medicion PRIMARY KEY (serie_id,indicador_id,ano,periodo),
	CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion11086_11085 ON dbo.medicion (  indicador_id ASC  )  
	 INCLUDE ( ano , periodo , protegido , serie_id , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion11252_11251 ON dbo.medicion (  serie_id ASC  , indicador_id ASC  )  
	 INCLUDE ( ano , periodo , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion11271_11270 ON dbo.medicion (  serie_id ASC  , indicador_id ASC  )  
	 INCLUDE ( ano , periodo , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion11633_11632 ON dbo.medicion (  serie_id ASC  )  
	 INCLUDE ( ano , indicador_id , periodo , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion14183_14182 ON dbo.medicion (  indicador_id ASC  )  
	 INCLUDE ( ano , periodo , protegido ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion24871_24870 ON dbo.medicion (  ano ASC  , periodo ASC  )  
	 INCLUDE ( indicador_id , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion46409_46408 ON dbo.medicion (  serie_id ASC  , indicador_id ASC  , ano ASC  )  
	 INCLUDE ( periodo , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_medicion47110_47109 ON dbo.medicion (  serie_id ASC  , indicador_id ASC  , ano ASC  )  
	 INCLUDE ( periodo , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IF_medicion_indicador ON dbo.medicion (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_medicion_ano_periodo ON dbo.medicion (  ano ASC  , periodo ASC  )  
	 INCLUDE ( indicador_id , protegido , serie_id , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_medicion_serie_id_indicador_id ON dbo.medicion (  serie_id ASC  , indicador_id ASC  )  
	 INCLUDE ( ano , periodo , protegido , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IX_medicion_serie_id_indicador_id_ano ON dbo.medicion (  serie_id ASC  , indicador_id ASC  , ano ASC  )  
	 INCLUDE ( periodo , protegido , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_medicion_ano_20180504 ON dbo.medicion (  ano ASC  )  
	 INCLUDE ( indicador_id , periodo , protegido , serie_id , valor ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_medicion_ano_periodo_20180504 ON dbo.medicion (  ano ASC  , periodo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.meta definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.meta;

CREATE TABLE meta (
	plan_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	valor float NULL,
	serie_id numeric(10,0) NOT NULL,
	protegido numeric(1,0) NULL,
	CONSTRAINT pk_meta PRIMARY KEY (plan_id,indicador_id,tipo,ano,periodo,serie_id),
	CONSTRAINT fk_plan_meta FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IF_meta_plan ON dbo.meta (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_metaC47F7 ON dbo.meta (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_meta_20180504 ON dbo.meta (  ano ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_meta_ano_periodo_20180504 ON dbo.meta (  ano ASC  , periodo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX JESB_IX1_meta_id_ano_20180504 ON dbo.meta (  indicador_id ASC  , ano ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.modelo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.modelo;

CREATE TABLE modelo (
	modelo_id numeric(10,0) NOT NULL,
	plan_id numeric(10,0) NOT NULL,
	nombre varchar(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	binario text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_id_plan_modelo PRIMARY KEY (modelo_id,plan_id),
	CONSTRAINT FK_planes_modelo FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_plan_modelo ON dbo.modelo (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_modelo ON dbo.modelo (  modelo_id ASC  , plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.pagina definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.pagina;

CREATE TABLE pagina (
	pagina_id numeric(10,0) NOT NULL,
	vista_id numeric(10,0) NULL,
	portafolioId numeric(10,0) NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	filas numeric(2,0) NOT NULL,
	columnas numeric(2,0) NOT NULL,
	ancho numeric(4,0) NOT NULL,
	alto numeric(4,0) NOT NULL,
	numero numeric(4,0) NULL,
	CONSTRAINT pk_pagina PRIMARY KEY (pagina_id),
	CONSTRAINT FK_portafolio_vista FOREIGN KEY (portafolioId) REFERENCES portafolio(id) ON DELETE CASCADE,
	CONSTRAINT FK_vista_pagina FOREIGN KEY (vista_id) REFERENCES vista(vista_id)
);
 CREATE  UNIQUE NONCLUSTERED INDEX ie_pagina ON dbo.pagina (  pagina_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_pagina_portafolio ON dbo.pagina (  portafolioId ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_pagina_vista ON dbo.pagina (  vista_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.perspectiva definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.perspectiva;

CREATE TABLE perspectiva (
	perspectiva_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	plan_id numeric(10,0) NOT NULL,
	responsable_id numeric(10,0) NULL,
	nombre varchar(250) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	orden numeric(2,0) NULL,
	frecuencia numeric(1,0) NULL,
	titulo varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	tipo numeric(1,0) NOT NULL,
	fecha_ultima_medicion varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	ultima_medicion_anual float NULL,
	ultima_medicion_parcial float NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	crecimiento_parcial numeric(1,0) NULL,
	crecimiento_anual numeric(1,0) NULL,
	ano numeric(10,0) NULL,
	clase_id numeric(10,0) NULL,
	tipo_calculo numeric(1,0) NULL,
	nl_par_indicador_id numeric(10,0) NULL,
	nl_ano_indicador_id numeric(10,0) NULL,
	CONSTRAINT ak1_perspectiva UNIQUE (plan_id,padre_id,nombre,ano),
	CONSTRAINT pk_perspectiva PRIMARY KEY (perspectiva_id),
	CONSTRAINT fk_perspectiva_clase FOREIGN KEY (clase_id) REFERENCES clase(clase_id),
	CONSTRAINT fk_perspectiva_creado FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT fk_perspectiva_logroanual FOREIGN KEY (nl_ano_indicador_id) REFERENCES indicador(indicador_id),
	CONSTRAINT fk_perspectiva_logropacial FOREIGN KEY (nl_par_indicador_id) REFERENCES indicador(indicador_id),
	CONSTRAINT fk_perspectiva_modificado FOREIGN KEY (modificado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT fk_perspectiva_padre FOREIGN KEY (padre_id) REFERENCES perspectiva(perspectiva_id),
	CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES planes(plan_id) ON DELETE CASCADE,
	CONSTRAINT fk_perspectiva_responsable FOREIGN KEY (responsable_id) REFERENCES responsable(responsable_id)
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_perspectiva11451_11450 ON dbo.perspectiva (  padre_id ASC  , plan_id ASC  , ano ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_perspectiva7835_7834 ON dbo.perspectiva (  nombre ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_perspectiva7886_7885 ON dbo.perspectiva (  nombre ASC  )  
	 INCLUDE ( padre_id , perspectiva_id , plan_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_clase ON dbo.perspectiva (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_creado ON dbo.perspectiva (  creado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_logroanual ON dbo.perspectiva (  nl_ano_indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_logropacial ON dbo.perspectiva (  nl_par_indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_modificado ON dbo.perspectiva (  modificado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_padre ON dbo.perspectiva (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_plan ON dbo.perspectiva (  plan_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_responsable ON dbo.perspectiva (  responsable_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.perspectiva_nivel definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.perspectiva_nivel;

CREATE TABLE perspectiva_nivel (
	perspectiva_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	nivel float NULL,
	meta float NULL,
	alerta numeric(1,0) NULL,
	CONSTRAINT pk_perspectiva_estado PRIMARY KEY (perspectiva_id,tipo,ano,periodo),
	CONSTRAINT FK_perspectiva_perspectiva_niv FOREIGN KEY (perspectiva_id) REFERENCES perspectiva(perspectiva_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IDX_Strategos2018_dbo_perspectiva_nivel11309_11308 ON dbo.perspectiva_nivel (  tipo ASC  , ano ASC  )  
	 INCLUDE ( nivel , periodo , perspectiva_id ) 
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_perspectiva_nivel ON dbo.perspectiva_nivel (  perspectiva_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_perspectiva_estado ON dbo.perspectiva_nivel (  perspectiva_id ASC  , tipo ASC  , ano ASC  , periodo ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.perspectiva_relacion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.perspectiva_relacion;

CREATE TABLE perspectiva_relacion (
	perspectiva_id numeric(10,0) NOT NULL,
	relacion_id numeric(10,0) NOT NULL,
	CONSTRAINT PK_perspectiva_relacion PRIMARY KEY (perspectiva_id,relacion_id),
	CONSTRAINT FK_relacion_perspectiva FOREIGN KEY (perspectiva_id) REFERENCES perspectiva(perspectiva_id) ON DELETE CASCADE,
	CONSTRAINT FK_relacion_relacion FOREIGN KEY (relacion_id) REFERENCES perspectiva(perspectiva_id)
);
 CREATE NONCLUSTERED INDEX IE_relacion_perspectiva ON dbo.perspectiva_relacion (  perspectiva_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_relacion_relacion ON dbo.perspectiva_relacion (  relacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.portafolio_iniciativa definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.portafolio_iniciativa;

CREATE TABLE portafolio_iniciativa (
	portafolio_id numeric(10,0) NOT NULL,
	iniciativa_id numeric(10,0) NOT NULL,
	peso float NULL,
	CONSTRAINT PK_portafolio_iniciativa_portId PRIMARY KEY (portafolio_id,iniciativa_id),
	CONSTRAINT FK_portafolio_iniciativa_iniId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id),
	CONSTRAINT FK_portafolio_portafolioId FOREIGN KEY (portafolio_id) REFERENCES portafolio(id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_portafolio_iniciativa_inici ON dbo.portafolio_iniciativa (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_portafolio_iniciativa_porta ON dbo.portafolio_iniciativa (  portafolio_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX PK_portafolio_iniciativa ON dbo.portafolio_iniciativa (  portafolio_id ASC  , iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.prd_producto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.prd_producto;

CREATE TABLE prd_producto (
	producto_id numeric(10,0) NOT NULL,
	iniciativa_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fecha_inicio datetime NULL,
	descripcion varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	responsable_id numeric(10,0) NULL,
	CONSTRAINT pk_prd_producto PRIMARY KEY (producto_id),
	CONSTRAINT fk_iniciativa_producto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_prd_producto_iniciativaid ON dbo.prd_producto (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.prd_seg_producto definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.prd_seg_producto;

CREATE TABLE prd_seg_producto (
	iniciativa_id numeric(10,0) NOT NULL,
	producto_id numeric(10,0) NOT NULL,
	ano numeric(4,0) NOT NULL,
	periodo numeric(3,0) NOT NULL,
	fecha_ini datetime NULL,
	fecha_fin datetime NULL,
	alerta numeric(1,0) NULL,
	CONSTRAINT pk_prd_seg_producto PRIMARY KEY (iniciativa_id,producto_id,ano,periodo),
	CONSTRAINT fk_iniciativa_pro_seg FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE,
	CONSTRAINT fk_producto_pro_seg FOREIGN KEY (producto_id) REFERENCES prd_producto(producto_id)
);
 CREATE NONCLUSTERED INDEX IE_prd_seg_producto_inicitiva ON dbo.prd_seg_producto (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_prd_seg_producto_producto ON dbo.prd_seg_producto (  producto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.problema definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.problema;

CREATE TABLE problema (
	problema_id numeric(10,0) NOT NULL,
	organizacion_id numeric(10,0) NOT NULL,
	indicador_efecto_id numeric(10,0) NULL,
	iniciativa_efecto_id numeric(10,0) NULL,
	fecha datetime NOT NULL,
	responsable_id numeric(10,0) NULL,
	auxiliar_id numeric(10,0) NULL,
	estado_id numeric(10,0) NULL,
	fecha_estimada_inicio datetime NULL,
	fecha_estimada_final datetime NULL,
	fecha_real_inicio datetime NULL,
	fecha_real_final datetime NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	read_only numeric(1,0) NULL,
	indicador_id numeric(10,0) NULL,
	iniciativa_id numeric(10,0) NULL,
	clase_id numeric(10,0) NULL,
	CONSTRAINT pk_problema PRIMARY KEY (problema_id),
	CONSTRAINT FK_auxiliar_problema FOREIGN KEY (auxiliar_id) REFERENCES responsable(responsable_id),
	CONSTRAINT FK_creado_problema FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT FK_estado_problema FOREIGN KEY (estado_id) REFERENCES estado_acciones(estado_id),
	CONSTRAINT FK_indicador_efecto_problema FOREIGN KEY (indicador_efecto_id) REFERENCES indicador(indicador_id),
	CONSTRAINT FK_indicador_problema FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id),
	CONSTRAINT FK_iniciativa_efecto_problema FOREIGN KEY (iniciativa_efecto_id) REFERENCES iniciativa(iniciativa_id),
	CONSTRAINT FK_iniciativa_problema FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id),
	CONSTRAINT FK_modificado_problema FOREIGN KEY (modificado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT FK_organizacion_problema FOREIGN KEY (organizacion_id) REFERENCES organizacion(organizacion_id) ON DELETE CASCADE,
	CONSTRAINT FK_problema_claseproblema FOREIGN KEY (clase_id) REFERENCES clase_problema(clase_id) ON DELETE CASCADE,
	CONSTRAINT FK_responsable_problema FOREIGN KEY (responsable_id) REFERENCES responsable(responsable_id)
);
 CREATE NONCLUSTERED INDEX IE_problema_auxiliar ON dbo.problema (  auxiliar_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_creado ON dbo.problema (  creado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_estado ON dbo.problema (  estado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_indicador ON dbo.problema (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_indicador_efecto ON dbo.problema (  indicador_efecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_iniciativa ON dbo.problema (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_iniciativa_efecto ON dbo.problema (  iniciativa_efecto_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_modificado ON dbo.problema (  modificado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_organizacion ON dbo.problema (  organizacion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_problema_responsable ON dbo.problema (  responsable_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX XIF12problema ON dbo.problema (  clase_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.accion definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.accion;

CREATE TABLE accion (
	accion_id numeric(10,0) NOT NULL,
	padre_id numeric(10,0) NULL,
	problema_id numeric(10,0) NOT NULL,
	estado_id numeric(10,0) NULL,
	nombre varchar(50) COLLATE Modern_Spanish_CI_AS NOT NULL,
	fecha_estimada_inicio datetime NULL,
	fecha_estimada_final datetime NULL,
	fecha_real_inicio datetime NULL,
	fecha_real_final datetime NULL,
	frecuencia numeric(5,0) NOT NULL,
	orden numeric(2,0) NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	descripcion text COLLATE Modern_Spanish_CI_AS NULL,
	read_only numeric(1,0) NULL,
	CONSTRAINT pk_accion PRIMARY KEY (accion_id),
	CONSTRAINT FK_ACCION_ESTADO FOREIGN KEY (estado_id) REFERENCES estado_acciones(estado_id),
	CONSTRAINT FK_ACCION_Problema FOREIGN KEY (problema_id) REFERENCES problema(problema_id) ON DELETE CASCADE,
	CONSTRAINT FK_Accion_PadreId FOREIGN KEY (accion_id) REFERENCES accion(accion_id),
	CONSTRAINT FK_creado_accion FOREIGN KEY (creado_id) REFERENCES afw_usuario(usuario_id),
	CONSTRAINT FK_modificado_accion FOREIGN KEY (modificado_id) REFERENCES afw_usuario(usuario_id)
);
 CREATE NONCLUSTERED INDEX IE_accion_accion ON dbo.accion (  accion_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_accion_creado ON dbo.accion (  creado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_accion_estado ON dbo.accion (  estado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_accion_modificado ON dbo.accion (  modificado_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_accion_problema ON dbo.accion (  problema_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.causas_declaracion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.causas_declaracion_riesgos;

CREATE TABLE causas_declaracion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	probabilidad_riesgo_id bigint NULL,
	declaracion_riesgo_id bigint NULL,
	causa bigint NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__causas_d__3213E83FCE1EB0B6 PRIMARY KEY (id),
	CONSTRAINT FK__causas_de__causa__51851410 FOREIGN KEY (causa) REFERENCES causas_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__causas_de__decla__5090EFD7 FOREIGN KEY (declaracion_riesgo_id) REFERENCES declaracion_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__causas_de__proba__4F9CCB9E FOREIGN KEY (probabilidad_riesgo_id) REFERENCES probabilidad_riesgos(id) ON UPDATE CASCADE
);


-- Strategos2022.dbo.celda definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.celda;

CREATE TABLE celda (
	celda_id numeric(10,0) NOT NULL,
	pagina_id numeric(10,0) NOT NULL,
	titulo varchar(100) COLLATE Modern_Spanish_CI_AS NULL,
	fila numeric(2,0) NOT NULL,
	columna numeric(2,0) NOT NULL,
	creado datetime NULL,
	modificado datetime NULL,
	creado_id numeric(10,0) NULL,
	modificado_id numeric(10,0) NULL,
	configuracion text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_celda PRIMARY KEY (celda_id),
	CONSTRAINT pk_celda_pagina_celda UNIQUE (pagina_id,fila,columna),
	CONSTRAINT FK_pagina_celda FOREIGN KEY (pagina_id) REFERENCES pagina(pagina_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX ie_celda ON dbo.celda (  celda_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_celda_pagina ON dbo.celda (  pagina_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ie_celda_pagina_celda ON dbo.celda (  pagina_id ASC  , fila ASC  , columna ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.conjunto_formula definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.conjunto_formula;

CREATE TABLE conjunto_formula (
	padre_id numeric(10,0) NOT NULL,
	serie_id numeric(10,0) NOT NULL,
	insumo_serie_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	CONSTRAINT pk_conjunto_formula PRIMARY KEY (insumo_serie_id,indicador_id,padre_id,serie_id),
	CONSTRAINT fk_conjfor_indid_indicador FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id),
	CONSTRAINT fk_conjfor_insid_serie FOREIGN KEY (insumo_serie_id) REFERENCES serie_tiempo(serie_id),
	CONSTRAINT fk_conjfor_padre_indicador FOREIGN KEY (padre_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT fk_conjfor_serid_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo(serie_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IX_conjunto_formula_padre_id_serie_id ON dbo.conjunto_formula (  padre_id ASC  , serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE  UNIQUE NONCLUSTERED INDEX ak_conjunto_formula ON dbo.conjunto_formula (  insumo_serie_id ASC  , indicador_id ASC  , padre_id ASC  , serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_conjunto_formula_indid ON dbo.conjunto_formula (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_conjunto_formula_insserieid ON dbo.conjunto_formula (  insumo_serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_conjunto_formula_padreid ON dbo.conjunto_formula (  padre_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_conjunto_formula_serieid ON dbo.conjunto_formula (  serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.controles_declaracion_riesgos definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.controles_declaracion_riesgos;

CREATE TABLE controles_declaracion_riesgos (
	id bigint IDENTITY(1,1) NOT NULL,
	efectividad_riesgos_id bigint NULL,
	declaracion_riesgo_id bigint NULL,
	control varchar(255) COLLATE Modern_Spanish_CI_AS NOT NULL,
	descripcion varchar(255) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK__controle__3213E83F6956B385 PRIMARY KEY (id),
	CONSTRAINT FK__controles__decla__592635D8 FOREIGN KEY (declaracion_riesgo_id) REFERENCES declaracion_riesgos(id) ON UPDATE CASCADE,
	CONSTRAINT FK__controles__efect__5832119F FOREIGN KEY (efectividad_riesgos_id) REFERENCES efectividad_riesgos(id) ON UPDATE CASCADE
);


-- Strategos2022.dbo.duppont definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.duppont;

CREATE TABLE duppont (
	indicador_id numeric(10,0) NOT NULL,
	usuario_id numeric(10,0) NOT NULL,
	configuracion varchar(5000) COLLATE Modern_Spanish_CI_AS NOT NULL,
	CONSTRAINT PK_duppont PRIMARY KEY (indicador_id,usuario_id),
	CONSTRAINT FK_indicador_duppont FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT FK_usuario_duppont FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_duppont ON dbo.duppont (  indicador_id ASC  , usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_duppont_indicadorId ON dbo.duppont (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_duppont_usuarioId ON dbo.duppont (  usuario_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.formula definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.formula;

CREATE TABLE formula (
	indicador_id numeric(10,0) NOT NULL,
	serie_id numeric(10,0) NOT NULL,
	expresion varchar(5000) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT pk_formula PRIMARY KEY (indicador_id,serie_id),
	CONSTRAINT FK_FORMULA_INDICADOR FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE,
	CONSTRAINT fk_formula_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo(serie_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX ak_formula ON dbo.formula (  indicador_id ASC  , serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_formula_indicadorid ON dbo.formula (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX ie_formula_serie ON dbo.formula (  serie_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.indicador_por_iniciativa definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.indicador_por_iniciativa;

CREATE TABLE indicador_por_iniciativa (
	iniciativa_id numeric(10,0) NOT NULL,
	indicador_id numeric(10,0) NOT NULL,
	tipo numeric(1,0) NOT NULL,
	CONSTRAINT PK_indicador_por_iniciativa PRIMARY KEY (iniciativa_id,indicador_id),
	CONSTRAINT FK_ind_por_ini_IniciativaId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE,
	CONSTRAINT FK_ind_por_ini_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador(indicador_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_indicador_por_iniciativa ON dbo.indicador_por_iniciativa (  iniciativa_id ASC  , indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_ind_por_ini_indicadorId ON dbo.indicador_por_iniciativa (  indicador_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_ind_por_ini_iniciativaId ON dbo.indicador_por_iniciativa (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 5    ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.iniciativa_por_perspectiva definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.iniciativa_por_perspectiva;

CREATE TABLE iniciativa_por_perspectiva (
	perspectiva_id numeric(10,0) NOT NULL,
	iniciativa_id numeric(10,0) NOT NULL,
	CONSTRAINT pk_iniciativa_por_perspectiva PRIMARY KEY (perspectiva_id,iniciativa_id),
	CONSTRAINT fK_perspectiva_iniciativa FOREIGN KEY (perspectiva_id) REFERENCES perspectiva(perspectiva_id) ON DELETE CASCADE,
	CONSTRAINT fk_iniciativa_perspectiva FOREIGN KEY (iniciativa_id) REFERENCES iniciativa(iniciativa_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_inic_pers_persid ON dbo.iniciativa_por_perspectiva (  perspectiva_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 15   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_inicia_pers_iniciativaid ON dbo.iniciativa_por_perspectiva (  iniciativa_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 30   ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = ON , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_atrib_coment_cat definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_atrib_coment_cat;

CREATE TABLE mb_atrib_coment_cat (
	atributo_id numeric(10,0) NOT NULL,
	orden numeric(3,0) NOT NULL,
	CONSTRAINT PK_mb_atrib_coment_cat PRIMARY KEY (atributo_id,orden),
	CONSTRAINT PK_mb_atributo_commnet_orden FOREIGN KEY (atributo_id) REFERENCES mb_atributo(atributo_id) ON DELETE CASCADE
);
 CREATE  UNIQUE NONCLUSTERED INDEX AK_mb_atrib_coment_cat ON dbo.mb_atrib_coment_cat (  atributo_id ASC  , orden ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_mb_atrib_coment_cat ON dbo.mb_atrib_coment_cat (  atributo_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_categoria_criterio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_categoria_criterio;

CREATE TABLE mb_categoria_criterio (
	criterio_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NOT NULL,
	categoria varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_MB_CATEGORIA_CRITERIO PRIMARY KEY (criterio_id,orden),
	CONSTRAINT FK_MB_CRITERIO_CATEGORIA FOREIGN KEY (criterio_id) REFERENCES mb_criterio_estratificacion(criterio_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_encuesta definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_encuesta;

CREATE TABLE mb_encuesta (
	encuesta_id numeric(10,0) NOT NULL,
	sector_id numeric(10,0) NOT NULL,
	encuestado_id numeric(10,0) NULL,
	numero numeric(10,0) NULL,
	fecha datetime NOT NULL,
	persona varchar(50) COLLATE Modern_Spanish_CI_AS NULL,
	ci varchar(10) COLLATE Modern_Spanish_CI_AS NULL,
	estado numeric(2,0) NULL,
	comentario varchar(2000) COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT AK_MB_ENCUESTA UNIQUE (sector_id,numero),
	CONSTRAINT PK_MB_ENCUESTA PRIMARY KEY (encuesta_id),
	CONSTRAINT FK_MB_ENCUESTADO_ENCUESTA FOREIGN KEY (encuestado_id) REFERENCES mb_encuestado(encuestado_id) ON DELETE SET NULL,
	CONSTRAINT FK_MB_SECTOR_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector(sector_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_ENCUESTA_SECTOR_ID ON dbo.mb_encuesta (  sector_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_encuesta_atributo definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_encuesta_atributo;

CREATE TABLE mb_encuesta_atributo (
	atributo_id numeric(10,0) NOT NULL,
	encuesta_id numeric(10,0) NOT NULL,
	orden numeric(2,0) NULL,
	prioridad numeric(2,0) NULL,
	comentario text COLLATE Modern_Spanish_CI_AS NULL,
	CONSTRAINT PK_MB_ENCUESTA_ATRIBUTO PRIMARY KEY (atributo_id,encuesta_id),
	CONSTRAINT FK_MB_ATRIBUTO_ENCUESTA FOREIGN KEY (atributo_id) REFERENCES mb_atributo(atributo_id),
	CONSTRAINT FK_MB_ENCUESTA_ATRIBUTO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta(encuesta_id) ON DELETE CASCADE
);
 CREATE NONCLUSTERED INDEX IE_MB_ENCUESTA_ATRIBUTO_ATR ON dbo.mb_encuesta_atributo (  atributo_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;
 CREATE NONCLUSTERED INDEX IE_MB_ENCUESTA_ATRIBUTO_ENC ON dbo.mb_encuesta_atributo (  encuesta_id ASC  )  
	 WITH (  PAD_INDEX = OFF ,FILLFACTOR = 100  ,SORT_IN_TEMPDB = OFF , IGNORE_DUP_KEY = OFF , STATISTICS_NORECOMPUTE = OFF , ONLINE = OFF , ALLOW_ROW_LOCKS = ON , ALLOW_PAGE_LOCKS = ON  )
	 ON [PRIMARY ] ;


-- Strategos2022.dbo.mb_encuesta_criterio definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_encuesta_criterio;

CREATE TABLE mb_encuesta_criterio (
	encuesta_id numeric(10,0) NOT NULL,
	c1 numeric(10,0) NULL,
	c2 numeric(10,0) NULL,
	c3 numeric(10,0) NULL,
	c4 numeric(10,0) NULL,
	c5 numeric(10,0) NULL,
	c6 numeric(10,0) NULL,
	c7 numeric(10,0) NULL,
	c8 numeric(10,0) NULL,
	c9 numeric(10,0) NULL,
	c10 numeric(10,0) NULL,
	CONSTRAINT PK_MB_ENCUESTA_CRITERIO PRIMARY KEY (encuesta_id),
	CONSTRAINT FK_MB_ENCUESTA_CRITERIO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta(encuesta_id) ON DELETE CASCADE
);


-- Strategos2022.dbo.mb_encuesta_variable definition

-- Drop table

-- DROP TABLE Strategos2022.dbo.mb_encuesta_variable;

CREATE TABLE mb_encuesta_variable (
	variable_id numeric(10,0) NOT NULL,
	encuesta_id numeric(10,0) NOT NULL,
	apc numeric(10,0) NULL,
	validas numeric(10,0) NULL,
	novalidas numeric(10,0) NULL,
	estatus numeric(1,0) NULL,
	padre_id numeric(10,0) NULL,
	nivel numeric(4,0) NULL,
	variable_red_id numeric(10,0) NULL,
	promedio float NULL,
	CONSTRAINT PK_MB_ENCUESTA_VARIABLE PRIMARY KEY (encuesta_id,variable_id),
	CONSTRAINT FK_MB_ENCUESTA_VARIABLE FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta(encuesta_id) ON DELETE CASCADE,
	CONSTRAINT FK_MB_MEDICION_ENC_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable(variable_id) ON DELETE CASCADE
);

ALTER TABLE iniciativa
ADD
    alineacion_pdmp VARCHAR(500),
    alineacion_ods VARCHAR(500),
    cobertura_geografica VARCHAR(500),
    impacto_ciudadania VARCHAR(500),
    implementador_recursos VARCHAR(500),
    dependencia_responsable VARCHAR(500),
    sostenibilidad VARCHAR(500),
    dependencias_competentes VARCHAR(500);
-- Database: "StrategosPlus-Vacia"

-- DROP DATABASE "StrategosPlus-Vacia";

--CREATE DATABASE "StrategosPlus-Vacia"
--  WITH OWNER = postgres
--       ENCODING = 'UTF8'
--       TABLESPACE = pg_default
--       CONNECTION LIMIT = -1;

	   
--
-- PostgreSQL database dump
--

-- Started on 2012-05-31 19:43:20

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 835 (class 2612 OID 275813)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

--CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 511 (class 1247 OID 276552)
-- Dependencies: 6
-- Name: lo; Type: DOMAIN; Schema: public; Owner: -
--

CREATE DOMAIN lo AS oid;


--
-- TOC entry 236 (class 1255 OID 276553)
-- Dependencies: 6
-- Name: lo_manage(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION lo_manage() RETURNS trigger
    AS '$libdir/lo', 'lo_manage'
    LANGUAGE c;


--
-- TOC entry 237 (class 1255 OID 276554)
-- Dependencies: 511 6
-- Name: lo_oid(lo); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION lo_oid(lo) RETURNS oid
    AS $_$SELECT $1::pg_catalog.oid$_$
    LANGUAGE sql IMMUTABLE STRICT;


--
-- TOC entry 238 (class 1255 OID 276555)
-- Dependencies: 6
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


--
-- TOC entry 239 (class 1255 OID 276556)
-- Dependencies: 6
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


--
-- TOC entry 240 (class 1255 OID 276557)
-- Dependencies: 6
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


--
-- TOC entry 241 (class 1255 OID 276558)
-- Dependencies: 6
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 129 (class 1259 OID 276568)
-- Dependencies: 6
-- Name: accion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE accion (
    accion_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    problema_id numeric(10,0) NOT NULL,
    estado_id numeric(10,0),
    nombre character varying(50) NOT NULL,
    fecha_estimada_inicio timestamp without time zone,
    fecha_estimada_final timestamp without time zone,
    fecha_real_inicio timestamp without time zone,
    fecha_real_final timestamp without time zone,
    frecuencia numeric(5,0) NOT NULL,
    orden numeric(2,0),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    descripcion text,
    read_only numeric(1,0)
);


--
-- TOC entry 130 (class 1259 OID 276574)
-- Dependencies: 6
-- Name: afw_auditoria_entero; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_entero (
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    valor numeric(10,0),
    usuario_id numeric(10,0),
    objeto_id numeric(10,0),
    nombre_atributo character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
	valor_anterior NUMERIC(10, 0) NULL
);


--
-- TOC entry 131 (class 1259 OID 276577)
-- Dependencies: 6
-- Name: afw_auditoria_evento; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_evento (
    objeto_id numeric(10,0) NOT NULL,
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
    instancia_nombre character varying(300),
    usuario_id numeric(10,0)
);


--
-- TOC entry 132 (class 1259 OID 276580)
-- Dependencies: 6
-- Name: afw_auditoria_fecha; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_fecha (
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    valor timestamp without time zone,
    usuario_id numeric(10,0),
    objeto_id numeric(10,0),
    nombre_atributo character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
	valor_anterior TIMESTAMP without time zone NULL
);


--
-- TOC entry 133 (class 1259 OID 276583)
-- Dependencies: 6
-- Name: afw_auditoria_flotante; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_flotante (
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    valor double precision,
    usuario_id numeric(10,0),
    objeto_id numeric(10,0),
    nombre_atributo character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
	valor_anterior double precision NULL
);


--
-- TOC entry 134 (class 1259 OID 276586)
-- Dependencies: 6
-- Name: afw_auditoria_memo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_memo (
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    valor character varying(2000) NULL,
    usuario_id numeric(10,0),
    objeto_id numeric(10,0),
    nombre_atributo character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
	valor_anterior character varying(2000) NULL
);


--
-- TOC entry 135 (class 1259 OID 276592)
-- Dependencies: 6
-- Name: afw_auditoria_string; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_auditoria_string (
    fecha timestamp without time zone NOT NULL,
    instancia_id character varying(100) NOT NULL,
    valor character varying(500),
    usuario_id numeric(10,0),
    objeto_id numeric(10,0),
    nombre_atributo character varying(100) NOT NULL,
    tipo_evento numeric(1,0) NOT NULL,
	valor_anterior character varying(500) NULL
);


--
-- TOC entry 136 (class 1259 OID 276598)
-- Dependencies: 6
-- Name: afw_config_usuario; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_config_usuario (
    usuario_id numeric(10,0) NOT NULL,
    configuracion_base character varying(200) NOT NULL,
    objeto character varying(100) NOT NULL,
    data text NOT NULL
);


SET default_with_oids = false;

--
-- TOC entry 137 (class 1259 OID 276604)
-- Dependencies: 6
-- Name: afw_configuracion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_configuracion (
    parametro character varying(200) NOT NULL,
    valor text NOT NULL
);


SET default_with_oids = false;

--
-- TOC entry 138 (class 1259 OID 276610)
-- Dependencies: 6
-- Name: afw_error; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_error (
    err_number numeric(10,0),
    err_source character varying(2000),
    err_description character varying(2000),
    err_timestamp timestamp without time zone,
    err_user_id character varying(20),
    err_version character varying(20),
    err_step character varying(20),
    err_stacktrace text,
    err_cause text
);


--
-- TOC entry 139 (class 1259 OID 276616)
-- Dependencies: 6
-- Name: afw_grupo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_grupo (
    grupo_id numeric(10,0) NOT NULL,
    grupo character varying(50) NOT NULL,
    creado timestamp without time zone NOT NULL,
    modificado timestamp without time zone,
    creado_id numeric(10,0) NOT NULL,
    modificado_id numeric(10,0)
);


--
-- TOC entry 140 (class 1259 OID 276619)
-- Dependencies: 6
-- Name: afw_idgen; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE afw_idgen
    INCREMENT BY 1
	START 50
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

--
-- TOC entry 141 (class 1259 OID 276621)
-- Dependencies: 6
-- Name: afw_lock; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_lock (
    objeto_id numeric(10,0) NOT NULL,
    tipo character varying(1) NOT NULL,
    instancia character varying(50),
    time_stamp timestamp without time zone
);


--
-- TOC entry 142 (class 1259 OID 276624)
-- Dependencies: 6
-- Name: afw_lock_read; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_lock_read (
    objeto_id numeric(10,0) NOT NULL,
    instancia character varying(50) NOT NULL
);


--
-- TOC entry 143 (class 1259 OID 276627)
-- Dependencies: 6
-- Name: afw_message_resource; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_message_resource (
    clave character varying(200) NOT NULL,
    valor character varying(2000) NOT NULL
);


--
-- TOC entry 144 (class 1259 OID 276633)
-- Dependencies: 6
-- Name: afw_objeto_auditable; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_objeto_auditable (
    objeto_id numeric(10,0) NOT NULL,
    nombre_clase character varying(200),
    nombre_campo_id character varying(100),
    nombre_campo_nombre character varying(100),
    auditoria_activa numeric(1,0)
);


--
-- TOC entry 145 (class 1259 OID 276636)
-- Dependencies: 6
-- Name: afw_objeto_auditable_atributo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_objeto_auditable_atributo (
    objeto_id numeric(10,0) NOT NULL,
    nombre_atributo character varying(100) NOT NULL,
    tipo numeric(1,0) NOT NULL,
	configuracion character varying(2000)
);


SET default_with_oids = false;

--
-- TOC entry 146 (class 1259 OID 276639)
-- Dependencies: 511 6
-- Name: afw_objeto_binario; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_objeto_binario (
    objeto_binario_id numeric(10,0) NOT NULL,
    nombre character varying(200) NOT NULL,
    mime_type character varying(200),
    data lo
);


SET default_with_oids = false;

--
-- TOC entry 147 (class 1259 OID 276642)
-- Dependencies: 6
-- Name: afw_objeto_sistema; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_objeto_sistema (
    objeto_id character varying(50) NOT NULL,
    art_singular character varying(10) NOT NULL,
    nombre_singular character varying(100) NOT NULL,
    art_plural character varying(20) NOT NULL,
    nombre_plural character varying(100) NOT NULL
);


--
-- TOC entry 148 (class 1259 OID 276645)
-- Dependencies: 6
-- Name: afw_permiso; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_permiso (
    permiso_id character varying(50) NOT NULL,
    padre_id character varying(50),
    permiso character varying(50) NOT NULL,
    nivel numeric(2,0),
    grupo numeric(2,0),
    global numeric(1,0),
    descripcion character varying(2000)
);


--
-- TOC entry 149 (class 1259 OID 276651)
-- Dependencies: 6
-- Name: afw_permiso_grupo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_permiso_grupo (
    permiso_id character varying(50) NOT NULL,
    grupo_id numeric(10,0) NOT NULL
);


--
-- TOC entry 150 (class 1259 OID 276654)
-- Dependencies: 6
-- Name: afw_plantilla; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_plantilla (
    plantilla_id numeric(10,0) NOT NULL,
    objeto_id numeric(10,0),
    nombre character varying(100) NOT NULL,
    descripcion character varying(2000),
    publico numeric(1,0),
    tipo character varying(20),
    xml text,
    usuario_id numeric(10,0)
);


--
-- TOC entry 151 (class 1259 OID 276660)
-- Dependencies: 6
-- Name: afw_sistema; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_sistema (
    id numeric(10,0) NOT NULL,
    version character varying(20),
	build	numeric(10,0),
	fecha	timestamp without time zone,
    rdbms_id character varying(20),
    producto character varying(50),
	cmaxc character varying(1000),
	conexion character varying(50),
	actual character varying(50)
);


--
-- TOC entry 152 (class 1259 OID 276666)
-- Dependencies: 6
-- Name: afw_user_session; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_user_session (
    session_id character varying(100) NOT NULL,
    usuario_id numeric(10,0) NOT NULL,
    login_ts timestamp without time zone NOT NULL,
    remote_address character varying(200),
    remote_host character varying(200),
    remote_user character varying(1000),
    url character varying(100),
    persona_id numeric(10,0)
);


--
-- TOC entry 153 (class 1259 OID 276672)
-- Dependencies: 6
-- Name: afw_usuario; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_usuario (
    usuario_id numeric(10,0) NOT NULL,
    full_name character varying(50) NOT NULL,
    u_id character varying(50) NOT NULL,
    pwd character varying(100) NOT NULL,
    is_admin numeric(1,0),
    is_connected numeric(1,0),
	is_system numeric(1,0),
    time_stamp timestamp without time zone,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    instancia character varying(40),
    inactivo numeric(1,0),
	estatus numeric(1,0),
	bloqueado numeric(1,0),
	ultima_modif_pwd timestamp without time zone,
	deshabilitado numeric(1,0),
	forzarCambiarPwd numeric(1,0)
);


ALTER TABLE ONLY afw_usuario
    ADD CONSTRAINT pk_afw_usuario PRIMARY KEY (usuario_id);

--
-- TOC entry 154 (class 1259 OID 276675)
-- Dependencies: 6
-- Name: afw_usuario_grupo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_usuario_grupo (
    usuario_id numeric(10,0) NOT NULL,
    grupo_id numeric(10,0) NOT NULL,
    organizacion_id numeric(10,0) NOT NULL,
    modificado timestamp without time zone,
    modificado_id numeric(10,0)
);


--
-- TOC entry 155 (class 1259 OID 276678)
-- Dependencies: 6
-- Name: afw_usuarioweb; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE afw_usuarioweb (
    usuario_id numeric(10,0) NOT NULL,
    sessionweb character varying(50) NOT NULL,
    time_stamp_conex timestamp with time zone NOT NULL
);


CREATE TABLE afw_pwd_historia
(
	usuario_id           numeric(10) NOT NULL ,
	pwd                  character varying(100) NULL ,
	fecha                timestamp without time zone NOT NULL,
	CONSTRAINT PK_afw_pwd_historia PRIMARY KEY (usuario_id,fecha),
	CONSTRAINT FK_USER_PWDHISTORIA FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF_afw_pwd_historia ON afw_pwd_historia USING btree (usuario_id   ASC, fecha ASC);


--
-- TOC entry 156 (class 1259 OID 276681)
-- Dependencies: 6
-- Name: categoria; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE categoria (
    categoria_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL
);


--
-- TOC entry 157 (class 1259 OID 276684)
-- Dependencies: 6
-- Name: categoria_por_indicador; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE categoria_por_indicador (
    indicador_id numeric(10,0) NOT NULL,
    categoria_id numeric(10,0) NOT NULL,
    orden numeric(4,0)
);


--
-- TOC entry 158 (class 1259 OID 276687)
-- Dependencies: 6
-- Name: causa; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE causa (
    causa_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    nombre character varying(50) NOT NULL,
    descripcion text,
    nivel numeric(2,0)
);


--
-- TOC entry 159 (class 1259 OID 276693)
-- Dependencies: 6
-- Name: causa_por_problema; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE causa_por_problema (
    problema_id numeric(10,0) NOT NULL,
    causa_id numeric(10,0) NOT NULL,
    orden numeric(5,0)
);

--
-- TOC entry 161 (class 1259 OID 276699)
-- Dependencies: 6
-- Name: clase; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE clase (
    clase_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    organizacion_id numeric(10,0),
    nombre character varying(310) NOT NULL,
    descripcion character varying(250),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    modificado_id numeric(10,0),
    creado_id numeric(10,0),
    enlace_parcial character varying(100),
    tipo numeric(2,0),
    visible numeric(1,0)
);


--
-- TOC entry 162 (class 1259 OID 276702)
-- Dependencies: 6
-- Name: clase_problema; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE clase_problema (
    clase_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    organizacion_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(2000),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
	tipo numeric(1) NOT NULL
);


--
-- TOC entry 163 (class 1259 OID 276708)
-- Dependencies: 6
-- Name: conjunto_formula; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE conjunto_formula 
(
    padre_id numeric(10,0) NOT NULL,
    serie_id numeric(10,0) NOT NULL,
    insumo_serie_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL
);

CREATE UNIQUE INDEX ak_conjunto_formula ON conjunto_formula (insumo_serie_id   ASC,indicador_id   ASC,padre_id   ASC,serie_id   ASC);
ALTER TABLE conjunto_formula ADD CONSTRAINT pk_conjunto_formula PRIMARY KEY (insumo_serie_id, indicador_id, padre_id, serie_id);
CREATE INDEX ie_conjunto_formula_padreid ON conjunto_formula (padre_id   ASC);
CREATE INDEX ie_conjunto_formula_indid ON conjunto_formula (indicador_id   ASC);
CREATE INDEX ie_conjunto_formula_serieid ON conjunto_formula (serie_id   ASC);
CREATE INDEX ie_conjunto_formula_insserieid ON conjunto_formula (insumo_serie_id   ASC);

--
-- TOC entry 164 (class 1259 OID 276711)
-- Dependencies: 6
-- Name: cuenta; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE cuenta (
    cuenta_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    codigo character varying(10) NOT NULL,
    descripcion character varying(250) NOT NULL
);


--
-- TOC entry 165 (class 1259 OID 276714)
-- Dependencies: 6
-- Name: estado_acciones; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE estado_acciones (
    estado_id numeric(10,0) NOT NULL,
    nombre character varying(15) NOT NULL,
    aplica_seguimiento numeric(1,0),
    orden numeric(4,0),
    condicion numeric(4,0)
);


--
-- TOC entry 166 (class 1259 OID 276717)
-- Dependencies: 6
-- Name: explicacion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE explicacion (
    explicacion_id numeric(10,0) NOT NULL,
    creado_id numeric(10,0),
    creado timestamp without time zone,
    objeto_id numeric(10,0) NOT NULL,
    objeto_key numeric(2,0) NOT NULL,
    titulo character varying(250) NOT NULL,
    fecha timestamp without time zone,
	tipo numeric(1) NOT NULL,
	fecha_compromiso timestamp without time zone,
	fecha_real timestamp without time zone,
	publico numeric(1) NOT NULL
);


--
-- TOC entry 167 (class 1259 OID 276720)
-- Dependencies: 6 511
-- Name: explicacion_adjunto; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE explicacion_adjunto (
    explicacion_id numeric(10,0) NOT NULL,
    adjunto_id numeric(10,0) NOT NULL,
    documento lo,
    mime_type character varying(50),
    titulo character varying(100)
);


--
-- TOC entry 168 (class 1259 OID 276723)
-- Dependencies: 6
-- Name: explicacion_memo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE explicacion_memo (
    explicacion_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    memo text
);


--
-- TOC entry 169 (class 1259 OID 276729)
-- Dependencies: 6
-- Name: formula; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--
CREATE TABLE formula 
(
    indicador_id numeric(10,0) NOT NULL,
    serie_id numeric(10,0) NOT NULL,
    expresion text
);

CREATE UNIQUE INDEX ak_formula ON formula (indicador_id ASC,serie_id ASC);

ALTER TABLE FORMULA ADD CONSTRAINT pk_formula PRIMARY KEY (indicador_id, serie_id);

CREATE  INDEX ie_formula_indicadorid ON FORMULA (indicador_id   ASC);

CREATE  INDEX ie_formula_serie ON FORMULA (serie_id   ASC);

--
-- TOC entry 170 (class 1259 OID 276735)
-- Dependencies: 6
-- Name: foro; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE foro (
    foro_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    creado_id numeric(10,0),
    creado timestamp without time zone,
    objeto_id numeric(10,0) NOT NULL,
    objeto_key numeric(2,0) NOT NULL,
    asunto character varying(100),
    email character varying(100),
    comentario text NOT NULL,
    modificado_id numeric(10,0),
    modificado timestamp without time zone,
    tipo numeric(1,0) NOT NULL
);


--
-- TOC entry 171 (class 1259 OID 276741)
-- Dependencies: 6
-- Name: inc_actividad; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE inc_actividad (
    actividad_id numeric(10,0) NOT NULL,
    producto_esperado text,
    recurso_humano text,
    recurso_material text,
    peso double precision,
    alerta_za double precision,
    alerta_zv double precision,
    porcentaje_ejecutado double precision,
    porcentaje_esperado double precision,
    porcentaje_completado double precision,
    alerta numeric(4,0)
);


--
-- TOC entry 172 (class 1259 OID 276747)
-- Dependencies: 6
-- Name: indicador; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE indicador (
    indicador_id numeric(10,0) NOT NULL,
    alerta_n2_ind_id numeric(10,0),
    alerta_n1_ind_id numeric(10,0),
    organizacion_id numeric(10,0),
    clase_id numeric(10,0),
    nombre character varying(150) NOT NULL,
    descripcion text,
    naturaleza numeric(1,0) NOT NULL,
    frecuencia numeric(1,0) NOT NULL,
    guia character varying(50),
    unidad_id numeric(10,0),
    codigo_enlace character varying(100),
    nombre_corto character varying(150) NOT NULL,
    prioridad numeric(1,0) NOT NULL,
    constante numeric(1,0) NOT NULL,
    fuente text,
    orden character varying(10),
    read_only numeric(1,0),
    caracteristica numeric(1,0),
    crecimiento_ind numeric(1,0),
    fecha_ultima_medicion character varying(10),
    ultima_medicion double precision,
    resp_fijar_meta_id numeric(10,0),
    resp_lograr_meta_id numeric(10,0),
    resp_seguimiento_id numeric(10,0),
    resp_cargar_meta_id numeric(10,0),
    resp_cargar_ejecutado_id numeric(10,0),
    lag_lead numeric(1,0),
    corte numeric(1,0),
    enlace_parcial character varying(100),
    alerta_min_max numeric(2,0),
    alerta_meta_n1 double precision,
    alerta_meta_n2 double precision,
    intranet numeric(1,0),
    alerta_n1_tipo numeric(1,0),
    alerta_n2_tipo numeric(1,0),
    alerta_n1_fv numeric(1,0),
    alerta_n2_fv numeric(1,0),
    valor_inicial_cero numeric(1,0),
    mascara_decimales numeric(2,0),
    tipo_medicion numeric(1,0),
    url text,
    fecha_bloqueo_ejecutado timestamp without time zone,
    fecha_bloqueo_meta timestamp without time zone,
    psuperior_id numeric(10,0),
    pinferior_id numeric(10,0),
    psuperior_vf double precision,
    pinferior_vf double precision,
    multidimensional numeric(1,0),
    tipo numeric(1,0),
    tipo_asociado numeric(1,0),
    asociado_id numeric(10,0),
    revision numeric(3,0),
    ultimo_programado double precision,
	tipo_suma numeric(1,0),
	asignar_Inventario	 numeric(1,0)
);

ALTER TABLE ONLY indicador ADD CONSTRAINT pk_indicador PRIMARY KEY (indicador_id);
CREATE UNIQUE INDEX ak_indicador ON indicador (indicador_id   ASC);
ALTER TABLE indicador ADD CONSTRAINT pk_indicador_clase_nombre UNIQUE (clase_id, nombre);
CREATE UNIQUE INDEX ak_indicador_clase_nombre ON indicador (clase_id   ASC,nombre   ASC);
CREATE INDEX ie_indicador_respfijarmeta ON indicador (resp_fijar_meta_id   ASC);
CREATE INDEX ie_indicador_resplograrmeta ON indicador (resp_lograr_meta_id   ASC);
CREATE INDEX ie_indicador_resseguimiento ON indicador (resp_seguimiento_id   ASC);
CREATE INDEX ie_indicador_rescargameta ON indicador (resp_cargar_meta_id   ASC);
CREATE INDEX ie_indicador_resejecutado ON indicador (resp_cargar_ejecutado_id   ASC);
CREATE INDEX ie_indicador_claseid ON indicador (clase_id   ASC);
CREATE INDEX ie_indicador_organizacion ON indicador (organizacion_id   ASC);
CREATE INDEX ie_indicador_unidad ON indicador (unidad_id   ASC);


--
-- TOC entry 174 (class 1259 OID 276756)
-- Dependencies: 6
-- Name: indicador_estado; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE indicador_estado (
    plan_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    estado double precision
);


--
-- TOC entry 175 (class 1259 OID 276759)
-- Dependencies: 6
-- Name: indicador_por_celda; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE indicador_por_celda (
    celda_id numeric(10,0) NOT NULL,
    serie_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    serie_color character varying(20),
    serie_estilo numeric(1,0),
    leyenda character varying(20),
    plan_id numeric(10,0)
);


--
-- TOC entry 176 (class 1259 OID 276762)
-- Dependencies: 6
-- Name: indicador_por_perspectiva; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE indicador_por_perspectiva (
    perspectiva_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    peso double precision
);


--
-- TOC entry 177 (class 1259 OID 276765)
-- Dependencies: 6
-- Name: indicador_por_plan; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE indicador_por_plan (
    plan_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    peso double precision,
    crecimiento numeric(1,0),
    tipo_medicion numeric(1,0)	
);

CREATE TABLE iniciativa_estatus
(
	id                   numeric(10,0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	porcentaje_inicial   double precision,
	porcentaje_final     double precision,
    sistema				 numeric(1,0) NOT NULL,
	bloquear_medicion	 numeric(1,0) NOT NULL,
	bloquear_indicadores numeric(1,0) NOT NULL	
);

CREATE UNIQUE INDEX IE_Iniciativa_Estatus ON iniciativa_estatus USING btree (id   ASC);
CREATE UNIQUE INDEX AK_iniciativa_estatus ON iniciativa_estatus USING btree (nombre   ASC);

ALTER TABLE ONLY iniciativa_estatus ADD CONSTRAINT PK_Iniciativa_Estatus PRIMARY KEY (id);
ALTER TABLE iniciativa_estatus ADD CONSTRAINT PK_iniciativa_estatus_nombre UNIQUE (nombre);

--
-- TOC entry 178 (class 1259 OID 276768)
-- Dependencies: 6
-- Name: iniciativa; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE iniciativa (
    iniciativa_id numeric(10,0) NOT NULL,
    organizacion_id numeric(10,0),
    nombre character varying(250) NOT NULL,
    nombre_largo character varying(300),
    alerta_za double precision,
    alerta_zv double precision,
    frecuencia numeric(2,0) NOT NULL,
    tipo_alerta numeric(2,0) NOT NULL,
    contratista character varying(50),
    naturaleza numeric(1,0) NOT NULL,
    resp_fijar_meta_id numeric(10,0),
    resp_lograr_meta_id numeric(10,0),
    resp_seguimiento_id numeric(10,0),
    resp_cargar_meta_id numeric(10,0),
    resp_cargar_ejecutado_id numeric(10,0),
    proyecto_id numeric(10,0),
    visible numeric(1,0),
    clase_id numeric(10,0),
    read_only numeric(1,0),
	porcentaje_completado double precision,
	crecimiento numeric(1,0) NULL,
	fecha_ultima_medicion character varying(10) NULL,
	tipo_medicion numeric(1,0) NOT NULL,
	historico_date timestamp without time zone,
	estatusId numeric(10,0) NOT NULL
);

CREATE UNIQUE INDEX ak_iniciativa ON iniciativa USING btree (organizacion_id   ASC,nombre   ASC);
CREATE  INDEX IE_iniciativa_organizacionid ON iniciativa (organizacion_id   ASC);
CREATE  INDEX IE_iniciativa_proyecto ON iniciativa USING btree (proyecto_id   ASC);
CREATE  INDEX IE_iniciativa_clase ON iniciativa USING btree (clase_id   ASC);
CREATE  INDEX IE_iniciativa_resp_ejecutar ON iniciativa USING btree (resp_cargar_ejecutado_id   ASC);
CREATE  INDEX IE_iniciativa_resp_meta ON iniciativa USING btree (resp_fijar_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_lograr ON iniciativa USING btree (resp_lograr_meta_id   ASC);
CREATE  INDEX IE_iniciativa_resp_seguimiento ON iniciativa USING btree (resp_seguimiento_id   ASC);
CREATE  INDEX IE_iniciativa_resp_cargar ON iniciativa USING btree (resp_cargar_meta_id   ASC);
CREATE  INDEX IE_iniciativa_estatusId ON iniciativa USING btree (estatusId   ASC);

--
-- TOC entry 179 (class 1259 OID 276774)
-- Dependencies: 6
-- Name: iniciativa_ano; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE iniciativa_ano (
    iniciativa_id numeric(10,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    resultado text
);

CREATE  INDEX IE_iniciativaId_ano ON iniciativa_ano USING btree (iniciativa_id   ASC);

--
-- TOC entry 180 (class 1259 OID 276780)
-- Dependencies: 6
-- Name: iniciativa_objeto; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE iniciativa_objeto (
    iniciativa_id numeric(10,0) NOT NULL,
    objeto text,
    resultado text
);


--
-- TOC entry 181 (class 1259 OID 276786)
-- Dependencies: 6
-- Name: iniciativa_por_perspectiva; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE iniciativa_por_perspectiva (
    perspectiva_id numeric(10,0) NOT NULL,
    iniciativa_id numeric(10,0) NOT NULL
);

CREATE  INDEX IE_inicia_pers_iniciativaid ON iniciativa_por_perspectiva USING btree (iniciativa_id   ASC);
CREATE  INDEX IE_inic_pers_persid ON iniciativa_por_perspectiva USING btree (perspectiva_id   ASC);

--
-- TOC entry 181 (class 1259 OID 276786)
-- Dependencies: 6
-- Name: iniciativa_plan; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE iniciativa_plan
(
	iniciativa_id        numeric(10,0) NOT NULL,
	plan_id              numeric(10,0) NOT NULL 
);

CREATE UNIQUE INDEX AK_iniciativa_plan ON iniciativa_plan USING btree (iniciativa_id   ASC,plan_id   ASC);

ALTER TABLE ONLY iniciativa_plan
	ADD CONSTRAINT  PK_iniciativa_plan PRIMARY KEY (iniciativa_id,plan_id);

CREATE  INDEX IE_iniciativa_iniciativa_plan ON iniciativa_plan USING btree (iniciativa_id   ASC);
CREATE  INDEX IE_plan_iniciativa_plan ON iniciativa_plan USING btree (plan_id   ASC);

--
-- TOC entry 183 (class 1259 OID 276792)
-- Dependencies: 6
-- Name: mascara_codigo_plan_cuentas; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mascara_codigo_plan_cuentas (
    mascara_id numeric(10,0) NOT NULL,
    niveles numeric(4,0) NOT NULL,
    mascara character varying(200)
);

--
-- TOC entry 184 (class 1259 OID 276795)
-- Dependencies: 6
-- Name: mascara_codigo_plan_cuentas_grupo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE masc_cod_plan_cuentas_grupo (
    mascara_id numeric(10,0) NOT NULL,
    nivel numeric(4,0) NOT NULL,
    mascara character varying(10),
    nombre character varying(50)
);


--
-- TOC entry 185 (class 1259 OID 276798)
-- Dependencies: 6
-- Name: medicion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE medicion (
    serie_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    valor double precision,
    protegido numeric(1,0)
);


--
-- TOC entry 186 (class 1259 OID 276801)
-- Dependencies: 6
-- Name: meta; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE meta (
    plan_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    valor double precision,
    serie_id numeric(10,0) NOT NULL,
    protegido numeric(1,0)
);


--
-- TOC entry 187 (class 1259 OID 276804)
-- Dependencies: 6
-- Name: metodologia; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE metodologia (
    metodologia_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion text,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    indicador_nombre character varying(50),
    iniciativa_nombre character varying(50),
    actividad_nombre character varying(50),
    indicador_nombre_plural character varying(50),
    iniciativa_nombre_plural character varying(50),
    actividad_nombre_plural character varying(50),
    tipo numeric(1,0),
    indicador_articulo character varying(50),
    iniciativa_articulo character varying(50),
    actividad_articulo character varying(50),
    indicador_articulo_plural character varying(50),
    iniciativa_articulo_plural character varying(50),
    actividad_articulo_plural character varying(50)
);


--
-- TOC entry 188 (class 1259 OID 276810)
-- Dependencies: 6
-- Name: metodologia_template; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE metodologia_template (
    metodologia_template_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL,
    metodologia_id numeric(10,0),
    orden numeric(4,0),
    tipo numeric(2,0)
);


--
-- TOC entry 189 (class 1259 OID 276813)
-- Dependencies: 6
-- Name: modelo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE modelo 
(
    modelo_id numeric(10,0) NOT NULL,
	plan_id numeric(10,0) NOT NULL,
	nombre character varying(100) NOT NULL,
    descripcion character varying(2000),
    binario text
);

--
-- TOC entry 191 (class 1259 OID 276825)
-- Dependencies: 6
-- Name: organizacion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizacion (
    organizacion_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    nombre character varying(150) NOT NULL,
    rif character varying(15),
    direccion character varying(150),
    telefono character varying(50),
    fax character varying(50),
    mes_cierre numeric(4,0),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    alerta_min_max numeric(4,0),
    alerta_meta_n1 numeric(4,0),
    alerta_meta_n2 numeric(4,0),
    alerta_iniciativa_zv numeric(4,0),
    alerta_iniciativa_za numeric(4,0),
    enlace_parcial character varying(100),
    subclase character varying(4),
    visible numeric(1,0) NOT NULL,
    read_only numeric(1,0)
);

ALTER TABLE ONLY organizacion
    ADD CONSTRAINT pk_organizacion PRIMARY KEY (organizacion_id);

--
-- TOC entry 192 (class 1259 OID 276828)
-- Dependencies: 6
-- Name: organizacion_memo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE organizacion_memo (
    organizacion_id numeric(10,0) NOT NULL,
    tipo numeric(2,0) NOT NULL,
    descripcion text
);


--
-- TOC entry 193 (class 1259 OID 276834)
-- Dependencies: 6
-- Name: pagina; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pagina (
    pagina_id numeric(10,0) NOT NULL,
    vista_id numeric(10,0) NULL,
    descripcion character varying(2000),
    filas numeric(2,0) NOT NULL,
    columnas numeric(2,0) NOT NULL,
    ancho numeric(4,0) NOT NULL,
    alto numeric(4,0) NOT NULL,
    numero numeric(4,0),
	portafolioId numeric(10,0) NULL 
);

ALTER TABLE pagina ADD CONSTRAINT  pk_pagina PRIMARY KEY (pagina_id);
CREATE UNIQUE INDEX ie_pagina ON pagina USING btree (pagina_id   ASC);
CREATE INDEX ie_pagina_vista ON pagina USING btree (vista_id   ASC);
CREATE INDEX ie_pagina_portafolio ON pagina USING btree (portafolioId   ASC);

--
-- TOC entry 194 (class 1259 OID 276840)
-- Dependencies: 6
-- Name: perspectiva; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE perspectiva (
    perspectiva_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    plan_id numeric(10,0) NOT NULL,
    responsable_id numeric(10,0),
    nombre character varying(250) NOT NULL,
    descripcion character varying(2000),
    orden numeric(2,0),
    frecuencia numeric(1,0),
    titulo character varying(50),
    tipo numeric(1,0) NOT NULL,
    fecha_ultima_medicion character varying(10),
    ultima_medicion_anual double precision,
    ultima_medicion_parcial double precision,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    crecimiento_parcial numeric(1,0),
	crecimiento_anual numeric(1,0),
    ano numeric(10,0),
    clase_id numeric(10,0),
    tipo_calculo numeric(1,0),
    nl_par_indicador_id numeric(10,0),
    nl_ano_indicador_id numeric(10,0)
);


--
-- TOC entry 195 (class 1259 OID 276846)
-- Dependencies: 6
-- Name: perspectiva_nivel; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE perspectiva_nivel (
    perspectiva_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    nivel double precision,
	meta double precision,
	alerta numeric(1,0) NULL 
);

CREATE UNIQUE INDEX ak_perspectiva_estado ON perspectiva_nivel USING btree (perspectiva_id   ASC,tipo   ASC,ano   ASC,periodo   ASC);
CREATE INDEX IE_perspectiva_nivel ON perspectiva_nivel USING btree (perspectiva_id   ASC);

--
-- TOC entry 196 (class 1259 OID 276849)
-- Dependencies: 6
-- Name: plan; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE planes
(
	plan_id              NUMERIC(10, 0) NOT NULL ,
	padre_id             NUMERIC(10, 0) NULL ,
	plan_impacta_id      NUMERIC(10, 0) NULL ,
	organizacion_id      NUMERIC(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	ano_inicial          NUMERIC(4, 0) NOT NULL ,
	ano_final            NUMERIC(4, 0) NOT NULL ,
	tipo                 NUMERIC(1, 0) NULL ,
	activo               NUMERIC(1, 0) NULL ,
	revision             NUMERIC(2, 0) NOT NULL ,
	metodologia_id       NUMERIC(10, 0) NOT NULL ,
	clase_id             NUMERIC(10, 0) NOT NULL ,
	clase_id_indicadores_totales NUMERIC(10, 0) NULL ,
	ind_total_imputacion_id NUMERIC(10, 0) NULL ,
	ind_total_iniciativa_id NUMERIC(10, 0) NULL ,
	ultima_medicion_anual double precision NULL ,
	ultima_medicion_parcial double precision NULL ,
	nl_ano_indicador_id  NUMERIC(10, 0) NULL ,
	nl_par_indicador_id  NUMERIC(10, 0) NULL ,
	fecha_ultima_medicion character varying(10) NULL ,
	crecimiento          NUMERIC(1, 0) NULL ,
	esquema              character varying(50) NULL 
);

ALTER TABLE planes ADD CONSTRAINT pk_planes PRIMARY KEY (plan_id);

CREATE  INDEX IE_padre_plan ON planes USING btree (padre_id   ASC);
CREATE  INDEX IE_impacta_plan ON planes USING btree (plan_impacta_id   ASC);
CREATE  INDEX IE_organizacion_plan ON planes USING btree (organizacion_id   ASC);
CREATE  INDEX IE_metodologia_plan ON planes USING btree (metodologia_id   ASC);
CREATE  INDEX IE_clase_plan ON planes USING btree (clase_id   ASC);
CREATE  INDEX IE_clasetotales_plan ON planes USING btree (clase_id_indicadores_totales   ASC);

--
-- TOC entry 197 (class 1259 OID 276852)
-- Dependencies: 6
-- Name: plan_nivel; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE plan_nivel (
    plan_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    nivel double precision
);


--
-- TOC entry 198 (class 1259 OID 276855)
-- Dependencies: 6
-- Name: prd_producto; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE prd_producto (
    producto_id numeric(10,0) NOT NULL,
    iniciativa_id numeric(10,0),
    nombre character varying(50) NOT NULL,
    fecha_inicio timestamp without time zone,
    descripcion character varying(2000),
    responsable_id numeric(10,0)
);

CREATE  INDEX IE_prd_producto_iniciativaid ON prd_producto USING btree (iniciativa_id   ASC);

--
-- TOC entry 199 (class 1259 OID 276861)
-- Dependencies: 6
-- Name: prd_seg_producto; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE prd_seg_producto (
    iniciativa_id numeric(10,0) NOT NULL,
    producto_id numeric(10,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    fecha_ini timestamp without time zone,
    fecha_fin timestamp without time zone,
    alerta numeric(1,0)
);

CREATE  INDEX IE_prd_seg_producto_inicitiva ON prd_seg_producto USING btree (iniciativa_id   ASC);
CREATE  INDEX IE_prd_seg_producto_producto ON prd_seg_producto USING btree (producto_id   ASC);

--
-- TOC entry 200 (class 1259 OID 276864)
-- Dependencies: 6
-- Name: prd_seguimiento; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE prd_seguimiento (
    iniciativa_id numeric(10,0) NOT NULL,
    ano numeric(4,0) NOT NULL,
    periodo numeric(3,0) NOT NULL,
    fecha timestamp without time zone,
    alerta numeric(1,0),
    seguimiento text
);

CREATE  INDEX xif1prd_seguimiento ON prd_seguimiento USING btree (iniciativa_id   ASC);

--
-- TOC entry 201 (class 1259 OID 276870)
-- Dependencies: 6
-- Name: problema; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE problema (
    problema_id numeric(10,0) NOT NULL,
    organizacion_id numeric(10,0) NOT NULL,
    clase_id numeric(10,0) NOT NULL,
    indicador_efecto_id numeric(10,0),
    iniciativa_efecto_id numeric(10,0),
    fecha timestamp without time zone NOT NULL,
    responsable_id numeric(10,0),
    auxiliar_id numeric(10,0),
    estado_id numeric(10,0),
    fecha_estimada_inicio timestamp without time zone,
    fecha_estimada_final timestamp without time zone,
    fecha_real_inicio timestamp without time zone,
    fecha_real_final timestamp without time zone,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    nombre character varying(50) NOT NULL,
    read_only numeric(1,0)
);


--
-- TOC entry 202 (class 1259 OID 276873)
-- Dependencies: 6
-- Name: problema_memo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE problema_memo (
    problema_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    memo text
);


--
-- TOC entry 203 (class 1259 OID 276879)
-- Dependencies: 6
-- Name: pry_actividad; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_actividad (
    actividad_id numeric(10,0) NOT NULL,
    padre_id numeric(10,0),
    proyecto_id numeric(10,0) NOT NULL,
    nombre character varying(250) NOT NULL,
    descripcion text,
    comienzo_plan timestamp without time zone,
    comienzo_real timestamp without time zone,
    fin_plan timestamp without time zone,
    fin_real timestamp without time zone,
    duracion_plan double precision,
    duracion_real double precision,
    unidad_id numeric(10,0),
    fila numeric(10,0),
    nivel numeric(4,0),
    compuesta numeric(1,0),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    indicador_id numeric(10,0),
    naturaleza numeric(1,0),
    clase_id numeric(10,0),
    resp_fijar_meta_id numeric(10,0),
    resp_lograr_meta_id numeric(10,0),
    resp_seguimiento_id numeric(10,0),
    resp_cargar_ejecutado_id numeric(10,0),
    resp_cargar_meta_id numeric(10,0),
    tipo_medicion numeric(1,0),
    objeto_asociado_id numeric(10,0),
    objeto_asociado_plan_id numeric(10,0),
	objeto_asociado_className character varying(50) NULL,
	crecimiento numeric(1,0),
	porcentaje_completado double precision,
	porcentaje_esperado double precision,
	porcentaje_ejecutado double precision,
	fecha_ultima_medicion character varying(10) NULL
);


--
-- TOC entry 205 (class 1259 OID 276888)
-- Dependencies: 6
-- Name: pry_calendario; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_calendario (
    calendario_id numeric(10,0) NOT NULL,
    nombre character varying(50),
    dom numeric(1,0),
    lun numeric(1,0),
    mar numeric(1,0),
    mie numeric(1,0),
    jue numeric(1,0),
    vie numeric(1,0),
    sab numeric(1,0),
    proyecto_id numeric(10,0)
);


--
-- TOC entry 206 (class 1259 OID 276891)
-- Dependencies: 6
-- Name: pry_calendario_detalle; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_calendario_detalle (
    calendario_id numeric(10,0) NOT NULL,
    fecha timestamp without time zone,
    feriado numeric(1,0)
);


--
-- TOC entry 207 (class 1259 OID 276894)
-- Dependencies: 6
-- Name: pry_columna; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_columna (
    columna_id numeric(10,0) NOT NULL,
    nombre character varying(50),
    numerico numeric(1,0),
    alineacion numeric(1,0),
    formato_java character varying(50),
    tamano_java numeric(4,0)
);


--
-- TOC entry 208 (class 1259 OID 276897)
-- Dependencies: 6
-- Name: pry_columna_por_vista; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_columna_por_vista (
    vista_id numeric(10,0) NOT NULL,
    columna_id numeric(10,0) NOT NULL,
    alineacion numeric(1,0),
    ancho_java numeric(4,0),
    orden numeric(2,0)
);


--
-- TOC entry 209 (class 1259 OID 276900)
-- Dependencies: 6
-- Name: pry_proyecto; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_proyecto (
    proyecto_id numeric(10,0) NOT NULL,
    nombre character varying(250),
    comienzo date,
    comienzo_plan date,
    comienzo_real date,
    fin_plan date,
    fin_real date,
    duracion_plan double precision,
    duracion_real double precision,
    variacion_comienzo double precision,
    variacion_fin double precision,
    variacion_duracion double precision,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0)
);


--
-- TOC entry 210 (class 1259 OID 276903)
-- Dependencies: 6
-- Name: pry_vista; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pry_vista (
    vista_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 276906)
-- Dependencies: 6
-- Name: responsable; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE responsable (
    responsable_id numeric(10,0) NOT NULL,
    usuario_id numeric(10,0),
    nombre character varying(50) NOT NULL,
    cargo character varying(50) NOT NULL,
    ubicacion character varying(250),
    email character varying(50),
    notas text,
    children_count numeric(1,0),
    tipo numeric(1,0) NOT NULL,
    grupo numeric(1,0) NOT NULL,
    organizacion_id numeric(10,0)
);


--
-- TOC entry 212 (class 1259 OID 276912)
-- Dependencies: 6
-- Name: responsable_grupo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE responsable_grupo (
    padre_id numeric(10,0) NOT NULL,
    responsable_id numeric(10,0) NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 276915)
-- Dependencies: 6
-- Name: responsable_por_accion; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE responsable_por_accion (
    accion_id numeric(10,0) NOT NULL,
    responsable_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 276918)
-- Dependencies: 6
-- Name: seguimiento; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE seguimiento (
    seguimiento_id numeric(10,0) NOT NULL,
    accion_id numeric(10,0) NOT NULL,
    estado_id numeric(10,0),
    fecha_emision timestamp without time zone,
    emision_enviado numeric(1,0),
    fecha_recepcion timestamp without time zone,
    recepcion_enviado numeric(1,0),
    fecha_aprobacion timestamp without time zone,
    preparado_por character varying(50),
    numero_de_reporte numeric(5,0),
    aprobado numeric(1,0),
    aprobado_por character varying(50),
    clave_correo character varying(50),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    read_only numeric(1,0)
);


--
-- TOC entry 215 (class 1259 OID 276921)
-- Dependencies: 6
-- Name: seguimiento_memo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE seguimiento_memo (
    seguimiento_id numeric(10,0) NOT NULL,
    tipo numeric(1,0) NOT NULL,
    memo text
);


--
-- TOC entry 216 (class 1259 OID 276927)
-- Dependencies: 6
-- Name: seguimiento_mensaje_email; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE seguimiento_mensaje_email (
    mensaje_id numeric(10,0) NOT NULL,
    descripcion character varying(50) NOT NULL,
    mensaje text NOT NULL,
    acuse_recibo numeric(1,0),
    solo_auxiliar numeric(1,0),
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0)
);


--
-- TOC entry 217 (class 1259 OID 276933)
-- Dependencies: 6
-- Name: serie_indicador; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE serie_indicador (
    serie_id numeric(10,0) NOT NULL,
    indicador_id numeric(10,0) NOT NULL,
    naturaleza numeric(1,0),
    fecha_bloqueo timestamp without time zone
);


--
-- TOC entry 218 (class 1259 OID 276936)
-- Dependencies: 6
-- Name: serie_plan; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE serie_plan (
    plan_id numeric(10,0) NOT NULL,
    serie_id numeric(10,0) NOT NULL
);


--
-- TOC entry 219 (class 1259 OID 276939)
-- Dependencies: 6
-- Name: serie_tiempo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE serie_tiempo 
(
    serie_id numeric(10,0) NOT NULL,
    nombre character varying(50) NOT NULL,
    fija numeric(1,0) NOT NULL,
    oculta numeric(1,0) NOT NULL,
    identificador character varying(5) NOT NULL,
    preseleccionada numeric(1,0) NOT NULL
);

CREATE UNIQUE INDEX ak_serie_tiempo ON serie_tiempo (serie_id   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo PRIMARY KEY (serie_id);

CREATE UNIQUE INDEX ak_serie_tiempo_nombre ON serie_tiempo (nombre   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_nombre UNIQUE (nombre);

CREATE UNIQUE INDEX ak_serie_tiempo_identificador ON serie_tiempo (identificador   ASC);

ALTER TABLE serie_tiempo ADD CONSTRAINT pk_serie_tiempo_identificador UNIQUE (identificador);

--
-- TOC entry 220 (class 1259 OID 276942)
-- Dependencies: 6
-- Name: tipo_moneda; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE tipo_moneda (
    tipo_moneda_id numeric(10,0) NOT NULL,
    moneda character varying(50) NOT NULL,
    identificador character varying(5) NOT NULL,
    factor_conversion_bolivares double precision NOT NULL,
    creado timestamp without time zone,
    modificado timestamp without time zone,
    creado_id numeric(10,0),
    modificado_id numeric(10,0),
    solo_lectura numeric(1,0)
);


--
-- TOC entry 221 (class 1259 OID 276945)
-- Dependencies: 6
-- Name: unidad; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE unidad (
    unidad_id numeric(10,0) NOT NULL,
    nombre character varying(100) NOT NULL,
    tipo numeric(2,0)
);


--
-- TOC entry 222 (class 1259 OID 276948)
-- Dependencies: 6
-- Name: vista; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vista
(
	vista_id             numeric(10,0) NOT NULL ,
	organizacion_id      numeric(10,0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	descripcion          text NULL ,
	visible              numeric(1,0),
	fecha_inicio         character varying(10),
	fecha_fin            character varying(10) 
);

ALTER TABLE vista ADD CONSTRAINT pk_vista PRIMARY KEY (vista_id);
ALTER TABLE vista ADD CONSTRAINT ak_vista_nombre UNIQUE (organizacion_id, nombre);
CREATE UNIQUE INDEX ak_vista ON vista USING btree (organizacion_id   ASC,nombre   ASC);
CREATE UNIQUE INDEX ie_vista ON vista USING btree (vista_id   ASC);
CREATE INDEX ie_vista_organizacion ON vista USING btree (organizacion_id   ASC);

CREATE TABLE reporte
(
  reporte_id numeric(10) NOT NULL,
  organizacion_id numeric(10) NOT NULL,
  nombre character varying(50) NOT NULL,
  configuracion text NOT NULL,
  descripcion          character varying(1000) NULL ,
  publico              NUMERIC(1) NOT NULL ,
  tipo                 NUMERIC(1) NOT NULL ,
  corte                NUMERIC(1) NOT NULL ,
  usuario_id           NUMERIC(10) NOT NULL,
  CONSTRAINT pk_reporte PRIMARY KEY (reporte_id),
  CONSTRAINT fk_organizacion_reporte FOREIGN KEY (organizacion_id)
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT FK_usuario_reporte FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT ak_reporte UNIQUE (organizacion_id, nombre)
);

CREATE INDEX IE_usuario_reporte ON reporte USING btree (usuario_id   ASC);

CREATE TABLE grafico
(
  grafico_id numeric(10) NOT NULL,
  organizacion_id numeric(10) NOT NULL,
  nombre character varying(100) NOT NULL,
  configuracion text NOT NULL,
  objeto_id numeric(10) NULL,
  className character varying(50) NULL,
  usuario_id numeric(10),
  CONSTRAINT pk_grafico PRIMARY KEY (grafico_id),
  CONSTRAINT fk_organizacion_grafico FOREIGN KEY (organizacion_id) 
      REFERENCES organizacion (organizacion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_usuario_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

ALTER TABLE grafico ADD CONSTRAINT ak_grafico UNIQUE (organizacion_id, usuario_id, nombre, objeto_id);
--
-- TOC entry 2161 (class 2606 OID 279872)
-- Dependencies: 129 129 129 129
-- Name: ak1_accion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY accion
    ADD CONSTRAINT ak_accion UNIQUE (problema_id, padre_id, nombre);


--
-- TOC entry 2185 (class 2606 OID 279874)
-- Dependencies: 139 139
-- Name: ak1_afw_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_grupo
    ADD CONSTRAINT ak1_afw_grupo UNIQUE (grupo);


--
-- TOC entry 2201 (class 2606 OID 279876)
-- Dependencies: 147 147
-- Name: ak1_afw_objeto_sistema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_objeto_sistema
    ADD CONSTRAINT ak1_afw_objeto_sistema UNIQUE (nombre_singular);


--
-- TOC entry 2205 (class 2606 OID 279878)
-- Dependencies: 148 148 148
-- Name: ak1_afw_permiso; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_permiso
    ADD CONSTRAINT ak1_afw_permiso UNIQUE (padre_id, permiso_id);


--
-- TOC entry 2220 (class 2606 OID 279880)
-- Dependencies: 153 153
-- Name: ak1_afw_usuario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_usuario
    ADD CONSTRAINT ak1_afw_usuario UNIQUE (u_id);


--
-- TOC entry 2229 (class 2606 OID 279882)
-- Dependencies: 156 156
-- Name: ak1_categoria; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categoria
    ADD CONSTRAINT ak1_categoria UNIQUE (nombre);


--
-- TOC entry 2237 (class 2606 OID 279884)
-- Dependencies: 158 158 158
-- Name: ak1_causa; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY causa
    ADD CONSTRAINT ak1_causa UNIQUE (padre_id, nombre);


--
-- TOC entry 2251 (class 2606 OID 439188)
-- Dependencies: 161 161 161 161
-- Name: ak1_clase; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clase
    ADD CONSTRAINT ak1_clase UNIQUE (padre_id, organizacion_id, nombre);


--
-- TOC entry 2257 (class 2606 OID 279890)
-- Dependencies: 162 162 162 162
-- Name: ak1_clase_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clase_problema
    ADD CONSTRAINT ak_clase_problema UNIQUE (organizacion_id, padre_id, nombre);


--
-- TOC entry 2268 (class 2606 OID 279892)
-- Dependencies: 164 164 164
-- Name: ak1_cuenta; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cuenta
    ADD CONSTRAINT ak1_cuenta UNIQUE (padre_id, codigo);


--
-- TOC entry 2273 (class 2606 OID 279894)
-- Dependencies: 165 165
-- Name: ak1_estado_acciones; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_acciones
    ADD CONSTRAINT ak1_estado_acciones UNIQUE (nombre);


--
-- TOC entry 2357 (class 2606 OID 279900)
-- Dependencies: 187 187
-- Name: ak1_metodologia; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY metodologia
    ADD CONSTRAINT ak1_metodologia UNIQUE (nombre);


--
-- TOC entry 2361 (class 2606 OID 279902)
-- Dependencies: 188 188 188
-- Name: ak1_metodologia_template; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY metodologia_template
    ADD CONSTRAINT ak1_metodologia_template UNIQUE (metodologia_id, nombre);


--
-- TOC entry 2372 (class 2606 OID 279904)
-- Dependencies: 191 191 191
-- Name: ak1_organizacion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizacion
    ADD CONSTRAINT ak1_organizacion UNIQUE (padre_id, nombre);


--
-- TOC entry 2383 (class 2606 OID 279906)
-- Dependencies: 194 194 194 194 194
-- Name: ak1_perspectiva; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY perspectiva
    ADD CONSTRAINT ak_perspectiva UNIQUE (plan_id, padre_id, nombre, ano);


--
-- TOC entry 2414 (class 2606 OID 279910)
-- Dependencies: 201 201 201 201
-- Name: ak1_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY problema
    ADD CONSTRAINT ak_problema UNIQUE (organizacion_id, clase_id, nombre);


--
-- TOC entry 2435 (class 2606 OID 279912)
-- Dependencies: 205 205 205
-- Name: ak1_pry_calendario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_calendario
    ADD CONSTRAINT ak1_pry_calendario UNIQUE (proyecto_id, nombre);


--
-- TOC entry 2443 (class 2606 OID 279914)
-- Dependencies: 207 207
-- Name: ak1_pry_columna; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_columna
    ADD CONSTRAINT ak1_pry_columna UNIQUE (nombre);


--
-- TOC entry 2453 (class 2606 OID 279916)
-- Dependencies: 210 210
-- Name: ak1_pry_vista; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_vista
    ADD CONSTRAINT ak1_pry_vista UNIQUE (nombre);


--
-- TOC entry 2457 (class 2606 OID 279918)
-- Dependencies: 211 211 211 211
-- Name: ak1_responsable; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY responsable ADD CONSTRAINT ak_responsable UNIQUE (organizacion_id, nombre, cargo);


--
-- TOC entry 2494 (class 2606 OID 279922)
-- Dependencies: 220 220
-- Name: ak1_tipo_moneda; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tipo_moneda
    ADD CONSTRAINT ak1_tipo_moneda UNIQUE (moneda);


--
-- TOC entry 2500 (class 2606 OID 279924)
-- Dependencies: 221 221
-- Name: ak1_unidad; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY unidad
    ADD CONSTRAINT ak1_unidad UNIQUE (nombre);
	

--
-- TOC entry 2496 (class 2606 OID 279930)
-- Dependencies: 220 220
-- Name: ak2_tipo_moneda; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tipo_moneda
    ADD CONSTRAINT ak2_tipo_moneda UNIQUE (identificador);


--
-- TOC entry 2367 (class 2606 OID 279934)
-- Dependencies: 189 189
-- Name: modelo_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE modelo 
	ADD CONSTRAINT pk_id_plan_modelo PRIMARY KEY (modelo_id,plan_id);

--
-- TOC entry 2163 (class 2606 OID 279936)
-- Dependencies: 129 129
-- Name: pk_accion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY accion
    ADD CONSTRAINT pk_accion PRIMARY KEY (accion_id);


--
-- TOC entry 2168 (class 2606 OID 279938)
-- Dependencies: 130 130 130
-- Name: pk_afw_auditoria_entero; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_entero
    ADD CONSTRAINT pk_afw_auditoria_entero PRIMARY KEY (fecha, instancia_id, nombre_atributo);


CREATE UNIQUE INDEX UN_afw_auditoria_entero ON afw_auditoria_entero USING btree
(fecha   ASC,instancia_id   ASC,nombre_atributo   ASC);

CREATE  INDEX IF_afw_auditoria_entero ON afw_auditoria_entero USING btree (usuario_id   ASC);

--
-- TOC entry 2170 (class 2606 OID 279940)
-- Dependencies: 131 131 131 131
-- Name: pk_afw_auditoria_evento; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_evento
    ADD CONSTRAINT pk_afw_auditoria_evento PRIMARY KEY (fecha, instancia_id, tipo_evento);


--
-- TOC entry 2172 (class 2606 OID 279942)
-- Dependencies: 132 132 132 132
-- Name: pk_afw_auditoria_fecha; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_fecha
    ADD CONSTRAINT pk_afw_auditoria_fecha PRIMARY KEY (fecha, instancia_id, nombre_atributo);


--
-- TOC entry 2174 (class 2606 OID 279944)
-- Dependencies: 133 133 133 133
-- Name: pk_afw_auditoria_flotante; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_flotante
    ADD CONSTRAINT pk_afw_auditoria_flotante PRIMARY KEY (fecha, instancia_id, nombre_atributo);


--
-- TOC entry 2176 (class 2606 OID 279946)
-- Dependencies: 134 134 134 134
-- Name: pk_afw_auditoria_memo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_memo
    ADD CONSTRAINT pk_afw_auditoria_memo PRIMARY KEY (fecha, instancia_id, nombre_atributo);


--
-- TOC entry 2178 (class 2606 OID 279948)
-- Dependencies: 135 135 135 135
-- Name: pk_afw_auditoria_string; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_auditoria_string
    ADD CONSTRAINT pk_afw_auditoria_string PRIMARY KEY (fecha, instancia_id, nombre_atributo);


--
-- TOC entry 2180 (class 2606 OID 279950)
-- Dependencies: 136 136 136 136
-- Name: pk_afw_config_usuario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_config_usuario
    ADD CONSTRAINT pk_afw_config_usuario PRIMARY KEY (usuario_id, objeto, configuracion_base);


--
-- TOC entry 2183 (class 2606 OID 279952)
-- Dependencies: 137 137
-- Name: pk_afw_configuracion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_configuracion
    ADD CONSTRAINT pk_afw_configuracion PRIMARY KEY (parametro);


--
-- TOC entry 2187 (class 2606 OID 279954)
-- Dependencies: 139 139
-- Name: pk_afw_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_grupo
    ADD CONSTRAINT pk_afw_grupo PRIMARY KEY (grupo_id);


--
-- TOC entry 2189 (class 2606 OID 279956)
-- Dependencies: 141 141 141
-- Name: pk_afw_lock; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_lock
    ADD CONSTRAINT pk_afw_lock PRIMARY KEY (objeto_id, tipo);


--
-- TOC entry 2191 (class 2606 OID 279958)
-- Dependencies: 142 142 142
-- Name: pk_afw_lock_read; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_lock_read
    ADD CONSTRAINT pk_afw_lock_read PRIMARY KEY (objeto_id, instancia);


--
-- TOC entry 2193 (class 2606 OID 279960)
-- Dependencies: 143 143
-- Name: pk_afw_message_resource; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_message_resource
    ADD CONSTRAINT pk_afw_message_resource PRIMARY KEY (clave);


--
-- TOC entry 2195 (class 2606 OID 279962)
-- Dependencies: 144 144
-- Name: pk_afw_objeto_auditable; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_objeto_auditable
    ADD CONSTRAINT pk_afw_objeto_auditable PRIMARY KEY (objeto_id);


--
-- TOC entry 2197 (class 2606 OID 279964)
-- Dependencies: 145 145 145
-- Name: pk_afw_objeto_auditable_atributo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_objeto_auditable_atributo
    ADD CONSTRAINT pk_afw_objeto_auditable_atributo PRIMARY KEY (objeto_id, nombre_atributo);


--
-- TOC entry 2199 (class 2606 OID 279966)
-- Dependencies: 146 146
-- Name: pk_afw_objeto_binario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_objeto_binario
    ADD CONSTRAINT pk_afw_objeto_binario PRIMARY KEY (objeto_binario_id);


--
-- TOC entry 2203 (class 2606 OID 279968)
-- Dependencies: 147 147
-- Name: pk_afw_objeto_sistema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_objeto_sistema
    ADD CONSTRAINT pk_afw_objeto_sistema PRIMARY KEY (objeto_id);


--
-- TOC entry 2207 (class 2606 OID 279970)
-- Dependencies: 148 148
-- Name: pk_afw_permiso; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_permiso
    ADD CONSTRAINT pk_afw_permiso PRIMARY KEY (permiso_id);


--
-- TOC entry 2210 (class 2606 OID 279972)
-- Dependencies: 149 149 149
-- Name: pk_afw_permiso_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_permiso_grupo
    ADD CONSTRAINT pk_afw_permiso_grupo PRIMARY KEY (permiso_id, grupo_id);


--
-- TOC entry 2214 (class 2606 OID 279974)
-- Dependencies: 150 150
-- Name: pk_afw_plantilla; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_plantilla
    ADD CONSTRAINT pk_afw_plantilla PRIMARY KEY (plantilla_id);


--
-- TOC entry 2217 (class 2606 OID 279976)
-- Dependencies: 152 152
-- Name: pk_afw_usersession; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_user_session
    ADD CONSTRAINT pk_afw_usersession PRIMARY KEY (session_id);


--
-- TOC entry 2224 (class 2606 OID 279980)
-- Dependencies: 154 154 154 154
-- Name: pk_afw_usuario_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY afw_usuario_grupo
    ADD CONSTRAINT pk_afw_usuario_grupo PRIMARY KEY (usuario_id, grupo_id, organizacion_id);


--
-- TOC entry 2231 (class 2606 OID 279982)
-- Dependencies: 156 156
-- Name: pk_categoria; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (categoria_id);


--
-- TOC entry 2233 (class 2606 OID 279984)
-- Dependencies: 157 157 157
-- Name: pk_categoria_por_indicador; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categoria_por_indicador
    ADD CONSTRAINT pk_categoria_por_indicador PRIMARY KEY (indicador_id, categoria_id);


--
-- TOC entry 2240 (class 2606 OID 279986)
-- Dependencies: 158 158
-- Name: pk_causa; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY causa
    ADD CONSTRAINT pk_causa PRIMARY KEY (causa_id);


--
-- TOC entry 2242 (class 2606 OID 279988)
-- Dependencies: 159 159 159
-- Name: pk_causa_por_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY causa_por_problema
    ADD CONSTRAINT pk_causa_por_problema PRIMARY KEY (problema_id, causa_id);


--
-- TOC entry 2253 (class 2606 OID 279992)
-- Dependencies: 161 161
-- Name: pk_clase; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clase
    ADD CONSTRAINT pk_clase PRIMARY KEY (clase_id);


--
-- TOC entry 2259 (class 2606 OID 279994)
-- Dependencies: 162 162
-- Name: pk_clase_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY clase_problema
    ADD CONSTRAINT pk_clase_problema PRIMARY KEY (clase_id);


--
-- TOC entry 2271 (class 2606 OID 279998)
-- Dependencies: 164 164
-- Name: pk_cuenta; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY cuenta
    ADD CONSTRAINT pk_cuenta PRIMARY KEY (cuenta_id);


--
-- TOC entry 2275 (class 2606 OID 280000)
-- Dependencies: 165 165
-- Name: pk_estado_acciones; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY estado_acciones
    ADD CONSTRAINT pk_estado_acciones PRIMARY KEY (estado_id);


--
-- TOC entry 2277 (class 2606 OID 280002)
-- Dependencies: 166 166
-- Name: pk_explicacion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY explicacion
    ADD CONSTRAINT pk_explicacion PRIMARY KEY (explicacion_id);


--
-- TOC entry 2279 (class 2606 OID 280004)
-- Dependencies: 167 167 167
-- Name: pk_explicacion_adjunto; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY explicacion_adjunto
    ADD CONSTRAINT pk_explicacion_adjunto PRIMARY KEY (explicacion_id, adjunto_id);


--
-- TOC entry 2282 (class 2606 OID 280006)
-- Dependencies: 168 168 168
-- Name: pk_explicacion_memo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY explicacion_memo
    ADD CONSTRAINT pk_explicacion_memo PRIMARY KEY (explicacion_id, tipo);


--
-- TOC entry 2290 (class 2606 OID 280010)
-- Dependencies: 170 170
-- Name: pk_foro; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY foro
    ADD CONSTRAINT pk_foro PRIMARY KEY (foro_id);


--
-- TOC entry 2293 (class 2606 OID 280012)
-- Dependencies: 171 171
-- Name: pk_inc_actividad; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY inc_actividad
    ADD CONSTRAINT pk_inc_actividad PRIMARY KEY (actividad_id);


--
-- TOC entry 2306 (class 2606 OID 280018)
-- Dependencies: 174 174 174 174 174 174
-- Name: pk_indicador_estado; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY indicador_estado
    ADD CONSTRAINT pk_indicador_estado PRIMARY KEY (plan_id, indicador_id, tipo, ano, periodo);

CREATE INDEX ie_indicadorid_indicador_estad ON indicador_estado USING btree (indicador_id);
CREATE INDEX ie_planid_indicador_estado ON indicador_estado USING btree (plan_id);  

--
-- TOC entry 2308 (class 2606 OID 280020)
-- Dependencies: 175 175 175 175
-- Name: pk_indicador_por_celda; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY indicador_por_celda
    ADD CONSTRAINT pk_indicador_por_celda PRIMARY KEY (celda_id, serie_id, indicador_id);


--
-- TOC entry 2313 (class 2606 OID 280022)
-- Dependencies: 176 176 176
-- Name: pk_indicador_por_perspectiva; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY indicador_por_perspectiva
    ADD CONSTRAINT pk_indicador_por_perspectiva PRIMARY KEY (perspectiva_id, indicador_id);


--
-- TOC entry 2317 (class 2606 OID 280024)
-- Dependencies: 177 177 177
-- Name: pk_indicador_por_plan; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY indicador_por_plan
    ADD CONSTRAINT pk_indicador_por_plan PRIMARY KEY (plan_id, indicador_id);


--
-- TOC entry 2323 (class 2606 OID 280026)
-- Dependencies: 178 178
-- Name: pk_iniciativa; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY iniciativa
    ADD CONSTRAINT pk_iniciativa PRIMARY KEY (iniciativa_id);


--
-- TOC entry 2328 (class 2606 OID 280028)
-- Dependencies: 179 179 179
-- Name: pk_iniciativa_ano; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY iniciativa_ano
    ADD CONSTRAINT pk_iniciativa_ano PRIMARY KEY (iniciativa_id, ano);


--
-- TOC entry 2331 (class 2606 OID 280030)
-- Dependencies: 180 180
-- Name: pk_iniciativa_objeto; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY iniciativa_objeto
    ADD CONSTRAINT pk_iniciativa_objeto PRIMARY KEY (iniciativa_id);

--
-- TOC entry 2334 (class 2606 OID 280032)
-- Dependencies: 181 181 181
-- Name: pk_iniciativa_por_perspectiva; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY iniciativa_por_perspectiva
    ADD CONSTRAINT pk_iniciativa_por_perspectiva PRIMARY KEY (perspectiva_id, iniciativa_id);

--
-- TOC entry 2343 (class 2606 OID 280036)
-- Dependencies: 183 183
-- Name: pk_mascara_codigo_plan_cuentas; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mascara_codigo_plan_cuentas
    ADD CONSTRAINT pk_mascara_codigo_plan_cuentas PRIMARY KEY (mascara_id);


--
-- TOC entry 2346 (class 2606 OID 280038)
-- Dependencies: 184 184 184
-- Name: pk_mascara_codigo_plan_cuentas_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY masc_cod_plan_cuentas_grupo
    ADD CONSTRAINT pk_masc_cod_plan_cuentas_grupo PRIMARY KEY (mascara_id, nivel);


--
-- TOC entry 2348 (class 2606 OID 280040)
-- Dependencies: 185 185 185 185 185
-- Name: pk_medicion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY medicion
    ADD CONSTRAINT pk_medicion PRIMARY KEY (indicador_id, serie_id, ano, periodo);


--
-- TOC entry 2421 (class 2606 OID 280042)
-- Dependencies: 202 202 202
-- Name: pk_memo_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY problema_memo
    ADD CONSTRAINT pk_memo_problema PRIMARY KEY (problema_id, tipo);


--
-- TOC entry 2353 (class 2606 OID 280044)
-- Dependencies: 186 186 186 186 186 186 186
-- Name: pk_meta; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY meta
    ADD CONSTRAINT pk_meta PRIMARY KEY (plan_id, indicador_id, serie_id, tipo, ano, periodo);


--
-- TOC entry 2359 (class 2606 OID 280046)
-- Dependencies: 187 187
-- Name: pk_metodologia; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY metodologia
    ADD CONSTRAINT pk_metodologia PRIMARY KEY (metodologia_id);


--
-- TOC entry 2363 (class 2606 OID 280048)
-- Dependencies: 188 188
-- Name: pk_metodologia_template; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY metodologia_template
    ADD CONSTRAINT pk_metodologia_template PRIMARY KEY (metodologia_template_id);


--
-- TOC entry 2377 (class 2606 OID 280052)
-- Dependencies: 192 192 192
-- Name: pk_organizacion_memo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY organizacion_memo
    ADD CONSTRAINT pk_organizacion_memo PRIMARY KEY (organizacion_id, tipo);

--
-- TOC entry 2385 (class 2606 OID 280056)
-- Dependencies: 194 194
-- Name: pk_perspectiva; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY perspectiva
    ADD CONSTRAINT pk_perspectiva PRIMARY KEY (perspectiva_id);


--
-- TOC entry 2390 (class 2606 OID 280058)
-- Dependencies: 195 195 195 195 195
-- Name: pk_perspectiva_estado; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY perspectiva_nivel
    ADD CONSTRAINT pk_perspectiva_estado PRIMARY KEY (perspectiva_id, tipo, ano, periodo);


--
-- TOC entry 2401 (class 2606 OID 280062)
-- Dependencies: 197 197 197 197 197
-- Name: pk_plan_estado; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY plan_nivel
    ADD CONSTRAINT pk_plan_estado PRIMARY KEY (plan_id, tipo, ano, periodo);


--
-- TOC entry 2403 (class 2606 OID 280064)
-- Dependencies: 198 198
-- Name: pk_prd_producto; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY prd_producto
    ADD CONSTRAINT pk_prd_producto PRIMARY KEY (producto_id);


--
-- TOC entry 2407 (class 2606 OID 280066)
-- Dependencies: 199 199 199 199 199
-- Name: pk_prd_seg_producto; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY prd_seg_producto
    ADD CONSTRAINT pk_prd_seg_producto PRIMARY KEY (iniciativa_id, producto_id, ano, periodo);


--
-- TOC entry 2411 (class 2606 OID 280068)
-- Dependencies: 200 200 200 200
-- Name: pk_prd_seguimiento; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY prd_seguimiento
    ADD CONSTRAINT pk_prd_seguimiento PRIMARY KEY (iniciativa_id, ano, periodo);


--
-- TOC entry 2416 (class 2606 OID 280070)
-- Dependencies: 201 201
-- Name: pk_problema; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY problema
    ADD CONSTRAINT pk_problema PRIMARY KEY (problema_id);


--
-- TOC entry 2424 (class 2606 OID 280072)
-- Dependencies: 203 203
-- Name: pk_pry_actividad; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_actividad
    ADD CONSTRAINT pk_pry_actividad PRIMARY KEY (actividad_id);


--
-- TOC entry 2437 (class 2606 OID 280076)
-- Dependencies: 205 205
-- Name: pk_pry_calendario; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_calendario
    ADD CONSTRAINT pk_pry_calendario PRIMARY KEY (calendario_id);


--
-- TOC entry 2440 (class 2606 OID 280078)
-- Dependencies: 206 206
-- Name: pk_pry_calendario_detalle; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_calendario_detalle
    ADD CONSTRAINT pk_pry_calendario_detalle PRIMARY KEY (calendario_id);


--
-- TOC entry 2445 (class 2606 OID 280080)
-- Dependencies: 207 207
-- Name: pk_pry_columna; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_columna
    ADD CONSTRAINT pk_pry_columna PRIMARY KEY (columna_id);


--
-- TOC entry 2447 (class 2606 OID 280082)
-- Dependencies: 208 208 208
-- Name: pk_pry_columna_por_vista; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_columna_por_vista
    ADD CONSTRAINT pk_pry_columna_por_vista PRIMARY KEY (vista_id, columna_id);


--
-- TOC entry 2451 (class 2606 OID 280084)
-- Dependencies: 209 209
-- Name: pk_pry_proyecto; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_proyecto
    ADD CONSTRAINT pk_pry_proyecto PRIMARY KEY (proyecto_id);


--
-- TOC entry 2455 (class 2606 OID 280086)
-- Dependencies: 210 210
-- Name: pk_pry_vista; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pry_vista
    ADD CONSTRAINT pk_pry_vista PRIMARY KEY (vista_id);


--
-- TOC entry 2460 (class 2606 OID 280088)
-- Dependencies: 211 211
-- Name: pk_responsable; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY responsable
    ADD CONSTRAINT pk_responsable PRIMARY KEY (responsable_id);


--
-- TOC entry 2465 (class 2606 OID 280090)
-- Dependencies: 212 212 212
-- Name: pk_responsable_grupo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY responsable_grupo
    ADD CONSTRAINT pk_responsable_grupo PRIMARY KEY (padre_id, responsable_id);


--
-- TOC entry 2471 (class 2606 OID 280092)
-- Dependencies: 214 214
-- Name: pk_seguimiento; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY seguimiento
    ADD CONSTRAINT pk_seguimiento PRIMARY KEY (seguimiento_id);


--
-- TOC entry 2475 (class 2606 OID 280094)
-- Dependencies: 215 215 215
-- Name: pk_seguimiento_memo; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY seguimiento_memo
    ADD CONSTRAINT pk_seguimiento_memo PRIMARY KEY (seguimiento_id, tipo);


--
-- TOC entry 2478 (class 2606 OID 280096)
-- Dependencies: 216 216
-- Name: pk_seguimiento_mensaje_email; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY seguimiento_mensaje_email
    ADD CONSTRAINT pk_seguimiento_mensaje_email PRIMARY KEY (mensaje_id);


--
-- TOC entry 2480 (class 2606 OID 280098)
-- Dependencies: 217 217 217
-- Name: pk_serie_indicador; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY serie_indicador
    ADD CONSTRAINT pk_serie_indicador PRIMARY KEY (indicador_id, serie_id);


--
-- TOC entry 2498 (class 2606 OID 280102)
-- Dependencies: 220 220
-- Name: pk_tipo_moneda; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tipo_moneda
    ADD CONSTRAINT pk_tipo_moneda PRIMARY KEY (tipo_moneda_id);


--
-- TOC entry 2502 (class 2606 OID 280104)
-- Dependencies: 221 221
-- Name: pk_unidad; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY unidad
    ADD CONSTRAINT pk_unidad PRIMARY KEY (unidad_id);
	

--
-- TOC entry 2469 (class 2606 OID 280108)
-- Dependencies: 213 213 213
-- Name: xpkresponsable_por_accion; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY responsable_por_accion
    ADD CONSTRAINT xpkresponsable_por_accion PRIMARY KEY (accion_id, responsable_id);


--
-- TOC entry 2486 (class 2606 OID 280110)
-- Dependencies: 218 218 218
-- Name: xpkserie_plan; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY serie_plan
    ADD CONSTRAINT xpkserie_plan PRIMARY KEY (plan_id, serie_id);



--
-- TOC entry 2365 (class 1259 OID 280113)
-- Dependencies: 189
-- Name: ie_modelo_plan_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX ak_modelo ON modelo USING btree (modelo_id, plan_id);
CREATE INDEX IE_plan_modelo ON modelo USING btree (plan_id);

--
-- TOC entry 2238 (class 1259 OID 280114)
-- Dependencies: 158
-- Name: if_causa_padre_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_causa_padre_id ON causa USING btree (padre_id);


--
-- TOC entry 2269 (class 1259 OID 280115)
-- Dependencies: 164
-- Name: if_cuenta_padre_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_cuenta_padre_id ON cuenta USING btree (padre_id);


--
-- TOC entry 2344 (class 1259 OID 280116)
-- Dependencies: 184
-- Name: if_masc_cod_plan_cuentas_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_masc_cod_plan_cuentas_grupo ON masc_cod_plan_cuentas_grupo USING btree (mascara_id);


--
-- TOC entry 2373 (class 1259 OID 280117)
-- Dependencies: 191
-- Name: if_organizacion_padre_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_organizacion_padre_id ON organizacion USING btree (padre_id);


--
-- TOC entry 2462 (class 1259 OID 280118)
-- Dependencies: 212
-- Name: if_resp_grupo_pad_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_resp_grupo_pad_id ON responsable_grupo USING btree (padre_id);


--
-- TOC entry 2463 (class 1259 OID 280119)
-- Dependencies: 212
-- Name: if_resp_grupo_res_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_resp_grupo_res_id ON responsable_grupo USING btree (responsable_id);


--
-- TOC entry 2458 (class 1259 OID 280120)
-- Dependencies: 211
-- Name: if_responsable_usu_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX if_responsable_usu_id ON responsable USING btree (usuario_id);


--
-- TOC entry 2425 (class 1259 OID 280124)
-- Dependencies: 203
-- Name: xif10pry_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif10pry_actividad ON pry_actividad USING btree (resp_cargar_ejecutado_id);


--
-- TOC entry 2426 (class 1259 OID 280126)
-- Dependencies: 203
-- Name: xif11pry_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif11pry_actividad ON pry_actividad USING btree (resp_cargar_meta_id);


--
-- TOC entry 2164 (class 1259 OID 280127)
-- Dependencies: 129
-- Name: xif1accion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--
CREATE  INDEX IE_accion_estado ON accion (estado_id   ASC);
CREATE  INDEX IE_accion_problema ON accion USING btree (problema_id   ASC);
CREATE  INDEX IE_accion_creado ON accion USING btree (creado_id   ASC);
CREATE  INDEX IE_accion_modificado ON accion USING btree (modificado_id   ASC);

--
-- TOC entry 2181 (class 1259 OID 280128)
-- Dependencies: 136
-- Name: xif1afw_config_usuario; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_config_usuario ON afw_config_usuario USING btree (usuario_id);


--
-- TOC entry 2208 (class 1259 OID 280129)
-- Dependencies: 148
-- Name: xif1afw_permiso; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_permiso ON afw_permiso USING btree (padre_id);


--
-- TOC entry 2211 (class 1259 OID 280130)
-- Dependencies: 149
-- Name: xif1afw_permiso_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_permiso_grupo ON afw_permiso_grupo USING btree (permiso_id);


--
-- TOC entry 2215 (class 1259 OID 280131)
-- Dependencies: 150
-- Name: xif1afw_plantilla; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_plantilla ON afw_plantilla USING btree (usuario_id);


--
-- TOC entry 2218 (class 1259 OID 280132)
-- Dependencies: 152
-- Name: xif1afw_usersession; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_usersession ON afw_user_session USING btree (usuario_id);


--
-- TOC entry 2225 (class 1259 OID 280133)
-- Dependencies: 154
-- Name: xif1afw_usuario_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1afw_usuario_grupo ON afw_usuario_grupo USING btree (usuario_id);


--
-- TOC entry 2234 (class 1259 OID 280134)
-- Dependencies: 157
-- Name: xif1categoria_por_indicador; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1categoria_por_indicador ON categoria_por_indicador USING btree (indicador_id);


--
-- TOC entry 2243 (class 1259 OID 280135)
-- Dependencies: 159
-- Name: xif1causa_por_problema; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1causa_por_problema ON causa_por_problema USING btree (problema_id);

--
-- TOC entry 2254 (class 1259 OID 280137)
-- Dependencies: 161
-- Name: xif1clase; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1clase ON clase USING btree (organizacion_id);


--
-- TOC entry 2260 (class 1259 OID 280138)
-- Dependencies: 162
-- Name: xif1clase_problema; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE  INDEX IE_padre_claseproblema ON clase_problema USING btree (padre_id   ASC);
CREATE  INDEX IE_organizacion_claseproblema ON clase_problema USING btree (organizacion_id   ASC);
CREATE  INDEX IE_creado_claseproblema ON clase_problema USING btree (creado_id   ASC);
CREATE  INDEX IE_modificado_claseproblema ON clase_problema USING btree (modificado_id   ASC);

--
-- TOC entry 2280 (class 1259 OID 280140)
-- Dependencies: 167
-- Name: xif1explicacion_adjunto; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1explicacion_adjunto ON explicacion_adjunto USING btree (explicacion_id);


--
-- TOC entry 2283 (class 1259 OID 280141)
-- Dependencies: 168
-- Name: xif1explicacion_memo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1explicacion_memo ON explicacion_memo USING btree (explicacion_id);


--
-- TOC entry 2291 (class 1259 OID 280143)
-- Dependencies: 170
-- Name: xif1foro; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1foro ON foro USING btree (padre_id);


--
-- TOC entry 2294 (class 1259 OID 280144)
-- Dependencies: 171
-- Name: xif1inc_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1inc_actividad ON inc_actividad USING btree (actividad_id);


--
-- TOC entry 2309 (class 1259 OID 280146)
-- Dependencies: 175
-- Name: xif1indicador_por_celda; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1indicador_por_celda ON indicador_por_celda USING btree (celda_id);


--
-- TOC entry 2314 (class 1259 OID 280147)
-- Dependencies: 176
-- Name: xif1indicador_por_perspectiva; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1indicador_por_perspectiva ON indicador_por_perspectiva USING btree (perspectiva_id);


--
-- TOC entry 2318 (class 1259 OID 280148)
-- Dependencies: 177
-- Name: ie_indicadorid_indicador_por_p; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX ie_indicadorid_indicador_por_p ON indicador_por_plan USING btree (indicador_id);
CREATE INDEX ie_planid_indicador_por_plan ON indicador_por_plan USING btree (plan_id);

--
-- TOC entry 2332 (class 1259 OID 280151)
-- Dependencies: 180
-- Name: xif1iniciativa_memo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1iniciativa_memo ON iniciativa_objeto USING btree (iniciativa_id);


--
-- TOC entry 2349 (class 1259 OID 280154)
-- Dependencies: 185
-- Name: xif1medicion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1medicion ON medicion USING btree (serie_id);


--
-- TOC entry 2422 (class 1259 OID 280155)
-- Dependencies: 202
-- Name: xif1memo_problema; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1memo_problema ON problema_memo USING btree (problema_id);


--
-- TOC entry 2354 (class 1259 OID 280156)
-- Dependencies: 186
-- Name: xif1meta; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1meta ON meta USING btree (plan_id);


--
-- TOC entry 2364 (class 1259 OID 280157)
-- Dependencies: 188
-- Name: xif1metodologia_template; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1metodologia_template ON metodologia_template USING btree (metodologia_id);


--
-- TOC entry 2378 (class 1259 OID 280158)
-- Dependencies: 192
-- Name: xif1organizacion_memo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1organizacion_memo ON organizacion_memo USING btree (organizacion_id);


--
-- TOC entry 2386 (class 1259 OID 280160)
-- Dependencies: 194
-- Name: xif1perspectiva; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX IE_perspectiva_plan ON perspectiva USING btree (plan_id);


--
-- TOC entry 2417 (class 1259 OID 280165)
-- Dependencies: 201
-- Name: xif1problema; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE  INDEX IE_problema_organizacion ON problema USING btree (organizacion_id   ASC);
CREATE  INDEX IE_problema_iniciativa_efecto ON problema USING btree (iniciativa_efecto_id   ASC);
CREATE  INDEX IE_problema_auxiliar ON problema USING btree (auxiliar_id   ASC);
CREATE  INDEX XIF12problema ON problema USING btree (clase_id   ASC);
CREATE  INDEX IE_problema_estado ON problema USING btree (estado_id   ASC);
CREATE  INDEX IE_problema_responsable ON problema USING btree (responsable_id   ASC);
CREATE  INDEX IE_problema_creado ON problema USING btree (creado_id   ASC);
CREATE  INDEX IE_problema_modificado ON problema USING btree (modificado_id   ASC);
CREATE  INDEX IE_problema_indicador_efecto ON problema USING btree (indicador_efecto_id   ASC);

--
-- TOC entry 2427 (class 1259 OID 280166)
-- Dependencies: 203
-- Name: xif1pry_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1pry_actividad ON pry_actividad USING btree (proyecto_id);



--
-- TOC entry 2438 (class 1259 OID 280168)
-- Dependencies: 205
-- Name: xif1pry_calendario; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1pry_calendario ON pry_calendario USING btree (proyecto_id);


--
-- TOC entry 2441 (class 1259 OID 280169)
-- Dependencies: 206
-- Name: xif1pry_calendario_detalle; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1pry_calendario_detalle ON pry_calendario_detalle USING btree (calendario_id);


--
-- TOC entry 2448 (class 1259 OID 280170)
-- Dependencies: 208
-- Name: xif1pry_columna_por_vista; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1pry_columna_por_vista ON pry_columna_por_vista USING btree (vista_id);


--
-- TOC entry 2466 (class 1259 OID 280171)
-- Dependencies: 213
-- Name: xif1responsable_por_accion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1responsable_por_accion ON responsable_por_accion USING btree (accion_id);


--
-- TOC entry 2472 (class 1259 OID 280172)
-- Dependencies: 214
-- Name: xif1seguimiento; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1seguimiento ON seguimiento USING btree (accion_id);


--
-- TOC entry 2476 (class 1259 OID 280173)
-- Dependencies: 215
-- Name: xif1seguimiento_memo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1seguimiento_memo ON seguimiento_memo USING btree (seguimiento_id);


--
-- TOC entry 2481 (class 1259 OID 280174)
-- Dependencies: 217
-- Name: xif1serie_indicador; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1serie_indicador ON serie_indicador USING btree (serie_id);


--
-- TOC entry 2483 (class 1259 OID 280175)
-- Dependencies: 218
-- Name: xif1serie_plan; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif1serie_plan ON serie_plan USING btree (plan_id);


--
-- TOC entry 2212 (class 1259 OID 280178)
-- Dependencies: 149
-- Name: xif2afw_permiso_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2afw_permiso_grupo ON afw_permiso_grupo USING btree (grupo_id);


--
-- TOC entry 2226 (class 1259 OID 280179)
-- Dependencies: 154
-- Name: xif2afw_usuario_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2afw_usuario_grupo ON afw_usuario_grupo USING btree (grupo_id);


--
-- TOC entry 2235 (class 1259 OID 280180)
-- Dependencies: 157
-- Name: xif2categoria_por_indicador; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2categoria_por_indicador ON categoria_por_indicador USING btree (categoria_id);


--
-- TOC entry 2244 (class 1259 OID 280181)
-- Dependencies: 159
-- Name: xif2causa_por_problema; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2causa_por_problema ON causa_por_problema USING btree (causa_id);


--
-- TOC entry 2255 (class 1259 OID 280182)
-- Dependencies: 161
-- Name: xif2clase; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2clase ON clase USING btree (padre_id);



--
-- TOC entry 2287 (class 1259 OID 280185)
-- Dependencies: 169
-- Name: IE_formula_SerieId; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX IE_formula_SerieId ON formula USING btree (serie_id);


--
-- TOC entry 2310 (class 1259 OID 280187)
-- Dependencies: 175 175
-- Name: xif2indicador_por_celda; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2indicador_por_celda ON indicador_por_celda USING btree (serie_id, indicador_id);


--
-- TOC entry 2315 (class 1259 OID 280188)
-- Dependencies: 176
-- Name: xif2indicador_por_perspectiva; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2indicador_por_perspectiva ON indicador_por_perspectiva USING btree (indicador_id);



--
-- TOC entry 2350 (class 1259 OID 280192)
-- Dependencies: 185
-- Name: xif2medicion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2medicion ON medicion USING btree (indicador_id);


--
-- TOC entry 2355 (class 1259 OID 280193)
-- Dependencies: 186
-- Name: xif2meta; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2meta ON meta USING btree (indicador_id);


--
-- TOC entry 2387 (class 1259 OID 280194)
-- Dependencies: 194
-- Name: xif2perspectiva; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX IE_perspectiva_responsable ON perspectiva USING btree (responsable_id);


--
-- TOC entry 2405 (class 1259 OID 280196)
-- Dependencies: 198
-- Name: xif2prd_producto; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2prd_producto ON prd_producto USING btree (responsable_id);


--
-- TOC entry 2428 (class 1259 OID 280199)
-- Dependencies: 203
-- Name: xif2pry_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2pry_actividad ON pry_actividad USING btree (padre_id);


--
-- TOC entry 2449 (class 1259 OID 280201)
-- Dependencies: 208
-- Name: xif2pry_columna_por_vista; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2pry_columna_por_vista ON pry_columna_por_vista USING btree (columna_id);


--
-- TOC entry 2461 (class 1259 OID 280202)
-- Dependencies: 211
-- Name: xif2responsable; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2responsable ON responsable USING btree (organizacion_id);


--
-- TOC entry 2467 (class 1259 OID 280203)
-- Dependencies: 213
-- Name: xif2responsable_por_accion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2responsable_por_accion ON responsable_por_accion USING btree (responsable_id);


--
-- TOC entry 2473 (class 1259 OID 280204)
-- Dependencies: 214
-- Name: xif2seguimiento; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2seguimiento ON seguimiento USING btree (estado_id);


--
-- TOC entry 2482 (class 1259 OID 280205)
-- Dependencies: 217
-- Name: xif2serie_indicador; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2serie_indicador ON serie_indicador USING btree (indicador_id);


--
-- TOC entry 2484 (class 1259 OID 280206)
-- Dependencies: 218
-- Name: xif2serie_plan; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif2serie_plan ON serie_plan USING btree (serie_id);


--
-- TOC entry 2227 (class 1259 OID 280208)
-- Dependencies: 154
-- Name: xif3afw_usuario_grupo; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif3afw_usuario_grupo ON afw_usuario_grupo USING btree (organizacion_id);


--
-- TOC entry 2311 (class 1259 OID 280212)
-- Dependencies: 175
-- Name: xif3indicador_por_celda; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif3indicador_por_celda ON indicador_por_celda USING btree (plan_id);


--
-- TOC entry 2351 (class 1259 OID 280215)
-- Dependencies: 185 185
-- Name: xif3medicion; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif3medicion ON medicion USING btree (serie_id, indicador_id);


--
-- TOC entry 2388 (class 1259 OID 280216)
-- Dependencies: 194
-- Name: xif3perspectiva; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX IE_perspectiva_padre ON perspectiva USING btree (padre_id);


--
-- TOC entry 2429 (class 1259 OID 280219)
-- Dependencies: 203
-- Name: xif3pry_actividad; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX xif3pry_actividad ON pry_actividad USING btree (unidad_id);


--
-- TOC entry 2510 (class 2606 OID 280230)
-- Dependencies: 153 2221 155
-- Name: pk_afw_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY afw_usuarioweb
    ADD CONSTRAINT pk_afw_usuarioweb FOREIGN KEY (usuario_id) REFERENCES afw_usuario(usuario_id) MATCH FULL;


CREATE TABLE afw_servicio
(
	usuario_id           numeric(10,0) NOT NULL ,
	fecha                timestamp without time zone NOT NULL,
	nombre               character varying(50) NOT NULL,
	estatus              numeric(1,0) NOT NULL,
	mensaje              character varying(1000) NOT NULL,
	log              	 text NULL,
	CONSTRAINT PK_afw_servicio PRIMARY KEY (usuario_id, fecha, nombre)
);
ALTER TABLE afw_servicio OWNER TO postgres;

CREATE UNIQUE INDEX UK_afw_servicio ON afw_servicio USING btree
(usuario_id   ASC,fecha   ASC,nombre   ASC);

CREATE  INDEX IF_afw_servicio ON afw_servicio USING btree (usuario_id   ASC);

--
-- TOC entry 2510 (class 2606 OID 280230)
-- Dependencies: 153 2221 155
-- Name: pk_afw_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

CREATE TABLE afw_message
(
	usuario_id           NUMERIC(10, 0) NOT NULL ,
	fecha                TIMESTAMP without time zone NOT NULL ,
	estatus              NUMERIC(1, 0) NOT NULL ,
	mensaje              character varying(500) NOT NULL,
	tipo                 NUMERIC(1, 0) NOT NULL,
	fuente				 character varying(50) NULL,
	CONSTRAINT PK_afw_message PRIMARY KEY (usuario_id, fecha)
);
ALTER TABLE afw_message OWNER TO postgres;

CREATE INDEX IF_afw_message ON afw_message USING btree (usuario_id   ASC);

--
-- TOC entry 2510 (class 2606 OID 280230)
-- Dependencies: 153 2221 155
-- Name: pk_afw_usuario; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE perspectiva
  ADD CONSTRAINT fk_perspectiva_padre FOREIGN KEY (padre_id) REFERENCES perspectiva (perspectiva_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;

ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_responsable FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON UPDATE NO ACTION ON DELETE SET NULL;
	
ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_creado FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_modificado FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_clase FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_logropacial FOREIGN KEY (nl_par_indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE SET NULL;
	
ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_logroanual FOREIGN KEY (nl_ano_indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE SET NULL;

	
-- perspectiva_relacion
CREATE TABLE perspectiva_relacion
(
	perspectiva_id       NUMERIC(10, 0) NOT NULL ,
	relacion_id          NUMERIC(10, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_perspectiva_relacion ON perspectiva_relacion USING btree (perspectiva_id   ASC,relacion_id   ASC);

ALTER TABLE perspectiva_relacion
	ADD CONSTRAINT PK_perspectiva_relacion PRIMARY KEY (perspectiva_id,relacion_id);

CREATE  INDEX IE_relacion_perspectiva ON perspectiva_relacion USING btree (perspectiva_id   ASC);

CREATE  INDEX IE_relacion_relacion ON perspectiva_relacion USING btree (relacion_id   ASC);

ALTER TABLE perspectiva_relacion
	ADD CONSTRAINT FK_relacion_perspectiva FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE perspectiva_relacion
	ADD CONSTRAINT FK_relacion_relacion FOREIGN KEY (relacion_id) REFERENCES perspectiva (perspectiva_id) ON UPDATE NO ACTION ON DELETE CASCADE;

-- version
CREATE TABLE afw_version
(
	version              character varying(50) NOT NULL ,
	build                character varying(8) NOT NULL ,
	dateBuild            character varying(50) NOT NULL ,
	createdAt            timestamp without time zone NOT NULL 
);

CREATE UNIQUE INDEX ak_afw_version ON afw_version USING btree (version ASC, build ASC, datebuild ASC);

CREATE INDEX ie_afw_version ON afw_version USING btree (createdat   ASC);

ALTER TABLE afw_version ADD CONSTRAINT  PK_afw_version PRIMARY KEY (version, build, dateBuild);

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_organizacion_iniciativa FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_fijar_meta FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_lograr_meta FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_seguimiento FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_resp_cargar_meta FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_cargar_ejecutado FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_proyecto_iniciativa FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON DELETE SET NULL;

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_clase_iniciativa FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_ano
	ADD CONSTRAINT fk_iniciativa_ano FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_objeto
	ADD CONSTRAINT fK_iniciativa_objeto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_perspectiva
	ADD CONSTRAINT fk_iniciativa_perspectiva FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_por_perspectiva
	ADD CONSTRAINT fK_perspectiva_iniciativa FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_plan
	ADD CONSTRAINT FK_iniciativa_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE iniciativa_plan
	ADD CONSTRAINT FK_plan_iniciaitva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;
	
ALTER TABLE prd_producto
	ADD CONSTRAINT fk_iniciativa_producto FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE prd_seg_producto
	ADD CONSTRAINT fk_iniciativa_pro_seg FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE;

ALTER TABLE prd_seg_producto
	ADD CONSTRAINT fk_producto_pro_seg FOREIGN KEY (producto_id) REFERENCES prd_producto (producto_id) ON DELETE CASCADE;

ALTER TABLE accion
	ADD CONSTRAINT FK_ACCION_ESTADO FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL;

ALTER TABLE accion
	ADD CONSTRAINT FK_ACCION_Problema FOREIGN KEY (problema_id) REFERENCES problema (problema_id) ON DELETE CASCADE;

ALTER TABLE accion
	ADD CONSTRAINT FK_Accion_PadreId FOREIGN KEY (accion_id) REFERENCES accion (accion_id) ON DELETE SET NULL;

ALTER TABLE accion
	ADD CONSTRAINT FK_creado_accion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE accion
	ADD CONSTRAINT FK_modificado_accion FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;
	
ALTER TABLE clase_problema
	ADD CONSTRAINT FK_padre_claseproblema FOREIGN KEY (padre_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE;

ALTER TABLE clase_problema
	ADD CONSTRAINT FK_organizacion_claseproblema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE SET NULL;

ALTER TABLE clase_problema
	ADD CONSTRAINT FK_creado_claseproblema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE clase_problema
	ADD CONSTRAINT FK_modificado_claseproblema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_organizacion_problema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;

ALTER TABLE problema
	ADD CONSTRAINT FK_estado_problema FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_responsable_problema FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_creado_problema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_modificado_problema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_indicador_efecto_problema FOREIGN KEY (indicador_efecto_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_iniciativa_efecto_problema FOREIGN KEY (iniciativa_efecto_id) REFERENCES iniciativa (iniciativa_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_auxiliar_problema FOREIGN KEY (auxiliar_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE problema
	ADD CONSTRAINT FK_problema_claseproblema FOREIGN KEY (clase_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE;

ALTER TABLE planes
	ADD CONSTRAINT FK_padre_planes FOREIGN KEY (padre_id) REFERENCES planes (plan_id) ON DELETE SET NULL;

ALTER TABLE planes
	ADD CONSTRAINT FK_planImpacta_plan FOREIGN KEY (plan_impacta_id) REFERENCES planes (plan_id) ON DELETE SET NULL;

ALTER TABLE planes
	ADD CONSTRAINT FK_organizacion_plan FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;

ALTER TABLE planes
	ADD CONSTRAINT FK_metodologia_plan FOREIGN KEY (metodologia_id) REFERENCES metodologia (metodologia_id) ON DELETE CASCADE;

ALTER TABLE planes
	ADD CONSTRAINT FK_clase_plan FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE;

ALTER TABLE planes
	ADD CONSTRAINT FK_clasetotales_plan FOREIGN KEY (clase_id_indicadores_totales) REFERENCES clase (clase_id) ON DELETE SET NULL;

ALTER TABLE responsable
	ADD CONSTRAINT FK_organizacion_responsable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;
	
ALTER TABLE responsable
	ADD CONSTRAINT FK_usuario_responsable FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL;	

ALTER TABLE indicador_estado
  ADD CONSTRAINT fk_indicador_indicador_estado FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_estado
  ADD CONSTRAINT fk_plan_indicador_estado FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_por_plan
  ADD CONSTRAINT fk_ind_indicador_plan FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_por_plan
  ADD CONSTRAINT fk_plan_indicador_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE modelo 
	ADD CONSTRAINT FK_planes_modelo FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;
	
CREATE TABLE afw_modulo
(
	Id                   character varying(50) NOT NULL ,
	Modulo               character varying(100) NOT NULL ,
	Activo               numeric(1, 0) NOT NULL
);

ALTER TABLE afw_modulo ADD CONSTRAINT  IE_afw_modulo PRIMARY KEY (Id);

--Importacion
CREATE TABLE afw_importacion
(
	id           		 numeric(10, 0) NOT NULL, 
	usuario_id           numeric(10, 0) NOT NULL, 
	nombre               character varying(100) NOT NULL,
	tipo                 numeric(1, 0) NOT NULL,
	configuracion        character varying(2000) NOT NULL
);

CREATE UNIQUE INDEX AK_afw_importacion ON afw_importacion USING btree (id   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT  PK_afw_importacion PRIMARY KEY (id);

CREATE UNIQUE INDEX AK_fw_importacion_usuario_nomb ON afw_importacion USING btree (usuario_id   ASC,nombre   ASC);

ALTER TABLE afw_importacion ADD CONSTRAINT PK_fw_importacion_usuario_nomb UNIQUE (usuario_id,nombre);

CREATE  INDEX IE_afw_importacion_nombre ON afw_importacion USING btree (nombre   ASC);

CREATE  INDEX IE_afw_importacion_usuarioId ON afw_importacion USING btree (usuario_id   ASC);

ALTER TABLE afw_importacion
	ADD CONSTRAINT FK_usuario_importacion FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE meta
	ADD CONSTRAINT fk_plan_meta FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;	

CREATE TABLE mb_atributo
(
	atributo_id          numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	variable_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	pregunta             character varying(2000) NOT NULL ,
	prioridad            numeric(2, 0),
	orden                numeric(2, 0) NOT NULL,
	indicador_id 		 numeric(10, 0) NULL,
	apc 				 numeric(10, 0) NULL 
);

ALTER TABLE mb_atributo
	ADD CONSTRAINT  PK_MB_ATRIBUTO PRIMARY KEY (atributo_id);

ALTER TABLE mb_atributo ADD CONSTRAINT  AK_MB_ATRIBUTO UNIQUE (sector_id,nombre);

CREATE  INDEX IE_MB_ATRIBUTO_SECTOR_ID ON mb_atributo USING btree (sector_id   ASC);

CREATE TABLE mb_atrib_coment_cat
(
	atributo_id          numeric(10, 0) NOT NULL ,
	orden                numeric(3, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_mb_atrib_coment_cat ON mb_atrib_coment_cat USING btree (atributo_id   ASC,orden   ASC);

ALTER TABLE mb_atrib_coment_cat ADD CONSTRAINT  PK_mb_atrib_coment_cat PRIMARY KEY (atributo_id,orden);

CREATE  INDEX IE_mb_atrib_coment_cat ON mb_atrib_coment_cat USING btree (atributo_id   ASC);

ALTER TABLE mb_atrib_coment_cat
	ADD CONSTRAINT PK_mb_atributo_commnet_orden FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id) ON UPDATE NO ACTION ON DELETE CASCADE;

CREATE TABLE mb_categoria_criterio
(
	criterio_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	categoria            character varying(50) NULL 
);

ALTER TABLE mb_categoria_criterio
	ADD CONSTRAINT  PK_MB_CATEGORIA_CRITERIO PRIMARY KEY (criterio_id,orden);

CREATE TABLE mb_categoria_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	categoria_id         numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL 
);

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT  AK_MB_CATEGORIA_VARIABLE PRIMARY KEY (variable_id,categoria_id);

CREATE  INDEX IE_MBCATEGORIA_VARIABLEID ON mb_categoria_variable USING btree (variable_id   ASC);

CREATE  INDEX IE_MBVARIABLE_CATEGORIAID ON mb_categoria_variable USING btree (categoria_id   ASC);

CREATE TABLE mb_criterio_estratificacion
(
	criterio_id          numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	pregunta             character varying(2000) NOT NULL 
);

ALTER TABLE mb_criterio_estratificacion
	ADD CONSTRAINT  PK_MB_CRITERIO_ESTRATIFICACION PRIMARY KEY (criterio_id);

ALTER TABLE mb_criterio_estratificacion ADD CONSTRAINT  AK_MB_CRITERIO_ESTRATIFICACION UNIQUE (medicion_id,nombre);

CREATE TABLE mb_encuesta
(
	encuesta_id          numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	encuestado_id        numeric(10, 0),
	numero               numeric(10, 0),
	fecha                timestamp without time zone NOT NULL ,
	persona              character varying(50),
	ci                   character varying(10),
	estado               numeric(2, 0),
	comentario           character varying(2000) NULL 
);

ALTER TABLE mb_encuesta
	ADD CONSTRAINT  PK_MB_ENCUESTA PRIMARY KEY (encuesta_id);

ALTER TABLE mb_encuesta ADD CONSTRAINT  AK_MB_ENCUESTA UNIQUE (sector_id,numero);

CREATE  INDEX IE_MB_ENCUESTA_SECTOR_ID ON mb_encuesta USING btree (sector_id   ASC);

CREATE TABLE mb_encuesta_atributo
(
	atributo_id          numeric(10, 0) NOT NULL ,
	encuesta_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0),
	prioridad            numeric(2, 0),
	comentario 			 text NULL 
);

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT  PK_MB_ENCUESTA_ATRIBUTO PRIMARY KEY (atributo_id,encuesta_id);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ENC ON mb_encuesta_atributo USING btree (encuesta_id   ASC);

CREATE  INDEX IE_MB_ENCUESTA_ATRIBUTO_ATR ON mb_encuesta_atributo USING btree (atributo_id   ASC);

CREATE TABLE mb_encuesta_criterio
(
	encuesta_id          numeric(10, 0) NOT NULL ,
	c1                   numeric(10, 0),
	c2                   numeric(10, 0),
	c3                   numeric(10, 0),
	c4                   numeric(10, 0),
	c5                   numeric(10, 0),
	c6                   numeric(10, 0),
	c7                   numeric(10, 0),
	c8                   numeric(10, 0),
	c9                   numeric(10, 0),
	c10                  numeric(10, 0) 
);

ALTER TABLE mb_encuesta_criterio
	ADD CONSTRAINT  PK_MB_ENCUESTA_CRITERIO PRIMARY KEY (encuesta_id);

CREATE TABLE mb_encuesta_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	encuesta_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	validas              numeric(10, 0),
	novalidas            numeric(10, 0),
	estatus              numeric(1, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_red_id      numeric(10, 0),
	promedio             double precision NULL 
);

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT  PK_MB_ENCUESTA_VARIABLE PRIMARY KEY (encuesta_id,variable_id);

CREATE TABLE mb_encuestado
(
	encuestado_id        numeric(10, 0) NOT NULL ,
	sector_id            numeric(10, 0) NOT NULL ,
	persona_id           numeric(10, 0),
	nombre               character varying(100),
	cedula               character varying(20),
	cargo                character varying(50),
	email                character varying(50),
	estado               numeric(2, 0),
	numero_encuesta      numeric(10, 0),
	encuesta_id          numeric(10, 0),
	notas                character varying(2000),
	web_address          character varying(20) 
);

ALTER TABLE mb_encuestado
	ADD CONSTRAINT  PK_MB_ENCUESTADO PRIMARY KEY (encuestado_id);

CREATE TABLE mb_etiqueta
(
	atributo_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL 
);

ALTER TABLE mb_etiqueta
	ADD CONSTRAINT  PK_MB_ETIQUETA PRIMARY KEY (atributo_id,orden);

CREATE TABLE mb_formato_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	texto_ayuda          character varying(2000),
	mostrar_ayuda        numeric(1, 0),
	preenunciado         character varying(100),
	mostrar_pre          numeric(1, 0),
	postenunciado        character varying(100),
	mostrar_post         numeric(1, 0),
	mostrar_variable     numeric(1, 0) 
);

ALTER TABLE mb_formato_variable
	ADD CONSTRAINT  PK_MB_FORMATO_VARIABLE PRIMARY KEY (variable_id);

CREATE TABLE mb_grafico
(
	grafico_id           numeric(10, 0) NOT NULL ,
	nombre               character varying(50),
	tipo                 numeric(1, 0) NOT NULL ,
	elemento_id          numeric(10, 0) 
);

ALTER TABLE mb_grafico
	ADD CONSTRAINT  PK_MB_GRAFICO PRIMARY KEY (grafico_id);

CREATE TABLE mb_grafico_binario
(
	grafico_id           numeric(10, 0) NOT NULL ,
	binario              text NULL 
);

ALTER TABLE mb_grafico_binario
	ADD CONSTRAINT  PK_MB_GRAFICO_BINARIO PRIMARY KEY (grafico_id);

CREATE TABLE mb_medicion
(
	medicion_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	address_id           numeric(10, 0),
	fecha                timestamp without time zone NOT NULL ,
	modalidad            numeric(1, 0) NOT NULL ,
	responsable          character varying(20),
	creado               timestamp without time zone NULL ,
	creado_id            numeric(10, 0),
	modificado           timestamp without time zone NULL ,
	modificado_id        numeric(10, 0),
	criterio_validez     numeric(3, 0),
	criterio_desempate   numeric(1, 0),
	define_prioridad     numeric(1, 0),
	tipo_sintesis        numeric(2, 0),
	sintetizado          numeric(1, 0),
	anonimo              numeric(1, 0),
	etapa                numeric(1, 0),
	mensaje_correo       character varying(4000),
	encstas_x_encstado 	 numeric(10, 0) NULL,
	coment_x_pregunta 	 numeric(10, 0) NULL,
	asunto_correo 		 text NULL 
);

ALTER TABLE mb_medicion
	ADD CONSTRAINT  PK_MB_MEDICION PRIMARY KEY (medicion_id);

CREATE  INDEX IE_MB_MEDICION_FECHA ON mb_medicion USING btree (fecha   ASC);

CREATE TABLE mb_medicion_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	padre_id             numeric(10, 0),
	variable_ref_id      numeric(10, 0),
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	nivel                numeric(4, 0),
	porcentaje           double precision NULL 
);

ALTER TABLE mb_medicion_variable
	ADD CONSTRAINT  PK_MB_MEDICION_VARIABLE PRIMARY KEY (variable_id);

CREATE  INDEX IE_MB_MED_VAR_MEDICION_ID ON mb_medicion_variable USING btree (medicion_id   ASC);

CREATE TABLE mb_numero_encuesta
(
	sector_id            numeric(10, 0) NOT NULL ,
	numero               numeric(10, 0) NOT NULL 
);

ALTER TABLE mb_numero_encuesta
	ADD CONSTRAINT  PK_MB_NUMERO_ENCUESTA PRIMARY KEY (sector_id,numero);

CREATE TABLE mb_orden_criterio
(
	medicion_id          numeric(10, 0) NOT NULL ,
	orden                numeric(2, 0) NOT NULL 
);

ALTER TABLE mb_orden_criterio
	ADD CONSTRAINT  PK_MB_ORDEN_CRITERIO PRIMARY KEY (medicion_id,orden);

CREATE TABLE mb_organizacion
(
	organizacion_id      numeric(10, 0) NOT NULL ,
	medicion_id          numeric(10, 0) NOT NULL ,
	padre_id             numeric(10, 0),
	organizacion_ref_id  numeric(10, 0),
	nombre               character varying(50) NOT NULL ,
	porcentaje           double precision NULL ,
	porcentaje_propio    double precision NULL 
);

ALTER TABLE mb_organizacion
	ADD CONSTRAINT  PK_MB_ORGANIZACION PRIMARY KEY (organizacion_id);

ALTER TABLE mb_organizacion ADD CONSTRAINT  AK_MB_ORGANIZACION UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_ORGANIZACION_PADRE_ID ON mb_organizacion USING btree (padre_id   ASC);

CREATE TABLE mb_organizacion_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	apc_propio           numeric(10, 0),
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	indicador_id         numeric(10, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_red_id      numeric(10, 0),
	organizacion_id      numeric(10, 0) NOT NULL,
	codigo_enlace 		 character varying(100) NULL 
);

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT  AK_MB_ORGANIZACION_VARIABLE PRIMARY KEY (variable_id,organizacion_id);

CREATE  INDEX IE_MB_VARIABLE_ORGANIZACIONID ON mb_organizacion_variable USING btree (organizacion_id   ASC);

CREATE  INDEX IE_mborgvariable_indicadorId ON mb_organizacion_variable USING btree (indicador_id   ASC);

CREATE TABLE mb_sector
(
	sector_id            numeric(10, 0) NOT NULL ,
	padre_id             numeric(10, 0),
	organizacion_id      numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	porcentaje           double precision NULL ,
	nodo_final           numeric(1, 0),
	definidos            numeric(10, 0),
	tamano_muestra       numeric(10, 0),
	codigo_enlace 		 character varying(100) NULL  
);

ALTER TABLE mb_sector
	ADD CONSTRAINT  PK_MB_SECTOR PRIMARY KEY (sector_id);

ALTER TABLE mb_sector ADD CONSTRAINT  AK_MB_SECTOR UNIQUE (padre_id,nombre);

CREATE  INDEX IE_MB_SECTOR_PADRE_ID ON mb_sector USING btree (padre_id   ASC);

CREATE TABLE mb_sector_variable
(
	sector_id            numeric(10, 0) NOT NULL ,
	variable_id          numeric(10, 0) NOT NULL ,
	apc                  numeric(10, 0),
	numero_instrumentos_validos numeric(4, 0),
	numero_instrumentos_novalidos numeric(4, 0),
	estatus              numeric(1, 0),
	padre_id             numeric(10, 0),
	nivel                numeric(4, 0),
	variable_ref_id      numeric(10, 0) 
);

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT  PK_MB_SECTOR_VARIABLE PRIMARY KEY (sector_id,variable_id);

CREATE TABLE mb_variable
(
	variable_id          numeric(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	descripcion          character varying(2000),
	frecuencia           numeric(1, 0) NOT NULL ,
	creado               timestamp without time zone NULL ,
	creado_id            numeric(10, 0),
	modificado           timestamp without time zone NULL ,
	modificado_id        numeric(10, 0) 
);

ALTER TABLE mb_variable
	ADD CONSTRAINT  PK_MB_VARIABLE PRIMARY KEY (variable_id);

ALTER TABLE mb_variable ADD CONSTRAINT  AK_MB_VARIABLE UNIQUE (nombre);

CREATE TABLE mb_webaddress
(
	address_id           numeric(10, 0) NOT NULL ,
	address              character varying(100) NOT NULL 
);

ALTER TABLE mb_webaddress
	ADD CONSTRAINT  PK_MB_WEBADDRESS PRIMARY KEY (address_id);

ALTER TABLE mb_atributo
	ADD CONSTRAINT FK_MB_MED_VARIABLE_ATRIBUTO FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_atributo
	ADD CONSTRAINT FK_MB_SECTOR_ATRIBUTO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_criterio
	ADD CONSTRAINT FK_MB_CRITERIO_CATEGORIA FOREIGN KEY (criterio_id) REFERENCES mb_criterio_estratificacion (criterio_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT FK_MB_VARIABLE_CATEGORIA FOREIGN KEY (variable_id) REFERENCES mb_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_categoria_variable
	ADD CONSTRAINT FK_mdvariable_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_criterio_estratificacion
	ADD CONSTRAINT FK_MB_MEDICION_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta
	ADD CONSTRAINT FK_MB_ENCUESTADO_ENCUESTA FOREIGN KEY (encuestado_id) REFERENCES mb_encuestado (encuestado_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE mb_encuesta
	ADD CONSTRAINT FK_MB_SECTOR_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT FK_MB_ENCUESTA_ATRIBUTO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_atributo
	ADD CONSTRAINT FK_MB_ATRIBUTO_ENCUESTA FOREIGN KEY (atributo_id) REFERENCES mb_atributo (atributo_id);

ALTER TABLE mb_encuesta_criterio
	ADD CONSTRAINT FK_MB_ENCUESTA_CRITERIO FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT FK_MB_MEDICION_ENC_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuesta_variable
	ADD CONSTRAINT FK_MB_ENCUESTA_VARIABLE FOREIGN KEY (encuesta_id) REFERENCES mb_encuesta (encuesta_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_encuestado
	ADD CONSTRAINT FK_MB_SECTOR_ENCUESTADO FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id);

ALTER TABLE mb_formato_variable
	ADD CONSTRAINT FK_MB_MEDICION_VARIABLE_FORMAT FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_grafico_binario
	ADD CONSTRAINT FK_MB_GRAFICO_BINARIO FOREIGN KEY (grafico_id) REFERENCES mb_grafico (grafico_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_medicion
	ADD CONSTRAINT FK_MB_WEBADDRESS_MEDICION FOREIGN KEY (address_id) REFERENCES mb_webaddress (address_id) ON UPDATE NO ACTION ON DELETE SET NULL;

ALTER TABLE mb_medicion_variable
	ADD CONSTRAINT FK_MB_MEDICION_VARIABLE FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_numero_encuesta
	ADD CONSTRAINT FK_MB_SECTOR_NUMERO_ENCUESTA FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_orden_criterio
	ADD CONSTRAINT FK_MB_MEDICION_ORDEN_CRITERIO FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion
	ADD CONSTRAINT FK_MB_MEDICION_ORGANIZACION FOREIGN KEY (medicion_id) REFERENCES mb_medicion (medicion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_MB_MEDICION_ORG_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_organizacion_variable FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_organizacion_variable
	ADD CONSTRAINT FK_mdorgvariable_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_sector
	ADD CONSTRAINT FK_MB_ORGANIZACION_SECTOR FOREIGN KEY (organizacion_id) REFERENCES mb_organizacion (organizacion_id);

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT FK_MB_MEDICION_SECTOR_VARIABLE FOREIGN KEY (variable_id) REFERENCES mb_medicion_variable (variable_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE mb_sector_variable
	ADD CONSTRAINT FK_MB_SECTOR_VARIABLE FOREIGN KEY (sector_id) REFERENCES mb_sector (sector_id) ON UPDATE NO ACTION ON DELETE CASCADE;

CREATE TABLE indicador_por_iniciativa
(
	iniciativa_id        numeric(10,0) NOT NULL ,
	indicador_id         numeric(10,0) NOT NULL,
	tipo         		 numeric(1, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_iniciativa ON indicador_por_iniciativa USING btree (iniciativa_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_iniciativa
	ADD CONSTRAINT  PK_indicador_por_iniciativa PRIMARY KEY (iniciativa_id,indicador_id);

CREATE INDEX IE_ind_por_ini_iniciativaId ON indicador_por_iniciativa USING btree (iniciativa_id   ASC);

CREATE  INDEX IE_ind_por_ini_indicadorId ON indicador_por_iniciativa USING btree (indicador_id   ASC);

ALTER TABLE indicador_por_iniciativa
	ADD CONSTRAINT FK_ind_por_ini_IniciativaId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_por_iniciativa
	ADD CONSTRAINT FK_ind_por_ini_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE FORMULA 
	ADD CONSTRAINT fk_formula_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE;

ALTER TABLE FORMULA 
	ADD CONSTRAINT FK_FORMULA_INDICADOR FOREIGN KEY (INDICADOR_ID) REFERENCES INDICADOR (INDICADOR_ID) ON DELETE CASCADE;

ALTER TABLE conjunto_formula 
	ADD CONSTRAINT fk_conjfor_padre_indicador FOREIGN KEY (padre_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

ALTER TABLE conjunto_formula 
	ADD CONSTRAINT fk_conjfor_indid_indicador FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON DELETE CASCADE;

ALTER TABLE conjunto_formula 
	ADD CONSTRAINT fk_conjfor_serid_serie FOREIGN KEY (serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE;

ALTER TABLE conjunto_formula 
	ADD CONSTRAINT fk_conjfor_insid_serie FOREIGN KEY (insumo_serie_id) REFERENCES serie_tiempo (serie_id) ON DELETE CASCADE;

ALTER TABLE indicador 
	ADD CONSTRAINT fk_respfijarmeta_indicador FOREIGN KEY (resp_fijar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;

ALTER TABLE indicador 
	ADD CONSTRAINT fk_resplograrmeta_indicador FOREIGN KEY (resp_lograr_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_respsegui_indicador FOREIGN KEY (resp_seguimiento_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_respcargarmeta_indicador FOREIGN KEY (resp_cargar_meta_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_respejecutado_indicador FOREIGN KEY (resp_cargar_ejecutado_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_clase_indicador FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_indicador_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE;
	
ALTER TABLE indicador 
	ADD CONSTRAINT fk_indicador_unidad FOREIGN KEY (unidad_id) REFERENCES unidad (unidad_id) ON DELETE SET NULL;

CREATE TABLE duppont
(
	indicador_id        numeric(10,0) NOT NULL ,
	usuario_id         	numeric(10,0) NOT NULL,
	configuracion       text NOT NULL
);

CREATE UNIQUE INDEX AK_duppont ON duppont USING btree (indicador_id   ASC, usuario_id   ASC);
ALTER TABLE duppont ADD CONSTRAINT  PK_duppont PRIMARY KEY (indicador_id, usuario_id);
CREATE  INDEX IE_duppont_indicadorId ON duppont USING btree (indicador_id   ASC);
CREATE  INDEX IE_duppont_usuarioId ON duppont USING btree (usuario_id   ASC);
ALTER TABLE duppont ADD CONSTRAINT FK_indicador_duppont FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE duppont ADD CONSTRAINT FK_usuario_duppont FOREIGN KEY (usuario_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE iniciativa ADD CONSTRAINT PK_Iniciativa_EstatusId FOREIGN KEY (estatusId) REFERENCES iniciativa_estatus (id) ON UPDATE NO ACTION ON DELETE CASCADE;
--
-- TOC entry 2515 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: -
--

CREATE TABLE objetos_calcular
(
	Id                   UUID NOT NULL ,
	Objeto_Id            numeric(10, 0) NOT NULL ,
	Usuario_Id           numeric(10, 0) NOT NULL ,
	Calculado            numeric(1, 0) NULL 
);

CREATE TABLE portafolio
(
	organizacion_id      NUMERIC(10, 0) NOT NULL ,
	id                   NUMERIC(10, 0) NOT NULL ,
	nombre               character varying(50) NOT NULL ,
	activo               NUMERIC(1, 0) NOT NULL ,
	porcentaje_completado double precision NULL ,
	estatusid            NUMERIC(10, 0) NOT NULL,
	frecuencia           NUMERIC(1, 0) NOT NULL, 
	clase_id             NUMERIC(10, 0) NOT NULL,
	fecha_ultimo_calculo character varying(10) NULL
);

CREATE UNIQUE INDEX PK_Portafolio ON portafolio USING btree (id   ASC);
ALTER TABLE portafolio ADD CONSTRAINT  PK_PortafolioId PRIMARY KEY (id);
CREATE UNIQUE INDEX AK_Portafolio ON portafolio USING btree (organizacion_id   ASC,nombre   ASC);
ALTER TABLE portafolio ADD CONSTRAINT  AK_Portafolio_Nombre UNIQUE (organizacion_id,nombre);
CREATE  INDEX IE_Portafolio_OrganizacionId ON portafolio USING btree (organizacion_id   ASC);
CREATE  INDEX IE_portafolio_estatus ON portafolio USING btree (estatusid   ASC);
CREATE  INDEX IE_portafolio_clase ON portafolio USING btree (clase_id   ASC);

CREATE TABLE portafolio_iniciativa
(
	portafolio_id        NUMERIC(10, 0) NOT NULL ,
	iniciativa_id        NUMERIC(10, 0) NOT NULL,
	peso 				 double precision
);

CREATE UNIQUE INDEX PK_portafolio_iniciativa ON portafolio_iniciativa USING btree (portafolio_id   ASC,iniciativa_id   ASC);
ALTER TABLE portafolio_iniciativa ADD CONSTRAINT PK_portafolio_iniciativa_portId PRIMARY KEY (portafolio_id,iniciativa_id);
CREATE  INDEX IE_portafolio_iniciativa_porta ON portafolio_iniciativa USING btree (portafolio_id   ASC);
CREATE  INDEX IE_portafolio_iniciativa_inici ON portafolio_iniciativa USING btree (iniciativa_id   ASC);
ALTER TABLE portafolio ADD CONSTRAINT FK_ORG_PORTAFOLIO FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE portafolio ADD CONSTRAINT FK_estatus_portafolio FOREIGN KEY (estatusid) REFERENCES iniciativa_estatus (id) ON UPDATE NO ACTION ON DELETE SET NULL;		
ALTER TABLE portafolio ADD CONSTRAINT FK_clase_portafolio FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE portafolio_iniciativa ADD CONSTRAINT FK_portafolio_portafolioId FOREIGN KEY (portafolio_id) REFERENCES portafolio (id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE portafolio_iniciativa ADD CONSTRAINT FK_portafolio_iniciativa_iniId FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE vista ADD CONSTRAINT FK_organizacion_vista FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE pagina ADD CONSTRAINT FK_vista_pagina FOREIGN KEY (vista_id) REFERENCES vista (vista_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE pagina ADD CONSTRAINT FK_portafolio_vista FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON UPDATE NO ACTION ON DELETE CASCADE;

CREATE TABLE indicador_por_portafolio
(
	indicador_id         NUMERIC(10, 0) NOT NULL ,
	portafolioId         NUMERIC(10, 0) NOT NULL ,
	tipo                 NUMERIC(1, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_portafolio ON indicador_por_portafolio USING btree (indicador_id   ASC,portafolioId   ASC);
ALTER TABLE indicador_por_portafolio ADD CONSTRAINT PK_indicador_por_portafolio PRIMARY KEY (indicador_id,portafolioId);
CREATE INDEX IE_indicador_por_portafolio_in ON indicador_por_portafolio USING btree (indicador_id   ASC);
CREATE INDEX IE_indicador_por_portafolio_po ON indicador_por_portafolio USING btree (portafolioId   ASC);
ALTER TABLE indicador_por_portafolio ADD CONSTRAINT FK_indicador_indXpor FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE indicador_por_portafolio ADD CONSTRAINT FK_portafolio_indXpor FOREIGN KEY (portafolioId) REFERENCES portafolio (id) ON UPDATE NO ACTION ON DELETE CASCADE;

--
-- TOC entry 160 (class 1259 OID 276696)
-- Dependencies: 6
-- Name: celda; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE celda (
    celda_id numeric(10,0) NOT NULL,
    pagina_id numeric(10,0) NOT NULL,
    titulo character varying(100),
    fila numeric(2,0) NOT NULL,
    columna numeric(2,0) NOT NULL,
	configuracion text NULL,
	creado timestamp without time zone,
	modificado timestamp without time zone,
	creado_id numeric(10),
	modificado_id numeric(10)
);

ALTER TABLE celda ADD CONSTRAINT pk_celda_pagina_celda UNIQUE (celda_id, pagina_id,fila,columna);
ALTER TABLE ONLY celda ADD CONSTRAINT pk_celda PRIMARY KEY (celda_id);
CREATE UNIQUE INDEX ie_celda ON celda USING btree (celda_id   ASC);
CREATE UNIQUE INDEX ie_celda_pagina_celda ON celda USING btree (celda_id, pagina_id   ASC,fila   ASC,columna   ASC);
CREATE INDEX ie_celda_pagina ON celda USING btree (pagina_id   ASC);
ALTER TABLE celda ADD CONSTRAINT FK_pagina_celda FOREIGN KEY (pagina_id) REFERENCES pagina (pagina_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE perspectiva_nivel ADD CONSTRAINT FK_perspectiva_perspectiva_niv FOREIGN KEY (perspectiva_id) REFERENCES perspectiva (perspectiva_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE EXPLICACION ADD CONSTRAINT FK_usuario_explicacion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE medicion ADD CONSTRAINT FK_INDICADOR_MEDICION FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE pry_calendario ADD CONSTRAINT FK_PRY_PROYECTO_CALENDARIO FOREIGN KEY (proyecto_id) REFERENCES pry_proyecto (proyecto_id) ON UPDATE NO ACTION ON DELETE CASCADE;

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


ALTER TABLE iniciativa 
ADD anio_form_proy  varchar(4) Null;

alter table explicacion_adjunto drop column documento;

alter table explicacion_adjunto drop column mime_type;

alter table explicacion_adjunto add ruta varchar(100);

CREATE TABLE indicador_asignar_inventario(
	asignar_id serial PRIMARY KEY,
	indicador_id integer NOT NULL,
	indicador_insumo_id integer NOT NULL
);

CREATE UNIQUE INDEX ak_asignar ON indicador_asignar_inventario (asignar_id);

CREATE TABLE tipo_proyecto(
	tipo_proyecto_id serial PRIMARY KEY,
	nombre_proyecto VARCHAR(50) NOT NULL
);

CREATE UNIQUE INDEX ak_tipo_proyecto ON tipo_proyecto (tipo_proyecto_id);

ALTER TABLE iniciativa ADD COLUMN tipoId integer;

ALTER TABLE iniciativa ADD CONSTRAINT pk_proyecto_id FOREIGN KEY (tipoId) REFERENCES tipo_proyecto (tipo_proyecto_id);

CREATE INDEX indx_tipo_proyecto ON iniciativa (tipoId);

CREATE TABLE auditoria_medicion (
  auditoria_medicion_id serial PRIMARY KEY,
  sesion varchar(200) NULL,
  fecha_ejecucion timestamp without time zone,
  accion varchar(50) NOT NULL,
  organizacion varchar(100) NOT NULL,
  organizacion_id integer NOT NULL,
  periodo varchar(10) NOT NULL,
  periodo_final varchar(10) NOT NULL,
  usuario varchar(20) NOT NULL,
  indicador varchar(200) NOT NULL,
  clase varchar(500) NOT NULL,
  iniciativa varchar(300) NULL,
  indicador_id integer NOT NULL,
  detalle varchar NULL
);

CREATE UNIQUE INDEX ak_auditoria_medicion ON auditoria_medicion (auditoria_medicion_id   ASC);

insert into afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) values ('CLASE_VISTA','CLASE','Ver Objetos',3,8,0,'Ver Objetos Visibles');

CREATE TABLE auditoria(
  auditoria_id serial PRIMARY KEY,
  fecha_ejecucion timestamp without time zone,
  usuario varchar(200) NULL,
  entidad varchar(500) NULL,  
  clase_entidad varchar NOT NULL,
  tipo_evento varchar(50) NOT NULL,
  detalle varchar NULL
);

ALTER TABLE indicador ADD COLUMN resp_notificacion_id integer;

CREATE INDEX ie_indicador_respnotificacion ON indicador (resp_notificacion_id );

ALTER TABLE indicador ADD CONSTRAINT fk_respnotificacion_indicador FOREIGN KEY (resp_notificacion_id) REFERENCES responsable (responsable_id);

CREATE TABLE reporte_servicio (
  reporte_id numeric (10, 0) NOT NULL,	
  responsable_id numeric(10, 0) NOT NULL,
  medicion varchar NOT NULL,
  indicador_id numeric(10, 0) NOT NULL
);

CREATE UNIQUE INDEX ak_reporte_servicio ON reporte_servicio (responsable_id );


CREATE TABLE reporte_grafico
(
  reporte_id serial PRIMARY KEY,
  nombre varchar(50) NOT NULL,
  configuracion text NOT NULL,
  descripcion varchar(1000) NULL ,
  publico NUMERIC(1) NOT NULL ,
  tipo NUMERIC(1) NOT NULL ,
  fecha timestamp without time zone,
  indicadores varchar NULL ,
  organizaciones varchar NULL ,
  periodo_ini  varchar NULL,
  periodo_fin varchar NULL,
  tiempo varchar NULL,  
  usuario_id NUMERIC(10) NOT NULL, 
  grafico_id numeric(10) NULL,  
  CONSTRAINT FK_usuario_reporte_grafico FOREIGN KEY (usuario_id)
      REFERENCES afw_usuario (usuario_id),
	  
  CONSTRAINT ak_reporte_grafico UNIQUE (nombre)
);
CREATE INDEX IE_usuario_reporte_grafico ON reporte_grafico (usuario_id );

CREATE TABLE tipo_convenio (
  tipos_convenio_id serial PRIMARY KEY,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL
);

CREATE TABLE cooperante (
  cooperante_id serial PRIMARY KEY,
  nombre varchar(50) NULL,
  descripcion varchar(150) NULL,
  pais varchar(150) NULL
);

CREATE TABLE instrumentos (
  instrumento_id serial PRIMARY KEY,
  nombre_corto varchar(50) NULL,
  nombre_instrumento varchar NULL,
  objetivo_instrumento varchar NULL,
  productos varchar NULL,
  cooperante_id serial,
  tipos_convenio_id serial ,
  anio varchar(4) NULL,
  instrumento_marco varchar NULL,
  fecha_inicio timestamp without time zone,
  fecha_terminacion timestamp without time zone,
  fecha_prorroga timestamp without time zone,
  recursos_pesos float NULL,
  recursos_dolares float NULL,
  nombre_ejecutante varchar(250) NULL,
  estatus numeric(1) NULL,
  areas_cargo varchar(500) NULL,
  nombre_responsable_areas varchar(250) NULL,
  responsable_cgi varchar(250) NULL,
  observaciones varchar(250) NULL

);


ALTER TABLE instrumentos ADD CONSTRAINT pk_cooperante_id FOREIGN KEY (cooperante_id) REFERENCES cooperante (cooperante_id);

CREATE INDEX indx_cooperante ON instrumentos (cooperante_id);


ALTER TABLE instrumentos ADD CONSTRAINT pk_tipos_convenio_id FOREIGN KEY (tipos_convenio_id) REFERENCES tipo_convenio (tipos_convenio_id);

CREATE INDEX indx_tipo_convenio ON instrumentos (tipos_convenio_id);

CREATE TABLE instrumento_iniciativa
(
	instrumento_id        NUMERIC(10, 0) NOT NULL ,
	iniciativa_id        NUMERIC(10, 0) NOT NULL
);

CREATE UNIQUE INDEX PK_instrumento_iniciativa ON instrumento_iniciativa USING btree (instrumento_id   ASC,iniciativa_id   ASC);
ALTER TABLE instrumento_iniciativa ADD CONSTRAINT PK_instrumento_iniciativa_portId PRIMARY KEY (instrumento_id, iniciativa_id);
CREATE  INDEX IE_instrumento_iniciativa_instru ON instrumento_iniciativa USING btree (instrumento_id   ASC);
CREATE  INDEX IE_instrumento_iniciativa_inici ON instrumento_iniciativa USING btree (iniciativa_id   ASC);


alter table iniciativa_objeto
    alter column objeto type varchar(1024);
    

ALTER TABLE iniciativa 
    add column responsable_proyecto character varying(150),
	add column cargo_responsable character varying(200),
	add column organizaciones_involucradas character varying(250),
	add column objetivo_estrategico character varying(1024),
	add column fuente_financiacion character varying(100),
	add column monto_financiamiento character varying(100),
	add column iniciativa_estrategica character varying(150),
	add column lider_iniciativa character varying(150),
	add column tipo_iniciativa character varying(150),
	add column poblacion_beneficiada character varying(1024),
	add column contexto character varying(1024),
	add column definicion_problema character varying(1024),
	add column alcance character varying(1024),
	add column objetivo_general character varying(1024),
	add column objetivos_especificos character varying(1024);        
    
    
alter table instrumento_iniciativa add peso float;

CREATE TABLE indicador_por_instrumento
(
	instrumento_id       serial NOT NULL ,
	indicador_id         numeric(10,0) NOT NULL,
	tipo         		 numeric(1, 0) NOT NULL 
);

CREATE UNIQUE INDEX AK_indicador_por_instrumento ON indicador_por_instrumento(instrumento_id   ASC,indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT  PK_indicador_por_instrumento PRIMARY KEY (instrumento_id,indicador_id);

CREATE INDEX IE_ind_por_ins_instrumentoId ON indicador_por_instrumento  (instrumento_id   ASC);

CREATE  INDEX IE_ind_por_ins_indicadorId ON indicador_por_instrumento  (indicador_id   ASC);

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_InstrumentoId FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE indicador_por_instrumento
	ADD CONSTRAINT FK_ind_por_ins_indicadorId FOREIGN KEY (indicador_id) REFERENCES indicador (indicador_id) ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE instrumentos ADD clase_id numeric (10,0);

INSERT INTO afw_permiso (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EDIT_COOPERACION', 'Modificacion Otras Organizaciones', 'INICIATIVA', 2, 14, 1, 'Modificacion Otras Organizaciones');

CREATE TABLE instrumento_peso (
	instrumento_id serial NOT NULL,
	anio varchar(4) NOT NULL,
	peso float
);

CREATE  INDEX IE_instrumento_peso ON instrumento_peso (instrumento_id   ASC);

ALTER TABLE instrumento_peso
    ADD CONSTRAINT pk_instrumento_peso PRIMARY KEY (instrumento_id);
    
ALTER TABLE instrumento_peso
	ADD CONSTRAINT fk_instrumento_peso FOREIGN KEY (instrumento_id) REFERENCES instrumentos (instrumento_id) ON UPDATE NO ACTION ON DELETE CASCADE;


UPDATE afw_permiso set permiso = 'Desasociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_DELETE';
UPDATE afw_permiso set permiso = 'Asociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_ADD';

-- alter table explicacion_adjunto drop column ruta;

alter table explicacion_adjunto add adjunto_binario bytea;

ALTER TABLE instrumentos
    ALTER COLUMN nombre_corto type varchar(150) ;
ALTER TABLE instrumentos 
    ALTER COLUMN observaciones type varchar(2500) ;

ALTER TABLE planes
    ALTER COLUMN nombre type varchar(150) ;

CREATE TABLE cargo (
	cargo_id numeric(10,0) NOT NULL,
	nombre character varying(50) NOT NULL
);

ALTER TABLE cargo
    ADD CONSTRAINT ak1_cargo UNIQUE (nombre);

ALTER TABLE cargo
	ADD CONSTRAINT pk_cargo PRIMARY KEY (cargo_id);
    
ALTER TABLE iniciativa ADD COLUMN cargo_id numeric(10);

ALTER TABLE vista 
	ALTER COLUMN nombre TYPE varchar(250);
    
ALTER TABLE iniciativa ADD COLUMN codigo varchar(50);
ALTER TABLE iniciativa ADD COLUMN unidad_medida numeric(10); 

alter table iniciativa 
	alter column nombre type varchar(300),
	add column justificacion varchar(500),
	add column fecha_inicio  TIMESTAMP,
	add column fecha_fin  TIMESTAMP,
	add column monto_total  varchar(50),
	add column monto_moneda_extr  varchar(50),
	add column sit_presupuestaria  varchar(150),
	add column hitos_relevantes  varchar(500),
	add column sector  varchar(150),
	add column fecha_acta_inicio  TIMESTAMP,
	add column gerencia_general_resp  varchar(150),
	add column codigo_sipe  varchar(50),
	add column proyecto_presup_asociado  varchar(300),
	add column estado  varchar(100),
	add column municipio  varchar(300),
	add column parroquia  varchar(300),
	add column direccion_proyecto varchar(500),
	add column obj_historico varchar(1500),
	add column obj_nacional varchar(1500),
	add column obj_estrategico_v varchar(1500),
	add column obj_general_v varchar(1500),
	add column obj_especifico varchar(1500),
	add column programa varchar(500),
	add column problemas varchar(750),
	add column causas varchar(500),
	add column lineas_estrategicas varchar(500),	
	add column gerente_proy_nombre varchar(50),
	add column gerente_proy_cedula varchar(20),
	add column gerente_proy_email varchar(50),
	add column gerente_proy_telefono varchar(50),	
	add column resp_tecnico_nombre varchar(50),
	add column resp_tecnico_cedula varchar(20),
	add column resp_tecnico_email varchar(50),
	add column resp_tecnico_telefono varchar(50),
	add column resp_registrador_nombre varchar(50),
	add column resp_registrador_cedula varchar(20),
	add column resp_registrador_email varchar(50),
	add column resp_registrador_telefono varchar(50),
	add column resp_administrativo_nombre varchar(50),
	add column resp_administrativo_cedula varchar(20),
	add column resp_administrativo_email varchar(50),
	add column resp_administrativo_telefono varchar(50),
	add column resp_admin_contratos_nombre varchar(50),
	add column resp_admin_contratos_cedula varchar(20),
	add column resp_admin_contratos_email varchar(50),
	add column resp_admin_contratos_telefono varchar(50),
	add column fase_id numeric(10);
    
    
	
CREATE TABLE fases_proyecto (
    fase_id numeric(10) NOT NULL,
    nombre character varying(50) NOT NULL
);

alter table fases_proyecto
    add constraint ak1_fase unique (nombre);

alter table fases_proyecto
    add constraint pk_fase primary key (fase_id);
    
CREATE TABLE afw_lic (
    id numeric(10,0) NOT NULL,
    corporacion character varying(100),	    
    serial character varying(50),
	licenciamiento character varying(100)	
);


alter table instrumentos 
	add column is_historico numeric(1,0);
    
ALTER TABLE organizacion 
ADD COLUMN administrador VARCHAR(500);

ALTER TABLE iniciativa
	add column alineacion_pdmp VARCHAR(500),
    add column alineacion_ods VARCHAR(500),
    add column cobertura_geografica VARCHAR(500),
	add column impacto_ciudadania VARCHAR(500),
	add column implementador_recursos VARCHAR(500),
	add column dependencia_responsable VARCHAR(500),
	add column sostenibilidad VARCHAR(500),
	add column dependencias_competentes VARCHAR(500);
-- Completed on 2023-04-27 19:43:20

--
-- PostgreSQL database dump complete
--


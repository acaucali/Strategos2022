CREATE TABLE organizacion (
    organizacion_id serial PRIMARY KEY,
    padre_id numeric(10,0),
    nombre varchar(150) NOT NULL,
    rif varchar(15),
    direccion varchar(150),
    telefono varchar(50),
    fax varchar(50),
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
    enlace_parcial varchar(100),
    subclase varchar(4),
    visible numeric(1,0) NOT NULL,
    read_only numeric(1,0)
);


CREATE TABLE organizacion_memo (
    organizacion_id serial NOT NULL,
    tipo numeric(2,0) NOT NULL,
    descripcion varchar(max)
);

CREATE TABLE ideas_evaluadas (
  evaluacion_id serial,
  idea_id serial
);

CREATE TABLE bp_tipos_propuestas (
  tipo_propuesta_id serial PRIMARY KEY,
  tipo_propuesta varchar(50) NULL
);

CREATE TABLE bp_tipos_objetivos (
  tipo_objetivo_id serial PRIMARY KEY,
  descripcion_objetivo varchar(250) NULL
);

CREATE TABLE bp_ideas_proyectos (
  idea_id serial PRIMARY KEY,
  nombre_idea varchar(250) NOT NULL,
  descripcion_idea varchar NOT NULL,
  tipo_propuesta_id serial,
  impacto varchar NOT NULL,
  problematica varchar NOT NULL,
  poblacion varchar NOT NULL,
  focalizacion varchar NULL,
  tipo_objetivo_id serial,
  finaciacion varchar(500) NOT NULL,
  depedencias_participantes varchar(500) NOT NULL,
  dependencia_persona serial,
  persona_encargada varchar(50) NOT NULL,
  persona_contacto_datos varchar(250) NOT NULL,  
  dependencia_id serial NULL,
  proyectos_ejecutados varchar NOT NULL,
  capacidad_tecnica varchar NOT NULL,
  fecha_idea timestamp without time zone,
  anio_formulacion varchar(4) NULL,
  estatus_idea_id serial,
  fecha_estatus timestamp without time zone,
  historico numeric(1) NULL,
  valor_ultima_evaluacion float NULL,
  fecha_ultima_evaluacion timestamp without time zone,
  observaciones varchar NULL,
  documento_id serial
);

CREATE TABLE bp_ideas_anexos (
  documento_id serial PRIMARY KEY,
  titulo_documento varchar(250) NOT NULL,
  descripcion varchar(500) NOT NULL,
  documento_ruta varchar(1000) NOT NULL,
  idea_id serial,
  tipo numeric(1)
);

CREATE TABLE bp_evaluacion_ideas_detalle (
  evaluacion_id serial PRIMARY KEY,
  idea_id serial,
  criterio varchar(200) NULL,
  peso float NULL,
  valor_evaluado float NULL
);

CREATE TABLE bp_evaluacion_ideas (
  evaluacion_id serial PRIMARY KEY,
  fecha_evaluacion timestamp without time zone,
  observacion varchar(500) NULL,
);

CREATE TABLE estatus_proyecto (
  estatus_proyecto_id serial PRIMARY KEY,
  estatus varchar(50) NULL
);

CREATE TABLE estatus_ideas (
  estatus_idea_id serial PRIMARY KEY,
  estatus varchar(50) NULL
);

CREATE TABLE bp_criterios_evaluacion (
  criterio_id serial PRIMARY KEY,
  control varchar(100) NULL,
  peso float NULL
);




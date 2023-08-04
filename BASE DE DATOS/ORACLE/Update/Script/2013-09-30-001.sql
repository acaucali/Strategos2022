ALTER TABLE plan DROP COLUMN creado;
ALTER TABLE plan DROP COLUMN modificado;
ALTER TABLE plan DROP COLUMN creado_id;
ALTER TABLE plan DROP COLUMN modificado_id;
ALTER TABLE plan DROP COLUMN serie_vigente_id;

CREATE TABLE planes
(
	plan_id              NUMBER(10) NOT NULL ,
	padre_id             NUMBER(10) NULL ,
	plan_impacta_id      NUMBER(10) NULL ,
	organizacion_id      NUMBER(10) NOT NULL ,
	nombre               VARCHAR2(50) NOT NULL ,
	ano_inicial          NUMBER(4) NOT NULL ,
	ano_final            NUMBER(4) NOT NULL ,
	tipo                 NUMBER(1) NULL ,
	activo               NUMBER(1) NULL ,
	revision             NUMBER(2) NOT NULL ,
	metodologia_id       NUMBER(10) NOT NULL ,
	clase_id             NUMBER(10) NOT NULL ,
	clase_id_indicadores_totales NUMBER(10) NULL ,
	ind_total_imputacion_id NUMBER(10) NULL ,
	ind_total_iniciativa_id NUMBER(10) NULL ,
	ultima_medicion_anual FLOAT NULL ,
	ultima_medicion_parcial FLOAT NULL ,
	nl_ano_indicador_id  NUMBER(10) NULL ,
	nl_par_indicador_id  NUMBER(10) NULL ,
	fecha_ultima_medicion VARCHAR2(10) NULL ,
	crecimiento          NUMBER(1) NULL ,
	esquema              VARCHAR2(50) NULL 
);

CREATE UNIQUE INDEX pk_planes ON planes
(plan_id   ASC);

ALTER TABLE planes
	ADD CONSTRAINT  pk_planes PRIMARY KEY (plan_id);

CREATE  INDEX IE_padre_plan ON planes
(padre_id   ASC);

CREATE  INDEX IE_impacta_plan ON planes
(plan_impacta_id   ASC);

CREATE  INDEX IE_organizacion_plan ON planes
(organizacion_id   ASC);

CREATE  INDEX IE_metodologia_plan ON planes
(metodologia_id   ASC);

CREATE  INDEX IE_clase_plan ON planes
(clase_id   ASC);

CREATE  INDEX IE_clasetotales_plan ON planes
(clase_id_indicadores_totales   ASC);

ALTER TABLE planes
	ADD (CONSTRAINT FK_padre_planes FOREIGN KEY (padre_id) REFERENCES planes (plan_id) ON DELETE SET NULL);

ALTER TABLE planes
	ADD (CONSTRAINT FK_planImpacta_plan FOREIGN KEY (plan_impacta_id) REFERENCES planes (plan_id) ON DELETE SET NULL);

ALTER TABLE planes
	ADD (CONSTRAINT FK_organizacion_plan FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_metodologia_plan FOREIGN KEY (metodologia_id) REFERENCES metodologia (metodologia_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_clase_plan FOREIGN KEY (clase_id) REFERENCES clase (clase_id) ON DELETE CASCADE);

ALTER TABLE planes
	ADD (CONSTRAINT FK_clasetotales_plan FOREIGN KEY (clase_id_indicadores_totales) REFERENCES clase (clase_id) ON DELETE SET NULL);

INSERT INTO PLANES
(
	plan_id,
	padre_id,
	plan_impacta_id,
	organizacion_id,
	nombre,
	ano_inicial,
	ano_final,
	tipo,
	activo,
	revision,
	metodologia_id,
	clase_id,
	clase_id_indicadores_totales,
	ind_total_imputacion_id,
	ind_total_iniciativa_id,
	ultima_medicion_anual,
	ultima_medicion_parcial,
	nl_ano_indicador_id,
	nl_par_indicador_id,
	fecha_ultima_medicion,
	crecimiento,
	esquema
)
SELECT 
	plan_id,
	padre_id,
	plan_impacta_id,
	organizacion_id,
	nombre,
	ano_inicial,
	ano_final,
	tipo,
	activo,
	revision,
	metodologia_id,
	clase_id,
	clase_id_indicadores_totales,
	ind_total_imputacion_id,
	ind_total_iniciativa_id,
	ultima_medicion_anual,
	ultima_medicion_parcial,
	nl_ano_indicador_id,
	nl_par_indicador_id,
	fecha_ultima_medicion,
	crecimiento,
	esquema
FROM PLAN;

ALTER TABLE iniciativa DROP CONSTRAINT fk_iniciativa_asociada_plan;
ALTER TABLE iniciativa
	ADD (CONSTRAINT fk_iniciativa_asociada_plan FOREIGN KEY (iniciativa_asociada_plan_id) REFERENCES planes (plan_id) ON DELETE SET NULL);

ALTER TABLE iniciativa_por_plan DROP CONSTRAINT fk_plan_iniciativa;	
ALTER TABLE iniciativa_por_plan
	ADD (CONSTRAINT fk_plan_iniciativa FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

ALTER TABLE perspectiva DROP CONSTRAINT fk_perspectiva_plan;		
ALTER TABLE perspectiva
	ADD (CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

COMMIT;
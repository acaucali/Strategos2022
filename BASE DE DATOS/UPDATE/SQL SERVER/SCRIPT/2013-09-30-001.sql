ALTER TABLE plan DROP COLUMN creado;
ALTER TABLE plan DROP COLUMN modificado;
ALTER TABLE plan DROP COLUMN creado_id;
ALTER TABLE plan DROP COLUMN modificado_id;
ALTER TABLE plan DROP COLUMN serie_vigente_id;
ALTER TABLE iniciativa DROP CONSTRAINT fk_iniciativa_asociada_plan;
ALTER TABLE iniciativa_por_plan DROP CONSTRAINT fk_plan_iniciativa;
ALTER TABLE perspectiva DROP CONSTRAINT fk_perspectiva_plan;

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

ALTER TABLE iniciativa
	ADD CONSTRAINT fk_iniciativa_asociada_plan FOREIGN KEY (iniciativa_asociada_plan_id) REFERENCES planes (plan_id) ON DELETE SET NULL;
	
ALTER TABLE iniciativa_por_plan
	ADD CONSTRAINT fk_plan_iniciativa FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;
	
ALTER TABLE perspectiva
	ADD CONSTRAINT fk_perspectiva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE;

DROP TABLE PLAN;

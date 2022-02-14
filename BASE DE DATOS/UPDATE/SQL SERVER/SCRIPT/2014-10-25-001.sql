ALTER TABLE meta DROP CONSTRAINT fk_plan_meta;
ALTER TABLE inc_combinacion DROP CONSTRAINT fk_plan_inc_combinacion;
ALTER TABLE indicador_estado DROP CONSTRAINT fk_plan_indicador_estado;
ALTER TABLE modelo DROP CONSTRAINT fk_plan_modelo;
ALTER TABLE vector DROP CONSTRAINT fk_plan_vector;
ALTER TABLE pry_actividad_plan DROP CONSTRAINT fk_pry_plan_actividad;
ALTER TABLE pry_proyecto_plan DROP CONSTRAINT fk_pry_plan_proyecto;
ALTER TABLE inc_actividad_partida DROP CONSTRAINT pk_plan_inc_actividad_partida;
ALTER TABLE iniciativa_partida DROP CONSTRAINT pk_plan_iniciativa_partida;
ALTER TABLE plan_nivel DROP CONSTRAINT pk_plan_plan_nivel;
ALTER TABLE responsable_por_plan DROP CONSTRAINT pk_plan_responsable_por_plan;
DROP TABLE plan;

DELETE FROM meta WHERE plan_id NOT IN (SELECT plan_id FROM planes);
ALTER TABLE meta
	ADD CONSTRAINT fk_plan_meta FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON UPDATE NO ACTION ON DELETE CASCADE;		

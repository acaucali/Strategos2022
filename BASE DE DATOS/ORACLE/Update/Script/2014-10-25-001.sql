DECLARE
	vCount NUMBER(5);

BEGIN
	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('meta') AND CONSTRAINT_NAME = UPPER('fk_plan_meta');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE meta DROP CONSTRAINT fk_plan_meta';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('inc_combinacion') AND CONSTRAINT_NAME = UPPER('fk_plan_inc_combinacion');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE inc_combinacion DROP CONSTRAINT fk_plan_inc_combinacion';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('indicador_estado') AND CONSTRAINT_NAME = UPPER('fk_plan_indicador_estado');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE indicador_estado DROP CONSTRAINT fk_plan_indicador_estado';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('modelo') AND CONSTRAINT_NAME = UPPER('fk_plan_modelo');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE modelo DROP CONSTRAINT fk_plan_modelo';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('vector') AND CONSTRAINT_NAME = UPPER('fk_plan_vector');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE vector DROP CONSTRAINT fk_plan_vector';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_actividad_plan') AND CONSTRAINT_NAME = UPPER('fk_pry_plan_actividad');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_actividad_plan DROP CONSTRAINT fk_pry_plan_actividad';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('pry_proyecto_plan') AND CONSTRAINT_NAME = UPPER('fk_pry_plan_proyecto');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE pry_proyecto_plan DROP CONSTRAINT fk_pry_plan_proyecto';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('inc_actividad_partida') AND CONSTRAINT_NAME = UPPER('pk_plan_inc_actividad_partida');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE inc_actividad_partida DROP CONSTRAINT pk_plan_inc_actividad_partida';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('iniciativa_partida') AND CONSTRAINT_NAME = UPPER('pk_plan_iniciativa_partida');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE iniciativa_partida DROP CONSTRAINT pk_plan_iniciativa_partida';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('plan_nivel') AND CONSTRAINT_NAME = UPPER('pk_plan_plan_nivel');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE plan_nivel DROP CONSTRAINT pk_plan_plan_nivel';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('responsable_por_plan') AND CONSTRAINT_NAME = UPPER('pk_plan_responsable_por_plan');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE responsable_por_plan DROP CONSTRAINT pk_plan_responsable_por_plan';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM DBA_TABLES WHERE TABLE_NAME = UPPER('plan');
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DROP TABLE plan';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM meta WHERE plan_id NOT IN (SELECT plan_id FROM planes);
	IF vCount > 0 THEN 
	BEGIN
		execute immediate 'DELETE FROM meta WHERE plan_id NOT IN (SELECT plan_id FROM planes)';
	END;
	END IF;

	SELECT COUNT(*) INTO vCount FROM ALL_CONSTRAINTS WHERE TABLE_NAME = UPPER('meta') AND CONSTRAINT_NAME = UPPER('fk_plan_meta');
	IF vCount = 0 THEN 
	BEGIN
		execute immediate 'ALTER TABLE meta ADD CONSTRAINT fk_plan_meta FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE';
	END;
	END IF;
	
END;
/

COMMIT;

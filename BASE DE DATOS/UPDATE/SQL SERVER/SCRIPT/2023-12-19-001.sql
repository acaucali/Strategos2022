-- Modificar tipo de columnas
ALTER TABLE iniciativa_objeto 
ALTER COLUMN objeto VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN iniciativa_estrategica VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN lider_iniciativa VARCHAR(250);

ALTER TABLE iniciativa
ALTER COLUMN tipo_iniciativa VARCHAR(250);

ALTER TABLE iniciativa
ALTER COLUMN definicion_problema VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN alcance VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_general VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivos_especificos VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN organizaciones_involucradas VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_historico VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_nacional VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_estrategico_v VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_general_v VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_especifico VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN programa VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN problemas VARCHAR(750);

ALTER TABLE iniciativa
ALTER COLUMN causas VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN lineas_estrategicas VARCHAR(500);

-- Renombrar columnas
EXEC sp_rename 'iniciativa.monto_moneda_extranjera', 'monto_moneda_extr', 'COLUMN';
EXEC sp_rename 'iniciativa.situacion_presupuestaria', 'sit_presupuestaria', 'COLUMN';
EXEC sp_rename 'iniciativa.gerencia_general_responsable', 'gerencia_general_resp', 'COLUMN';
EXEC sp_rename 'iniciativa.proyecto_presupuestario_asociado', 'proyecto_presup_asociado', 'COLUMN';
EXEC sp_rename 'iniciativa.objetivo_historico', 'obj_historico', 'COLUMN';
EXEC sp_rename 'iniciativa.objetivo_nacional', 'obj_nacional', 'COLUMN';
EXEC sp_rename 'iniciativa.objetivo_estrategico_v', 'obj_estrategico_v', 'COLUMN';
EXEC sp_rename 'iniciativa.objetivo_general_v', 'obj_general_v', 'COLUMN';
EXEC sp_rename 'iniciativa.objetivo_especifico', 'obj_especifico', 'COLUMN';
EXEC sp_rename 'iniciativa.gerente_proyecto_nombre', 'gerente_proy_nombre', 'COLUMN';
EXEC sp_rename 'iniciativa.gerente_proyecto_cedula', 'gerente_proy_cedula', 'COLUMN';
EXEC sp_rename 'iniciativa.gerente_proyecto_email', 'gerente_proy_email', 'COLUMN';
EXEC sp_rename 'iniciativa.gerente_proyecto_telefono', 'gerente_proy_telefono', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_admin_contratos_cedula', 'resp_admin_contratos_cedula', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_admin_contratos_nombre', 'resp_admin_contratos_nombre', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_admin_contratos_email', 'resp_admin_contratos_email', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_admin_contratos_telefono', 'resp_admin_contratos_telefono', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_administrativo_nombre', 'resp_administrativo_nombre', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_administrativo_cedula', 'resp_administrativo_cedula', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_administrativo_email', 'resp_administrativo_email', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_administrativo_telefono', 'resp_administrativo_telefono', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_registrador_nombre', 'resp_registrador_nombre', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_registrador_cedula', 'resp_registrador_cedula', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_registrador_email', 'resp_registrador_email', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_registrador_telefono', 'resp_registrador_telefono', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_tecnico_nombre', 'resp_tecnico_nombre', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_tecnico_cedula', 'resp_tecnico_cedula', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_tecnico_email', 'resp_tecnico_email', 'COLUMN';
EXEC sp_rename 'iniciativa.responsable_tecnico_telefono', 'resp_tecnico_telefono', 'COLUMN';

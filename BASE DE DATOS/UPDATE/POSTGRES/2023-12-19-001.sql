ALTER TABLE iniciativa_objeto 
ALTER COLUMN objeto TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN iniciativa_estrategica TYPE VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN lider_iniciativa TYPE VARCHAR(250);

ALTER TABLE iniciativa
ALTER COLUMN tipo_iniciativa TYPE VARCHAR(250);

ALTER TABLE iniciativa
ALTER COLUMN definicion_problema TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN alcance TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_general TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivos_especificos TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN organizaciones_involucradas TYPE VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_historico TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_nacional TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_estrategico_v TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_general_v TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN objetivo_especifico TYPE VARCHAR(1500);

ALTER TABLE iniciativa
ALTER COLUMN programa TYPE VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN problemas TYPE VARCHAR(750);

ALTER TABLE iniciativa
ALTER COLUMN causas TYPE VARCHAR(500);

ALTER TABLE iniciativa
ALTER COLUMN lineas_estrategicas TYPE VARCHAR(500);

ALTER TABLE iniciativa
RENAME COLUMN monto_moneda_extranjera TO monto_moneda_extr;

ALTER TABLE iniciativa
RENAME COLUMN situacion_presupuestaria TO sit_presupuestaria;

ALTER TABLE iniciativa
RENAME COLUMN gerencia_general_responsable TO gerencia_general_resp;

ALTER TABLE iniciativa
RENAME COLUMN proyecto_presupuestario_asociado TO proyecto_presup_asociado;

ALTER TABLE iniciativa
RENAME COLUMN objetivo_historico TO obj_historico;

ALTER TABLE iniciativa
RENAME COLUMN objetivo_nacional TO obj_nacional;

ALTER TABLE iniciativa
RENAME COLUMN objetivo_estrategico_v TO obj_estrategico_v;

ALTER TABLE iniciativa
RENAME COLUMN objetivo_general_v TO obj_general_v;

ALTER TABLE iniciativa
RENAME COLUMN objetivo_especifico TO obj_especifico;

ALTER TABLE iniciativa
RENAME COLUMN gerente_proyecto_nombre TO gerente_proy_nombre;

ALTER TABLE iniciativa
RENAME COLUMN gerente_proyecto_cedula TO gerente_proy_cedula;

ALTER TABLE iniciativa
RENAME COLUMN gerente_proyecto_email TO gerente_proy_email;

ALTER TABLE iniciativa
RENAME COLUMN gerente_proyecto_telefono TO gerente_proy_telefono;

ALTER TABLE iniciativa
RENAME COLUMN responsable_admin_contratos_cedula TO resp_admin_contratos_cedula;

ALTER TABLE iniciativa
RENAME COLUMN responsable_admin_contratos_nombre TO resp_admin_contratos_nombre;

ALTER TABLE iniciativa
RENAME COLUMN responsable_admin_contratos_email TO resp_admin_contratos_email;

ALTER TABLE iniciativa
RENAME COLUMN responsable_admin_contratos_telefono TO resp_admin_contratos_telefono;

ALTER TABLE iniciativa
RENAME COLUMN responsable_administrativo_nombre TO resp_administrativo_nombre;

ALTER TABLE iniciativa
RENAME COLUMN responsable_administrativo_cedula TO resp_administrativo_cedula;

ALTER TABLE iniciativa
RENAME COLUMN responsable_administrativo_email TO resp_administrativo_email;

ALTER TABLE iniciativa
RENAME COLUMN responsable_administrativo_telefono TO resp_administrativo_telefono;

ALTER TABLE iniciativa
RENAME COLUMN responsable_registrador_nombre TO resp_registrador_nombre;

ALTER TABLE iniciativa
RENAME COLUMN responsable_registrador_cedula TO resp_registrador_cedula;

ALTER TABLE iniciativa
RENAME COLUMN responsable_registrador_email TO resp_registrador_email;

ALTER TABLE iniciativa
RENAME COLUMN responsable_registrador_telefono TO resp_registrador_telefono;

ALTER TABLE iniciativa
RENAME COLUMN responsable_tecnico_nombre TO resp_tecnico_nombre;

ALTER TABLE iniciativa
RENAME COLUMN responsable_tecnico_cedula TO resp_tecnico_cedula;

ALTER TABLE iniciativa
RENAME COLUMN responsable_tecnico_email TO resp_tecnico_email;

ALTER TABLE iniciativa
RENAME COLUMN responsable_tecnico_telefono TO resp_tecnico_telefono;


UPDATE afw_sistema set actual = '8.01-230505';  
UPDATE afw_sistema set build = 230505;

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
    ADD CONSTRAINT pk_cargo PRIMARY KEY (cargo_id);

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS', 'Gestionar Cargos', NULL, 0, 3, 1, 'Gestionar Categor�as de Medici�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_ADD', 'Crear', 'CARGOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_EDIT', 'Modificar', 'CARGOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_DELETE', 'Eliminar', 'CARGOS', 1, 3, 1, 'Eliminar');

ALTER TABLE iniciativa ADD COLUMN cargo_id numeric(10);
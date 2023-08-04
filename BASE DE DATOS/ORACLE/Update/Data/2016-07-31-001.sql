--Portafolio
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO', 'Portafolio', NULL, 0, 13, 1, 'Portafolio');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_ADD', 'Crear', 'PORTAFOLIO', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_EDIT', 'Modificar', 'PORTAFOLIO', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_DELETE', 'Eliminar', 'PORTAFOLIO', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_VIEW', 'Ver', 'PORTAFOLIO', 1, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_PRINT', 'Imprimir', 'PORTAFOLIO', 1, 5, 1, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_VISTA', 'Graficar', 'PORTAFOLIO', 1, 6, 1, 'Graficar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA', 'Iniciativa', 'PORTAFOLIO', 1, 7, 1, 'Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA_ADD', 'Agregar', 'PORTAFOLIO_INICIATIVA', 2, 1, 1, 'Agregar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA_DELETE', 'Remover', 'PORTAFOLIO_INICIATIVA', 2, 2, 1, 'Remover');

-- Portafolio
DELETE FROM afw_objeto_auditable WHERE objeto_id = 50;
DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 50;
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (50, 'com.visiongc.app.strategos.portafolios.model.Portafolio', 'id', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'activo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'estatusId', 4); 

UPDATE afw_sistema set actual = '8.01-160731';

COMMIT;

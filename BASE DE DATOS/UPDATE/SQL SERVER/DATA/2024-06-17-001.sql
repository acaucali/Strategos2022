INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS', 'Gestionar Tipos de Proyectos', NULL, 0, 18, 1, 'Gestionar Tipos de Proyectos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_ADD', 'Crear', 'TIPOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_EDIT', 'Modificar', 'TIPOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_DELETE', 'Eliminar', 'TIPOS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_PRINT', 'Imprimir Reporte de Tipos de Proyectos', 'TIPOS', 1, 4, 1, 'Imprimir Reporte de Tipos de Proyectos');


INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTES_ORGANIZACION', 'Reportes Organizacion', 'ORGANIZACION', 1, 16, 0, 'Reportes Organizacion');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTE_CUMPLIMIENTO', 'Reporte Cumplimiento', 'REPORTES_ORGANIZACION', 2, 1, 0, 'Reporte Cumplimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTE_DEPENDENCIAS_OMISIVAS', 'Reporte Dependencias Omisivas', 'REPORTES_ORGANIZACION', 1, 2, 0, 'Reporte Dependencias Omisivas');
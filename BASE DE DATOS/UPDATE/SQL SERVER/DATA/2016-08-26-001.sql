--Portafolio
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_CALCULAR', 'Calcular', 'PORTAFOLIO', 1, 8, 1, 'Graficar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_ASIGNARPESOS', 'Asignar Pesos', 'PORTAFOLIO', 1, 9, 1, 'Asignar Pesos');

UPDATE afw_sistema set actual = '8.01-160826';

-- Servicio
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (72, 'com.visiongc.servicio.strategos.servicio.model.Servicio', 'usuarioId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (72, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (72, 'fecha', 1);

UPDATE afw_sistema set actual = '8.01-160430';

--Auditoria de los estatus de la iniciativa
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'estatusId', 4);

UPDATE afw_sistema set actual = '8.01-160705';

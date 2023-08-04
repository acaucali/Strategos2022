INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_HISTORICO',  'INICIATIVA',  'Marcar o Desmarcar Historico',  2,   13,   0,  'Marcar o Desmarcar Historico');

UPDATE afw_sistema set actual = '8.01-160519';

COMMIT;

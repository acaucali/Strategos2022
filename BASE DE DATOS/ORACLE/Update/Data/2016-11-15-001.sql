UPDATE clase SET tipo = 1 WHERE clase_id in (select clase_id from iniciativa);
UPDATE clase SET tipo = 1 WHERE clase_id in (select clase_id from pry_actividad);
UPDATE clase SET tipo = 3 WHERE clase_id in (select clase_id from perspectiva);

UPDATE afw_sistema set actual = '8.01-161115';

COMMIT;

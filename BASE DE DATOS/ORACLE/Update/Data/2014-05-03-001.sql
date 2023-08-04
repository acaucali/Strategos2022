UPDATE clase SET visible = 1;
UPDATE clase SET visible = 0 WHERE clase_id IN (SELECT clase_id FROM perspectiva WHERE clase_id IS NOT NULL);
UPDATE clase SET visible = 0 WHERE clase_id IN (SELECT clase_id FROM planes WHERE clase_id IS NOT NULL);

COMMIT;
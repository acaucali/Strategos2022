ALTER TABLE afw_usuario ADD is_system NUMBER(1) NULL;
UPDATE afw_usuario SET is_system = 0;
ALTER TABLE afw_usuario MODIFY is_system NUMBER(1) NOT NULL;
UPDATE afw_usuario SET is_system = 1 WHERE u_id = 'admin';

COMMIT;
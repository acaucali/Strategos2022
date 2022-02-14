ALTER TABLE afw_usuario ADD COLUMN is_system numeric(1,0);
UPDATE afw_usuario SET is_system = 0;
ALTER TABLE afw_usuario ALTER COLUMN is_system SET NOT NULL;
UPDATE afw_usuario SET is_system = 1 WHERE u_id = 'admin';

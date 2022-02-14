DROP INDEX if_afw_pwd_historia;
CREATE UNIQUE INDEX if_afw_pwd_historia
  ON afw_pwd_historia
  USING btree
  (usuario_id, fecha );
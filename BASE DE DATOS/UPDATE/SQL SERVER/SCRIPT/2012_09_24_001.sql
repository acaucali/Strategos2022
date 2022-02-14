ALTER TABLE celda ADD COLUMN configuracion character(2000);
ALTER TABLE celda ADD COLUMN creado timestamp without time zone;
ALTER TABLE celda ADD COLUMN modificado timestamp without time zone;
ALTER TABLE celda ADD COLUMN creado_id numeric(10);
ALTER TABLE celda ADD COLUMN modificado_id numeric(10);

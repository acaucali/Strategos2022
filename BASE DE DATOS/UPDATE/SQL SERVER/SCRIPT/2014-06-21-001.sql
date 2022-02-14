ALTER TABLE iniciativa ADD COLUMN alerta_zv double precision;
ALTER TABLE iniciativa ADD COLUMN alerta_za double precision;
ALTER TABLE iniciativa ADD COLUMN crecimiento numeric(1,0);
ALTER TABLE iniciativa ADD COLUMN fecha_ultima_medicion character varying(10);

UPDATE iniciativa set alerta_zv = alerta_iniciativa_zv, alerta_za = alerta_iniciativa_za;

ALTER TABLE iniciativa DROP COLUMN alerta_iniciativa_zv;
ALTER TABLE iniciativa DROP COLUMN alerta_iniciativa_za;

ALTER TABLE inc_actividad ADD COLUMN alerta_zv double precision;
ALTER TABLE inc_actividad ADD COLUMN alerta_za double precision;

UPDATE inc_actividad set alerta_zv = porcentaje_verde, alerta_za = porcentaje_amarillo;

ALTER TABLE inc_actividad DROP COLUMN porcentaje_verde;
ALTER TABLE inc_actividad DROP COLUMN porcentaje_amarillo;

ALTER TABLE pry_actividad ADD COLUMN crecimiento numeric(1,0);
ALTER TABLE pry_actividad ADD COLUMN porcentaje_completado double precision;
ALTER TABLE pry_actividad ADD COLUMN porcentaje_esperado double precision;
ALTER TABLE pry_actividad ADD COLUMN porcentaje_ejecutado double precision;
ALTER TABLE pry_actividad ADD COLUMN fecha_ultima_medicion character varying(10);

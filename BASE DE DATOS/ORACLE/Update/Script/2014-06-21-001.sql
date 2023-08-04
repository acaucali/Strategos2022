ALTER TABLE iniciativa ADD alerta_zv FLOAT;
ALTER TABLE iniciativa ADD alerta_za FLOAT;
ALTER TABLE iniciativa ADD crecimiento NUMBER(1);
ALTER TABLE iniciativa ADD fecha_ultima_medicion VARCHAR2(10);

UPDATE iniciativa set alerta_zv = alerta_iniciativa_zv, alerta_za = alerta_iniciativa_za;

ALTER TABLE iniciativa DROP COLUMN alerta_iniciativa_zv;
ALTER TABLE iniciativa DROP COLUMN alerta_iniciativa_za;

ALTER TABLE inc_actividad ADD alerta_zv FLOAT;
ALTER TABLE inc_actividad ADD alerta_za FLOAT;

UPDATE inc_actividad set alerta_zv = porcentaje_verde, alerta_za = porcentaje_amarillo;

ALTER TABLE inc_actividad DROP COLUMN porcentaje_verde;
ALTER TABLE inc_actividad DROP COLUMN porcentaje_amarillo;

ALTER TABLE pry_actividad ADD crecimiento NUMBER(1);
ALTER TABLE pry_actividad ADD porcentaje_completado FLOAT;
ALTER TABLE pry_actividad ADD porcentaje_esperado FLOAT;
ALTER TABLE pry_actividad ADD porcentaje_ejecutado FLOAT;
ALTER TABLE pry_actividad ADD fecha_ultima_medicion VARCHAR2(10);

COMMIT;
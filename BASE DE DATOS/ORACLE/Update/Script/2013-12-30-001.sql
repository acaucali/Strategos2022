ALTER TABLE EXPLICACION ADD tipo NUMBER(1) NULL;
UPDATE EXPLICACION SET tipo = 0;
ALTER TABLE EXPLICACION MODIFY tipo NUMBER(1) NOT NULL;

ALTER TABLE EXPLICACION ADD fecha_compromiso TIMESTAMP NULL;
ALTER TABLE EXPLICACION ADD fecha_real TIMESTAMP NULL;

ALTER TABLE CLASE_PROBLEMA ADD tipo NUMBER(1) NULL;
UPDATE CLASE_PROBLEMA SET tipo = 0;
ALTER TABLE CLASE_PROBLEMA MODIFY tipo NUMBER(1) NOT NULL;

COMMIT;
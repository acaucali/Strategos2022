UPDATE indicador SET VALOR_INICIAL_CERO = 0 where tipo = 1 and VALOR_INICIAL_CERO = 1 AND indicador_id IN (select indicador_id from PRY_ACTIVIDAD);
UPDATE afw_sistema set actual = '8.01-170702';

COMMIT;

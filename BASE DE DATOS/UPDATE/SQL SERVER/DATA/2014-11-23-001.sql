-- Configuracion Iniciativa
DELETE FROM afw_configuracion WHERE PARAMETRO = 'Strategos.Configuracion.Iniciativas';
INSERT INTO afw_configuracion (PARAMETRO, VALOR) VALUES ('Strategos.Configuracion.Iniciativas', '<?xml version="1.0" encoding="utf-8" standalone="no"?>
<iniciativa>
  <properties>
    <nombre>Iniciativa</nombre>
    <indicadores>
      <indicador>
        <tipo>1</tipo>
        <nombre>Avance</nombre>
        <crear>1</crear>
      </indicador>
      <indicador>
        <tipo>6</tipo>
        <nombre>Presupuesto</nombre>
        <crear>1</crear>
      </indicador>
      <indicador>
        <tipo>7</tipo>
        <nombre>Eficacia</nombre>
        <crear>1</crear>
      </indicador>
      <indicador>
        <tipo>8</tipo>
        <nombre>Eficiencia</nombre>
        <crear>1</crear>
      </indicador>
    </indicadores>
  </properties>
</iniciativa>');

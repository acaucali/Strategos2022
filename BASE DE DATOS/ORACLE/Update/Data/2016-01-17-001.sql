-- Configuracion Lista de Indicadores en el Plan
DELETE FROM afw_configuracion WHERE PARAMETRO = 'visorLista.visorIndicadoresPlan';
INSERT INTO afw_configuracion (PARAMETRO, VALOR) VALUES ('visorLista.visorIndicadoresPlan', '<?xml version="1.0" encoding="utf-8"?>
<xmlNodo id="configuracion.visorLista.visorIndicadoresPlan">
  <xmlLista>
    <xmlNodo id="columnas">
      <xmlLista>
        <xmlNodo id="alerta" orden="01" titulo="Alerta" ancho="40" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="nombre" orden="02" titulo="Indicadores" ancho="300" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="unidad" orden="03" titulo="Unidad de Medida" ancho="80" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="real" orden="04" titulo="Real" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="metaParcial" orden="05" titulo="Meta Parcial" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="estadoParcial" orden="06" titulo="Estado Parcial" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="ultimoPeriodoMedicion" orden="07" titulo="Último Período" ancho="130" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="metaAnual" orden="08" titulo="Meta Anual" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="estadoAnual" orden="09" titulo="Estado Anual" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="peso" orden="10" titulo="Peso" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="frecuencia" orden="11" titulo="Frecuencia" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="tipo" orden="12" titulo="Tipo" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="naturaleza" orden="13" titulo="Naturaleza" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="prioridad" orden="14" titulo="Prioridad" ancho="70" visible="false">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="orden" orden="15" titulo="Orden" ancho="60" visible="false">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="codigoEnlace" orden="16" titulo="Código de Enlace" ancho="150" visible="false">
          <xmlLista/>
        </xmlNodo>
      </xmlLista>
    </xmlNodo>
  </xmlLista>
</xmlNodo>');

-- Configuracion Lista de Indicadores
DELETE FROM afw_configuracion WHERE PARAMETRO = 'visorLista.visorIndicadores';
INSERT INTO afw_configuracion (PARAMETRO, VALOR) VALUES ('visorLista.visorIndicadores', '<?xml version="1.0" encoding="UTF-8"?>
<xmlNodo id="configuracion.visorLista.visorIndicadores">
  <xmlLista>
    <xmlNodo id="columnas">
      <xmlLista>
        <xmlNodo id="alerta" orden="01" titulo="Alerta" ancho="30" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="nombre" orden="02" titulo="Nombre" ancho="350" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="unidad" orden="03" titulo="Unidad de Medida" ancho="60" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="real" orden="04" titulo="Real" ancho="60" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="programado" orden="05" titulo="Programado" ancho="60" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="ultimoPeriodoMedicion" orden="06" titulo="Último Período" ancho="130" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="cumplimientoParcial" orden="07" titulo="% Cumplimiento Parcial" ancho="60" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="frecuencia" orden="08" titulo="Frecuencia" ancho="80" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="naturaleza" orden="09" titulo="Naturaleza" ancho="100" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="codigoEnlace" orden="10" titulo="Código de Enlace" ancho="90" visible="true">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="prioridad" orden="11" titulo="Prioridad" ancho="70" visible="false">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="orden" orden="12" titulo="Orden" ancho="60" visible="false">
          <xmlLista/>
        </xmlNodo>
        <xmlNodo id="cumplimientoAnual" orden="13" titulo="% Cumplimiento Anual" ancho="60" visible="false">
          <xmlLista/>
        </xmlNodo>
      </xmlLista>
    </xmlNodo>
  </xmlLista>
</xmlNodo>');

COMMIT;

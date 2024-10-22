/************************************************/
/*            Configuraci�n B�sica              */
/************************************************/
INSERT INTO AFW_USUARIO (USUARIO_ID, FULL_NAME, U_ID, PWD, IS_ADMIN, IS_CONNECTED, ESTATUS, BLOQUEADO, IS_SYSTEM) VALUES (1, 'Administrador', 'admin', '&H07056D763639BF0585A18A0E5BAB9343CBA302B251758A1953191E03342B', 1, 0, 0, 0, 1);
INSERT INTO ORGANIZACION (ORGANIZACION_ID, NOMBRE, MES_CIERRE, VISIBLE, READ_ONLY) VALUES (1, 'Organizaciones', 12, 1, 0);
INSERT INTO afw_configuracion (PARAMETRO, VALOR) VALUES ('Strategos.Estilos.Configuracion', '<?xml version="1.0" encoding="utf-8" standalone="no"?>
<configuracion>
  <properties>
    <estiloSuperior>height:30px; border-style:solid; border-width:1px; border-color:#666666; background-color:#2093b8;</estiloSuperior>
    <estiloSuperiorForma>height:25px; border-style:solid; border-width:1px; border-color:#666666; background-color:#2093b8; font-size:11px; color:#ffffff; font-family:Verdana;</estiloSuperiorForma>
    <estiloSuperiorFormaIzquierda>height:25px; border-style:solid; border-width:1px; border-color:#666666; background-color:#57c042; font-size:11px; color:#ffffff; font-family:Verdana;</estiloSuperiorFormaIzquierda>
    <estiloInferior>height:25px; border-style:none; border-width:1px; border-color:#666666; background-color:#2093b8; font-family:Verdana; font-size:11px; color:#ffffff; text-align:right</estiloInferior>
    <estiloFormaInterna>height:25px; border-style:solid; border-width:1px; border-color:#666666; background-color:#2093b8; font-family:Verdana; font-size:11px; color:#ffffff;</estiloFormaInterna>
    <estiloLetras>font-size:11px;color:#ffffff;font-family:Verdana;text-decoration:none;</estiloLetras>
    <mouseFueraBarraSuperiorForma>font-size:11px; color:#ffffff; font-family:Verdana; text-decoration:none;</mouseFueraBarraSuperiorForma>
    <mouseFueraBarraSuperiorFormaColor>#ffffff</mouseFueraBarraSuperiorFormaColor>
    <logo1>logo1</logo1>
  </properties>
</configuracion>');

-- Configuracion Iniciativa
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

-- Configuracion Lista de Indicadores en el Plan
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
        <xmlNodo id="ultimoPeriodoMedicion" orden="07" titulo="�ltimo Per�odo" ancho="130" visible="true">
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
        <xmlNodo id="codigoEnlace" orden="16" titulo="C�digo de Enlace" ancho="150" visible="false">
          <xmlLista/>
        </xmlNodo>
      </xmlLista>
    </xmlNodo>
  </xmlLista>
</xmlNodo>');

-- Configuracion Lista de Indicadores
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
        <xmlNodo id="ultimoPeriodoMedicion" orden="06" titulo="�ltimo Per�odo" ancho="130" visible="true">
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
        <xmlNodo id="codigoEnlace" orden="10" titulo="C�digo de Enlace" ancho="90" visible="true">
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

/************************************************/
/************************************************/

/************************************************/
/*             ESPECIFICAR SISTEMA              */
/************************************************/

INSERT INTO AFW_SISTEMA (id, version, RDBMS_ID, PRODUCTO, CONEXION) VALUES (1, NULL, 'POSTGRESQL', 'PROTOTIPO', '2063<46,2451?589><=-(''#');
UPDATE AFW_SISTEMA SET VERSION='1.0.0.2';

/************************************************/
/************************************************/
/************************************************/
/*                  Estatus Iniciativa          */
/************************************************/
INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (1, 'Sin Iniciar', 0, 0, 1, 0, 0);
INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (2, 'En Ejecucion', 0.1, 99.99, 0, 0, 0);
INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (3, 'Cancelado', NULL, NULL, 1, 1, 1);
INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (4, 'Suspendido', NULL, NULL, 1, 1, 1);
INSERT INTO iniciativa_estatus (id, nombre, porcentaje_inicial, porcentaje_final, sistema, bloquear_medicion, bloquear_indicadores) VALUES (5, 'Culminado', 100, 100, 1, 1, 1);
/************************************************/
/************************************************/
/************************************************/
/*                  Seguridad Persmisos         */
/************************************************/
/*            	Aplicacion              	  	*/
--Unidad
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD', 'Gestionar Unidades de Medida', NULL, 0, 0, 1, 'Gestionar Unidades de Medida');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_ADD', 'Crear', 'UNIDAD', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_EDIT', 'Modificar', 'UNIDAD', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_DELETE', 'Eliminar', 'UNIDAD', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_PRINT', 'Imprimir Reporte de Unidades', 'UNIDAD', 1, 4, 1, 'Imprimir Reporte de Unidades');
--Causa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA', 'Gestionar Causas', NULL, 0, 1, 1, 'Gestionar Causas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_ADD', 'Crear', 'CAUSA', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_EDIT', 'Modificar', 'CAUSA', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_DELETE', 'Eliminar', 'CAUSA', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_PRINT', 'Imprimir Reporte de Causas', 'CAUSA', 1, 4, 1, 'Imprimir Reporte de Causas');
--Foro
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('FORO', 'Gestionar Foros', NULL, 0, 2, 1, 'Gestionar Foros');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('FORO_ADD', 'Crear', 'FORO', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('FORO_VIEW', 'Ver', 'FORO', 1, 2, 1, 'Ver');
--Categoria
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA', 'Gestionar Categor�as de Medici�n', NULL, 0, 3, 1, 'Gestionar Categor�as de Medici�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_ADD', 'Crear', 'CATEGORIA', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_EDIT', 'Modificar', 'CATEGORIA', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_DELETE', 'Eliminar', 'CATEGORIA', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_PRINT', 'Imprimir Reporte de Categor�as', 'CATEGORIA', 1, 4, 1, 'Imprimir Reporte de Categor�as');
--Estatus
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS', 'Gestionar Estados de Acciones', NULL, 0, 4, 1, 'Gestionar Estados de Acciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_ADD', 'Crear', 'ESTATUS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_EDIT', 'Modificar', 'ESTATUS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_DELETE', 'Eliminar', 'ESTATUS', 1, 3, 1, 'Eliminar');
--Responsable
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE', 'Gestionar Responsables', NULL, 0, 5, 1, 'Gestionar Responsables');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_ADD', 'Crear', 'RESPONSABLE', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_EDIT', 'Modificar', 'RESPONSABLE', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_DELETE', 'Eliminar', 'RESPONSABLE', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_PRINT', 'Reporte', 'RESPONSABLE', 1, 4, 1, 'Reporte');
--Imputacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION', 'Gestionar Plan de Cuentas', NULL, 0, 6, 1, 'Gestionar Plan de Cuentas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_ADD', 'Crear', 'IMPUTACION', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_EDIT', 'Modificar', 'IMPUTACION', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_DELETE', 'Eliminar', 'IMPUTACION', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_MASK', 'Definir Mascara de C�digo de Plan de Cuentas', 'IMPUTACION', 1, 4, 1, 'Definir Mascara de C�digo de Plan de Cuentas');
--Serie de Tiempo
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO', 'Gestionar Series de Tiempo', NULL, 0, 7, 1, 'Gestionar Series de Tiempo');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_ADD', 'Crear', 'SERIE_TIEMPO', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_EDIT', 'Modificar', 'SERIE_TIEMPO', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_DELETE', 'Eliminar', 'SERIE_TIEMPO', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_PRINT', 'Imprimir Reporte de Series de Tiempo', 'SERIE_TIEMPO', 1, 4, 1, 'Imprimir Reporte de Series de Tiempo');
--Explicacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION', 'Gestionar Explicaciones', NULL, 0, 8, 1, 'Gestionar Explicaciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_ADD', 'Crear', 'EXPLICACION', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_EDIT', 'Modificar', 'EXPLICACION', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_DELETE', 'Eliminar', 'EXPLICACION', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_VIEW', 'Ver', 'EXPLICACION', 1, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_PUBLIC', 'Publicar', 'EXPLICACION', 1, 5, 1, 'Publicar');
--Configuracion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIG', 'Configuraci�n del Sistema', NULL, 0, 9, 1, 'Configuraci�n del Sistema');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIGURACION_SISTEMA', 'Gestionar Configuraci�n del Sistema', 'CONFIG', 1, 1, 0, 'Gestionar Configuraci�n del Sistema');
--Organizacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION', 'Gestionar Organizaciones', NULL, 0, 10, 1, 'Gestionar Organizaciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_ADD', 'Crear Organizaci�n', 'ORGANIZACION', 1, 1, 0, 'Crear Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_EDIT', 'Modificar Organizaci�n', 'ORGANIZACION', 1, 2, 0, 'Modificar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_COPY', 'Copiar Organizaci�n', 'ORGANIZACION', 1, 3, 0, 'Copiar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_DELETE', 'Eliminar Organizaci�n', 'ORGANIZACION', 1, 4, 0, 'Eliminar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_PRINT', 'Imprimir Reporte de Organizaci�n', 'ORGANIZACION', 1, 5, 0, 'Imprimir Reporte de Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_READONLY', 'Marcar como solo lectura', 'ORGANIZACION', 1, 6, 0, 'Marcar como solo lectura');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_VIEWALL', 'Ver Organizaci�n', 'ORGANIZACION', 1, 7, 1, 'Ver Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_MOVE', 'Mover Organizaci�n', 'ORGANIZACION', 1, 8, 1, 'Mover Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CODIGO_ENLACE', 'Generar C�digos de Enlace', 'ORGANIZACION', 1, 9, 1, 'Generar C�digos de Enlace');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR', 'Calcular', 'ORGANIZACION', 1, 10, 1, 'Calcular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR_INDICADOR', 'Indicadores', 'ORGANIZACION_CALCULAR', 2, 1, 1, 'Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_CALCULAR_INICIATIVA', 'Iniciativas', 'ORGANIZACION_CALCULAR', 2, 2, 1, 'Iniciativas');
--Iniciativa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA', 'Gestionar Iniciativas', 'ORGANIZACION', 1, 10, 0, 'Gestionar Iniciativas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ADD', 'Crear', 'INICIATIVA', 2, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EDIT', 'Modificar', 'INICIATIVA', 2, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_DELETE', 'Eliminar', 'INICIATIVA', 2, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_READONLY', 'Marcar Solo Lectura', 'INICIATIVA', 2, 4, 0, 'Marcar Solo Lectura');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_VIEWALL', 'Ver', 'INICIATIVA', 2, 5, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_VINCULAR', 'Vincular', 'INICIATIVA', 2, 6, 0, 'Vincular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_DESVINCULAR', 'Desvincular', 'INICIATIVA', 2, 7, 0, 'Desvincular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_GESTION', 'Gestionar Iniciativas / Proyectos', 'INICIATIVA', 2, 8, 0, 'Gestionar Iniciativas / Proyectos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_SEGUIMIENTO', 'Planificaci�n y Seguimiento', 'INICIATIVA', 2, 9, 0, 'Planificaci�n y Seguimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EMAIL', 'Enviar Correo', 'INICIATIVA', 2, 10, 0, 'Enviar Correo');
INSERT INTO AFW_PERMISO ( permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_RELACION', 'Iniciativas Relacionadas', 'INICIATIVA', 2, 11, 0, 'Iniciativas Relacionadas');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR',  'INICIATIVA',  'Evaluaci�n de Iniciativas',  2,   12,   0,  'Evaluaci�n de Iniciativas');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO',  'INICIATIVA_EVALUAR',  'Graficar',  3,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO_ESTATUS',  'INICIATIVA_EVALUAR_GRAFICO',  'Iniciativa por Estatus',  4,   1,   0,  'Iniciativa por Estatus');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_GRAFICO_PORCENTAJE',  'INICIATIVA_EVALUAR_GRAFICO',  'Iniciativa por Porcentaje',  4,   2,   0,  'Iniciativa por Porcentaje');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE',  'INICIATIVA_EVALUAR',  'Reportes',  3,   2,   0,  'Reportes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_RESUMIDO',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Resumido',  4,   1,   0,  'Reporte Resumido');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_HISTORICO',  'INICIATIVA',  'Marcar o Desmarcar Historico',  2,   13,   0,  'Marcar o Desmarcar Historico');
--Actividad
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD', 'Gestionar Actividades', 'INICIATIVA_SEGUIMIENTO', 3, 2, 0, 'Gestionar Actividades');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_ADD', 'Crear', 'ACTIVIDAD', 2, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_EDIT', 'Modificar', 'ACTIVIDAD', 2, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_DELETE', 'Eliminar', 'ACTIVIDAD', 2, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_VIEWALL', 'Ver', 'ACTIVIDAD', 2, 4, 0, 'Ver');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_IMPORTAR',  'ACTIVIDAD',  'Importar',  2,   5,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_CALCULAR',  'ACTIVIDAD',  'Calcular',  2,   6,   0,  'Calcular');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_ASOCIAR',  'ACTIVIDAD',  'Asociar',  2,   7,   0,  'Asociar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_DESASOCIAR',  'ACTIVIDAD',  'Desasociar',  2,   8,   0,  'Desasociar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PESO',  'ACTIVIDAD',  'Asignar pesos',  2,   9,   0,  'Asignar pesos');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION',  'ACTIVIDAD',  'Mediciones',  2,   10,   0,  'Mediciones');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION_PROGRAMADO',  'ACTIVIDAD_MEDICION',  'Programado',  3,   1,   0,  'Programado');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION_REAL',  'ACTIVIDAD_MEDICION',  'Real',  3,   2,   0,  'Real');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER',  'ACTIVIDAD',  'Proteger',  2,   11,   0,  'Proteger');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER_LIBERAR',  'ACTIVIDAD_PROTEGER',  'Liberar',  3,   1,   0,  'Liberar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER_BLOQUEAR',  'ACTIVIDAD_PROTEGER',  'Bloquear',  3,   2,   0,  'Bloquear');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_EMAIL',  'ACTIVIDAD',  'Enviar Correo',  2,   12,   0,  'Enviar Correo');
--Indicador
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_RAIZ', 'M�dulo de Indicadores', 'ORGANIZACION', 1, 11, 0, 'M�dulo de Indicadores');
-- Clase
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE', 'Gestionar Clases de Indicadores', 'INDICADOR_RAIZ', 2, 1, 0, 'Gestionar Clases de Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_ADD', 'Crear', 'CLASE', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_EDIT', 'Modificar', 'CLASE', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_DELETE', 'Eliminar', 'CLASE', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PRINT', 'Imprimir', 'CLASE', 3, 4, 0, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_VIEWALL', 'Ver', 'CLASE', 3, 5, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_COPY', 'Copiar', 'CLASE', 3, 6, 0, 'Copiar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_MOVE', 'Mover', 'CLASE', 3, 7, 0, 'Mover');
-- Indicador
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR', 'Gestionar Indicadores', 'INDICADOR_RAIZ', 2, 2, 0, 'Gestionar Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_ADD', 'Crear', 'INDICADOR', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_EDIT', 'Modificar', 'INDICADOR', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_DELETE', 'Eliminar', 'INDICADOR', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_PRINT', 'Imprimir', 'INDICADOR', 3, 4, 0, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_READONLY', 'Marcar Solo Lectura', 'INDICADOR', 3, 5, 0, 'Marcar Solo Lectura');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_VIEWALL', 'Ver', 'INDICADOR', 3, 6, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_CONSOLIDADO', 'Crear Indicadores Consolidados', 'INDICADOR', 3, 7, 0, 'Crear Indicadores Consolidados');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_ASOCIAR', 'Asociar', 'INDICADOR', 3, 8, 0, 'Asociar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_DESASOCIAR', 'Des-Asociar', 'INDICADOR', 3, 9, 0, 'Des-Asociar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION',  'INDICADOR',  'Mediciones',  3,   10,   0,  'Mediciones');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR',  'INDICADOR_MEDICION',  'Cargar',  4,   1,   0,  'Cargar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_REAL',  'INDICADOR_MEDICION_CARGAR',  'Real',  5,   1,   0,  'Real');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_PROG',  'INDICADOR_MEDICION_CARGAR',  'Programado',  5,   2,   0,  'Programado');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MAX',  'INDICADOR_MEDICION_CARGAR',  'M�ximo',  5,   3,   0,  'M�ximo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR_MIN',  'INDICADOR_MEDICION_CARGAR',  'M�nimo',  5,   4,   0,  'M�nimo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_IMPORTAR',  'INDICADOR_MEDICION',  'Importar',  4,   2,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CALCULAR',  'INDICADOR_MEDICION',  'Calcular',  4,   3,   0,  'Calcular');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_PROTECCION', 'INDICADOR_MEDICION', 'Proteger', 4,  4,  0,  'Proteger');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_DESPROTECCION', 'INDICADOR_MEDICION', 'No Proteger', 4,  5,  0,  'No Proteger');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_VIEW', 'INDICADOR_MEDICION', 'Ver', 4, 6, 0, 'Ver');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR',  'INDICADOR',  'Evaluaci�n de Indicadores',  3,   11,   0,  'Evaluaci�n de Indicadores');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO',  'INDICADOR_EVALUAR',  'Graficar',  4,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_GRAFICO',  'INDICADOR_EVALUAR_GRAFICO',  'Graficar',  5,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_PLANTILLA',  'INDICADOR_EVALUAR_GRAFICO',  'Plantilla',  5,   2,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_ASISTENTE',  'INDICADOR_EVALUAR_GRAFICO',  'Asistente',  5,   3,   0,  'Asistente');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE',  'INDICADOR_EVALUAR',  'Reportes',  4,   2,   0,  'Reportes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_PLANTILLA',  'INDICADOR_EVALUAR_REPORTE',  'Plantilla',  5,   1,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_ASISTENTE',  'INDICADOR_EVALUAR_REPORTE',  'Asistente',  5,   2,   0,  'Asistente');
--INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_COMITE',  'INDICADOR_EVALUAR_REPORTE',  'Reporte Comit� Ejecutivo',  5,   3,   0,  'Reporte Comit� Ejecutivo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_ARBOL',  'INDICADOR_EVALUAR',  'Arbol de Dupont',  4,   3,   0,  'Arbol de Dupont');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_INICIAL',  'INDICADOR',  'Valor Inicial',  3,   12,   0,  'Valor Inicial');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_INICIAL_CARGAR',  'INDICADOR_VALOR_INICIAL',  'Cargar',  4,   1,   0,  'Cargar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_INICIAL_VIEW',  'INDICADOR_VALOR_INICIAL',  'Ver',  4,   2,   0,  'Ver');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_META',  'INDICADOR',  'Metas',  3,   13,   0,  'Metas');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_META_CARGAR',  'INDICADOR_VALOR_META',  'Cargar',  4,   1,   0,  'Cargar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_VALOR_META_VIEW',  'INDICADOR_VALOR_META',  'Ver',  4,   2,   0,  'Ver');

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM afw_modulo WHERE id = '0DC25625-AF06-4F7C-B981-258AEAF18C01' AND activo = 1) 
	THEN
		INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION',  'INDICADOR',  'Transacciones',  3,   14,   0,  'Transacciones');
		INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION_IMPORTAR',  'INDICADOR_MEDICION_TRANSACCION',  'Importar',  4,   1,   0,  'Importar');
		INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_TRANSACCION_REPORTE',  'INDICADOR_MEDICION_TRANSACCION',  'Reporte',  4,   2,   0,  'Reporte');
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EMAIL',  'INDICADOR',  'Enviar Correo',  3,   15,   0,  'Enviar Correo');
--Plan
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN', 'Gestionar Planes', 'ORGANIZACION', 1, 12, 0, 'Gestionar Planes');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_ADD', 'Crear', 'PLAN', 2, 0, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_EDIT', 'Modificar', 'PLAN', 2, 1, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_DELETE', 'Eliminar', 'PLAN', 2, 2, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_VIEW', 'Ver', 'PLAN', 2, 3, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_COPY', 'Copiar', 'PLAN', 2, 4, 0, 'Copiar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PESO', 'Asignar Pesos', 'PLAN', 2, 5, 0, 'Asignar Pesos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO', 'Dise�o Modelo Causa Efecto', 'PLAN', 2, 6, 0, 'Dise�o Modelo Causa Efecto');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO_ADD', 'Crear', 'PLAN_MODELO_DISENO', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO_EDIT', 'Modificar', 'PLAN_MODELO_DISENO', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO_DELETE', 'Eliminar', 'PLAN_MODELO_DISENO', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO_VIEW', 'Ver', 'PLAN_MODELO_DISENO', 3, 4, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_DISENO_PRINT', 'Imprimir', 'PLAN_MODELO_DISENO', 3, 5, 0, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_MODELO_VER', 'Ver Modelo Causa Efecto', 'PLAN', 2, 7, 0, 'Ver Modelo Causa Efecto');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_REPORTE', 'Reportes', 'PLAN', 2, 8, 0, 'Reportes');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_EJECUTIVO', 'Dise�o Ejecutivo', 'PLAN', 2, 9, 0, 'Dise�o Ejecutivo');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA', 'Perspectiva / Objetivo', 'PLAN', 2, 10, 0, 'Perspectiva / Objetivo');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_ADD', 'Crear', 'PLAN_PERSPECTIVA', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_EDIT', 'Modificar', 'PLAN_PERSPECTIVA', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_DELETE', 'Eliminar', 'PLAN_PERSPECTIVA', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_COPY', 'Copiar', 'PLAN_PERSPECTIVA', 3, 4, 0, 'Copiar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_VIEW', 'Ver', 'PLAN_PERSPECTIVA', 3, 5, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_RELACION', 'Objetivos/Perspectivas Relacionadas', 'PLAN_PERSPECTIVA', 3, 6, 0, 'Objetivos/Perspectivas Relacionadas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_PERSPECTIVA_EMAIL', 'Enviar Correo', 'PLAN_PERSPECTIVA', 3, 7, 0, 'Enviar Correo');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_ACTIVO', 'Activar/Desactivar Plan', 'PLAN', 2, 11, 0, 'Activar/Desactivar Plan');
--Metodologia
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA', 'Gestionar Plantillas de Planes', 'PLAN', 2, 11, 0, 'Gestionar Plantillas de Planes');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_ADD', 'Crear', 'METODOLOGIA', 3, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_EDIT', 'Modificar', 'METODOLOGIA', 3, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_DELETE', 'Eliminar', 'METODOLOGIA', 3, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_PRINT', 'Imprimir', 'METODOLOGIA', 3, 4, 1, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_VIEW', 'ver', 'METODOLOGIA', 3, 5, 1, 'Ver');
-- Vista
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA', 'Panel de Control', 'ORGANIZACION', 1, 13, 0, 'Panel de Control');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_ADD', 'Crear', 'VISTA', 2, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_EDIT', 'Modificar', 'VISTA', 2, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DELETE', 'Eliminar', 'VISTA', 2, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_VIEW', 'Visualizar', 'VISTA', 2, 4, 1, 'Visualizar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_VIEWALL', 'Ver', 'VISTA', 2, 5, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PAGINA', 'Gestionar P�ginas', 'VISTA', 2, 6, 0, 'Gestionar P�ginas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PAGINA_ADD', 'Crear', 'PAGINA', 3, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PAGINA_EDIT', 'Modificar', 'PAGINA', 3, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PAGINA_DELETE', 'Eliminar', 'PAGINA', 3, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PAGINA_PRINT', 'Imprimir Reporte', 'PAGINA', 3, 4, 1, 'Imprimir Reporte');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CELDA', 'Gestionar Celdas', 'PAGINA', 3, 5, 0, 'Gestionar Celdas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CELDA_ADD', 'Crear', 'CELDA', 4, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CELDA_EDIT', 'Modificar', 'CELDA', 4, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CELDA_DELETE', 'Eliminar', 'CELDA', 4, 3, 1, 'Eliminar');
--Problemas
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_RAIZ', 'Acciones Correctivas', 'ORGANIZACION', 1, 14, 0, 'Acciones Correctivas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA', 'Gestionar Clases de Problemas', 'PROBLEMA_RAIZ', 2, 1, 0, 'Gestionar Clases de Problemas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_ADD', 'Crear', 'CLASE_PROBLEMA', 3, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_EDIT', 'Modificar', 'CLASE_PROBLEMA', 3, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_DELETE', 'Eliminar', 'CLASE_PROBLEMA', 3, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_VIEWALL', 'Ver', 'CLASE_PROBLEMA', 3, 4, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA', 'Gestionar Problemas', 'CLASE_PROBLEMA', 3, 5, 0, 'Gestionar Problemas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_ADD', 'Crear', 'PROBLEMA', 4, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_EDIT', 'Modificar', 'PROBLEMA', 4, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_DELETE', 'Eliminar', 'PROBLEMA', 4, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_VIEWALL', 'Ver', 'PROBLEMA', 4, 4, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION', 'Actividades de Acciones Correctivas', 'PROBLEMA', 4, 5, 0, 'Actividades de Acciones Correctivas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_ADD', 'Crear', 'ACCION', 5, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_EDIT', 'Modificar', 'ACCION', 5, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_DELETE', 'Eliminar', 'ACCION', 5, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_PRINT', 'Imprimir Reporte', 'ACCION', 5, 4, 0, 'Imprimir Reporte');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_VIEWALL', 'Ver', 'ACCION', 5, 5, 0, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO', 'Seguimiento', 'ACCION', 5, 6, 0, 'Seguimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_ADD', 'A�adir', 'SEGUIMIENTO', 6, 1, 0, 'A�adir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_EDIT', 'Modificar', 'SEGUIMIENTO', 6, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_MENSAJE', 'Mensaje', 'SEGUIMIENTO', 6, 3, 0, 'Mensaje');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_DELETE', 'Eliminar', 'SEGUIMIENTO', 6, 4, 0, 'Eliminar');
-- Vista Datos
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS', 'Gestionar Vista de Datos', 'ORGANIZACION', 1, 15, 0, 'Gestionar Vista de Datos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_ADD', 'Crear', 'VISTA_DATOS', 2, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_EDIT', 'Modificar', 'VISTA_DATOS', 2, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_DELETE', 'Eliminar', 'VISTA_DATOS', 2, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_VIEW', 'Ver', 'VISTA_DATOS', 2, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_REPORTE', 'Reporte', 'VISTA_DATOS', 2, 5, 1, 'Reporte');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_REPORTE_VIEW', 'Ver', 'VISTA_DATOS_REPORTE', 3, 1, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_REPORTE_PRINT', 'Imprimir', 'VISTA_DATOS_REPORTE', 3, 2, 1, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DATOS_REPORTE_EXPORT', 'Exportar', 'VISTA_DATOS_REPORTE', 3, 3, 1, 'Exportar');
/*            	Administracion            	  	*/
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CONFIGURACION', 'Administraci�n', NULL, 0, 11, 1, 'Administraci�n');
--Inicio de Sesi�n
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('CONFIGURACION_SET',  'CONFIGURACION',  'Inicio de Sesi�n',  1,   1,   1,   'Inicio de Sesi�n');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('CONFIGURACION_SET_SAVE', 'CONFIGURACION_SET', 'Salvar', 2, 1, 1, 'Salvar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('CONFIGURACION_SET_VIEW', 'CONFIGURACION_SET', 'Ver', 2, 2, 1, 'Ver');
--Usuarios
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO', 'CONFIGURACION', 'Usuarios', 1, 1, 1, 'Usuarios');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_ADD', 'USUARIO', 'Crear', 2, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_EDIT', 'USUARIO', 'Modificar', 2, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_DELETE', 'USUARIO', 'Eliminar', 2, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_COPY', 'USUARIO', 'Copiar', 2, 4, 1, 'Copiar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_PROPERTIES', 'USUARIO', 'Propiedades', 1, 5, 1, 'Propiedades');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_CLAVE', 'USUARIO', 'Cambiar Clave', 2, 6, 1, 'Cambiar Clave');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_SET_VIEW', 'USUARIO', 'Configurar Visor Vista', 2, 7, 1, 'Configurar Visor Vista');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_PRINT', 'USUARIO', 'Presentaci�n Preliminar', 2, 8, 1, 'Presentaci�n Preliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('USUARIO_PRINT_SET', 'USUARIO', 'Preparar P�gina', 2, 9, 1, 'Preparar P�gina');
--Grupos
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO', 'CONFIGURACION', 'Grupos', 1, 2, 1, 'Grupos');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_ADD', 'GRUPO', 'Crear', 2, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_EDIT', 'GRUPO', 'Modificar', 2, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_DELETE', 'GRUPO', 'Eliminar', 2, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_COPY', 'GRUPO', 'Copiar', 2, 4, 1, 'Copiar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_PROPERTIES', 'GRUPO', 'Propiedades', 2, 5, 1, 'Propiedades');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_PRINT', 'GRUPO', 'Presentaci�n Preliminar', 2, 6, 1, 'Presentaci�n Preliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('GRUPO_PRINT_SET', 'GRUPO', 'Preparar P�gina', 2, 7, 1, 'Preparar P�gina');
--Error
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('ERROR', 'CONFIGURACION', 'Error', 1, 3, 1, 'Error');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('ERROR_PRINT', 'ERROR', 'Presentaci�n Preliminar', 2, 1, 1, 'Presentaci�n Preliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('ERROR_PRINT_SET', 'ERROR', 'Preparar P�gina', 2, 2, 1, 'Preparar P�gina');
--Auditoria
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('AUDITORIA', 'CONFIGURACION', 'Auditoria', 1, 4, 1, 'Auditoria');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('AUDITORIA_PRINT', 'AUDITORIA', 'Presentaci�n Preliminar', 2, 1, 1, 'Presentaci�n Preliminar');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('AUDITORIA_PRINT_SET', 'AUDITORIA', 'Preparar P�gina', 2, 2, 1, 'Preparar P�gina');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('AUDITORIA_SET_VIEW', 'AUDITORIA', 'Configurar Visor Vista', 2, 3, 1, 'Configurar Visor Vista');
--Servicio
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('SERVICIO', 'CONFIGURACION', 'Servicio', 1, 5, 1, 'Servicio');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('SERVICIO_SET', 'SERVICIO', 'Configuraci�n de Servicio', 2, 1, 1, 'Configuraci�n de Servicio');
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('SERVICIO_LOG', 'SERVICIO', 'Ejecutar Reporte Log', 3, 2, 1, 'Ejecutar Reporte Log');
--Estatus de Iniciativa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS', 'Gestionar Estatus de Iniciativa', NULL, 0, 12, 1, 'Gestionar Estatus de Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_ADD', 'Crear', 'INICIATIVA_ESTATUS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_EDIT', 'Modificar', 'INICIATIVA_ESTATUS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_DELETE', 'Eliminar', 'INICIATIVA_ESTATUS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_VIEW', 'Ver', 'INICIATIVA_ESTATUS', 1, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ESTATUS_PRINT', 'Imprimir', 'INICIATIVA_ESTATUS', 1, 5, 1, 'Imprimir');
--Portafolio
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO', 'Portafolio', NULL, 0, 13, 1, 'Portafolio');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_ADD', 'Crear', 'PORTAFOLIO', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_EDIT', 'Modificar', 'PORTAFOLIO', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_DELETE', 'Eliminar', 'PORTAFOLIO', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_VIEW', 'Ver', 'PORTAFOLIO', 1, 4, 1, 'Ver');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_PRINT', 'Imprimir', 'PORTAFOLIO', 1, 5, 1, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_VISTA', 'Graficar', 'PORTAFOLIO', 1, 6, 1, 'Graficar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA', 'Iniciativa', 'PORTAFOLIO', 1, 7, 1, 'Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA_ADD', 'Agregar', 'PORTAFOLIO_INICIATIVA', 2, 1, 1, 'Agregar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_INICIATIVA_DELETE', 'Remover', 'PORTAFOLIO_INICIATIVA', 2, 2, 1, 'Remover');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_CALCULAR', 'Calcular', 'PORTAFOLIO', 1, 8, 1, 'Graficar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PORTAFOLIO_ASIGNARPESOS', 'Asignar Pesos', 'PORTAFOLIO', 1, 9, 1, 'Asignar Pesos');

CREATE OR REPLACE FUNCTION checkSql() RETURNS VOID AS
$BODY$
BEGIN
	IF EXISTS (SELECT * FROM afw_modulo WHERE id = 'B069CF20-1E8F-4E5F-BDE1-9C28C776511A' AND activo = 1) 
	THEN
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE', 'Gestionar Codigo Enlace', NULL, 0, 14, 1, 'Gestionar Codigo Enlace');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE_ADD', 'Crear', 'CODIGO_ENLACE', 1, 1, 1, 'Crear');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE_EDIT', 'Modificar', 'CODIGO_ENLACE', 1, 2, 1, 'Modificar');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE_DELETE', 'Eliminar', 'CODIGO_ENLACE', 1, 3, 1, 'Eliminar');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE_VIEW', 'Ver', 'CODIGO_ENLACE', 1, 4, 1, 'Ver');
		INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CODIGO_ENLACE_PRINT', 'Imprimir', 'CODIGO_ENLACE', 1, 5, 1, 'Imprimir');
	END IF;
END;
$BODY$
LANGUAGE 'plpgsql';
SELECT checkSql();
DROP FUNCTION checkSql();

/************************************************/
/*            	Causa            	  	*/
/************************************************/

INSERT INTO Causa (Causa_Id, Padre_Id, Nombre, Descripcion, Nivel) VALUES (1, NULL, 'Causa', 'Causa Padre', 1);

/************************************************/
/************************************************/

/************************************************/ 
/************************************************/ 
/************************************************/
/*            	Auditoria            	  	*/
/************************************************/
/************************************************/ 
/************************************************/ 
-- Grupo
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (71, 'com.visiongc.framework.model.Grupo', 'grupoId', 'grupo', 1);

-- Configuracion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (70, 'com.visiongc.framework.model.Configuracion', 'parametro', 'parametro', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (70, 'parametro', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (70, 'valor', 2); 

-- User Session (Colocar nombre de usuario)
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (69, 'com.visiongc.framework.model.UserSession', 'sessionId', 'UsuarioLogin', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'usuarioLogin', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteAddress', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteHost', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'remoteUser', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (69, 'url', 1); 

-- Usuario
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (68, 'com.visiongc.framework.model.Usuario', 'usuarioId', 'fullName', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'UId', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'fullName', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'isAdmin', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'estatus', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (68, 'bloqueado', 4); 

-- UnidadMedida
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (67, 'com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida', 'unidadId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (67, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (67, 'tipo', 4); 

-- SerieTiempo
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (66, 'com.visiongc.app.strategos.seriestiempo.model.SerieTiempo', 'serieId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'fija', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'oculta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'identificador', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (66, 'preseleccionada', 4); 

-- Responsable
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (65, 'com.visiongc.app.strategos.responsables.model.Responsable', 'responsableId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'usuarioId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'cargo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'email', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'ubicacion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'notas', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (65, 'esGrupo', 4); 

-- GrupoResponsable
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (64, 'com.visiongc.app.strategos.responsables.model.GrupoResponsable', 'pk', 'pk', 1);

-- Reporte
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (63, 'com.visiongc.app.strategos.reportes.model.Reporte', 'reporteId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (63, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (63, 'organizacionId', 4); 

-- Seguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (62, 'com.visiongc.app.strategos.problemas.model.Seguimiento', 'seguimientoId', 'fechaEmision', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaEmision', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'estadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'accionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaRecepcion', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'fechaAprobacion', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'preparadoPor', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'aprobadoPor', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (62, 'nota', 1); 

-- ResponsableAccion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (61, 'com.visiongc.app.strategos.problemas.model.ResponsableAccion', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (61, 'tipo', 4); 

-- Problema
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (60, 'com.visiongc.app.strategos.problemas.model.Problema', 'problemaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaEstimadaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaRealInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaEstimadaFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'fechaRealFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'indicadorEfectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'iniciativaEfectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'auxiliarId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (60, 'estadoId', 4); 
  
-- MemoSeguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (59, 'com.visiongc.app.strategos.problemas.model.MemoSeguimiento', ' pk', 'memo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (59, 'memo', 1); 

-- MemoProblema
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (58, 'com.visiongc.app.strategos.problemas.model.MemoProblema', 'pk', 'memo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (58, 'memo', 1); 
 
-- ConfiguracionMensajeEmailSeguimientos
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (57, 'com.visiongc.app.strategos.problemas.model.ConfiguracionMensajeEmailSeguimientos', 'mensajeId', 'mensaje', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'mensaje', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'acuseRecibo', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (57, 'soloAuxiliar', 4);
 
-- ClaseProblemas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (56, 'com.visiongc.app.strategos.problemas.model.ClaseProblemas', 'claseId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (56, 'padreId', 4); 

-- Accion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (55, 'com.visiongc.app.strategos.problemas.model.Accion', 'accionId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'estadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'problemaId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaEstimadaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaRealInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaEstimadaFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'fechaRealFinal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'frecuencia', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (55, 'padreId', 4); 

-- Portafolio
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (50, 'com.visiongc.app.strategos.portafolios.model.Portafolio', 'id', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'activo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (50, 'estatusId', 4); 

-- PryVista
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (49, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryVista', 'vistaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (49, 'nombre', 1); 

-- PryProyecto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (48, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto', 'proyectoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzo', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzoPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'comienzoReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'finPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'finReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'duracionPlan', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'duracionReal', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionComienzo', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionFin', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (48, 'variacionDuracion', 5); 
  
-- PryInformacionActividad
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (47, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad', 'actividadId', 'productoEsperado', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'productoEsperado', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'recursosHumanos', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'recursosMateriales', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'peso', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'porcentajeAmarillo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (47, 'porcentajeVerde', 4); 
 
-- PryColumnaVista
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (46, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryColumnaVista', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'alineacion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'ancho', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (46, 'orden', 4); 

-- PryColumna
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (45, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryColumna', 'columnaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'numerico', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'alineacion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'formato', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (45, 'tamano', 4); 

-- PryCalendarioDetalle
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (44, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendarioDetalle', 'calendarioId', 'fecha', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (44, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (44, 'feriado', 4); 

-- PryCalendario
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (43, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryCalendario', 'calendarioId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'proyectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'domingo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'lunes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'martes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'miercoles', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'jueves', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'viernes', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (43, 'sabado', 4); 
 
-- PryActividad
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (42, 'com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad', 'actividadId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'proyectoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'unidadId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'comienzoPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'comienzoReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'finPlan', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'finReal', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'duracionPlan', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'duracionReal', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'fila', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'nivel', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'compuesta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'indicadorId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'naturaleza', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableFijarMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableLograrMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableSeguimientoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableCargarMetaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'responsableCargarEjecutadoId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'tipoMedicion', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'tieneHijos', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (42, 'peso', 5); 

-- PrdSeguimientoProducto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (41, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'fechaInicio', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'fechaFin', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (41, 'alerta', 4); 

-- PrdSeguimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (40, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'fecha', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (40, 'seguimiento', 1); 

-- PrdProducto
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (39, 'com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto', 'productoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'iniciativaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'fechaInicio', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'fechaFin', 3); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (39, 'alerta', 4); 

-- PryActividadPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (38, 'com.visiongc.app.strategos.planes.model.PryActividadPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeEjecutado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (38, 'porcentajeEsperado', 5); 

-- PlantillaPlanes
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (37, 'com.visiongc.app.strategos.planes.model.PlantillaPlanes', 'plantillaPlanesId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreIndicadorSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreIniciativaSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'nombreActividadSingular', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (37, 'tipo', 4); 

-- PlanEstado
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (36, 'com.visiongc.app.strategos.planes.model.PlanEstado', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'estado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'totalConValor', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (36, 'total', 4); 
  
-- Plan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (35, 'com.visiongc.app.strategos.planes.model.Plan', 'planId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'organizacionId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'planImpactaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'anoInicial', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'anoFinal', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'activo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'metodologiaId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (35, 'esquema', 1); 
  
-- PerspectivaEstado
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (34, 'com.visiongc.app.strategos.planes.model.PerspectivaEstado', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'estado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'totalConValor', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (34, 'total', 4); 

-- Perspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (33, 'com.visiongc.app.strategos.planes.model.Perspectiva', 'perspectivaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'planId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'responsableId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nombreObjetoPerspectiva', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'frecuencia', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'titulo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'tipo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'claseId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (33, 'nivel', 4); 

--IniciativaPerspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (29, 'com.visiongc.app.strategos.planes.model.IniciativaPerspectiva', 'pk', 'pk', 1);

--IniciativaPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (28, 'com.visiongc.app.strategos.planes.model.IniciativaPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'alerta', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'porcentajeCompletado', 5); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'ano', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'periodo', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (28, 'claseId', 4); 

--IndicadorPlan
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (27, 'com.visiongc.app.strategos.planes.model.IndicadorPlan', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (27, 'peso', 5); 

--IndicadorPerspectiva
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (26, 'com.visiongc.app.strategos.planes.model.IndicadorPerspectiva', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (26, 'peso', 5); 

--IndicadorCrecimiento
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (25, 'com.visiongc.app.strategos.planes.model.IndicadorCrecimiento', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (25, 'crecimiento', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (25, 'tipoMedicion', 4); 

--ElementoPlantillaPlanes
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (24, 'com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes', 'elementoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'plantillaPlanesId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'orden', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (24, 'tipo', 4); 

--GrupoMascaraCodigoPlanCuentas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (23, 'com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas', 'pk', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'nombre', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'mascara', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (23, 'madre', 1); --****

--MascaraCodigoPlanCuentas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (22, 'com.visiongc.app.strategos.plancuentas.model.MascaraCodigoPlanCuentas', 'mascaraId', 'mascara', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (22, 'mascara', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (22, 'niveles', 4); 

--Cuenta
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (21, 'com.visiongc.app.strategos.plancuentas.model.Cuenta', 'cuentaId', 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'codigo', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'descripcion', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'padreId', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (21, 'hijosCargados', 4); 

--OrganizacionStrategos
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (19, 'com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos', 'organizacionId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'nombre', 1, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.framework.model.OrganizacionBasica</claseRelacion>
    <claseImpl></claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>nombre</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>'); 

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'padre', 1,
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.framework.model.OrganizacionBasica</claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>nombre</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>'); 

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) VALUES 
(19, 'direccion', 1,
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.framework.model.OrganizacionBasica</claseRelacion>
    <claseImpl></claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>direccion</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>'); 

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'rif', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'telefono', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'fax', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'mesCierre', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'enlaceParcial', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'visible', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'soloLectura', 4); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaMinMaxIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaVerdeMetaIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaMetaIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaVerdeIniciativas', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaIniciativas', 4);

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,descripcion', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>0</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,observacion', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>1</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,personaldirectivo', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>2</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,mision', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>3</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,vision', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>4</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,oportunidad', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>5</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,estrategia', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>6</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,factores', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>7</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,politicas', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>8</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion) 
VALUES (19, 'memos,valores', 2, 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion</claseImpl>
    <claseSet>com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK</claseSet>
    <nombreRelacion>tipo</nombreRelacion>
	<valorRelacion>9</valorRelacion>
    <nombreImpl>descripcion</nombreImpl>
    <nombreSet>pk</nombreSet>
  </properties>
</configuracion>');

--ResultadoEspecificoIniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (18, 'com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa', 'pk', 'resultado', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (18, 'resultado', 1); 
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (18, 'iniciativa', 1); --****

--MemoIniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (17, 'com.visiongc.app.strategos.iniciativas.model.MemoIniciativa', 'iniciativaId', 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (17, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (17, 'resultado', 1);

--Iniciativa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (16, 'com.visiongc.app.strategos.iniciativas.model.Iniciativa', 'iniciativaId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'proyectoId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'tipoAlerta', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'frecuencia', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'nombreLargo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'enteEjecutor', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'visible', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'indicadorId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'claseId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (16, 'porcentajeCompletado', 5);

--SerieIndicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (15, 'com.visiongc.app.strategos.indicadores.model.SerieIndicador', 'pk', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'naturaleza', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'fechaBloqueo', 3);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (15, 'indicador', 1); --****

--Medicion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (14, 'com.visiongc.app.strategos.indicadores.model.Medicion', 'medicionId', 'valor', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'valor', 5);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'valorString', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'protegido', 4);
--INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (14, 'serieIndicador', 4);

--Indicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (13, 'com.visiongc.app.strategos.indicadores.model.Indicador', 'indicadorId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'claseId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'nombreCorto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'naturaleza', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'frecuencia', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'comportamiento', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'unidadId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'codigoEnlace', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'prioridad', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'fuente', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'orden', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'soloLectura', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'estaBloqueado', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (13, 'peso', 5);

--InsumoFormula
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (12, 'com.visiongc.app.strategos.indicadores.model.InsumoFormula', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (12, 'macro', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (12, 'valor', 1);

--Formula
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (11, 'com.visiongc.app.strategos.indicadores.model.Formula', 'pk', 'expresion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (11, 'expresion', 1);

--ClaseIndicadores
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (10, 'com.visiongc.app.strategos.indicadores.model.ClaseIndicadores', 'claseId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'enlaceParcial', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'tipo', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'visible', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (10, 'hijosCargados', 4);

--CategoriaIndicador
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (9, 'com.visiongc.app.strategos.indicadores.model.CategoriaIndicador', 'pk', 'pk', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (9, 'orden', 4);

--Grafico
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (8, 'com.visiongc.app.strategos.graficos.model.Grafico', 'graficoId', 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'objetoId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (8, 'className', 1);

--Foro
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (7, 'com.visiongc.app.strategos.foros.model.Foro', 'foroId', 'asunto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'asunto', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'email', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'comentario', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (7, 'numeroRespuestas', 4);

--AdjuntoExplicacion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (6, 'com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion', 'pk', 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (6, 'titulo', 1);

--Explicacion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (5, 'com.visiongc.app.strategos.explicaciones.model.Explicacion', 'explicacionId', 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'titulo', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'fecha', 3);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (5, 'numeroAdjuntos', 4);

--EstadoAcciones
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (4, 'com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones', 'estadoId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'aplicaSeguimiento', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'orden', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (4, 'condicion', 4);

-- Causa
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (3, 'com.visiongc.app.strategos.causas.model.Causa', 'causaId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'padreId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'nivel', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (3, 'hijosCargados', 4);

-- CategoriaMedicion
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (2, 'com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion', 'categoriaId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (2, 'nombre', 1);

-- ClaseProblemas
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (1, 'com.visiongc.app.strategos.problemas.model.ClaseProblemas', 'claseId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'descripcion', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'organizacionId', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (1, 'padreId', 4);

-- Servicio
INSERT INTO afw_objeto_auditable (objeto_id, nombre_clase, nombre_campo_id, nombre_campo_nombre, auditoria_activa) VALUES (72, 'com.visiongc.servicio.strategos.servicio.model.Servicio', 'usuarioId', 'Nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (72, 'nombre', 1);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (72, 'fecha', 1);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (6, 'ruta', 1, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'tipo', 4, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'fechaCompromiso', 3, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'fechaReal', 3, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'creadoId', 4, null);

insert into afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo, configuracion)
VALUES (5, 'objetoId', 4, null);



insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_OP', 'PLAN', 'Ver Operativo', 2, 6, 0, 'Ver Opeartivo');

insert INTO afw_permiso (permiso_id, padre_id, permiso, nivel, grupo, global, descripcion)
values ('PLAN_VIEW_ES', 'PLAN', 'Ver Estrategico', 2, 7, 0, 'Ver Estrategico');

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) values ('INSTRUMENTOS', 'Gestionar Instrumentos', NULL, 0, 17, 1, 'Instrumentos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_ADD', 'Crear', 'INSTRUMENTOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_EDIT', 'Modificar', 'INSTRUMENTOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_DELETE', 'Eliminar', 'INSTRUMENTOS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_VIEW_TIPO', 'Ver Tipos', 'INSTRUMENTOS', 1, 4, 1, 'Ver Tipos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_VIEW_COOP', 'Ver Cooperante', 'INSTRUMENTOS', 1, 5, 1, 'Ver Cooperante');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA', 'Iniciativa', 'INSTRUMENTOS', 1, 6, 1, 'Iniciativa');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA_ADD', 'Agregar', 'INSTRUMENTOS_INICIATIVA', 2, 1, 1, 'Agregar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_INICIATIVA_DELETE', 'Remover', 'INSTRUMENTOS_INICIATIVA', 2, 2, 1, 'Remover');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_ASOCIAR', 'Asociar', 'INSTRUMENTOS', 1, 8, 1, 'Asociar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INSTRUMENTOS_DESASOCIAR', 'Desasociar', 'INSTRUMENTOS', 1, 9, 1, 'Desasociar');

UPDATE afw_permiso set permiso = 'Desasociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_DELETE';
UPDATE afw_permiso set permiso = 'Asociar' where permiso_id = 'INSTRUMENTOS_INICIATIVA_ADD';

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS', 'Gestionar Cargos', NULL, 0, 3, 1, 'Gestionar Categor�as de Medici�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_ADD', 'Crear', 'CARGOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_EDIT', 'Modificar', 'CARGOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CARGOS_DELETE', 'Eliminar', 'CARGOS', 1, 3, 1, 'Eliminar');

INSERT INTO fases_proyecto (fase_id, nombre) VALUES (1, 'Planificación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (2, 'Ingeniería');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (3, 'Contratación');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (4, 'Procura');
INSERT INTO fases_proyecto (fase_id, nombre) VALUES (5, 'Puesta en Marcha');

INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DETALLADO',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Detallado',  4,   1,   0,  'Reporte Detallado');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_RESUMIDO_VIGENTES',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Resumido Vigentes',  4,   1,   0,  'Reporte Resumido Vigentes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DATOS_BASICOS',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Datos Basicos',  4,   1,   0,  'Reporte Datos Basicos');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_MEDICIONES_ATRASADAS',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Mediciones Atrasadas',  4,   1,   0,  'Reporte Mediciones Atrasadas');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_DETALLADO_PLANES_ACCION',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Detallado Detallado Planes de Accion',  4,   1,   0,  'Reporte Detallado Planes de Accion');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INICIATIVA_EVALUAR_REPORTE_INDICADORES',  'INICIATIVA_EVALUAR_REPORTE',  'Reporte Indicadores',  4,   1,   0,  'Reporte Indicadores');


INSERT INTO afw_lic (id, corporacion, serial, licenciamiento) VALUES (1, 'Nombre Corporacion', 'Serial Corporacion', 'Tipo Licenciamiento');

INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS', 'Gestionar Tipos de Proyectos', NULL, 0, 18, 1, 'Gestionar Tipos de Proyectos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_ADD', 'Crear', 'TIPOS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_EDIT', 'Modificar', 'TIPOS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_DELETE', 'Eliminar', 'TIPOS', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('TIPOS_PRINT', 'Imprimir Reporte de Tipos de Proyectos', 'TIPOS', 1, 4, 1, 'Imprimir Reporte de Tipos de Proyectos');


INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTES_ORGANIZACION', 'Reportes Organizacion', 'ORGANIZACION', 1, 16, 0, 'Reportes Organizacion');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTE_CUMPLIMIENTO', 'Reporte Cumplimiento', 'REPORTES_ORGANIZACION', 2, 1, 0, 'Reporte Cumplimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('REPORTE_DEPENDENCIAS_OMISIVAS', 'Reporte Dependencias Omisivas', 'REPORTES_ORGANIZACION', 1, 2, 0, 'Reporte Dependencias Omisivas');

UPDATE afw_sistema set actual = '10.01-240725';  
UPDATE afw_sistema set build = 240725;
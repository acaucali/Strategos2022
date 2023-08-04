--Configuracion
DELETE FROM afw_configuracion WHERE PARAMETRO = 'Strategos.Estilos.Configuracion';
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

DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 13 and nombre_atributo = 'descripcion';

COMMIT;

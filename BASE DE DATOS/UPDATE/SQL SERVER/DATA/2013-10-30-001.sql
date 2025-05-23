DELETE FROM AFW_PERMISO;
/************************************************/
/*                  Seguridad Persmisos         */
/************************************************/
/*            	Administracion            	  	*/
INSERT INTO AFW_PERMISO (PERMISO_ID, PADRE_ID, PERMISO, NIVEL, GRUPO, GLOBAL, DESCRIPCION) VALUES ('CONFIGURACION',  NULL,  'Administraci�n',  0,  0,  1,   'Administraci�n');
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
/*            	Aplicacion              	  	*/
--Unidad
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD', 'Gestionar Unidades de Medida', NULL, 0, 1, 1, 'Gestionar Unidades de Medida');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_ADD', 'Crear', 'UNIDAD', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_EDIT', 'Modificar', 'UNIDAD', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_DELETE', 'Eliminar', 'UNIDAD', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('UNIDAD_PRINT', 'Imprimir Reporte de Unidades', 'UNIDAD', 1, 4, 1, 'Imprimir Reporte de Unidades');
--Causa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA', 'Gestionar Causas', NULL, 0, 2, 1, 'Gestionar Causas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_ADD', 'Crear', 'CAUSA', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_EDIT', 'Modificar', 'CAUSA', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_DELETE', 'Eliminar', 'CAUSA', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CAUSA_PRINT', 'Imprimir Reporte de Causas', 'CAUSA', 1, 4, 1, 'Imprimir Reporte de Causas');
--Foro
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('FORO', 'Gestionar Foros', NULL, 0, 3, 1, 'Gestionar Foros');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('FORO_ADD', 'Crear', 'FORO', 1, 1, 1, 'Crear');
--Categoria
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA', 'Gestionar Categor�as de Medici�n', NULL, 0, 4, 1, 'Gestionar Categor�as de Medici�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_ADD', 'Crear', 'CATEGORIA', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_EDIT', 'Modificar', 'CATEGORIA', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_DELETE', 'Eliminar', 'CATEGORIA', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CATEGORIA_PRINT', 'Imprimir Reporte de Categor�as', 'CATEGORIA', 1, 4, 1, 'Imprimir Reporte de Categor�as');
--Estatus
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS', 'Gestionar Estados de Acciones', NULL, 0, 5, 1, 'Gestionar Estados de Acciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_ADD', 'Crear', 'ESTATUS', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_EDIT', 'Modificar', 'ESTATUS', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ESTATUS_DELETE', 'Eliminar', 'ESTATUS', 1, 3, 1, 'Eliminar');
--Responsable
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE', 'Gestionar Responsables', NULL, 0, 6, 1, 'Gestionar Responsables');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_ADD', 'Crear', 'RESPONSABLE', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_EDIT', 'Modificar', 'RESPONSABLE', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_DELETE', 'Eliminar', 'RESPONSABLE', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('RESPONSABLE_PRINT', 'Reporte', 'RESPONSABLE', 1, 4, 1, 'Reporte');
--Imputacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION', 'Gestionar Plan de Cuentas', NULL, 0, 7, 1, 'Gestionar Plan de Cuentas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_ADD', 'Crear', 'IMPUTACION', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_EDIT', 'Modificar', 'IMPUTACION', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_DELETE', 'Eliminar', 'IMPUTACION', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('IMPUTACION_MASK', 'Definir Mascara de C�digo de Plan de Cuentas', 'IMPUTACION', 1, 4, 1, 'Definir Mascara de C�digo de Plan de Cuentas');
--Serie de Tiempo
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO', 'Gestionar Series de Tiempo', NULL, 0, 8, 1, 'Gestionar Series de Tiempo');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_ADD', 'Crear', 'SERIE_TIEMPO', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_EDIT', 'Modificar', 'SERIE_TIEMPO', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_DELETE', 'Eliminar', 'SERIE_TIEMPO', 1, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SERIE_TIEMPO_PRINT', 'Imprimir Reporte de Series de Tiempo', 'SERIE_TIEMPO', 1, 4, 1, 'Imprimir Reporte de Series de Tiempo');
--Explicacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION', 'Gestionar Explicaciones', NULL, 0, 9, 1, 'Gestionar Explicaciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_ADD', 'Crear', 'EXPLICACION', 1, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_EDIT', 'Modificar', 'EXPLICACION', 1, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('EXPLICACION_DELETE', 'Eliminar', 'EXPLICACION', 1, 3, 1, 'Eliminar');
--Organizacion
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION', 'Gestionar Organizaciones', NULL, 0, 10, 1, 'Gestionar Organizaciones');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_ADD', 'Crear Organizaci�n', 'ORGANIZACION', 1, 1, 0, 'Crear Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_EDIT', 'Modificar Organizaci�n', 'ORGANIZACION', 1, 2, 0, 'Modificar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_COPY', 'Copiar Organizaci�n', 'ORGANIZACION', 1, 3, 0, 'Copiar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_DELETE', 'Eliminar Organizaci�n', 'ORGANIZACION', 1, 4, 0, 'Eliminar Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_PRINT', 'Imprimir Reporte de Organizaci�n', 'ORGANIZACION', 1, 5, 0, 'Imprimir Reporte de Organizaci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_READONLY', 'Marcar como solo lectura', 'ORGANIZACION', 1, 6, 0, 'Marcar como solo lectura');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ORGANIZACION_VIEWALL', 'Ver Todas', 'ORGANIZACION', 1, 7, 1, 'Ver Todas');
--Iniciativa
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA', 'Gestionar Iniciativas', 'ORGANIZACION', 1, 8, 0, 'Gestionar Iniciativas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_ADD', 'Crear', 'INICIATIVA', 2, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_EDIT', 'Modificar', 'INICIATIVA', 2, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_DELETE', 'Eliminar', 'INICIATIVA', 2, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_READONLY', 'Marcar Solo Lectura', 'INICIATIVA', 2, 4, 0, 'Marcar Solo Lectura');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_VIEWALL', 'Ver Todas', 'INICIATIVA', 2, 5, 0, 'Ver Todas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_SEGUIMIENTO', 'Seguimiento', 'INICIATIVA', 2, 6, 0, 'Seguimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_VINCULAR', 'Vincular', 'INICIATIVA', 2, 7, 0, 'Vincular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_DESVINCULAR', 'Desvincular', 'INICIATIVA', 2, 8, 0, 'Desvincular');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INICIATIVA_GESTION', 'Gesti�n', 'INICIATIVA', 2, 9, 0, 'Gesti�n');
--Producto
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PRODUCTO', 'Productos', 'INICIATIVA_SEGUIMIENTO', 3, 1, 0, 'Productos');
--Actividad
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD', 'Gestionar Actividades', 'INICIATIVA_SEGUIMIENTO', 3, 2, 0, 'Gestionar Actividades');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_ADD', 'Crear', 'ACTIVIDAD', 2, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_EDIT', 'Modificar', 'ACTIVIDAD', 2, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_DELETE', 'Eliminar', 'ACTIVIDAD', 2, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_VIEWALL', 'Ver Todas', 'ACTIVIDAD', 2, 4, 0, 'Ver Todas');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION',  'ACTIVIDAD',  'Mediciones',  2,   5,   0,  'Mediciones');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION_PROGRAMADO',  'ACTIVIDAD_MEDICION',  'Programado',  2,   6,   0,  'Programado');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_MEDICION_REAL',  'ACTIVIDAD_MEDICION',  'Real',  2,   7,   0,  'Real');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_IMPORTAR',  'ACTIVIDAD',  'Importar',  2,   8,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_CALCULAR',  'ACTIVIDAD',  'Calcular',  2,   9,   0,  'Calcular');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER',  'ACTIVIDAD',  'Proteger',  2,   10,   0,  'Proteger');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER_LIBERAR',  'ACTIVIDAD_PROTEGER',  'Liberar',  3,   1,   0,  'Liberar');
INSERT INTO afw_permiso( permiso_id, padre_id, permiso, nivel, grupo, global, descripcion) VALUES ('ACTIVIDAD_PROTEGER_BLOQUEAR',  'ACTIVIDAD_PROTEGER',  'Bloquear',  3,   2,   0,  'Bloquear');
--Indicador
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_RAIZ', 'M�dulo de Indicadores', 'ORGANIZACION', 1, 9, 0, 'M�dulo de Indicadores');
-- Clase
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE', 'Gestionar Clases de Indicadores', 'INDICADOR_RAIZ', 2, 1, 0, 'Gestionar Clases de Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_ADD', 'Crear', 'CLASE', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_EDIT', 'Modificar', 'CLASE', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_DELETE', 'Eliminar', 'CLASE', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PRINT', 'Imprimir', 'CLASE', 3, 4, 0, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_VIEWALL', 'Ver Todas', 'CLASE', 3, 5, 0, 'Ver Todas');
-- Indicador
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR', 'Gestionar Indicadores', 'INDICADOR_RAIZ', 2, 2, 0, 'Gestionar Indicadores');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_ADD', 'Crear', 'INDICADOR', 3, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_EDIT', 'Modificar', 'INDICADOR', 3, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_DELETE', 'Eliminar', 'INDICADOR', 3, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_PRINT', 'Imprimir', 'INDICADOR', 3, 4, 0, 'Imprimir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_READONLY', 'Marcar Solo Lectura', 'INDICADOR', 3, 5, 0, 'Marcar Solo Lectura');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION',  'INDICADOR',  'Mediciones',  3,   6,   0,  'Mediciones');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CARGAR',  'INDICADOR_MEDICION',  'Cargar',  4,   1,   0,  'Cargar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_IMPORTAR',  'INDICADOR_MEDICION',  'Importar',  4,   2,   0,  'Importar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_CALCULAR',  'INDICADOR_MEDICION',  'Calcular',  4,   3,   0,  'Calcular');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_MEDICION_PROTECCION',  'INDICADOR_MEDICION',  'Protecci�n',  4,   4,   0,  'Protecci�n');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('INDICADOR_VIEWALL', 'Ver Todos', 'INDICADOR', 3, 7, 0, 'Ver Todos');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR',  'INDICADOR',  'Evaluaci�n de Indicadores',  3,   8,   0,  'Evaluaci�n de Indicadores');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO',  'INDICADOR_EVALUAR',  'Graficar',  4,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_GRAFICO',  'INDICADOR_EVALUAR_GRAFICO',  'Graficar',  5,   1,   0,  'Graficar');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_PLANTILLA',  'INDICADOR_EVALUAR_GRAFICO',  'Plantilla',  5,   2,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_GRAFICO_ASISTENTE',  'INDICADOR_EVALUAR_GRAFICO',  'Asistente',  5,   3,   0,  'Asistente');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE',  'INDICADOR_EVALUAR',  'Reportes',  4,   2,   0,  'Reportes');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_PLANTILLA',  'INDICADOR_EVALUAR_REPORTE',  'Plantilla',  5,   1,   0,  'Plantilla');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_ASISTENTE',  'INDICADOR_EVALUAR_REPORTE',  'Asistente',  5,   2,   0,  'Asistente');
--INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_REPORTE_COMITE',  'INDICADOR_EVALUAR_REPORTE',  'Reporte Comit� Ejecutivo',  5,   3,   0,  'Reporte Comit� Ejecutivo');
INSERT INTO afw_permiso( permiso_id,  padre_id,  permiso,  nivel,  grupo,  global,  descripcion) VALUES ('INDICADOR_EVALUAR_ARBOL',  'INDICADOR_EVALUAR',  'Arbol de Dupont',  4,   3,   0,  'Arbol de Dupont');
--Plan
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN', 'Gestionar Planes', 'ORGANIZACION', 1, 10, 0, 'Gestionar Planes');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PLAN_EDIT', 'Modificar', 'PLAN', 2, 0, 0, 'Modificar');
--Metodologia
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA', 'Gestionar Plantillas de Planes', 'PLAN', 2, 1, 0, 'Gestionar Plantillas de Planes');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_ADD', 'Crear', 'METODOLOGIA', 3, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_EDIT', 'Modificar', 'METODOLOGIA', 3, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_DELETE', 'Eliminar', 'METODOLOGIA', 3, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('METODOLOGIA_PRINT', 'Imprimir', 'METODOLOGIA', 3, 4, 1, 'Imprimir');
-- Vista
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA', 'Gestionar Vistas', 'ORGANIZACION', 1, 11, 0, 'Gestionar Vistas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_ADD', 'Crear', 'VISTA', 2, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_EDIT', 'Modificar', 'VISTA', 2, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_DELETE', 'Eliminar', 'VISTA', 2, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_VIEW', 'Visualizar', 'VISTA', 2, 4, 1, 'Visualizar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('VISTA_VIEWALL', 'Ver Todas', 'VISTA', 2, 5, 0, 'Ver Todas');
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
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_RAIZ', 'M�dulo de Problemas', 'ORGANIZACION', 1, 12, 0, 'M�dulo de Problemas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA', 'Gestionar Clases de Problemas', 'PROBLEMA_RAIZ', 2, 1, 0, 'Gestionar Clases de Problemas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_ADD', 'Crear', 'CLASE_PROBLEMA', 3, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_EDIT', 'Modificar', 'CLASE_PROBLEMA', 3, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_DELETE', 'Eliminar', 'CLASE_PROBLEMA', 3, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('CLASE_PROBLEMA_VIEWALL', 'Ver Todas', 'CLASE_PROBLEMA', 3, 4, 0, 'Ver Todas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA', 'Gestionar Problemas', 'CLASE_PROBLEMA', 3, 5, 0, 'Gestionar Problemas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_ADD', 'Crear', 'PROBLEMA', 4, 1, 1, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_EDIT', 'Modificar', 'PROBLEMA', 4, 2, 1, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_DELETE', 'Eliminar', 'PROBLEMA', 4, 3, 1, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('PROBLEMA_VIEWALL', 'Ver Todos', 'PROBLEMA', 4, 4, 0, 'Ver Todos');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION', 'Gestionar Acciones Correctivas', 'PROBLEMA', 4, 5, 0, 'Gestionar Acciones Correctivas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_ADD', 'Crear', 'ACCION', 5, 1, 0, 'Crear');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_EDIT', 'Modificar', 'ACCION', 5, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_DELETE', 'Eliminar', 'ACCION', 5, 3, 0, 'Eliminar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_PRINT', 'Imprimir Reporte', 'ACCION', 5, 4, 0, 'Imprimir Reporte');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('ACCION_VIEWALL', 'Ver Todas', 'ACCION', 5, 5, 0, 'Ver Todas');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO', 'Seguimiento', 'ACCION', 5, 6, 0, 'Seguimiento');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_ADD', 'A�adir', 'SEGUIMIENTO', 6, 1, 0, 'A�adir');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_EDIT', 'Modificar', 'SEGUIMIENTO', 6, 2, 0, 'Modificar');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_MENSAJE', 'Mensaje', 'SEGUIMIENTO', 6, 3, 0, 'Mensaje');
INSERT INTO AFW_PERMISO (permiso_id, permiso, padre_id, nivel, grupo, global, descripcion) VALUES ('SEGUIMIENTO_DELETE', 'Eliminar', 'SEGUIMIENTO', 6, 4, 0, 'Eliminar');

DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 8 AND nombre_atributo = 'configuracion';
DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 63 AND nombre_atributo = 'configuracion';
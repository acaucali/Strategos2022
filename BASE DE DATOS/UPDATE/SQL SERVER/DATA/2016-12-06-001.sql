UPDATE AFW_PERMISO SET permiso = 'Panel de Control', descripcion = 'Panel de Control' WHERE permiso_id = 'VISTA';
DELETE FROM AFW_PERMISO WHERE permiso_id = 'PRODUCTO';

UPDATE afw_sistema set actual = '8.01-161206';

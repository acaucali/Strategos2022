--OrganizacionStrategos
DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 19 AND nombre_atributo = 'rutaCompleta'; 

--MemoOrganizacion
DELETE FROM afw_objeto_auditable_atributo WHERE objeto_id = 20; 
DELETE FROM afw_objeto_auditable WHERE objeto_id = 20; 

INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaMinMaxIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaVerdeMetaIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaMetaIndicadores', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaVerdeIniciativas', 4);
INSERT INTO afw_objeto_auditable_atributo (objeto_id, nombre_atributo, tipo) VALUES (19, 'porcentajeZonaAmarillaIniciativas', 4);

UPDATE afw_objeto_auditable_atributo SET configuracion = 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos</claseRelacion>
    <claseImpl>com.visiongc.framework.model.OrganizacionBasica</claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>nombre</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>',
		tipo = 1,
		nombre_atributo = 'padre'
WHERE objeto_id = 19 AND nombre_atributo = 'padreId';

UPDATE afw_objeto_auditable_atributo SET configuracion = 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.framework.model.OrganizacionBasica</claseRelacion>
    <claseImpl></claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>direccion</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>', 
		tipo = 1
WHERE objeto_id = 19 AND nombre_atributo = 'direccion';

UPDATE afw_objeto_auditable_atributo SET configuracion = 
'<configuracion>
  <properties>
    <claseRelacion>com.visiongc.framework.model.OrganizacionBasica</claseRelacion>
    <claseImpl></claseImpl>
    <claseSet></claseSet>
    <nombreRelacion>nombre</nombreRelacion>
    <nombreImpl></nombreImpl>
    <nombreSet></nombreSet>
  </properties>
</configuracion>',
		tipo = 1
WHERE objeto_id = 19 AND nombre_atributo = 'nombre';

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

COMMIT;

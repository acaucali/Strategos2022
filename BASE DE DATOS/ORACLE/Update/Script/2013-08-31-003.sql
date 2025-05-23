DROP INDEX xif2accion;
CREATE  INDEX IE_accion_estado ON accion
(estado_id   ASC);

DROP INDEX xif1accion;
CREATE  INDEX IE_accion_problema ON accion
(problema_id   ASC);

CREATE  INDEX IE_accion_creado ON accion
(creado_id   ASC);

CREATE  INDEX IE_accion_modificado ON accion
(modificado_id   ASC);

DROP INDEX xif2clase_problema;
CREATE  INDEX IE_padre_claseproblema ON clase_problema
(padre_id   ASC);

DROP INDEX xif1clase_problema;
CREATE  INDEX IE_organizacion_claseproblema ON clase_problema
(organizacion_id   ASC);

CREATE  INDEX IE_creado_claseproblema ON clase_problema
(creado_id   ASC);

CREATE  INDEX IE_modificado_claseproblema ON clase_problema
(modificado_id   ASC);

DROP INDEX xif1problema;
CREATE  INDEX IE_problema_organizacion ON problema
(organizacion_id   ASC);

CREATE  INDEX IE_problema_iniciativa_efecto ON problema
(iniciativa_efecto_id   ASC);

CREATE  INDEX IE_problema_auxiliar ON problema
(auxiliar_id   ASC);

DROP INDEX xif2problema;
CREATE  INDEX XIF12problema ON problema
(clase_id   ASC);

CREATE  INDEX IE_problema_estado ON problema
(estado_id   ASC);

DROP INDEX xif3problema;
CREATE  INDEX IE_problema_responsable ON problema
(responsable_id   ASC);

CREATE  INDEX IE_problema_creado ON problema
(creado_id   ASC);

CREATE  INDEX IE_problema_modificado ON problema
(modificado_id   ASC);

CREATE  INDEX IE_problema_indicador_efecto ON problema
(indicador_efecto_id   ASC);

ALTER TABLE accion
	ADD (CONSTRAINT FK_ACCION_ESTADO FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_ACCION_Problema FOREIGN KEY (problema_id) REFERENCES problema (problema_id) ON DELETE CASCADE);

ALTER TABLE accion
	ADD (CONSTRAINT FK_Accion_PadreId FOREIGN KEY (accion_id) REFERENCES accion (accion_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_creado_accion FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE accion
	ADD (CONSTRAINT FK_modificado_accion FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);
	
ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_padre_claseproblema FOREIGN KEY (padre_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_organizacion_claseproblema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE SET NULL);

UPDATE clase_problema SET creado_id = NULL WHERE creado_id NOT IN (SELECT usuario_id FROM afw_usuario);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_creado_claseproblema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE clase_problema
	ADD (CONSTRAINT FK_modificado_claseproblema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_organizacion_problema FOREIGN KEY (organizacion_id) REFERENCES organizacion (organizacion_id) ON DELETE CASCADE);

ALTER TABLE problema
	ADD (CONSTRAINT FK_estado_problema FOREIGN KEY (estado_id) REFERENCES estado_acciones (estado_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_responsable_problema FOREIGN KEY (responsable_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_creado_problema FOREIGN KEY (creado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_modificado_problema FOREIGN KEY (modificado_id) REFERENCES afw_usuario (usuario_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_indicador_efecto_problema FOREIGN KEY (indicador_efecto_id) REFERENCES indicador (indicador_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_iniciativa_efecto_problema FOREIGN KEY (iniciativa_efecto_id) REFERENCES iniciativa (iniciativa_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_auxiliar_problema FOREIGN KEY (auxiliar_id) REFERENCES responsable (responsable_id) ON DELETE SET NULL);

ALTER TABLE problema
	ADD (CONSTRAINT FK_problema_claseproblema FOREIGN KEY (clase_id) REFERENCES clase_problema (clase_id) ON DELETE CASCADE);
	
COMMIT;

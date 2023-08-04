CREATE TABLE afw_version
(
	version              VARCHAR2(50) NOT NULL ,
	build                VARCHAR2(8) NOT NULL ,
	dateBuild            VARCHAR2(50) NOT NULL ,
	createdAt            TIMESTAMP NOT NULL 
);

CREATE UNIQUE INDEX AK_afw_version ON afw_version (version   ASC, build   ASC, dateBuild   ASC, createdAt   ASC);

ALTER TABLE afw_version ADD CONSTRAINT  PK_afw_version PRIMARY KEY (version, build, dateBuild, createdAt);

COMMIT;

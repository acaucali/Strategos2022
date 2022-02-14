CREATE TABLE afw_version
(
	version              character varying(50) NOT NULL ,
	build                character varying(8) NOT NULL ,
	dateBuild            character varying(50) NOT NULL ,
	createdAt            timestamp without time zone NOT NULL 
);

CREATE UNIQUE INDEX AK_afw_version ON afw_version USING btree (version   ASC, build   ASC, dateBuild   ASC, createdAt   ASC);

ALTER TABLE afw_version ADD CONSTRAINT  PK_afw_version PRIMARY KEY (version, build, dateBuild, createdAt);

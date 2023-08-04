CREATE TABLE iniciativa_plan
(
	iniciativa_id        NUMBER(10) NOT NULL ,
	plan_id              NUMBER(10) NOT NULL 
);

CREATE UNIQUE INDEX AK_iniciativa_plan ON iniciativa_plan (iniciativa_id   ASC,plan_id   ASC);

ALTER TABLE iniciativa_plan
	ADD CONSTRAINT  PK_iniciativa_plan PRIMARY KEY (iniciativa_id,plan_id);

CREATE  INDEX IE_iniciativa_iniciativa_plan ON iniciativa_plan (iniciativa_id   ASC);
CREATE  INDEX IE_plan_iniciativa_plan ON iniciativa_plan (plan_id   ASC);


ALTER TABLE iniciativa_plan
	ADD (CONSTRAINT FK_iniciativa_iniciativa_plan FOREIGN KEY (iniciativa_id) REFERENCES iniciativa (iniciativa_id) ON DELETE CASCADE);

ALTER TABLE iniciativa_plan
	ADD (CONSTRAINT FK_plan_iniciaitva_plan FOREIGN KEY (plan_id) REFERENCES planes (plan_id) ON DELETE CASCADE);

COMMIT;
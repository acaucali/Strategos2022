DECLARE
	vCount NUMBER(5);
	vVersion VARCHAR2(50);
	vBuild VARCHAR2(50);

BEGIN
	SELECT COUNT(*) INTO vCount FROM afw_sistema WHERE actual is null;
	IF vCount <> 0 THEN 
	BEGIN
		SELECT version, build INTO vVersion, vBuild FROM afw_sistema;
		UPDATE afw_sistema SET actual = vVersion||'-'||vBuild;
	END;
	END IF;				
END;
/
COMMIT;

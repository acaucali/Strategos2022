CREATE USER STRATEGOS IDENTIFIED BY vision DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT EXPORT FULL DATABASE TO STRATEGOS;
GRANT IMPORT FULL DATABASE TO STRATEGOS;
GRANT UNLIMITED TABLESPACE TO STRATEGOS;
GRANT CONNECT TO STRATEGOS;
GRANT DBA TO STRATEGOS;
GRANT EXP_FULL_DATABASE TO STRATEGOS;
GRANT IMP_FULL_DATABASE TO STRATEGOS;
GRANT RESOURCE TO STRATEGOS;
ALTER USER STRATEGOS DEFAULT ROLE ALL;

COMMIT;
CREATE SEQUENCE task_sequence;

CREATE TABLE TASK (
    ENTITY_ID          BIGINT PRIMARY KEY DEFAULT nextval('task_sequence'),
    NAME               VARCHAR(256) NOT NULL,
    CREATED_DATE       TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC'),
    LAST_MODIFIED_DATE TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC')
);

GRANT SELECT, INSERT, UPDATE, DELETE ON TASK TO ${appRole};
GRANT SELECT ON TASK TO ${readerRole};
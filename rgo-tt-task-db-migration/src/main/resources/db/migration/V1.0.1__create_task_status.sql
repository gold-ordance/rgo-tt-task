CREATE SEQUENCE task_status_sequence;

CREATE TABLE task_status (
    PRIMARY KEY(entity_id),
    entity_id BIGINT      DEFAULT nextval('task_status_sequence'),
    name      VARCHAR(64) NOT NULL
);

CREATE UNIQUE INDEX task_status_name_idx
                 ON task_status(name)
         TABLESPACE ${tbsIndexes};

GRANT SELECT, INSERT, UPDATE, DELETE ON task_status TO ${appRole};
GRANT SELECT ON task_status TO ${readerRole};

INSERT INTO task_status(name)
VALUES ('TO DO'),
       ('IN PROGRESS'),
       ('DONE');
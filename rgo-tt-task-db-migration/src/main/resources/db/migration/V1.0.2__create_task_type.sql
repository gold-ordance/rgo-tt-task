CREATE SEQUENCE task_type_sequence;

CREATE TABLE task_type (
    PRIMARY KEY(entity_id),
    entity_id BIGINT      DEFAULT nextval('task_type_sequence'),
    name      VARCHAR(64) NOT NULL
);

CREATE UNIQUE INDEX task_type_name_idx
                 ON task_type(name)
         TABLESPACE ${tbsIndexes};

GRANT SELECT, INSERT, UPDATE, DELETE ON task_type TO ${appRole};
GRANT SELECT ON task_type TO ${readerRole};

INSERT INTO task_type(name)
VALUES ('Task'),
       ('Bug');
CREATE SEQUENCE task_type_sequence;

CREATE TABLE task_type (
    PRIMARY KEY(entity_id),
    entity_id BIGINT      DEFAULT nextval('task_type_sequence'),
    name      VARCHAR(64) NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE ON task_type TO ${appRole};
GRANT SELECT ON task_type TO ${readerRole};

INSERT INTO task_type(name)
VALUES ('Task'),
       ('Bug');
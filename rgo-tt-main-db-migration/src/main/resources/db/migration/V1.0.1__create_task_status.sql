CREATE SEQUENCE task_status_sequence;

CREATE TABLE task_status (
    PRIMARY KEY(entity_id),
    entity_id BIGINT      DEFAULT nextval('task_status_sequence'),
    name      VARCHAR(64) NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE ON task_status TO ${appRole};
GRANT SELECT ON task_status TO ${readerRole};

INSERT INTO task_status(name)
VALUES ('TO DO'),
       ('IN PROGRESS'),
       ('DONE');
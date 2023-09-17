CREATE SEQUENCE tasks_board_sequence;

CREATE TABLE tasks_board (
    PRIMARY KEY(entity_id),
    entity_id  BIGINT      DEFAULT nextval('tasks_board_sequence'),
    name       VARCHAR(64) NOT NULL,
    short_name VARCHAR(16) NOT NULL
);

GRANT SELECT, INSERT, UPDATE, DELETE ON tasks_board TO ${appRole};
GRANT SELECT ON tasks_board TO ${readerRole};
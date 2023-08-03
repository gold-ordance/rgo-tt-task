CREATE SEQUENCE task_sequence;

CREATE TABLE task (
    PRIMARY KEY(entity_id),
    entity_id          BIGINT        DEFAULT nextval('task_sequence'),
    name               VARCHAR(128)  NOT NULL,
    created_date       TIMESTAMP     DEFAULT (now() AT TIME ZONE 'UTC'),
    last_modified_date TIMESTAMP     DEFAULT (now() AT TIME ZONE 'UTC'),
    board_id           BIGINT        NOT NULL REFERENCES tasks_board(entity_id) ON DELETE CASCADE,
    status_id          BIGINT        NOT NULL REFERENCES task_status(entity_id) DEFAULT 1,
    description        VARCHAR(4096)
);

CREATE INDEX board_id_name_task_idx
          ON task(board_id, lower(name))
  TABLESPACE ${tbsIndexes};

GRANT SELECT, INSERT, UPDATE, DELETE ON task TO ${appRole};
GRANT SELECT ON task TO ${readerRole};
CREATE SEQUENCE task_sequence;

CREATE TABLE task (
    PRIMARY KEY(entity_id),
    entity_id          BIGINT         DEFAULT nextval('task_sequence'),
    name               VARCHAR(128)   NOT NULL,
    created_date       TIMESTAMP      DEFAULT (now() AT TIME ZONE 'UTC'),
    last_modified_date TIMESTAMP      DEFAULT (now() AT TIME ZONE 'UTC'),
    description        VARCHAR(4096),
    number             BIGINT,
    board_id           BIGINT         NOT NULL REFERENCES tasks_board(entity_id) ON DELETE CASCADE,
    type_id            BIGINT         NOT NULL REFERENCES task_type(entity_id),
    status_id          BIGINT         NOT NULL REFERENCES task_status(entity_id)
);

CREATE INDEX task_board_id_name_idx
          ON task(board_id, lower(name))
  TABLESPACE ${tbsIndexes};

CREATE UNIQUE INDEX task_board_id_number_idx
                 ON task(board_id, number)
         TABLESPACE ${tbsIndexes};

GRANT SELECT, INSERT, UPDATE, DELETE ON task TO ${appRole};
GRANT SELECT ON task TO ${readerRole};